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

package com.liferay.portal.security.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public interface AutoLogin {

	/**
	 * Set a request attribute with this variable to tell the AutoLoginFilter to
	 * stop processing filters and redirect the user to a specified location.
	 */
	public static final String AUTO_LOGIN_REDIRECT = "AUTO_LOGIN_REDIRECT";

	/**
	 * Set a request attribute with this variable to tell the AutoLoginFilter to
	 * continue processing filters and then redirect the user to a specified
	 * location.
	 */
	public static final String AUTO_LOGIN_REDIRECT_AND_CONTINUE =
		"AUTO_LOGIN_REDIRECT_AND_CONTINUE";

	public String[] handleException(
			HttpServletRequest request, HttpServletResponse response,
			Exception e)
		throws AutoLoginException;

	public String[] login(
			HttpServletRequest request, HttpServletResponse response)
		throws AutoLoginException;

}