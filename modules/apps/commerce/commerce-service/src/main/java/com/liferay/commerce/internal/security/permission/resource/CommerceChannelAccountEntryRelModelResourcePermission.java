/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	property = "model.class.name=com.liferay.commerce.product.model.CommerceChannelAccountEntryRel",
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