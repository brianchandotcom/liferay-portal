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

package com.liferay.portal.kernel.security.sso;

import java.io.IOException;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Michael C. Han
 */
public interface OpenSSO {

	public Map<String, String> getAttributes(
		HttpServletRequest httpServletRequest, String serviceUrl);

	public String getSubjectId(
		HttpServletRequest httpServletRequest, String serviceUrl);

	public boolean isAuthenticated(
			HttpServletRequest httpServletRequest, String serviceUrl)
		throws IOException;

	public boolean isValidServiceUrl(String serviceUrl);

	public boolean isValidUrl(String url);

	public boolean isValidUrls(String[] urls);

}