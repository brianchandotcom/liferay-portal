/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "indexer.class.name=com.liferay.commerce.model.CommerceOrderItem",
	service = ModelPreFilterContributor.class
)
public class CommerceOrderItemModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		_filterByCommerceOrderId(booleanFilter, searchContext);
		_filterByParentCommerceOrderItemId(booleanFilter, searchContext);
	}

	private void _filterByCommerceOrderId(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		Long commerceOrderId = (Long)searchContext.getAttribute(
			"commerceOrderId");

		if (commerceOrderId != null) {
			booleanFilter.addRequiredTerm("commerceOrderId", commerceOrderId);
		}
	}

	private void _filterByParentCommerceOrderItemId(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		Long parentCommerceOrderItemId = (Long)searchContext.getAttribute(
			"parentCommerceOrderItemId");

		if (parentCommerceOrderItemId != null) {
			booleanFilter.addRequiredTerm(
				"parentCommerceOrderItemId", parentCommerceOrderItemId);
		}
	}

}