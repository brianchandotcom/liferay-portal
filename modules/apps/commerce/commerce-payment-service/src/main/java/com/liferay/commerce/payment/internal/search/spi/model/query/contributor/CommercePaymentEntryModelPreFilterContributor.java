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
	property = "indexer.class.name=com.liferay.commerce.payment.model.CommercePaymentEntry",
	service = ModelPreFilterContributor.class
)
public class CommercePaymentEntryModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		_filterByClassNameIds(booleanFilter, searchContext);
		_filterByClassPKs(booleanFilter, searchContext);
		_filterByCurrencyCodes(booleanFilter, searchContext);
		_filterByPaymentMethodNames(booleanFilter, searchContext);
		_filterByStatuses(booleanFilter, searchContext);
	}

	private void _filterByClassNameIds(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		long[] classNameIds = GetterUtil.getLongValues(
			searchContext.getAttribute("classNameIds"), null);

		if (classNameIds == null) {
			return;
		}

		BooleanFilter classNameIdsBooleanFilter = new BooleanFilter();

		for (long classNameId : classNameIds) {
			Filter termFilter = new TermFilter(
				"classNameId", String.valueOf(classNameId));

			classNameIdsBooleanFilter.add(
				termFilter, BooleanClauseOccur.SHOULD);
		}

		classNameIdsBooleanFilter.add(
			new MissingFilter("classNameId"), BooleanClauseOccur.SHOULD);

		booleanFilter.add(classNameIdsBooleanFilter, BooleanClauseOccur.MUST);
	}

	private void _filterByClassPKs(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		long[] classPKs = GetterUtil.getLongValues(
			searchContext.getAttribute("classPKs"), null);

		if (classPKs == null) {
			return;
		}

		BooleanFilter classPKsBooleanFilter = new BooleanFilter();

		for (long classPK : classPKs) {
			Filter termFilter = new TermFilter(
				"classPK", String.valueOf(classPK));

			classPKsBooleanFilter.add(termFilter, BooleanClauseOccur.SHOULD);
		}

		classPKsBooleanFilter.add(
			new MissingFilter("classPK"), BooleanClauseOccur.SHOULD);

		booleanFilter.add(classPKsBooleanFilter, BooleanClauseOccur.MUST);
	}

	private void _filterByCurrencyCodes(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		String[] currencyCodes = GetterUtil.getStringValues(
			searchContext.getAttribute("currencyCodes"));

		if (currencyCodes.length < 1) {
			return;
		}

		BooleanFilter currencyCodesBooleanFilter = new BooleanFilter();

		for (String currencyCode : currencyCodes) {
			Filter termFilter = new TermFilter(
				"currencyCode", String.valueOf(currencyCode));

			currencyCodesBooleanFilter.add(
				termFilter, BooleanClauseOccur.SHOULD);
		}

		currencyCodesBooleanFilter.add(
			new MissingFilter("currencyCode"), BooleanClauseOccur.SHOULD);

		booleanFilter.add(currencyCodesBooleanFilter, BooleanClauseOccur.MUST);
	}

	private void _filterByPaymentMethodNames(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		String[] paymentMethodNames = GetterUtil.getStringValues(
			searchContext.getAttribute("paymentMethodNames"));

		if (paymentMethodNames.length < 1) {
			return;
		}

		BooleanFilter paymentMethodNamesBooleanFilter = new BooleanFilter();

		for (String paymentMethodName : paymentMethodNames) {
			Filter termFilter = new TermFilter(
				"paymentMethodName", String.valueOf(paymentMethodName));

			paymentMethodNamesBooleanFilter.add(
				termFilter, BooleanClauseOccur.SHOULD);
		}

		paymentMethodNamesBooleanFilter.add(
			new MissingFilter("paymentMethodName"), BooleanClauseOccur.SHOULD);

		booleanFilter.add(
			paymentMethodNamesBooleanFilter, BooleanClauseOccur.MUST);
	}

	private void _filterByStatuses(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		int[] paymentStatuses = GetterUtil.getIntegerValues(
			searchContext.getAttribute("paymentStatuses"), null);

		if (paymentStatuses == null) {
			return;
		}

		BooleanFilter statusesBooleanFilter = new BooleanFilter();

		for (long paymentStatus : paymentStatuses) {
			Filter termFilter = new TermFilter(
				"paymentStatus", String.valueOf(paymentStatus));

			statusesBooleanFilter.add(termFilter, BooleanClauseOccur.SHOULD);
		}

		statusesBooleanFilter.add(
			new MissingFilter("paymentStatus"), BooleanClauseOccur.SHOULD);

		if (GetterUtil.getBoolean(
				searchContext.getAttribute("excludePaymentStatuses"))) {

			booleanFilter.add(
				statusesBooleanFilter, BooleanClauseOccur.MUST_NOT);
		}
		else {
			booleanFilter.add(statusesBooleanFilter, BooleanClauseOccur.MUST);
		}
	}

}