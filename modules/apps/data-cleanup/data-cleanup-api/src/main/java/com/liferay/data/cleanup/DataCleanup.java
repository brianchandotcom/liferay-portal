/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup;

import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.service.ReleaseLocalServiceUtil;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Maríano Álvaro Sáiz
 */
public abstract class DataCleanup {

	public static final String MODULE_DATA_CLEANUP = "module";

	public static final String SYSTEM_DATA_CLEANUP = "system";

	public void cleanup() throws Exception {
		doCleanup();

		CacheRegistryUtil.clear();

		if (ReleaseLocalServiceUtil.fetchRelease(getServletContextName()) ==
				null) {

			_enabled.set(false);
		}
	}

	public abstract String getHelpLabel();

	public abstract String getLabel();

	public abstract String getServletContextName();

	public abstract String getType();

	public boolean isEnabled() {
		return _enabled.get();
	}

	protected abstract void doCleanup() throws Exception;

	private final AtomicBoolean _enabled = new AtomicBoolean(true);

}