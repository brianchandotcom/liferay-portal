/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.db.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.sample.service.CTSChildLocalService;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTCollectionService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gislayne Vitorino
 */
@RunWith(Arquillian.class)
public class SQLServerDBCTTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_db = DBManagerUtil.getDB();

		_ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, SQLServerDBCTTest.class.getSimpleName(),
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

		_db.runSQL("truncate table CTSChild");
		_db.runSQL("truncate table CTSGrandParent");
		_db.runSQL("truncate table CTSParent");
	}

	@Test
	public void testDeleteCTCollectionWithOver50000Entries() throws Exception {
		_db.runSQL(
			"insert into CTSGrandParent (ctsGrandParentId, " +
				"parentCTSGrandParentId) values (1, 0)");

		_db.runSQL(
			"insert into CTSParent (ctCollectionId, ctsParentId, " +
				"ctsGrandParentId, name) values (0, 11, 1, 'p1')");

		for (int i = 0; i <= _BATCH_SIZE; i++) {
			_db.runSQL(
				StringBundler.concat(
					"insert into CTSChild (ctCollectionId, ctsChildId, ",
					"ctsGrandParentId, parentCTSChildId, ctsParentName) ",
					"values (", _ctCollection.getCtCollectionId(), ", ", i,
					", 1, 0, 'p1')"));

			_addCTEntry(_ctsChildLocalService.createCTSChild(i));
		}

		_ctCollectionLocalService.deleteCTCollection(_ctCollection);

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select * from CTSChild where ctCollectionId = " +
					_ctCollection.getCtCollectionId());
			ResultSet resultSet = preparedStatement.executeQuery()) {

			Assert.assertFalse(resultSet.next());
		}
	}

	@Test
	public void testPublishCTCollectionWithOver50000Entries() throws Exception {
		_db.runSQL(
			"insert into CTSGrandParent (ctsGrandParentId, " +
				"parentCTSGrandParentId) values (1, 0)");

		_db.runSQL(
			"insert into CTSParent (ctCollectionId, ctsParentId, " +
				"ctsGrandParentId, name) values (0, 11, 1, 'p1')");

		for (int i = 0; i <= _BATCH_SIZE; i++) {
			_db.runSQL(
				StringBundler.concat(
					"insert into CTSChild (ctCollectionId, ctsChildId, ",
					"ctsGrandParentId, parentCTSChildId, ctsParentName) ",
					"values (", _ctCollection.getCtCollectionId(), ", ", i,
					", 1, 0, 'p1')"));

			_addCTEntry(_ctsChildLocalService.createCTSChild(i));
		}

		_ctCollectionService.publishCTCollection(
			TestPropsValues.getUserId(), _ctCollection.getCtCollectionId());

		_ctCollection = _ctCollectionLocalService.getCTCollection(
			_ctCollection.getCtCollectionId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, _ctCollection.getStatus());
	}

	private void _addCTEntry(CTModel<?> ctModel) throws Exception {
		_ctEntryLocalService.addCTEntry(
			null, _ctCollection.getCtCollectionId(),
			_classNameLocalService.getClassNameId(ctModel.getModelClass()),
			ctModel, TestPropsValues.getUserId(),
			CTConstants.CT_CHANGE_TYPE_ADDITION);
	}

	private static final int _BATCH_SIZE = 50001;

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private static CTCollectionService _ctCollectionService;

	@Inject
	private static CTEntryLocalService _ctEntryLocalService;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	private CTCollection _ctCollection;

	@Inject
	private CTSChildLocalService _ctsChildLocalService;

	private DB _db;

}