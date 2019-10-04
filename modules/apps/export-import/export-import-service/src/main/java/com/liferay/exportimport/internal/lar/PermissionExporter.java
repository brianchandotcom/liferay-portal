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
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

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

	public static PermissionExporter getInstance() {
		return _permissionExporter;
	}

	public void exportPortletDataPermissions(
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

	public void exportPortletPermissions(
			PortletDataContext portletDataContext, String portletId,
			Layout layout, Element portletElement)
		throws Exception {

		String resourceName = PortletIdCodec.decodePortletName(portletId);

		String resourcePrimKey = StringPool.BLANK;

		if (layout != null) {
			resourcePrimKey = PortletPermissionUtil.getPrimaryKey(
				layout.getPlid(), portletId);
		}
		else {
			resourcePrimKey = PortletPermissionUtil.getPrimaryKey(
				LayoutConstants.DEFAULT_PLID, portletId);
		}

		Element permissionsElement = portletElement.addElement("permissions");

		exportPermissions(
			portletDataContext, resourceName, resourcePrimKey,
			permissionsElement);
	}

	protected void exportPermissions(
			PortletDataContext portletDataContext, String resourceName,
			String resourcePrimKey, Element permissionsElement)
		throws Exception {

		List<String> actionIds = ResourceActionsUtil.getPortletResourceActions(
			resourceName);

		Map<Long, Set<String>> roleToActionIds =
			ExportImportPermissionUtil.getRoleIdsToActionIds(
				portletDataContext.getCompanyId(), resourceName,
				resourcePrimKey, actionIds);

		for (Map.Entry<Long, Set<String>> entry : roleToActionIds.entrySet()) {
			long roleId = entry.getKey();

			Role role = RoleLocalServiceUtil.fetchRole(roleId);

			String roleName = role.getName();

			if (role.isTeam()) {
				try {
					roleName = ExportImportPermissionUtil.getTeamRoleName(
						role.getDescriptiveName());
				}
				catch (PortalException pe) {

					// LPS-52675

					if (_log.isDebugEnabled()) {
						_log.debug(pe, pe);
					}
				}
			}

			Element roleElement = permissionsElement.addElement("role");

			roleElement.addAttribute("uuid", role.getUuid());
			roleElement.addAttribute("name", roleName);
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

	private PermissionExporter() {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PermissionExporter.class);

	private static final PermissionExporter _permissionExporter =
		new PermissionExporter();

}