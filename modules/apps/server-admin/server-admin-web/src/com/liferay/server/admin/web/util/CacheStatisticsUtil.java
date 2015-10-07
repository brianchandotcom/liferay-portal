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

package com.liferay.server.admin.web.util;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.lang.management.ManagementFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

/**
 * @author Preston Crary
 */
public class CacheStatisticsUtil {

	public static List<String> getCacheManagerNames() {
		List<String> names = null;

		try {
			Set<ObjectName> objectNames = _mBeanServer.queryNames(
				null,
				new ObjectName("net.sf.ehcache:type=CacheManager,name=*"));

			names = new ArrayList<>(objectNames.size());

			for (ObjectName objectName : objectNames) {
				names.add(objectName.getKeyProperty("name"));
			}
		}
		catch (MalformedObjectNameException mone) {
			throw new SystemException(mone);
		}

		return names;
	}

	public static List<CacheStatistics> getCacheStatistics(
		String cacheManagerName, String keywords) {

		List<CacheStatistics> cacheManagerStatistics = null;

		StringBundler sb = new StringBundler(5);

		sb.append("net.sf.ehcache:type=CacheStatistics,CacheManager=");
		sb.append(cacheManagerName);
		sb.append(",name=*");

		if (_isValidKeywords(keywords)) {
			sb.append(keywords);
			sb.append("*");
		}

		try {
			Set<ObjectName> objectNames = _mBeanServer.queryNames(
				null, new ObjectName(sb.toString()));

			List<ObjectName> cacheStatisticsNames = new ArrayList<>(
				objectNames);

			Collections.sort(cacheStatisticsNames);

			cacheManagerStatistics = new ArrayList<>(
				cacheStatisticsNames.size());

			for (ObjectName objectName : cacheStatisticsNames) {
				cacheManagerStatistics.add(
					new CacheStatistics(_mBeanServer, objectName));
			}
		}
		catch (MalformedObjectNameException mone) {
			throw new SystemException(mone);
		}

		return cacheManagerStatistics;
	}

	private static boolean _isValidKeywords(String keywords) {
		if (Validator.isNull(keywords)) {
			return false;
		}

		Matcher matcher = _VALID_KEYWORD_PATTERN.matcher(keywords);

		return matcher.matches();
	}

	private static final Pattern _VALID_KEYWORD_PATTERN = Pattern.compile(
		"[A-Za-z0-9.*]+");

	private static final MBeanServer _mBeanServer =
		ManagementFactory.getPlatformMBeanServer();

}