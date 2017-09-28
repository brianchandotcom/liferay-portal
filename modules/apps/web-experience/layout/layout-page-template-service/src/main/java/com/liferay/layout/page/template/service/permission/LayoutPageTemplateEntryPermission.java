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

package com.liferay.layout.page.template.service.permission;

import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.HashUtil;

import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = {"model.class.name=com.liferay.layout.page.template.model.LayoutPageTemplateEntry"},
	service = BaseModelPermissionChecker.class
)
public class LayoutPageTemplateEntryPermission
	implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker,
			LayoutPageTemplateEntry layoutPageTemplateEntry, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, layoutPageTemplateEntry, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, LayoutPageTemplateEntry.class.getName(),
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
				actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long layoutPageTemplateEntryId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, layoutPageTemplateEntryId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, LayoutPageTemplateEntry.class.getName(),
				layoutPageTemplateEntryId, actionId);
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker,
		LayoutPageTemplateEntry layoutPageTemplateEntry, String actionId) {

		Map<Object, Object> permissionChecksMap =
			permissionChecker.getPermissionChecksMap();

		PermissionCacheKey permissionCacheKey = new PermissionCacheKey(
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(), actionId);

		Boolean contains = (Boolean)permissionChecksMap.get(permissionCacheKey);

		if (contains == null) {
			contains = _contains(
				permissionChecker, layoutPageTemplateEntry, actionId);

			permissionChecksMap.put(permissionCacheKey, contains);
		}

		return contains;
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long layoutPageTemplateEntryId,
			String actionId)
		throws PortalException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntry(layoutPageTemplateEntryId);

		if (layoutPageTemplateEntry == null) {
			_log.error(
				"Unable to get layout page template " +
					layoutPageTemplateEntryId);

			return false;
		}

		return contains(permissionChecker, layoutPageTemplateEntry, actionId);
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
		LayoutPageTemplateEntry layoutPageTemplateEntry, String actionId) {

		if (permissionChecker.hasOwnerPermission(
				layoutPageTemplateEntry.getCompanyId(),
				LayoutPageTemplateEntry.class.getName(),
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
				layoutPageTemplateEntry.getUserId(),
				actionId) ||
			permissionChecker.hasPermission(
				layoutPageTemplateEntry.getGroupId(),
				LayoutPageTemplateEntry.class.getName(),
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
				actionId)) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplateEntryPermission.class);

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

			if ((_layoutPageTemplateEntryId ==
					permissionCacheKey._layoutPageTemplateEntryId) &&
				Objects.equals(_actionId, permissionCacheKey._actionId)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hash = HashUtil.hash(0, _layoutPageTemplateEntryId);

			return HashUtil.hash(hash, _actionId);
		}

		private PermissionCacheKey(
			long layoutPageTemplateEntryId, String actionId) {

			_layoutPageTemplateEntryId = layoutPageTemplateEntryId;
			_actionId = actionId;
		}

		private final String _actionId;
		private final long _layoutPageTemplateEntryId;

	}

}