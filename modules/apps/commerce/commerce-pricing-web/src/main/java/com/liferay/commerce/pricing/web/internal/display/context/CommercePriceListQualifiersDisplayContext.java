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

import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListAccountRelService;
import com.liferay.commerce.price.list.service.CommercePriceListChannelRelService;
import com.liferay.commerce.price.list.service.CommercePriceListCommerceAccountGroupRelService;
import com.liferay.commerce.price.list.service.CommercePriceListOrderTypeRelService;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceCatalogService;
import com.liferay.frontend.taglib.clay.data.set.servlet.taglib.util.ClayDataSetActionDropdownItem;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommercePriceListQualifiersDisplayContext
	extends BaseCommercePriceListDisplayContext {

	public CommercePriceListQualifiersDisplayContext(
		CommerceCatalogService commerceCatalogService,
		CommercePriceListAccountRelService commercePriceListAccountRelService,
		CommercePriceListChannelRelService commercePriceListChannelRelService,
		CommercePriceListCommerceAccountGroupRelService
			commercePriceListCommerceAccountGroupRelService,
		CommercePriceListOrderTypeRelService
			commercePriceListOrderTypeRelService,
		ModelResourcePermission<CommercePriceList>
			commercePriceListModelResourcePermission,
		CommercePriceListService commercePriceListService, Portal portal,
		HttpServletRequest httpServletRequest) {

		super(
			commerceCatalogService, commercePriceListModelResourcePermission,
			commercePriceListService, httpServletRequest);

		_commercePriceListAccountRelService =
			commercePriceListAccountRelService;
		_commercePriceListChannelRelService =
			commercePriceListChannelRelService;
		_commercePriceListCommerceAccountGroupRelService =
			commercePriceListCommerceAccountGroupRelService;
		_commercePriceListOrderTypeRelService =
			commercePriceListOrderTypeRelService;

		_portal = portal;
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
		int commercePriceListChannelRelsCount =
			_commercePriceListChannelRelService.
				getCommercePriceListChannelRelsCount(getCommercePriceListId());

		if (commercePriceListChannelRelsCount > 0) {
			return "channels";
		}

		return "all";
	}

	public String getActiveOrderTypeEligibility() throws PortalException {
		int commercePriceListChannelRelsCount =
			_commercePriceListOrderTypeRelService.
				getCommercePriceListOrderTypeRelsCount(
					getCommercePriceListId(), null);

		if (commercePriceListChannelRelsCount > 0) {
			return "orderTypes";
		}

		return "all";
	}

	public List<ClayDataSetActionDropdownItem>
			getPriceListAccountClayDataSetActionDropdownItems()
		throws PortalException {

		return getClayDataSetActionDropdownItems(
			PortletURLBuilder.create(
				_portal.getControlPanelPortletURL(
					httpServletRequest,
					AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN,
					PortletRequest.RENDER_PHASE)
			).setMVCRenderCommandName(
				"/account_admin/edit_account_entry"
			).setRedirect(
				commercePricingRequestHelper.getCurrentURL()
			).setParameter(
				"accountEntryId", "{account.id}"
			).buildString(),
			false);
	}

	public List<ClayDataSetActionDropdownItem>
			getPriceListAccountGroupClayDataSetActionDropdownItems()
		throws PortalException {

		return ListUtil.fromArray(
			new ClayDataSetActionDropdownItem(
				null, "trash", "delete",
				LanguageUtil.get(httpServletRequest, "delete"), "delete",
				"delete", "headless"));
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

		return getClayDataSetActionDropdownItems(
			PortletURLBuilder.create(
				PortletProviderUtil.getPortletURL(
					httpServletRequest, CommerceChannel.class.getName(),
					PortletProvider.Action.MANAGE)
			).setMVCRenderCommandName(
				"/commerce_channels/edit_commerce_channel"
			).setRedirect(
				commercePricingRequestHelper.getCurrentURL()
			).setParameter(
				"commerceChannelId", "{channel.id}"
			).buildString(),
			false);
	}

	public String getPriceListChannelsApiURL() throws PortalException {
		return "/o/headless-commerce-admin-pricing/v2.0/price-lists/" +
			getCommercePriceListId() +
				"/price-list-channels?nestedFields=channel";
	}

	public List<ClayDataSetActionDropdownItem>
			getPriceListOrderTypeClayDataSetActionDropdownItems()
		throws PortalException {

		return getClayDataSetActionDropdownItems(
			PortletURLBuilder.create(
				PortletProviderUtil.getPortletURL(
					httpServletRequest, CommerceOrderType.class.getName(),
					PortletProvider.Action.MANAGE)
			).setMVCRenderCommandName(
				"/commerce_order_type/edit_commerce_order_type"
			).setRedirect(
				commercePricingRequestHelper.getCurrentURL()
			).setParameter(
				"commerceOrderTypeId", "{orderType.id}"
			).buildString(),
			false);
	}

	public String getPriceListOrderTypesAPIURL() throws PortalException {
		return "/o/headless-commerce-admin-pricing/v2.0/price-lists/" +
			getCommercePriceListId() +
				"/price-list-order-types?nestedFields=orderType";
	}

	private final CommercePriceListAccountRelService
		_commercePriceListAccountRelService;
	private final CommercePriceListChannelRelService
		_commercePriceListChannelRelService;
	private final CommercePriceListCommerceAccountGroupRelService
		_commercePriceListCommerceAccountGroupRelService;
	private final CommercePriceListOrderTypeRelService
		_commercePriceListOrderTypeRelService;
	private final Portal _portal;

}