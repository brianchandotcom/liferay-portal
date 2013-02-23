/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.util.ContentTypes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author Hugo Huijser
 * @author Igor Spasic
 */
public class JSONContentTypeServletResponse extends HttpServletResponseWrapper {

	public JSONContentTypeServletResponse(HttpServletResponse response) {
		super(response);
	}

	@Override
	public void setContentType(String contentType) {
		if (contentType.equalsIgnoreCase(ContentTypes.APPLICATION_JSON)) {
			contentType = ContentTypes.TEXT_JAVASCRIPT;
		}

		super.setContentType(contentType);
	}

}