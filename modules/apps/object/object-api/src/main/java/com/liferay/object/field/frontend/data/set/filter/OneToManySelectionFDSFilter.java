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
import com.liferay.frontend.data.set.filter.BaseSelectionFDSFilter;

import java.util.Map;

/**
 * @author Paulo ALbuquerque
 */
public class OneToManySelectionFDSFilter extends BaseSelectionFDSFilter {

	public OneToManySelectionFDSFilter(
		Map<String, Object> preloadedData, String restContextPath,
		String titleObjectFieldLabel, String relationshipObjectFieldName,
		String titleObjectFieldName) {

		_preloadedData = preloadedData;
		_restContextPath = restContextPath;
		_titleObjectFieldLabel = titleObjectFieldLabel;
		_relationshipObjectFieldName = relationshipObjectFieldName;
		_titleObjectFieldName = titleObjectFieldName;
	}

	@Override
	public String getAPIURL() {
		return _restContextPath;
	}

	@Override
	public String getEntityFieldType() {
		return FDSEntityFieldTypes.STRING;
	}

	@Override
	public String getId() {
		return _relationshipObjectFieldName;
	}

	@Override
	public String getItemKey() {
		return "id";
	}

	@Override
	public String getItemLabel() {
		return _titleObjectFieldName;
	}

	@Override
	public String getLabel() {
		return _titleObjectFieldLabel;
	}

	@Override
	public Map<String, Object> getPreloadedData() {
		return _preloadedData;
	}

	@Override
	public boolean isAutocompleteEnabled() {
		return true;
	}

	private final Map<String, Object> _preloadedData;
	private final String _relationshipObjectFieldName;
	private final String _restContextPath;
	private final String _titleObjectFieldLabel;
	private final String _titleObjectFieldName;

}