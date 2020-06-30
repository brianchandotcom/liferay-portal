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

package com.liferay.commerce.product.definitions.web.internal.model;

import com.liferay.commerce.frontend.model.ImageField;
import com.liferay.commerce.frontend.model.LabelField;

/**
 * @author Alessio Antonio Rendina
 */
public class Product {

	public Product(
		String catalog, long catalogId, long cpDefinitionId, ImageField image,
		String modifiedDate, String name, String sku, LabelField status,
		String type) {

		_catalog = catalog;
		_catalogId = catalogId;
		_cpDefinitionId = cpDefinitionId;
		_image = image;
		_modifiedDate = modifiedDate;
		_name = name;
		_sku = sku;
		_status = status;
		_type = type;
	}

	public String getCatalog() {
		return _catalog;
	}

	public long getCatalogId() {
		return _catalogId;
	}

	public long getCPDefinitionId() {
		return _cpDefinitionId;
	}

	public ImageField getImage() {
		return _image;
	}

	public String getModifiedDate() {
		return _modifiedDate;
	}

	public String getName() {
		return _name;
	}

	public String getSku() {
		return _sku;
	}

	public LabelField getStatus() {
		return _status;
	}

	public String getType() {
		return _type;
	}

	private final String _catalog;
	private final long _catalogId;
	private final long _cpDefinitionId;
	private final ImageField _image;
	private final String _modifiedDate;
	private final String _name;
	private final String _sku;
	private final LabelField _status;
	private final String _type;

}