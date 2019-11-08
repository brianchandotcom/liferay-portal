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

package com.liferay.depot.application.list.test;

import com.liferay.application.list.PanelApp;
import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class DepotPanelAppControllerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser());

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		_depotEntry = _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.<Locale, String>put(
				LocaleUtil.getDefault(), "name"
			).build(),
			new HashMap<>(), ServiceContextTestUtil.getServiceContext());
	}

	@Test
	public void testGetPanelAppsDoesNotShowTheConfigurationCategoryForADepotGroup()
		throws Exception {

		_assertIsHiddenForADepotGroup(
			PanelCategoryKeys.SITE_ADMINISTRATION_CONFIGURATION);
	}

	@Test
	public void testGetPanelAppsDoesNotShowThePeopleCategoryForADepotGroup()
		throws Exception {

		_assertIsHiddenForADepotGroup(
			PanelCategoryKeys.SITE_ADMINISTRATION_MEMBERS);
	}

	@Test
	public void testGetPanelAppsDoesNotShowTheSiteBuilderCategoryForADepotGroup()
		throws Exception {

		_assertIsHiddenForADepotGroup(
			PanelCategoryKeys.SITE_ADMINISTRATION_BUILD);
	}

	@Test
	public void testGetPanelAppsDoesNotShowTheStagingCategoryForADepotGroup()
		throws Exception {

		_assertIsHiddenForADepotGroup(
			PanelCategoryKeys.SITE_ADMINISTRATION_PUBLISHING);
	}

	@Test
	public void testGetPanelAppsShowsAllContentWithSites() throws Exception {
		List<PanelApp> panelApps = _panelAppRegistry.getPanelApps(
			PanelCategoryKeys.SITE_ADMINISTRATION_CONTENT,
			PermissionThreadLocal.getPermissionChecker(),
			_groupLocalService.getGroup(TestPropsValues.getGroupId()));

		Assert.assertTrue(panelApps.size() > 2);
	}

	@Test
	public void testGetPanelAppsShowsOnlyDocumentsAndMediaAndWebContentInTheContentCategoryForADepotGroup()
		throws Exception {

		List<PanelApp> panelApps = _panelAppRegistry.getPanelApps(
			PanelCategoryKeys.SITE_ADMINISTRATION_CONTENT,
			PermissionThreadLocal.getPermissionChecker(),
			_groupLocalService.getGroup(_depotEntry.getGroupId()));

		Assert.assertEquals(panelApps.toString(), 2, panelApps.size());

		_assertPanelAppsContain(
			panelApps, DLPortletKeys.DOCUMENT_LIBRARY_ADMIN);
		_assertPanelAppsContain(panelApps, JournalPortletKeys.JOURNAL);

		panelApps = _panelAppRegistry.getPanelApps(
			PanelCategoryKeys.SITE_ADMINISTRATION_CONTENT,
			PermissionThreadLocal.getPermissionChecker(),
			_groupLocalService.getGroup(TestPropsValues.getGroupId()));

		Assert.assertTrue(panelApps.size() > 1);
	}

	@Test
	public void testGetPanelAppsShowsTheCategorizationCategoryForADepotGroup()
		throws Exception {

		_assertIsDisplayed(
			_depotEntry.getGroupId(),
			PanelCategoryKeys.SITE_ADMINISTRATION_CATEGORIZATION);
	}

	@Test
	public void testGetPanelAppsShowsTheControlPanelCategoryForADepotGroup()
		throws Exception {

		_assertIsDisplayed(
			_depotEntry.getGroupId(), PanelCategoryKeys.CONTROL_PANEL_APPS);
		_assertIsDisplayed(
			_depotEntry.getGroupId(),
			PanelCategoryKeys.CONTROL_PANEL_CONFIGURATION);
		_assertIsDisplayed(
			_depotEntry.getGroupId(), PanelCategoryKeys.CONTROL_PANEL_SITES);
		_assertIsDisplayed(
			_depotEntry.getGroupId(), PanelCategoryKeys.CONTROL_PANEL_USERS);
		_assertIsDisplayed(
			_depotEntry.getGroupId(), PanelCategoryKeys.CONTROL_PANEL_WORKFLOW);
	}

	@Test
	public void testGetPanelAppsShowsTheRecycleBinCategoryForADepotGroup()
		throws Exception {

		_assertIsDisplayed(
			_depotEntry.getGroupId(),
			PanelCategoryKeys.SITE_ADMINISTRATION_RECYCLE_BIN);
	}

	private void _assertIsDisplayed(long groupId, String parentPanelCategoryKey)
		throws PortalException {

		List<PanelApp> panelApps = _panelAppRegistry.getPanelApps(
			parentPanelCategoryKey,
			PermissionThreadLocal.getPermissionChecker(),
			_groupLocalService.getGroup(groupId));

		Assert.assertFalse(panelApps.isEmpty());
	}

	private void _assertIsHiddenForADepotGroup(String parentPanelCategoryKey)
		throws PortalException {

		List<PanelApp> panelApps = _panelAppRegistry.getPanelApps(
			parentPanelCategoryKey,
			PermissionThreadLocal.getPermissionChecker(),
			_groupLocalService.getGroup(_depotEntry.getGroupId()));

		Assert.assertTrue(panelApps.isEmpty());

		_assertIsDisplayed(
			TestPropsValues.getGroupId(), parentPanelCategoryKey);
	}

	private void _assertPanelAppsContain(
		List<PanelApp> panelApps, String portletId) {

		Stream<PanelApp> stream = panelApps.stream();

		stream.filter(
			panelApp -> Objects.equals(portletId, panelApp.getPortletId())
		).findFirst(
		).orElseThrow(
			() -> new AssertionError(
				"Panel apps do not contain portlet " + portletId)
		);
	}

	@DeleteAfterTestRun
	private DepotEntry _depotEntry;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private PanelAppRegistry _panelAppRegistry;

}