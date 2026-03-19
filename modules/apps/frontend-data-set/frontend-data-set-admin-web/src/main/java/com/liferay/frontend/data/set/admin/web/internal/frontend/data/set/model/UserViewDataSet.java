/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.admin.web.internal.frontend.data.set.model;

/**
 * @author Miguel Arroyo
 */
public class UserViewDataSet {

	public UserViewDataSet(String label, String value) {
		_label = label;
		_value = value;
	}

	public String getLabel() {
		return _label;
	}

	public String getValue() {
		return _value;
	}

	private final String _label;
	private final String _value;

}
