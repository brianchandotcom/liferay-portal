/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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