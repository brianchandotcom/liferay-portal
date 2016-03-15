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

package com.liferay.portal.dao.orm;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.osgi.framework.BundleContext;

/**
 * @author Peter Fellwock
 */
public class CustomSQLPool {

	public CustomSQLPool() {
		_sqlPoolMaps = new WeakHashMap<>();
	}

	public void clear() {
		_sqlPoolMaps = null;
		_sqlPoolMaps = new WeakHashMap<>();
	}

	public String get(BundleContext bundleContext, String id) {
		Map<String, String> bundleContextSqlPoolMap = _sqlPoolMaps.get(
			bundleContext);

		if (bundleContextSqlPoolMap != null) {
			return bundleContextSqlPoolMap.get(id);
		}

		return null;
	}

	public String get(Map<String, String> bundleContextSqlPoolMap, String id) {
		return bundleContextSqlPoolMap.get(id);
	}

	public String get(String id) {
		for (BundleContext bundleContext : _sqlPoolMaps.keySet()) {
			if (bundleContext != null) {
				Map<String, String> bundleContextSqlPoolMap = _sqlPoolMaps.get(
					bundleContext);

				if (bundleContextSqlPoolMap != null) {
					String content = bundleContextSqlPoolMap.get(id);

					if (content != null) {
						return content;
					}
				}
			}
		}

		return null;
	}

	public boolean isBundleContextLoaded(BundleContext bundleContext) {
		Map<String, String> bundleContextSqlPoolMap = _sqlPoolMaps.get(
			bundleContext);

		if (bundleContextSqlPoolMap != null) {
			return true;
		}

		return false;
	}

	public void put(BundleContext bundleContext, String id, String content) {
		Map<String, String> bundleContextSqlPoolMap = _sqlPoolMaps.get(
			bundleContext);

		if (bundleContextSqlPoolMap == null) {
			bundleContextSqlPoolMap = new HashMap<>();
			_sqlPoolMaps.put(bundleContext, bundleContextSqlPoolMap);
		}

		bundleContextSqlPoolMap.put(id, content);
	}

	private static Map<BundleContext, Map<String, String>>
		_sqlPoolMaps = new WeakHashMap<>();

}