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

package com.liferay.portal.search.web.internal.sort.display.context;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.search.web.internal.sort.portlet.SortPortletPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
public class SortDisplayBuilder {

	public SortDisplayBuilder(SortPortletPreferences sortPortletPreferences) {
		_sortPortletPreferences = sortPortletPreferences;
	}

	public SortDisplayContext build() {
		SortDisplayContext sortDisplayContext = new SortDisplayContext();

		sortDisplayContext.setParameterName(_parameterName);
		sortDisplayContext.setParameterValue(getParameterValue());
		sortDisplayContext.setRenderNothing(isRenderNothing());
		sortDisplayContext.setSortTermDisplayContexts(
			buildTermDisplayContexts());

		return sortDisplayContext;
	}

	public SortDisplayBuilder parameterName(String parameterName) {
		_parameterName = parameterName;

		return this;
	}

	public SortDisplayBuilder parameterValues(String... parameterValues) {
		if (parameterValues == null) {
			_selectedFields = Collections.emptyList();

			return this;
		}

		_selectedFields = Arrays.asList(parameterValues);

		return this;
	}

	protected SortTermDisplayContext buildTermDisplayContext(
		String label, String field) {

		SortTermDisplayContext sortTermDisplayContext =
			new SortTermDisplayContext();

		sortTermDisplayContext.setLabel(label);
		sortTermDisplayContext.setField(field);
		sortTermDisplayContext.setSelected(_selectedFields.contains(field));

		return sortTermDisplayContext;
	}

	protected List<SortTermDisplayContext> buildTermDisplayContexts() {
		List<SortTermDisplayContext> sortTermDisplayContexts =
			new ArrayList<>();

		JSONArray fieldsJSONArray =
			_sortPortletPreferences.getFieldsJSONArray();

		for (int i = 0; i < fieldsJSONArray.length(); i++) {
			JSONObject jsonObject = fieldsJSONArray.getJSONObject(i);

			sortTermDisplayContexts.add(
				buildTermDisplayContext(
					jsonObject.getString("label"),
					jsonObject.getString("field")));
		}

		return sortTermDisplayContexts;
	}

	protected String getParameterValue() {
		if (_selectedFields.size() > 0) {
			return _selectedFields.get(_selectedFields.size() - 1);
		}

		return null;
	}

	protected boolean isRenderNothing() {
		JSONArray jsonArray = _sortPortletPreferences.getFieldsJSONArray();

		if (jsonArray == null) {
			return true;
		}

		if (jsonArray.length() == 0) {
			return true;
		}

		return false;
	}

	private String _parameterName;
	private List<String> _selectedFields = Collections.emptyList();
	private final SortPortletPreferences _sortPortletPreferences;

}