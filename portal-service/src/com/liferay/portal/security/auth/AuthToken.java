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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Amos Fong
 */
public interface AuthToken {

	public void addCSRFToken(
		HttpServletRequest request, LiferayPortletURL liferayPortletURL);

	public void addPortletInvocationToken(
		HttpServletRequest request, LiferayPortletURL liferayPortletURL);

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #checkCSRFToken(HttpServletRequest, String)}
	 */
	@Deprecated
	public void check(HttpServletRequest request) throws PortalException;

	public void checkCSRFToken(HttpServletRequest request, String origin)
		throws PrincipalException;

	public String getToken(HttpServletRequest request);

	public String getToken(
		HttpServletRequest request, long plid, String portletId);

	public boolean isValidPortletInvocationToken(
		HttpServletRequest request, Layout layout, Portlet portlet);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #isValidPortletInvocationToken(HttpServletRequest, Layout,
	 *             Portlet)}
	 */
	@Deprecated
	public boolean isValidPortletInvocationToken(
		HttpServletRequest request, long plid, String portletId,
		String strutsAction, String tokenValue);

}