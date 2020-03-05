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
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.base.BaseTable;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.sql.Clob;
import java.sql.Types;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class TableReferenceDefinitionHelperImplTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

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

		Consumer<TableReferenceDefinitionHelper<MainExampleTable>> consumer =
			tableReferenceDefinitionHelper -> {
				tableReferenceDefinitionHelper.defineNonreferenceColumn(
					MainExampleTable.INSTANCE.mvccVersion);
				tableReferenceDefinitionHelper.defineNonreferenceColumn(
					MainExampleTable.INSTANCE.description);
				tableReferenceDefinitionHelper.defineNonreferenceColumn(
					MainExampleTable.INSTANCE.flag);
				tableReferenceDefinitionHelper.defineNonreferenceColumn(
					MainExampleTable.INSTANCE.name);

				tableReferenceDefinitionHelper.defineReferenceInnerJoin(
					childJoinFunction);
			};

		TableReferenceDefinitionHelperImpl<MainExampleTable>
			tableReferenceDefinitionHelperImpl = _defineTableReferences(
				MainExampleTable.INSTANCE,
				MainExampleTable.INSTANCE.mainExampleId, consumer);

		TableReferenceInfo<MainExampleTable> tableReferenceInfo =
			tableReferenceDefinitionHelperImpl.getTableReferenceInfo();

		Assert.assertNotNull(tableReferenceInfo);

		Assert.assertSame(
			MainExampleTable.INSTANCE.mainExampleId,
			tableReferenceInfo.getPrimaryKeyColumn());

		Map<Table<?>, List<Function<FromStep, JoinStep>>> childJoinMap =
			tableReferenceInfo.getChildJoinMap();

		Assert.assertEquals(childJoinMap.toString(), 1, childJoinMap.size());

		List<Function<FromStep, JoinStep>> functions = childJoinMap.get(
			ReferenceExampleTable.INSTANCE);

		Assert.assertEquals(functions.toString(), 1, functions.size());
		Assert.assertSame(childJoinFunction, functions.get(0));

		Map<Table<?>, List<Function<FromStep, JoinStep>>> parentJoinMap =
			tableReferenceInfo.getParentJoinMap();

		Assert.assertTrue(parentJoinMap.toString(), parentJoinMap.isEmpty());
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

		Consumer<TableReferenceDefinitionHelper<ReferenceExampleTable>>
			consumer = tableReferenceDefinitionHelper -> {
				tableReferenceDefinitionHelper.defineNonreferenceColumn(
					ReferenceExampleTable.INSTANCE.name);

				tableReferenceDefinitionHelper.defineReferenceInnerJoin(
					parentJoinFunction);
			};

		TableReferenceDefinitionHelperImpl<ReferenceExampleTable>
			tableReferenceDefinitionHelperImpl = _defineTableReferences(
				ReferenceExampleTable.INSTANCE,
				ReferenceExampleTable.INSTANCE.referenceExampleId, consumer);

		TableReferenceInfo<ReferenceExampleTable> tableReferenceInfo =
			tableReferenceDefinitionHelperImpl.getTableReferenceInfo();

		Assert.assertNotNull(tableReferenceInfo);

		Assert.assertSame(
			ReferenceExampleTable.INSTANCE.referenceExampleId,
			tableReferenceInfo.getPrimaryKeyColumn());

		Map<Table<?>, List<Function<FromStep, JoinStep>>> childJoinMap =
			tableReferenceInfo.getChildJoinMap();

		Assert.assertTrue(childJoinMap.toString(), childJoinMap.isEmpty());

		Map<Table<?>, List<Function<FromStep, JoinStep>>> parentJoinMap =
			tableReferenceInfo.getParentJoinMap();

		Assert.assertEquals(parentJoinMap.toString(), 1, parentJoinMap.size());

		List<Function<FromStep, JoinStep>> functions = parentJoinMap.get(
			MainExampleTable.INSTANCE);

		Assert.assertEquals(functions.toString(), 1, functions.size());

		Assert.assertSame(parentJoinFunction, functions.get(0));
	}

	@Test
	public void testTableReferenceDefinitionInnerJoinValidation() {
		Consumer<TableReferenceDefinitionHelper<MainExampleTable>> consumer =
			tableReferenceDefinitionHelper -> {
				tableReferenceDefinitionHelper.defineNonreferenceColumn(
					MainExampleTable.INSTANCE.flag);

				tableReferenceDefinitionHelper.defineNonreferenceColumn(
					MainExampleTable.INSTANCE.name);

				tableReferenceDefinitionHelper.defineNonreferenceColumn(
					MainExampleTable.INSTANCE.description);

				try {
					tableReferenceDefinitionHelper.defineReferenceInnerJoin(
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
					tableReferenceDefinitionHelper.defineReferenceInnerJoin(
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
							"Join function must use provided FromStep for ",
							"JoinStep \"select * from MainExample inner join ",
							"MainExample on MainExample.mainExampleId = ",
							"MainExample.mainExampleId\""),
						illegalArgumentException.getMessage());
				}

				try {
					tableReferenceDefinitionHelper.defineReferenceInnerJoin(
						fromStep -> fromStep.from(
							ReferenceExampleTable.INSTANCE
						).innerJoinON(
							ReferenceExampleTable.INSTANCE,
							ReferenceExampleTable.INSTANCE.mainExampleId.eq(
								ReferenceExampleTable.INSTANCE.mainExampleId)
						));

					Assert.fail();
				}
				catch (IllegalArgumentException illegalArgumentException) {
					Assert.assertEquals(
						StringBundler.concat(
							"Required table \"MainExample\" is unused in ",
							"JoinStep \"... from ReferenceExample inner join ",
							"ReferenceExample on ",
							"ReferenceExample.mainExampleId = ",
							"ReferenceExample.mainExampleId\""),
						illegalArgumentException.getMessage());
				}

				try {
					tableReferenceDefinitionHelper.defineReferenceInnerJoin(
						fromStep -> fromStep.from(
							ReferenceExampleTable.INSTANCE
						).leftJoinOn(
							ReferenceExampleTable.INSTANCE,
							ReferenceExampleTable.INSTANCE.mainExampleId.eq(
								MainExampleTable.INSTANCE.mainExampleId)
						).leftJoinOn(
							MainExampleTable.INSTANCE,
							MainExampleTable.INSTANCE.mainExampleId.isNull()
						));

					Assert.fail();
				}
				catch (IllegalArgumentException illegalArgumentException) {
					Assert.assertEquals(
						StringBundler.concat(
							"Invalid join type \"left\" for JoinStep \"... ",
							"from ReferenceExample left join ReferenceExample ",
							"on ReferenceExample.mainExampleId = ",
							"MainExample.mainExampleId left join MainExample ",
							"on MainExample.mainExampleId is NULL\""),
						illegalArgumentException.getMessage());
				}

				try {
					tableReferenceDefinitionHelper.defineReferenceInnerJoin(
						fromStep -> fromStep.from(
							MainExampleTable.INSTANCE
						).innerJoinON(
							ReferenceExampleTable.INSTANCE,
							ReferenceExampleTable.INSTANCE.mainExampleId.neq(
								MainExampleTable.INSTANCE.mainExampleId
							).and(
								MainExampleTable.INSTANCE.mainExampleId.gte(
									ReferenceExampleTable.INSTANCE.
										mainExampleId)
							)
						));

					Assert.fail();
				}
				catch (IllegalArgumentException illegalArgumentException) {
					Assert.assertEquals(
						StringBundler.concat(
							"Invalid Predicate Operand \"!=\" for JoinStep ",
							"\"... from MainExample inner join ",
							"ReferenceExample on ",
							"ReferenceExample.mainExampleId != ",
							"MainExample.mainExampleId and ",
							"MainExample.mainExampleId >= ",
							"ReferenceExample.mainExampleId\""),
						illegalArgumentException.getMessage());
				}

				try {
					tableReferenceDefinitionHelper.defineReferenceInnerJoin(
						fromStep -> fromStep.from(
							MainExampleTable.INSTANCE
						).innerJoinON(
							MainExampleTable.INSTANCE,
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
							"[MainExample] for joinStep \"... from ",
							"MainExample inner join MainExample on ",
							"ReferenceExample.mainExampleId = ",
							"MainExample.mainExampleId\""),
						illegalArgumentException.getMessage());
				}

				try {
					tableReferenceDefinitionHelper.defineReferenceInnerJoin(
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
					tableReferenceDefinitionHelper.defineReferenceInnerJoin(
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
					tableReferenceDefinitionHelper.defineReferenceInnerJoin(
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
			};

		TableReferenceDefinitionHelperImpl<MainExampleTable>
			tableReferenceDefinitionHelperImpl = _defineTableReferences(
				MainExampleTable.INSTANCE,
				MainExampleTable.INSTANCE.mainExampleId, consumer);

		TableReferenceInfo<MainExampleTable> tableReferenceInfo =
			tableReferenceDefinitionHelperImpl.getTableReferenceInfo();

		Assert.assertNotNull(tableReferenceInfo);

		Assert.assertSame(
			MainExampleTable.INSTANCE.mainExampleId,
			tableReferenceInfo.getPrimaryKeyColumn());

		Map<Table<?>, List<Function<FromStep, JoinStep>>> childJoinMap =
			tableReferenceInfo.getChildJoinMap();

		Assert.assertTrue(childJoinMap.toString(), childJoinMap.isEmpty());

		Map<Table<?>, List<Function<FromStep, JoinStep>>> parentJoinMap =
			tableReferenceInfo.getParentJoinMap();

		Assert.assertTrue(parentJoinMap.toString(), parentJoinMap.isEmpty());
	}

	@Test
	public void testTableReferenceDefinitionTableColumnValidation() {
		Consumer<TableReferenceDefinitionHelper<MainExampleTable>> consumer =
			tableReferenceDefinitionHelper -> {
				tableReferenceDefinitionHelper.defineReferenceInnerJoin(
					fromStep -> fromStep.from(
						ReferenceExampleTable.INSTANCE
					).innerJoinON(
						MainExampleTable.INSTANCE,
						ReferenceExampleTable.INSTANCE.mainExampleId.eq(
							MainExampleTable.INSTANCE.mainExampleId)
					));

				tableReferenceDefinitionHelper.defineNonreferenceColumn(
					MainExampleTable.INSTANCE.name);
			};

		TableReferenceDefinitionHelperImpl<MainExampleTable>
			tableReferenceDefinitionHelperImpl = _defineTableReferences(
				MainExampleTable.INSTANCE,
				MainExampleTable.INSTANCE.mainExampleId, consumer);

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					TableReferenceDefinitionHelperImpl.class.getName(),
					Level.WARNING)) {

			TableReferenceInfo<MainExampleTable> tableReferenceInfo =
				tableReferenceDefinitionHelperImpl.getTableReferenceInfo();

			Assert.assertNull(tableReferenceInfo);

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				StringBundler.concat(
					TestTableReferenceDefinition.class.getName(),
					" did not define columns [MainExample.description, ",
					"MainExample.flag]"),
				logRecord.getMessage());
		}
	}

	private <T extends Table<T>> TableReferenceDefinitionHelperImpl<T>
		_defineTableReferences(
			T table, Column<T, ?> primaryKeyColumn,
			Consumer<TableReferenceDefinitionHelper<T>> consumer) {

		TestTableReferenceDefinition<T> testTableReferenceDefinition =
			new TestTableReferenceDefinition<>(table, consumer);

		TableReferenceDefinitionHelperImpl<T>
			tableReferenceDefinitionHelperImpl =
				new TableReferenceDefinitionHelperImpl<>(
					testTableReferenceDefinition, primaryKeyColumn);

		testTableReferenceDefinition.defineTableReferences(
			tableReferenceDefinitionHelperImpl);

		return tableReferenceDefinitionHelperImpl;
	}

	private static class MainExampleTable extends BaseTable<MainExampleTable> {

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

		public static final ReferenceExampleTable INSTANCE =
			new ReferenceExampleTable();

		public final Column<ReferenceExampleTable, Long> mainExampleId =
			createColumn(
				"mainExampleId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
		public final Column<ReferenceExampleTable, Long> mvccVersion =
			createColumn(
				"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
		public final Column<ReferenceExampleTable, String> name = createColumn(
			"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
		public final Column<ReferenceExampleTable, Long> referenceExampleId =
			createColumn(
				"referenceExampleId", Long.class, Types.BIGINT,
				Column.FLAG_PRIMARY);

		private ReferenceExampleTable() {
			super("ReferenceExample", ReferenceExampleTable::new);
		}

	}

	private class TestTableReferenceDefinition<T extends Table<T>>
		implements TableReferenceDefinition<T> {

		@Override
		public void defineTableReferences(
			TableReferenceDefinitionHelper<T> tableReferenceDefinitionHelper) {

			_consumer.accept(tableReferenceDefinitionHelper);
		}

		@Override
		public PersistedModelLocalService getPersistedModelLocalService() {
			return null;
		}

		@Override
		public T getTable() {
			return _table;
		}

		@Override
		public String toString() {
			return TestTableReferenceDefinition.class.getName();
		}

		private TestTableReferenceDefinition(
			T table, Consumer<TableReferenceDefinitionHelper<T>> consumer) {

			_table = table;
			_consumer = consumer;
		}

		private final Consumer<TableReferenceDefinitionHelper<T>> _consumer;
		private final T _table;

	}

}