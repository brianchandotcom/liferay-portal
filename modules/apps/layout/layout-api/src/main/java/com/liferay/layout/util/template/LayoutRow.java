/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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