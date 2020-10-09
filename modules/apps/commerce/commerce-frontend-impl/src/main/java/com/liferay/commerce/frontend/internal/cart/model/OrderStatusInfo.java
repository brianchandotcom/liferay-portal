/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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