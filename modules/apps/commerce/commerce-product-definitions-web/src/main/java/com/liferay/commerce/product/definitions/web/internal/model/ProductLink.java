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

/**
 * @author Alessio Antonio Rendina
 */
public class ProductLink {

	public ProductLink(
		long cpDefinitionLinkId, ImageField image, String name, String type,
		double order, String createDateString) {

		_cpDefinitionLinkId = cpDefinitionLinkId;
		_image = image;
		_name = name;
		_type = type;
		_order = order;
		_createDateString = createDateString;
	}

	public long getCPDefinitionLinkId() {
		return _cpDefinitionLinkId;
	}

	public String getCreateDateString() {
		return _createDateString;
	}

	public ImageField getImage() {
		return _image;
	}

	public String getName() {
		return _name;
	}

	public double getOrder() {
		return _order;
	}

	public String getType() {
		return _type;
	}

	private final long _cpDefinitionLinkId;
	private final String _createDateString;
	private final ImageField _image;
	private final String _name;
	private final double _order;
	private final String _type;

}