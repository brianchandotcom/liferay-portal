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

package com.liferay.portal.sharepoint;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.WebDAVUtil;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.util.PropsUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bruno Farache
 */
public class SharepointUtil {

	public static final String VEERMER_URLENCODED =
		"application/x-vermeer-urlencoded";

	public static final String VERSION = "6.0.2.8117";

	public static long getGroupId(String path) {
		long groupId = 0;

		long companyId = CompanyThreadLocal.getCompanyId();

		try {
			groupId = WebDAVUtil.getGroupId(companyId, path);
		}
		catch (WebDAVException wde) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get groupId for path " + path);
			}
		}

		return groupId;
	}

	public static String[] getPathArray(String path) {
		path = HttpUtil.fixPath(path, true, true);

		return StringUtil.split(path, CharPool.SLASH);
	}

	public static SharepointStorage getStorage(String path) {
		String storageClass = null;

		if (path == null) {
			return null;
		}

		String[] pathArray = getPathArray(path);

		if (pathArray.length == 0) {
			storageClass = CompanySharepointStorageImpl.class.getName();
		}
		else if (pathArray.length == 1) {
			storageClass = GroupSharepointStorageImpl.class.getName();
		}
		else if (pathArray.length >= 2) {
			storageClass = getStorageClass(pathArray[1]);
		}

		if (_log.isInfoEnabled()) {
			_log.info("Storage class for path " + path + " is " + storageClass);
		}

		return (SharepointStorage)InstancePool.get(storageClass);
	}

	public static String getStorageClass(String token) {
		return _instance._getStorageClass(token);
	}

	public static String getStorageToken(String className) {
		return _instance._getStorageToken(className);
	}

	public static Collection<String> getStorageTokens() {
		return _instance._getStorageTokens();
	}

	public static String replaceBackSlashes(String value) {
		return StringUtil.replace(value, "\\", StringPool.BLANK);
	}

	public static String stripService(String url, boolean trailingSlash) {
		return _instance._stripService(url, trailingSlash);
	}

	private SharepointUtil() {
		_storageMap = new HashMap<String, String>();

		String[] tokens = PropsUtil.getArray(
			PropsKeys.SHAREPOINT_STORAGE_TOKENS);

		for (String token : tokens) {
			Filter filter = new Filter(token);

			String className = PropsUtil.get(
				PropsKeys.SHAREPOINT_STORAGE_CLASS, filter);

			if (Validator.isNotNull(className)) {
				_storageMap.put(className, token);
			}
		}
	}

	private String _getStorageClass(String token) {
		for (Map.Entry<String, String> entry : _storageMap.entrySet()) {
			String value = entry.getValue();

			if (value.equals(token)) {
				return entry.getKey();
			}
		}

		return null;
	}

	private String _getStorageToken(String className) {
		return _storageMap.get(className);
	}

	private Collection<String> _getStorageTokens() {
		return _storageMap.values();
	}

	private String _stripService(String url, boolean trailingSlash) {
		url = _stripService(url, "sharepoint", trailingSlash);
		url = _stripService(url, "webdav", trailingSlash);

		return url;
	}

	private String _stripService(
		String url, String service, boolean trailingSlash) {

		if (trailingSlash) {
			service = service + StringPool.SLASH;
		}
		else {
			service = StringPool.SLASH + service;
		}

		int pos = url.lastIndexOf(service);

		if (pos != -1) {
			url = url.substring(pos + service.length());
		}

		return url;
	}

	private static final Log _log = LogFactoryUtil.getLog(SharepointUtil.class);

	private static final SharepointUtil _instance = new SharepointUtil();

	private final Map<String, String> _storageMap;

}