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

import com.liferay.fragment.constants.FragmentConstants;
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

	public static LayoutData of(
		Layout layout, Consumer<LayoutRow>... consumers) {

		LayoutData layoutData = new LayoutData(layout);

		for (Consumer<LayoutRow> consumer : consumers) {
			layoutData.addLayoutRow(consumer);
		}

		return layoutData;
	}

	public void addLayoutRow(Consumer<LayoutRow> consumer) {
		LayoutRow layoutRow = new LayoutRow(_layout);

		try {
			consumer.accept(layoutRow);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		_layoutRows.add(layoutRow);
	}

	public JSONObject getLayoutDataJSONObject() {
		int rowId = 0;
		int columnId = 0;

		JSONArray layoutRowJSONArray = JSONFactoryUtil.createJSONArray();

		for (LayoutRow layoutRow : _layoutRows) {
			JSONArray layoutColumnJSONArray = JSONFactoryUtil.createJSONArray();

			for (LayoutColumn layoutColumn : layoutRow.getLayoutColumns()) {
				JSONObject layoutColumnJSONObject = JSONUtil.put(
					"columnId", String.valueOf(columnId++));

				JSONArray fragmentEntryLinkIdsJSONArray =
					JSONFactoryUtil.createJSONArray();

				for (long fragmentEntryLinkId :
						layoutColumn.getFragmentEntryLinkIds()) {

					fragmentEntryLinkIdsJSONArray.put(fragmentEntryLinkId);
				}

				layoutColumnJSONObject.put(
					"fragmentEntryLinkIds", fragmentEntryLinkIdsJSONArray
				).put(
					"size", String.valueOf(layoutColumn.getSize())
				);

				layoutColumnJSONArray.put(layoutColumnJSONObject);
			}

			layoutRowJSONArray = JSONUtil.put(
				JSONUtil.put(
					"columns", layoutColumnJSONArray
				).put(
					"rowId", String.valueOf(rowId++)
				).put(
					"type", String.valueOf(FragmentConstants.TYPE_COMPONENT)
				));
		}

		return JSONUtil.put(
			"nextColumnId", columnId
		).put(
			"nextRowId", rowId
		).put(
			"structure", layoutRowJSONArray
		);
	}

	private LayoutData(Layout layout) {
		_layout = layout;
	}

	private final Layout _layout;
	private final List<LayoutRow> _layoutRows = new ArrayList<>();

}