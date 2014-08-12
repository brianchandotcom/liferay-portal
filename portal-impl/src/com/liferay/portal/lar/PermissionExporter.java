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

import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @author Joel Kozikowski
 * @author Charles May
 * @author Raymond Augé
 * @author Jorge Ferrer
 * @author Bruno Farache
 * @author Zsigmond Rab
 * @author Douglas Wong
 */
public class PermissionExporter {

	public static final String ROLE_TEAM_PREFIX = "ROLE_TEAM_,*";

	public static PermissionExporter getInstance() {
		return _instance;
	}

	protected void exportPermissions(
			PortletDataContext portletDataContext, String resourceName,
			String resourcePrimKey, Element permissionsElement)
		throws Exception {

		List<String> actionIds = ResourceActionsUtil.getPortletResourceActions(
			resourceName);

		Map<Long, Set<String>> roleToActionIds =
			ResourcePermissionLocalServiceUtil.
				getAvailableResourcePermissionActionIds(
					portletDataContext.getCompanyId(), resourceName,
					ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey,
					actionIds);

		for (Map.Entry<Long, Set<String>> entry : roleToActionIds.entrySet()) {
			long roleId = entry.getKey();

			Role role = RoleLocalServiceUtil.fetchRole(roleId);

			Element roleElement = permissionsElement.addElement("role");

			roleElement.addAttribute("name", role.getName());
			roleElement.addAttribute("title", role.getTitle());
			roleElement.addAttribute("description", role.getDescription());
			roleElement.addAttribute("type", String.valueOf(role.getType()));
			roleElement.addAttribute("subtype", role.getSubtype());

			Set<String> availableActionIds = entry.getValue();

			for (String actionId : availableActionIds) {
				Element actionKeyElement = roleElement.addElement("action-key");

				actionKeyElement.addText(actionId);
			}
		}
	}

	protected void exportPortletDataPermissions(
			PortletDataContext portletDataContext)
		throws Exception {

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("portlet-data-permissions");

		Map<String, List<KeyValuePair>> permissionsMap =
			portletDataContext.getPermissions();

		for (Map.Entry<String, List<KeyValuePair>> entry :
				permissionsMap.entrySet()) {

			String[] permissionParts = StringUtil.split(
				entry.getKey(), CharPool.POUND);

			String resourceName = permissionParts[0];
			long resourcePK = GetterUtil.getLong(permissionParts[1]);

			Element portletDataElement = rootElement.addElement("portlet-data");

			portletDataElement.addAttribute("resource-name", resourceName);
			portletDataElement.addAttribute(
				"resource-pk", String.valueOf(resourcePK));

			List<KeyValuePair> permissions = entry.getValue();

			for (KeyValuePair permission : permissions) {
				String roleName = permission.getKey();
				String actions = permission.getValue();

				Element permissionsElement = portletDataElement.addElement(
					"permissions");

				permissionsElement.addAttribute("role-name", roleName);
				permissionsElement.addAttribute("actions", actions);
			}
		}

		portletDataContext.addZipEntry(
			ExportImportPathUtil.getRootPath(portletDataContext) +
				"/portlet-data-permissions.xml",
			document.formattedString());
	}

	protected void exportPortletPermissions(
			PortletDataContext portletDataContext, String portletId,
			Layout layout, Element portletElement)
		throws Exception {

		String resourceName = PortletConstants.getRootPortletId(portletId);
		String resourcePrimKey = PortletPermissionUtil.getPrimaryKey(
			layout.getPlid(), portletId);

		Element permissionsElement = portletElement.addElement("permissions");

		exportPermissions(
			portletDataContext, resourceName, resourcePrimKey,
			permissionsElement);
	}

	private PermissionExporter() {
	}

	private static final PermissionExporter _instance =
		new PermissionExporter();

}