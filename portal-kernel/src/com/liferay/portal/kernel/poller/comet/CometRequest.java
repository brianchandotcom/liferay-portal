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

package com.liferay.portal.kernel.poller.comet;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Edward Han
 * @author Brian Wing Shun Chan
 */
public interface CometRequest {

	public long getCompanyId();

	public String getParameter(String name);

	public Map<String, String[]> getParameterMap();

	public Enumeration<String> getParameterNames();

	public String getPathInfo();

	public HttpServletRequest getRequest();

	public long getTimestamp();

	public long getUserId();

	public void setCompanyId(long companyId);

	public void setPathInfo(String pathInfo);

	public void setTimestamp(long timestamp);

	public void setUserId(long userId);

}