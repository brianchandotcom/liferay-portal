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

package com.liferay.dynamic.data.mapping.form.renderer.internal.util;

import com.liferay.portal.kernel.cache.thread.local.Lifecycle;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCache;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCacheManager;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Bruno Basto
 */
public class DDMFormFieldTypesThreadLocal {

	public static boolean isFieldTypesRequested() {
		ThreadLocalCache<Boolean> threadLocalCache = _getThreadLocalCache();

		return GetterUtil.getBoolean(
			threadLocalCache.get(_FIELD_TYPES_REQUESTED));
	}

	public static void removeAll() {
		ThreadLocalCache<Boolean> threadLocalCache = _getThreadLocalCache();

		threadLocalCache.removeAll();
	}

	public static void setFieldTypesRequested(boolean fieldTypesRequested) {
		ThreadLocalCache<Boolean> threadLocalCache = _getThreadLocalCache();

		threadLocalCache.put(_FIELD_TYPES_REQUESTED, fieldTypesRequested);
	}

	private static ThreadLocalCache<Boolean> _getThreadLocalCache() {
		return ThreadLocalCacheManager.getThreadLocalCache(
			Lifecycle.REQUEST, DDMFormFieldTypesThreadLocal.class.getName());
	}

	private static final String _FIELD_TYPES_REQUESTED = "fieldTypesRequested";

}