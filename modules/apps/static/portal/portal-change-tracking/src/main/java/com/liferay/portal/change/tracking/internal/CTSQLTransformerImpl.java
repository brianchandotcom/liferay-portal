/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.change.tracking.internal;

import com.liferay.petra.io.unsync.UnsyncStringReader;
import com.liferay.portal.change.tracking.registry.CTModelRegistration;
import com.liferay.portal.change.tracking.registry.CTModelRegistry;
import com.liferay.portal.change.tracking.sql.CTSQLContextFactory;
import com.liferay.portal.change.tracking.sql.CTSQLModeThreadLocal;
import com.liferay.portal.change.tracking.sql.CTSQLTransformer;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.AllComparisonExpression;
import net.sf.jsqlparser.expression.AnalyticExpression;
import net.sf.jsqlparser.expression.AnyComparisonExpression;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.CastExpression;
import net.sf.jsqlparser.expression.CollateExpression;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.ExtractExpression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.HexValue;
import net.sf.jsqlparser.expression.IntervalExpression;
import net.sf.jsqlparser.expression.JdbcNamedParameter;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.JsonExpression;
import net.sf.jsqlparser.expression.KeepExpression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.MySQLGroupConcat;
import net.sf.jsqlparser.expression.NextValExpression;
import net.sf.jsqlparser.expression.NotExpression;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.NumericBind;
import net.sf.jsqlparser.expression.OracleHierarchicalExpression;
import net.sf.jsqlparser.expression.OracleHint;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.RowConstructor;
import net.sf.jsqlparser.expression.SignedExpression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.TimeKeyExpression;
import net.sf.jsqlparser.expression.TimeValue;
import net.sf.jsqlparser.expression.TimestampValue;
import net.sf.jsqlparser.expression.UserVariable;
import net.sf.jsqlparser.expression.ValueListExpression;
import net.sf.jsqlparser.expression.WhenClause;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseAnd;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseLeftShift;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseOr;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseRightShift;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseXor;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import net.sf.jsqlparser.expression.operators.arithmetic.Division;
import net.sf.jsqlparser.expression.operators.arithmetic.Modulo;
import net.sf.jsqlparser.expression.operators.arithmetic.Multiplication;
import net.sf.jsqlparser.expression.operators.arithmetic.Subtraction;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.expression.operators.relational.JsonOperator;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.Matches;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.expression.operators.relational.NamedExpressionList;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.expression.operators.relational.RegExpMatchOperator;
import net.sf.jsqlparser.expression.operators.relational.RegExpMySQLOperator;
import net.sf.jsqlparser.expression.operators.relational.SimilarToExpression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.JSqlParser;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Block;
import net.sf.jsqlparser.statement.Commit;
import net.sf.jsqlparser.statement.DescribeStatement;
import net.sf.jsqlparser.statement.ExplainStatement;
import net.sf.jsqlparser.statement.SetStatement;
import net.sf.jsqlparser.statement.ShowColumnsStatement;
import net.sf.jsqlparser.statement.ShowStatement;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitor;
import net.sf.jsqlparser.statement.Statements;
import net.sf.jsqlparser.statement.UseStatement;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.comment.Comment;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.view.AlterView;
import net.sf.jsqlparser.statement.create.view.CreateView;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.execute.Execute;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.merge.Merge;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.LateralSubSelect;
import net.sf.jsqlparser.statement.select.ParenthesisFromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.TableFunction;
import net.sf.jsqlparser.statement.select.ValuesList;
import net.sf.jsqlparser.statement.select.WithItem;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.upsert.Upsert;
import net.sf.jsqlparser.statement.values.ValuesStatement;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = CTSQLTransformer.class)
public class CTSQLTransformerImpl implements CTSQLTransformer {

