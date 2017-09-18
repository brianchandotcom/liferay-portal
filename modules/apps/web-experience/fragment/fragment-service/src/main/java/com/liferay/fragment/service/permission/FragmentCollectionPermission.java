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

package com.liferay.fragment.service.permission;

import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.FragmentCollectionLocalServiceUtil;
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
	property = {"model.class.name=com.liferay.fragment.model.FragmentCollection"},
	service = BaseModelPermissionChecker.class
)
public class FragmentCollectionPermission
	implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker,
			FragmentCollection fragmentCollection, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, fragmentCollection, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, FragmentCollection.class.getName(),
				fragmentCollection.getFragmentCollectionId(), actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long fragmentCollectionId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, fragmentCollectionId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, FragmentCollection.class.getName(),
				fragmentCollectionId, actionId);
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker,
		FragmentCollection fragmentCollection, String actionId) {

		Map<Object, Object> permissionChecksMap =
			permissionChecker.getPermissionChecksMap();

		PermissionCacheKey permissionCacheKey = new PermissionCacheKey(
			fragmentCollection.getFragmentCollectionId(), actionId);

		Boolean contains = (Boolean)permissionChecksMap.get(permissionCacheKey);

		if (contains == null) {
			contains = _contains(
				permissionChecker, fragmentCollection, actionId);

			permissionChecksMap.put(permissionCacheKey, contains);
		}

		return contains;
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long fragmentCollectionId,
			String actionId)
		throws PortalException {

		FragmentCollection fragmentCollection =
			FragmentCollectionLocalServiceUtil.fetchFragmentCollection(
				fragmentCollectionId);

		if (fragmentCollection == null) {
			_log.error(
				"Unable to get fragment collection " + fragmentCollectionId);

			return false;
		}

		return contains(permissionChecker, fragmentCollection, actionId);
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
		FragmentCollection fragmentCollection, String actionId) {

		if (permissionChecker.hasOwnerPermission(
				fragmentCollection.getCompanyId(),
				FragmentCollection.class.getName(),
				fragmentCollection.getFragmentCollectionId(),
				fragmentCollection.getUserId(), actionId) ||
			permissionChecker.hasPermission(
				fragmentCollection.getGroupId(),
				FragmentCollection.class.getName(),
				fragmentCollection.getFragmentCollectionId(), actionId)) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentCollectionPermission.class);

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

			if ((_fragmentCollectionId ==
					permissionCacheKey._fragmentCollectionId) &&
				Objects.equals(_actionId, permissionCacheKey._actionId)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hash = HashUtil.hash(0, _fragmentCollectionId);

			return HashUtil.hash(hash, _actionId);
		}

		private PermissionCacheKey(long fragmentCollectionId, String actionId) {
			_fragmentCollectionId = fragmentCollectionId;
			_actionId = actionId;
		}

		private final String _actionId;
		private final long _fragmentCollectionId;

	}

}