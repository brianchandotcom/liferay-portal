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
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class LayoutData {

	public static LayoutData of(
		Layout layout,
		UnsafeConsumer<LayoutRow, Exception>... unsafeConsumers) {

		return new LayoutData(layout, unsafeConsumers);
	}

	public JSONObject getLayoutDataJSONObject() {
		int columnId = 0;
		int rowId = 0;

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

			layoutRowJSONArray.put(
				JSONUtil.put(
					"columns", layoutColumnJSONArray
				).put(
					"config", JSONFactoryUtil.createJSONObject()
				).put(
					"rowId", String.valueOf(rowId++)
				).put(
					"type", String.valueOf(FragmentConstants.TYPE_COMPONENT)
				));
		}

		return JSONUtil.put(
			"config", JSONFactoryUtil.createJSONObject()
		).put(
			"nextColumnId", columnId
		).put(
			"nextRowId", rowId
		).put(
			"structure", layoutRowJSONArray
		);
	}

	private LayoutData(
		Layout layout,
		UnsafeConsumer<LayoutRow, Exception>... unsafeConsumers) {

		for (UnsafeConsumer<LayoutRow, Exception> unsafeConsumer :
				unsafeConsumers) {

			_layoutRows.add(LayoutRow.of(layout, unsafeConsumer));
		}
	}

	private final List<LayoutRow> _layoutRows = new ArrayList<>();

}