/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.field.filter.parser;

import com.liferay.object.constants.ObjectViewFilterColumnConstants;
import com.liferay.object.exception.ObjectViewFilterColumnException;
import com.liferay.object.model.ObjectViewFilterColumn;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;

/**
 * @author Feliphe Marinho
 */
public abstract class BaseObjectFieldFilterStrategy
	implements ObjectFieldFilterStrategy {

	public BaseObjectFieldFilterStrategy(
		Locale locale, ObjectViewFilterColumn objectViewFilterColumn) {

		this.locale = locale;
		this.objectViewFilterColumn = objectViewFilterColumn;
	}

	@Override
	public Map<String, Object> parse() throws PortalException {
		if (Validator.isNull(objectViewFilterColumn.getFilterType())) {
			return null;
		}

		return HashMapBuilder.<String, Object>put(
			"exclude",
			ObjectViewFilterColumnConstants.FILTER_TYPE_EXCLUDES.equals(
				objectViewFilterColumn.getFilterType())
		).put(
			"selectedItems", getSelectionFDSFilterItems()
		).build();
	}

	@Override
	public void validate() throws PortalException {
		if (getJSONArray() == null) {
			throw new ObjectViewFilterColumnException(
				"JSON array is null for filter type " +
					objectViewFilterColumn.getFilterType());
		}
	}

	protected JSONArray getJSONArray() throws JSONException {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			objectViewFilterColumn.getJSON());

		return jsonObject.getJSONArray(objectViewFilterColumn.getFilterType());
	}

	protected Locale locale;
	protected ObjectViewFilterColumn objectViewFilterColumn;

}