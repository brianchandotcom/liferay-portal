/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.field.frontend.data.set.filter;

import com.liferay.frontend.data.set.constants.FDSEntityFieldTypes;
import com.liferay.frontend.data.set.filter.BaseSelectionFDSFilter;

import java.util.Map;

/**
 * @author Feliphe Marinho
 */
public class ListTypeEntrySelectionFDSFilter extends BaseSelectionFDSFilter {

	public ListTypeEntrySelectionFDSFilter(
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

	@Override
	public boolean isAutocompleteEnabled() {
		return true;
	}

	private final boolean _collection;
	private final String _id;
	private final String _label;
	private final long _listTypeDefinitionId;
	private final Map<String, Object> _preloadedData;

}