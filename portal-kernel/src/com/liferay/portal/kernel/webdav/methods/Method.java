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

package com.liferay.portal.kernel.webdav.methods;

import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.WebDAVRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public interface Method {

	public static final String COPY = "COPY";

	public static final String DELETE = "DELETE";

	public static final String GET = "GET";

	public static final String HEAD = "HEAD";

	public static final String LOCK = "LOCK";

	public static final String MKCOL = "MKCOL";

	public static final String MOVE = "MOVE";

	public static final String OPTIONS = "OPTIONS";

	public static final String PROPFIND = "PROPFIND";

	public static final String PROPPATCH = "PROPPATCH";

	public static final String PUT = "PUT";

	public static final String[] SUPPORTED_METHOD_NAMES = {
		COPY, DELETE, GET, HEAD, LOCK, MKCOL, MOVE, OPTIONS, PROPFIND,
		PROPPATCH, PUT, Method.UNLOCK
	};

	public static final String UNLOCK = "UNLOCK";

	/**
	 * Returns -1 or a supported HTTP status code. If it is -1, then the status
	 * code has already been set. Otherwise, the status code needs to be set by
	 * the caller.
	 *
	 * @param  webDAVRequest the WebDAV request
	 * @return -1 or a supported HTTP status code. If it is -1, then the status
	 *         code has already been set. Otherwise, the status code needs to be
	 *         set by the caller.
	 */
	public int process(WebDAVRequest webDAVRequest) throws WebDAVException;

}