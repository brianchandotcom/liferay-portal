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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.servlet.SharedSessionServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 */
public class SessionLayoutClone implements LayoutClone {

	@Override
	public String get(HttpServletRequest request, long plid) {
		HttpSession session = getPortalSession(request);

		return (String)session.getAttribute(encodeKey(plid));
	}

	@Override
	public void update(
		HttpServletRequest request, long plid, String typeSettings) {

		HttpSession session = getPortalSession(request);

		session.setAttribute(encodeKey(plid), typeSettings);
	}

	protected String encodeKey(long plid) {
		return SessionLayoutClone.class.getName().concat(
			StringPool.POUND).concat(StringUtil.toHexString(plid));
	}

	protected HttpSession getPortalSession(HttpServletRequest request) {
		HttpServletRequest originalRequest = request;

		while (originalRequest instanceof HttpServletRequestWrapper) {
			if (originalRequest instanceof SharedSessionServletRequest) {
				SharedSessionServletRequest sharedSessionServletRequest =
					(SharedSessionServletRequest)originalRequest;

				return sharedSessionServletRequest.getSharedSession();
			}

			originalRequest = (HttpServletRequest)
				((HttpServletRequestWrapper)originalRequest).getRequest();
		}

		return request.getSession();
	}

}