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

package com.liferay.commerce.currency.web.internal.display.context;

import com.liferay.commerce.currency.configuration.CommerceCurrencyConfiguration;
import com.liferay.commerce.currency.configuration.RoundingTypeConfiguration;
import com.liferay.commerce.currency.constants.CommerceCurrencyActionKeys;
import com.liferay.commerce.currency.constants.CommerceCurrencyConstants;
import com.liferay.commerce.currency.constants.CommerceCurrencyExchangeRateConstants;
import com.liferay.commerce.currency.constants.CommerceCurrencyPortletKeys;
import com.liferay.commerce.currency.constants.RoundingTypeConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.currency.util.ExchangeRateProviderRegistry;
import com.liferay.commerce.currency.web.internal.util.CommerceCurrencyUtil;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.SystemSettingsLocator;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Andrea Di Giorgi
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceCurrenciesDisplayContext {

	public CommerceCurrenciesDisplayContext(
		CommerceCurrencyService commerceCurrencyService,
		CommercePriceFormatter commercePriceFormatter,
		ConfigurationProvider configurationProvider,
		ExchangeRateProviderRegistry exchangeRateProviderRegistry,
		PortletResourcePermission portletResourcePermission,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_commerceCurrencyService = commerceCurrencyService;
		_commercePriceFormatter = commercePriceFormatter;
		_configurationProvider = configurationProvider;
		_exchangeRateProviderRegistry = exchangeRateProviderRegistry;
		_portletResourcePermission = portletResourcePermission;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public String format(BigDecimal rate) throws PortalException {
		return _commercePriceFormatter.format(
			rate, PortalUtil.getLocale(_renderRequest));
	}

	public CommerceCurrency getCommerceCurrency() throws PortalException {
		if (_commerceCurrency != null) {
			return _commerceCurrency;
		}

		long commerceCurrencyId = ParamUtil.getLong(
			_renderRequest, "commerceCurrencyId");

		if (commerceCurrencyId > 0) {
			_commerceCurrency = _commerceCurrencyService.getCommerceCurrency(
				commerceCurrencyId);
		}

		return _commerceCurrency;
	}

	public CommerceCurrencyConfiguration getCommerceCurrencyConfiguration()
		throws ConfigurationException {

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return _configurationProvider.getConfiguration(
			CommerceCurrencyConfiguration.class,
			new CompanyServiceSettingsLocator(
				themeDisplay.getCompanyId(),
				CommerceCurrencyExchangeRateConstants.SERVICE_NAME));
	}

	public String getDefaultFormatPattern() throws ConfigurationException {
		return CommerceCurrencyConstants.DECIMAL_FORMAT_PATTERN;
	}

	public int getDefaultMaxFractionDigits() throws ConfigurationException {
		RoundingTypeConfiguration roundingTypeConfiguration =
			_getRoundingTypeConfiguration();

		return roundingTypeConfiguration.maximumFractionDigits();
	}

	public int getDefaultMinFractionDigits() throws ConfigurationException {
		RoundingTypeConfiguration roundingTypeConfiguration =
			_getRoundingTypeConfiguration();

		return roundingTypeConfiguration.minimumFractionDigits();
	}

	public String getDefaultRoundingMode() throws ConfigurationException {
		RoundingTypeConfiguration roundingTypeConfiguration =
			_getRoundingTypeConfiguration();

		RoundingMode roundingMode = roundingTypeConfiguration.roundingMode();

		return roundingMode.name();
	}

	public Iterable<String> getExchangeRateProviderKeys() {
		return _exchangeRateProviderRegistry.getExchangeRateProviderKeys();
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			_renderRequest, CommerceCurrencyPortletKeys.COMMERCE_CURRENCY,
			"priority");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			_renderRequest, CommerceCurrencyPortletKeys.COMMERCE_CURRENCY,
			"asc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setNavigation(
			_getNavigation()
		).setParameter(
			"orderByCol", getOrderByCol()
		).setParameter(
			"orderByType", getOrderByType()
		).buildPortletURL();
	}

	public CommerceCurrency getPrimaryCommerceCurrency()
		throws PortalException {

		if (_primaryCommerceCurrency != null) {
			return _primaryCommerceCurrency;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_primaryCommerceCurrency =
			_commerceCurrencyService.fetchPrimaryCommerceCurrency(
				themeDisplay.getCompanyId());

		return _primaryCommerceCurrency;
	}

	public String getRoundingModeLabel(String roundingModeName) {
		return StringUtil.replace(
			roundingModeName, CharPool.UNDERLINE, CharPool.SPACE);
	}

	public SearchContainer<CommerceCurrency> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Boolean active = null;
		String emptyResultsMessage = "there-are-no-currencies";

		String navigation = _getNavigation();

		if (navigation.equals("active")) {
			active = Boolean.TRUE;
			emptyResultsMessage = "there-are-no-active-currencies";
		}
		else if (navigation.equals("inactive")) {
			active = Boolean.FALSE;
			emptyResultsMessage = "there-are-no-inactive-currencies";
		}

		_searchContainer = new SearchContainer<>(
			_renderRequest, getPortletURL(), null, emptyResultsMessage);

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<CommerceCurrency> orderByComparator =
			CommerceCurrencyUtil.getCommerceCurrencyOrderByComparator(
				orderByCol, orderByType);

		_searchContainer.setOrderByCol(orderByCol);
		_searchContainer.setOrderByComparator(orderByComparator);
		_searchContainer.setOrderByType(orderByType);
		_searchContainer.setRowChecker(_getRowChecker());

		if (active != null) {
			boolean navigationActive = active;

			_searchContainer.setResultsAndTotal(
				() -> _commerceCurrencyService.getCommerceCurrencies(
					themeDisplay.getCompanyId(), navigationActive,
					_searchContainer.getStart(), _searchContainer.getEnd(),
					orderByComparator),
				_commerceCurrencyService.getCommerceCurrenciesCount(
					themeDisplay.getCompanyId(), navigationActive));
		}
		else {
			_searchContainer.setResultsAndTotal(
				() -> _commerceCurrencyService.getCommerceCurrencies(
					themeDisplay.getCompanyId(), _searchContainer.getStart(),
					_searchContainer.getEnd(), orderByComparator),
				_commerceCurrencyService.getCommerceCurrenciesCount(
					themeDisplay.getCompanyId()));
		}

		return _searchContainer;
	}

	public boolean hasManageCommerceCurrencyPermission() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return _portletResourcePermission.contains(
			themeDisplay.getPermissionChecker(), null,
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);
	}

	private String _getNavigation() {
		return ParamUtil.getString(_renderRequest, "navigation");
	}

	private RoundingTypeConfiguration _getRoundingTypeConfiguration()
		throws ConfigurationException {

		return _configurationProvider.getConfiguration(
			RoundingTypeConfiguration.class,
			new SystemSettingsLocator(RoundingTypeConstants.SERVICE_NAME));
	}

	private RowChecker _getRowChecker() {
		if (_rowChecker == null) {
			_rowChecker = new EmptyOnClickRowChecker(_renderResponse);
		}

		return _rowChecker;
	}

	private CommerceCurrency _commerceCurrency;
	private final CommerceCurrencyService _commerceCurrencyService;
	private final CommercePriceFormatter _commercePriceFormatter;
	private final ConfigurationProvider _configurationProvider;
	private final ExchangeRateProviderRegistry _exchangeRateProviderRegistry;
	private String _orderByCol;
	private String _orderByType;
	private final PortletResourcePermission _portletResourcePermission;
	private CommerceCurrency _primaryCommerceCurrency;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private RowChecker _rowChecker;
	private SearchContainer<CommerceCurrency> _searchContainer;

}