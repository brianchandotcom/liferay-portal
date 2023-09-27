/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.info.item.helper;

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
public class LayoutInfoItemPermissionProviderHelper {

	public LayoutInfoItemPermissionProviderHelper(
		ModelResourcePermission<Layout> layoutModelResourcePermission) {

		_layoutModelResourcePermission = layoutModelResourcePermission;
	}

	public boolean hasPermission(
			PermissionChecker permissionChecker,
			InfoItemReference infoItemReference, String actionId)
		throws InfoItemPermissionException {

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)
				infoItemReference.getInfoItemIdentifier();

		return _hasPermission(
			permissionChecker, classPKInfoItemIdentifier.getClassPK(),
			actionId);
	}

	public boolean hasPermission(
			PermissionChecker permissionChecker, Layout layout, String actionId)
		throws InfoItemPermissionException {

		return _hasPermission(permissionChecker, layout.getPlid(), actionId);
	}

	private boolean _hasPermission(
			PermissionChecker permissionChecker, long plid, String actionId)
		throws InfoItemPermissionException {

		try {
			return _layoutModelResourcePermission.contains(
				permissionChecker, plid, actionId);
		}
		catch (PortalException portalException) {
			throw new InfoItemPermissionException(plid, portalException);
		}
	}

	private final ModelResourcePermission<Layout>
		_layoutModelResourcePermission;

}