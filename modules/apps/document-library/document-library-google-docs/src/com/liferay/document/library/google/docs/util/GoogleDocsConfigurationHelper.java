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

package com.liferay.document.library.google.docs.util;

import com.liferay.portal.kernel.util.PrefsPropsUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Ivan Zaera
 */
public class GoogleDocsConfigurationHelper {

	public GoogleDocsConfigurationHelper(long companyId) {
		_portletPreferences = PrefsPropsUtil.getPreferences(companyId);
	}

	public String getGoogleAppsAPIKey() {
		return _portletPreferences.getValue("googleAppsAPIKey", "");
	}

	public String getGoogleClientId() {
		return _portletPreferences.getValue("googleClientId", "");
	}

	private final PortletPreferences _portletPreferences;

}