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

package com.liferay.portal.lar;

import com.liferay.portal.NoSuchTeamException;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.Team;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Joel Kozikowski
 * @author Charles May
 * @author Raymond Augé
 * @author Jorge Ferrer
 * @author Bruno Farache
 * @author Wesley Gong
 * @author Zsigmond Rab
 * @author Douglas Wong
 */
public class PermissionImporter {

	public static PermissionImporter getInstance() {
		return _instance;
	}

	protected Role checkRole(
			LayoutCache layoutCache, long companyId, long groupId, long userId,
			Element roleElement)
		throws Exception {

		String name = roleElement.attributeValue("name");

		Role role = null;

		if (name.startsWith(PermissionExporter.ROLE_TEAM_PREFIX)) {
			name = name.substring(PermissionExporter.ROLE_TEAM_PREFIX.length());

			String description = roleElement.attributeValue("description");

			Team team = null;

			try {
				team = TeamLocalServiceUtil.getTeam(groupId, name);
			}
			catch (NoSuchTeamException nste) {
				team = TeamLocalServiceUtil.addTeam(
					userId, groupId, name, description);
			}

			role = RoleLocalServiceUtil.getTeamRole(
				companyId, team.getTeamId());
		}
		else {
			role = layoutCache.getRole(companyId, name);
		}

		if (role == null) {
			String title = roleElement.attributeValue("title");

			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
				title);

			String description = roleElement.attributeValue("description");

			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(description);

			int type = GetterUtil.getInteger(
				roleElement.attributeValue("type"));
			String subtype = roleElement.attributeValue("subtype");

			role = RoleLocalServiceUtil.addRole(
				userId, null, 0, name, titleMap, descriptionMap, type, subtype,
				null);
		}

		return role;
	}

	protected void checkRoles(
			LayoutCache layoutCache, long companyId, long groupId, long userId,
			Element portletElement)
		throws Exception {

		Element permissionsElement = portletElement.element("permissions");

		if (permissionsElement == null) {
			return;
		}

		List<Element> roleElements = permissionsElement.elements("role");

		for (Element roleElement : roleElements) {
			checkRole(layoutCache, companyId, groupId, userId, roleElement);
		}
	}

	protected List<String> getActions(Element element) {
		List<String> actions = new ArrayList<>();

		List<Element> actionKeyElements = element.elements("action-key");

		for (Element actionKeyElement : actionKeyElements) {
			actions.add(actionKeyElement.getText());
		}

		return actions;
	}

	protected void importPermissions(
			LayoutCache layoutCache, long companyId, long groupId, long userId,
			Layout layout, String resourceName, String resourcePrimKey,
			Element permissionsElement, boolean portletActions)
		throws Exception {

		Map<Long, String[]> roleIdsToActionIds = new HashMap<>();

		List<Element> roleElements = permissionsElement.elements("role");

		for (Element roleElement : roleElements) {
			Role role = checkRole(
				layoutCache, companyId, groupId, userId, roleElement);

			Group group = GroupLocalServiceUtil.getGroup(groupId);

			if (!group.isLayoutPrototype() && !group.isLayoutSetPrototype()) {
				String roleName = role.getName();

				if (roleName.equals(RoleConstants.GUEST)) {
					continue;
				}
			}

			List<String> actions = getActions(roleElement);

			roleIdsToActionIds.put(
				role.getRoleId(), actions.toArray(new String[actions.size()]));
		}

		if (roleIdsToActionIds.isEmpty()) {
			return;
		}

		ResourcePermissionLocalServiceUtil.setResourcePermissions(
			companyId, resourceName, ResourceConstants.SCOPE_INDIVIDUAL,
			resourcePrimKey, roleIdsToActionIds);
	}

	protected void importPortletPermissions(
			LayoutCache layoutCache, long companyId, long groupId, long userId,
			Layout layout, Element portletElement, String portletId)
		throws Exception {

		Element permissionsElement = portletElement.element("permissions");

		if ((layout != null) && (permissionsElement != null)) {
			String resourceName = PortletConstants.getRootPortletId(portletId);

			String resourcePrimKey = PortletPermissionUtil.getPrimaryKey(
				layout.getPlid(), portletId);

			importPermissions(
				layoutCache, companyId, groupId, userId, layout, resourceName,
				resourcePrimKey, permissionsElement, true);
		}
	}

	protected void readPortletDataPermissions(
			PortletDataContext portletDataContext)
		throws Exception {

		String xml = portletDataContext.getZipEntryAsString(
			ExportImportPathUtil.getSourceRootPath(portletDataContext) +
				"/portlet-data-permissions.xml");

		if (xml == null) {
			return;
		}

		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		List<Element> portletDataElements = rootElement.elements(
			"portlet-data");

		for (Element portletDataElement : portletDataElements) {
			String resourceName = portletDataElement.attributeValue(
				"resource-name");
			long resourcePK = GetterUtil.getLong(
				portletDataElement.attributeValue("resource-pk"));

			List<KeyValuePair> permissions = new ArrayList<>();

			List<Element> permissionsElements = portletDataElement.elements(
				"permissions");

			for (Element permissionsElement : permissionsElements) {
				String roleName = permissionsElement.attributeValue(
					"role-name");
				String actions = permissionsElement.attributeValue("actions");

				KeyValuePair permission = new KeyValuePair(roleName, actions);

				permissions.add(permission);
			}

			portletDataContext.addPermissions(
				resourceName, resourcePK, permissions);
		}
	}

	private PermissionImporter() {
	}

	private static final PermissionImporter _instance =
		new PermissionImporter();

}