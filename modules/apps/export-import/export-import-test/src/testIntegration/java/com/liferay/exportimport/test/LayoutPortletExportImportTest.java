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
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.documentlibrary.constants.DLConstants;

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

		_initModelResource(
			_companyId, _globalGroup.getGroupId(), DLConstants.RESOURCE_NAME);
		_initModelResource(
			_companyId, group.getGroupId(), DLConstants.RESOURCE_NAME);

		LayoutTestUtil.addPortletToLayout(
			layout, DLPortletKeys.DOCUMENT_LIBRARY,
			HashMapBuilder.put(
				"lfrScopeType", new String[] {"company"}
			).build());

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
			_resourcePermissionLocalService.getResourcePermission(
				_companyId, DLConstants.RESOURCE_NAME,
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(_globalGroup.getGroupId()),
				_ownerRole.getRoleId());

		Assert.assertEquals(0, _resourcePermission.getActionIds());
		Assert.assertFalse(_resourcePermission.isViewActionId());
	}

	private void _initModelResource(long companyId, long groupId, String name)
		throws Exception {

		String primaryKey = String.valueOf(groupId);

		int count = _resourcePermissionLocalService.getResourcePermissionsCount(
			companyId, name, ResourceConstants.SCOPE_INDIVIDUAL, primaryKey);

		if (count > 0) {
			return;
		}

		_resourceLocalService.addResources(
			companyId, groupId, 0, name, primaryKey, false, true, true);
	}

	private void _removeOwnerPermissionsFromDLHomeFolderPermissionsInGlobalSite()
		throws Exception {

		long globalGroupId = _globalGroup.getGroupId();

		_resourcePermission =
			_resourcePermissionLocalService.getResourcePermission(
				_globalGroup.getCompanyId(), DLConstants.RESOURCE_NAME,
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(globalGroupId), _ownerRole.getRoleId());

		_resourcePermission.setActionIds(0);
		_resourcePermission.setViewActionId(false);

		_resourcePermissionLocalService.updateResourcePermission(
			_resourcePermission);
	}

	private long _companyId;
	private Group _globalGroup;
	private Role _ownerRole;

	@Inject
	private ResourceLocalService _resourceLocalService;

	@DeleteAfterTestRun
	private ResourcePermission _resourcePermission;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

}