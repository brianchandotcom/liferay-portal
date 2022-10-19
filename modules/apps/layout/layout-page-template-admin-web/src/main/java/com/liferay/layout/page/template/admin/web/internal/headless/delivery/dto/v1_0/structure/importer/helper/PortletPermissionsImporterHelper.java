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

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.structure.importer.helper;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.permission.PortletPermission;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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
			long plid, String portletId, Set<String> warningMessages,
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

		Map<Long, String[]> roleIdsToActionIds = new HashMap<>();

		for (Map<String, Object> widgetPermissionsMap : widgetPermissionsMaps) {
			String roleKey = (String)widgetPermissionsMap.get("roleKey");

			Role role = _roleLocalService.fetchRole(
				layout.getCompanyId(), roleKey);

			if (role == null) {
				role = _getTeamRole(layout, roleKey);

				if (role == null) {
					warningMessages.add(
						_getWarningMessage(layout.getGroupId(), roleKey));

					continue;
				}
			}

			Group group = _groupLocalService.getGroup(layout.getGroupId());

			if ((role.getType() == RoleConstants.TYPE_ORGANIZATION) &&
				!group.isOrganization()) {

				warningMessages.add(
					_getWarningMessage(layout.getGroupId(), roleKey));

				continue;
			}

			List<ResourceAction> resourceActions =
				_resourceActionLocalService.getResourceActions(portletName);

			if (ListUtil.isEmpty(resourceActions)) {
				continue;
			}

			List<String> actionIds = new ArrayList<>();

			Stream<ResourceAction> stream = resourceActions.stream();

			List<String> resourceActionsIds = stream.map(
				ResourceAction::getActionId
			).collect(
				Collectors.toList()
			);

			List<String> actionKeys = (List<String>)widgetPermissionsMap.get(
				"actionKeys");

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
			String resourcePrimKey = _portletPermission.getPrimaryKey(
				plid, portletId);

			_resourcePermissionService.setIndividualResourcePermissions(
				layout.getGroupId(), layout.getCompanyId(), portletName,
				resourcePrimKey, roleIdsToActionIds);
		}
	}

	private Role _getTeamRole(Layout layout, String roleKey) throws Exception {
		Map<Team, Role> teamRoleMap = _roleLocalService.getTeamRoleMap(
			layout.getGroupId());

		for (Map.Entry<Team, Role> entry : teamRoleMap.entrySet()) {
			Team team = entry.getKey();

			if (Objects.equals(team.getName(), roleKey)) {
				return entry.getValue();
			}
		}

		return null;
	}

	private String _getWarningMessage(long groupId, String roleKey)
		throws Exception {

		Locale locale = null;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			locale = serviceContext.getLocale();
		}
		else {
			locale = _portal.getSiteDefaultLocale(groupId);
		}

		return _language.format(
			locale, "role-with-key-x-was-ignored-because-it-does-not-exist",
			new String[] {roleKey});
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

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