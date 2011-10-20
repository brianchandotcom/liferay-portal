/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.lar.ImportExportThreadLocal;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.io.Serializable;

import java.util.Map;

import org.apache.commons.collections.map.LRUMap;

/**
 * @author Charles May
 * @author Michael Young
 * @author Shuyang Zhou
 * @author Connor McKay
 */
public class PermissionCacheUtil {

	public static final String PERMISSION_CACHE_NAME =
		PermissionCacheUtil.class.getName() + "_PERMISSION";

	public static final String PERMISSION_CHECKER_BAG_CACHE_NAME =
		PermissionCacheUtil.class.getName() + "_PERMISSION_CHECKER_BAG";

	public static final String RESOURCE_BLOCK_IDS_BAG_CACHE_NAME =
		PermissionCacheUtil.class.getName() + "_RESOURCE_BLOCK_IDS_BAG";

	public static void clearCache() {
		if (ImportExportThreadLocal.isImportInProcess() ||
			!PermissionThreadLocal.isFlushEnabled()) {

			return;
		}

		clearLocalCache();

		_permissionCheckerBagPortalCache.removeAll();
		_permissionPortalCache.removeAll();
		_resourceBlockIdsBagCache.removeAll();
	}

	public static void clearLocalCache() {
		if (_localCacheAvailable) {
			Map<String, Object> localCache = _localCache.get();

			localCache.clear();
		}
	}

	public static PermissionCheckerBag getBag(long userId, long groupId) {
		PermissionCheckerBag bag = null;

		Serializable key = new BagKey(userId, groupId);

		if (_localCacheAvailable) {
			Map<String, Object> localCache = _localCache.get();

			bag = (PermissionCheckerBag)localCache.get(key);
		}

		if (bag == null) {
			bag = (PermissionCheckerBag)_permissionCheckerBagPortalCache.get(
				key);
		}

		return bag;
	}

	public static Boolean getPermission(
		long userId, boolean signedIn, boolean checkGuest, long groupId,
		String name, String primKey, String actionId) {

		Boolean value = null;

		Serializable key = new PermissionKey(
			userId, signedIn, checkGuest, groupId, name, primKey, actionId);

		if (_localCacheAvailable) {
			Map<String, Object> localCache = _localCache.get();

			value = (Boolean)localCache.get(key);
		}

		if (value == null) {
			value = (Boolean)_permissionPortalCache.get(key);
		}

		return value;
	}

	public static ResourceBlockIdsBag getResourceBlockIdsBag(
		long companyId, long userId, long groupId, String name,
		boolean checkGuest) {

		ResourceBlockIdsBag resourceBlockIdsBag = null;

		Serializable key = new ResourceBlockIdsBagKey(
			companyId, groupId, userId, name, checkGuest);

		if (_localCacheAvailable) {
			Map<String, Object> localCache = _localCache.get();

			resourceBlockIdsBag = (ResourceBlockIdsBag)localCache.get(key);
		}

		if (resourceBlockIdsBag == null) {
			resourceBlockIdsBag =
				(ResourceBlockIdsBag)_resourceBlockIdsBagCache.get(key);
		}

		return resourceBlockIdsBag;
	}

	public static PermissionCheckerBag putBag(
		long userId, long groupId, PermissionCheckerBag bag) {

		if (bag == null) {
			return null;
		}

		Serializable key = new BagKey(userId, groupId);

		if (_localCacheAvailable) {
			Map<Serializable, Object> localCache = _localCache.get();

			localCache.put(key, bag);
		}

		_permissionCheckerBagPortalCache.put(key, bag);

		return bag;
	}

	public static Boolean putPermission(
		long userId, boolean signedIn, boolean checkGuest, long groupId,
		String name, String primKey, String actionId, Boolean value) {

		if (value == null) {
			return null;
		}

		Serializable key = new PermissionKey(
			userId, signedIn, checkGuest, groupId, name, primKey, actionId);

		if (_localCacheAvailable) {
			Map<Serializable, Object> localCache = _localCache.get();

			localCache.put(key, value);
		}

		_permissionPortalCache.put(key, value);

		return value;
	}

