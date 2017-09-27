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

import com.liferay.layout.page.template.model.LayoutPageTemplate;
import com.liferay.layout.page.template.service.LayoutPageTemplateLocalServiceUtil;
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
	property = {"model.class.name=com.liferay.layout.page.template.model.LayoutPageTemplate"},
	service = BaseModelPermissionChecker.class
)
public class LayoutPageTemplatePermission
	implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker,
			LayoutPageTemplate layoutPageTemplate, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, layoutPageTemplate, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, LayoutPageTemplate.class.getName(),
				layoutPageTemplate.getLayoutPageTemplateId(), actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long layoutPageTemplateId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, layoutPageTemplateId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, LayoutPageTemplate.class.getName(),
				layoutPageTemplateId, actionId);
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker,
		LayoutPageTemplate layoutPageTemplate, String actionId) {

		Map<Object, Object> permissionChecksMap =
			permissionChecker.getPermissionChecksMap();

		PermissionCacheKey permissionCacheKey = new PermissionCacheKey(
			layoutPageTemplate.getLayoutPageTemplateId(), actionId);

		Boolean contains = (Boolean)permissionChecksMap.get(permissionCacheKey);

		if (contains == null) {
			contains = _contains(
				permissionChecker, layoutPageTemplate, actionId);

			permissionChecksMap.put(permissionCacheKey, contains);
		}

		return contains;
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long layoutPageTemplateId,
			String actionId)
		throws PortalException {

		LayoutPageTemplate layoutPageTemplate =
			LayoutPageTemplateLocalServiceUtil.fetchLayoutPageTemplate(
				layoutPageTemplateId);

		if (layoutPageTemplate == null) {
			_log.error(
				"Unable to get layout page template " + layoutPageTemplateId);

			return false;
		}

		return contains(permissionChecker, layoutPageTemplate, actionId);
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
		LayoutPageTemplate layoutPageTemplate, String actionId) {

		if (permissionChecker.hasOwnerPermission(
				layoutPageTemplate.getCompanyId(),
				LayoutPageTemplate.class.getName(),
				layoutPageTemplate.getLayoutPageTemplateId(),
				layoutPageTemplate.getUserId(),
				actionId) ||
			permissionChecker.hasPermission(
				layoutPageTemplate.getGroupId(),
				LayoutPageTemplate.class.getName(),
				layoutPageTemplate.getLayoutPageTemplateId(), actionId)) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplatePermission.class);

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

			if ((_layoutPageTemplateId ==
					permissionCacheKey._layoutPageTemplateId) &&
				Objects.equals(_actionId, permissionCacheKey._actionId)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hash = HashUtil.hash(0, _layoutPageTemplateId);

			return HashUtil.hash(hash, _actionId);
		}

		private PermissionCacheKey(long layoutPageTemplateId, String actionId) {
			_layoutPageTemplateId = layoutPageTemplateId;
			_actionId = actionId;
		}

		private final String _actionId;
		private final long _layoutPageTemplateId;

	}

}