/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.frontend.model;

/**
 * @author Alessio Antonio Rendina
 */
public class LabelField {

	public LabelField(String label) {
		_label = label;
	}

	public LabelField(String displayStyle, String label) {
		_displayStyle = displayStyle;
		_label = label;
	}

	public String getDisplayStyle() {
		return _displayStyle;
	}

	public String getLabel() {
		return _label;
	}

	private String _displayStyle;
	private final String _label;

}