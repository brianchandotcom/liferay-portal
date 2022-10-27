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

package com.liferay.object.field.filter.parser;

import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.frontend.data.set.filter.ListTypeEntryAutocompleteFDSFilter;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectViewFilterColumn;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Feliphe Marinho
 */
public class PicklistObjectFieldFilterStrategy
	extends BaseObjectFieldFilterStrategy {

	public PicklistObjectFieldFilterStrategy(
		Locale locale, long listTypeDefinitionId,
		ListTypeDefinitionLocalService listTypeDefinitionLocalService,
		ListTypeEntryLocalService listTypeEntryLocalService,
		ObjectField objectField,
		ObjectViewFilterColumn objectViewFilterColumn) {

		super(locale, objectViewFilterColumn);

		_listTypeDefinitionId = listTypeDefinitionId;
		_listTypeDefinitionLocalService = listTypeDefinitionLocalService;
		_listTypeEntryLocalService = listTypeEntryLocalService;
		_objectField = objectField;
	}

	@Override
	public FDSFilter getFDSFilter() throws PortalException {
		ListTypeDefinition listTypeDefinition =
			_listTypeDefinitionLocalService.getListTypeDefinition(
				_objectField.getListTypeDefinitionId());

		return new ListTypeEntryAutocompleteFDSFilter(
			StringUtil.equals(
				_objectField.getBusinessType(),
				ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST),
			_objectField.getName(), listTypeDefinition.getName(locale),
			_objectField.getListTypeDefinitionId(), parse());
	}

	@Override
	public List<Map<String, String>> getItemsValues() throws JSONException {
		List<Map<String, String>> itemsValues = new ArrayList<>();

		JSONArray jsonArray = getJSONArray();

		for (int i = 0; i < jsonArray.length(); i++) {
			ListTypeEntry listTypeEntry =
				_listTypeEntryLocalService.fetchListTypeEntry(
					_listTypeDefinitionId, jsonArray.getString(i));

			itemsValues.add(
				HashMapBuilder.put(
					"label", listTypeEntry.getName(locale)
				).put(
					"value", jsonArray.getString(i)
				).build());
		}

		return itemsValues;
	}

	@Override
	public String toValueSummary() throws PortalException {
		return StringUtil.merge(
			ListUtil.toList(
				getItemsValues(), itemValue -> itemValue.get("label")),
			StringPool.COMMA_AND_SPACE);
	}

	private final long _listTypeDefinitionId;
	private final ListTypeDefinitionLocalService
		_listTypeDefinitionLocalService;
	private final ListTypeEntryLocalService _listTypeEntryLocalService;
	private final ObjectField _objectField;

}