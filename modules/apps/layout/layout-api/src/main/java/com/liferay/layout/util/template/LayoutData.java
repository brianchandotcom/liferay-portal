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

import com.liferay.layout.util.structure.ColumnLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.json.JSONObject;
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
		LayoutStructure layoutStructure = new LayoutStructure();

		LayoutStructureItem rootLayoutStructureItem =
			layoutStructure.addRootLayoutStructureItem();

		int i = 0;

		for (LayoutRow layoutRow : _layoutRows) {
			LayoutStructureItem containerLayoutStructureItem =
				layoutStructure.addContainerLayoutStructureItem(
					rootLayoutStructureItem.getItemId(), i++);

			List<LayoutColumn> layoutColumns = layoutRow.getLayoutColumns();

			LayoutStructureItem rowLayoutStructureItem =
				layoutStructure.addRowLayoutStructureItem(
					containerLayoutStructureItem.getItemId(), 0,
					layoutColumns.size());

			int j = 0;

			for (LayoutColumn layoutColumn : layoutColumns) {
				ColumnLayoutStructureItem columnLayoutStructureItem =
					(ColumnLayoutStructureItem)
						layoutStructure.addColumnLayoutStructureItem(
							rowLayoutStructureItem.getItemId(), j++);

				columnLayoutStructureItem.setSize(layoutColumn.getSize());

				int k = 0;

				for (long fragmentEntryLinkId :
						layoutColumn.getFragmentEntryLinkIds()) {

					layoutStructure.addFragmentLayoutStructureItem(
						fragmentEntryLinkId,
						columnLayoutStructureItem.getItemId(), k++);
				}
			}
		}

		return layoutStructure.toJSONObject();
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