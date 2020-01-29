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

package com.liferay.petra.sql.dsl.ast;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLFunctionUtil;
import com.liferay.petra.sql.dsl.DSLQueryUtil;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.ast.impl.BaseASTNode;
import com.liferay.petra.sql.dsl.ast.impl.DefaultASTNodeListener;
import com.liferay.petra.sql.dsl.expressions.Alias;
import com.liferay.petra.sql.dsl.expressions.Expression;
import com.liferay.petra.sql.dsl.expressions.Predicate;
import com.liferay.petra.sql.dsl.expressions.WhenThenStep;
import com.liferay.petra.sql.dsl.expressions.impl.AggregateExpression;
import com.liferay.petra.sql.dsl.expressions.impl.AliasImpl;
import com.liferay.petra.sql.dsl.expressions.impl.CaseWhenThen;
import com.liferay.petra.sql.dsl.expressions.impl.DSLFunction;
import com.liferay.petra.sql.dsl.expressions.impl.DSLFunctionType;
import com.liferay.petra.sql.dsl.expressions.impl.ElseEnd;
import com.liferay.petra.sql.dsl.expressions.impl.NullExpression;
import com.liferay.petra.sql.dsl.expressions.impl.Operand;
import com.liferay.petra.sql.dsl.expressions.impl.PredicateImpl;
import com.liferay.petra.sql.dsl.expressions.impl.Scalar;
import com.liferay.petra.sql.dsl.expressions.impl.ScalarList;
import com.liferay.petra.sql.dsl.expressions.impl.WhenThen;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.GroupByStep;
import com.liferay.petra.sql.dsl.query.HavingStep;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.petra.sql.dsl.query.LimitStep;
import com.liferay.petra.sql.dsl.query.OrderByExpression;
import com.liferay.petra.sql.dsl.query.OrderByInfo;
import com.liferay.petra.sql.dsl.query.OrderByStep;
import com.liferay.petra.sql.dsl.query.impl.From;
import com.liferay.petra.sql.dsl.query.impl.GroupBy;
import com.liferay.petra.sql.dsl.query.impl.Having;
import com.liferay.petra.sql.dsl.query.impl.Join;
import com.liferay.petra.sql.dsl.query.impl.JoinType;
import com.liferay.petra.sql.dsl.query.impl.Limit;
import com.liferay.petra.sql.dsl.query.impl.OrderBy;
import com.liferay.petra.sql.dsl.query.impl.OrderByExpressionImpl;
import com.liferay.petra.sql.dsl.query.impl.QueryExpression;
import com.liferay.petra.sql.dsl.query.impl.QueryTable;
import com.liferay.petra.sql.dsl.query.impl.Select;
import com.liferay.petra.sql.dsl.query.impl.SetOperation;
import com.liferay.petra.sql.dsl.query.impl.SetOperationType;
import com.liferay.petra.sql.dsl.query.impl.Where;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.sql.Clob;
import java.sql.Types;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class SQLDSLTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor() {

			@Override
			public void appendAssertClasses(List<Class<?>> assertClasses) {
				assertClasses.clear();

				assertClasses.add(AggregateExpression.class);
				assertClasses.add(AliasImpl.class);
				assertClasses.add(BaseASTNode.class);
				assertClasses.add(CaseWhenThen.class);
				assertClasses.add(Column.class);
				assertClasses.add(DefaultASTNodeListener.class);
				assertClasses.add(DSLFunction.class);
				assertClasses.add(DSLFunctionType.class);
				assertClasses.add(DSLFunctionUtil.class);
				assertClasses.add(DSLQueryUtil.class);
				assertClasses.add(ElseEnd.class);
				assertClasses.add(From.class);
				assertClasses.add(GroupBy.class);
				assertClasses.add(Having.class);
				assertClasses.add(Join.class);
				assertClasses.add(JoinType.class);
				assertClasses.add(Limit.class);
				assertClasses.add(NullExpression.class);
				assertClasses.add(Operand.class);
				assertClasses.add(OrderBy.class);
				assertClasses.add(OrderByExpressionImpl.class);
				assertClasses.add(PredicateImpl.class);
				assertClasses.add(QueryExpression.class);
				assertClasses.add(QueryTable.class);
				assertClasses.add(Scalar.class);
				assertClasses.add(ScalarList.class);
				assertClasses.add(Select.class);
				assertClasses.add(SetOperation.class);
				assertClasses.add(SetOperationType.class);
				assertClasses.add(Table.class);
				assertClasses.add(WhenThen.class);
				assertClasses.add(Where.class);
			}

		};

	@Test
	public void testAggregateExpression() {
		Expression<Long> countExpression = DSLFunctionUtil.countDistinct(
			ReferenceExampleTable.TABLE.name);

		AggregateExpression<Long> countAggregateExpression =
			(AggregateExpression<Long>)countExpression;

		Assert.assertTrue(
			countAggregateExpression.toString(),
			countAggregateExpression.isDistinct());
		Assert.assertSame(
			ReferenceExampleTable.TABLE.name,
			countAggregateExpression.getExpression());
		Assert.assertEquals("count", countAggregateExpression.getName());
	}

	@Test
	public void testAlias() {
		String name = "alias";

		Alias<String> alias = ReferenceExampleTable.TABLE.name.as(name);

		Assert.assertSame(name, alias.getName());
	}

	@Test
	public void testBaseASTNode() {
		FromStep fromStep = DSLQueryUtil.select();

		From from = new From(fromStep, MainExampleTable.TABLE);

		Assert.assertSame(fromStep, from.getChild());

		OrderBy orderBy = new OrderBy(
			from,
			new OrderByExpression[] {
				MainExampleTable.TABLE.mainExampleId.ascending()
			});

		Assert.assertSame(from, orderBy.getChild());

		Assert.assertEquals(
			"select * from MainExample order by MainExample.mainExampleId asc",
			orderBy.toString());

		JoinStep joinStep = from.innerJoinON(
			ReferenceExampleTable.TABLE,
			ReferenceExampleTable.TABLE.mainExampleId.eq(
				MainExampleTable.TABLE.mainExampleId));

		OrderBy orderBy2 = orderBy.withNewChild(joinStep);

		Assert.assertNotSame(orderBy, orderBy2);

		Assert.assertEquals(
			"select * from MainExample inner join ReferenceExample on " +
				"ReferenceExample.mainExampleId = MainExample.mainExampleId " +
					"order by MainExample.mainExampleId asc",
			orderBy2.toString());

		try {
			ReferenceExampleTable.TABLE.withNewChild(null);
		}
		catch (RuntimeException runtimeException) {
			Throwable cause = runtimeException.getCause();

			Assert.assertSame(
				CloneNotSupportedException.class, cause.getClass());
		}
	}

	@Test
	public void testCaseSelect() {
		Alias<String> numberAlias = DSLFunctionUtil.casesWhenThen(
			MainExampleTable.TABLE.mainExampleId.eq(1L), "one"
		).whenThen(
			MainExampleTable.TABLE.mainExampleId.eq(2L), "two"
		).whenThen(
			MainExampleTable.TABLE.mainExampleId.eq(3L), "three"
		).elseEnd(
			"unknown"
		).as(
			"number"
		);

		DSLQuery dslQuery = DSLQueryUtil.select(
			numberAlias
		).from(
			MainExampleTable.TABLE
		).where(
			numberAlias.neq("unknown")
		);

		DefaultASTNodeListener defaultASTNodeListener =
			new DefaultASTNodeListener();

		Assert.assertEquals(
			StringBundler.concat(
				"select case when MainExample.mainExampleId = ? then ? when ",
				"MainExample.mainExampleId = ? then ? when MainExample.",
				"mainExampleId = ? then ? else ? end number from MainExample ",
				"where number != ?"),
			dslQuery.toSQL(defaultASTNodeListener));

		Assert.assertEquals(
			Arrays.asList(
				1L, "one", 2L, "two", 3L, "three", "unknown", "unknown"),
			defaultASTNodeListener.getScalarValues());
	}

	@Test
	public void testCaseWhenThen() {
		Predicate predicate = MainExampleTable.TABLE.mainExampleId.eq(1L);

		Scalar<String> scalar = new Scalar<>("one");

		CaseWhenThen<String> caseWhenThen = new CaseWhenThen<>(
			predicate, scalar);

		Assert.assertSame(predicate, caseWhenThen.getPredicate());
		Assert.assertSame(scalar, caseWhenThen.getThenExpression());
	}

	@Test
	public void testColumn() {
		MainExampleTable alias = MainExampleTable.TABLE.as("alias");

		Assert.assertEquals(
			MainExampleTable.TABLE.mainExampleId,
			MainExampleTable.TABLE.mainExampleId);
		Assert.assertEquals(
			MainExampleTable.TABLE.mainExampleId, alias.mainExampleId);
		Assert.assertEquals(
			MainExampleTable.TABLE.mainExampleId.hashCode(),
			alias.mainExampleId.hashCode());
		Assert.assertNotEquals(
			MainExampleTable.TABLE.mainExampleId, MainExampleTable.TABLE.name);
		Assert.assertNotEquals(
			MainExampleTable.TABLE.mainExampleId,
			ReferenceExampleTable.TABLE.mainExampleId);
		Assert.assertNotEquals(
			MainExampleTable.TABLE.mainExampleId, MainExampleTable.TABLE);

		Assert.assertSame(alias, alias.mainExampleId.getTable());
	}

	@Test
	public void testConstructors() {
		new DSLFunctionUtil();
		new DSLQueryUtil();
	}

	@Test
	public void testDerivedTable() {
		ReferenceExampleTable referenceExampleTable =
			ReferenceExampleTable.TABLE.as("referenceExample");

		DSLQuery dslQuery = DSLQueryUtil.select(
		).from(
			MainExampleTable.TABLE
		).leftJoinOn(
			DSLQueryUtil.select(
				ReferenceExampleTable.TABLE.mainExampleId,
				ReferenceExampleTable.TABLE.name
			).from(
				ReferenceExampleTable.TABLE
			).groupBy(
				ReferenceExampleTable.TABLE.mainExampleId,
				ReferenceExampleTable.TABLE.name
			).having(
				DSLFunctionUtil.count(
					ReferenceExampleTable.TABLE.name
				).gt(
					3L
				)
			).as(
				referenceExampleTable.getName()
			),
			referenceExampleTable.mainExampleId.eq(
				MainExampleTable.TABLE.mainExampleId)
		).orderBy(
			ReferenceExampleTable.TABLE.name.ascending()
		);

		DefaultASTNodeListener defaultASTNodeListener =
			new DefaultASTNodeListener();

		Assert.assertEquals(
			StringBundler.concat(
				"select * from MainExample left join (select ",
				"ReferenceExample.mainExampleId, ReferenceExample.name from ",
				"ReferenceExample group by ReferenceExample.mainExampleId, ",
				"ReferenceExample.name having count(ReferenceExample.name) > ",
				"?) referenceExample on referenceExample.mainExampleId = ",
				"MainExample.mainExampleId order by ReferenceExample.name asc"),
			dslQuery.toSQL(defaultASTNodeListener));

		Assert.assertArrayEquals(
			new String[] {"MainExample", "ReferenceExample"},
			defaultASTNodeListener.getTableNames());
	}

	@Test
	public void testElseEnd() {
		WhenThenStep<String> whenThenStep = DSLFunctionUtil.casesWhenThen(
			MainExampleTable.TABLE.mainExampleId.eq(
				ReferenceExampleTable.TABLE.mainExampleId),
			"equals");

		String scalarValue = "not equals";

		ElseEnd<String> elseEnd = new ElseEnd<>(
			whenThenStep, new Scalar<>(scalarValue));

		Scalar<String> scalar = (Scalar<String>)elseEnd.getElseExpression();

		Assert.assertSame(scalarValue, scalar.getValue());
	}

	@Test
	public void testFrom() {
		From from = new From(DSLQueryUtil.count(), MainExampleTable.TABLE);

		Assert.assertSame(MainExampleTable.TABLE, from.getTable());
	}

	@Test
	public void testFunction() {
		Expression<?>[] expressions = new Expression<?>[] {
			MainExampleTable.TABLE.mainExampleId, MainExampleTable.TABLE.flag
		};

		DSLFunction<Long> dslFunction = new DSLFunction<>(
			DSLFunctionType.BITWISE_AND, expressions);

		Assert.assertSame(
			DSLFunctionType.BITWISE_AND, dslFunction.getDslFunctionType());

		Assert.assertSame(expressions, dslFunction.getExpressions());

		try {
			new DSLFunction<>(DSLFunctionType.BITWISE_AND);
		}
		catch (Exception exception) {
			Assert.assertSame(
				IllegalArgumentException.class, exception.getClass());
		}
	}

	@Test
	public void testFunctions() {
		Assert.assertEquals(
			"MainExample.mainExampleId + ?",
			String.valueOf(
				DSLFunctionUtil.add(MainExampleTable.TABLE.mainExampleId, 2L)));
		Assert.assertEquals(
			"avg(MainExample.mainExampleId)",
			String.valueOf(
				DSLFunctionUtil.avg(MainExampleTable.TABLE.mainExampleId)));
		Assert.assertEquals(
			"BITAND(MainExample.mainExampleId, ?)",
			String.valueOf(
				DSLFunctionUtil.bitAnd(
					MainExampleTable.TABLE.mainExampleId, 2L)));
		Assert.assertEquals(
			"CAST_CLOB_TEXT(MainExample.description)",
			String.valueOf(
				DSLFunctionUtil.castClobText(
					MainExampleTable.TABLE.description)));
		Assert.assertEquals(
			"CAST_LONG(MainExample.name)",
			String.valueOf(
				DSLFunctionUtil.castLong(MainExampleTable.TABLE.name)));
		Assert.assertEquals(
			"CAST_TEXT(MainExample.mainExampleId)",
			String.valueOf(
				DSLFunctionUtil.castText(
					MainExampleTable.TABLE.mainExampleId)));
		Assert.assertEquals(
			"CONCAT(MainExample.name, ?, ReferenceExample.name)",
			String.valueOf(
				DSLFunctionUtil.concat(
					MainExampleTable.TABLE.name, new Scalar<>("__delimiter__"),
					ReferenceExampleTable.TABLE.name)));
		Assert.assertEquals(
			"count(MainExample.name)",
			String.valueOf(DSLFunctionUtil.count(MainExampleTable.TABLE.name)));
		Assert.assertEquals(
			"LOWER(MainExample.name)",
			String.valueOf(DSLFunctionUtil.lower(MainExampleTable.TABLE.name)));
		Assert.assertEquals(
			"MainExample.mainExampleId / ?",
			String.valueOf(
				DSLFunctionUtil.divide(
					MainExampleTable.TABLE.mainExampleId, 2L)));
		Assert.assertEquals(
			"max(MainExample.mainExampleId)",
			String.valueOf(
				DSLFunctionUtil.max(MainExampleTable.TABLE.mainExampleId)));
		Assert.assertEquals(
			"min(MainExample.mainExampleId)",
			String.valueOf(
				DSLFunctionUtil.min(MainExampleTable.TABLE.mainExampleId)));
		Assert.assertEquals(
			"MainExample.mainExampleId * ?",
			String.valueOf(
				DSLFunctionUtil.multiply(
					MainExampleTable.TABLE.mainExampleId, 2L)));
		Assert.assertEquals(
			"MainExample.mainExampleId - ?",
			String.valueOf(
				DSLFunctionUtil.subtract(
					MainExampleTable.TABLE.mainExampleId, 2L)));
		Assert.assertEquals(
			"sum(MainExample.mainExampleId)",
			String.valueOf(
				DSLFunctionUtil.sum(MainExampleTable.TABLE.mainExampleId)));
	}

	@Test
	public void testGroupBy() {
		From from = new From(
			DSLQueryUtil.select(
				MainExampleTable.TABLE.mainExampleId,
				MainExampleTable.TABLE.name),
			MainExampleTable.TABLE);

		GroupBy groupBy = new GroupBy(from, MainExampleTable.TABLE.name);

		Expression<?>[] expressions = groupBy.getExpressions();

		Assert.assertEquals(
			Arrays.toString(expressions), 1, expressions.length);

		Assert.assertSame(MainExampleTable.TABLE.name, expressions[0]);

		DSLQuery dslQuery = groupBy.limit(0, 20);

		Assert.assertEquals(
			"select MainExample.mainExampleId, MainExample.name from " +
				"MainExample group by MainExample.name ",
			dslQuery.toString());

		try {
			from.groupBy();
		}
		catch (Exception exception) {
			Assert.assertSame(
				IllegalArgumentException.class, exception.getClass());
		}
	}

	@Test
	public void testHaving() {
		HavingStep havingStep = DSLQueryUtil.select(
			MainExampleTable.TABLE.mainExampleId
		).from(
			MainExampleTable.TABLE
		).groupBy(
			MainExampleTable.TABLE.name
		);

		OrderByStep orderByStep = havingStep.having(null);

		Assert.assertEquals(
			"select MainExample.mainExampleId from MainExample group by " +
				"MainExample.name",
			orderByStep.toString());

		Predicate predicate = DSLFunctionUtil.count(
			MainExampleTable.TABLE.name
		).gt(
			10L
		);

		Having having = new Having(havingStep, predicate);

		Assert.assertEquals(
			"select MainExample.mainExampleId from MainExample group by " +
				"MainExample.name having count(MainExample.name) > ?",
			having.toString());

		Assert.assertSame(predicate, having.getPredicate());
	}

	@Test
	public void testJoin() {
		Predicate onPredicate = ReferenceExampleTable.TABLE.mainExampleId.eq(
			MainExampleTable.TABLE.mainExampleId);

		Join join = new Join(
			DSLQueryUtil.countDistinct(
				MainExampleTable.TABLE.name
			).from(
				MainExampleTable.TABLE
			),
			JoinType.INNER, ReferenceExampleTable.TABLE, onPredicate);

		Assert.assertSame(ReferenceExampleTable.TABLE, join.getTable());
		Assert.assertSame(JoinType.INNER, join.getJoinType());
		Assert.assertSame(onPredicate, join.getOnPredicate());
	}

	@Test
	public void testJoinCount() {
		DSLQuery dslQuery = DSLQueryUtil.countDistinct(
			MainExampleTable.TABLE.name
		).from(
			MainExampleTable.TABLE
		).leftJoinOn(
			MainExampleTable.TABLE, null
		).innerJoinON(
			ReferenceExampleTable.TABLE,
			ReferenceExampleTable.TABLE.mainExampleId.eq(
				MainExampleTable.TABLE.mainExampleId)
		).innerJoinON(
			ReferenceExampleTable.TABLE, null
		).where(
			MainExampleTable.TABLE.name.neq("")
		);

		Assert.assertEquals(
			StringBundler.concat(
				"select count(distinct MainExample.name) COUNT_VALUE from ",
				"MainExample inner join ReferenceExample on ",
				"ReferenceExample.mainExampleId = MainExample.mainExampleId ",
				"where MainExample.name != ?"),
			dslQuery.toString());
	}

	@Test
	public void testLeftJoin() {
		DSLQuery dslQuery = DSLQueryUtil.select(
			MainExampleTable.TABLE.mainExampleId
		).from(
			MainExampleTable.TABLE
		).leftJoinOn(
			ReferenceExampleTable.TABLE,
			ReferenceExampleTable.TABLE.mainExampleId.eq(
				MainExampleTable.TABLE.mainExampleId)
		).where(
			ReferenceExampleTable.TABLE.mainExampleId.isNull()
		).orderBy(
			MainExampleTable.TABLE.flag.descending(),
			MainExampleTable.TABLE.name.ascending()
		);

		Assert.assertEquals(
			StringBundler.concat(
				"select MainExample.mainExampleId from MainExample left join ",
				"ReferenceExample on ReferenceExample.mainExampleId = ",
				"MainExample.mainExampleId where ",
				"ReferenceExample.mainExampleId is NULL order by ",
				"MainExample.flag desc, MainExample.name asc"),
			dslQuery.toString());
	}

	@Test
	public void testOperands() {
		Assert.assertEquals("and", Operand.AND.toString());

		Assert.assertEquals("=", Operand.EQUAL.toString());

		Assert.assertEquals(">", Operand.GREATER_THAN.toString());

		Assert.assertEquals(">=", Operand.GREATER_THAN_OR_EQUAL.toString());

		Assert.assertEquals("in", Operand.IN.toString());

		Assert.assertEquals("is", Operand.IS.toString());

		Assert.assertEquals("is not", Operand.IS_NOT.toString());

		Assert.assertEquals("<", Operand.LESS_THAN.toString());

		Assert.assertEquals("<=", Operand.LESS_THAN_OR_EQUAL.toString());

		Assert.assertEquals("like", Operand.LIKE.toString());

		Assert.assertEquals("not like", Operand.NOT_LIKE.toString());

		Assert.assertEquals("!=", Operand.NOT_EQUAL.toString());

		Assert.assertEquals("not in", Operand.NOT_IN.toString());

		Assert.assertEquals("or", Operand.OR.toString());
	}

	@Test
	public void testOrderBy() {
		JoinStep joinStep = DSLQueryUtil.select(
			MainExampleTable.TABLE.name
		).from(
			MainExampleTable.TABLE
		);

		Assert.assertEquals(
			"select MainExample.name from MainExample", joinStep.toString());

		LimitStep limitStep = joinStep.orderBy();

		Assert.assertEquals(
			"select MainExample.name from MainExample", limitStep.toString());

		limitStep = joinStep.orderBy((OrderByExpression[])null);

		Assert.assertEquals(
			"select MainExample.name from MainExample", limitStep.toString());

		try {
			new OrderBy(joinStep, new OrderByExpression[0]);
		}
		catch (Exception exception) {
			Assert.assertSame(
				IllegalArgumentException.class, exception.getClass());
		}

		OrderByExpression orderByExpression =
			MainExampleTable.TABLE.name.ascending();

		OrderByExpression[] orderByExpressions = {orderByExpression};

		OrderBy orderBy = new OrderBy(joinStep, orderByExpressions);

		Assert.assertSame(orderByExpressions, orderBy.getOrderByExpressions());

		Assert.assertEquals(
			MainExampleTable.TABLE.name, orderByExpression.getExpression());
		Assert.assertTrue(
			orderByExpression.toString(), orderByExpression.isAscending());

		Assert.assertEquals(
			"select MainExample.name from MainExample order by " +
				"MainExample.name asc",
			orderBy.toString());
	}

	@Test
	public void testOrderByOrderByInfo() {
		OrderByStep orderByStep = DSLQueryUtil.select(
			MainExampleTable.TABLE.name
		).from(
			MainExampleTable.TABLE
		);

		DSLQuery dslQuery = orderByStep.orderBy(MainExampleTable.TABLE, null);

		Assert.assertEquals(
			"select MainExample.name from MainExample", dslQuery.toString());

		OrderByInfo orderByInfo = new OrderByInfo() {

			@Override
			public String[] getOrderByFields() {
				return new String[] {
					MainExampleTable.TABLE.flag.getColumnName()
				};
			}

			@Override
			public boolean isAscending(String field) {
				return true;
			}

		};

		dslQuery = orderByStep.orderBy(MainExampleTable.TABLE, orderByInfo);

		Assert.assertEquals(
			"select MainExample.name from MainExample order by " +
				"MainExample.flag asc",
			dslQuery.toString());

		try {
			orderByStep.orderBy(ReferenceExampleTable.TABLE, orderByInfo);
		}
		catch (IllegalArgumentException illegalArgumentException) {
			Assert.assertEquals(
				"No column \"flag\" for table ReferenceExample",
				illegalArgumentException.getMessage());
		}
	}

	@Test
	public void testPredicateParentheses() {
		Predicate leftPredicate = MainExampleTable.TABLE.mainExampleId.gte(1L);

		Predicate rightPredicate = MainExampleTable.TABLE.name.eq(
			"test"
		).or(
			MainExampleTable.TABLE.name.eq((String)null)
		).withParentheses();

		PredicateImpl predicateImpl = new PredicateImpl(
			leftPredicate, Operand.AND, rightPredicate);

		Assert.assertSame(leftPredicate, predicateImpl.getLeftExpression());
		Assert.assertSame(Operand.AND, predicateImpl.getOperand());
		Assert.assertSame(rightPredicate, predicateImpl.getRightExpression());

		Assert.assertFalse(predicateImpl.isWrapParentheses());

		Assert.assertSame(rightPredicate, rightPredicate.withParentheses());

		DSLQuery dslQuery = DSLQueryUtil.count(
		).from(
			MainExampleTable.TABLE
		).where(
			predicateImpl
		);

		DefaultASTNodeListener defaultASTNodeListener =
			new DefaultASTNodeListener();

		Assert.assertEquals(
			StringBundler.concat(
				"select count(*) COUNT_VALUE from MainExample where ",
				"MainExample.mainExampleId >= ? and (MainExample.name = ? or ",
				"MainExample.name = ?)"),
			dslQuery.toSQL(defaultASTNodeListener));

		Assert.assertEquals(
			Arrays.asList(1L, "test", null),
			defaultASTNodeListener.getScalarValues());
	}

	@Test
	public void testScalarList() {
		Long[] longs = {0L, 1L, 2L};

		ScalarList<Long> scalarList = new ScalarList<>(longs);

		Assert.assertSame(longs, scalarList.getValues());

		try {
			new ScalarList<>(new String[0]);
		}
		catch (Exception exception) {
			Assert.assertSame(
				IllegalArgumentException.class, exception.getClass());
		}
	}

	@Test
	public void testSelect() {
		Expression<?>[] expressions = {
			MainExampleTable.TABLE.mainExampleId, MainExampleTable.TABLE.flag
		};

		Select select = new Select(true, expressions);

		Assert.assertTrue(select.isDistinct());
		Assert.assertSame(expressions, select.getExpressions());
	}

	@Test
	public void testSelect1() {
		DSLQuery dslQuery = DSLQueryUtil.select(new Scalar<>(1));

		Assert.assertEquals("select ?", dslQuery.toString());
	}

	@Test
	public void testSelectDistinctTable() {
		FromStep fromStep = DSLQueryUtil.selectDistinct(MainExampleTable.TABLE);

		Assert.assertEquals(
			"select distinct MainExample.description, MainExample.flag, " +
				"MainExample.mainExampleId, MainExample.name",
			fromStep.toString());
	}

	@Test
	public void testSelectDistinctWhereInWithAlias() {
		MainExampleTable mainTable = MainExampleTable.TABLE.as("mainTable");

		JoinStep joinStep = DSLQueryUtil.selectDistinct(
			mainTable.name
		).from(
			mainTable
		);

		DSLQuery dslQuery = joinStep.where(
			mainTable.flag.in(new Integer[] {1, 2}));

		Assert.assertEquals(
			"select distinct mainTable.name from MainExample mainTable where " +
				"mainTable.flag in (?, ?)",
			dslQuery.toString());

		dslQuery = joinStep.where(
			mainTable.mainExampleId.in(new Long[] {1L, 2L, null})
		).orderBy(
			mainTable.name.ascending()
		);

		DefaultASTNodeListener defaultASTNodeListener =
			new DefaultASTNodeListener();

		Assert.assertEquals(
			StringBundler.concat(
				"select distinct mainTable.name from MainExample mainTable ",
				"where mainTable.mainExampleId in (?, ?, ?) order by ",
				"mainTable.name asc"),
			dslQuery.toSQL(defaultASTNodeListener));

		Assert.assertEquals(
			Arrays.asList(1L, 2L, null),
			defaultASTNodeListener.getScalarValues());

		String[] strings = {"1", "2", "3"};

		dslQuery = joinStep.where(
			DSLFunctionUtil.castText(
				mainTable.mainExampleId
			).in(
				strings
			)
		).orderBy(
			mainTable.name.ascending()
		);

		defaultASTNodeListener = new DefaultASTNodeListener();

		Assert.assertEquals(
			StringBundler.concat(
				"select distinct mainTable.name from MainExample mainTable ",
				"where CAST_TEXT(mainTable.mainExampleId) in (?, ?, ?) order ",
				"by mainTable.name asc"),
			dslQuery.toSQL(defaultASTNodeListener));

		Assert.assertEquals(
			Arrays.asList(strings), defaultASTNodeListener.getScalarValues());
	}

	@Test
	public void testSelfJoin() {
		MainExampleTable tempMainExample = MainExampleTable.TABLE.as(
			"tempMainExample");

		DSLQuery dslQuery = DSLQueryUtil.select(
			MainExampleTable.TABLE.mainExampleId, MainExampleTable.TABLE.name
		).from(
			MainExampleTable.TABLE
		).leftJoinOn(
			tempMainExample,
			MainExampleTable.TABLE.mainExampleId.lt(
				tempMainExample.mainExampleId)
		).where(
			tempMainExample.mainExampleId.isNull()
		);

		Assert.assertEquals(
			StringBundler.concat(
				"select MainExample.mainExampleId, MainExample.name from ",
				"MainExample left join MainExample tempMainExample on ",
				"MainExample.mainExampleId < tempMainExample.mainExampleId ",
				"where tempMainExample.mainExampleId is NULL"),
			dslQuery.toString());
	}

	@Test
	public void testSimpleCount() {
		DSLQuery dslQuery = DSLQueryUtil.count(
		).from(
			MainExampleTable.TABLE
		);

		Assert.assertEquals(
			"select count(*) COUNT_VALUE from MainExample",
			dslQuery.toString());
	}

	@Test
	public void testSimpleSelect() {
		FromStep fromStep = DSLQueryUtil.select();

		Assert.assertEquals("select *", fromStep.toString());

		JoinStep joinStep = fromStep.from(MainExampleTable.TABLE);

		Assert.assertEquals("select * from MainExample", joinStep.toString());

		GroupByStep groupByStep = joinStep.where(null);

		Assert.assertEquals(
			"select * from MainExample", groupByStep.toString());

		Predicate predicate = MainExampleTable.TABLE.name.eq("test");

		Assert.assertEquals("MainExample.name = ?", predicate.toString());

		predicate = predicate.and(MainExampleTable.TABLE.mainExampleId.gt(0L));

		Assert.assertEquals(
			"MainExample.name = ? and MainExample.mainExampleId > ?",
			predicate.toString());

		predicate = predicate.and(null);

		Assert.assertEquals(
			"MainExample.name = ? and MainExample.mainExampleId > ?",
			predicate.toString());

		predicate = predicate.or(null);

		Assert.assertEquals(
			"MainExample.name = ? and MainExample.mainExampleId > ?",
			predicate.toString());

		Where where = new Where(joinStep, predicate);

		Assert.assertSame(predicate, where.getPredicate());

		Assert.assertEquals(
			"select * from MainExample where MainExample.name = ? and " +
				"MainExample.mainExampleId > ?",
			where.toString());

		OrderByExpression orderByExpression =
			MainExampleTable.TABLE.mainExampleId.descending();

		Assert.assertEquals(
			"MainExample.mainExampleId desc", orderByExpression.toString());

		OrderBy orderBy = new OrderBy(
			where, new OrderByExpression[] {orderByExpression});

		DefaultASTNodeListener defaultASTNodeListener =
			new DefaultASTNodeListener();

		Assert.assertEquals(
			StringBundler.concat(
				"select * from MainExample where MainExample.name = ? and ",
				"MainExample.mainExampleId > ? order by ",
				"MainExample.mainExampleId desc"),
			orderBy.toSQL(defaultASTNodeListener));

		String[] tableNames = defaultASTNodeListener.getTableNames();

		Assert.assertArrayEquals(
			new String[] {MainExampleTable.TABLE.getTableName()}, tableNames);

		Assert.assertEquals(
			Arrays.asList("test", 0L),
			defaultASTNodeListener.getScalarValues());

		Assert.assertEquals(-1, defaultASTNodeListener.getStart());
		Assert.assertEquals(-1, defaultASTNodeListener.getEnd());

		Limit limit = new Limit(orderBy, -1, -1);

		defaultASTNodeListener = new DefaultASTNodeListener();

		Assert.assertEquals(
			StringBundler.concat(
				"select * from MainExample where MainExample.name = ? and ",
				"MainExample.mainExampleId > ? order by ",
				"MainExample.mainExampleId desc "),
			limit.toSQL(defaultASTNodeListener));

		Assert.assertEquals(-1, defaultASTNodeListener.getStart());
		Assert.assertEquals(-1, defaultASTNodeListener.getEnd());

		limit = new Limit(orderBy, 10, 30);

		defaultASTNodeListener = new DefaultASTNodeListener();

		Assert.assertEquals(
			StringBundler.concat(
				"select * from MainExample where MainExample.name = ? and ",
				"MainExample.mainExampleId > ? order by ",
				"MainExample.mainExampleId desc "),
			limit.toSQL(defaultASTNodeListener));

		Assert.assertEquals(10, defaultASTNodeListener.getStart());
		Assert.assertEquals(30, defaultASTNodeListener.getEnd());
	}

	@Test
	public void testSubqueryCount() {
		DSLQuery dslQuery = DSLQueryUtil.count(
		).from(
			MainExampleTable.TABLE
		).where(
			MainExampleTable.TABLE.mainExampleId.in(
				DSLQueryUtil.select(
					ReferenceExampleTable.TABLE.mainExampleId
				).from(
					ReferenceExampleTable.TABLE
				).where(
					ReferenceExampleTable.TABLE.name.eq("test")
				))
		);

		Assert.assertEquals(
			StringBundler.concat(
				"select count(*) COUNT_VALUE from MainExample where ",
				"MainExample.mainExampleId in (select ",
				"ReferenceExample.mainExampleId from ReferenceExample where ",
				"ReferenceExample.name = ?)"),
			dslQuery.toString());
	}

	@Test
	public void testTable() {
		MainExampleTable alias = MainExampleTable.TABLE.as("alias");

		Assert.assertNotSame(MainExampleTable.TABLE, alias);

		Assert.assertEquals(MainExampleTable.TABLE, MainExampleTable.TABLE);
		Assert.assertEquals(MainExampleTable.TABLE, alias);
		Assert.assertNotEquals(
			MainExampleTable.TABLE, MainExampleTable.TABLE.name);
		Assert.assertNotEquals(
			MainExampleTable.TABLE, ReferenceExampleTable.TABLE);

		Assert.assertEquals(
			MainExampleTable.TABLE.hashCode(), alias.hashCode());

		Assert.assertSame(
			MainExampleTable.TABLE.name,
			MainExampleTable.TABLE.getColumn(
				MainExampleTable.TABLE.name.getColumnName(),
				MainExampleTable.TABLE.name.getColumnType()));
		Assert.assertNull(
			MainExampleTable.TABLE.getColumn(
				MainExampleTable.TABLE.name.getColumnName(), Long.class));

		Alias<String> nameAlias = alias.name.as("nameAlias");

		Column<MainExampleTable, String> column =
			(Column<MainExampleTable, String>)nameAlias.getExpression();

		Assert.assertNotSame(MainExampleTable.TABLE.name, column);

		alias = column.getTable();

		Assert.assertEquals("alias", alias.getName());

		Assert.assertEquals(
			MainExampleTable.TABLE.name,
			alias.getColumn(nameAlias.getName(), column.getColumnType()));

		Assert.assertNull(
			MainExampleTable.TABLE.getColumn(nameAlias.getName()));
		Assert.assertNull(
			MainExampleTable.TABLE.getColumn(
				nameAlias.getName(), column.getColumnType()));

		Collection<Column<MainExampleTable, ?>> columns =
			MainExampleTable.TABLE.getColumns();

		Assert.assertEquals(columns.toString(), 4, columns.size());

		Assert.assertTrue(
			columns.contains(MainExampleTable.TABLE.mainExampleId));
		Assert.assertTrue(columns.contains(MainExampleTable.TABLE.name));
		Assert.assertTrue(columns.contains(MainExampleTable.TABLE.description));
		Assert.assertTrue(columns.contains(MainExampleTable.TABLE.flag));

		try {
			columns.remove(MainExampleTable.TABLE.mainExampleId);
		}
		catch (Exception exception) {
			Assert.assertSame(
				UnsupportedOperationException.class, exception.getClass());
		}
	}

	@Test
	public void testUnionSelect() {
		DSLQuery dslQuery1 = DSLQueryUtil.select(
			MainExampleTable.TABLE.name.as("name")
		).from(
			MainExampleTable.TABLE
		);

		DSLQuery dslQuery2 = DSLQueryUtil.select(
			ReferenceExampleTable.TABLE.name.as("name")
		).from(
			ReferenceExampleTable.TABLE
		);

		SetOperation union = new SetOperation(
			dslQuery1, SetOperationType.UNION, dslQuery2);

		Assert.assertSame(dslQuery1, union.getLeftDSLQuery());

		Assert.assertSame(SetOperationType.UNION, union.getSetOperationType());

		Assert.assertSame(dslQuery2, union.getRightDSLQuery());

		Assert.assertEquals(
			"select MainExample.name name from MainExample union select " +
				"ReferenceExample.name name from ReferenceExample",
			union.toString());

		union = new SetOperation(
			dslQuery1, SetOperationType.UNION_ALL,
			DSLQueryUtil.select(
				ReferenceExampleTable.TABLE.name.as("name")
			).from(
				ReferenceExampleTable.TABLE
			));

		Assert.assertSame(
			SetOperationType.UNION_ALL, union.getSetOperationType());

		String sql =
			"select MainExample.name name from MainExample union all select " +
				"ReferenceExample.name name from ReferenceExample";

		Assert.assertEquals(sql, union.toString());

		union = new SetOperation(union, SetOperationType.UNION, union);

		Assert.assertEquals(
			StringBundler.concat(sql, " union ", sql), union.toString());
	}

	@Test
	public void testWhenThen() {
		Predicate predicate = MainExampleTable.TABLE.mainExampleId.eq(2L);

		Scalar<String> scalar = new Scalar<>("two");

		WhenThen<String> whenThen = new WhenThen<>(
			DSLFunctionUtil.casesWhenThen(
				MainExampleTable.TABLE.mainExampleId.eq(1L), "one"),
			predicate, scalar);

		Assert.assertSame(predicate, whenThen.getPredicate());

		Assert.assertSame(scalar, whenThen.getThenExpression());
	}

	private static class MainExampleTable extends Table<MainExampleTable> {

		public static final MainExampleTable TABLE = new MainExampleTable();

		public final Column<MainExampleTable, Clob> description = createColumn(
			"description", Clob.class, Types.CLOB);
		public final Column<MainExampleTable, Integer> flag = createColumn(
			"flag", Integer.class, Types.INTEGER);
		public final Column<MainExampleTable, Long> mainExampleId =
			createColumn("mainExampleId", Long.class, Types.BIGINT);
		public final Column<MainExampleTable, String> name = createColumn(
			"name", String.class, Types.VARCHAR);

		private MainExampleTable() {
			super("MainExample", MainExampleTable::new);
		}

	}

	private static class ReferenceExampleTable
		extends Table<ReferenceExampleTable> {

		public static final ReferenceExampleTable TABLE =
			new ReferenceExampleTable();

		@Override
		public ReferenceExampleTable clone() throws CloneNotSupportedException {
			throw new CloneNotSupportedException();
		}

		public final Column<ReferenceExampleTable, Long> mainExampleId =
			createColumn("mainExampleId", Long.class, Types.BIGINT);
		public final Column<ReferenceExampleTable, String> name = createColumn(
			"name", String.class, Types.VARCHAR);
		public final Column<ReferenceExampleTable, Long> referenceExampleId =
			createColumn("referenceExampleId", Long.class, Types.BIGINT);

		private ReferenceExampleTable() {
			super("ReferenceExample", ReferenceExampleTable::new);
		}

	}

}