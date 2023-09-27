/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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