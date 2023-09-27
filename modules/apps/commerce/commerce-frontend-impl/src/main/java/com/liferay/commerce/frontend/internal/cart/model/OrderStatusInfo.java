/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.frontend.internal.cart.model;

/**
 * @author Luca Pellizzon
 * @author Fabio Diego Mastrorilli
 */
public class OrderStatusInfo {

	public OrderStatusInfo(int code, String label, String localizedLabel) {
		_code = code;
		_label = label;

		_label_i18n = localizedLabel;
	}

	public int getCode() {
		return _code;
	}

	public String getLabel() {
		return _label;
	}

	public String getLabel_i18n() {
		return _label_i18n;
	}

	private final int _code;
	private final String _label;
	private final String _label_i18n;

}