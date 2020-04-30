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

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.converter;

import com.liferay.headless.delivery.dto.v1_0.PageWidgetInstanceDefinition;
import com.liferay.headless.delivery.dto.v1_0.Widget;
import com.liferay.headless.delivery.dto.v1_0.WidgetPermission;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletPreferences;

/**
 * @author Jürgen Kappler
 */
public class PageWidgetInstanceDefinitionDTOConverter {

	public static PageWidgetInstanceDefinition toDTO(
		long plid, String portletId) {

		if (Validator.isNull(portletId)) {
			return null;
		}

		return new PageWidgetInstanceDefinition() {
			{
				widget = new Widget() {
					{
						name = PortletIdCodec.decodePortletName(portletId);
					}
				};
				widgetConfig = _getWidgetConfig(plid, portletId);
				widgetPermissions = _getWidgetPermissions(plid, portletId);
			}
		};
	}

	private static Map<String, Object> _getWidgetConfig(
		long plid, String portletId) {

		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

		if (layout == null) {
			return null;
		}

		String portletName = PortletIdCodec.decodePortletName(portletId);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(portletName);

		if (portlet == null) {
			return null;
		}

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getLayoutPortletSetup(
				layout.getPlid(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, portletId,
				portlet.getDefaultPreferences());

		if (portletPreferences == null) {
			return null;
		}

		Map<String, Object> widgetConfigMap = new HashMap<>();

		Map<String, String[]> portletPreferencesMap =
			portletPreferences.getMap();

		for (Map.Entry<String, String[]> entrySet :
				portletPreferencesMap.entrySet()) {

			String[] values = entrySet.getValue();

			if (ArrayUtil.isNotEmpty(values)) {
				widgetConfigMap.put(entrySet.getKey(), values[0]);
			}
			else {
				widgetConfigMap.put(entrySet.getKey(), StringPool.BLANK);
			}
		}

		return widgetConfigMap;
	}

	private static WidgetPermission[] _getWidgetPermissions(
		long plid, String portletId) {

		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

		if (layout == null) {
			return null;
		}

		String portletName = PortletIdCodec.decodePortletName(portletId);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(portletName);

		if (portlet == null) {
			return null;
		}

		String resourcePrimKey = PortletPermissionUtil.getPrimaryKey(
			plid, portletId);

		List<ResourcePermission> resourcePermissions =
			ResourcePermissionLocalServiceUtil.getResourcePermissions(
				layout.getCompanyId(), portletName,
				ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey);

		if (ListUtil.isEmpty(resourcePermissions)) {
			return null;
		}

		List<ResourceAction> resourceActions =
			ResourceActionLocalServiceUtil.getResourceActions(portletName);

		if (ListUtil.isEmpty(resourceActions)) {
			return null;
		}

		List<WidgetPermission> widgetPermissions = new ArrayList<>();

		for (ResourcePermission resourcePermission : resourcePermissions) {
			Role role = RoleLocalServiceUtil.fetchRole(
				resourcePermission.getRoleId());

			if (role == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						String.format(
							"Resource permission %s will not be exported " +
								"since no role was found with role ID %s",
							resourcePermission.getName(),
							resourcePermission.getRoleId()));
				}

				continue;
			}

			Set<String> actionIdsSet = new HashSet<>();

			long actionIds = resourcePermission.getActionIds();

			for (ResourceAction resourceAction : resourceActions) {
				long bitwiseValue = resourceAction.getBitwiseValue();

				if ((actionIds & bitwiseValue) == bitwiseValue) {
					actionIdsSet.add(resourceAction.getActionId());
				}
			}

			widgetPermissions.add(
				new WidgetPermission() {
					{
						actionKeys = actionIdsSet.toArray(new String[0]);
						roleKey = role.getName();
					}
				});
		}

		return widgetPermissions.toArray(new WidgetPermission[0]);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PageWidgetInstanceDefinitionDTOConverter.class);

}