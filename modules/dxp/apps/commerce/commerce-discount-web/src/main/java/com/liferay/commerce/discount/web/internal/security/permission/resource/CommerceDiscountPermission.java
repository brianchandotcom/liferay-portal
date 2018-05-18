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

package com.liferay.commerce.discount.web.internal.security.permission.resource;

import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true)
public class CommerceDiscountPermission {

	public static boolean contains(
			PermissionChecker permissionChecker,
			CommerceDiscount commerceDiscount, String actionId)
		throws PortalException {

		return _commerceDiscountModelResourcePermission.contains(
			permissionChecker, commerceDiscount, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long commerceDiscountId,
			String actionId)
		throws PortalException {

		return _commerceDiscountModelResourcePermission.contains(
			permissionChecker, commerceDiscountId, actionId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.discount.model.CommerceDiscount)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<CommerceDiscount> modelResourcePermission) {

		_commerceDiscountModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission<CommerceDiscount>
		_commerceDiscountModelResourcePermission;

}