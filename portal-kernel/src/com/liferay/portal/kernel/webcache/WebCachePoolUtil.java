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

package com.liferay.portal.kernel.webcache;

/**
 * @author Brian Wing Shun Chan
 */
public class WebCachePoolUtil {

	public static void clear() {
		getWebCachePool().clear();
	}

	public static Object get(String key, WebCacheItem wci) {
		return getWebCachePool().get(key, wci);
	}

	public static WebCachePool getWebCachePool() {
		return _webCachePool;
	}

	public static void remove(String key) {
		getWebCachePool().remove(key);
	}

	public void setWebCachePool(WebCachePool webCachePool) {
		_webCachePool = webCachePool;
	}

	private static WebCachePool _webCachePool;

}