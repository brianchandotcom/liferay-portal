/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.filter;

import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Marko Cikos
 */
public abstract class BaseSelectionFDSFilter implements FDSFilter {

	public String getAPIURL() {
		return null;
	}

	public String getItemKey() {
		return null;
	}

	public String getItemLabel() {
		return null;
	}

	public String getPlaceholder() {
		return "search";
	}

	public ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	public List<SelectionFDSFilterItem> getSelectionFDSFilterItems(
		Locale locale) {

		return Collections.emptyList();
	}

	@Override
	public String getType() {
		return "selection";
	}

	public boolean isAutocompleteEnabled() {
		return false;
	}

	public boolean isMultiple() {
		return true;
	}

}