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

package com.liferay.portal.security.auth;

import java.util.Set;

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

	public boolean isCSRFOriginWhitelisted(long companyId, String origin);

	public boolean isPortletCSRFWhitelisted(
		long companyId, String portletId, String strutsAction);

	public boolean isPortletInvocationWhitelisted(
		long companyId, String portletId, String strutsAction);

	public boolean isValidCSRFSharedSecret(String sharedSecret);

	public Set<String> resetOriginCSRFWhitelist();

	public Set<String> resetPortletCSRFWhitelist();

	public Set<String> resetPortletCSRFWhitelistActions();

	public Set<String> resetPortletInvocationWhitelist();

	public Set<String> resetPortletInvocationWhitelistActions();

}