	@Override
	public String transform(String sql) {
		long ctCollectionId = CTCollectionThreadLocal.getCTCollectionId();

		if (ctCollectionId == 0) {
			String transformedSQL = _portalCache.get(sql);

			if (transformedSQL != null) {
				return transformedSQL;
			}
		}

		boolean foundTable = false;

		for (String tableName : CTModelRegistry.getTableNames()) {
			if (sql.contains(tableName)) {
				foundTable = true;

				break;
			}
		}

		if (!foundTable) {
			_portalCache.put(sql, sql);

			return sql;
		}

		try {
			Statement statement = _jSqlParser.parse(
				new UnsyncStringReader(_escape(sql)));

			String transformedSQL = sql;

			if (statement instanceof Select) {
				statement.accept(
					new SelectStatementVisitor(
						ctCollectionId, CTSQLModeThreadLocal.getCTSQLMode()));

				transformedSQL = _unescape(statement.toString());
			}
			else if (statement instanceof Delete ||
					 statement instanceof Update) {

				statement.accept(
					new DeleteAndUpdateStatementVisitor(ctCollectionId));

				transformedSQL = _unescape(statement.toString());
			}

			if (ctCollectionId == 0) {
				_portalCache.put(sql, transformedSQL);
			}

			return transformedSQL;
		}
		catch (JSQLParserException jsqlpe) {
			throw new RuntimeException(
				"Failed to parse sql for " + sql, jsqlpe);
		}
	}

	@Activate
	@SuppressWarnings("unchecked")
	protected void activate() {
		_portalCache =
			(PortalCache<String, String>)_singleVMPool.getPortalCache(
				CTSQLTransformerImpl.class.getName());
	}

	@Deactivate
	protected void deactivate() {
		_portalCache.removeAll();
	}

	/**
	 * See https://github.com/JSQLParser/JSqlParser/issues/832
	 */
	private String _escape(String sql) {
		return StringUtil.replace(
			sql, "LIKE ? ESCAPE '\\'", "LIKE '[$LFR_LIKE_ESCAPE_STRING$]'");
	}

	/**
	 * See https://github.com/JSQLParser/JSqlParser/issues/832
	 */
	private String _unescape(String sql) {
		return StringUtil.replace(
			sql, "LIKE '[$LFR_LIKE_ESCAPE_STRING$]'", "LIKE ? ESCAPE '\\'");
	}

	private static final JSqlParser _jSqlParser = new CCJSqlParserManager();

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile CTSQLContextFactory _ctSQLContextFactory;

	private PortalCache<String, String> _portalCache;

	@Reference
	private SingleVMPool _singleVMPool;

