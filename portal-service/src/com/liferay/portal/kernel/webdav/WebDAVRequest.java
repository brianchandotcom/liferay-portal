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

package com.liferay.portal.kernel.webdav;

import com.liferay.portal.security.permission.PermissionChecker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public interface WebDAVRequest {

	public long getCompanyId();

	public long getGroupId();

	public HttpServletRequest getHttpServletRequest();

	public HttpServletResponse getHttpServletResponse();

	public String getLockUuid();

	public String getPath();

	public String[] getPathArray();

	public PermissionChecker getPermissionChecker();

	public String getRootPath();

	public long getUserId();

	public WebDAVStorage getWebDAVStorage();

	public boolean isAppleDoubleRequest();

	public boolean isLitmus();

	public boolean isMac();

	public boolean isManualCheckInRequired();

	public boolean isWindows();

}