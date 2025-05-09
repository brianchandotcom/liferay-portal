/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.hashed.files.request.helper;

import com.liferay.frontend.js.web.internal.hashed.files.request.RequestHelperInfo;

/**
 * @author Iván Zaera Avellón
 */
public class LanguageRequestHelperInfo implements RequestHelperInfo {

	@Override
	public String getETag() {
		return null;
	}

	public String getLanguageId() {
		return _languageId;
	}

	@Override
	public long getMaxAge() {
		return _maxAge;
	}

	public String getWebContextPath() {
		return _webContextPath;
	}

	@Override
	public boolean isImmutable() {
		return false;
	}

	@Override
	public boolean isSendNoCache() {
		return _sendNoCache;
	}

	protected LanguageRequestHelperInfo(
		String languageId, long maxAge, boolean sendNoCache,
		String webContextPath) {

		_languageId = languageId;
		_maxAge = maxAge;
		_sendNoCache = sendNoCache;
		_webContextPath = webContextPath;
	}

	private final String _languageId;
	private final long _maxAge;
	private final boolean _sendNoCache;
	private final String _webContextPath;

}