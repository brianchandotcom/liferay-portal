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

package com.liferay.journal.web.internal.upgrade.v1_1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import java.util.Objects;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class MenuFavItemPortalPreferenceValuesUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testUpgradeProcess() throws Exception {
		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		Group companyGroup = company.getGroup();

		_companyGroupDDMStructure = DDMStructureTestUtil.addStructure(
			companyGroup.getGroupId(), JournalArticle.class.getName());

		_addMenuFavItemPortalPreferences(
			0, companyGroup, _companyGroupDDMStructure);

		_group1 = GroupTestUtil.addGroup();

		DDMStructure group1DDMStructure = DDMStructureTestUtil.addStructure(
			_group1.getGroupId(), JournalArticle.class.getName());

		_addMenuFavItemPortalPreferences(
			0, _group1, _companyGroupDDMStructure, group1DDMStructure);

		_group2 = GroupTestUtil.addGroup();

		DDMStructure group2DDMStructure = DDMStructureTestUtil.addStructure(
			_group2.getGroupId(), JournalArticle.class.getName());

		_addMenuFavItemPortalPreferences(
			0, _group2, _companyGroupDDMStructure, group2DDMStructure);

		_addMenuFavItemPortalPreferences(1, _group2, group2DDMStructure);

		_runUpgrade();

		_assertMenuFavItemPortalPreferences(
			0, companyGroup, _companyGroupDDMStructure);

		_assertMenuFavItemPortalPreferences(
			0, _group1, _companyGroupDDMStructure, group1DDMStructure);

		_assertMenuFavItemPortalPreferences(
			0, _group2, _companyGroupDDMStructure, group2DDMStructure);

		_assertMenuFavItemPortalPreferences(1, _group2, group2DDMStructure);
	}

	private void _addMenuFavItemPortalPreferences(
			long folderId, Group group, DDMStructure... ddmStructures)
		throws Exception {

		String key = "journal-add-menu-fav-items-" + group.getGroupId();

		if (folderId > 0) {
			key = key + StringPool.DASH + folderId;
		}

		PortalPreferences portalPreferences =
			_portletPreferencesFactory.getPortalPreferences(
				null, TestPropsValues.getUserId(), true);

		String[] addMenuFavItems = portalPreferences.getValues(
			JournalPortletKeys.JOURNAL, key, new String[0]);

		for (DDMStructure ddmStructure : ddmStructures) {
			addMenuFavItems = ArrayUtil.append(
				addMenuFavItems, ddmStructure.getStructureKey());
		}

		portalPreferences.setValues(
			JournalPortletKeys.JOURNAL, key, addMenuFavItems);
	}

	private void _assertMenuFavItemPortalPreferences(
			long folderId, Group group, DDMStructure... ddmStructures)
		throws Exception {

		String key = "journal-add-menu-fav-items-" + group.getGroupId();

		if (folderId > 0) {
			key = key + StringPool.DASH + folderId;
		}

		PortalPreferences portalPreferences =
			_portletPreferencesFactory.getPortalPreferences(
				null, TestPropsValues.getUserId(), true);

		String[] addMenuFavItems = portalPreferences.getValues(
			JournalPortletKeys.JOURNAL, key, new String[0]);

		for (DDMStructure ddmStructure : ddmStructures) {
			Assert.assertTrue(
				ArrayUtil.contains(
					addMenuFavItems,
					String.valueOf(ddmStructure.getStructureId())));
		}
	}

	private UpgradeProcess _getUpgradeProcess() {
		UpgradeProcess[] upgradeProcesses = new UpgradeProcess[1];

		_upgradeStepRegistrator.register(
			(fromSchemaVersionString, toSchemaVersionString, upgradeSteps) -> {
				for (UpgradeStep upgradeStep : upgradeSteps) {
					Class<? extends UpgradeStep> clazz = upgradeStep.getClass();

					if (Objects.equals(clazz.getName(), _CLASS_NAME)) {
						upgradeProcesses[0] = (UpgradeProcess)upgradeStep;

						break;
					}
				}
			});

		return upgradeProcesses[0];
	}

	private void _runUpgrade() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = _getUpgradeProcess();

			upgradeProcess.upgrade();

			_multiVMPool.clear();
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.journal.web.internal.upgrade.v1_1_0." +
			"MenuFavItemPortalPreferenceValuesUpgradeProcess";

	@Inject(
		filter = "(&(component.name=com.liferay.journal.web.internal.upgrade.registry.JournalWebUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@DeleteAfterTestRun
	private DDMStructure _companyGroupDDMStructure;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group1;

	@DeleteAfterTestRun
	private Group _group2;

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject
	private PortletPreferencesFactory _portletPreferencesFactory;

}