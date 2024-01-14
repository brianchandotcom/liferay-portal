/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.mapper;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.headless.delivery.dto.v1_0.WidgetInstance;
import com.liferay.headless.delivery.dto.v1_0.WidgetPermission;
import com.liferay.layout.exporter.PortletPreferencesPortletConfigurationExporter;
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
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Jürgen Kappler
 * @author Javier de Arcos
 */
public class WidgetInstanceMapper {

	public WidgetInstanceMapper(
		LayoutLocalService layoutLocalService, Portal portal,
		PortletLocalService portletLocalService,
		PortletPreferencesPortletConfigurationExporter
			portletPreferencesPortletConfigurationExporter,
		ResourceActionLocalService resourceActionLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService,
		RoleLocalService roleLocalService, TeamLocalService teamLocalService) {

		_layoutLocalService = layoutLocalService;
		_portal = portal;
		_portletLocalService = portletLocalService;
		_portletPreferencesPortletConfigurationExporter =
			portletPreferencesPortletConfigurationExporter;
		_resourceActionLocalService = resourceActionLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
		_roleLocalService = roleLocalService;
		_teamLocalService = teamLocalService;
	}

	public WidgetInstance getWidgetInstance(
		FragmentEntryLink fragmentEntryLink, String portletId) {

		if (Validator.isNull(portletId)) {
			return null;
		}

		return new WidgetInstance() {
			{
				setWidgetConfig(
					() -> _getWidgetConfig(
						fragmentEntryLink.getPlid(), portletId));
				setWidgetInstanceId(
					() -> _getWidgetInstanceId(fragmentEntryLink, portletId));
				setWidgetName(
					() -> PortletIdCodec.decodePortletName(portletId));
				setWidgetPermissions(
					() -> _getWidgetPermissions(
						fragmentEntryLink.getPlid(), portletId));
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

		String resourcePrimKey = PortletPermissionUtil.getPrimaryKey(
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
						setActionKeys(
							() -> actionIdsSet.toArray(new String[0]));
						setRoleKey(() -> finalRoleKey);
					}
				});
		}

		return widgetPermissions.toArray(new WidgetPermission[0]);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WidgetInstanceMapper.class);

	private final LayoutLocalService _layoutLocalService;
	private final Portal _portal;
	private final PortletLocalService _portletLocalService;
	private final PortletPreferencesPortletConfigurationExporter
		_portletPreferencesPortletConfigurationExporter;
	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;
	private final TeamLocalService _teamLocalService;

}