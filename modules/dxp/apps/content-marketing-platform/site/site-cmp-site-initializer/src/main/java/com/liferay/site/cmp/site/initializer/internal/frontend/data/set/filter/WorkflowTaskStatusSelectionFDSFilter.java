/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.frontend.data.set.filter;

import com.liferay.frontend.data.set.constants.FDSEntityFieldTypes;
import com.liferay.frontend.data.set.filter.BaseSelectionFDSFilter;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.Locale;

/**
 * @author Jhosseph Gonzalez
 */
public class WorkflowTaskStatusSelectionFDSFilter
	extends BaseSelectionFDSFilter {

	@Override
	public String getEntityFieldType() {
		return FDSEntityFieldTypes.BOOLEAN;
	}

	@Override
	public String getId() {
		return "completed";
	}

	@Override
	public String getLabel() {
		return "status";
	}

	@Override
	public List<SelectionFDSFilterItem> getSelectionFDSFilterItems(
		Locale locale) {

		return ListUtil.fromArray(
			new SelectionFDSFilterItem(
				LanguageUtil.get(locale, "completed"), "true"),
			new SelectionFDSFilterItem(
				LanguageUtil.get(locale, "pending"), "false"));
	}

	@Override
	public boolean isAutocompleteEnabled() {
		return false;
	}

}