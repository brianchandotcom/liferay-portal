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

package com.liferay.commerce.internal.security.permission.resource;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.model.CommerceShippingOptionAccountEntryRel;
import com.liferay.commerce.service.CommerceShippingOptionAccountEntryRelService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Danny Situ
 */
@Component(
	property = "model.class.name=com.liferay.commerce.model.CommerceShippingOptionAccountEntryRel",
	service = ModelResourcePermission.class
)
public class CommerceShippingOptionAccountEntryRelModelResourcePermission
	implements ModelResourcePermission<CommerceShippingOptionAccountEntryRel> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceShippingOptionAccountEntryRel
				commerceShippingOptionAccountEntryRel,
			String actionId)
		throws PortalException {

		if (!contains(
				permissionChecker, commerceShippingOptionAccountEntryRel,
				actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker,
				CommerceShippingOptionAccountEntryRel.class.getName(),
				commerceShippingOptionAccountEntryRel.
					getCommerceShippingOptionAccountEntryRelId(),
				actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			long commerceShippingOptionAccountEntryRelId, String actionId)
		throws PortalException {

		if (!contains(
				permissionChecker, commerceShippingOptionAccountEntryRelId,
				actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker,
				CommerceShippingOptionAccountEntryRel.class.getName(),
				commerceShippingOptionAccountEntryRelId, actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceShippingOptionAccountEntryRel
				commerceShippingOptionAccountEntryRel,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			commerceShippingOptionAccountEntryRel.
				getCommerceShippingOptionAccountEntryRelId(),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			long commerceShippingOptionAccountEntryRelId, String actionId)
		throws PortalException {

		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel =
				_commerceShippingOptionAccountEntryRelService.
					getCommerceShippingOptionAccountEntryRel(
						commerceShippingOptionAccountEntryRelId);

		if (permissionChecker.isCompanyAdmin(
				commerceShippingOptionAccountEntryRel.getCompanyId()) ||
			permissionChecker.isOmniadmin()) {

			return true;
		}

		if (permissionChecker.hasOwnerPermission(
				permissionChecker.getCompanyId(),
				CommerceShippingOptionAccountEntryRel.class.getName(),
				commerceShippingOptionAccountEntryRelId,
				permissionChecker.getUserId(), actionId) &&
			(commerceShippingOptionAccountEntryRel.getUserId() ==
				permissionChecker.getUserId())) {

			return true;
		}

		return _accountEntryModelResourcePermission.contains(
			permissionChecker,
			commerceShippingOptionAccountEntryRel.getAccountEntryId(),
			actionId);
	}

	@Override
	public String getModelName() {
		return CommerceShippingOptionAccountEntryRel.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.account.model.AccountEntry)"
	)
	private volatile ModelResourcePermission<AccountEntry>
		_accountEntryModelResourcePermission;

	@Reference
	private CommerceShippingOptionAccountEntryRelService
		_commerceShippingOptionAccountEntryRelService;

}