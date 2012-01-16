/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portlet.BasePreferencesImpl;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Young
 * @author Shuyang Zhou
 */
public class PortletPreferencesLocalUtil {

	public static Map<Serializable, BasePreferencesImpl> getPreferencesPool(
		long ownerId, int ownerType) {

		Serializable key = new PreferencesPoolKey(ownerId, ownerType);

		Map<Serializable, BasePreferencesImpl> preferencesPool =
			(Map<Serializable, BasePreferencesImpl>)_portalCache.get(key);

		if (preferencesPool == null) {
			preferencesPool = new HashMap<Serializable, BasePreferencesImpl>();

			_portalCache.put(key, preferencesPool);
		}

		return preferencesPool;
	}

	protected static void clearPreferencesPool() {
		_portalCache.removeAll();
	}

	protected static void clearPreferencesPool(long ownerId, int ownerType) {
		Serializable key = new PreferencesPoolKey(ownerId, ownerType);

		_portalCache.remove(key);
	}

	private static final String _CACHE_NAME =
		PortletPreferencesLocalUtil.class.getName();

	private static PortalCache _portalCache = MultiVMPoolUtil.getCache(
		_CACHE_NAME);

	private static class PreferencesPoolKey implements Serializable {

		public PreferencesPoolKey(long ownerId, int ownerType) {
			_ownerId = ownerId;
			_ownerType = ownerType;
		}

		@Override
		public boolean equals(Object obj) {
			PreferencesPoolKey preferencesPoolKey = (PreferencesPoolKey)obj;

			if ((preferencesPoolKey._ownerId == _ownerId) &&
				(preferencesPoolKey._ownerType == _ownerType)) {

				return true;
			}
			else {
				return false;
			}
		}

		@Override
		public int hashCode() {
			return (int)(_ownerId * 11 + _ownerType);
		}

		private static final long serialVersionUID = 1L;

		private final long _ownerId;
		private final int _ownerType;

	}

}