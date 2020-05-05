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

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.structure.importer.util;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.permission.PortletPermission;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = PortletPermissionsImporterHelper.class)
public class PortletPermissionsImporterHelper {

	public void importPortletPermissions(
			long plid, String portletId,
			List<Map<String, Object>> widgetPermissionsMaps)
		throws Exception {

		if (widgetPermissionsMaps == null) {
			return;
		}

		Layout layout = _layoutLocalService.fetchLayout(plid);

		if (layout == null) {
			return;
		}

		String portletName = PortletIdCodec.decodePortletName(portletId);

		Portlet portlet = _portletLocalService.getPortletById(portletName);

		if (portlet == null) {
			return;
		}

		String resourcePrimKey = _portletPermission.getPrimaryKey(
			plid, portletId);

		Map<Long, String[]> roleIdsToActionIds = new HashMap<>();

		for (Map<String, Object> widgetPermissionsMap : widgetPermissionsMaps) {
			Role role = _roleLocalService.fetchRole(
				layout.getCompanyId(),
				(String)widgetPermissionsMap.get("roleKey"));

			if (role == null) {
				continue;
			}

			List<ResourceAction> resourceActions =
				_resourceActionLocalService.getResourceActions(portletName);

			if (ListUtil.isEmpty(resourceActions)) {
				continue;
			}

			Stream<ResourceAction> stream = resourceActions.stream();

			List<String> resourceActionsIds = stream.map(
				ResourceAction::getActionId
			).collect(
				Collectors.toList()
			);

			List<String> actionKeys = (List<String>)widgetPermissionsMap.get(
				"actionKeys");

			List<String> actionIds = new ArrayList<>();

			for (String actionKey : actionKeys) {
				if (!resourceActionsIds.contains(actionKey)) {
					continue;
				}

				actionIds.add(actionKey);
			}

			if (ListUtil.isNotEmpty(actionIds)) {
				roleIdsToActionIds.put(
					role.getRoleId(), actionIds.toArray(new String[0]));
			}
		}

		if (MapUtil.isNotEmpty(roleIdsToActionIds)) {
			_resourcePermissionService.setIndividualResourcePermissions(
				layout.getGroupId(), layout.getCompanyId(), portletName,
				resourcePrimKey, roleIdsToActionIds);
		}
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private PortletPermission _portletPermission;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourcePermissionService _resourcePermissionService;

	@Reference
	private RoleLocalService _roleLocalService;

}