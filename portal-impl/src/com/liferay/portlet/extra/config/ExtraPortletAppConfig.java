/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.extra.config;

import java.util.Map;

/**
 * @author Leon Chi
 */
public class ExtraPortletAppConfig {

	public ExtraPortletAppConfig(Map<String, String> localeEncodings) {
		_localeEncodings = localeEncodings;
	}

	public String getEncoding(String locale) {
		return _localeEncodings.get(locale);
	}

	private final Map<String, String> _localeEncodings;

}