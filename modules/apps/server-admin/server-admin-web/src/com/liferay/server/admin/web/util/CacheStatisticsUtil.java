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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.lang.management.ManagementFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

/**
 * @author Preston Crary
 */
public class CacheStatisticsUtil {

	public static List<String> getCacheManagerNames() {
		Set<ObjectName> objectNames = _mBeanServer.queryNames(
			null, _cacheManagerObjectName);

		List<String> names = new ArrayList<>(objectNames.size());

		for (ObjectName objectName : objectNames) {
			names.add(objectName.getKeyProperty("name"));
		}

		Collections.sort(names);

		return names;
	}

	public static List<CacheStatistics> getCacheStatisticsList(
		String cacheManagerName, String keywords) {

		StringBundler sb = new StringBundler(4);

		sb.append("net.sf.ehcache:type=CacheStatistics,CacheManager=");
		sb.append(cacheManagerName);
		sb.append(",name=");
		sb.append(_getKeywords(keywords));

		try {
			Set<ObjectName> objectNames = _mBeanServer.queryNames(
				null, new ObjectName(sb.toString()));

			objectNames = new ArrayList<>(objectNames);

			Collections.sort(objectNames);

			List<CacheStatistics> cacheStatisticsList = new ArrayList<>(
				objectNames.size());

			for (ObjectName objectName : objectNames) {
				cacheStatisticsList.add(
					new CacheStatistics(_mBeanServer, objectName));
			}

			return cacheStatisticsList;
		}
		catch (JMException jme) {
			throw new SystemException(jme);
		}
	}

	private static String _getKeywords(String keywords) {
		if (Validator.isNull(keywords)) {
			return StringPool.STAR;
		}

		Matcher matcher = _VALID_KEYWORD_PATTERN.matcher(keywords);

		if (!matcher.matches()) {
			return StringPool.STAR;
		}

		return StringUtil.quote(keywords, StringPool.STAR);
	}

	private static final Pattern _VALID_KEYWORD_PATTERN = Pattern.compile(
		"[A-Za-z0-9.*]+");

	private static final ObjectName _cacheManagerObjectName;
	private static final MBeanServer _mBeanServer =
		ManagementFactory.getPlatformMBeanServer();

	static {
		try {
			_cacheManagerObjectName = new ObjectName(
				"net.sf.ehcache:type=CacheManager,name=*");
		}
		catch (MalformedObjectNameException mone) {
			throw new ExceptionInInitializerError(mone);
		}
	}

}