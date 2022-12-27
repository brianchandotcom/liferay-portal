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
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;
import com.liferay.object.exception.ObjectViewFilterColumnException;
import com.liferay.object.field.frontend.data.set.filter.ObjectEntryStatusSelectionFDSFilter;
import com.liferay.object.model.ObjectViewFilterColumn;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Feliphe Marinho
 */
public class StatusSystemObjectFieldFilterStrategy
	extends BaseObjectFieldFilterStrategy {

	public StatusSystemObjectFieldFilterStrategy(
		Language language, Locale locale,
		ObjectViewFilterColumn objectViewFilterColumn) {

		super(locale, objectViewFilterColumn);

		_language = language;
	}

	@Override
	public FDSFilter getFDSFilter() throws PortalException {
		return new ObjectEntryStatusSelectionFDSFilter(parse());
	}

	@Override
	public List<SelectionFDSFilterItem> getSelectionFDSFilterItems()
		throws JSONException {

		JSONArray jsonArray = getJSONArray();

		List<SelectionFDSFilterItem> selectionFDSFilterItems =
			new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			Integer status = (Integer)jsonArray.get(i);

			selectionFDSFilterItems.add(
				new SelectionFDSFilterItem(
					_language.get(
						locale, WorkflowConstants.getStatusLabel(status)),
					status));
		}

		return selectionFDSFilterItems;
	}

	@Override
	public String toValueSummary() throws PortalException {
		return StringUtil.merge(
			ListUtil.toList(
				getSelectionFDSFilterItems(),
				getSelectionFDSFilterItem ->
					getSelectionFDSFilterItem.getLabel()),
			StringPool.COMMA_AND_SPACE);
	}

	@Override
	public void validate() throws PortalException {
		super.validate();

		try {
			getSelectionFDSFilterItems();
		}
		catch (Exception exception) {
			throw new ObjectViewFilterColumnException(
				"JSON array is invalid for filter type " +
					objectViewFilterColumn.getFilterType(),
				exception);
		}
	}

	private final Language _language;

}