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

		Map<Object, Object> permissionsCache =
			permissionChecker.getPermissionsCache();

		CacheKey cacheKey = new CacheKey(actionId);

		Boolean contains = (Boolean)permissionsCache.get(cacheKey);

		if (contains == null) {
			contains = permissionChecker.hasPermission(
				null, PortletKeys.PORTAL, PortletKeys.PORTAL, actionId);

			permissionsCache.put(cacheKey, contains);
		}

		return contains;
	}

	private static class CacheKey {

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}

			if (!(obj instanceof CacheKey)) {
				return false;
			}

			CacheKey cacheKey = (CacheKey)obj;

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