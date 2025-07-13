/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.db.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.sample.service.CTSChildLocalService;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTCollectionService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.test.util.CTSampleTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
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
@DataGuard(scope = DataGuard.Scope.NONE)
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
		CTSampleTestUtil.reset();

		_db = DBManagerUtil.getDB();

		_ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, SQLServerDBCTTest.class.getSimpleName(), StringPool.BLANK);
	}

	@After
	public void tearDown() throws Exception {
		CTSampleTestUtil.reset();

		_ctCollectionLocalService.deleteCTCollection(_ctCollection);
	}

	@Test
	public void testDeleteCTCollectionWithOver50000Entries() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			CTSampleTestUtil.addCTSChild(_BATCH_SIZE);
		}

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_ctCollectionLocalService.deleteCTCollection(_ctCollection);
		}

		try (LoggingTimer loggingTimer = new LoggingTimer();
			 Connection connection = DataAccess.getConnection();

			 PreparedStatement preparedStatement = connection.prepareStatement(
				"select * from CTSChild where ctCollectionId = " +
					_ctCollection.getCtCollectionId());
			ResultSet resultSet = preparedStatement.executeQuery()) {

			Assert.assertFalse(resultSet.next());
		}
	}

	@Test
	public void testPublishCTCollectionWithOver50000Entries() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			CTSampleTestUtil.addCTSChild(_BATCH_SIZE);
		}

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_ctCollectionService.publishCTCollection(
				TestPropsValues.getUserId(), _ctCollection.getCtCollectionId());
		}

		_ctCollection = _ctCollectionLocalService.getCTCollection(
			_ctCollection.getCtCollectionId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, _ctCollection.getStatus());
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