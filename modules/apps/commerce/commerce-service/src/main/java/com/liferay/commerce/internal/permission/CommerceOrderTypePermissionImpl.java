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

package com.liferay.commerce.internal.permission;

import com.liferay.commerce.internal.context.CommerceGroupThreadLocal;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.permission.CommerceOrderTypePermission;
import com.liferay.commerce.service.CommerceOrderTypeLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ArrayUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	enabled = false, immediate = true,
	service = CommerceOrderTypePermission.class
)
public class CommerceOrderTypePermissionImpl
	implements CommerceOrderTypePermission {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceOrderType commerceOrderType, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, commerceOrderType, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceOrderType.class.getName(),
				commerceOrderType.getCommerceOrderTypeId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commerceOrderTypeId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, commerceOrderTypeId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceOrderType.class.getName(),
				commerceOrderTypeId, actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceOrderType commerceOrderType, String actionId)
		throws PortalException {

		if (contains(
				permissionChecker, commerceOrderType.getCommerceOrderTypeId(),
				actionId)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commerceOrderTypeId,
			String actionId)
		throws PortalException {

		CommerceOrderType commerceOrderType =
			_commerceOrderTypeLocalService.fetchCommerceOrderType(
				commerceOrderTypeId);

		if (commerceOrderType == null) {
			return false;
		}

		return _contains(permissionChecker, commerceOrderType, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long[] commerceOrderTypeIds,
			String actionId)
		throws PortalException {

		if (ArrayUtil.isEmpty(commerceOrderTypeIds)) {
			return false;
		}

		for (long commerceOrderTypeId : commerceOrderTypeIds) {
			if (!contains(permissionChecker, commerceOrderTypeId, actionId)) {
				return false;
			}
		}

		return true;
	}

	private boolean _contains(
			PermissionChecker permissionChecker,
			CommerceOrderType commerceOrderType, String actionId)
		throws PortalException {

		if (permissionChecker.isCompanyAdmin(
				commerceOrderType.getCompanyId()) ||
			permissionChecker.isOmniadmin()) {

			return true;
		}

		if (permissionChecker.hasOwnerPermission(
				permissionChecker.getCompanyId(),
				CommerceOrderType.class.getName(),
				commerceOrderType.getCommerceOrderTypeId(),
				permissionChecker.getUserId(), actionId) &&
			(commerceOrderType.getUserId() == permissionChecker.getUserId())) {

			return true;
		}

		return permissionChecker.hasPermission(
			CommerceGroupThreadLocal.get(), CommerceOrderType.class.getName(),
			commerceOrderType.getCommerceOrderTypeId(), actionId);
	}

	@Reference
	private CommerceOrderTypeLocalService _commerceOrderTypeLocalService;

}