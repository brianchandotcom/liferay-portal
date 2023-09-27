/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.term.internal.permission;

import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.permission.CommerceTermEntryPermission;
import com.liferay.commerce.term.service.CommerceTermEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ArrayUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = CommerceTermEntryPermission.class)
public class CommerceTermEntryPermissionImpl
	implements CommerceTermEntryPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceTermEntry commerceTermEntry, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, commerceTermEntry, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceTermEntry.class.getName(),
				commerceTermEntry.getCommerceTermEntryId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commerceTermEntryId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, commerceTermEntryId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceTermEntry.class.getName(),
				commerceTermEntryId, actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceTermEntry commerceTermEntry, String actionId)
		throws PortalException {

		if (contains(
				permissionChecker, commerceTermEntry.getCommerceTermEntryId(),
				actionId)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commerceTermEntryId,
			String actionId)
		throws PortalException {

		CommerceTermEntry commerceTermEntry =
			_commerceTermEntryLocalService.fetchCommerceTermEntry(
				commerceTermEntryId);

		if (commerceTermEntry == null) {
			return false;
		}

		return _contains(permissionChecker, commerceTermEntry, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long[] commerceTermEntryIds,
			String actionId)
		throws PortalException {

		if (ArrayUtil.isEmpty(commerceTermEntryIds)) {
			return false;
		}

		for (long commerceTermEntryId : commerceTermEntryIds) {
			if (!contains(permissionChecker, commerceTermEntryId, actionId)) {
				return false;
			}
		}

		return true;
	}

	private boolean _contains(
		PermissionChecker permissionChecker,
		CommerceTermEntry commerceTermEntry, String actionId) {

		if (permissionChecker.isCompanyAdmin(
				commerceTermEntry.getCompanyId()) ||
			permissionChecker.isOmniadmin() ||
			permissionChecker.hasOwnerPermission(
				commerceTermEntry.getCompanyId(),
				CommerceTermEntry.class.getName(),
				commerceTermEntry.getCommerceTermEntryId(),
				commerceTermEntry.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			null, CommerceTermEntry.class.getName(),
			commerceTermEntry.getCommerceTermEntryId(), actionId);
	}

	@Reference
	private CommerceTermEntryLocalService _commerceTermEntryLocalService;

}