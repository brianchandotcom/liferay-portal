/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.blueprint.parameter;

import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Petteri Karttunen
 */
public class BooleanSXPParameter extends BaseSXPParameter {

	public BooleanSXPParameter(
		String name, boolean templateVariable, Boolean value) {

		super(name, templateVariable);

		_value = value;
	}

	@Override
	public boolean evaluateEquals(Object object) {
		if (_value.booleanValue() == GetterUtil.getBoolean(object)) {
			return true;
		}

		return false;
	}

	@Override
	public Boolean getValue() {
		return _value;
	}

	private final Boolean _value;

}