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

package com.liferay.change.tracking.upgrade.v2_4_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import java.sql.Connection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Samuel Trong Tran
 */
@RunWith(Arquillian.class)
public class UpgradeCTSchemaVersionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_ctCollection = _ctCollectionLocalService.addCTCollection(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			UpgradeCTSchemaVersionTest.class.getSimpleName(), null);

		_ctPreferences = _ctPreferencesLocalService.getCTPreferences(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		_ctPreferences.setCtCollectionId(_ctCollection.getCtCollectionId());
		_ctPreferences.setPreviousCtCollectionId(
			_ctCollection.getCtCollectionId());

		_ctPreferences = _ctPreferencesLocalService.updateCTPreferences(
			_ctPreferences);

		_upgradeStepRegistrator.register(
			new UpgradeStepRegistrator.Registry() {

				@Override
				public void register(
					String fromSchemaVersionString,
					String toSchemaVersionString, UpgradeStep... upgradeSteps) {

					for (UpgradeStep upgradeStep : upgradeSteps) {
						Class<?> clazz = upgradeStep.getClass();

						String className = clazz.getName();

						if (className.contains(_CLASS_NAME)) {
							_upgradeCTSchemaVersion =
								(UpgradeProcess)upgradeStep;
						}
					}
				}

			});
	}

	@Test
	public void testUpgradeSetsExpiredStatusAndResetsPreferences()
		throws Exception {

		try (Connection connection = DataAccess.getConnection()) {
			DB db = DBManagerUtil.getDB();
			DBInspector dbInspector = new DBInspector(connection);

			if (dbInspector.hasTable("CTSchemaVersion")) {
				db.runSQL("drop table CTSchemaVersion");
			}

			if (dbInspector.hasColumn("CTCollection", "schemaVersionId")) {
				db.runSQL(
					"alter table CTCollection drop column schemaVersionId");
			}
		}

		_upgradeCTSchemaVersion.upgrade();

		CacheRegistryUtil.clear();

		_ctCollection = _ctCollectionLocalService.getCTCollection(
			_ctCollection.getCtCollectionId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_EXPIRED, _ctCollection.getStatus());

		_ctPreferences = _ctPreferencesLocalService.getCTPreferences(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		Assert.assertEquals(
			CTConstants.CT_COLLECTION_ID_PRODUCTION,
			_ctPreferences.getCtCollectionId());
		Assert.assertEquals(
			CTConstants.CT_COLLECTION_ID_PRODUCTION,
			_ctPreferences.getPreviousCtCollectionId());
	}

	private static final String _CLASS_NAME =
		"com.liferay.change.tracking.internal.upgrade.v2_4_0." +
			"UpgradeCTSchemaVersion";

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private static CTPreferencesLocalService _ctPreferencesLocalService;

	@Inject(
		filter = "(&(component.name=com.liferay.change.tracking.internal.upgrade.ChangeTrackingServiceUpgrade))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@DeleteAfterTestRun
	private CTCollection _ctCollection;

	@DeleteAfterTestRun
	private CTPreferences _ctPreferences;

	private UpgradeProcess _upgradeCTSchemaVersion;

}