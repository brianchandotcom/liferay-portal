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
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.persistence.SiteNavigationMenuItemUtil;

import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pavel Savinov
 */
@Component(
	property = {"model.class.name=com.liferay.site.navigation.model.SiteNavigationMenuItem"},
	service = BaseModelPermissionChecker.class
)
public class SiteNavigationMenuItemPermission
	implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, long siteNavigationMenuItemId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, siteNavigationMenuItemId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, SiteNavigationMenuItem.class.getName(),
				siteNavigationMenuItemId, actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker,
			SiteNavigationMenuItem siteNavigationMenuItem, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, siteNavigationMenuItem, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, SiteNavigationMenuItem.class.getName(),
				siteNavigationMenuItem.getSiteNavigationMenuItemId(), actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long siteNavigationMenuItemId,
			String actionId)
		throws PortalException {

		SiteNavigationMenuItem siteNavigationMenuItem =
			SiteNavigationMenuItemUtil.fetchByPrimaryKey(
				siteNavigationMenuItemId);

		if (siteNavigationMenuItem == null) {
			_log.error(
				"Unable to get site navigation menu item " +
					siteNavigationMenuItemId);

			return false;
		}

		return contains(permissionChecker, siteNavigationMenuItem, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker,
		SiteNavigationMenuItem siteNavigationMenuItem, String actionId) {

		Map<Object, Object> permissionChecksMap =
			permissionChecker.getPermissionChecksMap();

		PermissionCacheKey permissionCacheKey = new PermissionCacheKey(
			siteNavigationMenuItem.getSiteNavigationMenuItemId(), actionId);

		Boolean contains = (Boolean)permissionChecksMap.get(permissionCacheKey);

		if (contains == null) {
			contains = _contains(
				permissionChecker, siteNavigationMenuItem, actionId);

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
		SiteNavigationMenuItem siteNavigationMenuItem, String actionId) {

		if (permissionChecker.hasOwnerPermission(
				siteNavigationMenuItem.getCompanyId(),
				SiteNavigationMenuItem.class.getName(),
				siteNavigationMenuItem.getSiteNavigationMenuItemId(),
				siteNavigationMenuItem.getUserId(), actionId) ||
			permissionChecker.hasPermission(
				siteNavigationMenuItem.getGroupId(),
				SiteNavigationMenuItem.class.getName(),
				siteNavigationMenuItem.getSiteNavigationMenuItemId(),
				actionId)) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SiteNavigationMenuItemPermission.class);

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

			if ((_siteNavigationMenuItemId ==
					permissionCacheKey._siteNavigationMenuItemId) &&
				Objects.equals(_actionId, permissionCacheKey._actionId)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hash = HashUtil.hash(0, _siteNavigationMenuItemId);

			return HashUtil.hash(hash, _actionId);
		}

		private PermissionCacheKey(
			long siteNavigationMenuItemId, String actionId) {

			_siteNavigationMenuItemId = siteNavigationMenuItemId;
			_actionId = actionId;
		}

		private final String _actionId;
		private final long _siteNavigationMenuItemId;

	}

}