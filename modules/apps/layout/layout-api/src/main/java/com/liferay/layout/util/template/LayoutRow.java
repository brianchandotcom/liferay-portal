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

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.model.Layout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class LayoutRow {

	public static LayoutRow of(
		Layout layout, UnsafeConsumer<LayoutRow, Exception> unsafeConsumer) {

		LayoutRow layoutRow = new LayoutRow(layout);

		try {
			unsafeConsumer.accept(layoutRow);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}

		return layoutRow;
	}

	public void addLayoutColumns(
		UnsafeConsumer<LayoutColumn, Exception>... unsafeConsumers) {

		for (UnsafeConsumer<LayoutColumn, Exception> unsafeConsumer :
				unsafeConsumers) {

			_layoutColumns.add(LayoutColumn.of(_layout, unsafeConsumer));
		}
	}

	public List<LayoutColumn> getLayoutColumns() {
		return _layoutColumns;
	}

	private LayoutRow(Layout layout) {
		_layout = layout;
	}

	private final Layout _layout;
	private final List<LayoutColumn> _layoutColumns = new ArrayList<>();

}