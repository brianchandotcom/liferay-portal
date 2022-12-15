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
import com.liferay.commerce.product.model.CommerceChannelAccountEntryRel;
import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelService;
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
	property = "model.class.name=com.liferay.commerce.model.CommerceChannelAccountEntryRel",
	service = ModelResourcePermission.class
)
public class CommerceChannelAccountEntryRelModelResourcePermission
	implements ModelResourcePermission<CommerceChannelAccountEntryRel> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceChannelAccountEntryRel commerceChannelAccountEntryRel,
			String actionId)
		throws PortalException {

		if (!contains(
				permissionChecker, commerceChannelAccountEntryRel, actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker,
				CommerceChannelAccountEntryRel.class.getName(),
				commerceChannelAccountEntryRel.
					getCommerceChannelAccountEntryRelId(),
				actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			long commerceChannelAccountEntryRelId, String actionId)
		throws PortalException {

		if (!contains(
				permissionChecker, commerceChannelAccountEntryRelId,
				actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker,
				CommerceChannelAccountEntryRel.class.getName(),
				commerceChannelAccountEntryRelId, actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceChannelAccountEntryRel commerceChannelAccountEntryRel,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			commerceChannelAccountEntryRel.
				getCommerceChannelAccountEntryRelId(),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			long commerceChannelAccountEntryRelId, String actionId)
		throws PortalException {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_commerceChannelAccountEntryRelService.
				getCommerceChannelAccountEntryRel(
					commerceChannelAccountEntryRelId);

		if (permissionChecker.isCompanyAdmin(
				commerceChannelAccountEntryRel.getCompanyId()) ||
			permissionChecker.isOmniadmin()) {

			return true;
		}

		if (permissionChecker.hasOwnerPermission(
				permissionChecker.getCompanyId(),
				CommerceChannelAccountEntryRel.class.getName(),
				commerceChannelAccountEntryRelId, permissionChecker.getUserId(),
				actionId) &&
			(commerceChannelAccountEntryRel.getUserId() ==
				permissionChecker.getUserId())) {

			return true;
		}

		return _accountEntryModelResourcePermission.contains(
			permissionChecker,
			commerceChannelAccountEntryRel.getAccountEntryId(), actionId);
	}

	@Override
	public String getModelName() {
		return CommerceChannelAccountEntryRel.class.getName();
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
	private CommerceChannelAccountEntryRelService
		_commerceChannelAccountEntryRelService;

}