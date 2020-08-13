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

package com.liferay.analytics.settings.web.internal.model;

/**
 * @author Rachael Koestartyo
 */
public class Field {

	public Field(String category, String dataType, String name) {
		_category = category;
		_dataType = dataType;
		_name = name;
	}

	public String getCategory() {
		return _category;
	}

	public String getDataType() {
		return _dataType;
	}

	public String getName() {
		return _name;
	}

	public void setCategory(String category) {
		_category = category;
	}

	public void setDataType(String dataType) {
		_dataType = dataType;
	}

	public void setName(String name) {
		_name = name;
	}

	private String _category;
	private String _dataType;
	private String _name;

}