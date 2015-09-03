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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletCategory;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Eudaldo Alonso
 */
public class PortletCategoryUtil {

	public static String getPortletCategoryKey(
		String legacyPortletCategoryKey) {

		if (Validator.equals(legacyPortletCategoryKey, "content")) {
			legacyPortletCategoryKey =
				PortletCategoryKeys.SITE_ADMINISTRATION_CONTENT;
		}
		else if (Validator.equals(legacyPortletCategoryKey, "marketplace")) {
			legacyPortletCategoryKey = PortletCategoryKeys.APPS;
		}
		else if (Validator.equals(legacyPortletCategoryKey, "portal")) {
			legacyPortletCategoryKey = PortletCategoryKeys.USERS;
		}
		else if (Validator.equals(legacyPortletCategoryKey, "server")) {
			legacyPortletCategoryKey = PortletCategoryKeys.APPS;
		}

		return legacyPortletCategoryKey;
	}

	public static PortletCategory getRelevantPortletCategory(
			PermissionChecker permissionChecker, long companyId, Layout layout,
			PortletCategory portletCategory,
			LayoutTypePortlet layoutTypePortlet)
		throws Exception {

		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		Set<String> panelSelectedPortletIds = SetUtil.fromArray(
			StringUtil.split(
				typeSettingsProperties.getProperty("panelSelectedPortlets")));

		return getRelevantPortletCategory(
			permissionChecker, companyId, layout, portletCategory,
			panelSelectedPortletIds, layoutTypePortlet);
	}

	protected static PortletCategory getRelevantPortletCategory(
			PermissionChecker permissionChecker, long companyId, Layout layout,
			PortletCategory portletCategory,
			Set<String> panelSelectedPortletIds,
			LayoutTypePortlet layoutTypePortlet)
		throws Exception {

		PortletCategory relevantPortletCategory = new PortletCategory(
			portletCategory.getName(), portletCategory.getPortletIds());

		for (PortletCategory curPortletCategory :
				portletCategory.getCategories()) {

			Set<String> portletIds = new HashSet<>();

			if (curPortletCategory.isHidden()) {
				continue;
			}

			for (String portletId : curPortletCategory.getPortletIds()) {
				Portlet portlet = PortletLocalServiceUtil.getPortletById(
					companyId, portletId);

				if (portlet != null) {
					if (portlet.isSystem()) {
					}
					else if (!portlet.isActive() ||
							 portlet.isUndeployedPortlet()) {
					}
					else if (layout.isTypePanel() &&
							 panelSelectedPortletIds.contains(
								 portlet.getRootPortletId())) {

						portletIds.add(portlet.getPortletId());
					}
					else if (layout.isTypePanel() &&
							 !panelSelectedPortletIds.contains(
								 portlet.getRootPortletId())) {
					}
					else if (!PortletPermissionUtil.contains(
								permissionChecker, layout, portlet,
								ActionKeys.ADD_TO_PAGE)) {
					}
					else if (!portlet.isInstanceable() &&
							 layoutTypePortlet.hasPortletId(
								 portlet.getPortletId())) {

						portletIds.add(portlet.getPortletId());
					}
					else {
						portletIds.add(portlet.getPortletId());
					}
				}
			}

			PortletCategory curRelevantPortletCategory =
				getRelevantPortletCategory(
					permissionChecker, companyId, layout, curPortletCategory,
					panelSelectedPortletIds, layoutTypePortlet);

			curRelevantPortletCategory.setPortletIds(portletIds);

			if (!curRelevantPortletCategory.getCategories().isEmpty() ||
				!portletIds.isEmpty()) {

				relevantPortletCategory.addCategory(curRelevantPortletCategory);
			}
		}

		return relevantPortletCategory;
	}

}