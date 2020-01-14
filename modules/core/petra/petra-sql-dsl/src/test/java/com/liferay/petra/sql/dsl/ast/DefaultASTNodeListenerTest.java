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
import com.liferay.petra.sql.dsl.FunctionUtil;
import com.liferay.petra.sql.dsl.SelectUtil;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.expressions.Alias;
import com.liferay.petra.sql.dsl.expressions.Predicate;
import com.liferay.petra.sql.dsl.expressions.Scalar;
import com.liferay.petra.sql.dsl.query.From;
import com.liferay.petra.sql.dsl.query.Limit;
import com.liferay.petra.sql.dsl.query.OrderBy;
import com.liferay.petra.sql.dsl.query.OrderByExpression;
import com.liferay.petra.sql.dsl.query.OrderByInfo;
import com.liferay.petra.sql.dsl.query.Query;
import com.liferay.petra.sql.dsl.query.Select;
import com.liferay.petra.sql.dsl.query.Where;
import com.liferay.petra.sql.dsl.set.Union;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.sql.Types;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class DefaultASTNodeListenerTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor() {

			@Override
			public void appendAssertClasses(List<Class<?>> assertClasses) {
				assertClasses.add(FunctionUtil.class);
				assertClasses.add(SelectUtil.class);
			}

		};

	@Test
	public void testCaseSelect() {
		Alias<String> numberAlias = FunctionUtil.casesWhenThen(
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

		Query query = SelectUtil.select(
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
			query.toSQL(defaultASTNodeListener));

		Assert.assertEquals(
			Arrays.asList(
				1L, "one", 2L, "two", 3L, "three", "unknown", "unknown"),
			defaultASTNodeListener.getScalarValues());
	}

	@Test
	public void testConstructors() {
		new FunctionUtil();
		new SelectUtil();
	}

	@Test
	public void testDerivedTable() {
		ReferenceExampleTable referenceExampleTable =
			ReferenceExampleTable.TABLE.as("referenceExample");

		Query query = SelectUtil.select(
		).from(
			MainExampleTable.TABLE
		).leftJoinOn(
			SelectUtil.select(
				ReferenceExampleTable.TABLE.mainExampleId,
				ReferenceExampleTable.TABLE.name
			).from(
				ReferenceExampleTable.TABLE
			).groupBy(
				ReferenceExampleTable.TABLE.mainExampleId,
				ReferenceExampleTable.TABLE.name
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
				"ReferenceExample.name) referenceExample on ",
				"referenceExample.mainExampleId = MainExample.mainExampleId ",
				"order by ReferenceExample.name asc"),
			query.toSQL(defaultASTNodeListener));

		Assert.assertArrayEquals(
			new String[] {"MainExample", "ReferenceExample"},
			defaultASTNodeListener.getTableNames());
	}

	@Test
	public void testFunctions() {
		Assert.assertEquals(
			"MainExample.mainExampleId + ?",
			String.valueOf(
				FunctionUtil.add(MainExampleTable.TABLE.mainExampleId, 2L)));
		Assert.assertEquals(
			"BITAND(MainExample.mainExampleId, ?)",
			String.valueOf(
				FunctionUtil.bitAnd(
					MainExampleTable.TABLE.mainExampleId, 2L)));
		Assert.assertEquals(
			"CAST_CLOB_TEXT(MainExample.mainExampleId)",
			String.valueOf(
				FunctionUtil.castClobText(
					MainExampleTable.TABLE.mainExampleId)));
		Assert.assertEquals(
			"CAST_LONG(MainExample.name)",
			String.valueOf(
				FunctionUtil.castLong(MainExampleTable.TABLE.name)));
		Assert.assertEquals(
			"CAST_TEXT(MainExample.mainExampleId)",
			String.valueOf(
				FunctionUtil.castText(
					MainExampleTable.TABLE.mainExampleId)));
		Assert.assertEquals(
			"CONCAT(MainExample.name, ?, ReferenceExample.name)",
			String.valueOf(
				FunctionUtil.concat(
					MainExampleTable.TABLE.name, new Scalar<>("__delimiter__"),
					ReferenceExampleTable.TABLE.name)));
		Assert.assertEquals(
			"LOWER(MainExample.name)",
			String.valueOf(FunctionUtil.lower(MainExampleTable.TABLE.name)));
		Assert.assertEquals(
			"MainExample.mainExampleId / ?",
			String.valueOf(
				FunctionUtil.divide(
					MainExampleTable.TABLE.mainExampleId, 2L)));
		Assert.assertEquals(
			"MainExample.mainExampleId * ?",
			String.valueOf(
				FunctionUtil.multiply(
					MainExampleTable.TABLE.mainExampleId, 2L)));
		Assert.assertEquals(
			"MainExample.mainExampleId - ?",
			String.valueOf(
				FunctionUtil.subtract(
					MainExampleTable.TABLE.mainExampleId, 2L)));
	}

	@Test
	public void testGroupBy() {
		Query query = SelectUtil.select(
			MainExampleTable.TABLE.mainExampleId, MainExampleTable.TABLE.name
		).from(
			MainExampleTable.TABLE
		).groupBy(
			MainExampleTable.TABLE.name
		).limit(
			0, 20
		);

		Assert.assertEquals(
			"select MainExample.mainExampleId, MainExample.name from " +
				"MainExample group by MainExample.name ",
			query.toString());
	}

	@Test
	public void testJoinCount() {
		Query query = SelectUtil.countDistinct(
			MainExampleTable.TABLE.name
		).from(
			MainExampleTable.TABLE
		).innerJoinON(
			ReferenceExampleTable.TABLE,
			ReferenceExampleTable.TABLE.mainExampleId.eq(
				MainExampleTable.TABLE.mainExampleId)
		).where(
			MainExampleTable.TABLE.name.neq("")
		);

		Assert.assertEquals(
			StringBundler.concat(
				"select count(distinct MainExample.name) from MainExample ",
				"inner join ReferenceExample on ",
				"ReferenceExample.mainExampleId = MainExample.mainExampleId ",
				"where MainExample.name != ?"),
			query.toString());
	}

	@Test
	public void testLeftJoin() {
		Query query = SelectUtil.select(
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
			query.toString());
	}

	@Test
	public void testOrderByComparatorAdaptor() {
		Query query = SelectUtil.select(
			MainExampleTable.TABLE.name
		).from(
			MainExampleTable.TABLE
		).orderBy(
			MainExampleTable.TABLE,
			new OrderByInfo() {

				@Override
				public String[] getOrderByFields() {
					return new String[] {
						MainExampleTable.TABLE.name.getColumnName()
					};
				}

				@Override
				public boolean isAscending(String field) {
					return true;
				}

			}
		);

		Assert.assertEquals(
			"select MainExample.name from MainExample order by " +
				"MainExample.name asc",
			query.toString());
	}

	@Test
	public void testPredicateParentheses() {
		Query query = SelectUtil.count(
		).from(
			MainExampleTable.TABLE
		).where(
			MainExampleTable.TABLE.mainExampleId.gte(
				1L
			).and(
				MainExampleTable.TABLE.name.eq(
					"test"
				).or(
					MainExampleTable.TABLE.name.eq((String)null)
				).withParentheses()
			)
		);

		DefaultASTNodeListener defaultASTNodeListener =
			new DefaultASTNodeListener();

		Assert.assertEquals(
			StringBundler.concat(
				"select count(*) COUNT_VALUE from MainExample where ",
				"MainExample.mainExampleId >= ? and (MainExample.name = ? or ",
				"MainExample.name = ?)"),
			query.toSQL(defaultASTNodeListener));

		Assert.assertEquals(
			Arrays.asList(1L, "test", null),
			defaultASTNodeListener.getScalarValues());
	}

	@Test
	public void testSelect1() {
		Query query = SelectUtil.select(new Scalar<>(1));

		Assert.assertEquals("select ?", query.toString());
	}

	@Test
	public void testSelectDistinctWhereInWithAlias() {
		MainExampleTable mainTable = MainExampleTable.TABLE.as("mainTable");

		From from = SelectUtil.selectDistinct(
			mainTable.name
		).from(
			mainTable
		);

		Query query = from.where(mainTable.flag.in(new Integer[] {1, 2}));

		Assert.assertEquals(
			"select distinct mainTable.name from MainExample mainTable where " +
				"mainTable.flag in (?, ?)",
			query.toString());

		query = from.where(
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
			query.toSQL(defaultASTNodeListener));

		Assert.assertEquals(
			Arrays.asList(1L, 2L, null),
			defaultASTNodeListener.getScalarValues());

		String[] strings = {"1", "2", "3"};

		query = from.where(
			FunctionUtil.castText(
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
			query.toSQL(defaultASTNodeListener));

		Assert.assertEquals(
			Arrays.asList(strings), defaultASTNodeListener.getScalarValues());
	}

	@Test
	public void testSelfJoin() {
		MainExampleTable tempMainExample = MainExampleTable.TABLE.as(
			"tempMainExample");

		Query query = SelectUtil.select(
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
			query.toString());
	}

	@Test
	public void testSimpleCount() {
		Query query = SelectUtil.count(
		).from(
			MainExampleTable.TABLE
		);

		Assert.assertEquals(
			"select count(*) COUNT_VALUE from MainExample", query.toString());
	}

	@Test
	public void testSimpleSelect() {
		Select select = SelectUtil.select();

		Assert.assertEquals("select *", select.toString());

		From from = select.from(MainExampleTable.TABLE);

		Assert.assertEquals("select * from MainExample", from.toString());

		Predicate predicate = MainExampleTable.TABLE.name.eq("test");

		Assert.assertEquals("MainExample.name = ?", predicate.toString());

		predicate = predicate.and(MainExampleTable.TABLE.mainExampleId.gt(0L));

		Assert.assertEquals(
			"MainExample.name = ? and MainExample.mainExampleId > ?",
			predicate.toString());

		Where where = from.where(predicate);

		Assert.assertEquals(
			"select * from MainExample where MainExample.name = ? and " +
				"MainExample.mainExampleId > ?",
			where.toString());

		OrderByExpression orderByExpression =
			MainExampleTable.TABLE.mainExampleId.descending();

		Assert.assertEquals(
			"MainExample.mainExampleId desc", orderByExpression.toString());

		OrderBy orderBy = where.orderBy(orderByExpression);

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

		Limit limit = orderBy.limit(-1, -1);

		defaultASTNodeListener = new DefaultASTNodeListener();

		Assert.assertEquals(
			StringBundler.concat(
				"select * from MainExample where MainExample.name = ? and ",
				"MainExample.mainExampleId > ? order by ",
				"MainExample.mainExampleId desc "),
			limit.toSQL(defaultASTNodeListener));

		Assert.assertEquals(-1, defaultASTNodeListener.getStart());
		Assert.assertEquals(-1, defaultASTNodeListener.getEnd());

		limit = orderBy.limit(10, 30);

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
		Query query = SelectUtil.count(
		).from(
			MainExampleTable.TABLE
		).where(
			MainExampleTable.TABLE.mainExampleId.in(
				SelectUtil.select(
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
			query.toString());
	}

	@Test
	public void testUnionSelect() {
		Query query = SelectUtil.select(
			MainExampleTable.TABLE.name.as("name")
		).from(
			MainExampleTable.TABLE
		);

		Union union = query.union(
			SelectUtil.select(
				ReferenceExampleTable.TABLE.name.as("name")
			).from(
				ReferenceExampleTable.TABLE
			));

		Assert.assertEquals(
			"select MainExample.name name from MainExample union select " +
				"ReferenceExample.name name from ReferenceExample",
			union.toString());

		union = query.unionAll(
			SelectUtil.select(
				ReferenceExampleTable.TABLE.name.as("name")
			).from(
				ReferenceExampleTable.TABLE
			));

		String sql =
			"select MainExample.name name from MainExample union all select " +
				"ReferenceExample.name name from ReferenceExample";

		Assert.assertEquals(sql, union.toString());

		union = union.union(union);

		Assert.assertEquals(
			StringBundler.concat(sql, " union ", sql), union.toString());
	}

	private static class MainExampleTable extends Table<MainExampleTable> {

		public static final MainExampleTable TABLE = new MainExampleTable();

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