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

package com.liferay.dataset.ui.filter.autocomplete;

import com.liferay.dataset.ui.filter.DatasetFilter;
import com.liferay.dataset.ui.filter.DatasetFilterTypes;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Iván Zaera
 */
public abstract class BaseAutocompleteDatasetFilter implements DatasetFilter {

	public abstract String getAPIURL();

	public abstract String getItemKey();

	public abstract String getItemLabel();

	public String getPlaceholder() {
		return "search";
	}

	public ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	@Override
	public String getType() {
		return DatasetFilterTypes.AUTOCOMPLETE;
	}

	public boolean isMultipleSelection() {
		return true;
	}

}