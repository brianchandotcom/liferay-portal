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

package com.liferay.data.engine.model;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DEDataLayoutPage implements Serializable {

	public List<DEDataLayoutRow> getDEDataLayoutRows() {
		return _deDataLayoutRows;
	}

	public Map<Locale, String> getDescription() {
		return _description;
	}

	public Map<Locale, String> getTitle() {
		return _title;
	}

	public void setDEDataLayoutRows(List<DEDataLayoutRow> deDataLayoutRows) {
		_deDataLayoutRows = deDataLayoutRows;
	}

	public void setDescription(Map<Locale, String> description) {
		_description = description;
	}

	public void setTitle(Map<Locale, String> title) {
		_title = title;
	}

	private List<DEDataLayoutRow> _deDataLayoutRows;
	private Map<Locale, String> _description;
	private Map<Locale, String> _title;

}