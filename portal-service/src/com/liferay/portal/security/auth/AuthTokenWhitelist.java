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

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public Set<String> getOriginCSRFWhitelist();

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public Set<String> getPortletCSRFWhitelist();

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public Set<String> getPortletCSRFWhitelistActions();

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public Set<String> getPortletInvocationWhitelist();

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
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
		HttpServletRequest request, Portlet portlet);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #isPortletInvocationWhitelisted(HttpServletRequest, Portlet)}
	 */
	@Deprecated
	public boolean isPortletInvocationWhitelisted(
		long companyId, String portletId, String strutsAction);

	public boolean isPortletURLCSRFWhitelisted(
		LiferayPortletURL liferayPortletURL);

	public boolean isPortletURLPortletInvocationWhitelisted(
		LiferayPortletURL liferayPortletURL);

	public boolean isValidSharedSecret(String sharedSecret);

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public Set<String> resetOriginCSRFWhitelist();

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public Set<String> resetPortletCSRFWhitelist();

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public Set<String> resetPortletInvocationWhitelist();

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public Set<String> resetPortletInvocationWhitelistActions();

}