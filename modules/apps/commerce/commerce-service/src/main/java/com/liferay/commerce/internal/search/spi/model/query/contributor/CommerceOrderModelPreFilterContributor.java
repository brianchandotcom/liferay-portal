/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.MissingFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
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
		BooleanFilter nestedBooleanFilter = new BooleanFilter();

		for (int i = 0; i < commerceAccountIds.length; i++) {
			nestedBooleanFilter.add(
				new TermFilter(
					"commerceAccountId", String.valueOf(commerceAccountIds[i])),
				BooleanClauseOccur.SHOULD);

			if (((i + 1) % _MAX_CLAUSES_COUNT) == 0) {
				commerceAccountIdBooleanFilter.add(
					nestedBooleanFilter, BooleanClauseOccur.SHOULD);

				nestedBooleanFilter = new BooleanFilter();
			}
		}

		if (nestedBooleanFilter.hasClauses()) {
			commerceAccountIdBooleanFilter.add(
				nestedBooleanFilter, BooleanClauseOccur.SHOULD);
		}

		commerceAccountIdBooleanFilter.add(
			new MissingFilter("commerceAccountId"), BooleanClauseOccur.SHOULD);

		booleanFilter.add(
			commerceAccountIdBooleanFilter, BooleanClauseOccur.MUST);
	}

	private void _filterByGroupIds(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		if (ArrayUtil.isEmpty(searchContext.getGroupIds())) {
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
			orderStatusesBooleanFilter.add(
				new TermFilter("orderStatus", String.valueOf(orderStatus)),
				BooleanClauseOccur.SHOULD);
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

	private static final int _MAX_CLAUSES_COUNT = 1024;

}