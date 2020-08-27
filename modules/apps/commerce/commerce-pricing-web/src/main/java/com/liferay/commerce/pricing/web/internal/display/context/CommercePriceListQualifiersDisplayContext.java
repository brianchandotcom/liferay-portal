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

package com.liferay.commerce.pricing.web.internal.display.context;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListAccountRelService;
import com.liferay.commerce.price.list.service.CommercePriceListCommerceAccountGroupRelService;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceCatalogService;
import com.liferay.commerce.product.service.CommerceChannelRelService;
import com.liferay.frontend.taglib.clay.data.set.servlet.taglib.util.ClayDataSetActionDropdownItem;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommercePriceListQualifiersDisplayContext
	extends BaseCommercePriceListDisplayContext {

	public CommercePriceListQualifiersDisplayContext(
		CommerceCatalogService commerceCatalogService,
		CommerceChannelRelService commerceChannelRelService,
		CommercePriceListAccountRelService commercePriceListAccountRelService,
		CommercePriceListCommerceAccountGroupRelService
			commercePriceListCommerceAccountGroupRelService,
		ModelResourcePermission<CommercePriceList>
			commercePriceListModelResourcePermission,
		CommercePriceListService commercePriceListService,
		HttpServletRequest httpServletRequest) {

		super(
			commerceCatalogService, commercePriceListModelResourcePermission,
			commercePriceListService, httpServletRequest);

		_commerceChannelRelService = commerceChannelRelService;
		_commercePriceListAccountRelService =
			commercePriceListAccountRelService;
		_commercePriceListCommerceAccountGroupRelService =
			commercePriceListCommerceAccountGroupRelService;
	}

	public String getActiveAccountEligibility() throws PortalException {
		long commercePriceListId = getCommercePriceListId();

		long commercePriceListAccountRelsCount =
			_commercePriceListAccountRelService.
				getCommercePriceListAccountRelsCount(commercePriceListId);

		if (commercePriceListAccountRelsCount > 0) {
			return "accounts";
		}

		long commercePriceListAccountGroupRelsCount =
			_commercePriceListCommerceAccountGroupRelService.
				getCommercePriceListCommerceAccountGroupRelsCount(
					commercePriceListId);

		if (commercePriceListAccountGroupRelsCount > 0) {
			return "accountGroups";
		}

		return "all";
	}

	public String getActiveChannelEligibility() throws PortalException {
		long commercePriceListId = getCommercePriceListId();

		long commerceChannelRelsCount =
			_commerceChannelRelService.getCommerceChannelRelsCount(
				CommercePriceList.class.getName(), commercePriceListId);

		if (commerceChannelRelsCount > 0) {
			return "channels";
		}

		return "all";
	}

	public List<ClayDataSetActionDropdownItem>
			getPriceListAccountClayDataSetActionDropdownItems()
		throws PortalException {

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			httpServletRequest, CommerceAccount.class.getName(),
			PortletProvider.Action.EDIT);

		portletURL.setParameter("mvcRenderCommandName", "editCommerceAccount");
		portletURL.setParameter(
			"redirect", commercePricingRequestHelper.getCurrentURL());
		portletURL.setParameter("commerceAccountId", "{account.id}");

		return getClayDataSetActionDropdownItems(portletURL.toString(), false);
	}

	public List<ClayDataSetActionDropdownItem>
			getPriceListAccountGroupClayDataSetActionDropdownItems()
		throws PortalException {

		List<ClayDataSetActionDropdownItem> clayDataSetActionDropdownItems =
			new ArrayList<>();

		clayDataSetActionDropdownItems.add(
			new ClayDataSetActionDropdownItem(
				null, "trash", "delete",
				LanguageUtil.get(httpServletRequest, "delete"), "delete",
				"delete", "headless"));

		return clayDataSetActionDropdownItems;
	}

	public String getPriceListAccountGroupsApiURL() throws PortalException {
		return "/o/headless-commerce-admin-pricing/v2.0/price-lists/" +
			getCommercePriceListId() +
				"/price-list-account-groups?nestedFields=accountGroup";
	}

	public String getPriceListAccountsApiURL() throws PortalException {
		return "/o/headless-commerce-admin-pricing/v2.0/price-lists/" +
			getCommercePriceListId() +
				"/price-list-accounts?nestedFields=account";
	}

	public List<ClayDataSetActionDropdownItem>
			getPriceListChannelClayDataSetActionDropdownItems()
		throws PortalException {

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			httpServletRequest, CommerceChannel.class.getName(),
			PortletProvider.Action.MANAGE);

		portletURL.setParameter("mvcRenderCommandName", "editCommerceChannel");
		portletURL.setParameter(
			"redirect", commercePricingRequestHelper.getCurrentURL());
		portletURL.setParameter("commerceChannelId", "{channel.id}");

		return getClayDataSetActionDropdownItems(portletURL.toString(), false);
	}

	public String getPriceListChannelsApiURL() throws PortalException {
		return "/o/headless-commerce-admin-pricing/v2.0/price-lists/" +
			getCommercePriceListId() +
				"/price-list-channels?nestedFields=channel";
	}

	private final CommerceChannelRelService _commerceChannelRelService;
	private final CommercePriceListAccountRelService
		_commercePriceListAccountRelService;
	private final CommercePriceListCommerceAccountGroupRelService
		_commercePriceListCommerceAccountGroupRelService;

}