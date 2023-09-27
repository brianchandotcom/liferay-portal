/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.frontend.model;

/**
 * @author Alec Sloan
 */
public class Icon {

	public Icon(String icon) {
		_icon = icon;
	}

	public String getIcon() {
		return _icon;
	}

	private final String _icon;

}