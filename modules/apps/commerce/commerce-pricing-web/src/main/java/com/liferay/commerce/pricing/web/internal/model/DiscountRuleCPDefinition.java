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

package com.liferay.commerce.pricing.web.internal.model;

import com.liferay.commerce.frontend.model.ImageField;

/**
 * @author Riccardo Alberti
 */
public class DiscountRuleCPDefinition {

	public DiscountRuleCPDefinition(
		long discountRuleId, long cpDefinitionId, String name, String sku,
		ImageField image) {

		_discountRuleId = discountRuleId;
		_cpDefinitionId = cpDefinitionId;
		_name = name;
		_sku = sku;
		_image = image;
	}

	public long getCPDefinitionId() {
		return _cpDefinitionId;
	}

	public long getDiscountRuleId() {
		return _discountRuleId;
	}

	public ImageField getImage() {
		return _image;
	}

	public String getName() {
		return _name;
	}

	public String getSku() {
		return _sku;
	}

	private final long _cpDefinitionId;
	private final long _discountRuleId;
	private final ImageField _image;
	private final String _name;
	private final String _sku;

}