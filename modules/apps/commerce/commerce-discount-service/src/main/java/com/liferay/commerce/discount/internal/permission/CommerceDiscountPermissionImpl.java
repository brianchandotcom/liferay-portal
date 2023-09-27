/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = CommerceDiscountPermission.class)
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