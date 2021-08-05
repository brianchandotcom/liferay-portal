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

package com.liferay.commerce.price.list.internal.permission;

import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.permission.CommercePriceListPermission;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
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
	service = CommercePriceListPermission.class
)
public class CommercePriceListPermissionImpl
	implements CommercePriceListPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommercePriceList commercePriceList, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, commercePriceList, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommercePriceList.class.getName(),
				commercePriceList.getCommercePriceListId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commercePriceListId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, commercePriceListId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommercePriceList.class.getName(),
				commercePriceListId, actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommercePriceList commercePriceList, String actionId)
		throws PortalException {

		if (contains(
				permissionChecker, commercePriceList.getCommercePriceListId(),
				actionId)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commercePriceListId,
			String actionId)
		throws PortalException {

		CommercePriceList commercePriceList =
			_commercePriceListLocalService.fetchCommercePriceList(
				commercePriceListId);

		if (commercePriceList == null) {
			return false;
		}

		return _contains(permissionChecker, commercePriceList, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long[] commercePriceListIds,
			String actionId)
		throws PortalException {

		if (ArrayUtil.isEmpty(commercePriceListIds)) {
			return false;
		}

		for (long commercePriceListId : commercePriceListIds) {
			if (!contains(permissionChecker, commercePriceListId, actionId)) {
				return false;
			}
		}

		return true;
	}

	private boolean _contains(
			PermissionChecker permissionChecker,
			CommercePriceList commercePriceList, String actionId)
		throws PortalException {

		if (permissionChecker.isCompanyAdmin(
				commercePriceList.getCompanyId()) ||
			permissionChecker.isOmniadmin() ||
			permissionChecker.hasOwnerPermission(
				commercePriceList.getCompanyId(),
				CommercePriceList.class.getName(),
				commercePriceList.getCommercePriceListId(),
				commercePriceList.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			commercePriceList.getGroupId(), CommercePriceList.class.getName(),
			commercePriceList.getCommercePriceListId(), actionId);
	}

	@Reference
	private CommercePriceListLocalService _commercePriceListLocalService;

}