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

package com.liferay.headless.commerce.machine.learning.internal.resource.v1_0;

import com.liferay.commerce.machine.learning.forecast.AssetCategoryCommerceMLForecast;
import com.liferay.commerce.machine.learning.forecast.AssetCategoryCommerceMLForecastManager;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.AccountCategoryForecast;
import com.liferay.headless.commerce.machine.learning.internal.constants.CommerceMLForecastConstants;
import com.liferay.headless.commerce.machine.learning.internal.dto.v1_0.converter.AccountCategoryForecastDTOConverter;
import com.liferay.headless.commerce.machine.learning.internal.dto.v1_0.converter.CommerceMLForecastCompositeResourcePrimaryKey;
import com.liferay.headless.commerce.machine.learning.internal.helper.v1_0.CommerceAccountPermissionHelper;
import com.liferay.headless.commerce.machine.learning.resource.v1_0.AccountCategoryForecastResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Riccardo Ferrari
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/account-category-forecast.properties",
	scope = ServiceScope.PROTOTYPE,
	service = AccountCategoryForecastResource.class
)
public class AccountCategoryForecastResourceImpl
	extends BaseAccountCategoryForecastResourceImpl {

	@Override
	public void create(
			Collection<AccountCategoryForecast> accountCategoryForecasts,
			Map<String, Serializable> parameters)
		throws Exception {

		UnsafeConsumer<AccountCategoryForecast, Exception> unsafeConsumer =
			accountCategoryForecast -> {
				AssetCategoryCommerceMLForecast
					assetCategoryCommerceMLForecast =
						_assetCategoryCommerceMLForecastManager.create();

				if (accountCategoryForecast.getActual() != null) {
					assetCategoryCommerceMLForecast.setActual(
						accountCategoryForecast.getActual());
				}

				assetCategoryCommerceMLForecast.setAssetCategoryId(
					accountCategoryForecast.getCategory());
				assetCategoryCommerceMLForecast.setCommerceAccountId(
					accountCategoryForecast.getAccount());
				assetCategoryCommerceMLForecast.setCompanyId(
					contextCompany.getCompanyId());
				assetCategoryCommerceMLForecast.setForecast(
					accountCategoryForecast.getForecast());
				assetCategoryCommerceMLForecast.setForecastLowerBound(
					accountCategoryForecast.getForecastLowerBound());
				assetCategoryCommerceMLForecast.setForecastUpperBound(
					accountCategoryForecast.getForecastUpperBound());
				assetCategoryCommerceMLForecast.setPeriod("month");
				assetCategoryCommerceMLForecast.setScope("asset-category");
				assetCategoryCommerceMLForecast.setTarget("revenue");
				assetCategoryCommerceMLForecast.setTimestamp(
					accountCategoryForecast.getTimestamp());

				_assetCategoryCommerceMLForecastManager.
					addAssetCategoryCommerceMLForecast(
						assetCategoryCommerceMLForecast);
			};

		contextBatchStrategy.apply(accountCategoryForecasts, unsafeConsumer);
	}

	@Override
	public Page<AccountCategoryForecast>
			getAccountCategoryForecastsByMonthlyRevenuePage(
				Long[] accountIds, Long[] categoryIds, Integer forecastLength,
				Date forecastStartDate, Integer historyLength,
				Pagination pagination)
		throws Exception {

		List<Long> commerceAccountIds =
			_commerceAccountPermissionHelper.filterCommerceAccountIds(
				Arrays.asList(accountIds));

		if (commerceAccountIds.isEmpty()) {
			return Page.of(Collections.emptyList());
		}

		Date startDate = forecastStartDate;

		if (startDate == null) {
			startDate = new Date();
		}

		if (historyLength == null) {
			historyLength = CommerceMLForecastConstants.HISTORY_LENGTH_DEFAULT;
		}

		if (forecastLength == null) {
			forecastLength =
				CommerceMLForecastConstants.FORECAST_LENGTH_DEFAULT;
		}

		return Page.of(
			transform(
				_assetCategoryCommerceMLForecastManager.
					getMonthlyRevenueAssetCategoryCommerceMLForecasts(
						contextCompany.getCompanyId(),
						ArrayUtil.toArray(categoryIds),
						ArrayUtil.toLongArray(commerceAccountIds), startDate,
						historyLength, forecastLength,
						pagination.getStartPosition(),
						pagination.getEndPosition()),
				assetCategoryCommerceMLForecast ->
					_accountCategoryForecastDTOConverter.toDTO(
						new DefaultDTOConverterContext(
							new CommerceMLForecastCompositeResourcePrimaryKey(
								assetCategoryCommerceMLForecast.getCompanyId(),
								assetCategoryCommerceMLForecast.
									getForecastId()),
							contextAcceptLanguage.getPreferredLocale()))),
			pagination,
			_assetCategoryCommerceMLForecastManager.
				getMonthlyRevenueAssetCategoryCommerceMLForecastsCount(
					contextCompany.getCompanyId(),
					ArrayUtil.toArray(categoryIds),
					ArrayUtil.toLongArray(commerceAccountIds), startDate,
					historyLength, forecastLength));
	}

	@Reference
	private AccountCategoryForecastDTOConverter
		_accountCategoryForecastDTOConverter;

	@Reference
	private AssetCategoryCommerceMLForecastManager
		_assetCategoryCommerceMLForecastManager;

	@Reference
	private CommerceAccountPermissionHelper _commerceAccountPermissionHelper;

}