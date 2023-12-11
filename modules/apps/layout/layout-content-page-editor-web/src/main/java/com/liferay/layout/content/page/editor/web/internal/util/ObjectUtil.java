/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.util;

import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.permission.provider.InfoPermissionProvider;
import com.liferay.layout.page.template.info.item.capability.EditPageInfoItemCapability;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Eudaldo Alonso
 */
public class ObjectUtil {

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