/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.lar.ExportImportThreadLocal;
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
		if (ExportImportThreadLocal.isImportInProcess() ||
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
			Map<Serializable, Object> localCache = _localCache.get();

			localCache.clear();
		}
	}

	public static PermissionCheckerBag getBag(long userId, long groupId) {
		BagKey bagKey = new BagKey(userId, groupId);

		return doGet(bagKey, _permissionCheckerBagPortalCache);
	}

	public static Boolean getPermission(
		long userId, boolean signedIn, long groupId, String name,
		String primKey, String actionId) {

		PermissionKey permissionKey = new PermissionKey(
			userId, signedIn, groupId, name, primKey, actionId);

		return doGet(permissionKey, _permissionPortalCache);
	}

	public static ResourceBlockIdsBag getResourceBlockIdsBag(
		long companyId, long groupId, long userId, String name) {

		ResourceBlockIdsBagKey resourceBlockIdsBagKey =
			new ResourceBlockIdsBagKey(companyId, groupId, userId, name);

		return doGet(resourceBlockIdsBagKey, _resourceBlockIdsBagCache);
	}

	public static PermissionCheckerBag putBag(
		long userId, long groupId, PermissionCheckerBag bag) {

		if (bag == null) {
			return null;
		}

		BagKey bagKey = new BagKey(userId, groupId);

		doPut(bagKey, bag, _permissionCheckerBagPortalCache);

		return bag;
	}

	public static void putPermission(
		long userId, boolean signedIn, long groupId, String name,
		String primKey, String actionId, Boolean value) {

		PermissionKey permissionKey = new PermissionKey(
			userId, signedIn, groupId, name, primKey, actionId);

		doPut(permissionKey, value, _permissionPortalCache);
	}

	public static ResourceBlockIdsBag putResourceBlockIdsBag(
		long companyId, long groupId, long userId, String name,
		ResourceBlockIdsBag resourceBlockIdsBag) {

		if (resourceBlockIdsBag == null) {
			return null;
		}

		ResourceBlockIdsBagKey resourceBlockIdsBagKey =
			new ResourceBlockIdsBagKey(companyId, groupId, userId, name);

		doPut(
			resourceBlockIdsBagKey, resourceBlockIdsBag,
			_resourceBlockIdsBagCache);

		return resourceBlockIdsBag;
	}

	protected static <K extends Serializable, V, C extends PortalCache<K, V>>
		V doGet(K key, C portalCache) {

		V value = null;

		if (_localCacheAvailable) {
			Map<K, V> localCache = _localCache.get();

			value = localCache.get(key);
		}

		if (value == null) {
			value = portalCache.get(key);
		}

		return value;
	}

	protected static <K extends Serializable, V, C extends PortalCache<K, V>>
		void doPut(K key, V value, C portalCache) {

		if (_localCacheAvailable) {
			Map<K, V> localCache = _localCache.get();

			localCache.put(key, value);
		}

		portalCache.put(key, value);
	}

	private static ThreadLocal<LRUMap> _localCache;
	private static boolean _localCacheAvailable;
	private static PortalCache<BagKey, PermissionCheckerBag>
		_permissionCheckerBagPortalCache = MultiVMPoolUtil.getCache(
			PERMISSION_CHECKER_BAG_CACHE_NAME,
			PropsValues.PERMISSIONS_OBJECT_BLOCKING_CACHE);
	private static PortalCache<PermissionKey, Boolean> _permissionPortalCache =
		MultiVMPoolUtil.getCache(
			PERMISSION_CACHE_NAME,
			PropsValues.PERMISSIONS_OBJECT_BLOCKING_CACHE);
	private static PortalCache<ResourceBlockIdsBagKey, ResourceBlockIdsBag>
		_resourceBlockIdsBagCache = MultiVMPoolUtil.getCache(
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