/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.internal.security.permission.resource;

import com.liferay.commerce.payment.model.CommercePaymentEntry;
import com.liferay.commerce.payment.service.CommercePaymentEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import javax.annotation.Resource;

import org.osgi.service.component.annotations.Component;

/**
 * @author Luca Pellizzon
 */
@Component(
	property = "model.class.name=com.liferay.commerce.payment.model.CommercePaymentEntry",
	service = ModelResourcePermission.class
)
public class CommercePaymentEntryModelResourcePermission
	implements ModelResourcePermission<CommercePaymentEntry> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommercePaymentEntry commercePaymentEntry, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, commercePaymentEntry, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommercePaymentEntry.class.getName(),
				commercePaymentEntry.getCommercePaymentEntryId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commercePaymentEntryId,
			String actionId)
		throws PortalException {

		CommercePaymentEntry commercePaymentEntry =
			_commercePaymentEntryLocalService.getCommercePaymentEntry(
				commercePaymentEntryId);

		if (!contains(permissionChecker, commercePaymentEntry, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommercePaymentEntry.class.getName(),
				commercePaymentEntry.getCommercePaymentEntryId(), actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommercePaymentEntry commercePaymentEntry, String actionId)
		throws PortalException {

		return _contains(permissionChecker, commercePaymentEntry, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commercePaymentEntryId,
			String actionId)
		throws PortalException {

		return _contains(
			permissionChecker,
			_commercePaymentEntryLocalService.getCommercePaymentEntry(
				commercePaymentEntryId),
			actionId);
	}

	@Override
	public String getModelName() {
		return null;
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	private boolean _contains(
		PermissionChecker permissionChecker,
		CommercePaymentEntry commercePaymentEntry, String actionId) {

		if (permissionChecker.isCompanyAdmin(
				commercePaymentEntry.getCompanyId()) ||
			permissionChecker.isOmniadmin()) {

			return true;
		}

		if (permissionChecker.hasOwnerPermission(
				permissionChecker.getCompanyId(),
				CommercePaymentEntry.class.getName(),
				commercePaymentEntry.getCommercePaymentEntryId(),
				permissionChecker.getUserId(), actionId) &&
			(commercePaymentEntry.getUserId() ==
				permissionChecker.getUserId())) {

			return true;
		}

		return permissionChecker.hasPermission(
			null, CommercePaymentEntry.class.getName(),
			commercePaymentEntry.getCommercePaymentEntryId(), actionId);
	}

	@Resource
	private CommercePaymentEntryLocalService _commercePaymentEntryLocalService;

}