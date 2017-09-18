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

package com.liferay.modern.site.building.fragment.service.permission;

import com.liferay.modern.site.building.fragment.model.MSBFragmentCollection;
import com.liferay.modern.site.building.fragment.service.MSBFragmentCollectionLocalServiceUtil;
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
	property = {"model.class.name=com.liferay.modern.site.building.fragment.model.MSBFragmentCollection"},
	service = BaseModelPermissionChecker.class
)
public class MSBFragmentCollectionPermission
	implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, long msbFragmentCollectionId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, msbFragmentCollectionId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, MSBFragmentCollection.class.getName(),
				msbFragmentCollectionId, actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker,
			MSBFragmentCollection msbFragmentCollection, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, msbFragmentCollection, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, MSBFragmentCollection.class.getName(),
				msbFragmentCollection.getMsbFragmentCollectionId(), actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long msbFragmentCollectionId,
			String actionId)
		throws PortalException {

		MSBFragmentCollection msbFragmentCollection =
			MSBFragmentCollectionLocalServiceUtil.fetchMSBFragmentCollection(
				msbFragmentCollectionId);

		if (msbFragmentCollection == null) {
			_log.error(
				"Unable to get modern site building fragment collection " +
					msbFragmentCollectionId);

			return false;
		}

		return contains(permissionChecker, msbFragmentCollection, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker,
		MSBFragmentCollection msbFragmentCollection, String actionId) {

		Map<Object, Object> permissionChecksMap =
			permissionChecker.getPermissionChecksMap();

		PermissionCacheKey permissionCacheKey = new PermissionCacheKey(
			msbFragmentCollection.getMsbFragmentCollectionId(), actionId);

		Boolean contains = (Boolean)permissionChecksMap.get(permissionCacheKey);

		if (contains == null) {
			contains = _contains(
				permissionChecker, msbFragmentCollection, actionId);

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
		MSBFragmentCollection msbFragmentCollection, String actionId) {

		if (permissionChecker.hasOwnerPermission(
				msbFragmentCollection.getCompanyId(),
				MSBFragmentCollection.class.getName(),
				msbFragmentCollection.getMsbFragmentCollectionId(),
				msbFragmentCollection.getUserId(), actionId) ||
			permissionChecker.hasPermission(
				msbFragmentCollection.getGroupId(),
				MSBFragmentCollection.class.getName(),
				msbFragmentCollection.getMsbFragmentCollectionId(), actionId)) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MSBFragmentCollectionPermission.class);

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

			if ((_msbFragmentCollectionId ==
					permissionCacheKey._msbFragmentCollectionId) &&
				Objects.equals(_actionId, permissionCacheKey._actionId)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hash = HashUtil.hash(0, _msbFragmentCollectionId);

			return HashUtil.hash(hash, _actionId);
		}

		private PermissionCacheKey(
			long msbFragmentCollectionId, String actionId) {

			_msbFragmentCollectionId = msbFragmentCollectionId;
			_actionId = actionId;
		}

		private final String _actionId;
		private final long _msbFragmentCollectionId;

	}

}