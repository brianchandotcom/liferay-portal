/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.web.internal.model;

import com.liferay.commerce.frontend.model.ImageField;

/**
 * @author Riccardo Alberti
 */
public class PricingClassCPDefinitionRel {

	public PricingClassCPDefinitionRel(
		long pricingClassCPDefinitionRelId, long cpDefinitionId, String name,
		String sku, ImageField image) {

		_pricingClassCPDefinitionRelId = pricingClassCPDefinitionRelId;
		_cpDefinitionId = cpDefinitionId;
		_name = name;
		_sku = sku;
		_image = image;
	}

	public long getCPDefinitionId() {
		return _cpDefinitionId;
	}

	public ImageField getImage() {
		return _image;
	}

	public String getName() {
		return _name;
	}

	public long getPricingClassCPDefinitionRelId() {
		return _pricingClassCPDefinitionRelId;
	}

	public String getSku() {
		return _sku;
	}

	private final long _cpDefinitionId;
	private final ImageField _image;
	private final String _name;
	private final long _pricingClassCPDefinitionRelId;
	private final String _sku;

}