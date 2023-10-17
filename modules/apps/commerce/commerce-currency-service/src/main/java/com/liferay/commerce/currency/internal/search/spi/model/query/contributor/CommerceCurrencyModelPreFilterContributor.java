/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.internal.search.spi.model.query.contributor;

import com.liferay.commerce.product.constants.CPField;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mahmoud Azzam
 */
@Component(
	property = "indexer.class.name=com.liferay.commerce.currency.model.CommerceCurrency",
	service = ModelPreFilterContributor.class
)
public class CommerceCurrencyModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		_filterByActive(booleanFilter, searchContext);
	}

	private void _filterByActive(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		Boolean active = (Boolean)GetterUtil.getObject(
			searchContext.getAttribute(CPField.ACTIVE));

		if (active != null) {
			booleanFilter.addRequiredTerm(CPField.ACTIVE, active);
		}
	}

}