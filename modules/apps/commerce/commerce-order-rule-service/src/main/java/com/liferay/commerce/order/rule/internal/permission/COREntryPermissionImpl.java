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
@Component(
	enabled = false, immediate = true, service = COREntryPermission.class
)
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