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

package com.liferay.object.rest.odata.entity.v1_0;

import com.liferay.object.constants.ObjectFieldConstants;

/**
 * @author Luis Miguel Barcos
 */
public enum UnsupportedBusinessTypes {

	BUSINESS_TYPE_AGGREGATION(ObjectFieldConstants.BUSINESS_TYPE_AGGREGATION),
	BUSINESS_TYPE_ATTACHMENT(ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT),
	BUSINESS_TYPE_FORMULA(ObjectFieldConstants.BUSINESS_TYPE_FORMULA),
	BUSINESS_TYPE_RICH_TEXT(ObjectFieldConstants.BUSINESS_TYPE_RICH_TEXT);

	public String getValue() {
		return _value;
	}

	private UnsupportedBusinessTypes(String value) {
		_value = value;
	}

	private final String _value;

}