	private abstract static class BaseStatementVisitor
		implements ExpressionVisitor, FromItemVisitor, ItemsListVisitor,
				   SelectItemVisitor, SelectVisitor, StatementVisitor {

		@Override
		public void visit(Addition addition) {
			_visit(addition);
		}

		@Override
		public void visit(AllColumns allColumns) {
		}

		@Override
		public void visit(AllComparisonExpression allComparisonExpression) {
			SubSelect select = allComparisonExpression.getSubSelect();

			SelectBody selectBody = select.getSelectBody();

			selectBody.accept(this);
		}

		@Override
		public void visit(AllTableColumns allTableColumns) {
		}

		@Override
		public void visit(Alter alter) {
		}

		@Override
		public void visit(AlterView alterView) {
		}

		@Override
		public void visit(AnalyticExpression analyticExpression) {
		}

		@Override
		public void visit(AndExpression andExpression) {
			_visit(andExpression);
		}

		@Override
		public void visit(AnyComparisonExpression anyComparisonExpression) {
			SubSelect select = anyComparisonExpression.getSubSelect();

			SelectBody selectBody = select.getSelectBody();

			selectBody.accept(this);
		}

		@Override
		public void visit(Between between) {
			Expression leftExpression = between.getLeftExpression();

			leftExpression.accept(this);

			Expression betweenExpressionStart =
				between.getBetweenExpressionStart();

			betweenExpressionStart.accept(this);

			Expression betweenExpressionEnd = between.getBetweenExpressionEnd();

			betweenExpressionEnd.accept(this);
		}

		@Override
		public void visit(BitwiseAnd bitwiseAnd) {
			_visit(bitwiseAnd);
		}

		@Override
		public void visit(BitwiseLeftShift bitwiseLeftShift) {
			_visit(bitwiseLeftShift);
		}

		@Override
		public void visit(BitwiseOr bitwiseOr) {
			_visit(bitwiseOr);
		}

		@Override
		public void visit(BitwiseRightShift bitwiseRightShift) {
			_visit(bitwiseRightShift);
		}

		@Override
		public void visit(BitwiseXor bitwiseXor) {
			_visit(bitwiseXor);
		}

		@Override
		public void visit(Block block) {
			Statements statements = block.getStatements();

			if (statements != null) {
				statements.accept(this);
			}
		}

		@Override
		public void visit(CaseExpression caseExpression) {
			Expression switchExpression = caseExpression.getSwitchExpression();

			if (switchExpression != null) {
				switchExpression.accept(this);
			}

			List<WhenClause> whenClauses = caseExpression.getWhenClauses();

			if (whenClauses != null) {
				for (WhenClause whenClause : whenClauses) {
					whenClause.accept(this);
				}
			}

			Expression elseExpression = caseExpression.getElseExpression();

			if (elseExpression != null) {
				elseExpression.accept(this);
			}
		}

		@Override
		public void visit(CastExpression castExpression) {
			Expression leftExpression = castExpression.getLeftExpression();

			leftExpression.accept(this);
		}

		@Override
		public void visit(CollateExpression collateExpression) {
		}

		@Override
		public void visit(Column column) {
		}

		@Override
		public void visit(Comment comment) {
		}

		@Override
		public void visit(Commit commit) {
		}

		@Override
		public void visit(Concat concat) {
			_visit(concat);
		}

		@Override
		public void visit(CreateIndex createIndex) {
		}

		@Override
		public void visit(CreateTable createTable) {
		}

		@Override
		public void visit(CreateView createView) {
		}

		@Override
		public void visit(DateTimeLiteralExpression dateTimeLiteralExpression) {
		}

		@Override
		public void visit(DateValue dateValue) {
		}

		@Override
		public void visit(Delete delete) {
			visit(delete.getTable());

			List<Join> joins = delete.getJoins();

			if (joins != null) {
				for (Join join : joins) {
					FromItem fromItem = join.getRightItem();

					fromItem.accept(this);
				}
			}

			delete.setWhere(_visit(delete.getWhere()));
		}

		@Override
		public void visit(DescribeStatement describeStatement) {
		}

		@Override
		public void visit(Division division) {
			_visit(division);
		}

		@Override
		public void visit(DoubleValue doubleValue) {
		}

		@Override
		public void visit(Drop drop) {
		}

		@Override
		public void visit(EqualsTo equalsTo) {
			_visit(equalsTo);
		}

		@Override
		public void visit(Execute execute) {
		}

		@Override
		public void visit(ExistsExpression existsExpression) {
			Expression rightExpression = existsExpression.getRightExpression();

			rightExpression.accept(this);
		}

		@Override
		public void visit(ExplainStatement explainStatement) {
		}

		@Override
		public void visit(ExpressionList expressionList) {
			for (Expression expression : expressionList.getExpressions()) {
				expression.accept(this);
			}
		}

		@Override
		public void visit(ExtractExpression extractExpression) {
		}

		@Override
		public void visit(Function function) {
			ExpressionList expressionList = function.getParameters();

			if (expressionList != null) {
				expressionList.accept(this);
			}
		}

		@Override
		public void visit(GreaterThan greaterThan) {
			_visit(greaterThan);
		}

		@Override
		public void visit(GreaterThanEquals greaterThanEquals) {
			_visit(greaterThanEquals);
		}

		@Override
		public void visit(HexValue hexValue) {
		}

		@Override
		public void visit(InExpression inExpression) {
			Expression leftExpression = inExpression.getLeftExpression();

			if (leftExpression != null) {
				leftExpression.accept(this);
			}
			else {
				ItemsList leftItemsList = inExpression.getLeftItemsList();

				if (leftItemsList == null) {
					leftItemsList.accept(this);
				}
			}

			ItemsList rightItemsList = inExpression.getRightItemsList();

			rightItemsList.accept(this);
		}

		@Override
		public void visit(Insert insert) {
		}

		@Override
		public void visit(IntervalExpression intervalExpression) {
		}

		@Override
		public void visit(IsNullExpression isNullExpression) {
		}

		@Override
		public void visit(JdbcNamedParameter jdbcNamedParameter) {
		}

		@Override
		public void visit(JdbcParameter jdbcParameter) {
		}

		@Override
		public void visit(JsonExpression jsonExpression) {
		}

		@Override
		public void visit(JsonOperator jsonOperator) {
		}

		@Override
		public void visit(KeepExpression keepExpression) {
		}

		@Override
		public void visit(LateralSubSelect lateralSubSelect) {
			SubSelect select = lateralSubSelect.getSubSelect();

			SelectBody selectBody = select.getSelectBody();

			selectBody.accept(this);
		}

		@Override
		public void visit(LikeExpression likeExpression) {
			_visit(likeExpression);
		}

		@Override
		public void visit(LongValue longValue) {
		}

		@Override
		public void visit(Matches matches) {
			_visit(matches);
		}

		@Override
		public void visit(Merge merge) {
		}

		@Override
		public void visit(MinorThan minorThan) {
			_visit(minorThan);
		}

		@Override
		public void visit(MinorThanEquals minorThanEquals) {
			_visit(minorThanEquals);
		}

		@Override
		public void visit(Modulo modulo) {
			_visit(modulo);
		}

		@Override
		public void visit(MultiExpressionList multiExprList) {
			for (ExpressionList expressionList : multiExprList.getExprList()) {
				expressionList.accept(this);
			}
		}

		@Override
		public void visit(Multiplication multiplication) {
			_visit(multiplication);
		}

		@Override
		public void visit(MySQLGroupConcat mySQLGroupConcat) {
		}

		@Override
		public void visit(NamedExpressionList namedExpressionList) {
			for (Expression expression : namedExpressionList.getExpressions()) {
				expression.accept(this);
			}
		}

		@Override
		public void visit(NextValExpression nextValExpression) {
		}

		@Override
		public void visit(NotEqualsTo notEqualsTo) {
			_visit(notEqualsTo);
		}

		@Override
		public void visit(NotExpression notExpression) {
			Expression expression = notExpression.getExpression();

			expression.accept(this);
		}

		@Override
		public void visit(NullValue nullValue) {
		}

		@Override
		public void visit(NumericBind numericBind) {
		}

		@Override
		public void visit(
			OracleHierarchicalExpression oracleHierarchicalExpression) {

			Expression startExpression =
				oracleHierarchicalExpression.getStartExpression();

			startExpression.accept(this);

			Expression connectExpression =
				oracleHierarchicalExpression.getConnectExpression();

			connectExpression.accept(this);
		}

		@Override
		public void visit(OracleHint oracleHint) {
		}

		@Override
		public void visit(OrExpression orExpression) {
			_visit(orExpression);
		}

		@Override
		public void visit(Parenthesis parenthesis) {
			Expression expression = parenthesis.getExpression();

			expression.accept(this);
		}

		@Override
		public void visit(ParenthesisFromItem parenthesisFromItem) {
			FromItem fromItem = parenthesisFromItem.getFromItem();

			fromItem.accept(this);
		}

		@Override
		public void visit(PlainSelect plainSelect) {
			List<SelectItem> selectItems = plainSelect.getSelectItems();

			if (selectItems != null) {
				for (SelectItem selectItem : selectItems) {
					selectItem.accept(this);
				}
			}

			FromItem fromItem = plainSelect.getFromItem();

			if (fromItem != null) {
				fromItem.accept(this);
			}

			List<Join> joins = plainSelect.getJoins();

			if (joins != null) {
				for (Join join : joins) {
					FromItem rightFromItem = join.getRightItem();

					rightFromItem.accept(this);

					if (join.isLeft() || join.isRight() || join.isFull() ||
						join.isOuter()) {

						allowNull = true;

						Expression onExpression = join.getOnExpression();

						if (onExpression != null) {
							join.setOnExpression(_visit(onExpression));
						}
					}
				}
			}

			plainSelect.setWhere(_visit(plainSelect.getWhere()));

			Expression having = plainSelect.getHaving();

			if (having != null) {
				having.accept(this);
			}

			OracleHierarchicalExpression oracleHierarchicalExpression =
				plainSelect.getOracleHierarchical();

			if (oracleHierarchicalExpression != null) {
				oracleHierarchicalExpression.accept(this);
			}
		}

		@Override
		public void visit(RegExpMatchOperator regExpMatchOperator) {
			_visit(regExpMatchOperator);
		}

		@Override
		public void visit(RegExpMySQLOperator regExpMySQLOperator) {
			_visit(regExpMySQLOperator);
		}

		@Override
		public void visit(Replace replace) {
		}

		@Override
		public void visit(RowConstructor rowConstructor) {
		}

		@Override
		public void visit(Select select) {
			SelectBody selectBody = select.getSelectBody();

			selectBody.accept(this);
		}

		@Override
		public void visit(SelectExpressionItem selectExpressionItem) {
			Expression expression = selectExpressionItem.getExpression();

			expression.accept(this);
		}

		@Override
		public void visit(SetOperationList setOperationList) {
			for (SelectBody selectBody : setOperationList.getSelects()) {
				selectBody.accept(newInstance());
			}
		}

		@Override
		public void visit(SetStatement setStatement) {
		}

		@Override
		public void visit(ShowColumnsStatement showColumnsStatement) {
		}

		@Override
		public void visit(ShowStatement showStatement) {
		}

		@Override
		public void visit(SignedExpression signedExpression) {
			Expression expression = signedExpression.getExpression();

			expression.accept(this);
		}

		@Override
		public void visit(SimilarToExpression similarToExpression) {
		}

		@Override
		public void visit(Statements statements) {
		}

		@Override
		public void visit(StringValue stringValue) {
		}

		@Override
		public void visit(SubJoin join) {
			FromItem fromItem = join.getLeft();

			fromItem.accept(this);

			for (Join currentJoin : join.getJoinList()) {
				FromItem currentFromItem = currentJoin.getRightItem();

				currentFromItem.accept(this);
			}
		}

		@Override
		public void visit(SubSelect select) {
			List<WithItem> withItems = select.getWithItemsList();

			if (withItems != null) {
				for (WithItem withItem : withItems) {
					withItem.accept(this);
				}
			}

			SelectBody selectBody = select.getSelectBody();

			selectBody.accept(newInstance());
		}

		@Override
		public void visit(Subtraction subtraction) {
			_visit(subtraction);
		}

		@Override
		public void visit(Table table) {
			_tableWrappers.add(new TableWrapper(table));
		}

		@Override
		public void visit(TableFunction tableFunction) {
		}

		@Override
		public void visit(TimeKeyExpression timeKeyExpression) {
		}

		@Override
		public void visit(TimestampValue timestampValue) {
		}

		@Override
		public void visit(TimeValue timeValue) {
		}

		@Override
		public void visit(Truncate truncate) {
		}

		@Override
		public void visit(Update update) {
			for (Table table : update.getTables()) {
				visit(table);
			}

			List<Expression> expressions = update.getExpressions();

			if (expressions != null) {
				for (Expression expression : expressions) {
					expression.accept(this);
				}
			}

			FromItem fromItem = update.getFromItem();

			if (fromItem != null) {
				fromItem.accept(this);
			}

			List<Join> joins = update.getJoins();

			if (joins != null) {
				for (Join join : update.getJoins()) {
					fromItem = join.getRightItem();

					fromItem.accept(this);
				}
			}

			update.setWhere(_visit(update.getWhere()));
		}

		@Override
		public void visit(Upsert upsert) {
		}

		@Override
		public void visit(UserVariable userVariable) {
		}

		@Override
		public void visit(UseStatement useStatement) {
		}

		@Override
		public void visit(ValueListExpression valueListExpression) {
			ExpressionList expressionList =
				valueListExpression.getExpressionList();

			expressionList.accept(this);
		}

		@Override
		public void visit(ValuesList valuesList) {
		}

		@Override
		public void visit(ValuesStatement valuesStatement) {
		}

		@Override
		public void visit(WhenClause whenClause) {
			Expression whenExpression = whenClause.getWhenExpression();

			if (whenExpression != null) {
				whenExpression.accept(this);
			}

			Expression thenExpression = whenClause.getThenExpression();

			if (thenExpression != null) {
				thenExpression.accept(this);
			}
		}

		@Override
		public void visit(WithItem withItem) {
			SelectBody selectBody = withItem.getSelectBody();

			selectBody.accept(this);
		}

		protected BaseStatementVisitor(long ctCollectionId) {
			this.ctCollectionId = ctCollectionId;
		}

		protected abstract Expression getWhereExpression(
			Table table, String tableName,
			CTModelRegistration ctModelRegistration);

		protected abstract BaseStatementVisitor newInstance();

		protected boolean allowNull;
		protected final long ctCollectionId;

		private void _visit(BinaryExpression binaryExpression) {
			Expression leftExpression = binaryExpression.getLeftExpression();

			leftExpression.accept(this);

			Expression rightExpression = binaryExpression.getRightExpression();

			rightExpression.accept(this);
		}

		private Expression _visit(Expression whereExpression) {
			if (whereExpression != null) {
				whereExpression.accept(this);
			}

			for (TableWrapper tableWrapper : _tableWrappers) {
				Table table = tableWrapper._table;

				String tableName = table.getName();

				CTModelRegistration ctModelRegistration =
					CTModelRegistry.getCTModelRegistration(tableName);

				if (ctModelRegistration != null) {
					Expression ctExpression = getWhereExpression(
						table, tableName, ctModelRegistration);

					if (whereExpression == null) {
						whereExpression = ctExpression;
					}
					else {
						whereExpression = new AndExpression(
							whereExpression, ctExpression);
					}
				}
			}

			return whereExpression;
		}

		private final Set<TableWrapper> _tableWrappers = new LinkedHashSet<>();

	}

