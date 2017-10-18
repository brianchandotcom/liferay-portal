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

import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalServiceUtil;
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
	property = {"model.class.name=com.liferay.layout.page.template.model.LayoutPageTemplateCollection"},
	service = BaseModelPermissionChecker.class
)
public class LayoutPageTemplateCollectionPermission
	implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker,
			LayoutPageTemplateCollection layoutPageTemplateCollection,
			String actionId)
		throws PortalException {

		if (!contains(
				permissionChecker, layoutPageTemplateCollection, actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, LayoutPageTemplateCollection.class.getName(),
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker,
			long layoutPageTemplateCollectionId, String actionId)
		throws PortalException {

		if (!contains(
				permissionChecker, layoutPageTemplateCollectionId, actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, LayoutPageTemplateCollection.class.getName(),
				layoutPageTemplateCollectionId, actionId);
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker,
		LayoutPageTemplateCollection layoutPageTemplateCollection,
		String actionId) {

		Map<Object, Object> permissionChecksMap =
			permissionChecker.getPermissionChecksMap();

		PermissionCacheKey permissionCacheKey = new PermissionCacheKey(
			layoutPageTemplateCollection.getLayoutPageTemplateCollectionId(),
			actionId);

		Boolean contains = (Boolean)permissionChecksMap.get(permissionCacheKey);

		if (contains == null) {
			contains = _contains(
				permissionChecker, layoutPageTemplateCollection, actionId);

			permissionChecksMap.put(permissionCacheKey, contains);
		}

		return contains;
	}

	public static boolean contains(
			PermissionChecker permissionChecker,
			long layoutPageTemplateCollectionId, String actionId)
		throws PortalException {

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			LayoutPageTemplateCollectionLocalServiceUtil.
				fetchLayoutPageTemplateCollection(
					layoutPageTemplateCollectionId);

		if (layoutPageTemplateCollection == null) {
			_log.error(
				"Unable to get layout page template collection " +
					layoutPageTemplateCollectionId);

			return false;
		}

		return contains(
			permissionChecker, layoutPageTemplateCollection, actionId);
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
		LayoutPageTemplateCollection layoutPageTemplateCollection,
		String actionId) {

		if (permissionChecker.hasOwnerPermission(
				layoutPageTemplateCollection.getCompanyId(),
				LayoutPageTemplateCollection.class.getName(),
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				layoutPageTemplateCollection.getUserId(), actionId) ||
			permissionChecker.hasPermission(
				layoutPageTemplateCollection.getGroupId(),
				LayoutPageTemplateCollection.class.getName(),
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				actionId)) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplateCollectionPermission.class);

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

			if ((_layoutPageTemplateCollectionId ==
					permissionCacheKey._layoutPageTemplateCollectionId) &&
				Objects.equals(_actionId, permissionCacheKey._actionId)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hash = HashUtil.hash(0, _layoutPageTemplateCollectionId);

			return HashUtil.hash(hash, _actionId);
		}

		private PermissionCacheKey(
			long layoutPageTemplateCollectionId, String actionId) {

			_layoutPageTemplateCollectionId = layoutPageTemplateCollectionId;
			_actionId = actionId;
		}

		private final String _actionId;
		private final long _layoutPageTemplateCollectionId;

	}

}