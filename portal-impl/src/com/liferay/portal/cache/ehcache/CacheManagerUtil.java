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

package com.liferay.portal.cache.ehcache;

import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.Field;

import java.util.concurrent.ScheduledThreadPoolExecutor;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.Configuration;

/**
 * @author Shuyang Zhou
 */
public class CacheManagerUtil {

	public static CacheManager createCacheManager(Configuration configuration) {
		CacheManager cacheManager = new CacheManager(configuration);

		try {
			ScheduledThreadPoolExecutor scheduledThreadPoolExecutor =
				(ScheduledThreadPoolExecutor)_STATISTICS_EXECUTOR_FIELD.get(
					cacheManager);

			scheduledThreadPoolExecutor.setCorePoolSize(
				PropsValues.EHCACHE_CACHE_MANAGER_STATISTICS_THREAD_POOL_SIZE);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		return cacheManager;
	}

	private static final Field _STATISTICS_EXECUTOR_FIELD;

	static {
		try {
			_STATISTICS_EXECUTOR_FIELD = ReflectionUtil.getDeclaredField(
				CacheManager.class, "statisticsExecutor");
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

}