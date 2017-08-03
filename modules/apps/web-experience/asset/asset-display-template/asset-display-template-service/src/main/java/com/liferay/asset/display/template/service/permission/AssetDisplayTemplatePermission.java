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

package com.liferay.asset.display.template.service.permission;

import com.liferay.asset.display.template.model.AssetDisplayTemplate;
import com.liferay.asset.display.template.service.AssetDisplayTemplateLocalServiceUtil;
import com.liferay.exportimport.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.HashUtil;

import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pavel Savinov
 */
@Component(
	property = {"model.class.name=com.liferay.asset.display.template.model.AssetDisplayTemplate"},
	service = BaseModelPermissionChecker.class
)
public class AssetDisplayTemplatePermission
	implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker,
			AssetDisplayTemplate assetDisplayTemplate, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, assetDisplayTemplate, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, AssetDisplayTemplate.class.getName(),
				assetDisplayTemplate.getAssetDisplayTemplateId(), actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long assetDisplayTemplateId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, assetDisplayTemplateId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, AssetDisplayTemplate.class.getName(),
				assetDisplayTemplateId, actionId);
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker,
		AssetDisplayTemplate assetDisplayTemplate, String actionId) {

		Map<Object, Object> permissionChecksMap =
			permissionChecker.getPermissionChecksMap();

		PermissionCacheKey permissionCacheKey = new PermissionCacheKey(
			assetDisplayTemplate.getAssetDisplayTemplateId(), actionId);

		Boolean contains = (Boolean)permissionChecksMap.get(permissionCacheKey);

		if (contains == null) {
			contains = _contains(
				permissionChecker, assetDisplayTemplate, actionId);

			permissionChecksMap.put(permissionCacheKey, contains);
		}

		return contains;
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long assetDisplayTemplateId,
			String actionId)
		throws PortalException {

		Map<Object, Object> permissionChecksMap =
			permissionChecker.getPermissionChecksMap();

		PermissionCacheKey permissionCacheKey = new PermissionCacheKey(
			assetDisplayTemplateId, actionId);

		Boolean contains = (Boolean)permissionChecksMap.get(permissionCacheKey);

		if (contains == null) {
			AssetDisplayTemplate assetDisplayTemplate =
				AssetDisplayTemplateLocalServiceUtil.getAssetDisplayTemplate(
					assetDisplayTemplateId);

			contains = _contains(
				permissionChecker, assetDisplayTemplate, actionId);

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
		AssetDisplayTemplate assetDisplayTemplate, String actionId) {

		String portletId = PortletProviderUtil.getPortletId(
			AssetDisplayTemplate.class.getName(), PortletProvider.Action.EDIT);

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, assetDisplayTemplate.getGroupId(),
			AssetDisplayTemplate.class.getName(),
			assetDisplayTemplate.getAssetDisplayTemplateId(), portletId,
			actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (permissionChecker.hasOwnerPermission(
				assetDisplayTemplate.getCompanyId(),
				AssetDisplayTemplate.class.getName(),
				assetDisplayTemplate.getAssetDisplayTemplateId(),
				assetDisplayTemplate.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			assetDisplayTemplate.getGroupId(),
			AssetDisplayTemplate.class.getName(),
			assetDisplayTemplate.getAssetDisplayTemplateId(), actionId);
	}

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

			if ((_entryId == permissionCacheKey._entryId) &&
				Objects.equals(_actionId, permissionCacheKey._actionId)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hash = HashUtil.hash(0, _entryId);

			return HashUtil.hash(hash, _actionId);
		}

		private PermissionCacheKey(long entryId, String actionId) {
			_entryId = entryId;
			_actionId = actionId;
		}

		private final String _actionId;
		private final long _entryId;

	}

}