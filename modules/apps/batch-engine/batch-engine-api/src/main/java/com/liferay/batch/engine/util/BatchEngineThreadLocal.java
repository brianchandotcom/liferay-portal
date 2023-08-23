/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.util;

import com.liferay.petra.lang.CentralizedThreadLocal;

/**
 * @author Gabriel Albuquerque
 */
public class BatchEngineThreadLocal {

	public static String getBundleNamespace() {
		return _bundleNamespaceThreadLocal.get();
	}

	public static void setBundleNamespace(String bundleNamespace) {
		_bundleNamespaceThreadLocal.set(bundleNamespace);
	}

	private static final ThreadLocal<String> _bundleNamespaceThreadLocal =
		new CentralizedThreadLocal<>(
			BatchEngineThreadLocal.class + "._bundleNamespaceThreadLocal");

}