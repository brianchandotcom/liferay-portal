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

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.currency.util.ExchangeRateProvider;
import com.liferay.commerce.currency.util.ExchangeRateProviderRegistry;
import com.liferay.commerce.currency.util.RoundingType;
import com.liferay.commerce.currency.util.RoundingTypeServicesTracker;
import com.liferay.commerce.currency.web.internal.configuration.ExchangeRateProviderGroupServiceConfiguration;
import com.liferay.commerce.currency.web.internal.constants.CommerceCurrencyExchangeRateConstants;
import com.liferay.commerce.currency.web.internal.util.CommerceCurrencyUtil;
import com.liferay.commerce.currency.web.internal.util.CurrenciesCommerceAdminModule;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Map;

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
		ConfigurationProvider configurationProvider,
		ExchangeRateProviderRegistry exchangeRateProviderRegistry,
		RoundingTypeServicesTracker roundingTypeServicesTracker,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_commerceCurrencyService = commerceCurrencyService;
		_configurationProvider = configurationProvider;
		_exchangeRateProviderRegistry = exchangeRateProviderRegistry;
		_roundingTypeServicesTracker = roundingTypeServicesTracker;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
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

	public ExchangeRateProviderGroupServiceConfiguration
			getExchangeRateProviderGroupServiceConfiguration()
		throws ConfigurationException {

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return _configurationProvider.getConfiguration(
			ExchangeRateProviderGroupServiceConfiguration.class,
			new GroupServiceSettingsLocator(
				themeDisplay.getScopeGroupId(),
				CommerceCurrencyExchangeRateConstants.SERVICE_NAME));
	}

	public Map<String, ExchangeRateProvider> getExchangeRateProviders() {
		return _exchangeRateProviderRegistry.getExchangeRateProviderMap();
	}

	public String getOrderByCol() {
		return ParamUtil.getString(
			_renderRequest, SearchContainer.DEFAULT_ORDER_BY_COL_PARAM,
			"priority");
	}

	public String getOrderByType() {
		return ParamUtil.getString(
			_renderRequest, SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM, "asc");
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter(
			"commerceAdminModuleKey", CurrenciesCommerceAdminModule.KEY);
		portletURL.setParameter("navigation", getNavigation());
		portletURL.setParameter("orderByCol", getOrderByCol());
		portletURL.setParameter("orderByType", getOrderByType());

		return portletURL;
	}

	public CommerceCurrency getPrimaryCommerceCurrency() {
		if (_primaryCommerceCurrency != null) {
			return _primaryCommerceCurrency;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_primaryCommerceCurrency =
			_commerceCurrencyService.fetchPrimaryCommerceCurrency(
				themeDisplay.getScopeGroupId());

		return _primaryCommerceCurrency;
	}

	public List<RoundingType> getRoundingTypes() {
		return _roundingTypeServicesTracker.getRoundingTypes();
	}

	public SearchContainer<CommerceCurrency> getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Boolean active = null;
		String emptyResultsMessage = "there-are-no-currencies";

		String navigation = getNavigation();

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
		_searchContainer.setRowChecker(getRowChecker());

		int total;
		List<CommerceCurrency> results;

		if (active != null) {
			total = _commerceCurrencyService.getCommerceCurrenciesCount(
				themeDisplay.getScopeGroupId(), active);
			results = _commerceCurrencyService.getCommerceCurrencies(
				themeDisplay.getScopeGroupId(), active,
				_searchContainer.getStart(), _searchContainer.getEnd(),
				orderByComparator);
		}
		else {
			total = _commerceCurrencyService.getCommerceCurrenciesCount(
				themeDisplay.getScopeGroupId());
			results = _commerceCurrencyService.getCommerceCurrencies(
				themeDisplay.getScopeGroupId(), _searchContainer.getStart(),
				_searchContainer.getEnd(), orderByComparator);
		}

		_searchContainer.setTotal(total);
		_searchContainer.setResults(results);

		return _searchContainer;
	}

	protected String getNavigation() {
		return ParamUtil.getString(_renderRequest, "navigation");
	}

	protected RowChecker getRowChecker() {
		if (_rowChecker == null) {
			_rowChecker = new EmptyOnClickRowChecker(_renderResponse);
		}

		return _rowChecker;
	}

	private CommerceCurrency _commerceCurrency;
	private final CommerceCurrencyService _commerceCurrencyService;
	private final ConfigurationProvider _configurationProvider;
	private final ExchangeRateProviderRegistry _exchangeRateProviderRegistry;
	private CommerceCurrency _primaryCommerceCurrency;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final RoundingTypeServicesTracker _roundingTypeServicesTracker;
	private RowChecker _rowChecker;
	private SearchContainer<CommerceCurrency> _searchContainer;

}