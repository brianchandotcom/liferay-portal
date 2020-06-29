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

package com.liferay.change.tracking.internal.reference.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.petra.sql.dsl.spi.query.From;
import com.liferay.portal.change.tracking.registry.CTModelRegistry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class MissingTableReferenceDefinitionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		Bundle bundle = FrameworkUtil.getBundle(
			MissingTableReferenceDefinitionTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext,
			(Class<TableReferenceDefinition<?>>)
				(Class<?>)TableReferenceDefinition.class,
			null,
			(serviceReference, emitter) -> {
				TableReferenceDefinition<?> tableReferenceDefinition =
					bundleContext.getService(serviceReference);

				Table<?> table = tableReferenceDefinition.getTable();

				emitter.emit(table.getTableName());

				bundleContext.ungetService(serviceReference);
			});
	}

	@After
	public void tearDown() {
		_serviceTrackerMap.close();
	}

	@Test
	public void testMissingTableReferenceDefinitions() {
		Set<String> missingTableNames = new TreeSet<>(
			CTModelRegistry.getTableNames());

		for (TableReferenceDefinition<?> tableReferenceDefinition :
				_serviceTrackerMap.values()) {

			_collectTableNames(tableReferenceDefinition, missingTableNames);
		}

		missingTableNames.removeAll(_serviceTrackerMap.keySet());

		Assert.assertTrue(
			"Missing TableReferenceDefinitions for " +
				missingTableNames.toString(),
			missingTableNames.isEmpty());
	}

	private static <T extends Table<T>> void _collectTableNames(
		TableReferenceDefinition<T> tableReferenceDefinition,
		Set<String> tableNames) {

		tableReferenceDefinition.defineParentTableReferences(
			new TestParentTableReferenceInfoBuilder<>(
				tableReferenceDefinition.getTable(), tableNames));

		tableReferenceDefinition.defineChildTableReferences(
			new TestChildTableReferenceInfoBuilder<>(
				tableReferenceDefinition.getTable(), tableNames));
	}

	private ServiceTrackerMap<String, TableReferenceDefinition<?>>
		_serviceTrackerMap;

	private static class BaseTestTableReferenceInfoBuilder<T extends Table<T>> {

		protected BaseTestTableReferenceInfoBuilder(
			T table, Set<String> tableNames) {

			_table = table;
			_tableNames = tableNames;
		}

		protected void applyReferenceInnerJoin(
			Function<FromStep, JoinStep> joinFunction) {

			FromStep fromStep = DSLQueryFactoryUtil.select(_table);

			DSLQuery dslQuery = joinFunction.apply(fromStep);

			dslQuery.toSQL(
				s -> {
				},
				astNode -> {
					if (astNode instanceof From) {
						From from = (From)astNode;

						if (from.getChild() == fromStep) {
							Table<?> fromTable = from.getTable();

							_tableNames.add(fromTable.getTableName());
						}
					}
				});
		}

		private final T _table;
		private final Set<String> _tableNames;

	}

	private static class TestChildTableReferenceInfoBuilder<T extends Table<T>>
		extends BaseTestTableReferenceInfoBuilder<T>
		implements ChildTableReferenceInfoBuilder<T> {

		@Override
		public ChildTableReferenceInfoBuilder<T> referenceInnerJoin(
			Function<FromStep, JoinStep> joinFunction) {

			applyReferenceInnerJoin(joinFunction);

			return this;
		}

		private TestChildTableReferenceInfoBuilder(
			T table, Set<String> tableNames) {

			super(table, tableNames);
		}

	}

	private static class TestParentTableReferenceInfoBuilder<T extends Table<T>>
		extends BaseTestTableReferenceInfoBuilder<T>
		implements ParentTableReferenceInfoBuilder<T> {

		@Override
		public ParentTableReferenceInfoBuilder<T> referenceInnerJoin(
			Function<FromStep, JoinStep> joinFunction) {

			applyReferenceInnerJoin(joinFunction);

			return this;
		}

		private TestParentTableReferenceInfoBuilder(
			T table, Set<String> tableNames) {

			super(table, tableNames);
		}

	}

}