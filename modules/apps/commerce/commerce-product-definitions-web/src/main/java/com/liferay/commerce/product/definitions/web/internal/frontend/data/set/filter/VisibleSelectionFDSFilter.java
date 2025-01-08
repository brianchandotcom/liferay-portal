/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.definitions.web.internal.frontend.data.set.filter;

import com.liferay.commerce.product.definitions.web.internal.constants.CPConfigurationFDSNames;
import com.liferay.frontend.data.set.filter.BaseSelectionFDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "frontend.data.set.name=" + CPConfigurationFDSNames.PRODUCT_CONFIGURATIONS,
	service = FDSFilter.class
)
public class VisibleSelectionFDSFilter extends BaseSelectionFDSFilter {

	@Override
	public String getId() {
		return "visible";
	}

	@Override
	public String getLabel() {
		return "visible";
	}

	@Override
	public List<SelectionFDSFilterItem> getSelectionFDSFilterItems(
		Locale locale) {

		return ListUtil.fromArray(
			new SelectionFDSFilterItem(_language.get(locale, "yes"), false),
			new SelectionFDSFilterItem(_language.get(locale, "no"), true));
	}

	@Override
	public boolean isMultiple() {
		return false;
	}

	@Reference
	private Language _language;

}