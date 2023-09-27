/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.reference;

import com.liferay.change.tracking.internal.reference.builder.BaseTableReferenceInfoBuilder;
import com.liferay.change.tracking.internal.reference.builder.ChildTableReferenceInfoBuilderImpl;
import com.liferay.change.tracking.internal.reference.builder.ParentTableReferenceInfoBuilderImpl;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.base.BaseTable;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.petra.sql.dsl.query.WhereStep;
import com.liferay.petra.sql.dsl.spi.ast.DefaultASTNodeListener;
import com.liferay.petra.sql.dsl.spi.expression.Scalar;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

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
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class TableReferenceInfoFactoryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new CodeCoverageAssertor() {

				@Override
				public void appendAssertClasses(List<Class<?>> assertClasses) {
					assertClasses.add(BaseTableReferenceInfoBuilder.class);
					assertClasses.add(ChildTableReferenceInfoBuilderImpl.class);
					assertClasses.add(
						ParentTableReferenceInfoBuilderImpl.class);
					assertClasses.add(TableJoinHolderFactory.class);

					Collections.addAll(
						assertClasses,
						TableJoinHolderFactory.class.getDeclaredClasses());

					assertClasses.add(TableReferenceInfo.class);
				}

			},
			LiferayUnitTestRule.INSTANCE);

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
				MainExampleTable.INSTANCE.mainExampleIdColumn.eq(
					ReferenceExampleTable.INSTANCE.mainExampleIdColumn)
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
				MainExampleTable.CLASS_NAME_ID,
				MainExampleTable.INSTANCE.mainExampleIdColumn,
				tableReferenceDefinition);

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
	public void testMissingRequirements() {
		TableReferenceInfo<ReferenceExampleTable> tableReferenceInfo =
			TableReferenceInfoFactory.create(
				ReferenceExampleTable.CLASS_NAME_ID,
				ReferenceExampleTable.INSTANCE.mainExampleIdColumn,
				new MissingRequirementsTableReferenceDefinition());

		Map<Table<?>, List<TableJoinHolder>> parentTableJoinHoldersMap =
			tableReferenceInfo.getParentTableJoinHoldersMap();

		Assert.assertEquals(
			parentTableJoinHoldersMap.toString(), 4,
			parentTableJoinHoldersMap.size());

		Scalar<String> key = new Scalar<>("key");

		_assertMissingRequirementsSQL(
			parentTableJoinHoldersMap.get(MainExampleTable.INSTANCE),
			DSLQueryFactoryUtil.select(
				ReferenceExampleTable.INSTANCE.mainExampleIdColumn,
				new Scalar<>(MainExampleTable.INSTANCE.getTableName())
			).from(
				ReferenceExampleTable.INSTANCE
			).leftJoinOn(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.value.eq(
					TableReferenceInfoFactoryTest.class.getName())
			).leftJoinOn(
				MainExampleTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					MainExampleTable.INSTANCE.classNameIdColumn
				).and(
					MainExampleTable.INSTANCE.flagColumn.eq(0)
				).and(
					ReferenceExampleTable.INSTANCE.referenceExampleIdColumn.eq(
						MainExampleTable.INSTANCE.classPKColumn)
				).and(
					key.eq(MainExampleTable.INSTANCE.nameColumn)
				)
			).where(
				() -> {
					Predicate predicate =
						MainExampleTable.INSTANCE.mainExampleIdColumn.isNull();

					return predicate.and(
						ReferenceExampleTable.INSTANCE.referenceExampleIdColumn.
							isNotNull()
					).and(
						ReferenceExampleTable.INSTANCE.referenceExampleIdColumn.
							neq(0L)
					);
				}
			));

		ReferenceExampleTable aliasReferenceExampleTable =
			ReferenceExampleTable.INSTANCE.as("aliasParentTable");

		_assertMissingRequirementsSQL(
			parentTableJoinHoldersMap.get(ReferenceExampleTable.INSTANCE),
			DSLQueryFactoryUtil.select(
				ReferenceExampleTable.INSTANCE.mainExampleIdColumn,
				new Scalar<>(ReferenceExampleTable.INSTANCE.getTableName())
			).from(
				ReferenceExampleTable.INSTANCE
			).leftJoinOn(
				aliasReferenceExampleTable,
				ReferenceExampleTable.INSTANCE.parentReferenceExampleIdColumn.
					eq(aliasReferenceExampleTable.referenceExampleIdColumn)
			).where(
				() -> {
					Predicate predicate =
						aliasReferenceExampleTable.referenceExampleIdColumn.
							isNull();

					return predicate.and(
						ReferenceExampleTable.INSTANCE.
							parentReferenceExampleIdColumn.isNotNull()
					).and(
						ReferenceExampleTable.INSTANCE.
							parentReferenceExampleIdColumn.neq(0L)
					);
				}
			));

		_assertMissingRequirementsSQL(
			parentTableJoinHoldersMap.get(StringIntExampleTable.INSTANCE),
			DSLQueryFactoryUtil.select(
				ReferenceExampleTable.INSTANCE.mainExampleIdColumn,
				new Scalar<>(StringIntExampleTable.INSTANCE.getTableName())
			).from(
				ReferenceExampleTable.INSTANCE
			).leftJoinOn(
				StringIntExampleTable.INSTANCE,
				ReferenceExampleTable.INSTANCE.stringKeyColumn.eq(
					StringIntExampleTable.INSTANCE.stringKeyColumn
				).and(
					ReferenceExampleTable.INSTANCE.integerKeyColumn.eq(
						StringIntExampleTable.INSTANCE.integerKeyColumn)
				)
			).where(
				() -> {
					Predicate predicate =
						StringIntExampleTable.INSTANCE.idColumn.isNull();

					return predicate.and(
						ReferenceExampleTable.INSTANCE.stringKeyColumn.
							isNotNull()
					).and(
						ReferenceExampleTable.INSTANCE.stringKeyColumn.neq("")
					).and(
						ReferenceExampleTable.INSTANCE.integerKeyColumn.
							isNotNull()
					);
				}
			));

		_assertMissingRequirementsSQL(
			parentTableJoinHoldersMap.get(BridgeJoinExampleTable.INSTANCE),
			DSLQueryFactoryUtil.select(
				ReferenceExampleTable.INSTANCE.mainExampleIdColumn,
				new Scalar<>(BridgeJoinExampleTable.INSTANCE.getTableName())
			).from(
				ReferenceExampleTable.INSTANCE
			).leftJoinOn(
				BridgeJoinExampleTable.INSTANCE,
				DSLFunctionFactoryUtil.castText(
					ReferenceExampleTable.INSTANCE.referenceExampleIdColumn
				).eq(
					BridgeJoinExampleTable.INSTANCE.stringKeyColumn
				)
			).leftJoinOn(
				MainExampleTable.INSTANCE,
				MainExampleTable.INSTANCE.mainExampleIdColumn.eq(
					ReferenceExampleTable.INSTANCE.mainExampleIdColumn)
			).where(
				BridgeJoinExampleTable.INSTANCE.bridgeJoinExampleIdColumn.
					isNull(
					).and(
						ReferenceExampleTable.INSTANCE.integerKeyColumn.eq(0)
					).and(
						ReferenceExampleTable.INSTANCE.mainExampleIdColumn.
							isNotNull()
					).and(
						ReferenceExampleTable.INSTANCE.mainExampleIdColumn.neq(
							0L)
					)
			));
	}

	@Test
	public void testParentInnerJoin() {
		Function<FromStep, JoinStep> parentJoinFunction =
			fromStep -> fromStep.from(
				MainExampleTable.INSTANCE
			).innerJoinON(
				ReferenceExampleTable.INSTANCE,
				MainExampleTable.INSTANCE.mainExampleIdColumn.eq(
					ReferenceExampleTable.INSTANCE.mainExampleIdColumn)
			);

		ReferenceExampleTable aliasReferenceExampleTable =
			ReferenceExampleTable.INSTANCE.as("aliasReferenceExampleTable");

		Function<FromStep, JoinStep> selfJoinFunction1 =
			fromStep -> fromStep.from(
				aliasReferenceExampleTable
			).innerJoinON(
				ReferenceExampleTable.INSTANCE,
				ReferenceExampleTable.INSTANCE.referenceExampleIdColumn.eq(
					aliasReferenceExampleTable.parentReferenceExampleIdColumn)
			);

		Function<FromStep, JoinStep> selfJoinFunction2 =
			fromStep -> fromStep.from(
				aliasReferenceExampleTable
			).innerJoinON(
				ReferenceExampleTable.INSTANCE,
				ReferenceExampleTable.INSTANCE.parentReferenceExampleIdColumn.
					eq(aliasReferenceExampleTable.referenceExampleIdColumn)
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
							selfJoinFunction1);
					}

					@Override
					public void defineParentTableReferences(
						ParentTableReferenceInfoBuilder<ReferenceExampleTable>
							parentTableReferenceInfoBuilder) {

						parentTableReferenceInfoBuilder.referenceInnerJoin(
							parentJoinFunction
						).referenceInnerJoin(
							selfJoinFunction2
						);
					}

				};

		TableReferenceInfo<ReferenceExampleTable> tableReferenceInfo =
			TableReferenceInfoFactory.create(
				ReferenceExampleTable.CLASS_NAME_ID,
				ReferenceExampleTable.INSTANCE.referenceExampleIdColumn,
				tableReferenceDefinition);

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
			childTableJoinHolders.toString(), 1, childTableJoinHolders.size());

		TableJoinHolder childJoinHolder = childTableJoinHolders.get(0);

		Assert.assertSame(selfJoinFunction1, childJoinHolder.getJoinFunction());

		Assert.assertSame(
			aliasReferenceExampleTable.referenceExampleIdColumn,
			childJoinHolder.getParentPKColumn());

		Assert.assertSame(
			ReferenceExampleTable.INSTANCE.referenceExampleIdColumn,
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
			MainExampleTable.INSTANCE.mainExampleIdColumn,
			parentJoinHolder.getParentPKColumn());

		Assert.assertSame(
			ReferenceExampleTable.INSTANCE.referenceExampleIdColumn,
			parentJoinHolder.getChildPKColumn());

		parentTableJoinHolders = parentTableJoinHoldersMap.get(
			ReferenceExampleTable.INSTANCE);

		Assert.assertEquals(
			parentTableJoinHolders.toString(), 1,
			parentTableJoinHolders.size());

		childJoinHolder = parentTableJoinHolders.get(0);

		Assert.assertSame(selfJoinFunction2, childJoinHolder.getJoinFunction());

		Assert.assertSame(
			aliasReferenceExampleTable.referenceExampleIdColumn,
			childJoinHolder.getParentPKColumn());

		Assert.assertSame(
			ReferenceExampleTable.INSTANCE.referenceExampleIdColumn,
			childJoinHolder.getChildPKColumn());

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
								MainExampleTable.INSTANCE.mainExampleIdColumn.
									eq(
										MainExampleTable.INSTANCE.
											mainExampleIdColumn)
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
								ReferenceExampleTable.INSTANCE.
									mainExampleIdColumn.eq(
										ReferenceExampleTable.INSTANCE.
											mainExampleIdColumn)
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
								MainExampleTable.INSTANCE.mainExampleIdColumn.
									eq(
										MainExampleTable.INSTANCE.
											mainExampleIdColumn)
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
							).innerJoinON(
								ReferenceExampleTable.INSTANCE,
								ReferenceExampleTable.INSTANCE.
									mainExampleIdColumn.eq(
										MainExampleTable.INSTANCE.
											mainExampleIdColumn)
							));

						Assert.fail();
					}
					catch (IllegalArgumentException illegalArgumentException) {
						Assert.assertEquals(
							StringBundler.concat(
								"First join must be on table \"MainExample\" ",
								"for join step \"... from MainExample inner ",
								"join ReferenceExample on ",
								"ReferenceExample.mainExampleId = ",
								"MainExample.mainExampleId\""),
							illegalArgumentException.getMessage());
					}

					try {
						childTableReferenceInfoBuilder.referenceInnerJoin(
							fromStep -> fromStep.from(
								ReferenceExampleTable.INSTANCE
							).leftJoinOn(
								MainExampleTable.INSTANCE,
								ReferenceExampleTable.INSTANCE.
									mainExampleIdColumn.eq(
										MainExampleTable.INSTANCE.
											mainExampleIdColumn)
							));

						Assert.fail();
					}
					catch (IllegalArgumentException illegalArgumentException) {
						Assert.assertEquals(
							StringBundler.concat(
								"Invalid join type \"left\" for join step ",
								"\"... from ReferenceExample left join ",
								"MainExample on ",
								"ReferenceExample.mainExampleId = ",
								"MainExample.mainExampleId\""),
							illegalArgumentException.getMessage());
					}

					try {
						childTableReferenceInfoBuilder.referenceInnerJoin(
							fromStep -> fromStep.from(
								ReferenceExampleTable.INSTANCE
							).innerJoinON(
								MainExampleTable.INSTANCE,
								ReferenceExampleTable.INSTANCE.
									mainExampleIdColumn.neq(
										MainExampleTable.INSTANCE.
											mainExampleIdColumn
									).and(
										MainExampleTable.INSTANCE.
											mainExampleIdColumn.gte(
												ReferenceExampleTable.INSTANCE.
													mainExampleIdColumn)
									)
							));

						Assert.fail();
					}
					catch (IllegalArgumentException illegalArgumentException) {
						Assert.assertEquals(
							StringBundler.concat(
								"Invalid predicate operand \"!=\" for join ",
								"step \"... from ReferenceExample inner join ",
								"MainExample on ",
								"ReferenceExample.mainExampleId != ",
								"MainExample.mainExampleId and ",
								"MainExample.mainExampleId >= ",
								"ReferenceExample.mainExampleId\""),
							illegalArgumentException.getMessage());
					}

					try {
						childTableReferenceInfoBuilder.referenceInnerJoin(
							fromStep -> fromStep.from(
								MainExampleTable.INSTANCE.as("aliasMainExample")
							).innerJoinON(
								MainExampleTable.INSTANCE,
								ReferenceExampleTable.INSTANCE.
									mainExampleIdColumn.eq(
										MainExampleTable.INSTANCE.
											mainExampleIdColumn)
							));

						Assert.fail();
					}
					catch (IllegalArgumentException illegalArgumentException) {
						Assert.assertEquals(
							StringBundler.concat(
								"Predicate column tables [MainExample, ",
								"ReferenceExample] do not match join tables ",
								"[MainExample, MainExample aliasMainExample] ",
								"for join step \"... from MainExample ",
								"aliasMainExample inner join MainExample on ",
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
								MainExampleTable.INSTANCE.mainExampleIdColumn.
									eq(
										InvalidTable.INSTANCE.
											mainExampleIdColumn)
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
								fromStep.as("test", Collections.emptyList());

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
								fromStep.as("test", MainExampleTable.INSTANCE);

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

					try {
						parentTableReferenceInfoBuilder.referenceInnerJoin(
							fromStep -> fromStep.from(
								BridgeJoinExampleTable.INSTANCE
							).innerJoinON(
								MainExampleTable.INSTANCE,
								MainExampleTable.INSTANCE.flagColumn.eq(2)
							).innerJoinON(
								ReferenceExampleTable.INSTANCE,
								ReferenceExampleTable.INSTANCE.
									mainExampleIdColumn.eq(
										MainExampleTable.INSTANCE.
											mainExampleIdColumn)
							).innerJoinON(
								StringIntExampleTable.INSTANCE,
								DSLFunctionFactoryUtil.concat(
									BridgeJoinExampleTable.INSTANCE.
										stringKeyColumn,
									new Scalar<>("_"),
									StringIntExampleTable.INSTANCE.
										stringKeyColumn
								).eq(
									MainExampleTable.INSTANCE.nameColumn
								)
							));

						Assert.fail();
					}
					catch (IllegalArgumentException illegalArgumentException) {
						Assert.assertEquals(
							StringBundler.concat(
								"Unable to apply predicates [CONCAT(",
								"BridgeJoinExample.stringKey, ?, ",
								"StringIntExample.stringKey) = ",
								"MainExample.name] to select ",
								"MainExample.mainExampleId, ? from ",
								"MainExample left join ReferenceExample on ",
								"ReferenceExample.mainExampleId = ",
								"MainExample.mainExampleId"),
							illegalArgumentException.getMessage());
					}
				}

			};

		TableReferenceInfo<MainExampleTable> tableReferenceInfo =
			TableReferenceInfoFactory.create(
				MainExampleTable.CLASS_NAME_ID,
				MainExampleTable.INSTANCE.mainExampleIdColumn,
				tableReferenceDefinition);

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

	private void _assertMissingRequirementsSQL(
		List<TableJoinHolder> tableJoinHolders, DSLQuery expectedDSLQuery) {

		Assert.assertNotNull(tableJoinHolders);

		Assert.assertEquals(
			tableJoinHolders.toString(), 1, tableJoinHolders.size());

		TableJoinHolder tableJoinHolder = tableJoinHolders.get(0);

		WhereStep whereStep = tableJoinHolder.getMissingRequirementWhereStep();

		DSLQuery dslQuery = whereStep.where(
			tableJoinHolder.getMissingRequirementWherePredicate());

		DefaultASTNodeListener expectedDefaultASTNodeListener =
			new DefaultASTNodeListener();
		DefaultASTNodeListener actualDefaultASTNodeListener =
			new DefaultASTNodeListener();

		Assert.assertEquals(
			expectedDSLQuery.toSQL(expectedDefaultASTNodeListener),
			dslQuery.toSQL(actualDefaultASTNodeListener));

		Assert.assertEquals(
			expectedDefaultASTNodeListener.getScalarValues(),
			actualDefaultASTNodeListener.getScalarValues());
	}

	private static List<TableReferenceAppender> _tableReferenceAppenders;

	private static class BridgeJoinExampleTable
		extends BaseTable<BridgeJoinExampleTable> {

		public static final BridgeJoinExampleTable INSTANCE =
			new BridgeJoinExampleTable();

		public final Column<BridgeJoinExampleTable, Long>
			bridgeJoinExampleIdColumn = createColumn(
				"bridgeJoinExampleId", Long.class, Types.BIGINT,
				Column.FLAG_PRIMARY);
		public final Column<BridgeJoinExampleTable, Long> mvccVersionColumn =
			createColumn(
				"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
		public final Column<BridgeJoinExampleTable, String> stringKeyColumn =
			createColumn(
				"stringKey", String.class, Types.BIGINT, Column.FLAG_DEFAULT);

		private BridgeJoinExampleTable() {
			super("BridgeJoinExample", BridgeJoinExampleTable::new);
		}

	}

	private static class InvalidTable extends BaseTable<InvalidTable> {

		public static final InvalidTable INSTANCE = new InvalidTable();

		public final Column<InvalidTable, Long> mainExampleIdColumn =
			createColumn(
				"mainExampleId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

		private InvalidTable() {
			super("InvalidTable", InvalidTable::new);
		}

	}

	private static class MainExampleTable extends BaseTable<MainExampleTable> {

		public static final long CLASS_NAME_ID = 1;

		public static final MainExampleTable INSTANCE = new MainExampleTable();

		public final Column<MainExampleTable, Long> classNameIdColumn =
			createColumn(
				"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
		public final Column<MainExampleTable, Long> classPKColumn =
			createColumn(
				"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
		public final Column<MainExampleTable, Clob> descriptionColumn =
			createColumn(
				"description", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
		public final Column<MainExampleTable, Integer> flagColumn =
			createColumn(
				"flag", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
		public final Column<MainExampleTable, Long> mainExampleIdColumn =
			createColumn(
				"mainExampleId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
		public final Column<MainExampleTable, Long> mvccVersionColumn =
			createColumn(
				"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
		public final Column<MainExampleTable, String> nameColumn = createColumn(
			"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

		private MainExampleTable() {
			super("MainExample", MainExampleTable::new);
		}

	}

	private static class MissingRequirementsTableReferenceDefinition
		extends TestTableReferenceDefinition<ReferenceExampleTable> {

		@Override
		public void defineChildTableReferences(
			ChildTableReferenceInfoBuilder<ReferenceExampleTable>
				childTableReferenceInfoBuilder) {
		}

		@Override
		public void defineParentTableReferences(
			ParentTableReferenceInfoBuilder<ReferenceExampleTable>
				parentTableReferenceInfoBuilder) {

			parentTableReferenceInfoBuilder.parentColumnReference(
				ReferenceExampleTable.INSTANCE.referenceExampleIdColumn,
				ReferenceExampleTable.INSTANCE.parentReferenceExampleIdColumn
			).referenceInnerJoin(
				fromStep -> fromStep.from(
					StringIntExampleTable.INSTANCE
				).innerJoinON(
					ReferenceExampleTable.INSTANCE,
					ReferenceExampleTable.INSTANCE.stringKeyColumn.eq(
						StringIntExampleTable.INSTANCE.stringKeyColumn
					).and(
						ReferenceExampleTable.INSTANCE.integerKeyColumn.eq(
							StringIntExampleTable.INSTANCE.integerKeyColumn)
					)
				)
			).referenceInnerJoin(
				fromStep -> {
					Scalar<String> key = new Scalar<>("key");

					return fromStep.from(
						MainExampleTable.INSTANCE
					).innerJoinON(
						ReferenceExampleTable.INSTANCE,
						ReferenceExampleTable.INSTANCE.referenceExampleIdColumn.
							eq(
								MainExampleTable.INSTANCE.classPKColumn
							).and(
								key.eq(MainExampleTable.INSTANCE.nameColumn)
							).and(
								MainExampleTable.INSTANCE.flagColumn.eq(0)
							)
					).innerJoinON(
						ClassNameTable.INSTANCE,
						ClassNameTable.INSTANCE.classNameId.eq(
							MainExampleTable.INSTANCE.classNameIdColumn
						).and(
							ClassNameTable.INSTANCE.value.eq(
								TableReferenceInfoFactoryTest.class.getName())
						)
					);
				}
			).referenceInnerJoin(
				fromStep -> fromStep.from(
					BridgeJoinExampleTable.INSTANCE
				).innerJoinON(
					ReferenceExampleTable.INSTANCE,
					ReferenceExampleTable.INSTANCE.integerKeyColumn.eq(0)
				).innerJoinON(
					MainExampleTable.INSTANCE,
					DSLFunctionFactoryUtil.castText(
						ReferenceExampleTable.INSTANCE.referenceExampleIdColumn
					).eq(
						BridgeJoinExampleTable.INSTANCE.stringKeyColumn
					).and(
						MainExampleTable.INSTANCE.mainExampleIdColumn.eq(
							ReferenceExampleTable.INSTANCE.mainExampleIdColumn)
					)
				)
			);
		}

		private MissingRequirementsTableReferenceDefinition() {
			super(ReferenceExampleTable.INSTANCE);
		}

	}

	private static class ReferenceExampleTable
		extends BaseTable<ReferenceExampleTable> {

		public static final long CLASS_NAME_ID = 2;

		public static final ReferenceExampleTable INSTANCE =
			new ReferenceExampleTable();

		public final Column<ReferenceExampleTable, Integer> integerKeyColumn =
			createColumn(
				"integerKey", Integer.class, Types.BIGINT, Column.FLAG_DEFAULT);
		public final Column<ReferenceExampleTable, Long> mainExampleIdColumn =
			createColumn(
				"mainExampleId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
		public final Column<ReferenceExampleTable, Long> mvccVersionColumn =
			createColumn(
				"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
		public final Column<ReferenceExampleTable, Long>
			parentReferenceExampleIdColumn = createColumn(
				"parentReferenceExampleId", Long.class, Types.VARCHAR,
				Column.FLAG_DEFAULT);
		public final Column<ReferenceExampleTable, Long>
			referenceExampleIdColumn = createColumn(
				"referenceExampleId", Long.class, Types.BIGINT,
				Column.FLAG_PRIMARY);
		public final Column<ReferenceExampleTable, String> stringKeyColumn =
			createColumn(
				"stringKey", String.class, Types.BIGINT, Column.FLAG_DEFAULT);

		private ReferenceExampleTable() {
			super("ReferenceExample", ReferenceExampleTable::new);
		}

	}

	private static class StringIntExampleTable
		extends BaseTable<StringIntExampleTable> {

		public static final StringIntExampleTable INSTANCE =
			new StringIntExampleTable();

		public final Column<StringIntExampleTable, Long> idColumn =
			createColumn("id", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
		public final Column<StringIntExampleTable, Integer> integerKeyColumn =
			createColumn(
				"integerKey", Integer.class, Types.BIGINT, Column.FLAG_DEFAULT);
		public final Column<StringIntExampleTable, String> stringKeyColumn =
			createColumn(
				"stringKey", String.class, Types.BIGINT, Column.FLAG_DEFAULT);

		private StringIntExampleTable() {
			super("StringIntExample", StringIntExampleTable::new);
		}

	}

	private abstract static class TestTableReferenceDefinition
		<T extends Table<T>>
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