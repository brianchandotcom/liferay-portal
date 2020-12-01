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

package com.liferay.dynamic.data.mapping.model.impl;

/**
 * @author Preson Crary
 */
public class DDMFieldAttributeImpl extends DDMFieldAttributeBaseImpl {

	public static final int SMALL_ATTRIBUTE_VALUE_MAX_LENGTH = 255;

	@Override
	public String getAttributeValue() {
		String value = getLargeAttributeValue();

		if (value.isEmpty()) {
			value = getSmallAttributeValue();
		}

		return value;
	}

	@Override
	public void setAttributeValue(String value) {
		String largeAttributeValue = null;
		String smallAttributeValue = null;

		if (value != null) {
			if (value.length() > SMALL_ATTRIBUTE_VALUE_MAX_LENGTH) {
				largeAttributeValue = value;
			}
			else {
				smallAttributeValue = value;
			}
		}

		setLargeAttributeValue(largeAttributeValue);
		setSmallAttributeValue(smallAttributeValue);
	}

}