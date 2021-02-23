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

package com.liferay.commerce.account.admin.web.internal.display.context;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.RegionService;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceAccountAddressAdminDisplayContext
	extends BaseCommerceAccountAdminDisplayContext<CommerceAddress> {

	public CommerceAccountAddressAdminDisplayContext(
		CommerceAccountService commerceAccountService,
		CommerceAddressService commerceAddressService,
		CountryService countryService,
		ModelResourcePermission<CommerceAccount> modelResourcePermission,
		RegionService regionService, RenderRequest renderRequest) {

		super(commerceAccountService, modelResourcePermission, renderRequest);

		_commerceAddressService = commerceAddressService;
		_countryService = countryService;
		_regionService = regionService;
	}

	public CommerceAddress getCommerceAddress() throws PortalException {
		HttpServletRequest httpServletRequest =
			commerceAccountAdminRequestHelper.getRequest();

		CommerceAddress commerceAddress =
			(CommerceAddress)httpServletRequest.getAttribute(
				CommerceWebKeys.COMMERCE_ADDRESS);

		if (commerceAddress != null) {
			return commerceAddress;
		}

		long commerceAddressId = ParamUtil.getLong(
			httpServletRequest, "commerceAddressId");

		if (commerceAddressId > 0) {
			commerceAddress = _commerceAddressService.fetchCommerceAddress(
				commerceAddressId);
		}

		if (commerceAddress != null) {
			httpServletRequest.setAttribute(
				CommerceWebKeys.COMMERCE_ADDRESS, commerceAddress);
		}

		return commerceAddress;
	}

	public List<CommerceAddress> getCommerceAddresses() throws PortalException {
		CommerceAccount commerceAccount = getCommerceAccount();

		if (commerceAccount == null) {
			return null;
		}

		return _commerceAddressService.getCommerceAddressesByCompanyId(
			commerceAccount.getCompanyId(), CommerceAccount.class.getName(),
			commerceAccount.getCommerceAccountId());
	}

	public long getCommerceAddressId() throws PortalException {
		CommerceAddress commerceAddress = getCommerceAddress();

		if (commerceAddress == null) {
			return 0;
		}

		return commerceAddress.getCommerceAddressId();
	}

	public List<Country> getCountries() {
		return _countryService.getCompanyCountries(
			commerceAccountAdminRequestHelper.getCompanyId(), true);
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
		LiferayPortletResponse liferayPortletResponse =
			commerceAccountAdminRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createActionURL();

		portletURL.setParameter(Constants.CMD, Constants.DELETE);
		portletURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/commerce_account_admin/edit_commerce_address");
		portletURL.setParameter(
			"redirect", commerceAccountAdminRequestHelper.getCurrentURL());
		portletURL.setParameter(
			"commerceAddressId", String.valueOf(commerceAddressId));

		return portletURL.toString();
	}

	public String getEditCommerceAddressURL(long commerceAddressId)
		throws PortalException {

		LiferayPortletResponse liferayPortletResponse =
			commerceAccountAdminRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName",
			"/commerce_account_admin/edit_commerce_address");
		portletURL.setParameter(
			"redirect", commerceAccountAdminRequestHelper.getCurrentURL());
		portletURL.setParameter(
			"commerceAccountId", String.valueOf(getCommerceAccountId()));
		portletURL.setParameter(
			"commerceAddressId", String.valueOf(commerceAddressId));

		return portletURL.toString();
	}

	@Override
	public PortletURL getPortletURL() {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter(
			"mvcRenderCommandName",
			"/commerce_account_admin/edit_commerce_account");

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

	@Override
	public SearchContainer<CommerceAddress> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			commerceAccountAdminRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), null, "there-are-no-addresses");

		setOrderByColAndType(
			CommerceAddress.class, _searchContainer, "create-date", "desc");

		OrderByComparator<CommerceAddress> orderByComparator =
			CommerceUtil.getCommerceAddressOrderByComparator(
				"create-date", "desc");

		_searchContainer.setOrderByComparator(orderByComparator);

		_searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(
				commerceAccountAdminRequestHelper.getLiferayPortletResponse()));

		BaseModelSearchResult<CommerceAddress> baseModelSearchResult =
			_commerceAddressService.searchCommerceAddresses(
				commerceAccount.getCompanyId(), CommerceAccount.class.getName(),
				commerceAccount.getCommerceAccountId(), null,
				_searchContainer.getStart(), _searchContainer.getEnd(), null);

		_searchContainer.setTotal(baseModelSearchResult.getLength());
		_searchContainer.setResults(baseModelSearchResult.getBaseModels());

		return _searchContainer;
	}

	private final CommerceAddressService _commerceAddressService;
	private final CountryService _countryService;
	private final RegionService _regionService;
	private SearchContainer<CommerceAddress> _searchContainer;

}