/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.auth;

import com.liferay.portal.kernel.util.ArrayUtil;

/**
 * @author Jorge Ferrer
 */
public class FullNameField {

	public String getName() {
		return _name;
	}

	public String[] getValues() {
		return _values;
	}

	public boolean isFreeText() {
		return ArrayUtil.isEmpty(_values);
	}

	public boolean isRequired() {
		return _required;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setRequired(boolean required) {
		_required = required;
	}

	public void setValues(String[] values) {
		_values = values;
	}

	private String _name;
	private boolean _required;
	private String[] _values;

}