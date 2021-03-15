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
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.rule.type.CommerceDiscountRuleTypeRegistry;
import com.liferay.commerce.discount.service.CommerceDiscountAccountRelService;
import com.liferay.commerce.discount.service.CommerceDiscountCommerceAccountGroupRelService;
import com.liferay.commerce.discount.service.CommerceDiscountRuleService;
import com.liferay.commerce.discount.service.CommerceDiscountService;
import com.liferay.commerce.discount.target.CommerceDiscountTargetRegistry;
import com.liferay.commerce.percentage.PercentageFormatter;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelRelService;
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

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceDiscountQualifiersDisplayContext
	extends CommerceDiscountDisplayContext {

	public CommerceDiscountQualifiersDisplayContext(
		CommerceChannelRelService commerceChannelRelService,
		CommerceCurrencyLocalService commerceCurrencyLocalService,
		ModelResourcePermission<CommerceDiscount>
			commerceDiscountModelResourcePermission,
		CommerceDiscountAccountRelService commerceDiscountAccountRelService,
		CommerceDiscountCommerceAccountGroupRelService
			commerceDiscountCommerceAccountGroupRelService,
		CommerceDiscountService commerceDiscountService,
		CommerceDiscountRuleService commerceDiscountRuleService,
		CommerceDiscountRuleTypeRegistry commerceDiscountRuleTypeRegistry,
		CommerceDiscountTargetRegistry commerceDiscountTargetRegistry,
		PercentageFormatter percentageFormatter,
		HttpServletRequest httpServletRequest, Portal portal) {

		super(
			commerceCurrencyLocalService,
			commerceDiscountModelResourcePermission, commerceDiscountService,
			commerceDiscountRuleService, commerceDiscountRuleTypeRegistry,
			commerceDiscountTargetRegistry, percentageFormatter,
			httpServletRequest, portal);

		_commerceChannelRelService = commerceChannelRelService;
		_commerceDiscountAccountRelService = commerceDiscountAccountRelService;
		_commerceDiscountCommerceAccountGroupRelService =
			commerceDiscountCommerceAccountGroupRelService;
	}

	public List<ClayDataSetActionDropdownItem>
			getAccountClayDataSetActionDropdownItems()
		throws PortalException {

		return getClayHeadlessDataSetActionTemplates(
			PortletURLBuilder.create(
				PortletProviderUtil.getPortletURL(
					httpServletRequest, CommerceAccount.class.getName(),
					PortletProvider.Action.EDIT)
			).setMVCRenderCommandName(
				"/commerce_account_admin/edit_commerce_account"
			).setRedirect(
				commercePricingRequestHelper.getCurrentURL()
			).setParameter(
				"commerceAccountId", "{account.id}"
			).buildString(),
			false);
	}

	public List<ClayDataSetActionDropdownItem>
			getAccountGroupClayDataSetActionDropdownItems()
		throws PortalException {

		return ListUtil.fromArray(
			new ClayDataSetActionDropdownItem(
				null, "trash", "remove",
				LanguageUtil.get(httpServletRequest, "remove"), "delete",
				"delete", "headless"));
	}

	public String getActiveAccountEligibility() throws PortalException {
		long commerceDiscountId = getCommerceDiscountId();

		long commerceDiscountAccountRelsCount =
			_commerceDiscountAccountRelService.
				getCommerceDiscountAccountRelsCount(commerceDiscountId);

		if (commerceDiscountAccountRelsCount > 0) {
			return "accounts";
		}

		long commerceDiscountAccountGroupRelsCount =
			_commerceDiscountCommerceAccountGroupRelService.
				getCommerceDiscountCommerceAccountGroupRelsCount(
					commerceDiscountId);

		if (commerceDiscountAccountGroupRelsCount > 0) {
			return "accountGroups";
		}

		return "all";
	}

	public String getActiveChannelEligibility() throws PortalException {
		long commerceChannelRelsCount =
			_commerceChannelRelService.getCommerceChannelRelsCount(
				CommerceDiscount.class.getName(), getCommerceDiscountId());

		if (commerceChannelRelsCount > 0) {
			return "channels";
		}

		return "all";
	}

	public String getDiscountAccountGroupsApiURL() throws PortalException {
		return "/o/headless-commerce-admin-pricing/v2.0/discounts/" +
			getCommerceDiscountId() +
				"/discount-account-groups?nestedFields=accountGroup";
	}

	public String getDiscountAccountsApiURL() throws PortalException {
		return "/o/headless-commerce-admin-pricing/v2.0/discounts/" +
			getCommerceDiscountId() + "/discount-accounts?nestedFields=account";
	}

	public List<ClayDataSetActionDropdownItem>
			getDiscountChannelClayDataSetActionDropdownItems()
		throws PortalException {

		return getClayHeadlessDataSetActionTemplates(
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

	public String getDiscountChannelsApiURL() throws PortalException {
		return "/o/headless-commerce-admin-pricing/v2.0/discounts/" +
			getCommerceDiscountId() + "/discount-channels?nestedFields=channel";
	}

	private final CommerceChannelRelService _commerceChannelRelService;
	private final CommerceDiscountAccountRelService
		_commerceDiscountAccountRelService;
	private final CommerceDiscountCommerceAccountGroupRelService
		_commerceDiscountCommerceAccountGroupRelService;

}