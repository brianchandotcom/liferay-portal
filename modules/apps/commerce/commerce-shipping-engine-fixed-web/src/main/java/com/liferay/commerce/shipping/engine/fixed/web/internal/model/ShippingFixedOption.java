/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipping.engine.fixed.web.internal.model;

/**
 * @author Marco Leo
 */
public class ShippingFixedOption {

	public ShippingFixedOption(
		String description, String name, double priority,
		long shippingFixedOptionId) {

		_description = description;
		_name = name;
		_priority = priority;
		_shippingFixedOptionId = shippingFixedOptionId;
	}

	public String getDescription() {
		return _description;
	}

	public String getName() {
		return _name;
	}

	public double getPriority() {
		return _priority;
	}

	public long getShippingFixedOptionId() {
		return _shippingFixedOptionId;
	}

	private final String _description;
	private final String _name;
	private final double _priority;
	private final long _shippingFixedOptionId;

}