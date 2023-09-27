/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.permission;

import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.permission.CPOptionPermission;
import com.liferay.commerce.product.service.CPOptionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ArrayUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = CPOptionPermission.class)
public class CPOptionPermissionImpl implements CPOptionPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, CPOption cpOption,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, cpOption, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CPOption.class.getName(),
				cpOption.getCPOptionId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long cpOptionId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, cpOptionId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CPOption.class.getName(), cpOptionId,
				actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, CPOption cpOption,
			String actionId)
		throws PortalException {

		if (contains(permissionChecker, cpOption.getCPOptionId(), actionId)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long cpOptionId,
			String actionId)
		throws PortalException {

		CPOption cpOption = _cpOptionLocalService.fetchCPOption(cpOptionId);

		if (cpOption == null) {
			return false;
		}

		return _contains(permissionChecker, cpOption, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long[] cpOptionIds,
			String actionId)
		throws PortalException {

		if (ArrayUtil.isEmpty(cpOptionIds)) {
			return false;
		}

		for (long cpOptionId : cpOptionIds) {
			if (!contains(permissionChecker, cpOptionId, actionId)) {
				return false;
			}
		}

		return true;
	}

	private boolean _contains(
			PermissionChecker permissionChecker, CPOption cpOption,
			String actionId)
		throws PortalException {

		if (permissionChecker.isCompanyAdmin(cpOption.getCompanyId()) ||
			permissionChecker.hasOwnerPermission(
				cpOption.getCompanyId(), CPOption.class.getName(),
				cpOption.getCPOptionId(), cpOption.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			null, CPOption.class.getName(), cpOption.getCPOptionId(), actionId);
	}

	@Reference
	private CPOptionLocalService _cpOptionLocalService;

}