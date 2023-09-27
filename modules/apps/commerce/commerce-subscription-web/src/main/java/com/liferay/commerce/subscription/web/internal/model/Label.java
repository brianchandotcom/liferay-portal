/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.subscription.web.internal.model;

/**
 * @author Luca Pellizzon
 */
public class Label {

	public static final String DANGER = "danger";

	public static final String INFO = "info";

	public static final String SECONDARY = "secondary";

	public static final String SUCCESS = "success";

	public static final String WARNING = "warning";

	public Label(String label, String displayStyle) {
		_label = label;
		_displayStyle = displayStyle;
	}

	public String getDisplayStyle() {
		return _displayStyle;
	}

	public String getLabel() {
		return _label;
	}

	private final String _displayStyle;
	private final String _label;

}