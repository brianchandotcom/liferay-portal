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

package com.liferay.change.tracking.internal.reference;

import com.liferay.change.tracking.internal.reference.builder.BaseTableReferenceInfoBuilder;
import com.liferay.change.tracking.internal.reference.builder.ChildTableReferenceInfoBuilderImpl;
import com.liferay.change.tracking.internal.reference.builder.ParentTableReferenceInfoBuilderImpl;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.base.BaseTable;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.sql.Clob;
import java.sql.Types;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class TableReferenceInfoFactoryTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor() {

			@Override
			public void appendAssertClasses(List<Class<?>> assertClasses) {
				assertClasses.add(BaseTableReferenceInfoBuilder.class);
				assertClasses.add(ChildTableReferenceInfoBuilderImpl.class);
				assertClasses.add(ParentTableReferenceInfoBuilderImpl.class);
				assertClasses.add(TableJoinHolderFactory.class);

				Collections.addAll(
					assertClasses,
					TableJoinHolderFactory.class.getDeclaredClasses());

				assertClasses.add(TableReferenceInfo.class);
			}

		};

	@BeforeClass
	public static void setUpClass() {
		_tableReferenceAppenders = ReflectionTestUtil.getAndSetFieldValue(
			TableReferenceAppenderRegistry.class, "_tableReferenceAppenders",
			Collections.emptyList());
	}

	@AfterClass
	public static void tearDownClass() {
		ReflectionTestUtil.getAndSetFieldValue(
			TableReferenceAppenderRegistry.class, "_tableReferenceAppenders",
			_tableReferenceAppenders);
	}

	@Test
	public void testChildInnerJoin() {
		Function<FromStep, JoinStep> childJoinFunction =
			fromStep -> fromStep.from(
				ReferenceExampleTable.INSTANCE
			).innerJoinON(
				MainExampleTable.INSTANCE,
				MainExampleTable.INSTANCE.mainExampleId.eq(
					ReferenceExampleTable.INSTANCE.mainExampleId)
			);

		TableReferenceDefinition<MainExampleTable> tableReferenceDefinition =
			new TestTableReferenceDefinition<MainExampleTable>(
				MainExampleTable.INSTANCE) {

				@Override
				public void defineChildTableReferences(
					ChildTableReferenceInfoBuilder<MainExampleTable>
						childTableReferenceInfoBuilder) {

					childTableReferenceInfoBuilder.referenceInnerJoin(
						childJoinFunction);
				}

				@Override
				public void defineParentTableReferences(
					ParentTableReferenceInfoBuilder<MainExampleTable>
						parentTableReferenceInfoBuilder) {
				}

			};

		TableReferenceInfo<MainExampleTable> tableReferenceInfo =
			TableReferenceInfoFactory.create(
				tableReferenceDefinition, MainExampleTable.CLASS_NAME_ID,
				MainExampleTable.INSTANCE.mainExampleId);

		Assert.assertNotNull(tableReferenceInfo);

		Assert.assertSame(
			tableReferenceDefinition,
			tableReferenceInfo.getTableReferenceDefinition());

		Map<Table<?>, List<TableJoinHolder>> childTableJoinHoldersMap =
			tableReferenceInfo.getChildTableJoinHoldersMap();

		Assert.assertEquals(
			childTableJoinHoldersMap.toString(), 1,
			childTableJoinHoldersMap.size());

		List<TableJoinHolder> childJoinHolders = childTableJoinHoldersMap.get(
			ReferenceExampleTable.INSTANCE);

		Assert.assertEquals(
			childJoinHolders.toString(), 1, childJoinHolders.size());

		TableJoinHolder childJoinHolder = childJoinHolders.get(0);

		Assert.assertSame(childJoinFunction, childJoinHolder.getJoinFunction());

		Map<Table<?>, List<TableJoinHolder>> parentTableJoinHoldersMap =
			tableReferenceInfo.getParentTableJoinHoldersMap();

		Assert.assertTrue(
			parentTableJoinHoldersMap.toString(),
			parentTableJoinHoldersMap.isEmpty());

		Assert.assertEquals(
			MainExampleTable.CLASS_NAME_ID,
			tableReferenceInfo.getClassNameId());
	}

	@Test
	public void testConstructors() {
		new TableReferenceInfoFactory();

		new TableJoinHolderFactory();
	}

	@Test
	public void testParentInnerJoin() {
		Function<FromStep, JoinStep> parentJoinFunction =
			fromStep -> fromStep.from(
				MainExampleTable.INSTANCE
			).innerJoinON(
				ReferenceExampleTable.INSTANCE,
				MainExampleTable.INSTANCE.mainExampleId.eq(
					ReferenceExampleTable.INSTANCE.mainExampleId)
			);

		ReferenceExampleTable aliasReferenceExampleTable =
			ReferenceExampleTable.INSTANCE.as("aliasReferenceExampleTable");

		Function<FromStep, JoinStep> selfJoinFunction1 =
			fromStep -> fromStep.from(
				aliasReferenceExampleTable
			).innerJoinON(
				ReferenceExampleTable.INSTANCE,
				ReferenceExampleTable.INSTANCE.referenceExampleId.eq(
					aliasReferenceExampleTable.parentReferenceExampleId)
			);

		Function<FromStep, JoinStep> selfJoinFunction2 =
			fromStep -> fromStep.from(
				ReferenceExampleTable.INSTANCE
			).innerJoinON(
				aliasReferenceExampleTable,
				aliasReferenceExampleTable.referenceExampleId.eq(
					ReferenceExampleTable.INSTANCE.parentReferenceExampleId)
			);

		Function<FromStep, JoinStep> selfJoinFunction3 =
			fromStep -> fromStep.from(
				aliasReferenceExampleTable
			).innerJoinON(
				ReferenceExampleTable.INSTANCE,
				ReferenceExampleTable.INSTANCE.parentReferenceExampleId.eq(
					aliasReferenceExampleTable.referenceExampleId)
			);

		Function<FromStep, JoinStep> selfJoinFunction4 =
			fromStep -> fromStep.from(
				ReferenceExampleTable.INSTANCE
			).innerJoinON(
				aliasReferenceExampleTable,
				aliasReferenceExampleTable.parentReferenceExampleId.eq(
					ReferenceExampleTable.INSTANCE.referenceExampleId)
			);

		TableReferenceDefinition<ReferenceExampleTable>
			tableReferenceDefinition =
				new TestTableReferenceDefinition<ReferenceExampleTable>(
					ReferenceExampleTable.INSTANCE) {

					@Override
					public void defineChildTableReferences(
						ChildTableReferenceInfoBuilder<ReferenceExampleTable>
							childTableReferenceInfoBuilder) {

						childTableReferenceInfoBuilder.referenceInnerJoin(
							selfJoinFunction1
						).referenceInnerJoin(
							selfJoinFunction2
						);
					}

					@Override
					public void defineParentTableReferences(
						ParentTableReferenceInfoBuilder<ReferenceExampleTable>
							parentTableReferenceInfoBuilder) {

						parentTableReferenceInfoBuilder.referenceInnerJoin(
							parentJoinFunction
						).referenceInnerJoin(
							selfJoinFunction3
						).referenceInnerJoin(
							selfJoinFunction4
						);
					}

				};

		TableReferenceInfo<ReferenceExampleTable> tableReferenceInfo =
			TableReferenceInfoFactory.create(
				tableReferenceDefinition, ReferenceExampleTable.CLASS_NAME_ID,
				ReferenceExampleTable.INSTANCE.referenceExampleId);

		Assert.assertNotNull(tableReferenceInfo);

		Assert.assertSame(
			tableReferenceDefinition,
			tableReferenceInfo.getTableReferenceDefinition());

		Map<Table<?>, List<TableJoinHolder>> childTableJoinHoldersMap =
			tableReferenceInfo.getChildTableJoinHoldersMap();

		Assert.assertEquals(
			childTableJoinHoldersMap.toString(), 1,
			childTableJoinHoldersMap.size());

		List<TableJoinHolder> childTableJoinHolders =
			childTableJoinHoldersMap.get(ReferenceExampleTable.INSTANCE);

		Assert.assertEquals(
			childTableJoinHolders.toString(), 2, childTableJoinHolders.size());

		TableJoinHolder childJoinHolder = childTableJoinHolders.get(0);

		Assert.assertSame(selfJoinFunction1, childJoinHolder.getJoinFunction());

		Assert.assertSame(
			aliasReferenceExampleTable.referenceExampleId,
			childJoinHolder.getParentPKColumn());

		Assert.assertSame(
			ReferenceExampleTable.INSTANCE.referenceExampleId,
			childJoinHolder.getChildPKColumn());

		childJoinHolder = childTableJoinHolders.get(1);

		Assert.assertSame(selfJoinFunction2, childJoinHolder.getJoinFunction());

		Assert.assertSame(
			ReferenceExampleTable.INSTANCE.referenceExampleId,
			childJoinHolder.getParentPKColumn());

		Assert.assertSame(
			aliasReferenceExampleTable.referenceExampleId,
			childJoinHolder.getChildPKColumn());

		Map<Table<?>, List<TableJoinHolder>> parentTableJoinHoldersMap =
			tableReferenceInfo.getParentTableJoinHoldersMap();

		Assert.assertEquals(
			parentTableJoinHoldersMap.toString(), 2,
			parentTableJoinHoldersMap.size());

		List<TableJoinHolder> parentTableJoinHolders =
			parentTableJoinHoldersMap.get(MainExampleTable.INSTANCE);

		Assert.assertEquals(
			parentTableJoinHolders.toString(), 1,
			parentTableJoinHolders.size());

		TableJoinHolder parentJoinHolder = parentTableJoinHolders.get(0);

		Assert.assertSame(
			parentJoinFunction, parentJoinHolder.getJoinFunction());

		Assert.assertSame(
			MainExampleTable.INSTANCE.mainExampleId,
			parentJoinHolder.getParentPKColumn());

		Assert.assertSame(
			ReferenceExampleTable.INSTANCE.referenceExampleId,
			parentJoinHolder.getChildPKColumn());

		parentTableJoinHolders = parentTableJoinHoldersMap.get(
			ReferenceExampleTable.INSTANCE);

		Assert.assertEquals(
			parentTableJoinHolders.toString(), 2,
			parentTableJoinHolders.size());

		childJoinHolder = parentTableJoinHolders.get(0);

		Assert.assertSame(selfJoinFunction3, childJoinHolder.getJoinFunction());

		Assert.assertSame(
			aliasReferenceExampleTable.referenceExampleId,
			childJoinHolder.getParentPKColumn());

		Assert.assertSame(
			ReferenceExampleTable.INSTANCE.referenceExampleId,
			childJoinHolder.getChildPKColumn());

		parentJoinHolder = parentTableJoinHolders.get(1);

		Assert.assertSame(
			selfJoinFunction4, parentJoinHolder.getJoinFunction());

		Assert.assertSame(
			ReferenceExampleTable.INSTANCE.referenceExampleId,
			parentJoinHolder.getParentPKColumn());

		Assert.assertSame(
			aliasReferenceExampleTable.referenceExampleId,
			parentJoinHolder.getChildPKColumn());

		Assert.assertEquals(
			ReferenceExampleTable.CLASS_NAME_ID,
			tableReferenceInfo.getClassNameId());
	}

	@Test
	public void testTableReferenceDefinitionInnerJoinValidation() {
		TableReferenceDefinition<MainExampleTable> tableReferenceDefinition =
			new TestTableReferenceDefinition<MainExampleTable>(
				MainExampleTable.INSTANCE) {

				@Override
				public void defineChildTableReferences(
					ChildTableReferenceInfoBuilder<MainExampleTable>
						childTableReferenceInfoBuilder) {

					try {
						childTableReferenceInfoBuilder.referenceInnerJoin(
							fromStep -> DSLQueryFactoryUtil.select(
							).from(
								MainExampleTable.INSTANCE
							));

						Assert.fail();
					}
					catch (IllegalArgumentException illegalArgumentException) {
						Assert.assertEquals(
							"Missing join in \"select * from MainExample\"",
							illegalArgumentException.getMessage());
					}

					try {
						childTableReferenceInfoBuilder.referenceInnerJoin(
							fromStep -> DSLQueryFactoryUtil.select(
							).from(
								MainExampleTable.INSTANCE
							).innerJoinON(
								MainExampleTable.INSTANCE,
								MainExampleTable.INSTANCE.mainExampleId.eq(
									MainExampleTable.INSTANCE.mainExampleId)
							));

						Assert.fail();
					}
					catch (IllegalArgumentException illegalArgumentException) {
						Assert.assertEquals(
							StringBundler.concat(
								"Join function must use provided from step ",
								"for join step \"select * from MainExample ",
								"inner join MainExample on ",
								"MainExample.mainExampleId = ",
								"MainExample.mainExampleId\""),
							illegalArgumentException.getMessage());
					}

					try {
						childTableReferenceInfoBuilder.referenceInnerJoin(
							fromStep -> fromStep.from(
								ReferenceExampleTable.INSTANCE
							).innerJoinON(
								ReferenceExampleTable.INSTANCE,
								ReferenceExampleTable.INSTANCE.mainExampleId.eq(
									ReferenceExampleTable.INSTANCE.
										mainExampleId)
							));

						Assert.fail();
					}
					catch (IllegalArgumentException illegalArgumentException) {
						Assert.assertEquals(
							StringBundler.concat(
								"Required table \"MainExample\" is unused in ",
								"join step \"... from ReferenceExample inner ",
								"join ReferenceExample on ",
								"ReferenceExample.mainExampleId = ",
								"ReferenceExample.mainExampleId\""),
							illegalArgumentException.getMessage());
					}

					try {
						childTableReferenceInfoBuilder.referenceInnerJoin(
							fromStep -> fromStep.from(
								MainExampleTable.INSTANCE
							).innerJoinON(
								MainExampleTable.INSTANCE,
								MainExampleTable.INSTANCE.mainExampleId.eq(
									MainExampleTable.INSTANCE.mainExampleId)
							));

						Assert.fail();
					}
					catch (IllegalArgumentException illegalArgumentException) {
						Assert.assertEquals(
							StringBundler.concat(
								"Invalid join for join step \"... from ",
								"MainExample inner join MainExample on ",
								"MainExample.mainExampleId = ",
								"MainExample.mainExampleId\", ensure table ",
								"alias is used for self joins"),
							illegalArgumentException.getMessage());
					}

					try {
						childTableReferenceInfoBuilder.referenceInnerJoin(
							fromStep -> fromStep.from(
								MainExampleTable.INSTANCE
							).leftJoinOn(
								ReferenceExampleTable.INSTANCE,
								ReferenceExampleTable.INSTANCE.mainExampleId.eq(
									MainExampleTable.INSTANCE.mainExampleId)
							));

						Assert.fail();
					}
					catch (IllegalArgumentException illegalArgumentException) {
						Assert.assertEquals(
							StringBundler.concat(
								"Invalid join type \"left\" for join step ",
								"\"... from MainExample left join ",
								"ReferenceExample on ",
								"ReferenceExample.mainExampleId = ",
								"MainExample.mainExampleId\""),
							illegalArgumentException.getMessage());
					}

					try {
						childTableReferenceInfoBuilder.referenceInnerJoin(
							fromStep -> fromStep.from(
								MainExampleTable.INSTANCE
							).innerJoinON(
								ReferenceExampleTable.INSTANCE,
								ReferenceExampleTable.INSTANCE.mainExampleId.
									neq(
										MainExampleTable.INSTANCE.mainExampleId
									).and(
										MainExampleTable.INSTANCE.mainExampleId.
											gte(
												ReferenceExampleTable.INSTANCE.
													mainExampleId)
									)
							));

						Assert.fail();
					}
					catch (IllegalArgumentException illegalArgumentException) {
						Assert.assertEquals(
							StringBundler.concat(
								"Invalid predicate operand \"!=\" for join ",
								"step \"... from MainExample inner join ",
								"ReferenceExample on ",
								"ReferenceExample.mainExampleId != ",
								"MainExample.mainExampleId and ",
								"MainExample.mainExampleId >= ",
								"ReferenceExample.mainExampleId\""),
							illegalArgumentException.getMessage());
					}

					try {
						childTableReferenceInfoBuilder.referenceInnerJoin(
							fromStep -> fromStep.from(
								MainExampleTable.INSTANCE
							).innerJoinON(
								MainExampleTable.INSTANCE.as(
									"aliasMainExample"),
								ReferenceExampleTable.INSTANCE.mainExampleId.eq(
									MainExampleTable.INSTANCE.mainExampleId)
							));

						Assert.fail();
					}
					catch (IllegalArgumentException illegalArgumentException) {
						Assert.assertEquals(
							StringBundler.concat(
								"Predicate column tables [MainExample, ",
								"ReferenceExample] do not match join tables ",
								"[MainExample, MainExample aliasMainExample] ",
								"for join step \"... from MainExample inner ",
								"join MainExample aliasMainExample on ",
								"ReferenceExample.mainExampleId = ",
								"MainExample.mainExampleId\""),
							illegalArgumentException.getMessage());
					}

					try {
						childTableReferenceInfoBuilder.referenceInnerJoin(
							fromStep -> fromStep.from(
								InvalidTable.INSTANCE
							).innerJoinON(
								MainExampleTable.INSTANCE,
								MainExampleTable.INSTANCE.mainExampleId.eq(
									InvalidTable.INSTANCE.mainExampleId)
							));
					}
					catch (IllegalArgumentException illegalArgumentException) {
						Assert.assertEquals(
							StringBundler.concat(
								"No long type primary key column found for ",
								"table \"InvalidTable\" for join step \"... ",
								"from InvalidTable inner join MainExample on ",
								"MainExample.mainExampleId = ",
								"InvalidTable.mainExampleId\""),
							illegalArgumentException.getMessage());
					}

					try {
						childTableReferenceInfoBuilder.referenceInnerJoin(
							fromStep -> fromStep.from(
								MainExampleTable.INSTANCE
							).innerJoinON(
								ReferenceExampleTable.INSTANCE,
								ReferenceExampleTable.INSTANCE.mainExampleId.eq(
									MainExampleTable.INSTANCE.mainExampleId)
							));
					}
					catch (IllegalArgumentException illegalArgumentException) {
						Assert.assertEquals(
							StringBundler.concat(
								"From table should be a different table than ",
								"\"MainExample\" for join step \"... from ",
								"MainExample inner join ReferenceExample on ",
								"ReferenceExample.mainExampleId = ",
								"MainExample.mainExampleId\""),
							illegalArgumentException.getMessage());
					}

					try {
						childTableReferenceInfoBuilder.referenceInnerJoin(
							fromStep -> {
								fromStep.as("test");

								return null;
							});

						Assert.fail();
					}
					catch (Exception exception) {
						Assert.assertSame(
							UnsupportedOperationException.class,
							exception.getClass());
					}

					try {
						childTableReferenceInfoBuilder.referenceInnerJoin(
							fromStep -> {
								fromStep.union(null);

								return null;
							});

						Assert.fail();
					}
					catch (Exception exception) {
						Assert.assertSame(
							UnsupportedOperationException.class,
							exception.getClass());
					}

					try {
						childTableReferenceInfoBuilder.referenceInnerJoin(
							fromStep -> {
								fromStep.unionAll(null);

								return null;
							});

						Assert.fail();
					}
					catch (Exception exception) {
						Assert.assertSame(
							UnsupportedOperationException.class,
							exception.getClass());
					}
				}

				@Override
				public void defineParentTableReferences(
					ParentTableReferenceInfoBuilder<MainExampleTable>
						parentTableReferenceInfoBuilder) {
				}

			};

		TableReferenceInfo<MainExampleTable> tableReferenceInfo =
			TableReferenceInfoFactory.create(
				tableReferenceDefinition, MainExampleTable.CLASS_NAME_ID,
				MainExampleTable.INSTANCE.mainExampleId);

		Assert.assertNotNull(tableReferenceInfo);

		Assert.assertSame(
			tableReferenceDefinition,
			tableReferenceInfo.getTableReferenceDefinition());

		Map<Table<?>, List<TableJoinHolder>> childTableJoinHoldersMap =
			tableReferenceInfo.getChildTableJoinHoldersMap();

		Assert.assertTrue(
			childTableJoinHoldersMap.toString(),
			childTableJoinHoldersMap.isEmpty());

		Map<Table<?>, List<TableJoinHolder>> parentTableJoinHoldersMap =
			tableReferenceInfo.getParentTableJoinHoldersMap();

		Assert.assertTrue(
			parentTableJoinHoldersMap.toString(),
			parentTableJoinHoldersMap.isEmpty());

		Assert.assertEquals(
			MainExampleTable.CLASS_NAME_ID,
			tableReferenceInfo.getClassNameId());
	}

	private static List<TableReferenceAppender> _tableReferenceAppenders;

	private static class InvalidTable extends BaseTable<InvalidTable> {

		public static final InvalidTable INSTANCE = new InvalidTable();

		public final Column<InvalidTable, Long> mainExampleId = createColumn(
			"mainExampleId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

		private InvalidTable() {
			super("InvalidTable", InvalidTable::new);
		}

	}

	private static class MainExampleTable extends BaseTable<MainExampleTable> {

		public static final long CLASS_NAME_ID = 1;

		public static final MainExampleTable INSTANCE = new MainExampleTable();

		public final Column<MainExampleTable, Clob> description = createColumn(
			"description", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
		public final Column<MainExampleTable, Integer> flag = createColumn(
			"flag", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
		public final Column<MainExampleTable, Long> mainExampleId =
			createColumn(
				"mainExampleId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
		public final Column<MainExampleTable, Long> mvccVersion = createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
		public final Column<MainExampleTable, String> name = createColumn(
			"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

		private MainExampleTable() {
			super("MainExample", MainExampleTable::new);
		}

	}

	private static class ReferenceExampleTable
		extends BaseTable<ReferenceExampleTable> {

		public static final long CLASS_NAME_ID = 2;

		public static final ReferenceExampleTable INSTANCE =
			new ReferenceExampleTable();

		public final Column<ReferenceExampleTable, Long> mainExampleId =
			createColumn(
				"mainExampleId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
		public final Column<ReferenceExampleTable, Long> mvccVersion =
			createColumn(
				"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
		public final Column<ReferenceExampleTable, Long>
			parentReferenceExampleId = createColumn(
				"parentReferenceExampleId", Long.class, Types.VARCHAR,
				Column.FLAG_DEFAULT);
		public final Column<ReferenceExampleTable, Long> referenceExampleId =
			createColumn(
				"referenceExampleId", Long.class, Types.BIGINT,
				Column.FLAG_PRIMARY);

		private ReferenceExampleTable() {
			super("ReferenceExample", ReferenceExampleTable::new);
		}

	}

	private abstract class TestTableReferenceDefinition<T extends Table<T>>
		implements TableReferenceDefinition<T> {

		@Override
		public BasePersistence<?> getBasePersistence() {
			return new BasePersistenceImpl<>();
		}

		@Override
		public T getTable() {
			return _table;
		}

		@Override
		public String toString() {
			return TestTableReferenceDefinition.class.getName();
		}

		private TestTableReferenceDefinition(T table) {
			_table = table;
		}

		private final T _table;

	}

}