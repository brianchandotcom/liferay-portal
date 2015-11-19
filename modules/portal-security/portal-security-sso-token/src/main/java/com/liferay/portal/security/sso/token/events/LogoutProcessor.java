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

package com.liferay.portal.security.sso.token.events;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Defines the public interface for logout behavior.
 * 
 * <p>
 * Custom <code>LogoutProcessor</code> classes can override these default
 * implementations:
 * </p>
 * 
 * <ul>
 * <li>
 * {@link com.liferay.portal.security.sso.token.internal.events.CookieLogoutProcessor}
 * </li>
 * <li>
 * {@link com.liferay.portal.security.sso.token.internal.events.RedirectLogoutProcessor
}
 * </li>
 * </ul>
 * 
 * @author Michael C. Han
 */
public interface LogoutProcessor {

	public LogoutProcessorType getLogoutProcessorType();

	public void logout(
			HttpServletRequest request, HttpServletResponse response,
			String... parameters)
		throws IOException;

}