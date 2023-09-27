/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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