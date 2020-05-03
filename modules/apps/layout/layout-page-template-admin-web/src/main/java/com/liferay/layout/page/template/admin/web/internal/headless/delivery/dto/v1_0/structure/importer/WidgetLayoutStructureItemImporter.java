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

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.structure.importer;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.PortletIdException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.permission.PortletPermission;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutStructureItemImporter.class)
public class WidgetLayoutStructureItemImporter
	extends BaseLayoutStructureItemImporter
	implements LayoutStructureItemImporter {

	@Override
	public LayoutStructureItem addLayoutStructureItem(
			Layout layout, LayoutStructure layoutStructure,
			PageElement pageElement, String parentItemId, int position)
		throws Exception {

		FragmentEntryLink fragmentEntryLink = _addFragmentEntryLink(
			layout, pageElement);

		if (fragmentEntryLink == null) {
			return null;
		}

		return layoutStructure.addFragmentLayoutStructureItem(
			fragmentEntryLink.getFragmentEntryLinkId(), parentItemId, position);
	}

	@Override
	public PageElement.Type getPageElementType() {
		return PageElement.Type.WIDGET;
	}

	private FragmentEntryLink _addFragmentEntryLink(
			Layout layout, PageElement pageElement)
		throws Exception {

		Map<String, Object> definitionMap = getDefinitionMap(
			pageElement.getDefinition());

		if (definitionMap == null) {
			return null;
		}

		Map<String, Object> widgetDefinitionMap =
			(Map<String, Object>)definitionMap.get("widget");

		String name = (String)widgetDefinitionMap.get("name");

		if (Validator.isNull(name)) {
			return null;
		}

		try {
			JSONObject editableValueJSONObject =
				_fragmentEntryProcessorRegistry.
					getDefaultEditableValuesJSONObject(
						StringPool.BLANK, StringPool.BLANK);

			String instanceId = _getPortletInstanceId(layout, name);

			editableValueJSONObject.put(
				"instanceId", instanceId
			).put(
				"portletId", name
			);

			Map<String, Object> widgetConfigDefinitionMap =
				(Map<String, Object>)definitionMap.get("widgetConfig");

			_importPortletConfiguration(
				layout.getPlid(), PortletIdCodec.encode(name, instanceId),
				widgetConfigDefinitionMap);

			List<Map<String, Object>> widgetPermissionsMaps =
				(List<Map<String, Object>>)definitionMap.get(
					"widgetPermissions");

			_importPortletPermissions(
				layout.getPlid(), PortletIdCodec.encode(name, instanceId),
				widgetPermissionsMaps);

			return _fragmentEntryLinkLocalService.addFragmentEntryLink(
				layout.getUserId(), layout.getGroupId(), 0, 0, 0,
				_portal.getClassNameId(Layout.class), layout.getPlid(),
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, editableValueJSONObject.toString(),
				StringPool.BLANK, 0, null,
				ServiceContextThreadLocal.getServiceContext());
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException, portalException);
			}
		}

		return null;
	}

	private String _getPortletInstanceId(Layout layout, String portletId)
		throws PortletIdException {

		Portlet portlet = _portletLocalService.fetchPortletById(
			layout.getCompanyId(), portletId);

		if (portlet == null) {
			throw new PortletIdException();
		}

		if (portlet.isInstanceable()) {
			return PortletIdCodec.generateInstanceId();
		}

		long count = _portletPreferencesLocalService.getPortletPreferencesCount(
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(), portletId);

		if (count > 0) {
			throw new PortletIdException(
				"Unable to add uninstanceable portlet more than once");
		}

		return StringPool.BLANK;
	}

	private void _importPortletConfiguration(
			long plid, String portletId, Map<String, Object> widgetConfig)
		throws Exception {

		if (widgetConfig == null) {
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

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				layout.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
				portletId, portlet.getDefaultPreferences());

		for (Map.Entry<String, Object> entrySet : widgetConfig.entrySet()) {
			portletPreferences.setValue(
				entrySet.getKey(), (String)entrySet.getValue());
		}

		String portletPreferencesXML = PortletPreferencesFactoryUtil.toXML(
			portletPreferences);

		_portletPreferencesLocalService.addPortletPreferences(
			layout.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(), portletId,
			null, portletPreferencesXML);
	}

	private void _importPortletPermissions(
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

	private static final Log _log = LogFactoryUtil.getLog(
		WidgetLayoutStructureItemImporter.class);

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentEntryProcessorRegistry _fragmentEntryProcessorRegistry;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private PortletPermission _portletPermission;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourcePermissionService _resourcePermissionService;

	@Reference
	private RoleLocalService _roleLocalService;

}