	public static ResourceBlockIdsBag putResourceBlockIdsBag(
		long companyId, long groupId, long userId, String name,
		boolean checkGuest, ResourceBlockIdsBag resourceBlockIdsBag) {

		if (resourceBlockIdsBag == null) {
			return null;
		}

		Serializable key = new ResourceBlockIdsBagKey(
			companyId, groupId, userId, name, checkGuest);

		if (_localCacheAvailable) {
			Map<Serializable, Object> localCache = _localCache.get();

			localCache.put(key, resourceBlockIdsBag);
		}

		_resourceBlockIdsBagCache.put(key, resourceBlockIdsBag);

		return resourceBlockIdsBag;
	}

	private static ThreadLocal<LRUMap> _localCache;
	private static boolean _localCacheAvailable;
	private static PortalCache _permissionCheckerBagPortalCache =
		MultiVMPoolUtil.getCache(
			PERMISSION_CHECKER_BAG_CACHE_NAME,
			PropsValues.PERMISSIONS_OBJECT_BLOCKING_CACHE);
	private static PortalCache _permissionPortalCache =
		MultiVMPoolUtil.getCache(
			PERMISSION_CACHE_NAME,
			PropsValues.PERMISSIONS_OBJECT_BLOCKING_CACHE);
	private static PortalCache _resourceBlockIdsBagCache =
		MultiVMPoolUtil.getCache(
			RESOURCE_BLOCK_IDS_BAG_CACHE_NAME,
			PropsValues.PERMISSIONS_OBJECT_BLOCKING_CACHE);

	private static class BagKey implements Serializable {

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
		public int hashCode() {
			return (int)(_userId * 11 + _groupId);
		}

		private static final long serialVersionUID = 1L;

		private final long _groupId;
		private final long _userId;

	}

	private static class PermissionKey implements Serializable {

		public PermissionKey(
			long userId, boolean signedIn, boolean checkGuest, long groupId,
			String name, String primKey, String actionId) {

			_userId = userId;
			_signedIn = signedIn;
			_checkGuest = checkGuest;
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
				(permissionKey._checkGuest == _checkGuest) &&
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
			hashCode = HashUtil.hash(hashCode, _checkGuest);
			hashCode = HashUtil.hash(hashCode, _groupId);
			hashCode = HashUtil.hash(hashCode, _name);
			hashCode = HashUtil.hash(hashCode, _primKey);
			hashCode = HashUtil.hash(hashCode, _actionId);

			return hashCode;
		}

		private static final long serialVersionUID = 1L;

		private final String _actionId;
		private final boolean _checkGuest;
		private final long _groupId;
		private final String _name;
		private final String _primKey;
		private final boolean _signedIn;
		private final long _userId;

	}

	private static class ResourceBlockIdsBagKey implements Serializable {

		public ResourceBlockIdsBagKey(
			long companyId, long groupId, long userId, String name,
			boolean checkGuest) {

			_companyId = companyId;
			_groupId = groupId;
			_userId = userId;
			_name = name;
			_checkGuest = checkGuest;
		}

		@Override
		public boolean equals(Object obj) {
			ResourceBlockIdsBagKey resourceBlockIdsKey =
				(ResourceBlockIdsBagKey)obj;

			if ((resourceBlockIdsKey._companyId == _companyId) &&
				(resourceBlockIdsKey._groupId == _groupId) &&
				(resourceBlockIdsKey._userId == _userId) &&
				(resourceBlockIdsKey._checkGuest == _checkGuest) &&
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
			hashCode = HashUtil.hash(hashCode, _checkGuest);

			return hashCode;
		}

		private static final long serialVersionUID = 1L;

		private final boolean _checkGuest;
		private final long _companyId;
		private final long _groupId;
		private final String _name;
		private final long _userId;

	}

	static {
		if (PropsValues.PERMISSIONS_THREAD_LOCAL_CACHE_MAX_SIZE > 0) {
			_localCache = new AutoResetThreadLocal<LRUMap>(
				PermissionCacheUtil.class + "._localCache",
				new LRUMap(
					PropsValues.PERMISSIONS_THREAD_LOCAL_CACHE_MAX_SIZE));
			_localCacheAvailable = true;
		}
	}

}