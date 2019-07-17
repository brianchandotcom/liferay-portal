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

package com.liferay.layout.util.template;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Eudaldo Alonso
 */
public class LayoutData {

	public LayoutData(Layout layout) {
		CounterLocalServiceUtil.reset(LayoutColumn.class.getName());
		CounterLocalServiceUtil.reset(LayoutRow.class.getName());

		_layout = layout;
	}

	public void addRow(Consumer<LayoutRow> consumer) {
		LayoutRow row = new LayoutRow(_layout);

		try {
			consumer.accept(row);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		_rows.add(row);
	}

	@Override
	public String toString() {
		JSONObject jsonObject = JSONUtil.put(
			"nextColumnId",
			CounterLocalServiceUtil.increment(LayoutColumn.class.getName())
		).put(
			"nextRowId",
			CounterLocalServiceUtil.increment(LayoutRow.class.getName())
		);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		try {
			for (LayoutRow row : _rows) {
				jsonArray.put(JSONFactoryUtil.createJSONObject(row.toString()));
			}
		}
		catch (Exception e) {
		}

		jsonObject.put("structure", jsonArray);

		return jsonObject.toString();
	}

	private final Layout _layout;
	private final List<LayoutRow> _rows = new ArrayList<>();

}