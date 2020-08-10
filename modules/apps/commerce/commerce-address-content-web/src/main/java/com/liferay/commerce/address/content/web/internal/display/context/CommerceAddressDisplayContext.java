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

package com.liferay.commerce.address.content.web.internal.display.context;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.address.content.web.internal.portlet.action.ActionHelper;
import com.liferay.commerce.address.content.web.internal.portlet.configuration.CommerceAddressContentPortletInstanceConfiguration;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.product.display.context.util.CPRequestHelper;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceCountryService;
import com.liferay.commerce.service.CommerceRegionService;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceAddressDisplayContext {

	public CommerceAddressDisplayContext(
			ActionHelper actionHelper,
			CommerceAccountHelper commerceAccountHelper,
			CommerceAddressService commerceAddressService,
			CommerceCountryService commerceCountryService,
			CommerceRegionService commerceRegionService,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		_actionHelper = actionHelper;
		_commerceAccountHelper = commerceAccountHelper;
		_commerceAddressService = commerceAddressService;
		_commerceCountryService = commerceCountryService;
		_commerceRegionService = commerceRegionService;
		_httpServletRequest = httpServletRequest;

		_cpRequestHelper = new CPRequestHelper(httpServletRequest);

		_liferayPortletRequest = _cpRequestHelper.getLiferayPortletRequest();
		_liferayPortletResponse = _cpRequestHelper.getLiferayPortletResponse();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_commerceAddressContentPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				CommerceAddressContentPortletInstanceConfiguration.class);
	}

	public String getAddCommerceAddressURL() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "editCommerceAddress");
		portletURL.setParameter("redirect", themeDisplay.getURLCurrent());

		return portletURL.toString();
	}

	public CommerceAccount getCommerceAccount() throws PortalException {
		return _commerceAccountHelper.getCurrentCommerceAccount(
			_cpRequestHelper.getChannelGroupId(), _httpServletRequest);
	}

	public CommerceAddress getCommerceAddress() throws PortalException {
		if (_commerceAddress != null) {
			return _commerceAddress;
		}

		_commerceAddress = _actionHelper.getCommerceAddress(
			_cpRequestHelper.getRenderRequest());

		return _commerceAddress;
	}

	public long getCommerceAddressId() throws PortalException {
		CommerceAddress commerceAddress = getCommerceAddress();

		if (commerceAddress == null) {
			return 0;
		}

		return commerceAddress.getCommerceAddressId();
	}

	public List<CommerceCountry> getCommerceCountries() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _commerceCountryService.getCommerceCountries(
			themeDisplay.getCompanyId(), true);
	}

	public long getCommerceCountryId() throws PortalException {
		long commerceCountryId = 0;

		CommerceAddress commerceAddress = getCommerceAddress();

		if (commerceAddress != null) {
			commerceCountryId = commerceAddress.getCommerceCountryId();
		}

		return commerceCountryId;
	}

	public long getCommerceRegionId() throws PortalException {
		long commerceRegionId = 0;

		CommerceAddress commerceAddress = getCommerceAddress();

		if (commerceAddress != null) {
			commerceRegionId = commerceAddress.getCommerceRegionId();
		}

		return commerceRegionId;
	}

	public List<CommerceRegion> getCommerceRegions() throws PortalException {
		return _commerceRegionService.getCommerceRegions(
			getCommerceCountryId(), true);
	}

	public String getDeleteCommerceAddressURL(long commerceAddressId) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletURL portletURL = _liferayPortletResponse.createActionURL();

		portletURL.setParameter(Constants.CMD, Constants.DELETE);
		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "editCommerceAddress");
		portletURL.setParameter("redirect", themeDisplay.getURLCurrent());
		portletURL.setParameter(
			"commerceAddressId", String.valueOf(commerceAddressId));

		return portletURL.toString();
	}

	public String getDisplayStyle() {
		return _commerceAddressContentPortletInstanceConfiguration.
			displayStyle();
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId > 0) {
			return _displayStyleGroupId;
		}

		_displayStyleGroupId =
			_commerceAddressContentPortletInstanceConfiguration.
				displayStyleGroupId();

		if (_displayStyleGroupId <= 0) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			_displayStyleGroupId = themeDisplay.getScopeGroupId();
		}

		return _displayStyleGroupId;
	}

	public String getEditCommerceAddressURL(long commerceAddressId) {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "editCommerceAddress");
		portletURL.setParameter(
			"redirect",
			String.valueOf(_liferayPortletResponse.createRenderURL()));
		portletURL.setParameter(
			"commerceAddressId", String.valueOf(commerceAddressId));

		return portletURL.toString();
	}

	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		String redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		CommerceAddress commerceAddress = getCommerceAddress();

		if (commerceAddress != null) {
			portletURL.setParameter(
				"commerceAddressId", String.valueOf(getCommerceAddressId()));
		}

		String delta = ParamUtil.getString(_httpServletRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String deltaEntry = ParamUtil.getString(
			_httpServletRequest, "deltaEntry");

		if (Validator.isNotNull(deltaEntry)) {
			portletURL.setParameter("deltaEntry", deltaEntry);
		}

		return portletURL;
	}

	public SearchContainer<CommerceAddress> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			_liferayPortletRequest, getPortletURL(), null, null);

		_searchContainer.setEmptyResultsMessage("there-are-no-addresses");

		OrderByComparator<CommerceAddress> orderByComparator =
			CommerceUtil.getCommerceAddressOrderByComparator(
				"create-date", "desc");

		_searchContainer.setOrderByCol("create-date");
		_searchContainer.setOrderByComparator(orderByComparator);
		_searchContainer.setOrderByType("desc");

		CommerceAccount commerceAccount = getCommerceAccount();

		BaseModelSearchResult<CommerceAddress> baseModelSearchResult =
			_commerceAddressService.searchCommerceAddresses(
				commerceAccount.getCompanyId(), CommerceAccount.class.getName(),
				commerceAccount.getCommerceAccountId(), null,
				_searchContainer.getStart(), _searchContainer.getEnd(), null);

		_searchContainer.setTotal(baseModelSearchResult.getLength());
		_searchContainer.setResults(baseModelSearchResult.getBaseModels());

		return _searchContainer;
	}

	public boolean hasCommerceChannel() throws PortalException {
		CommerceContext commerceContext =
			(CommerceContext)_httpServletRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		long commerceChannelId = commerceContext.getCommerceChannelId();

		if (commerceChannelId > 0) {
			return true;
		}

		return false;
	}

	private final ActionHelper _actionHelper;
	private final CommerceAccountHelper _commerceAccountHelper;
	private CommerceAddress _commerceAddress;
	private final CommerceAddressContentPortletInstanceConfiguration
		_commerceAddressContentPortletInstanceConfiguration;
	private final CommerceAddressService _commerceAddressService;
	private final CommerceCountryService _commerceCountryService;
	private final CommerceRegionService _commerceRegionService;
	private final CPRequestHelper _cpRequestHelper;
	private long _displayStyleGroupId;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private SearchContainer<CommerceAddress> _searchContainer;

}