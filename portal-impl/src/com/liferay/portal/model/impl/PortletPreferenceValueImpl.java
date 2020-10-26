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

package com.liferay.portal.model.impl;

/**
 * @author Preston Crary
 */
public class PortletPreferenceValueImpl extends PortletPreferenceValueBaseImpl {

	public static final int SMALL_VALUE_MAX_LENGTH = 75;

	@Override
	public String getValue() {
		String value = getSmallValue();

		if (value.isEmpty()) {
			value = getLargeValue();
		}

		return value;
	}

	@Override
	public void setValue(String value) {
		if (value == null) {
			setSmallValue(null);

			setLargeValue(null);
		}
		else if (value.length() <= SMALL_VALUE_MAX_LENGTH) {
			setSmallValue(value);

			setLargeValue(null);
		}
		else {
			setSmallValue(null);

			setLargeValue(value);
		}
	}

}