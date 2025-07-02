/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.closure.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.closure.CTClosure;
import com.liferay.change.tracking.closure.CTClosureFactory;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.sample.model.Child;
import com.liferay.change.tracking.sample.model.GrandParent;
import com.liferay.change.tracking.sample.model.Parent;
import com.liferay.change.tracking.sample.service.ChildLocalService;
import com.liferay.change.tracking.sample.service.ParentLocalService;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class CTClosureFactoryImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_db = DBManagerUtil.getDB();

		_ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, CTClosureFactoryImplTest.class.getSimpleName(),
			StringPool.BLANK);
	}

	@After
	public void tearDown() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.change.tracking.service.impl." +
					"CTCollectionLocalServiceImpl",
				LoggerTestUtil.WARN)) {

			_ctCollectionLocalService.deleteCTCollection(_ctCollection);
		}

		_db.runSQL("drop table GrandParent");
		_db.runSQL("drop table Parent");
		_db.runSQL("drop table Child");

		_db.runSQL(
			StringBundler.concat(
				"create table GrandParent (mvccVersion LONG default 0 not ",
				"null, grandParentId LONG not null primary key, companyId ",
				"LONG, name VARCHAR(75) null, parentGrandParentId LONG)"));
		_db.runSQL("create index IX_516F7BAC on GrandParent (companyId)");

		_db.runSQL(
			StringBundler.concat(
				"create table Parent (mvccVersion LONG default 0 not null, ",
				"ctCollectionId LONG default 0 not null, parentId LONG not ",
				"null, companyId LONG, name VARCHAR(75) null, grandParentId ",
				"LONG, primary key (parentId, ctCollectionId))"));

		_db.runSQL(
			"create index IX_E0FFE1E9 on Parent (companyId, grandParentId)");

		_db.runSQL(
			StringBundler.concat(
				"create table Child (mvccVersion LONG default 0 not null, ",
				"ctCollectionId LONG default 0 not null, childId LONG not ",
				"null, companyId LONG, name VARCHAR(75) null, grandParentId ",
				"LONG,parentChildId LONG, parentName VARCHAR(75) null, ",
				"primary key (childId, ctCollectionId))"));

		_db.runSQL(
			"create index IX_B9646ED7 on Child (companyId, parentChildId)");
		_db.runSQL(
			"create index IX_7333931B on Child (companyId, grandParentId)");
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
			null, _ctCollection.getCtCollectionId(),
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
			"insert into GrandParent (grandParentId, parentGrandParentId) " +
				"values (1, 2)");
		_db.runSQL(
			"insert into GrandParent (grandParentId, parentGrandParentId) " +
				"values (2, 1)");
		_db.runSQL(
			"insert into GrandParent (grandParentId, parentGrandParentId) " +
				"values (3, 1)");

		if (addParents) {
			_db.runSQL(
				"insert into Parent (parentId, ctCollectionId, " +
					"grandParentId, name) values (11, 0, 0, 'p1')");

			_db.runSQL(
				"insert into Parent (parentId, ctCollectionId, " +
					"grandParentId, name) values (12, 0, 2, 'p2')");

			_db.runSQL(
				"insert into Parent (parentId, ctCollectionId, " +
					"grandParentId, name) values (13, 0, 2, 'p3')");

			if (changeType != CTConstants.CT_CHANGE_TYPE_ADDITION) {
				_db.runSQL(
					"insert into Parent (parentId, ctCollectionId, " +
						"grandParentId, name) values (14, 0, 3, 'p4')");
			}

			if (changeType != CTConstants.CT_CHANGE_TYPE_DELETION) {
				_db.runSQL(
					StringBundler.concat(
						"insert into Parent (parentId, ctCollectionId, ",
						"grandParentId, name) values (14, ",
						_ctCollection.getCtCollectionId(), ", 3, 'p4')"));
			}

			_addCTEntry(_parentLocalService.createParent(14), changeType);
		}

		_db.runSQL(
			"insert into Child (childId, ctCollectionId, grandParentId, " +
				"parentChildId, parentName) values (21, 0, 2, 0, 'p1')");

		if (changeType != CTConstants.CT_CHANGE_TYPE_ADDITION) {
			_db.runSQL(
				StringBundler.concat(
					"insert into Child (childId, ctCollectionId, ",
					"grandParentId, parentChildId, parentName) values (22, 0, ",
					"2, 21, 'p2')"));
		}

		if (changeType != CTConstants.CT_CHANGE_TYPE_DELETION) {
			_db.runSQL(
				StringBundler.concat(
					"insert into Child (childId, ctCollectionId, ",
					"grandParentId, parentChildId, parentName) values (22, ",
					_ctCollection.getCtCollectionId(), ", 2, 21, 'p2')"));
		}

		_addCTEntry(_childLocalService.createChild(22), changeType);

		_db.runSQL(
			"insert into Child (childId, ctCollectionId, grandParentId, " +
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
			"insert into GrandParent (grandParentId, parentGrandParentId) " +
				"values (1, 0)");
		_db.runSQL(
			"insert into GrandParent (grandParentId, parentGrandParentId) " +
				"values (2, 1)");
		_db.runSQL(
			"insert into GrandParent (grandParentId, parentGrandParentId) " +
				"values (3, 1)");

		if (addParents) {
			_db.runSQL(
				"insert into Parent (parentId, ctCollectionId, " +
					"grandParentId, name) values (11, 0, 0, 'p1')");

			_db.runSQL(
				"insert into Parent (parentId, ctCollectionId, " +
					"grandParentId, name) values (12, 0, 2, 'p2')");

			_db.runSQL(
				"insert into Parent (parentId, ctCollectionId, " +
					"grandParentId, name) values (13, 0, 2, 'p3')");

			if (changeType != CTConstants.CT_CHANGE_TYPE_ADDITION) {
				_db.runSQL(
					"insert into Parent (parentId, ctCollectionId, " +
						"grandParentId, name) values (14, 0, 3, 'p4')");
			}

			if (changeType != CTConstants.CT_CHANGE_TYPE_DELETION) {
				_db.runSQL(
					StringBundler.concat(
						"insert into Parent (parentId, ctCollectionId, ",
						"grandParentId, name) values (14, ",
						_ctCollection.getCtCollectionId(), ", 3, 'p4')"));
			}

			_addCTEntry(_parentLocalService.createParent(14), changeType);
		}

		_db.runSQL(
			"insert into Child (childId, ctCollectionId, grandParentId, " +
				"parentChildId, parentName) values (21, 0, 2, 0, 'p2')");

		if (changeType != CTConstants.CT_CHANGE_TYPE_ADDITION) {
			_db.runSQL(
				StringBundler.concat(
					"insert into Child (childId, ctCollectionId, ",
					"grandParentId, parentChildId, parentName) values (22, 0, ",
					"2, 21, 'p2')"));
		}

		if (changeType != CTConstants.CT_CHANGE_TYPE_DELETION) {
			_db.runSQL(
				StringBundler.concat(
					"insert into Child (childId, ctCollectionId, ",
					"grandParentId, parentChildId, parentName) values (22, ",
					_ctCollection.getCtCollectionId(), ", 2, 21, 'p2')"));
		}

		_addCTEntry(_childLocalService.createChild(22), changeType);

		_db.runSQL(
			"insert into Child (childId, ctCollectionId, grandParentId, " +
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

	@Inject
	private ChildLocalService _childLocalService;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	private CTCollection _ctCollection;
	private DB _db;

	@Inject
	private ParentLocalService _parentLocalService;

}