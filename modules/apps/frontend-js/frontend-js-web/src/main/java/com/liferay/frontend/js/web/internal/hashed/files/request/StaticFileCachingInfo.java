/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.hashed.files.request;

/**
 * @author Iván Zaera Avellón
 */
public class StaticFileCachingInfo {

	public StaticFileCachingInfo(long maxAge, boolean sendNoCache) {
		_maxAge = maxAge;
		_sendNoCache = sendNoCache;
	}

	public long getMaxAge() {
		return _maxAge;
	}

	public boolean getSendNoCache() {
		return _sendNoCache;
	}

	private final long _maxAge;
	private final boolean _sendNoCache;

}