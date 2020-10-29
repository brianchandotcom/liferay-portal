/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.machine.learning.internal.forecast;

import com.liferay.commerce.machine.learning.forecast.CommerceAccountCommerceMLForecast;
import com.liferay.commerce.machine.learning.forecast.CommerceAccountCommerceMLForecastManager;
import com.liferay.commerce.machine.learning.internal.forecast.constants.CommerceMLForecastField;
import com.liferay.commerce.machine.learning.internal.forecast.constants.CommerceMLForecastPeriod;
import com.liferay.commerce.machine.learning.internal.forecast.constants.CommerceMLForecastScope;
import com.liferay.commerce.machine.learning.internal.forecast.constants.CommerceMLForecastTarget;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.engine.adapter.search.CountSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Riccardo Ferrari
 */
@Component(
	enabled = false, immediate = true,
	service = CommerceAccountCommerceMLForecastManager.class
)
public class CommerceAccountCommerceMLForecastManagerImpl
	extends BaseCommerceMLForecastServiceImpl<CommerceAccountCommerceMLForecast>
	implements CommerceAccountCommerceMLForecastManager {

	@Override
	public CommerceAccountCommerceMLForecast
			addCommerceAccountCommerceMLForecast(
				CommerceAccountCommerceMLForecast
					commerceAccountCommerceMLForecast)
		throws PortalException {

		long commerceMLForecastId = getHash(
			commerceAccountCommerceMLForecast.getCommerceAccountId(),
			commerceAccountCommerceMLForecast.getPeriod(),
			commerceAccountCommerceMLForecast.getScope(),
			commerceAccountCommerceMLForecast.getTarget(),
			commerceAccountCommerceMLForecast.getTimestamp());

		commerceAccountCommerceMLForecast.setForecastId(commerceMLForecastId);

		return addCommerceMLForecast(commerceAccountCommerceMLForecast);
	}

	@Override
	public CommerceAccountCommerceMLForecast create() {
		return new CommerceAccountCommerceMLForecastImpl();
	}

	@Override
	public CommerceAccountCommerceMLForecast
			getCommerceAccountCommerceMLForecast(
				long companyId, long forecastId)
		throws PortalException {

		return getCommerceMLForecast(companyId, forecastId);
	}

	@Override
	public List<CommerceAccountCommerceMLForecast>
			getMonthlyRevenueCommerceAccountCommerceMLForecasts(
				long companyId, long[] commerceAccountIds, Date actualDate,
				int historyLength, int forecastLength)
		throws PortalException {

		int size = commerceAccountIds.length * (historyLength + forecastLength);

		return getMonthlyRevenueCommerceAccountCommerceMLForecasts(
			companyId, commerceAccountIds, actualDate, historyLength,
			forecastLength, 0, size);
	}

	@Override
	public List<CommerceAccountCommerceMLForecast>
			getMonthlyRevenueCommerceAccountCommerceMLForecasts(
				long companyId, long[] commerceAccountIds, Date actualDate,
				int historyLength, int forecastLength, int start, int end)
		throws PortalException {

		Query query = _getMonthlyRevenueQuery(
			commerceAccountIds, actualDate, historyLength, forecastLength);

		int size = end - start;

		SearchSearchRequest searchSearchRequest = getSearchSearchRequest(
			commerceMLIndexer.getIndexName(companyId), query, start, size,
			getDefaultSort(true));

		return getSearchResults(searchSearchRequest);
	}

	@Override
	public long getMonthlyRevenueCommerceAccountCommerceMLForecastsCount(
			long companyId, long[] commerceAccountIds, Date actualDate,
			int historyLength, int forecastLength)
		throws PortalException {

		Query query = _getMonthlyRevenueQuery(
			commerceAccountIds, actualDate, historyLength, forecastLength);

		CountSearchRequest countSearchRequest = getCountSearchRequest(
			commerceMLIndexer.getIndexName(companyId), query);

		return getCountResult(countSearchRequest);
	}

	@Override
	protected Document toDocumentModel(
		CommerceAccountCommerceMLForecast commerceAccountCommerceMLForecast) {

		Document document = getBaseDocument(commerceAccountCommerceMLForecast);

		document.addNumber(
			CommerceMLForecastField.COMMERCE_ACCOUNT_ID,
			commerceAccountCommerceMLForecast.getCommerceAccountId());

		return document;
	}

	@Override
	protected CommerceAccountCommerceMLForecast toForecastModel(
		Document document) {

		CommerceAccountCommerceMLForecast commerceAccountCommerceMLForecast =
			getBaseCommerceMLForecastModel(
				new CommerceAccountCommerceMLForecastImpl(), document);

		commerceAccountCommerceMLForecast.setCommerceAccountId(
			GetterUtil.getLong(
				document.get(CommerceMLForecastField.COMMERCE_ACCOUNT_ID)));

		return commerceAccountCommerceMLForecast;
	}

	private Query _getMonthlyRevenueQuery(
			long[] commerceAccountIds, Date actualDate, int historyLength,
			int forecastLength)
		throws ParseException {

		CommerceMLForecastPeriod commerceMLForecastPeriod =
			CommerceMLForecastPeriod.MONTH;

		CommerceMLForecastTarget commerceMLForecastTarget =
			CommerceMLForecastTarget.REVENUE;

		Date endDate = getEndDate(
			actualDate, commerceMLForecastPeriod, forecastLength);

		Date startDate = getStartDate(
			actualDate, commerceMLForecastPeriod, historyLength);

		BooleanQuery baseQuery = getBaseQuery(
			_commerceMLForecastScope.getLabel(),
			commerceMLForecastPeriod.getLabel(),
			commerceMLForecastTarget.getLabel(), startDate, endDate);

		BooleanFilter preBooleanFilter = baseQuery.getPreBooleanFilter();

		TermsFilter termsFilter = new TermsFilter(
			CommerceMLForecastField.COMMERCE_ACCOUNT_ID);

		termsFilter.addValues(ArrayUtil.toStringArray(commerceAccountIds));

		preBooleanFilter.add(termsFilter, BooleanClauseOccur.MUST);

		return baseQuery;
	}

	private static final CommerceMLForecastScope _commerceMLForecastScope =
		CommerceMLForecastScope.COMMERCE_ACCOUNT;

}