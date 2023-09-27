/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		List<SelectionFDSFilterItem> selectionFDSFilterItems =
			new ArrayList<>();

		JSONArray jsonArray = getJSONArray();

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