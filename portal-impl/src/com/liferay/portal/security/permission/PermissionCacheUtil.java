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

package com.liferay.portal.security.permission;

import com.liferay.portal.cache.CompositePortalCacheKeyManager;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.key.CompositePortalCacheKey;
import com.liferay.portal.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Role;
import com.liferay.portal.util.PropsValues;

import java.io.Serializable;

/**
 * @author Charles May
 * @author Michael Young
 * @author Shuyang Zhou
 * @author Connor McKay
 * @author László Csontos
 */
public class PermissionCacheUtil {

	public static final String PERMISSION_CACHE_NAME =
		PermissionCacheUtil.class.getName() + "_PERMISSION";

	public static final String PERMISSION_CHECKER_BAG_CACHE_NAME =
		PermissionCacheUtil.class.getName() + "_PERMISSION_CHECKER_BAG";

	public static final String RESOURCE_BLOCK_IDS_BAG_CACHE_NAME =
		PermissionCacheUtil.class.getName() + "_RESOURCE_BLOCK_IDS_BAG";

	public static final String USER_PERMISSION_CHECKER_BAG_CACHE_NAME =
		PermissionCacheUtil.class.getName() + "_USER_PERMISSION_CHECKER_BAG";

	public static final String USER_ROLE_CACHE_NAME =
		PermissionCacheUtil.class.getName() + "_USER_ROLE";

	public static void clearCache() {
		if (ExportImportThreadLocal.isImportInProcess()) {
			return;
		}

		_userRolePortalCache.removeAll();
		_permissionCheckerBagPortalCache.removeAll();
		_permissionPortalCache.removeAll();
		_resourceBlockIdsBagCache.removeAll();
		_userPermissionCheckerBagPortalCache.removeAll();
	}

	public static void clearCache(long userId) {
		if (ExportImportThreadLocal.isImportInProcess()) {
			return;
		}

		_userPermissionCheckerBagPortalCache.remove(userId);

		_userRolePortalCacheKeyManager.removeBySimpleKey(
			UserRoleKey.getSimpleKey(userId));
		_permissionCheckerBagPortalCacheKeyManager.removeBySimpleKey(
			BagKey.getSimpleKey(userId));

		_permissionPortalCache.removeAll();
		_resourceBlockIdsBagCache.removeAll();
	}

	public static void clearCache(long[] userIds) {
		if (ExportImportThreadLocal.isImportInProcess()) {
			return;
		}

		for (long userId : userIds) {
			_userPermissionCheckerBagPortalCache.remove(userId);

			_userRolePortalCacheKeyManager.removeBySimpleKey(
				UserRoleKey.getSimpleKey(userId));
			_permissionCheckerBagPortalCacheKeyManager.removeBySimpleKey(
				BagKey.getSimpleKey(userId));
		}

		_permissionPortalCache.removeAll();
		_resourceBlockIdsBagCache.removeAll();
	}

	public static void clearResourceBlockCache() {
		if (ExportImportThreadLocal.isImportInProcess() ||
			!PermissionThreadLocal.isFlushResourceBlockEnabled()) {

			return;
		}

		_resourceBlockIdsBagCache.removeAll();
	}

	public static void clearResourceCache() {
		if (!ExportImportThreadLocal.isImportInProcess()) {
			_resourceBlockIdsBagCache.removeAll();
			_permissionPortalCache.removeAll();
		}
	}

	public static void clearResourcePermissionCache() {
		if (ExportImportThreadLocal.isImportInProcess() ||
			!PermissionThreadLocal.isFlushResourcePermissionEnabled()) {

			return;
		}

		_permissionPortalCache.removeAll();
	}

	public static PermissionCheckerBag getBag(long userId, long groupId) {
		BagKey bagKey = new BagKey(userId, groupId);

		return _permissionCheckerBagPortalCache.get(bagKey);
	}

	public static Boolean getPermission(
		long userId, boolean signedIn, long groupId, String name,
		String primKey, String actionId) {

		PermissionKey permissionKey = new PermissionKey(
			userId, signedIn, groupId, name, primKey, actionId);

		return _permissionPortalCache.get(permissionKey);
	}

	public static ResourceBlockIdsBag getResourceBlockIdsBag(
		long companyId, long groupId, long userId, String name) {

		ResourceBlockIdsBagKey resourceBlockIdsBagKey =
			new ResourceBlockIdsBagKey(companyId, groupId, userId, name);

		return _resourceBlockIdsBagCache.get(resourceBlockIdsBagKey);
	}

