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

package com.liferay.commerce.organization.web.internal.display.context;

import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.organization.service.CommerceOrganizationService;
import com.liferay.commerce.organization.util.CommerceOrganizationHelper;
import com.liferay.commerce.organization.web.internal.servlet.taglib.ui.CommerceOrganizationScreenNavigationConstants;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceCountryService;
import com.liferay.commerce.service.CommerceRegionService;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOrganizationAddressesDisplayContext
	extends BaseCommerceOrganizationDisplayContext {

	public CommerceOrganizationAddressesDisplayContext(
		CommerceAddressService commerceAddressService,
		CommerceCountryService commerceCountryService,
		CommerceOrganizationHelper commerceOrganizationHelper,
		CommerceOrganizationService commerceOrganizationService,
		CommerceRegionService commerceRegionService,
		HttpServletRequest httpServletRequest, Portal portal) {

		super(
			commerceOrganizationHelper, commerceOrganizationService,
			httpServletRequest, portal);

		_commerceAddressService = commerceAddressService;
		_commerceCountryService = commerceCountryService;
		_commerceRegionService = commerceRegionService;
	}

	public String getAddCommerceAddressHref() throws WindowStateException {
		return getEditCommerceAddressHref(0L);
	}

	public CommerceAddress getCommerceAddress() throws PortalException {
		HttpServletRequest httpServletRequest =
			commerceOrganizationRequestHelper.getRequest();

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

	public long getCommerceAddressId() throws PortalException {
		CommerceAddress commerceAddress = getCommerceAddress();

		if (commerceAddress == null) {
			return 0;
		}

		return commerceAddress.getCommerceAddressId();
	}

	public List<CommerceCountry> getCommerceCountries() {
		HttpServletRequest httpServletRequest =
			commerceOrganizationRequestHelper.getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _commerceCountryService.getCommerceCountries(
			themeDisplay.getScopeGroupId(), true);
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

	public String getEditCommerceAddressHref(long commerceAddressId)
		throws WindowStateException {

		HttpServletRequest httpServletRequest =
			commerceOrganizationRequestHelper.getRequest();
		LiferayPortletResponse liferayPortletResponse =
			commerceOrganizationRequestHelper.getLiferayPortletResponse();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String title = LanguageUtil.get(httpServletRequest, "add-address");

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "editCommerceAddress");
		portletURL.setParameter("redirect", themeDisplay.getURLCurrent());

		if (commerceAddressId > 0) {
			title = LanguageUtil.get(httpServletRequest, "edit-address");

			portletURL.setParameter(
				"commerceAddressId", String.valueOf(commerceAddressId));
		}

		portletURL.setWindowState(LiferayWindowState.POP_UP);

		StringBundler sb = new StringBundler(13);

		sb.append("javascript:");
		sb.append(liferayPortletResponse.getNamespace());
		sb.append("editCommerceAddress");
		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(StringPool.APOSTROPHE);
		sb.append(title);
		sb.append(StringPool.APOSTROPHE);
		sb.append(StringPool.COMMA_AND_SPACE);
		sb.append(StringPool.APOSTROPHE);
		sb.append(portletURL.toString());
		sb.append(StringPool.APOSTROPHE);
		sb.append(StringPool.CLOSE_PARENTHESIS);
		sb.append(StringPool.SEMICOLON);

		return sb.toString();
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(
			commerceOrganizationRequestHelper.getRequest(), "keywords");

		return _keywords;
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter(
			"screenNavigationCategoryKey",
			CommerceOrganizationScreenNavigationConstants.CATEGORY_DETAILS);

		portletURL.setParameter(
			"screenNavigationEntryKey",
			CommerceOrganizationScreenNavigationConstants.
				ENTRY_KEY_ORGANIZATION_ADDRESSES);

		return portletURL;
	}

	public SearchContainer<CommerceAddress> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			commerceOrganizationRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), null, "there-are-no-addresses");

		OrderByComparator<CommerceAddress> orderByComparator =
			CommerceUtil.getCommerceAddressOrderByComparator(
				getOrderByCol(), getOrderByType());

		_searchContainer.setOrderByCol(getOrderByCol());
		_searchContainer.setOrderByComparator(orderByComparator);
		_searchContainer.setOrderByType(getOrderByType());
		_searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(
				commerceOrganizationRequestHelper.getLiferayPortletResponse()));

		Organization organization = getCurrentOrganization();

		Sort sort = CommerceUtil.getCommerceAddressSort(
			getOrderByCol(), getOrderByType());

		BaseModelSearchResult<CommerceAddress>
			commerceAddressBaseModelSearchResult =
				_commerceAddressService.searchCommerceAddresses(
					commerceOrganizationRequestHelper.getCompanyId(),
					organization.getGroupId(), Organization.class.getName(),
					organization.getOrganizationId(), getKeywords(),
					_searchContainer.getStart(), _searchContainer.getEnd(),
					sort);

		_searchContainer.setTotal(
			commerceAddressBaseModelSearchResult.getLength());
		_searchContainer.setResults(
			commerceAddressBaseModelSearchResult.getBaseModels());

		return _searchContainer;
	}

	public boolean hasManageCommerceAddressPermission() {
		return true;
	}

	private final CommerceAddressService _commerceAddressService;
	private final CommerceCountryService _commerceCountryService;
	private final CommerceRegionService _commerceRegionService;
	private String _keywords;
	private SearchContainer<CommerceAddress> _searchContainer;

}