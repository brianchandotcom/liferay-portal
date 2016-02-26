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