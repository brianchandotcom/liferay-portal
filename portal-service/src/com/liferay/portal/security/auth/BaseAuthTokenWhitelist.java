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

import java.util.Collections;
import java.util.Set;

/**
 * @author Tomas Polesovsky
 */
public abstract class BaseAuthTokenWhitelist implements AuthTokenWhitelist {

	@Override
	public Set<String> getOriginCSRFWhitelist() {
		return Collections.emptySet();
	}

	@Override
	public Set<String> getPortletCSRFWhitelist() {
		return Collections.emptySet();
	}

	@Override
	public Set<String> getPortletCSRFWhitelistActions() {
		return Collections.emptySet();
	}

	@Override
	public Set<String> getPortletInvocationWhitelist() {
		return Collections.emptySet();
	}

	@Override
	public Set<String> getPortletInvocationWhitelistActions() {
		return Collections.emptySet();
	}

	@Override
	public boolean isOriginCSRFWhitelisted(long companyId, String origin) {
		return false;
	}

	@Override
	public boolean isPortletCSRFWhitelisted(
		long companyId, String portletId, String strutsAction) {

		return false;
	}

	@Override
	public boolean isPortletInvocationWhitelisted(long companyId, String
		portletId, String strutsAction) {

		return false;
	}

	@Override
	public boolean isValidSharedSecret(String sharedSecret) {
		return false;
	}

	@Override
	public Set<String> resetOriginCSRFWhitelist() {
		return Collections.emptySet();
	}

	@Override
	public Set<String> resetPortletCSRFWhitelist() {
		return Collections.emptySet();
	}

	@Override
	public Set<String> resetPortletInvocationWhitelist() {
		return Collections.emptySet();
	}

	@Override
	public Set<String> resetPortletInvocationWhitelistActions() {
		return Collections.emptySet();
	}

}