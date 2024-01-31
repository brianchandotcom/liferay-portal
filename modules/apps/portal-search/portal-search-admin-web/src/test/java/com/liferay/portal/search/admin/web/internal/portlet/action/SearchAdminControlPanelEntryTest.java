/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.admin.web.internal.portlet.action;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.admin.web.internal.portlet.SearchAdminControlPanelEntry;
import com.liferay.portal.search.configuration.ReindexConfiguration;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Gustavo Lima
 */
@FeatureFlags("LPS-183672")
public class SearchAdminControlPanelEntryTest {

	@ClassRule
	@Rule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_searchAdminControlPanelEntry = new SearchAdminControlPanelEntry();

		ReflectionTestUtil.setFieldValue(
			_searchAdminControlPanelEntry, "_reindexConfiguration",
			_reindexConfiguration);
	}

	@Test
	public void testIndexActionsVirtualInstanceEnabled() throws Exception {
		_setUpPermissionChecker(false, true);
		_setUpReindexConfiguration(RandomTestUtil.randomString(), true);

		Assert.assertTrue(
			_searchAdminControlPanelEntry.hasAccessPermission(
				_permissionChecker, _group, _setUpPortlet()));
	}

	@Test
	public void testIndexActionsVirtualInstancesDisableForTheGivenCompany()
		throws Exception {

		_setUpGroup(RandomTestUtil.randomLong());
		_setUpPermissionChecker(false, true);
		_setUpReindexConfiguration(RandomTestUtil.randomString(), false);

		Portlet portlet = Mockito.mock(Portlet.class);

		Assert.assertFalse(
			_searchAdminControlPanelEntry.hasAccessPermission(
				_permissionChecker, _group, portlet));
	}

	@Test
	public void testIndexActionsVirtualInstancesEnabledForTheGivenCompany()
		throws Exception {

		_setUpPermissionChecker(false, true);

		long companyId = RandomTestUtil.randomLong();

		_setUpGroup(companyId);
		_setUpReindexConfiguration(String.valueOf(companyId), false);

		Assert.assertTrue(
			_searchAdminControlPanelEntry.hasAccessPermission(
				_permissionChecker, _group, _setUpPortlet()));
	}

	@Test
	public void testOmniadminAccessControlPanel() throws Exception {
		_setUpPermissionChecker(true, true);

		Assert.assertTrue(
			_searchAdminControlPanelEntry.hasAccessPermission(
				_permissionChecker, Mockito.mock(Group.class),
				Mockito.mock(Portlet.class)));
	}

	private void _setUpGroup(long companyId) {
		Mockito.doReturn(
			companyId
		).when(
			_group
		).getCompanyId();
	}

	private void _setUpPermissionChecker(
		boolean omniadmin, boolean companyAdmin) {

		Mockito.doReturn(
			omniadmin
		).when(
			_permissionChecker
		).isOmniadmin();

		Mockito.doReturn(
			companyAdmin
		).when(
			_permissionChecker
		).isCompanyAdmin();
	}

	private Portlet _setUpPortlet() {
		Portlet portlet = Mockito.mock(Portlet.class);

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			portlet
		).getControlPanelEntryCategory();

		return portlet;
	}

	private void _setUpReindexConfiguration(
		String companyId, boolean indexActionsInAllVirtualInstancesEnabled) {

		Mockito.doReturn(
			indexActionsInAllVirtualInstancesEnabled
		).when(
			_reindexConfiguration
		).indexActionsInAllVirtualInstancesEnabled();

		Mockito.doReturn(
			new String[] {companyId}
		).when(
			_reindexConfiguration
		).indexActionsVirtualInstance();
	}

	private final Group _group = Mockito.mock(Group.class);
	private final PermissionChecker _permissionChecker = Mockito.mock(
		PermissionChecker.class);
	private final ReindexConfiguration _reindexConfiguration = Mockito.mock(
		ReindexConfiguration.class);
	private SearchAdminControlPanelEntry _searchAdminControlPanelEntry;

}