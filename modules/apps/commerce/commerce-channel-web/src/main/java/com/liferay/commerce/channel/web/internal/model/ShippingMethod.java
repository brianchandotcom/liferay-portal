/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.channel.web.internal.model;

import com.liferay.commerce.frontend.model.LabelField;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class ShippingMethod {

	public ShippingMethod(
		String description, String key, String name, String shippingEngine,
		LabelField status) {

		_description = description;
		_key = key;
		_name = name;
		_shippingEngine = shippingEngine;
		_status = status;
	}

	public String getDescription() {
		return _description;
	}

	public String getKey() {
		return _key;
	}

	public String getName() {
		return _name;
	}

	public String getShippingEngine() {
		return _shippingEngine;
	}

	public LabelField getStatus() {
		return _status;
	}

	private final String _description;
	private final String _key;
	private final String _name;
	private final String _shippingEngine;
	private final LabelField _status;

}