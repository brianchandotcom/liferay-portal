/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.loader.modules.extender.npm;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Joao Victor Alves
 */
public class NPMJavaScriptLastModifiedUtil {

	public static long getLastModified() {
		return _lastModified.get();
	}

	public static void setLastModified(long lastModified) {
		_lastModified.set(lastModified);
	}

	public static void updateLastModified(long lastModified) {
		_lastModified.accumulateAndGet(lastModified, Math::max);
	}

	private static final AtomicLong _lastModified = new AtomicLong();

}