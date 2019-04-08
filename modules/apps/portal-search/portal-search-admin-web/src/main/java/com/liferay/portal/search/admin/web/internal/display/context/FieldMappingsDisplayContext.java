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

package com.liferay.portal.search.admin.web.internal.display.context;

import java.util.List;

/**
 * @author Adam Brandizzi
 */
public class FieldMappingsDisplayContext {

	public List<FieldMappingIndexDisplayContext>
		getFieldMappingIndexDisplayContexts() {

		return _fieldMappingIndexDisplayContexts;
	}

	public String getFieldMappings() {
		return _fieldMappings;
	}

	public List<String> getIndexNames() {
		return _indexNames;
	}

	public String getSelectedIndexName() {
		return _selectedIndexName;
	}

	public void setFieldMappingIndexDisplayContexts(
		List<FieldMappingIndexDisplayContext>
			fieldMappingIndexDisplayContexts) {

		_fieldMappingIndexDisplayContexts = fieldMappingIndexDisplayContexts;
	}

	public void setFieldMappings(String fieldMappings) {
		_fieldMappings = fieldMappings;
	}

	public void setIndexNames(List<String> indexNames) {
		_indexNames = indexNames;
	}

	public void setSelectedIndexName(String selectedIndexName) {
		_selectedIndexName = selectedIndexName;
	}

	private List<FieldMappingIndexDisplayContext>
		_fieldMappingIndexDisplayContexts;
	private String _fieldMappings;
	private List<String> _indexNames;
	private String _selectedIndexName;

}