/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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