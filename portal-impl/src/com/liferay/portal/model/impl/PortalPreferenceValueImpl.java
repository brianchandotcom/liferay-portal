/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

/**
 * @author Preston Crary
 */
public class PortalPreferenceValueImpl extends PortalPreferenceValueBaseImpl {

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