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

package com.liferay.commerce.tax.engine.fixed.web.internal.frontend;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.model.CommerceMoneyFactory;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.percentage.PercentageFormatter;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel;
import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxFixedRateAddressRelService;
import com.liferay.commerce.tax.engine.fixed.web.internal.frontend.constants.CommerceTaxRateSettingDataSetConstants;
import com.liferay.commerce.tax.engine.fixed.web.internal.model.TaxRateSetting;
import com.liferay.commerce.tax.model.CommerceTaxMethod;
import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.provider.key=" + CommerceTaxRateSettingDataSetConstants.COMMERCE_DATA_SET_KEY_TAX_RATE_SETTING,
	service = ClayDataSetDataProvider.class
)
public class CommerceTaxRateSettingDataSetDataProvider
	implements ClayDataSetDataProvider<TaxRateSetting> {

	@Override
	public List<TaxRateSetting> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		List<TaxRateSetting> taxRateSettings = new ArrayList<>();

		long commerceChannelId = ParamUtil.getLong(
			httpServletRequest, "commerceChannelId");

		CommerceChannel commerceChannel =
			_commerceChannelService.getCommerceChannel(commerceChannelId);

		long commerceTaxMethodId = ParamUtil.getLong(
			httpServletRequest, "commerceTaxMethodId");

		List<CommerceTaxFixedRateAddressRel> commerceTaxFixedRateAddressRels =
			_commerceTaxFixedRateAddressRelService.
				getCommerceTaxMethodFixedRateAddressRels(
					commerceChannel.getGroupId(), commerceTaxMethodId,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null);

		CommerceCurrency commerceCurrency =
			_commerceCurrencyLocalService.getCommerceCurrency(
				commerceChannel.getCompanyId(),
				commerceChannel.getCommerceCurrencyCode());

		for (CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel :
				commerceTaxFixedRateAddressRels) {

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			CPTaxCategory cpTaxCategory =
				commerceTaxFixedRateAddressRel.getCPTaxCategory();

			taxRateSettings.add(
				new TaxRateSetting(
					_getCountry(
						commerceTaxFixedRateAddressRel.getCountry(),
						themeDisplay.getLanguageId()),
					_getLocalizedRate(
						commerceCurrency, commerceTaxFixedRateAddressRel,
						themeDisplay.getLocale()),
					_getRegion(commerceTaxFixedRateAddressRel.getRegion()),
					cpTaxCategory.getName(themeDisplay.getLanguageId()),
					commerceTaxFixedRateAddressRel.
						getCommerceTaxFixedRateAddressRelId(),
					_getZip(commerceTaxFixedRateAddressRel.getZip())));
		}

		return taxRateSettings;
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		long commerceChannelId = ParamUtil.getLong(
			httpServletRequest, "commerceChannelId");

		CommerceChannel commerceChannel =
			_commerceChannelService.getCommerceChannel(commerceChannelId);

		long commerceTaxMethodId = ParamUtil.getLong(
			httpServletRequest, "commerceTaxMethodId");

		return _commerceTaxFixedRateAddressRelService.
			getCommerceTaxMethodFixedRateAddressRelsCount(
				commerceChannel.getGroupId(), commerceTaxMethodId);
	}

	private String _getCountry(Country country, String languageId) {
		if (country == null) {
			return StringPool.STAR;
		}

		return country.getTitle(languageId);
	}

	private String _getLocalizedRate(
			CommerceCurrency commerceCurrency,
			CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel,
			Locale locale)
		throws PortalException {

		BigDecimal bigDecimalPercentage = new BigDecimal(
			commerceTaxFixedRateAddressRel.getRate());

		CommerceTaxMethod commerceTaxMethod =
			commerceTaxFixedRateAddressRel.getCommerceTaxMethod();

		if (commerceTaxMethod.isPercentage()) {
			return _percentageFormatter.getLocalizedPercentage(
				locale, commerceCurrency.getMaxFractionDigits(),
				commerceCurrency.getMinFractionDigits(), bigDecimalPercentage);
		}

		CommerceMoney commerceMoney = _commerceMoneyFactory.create(
			commerceCurrency, bigDecimalPercentage);

		return commerceMoney.format(locale);
	}

	private String _getRegion(Region region) {
		if (region == null) {
			return StringPool.STAR;
		}

		return region.getName();
	}

	private String _getZip(String zip) {
		if (Validator.isNull(zip)) {
			return StringPool.STAR;
		}

		return zip;
	}

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Reference
	private CommerceMoneyFactory _commerceMoneyFactory;

	@Reference
	private CommerceTaxFixedRateAddressRelService
		_commerceTaxFixedRateAddressRelService;

	@Reference
	private PercentageFormatter _percentageFormatter;

}