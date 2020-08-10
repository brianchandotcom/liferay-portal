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
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceCountryService;
import com.liferay.commerce.service.CommerceRegionService;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
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
		ModelResourcePermission<CommerceAccount>
			commerceAccountModelResourcePermission,
		CommerceAccountService commerceAccountService,
		CommerceAddressService commerceAddressService,
		CommerceCountryService commerceCountryService,
		CommerceRegionService commerceRegionService,
		RenderRequest renderRequest) {

		super(
			commerceAccountModelResourcePermission, commerceAccountService,
			renderRequest);

		_commerceAddressService = commerceAddressService;
		_commerceCountryService = commerceCountryService;
		_commerceRegionService = commerceRegionService;
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

	public List<CommerceCountry> getCommerceCountries() {
		return _commerceCountryService.getCommerceCountries(
			commerceAccountAdminRequestHelper.getCompanyId(), true);
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
		LiferayPortletResponse liferayPortletResponse =
			commerceAccountAdminRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createActionURL();

		portletURL.setParameter(Constants.CMD, Constants.DELETE);
		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "editCommerceAddress");
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

		portletURL.setParameter("mvcRenderCommandName", "editCommerceAddress");
		portletURL.setParameter(
			"redirect", commerceAccountAdminRequestHelper.getCurrentURL());
		portletURL.setParameter(
			"commerceAccountId", String.valueOf(getCommerceAccountId()));
		portletURL.setParameter(
			"commerceAddressId", String.valueOf(commerceAddressId));

		return portletURL.toString();
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter("mvcRenderCommandName", "editCommerceAccount");

		return portletURL;
	}

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
	private final CommerceCountryService _commerceCountryService;
	private final CommerceRegionService _commerceRegionService;
	private SearchContainer<CommerceAddress> _searchContainer;

}