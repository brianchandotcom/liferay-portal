/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
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
 * @author Luca Pellizzon
 */
@Component(
	property = "indexer.class.name=com.liferay.commerce.payment.model.CommercePaymentEntryAudit",
	service = ModelPreFilterContributor.class
)
public class CommercePaymentEntryAuditModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		_filterByCommercePaymentEntryIds(booleanFilter, searchContext);
		_filterByLogTypes(booleanFilter, searchContext);
	}

	private void _filterByCommercePaymentEntryIds(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		long[] commercePaymentEntryIds = GetterUtil.getLongValues(
			searchContext.getAttribute("commercePaymentEntryIds"), null);

		if (commercePaymentEntryIds.length < 1) {
			return;
		}

		BooleanFilter commercePaymentEntryIdBooleanFilter = new BooleanFilter();

		for (long commercePaymentEntryId : commercePaymentEntryIds) {
			Filter termFilter = new TermFilter(
				"commercePaymentEntryId",
				String.valueOf(commercePaymentEntryId));

			commercePaymentEntryIdBooleanFilter.add(
				termFilter, BooleanClauseOccur.SHOULD);
		}

		commercePaymentEntryIdBooleanFilter.add(
			new MissingFilter("commercePaymentEntryId"),
			BooleanClauseOccur.SHOULD);

		booleanFilter.add(
			commercePaymentEntryIdBooleanFilter, BooleanClauseOccur.MUST);
	}

	private void _filterByLogTypes(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		String[] logTypes = GetterUtil.getStringValues(
			searchContext.getAttribute("logTypes"));

		if (logTypes.length < 1) {
			return;
		}

		BooleanFilter logTypeBooleanFilter = new BooleanFilter();

		for (String logType : logTypes) {
			Filter termFilter = new TermFilter("logType", logType);

			logTypeBooleanFilter.add(termFilter, BooleanClauseOccur.SHOULD);
		}

		logTypeBooleanFilter.add(
			new MissingFilter("logType"), BooleanClauseOccur.SHOULD);

		booleanFilter.add(logTypeBooleanFilter, BooleanClauseOccur.MUST);
	}

}