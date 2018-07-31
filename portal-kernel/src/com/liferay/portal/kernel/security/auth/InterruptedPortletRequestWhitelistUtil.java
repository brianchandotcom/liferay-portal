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

package com.liferay.portal.kernel.security.auth;

import java.util.Set;

/**
 * @author Tomas Polesovsky
 * @author Raymond Augé
 */
public class InterruptedPortletRequestWhitelistUtil {

	public static PortletRequestWhitelist
		getInterruptedPortletRequestWhitelist() {

		return _interruptedPortletRequestWhitelist;
	}

	public static Set<String> getPortletInvocationWhitelist() {
		return getInterruptedPortletRequestWhitelist().
			getPortletInvocationWhitelist();
	}

	public static Set<String> getPortletInvocationWhitelistActions() {
		return getInterruptedPortletRequestWhitelist().
			getPortletInvocationWhitelistActions();
	}

	public static boolean isPortletInvocationWhitelisted(
		long companyId, String portletId, String strutsAction) {

		return getInterruptedPortletRequestWhitelist().
			isPortletInvocationWhitelisted(companyId, portletId, strutsAction);
	}

	public static Set<String> resetPortletInvocationWhitelist() {
		return getInterruptedPortletRequestWhitelist().
			resetPortletInvocationWhitelist();
	}

	public static Set<String> resetPortletInvocationWhitelistActions() {
		return getInterruptedPortletRequestWhitelist().
			resetPortletInvocationWhitelistActions();
	}

	public void setInterruptedPortletRequestWhitelist(
		PortletRequestWhitelist whitelist) {

		_interruptedPortletRequestWhitelist = whitelist;
	}

	private static PortletRequestWhitelist _interruptedPortletRequestWhitelist;

}