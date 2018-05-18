/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.order.web.security.permission.resource;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(immediate = true)
public class CommerceOrderPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, CommerceOrder commerceOrder,
			String actionId)
		throws PortalException {

		return _commerceOrderModelResourcePermission.contains(
			permissionChecker, commerceOrder, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long commerceOrderId,
			String actionId)
		throws PortalException {

		return _commerceOrderModelResourcePermission.contains(
			permissionChecker, commerceOrderId, actionId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CommerceOrder)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<CommerceOrder> modelResourcePermission) {

		_commerceOrderModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission<CommerceOrder>
		_commerceOrderModelResourcePermission;

}