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

package com.liferay.commerce.order.rule.internal.permission;

import com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry;
import com.liferay.commerce.order.rule.permission.CommerceOrderRuleEntryPermission;
import com.liferay.commerce.order.rule.service.CommerceOrderRuleEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ArrayUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	service = CommerceOrderRuleEntryPermission.class
)
public class CommerceOrderRuleEntryPermissionImpl
	implements CommerceOrderRuleEntryPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceOrderRuleEntry commerceOrderRuleEntry, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, commerceOrderRuleEntry, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceOrderRuleEntry.class.getName(),
				commerceOrderRuleEntry.getCommerceOrderRuleEntryId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commerceOrderRuleEntryId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, commerceOrderRuleEntryId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceOrderRuleEntry.class.getName(),
				commerceOrderRuleEntryId, actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceOrderRuleEntry commerceOrderRuleEntry, String actionId)
		throws PortalException {

		if (contains(
				permissionChecker,
				commerceOrderRuleEntry.getCommerceOrderRuleEntryId(),
				actionId)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commerceOrderRuleEntryId,
			String actionId)
		throws PortalException {

		CommerceOrderRuleEntry commerceOrderRuleEntry =
			_commerceOrderRuleEntryLocalService.fetchCommerceOrderRuleEntry(
				commerceOrderRuleEntryId);

		if (commerceOrderRuleEntry == null) {
			return false;
		}

		return _contains(permissionChecker, commerceOrderRuleEntry, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			long[] commerceOrderRuleEntryIds, String actionId)
		throws PortalException {

		if (ArrayUtil.isEmpty(commerceOrderRuleEntryIds)) {
			return false;
		}

		for (long commerceOrderRuleEntryId : commerceOrderRuleEntryIds) {
			if (!contains(
					permissionChecker, commerceOrderRuleEntryId, actionId)) {

				return false;
			}
		}

		return true;
	}

	private boolean _contains(
			PermissionChecker permissionChecker,
			CommerceOrderRuleEntry commerceOrderRuleEntry, String actionId)
		throws PortalException {

		if (permissionChecker.isCompanyAdmin(
				commerceOrderRuleEntry.getCompanyId()) ||
			permissionChecker.isOmniadmin() ||
			permissionChecker.hasOwnerPermission(
				commerceOrderRuleEntry.getCompanyId(),
				CommerceOrderRuleEntry.class.getName(),
				commerceOrderRuleEntry.getCommerceOrderRuleEntryId(),
				commerceOrderRuleEntry.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			null, CommerceOrderRuleEntry.class.getName(),
			commerceOrderRuleEntry.getCommerceOrderRuleEntryId(), actionId);
	}

	@Reference
	private CommerceOrderRuleEntryLocalService
		_commerceOrderRuleEntryLocalService;

}