/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.ExistsFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian I. Kim
 */
@Component(
	property = "indexer.class.name=com.liferay.commerce.inventory.model.CommerceInventoryBookedQuantity",
	service = ModelPreFilterContributor.class
)
public class CommerceInventoryBookedQuantityModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		_filterBySKU(booleanFilter, searchContext);
	}

	private void _filterBySKU(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		String sku = GetterUtil.getString(searchContext.getAttribute("sku"));

		if (Validator.isNull(sku)) {
			booleanFilter.add(
				new ExistsFilter("sku.raw"), BooleanClauseOccur.MUST_NOT);
		}

		booleanFilter.add(
			new TermFilter("sku.raw", sku), BooleanClauseOccur.MUST);
	}

}