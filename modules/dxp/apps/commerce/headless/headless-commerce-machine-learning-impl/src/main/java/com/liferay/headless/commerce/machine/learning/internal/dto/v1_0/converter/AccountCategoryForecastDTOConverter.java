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

package com.liferay.headless.commerce.machine.learning.internal.dto.v1_0.converter;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.commerce.machine.learning.forecast.AssetCategoryCommerceMLForecast;
import com.liferay.commerce.machine.learning.forecast.AssetCategoryCommerceMLForecastManager;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.AccountCategoryForecast;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	property = "model.class.name=com.liferay.commerce.machine.learning.forecast.model.AssetCategoryCommerceMLForecast",
	service = {AccountCategoryForecastDTOConverter.class, DTOConverter.class}
)
public class AccountCategoryForecastDTOConverter
	implements DTOConverter
		<AssetCategoryCommerceMLForecast, AccountCategoryForecast> {

	@Override
	public String getContentType() {
		return AssetCategoryCommerceMLForecast.class.getSimpleName();
	}

	@Override
	public AccountCategoryForecast toDTO(
			DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceMLForecastCompositeResourcePrimaryKey compositeResourcePrimKey =
			(CommerceMLForecastCompositeResourcePrimaryKey)
				dtoConverterContext.getId();

		AssetCategoryCommerceMLForecast assetCategoryCommerceMLForecast =
			_assetCategoryCommerceMLForecastManager.
				getAssetCategoryCommerceMLForecast(
					compositeResourcePrimKey.getCompanyId(),
					compositeResourcePrimKey.getForecastId());

		AssetCategory assetCategory =
			_assetCategoryLocalService.fetchAssetCategory(
				assetCategoryCommerceMLForecast.getAssetCategoryId());

		return new AccountCategoryForecast() {
			{
				account =
					assetCategoryCommerceMLForecast.getCommerceAccountId();
				actual = assetCategoryCommerceMLForecast.getActual();
				category = assetCategoryCommerceMLForecast.getAssetCategoryId();

				if (assetCategory != null) {
					categoryTitle = assetCategory.getTitle(
						LocaleUtil.toLanguageId(
							dtoConverterContext.getLocale()));
				}

				forecast = assetCategoryCommerceMLForecast.getForecast();
				forecastLowerBound =
					assetCategoryCommerceMLForecast.getForecastLowerBound();
				forecastUpperBound =
					assetCategoryCommerceMLForecast.getForecastUpperBound();
				timestamp = assetCategoryCommerceMLForecast.getTimestamp();
				unit = assetCategoryCommerceMLForecast.getTarget();
			}
		};
	}

	@Reference
	private AssetCategoryCommerceMLForecastManager
		_assetCategoryCommerceMLForecastManager;

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

}