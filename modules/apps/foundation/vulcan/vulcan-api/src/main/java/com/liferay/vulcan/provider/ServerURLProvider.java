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

package com.liferay.vulcan.provider;

import aQute.bnd.annotation.ConsumerType;

import javax.servlet.http.HttpServletRequest;

/**
 * Instances of this interface will be used to provide the final URL based on
 * the HTTP servlet request. The implementation will be dependant on the proxies
 * configuration, SSO...
 *
 * @author Javier Gamarra
 * @review
 */
@ConsumerType
public interface ServerURLProvider {

	/**
	 * Returns the original url of the HTTP servlet request
	 *
	 * @param  httpServletRequest the HTTP request
	 * @return an string URL built based on the HTTP servlet request
	 * @review
	 */
	public String getServerURL(HttpServletRequest httpServletRequest);

}