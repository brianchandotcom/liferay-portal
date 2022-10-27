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

package com.liferay.object.field.frontend.data.set.filter;

import com.liferay.frontend.data.set.constants.FDSEntityFieldTypes;
import com.liferay.frontend.data.set.filter.BaseAutocompleteFDSFilter;

import java.util.Map;

/**
 * @author Feliphe Marinho
 */
public class ListTypeEntryAutocompleteFDSFilter
	extends BaseAutocompleteFDSFilter {

	public ListTypeEntryAutocompleteFDSFilter(
		boolean collection, String id, String label, long listTypeDefinitionId,
		Map<String, Object> preloadedData) {

		_collection = collection;
		_id = id;
		_label = label;
		_listTypeDefinitionId = listTypeDefinitionId;
		_preloadedData = preloadedData;
	}

	@Override
	public String getAPIURL() {
		return "/o/headless-admin-list-type/v1.0/list-type-definitions/" +
			_listTypeDefinitionId + "/list-type-entries";
	}

	@Override
	public String getEntityFieldType() {
		if (_collection) {
			return FDSEntityFieldTypes.COLLECTION;
		}

		return FDSEntityFieldTypes.STRING;
	}

	@Override
	public String getId() {
		return _id;
	}

	@Override
	public String getItemKey() {
		return "key";
	}

	@Override
	public String getItemLabel() {
		return "name";
	}

	@Override
	public String getLabel() {
		return _label;
	}

	@Override
	public Map<String, Object> getPreloadedData() {
		return _preloadedData;
	}

	private final boolean _collection;
	private final String _id;
	private final String _label;
	private final long _listTypeDefinitionId;
	private final Map<String, Object> _preloadedData;

}