	public static UserPermissionCheckerBag getUserBag(long userId) {
		return _userPermissionCheckerBagPortalCache.get(userId);
	}

	public static Boolean getUserRole(long userId, Role role) {
		UserRoleKey userRoleKey = new UserRoleKey(userId, role.getRoleId());

		Boolean userRole = _userRolePortalCache.get(userRoleKey);

		if (userRole != null) {
			return userRole;
		}

		UserPermissionCheckerBag userPermissionCheckerBag = getUserBag(userId);

		if (userPermissionCheckerBag == null) {
			return null;
		}

		userRole = userPermissionCheckerBag.hasRole(role);

		_userRolePortalCache.put(userRoleKey, userRole);

		return userRole;
	}

	public static void putBag(
		long userId, long groupId, PermissionCheckerBag bag) {

		if (bag == null) {
			return;
		}

		BagKey bagKey = new BagKey(userId, groupId);

		_permissionCheckerBagPortalCache.put(bagKey, bag);
	}

	public static void putPermission(
		long userId, boolean signedIn, long groupId, String name,
		String primKey, String actionId, Boolean value) {

		PermissionKey permissionKey = new PermissionKey(
			userId, signedIn, groupId, name, primKey, actionId);

		_permissionPortalCache.put(permissionKey, value);
	}

	public static void putResourceBlockIdsBag(
		long companyId, long groupId, long userId, String name,
		ResourceBlockIdsBag resourceBlockIdsBag) {

		if (resourceBlockIdsBag == null) {
			return;
		}

		ResourceBlockIdsBagKey resourceBlockIdsBagKey =
			new ResourceBlockIdsBagKey(companyId, groupId, userId, name);

		_resourceBlockIdsBagCache.put(
			resourceBlockIdsBagKey, resourceBlockIdsBag);
	}

	public static void putUserBag(
		long userId, UserPermissionCheckerBag userPermissionCheckerBag) {

		_userPermissionCheckerBagPortalCache.put(
			userId, userPermissionCheckerBag);
	}

	public static void putUserRole(long userId, Role role, Boolean value) {
		if (value == null) {
			return;
		}

		UserRoleKey userRoleKey = new UserRoleKey(userId, role.getRoleId());

		_userRolePortalCache.put(userRoleKey, value);
	}

	private static final PortalCache<BagKey, PermissionCheckerBag>
		_permissionCheckerBagPortalCache = MultiVMPoolUtil.getCache(
			PERMISSION_CHECKER_BAG_CACHE_NAME,
			PropsValues.PERMISSIONS_OBJECT_BLOCKING_CACHE);
	private static final CompositePortalCacheKeyManager
		<BagKey, PermissionCheckerBag>
			_permissionCheckerBagPortalCacheKeyManager =
				new CompositePortalCacheKeyManager<>(
					_permissionCheckerBagPortalCache);
	private static final PortalCache<PermissionKey, Boolean>
		_permissionPortalCache = MultiVMPoolUtil.getCache(
			PERMISSION_CACHE_NAME,
			PropsValues.PERMISSIONS_OBJECT_BLOCKING_CACHE);
	private static final
		PortalCache<ResourceBlockIdsBagKey, ResourceBlockIdsBag>
			_resourceBlockIdsBagCache = MultiVMPoolUtil.getCache(
				RESOURCE_BLOCK_IDS_BAG_CACHE_NAME,
				PropsValues.PERMISSIONS_OBJECT_BLOCKING_CACHE);
	private static final PortalCache<Long, UserPermissionCheckerBag>
		_userPermissionCheckerBagPortalCache = MultiVMPoolUtil.getCache(
			USER_PERMISSION_CHECKER_BAG_CACHE_NAME,
			PropsValues.PERMISSIONS_OBJECT_BLOCKING_CACHE);
	private static final PortalCache<UserRoleKey, Boolean>
		_userRolePortalCache = MultiVMPoolUtil.getCache(
			USER_ROLE_CACHE_NAME,
			PropsValues.PERMISSIONS_OBJECT_BLOCKING_CACHE);
	private static final CompositePortalCacheKeyManager<UserRoleKey, Boolean>
		_userRolePortalCacheKeyManager = new CompositePortalCacheKeyManager<>(
			_userRolePortalCache);

