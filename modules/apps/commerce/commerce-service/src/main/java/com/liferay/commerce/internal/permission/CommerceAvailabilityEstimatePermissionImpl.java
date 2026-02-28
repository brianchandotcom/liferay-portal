/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.permission;

import com.liferay.commerce.model.CommerceAvailabilityEstimate;
import com.liferay.commerce.permission.CommerceAvailabilityEstimatePermission;
import com.liferay.commerce.service.CommerceAvailabilityEstimateLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ArrayUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Crescenzo Rega
 */
@Component(service = CommerceAvailabilityEstimatePermission.class)
public class CommerceAvailabilityEstimatePermissionImpl
	implements CommerceAvailabilityEstimatePermission {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceAvailabilityEstimate commerceAvailabilityEstimate,
			String actionId)
		throws PortalException {

		if (!contains(
				permissionChecker, commerceAvailabilityEstimate, actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceAvailabilityEstimate.class.getName(),
				commerceAvailabilityEstimate.
					getCommerceAvailabilityEstimateId(),
				actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			long commerceAvailabilityEstimateId, String actionId)
		throws PortalException {

		if (!contains(
				permissionChecker, commerceAvailabilityEstimateId, actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceAvailabilityEstimate.class.getName(),
				commerceAvailabilityEstimateId, actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceAvailabilityEstimate commerceAvailabilityEstimate,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			commerceAvailabilityEstimate.getCommerceAvailabilityEstimateId(),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			long commerceAvailabilityEstimateId, String actionId)
		throws PortalException {

		CommerceAvailabilityEstimate commerceAvailabilityEstimate =
			_commerceAvailabilityEstimateLocalService.
				fetchCommerceAvailabilityEstimate(
					commerceAvailabilityEstimateId);

		if (commerceAvailabilityEstimate == null) {
			return false;
		}

		return _contains(
			permissionChecker, commerceAvailabilityEstimate, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			long[] commerceAvailabilityEstimateIds, String actionId)
		throws PortalException {

		if (ArrayUtil.isEmpty(commerceAvailabilityEstimateIds)) {
			return false;
		}

		for (long commerceAvailabilityEstimateId :
				commerceAvailabilityEstimateIds) {

			if (!contains(
					permissionChecker, commerceAvailabilityEstimateId,
					actionId)) {

				return false;
			}
		}

		return true;
	}

	private boolean _contains(
			PermissionChecker permissionChecker,
			CommerceAvailabilityEstimate commerceAvailabilityEstimate,
			String actionId)
		throws PortalException {

		if (permissionChecker.isCompanyAdmin(
				commerceAvailabilityEstimate.getCompanyId()) ||
			permissionChecker.hasOwnerPermission(
				commerceAvailabilityEstimate.getCompanyId(),
				CommerceAvailabilityEstimate.class.getName(),
				commerceAvailabilityEstimate.
					getCommerceAvailabilityEstimateId(),
				commerceAvailabilityEstimate.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			null, CommerceAvailabilityEstimate.class.getName(),
			commerceAvailabilityEstimate.getCommerceAvailabilityEstimateId(),
			actionId);
	}

	@Reference
	private CommerceAvailabilityEstimateLocalService
		_commerceAvailabilityEstimateLocalService;

}