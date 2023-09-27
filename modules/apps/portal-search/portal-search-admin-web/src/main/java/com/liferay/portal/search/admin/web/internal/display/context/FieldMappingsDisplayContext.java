/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.admin.web.internal.display.context;

import java.util.List;
import java.util.Map;

/**
 * @author Adam Brandizzi
 */
public class FieldMappingsDisplayContext {

	public Map<String, Object> getData() {
		return _data;
	}

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

	public void setData(Map<String, Object> data) {
		_data = data;
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

	private Map<String, Object> _data;
	private List<FieldMappingIndexDisplayContext>
		_fieldMappingIndexDisplayContexts;
	private String _fieldMappings;
	private List<String> _indexNames;
	private String _selectedIndexName;

}