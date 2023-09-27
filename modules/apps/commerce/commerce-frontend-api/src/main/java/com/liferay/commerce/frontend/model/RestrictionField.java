/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.frontend.model;

/**
 * @author Alessio Antonio Rendina
 */
public class RestrictionField {

	public RestrictionField(String label, String name, boolean value) {
		_label = label;
		_name = name;
		_value = value;
	}

	public String getLabel() {
		return _label;
	}

	public String getName() {
		return _name;
	}

	public boolean getValue() {
		return _value;
	}

	private final String _label;
	private final String _name;
	private final boolean _value;

}