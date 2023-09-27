/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.admin.web.internal.display.context.builder;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.admin.web.internal.display.context.FieldMappingIndexDisplayContext;
import com.liferay.portal.search.admin.web.internal.display.context.FieldMappingsDisplayContext;
import com.liferay.portal.search.index.IndexInformation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Adam Brandizzi
 */
public class FieldMappingsDisplayContextBuilder {

	public FieldMappingsDisplayContext build() {
		FieldMappingsDisplayContext fieldMappingsDisplayContext =
			new FieldMappingsDisplayContext();

		List<String> indexNames = Arrays.asList(
			_indexInformation.getIndexNames());

		fieldMappingsDisplayContext.setIndexNames(indexNames);

		String selectedIndexName = getSelectedIndexName(indexNames);

		List<FieldMappingIndexDisplayContext> fieldMappingIndexDisplayContexts =
			getFieldMappingIndexDisplayContexts(indexNames, selectedIndexName);

		String fieldMappings = _indexInformation.getFieldMappings(
			selectedIndexName);

		fieldMappingsDisplayContext.setData(
			getData(
				fieldMappingIndexDisplayContexts, fieldMappings,
				selectedIndexName));

		fieldMappingsDisplayContext.setFieldMappingIndexDisplayContexts(
			fieldMappingIndexDisplayContexts);
		fieldMappingsDisplayContext.setFieldMappings(fieldMappings);
		fieldMappingsDisplayContext.setSelectedIndexName(selectedIndexName);

		return fieldMappingsDisplayContext;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setCurrentURL(String currentURL) {
		_currentURL = currentURL;
	}

	public void setIndexInformation(IndexInformation indexInformation) {
		_indexInformation = indexInformation;
	}

	public void setNamespace(String namespace) {
		_namespace = namespace;
	}

	public void setSelectedIndexName(String selectedIndexName) {
		_selectedIndexName = selectedIndexName;
	}

	protected Map<String, Object> getData(
		List<FieldMappingIndexDisplayContext> fieldMappingIndexDisplayContexts,
		String fieldMappings, String selectedIndexName) {

		Map<String, Object> data = new HashMap<>();

		data.put(
			"fieldMappingIndexDisplayContexts",
			fieldMappingIndexDisplayContexts);
		data.put("fieldMappings", fieldMappings);
		data.put("selectedIndexName", selectedIndexName);

		return data;
	}

	protected List<FieldMappingIndexDisplayContext>
		getFieldMappingIndexDisplayContexts(
			List<String> indexNames, String selectedIndexName) {

		return TransformUtil.transform(
			indexNames,
			indexName -> _getFieldMappingIndexDisplayContext(
				indexName, selectedIndexName.equals(indexName)));
	}

	protected String getSelectedIndexName(List<String> indexNames) {
		String selectedIndexName = _selectedIndexName;

		if (Validator.isBlank(selectedIndexName)) {
			selectedIndexName = _indexInformation.getCompanyIndexName(
				_companyId);
		}

		if (!indexNames.contains(selectedIndexName)) {
			selectedIndexName = indexNames.get(0);
		}

		return selectedIndexName;
	}

	private FieldMappingIndexDisplayContext _getFieldMappingIndexDisplayContext(
		String indexName, boolean selected) {

		FieldMappingIndexDisplayContext fieldMappingIndexDisplayContext =
			new FieldMappingIndexDisplayContext();

		fieldMappingIndexDisplayContext.setName(indexName);

		if (selected) {
			fieldMappingIndexDisplayContext.setCssClass("active");
		}

		fieldMappingIndexDisplayContext.setUrl(
			HttpComponentsUtil.setParameter(
				_currentURL, _namespace + "selectedIndexName", indexName));

		return fieldMappingIndexDisplayContext;
	}

	private long _companyId;
	private String _currentURL;
	private IndexInformation _indexInformation;
	private String _namespace = StringPool.BLANK;
	private String _selectedIndexName;

}