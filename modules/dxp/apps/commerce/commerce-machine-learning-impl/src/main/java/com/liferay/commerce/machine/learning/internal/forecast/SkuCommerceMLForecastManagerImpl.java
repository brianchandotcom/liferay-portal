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

import com.liferay.commerce.machine.learning.forecast.SkuCommerceMLForecast;
import com.liferay.commerce.machine.learning.forecast.SkuCommerceMLForecastManager;
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

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Riccardo Ferrari
 */
@Component(immediate = true, service = SkuCommerceMLForecastManager.class)
public class SkuCommerceMLForecastManagerImpl
	extends BaseCommerceMLForecastServiceImpl<SkuCommerceMLForecast>
	implements SkuCommerceMLForecastManager {

	@Override
	public SkuCommerceMLForecast addSkuCommerceMLForecast(
			SkuCommerceMLForecast skuCommerceMLForecast)
		throws PortalException {

		long commerceMLForecastId = getHash(
			skuCommerceMLForecast.getPeriod(), skuCommerceMLForecast.getScope(),
			skuCommerceMLForecast.getSku(), skuCommerceMLForecast.getTarget(),
			skuCommerceMLForecast.getTimestamp());

		skuCommerceMLForecast.setForecastId(commerceMLForecastId);

		return addCommerceMLForecast(skuCommerceMLForecast);
	}

	@Override
	public SkuCommerceMLForecast create() {
		return new SkuCommerceMLForecastImpl() {
			{
				setScope(_commerceMLForecastScope.getLabel());
			}
		};
	}

	@Override
	public List<SkuCommerceMLForecast> getMonthlyQuantitySkuCommerceMLForecasts(
			long companyId, String sku, Date actualDate, int historyLength,
			int forecastLength)
		throws PortalException {

		return getMonthlyQuantitySkuCommerceMLForecasts(
			companyId, sku, actualDate, historyLength, forecastLength, 0,
			forecastLength + historyLength);
	}

	@Override
	public List<SkuCommerceMLForecast> getMonthlyQuantitySkuCommerceMLForecasts(
			long companyId, String sku, Date actualDate, int historyLength,
			int forecastLength, int start, int end)
		throws PortalException {

		return getMonthlyQuantitySkuCommerceMLForecasts(
			companyId, new String[] {sku}, actualDate, historyLength,
			forecastLength, 0, forecastLength + historyLength);
	}

	@Override
	public List<SkuCommerceMLForecast> getMonthlyQuantitySkuCommerceMLForecasts(
			long companyId, String[] skus, Date actualDate, int historyLength,
			int forecastLength)
		throws PortalException {

		return getMonthlyQuantitySkuCommerceMLForecasts(
			companyId, skus, actualDate, historyLength, forecastLength, 0,
			forecastLength + historyLength);
	}

	@Override
	public List<SkuCommerceMLForecast> getMonthlyQuantitySkuCommerceMLForecasts(
			long companyId, String[] skus, Date actualDate, int historyLength,
			int forecastLength, int start, int end)
		throws PortalException {

		return getSearchResults(
			getSearchSearchRequest(
				commerceMLIndexer.getIndexName(companyId),
				_getMonthlyQuantityQuery(
					skus, actualDate, historyLength, forecastLength),
				start, end - start, getDefaultSort(true)));
	}

	@Override
	public long getMonthlyQuantitySkuCommerceMLForecastsCount(
			long companyId, String sku, Date actualDate, int historyLength,
			int forecastLength)
		throws PortalException {

		return getMonthlyQuantitySkuCommerceMLForecastsCount(
			companyId, new String[] {sku}, actualDate, historyLength,
			forecastLength);
	}

	@Override
	public long getMonthlyQuantitySkuCommerceMLForecastsCount(
			long companyId, String[] skus, Date actualDate, int historyLength,
			int forecastLength)
		throws PortalException {

		return getCountResult(
			getCountSearchRequest(
				commerceMLIndexer.getIndexName(companyId),
				_getMonthlyQuantityQuery(
					skus, actualDate, historyLength, forecastLength)));
	}

	@Override
	public SkuCommerceMLForecast getSkuCommerceMLForecast(
			long companyId, long forecastId)
		throws PortalException {

		return getCommerceMLForecast(companyId, forecastId);
	}

	@Override
	protected Document toDocumentModel(
		SkuCommerceMLForecast skuCommerceMLForecast) {

		Document document = getDocument(skuCommerceMLForecast);

		document.addText(
			CommerceMLForecastField.SKU, skuCommerceMLForecast.getSku());

		return document;
	}

	@Override
	protected SkuCommerceMLForecast toForecastModel(Document document) {
		SkuCommerceMLForecast skuCommerceMLForecast =
			getCommerceMLForecastModel(
				new SkuCommerceMLForecastImpl(), document);

		skuCommerceMLForecast.setSku(document.get(CommerceMLForecastField.SKU));

		return skuCommerceMLForecast;
	}

	private Query _getMonthlyQuantityQuery(
			String[] sku, Date actualDate, int historyLength,
			int forecastLength)
		throws ParseException {

		CommerceMLForecastPeriod commerceMLForecastPeriod =
			CommerceMLForecastPeriod.MONTH;
		CommerceMLForecastTarget commerceMLForecastTarget =
			CommerceMLForecastTarget.QUANTITY;

		BooleanQuery booleanQuery = getBooleanQuery(
			_commerceMLForecastScope.getLabel(),
			commerceMLForecastPeriod.getLabel(),
			commerceMLForecastTarget.getLabel(),
			getStartDate(actualDate, commerceMLForecastPeriod, historyLength),
			getEndDate(actualDate, commerceMLForecastPeriod, forecastLength));

		BooleanFilter preBooleanFilter = booleanQuery.getPreBooleanFilter();

		preBooleanFilter.add(
			new TermsFilter(CommerceMLForecastField.SKU) {
				{
					addValues(ArrayUtil.toStringArray(sku));
				}
			},
			BooleanClauseOccur.MUST);

		return booleanQuery;
	}

	private static final CommerceMLForecastScope _commerceMLForecastScope =
		CommerceMLForecastScope.SKU;

}