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

package com.liferay.commerce.discount.internal.permission;

import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.permission.CommerceDiscountPermission;
import com.liferay.commerce.discount.service.CommerceDiscountLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ArrayUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = CommerceDiscountPermission.class)
public class CommerceDiscountPermissionImpl
	implements CommerceDiscountPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceDiscount commerceDiscount, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, commerceDiscount, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceDiscount.class.getName(),
				commerceDiscount.getCommerceDiscountId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commerceDiscountId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, commerceDiscountId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceDiscount.class.getName(),
				commerceDiscountId, actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceDiscount commerceDiscount, String actionId)
		throws PortalException {

		if (contains(
				permissionChecker, commerceDiscount.getCommerceDiscountId(),
				actionId)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commerceDiscountId,
			String actionId)
		throws PortalException {

		CommerceDiscount commerceDiscount =
			_commerceDiscountLocalService.getCommerceDiscount(
				commerceDiscountId);

		if (commerceDiscount == null) {
			return false;
		}

		return _contains(permissionChecker, commerceDiscount, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long[] commerceDiscountIds,
			String actionId)
		throws PortalException {

		if (ArrayUtil.isEmpty(commerceDiscountIds)) {
			return false;
		}

		for (long commerceDiscountId : commerceDiscountIds) {
			if (!contains(permissionChecker, commerceDiscountId, actionId)) {
				return false;
			}
		}

		return true;
	}

	private boolean _contains(
		PermissionChecker permissionChecker, CommerceDiscount commerceDiscount,
		String actionId) {

		if (permissionChecker.isCompanyAdmin(commerceDiscount.getCompanyId()) ||
			permissionChecker.isOmniadmin()) {

			return true;
		}

		if (permissionChecker.hasOwnerPermission(
				permissionChecker.getCompanyId(),
				CommerceDiscount.class.getName(),
				commerceDiscount.getCommerceDiscountId(),
				permissionChecker.getUserId(), actionId) &&
			(commerceDiscount.getUserId() == permissionChecker.getUserId())) {

			return true;
		}

		return permissionChecker.hasPermission(
			null, CommerceDiscount.class.getName(),
			commerceDiscount.getCommerceDiscountId(), actionId);
	}

	@Reference
	private CommerceDiscountLocalService _commerceDiscountLocalService;

}