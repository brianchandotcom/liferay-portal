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
import com.liferay.commerce.address.content.web.internal.portlet.action.helper.ActionHelper;
import com.liferay.commerce.address.content.web.internal.portlet.configuration.CommerceAddressContentPortletInstanceConfiguration;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.RegionService;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

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
			CountryService countryService,
			HttpServletRequest httpServletRequest, RegionService regionService)
		throws PortalException {

		_actionHelper = actionHelper;
		_commerceAccountHelper = commerceAccountHelper;
		_commerceAddressService = commerceAddressService;
		_countryService = countryService;
		_httpServletRequest = httpServletRequest;
		_regionService = regionService;

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
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCRenderCommandName(
			"/commerce_address_content/edit_commerce_address"
		).setRedirect(
			() -> {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)_httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				return themeDisplay.getURLCurrent();
			}
		).buildString();
	}

	public CommerceAccount getCommerceAccount() throws PortalException {
		return _commerceAccountHelper.getCurrentCommerceAccount(
			_cpRequestHelper.getCommerceChannelGroupId(), _httpServletRequest);
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

	public List<Country> getCountries() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _countryService.getCompanyCountries(
			themeDisplay.getCompanyId(), true);
	}

	public long getCountryId() throws PortalException {
		long countryId = 0;

		CommerceAddress commerceAddress = getCommerceAddress();

		if (commerceAddress != null) {
			countryId = commerceAddress.getCountryId();
		}

		return countryId;
	}

	public String getDeleteCommerceAddressURL(long commerceAddressId) {
		return PortletURLBuilder.createActionURL(
			_liferayPortletResponse
		).setActionName(
			"/commerce_address_content/edit_commerce_address"
		).setCMD(
			Constants.DELETE
		).setRedirect(
			() -> {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)_httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				return themeDisplay.getURLCurrent();
			}
		).setParameter(
			"commerceAddressId", commerceAddressId
		).buildString();
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
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCRenderCommandName(
			"/commerce_address_content/edit_commerce_address"
		).setRedirect(
			(PortletURL)_liferayPortletResponse.createRenderURL()
		).setParameter(
			"commerceAddressId", commerceAddressId
		).buildString();
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

	public long getRegionId() throws PortalException {
		long regionId = 0;

		CommerceAddress commerceAddress = getCommerceAddress();

		if (commerceAddress != null) {
			regionId = commerceAddress.getRegionId();
		}

		return regionId;
	}

	public List<Region> getRegions() throws PortalException {
		return _regionService.getRegions(getCountryId(), true);
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

		_searchContainer.setResultsAndTotal(
			_commerceAddressService.searchCommerceAddresses(
				commerceAccount.getCompanyId(), CommerceAccount.class.getName(),
				commerceAccount.getCommerceAccountId(), null,
				_searchContainer.getStart(), _searchContainer.getEnd(), null));

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
	private final CountryService _countryService;
	private final CPRequestHelper _cpRequestHelper;
	private long _displayStyleGroupId;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final RegionService _regionService;
	private SearchContainer<CommerceAddress> _searchContainer;

}