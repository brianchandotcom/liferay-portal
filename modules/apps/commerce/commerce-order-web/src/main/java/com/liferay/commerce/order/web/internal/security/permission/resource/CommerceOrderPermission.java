/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.web.internal.security.permission.resource;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

/**
 * @author Marco Leo
 */
public class CommerceOrderPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, CommerceOrder commerceOrder,
			String actionId)
		throws PortalException {

		ModelResourcePermission<CommerceOrder>
			commerceOrderModelResourcePermission =
				_commerceOrderModelResourcePermissionSnapshot.get();

		return commerceOrderModelResourcePermission.contains(
			permissionChecker, commerceOrder, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long commerceOrderId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<CommerceOrder>
			commerceOrderModelResourcePermission =
				_commerceOrderModelResourcePermissionSnapshot.get();

		return commerceOrderModelResourcePermission.contains(
			permissionChecker, commerceOrderId, actionId);
	}

	private static final Snapshot<ModelResourcePermission<CommerceOrder>>
		_commerceOrderModelResourcePermissionSnapshot = new Snapshot<>(
			CommerceOrderPermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.commerce.model.CommerceOrder)");

}