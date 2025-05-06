/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.hashed.files.request;

/**
 * @author Iván Zaera Avellón
 */
public class StaticFileRequestInfo implements RequestInfo {

	@Override
	public String getHash() {
		return _hash;
	}

	@Override
	public long getMaxAge() {
		return _maxAge;
	}

	public String getRealModuleURI() {
		return _realModuleURI;
	}

	@Override
	public boolean getSendNoCache() {
		return _sendNoCache;
	}

	@Override
	public boolean isVirtual() {
		return _virtual;
	}

	protected StaticFileRequestInfo(
		String hash, long maxAge, String realModuleURI, boolean sendNoCache,
		boolean virtual) {

		_hash = hash;
		_maxAge = maxAge;
		_realModuleURI = realModuleURI;
		_sendNoCache = sendNoCache;
		_virtual = virtual;
	}

	private final String _hash;
	private final long _maxAge;
	private final String _realModuleURI;
	private final boolean _sendNoCache;
	private final boolean _virtual;

}