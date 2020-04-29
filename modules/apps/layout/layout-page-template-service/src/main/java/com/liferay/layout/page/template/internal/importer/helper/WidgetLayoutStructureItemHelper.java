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

package com.liferay.layout.page.template.internal.importer.helper;

import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.validator.FragmentEntryValidator;
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
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletPreferences;

/**
 * @author Jürgen Kappler
 */
public class WidgetLayoutStructureItemHelper
	extends BaseLayoutStructureItemHelper implements LayoutStructureItemHelper {

	@Override
	public LayoutStructureItem addLayoutStructureItem(
			FragmentCollectionContributorTracker
				fragmentCollectionContributorTracker,
			FragmentEntryProcessorRegistry fragmentEntryProcessorRegistry,
			FragmentEntryValidator fragmentEntryValidator,
			FragmentRendererTracker fragmentRendererTracker, Layout layout,
			LayoutStructure layoutStructure, PageElement pageElement,
			String parentItemId, int position)
		throws Exception {

		FragmentEntryLink fragmentEntryLink = _addFragmentEntryLink(
			fragmentEntryProcessorRegistry, layout, pageElement);

		if (fragmentEntryLink == null) {
			return null;
		}

		return layoutStructure.addFragmentLayoutStructureItem(
			fragmentEntryLink.getFragmentEntryLinkId(), parentItemId, position);
	}

	private FragmentEntryLink _addFragmentEntryLink(
			FragmentEntryProcessorRegistry fragmentEntryProcessorRegistry,
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
				fragmentEntryProcessorRegistry.
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

			return FragmentEntryLinkLocalServiceUtil.addFragmentEntryLink(
				layout.getUserId(), layout.getGroupId(), 0, 0, 0,
				PortalUtil.getClassNameId(Layout.class), layout.getPlid(),
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

		Portlet portlet = PortletLocalServiceUtil.fetchPortletById(
			layout.getCompanyId(), portletId);

		if (portlet == null) {
			throw new PortletIdException();
		}

		if (portlet.isInstanceable()) {
			return PortletIdCodec.generateInstanceId();
		}

		long count =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
				portletId);

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

		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

		if (layout == null) {
			return;
		}

		String portletName = PortletIdCodec.decodePortletName(portletId);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(portletName);

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

		PortletPreferencesLocalServiceUtil.addPortletPreferences(
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

		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

		if (layout == null) {
			return;
		}

		String portletName = PortletIdCodec.decodePortletName(portletId);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(portletName);

		if (portlet == null) {
			return;
		}

		String resourcePrimKey = PortletPermissionUtil.getPrimaryKey(
			plid, portletId);

		Map<Long, String[]> roleIdsToActionIds = new HashMap<>();

		for (Map<String, Object> widgetPermissionsMap : widgetPermissionsMaps) {
			Role role = RoleLocalServiceUtil.fetchRole(
				layout.getCompanyId(),
				(String)widgetPermissionsMap.get("roleKey"));

			if (role == null) {
				continue;
			}

			List<ResourceAction> resourceActions =
				ResourceActionLocalServiceUtil.getResourceActions(portletName);

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
			ResourcePermissionServiceUtil.setIndividualResourcePermissions(
				layout.getGroupId(), layout.getCompanyId(), portletName,
				resourcePrimKey, roleIdsToActionIds);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WidgetLayoutStructureItemHelper.class);

}