/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.application.internal.permission;

import com.liferay.commerce.application.model.CommerceApplicationBrand;
import com.liferay.commerce.application.permission.CommerceApplicationBrandPermission;
import com.liferay.commerce.application.service.CommerceApplicationBrandLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ArrayUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = CommerceApplicationBrandPermission.class)
public class CommerceApplicationBrandPermissionImpl
	implements CommerceApplicationBrandPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceApplicationBrand commerceApplicationBrand, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, commerceApplicationBrand, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceApplicationBrand.class.getName(),
				commerceApplicationBrand.getCommerceApplicationBrandId(),
				actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			long commerceApplicationBrandId, String actionId)
		throws PortalException {

		if (!contains(
				permissionChecker, commerceApplicationBrandId, actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceApplicationBrand.class.getName(),
				commerceApplicationBrandId, actionId);
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker,
		CommerceApplicationBrand commerceApplicationBrand, String actionId) {

		if (contains(
				permissionChecker,
				commerceApplicationBrand.getCommerceApplicationBrandId(),
				actionId)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long commerceApplicationBrandId,
		String actionId) {

		CommerceApplicationBrand commerceApplicationBrand =
			_commerceApplicationBrandLocalService.fetchCommerceApplicationBrand(
				commerceApplicationBrandId);

		if (commerceApplicationBrand == null) {
			return false;
		}

		return _contains(permissionChecker, commerceApplicationBrand, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			long[] commerceApplicationBrandIds, String actionId)
		throws PortalException {

		if (ArrayUtil.isEmpty(commerceApplicationBrandIds)) {
			return false;
		}

		for (long commerceApplicationBrandId : commerceApplicationBrandIds) {
			if (!contains(
					permissionChecker, commerceApplicationBrandId, actionId)) {

				return false;
			}
		}

		return true;
	}

	private boolean _contains(
		PermissionChecker permissionChecker,
		CommerceApplicationBrand commerceApplicationBrand, String actionId) {

		if (permissionChecker.isCompanyAdmin(
				commerceApplicationBrand.getCompanyId()) ||
			permissionChecker.isOmniadmin()) {

			return true;
		}

		if (permissionChecker.hasOwnerPermission(
				permissionChecker.getCompanyId(),
				CommerceApplicationBrand.class.getName(),
				commerceApplicationBrand.getCommerceApplicationBrandId(),
				permissionChecker.getUserId(), actionId) &&
			(commerceApplicationBrand.getUserId() ==
				permissionChecker.getUserId())) {

			return true;
		}

		return permissionChecker.hasPermission(
			null, CommerceApplicationBrand.class.getName(),
			commerceApplicationBrand.getCommerceApplicationBrandId(), actionId);
	}

	@Reference
	private CommerceApplicationBrandLocalService
		_commerceApplicationBrandLocalService;

}