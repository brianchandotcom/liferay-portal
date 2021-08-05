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

package com.liferay.commerce.product.internal.permission;

import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.permission.CPSpecificationOptionPermission;
import com.liferay.commerce.product.service.CPSpecificationOptionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ArrayUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	enabled = false, immediate = true,
	service = CPSpecificationOptionPermission.class
)
public class CPSpecificationOptionPermissionImpl
	implements CPSpecificationOptionPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CPSpecificationOption cpSpecificationOption, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, cpSpecificationOption, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CPSpecificationOption.class.getName(),
				cpSpecificationOption.getCPSpecificationOptionId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long cpSpecificationOptionId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, cpSpecificationOptionId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CPSpecificationOption.class.getName(),
				cpSpecificationOptionId, actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CPSpecificationOption cpSpecificationOption, String actionId)
		throws PortalException {

		if (contains(
				permissionChecker,
				cpSpecificationOption.getCPSpecificationOptionId(), actionId)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long cpSpecificationOptionId,
			String actionId)
		throws PortalException {

		CPSpecificationOption cpSpecificationOption =
			_cpSpecificationOptionLocalService.fetchCPSpecificationOption(
				cpSpecificationOptionId);

		if (cpSpecificationOption == null) {
			return false;
		}

		return _contains(permissionChecker, cpSpecificationOption, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			long[] cpSpecificationOptionIds, String actionId)
		throws PortalException {

		if (ArrayUtil.isEmpty(cpSpecificationOptionIds)) {
			return false;
		}

		for (long cpSpecificationOptionId : cpSpecificationOptionIds) {
			if (!contains(
					permissionChecker, cpSpecificationOptionId, actionId)) {

				return false;
			}
		}

		return true;
	}

	private boolean _contains(
			PermissionChecker permissionChecker,
			CPSpecificationOption cpSpecificationOption, String actionId)
		throws PortalException {

		if (permissionChecker.isCompanyAdmin(
				cpSpecificationOption.getCompanyId()) ||
			permissionChecker.hasOwnerPermission(
				cpSpecificationOption.getCompanyId(),
				CPSpecificationOption.class.getName(),
				cpSpecificationOption.getCPSpecificationOptionId(),
				cpSpecificationOption.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			null, CPSpecificationOption.class.getName(),
			cpSpecificationOption.getCPSpecificationOptionId(), actionId);
	}

	@Reference
	private CPSpecificationOptionLocalService
		_cpSpecificationOptionLocalService;

}