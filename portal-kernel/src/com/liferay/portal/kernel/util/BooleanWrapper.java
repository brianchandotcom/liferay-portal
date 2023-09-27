/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

/**
 * @author Brian Wing Shun Chan
 */
public class BooleanWrapper
	extends PrimitiveWrapper implements Comparable<BooleanWrapper> {

	public static final Class<?> TYPE = Boolean.TYPE;

	public BooleanWrapper() {
		this(false);
	}

	public BooleanWrapper(boolean value) {
		_value = value;
	}

	@Override
	public int compareTo(BooleanWrapper booleanWrapper) {
		if (booleanWrapper == null) {
			return 1;
		}

		if (getValue() && !booleanWrapper.getValue()) {
			return 1;
		}
		else if (!getValue() && booleanWrapper.getValue()) {
			return -1;
		}

		return 0;
	}

	public boolean getValue() {
		return _value;
	}

	public void setValue(boolean value) {
		_value = value;
	}

	private boolean _value;

}