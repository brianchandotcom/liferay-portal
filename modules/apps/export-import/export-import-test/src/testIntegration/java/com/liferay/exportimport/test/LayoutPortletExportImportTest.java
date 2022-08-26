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

package com.liferay.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.test.util.lar.BaseExportImportTestCase;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.PortletConfigurationListener;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.documentlibrary.constants.DLConstants;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Noor Najjar
 */
@RunWith(Arquillian.class)
public class LayoutPortletExportImportTest extends BaseExportImportTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		super.setUp();

		UserTestUtil.setUser(TestPropsValues.getUser());

		_companyId = TestPropsValues.getCompanyId();

		_globalGroup = GroupLocalServiceUtil.getCompanyGroup(_companyId);

		_ownerRole = RoleLocalServiceUtil.getRole(
			_companyId, RoleConstants.OWNER);
	}

	@Test
	public void testGloballyScopedPortletExportImportDoesNotOverrideGlobalSitePermissions()
		throws Exception {

		_addPortletToLayoutWithGlobalScope(layout);

		ResourcePermissionLocalServiceUtil.addModelResourcePermissions(
			_companyId, group.getGroupId(), TestPropsValues.getUserId(),
			DLConstants.RESOURCE_NAME, String.valueOf(group.getGroupId()), null,
			null);

		_removeOwnerPermissionsFromDLHomeFolderPermissionsInGlobalSite();

		exportLayouts(
			new long[] {layout.getLayoutId()},
			LinkedHashMapBuilder.putAll(
				getExportParameterMap()
			).put(
				PortletDataHandlerKeys.PERMISSIONS,
				new String[] {Boolean.TRUE.toString()}
			).build());

		importLayouts(
			LinkedHashMapBuilder.putAll(
				getImportParameterMap()
			).put(
				PortletDataHandlerKeys.PERMISSIONS,
				new String[] {Boolean.TRUE.toString()}
			).build());

		_resourcePermission =
			ResourcePermissionLocalServiceUtil.getResourcePermission(
				_companyId, DLConstants.RESOURCE_NAME,
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(_globalGroup.getGroupId()),
				_ownerRole.getRoleId());

		Assert.assertEquals(0, _resourcePermission.getActionIds());
		Assert.assertFalse(_resourcePermission.isViewActionId());
	}

	private void _addPortletToLayoutWithGlobalScope(Layout exportLayout)
		throws Exception {

		String portletId = LayoutTestUtil.addPortletToLayout(
			exportLayout, DLPortletKeys.DOCUMENT_LIBRARY);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getLayoutPortletSetup(
				exportLayout, portletId);

		portletPreferences.setValue("lfrScopeType", "company");

		portletPreferences.setValue("lfrScopeLayoutUuid", "");

		String languageId = LanguageUtil.getLanguageId(LocaleUtil.getDefault());

		String portletTitle = portletPreferences.getValue(
			"portletSetupTitle_" + languageId, StringPool.BLANK);

		portletPreferences.setValue(
			"portletSetupTitle_" + languageId,
			PortalUtil.getNewPortletTitle(portletTitle, null, "global"));

		portletPreferences.setValue(
			"portletSetupUseCustomTitle", Boolean.TRUE.toString());

		portletPreferences.store();

		PortletConfigurationListener portletConfigurationListener =
			portlet.getPortletConfigurationListenerInstance();

		if (portletConfigurationListener != null) {
			portletConfigurationListener.onUpdateScope(
				portletId, portletPreferences);
		}
	}

	private void _removeOwnerPermissionsFromDLHomeFolderPermissionsInGlobalSite()
		throws Exception {

		long globalGroupId = _globalGroup.getGroupId();

		ResourcePermissionLocalServiceUtil.addModelResourcePermissions(
			_globalGroup.getCompanyId(), globalGroupId,
			TestPropsValues.getUserId(), DLConstants.RESOURCE_NAME,
			String.valueOf(globalGroupId), null, null);

		_resourcePermission =
			ResourcePermissionLocalServiceUtil.getResourcePermission(
				_globalGroup.getCompanyId(), DLConstants.RESOURCE_NAME,
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(globalGroupId), _ownerRole.getRoleId());

		_resourcePermission.setActionIds(0);
		_resourcePermission.setViewActionId(false);

		ResourcePermissionLocalServiceUtil.updateResourcePermission(
			_resourcePermission);
	}

	private long _companyId;
	private Group _globalGroup;
	private Role _ownerRole;

	@DeleteAfterTestRun
	private ResourcePermission _resourcePermission;

}