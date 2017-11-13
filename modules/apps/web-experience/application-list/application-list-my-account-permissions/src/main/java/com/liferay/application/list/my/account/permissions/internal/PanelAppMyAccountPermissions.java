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

package com.liferay.application.list.my.account.permissions.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.PortletCategoryKeys;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;

import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(immediate = true, service = PanelAppMyAccountPermissions.class)
public class PanelAppMyAccountPermissions {

	public void initPermissions(Portlet portlet) throws Exception {
		_initPermissions(portlet);
	}

	private void _initPermissions(Portlet portlet)
		throws IOException, PortalException, ReadOnlyException,
			ValidatorException {

		String category = portlet.getControlPanelEntryCategory();

		if ((category == null) ||
			!category.equals(PortletCategoryKeys.USER_MY_ACCOUNT)) {

			return;
		}

		long companyId = portlet.getCompanyId();
		String portletId = portlet.getPortletId();

		PortletPreferences portletPreferences =
			_portletPreferencesFactory.getLayoutPortletSetup(
				companyId, companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY,
				LayoutConstants.DEFAULT_PLID, portletId,
				PortletConstants.DEFAULT_PREFERENCES);

		if (_prefsProps.getBoolean(
				portletPreferences,
				"myAccountAccessInControlPanelPermissionsInitialized")) {

			return;
		}

		Role userRole = _roleLocalService.getRole(
			companyId, RoleConstants.USER);

		List<String> actionIds = ResourceActionsUtil.getPortletResourceActions(
			portlet.getRootPortletId());

		String actionId = ActionKeys.ACCESS_IN_CONTROL_PANEL;

		if (actionIds.contains(actionId)) {
			_resourcePermissionLocalService.addResourcePermission(
				companyId, portlet.getRootPortletId(),
				ResourceConstants.SCOPE_COMPANY, String.valueOf(companyId),
				userRole.getRoleId(), actionId);
		}

		portletPreferences.setValue(
			"myAccountAccessInControlPanelPermissionsInitialized",
			StringPool.TRUE);

		portletPreferences.store();
	}

	@Reference
	private PortletPreferencesFactory _portletPreferencesFactory;

	@Reference
	private PrefsProps _prefsProps;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}