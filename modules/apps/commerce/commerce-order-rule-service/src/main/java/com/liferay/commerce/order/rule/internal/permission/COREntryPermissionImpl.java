/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.rule.internal.permission;

import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.permission.COREntryPermission;
import com.liferay.commerce.order.rule.service.COREntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ArrayUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = COREntryPermission.class)
public class COREntryPermissionImpl implements COREntryPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, COREntry corEntry,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, corEntry, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, COREntry.class.getName(),
				corEntry.getCOREntryId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long corEntryId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, corEntryId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, COREntry.class.getName(), corEntryId,
				actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, COREntry corEntry,
			String actionId)
		throws PortalException {

		if (contains(permissionChecker, corEntry.getCOREntryId(), actionId)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long corEntryId,
			String actionId)
		throws PortalException {

		COREntry corEntry = _corEntryLocalService.fetchCOREntry(corEntryId);

		if (corEntry == null) {
			return false;
		}

		return _contains(permissionChecker, corEntry, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long[] corEntryIds,
			String actionId)
		throws PortalException {

		if (ArrayUtil.isEmpty(corEntryIds)) {
			return false;
		}

		for (long corEntryId : corEntryIds) {
			if (!contains(permissionChecker, corEntryId, actionId)) {
				return false;
			}
		}

		return true;
	}

	private boolean _contains(
		PermissionChecker permissionChecker, COREntry corEntry,
		String actionId) {

		if (permissionChecker.isCompanyAdmin(corEntry.getCompanyId()) ||
			permissionChecker.isOmniadmin() ||
			permissionChecker.hasOwnerPermission(
				corEntry.getCompanyId(), COREntry.class.getName(),
				corEntry.getCOREntryId(), corEntry.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			null, COREntry.class.getName(), corEntry.getCOREntryId(), actionId);
	}

	@Reference
	private COREntryLocalService _corEntryLocalService;

}