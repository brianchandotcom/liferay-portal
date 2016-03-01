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

package com.liferay.dynamic.data.lists.web.upgrade.v1_0_0;

import com.liferay.dynamic.data.lists.constants.DDLPortletKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Marcellus Tavares
 */
public class UpgradeDDLFormPortletId
	extends com.liferay.portal.upgrade.util.UpgradePortletId {

	public UpgradeDDLFormPortletId(
		PortletPreferencesLocalService portletPreferencesLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService) {

		_portletPreferencesLocalService = portletPreferencesLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	protected void deleteResourcePermission(long resourcePermissionId)
		throws Exception {

		runSQL(
			"delete ResourcePermission where resourcePermissionId = " +
				resourcePermissionId);
	}

	@Override
	protected String[][] getRenamePortletIdsArray() {
		return new String[][] {
			new String[] {
				"1_WAR_ddlformportlet",
				DDLPortletKeys.DYNAMIC_DATA_LISTS_DISPLAY
			}
		};
	}

	protected boolean hasResourcePermission(
			long companyId, String name, int scope, String primKey, long roleId)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"select count(*) from ResourcePermission where companyId = ? " +
					"and name = ? and scope = ? and primKey = ? and " +
						"roleId = ? ")) {

			ps.setLong(1, companyId);
			ps.setString(2, name);
			ps.setInt(3, scope);
			ps.setString(4, primKey);
			ps.setLong(5, roleId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					int count = rs.getInt(1);

					if (count > 0) {
						return true;
					}
				}

				return false;
			}
		}
	}

	@Override
	protected void updatePortlet(
			String oldRootPortletId, String newRootPortletId)
		throws Exception {

		try {
			updateResourcePermission(oldRootPortletId, newRootPortletId, true);

			updateInstanceablePortletPreferences(
				oldRootPortletId, newRootPortletId);

			updateLayouts(oldRootPortletId, newRootPortletId, false);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
	}

	@Override
	protected void updatePortletPreference(
			long portletPreferencesId, String portletId)
		throws Exception {

		PortletPreferences portletPreferences =
			_portletPreferencesLocalService.getPortletPreferences(
				portletPreferencesId);

		String preferences = StringUtil.replace(
			portletPreferences.getPreferences(), "</portlet-preferences>",
			"<preference><name>formView</name><value>true</value>" +
				"</preference></portlet-preferences>");

		try (PreparedStatement ps = connection.prepareStatement(
				"update PortletPreferences set portletId = ?, preferences = " +
					"? where portletPreferencesId = " + portletPreferencesId)) {

			ps.setString(1, portletId);
			ps.setString(2, preferences);

			ps.executeUpdate();
		}
	}

	@Override
	protected void updateResourcePermission(
			long resourcePermissionId, String name, String primKey)
		throws Exception {

		ResourcePermission resourcePermission =
			_resourcePermissionLocalService.getResourcePermission(
				resourcePermissionId);

		if (hasResourcePermission(
				resourcePermission.getCompanyId(), name,
				resourcePermission.getScope(), primKey,
				resourcePermission.getRoleId())) {

			// Resource permission may have already been added by DDL Display
			// portlet, in this case it's safe to remove 1_WAR_ddlformportlet
			// resource permission

			_resourcePermissionLocalService.deleteResourcePermission(
				resourcePermission.getResourcePermissionId());

			return;
		}

		super.updateResourcePermission(resourcePermissionId, name, primKey);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeDDLFormPortletId.class);

	private final PortletPreferencesLocalService
		_portletPreferencesLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;

}