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

package com.liferay.portal.webserver;

import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.servlet.filters.cache.CacheUtil;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class WebServerServletTokenImpl implements WebServerServletToken {

	public static final String CACHE_NAME =
		WebServerServletToken.class.getName();

	public void afterPropertiesSet() {
		_portalCache = _multiVMPool.getCache(CACHE_NAME);
	}

	public String getToken(long imageId) {
		Long key = imageId;

		String token = (String)_portalCache.get(key);

		if (token == null) {
			token = _createToken(imageId);

			_portalCache.put(key, token);
		}

		return token;
	}

	public void resetToken(long imageId) {
		_portalCache.remove(imageId);

		// Journal content

		JournalContentUtil.clearCache();

		// Layout cache

		CacheUtil.clearCache();
	}

	public void setMultiVMPool(MultiVMPool multiVMPool) {
		_multiVMPool = multiVMPool;
	}

	private String _createToken(long imageId) {
		return String.valueOf(System.currentTimeMillis());
	}

	private MultiVMPool _multiVMPool;
	private PortalCache _portalCache;

}