/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.definitions.web.internal.model;

import com.liferay.commerce.frontend.model.ImageField;
import com.liferay.commerce.frontend.model.LabelField;

/**
 * @author Alessio Antonio Rendina
 */
public class ProductLink {

	public ProductLink(
		long cpDefinitionLinkId, String createDateString, ImageField image,
		String name, double order, String type, LabelField status) {

		_cpDefinitionLinkId = cpDefinitionLinkId;
		_createDateString = createDateString;
		_image = image;
		_name = name;
		_order = order;
		_type = type;
		_status = status;
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

	public LabelField getStatus() {
		return _status;
	}

	public String getType() {
		return _type;
	}

	private final long _cpDefinitionLinkId;
	private final String _createDateString;
	private final ImageField _image;
	private final String _name;
	private final double _order;
	private final LabelField _status;
	private final String _type;

}