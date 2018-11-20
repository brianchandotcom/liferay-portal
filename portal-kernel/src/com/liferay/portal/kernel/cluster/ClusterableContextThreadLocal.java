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

package com.liferay.portal.kernel.cluster;

import com.liferay.petra.lang.CentralizedThreadLocal;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tina Tian
 */
public class ClusterableContextThreadLocal {

	public static Map<String, Serializable> collectThreadLocalContext() {
		Map<String, Serializable> context = _contextThreadLocal.get();

		_contextThreadLocal.remove();

		return context;
	}

	public static void putThreadLocalContext(String key, Serializable value) {
		Map<String, Serializable> context = _contextThreadLocal.get();

		context.put(key, value);
	}

	private static final ThreadLocal<HashMap<String, Serializable>>
		_contextThreadLocal = new CentralizedThreadLocal<>(
			ClusterableContextThreadLocal.class.getName() +
				"._contextThreadLocal",
			HashMap::new);

}