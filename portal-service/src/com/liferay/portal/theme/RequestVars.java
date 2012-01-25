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

package com.liferay.portal.theme;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class RequestVars {

	public RequestVars(
		HttpServletRequest request, ThemeDisplay themeDisplay,
		long ancestorPlid, long ancestorLayoutId) {

		_request = request;
		_themeDisplay = themeDisplay;
		_ancestorPlid = ancestorPlid;
		_ancestorLayoutId = ancestorLayoutId;
	}

	public HttpServletRequest getRequest() {
		return _request;
	}

	public ThemeDisplay getThemeDisplay() {
		return _themeDisplay;
	}

	public long getAncestorPlid() {
		return _ancestorPlid;
	}

	public long getAncestorLayoutId() {
		return _ancestorLayoutId;
	}

	private long _ancestorLayoutId;
	private long _ancestorPlid;
	private HttpServletRequest _request;
	private ThemeDisplay _themeDisplay;

}