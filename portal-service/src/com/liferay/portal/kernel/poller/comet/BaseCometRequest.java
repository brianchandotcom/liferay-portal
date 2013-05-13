/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.util.PortalUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Edward Han
 * @author Brian Wing Shun Chan
 */
public abstract class BaseCometRequest implements CometRequest {

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public String getPathInfo() {
		return _pathInfo;
	}

	@Override
	public HttpServletRequest getRequest() {
		return _request;
	}

	@Override
	public long getTimestamp() {
		return _timestamp;
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@Override
	public void setPathInfo(String pathInfo) {
		_pathInfo = pathInfo;
	}

	public void setRequest(HttpServletRequest request) {
		setCompanyId(PortalUtil.getCompanyId(request));
		setPathInfo(request.getPathInfo());
		setUserId(PortalUtil.getUserId(request));
	}

	@Override
	public void setTimestamp(long timestamp) {
		_timestamp = timestamp;
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;
	}

	private long _companyId;
	private String _pathInfo;
	private HttpServletRequest _request;
	private long _timestamp = System.currentTimeMillis();
	private long _userId;

}