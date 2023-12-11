/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.util;

import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.permission.provider.InfoPermissionProvider;
import com.liferay.layout.content.page.editor.web.internal.constants.ContentPageEditorConstants;
import com.liferay.layout.page.template.info.item.capability.EditPageInfoItemCapability;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Eudaldo Alonso
 */
public class ObjectUtil {

	public static Map<String, List<Map<String, Object>>>
		getLayoutElementMapsListMap(
			InfoItemServiceRegistry infoItemServiceRegistry,
			PermissionChecker permissionChecker) {

		Map<String, List<Map<String, Object>>> layoutElementMapsListMap =
			new HashMap<>(ContentPageEditorConstants.layoutElementMapsListMap);

		if (hideInputFragments(infoItemServiceRegistry, permissionChecker)) {
			layoutElementMapsListMap.remove("INPUTS");
		}

		return layoutElementMapsListMap;
	}

	public static Boolean hideInputFragments(
		InfoItemServiceRegistry infoItemServiceRegistry,
		PermissionChecker permissionChecker) {

		for (InfoItemClassDetails infoItemClassDetails :
				infoItemServiceRegistry.getInfoItemClassDetails(
					EditPageInfoItemCapability.KEY)) {

			InfoPermissionProvider infoPermissionProvider =
				infoItemServiceRegistry.getFirstInfoItemService(
					InfoPermissionProvider.class,
					infoItemClassDetails.getClassName());

			if ((infoPermissionProvider == null) ||
				infoPermissionProvider.hasViewPermission(permissionChecker)) {

				return false;
			}
		}

		return true;
	}

}