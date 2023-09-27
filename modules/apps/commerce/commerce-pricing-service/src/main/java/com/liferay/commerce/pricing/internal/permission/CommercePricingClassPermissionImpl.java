/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.internal.permission;

import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.permission.CommercePricingClassPermission;
import com.liferay.commerce.pricing.service.CommercePricingClassLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ArrayUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(service = CommercePricingClassPermission.class)
public class CommercePricingClassPermissionImpl
	implements CommercePricingClassPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommercePricingClass commercePricingClass, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, commercePricingClass, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommercePricingClass.class.getName(),
				commercePricingClass.getCommercePricingClassId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commercePricingClassId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, commercePricingClassId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommercePricingClass.class.getName(),
				commercePricingClassId, actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommercePricingClass commercePricingClass, String actionId)
		throws PortalException {

		if (contains(
				permissionChecker,
				commercePricingClass.getCommercePricingClassId(), actionId)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commercePricingClassId,
			String actionId)
		throws PortalException {

		CommercePricingClass commercePricingClass =
			_commercePricingClassLocalService.getCommercePricingClass(
				commercePricingClassId);

		if (commercePricingClass == null) {
			return false;
		}

		return _contains(permissionChecker, commercePricingClass, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long[] commercePricingClassIds,
			String actionId)
		throws PortalException {

		if (ArrayUtil.isEmpty(commercePricingClassIds)) {
			return false;
		}

		for (long commercePricingClassId : commercePricingClassIds) {
			if (!contains(
					permissionChecker, commercePricingClassId, actionId)) {

				return false;
			}
		}

		return true;
	}

	private boolean _contains(
		PermissionChecker permissionChecker,
		CommercePricingClass commercePricingClass, String actionId) {

		if (permissionChecker.isCompanyAdmin(
				commercePricingClass.getCompanyId()) ||
			permissionChecker.isOmniadmin() ||
			permissionChecker.hasOwnerPermission(
				commercePricingClass.getCompanyId(),
				CommercePricingClass.class.getName(),
				commercePricingClass.getCommercePricingClassId(),
				commercePricingClass.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			null, CommercePricingClass.class.getName(),
			commercePricingClass.getCommercePricingClassId(), actionId);
	}

	@Reference
	private CommercePricingClassLocalService _commercePricingClassLocalService;

}