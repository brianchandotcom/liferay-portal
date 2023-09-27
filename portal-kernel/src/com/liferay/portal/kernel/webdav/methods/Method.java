/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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