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

package com.liferay.commerce.shipping.web.internal.frontend;

import com.liferay.commerce.frontend.model.RestrictionField;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.service.CommerceAddressRestrictionLocalService;
import com.liferay.commerce.service.CommerceShippingMethodService;
import com.liferay.commerce.shipping.web.internal.model.ShippingRestriction;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.frontend.taglib.clay.data.set.view.table.selectable.BaseSelectableTableClayDataSetDisplayView;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanProperties;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"clay.data.provider.key=" + CommerceShippingRestrictionsPageClayTable.NAME,
		"clay.data.set.display.name=" + CommerceShippingRestrictionsPageClayTable.NAME
	},
	service = {ClayDataSetDataProvider.class, ClayDataSetDisplayView.class}
)
public class CommerceShippingRestrictionsPageClayTable
	extends BaseSelectableTableClayDataSetDisplayView
	implements ClayDataSetDataProvider<ShippingRestriction> {

	public static final String NAME = "shipping-restrictions";

	@Override
	public String getFirstColumnLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "country");
	}

	@Override
	public String getFirstColumnName() {
		return "countryName";
	}

	@Override
	public List<ShippingRestriction> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<ShippingRestriction> shippingRestrictions = new ArrayList<>();

		long commerceChannelId = ParamUtil.getLong(
			httpServletRequest, "commerceChannelId");

		CommerceChannel commerceChannel =
			_commerceChannelService.getCommerceChannel(commerceChannelId);

		List<CommerceShippingMethod> commerceShippingMethods =
			_commerceShippingMethodService.getCommerceShippingMethods(
				commerceChannel.getGroupId());

		String orderByFieldName = _beanProperties.getString(
			sort, "fieldName", StringPool.BLANK);

		String orderByType = "asc";

		boolean reverse = _beanProperties.getBooleanSilent(
			sort, "reverse", false);

		if (reverse) {
			orderByType = "desc";
		}

		BaseModelSearchResult<Country> baseModelSearchResult =
			_countryService.searchCountries(
				_portal.getCompanyId(httpServletRequest), true,
				filter.getKeywords(), pagination.getStartPosition(),
				pagination.getEndPosition(),
				CommerceUtil.getCountryOrderByComparator(
					orderByFieldName, orderByType));

		for (Country country : baseModelSearchResult.getBaseModels()) {
			shippingRestrictions.add(
				new ShippingRestriction(
					country.getCountryId(),
					country.getTitle(themeDisplay.getLocale()),
					_getFields(
						country.getCountryId(), commerceShippingMethods,
						LocaleUtil.toLanguageId(themeDisplay.getLocale()))));
		}

		return shippingRestrictions;
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		BaseModelSearchResult<Country> baseModelSearchResult =
			_countryService.searchCountries(
				_portal.getCompanyId(httpServletRequest), true,
				filter.getKeywords(), 0, 0, null);

		return baseModelSearchResult.getLength();
	}

	private List<RestrictionField> _getFields(
		long countryId, List<CommerceShippingMethod> commerceShippingMethods,
		String languageId) {

		List<RestrictionField> restrictionFields = new ArrayList<>();

		for (CommerceShippingMethod commerceShippingMethod :
				commerceShippingMethods) {

			restrictionFields.add(
				new RestrictionField(
					commerceShippingMethod.getName(languageId),
					String.valueOf(
						commerceShippingMethod.getCommerceShippingMethodId()),
					_commerceAddressRestrictionLocalService.
						isCommerceShippingMethodRestricted(
							commerceShippingMethod.
								getCommerceShippingMethodId(),
							countryId)));
		}

		return restrictionFields;
	}

	@Reference
	private BeanProperties _beanProperties;

	@Reference
	private CommerceAddressRestrictionLocalService
		_commerceAddressRestrictionLocalService;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommerceShippingMethodService _commerceShippingMethodService;

	@Reference
	private CountryService _countryService;

	@Reference
	private Portal _portal;

}