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

package com.liferay.portal.servlet.filters.audit;

import com.liferay.portal.kernel.servlet.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author Isaac Obrist
 */
public class AuditRequest extends HttpServletRequestWrapper {

	public AuditRequest(HttpServletRequest request) {
		super(request);

		_request = request;
	}

	@Override
	public String getRemoteAddr() {
		String ip = _request.getHeader(HttpHeaders.X_FORWARDED_FOR);

		if (ip != null) {
			return ip;
		}

		return super.getRemoteAddr();
	}

	private final HttpServletRequest _request;

}