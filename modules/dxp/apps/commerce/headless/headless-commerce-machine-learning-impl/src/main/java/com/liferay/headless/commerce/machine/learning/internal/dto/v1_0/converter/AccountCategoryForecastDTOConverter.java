/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	service = DTOConverter.class
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

		return new AccountCategoryForecast() {
			{
				setAccount(
					assetCategoryCommerceMLForecast::getCommerceAccountId);
				setActual(assetCategoryCommerceMLForecast::getActual);
				setCategory(
					assetCategoryCommerceMLForecast::getAssetCategoryId);
				setCategoryTitle(
					() -> {
						AssetCategory assetCategory =
							_assetCategoryLocalService.fetchAssetCategory(
								assetCategoryCommerceMLForecast.
									getAssetCategoryId());

						if (assetCategory == null) {
							return null;
						}

						return assetCategory.getTitle(
							LocaleUtil.toLanguageId(
								dtoConverterContext.getLocale()));
					});
				setForecast(assetCategoryCommerceMLForecast::getForecast);
				setForecastLowerBound(
					assetCategoryCommerceMLForecast::getForecastLowerBound);
				setForecastUpperBound(
					assetCategoryCommerceMLForecast::getForecastUpperBound);
				setTimestamp(assetCategoryCommerceMLForecast::getTimestamp);
				setUnit(assetCategoryCommerceMLForecast::getTarget);
			}
		};
	}

	@Reference
	private AssetCategoryCommerceMLForecastManager
		_assetCategoryCommerceMLForecastManager;

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

}