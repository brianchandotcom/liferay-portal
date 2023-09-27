/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ratings.kernel.definition;

import com.liferay.ratings.kernel.RatingsType;

/**
 * @author Roberto Díaz
 */
public class PortletRatingsDefinitionValues {

	public PortletRatingsDefinitionValues(
		String[] classNames, RatingsType defaultRatingsType, String portletId) {

		_classNames = classNames;
		_defaultRatingsType = defaultRatingsType;
		_portletId = portletId;
	}

	public String[] getClassNames() {
		return _classNames;
	}

	public RatingsType getDefaultRatingsType() {
		return _defaultRatingsType;
	}

	public String getPortletId() {
		return _portletId;
	}

	private final String[] _classNames;
	private final RatingsType _defaultRatingsType;
	private final String _portletId;

}