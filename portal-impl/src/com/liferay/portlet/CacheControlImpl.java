/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet;

import javax.portlet.CacheControl;
import javax.portlet.MimeResponse;

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

	private String _eTag;
	private int _expirationTime;
	private MimeResponseImpl _mimeResponseImpl;
	private boolean _publicScope;
	private boolean _useCachedContent;

}