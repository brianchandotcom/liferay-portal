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

import com.liferay.modern.site.building.fragment.model.MSBFragmentEntry;
import com.liferay.modern.site.building.fragment.service.MSBFragmentEntryLocalServiceUtil;
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
	property = {"model.class.name=com.liferay.modern.site.building.fragment.model.MSBFragmentEntry"},
	service = BaseModelPermissionChecker.class
)
public class MSBFragmentEntryPermission implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, long msbFragmentEntryId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, msbFragmentEntryId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, MSBFragmentEntry.class.getName(),
				msbFragmentEntryId, actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker,
			MSBFragmentEntry msbFragmentEntry, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, msbFragmentEntry, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, MSBFragmentEntry.class.getName(),
				msbFragmentEntry.getMsbFragmentEntryId(), actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long msbFragmentEntryId,
			String actionId)
		throws PortalException {

		MSBFragmentEntry msbFragmentEntry =
			MSBFragmentEntryLocalServiceUtil.fetchMSBFragmentEntry(
				msbFragmentEntryId);

		if (msbFragmentEntry == null) {
			_log.error(
				"Unable to get modern site building fragment entry " +
					msbFragmentEntryId);

			return false;
		}

		return contains(permissionChecker, msbFragmentEntry, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, MSBFragmentEntry msbFragmentEntry,
		String actionId) {

		Map<Object, Object> permissionChecksMap =
			permissionChecker.getPermissionChecksMap();

		PermissionCacheKey permissionCacheKey = new PermissionCacheKey(
			msbFragmentEntry.getMsbFragmentEntryId(), actionId);

		Boolean contains = (Boolean)permissionChecksMap.get(permissionCacheKey);

		if (contains == null) {
			contains = _contains(permissionChecker, msbFragmentEntry, actionId);

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
		PermissionChecker permissionChecker, MSBFragmentEntry msbFragmentEntry,
		String actionId) {

		if (permissionChecker.hasOwnerPermission(
				msbFragmentEntry.getCompanyId(),
				MSBFragmentEntry.class.getName(),
				msbFragmentEntry.getMsbFragmentCollectionId(),
				msbFragmentEntry.getUserId(), actionId) ||
			permissionChecker.hasPermission(
				msbFragmentEntry.getGroupId(), MSBFragmentEntry.class.getName(),
				msbFragmentEntry.getMsbFragmentEntryId(), actionId)) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MSBFragmentEntryPermission.class);

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

			if ((_msbFragmentEntryId ==
					permissionCacheKey._msbFragmentEntryId) &&
				Objects.equals(_actionId, permissionCacheKey._actionId)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hash = HashUtil.hash(0, _msbFragmentEntryId);

			return HashUtil.hash(hash, _actionId);
		}

		private PermissionCacheKey(long msbFragmentEntryId, String actionId) {
			_msbFragmentEntryId = msbFragmentEntryId;
			_actionId = actionId;
		}

		private final String _actionId;
		private final long _msbFragmentEntryId;

	}

}