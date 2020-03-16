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

package com.liferay.change.tracking.internal.reference.helper;

import com.liferay.change.tracking.internal.reference.TableReferenceInfo;
import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.helper.TableReferenceDefinitionHelper;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.ast.ASTNode;
import com.liferay.petra.sql.dsl.ast.ASTNodeListener;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.petra.sql.dsl.spi.expression.DefaultPredicate;
import com.liferay.petra.sql.dsl.spi.expression.Operand;
import com.liferay.petra.sql.dsl.spi.query.From;
import com.liferay.petra.sql.dsl.spi.query.Join;
import com.liferay.petra.sql.dsl.spi.query.JoinType;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Preston Crary
 */
public class TableReferenceDefinitionHelperImpl<T extends Table<T>>
	implements TableReferenceDefinitionHelper<T> {

	public TableReferenceDefinitionHelperImpl(
		TableReferenceDefinition<T> tableReferenceDefinition,
		Column<T, ?> primaryKeyColumn) {

		_tableReferenceDefinition = tableReferenceDefinition;
		_primaryKeyColumn = primaryKeyColumn;
	}

	@Override
	public void defineNonreferenceColumn(Column<T, ?> column) {
		_definedColumns.add(column);
	}

	@Override
	public void defineReferenceInnerJoin(
		Function<FromStep, JoinStep> joinFunction) {

		JoinStep joinStep = joinFunction.apply(_validationFromStep);

		if (!(joinStep instanceof Join)) {
			throw new IllegalArgumentException(
				StringBundler.concat("Missing join in \"", joinStep, "\""));
		}

		JoinStepASTNodeListener joinStepASTNodeListener =
			new JoinStepASTNodeListener();

		joinStep.toSQL(_emptyStringConsumer, joinStepASTNodeListener);

		if (joinStepASTNodeListener._fromTable == null) {
			throw new IllegalArgumentException(
				StringBundler.concat(
					"Join function must use provided FromStep for JoinStep \"",
					joinStep, "\""));
		}

		if (!joinStepASTNodeListener._hasRequiredTable) {
			throw new IllegalArgumentException(
				StringBundler.concat(
					"Required table \"", _tableReferenceDefinition.getTable(),
					"\" is unused in JoinStep \"", joinStep, "\""));
		}

		if (joinStepASTNodeListener._invalidJoinType != null) {
			throw new IllegalArgumentException(
				StringBundler.concat(
					"Invalid join type \"",
					joinStepASTNodeListener._invalidJoinType,
					"\" for JoinStep \"", joinStep, "\""));
		}

		if (joinStepASTNodeListener._invalidOperand != null) {
			throw new IllegalArgumentException(
				StringBundler.concat(
					"Invalid Predicate Operand \"",
					joinStepASTNodeListener._invalidOperand,
					"\" for JoinStep \"", joinStep, "\""));
		}

		if (!joinStepASTNodeListener._tables.containsAll(
				joinStepASTNodeListener._columnTables)) {

			List<Table<?>> columnTables = new ArrayList<>(
				joinStepASTNodeListener._columnTables);

			Comparator<Table<?>> comparator = Comparator.comparing(
				Table::getName);

			columnTables.sort(comparator);

			List<Table<?>> joinTables = new ArrayList<>(
				joinStepASTNodeListener._tables);

			joinTables.sort(comparator);

			throw new IllegalArgumentException(
				StringBundler.concat(
					"Predicate column tables ", columnTables,
					" do not match join tables ", joinTables,
					" for joinStep \"", joinStep, "\""));
		}

		List<Function<FromStep, JoinStep>> joinList = null;

		if (joinStepASTNodeListener._parentJoinFunction) {
			joinList = _parentJoinMap.computeIfAbsent(
				joinStepASTNodeListener._fromTable,
				fromTable -> new ArrayList<>());
		}
		else {
			joinList = _childJoinMap.computeIfAbsent(
				joinStepASTNodeListener._fromTable,
				fromTable -> new ArrayList<>());
		}

		joinList.add(joinFunction);
	}

	public TableReferenceInfo<T> getTableReferenceInfo() {
		T table = _tableReferenceDefinition.getTable();

		List<Column<T, ?>> undefinedColumns = null;

		for (Column<T, ?> column : table.getColumns()) {
			if (!Objects.equals(column.getName(), "mvccVersion") &&
				!column.isPrimaryKey() && !_definedColumns.contains(column)) {

				if (undefinedColumns == null) {
					undefinedColumns = new ArrayList<>();
				}

				undefinedColumns.add(column);
			}
		}

		if (undefinedColumns != null) {
			_log.error(
				StringBundler.concat(
					_tableReferenceDefinition, " did not define columns ",
					undefinedColumns));

			return null;
		}

		return new TableReferenceInfo<>(
			_tableReferenceDefinition, _primaryKeyColumn, _parentJoinMap,
			_childJoinMap);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TableReferenceDefinitionHelperImpl.class);

	private static final Consumer<String> _emptyStringConsumer = string -> {
	};
	private static final FromStep _validationFromStep =
		new ValidationFromStep();
	private static final Set<Operand> _validOperands = new HashSet<Operand>() {
		{
			add(Operand.AND);
			add(Operand.EQUAL);
			add(Operand.LIKE);
		}
	};

	private final Map<Table<?>, List<Function<FromStep, JoinStep>>>
		_childJoinMap = new HashMap<>();
	private final Set<Column<?, ?>> _definedColumns = new HashSet<>();
	private final Map<Table<?>, List<Function<FromStep, JoinStep>>>
		_parentJoinMap = new HashMap<>();
	private final Column<T, ?> _primaryKeyColumn;
	private final TableReferenceDefinition<T> _tableReferenceDefinition;

	private static class ValidationFromStep implements FromStep {

		@Override
		public Table<?> as(String name) {
			throw new UnsupportedOperationException();
		}

		@Override
		public JoinStep from(Table<?> table) {
			return new From(this, table);
		}

		@Override
		public void toSQL(
			Consumer<String> consumer, ASTNodeListener astNodeListener) {

			consumer.accept(StringPool.TRIPLE_PERIOD);
		}

		@Override
		public DSLQuery union(DSLQuery dslQuery) {
			throw new UnsupportedOperationException();
		}

		@Override
		public DSLQuery unionAll(DSLQuery dslQuery) {
			throw new UnsupportedOperationException();
		}

	}

	private class JoinStepASTNodeListener implements ASTNodeListener {

		@Override
		public void process(ASTNode astNode) {
			if (astNode instanceof Column) {
				Column<?, ?> column = (Column<?, ?>)astNode;

				_columnTables.add(column.getTable());

				_definedColumns.add(column);

				if (!_hasRequiredTable &&
					Objects.equals(
						_tableReferenceDefinition.getTable(),
						column.getTable())) {

					_hasRequiredTable = true;
				}

				if (column.isPrimaryKey() &&
					Objects.equals(_fromTable, column.getTable())) {

					_parentJoinFunction = true;
				}
			}
			else if (astNode instanceof DefaultPredicate) {
				DefaultPredicate defaultPredicate = (DefaultPredicate)astNode;

				Operand operand = defaultPredicate.getOperand();

				if (!_validOperands.contains(operand) &&
					(_invalidOperand == null)) {

					_invalidOperand = operand;
				}
			}
			else if (astNode instanceof From) {
				From from = (From)astNode;

				if (from.getChild() == _validationFromStep) {
					_fromTable = from.getTable();

					_tables.add(from.getTable());
				}
			}
			else if (astNode instanceof Join) {
				Join join = (Join)astNode;

				JoinType joinType = join.getJoinType();

				if ((joinType != JoinType.INNER) &&
					(_invalidJoinType == null)) {

					_invalidJoinType = joinType;
				}

				_tables.add(join.getTable());
			}
		}

		private JoinStepASTNodeListener() {
		}

		private Set<Table<?>> _columnTables = new HashSet<>();
		private Table<?> _fromTable;
		private boolean _hasRequiredTable;
		private JoinType _invalidJoinType;
		private Operand _invalidOperand;
		private boolean _parentJoinFunction;
		private Set<Table<?>> _tables = new HashSet<>();

	}

}