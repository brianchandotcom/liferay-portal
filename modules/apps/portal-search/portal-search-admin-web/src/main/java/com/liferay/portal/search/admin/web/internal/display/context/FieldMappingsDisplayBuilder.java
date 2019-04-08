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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.index.IndexInformation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Adam Brandizzi
 */
public class FieldMappingsDisplayBuilder {

	public FieldMappingsDisplayBuilder(Http http) {
		_http = http;
	}

	public FieldMappingsDisplayContext build() {
		FieldMappingsDisplayContext fieldMappingsDisplayContext =
			new FieldMappingsDisplayContext();

		List<String> indexNames = Arrays.asList(
			_indexInformation.getIndexNames());

		fieldMappingsDisplayContext.setIndexNames(indexNames);

		String selectedIndexName = getSelectedIndexName(indexNames);

		fieldMappingsDisplayContext.setFieldMappingIndexDisplayContexts(
			getFieldMappingIndexDisplayContexts(indexNames, selectedIndexName));
		fieldMappingsDisplayContext.setFieldMappings(
			_indexInformation.getFieldMappings(selectedIndexName));
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

	protected FieldMappingIndexDisplayContext
		getFieldMappingIndexDisplayContext(String indexName, boolean selected) {

		FieldMappingIndexDisplayContext fieldMappingIndexDisplayContext =
			new FieldMappingIndexDisplayContext();

		fieldMappingIndexDisplayContext.setName(indexName);

		if (selected) {
			fieldMappingIndexDisplayContext.setCssClass("active");
		}

		String url = _http.setParameter(
			_currentURL, _namespace + "selectedIndexName", indexName);

		fieldMappingIndexDisplayContext.setUrl(url);

		return fieldMappingIndexDisplayContext;
	}

	protected List<FieldMappingIndexDisplayContext>
		getFieldMappingIndexDisplayContexts(
			List<String> indexNames, String selectedIndexName) {

		Stream<String> stream = indexNames.stream();

		return stream.map(
			indexName -> getFieldMappingIndexDisplayContext(
				indexName, selectedIndexName.equals(indexName))
		).collect(
			Collectors.toList()
		);
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

	private long _companyId;
	private String _currentURL;
	private final Http _http;
	private IndexInformation _indexInformation;
	private String _namespace = StringPool.BLANK;
	private String _selectedIndexName;

}