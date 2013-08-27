/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence.impl;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.lang.reflect.Field;

import java.util.Map;

/**
 * @author Manuel de la Peña
 */
public class TableMapperFactoryTestUtil {

	public static void clearCaches() {
		Map<String, TableMapper<?, ?>> tableMappers =
			TableMapperFactory.getTableMappers();

		try {
			for (TableMapper<?, ?> tableMapper : tableMappers.values()) {
				clearCache(tableMapper, "leftToRightPortalCache");
				clearCache(tableMapper, "rightToLeftPortalCache");
			}
		}
		catch (Exception e) {
			if (log.isWarnEnabled()) {
				log.warn("Unable to clear caches", e);
			}
		}
	}

	protected static void clearCache(TableMapper<?, ?> tableMapper, String name)
		throws Exception {

		Field field = ReflectionUtil.getDeclaredField(
			tableMapper.getClass(), name);

		field.setAccessible(true);

		PortalCache<Long, long[]> portalCache =
			(PortalCache<Long, long[]>)field.get(tableMapper);

		portalCache.removeAll();

		field.setAccessible(false);
	}

	protected static Log log = LogFactoryUtil.getLog(
		TableMapperFactoryTestUtil.class);

}