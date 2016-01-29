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

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.index.IndexEncoder;
import com.liferay.portal.kernel.cache.index.PortalCacheIndexer;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.ResourceBlockIdsBag;
import com.liferay.portal.kernel.security.permission.UserBag;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.exportimport.lar.ExportImportThreadLocal;

import java.io.Serializable;

import java.util.Arrays;

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

	public static final String USER_BAG_CACHE_NAME =
		PermissionCacheUtil.class.getName() + "_USER_BAG";

	public static final String USER_PRIMARY_KEY_ROLE_CACHE_NAME =
		PermissionCacheUtil.class.getName() + "_USER_PRIMARY_KEY_ROLE";

	public static final String USER_ROLE_CACHE_NAME =
		PermissionCacheUtil.class.getName() + "_USER_ROLE";

	public static void clearCache() {
		if (ExportImportThreadLocal.isImportInProcess()) {
			return;
		}

		_userRolePortalCache.removeAll();
		_userGroupRoleIdsPortalCache.removeAll();
		_permissionPortalCache.removeAll();
		_resourceBlockIdsBagCache.removeAll();
		_userBagPortalCache.removeAll();
		_userPrimaryKeyRolePortalCache.removeAll();
	}

	public static void clearCache(long... userIds) {
		if (ExportImportThreadLocal.isImportInProcess()) {
			return;
		}

		for (long userId : userIds) {
			_userBagPortalCache.remove(userId);

			_userGroupRoleIdsPortalCacheIndexer.removeKeys(userId);
			_userPrimaryKeyRolePortalCacheUserIdIndexer.removeKeys(userId);
			_userRolePortalCacheIndexer.removeKeys(userId);
		}

		_permissionPortalCache.removeAll();
		_resourceBlockIdsBagCache.removeAll();
	}

	public static void clearResourceBlockCache(
		long companyId, long groupId, String name) {

		if (ExportImportThreadLocal.isImportInProcess() ||
			!PermissionThreadLocal.isFlushResourceBlockEnabled(
				companyId, groupId, name)) {

			return;
		}

		_resourceBlockIdsBagCacheIndexer.removeKeys(
			ResourceBlockIdsBagKeyIndexEncoder.encode(
				companyId, groupId, name));
	}

	public static void clearResourceCache() {
		if (!ExportImportThreadLocal.isImportInProcess()) {
			_resourceBlockIdsBagCache.removeAll();
			_permissionPortalCache.removeAll();
		}
	}

	public static void clearResourcePermissionCache(
		int scope, String name, String primKey) {

		if (ExportImportThreadLocal.isImportInProcess() ||
			!PermissionThreadLocal.isFlushResourcePermissionEnabled(
				name, primKey)) {

			return;
		}

		if (scope == ResourceConstants.SCOPE_INDIVIDUAL) {
			_permissionPortalCacheNamePrimKeyIndexer.removeKeys(
				PermissionKeyNamePrimKeyIndexEncoder.encode(name, primKey));
		}
		else if (scope == ResourceConstants.SCOPE_GROUP) {
			_permissionPortalCacheGroupIdIndexer.removeKeys(
				Long.valueOf(primKey));
		}
		else {
			_permissionPortalCache.removeAll();
		}
	}

	public static Boolean getPermission(
		long groupId, String name, String primKey, long[] roleIds,
		String actionId) {

		PermissionKey permissionKey = new PermissionKey(
			groupId, name, primKey, roleIds, actionId);

		return _permissionPortalCache.get(permissionKey);
	}

	public static ResourceBlockIdsBag getResourceBlockIdsBag(
		long companyId, long groupId, long userId, String name) {

		ResourceBlockIdsBagKey resourceBlockIdsBagKey =
			new ResourceBlockIdsBagKey(companyId, groupId, userId, name);

		return _resourceBlockIdsBagCache.get(resourceBlockIdsBagKey);
	}

	public static UserBag getUserBag(long userId) {
		return _userBagPortalCache.get(userId);
	}

	public static long[] getUserGroupRoleIds(long userId, long groupId) {
		UserGroupRoleIdsKey userGroupRoleIdsKey = new UserGroupRoleIdsKey(
			userId, groupId);

		return _userGroupRoleIdsPortalCache.get(userGroupRoleIdsKey);
	}

	public static Boolean getUserPrimaryKeyRole(
		long userId, long primaryKey, String roleName) {

		UserPrimaryKeyRoleKey userPrimaryKeyRoleKey = new UserPrimaryKeyRoleKey(
			userId, primaryKey, roleName);

		return _userPrimaryKeyRolePortalCache.get(userPrimaryKeyRoleKey);
	}

	public static Boolean getUserRole(long userId, Role role) {
		UserRoleKey userRoleKey = new UserRoleKey(userId, role.getRoleId());

		Boolean userRole = _userRolePortalCache.get(userRoleKey);

		if (userRole != null) {
			return userRole;
		}

		UserBag userBag = getUserBag(userId);

		if (userBag == null) {
			return null;
		}

		userRole = userBag.hasRole(role);

		_userRolePortalCache.put(userRoleKey, userRole);

		return userRole;
	}

	public static void putPermission(
		long groupId, String name, String primKey, long[] roleIds,
		String actionId, Boolean value) {

		PermissionKey permissionKey = new PermissionKey(
			groupId, name, primKey, roleIds, actionId);

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

	public static void putUserBag(long userId, UserBag userBag) {
		_userBagPortalCache.put(userId, userBag);
	}

	public static void putUserGroupRoleIds(
		long userId, long groupId, long[] roleIds) {

		if (roleIds == null) {
			return;
		}

		UserGroupRoleIdsKey userGroupRoleIdsKey = new UserGroupRoleIdsKey(
			userId, groupId);

		_userGroupRoleIdsPortalCache.put(userGroupRoleIdsKey, roleIds);
	}

	public static void putUserPrimaryKeyRole(
		long userId, long primaryKey, String roleName, Boolean value) {

		if (value == null) {
			return;
		}

		UserPrimaryKeyRoleKey userPrimaryKeyRoleKey = new UserPrimaryKeyRoleKey(
			userId, primaryKey, roleName);

		_userPrimaryKeyRolePortalCache.put(userPrimaryKeyRoleKey, value);
	}

	public static void putUserRole(long userId, Role role, Boolean value) {
		if (value == null) {
			return;
		}

		UserRoleKey userRoleKey = new UserRoleKey(userId, role.getRoleId());

		_userRolePortalCache.put(userRoleKey, value);
	}

	public static void removePermission(
		long groupId, String name, String primKey, long[] roleIds,
		String actionId) {

		PermissionKey permissionKey = new PermissionKey(
			groupId, name, primKey, roleIds, actionId);

		_permissionPortalCache.remove(permissionKey);
	}

	public static void removeResourceBlockIdsBag(
		long companyId, long groupId, long userId, String name) {

		ResourceBlockIdsBagKey resourceBlockIdsBagKey =
			new ResourceBlockIdsBagKey(companyId, groupId, userId, name);

		_resourceBlockIdsBagCache.remove(resourceBlockIdsBagKey);
	}

	public static void removeUserBag(long userId) {
		_userBagPortalCache.remove(userId);
	}

	public static void removeUserGroupRoleIds(long userId, long groupId) {
		UserGroupRoleIdsKey userGroupRoleIdsKey = new UserGroupRoleIdsKey(
			userId, groupId);

		_userGroupRoleIdsPortalCache.remove(userGroupRoleIdsKey);
	}

	public static void removeUserPrimaryKeyRole(
		long userId, long primaryKey, String roleName) {

		UserPrimaryKeyRoleKey userPrimaryKeyRoleKey = new UserPrimaryKeyRoleKey(
			userId, primaryKey, roleName);

		_userPrimaryKeyRolePortalCache.remove(userPrimaryKeyRoleKey);
	}

	private static final PortalCache<PermissionKey, Boolean>
		_permissionPortalCache = MultiVMPoolUtil.getPortalCache(
			PERMISSION_CACHE_NAME,
			PropsValues.PERMISSIONS_OBJECT_BLOCKING_CACHE);
	private static final PortalCacheIndexer<Long, PermissionKey, Boolean>
		_permissionPortalCacheGroupIdIndexer = new PortalCacheIndexer<>(
			new PermissionKeyGroupIdIndexEncoder(), _permissionPortalCache);
	private static final PortalCacheIndexer<String, PermissionKey, Boolean>
		_permissionPortalCacheNamePrimKeyIndexer = new PortalCacheIndexer<>(
			new PermissionKeyNamePrimKeyIndexEncoder(), _permissionPortalCache);
	private static final
		PortalCache<ResourceBlockIdsBagKey, ResourceBlockIdsBag>
			_resourceBlockIdsBagCache = MultiVMPoolUtil.getPortalCache(
				RESOURCE_BLOCK_IDS_BAG_CACHE_NAME,
				PropsValues.PERMISSIONS_OBJECT_BLOCKING_CACHE);
	private static final PortalCacheIndexer
		<String, ResourceBlockIdsBagKey, ResourceBlockIdsBag>
			_resourceBlockIdsBagCacheIndexer = new PortalCacheIndexer<>(
				new ResourceBlockIdsBagKeyIndexEncoder(),
				_resourceBlockIdsBagCache);
	private static final PortalCache<Long, UserBag> _userBagPortalCache =
		MultiVMPoolUtil.getPortalCache(
			USER_BAG_CACHE_NAME, PropsValues.PERMISSIONS_OBJECT_BLOCKING_CACHE);
	private static final PortalCache<UserGroupRoleIdsKey, long[]>
		_userGroupRoleIdsPortalCache = MultiVMPoolUtil.getPortalCache(
			PERMISSION_CHECKER_BAG_CACHE_NAME,
			PropsValues.PERMISSIONS_OBJECT_BLOCKING_CACHE);
	private static final PortalCacheIndexer<Long, UserGroupRoleIdsKey, long[]>
		_userGroupRoleIdsPortalCacheIndexer = new PortalCacheIndexer<>(
			new UserGroupRoleIdsKeyIndexEncoder(),
			_userGroupRoleIdsPortalCache);
	private static final PortalCache<UserPrimaryKeyRoleKey, Boolean>
		_userPrimaryKeyRolePortalCache = MultiVMPoolUtil.getPortalCache(
			USER_PRIMARY_KEY_ROLE_CACHE_NAME,
			PropsValues.PERMISSIONS_OBJECT_BLOCKING_CACHE);
	private static final PortalCacheIndexer
		<Long, UserPrimaryKeyRoleKey, Boolean>
			_userPrimaryKeyRolePortalCacheUserIdIndexer =
				new PortalCacheIndexer<>(
					new UserGroupRoleKeyUserIdEncoder(),
					_userPrimaryKeyRolePortalCache);
	private static final PortalCache<UserRoleKey, Boolean>
		_userRolePortalCache = MultiVMPoolUtil.getPortalCache(
			USER_ROLE_CACHE_NAME,
			PropsValues.PERMISSIONS_OBJECT_BLOCKING_CACHE);
	private static final PortalCacheIndexer<Long, UserRoleKey, Boolean>
		_userRolePortalCacheIndexer = new PortalCacheIndexer<>(
			new UserRoleKeyIndexEncoder(), _userRolePortalCache);

	private static class PermissionKey implements Serializable {

		@Override
		public boolean equals(Object obj) {
			PermissionKey permissionKey = (PermissionKey)obj;

			if ((permissionKey._groupId == _groupId) &&
				Validator.equals(permissionKey._name, _name) &&
				Validator.equals(permissionKey._primKey, _primKey) &&
				Arrays.equals(permissionKey._roleIds, _roleIds) &&
				Validator.equals(permissionKey._actionId, _actionId)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hashCode = HashUtil.hash(0, _groupId);

			hashCode = HashUtil.hash(hashCode, _name);
			hashCode = HashUtil.hash(hashCode, _primKey);
			hashCode = HashUtil.hash(hashCode, _roleIds.length);

			for (long roleId : _roleIds) {
				hashCode = HashUtil.hash(hashCode, roleId);
			}

			hashCode = HashUtil.hash(hashCode, _actionId);

			return hashCode;
		}

		private PermissionKey(
			long groupId, String name, String primKey, long[] roleIds,
			String actionId) {

			_groupId = groupId;
			_name = name;
			_primKey = primKey;
			_roleIds = roleIds;
			_actionId = actionId;
		}

		private static final long serialVersionUID = 1L;

		private final String _actionId;
		private final long _groupId;
		private final String _name;
		private final String _primKey;
		private final long[] _roleIds;

	}

	private static class PermissionKeyGroupIdIndexEncoder
		implements IndexEncoder<Long, PermissionKey> {

		@Override
		public Long encode(PermissionKey permissionKey) {
			return permissionKey._groupId;
		}

	}

	private static class PermissionKeyNamePrimKeyIndexEncoder
		implements IndexEncoder<String, PermissionKey> {

		public static String encode(String name, String primKey) {
			return name.concat(StringPool.UNDERLINE).concat(primKey);
		}

		@Override
		public String encode(PermissionKey permissionKey) {
			return encode(permissionKey._name, permissionKey._primKey);
		}

	}

	private static class ResourceBlockIdsBagKey implements Serializable {

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

			return false;
		}

		@Override
		public int hashCode() {
			int hashCode = HashUtil.hash(0, _companyId);

			hashCode = HashUtil.hash(hashCode, _groupId);
			hashCode = HashUtil.hash(hashCode, _userId);
			hashCode = HashUtil.hash(hashCode, _name);

			return hashCode;
		}

		private ResourceBlockIdsBagKey(
			long companyId, long groupId, long userId, String name) {

			_companyId = companyId;
			_groupId = groupId;
			_userId = userId;
			_name = name;
		}

		private static final long serialVersionUID = 1L;

		private final long _companyId;
		private final long _groupId;
		private final String _name;
		private final long _userId;

	}

	private static class ResourceBlockIdsBagKeyIndexEncoder
		implements IndexEncoder<String, ResourceBlockIdsBagKey> {

		public static String encode(long companyId, long groupId, String name) {
			StringBundler sb = new StringBundler(5);

			sb.append(companyId);
			sb.append(StringPool.UNDERLINE);
			sb.append(groupId);
			sb.append(StringPool.UNDERLINE);
			sb.append(name);

			return sb.toString();
		}

		@Override
		public String encode(ResourceBlockIdsBagKey resourceBlockIdsBagKey) {
			return encode(
				resourceBlockIdsBagKey._companyId,
				resourceBlockIdsBagKey._groupId, resourceBlockIdsBagKey._name);
		}

	}

	private static class UserGroupRoleIdsKey implements Serializable {

		@Override
		public boolean equals(Object obj) {
			UserGroupRoleIdsKey userGroupRoleIdsKey = (UserGroupRoleIdsKey)obj;

			if ((userGroupRoleIdsKey._userId == _userId) &&
				(userGroupRoleIdsKey._groupId == _groupId)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hashCode = HashUtil.hash(0, _userId);

			return HashUtil.hash(hashCode, _groupId);
		}

		private UserGroupRoleIdsKey(long userId, long groupId) {
			_userId = userId;
			_groupId = groupId;
		}

		private static final long serialVersionUID = 1L;

		private final long _groupId;
		private final long _userId;

	}

	private static class UserGroupRoleIdsKeyIndexEncoder
		implements IndexEncoder<Long, UserGroupRoleIdsKey> {

		@Override
		public Long encode(UserGroupRoleIdsKey userGroupRoleIdsKey) {
			return userGroupRoleIdsKey._userId;
		}

	}

	private static class UserGroupRoleKeyUserIdEncoder
		implements IndexEncoder<Long, UserPrimaryKeyRoleKey> {

		@Override
		public Long encode(UserPrimaryKeyRoleKey key) {
			return key._userId;
		}

	}

	private static class UserPrimaryKeyRoleKey implements Serializable {

		@Override
		public boolean equals(Object obj) {
			UserPrimaryKeyRoleKey UserPrimaryKeyRoleKey =
				(UserPrimaryKeyRoleKey)obj;

			if ((UserPrimaryKeyRoleKey._userId == _userId) &&
				(UserPrimaryKeyRoleKey._primaryKey == _primaryKey) &&
				Validator.equals(UserPrimaryKeyRoleKey._name, _name)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hashCode = HashUtil.hash(0, _userId);

			hashCode = HashUtil.hash(hashCode, _primaryKey);
			hashCode = HashUtil.hash(hashCode, _name);

			return hashCode;
		}

		private UserPrimaryKeyRoleKey(
			long userId, long primaryKey, String name) {

			_userId = userId;
			_primaryKey = primaryKey;
			_name = name;
		}

		private static final long serialVersionUID = 1L;

		private final String _name;
		private final long _primaryKey;
		private final long _userId;

	}

	private static class UserRoleKey implements Serializable {

		@Override
		public boolean equals(Object obj) {
			UserRoleKey userRoleKey = (UserRoleKey)obj;

			if ((userRoleKey._userId == _userId) &&
				(userRoleKey._roleId == _roleId)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hashCode = HashUtil.hash(0, _userId);

			return HashUtil.hash(hashCode, _roleId);
		}

		private UserRoleKey(long userId, long roleId) {
			_userId = userId;
			_roleId = roleId;
		}

		private static final long serialVersionUID = 1L;

		private final long _roleId;
		private final long _userId;

	}

	private static class UserRoleKeyIndexEncoder
		implements IndexEncoder<Long, UserRoleKey> {

		@Override
		public Long encode(UserRoleKey userRoleKey) {
			return userRoleKey._userId;
		}

	}

}