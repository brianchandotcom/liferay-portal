/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.security.auth.BasePortletRequestWhitelist;
import com.liferay.portal.util.PropsValues;

/**
 * @author Péter Borkuti
 */
public class InterruptedPortletRequestWhitelistImpl
	extends BasePortletRequestWhitelist {

	@Override
	public String[] getWhitelistActionsPropsValues() {
		return PropsValues.PORTLET_INTERRUPTED_REQUEST_WHITELIST_ACTIONS;
	}

	@Override
	public String[] getWhitelistPropsValues() {
		return PropsValues.PORTLET_INTERRUPTED_REQUEST_WHITELIST;
	}

}