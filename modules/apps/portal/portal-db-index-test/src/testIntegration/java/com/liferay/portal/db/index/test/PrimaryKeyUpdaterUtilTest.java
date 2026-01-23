/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.index.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.test.util.ObjectRelationshipTestUtil;
import com.liferay.portal.db.DBResourceProvider;
import com.liferay.portal.db.index.PrimaryKeyUpdaterUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.BundleTracker;

/**
 * @author Mariano Álvaro Sáiz
 */
@RunWith(Arquillian.class)
public class PrimaryKeyUpdaterUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			PrimaryKeyUpdaterUtilTest.class);

		CountDownLatch countDownLatch = new CountDownLatch(1);

		BundleTracker<Bundle> bundleTracker = new BundleTracker<Bundle>(
			bundle.getBundleContext(), Bundle.ACTIVE, null) {

			@Override
			public Bundle addingBundle(Bundle bundle, BundleEvent event) {
				String symbolicName = bundle.getSymbolicName();

				if (symbolicName.equals("com.liferay.portal.lock.service")) {
					countDownLatch.countDown();

					close();
				}

				return null;
			}

		};

		bundleTracker.open();

		countDownLatch.await(10, TimeUnit.SECONDS);

		_objectDefinition1 = ObjectDefinitionTestUtil.publishObjectDefinition();
		_objectDefinition2 = ObjectDefinitionTestUtil.publishObjectDefinition();

		_objectRelationship = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectRelationshipLocalService, _objectDefinition1,
			_objectDefinition2,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			StringUtil.randomId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		if (_objectRelationship != null) {
			_objectRelationshipLocalService.deleteObjectRelationship(
				_objectRelationship.getObjectRelationshipId());
		}

		if (_objectDefinition1 != null) {
			_objectDefinitionLocalService.deleteObjectDefinition(
				_objectDefinition1.getObjectDefinitionId());
		}

		if (_objectDefinition2 != null) {
			_objectDefinitionLocalService.deleteObjectDefinition(
				_objectDefinition2.getObjectDefinitionId());
		}
	}

	@Test
	public void testUpdateAllPrimaryKeys() throws Exception {
		_testUpdateAllPrimaryKeys();

		try (AutoCloseable autoCloseable1 =
				ReflectionTestUtil.setFieldValueWithAutoCloseable(
					_dbResourceProvider, "_objectDefinitionLocalService", null);
			AutoCloseable autoCloseable2 =
				ReflectionTestUtil.setFieldValueWithAutoCloseable(
					_dbResourceProvider, "_objectFieldLocalService", null);
			AutoCloseable autoCloseable3 =
				ReflectionTestUtil.setFieldValueWithAutoCloseable(
					_dbResourceProvider, "_objectRelationshipLocalService",
					null)) {

			_testUpdateAllPrimaryKeys();
		}
	}

	private void _testUpdateAllPrimaryKeys() throws Exception {
		DB db = DBManagerUtil.getDB();

		try (Connection connection = DataAccess.getConnection()) {
			db.removePrimaryKey(connection, "Counter");
			db.removePrimaryKey(connection, "Lock_");
			db.removePrimaryKey(
				connection, _objectDefinition1.getDBTableName());
			db.removePrimaryKey(
				connection, _objectDefinition2.getDBTableName());
			db.removePrimaryKey(
				connection, _objectRelationship.getDBTableName());

			PrimaryKeyUpdaterUtil.updateAllPrimaryKeys();

			Assert.assertTrue(
				ArrayUtil.isNotEmpty(
					db.getPrimaryKeyColumnNames(connection, "Counter")));
			Assert.assertTrue(
				ArrayUtil.isNotEmpty(
					db.getPrimaryKeyColumnNames(connection, "Lock_")));
			Assert.assertTrue(
				ArrayUtil.isNotEmpty(
					db.getPrimaryKeyColumnNames(
						connection, _objectDefinition1.getDBTableName())));
			Assert.assertTrue(
				ArrayUtil.isNotEmpty(
					db.getPrimaryKeyColumnNames(
						connection, _objectDefinition2.getDBTableName())));
			Assert.assertTrue(
				ArrayUtil.isNotEmpty(
					db.getPrimaryKeyColumnNames(
						connection, _objectRelationship.getDBTableName())));
		}
	}

	private static ObjectDefinition _objectDefinition1;
	private static ObjectDefinition _objectDefinition2;

	@Inject
	private static ObjectDefinitionLocalService _objectDefinitionLocalService;

	private static ObjectRelationship _objectRelationship;

	@Inject
	private static ObjectRelationshipLocalService
		_objectRelationshipLocalService;

	@Inject(
		filter = "(&(component.name=com.liferay.object.internal.db.ObjectDBResourceProvider))"
	)
	private DBResourceProvider _dbResourceProvider;

}