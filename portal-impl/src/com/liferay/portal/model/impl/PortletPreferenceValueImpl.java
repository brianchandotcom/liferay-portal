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

	public static final int SMALL_VALUE_MAX_LENGTH = 255;

	@Override
	public String getValue() {
		String value = getLargeValue();

		if (value.isEmpty()) {
			value = getSmallValue();
		}

		return value;
	}

	@Override
	public void setValue(String value) {
		String largeValue = null;
		String smallValue = null;

		if (value != null) {
			if (value.length() > SMALL_VALUE_MAX_LENGTH) {
				largeValue = value;
			}
			else {
				smallValue = value;
			}
		}

		setLargeValue(largeValue);
		setSmallValue(smallValue);
	}

}