	private static class DeleteAndUpdateStatementVisitor
		extends BaseStatementVisitor {

		@Override
		protected Expression getWhereExpression(
			Table table, String tableName,
			CTModelRegistration ctModelRegistration) {

			EqualsTo equalsTo = new EqualsTo();

			equalsTo.setLeftExpression(new Column(table, "ctCollectionId"));
			equalsTo.setRightExpression(new LongValue(ctCollectionId));

			return equalsTo;
		}

		@Override
		protected DeleteAndUpdateStatementVisitor newInstance() {
			return new DeleteAndUpdateStatementVisitor(ctCollectionId);
		}

		private DeleteAndUpdateStatementVisitor(long ctCollectionId) {
			super(ctCollectionId);
		}

	}

	private static class TableWrapper {

		@Override
		public boolean equals(Object object) {
			TableWrapper tableWrapper = (TableWrapper)object;

			Table table = tableWrapper._table;

			if (table == _table) {
				return true;
			}

			if (!Objects.equals(table.getName(), _table.getName())) {
				return false;
			}

			Alias alias1 = table.getAlias();
			Alias alias2 = _table.getAlias();

			if (alias1 == alias2) {
				return true;
			}

			if ((alias1 != null) && (alias2 != null) &&
				Objects.equals(alias1.getName(), alias2.getName())) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			String name = _table.getName();

			return name.hashCode();
		}

