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

package com.liferay.web.proxy.web.internal.servlet.request;

import com.liferay.portal.upload.LiferayServletRequest;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author Daniel Couso
 */
public class WebProxyServletRequest extends HttpServletRequestWrapper {

	public WebProxyServletRequest(HttpServletRequest httpServletRequest)
		throws IOException {

		super(httpServletRequest);

		_liferayServletRequest = new LiferayServletRequest(httpServletRequest);

		readInputStream(_liferayServletRequest.getInputStream());

		_liferayServletRequest.setFinishedReadingOriginalStream(true);
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		if (_liferayServletRequest != null) {
			return _liferayServletRequest.getInputStream();
		}

		return super.getInputStream();
	}

	protected void readInputStream(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[4096];

		while (inputStream.read(buffer, 0, 4096) > 0) {
		}
	}

	private final LiferayServletRequest _liferayServletRequest;

}