/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.frontend.model;

/**
 * @author Alessio Antonio Rendina
 * @author Alec Sloan
 */
public class Sku {

	public Sku(String label, String href) {
		_label = label;
		_href = href;
	}

	public String getHref() {
		return _href;
	}

	public String getLabel() {
		return _label;
	}

	private final String _href;
	private final String _label;

}