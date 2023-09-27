/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.PortalPermission;
import com.liferay.portal.kernel.util.PortletKeys;

import java.util.Map;
import java.util.Objects;

/**
 * @author Charles May
 */
public class PortalPermissionImpl implements PortalPermission {

	@Override
	public void check(PermissionChecker permissionChecker, String actionId)
		throws PrincipalException {

		if (!contains(permissionChecker, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, PortletKeys.PORTAL, PortletKeys.PORTAL,
				actionId);
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, String actionId) {

		Map<Object, Object> permissionChecksMap =
			permissionChecker.getPermissionChecksMap();

		CacheKey cacheKey = new CacheKey(actionId);

		Boolean contains = (Boolean)permissionChecksMap.get(cacheKey);

		if (contains == null) {
			contains = permissionChecker.hasPermission(
				null, PortletKeys.PORTAL, PortletKeys.PORTAL, actionId);

			permissionChecksMap.put(cacheKey, contains);
		}

		return contains;
	}

	private static class CacheKey {

		@Override
		public boolean equals(Object object) {
			if (this == object) {
				return true;
			}

			if (!(object instanceof CacheKey)) {
				return false;
			}

			CacheKey cacheKey = (CacheKey)object;

			return Objects.equals(_actionId, cacheKey._actionId);
		}

		@Override
		public int hashCode() {
			return _actionId.hashCode();
		}

		private CacheKey(String actionId) {
			_actionId = actionId;
		}

		private final String _actionId;

	}

}