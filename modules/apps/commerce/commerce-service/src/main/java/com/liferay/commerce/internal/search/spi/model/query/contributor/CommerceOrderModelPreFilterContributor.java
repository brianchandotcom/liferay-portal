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

package com.liferay.commerce.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.MissingFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import org.osgi.service.component.annotations.Component;

/**
 * @author Danny Situ
 */
@Component(
	property = "indexer.class.name=com.liferay.commerce.model.CommerceOrder",
	service = ModelPreFilterContributor.class
)
public class CommerceOrderModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		_filterByCommerceAccountIds(booleanFilter, searchContext);
		_filterByGroupIds(booleanFilter, searchContext);
		_filterByOrderStatuses(booleanFilter, searchContext);
	}

	private void _filterByCommerceAccountIds(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		long[] commerceAccountIds = GetterUtil.getLongValues(
			searchContext.getAttribute("commerceAccountIds"), null);

		if (commerceAccountIds == null) {
			return;
		}

		BooleanFilter commerceAccountIdBooleanFilter = new BooleanFilter();

		for (long commerceAccountId : commerceAccountIds) {
			Filter termFilter = new TermFilter(
				"commerceAccountId", String.valueOf(commerceAccountId));

			commerceAccountIdBooleanFilter.add(
				termFilter, BooleanClauseOccur.SHOULD);
		}

		commerceAccountIdBooleanFilter.add(
			new MissingFilter("commerceAccountId"), BooleanClauseOccur.SHOULD);

		booleanFilter.add(
			commerceAccountIdBooleanFilter, BooleanClauseOccur.MUST);
	}

	private void _filterByGroupIds(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		long[] groupIds = searchContext.getGroupIds();

		if ((groupIds == null) || (groupIds.length == 0)) {
			booleanFilter.addTerm(
				Field.GROUP_ID, "-1", BooleanClauseOccur.MUST);
		}
	}

	private void _filterByOrderStatuses(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		int[] orderStatuses = GetterUtil.getIntegerValues(
			searchContext.getAttribute("orderStatuses"), null);

		if (orderStatuses == null) {
			return;
		}

		BooleanFilter orderStatusesBooleanFilter = new BooleanFilter();

		for (long orderStatus : orderStatuses) {
			Filter termFilter = new TermFilter(
				"orderStatus", String.valueOf(orderStatus));

			orderStatusesBooleanFilter.add(
				termFilter, BooleanClauseOccur.SHOULD);
		}

		orderStatusesBooleanFilter.add(
			new MissingFilter("orderStatus"), BooleanClauseOccur.SHOULD);

		if (GetterUtil.getBoolean(
				searchContext.getAttribute("negateOrderStatuses"))) {

			booleanFilter.add(
				orderStatusesBooleanFilter, BooleanClauseOccur.MUST_NOT);
		}
		else {
			booleanFilter.add(
				orderStatusesBooleanFilter, BooleanClauseOccur.MUST);
		}
	}

}