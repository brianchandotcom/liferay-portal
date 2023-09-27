/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.cache;

/**
 * @author Joseph Shum
 */
public class PortalCacheException extends RuntimeException {

	public PortalCacheException() {
	}

	public PortalCacheException(String msg) {
		super(msg);
	}

	public PortalCacheException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public PortalCacheException(Throwable throwable) {
		super(throwable);
	}

}