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

package com.liferay.portal.dao.orm.custom.sql;

import com.liferay.portal.kernel.concurrent.ConcurrentReferenceKeyHashMap;
import com.liferay.portal.kernel.memory.FinalizeManager;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;

/**
 * @author Peter Fellwock
 * @deprecated As of 1.0.0, with no direct replacement
 */
@Deprecated
public class CustomSQLPool {

	public CustomSQLPool() {
		_maps = new ConcurrentReferenceKeyHashMap<>(
			FinalizeManager.WEAK_REFERENCE_FACTORY);
	}

	public void clear() {
		_maps = null;

		_maps = new ConcurrentReferenceKeyHashMap<>(
			FinalizeManager.WEAK_REFERENCE_FACTORY);
	}

	public String get(BundleContext bundleContext, String id) {
		Map<String, String> map = _maps.get(bundleContext);

		if (map != null) {
			return map.get(id);
		}

		return null;
	}

	public String get(Map<String, String> map, String id) {
		return map.get(id);
	}

	public String get(String id) {
		for (Map.Entry<BundleContext, Map<String, String>> entry :
				_maps.entrySet()) {

			if (entry.getKey() == null) {
				continue;
			}

			Map<String, String> map = entry.getValue();

			if (map == null) {
				continue;
			}

			String content = map.get(id);

			if (content != null) {
				return content;
			}
		}

		return null;
	}

	public boolean isBundleContextLoaded(BundleContext bundleContext) {
		Map<String, String> map = _maps.get(bundleContext);

		if (map != null) {
			return true;
		}

		return false;
	}

	public void put(BundleContext bundleContext, String id, String content) {
		Map<String, String> map = _maps.get(bundleContext);

		if (map == null) {
			map = new HashMap<>();

			_maps.put(bundleContext, map);
		}

		map.put(id, content);
	}

	private static Map<BundleContext, Map<String, String>> _maps =
		new ConcurrentReferenceKeyHashMap<>(
			FinalizeManager.WEAK_REFERENCE_FACTORY);

}