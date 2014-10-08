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

package com.liferay.portal.servlet.filters.cache;

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.servlet.filters.CacheResponseData;

/**
 * @author Alexander Chow
 * @author Michael Young
 */
public class CacheUtil {

	public static final String CACHE_NAME = CacheUtil.class.getName();

	public static void clearCache() {
		if (ExportImportThreadLocal.isImportInProcess()) {
			return;
		}

		_portalCache.removeAll();
	}

	public static void clearCache(long companyId) {
		clearCache();
	}

	public static CacheResponseData getCacheResponseData(
		long companyId, String key) {

		if (Validator.isNull(key)) {
			return null;
		}

		key = _encodeKey(companyId, key);

		return _portalCache.get(key);
	}

	public static void putCacheResponseData(
		long companyId, String key, CacheResponseData data) {

		if (data != null) {
			key = _encodeKey(companyId, key);

			_portalCache.put(key, data);
		}
	}

	private static String _encodeKey(long companyId, String key) {
		StringBundler sb = new StringBundler(5);

		sb.append(CACHE_NAME);
		sb.append(StringPool.POUND);
		sb.append(StringUtil.toHexString(companyId));
		sb.append(StringPool.POUND);
		sb.append(key);

		return sb.toString();
	}

	private static final PortalCache<String, CacheResponseData> _portalCache =
		MultiVMPoolUtil.getCache(CACHE_NAME);

}