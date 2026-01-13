/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.resource;

import com.liferay.petra.io.StreamUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ContentTypes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.nio.charset.StandardCharsets;

import java.util.ResourceBundle;

/**
 * @author Iván Zaera Avellón
 */
public class JavaScriptFrontendResource implements FrontendResource {

	public JavaScriptFrontendResource(
		boolean aPrivate, String eTag, boolean immutable, Language language,
		long maxAge, ResourceBundle resourceBundle, boolean sendNoCache,
		URL url) {

		_eTag = eTag;
		_immutable = immutable;
		_language = language;
		_maxAge = maxAge;
		_resourceBundle = resourceBundle;
		_sendNoCache = sendNoCache;
		_url = url;

		_private = aPrivate;
	}

	@Override
	public String getContentType() {
		return ContentTypes.APPLICATION_JAVASCRIPT;
	}

	@Override
	public String getETag() {
		return _eTag;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		if (_resourceBundle == null) {
			return _url.openStream();
		}

		String content = _language.process(
			() -> _resourceBundle, _resourceBundle.getLocale(),
			StreamUtil.toString(_url.openStream()));

		return new ByteArrayInputStream(
			content.getBytes(StandardCharsets.UTF_8));
	}

	@Override
	public long getMaxAge() {
		return _maxAge;
	}

	@Override
	public boolean isImmutable() {
		return _immutable;
	}

	@Override
	public boolean isPrivate() {
		return _private;
	}

	@Override
	public boolean isSendNoCache() {
		return _sendNoCache;
	}

	private final String _eTag;
	private final boolean _immutable;
	private final Language _language;
	private final long _maxAge;
	private final boolean _private;
	private final ResourceBundle _resourceBundle;
	private final boolean _sendNoCache;
	private final URL _url;

}