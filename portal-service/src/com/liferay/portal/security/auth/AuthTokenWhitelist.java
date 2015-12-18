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

import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.model.Portlet;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tomas Polesovsky
 * @author Raymond Augé
 */
public interface AuthTokenWhitelist {

	public Set<String> getOriginCSRFWhitelist();

	public Set<String> getPortletCSRFWhitelist();

	public Set<String> getPortletCSRFWhitelistActions();

	public Set<String> getPortletInvocationWhitelist();

	public Set<String> getPortletInvocationWhitelistActions();

	public boolean isOriginCSRFWhitelisted(long companyId, String origin);

	public boolean isPortletCSRFWhitelisted(
		HttpServletRequest request, Portlet portlet);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #isPortletCSRFWhitelisted(HttpServletRequest, Portlet)}
	 */
	@Deprecated
	public boolean isPortletCSRFWhitelisted(
		long companyId, String portletId, String strutsAction);

	public boolean isPortletInvocationWhitelisted(
		long companyId, String portletId, String strutsAction);

	public boolean isPortletURLCSRFWhitelisted(
		LiferayPortletURL liferayPortletURL);

	public boolean isValidSharedSecret(String sharedSecret);

	public Set<String> resetOriginCSRFWhitelist();

	public Set<String> resetPortletCSRFWhitelist();

	public Set<String> resetPortletInvocationWhitelist();

	public Set<String> resetPortletInvocationWhitelistActions();

}