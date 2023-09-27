/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.plugin;

import com.liferay.portal.kernel.exception.SystemException;

/**
 * @author Jorge Ferrer
 */
public class PluginPackageException extends SystemException {

	public PluginPackageException() {
	}

	public PluginPackageException(String msg) {
		super(msg);
	}

	public PluginPackageException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public PluginPackageException(Throwable throwable) {
		super(throwable);
	}

}