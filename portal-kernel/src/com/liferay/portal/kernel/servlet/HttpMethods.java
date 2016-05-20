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

package com.liferay.portal.kernel.servlet;

/**
 * Provides constants for HTTP request methods specified in <a
 * href="http://www.rfc-base.org/rfc-2616.html">RFC 2616 Section 5.1.1</a>.
 *
 * @author Brian Wing Shun Chan
 */
public interface HttpMethods {

	public static final String CONNECT = "CONNECT";

	public static final String DELETE = "DELETE";

	public static final String GET = "GET";

	public static final String HEAD = "HEAD";

	public static final String OPTIONS = "OPTIONS";

	public static final String POST = "POST";

	public static final String PUT = "PUT";

	public static final String TRACE = "TRACE";

}