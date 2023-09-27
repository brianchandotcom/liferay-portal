/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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