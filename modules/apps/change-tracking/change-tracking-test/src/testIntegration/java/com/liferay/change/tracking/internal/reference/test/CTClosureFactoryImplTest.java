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
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.builder.TableReferenceInfoBuilder;
import com.liferay.change.tracking.reference.closure.CTClosure;
import com.liferay.change.tracking.reference.closure.CTClosureFactory;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.sql.Types;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class CTClosureFactoryImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(CTClosureFactoryImplTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		Method getDataSourceMethod = BasePersistence.class.getMethod(
			"getDataSource");
		Method getModelClassMethod = BasePersistence.class.getMethod(
			"getModelClass");

		_serviceRegistration1 = bundleContext.registerService(
			TableReferenceDefinition.class,
			new GrandParentTableReferenceDefinition(
				(BasePersistence<?>)ProxyUtil.newProxyInstance(
					BasePersistence.class.getClassLoader(),
					new Class<?>[] {BasePersistence.class},
					(proxy, method, args) -> {
						if (method.equals(getDataSourceMethod)) {
							return InfrastructureUtil.getDataSource();
						}
						else if (method.equals(getModelClassMethod)) {
							return GrandParent.class;
						}

						throw new UnsupportedOperationException(
							method.getName());
					})),
			null);

		_serviceRegistration2 = bundleContext.registerService(
			TableReferenceDefinition.class,
			new ParentTableReferenceDefinition(
				(BasePersistence<?>)ProxyUtil.newProxyInstance(
					BasePersistence.class.getClassLoader(),
					new Class<?>[] {BasePersistence.class},
					(proxy, method, args) -> {
						if (method.equals(getDataSourceMethod)) {
							return InfrastructureUtil.getDataSource();
						}
						else if (method.equals(getModelClassMethod)) {
							return Parent.class;
						}

						throw new UnsupportedOperationException(
							method.getName());
					})),
			null);

		_serviceRegistration3 = bundleContext.registerService(
			TableReferenceDefinition.class,
			new ChildTableReferenceDefinition(
				(BasePersistence<?>)ProxyUtil.newProxyInstance(
					BasePersistence.class.getClassLoader(),
					new Class<?>[] {BasePersistence.class},
					(proxy, method, args) -> {
						if (method.equals(getDataSourceMethod)) {
							return InfrastructureUtil.getDataSource();
						}
						else if (method.equals(getModelClassMethod)) {
							return Child.class;
						}

						throw new UnsupportedOperationException(
							method.getName());
					})),
			null);
	}

	@AfterClass
	public static void tearDownClass() {
		_serviceRegistration1.unregister();
		_serviceRegistration2.unregister();
		_serviceRegistration3.unregister();
	}

	@Before
	public void setUp() throws Exception {
		_db = DBManagerUtil.getDB();

		_db.runSQL(
			"create table GrandParentTable (grandParentId LONG not null " +
				"primary key, parentGrandParentId LONG);");
		_db.runSQL(
			StringBundler.concat(
				"create table ParentTable (parentId LONG not null, ",
				"ctCollectionId LONG not null, grandParentId LONG, name ",
				"VARCHAR(75) null, primary key (parentId, ctCollectionId));"));

		_db.runSQL(
			"create unique index IX_GP_N on ParentTable (grandParentId, " +
				"name, ctCollectionId);");

		_db.runSQL(
			StringBundler.concat(
				"create table ChildTable (childId LONG not null, ",
				"ctCollectionId LONG not null, grandParentId LONG, ",
				"parentChildId LONG, parentName VARCHAR(75) null, primary key ",
				"(childId, ctCollectionId));"));

		_ctCollection = _ctCollectionLocalService.addCTCollection(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			CTClosureFactoryImplTest.class.getSimpleName(), StringPool.BLANK);
	}

	@After
	public void tearDown() throws Exception {
		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"com.liferay.change.tracking.service.impl." +
						"CTCollectionLocalServiceImpl",
					Level.WARN)) {

			_ctCollectionLocalService.deleteCTCollection(
				_ctCollection.getCtCollectionId());
		}

		_db.runSQL("drop table GrandParentTable");
		_db.runSQL("drop table ParentTable");
		_db.runSQL("drop table ChildTable");
	}

	@Test
	public void testAddClosureCyclesNoParents() throws Exception {
		_testClosureCycles(CTConstants.CT_CHANGE_TYPE_ADDITION, false);
	}

	@Test
	public void testAddClosureCyclesParents() throws Exception {
		_testClosureCycles(CTConstants.CT_CHANGE_TYPE_ADDITION, true);
	}

	@Test
	public void testAddClosureNoCyclesNoParents() throws Exception {
		_testClosureNoCycles(CTConstants.CT_CHANGE_TYPE_ADDITION, false);
	}

	@Test
	public void testAddClosureNoCyclesParents() throws Exception {
		_testClosureNoCycles(CTConstants.CT_CHANGE_TYPE_ADDITION, true);
	}

	@Test
	public void testDeleteClosureCyclesNoParents() throws Exception {
		_testClosureCycles(CTConstants.CT_CHANGE_TYPE_DELETION, false);
	}

	@Test
	public void testDeleteClosureCyclesParents() throws Exception {
		_testClosureCycles(CTConstants.CT_CHANGE_TYPE_DELETION, true);
	}

	@Test
	public void testDeleteClosureNoCyclesNoParents() throws Exception {
		_testClosureNoCycles(CTConstants.CT_CHANGE_TYPE_DELETION, false);
	}

	@Test
	public void testDeleteClosureNoCyclesParents() throws Exception {
		_testClosureNoCycles(CTConstants.CT_CHANGE_TYPE_DELETION, true);
	}

	@Test
	public void testEmptyClosure() {
		CTClosure ctClosure = _ctClosureFactory.create(
			_ctCollection.getCtCollectionId());

		Map<Long, List<Long>> rootPKsMap = ctClosure.getRootPKsMap();

		Assert.assertTrue(rootPKsMap.toString(), rootPKsMap.isEmpty());
	}

	@Test
	public void testModifyClosureCyclesNoParents() throws Exception {
		_testClosureCycles(CTConstants.CT_CHANGE_TYPE_MODIFICATION, false);
	}

	@Test
	public void testModifyClosureCyclesParents() throws Exception {
		_testClosureCycles(CTConstants.CT_CHANGE_TYPE_MODIFICATION, true);
	}

	@Test
	public void testModifyClosureNoCyclesNoParents() throws Exception {
		_testClosureNoCycles(CTConstants.CT_CHANGE_TYPE_MODIFICATION, false);
	}

	@Test
	public void testModifyClosureNoCyclesParents() throws Exception {
		_testClosureNoCycles(CTConstants.CT_CHANGE_TYPE_MODIFICATION, true);
	}

	private void _addCTEntry(CTModel<?> ctModel, int changeType)
		throws Exception {

		_ctEntryLocalService.addCTEntry(
			_ctCollection.getCtCollectionId(),
			_classNameLocalService.getClassNameId(ctModel.getModelClass()),
			ctModel, TestPropsValues.getUserId(), changeType);
	}

	private void _assertMapContent(
		Map<Long, List<Long>> expectedMap, Map<Long, List<Long>> actualMap) {

		for (Map.Entry<Long, List<Long>> entry : actualMap.entrySet()) {
			List<Long> primaryKeys = entry.getValue();

			primaryKeys.sort(null);
		}

		Assert.assertEquals(expectedMap, actualMap);
	}

	private void _testClosureCycles(int changeType, boolean addParents)
		throws Exception {

		_db.runSQL(
			"insert into GrandParentTable (grandParentId, " +
				"parentGrandParentId) values (1, 2)");
		_db.runSQL(
			"insert into GrandParentTable (grandParentId, " +
				"parentGrandParentId) values (2, 1)");
		_db.runSQL(
			"insert into GrandParentTable (grandParentId, " +
				"parentGrandParentId) values (3, 1)");

		if (addParents) {
			_db.runSQL(
				"insert into ParentTable (parentId, ctCollectionId, " +
					"grandParentId, name) values (11, 0, 0, 'p1')");

			_db.runSQL(
				"insert into ParentTable (parentId, ctCollectionId, " +
					"grandParentId, name) values (12, 0, 2, 'p2')");

			_db.runSQL(
				"insert into ParentTable (parentId, ctCollectionId, " +
					"grandParentId, name) values (13, 0, 2, 'p3')");

			if (changeType != CTConstants.CT_CHANGE_TYPE_ADDITION) {
				_db.runSQL(
					"insert into ParentTable (parentId, ctCollectionId, " +
						"grandParentId, name) values (14, 0, 3, 'p4')");
			}

			if (changeType != CTConstants.CT_CHANGE_TYPE_DELETION) {
				_db.runSQL(
					StringBundler.concat(
						"insert into ParentTable (parentId, ctCollectionId, ",
						"grandParentId, name) values (14, ",
						_ctCollection.getCtCollectionId(), ", 3, 'p4')"));
			}

			_addCTEntry(new ParentImpl(14), changeType);
		}

		_db.runSQL(
			"insert into ChildTable (childId, ctCollectionId, grandParentId, " +
				"parentChildId, parentName) values (21, 0, 2, 0, 'p1')");

		if (changeType != CTConstants.CT_CHANGE_TYPE_ADDITION) {
			_db.runSQL(
				StringBundler.concat(
					"insert into ChildTable (childId, ctCollectionId, ",
					"grandParentId, parentChildId, parentName) values (22, 0, ",
					"2, 21, 'p2')"));
		}

		if (changeType != CTConstants.CT_CHANGE_TYPE_DELETION) {
			_db.runSQL(
				StringBundler.concat(
					"insert into ChildTable (childId, ctCollectionId, ",
					"grandParentId, parentChildId, parentName) values (22, ",
					_ctCollection.getCtCollectionId(), ", 2, 21, 'p2')"));
		}

		_addCTEntry(new ChildImpl(22), changeType);

		_db.runSQL(
			"insert into ChildTable (childId, ctCollectionId, grandParentId, " +
				"parentChildId, parentName) values (23, 0, 2, 21, 'p3')");

		CTClosure ctClosure = _ctClosureFactory.create(
			_ctCollection.getCtCollectionId());

		long grandParentClassNameId = _classNameLocalService.getClassNameId(
			GrandParent.class);

		Assert.assertEquals(
			Collections.singletonMap(
				grandParentClassNameId, Collections.singletonList(1L)),
			ctClosure.getRootPKsMap());

		long childClassNameId = _classNameLocalService.getClassNameId(
			Child.class);

		if (addParents) {
			_assertMapContent(
				Collections.singletonMap(
					grandParentClassNameId, Arrays.asList(2L, 3L)),
				ctClosure.getChildPKsMap(grandParentClassNameId, 1L));

			long parentClassNameId = _classNameLocalService.getClassNameId(
				Parent.class);

			Assert.assertEquals(
				HashMapBuilder.put(
					childClassNameId, Collections.singletonList(21L)
				).put(
					parentClassNameId, Collections.singletonList(12L)
				).build(),
				ctClosure.getChildPKsMap(grandParentClassNameId, 2L));

			Assert.assertEquals(
				Collections.singletonMap(
					parentClassNameId, Collections.singletonList(14L)),
				ctClosure.getChildPKsMap(grandParentClassNameId, 3L));

			Assert.assertEquals(
				Collections.singletonMap(
					childClassNameId, Collections.singletonList(22L)),
				ctClosure.getChildPKsMap(parentClassNameId, 12L));

			Assert.assertEquals(
				Collections.emptyMap(),
				ctClosure.getChildPKsMap(parentClassNameId, 14L));
		}
		else {
			Assert.assertEquals(
				Collections.singletonMap(
					grandParentClassNameId, Collections.singletonList(2L)),
				ctClosure.getChildPKsMap(grandParentClassNameId, 1L));

			Assert.assertEquals(
				Collections.singletonMap(
					childClassNameId, Collections.singletonList(21L)),
				ctClosure.getChildPKsMap(grandParentClassNameId, 2L));
		}

		Assert.assertEquals(
			Collections.singletonMap(
				childClassNameId, Collections.singletonList(22L)),
			ctClosure.getChildPKsMap(childClassNameId, 21L));

		Assert.assertEquals(
			Collections.emptyMap(),
			ctClosure.getChildPKsMap(childClassNameId, 22L));
	}

	private void _testClosureNoCycles(int changeType, boolean addParents)
		throws Exception {

		_db.runSQL(
			"insert into GrandParentTable (grandParentId, " +
				"parentGrandParentId) values (1, 0)");
		_db.runSQL(
			"insert into GrandParentTable (grandParentId, " +
				"parentGrandParentId) values (2, 1)");
		_db.runSQL(
			"insert into GrandParentTable (grandParentId, " +
				"parentGrandParentId) values (3, 1)");

		if (addParents) {
			_db.runSQL(
				"insert into ParentTable (parentId, ctCollectionId, " +
					"grandParentId, name) values (11, 0, 0, 'p1')");

			_db.runSQL(
				"insert into ParentTable (parentId, ctCollectionId, " +
					"grandParentId, name) values (12, 0, 2, 'p2')");

			_db.runSQL(
				"insert into ParentTable (parentId, ctCollectionId, " +
					"grandParentId, name) values (13, 0, 2, 'p3')");

			if (changeType != CTConstants.CT_CHANGE_TYPE_ADDITION) {
				_db.runSQL(
					"insert into ParentTable (parentId, ctCollectionId, " +
						"grandParentId, name) values (14, 0, 3, 'p4')");
			}

			if (changeType != CTConstants.CT_CHANGE_TYPE_DELETION) {
				_db.runSQL(
					StringBundler.concat(
						"insert into ParentTable (parentId, ctCollectionId, ",
						"grandParentId, name) values (14, ",
						_ctCollection.getCtCollectionId(), ", 3, 'p4')"));
			}

			_addCTEntry(new ParentImpl(14), changeType);
		}

		_db.runSQL(
			"insert into ChildTable (childId, ctCollectionId, grandParentId, " +
				"parentChildId, parentName) values (21, 0, 2, 0, 'p2')");

		if (changeType != CTConstants.CT_CHANGE_TYPE_ADDITION) {
			_db.runSQL(
				StringBundler.concat(
					"insert into ChildTable (childId, ctCollectionId, ",
					"grandParentId, parentChildId, parentName) values (22, 0, ",
					"2, 21, 'p2')"));
		}

		if (changeType != CTConstants.CT_CHANGE_TYPE_DELETION) {
			_db.runSQL(
				StringBundler.concat(
					"insert into ChildTable (childId, ctCollectionId, ",
					"grandParentId, parentChildId, parentName) values (22, ",
					_ctCollection.getCtCollectionId(), ", 2, 21, 'p2')"));
		}

		_addCTEntry(new ChildImpl(22), changeType);

		_db.runSQL(
			"insert into ChildTable (childId, ctCollectionId, grandParentId, " +
				"parentChildId, parentName) values (23, 0, 2, 21, 'p3')");

		CTClosure ctClosure = _ctClosureFactory.create(
			_ctCollection.getCtCollectionId());

		long grandParentClassNameId = _classNameLocalService.getClassNameId(
			GrandParent.class);

		Assert.assertEquals(
			Collections.singletonMap(
				grandParentClassNameId, Collections.singletonList(1L)),
			ctClosure.getRootPKsMap());

		long childClassNameId = _classNameLocalService.getClassNameId(
			Child.class);

		if (addParents) {
			_assertMapContent(
				Collections.singletonMap(
					grandParentClassNameId, Arrays.asList(2L, 3L)),
				ctClosure.getChildPKsMap(grandParentClassNameId, 1L));

			long parentClassNameId = _classNameLocalService.getClassNameId(
				Parent.class);

			Assert.assertEquals(
				Collections.singletonMap(
					parentClassNameId, Collections.singletonList(12L)),
				ctClosure.getChildPKsMap(grandParentClassNameId, 2L));

			Assert.assertEquals(
				Collections.singletonMap(
					parentClassNameId, Collections.singletonList(14L)),
				ctClosure.getChildPKsMap(grandParentClassNameId, 3L));

			Assert.assertEquals(
				Collections.singletonMap(
					childClassNameId, Collections.singletonList(21L)),
				ctClosure.getChildPKsMap(parentClassNameId, 12L));

			Assert.assertEquals(
				Collections.emptyMap(),
				ctClosure.getChildPKsMap(parentClassNameId, 14L));
		}
		else {
			Assert.assertEquals(
				Collections.singletonMap(
					grandParentClassNameId, Collections.singletonList(2L)),
				ctClosure.getChildPKsMap(grandParentClassNameId, 1L));

			Assert.assertEquals(
				Collections.singletonMap(
					childClassNameId, Collections.singletonList(21L)),
				ctClosure.getChildPKsMap(grandParentClassNameId, 2L));
		}

		Assert.assertEquals(
			Collections.singletonMap(
				childClassNameId, Collections.singletonList(22L)),
			ctClosure.getChildPKsMap(childClassNameId, 21L));

		Assert.assertEquals(
			Collections.emptyMap(),
			ctClosure.getChildPKsMap(childClassNameId, 22L));
	}

	@Inject
	private static CTClosureFactory _ctClosureFactory;

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private static CTEntryLocalService _ctEntryLocalService;

	private static ServiceRegistration<?> _serviceRegistration1;
	private static ServiceRegistration<?> _serviceRegistration2;
	private static ServiceRegistration<?> _serviceRegistration3;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	private CTCollection _ctCollection;
	private DB _db;

	private static class ChildTable extends BaseTable<ChildTable> {

		public static final ChildTable INSTANCE = new ChildTable();

		public final Column<ChildTable, Long> childId = createColumn(
			"childId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
		public final Column<ChildTable, Long> ctCollectionId = createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
		public final Column<ChildTable, Long> grandParentId = createColumn(
			"grandParentId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
		public final Column<ChildTable, Long> parentChildId = createColumn(
			"parentChildId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
		public final Column<ChildTable, String> parentName = createColumn(
			"parentName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

		private ChildTable() {
			super("ChildTable", ChildTable::new);
		}

	}

	private static class ChildTableReferenceDefinition
		implements TableReferenceDefinition<ChildTable> {

		@Override
		public void defineTableReferences(
			TableReferenceInfoBuilder<ChildTable> tableReferenceInfoBuilder) {

			tableReferenceInfoBuilder.defineReferenceInnerJoin(
				fromStep -> fromStep.from(
					GrandParentTable.INSTANCE
				).innerJoinON(
					ChildTable.INSTANCE,
					ChildTable.INSTANCE.grandParentId.eq(
						GrandParentTable.INSTANCE.grandParentId)
				));
			tableReferenceInfoBuilder.defineReferenceInnerJoin(
				fromStep -> {
					ChildTable aliasChildTable = ChildTable.INSTANCE.as(
						"aliasChildTable");

					return fromStep.from(
						aliasChildTable
					).innerJoinON(
						ChildTable.INSTANCE,
						ChildTable.INSTANCE.parentChildId.eq(
							aliasChildTable.childId)
					);
				});

			tableReferenceInfoBuilder.defineNonreferenceColumn(
				ChildTable.INSTANCE.parentName);
		}

		@Override
		public BasePersistence<?> getBasePersistence() {
			return _basePersistence;
		}

		@Override
		public ChildTable getTable() {
			return ChildTable.INSTANCE;
		}

		private ChildTableReferenceDefinition(
			BasePersistence<?> basePersistence) {

			_basePersistence = basePersistence;
		}

		private final BasePersistence<?> _basePersistence;

	}

	private static class GrandParentTable extends BaseTable<GrandParentTable> {

		public static final GrandParentTable INSTANCE = new GrandParentTable();

		public final Column<GrandParentTable, Long> grandParentId =
			createColumn(
				"grandParentId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
		public final Column<GrandParentTable, Long> parentGrandParentId =
			createColumn(
				"parentGrandParentId", Long.class, Types.BIGINT,
				Column.FLAG_DEFAULT);

		private GrandParentTable() {
			super("GrandParentTable", GrandParentTable::new);
		}

	}

	private static class GrandParentTableReferenceDefinition
		implements TableReferenceDefinition<GrandParentTable> {

		@Override
		public void defineTableReferences(
			TableReferenceInfoBuilder<GrandParentTable>
				tableReferenceInfoBuilder) {

			tableReferenceInfoBuilder.defineReferenceInnerJoin(
				fromStep -> {
					GrandParentTable aliasGrandParentTable =
						GrandParentTable.INSTANCE.as("aliasGrandParentTable");

					return fromStep.from(
						GrandParentTable.INSTANCE
					).innerJoinON(
						aliasGrandParentTable,
						aliasGrandParentTable.parentGrandParentId.eq(
							GrandParentTable.INSTANCE.grandParentId)
					);
				});
			tableReferenceInfoBuilder.defineReferenceInnerJoin(
				fromStep -> fromStep.from(
					ChildTable.INSTANCE
				).innerJoinON(
					GrandParentTable.INSTANCE,
					GrandParentTable.INSTANCE.grandParentId.eq(
						ChildTable.INSTANCE.grandParentId)
				));
		}

		@Override
		public BasePersistence<?> getBasePersistence() {
			return _basePersistence;
		}

		@Override
		public GrandParentTable getTable() {
			return GrandParentTable.INSTANCE;
		}

		private GrandParentTableReferenceDefinition(
			BasePersistence<?> basePersistence) {

			_basePersistence = basePersistence;
		}

		private final BasePersistence<?> _basePersistence;

	}

	private static class ParentTable extends BaseTable<ParentTable> {

		public static final ParentTable INSTANCE = new ParentTable();

		public final Column<ParentTable, Long> ctCollectionId = createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
		public final Column<ParentTable, Long> grandParentId = createColumn(
			"grandParentId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
		public final Column<ParentTable, String> name = createColumn(
			"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
		public final Column<ParentTable, Long> parentId = createColumn(
			"parentId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);

		private ParentTable() {
			super("ParentTable", ParentTable::new);
		}

	}

	private static class ParentTableReferenceDefinition
		implements TableReferenceDefinition<ParentTable> {

		@Override
		public void defineTableReferences(
			TableReferenceInfoBuilder<ParentTable> tableReferenceInfoBuilder) {

			tableReferenceInfoBuilder.defineReferenceInnerJoin(
				fromStep -> fromStep.from(
					GrandParentTable.INSTANCE
				).innerJoinON(
					ParentTable.INSTANCE,
					ParentTable.INSTANCE.grandParentId.eq(
						GrandParentTable.INSTANCE.grandParentId)
				));
			tableReferenceInfoBuilder.defineReferenceInnerJoin(
				fromStep -> fromStep.from(
					ChildTable.INSTANCE
				).innerJoinON(
					ParentTable.INSTANCE,
					ParentTable.INSTANCE.grandParentId.eq(
						ChildTable.INSTANCE.grandParentId
					).and(
						ParentTable.INSTANCE.name.eq(
							ChildTable.INSTANCE.parentName)
					)
				));
		}

		@Override
		public BasePersistence<?> getBasePersistence() {
			return _basePersistence;
		}

		@Override
		public ParentTable getTable() {
			return ParentTable.INSTANCE;
		}

		private ParentTableReferenceDefinition(
			BasePersistence<?> basePersistence) {

			_basePersistence = basePersistence;
		}

		private final BasePersistence<?> _basePersistence;

	}

	private interface Child extends CTModel<Child> {
	}

	private class ChildImpl extends TestModelImpl<Child> implements Child {

		@Override
		public Class<?> getModelClass() {
			return Child.class;
		}

		private ChildImpl(long referenceId) {
			super(referenceId);
		}

	}

	private interface GrandParent extends BaseModel<GrandParent> {
	}

	private interface Parent extends CTModel<Parent> {
	}

	private class ParentImpl extends TestModelImpl<Parent> implements Parent {

		@Override
		public Class<?> getModelClass() {
			return Parent.class;
		}

		private ParentImpl(long parentId) {
			super(parentId);
		}

	}

	private abstract class TestModelImpl<T extends CTModel<T>>
		extends BaseModelImpl<T> implements CTModel<T> {

		@Override
		public Object clone() {
			throw new UnsupportedOperationException();
		}

		@Override
		public int compareTo(T o) {
			throw new UnsupportedOperationException();
		}

		@Override
		public long getCtCollectionId() {
			return _ctCollection.getCtCollectionId();
		}

		@Override
		public String getModelClassName() {
			Class<?> clazz = getModelClass();

			return clazz.getName();
		}

		@Override
		public long getMvccVersion() {
			return 0;
		}

		@Override
		public long getPrimaryKey() {
			return _primaryKey;
		}

		@Override
		public Serializable getPrimaryKeyObj() {
			return _primaryKey;
		}

		@Override
		public boolean isEntityCacheEnabled() {
			return false;
		}

		@Override
		public boolean isFinderCacheEnabled() {
			return false;
		}

		@Override
		public void setCtCollectionId(long ctCollectionId) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
		}

		@Override
		public void setPrimaryKey(long primaryKey) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setPrimaryKeyObj(Serializable primaryKeyObj) {
			throw new UnsupportedOperationException();
		}

		@Override
		public String toXmlString() {
			throw new UnsupportedOperationException();
		}

		private TestModelImpl(long primaryKey) {
			_primaryKey = primaryKey;
		}

		private final long _primaryKey;

	}

}