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

package com.liferay.dataset.view.table;

import com.liferay.dataset.ClayDataSetDisplayView;
import com.liferay.dataset.constants.ClayDataSetConstants;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Marco Leo
 */
public abstract class BaseTableClayDataSetDisplayView
	implements ClayDataSetDisplayView {

	public abstract ClayTableSchema getClayTableSchema();

	@Override
	public String getContentRenderer() {
		return ClayDataSetConstants.TABLE;
	}

	@Override
	public String getLabel() {
		return ClayDataSetConstants.TABLE;
	}

	@Override
	public String getName() {
		return ClayDataSetConstants.TABLE;
	}

	public ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	@Override
	public String getThumbnail() {
		return ClayDataSetConstants.TABLE;
	}

}