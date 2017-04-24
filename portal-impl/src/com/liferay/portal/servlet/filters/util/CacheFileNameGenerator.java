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

package com.liferay.portal.servlet.filters.util;

import com.liferay.portal.kernel.cache.key.CacheKeyGenerator;
import com.liferay.portal.kernel.cache.key.CacheKeyGeneratorUtil;
import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Miguel Pastor
 */
public class CacheFileNameGenerator {

	public String getCacheFileName(
		Class<?> clazz, HttpServletRequest request, String[] paramsToRemove,
		String[] cacheKeyItems) {

		CacheKeyGenerator cacheKeyGenerator =
			CacheKeyGeneratorUtil.getCacheKeyGenerator(clazz.getName());

		cacheKeyGenerator.append(HttpUtil.getProtocol(request.isSecure()));
		cacheKeyGenerator.append(StringPool.UNDERLINE);
		cacheKeyGenerator.append(request.getRequestURI());

		StringBundler sb = new StringBundler();

		sb.append(StringPool.QUESTION);
		sb.append(request.getQueryString());

		String queryString = sb.toString();

		if (paramsToRemove != null) {
			for (String param : paramsToRemove) {
				queryString = HttpUtil.removeParameter(queryString, param);
			}
		}

		queryString = HttpUtil.getQueryString(queryString);

		String queryStringDigest = DigesterUtil.digestBase64(
			Digester.SHA_256, queryString);

		queryStringDigest = queryStringDigest.replaceAll(
			"\\+", StringPool.DASH);
		queryStringDigest = queryStringDigest.replaceAll(
			StringPool.SLASH, StringPool.AT);
		queryStringDigest = queryStringDigest.replaceAll(
			StringPool.EQUAL, StringPool.UNDERLINE);

		cacheKeyGenerator.append(queryStringDigest);

		if (cacheKeyItems != null) {
			for (String cacheKeyItem : cacheKeyItems) {
				cacheKeyGenerator.append(cacheKeyItem);
			}
		}

		return String.valueOf(cacheKeyGenerator.finish());
	}

}