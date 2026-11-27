/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.util;

import com.liferay.info.exception.InfoItemPermissionException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

/**
 * @author Adolfo Pérez
 */
public class LayoutInfoItemPermissionProviderUtil {

	public static boolean hasPermission(
			String actionId, InfoItemReference infoItemReference,
			ModelResourcePermission<Layout> layoutModelResourcePermission,
			PermissionChecker permissionChecker)
		throws InfoItemPermissionException {

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)
				infoItemReference.getInfoItemIdentifier();

		return _hasPermission(
			actionId, layoutModelResourcePermission, permissionChecker,
			classPKInfoItemIdentifier.getClassPK());
	}

	public static boolean hasPermission(
			String actionId, Layout layout,
			ModelResourcePermission<Layout> layoutModelResourcePermission,
			PermissionChecker permissionChecker)
		throws InfoItemPermissionException {

		return _hasPermission(
			actionId, layoutModelResourcePermission, permissionChecker,
			layout.getPlid());
	}

	private static boolean _hasPermission(
			String actionId,
			ModelResourcePermission<Layout> layoutModelResourcePermission,
			PermissionChecker permissionChecker, long plid)
		throws InfoItemPermissionException {

		try {
			return layoutModelResourcePermission.contains(
				permissionChecker, plid, actionId);
		}
		catch (PortalException portalException) {
			throw new InfoItemPermissionException(plid, portalException);
		}
	}

}