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

package com.liferay.site.navigation.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalServiceUtil;

import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pavel Savinov
 */
@Component(
	property = {"model.class.name=com.liferay.site.navigation.model.SiteNavigationMenu"},
	service = BaseModelPermissionChecker.class
)
public class SiteNavigationMenuPermission
	implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, long siteNavigationMenuId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, siteNavigationMenuId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, SiteNavigationMenu.class.getName(),
				siteNavigationMenuId, actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker,
			SiteNavigationMenu siteNavigationMenu, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, siteNavigationMenu, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, SiteNavigationMenu.class.getName(),
				siteNavigationMenu.getSiteNavigationMenuId(), actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long siteNavigationMenuId,
			String actionId)
		throws PortalException {

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuLocalServiceUtil.fetchSiteNavigationMenu(
				siteNavigationMenuId);

		if (siteNavigationMenu == null) {
			_log.error(
				"Unable to get site navitation menu " + siteNavigationMenuId);

			return false;
		}

		return contains(permissionChecker, siteNavigationMenu, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker,
		SiteNavigationMenu siteNavigationMenu, String actionId) {

		Map<Object, Object> permissionChecksMap =
			permissionChecker.getPermissionChecksMap();

		PermissionCacheKey permissionCacheKey = new PermissionCacheKey(
			siteNavigationMenu.getSiteNavigationMenuId(), actionId);

		Boolean contains = (Boolean)permissionChecksMap.get(permissionCacheKey);

		if (contains == null) {
			contains = _contains(
				permissionChecker, siteNavigationMenu, actionId);

			permissionChecksMap.put(permissionCacheKey, contains);
		}

		return contains;
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		check(permissionChecker, primaryKey, actionId);
	}

	private static boolean _contains(
		PermissionChecker permissionChecker,
		SiteNavigationMenu siteNavigationMenu, String actionId) {

		if (permissionChecker.hasOwnerPermission(
				siteNavigationMenu.getCompanyId(),
				SiteNavigationMenu.class.getName(),
				siteNavigationMenu.getSiteNavigationMenuId(),
				siteNavigationMenu.getUserId(), actionId) ||
			permissionChecker.hasPermission(
				siteNavigationMenu.getGroupId(),
				SiteNavigationMenu.class.getName(),
				siteNavigationMenu.getSiteNavigationMenuId(), actionId)) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SiteNavigationMenuPermission.class);

	private static class PermissionCacheKey {

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}

			if (!(obj instanceof PermissionCacheKey)) {
				return false;
			}

			PermissionCacheKey permissionCacheKey = (PermissionCacheKey)obj;

			if ((_siteNavigationMenuId ==
					permissionCacheKey._siteNavigationMenuId) &&
				Objects.equals(_actionId, permissionCacheKey._actionId)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hash = HashUtil.hash(0, _siteNavigationMenuId);

			return HashUtil.hash(hash, _actionId);
		}

		private PermissionCacheKey(long siteNavigationMenuId, String actionId) {
			_siteNavigationMenuId = siteNavigationMenuId;
			_actionId = actionId;
		}

		private final String _actionId;
		private final long _siteNavigationMenuId;

	}

}