		@Override
		public String toString() {
			return _table.toString();
		}

		private TableWrapper(Table table) {
			_table = table;
		}

		private final Table _table;

	}

	private class SelectStatementVisitor extends BaseStatementVisitor {

		@Override
		protected Expression getWhereExpression(
			Table table, String tableName,
			CTModelRegistration ctModelRegistration) {

			Expression expression = _getWhereExpression(
				table, tableName, ctModelRegistration);

			if (allowNull) {
				IsNullExpression isNullExpression = new IsNullExpression();

				isNullExpression.setLeftExpression(
					new Column(table, "ctCollectionId"));

				expression = new Parenthesis(
					new OrExpression(expression, isNullExpression));
			}

			return expression;
		}

		@Override
		protected SelectStatementVisitor newInstance() {
			return new SelectStatementVisitor(ctCollectionId, _ctSQLMode);
		}

		private SelectStatementVisitor(
			long ctCollectionId, CTSQLModeThreadLocal.CTSQLMode ctSQLMode) {

			super(ctCollectionId);

			_ctSQLMode = ctSQLMode;
		}

		private Expression _getWhereExpression(
			Table table, String tableName,
			CTModelRegistration ctModelRegistration) {

			if (CTSQLModeThreadLocal.CTSQLMode.CT_ONLY == _ctSQLMode) {
				EqualsTo equalsTo = new EqualsTo();

				equalsTo.setLeftExpression(new Column(table, "ctCollectionId"));
				equalsTo.setRightExpression(new LongValue(ctCollectionId));

				return equalsTo;
			}

			Column ctCollectionIdColumn = new Column(table, "ctCollectionId");

			EqualsTo equalsToCTCollectionIdZero = new EqualsTo();

			equalsToCTCollectionIdZero.setLeftExpression(ctCollectionIdColumn);

			equalsToCTCollectionIdZero.setRightExpression(new LongValue("0"));

			if (ctCollectionId == 0) {
				return equalsToCTCollectionIdZero;
			}

			CTSQLContextFactory ctSQLContextFactory = _ctSQLContextFactory;

			if (ctSQLContextFactory == null) {
				return equalsToCTCollectionIdZero;
			}

			CTSQLContextFactory.CTSQLContext ctSQLContext =
				ctSQLContextFactory.createCTSQLContext(
					ctCollectionId, tableName,
					ctModelRegistration.getPrimaryColumnName(),
					ctModelRegistration.getModelClass());

			List<Long> excludePKs = ctSQLContext.getExcludePKs();

			if (excludePKs.isEmpty()) {
				if (ctSQLContext.hasAdded()) {
					EqualsTo equalsToCTCollectionIdCurrent = new EqualsTo();

					equalsToCTCollectionIdCurrent.setLeftExpression(
						ctCollectionIdColumn);
					equalsToCTCollectionIdCurrent.setRightExpression(
						new LongValue(ctCollectionId));

					return new Parenthesis(
						new OrExpression(
							equalsToCTCollectionIdZero,
							equalsToCTCollectionIdCurrent));
				}

				return equalsToCTCollectionIdZero;
			}

			List<Expression> notInExpressions = new ArrayList<>(
				excludePKs.size());

			for (Long excludePK : excludePKs) {
				notInExpressions.add(new LongValue(excludePK));
			}

			if (ctSQLContext.hasAdded() || ctSQLContext.hasModified()) {
				InExpression inExpression = new InExpression(
					new Column(
						table, ctModelRegistration.getPrimaryColumnName()),
					new ExpressionList(notInExpressions));

				inExpression.setNot(true);

				EqualsTo equalsToCTCollectionIdCurrent = new EqualsTo();

				equalsToCTCollectionIdCurrent.setLeftExpression(
					ctCollectionIdColumn);
				equalsToCTCollectionIdCurrent.setRightExpression(
					new LongValue(ctCollectionId));

				return new Parenthesis(
					new AndExpression(
						inExpression,
						new OrExpression(
							equalsToCTCollectionIdZero,
							equalsToCTCollectionIdCurrent)));
			}

			InExpression inExpression = new InExpression(
				new Column(table, ctModelRegistration.getPrimaryColumnName()),
				new ExpressionList(notInExpressions));

			inExpression.setNot(true);

			return new AndExpression(inExpression, equalsToCTCollectionIdZero);
		}

		private final CTSQLModeThreadLocal.CTSQLMode _ctSQLMode;

	}

}