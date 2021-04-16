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

package com.liferay.dataset.ui.view.table;

import com.liferay.dataset.ui.view.DatasetView;
import com.liferay.dataset.ui.view.DatasetViewContentRendererNames;
import com.liferay.dataset.ui.view.table.schema.TableDatasetViewSchema;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Iván Zaera
 */
public abstract class BaseTableDatasetView implements DatasetView {

	@Override
	public String getContentRendererName() {
		return DatasetViewContentRendererNames.TABLE;
	}

	@Override
	public String getLabel() {
		return DatasetViewContentRendererNames.TABLE;
	}

	@Override
	public String getName() {
		return DatasetViewContentRendererNames.TABLE;
	}

	public ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	public abstract TableDatasetViewSchema getTableDatasetViewSchema();

	@Override
	public String getThumbnail() {
		return DatasetViewContentRendererNames.TABLE;
	}

}