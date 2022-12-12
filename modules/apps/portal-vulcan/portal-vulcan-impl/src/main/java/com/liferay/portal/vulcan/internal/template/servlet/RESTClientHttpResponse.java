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

package com.liferay.portal.vulcan.internal.template.servlet;

import com.liferay.portal.kernel.servlet.DummyHttpServletResponse;
import com.liferay.portal.kernel.servlet.HttpHeaders;

import java.util.Objects;

/**
 * @author Alejandro Tardín
 */
public class RESTClientHttpResponse extends DummyHttpServletResponse {

	@Override
	public String getContentType() {
		return _contentType;
	}

	@Override
	public void setContentType(String contentType) {
		_contentType = contentType;
	}

	@Override
	public void setHeader(String name, String value) {
		if (Objects.equals(name, HttpHeaders.CONTENT_TYPE)) {
			_contentType = value;
		}

		super.setHeader(name, value);
	}

	private String _contentType;

}