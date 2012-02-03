/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.PermissionLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.util.PropsValues;

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

	protected List<String> getActions(Element element) {
		List<String> actions = new ArrayList<String>();

		List<Element> actionKeyElements = element.elements("action-key");

		for (Element actionKeyElement : actionKeyElements) {
			actions.add(actionKeyElement.getText());
		}

		return actions;
	}

	protected void importGroupPermissions(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String resourcePrimKey, Element parentElement,
			String elementName, boolean portletActions)
		throws Exception {

		Element actionElement = parentElement.element(elementName);

		if (actionElement == null) {
			return;
		}

		List<String> actions = getActions(actionElement);

		Resource resource = layoutCache.getResource(
			companyId, groupId, resourceName,
			ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey,
			portletActions);

		PermissionLocalServiceUtil.setGroupPermissions(
			groupId, actions.toArray(new String[actions.size()]),
			resource.getResourceId());
	}

	protected void importGroupRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String entityName, Element parentElement)
		throws Exception {

		Element entityRolesElement = parentElement.element(
			entityName + "-roles");

		if (entityRolesElement == null) {
			return;
		}

		importRolePermissions(
			layoutCache, companyId, resourceName, ResourceConstants.SCOPE_GROUP,
			String.valueOf(groupId), entityRolesElement, true);
	}

	protected void importInheritedPermissions(
			LayoutCache layoutCache, long companyId, String resourceName,
			String resourcePrimKey, Element permissionsElement,
			String entityName, boolean portletActions)
		throws Exception {

		Element entityPermissionsElement = permissionsElement.element(
			entityName + "-permissions");

		if (entityPermissionsElement == null) {
			return;
		}

		List<Element> actionsElements = entityPermissionsElement.elements(
			entityName + "-actions");

		for (int i = 0; i < actionsElements.size(); i++) {
			Element actionElement = actionsElements.get(i);

			String name = actionElement.attributeValue("name");

			long entityGroupId = layoutCache.getEntityGroupId(
				companyId, entityName, name);

			if (entityGroupId == 0) {
				_log.warn(
					"Ignore inherited permissions for entity " + entityName +
						" with name " + name);
			}
			else {
				Element parentElement = SAXReaderUtil.createElement("parent");

				parentElement.add(actionElement.createCopy());

				importGroupPermissions(
					layoutCache, companyId, entityGroupId, resourceName,
					resourcePrimKey, parentElement, entityName + "-actions",
					portletActions);
			}
		}
	}

	protected void importInheritedRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String entityName, Element parentElement)
		throws Exception {

		Element entityRolesElement = parentElement.element(
			entityName + "-roles");

		if (entityRolesElement == null) {
			return;
		}

		List<Element> entityElements = entityRolesElement.elements(entityName);

		for (Element entityElement : entityElements) {
			String name = entityElement.attributeValue("name");

			long entityGroupId = layoutCache.getEntityGroupId(
				companyId, entityName, name);

			if (entityGroupId == 0) {
				_log.warn(
					"Ignore inherited roles for entity " + entityName +
						" with name " + name);
			}
			else {
				importRolePermissions(
					layoutCache, companyId, resourceName,
					ResourceConstants.SCOPE_GROUP, String.valueOf(groupId),
					entityElement, false);
			}
		}
	}

	protected void importLayoutPermissions(
			LayoutCache layoutCache, long companyId, long groupId, long userId,
			Layout layout, Element layoutElement, Element parentElement,
			boolean importUserPermissions)
		throws Exception {

		Element permissionsElement = layoutElement.element("permissions");

		if (permissionsElement != null) {
			String resourceName = Layout.class.getName();
			String resourcePrimKey = String.valueOf(layout.getPlid());

			if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
				importPermissions_5(
					layoutCache, companyId, groupId, userId, resourceName,
					resourcePrimKey, permissionsElement, false);
			}
			else if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
				importPermissions_6(
					layoutCache, companyId, groupId, userId, resourceName,
					resourcePrimKey, permissionsElement, false);
			}
			else {
				Group guestGroup = GroupLocalServiceUtil.getGroup(
					companyId, GroupConstants.GUEST);

				importLayoutPermissions_1to4(
					layoutCache, companyId, groupId, guestGroup, layout,
					resourceName, resourcePrimKey, permissionsElement,
					importUserPermissions);
			}
		}

		Element rolesElement = parentElement.element("roles");

		if ((PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM < 5) &&
			(rolesElement != null)) {

			importLayoutRoles(layoutCache, companyId, groupId, rolesElement);
		}
	}

	protected void importLayoutPermissions_1to4(
			LayoutCache layoutCache, long companyId, long groupId,
			Group guestGroup, Layout layout, String resourceName,
			String resourcePrimKey, Element permissionsElement,
			boolean importUserPermissions)
		throws Exception {

		importGroupPermissions(
			layoutCache, companyId, groupId, resourceName, resourcePrimKey,
			permissionsElement, "community-actions", false);

		if (groupId != guestGroup.getGroupId()) {
			importGroupPermissions(
				layoutCache, companyId, guestGroup.getGroupId(), resourceName,
				resourcePrimKey, permissionsElement, "guest-actions", false);
		}

		if (importUserPermissions) {
			importUserPermissions(
				layoutCache, companyId, groupId, resourceName, resourcePrimKey,
				permissionsElement, false);
		}

		importInheritedPermissions(
			layoutCache, companyId, resourceName, resourcePrimKey,
			permissionsElement, "organization", false);

		importInheritedPermissions(
			layoutCache, companyId, resourceName, resourcePrimKey,
			permissionsElement, "user-group", false);
	}

	protected void importLayoutRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			Element rolesElement)
		throws Exception {

		String resourceName = Layout.class.getName();

		importGroupRoles(
			layoutCache, companyId, groupId, resourceName, "community",
			rolesElement);

		importUserRoles(
			layoutCache, companyId, groupId, resourceName, rolesElement);

		importInheritedRoles(
			layoutCache, companyId, groupId, resourceName, "organization",
			rolesElement);

		importInheritedRoles(
			layoutCache, companyId, groupId, resourceName, "user-group",
			rolesElement);
	}

	protected void importPermissions_5(
			LayoutCache layoutCache, long companyId, long groupId, long userId,
			String resourceName, String resourcePrimKey,
			Element permissionsElement, boolean portletActions)
		throws Exception {

		Map<Long, String[]> roleIdsToActionIds = new HashMap<Long, String[]>();

		List<Element> roleElements = permissionsElement.elements("role");

		for (Element roleElement : roleElements) {
			String name = roleElement.attributeValue("name");

			Role role = null;

			if (name.startsWith(PermissionExporter.ROLE_TEAM_PREFIX)) {
				name = name.substring(
					PermissionExporter.ROLE_TEAM_PREFIX.length());

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
				String description = roleElement.attributeValue("description");

				Map<Locale, String> descriptionMap =
					LocalizationUtil.getLocalizationMap(description);

				int type = Integer.valueOf(roleElement.attributeValue("type"));

				role = RoleLocalServiceUtil.addRole(
					userId, companyId, name, null, descriptionMap, type);
			}

			List<String> actions = getActions(roleElement);

			roleIdsToActionIds.put(
				role.getRoleId(), actions.toArray(new String[actions.size()]));
		}

		if (roleIdsToActionIds.isEmpty()) {
			return;
		}

		PermissionLocalServiceUtil.setRolesPermissions(
			companyId, roleIdsToActionIds, resourceName,
			ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey);
	}

	protected void importPermissions_6(
			LayoutCache layoutCache, long companyId, long groupId, long userId,
			String resourceName, String resourcePrimKey,
			Element permissionsElement, boolean portletActions)
		throws Exception {

		Map<Long, String[]> roleIdsToActionIds = new HashMap<Long, String[]>();

		List<Element> roleElements = permissionsElement.elements("role");

		for (Element roleElement : roleElements) {
			String name = roleElement.attributeValue("name");
			int type = GetterUtil.getInteger(
				roleElement.attributeValue("type"));

			Role role = null;

			if (name.startsWith(PermissionExporter.ROLE_TEAM_PREFIX)) {
				name = name.substring(
					PermissionExporter.ROLE_TEAM_PREFIX.length());

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
				String description = roleElement.attributeValue("description");

				Map<Locale, String> descriptionMap =
					LocalizationUtil.getLocalizationMap(description);

				role = RoleLocalServiceUtil.addRole(
					userId, companyId, name, null, descriptionMap, type);
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
			Layout layout, Element portletElement, String portletId,
			boolean importUserPermissions)
		throws Exception {

		Element permissionsElement = portletElement.element("permissions");

		if (permissionsElement != null) {
			String resourceName = PortletConstants.getRootPortletId(portletId);

			String resourcePrimKey = PortletPermissionUtil.getPrimaryKey(
				layout.getPlid(), portletId);

			if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
				importPermissions_5(
					layoutCache, companyId, groupId, userId, resourceName,
					resourcePrimKey, permissionsElement, true);
			}
			else if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
				importPermissions_6(
					layoutCache, companyId, groupId, userId, resourceName,
					resourcePrimKey, permissionsElement, true);
			}
			else {
				Group guestGroup = GroupLocalServiceUtil.getGroup(
					companyId, GroupConstants.GUEST);

				importPortletPermissions_1to4(
					layoutCache, companyId, groupId, guestGroup, layout,
					permissionsElement, importUserPermissions);
			}
		}

		Element rolesElement = portletElement.element("roles");

		if ((PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM < 5) &&
			(rolesElement != null)) {

			importPortletRoles(layoutCache, companyId, groupId, portletElement);
			importPortletRoles(
				layoutCache, companyId, groupId, portletId, rolesElement);
		}
	}

	protected void importPortletPermissions_1to4(
			LayoutCache layoutCache, long companyId, long groupId,
			Group guestGroup, Layout layout, Element permissionsElement,
			boolean importUserPermissions)
		throws Exception {

		List<Element> portletElements = permissionsElement.elements("portlet");

		for (Element portletElement : portletElements) {
			String portletId = portletElement.attributeValue("portlet-id");

			String resourceName = PortletConstants.getRootPortletId(portletId);
			String resourcePrimKey = PortletPermissionUtil.getPrimaryKey(
				layout.getPlid(), portletId);

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				companyId, resourceName);

			if (portlet == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Do not import portlet permissions for " + portletId +
							" because the portlet does not exist");
				}
			}
			else {
				importGroupPermissions(
					layoutCache, companyId, groupId, resourceName,
					resourcePrimKey, portletElement, "community-actions", true);

				if (groupId != guestGroup.getGroupId()) {
					importGroupPermissions(
						layoutCache, companyId, guestGroup.getGroupId(),
						resourceName, resourcePrimKey, portletElement,
						"guest-actions", true);
				}

				if (importUserPermissions) {
					importUserPermissions(
						layoutCache, companyId, groupId, resourceName,
						resourcePrimKey, portletElement, true);
				}

				importInheritedPermissions(
					layoutCache, companyId, resourceName, resourcePrimKey,
					portletElement, "organization", true);

				importInheritedPermissions(
					layoutCache, companyId, resourceName, resourcePrimKey,
					portletElement, "user-group", true);
			}
		}
	}

	protected void importPortletRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			Element rolesElement)
		throws Exception {

		List<Element> portletElements = rolesElement.elements("portlet");

		for (Element portletElement : portletElements) {
			String portletId = portletElement.attributeValue("portlet-id");

			String resourceName = PortletConstants.getRootPortletId(portletId);

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				companyId, resourceName);

			if (portlet == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Do not import portlet roles for " + portletId +
							" because the portlet does not exist");
				}
			}
			else {
				importGroupRoles(
					layoutCache, companyId, groupId, resourceName, "community",
					portletElement);

				importUserRoles(
					layoutCache, companyId, groupId, resourceName,
					portletElement);

				importInheritedRoles(
					layoutCache, companyId, groupId, resourceName,
					"organization", portletElement);

				importInheritedRoles(
					layoutCache, companyId, groupId, resourceName, "user-group",
					portletElement);
			}
		}
	}

	protected void importPortletRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			String portletId, Element rolesElement)
		throws Exception {

		String resourceName = PortletConstants.getRootPortletId(portletId);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			companyId, resourceName);

		if (portlet == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Do not import portlet roles for " + portletId +
						" because the portlet does not exist");
			}
		}
		else {
			importGroupRoles(
				layoutCache, companyId, groupId, resourceName, "community",
				rolesElement);

			importUserRoles(
				layoutCache, companyId, groupId, resourceName, rolesElement);

			importInheritedRoles(
				layoutCache, companyId, groupId, resourceName, "organization",
				rolesElement);

			importInheritedRoles(
				layoutCache, companyId, groupId, resourceName, "user-group",
				rolesElement);
		}
	}

	protected void importRolePermissions(
			LayoutCache layoutCache, long companyId, String resourceName,
			int scope, String resourcePrimKey, Element parentElement,
			boolean communityRole)
		throws Exception {

		List<Element> roleElements = parentElement.elements("role");

		for (Element roleElement : roleElements) {
			String roleName = roleElement.attributeValue("name");

			Role role = layoutCache.getRole(companyId, roleName);

			if (role == null) {
				_log.warn(
					"Ignoring permissions for role with name " + roleName);
			}
			else {
				List<String> actions = getActions(roleElement);

				PermissionLocalServiceUtil.setRolePermissions(
					role.getRoleId(), companyId, resourceName, scope,
					resourcePrimKey,
					actions.toArray(new String[actions.size()]));

				if (communityRole) {
					long[] groupIds = {GetterUtil.getLong(resourcePrimKey)};

					GroupLocalServiceUtil.addRoleGroups(
						role.getRoleId(), groupIds);
				}
			}
		}
	}

	protected void importUserPermissions(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String resourcePrimKey, Element parentElement,
			boolean portletActions)
		throws Exception {

		Element userPermissionsElement = parentElement.element(
			"user-permissions");

		if (userPermissionsElement == null) {
			return;
		}

		List<Element> userActionsElements = userPermissionsElement.elements(
			"user-actions");

		for (Element userActionsElement : userActionsElements) {
			String uuid = userActionsElement.attributeValue("uuid");

			User user = layoutCache.getUser(companyId, groupId, uuid);

			if (user == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Ignoring permissions for user with uuid " + uuid);
				}
			}
			else {
				List<String> actions = getActions(userActionsElement);

				Resource resource = layoutCache.getResource(
					companyId, groupId, resourceName,
					ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey,
					portletActions);

				PermissionLocalServiceUtil.setUserPermissions(
					user.getUserId(),
					actions.toArray(new String[actions.size()]),
					resource.getResourceId());
			}
		}
	}

	protected void importUserRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, Element parentElement)
		throws Exception {

		Element userRolesElement = parentElement.element("user-roles");

		if (userRolesElement == null) {
			return;
		}

		List<Element> userElements = userRolesElement.elements("user");

		for (Element userElement : userElements) {
			String uuid = userElement.attributeValue("uuid");

			User user = layoutCache.getUser(companyId, groupId, uuid);

			if (user == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("Ignoring roles for user with uuid " + uuid);
				}
			}
			else {
				importRolePermissions(
					layoutCache, companyId, resourceName,
					ResourceConstants.SCOPE_GROUP, String.valueOf(groupId),
					userElement, false);
			}
		}
	}

	protected void readPortletDataPermissions(
			PortletDataContext portletDataContext)
		throws Exception {

		String xml = portletDataContext.getZipEntryAsString(
			portletDataContext.getSourceRootPath() +
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

			List<KeyValuePair> permissions = new ArrayList<KeyValuePair>();

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

	private static Log _log = LogFactoryUtil.getLog(PermissionImporter.class);

}