	private static class BagKey implements CompositePortalCacheKey {

		public static String getSimpleKey(long userId) {
			return String.valueOf(userId);
		}

		public BagKey(long userId, long groupId) {
			_userId = userId;
			_groupId = groupId;
		}

		@Override
		public boolean equals(Object obj) {
			BagKey bagKey = (BagKey)obj;

			if ((bagKey._userId == _userId) && (bagKey._groupId == _groupId)) {
				return true;
			}
			else {
				return false;
			}
		}

		@Override
		public String getSimpleKey() {
			return getSimpleKey(_userId);
		}

		@Override
		public int hashCode() {
			return (int)(_userId * 11 + _groupId);
		}

		private static final long serialVersionUID = 1L;

		private final long _groupId;
		private final long _userId;

	}

	private static class PermissionKey implements Serializable {

		public PermissionKey(
			long userId, boolean signedIn, long groupId, String name,
			String primKey, String actionId) {

			_userId = userId;
			_signedIn = signedIn;
			_groupId = groupId;
			_name = name;
			_primKey = primKey;
			_actionId = actionId;
		}

		@Override
		public boolean equals(Object obj) {
			PermissionKey permissionKey = (PermissionKey)obj;

			if ((permissionKey._userId == _userId) &&
				(permissionKey._signedIn == _signedIn) &&
				(permissionKey._groupId == _groupId) &&
				Validator.equals(permissionKey._name, _name) &&
				Validator.equals(permissionKey._primKey, _primKey) &&
				Validator.equals(permissionKey._actionId, _actionId)) {

				return true;
			}
			else {
				return false;
			}
		}

		@Override
		public int hashCode() {
			int hashCode = HashUtil.hash(0, _userId);

			hashCode = HashUtil.hash(hashCode, _signedIn);
			hashCode = HashUtil.hash(hashCode, _groupId);
			hashCode = HashUtil.hash(hashCode, _name);
			hashCode = HashUtil.hash(hashCode, _primKey);
			hashCode = HashUtil.hash(hashCode, _actionId);

			return hashCode;
		}

		private static final long serialVersionUID = 1L;

		private final String _actionId;
		private final long _groupId;
		private final String _name;
		private final String _primKey;
		private final boolean _signedIn;
		private final long _userId;

	}

	private static class ResourceBlockIdsBagKey implements Serializable {

		public ResourceBlockIdsBagKey(
			long companyId, long groupId, long userId, String name) {

			_companyId = companyId;
			_groupId = groupId;
			_userId = userId;
			_name = name;
		}

		@Override
		public boolean equals(Object obj) {
			ResourceBlockIdsBagKey resourceBlockIdsKey =
				(ResourceBlockIdsBagKey)obj;

			if ((resourceBlockIdsKey._companyId == _companyId) &&
				(resourceBlockIdsKey._groupId == _groupId) &&
				(resourceBlockIdsKey._userId == _userId) &&
				Validator.equals(resourceBlockIdsKey._name, _name)) {

				return true;
			}
			else {
				return false;
			}
		}

		@Override
		public int hashCode() {
			int hashCode = HashUtil.hash(0, _companyId);

			hashCode = HashUtil.hash(hashCode, _groupId);
			hashCode = HashUtil.hash(hashCode, _userId);
			hashCode = HashUtil.hash(hashCode, _name);

			return hashCode;
		}

		private static final long serialVersionUID = 1L;

		private final long _companyId;
		private final long _groupId;
		private final String _name;
		private final long _userId;

	}

	private static class UserRoleKey implements CompositePortalCacheKey {

		public static String getSimpleKey(long userId) {
			return String.valueOf(userId);
		}

		public UserRoleKey(long userId, long roleId) {
			_userId = userId;
			_roleId = roleId;
		}

		@Override
		public boolean equals(Object obj) {
			UserRoleKey userRoleKey = (UserRoleKey)obj;

			if ((userRoleKey._userId == _userId) &&
				(userRoleKey._roleId == _roleId)) {

				return true;
			}
			else {
				return false;
			}
		}

		@Override
		public String getSimpleKey() {
			return getSimpleKey(_userId);
		}

		@Override
		public int hashCode() {
			return (int)(_userId * 11 + _roleId);
		}

		private static final long serialVersionUID = 1L;

		private final long _roleId;
		private final long _userId;

	}

}