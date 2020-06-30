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