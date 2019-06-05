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

package com.liferay.exportimport.internal.lar;

import com.liferay.exportimport.internal.util.ExportImportPermissionUtil;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.exportimport.lar.PermissionImporter;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.exception.NoSuchTeamException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.TeamLocalService;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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
@Component(enabled = true, immediate = true, service = PermissionImporter.class)
public class PermissionImporterImpl implements PermissionImporter {

	@Override
	public void checkRoles(
			long companyId, long groupId, long userId, Element portletElement)
		throws Exception {

		Element permissionsElement = portletElement.element("permissions");

		if (permissionsElement == null) {
			return;
		}

		List<Element> roleElements = permissionsElement.elements("role");

		for (Element roleElement : roleElements) {
			_checkRole(companyId, groupId, userId, roleElement);
		}
	}

	@Override
	public void clearCache() {
		_layoutCacheThreadLocal.remove();
	}

	@Override
	public void importPortletPermissions(
			long companyId, long groupId, long userId, Layout layout,
			Element portletElement, String portletId)
		throws Exception {

		Element permissionsElement = portletElement.element("permissions");

		if ((layout != null) && (permissionsElement != null)) {
			String resourceName = PortletIdCodec.decodePortletName(portletId);

			String resourcePrimKey = PortletPermissionUtil.getPrimaryKey(
				layout.getPlid(), portletId);

			_importPermissions(
				companyId, groupId, userId, layout, resourceName,
				resourcePrimKey, permissionsElement);
		}
	}

	@Override
	public void readPortletDataPermissions(
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

	@Activate
	protected void activate() {
		_layoutCacheThreadLocal = new CentralizedThreadLocal<>(
			PermissionImporter.class.getName() + "._layoutCacheThreadLocal",
			LayoutCache::new);
	}

	private Role _checkRole(
			long companyId, long groupId, long userId, Element roleElement)
		throws Exception {

		String name = roleElement.attributeValue("name");

		Role role = null;

		if (ExportImportPermissionUtil.isTeamRoleName(name)) {
			name = name.substring(
				ExportImportPermissionUtil.ROLE_TEAM_PREFIX.length());

			String description = roleElement.attributeValue("description");

			Team team = null;

			try {
				team = _teamLocalService.getTeam(groupId, name);
			}
			catch (NoSuchTeamException nste) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(nste, nste);
				}

				team = _teamLocalService.addTeam(
					userId, groupId, name, description, new ServiceContext());
			}

			role = _roleLocalService.getTeamRole(companyId, team.getTeamId());

			return role;
		}

		String uuid = roleElement.attributeValue("uuid");

		LayoutCache layoutCache = _layoutCacheThreadLocal.get();

		role = layoutCache.getUuidRole(companyId, uuid);

		if (role == null) {
			role = layoutCache.getNameRole(companyId, name);
		}

		if ((role != null) || MergeLayoutPrototypesThreadLocal.isInProgress()) {
			return role;
		}

		String title = roleElement.attributeValue("title");

		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			title);

		String description = roleElement.attributeValue("description");

		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(description);

		int type = GetterUtil.getInteger(roleElement.attributeValue("type"));
		String subtype = roleElement.attributeValue("subtype");

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUuid(uuid);

		role = _roleLocalService.addRole(
			userId, null, 0, name, titleMap, descriptionMap, type, subtype,
			serviceContext);

		return role;
	}

	private List<String> _getActions(Element element) {
		List<String> actions = new ArrayList<>();

		List<Element> actionKeyElements = element.elements("action-key");

		for (Element actionKeyElement : actionKeyElements) {
			actions.add(actionKeyElement.getText());
		}

		return actions;
	}

	private void _importPermissions(
			long companyId, long groupId, long userId, Layout layout,
			String resourceName, String resourcePrimKey,
			Element permissionsElement)
		throws Exception {

		Map<Long, Set<String>> existingRoleIdsToActionIds =
			ExportImportPermissionUtil.getRoleIdsToActionIds(
				companyId, resourceName, resourcePrimKey);

		Map<Long, String[]> importedRoleIdsToActionIds = new HashMap<>();

		List<Element> roleElements = permissionsElement.elements("role");

		for (Element roleElement : roleElements) {
			Role role = _checkRole(companyId, groupId, userId, roleElement);

			if (role == null) {
				continue;
			}

			Group group = _groupLocalService.getGroup(groupId);

			if (!group.isLayoutPrototype() && !group.isLayoutSetPrototype() &&
				layout.isPrivateLayout()) {

				String roleName = role.getName();

				if (roleName.equals(RoleConstants.GUEST)) {
					continue;
				}
			}

			List<String> actions = _getActions(roleElement);

			importedRoleIdsToActionIds.put(
				role.getRoleId(), actions.toArray(new String[0]));
		}

		Map<Long, String[]> roleIdsToActionIds =
			ExportImportPermissionUtil.
				mergeImportedPermissionsWithExistingPermissions(
					existingRoleIdsToActionIds, importedRoleIdsToActionIds);

		ExportImportPermissionUtil.updateResourcePermissions(
			companyId, groupId, resourceName, resourcePrimKey,
			roleIdsToActionIds);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PermissionImporterImpl.class);

	@Reference
	private GroupLocalService _groupLocalService;

	private CentralizedThreadLocal<LayoutCache> _layoutCacheThreadLocal;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private TeamLocalService _teamLocalService;

}