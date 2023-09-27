/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.field.filter.parser;

import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.frontend.data.set.filter.ListTypeEntrySelectionFDSFilter;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectViewFilterColumn;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

		return new ListTypeEntrySelectionFDSFilter(
			StringUtil.equals(
				_objectField.getBusinessType(),
				ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST),
			_objectField.getName(), listTypeDefinition.getName(locale),
			_objectField.getListTypeDefinitionId(), parse());
	}

	@Override
	public List<SelectionFDSFilterItem> getSelectionFDSFilterItems()
		throws JSONException {

		List<SelectionFDSFilterItem> selectionFDSFilterItems =
			new ArrayList<>();

		JSONArray jsonArray = getJSONArray();

		for (int i = 0; i < jsonArray.length(); i++) {
			ListTypeEntry listTypeEntry =
				_listTypeEntryLocalService.fetchListTypeEntry(
					_listTypeDefinitionId, jsonArray.getString(i));

			selectionFDSFilterItems.add(
				new SelectionFDSFilterItem(
					listTypeEntry.getName(locale), jsonArray.getString(i)));
		}

		return selectionFDSFilterItems;
	}

	@Override
	public String toValueSummary() throws PortalException {
		return StringUtil.merge(
			ListUtil.toList(
				getSelectionFDSFilterItems(),
				selectionFDSFilterItem -> selectionFDSFilterItem.getLabel()),
			StringPool.COMMA_AND_SPACE);
	}

	private final long _listTypeDefinitionId;
	private final ListTypeDefinitionLocalService
		_listTypeDefinitionLocalService;
	private final ListTypeEntryLocalService _listTypeEntryLocalService;
	private final ObjectField _objectField;

}