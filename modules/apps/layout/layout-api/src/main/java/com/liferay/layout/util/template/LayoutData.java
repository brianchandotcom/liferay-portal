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
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.GetterUtil;

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

		ContainerStyledLayoutStructureItem containerStyledLayoutStructureItem =
			(ContainerStyledLayoutStructureItem)
				layoutStructure.addContainerStyledLayoutStructureItem(
					rootLayoutStructureItem.getItemId(), 0);

		boolean wrapWidgetPageContent = GetterUtil.getBoolean(
			_layout.getThemeSetting("wrap-widget-page-content", "regular"));

		if (wrapWidgetPageContent) {
			containerStyledLayoutStructureItem.setWidthType("fixed");
		}

		int i = 0;

		for (LayoutRow layoutRow : _layoutRows) {
			List<LayoutColumn> layoutColumns = layoutRow.getLayoutColumns();

			LayoutStructureItem rowStyledLayoutStructureItem =
				layoutStructure.addRowStyledLayoutStructureItem(
					containerStyledLayoutStructureItem.getItemId(), i++,
					layoutColumns.size());

			int j = 0;

			for (LayoutColumn layoutColumn : layoutColumns) {
				ColumnLayoutStructureItem columnLayoutStructureItem =
					(ColumnLayoutStructureItem)
						layoutStructure.addColumnLayoutStructureItem(
							rowStyledLayoutStructureItem.getItemId(), j++);

				columnLayoutStructureItem.setSize(layoutColumn.getSize());

				int k = 0;

				for (long fragmentEntryLinkId :
						layoutColumn.getFragmentEntryLinkIds()) {

					layoutStructure.addFragmentStyledLayoutStructureItem(
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

		_layout = layout;

		for (UnsafeConsumer<LayoutRow, Exception> unsafeConsumer :
				unsafeConsumers) {

			_layoutRows.add(LayoutRow.of(layout, unsafeConsumer));
		}
	}

	private final Layout _layout;
	private final List<LayoutRow> _layoutRows = new ArrayList<>();

}