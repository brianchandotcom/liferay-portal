/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.internal;

import com.liferay.portal.kernel.servlet.Header;
import com.liferay.portal.kernel.servlet.MetaInfoCacheServletResponse;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.CacheControl;
import javax.portlet.MimeResponse;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author Brian Wing Shun Chan
 * @author Deepak Gothe
 */
public class CacheControlImpl implements CacheControl {

	public CacheControlImpl(
		String eTag, int expirationTime, boolean publicScope,
		boolean useCachedContent, MimeResponseImpl mimeResponseImpl) {

		_eTag = eTag;
		_expirationTime = expirationTime;
		_publicScope = publicScope;
		_useCachedContent = useCachedContent;
		_mimeResponseImpl = mimeResponseImpl;
	}

	@Override
	public String getETag() {
		return _eTag;
	}

	@Override
	public int getExpirationTime() {
		return _expirationTime;
	}

	@Override
	public boolean isPublicScope() {
		return _publicScope;
	}

	@Override
	public void setETag(String eTag) {
		_eTag = eTag;

		if (eTag == null) {
			HttpServletResponse httpServletResponse =
				_mimeResponseImpl.getHttpServletResponse();

			if (httpServletResponse.getHeader(MimeResponse.ETAG) != null) {
				_removeETag(httpServletResponse);

				return;
			}
		}

		_mimeResponseImpl.setProperty(MimeResponse.ETAG, eTag);
	}

	@Override
	public void setExpirationTime(int expirationTime) {
		_expirationTime = expirationTime;

		_mimeResponseImpl.setProperty(
			MimeResponse.EXPIRATION_CACHE, String.valueOf(expirationTime));
	}

	@Override
	public void setPublicScope(boolean publicScope) {
		_publicScope = publicScope;

		_mimeResponseImpl.setProperty(
			MimeResponse.PUBLIC_SCOPE, String.valueOf(publicScope));
	}

	@Override
	public void setUseCachedContent(boolean useCachedContent) {
		_useCachedContent = useCachedContent;

		_mimeResponseImpl.setProperty(
			MimeResponse.USE_CACHED_CONTENT, String.valueOf(useCachedContent));
	}

	@Override
	public boolean useCachedContent() {
		return _useCachedContent;
	}

	private void _removeETag(HttpServletResponse httpServletResponse) {
		while (httpServletResponse instanceof HttpServletResponseWrapper) {
			if (httpServletResponse instanceof MetaInfoCacheServletResponse) {
				MetaInfoCacheServletResponse metaInfoCacheServletResponse =
					(MetaInfoCacheServletResponse)httpServletResponse;

				Map<String, Set<Header>> headers =
					metaInfoCacheServletResponse.getHeaders();

				headers.remove(MimeResponse.ETAG);
			}

			HttpServletResponseWrapper httpServletResponseWrapper =
				(HttpServletResponseWrapper)httpServletResponse;

			ServletResponse servletResponse =
				httpServletResponseWrapper.getResponse();

			httpServletResponse = (HttpServletResponse)servletResponse;
		}

		Map<String, Collection<String>> headers = new HashMap<>();

		Collection<String> headerNames = httpServletResponse.getHeaderNames();

		for (String headerName : headerNames) {
			if (!headerName.equals(MimeResponse.ETAG)) {
				headers.put(
					headerName, httpServletResponse.getHeaders(headerName));
			}
		}

		String characterEncoding = httpServletResponse.getCharacterEncoding();
		String contentType = httpServletResponse.getContentType();
		Locale locale = httpServletResponse.getLocale();
		int status = httpServletResponse.getStatus();

		httpServletResponse.reset();

		for (Map.Entry<String, Collection<String>> headerEntry :
				headers.entrySet()) {

			String headerName = headerEntry.getKey();

			for (String header : headerEntry.getValue()) {
				httpServletResponse.addHeader(headerName, header);
			}
		}

		if (characterEncoding != null) {
			httpServletResponse.setCharacterEncoding(characterEncoding);
		}

		if (contentType != null) {
			httpServletResponse.setContentType(contentType);
		}

		if (locale != null) {
			httpServletResponse.setLocale(locale);
		}

		if (status != HttpServletResponse.SC_OK) {
			httpServletResponse.setStatus(status);
		}
	}

	private String _eTag;
	private int _expirationTime;
	private final MimeResponseImpl _mimeResponseImpl;
	private boolean _publicScope;
	private boolean _useCachedContent;

}