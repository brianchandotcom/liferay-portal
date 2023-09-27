/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.permission;

import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.permission.CPOptionCategoryPermission;
import com.liferay.commerce.product.service.CPOptionCategoryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ArrayUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = CPOptionCategoryPermission.class)
public class CPOptionCategoryPermissionImpl
	implements CPOptionCategoryPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CPOptionCategory cpOptionCategory, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, cpOptionCategory, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CPOptionCategory.class.getName(),
				cpOptionCategory.getCPOptionCategoryId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long cpOptionCategoryId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, cpOptionCategoryId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CPOptionCategory.class.getName(),
				cpOptionCategoryId, actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CPOptionCategory cpOptionCategory, String actionId)
		throws PortalException {

		if (contains(
				permissionChecker, cpOptionCategory.getCPOptionCategoryId(),
				actionId)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long cpOptionCategoryId,
			String actionId)
		throws PortalException {

		CPOptionCategory cpOptionCategory =
			_cpOptionCategoryLocalService.fetchCPOptionCategory(
				cpOptionCategoryId);

		if (cpOptionCategory == null) {
			return false;
		}

		return _contains(permissionChecker, cpOptionCategory, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long[] cpOptionCategoryIds,
			String actionId)
		throws PortalException {

		if (ArrayUtil.isEmpty(cpOptionCategoryIds)) {
			return false;
		}

		for (long cpOptionCategoryId : cpOptionCategoryIds) {
			if (!contains(permissionChecker, cpOptionCategoryId, actionId)) {
				return false;
			}
		}

		return true;
	}

	private boolean _contains(
			PermissionChecker permissionChecker,
			CPOptionCategory cpOptionCategory, String actionId)
		throws PortalException {

		if (permissionChecker.isCompanyAdmin(cpOptionCategory.getCompanyId()) ||
			permissionChecker.hasOwnerPermission(
				cpOptionCategory.getCompanyId(),
				CPOptionCategory.class.getName(),
				cpOptionCategory.getCPOptionCategoryId(),
				cpOptionCategory.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			null, CPOptionCategory.class.getName(),
			cpOptionCategory.getCPOptionCategoryId(), actionId);
	}

	@Reference
	private CPOptionCategoryLocalService _cpOptionCategoryLocalService;

}