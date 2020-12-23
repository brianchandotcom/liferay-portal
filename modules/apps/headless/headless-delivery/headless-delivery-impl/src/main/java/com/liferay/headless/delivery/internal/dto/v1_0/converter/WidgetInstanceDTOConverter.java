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

package com.liferay.headless.delivery.internal.dto.v1_0.converter;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.headless.delivery.dto.v1_0.WidgetInstance;
import com.liferay.headless.delivery.dto.v1_0.WidgetPermission;
import com.liferay.layout.page.template.exporter.PortletConfigurationExporter;
import com.liferay.layout.page.template.exporter.PortletPreferencesPortletConfigurationExporter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.TeamLocalService;
import com.liferay.portal.kernel.service.permission.PortletPermission;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = WidgetInstanceDTOConverter.class)
public class WidgetInstanceDTOConverter {

	public WidgetInstance toDTO(
		FragmentEntryLink fragmentEntryLink, String portletId) {

		if (Validator.isNull(portletId)) {
			return null;
		}

		return new WidgetInstance() {
			{
				widgetConfig = _getWidgetConfig(
					fragmentEntryLink.getPlid(), portletId);
				widgetInstanceId = _getWidgetInstanceId(
					fragmentEntryLink, portletId);
				widgetName = PortletIdCodec.decodePortletName(portletId);
				widgetPermissions = _getWidgetPermissions(
					fragmentEntryLink.getPlid(), portletId);
			}
		};
	}

	private Map<String, Object> _getWidgetConfig(long plid, String portletId) {
		Layout layout = _layoutLocalService.fetchLayout(plid);

		if (layout == null) {
			return null;
		}

		String portletName = PortletIdCodec.decodePortletName(portletId);

		Portlet portlet = _portletLocalService.getPortletById(portletName);

		if (portlet == null) {
			return null;
		}

		PortletConfigurationExporter portletConfigurationExporter = null;
		//
		//			_portletConfigurationExporterTracker.
		//				getPortletConfigurationExporter(portletName);


		if (portletConfigurationExporter != null) {
			return portletConfigurationExporter.getPortletConfiguration(
				plid, portletId);
		}

		return _portletPreferencesPortletConfigurationExporter.
			getPortletConfiguration(plid, portletId);
	}

	private String _getWidgetInstanceId(
		FragmentEntryLink fragmentEntryLink, String portletId) {

		String instanceId = PortletIdCodec.decodeInstanceId(portletId);

		if (Validator.isNull(instanceId)) {
			return null;
		}

		String namespace = fragmentEntryLink.getNamespace();

		if (instanceId.startsWith(namespace)) {
			instanceId = instanceId.substring(namespace.length());
		}

		if (Validator.isNull(instanceId)) {
			return null;
		}

		return instanceId;
	}

	private WidgetPermission[] _getWidgetPermissions(
		long plid, String portletId) {

		Layout layout = _layoutLocalService.fetchLayout(plid);

		if (layout == null) {
			return null;
		}

		String portletName = PortletIdCodec.decodePortletName(portletId);

		Portlet portlet = _portletLocalService.getPortletById(portletName);

		if (portlet == null) {
			return null;
		}

		String resourcePrimKey = _portletPermission.getPrimaryKey(
			plid, portletId);

		List<ResourcePermission> resourcePermissions =
			_resourcePermissionLocalService.getResourcePermissions(
				layout.getCompanyId(), portletName,
				ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey);

		if (ListUtil.isEmpty(resourcePermissions)) {
			return null;
		}

		List<ResourceAction> resourceActions =
			_resourceActionLocalService.getResourceActions(portletName);

		if (ListUtil.isEmpty(resourceActions)) {
			return null;
		}

		List<WidgetPermission> widgetPermissions = new ArrayList<>();

		for (ResourcePermission resourcePermission : resourcePermissions) {
			Role role = _roleLocalService.fetchRole(
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

			String roleKey = role.getName();

			if (role.getClassNameId() == _portal.getClassNameId(Team.class)) {
				Team team = _teamLocalService.fetchTeam(role.getClassPK());

				if (team != null) {
					roleKey = team.getName();
				}
			}

			String finalRoleKey = roleKey;

			widgetPermissions.add(
				new WidgetPermission() {
					{
						actionKeys = actionIdsSet.toArray(new String[0]);
						roleKey = finalRoleKey;
					}
				});
		}

		return widgetPermissions.toArray(new WidgetPermission[0]);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WidgetInstanceDTOConverter.class);

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	//	@Reference
	//	private PortletConfigurationExporterTracker
	//		_portletConfigurationExporterTracker;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private PortletPermission _portletPermission;

	@Reference
	private PortletPreferencesPortletConfigurationExporter
		_portletPreferencesPortletConfigurationExporter;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private TeamLocalService _teamLocalService;

}