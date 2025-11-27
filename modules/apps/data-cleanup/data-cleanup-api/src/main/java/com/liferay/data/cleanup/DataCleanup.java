/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup;

import com.liferay.portal.kernel.cache.CacheRegistryUtil;

/**
 * @author Maríano Álvaro Sáiz
 */
public abstract class DataCleanup {

	public static final String MODULE_DATA_CLEANUP = "module";

	public static final String SYSTEM_DATA_CLEANUP = "system";

	public void cleanup() throws Exception {
		doCleanup();

		CacheRegistryUtil.clear();
	}

	public abstract String getLabel();

	public abstract String getType();

	protected abstract void doCleanup() throws Exception;

}