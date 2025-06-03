/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.hashed.files.request.helper;

/**
 * @author Iván Zaera Avellón
 */
public class StaticFileBaseRequestHelperInfo implements BaseRequestHelperInfo {

	@Override
	public String getETag() {
		return _eTag;
	}

	@Override
	public long getMaxAge() {
		return _maxAge;
	}

	public String getResourceURI() {
		return _resourceURI;
	}

	@Override
	public boolean isImmutable() {
		return _immutable;
	}

	@Override
	public boolean isSendNoCache() {
		return _sendNoCache;
	}

	protected StaticFileBaseRequestHelperInfo(
		String eTag, boolean immutable, long maxAge, String resourceURI,
		boolean sendNoCache) {

		_eTag = eTag;
		_immutable = immutable;
		_maxAge = maxAge;
		_resourceURI = resourceURI;
		_sendNoCache = sendNoCache;
	}

	private final String _eTag;
	private final boolean _immutable;
	private final long _maxAge;
	private final String _resourceURI;
	private final boolean _sendNoCache;

}