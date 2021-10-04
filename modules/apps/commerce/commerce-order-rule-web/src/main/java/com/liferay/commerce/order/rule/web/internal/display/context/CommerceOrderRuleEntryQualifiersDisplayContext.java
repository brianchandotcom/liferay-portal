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

package com.liferay.commerce.order.rule.web.internal.display.context;

import com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry;
import com.liferay.commerce.order.rule.service.CommerceOrderRuleEntryRelService;
import com.liferay.commerce.order.rule.service.CommerceOrderRuleEntryService;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.frontend.taglib.clay.data.set.servlet.taglib.util.ClayDataSetActionDropdownItem;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderRuleEntryQualifiersDisplayContext
	extends CommerceOrderRuleEntryDisplayContext {

	public CommerceOrderRuleEntryQualifiersDisplayContext(
		HttpServletRequest httpServletRequest,
		ModelResourcePermission<CommerceOrderRuleEntry>
			commerceOrderRuleEntryModelResourcePermission,
		CommerceOrderRuleEntryService commerceOrderRuleEntryService,
		CommerceOrderRuleEntryRelService commerceOrderRuleEntryRelService,
		Portal portal) {

		super(
			httpServletRequest, commerceOrderRuleEntryModelResourcePermission,
			commerceOrderRuleEntryService, portal);

		_commerceOrderRuleEntryRelService = commerceOrderRuleEntryRelService;
	}

	public String getActiveChannelEligibility() throws PortalException {
		long commerceChannelRelsCount =
			_commerceOrderRuleEntryRelService.
				getCommerceOrderRuleEntryCommerceChannelRelsCount(
					getCommerceOrderRuleEntryId(), null);

		if (commerceChannelRelsCount > 0) {
			return "channels";
		}

		return "all";
	}

	public List<ClayDataSetActionDropdownItem>
			getCommerceOrderRuleEntryChannelClayDataSetActionDropdownItems()
		throws PortalException {

		return _getClayHeadlessDataSetActionTemplates(
			PortletURLBuilder.create(
				PortletProviderUtil.getPortletURL(
					httpServletRequest, CommerceChannel.class.getName(),
					PortletProvider.Action.MANAGE)
			).setMVCRenderCommandName(
				"/commerce_channels/edit_commerce_channel"
			).setRedirect(
				commerceOrderRuleEntryRequestHelper.getCurrentURL()
			).setParameter(
				"commerceChannelId", "{channel.id}"
			).buildString(),
			false);
	}

	public String getCommerceOrderRuleEntryChannelsApiURL()
		throws PortalException {

		return "/o/headless-commerce-admin-order/v1.0/order-rules/" +
			getCommerceOrderRuleEntryId() +
				"/order-rule-channels?nestedFields=channel";
	}

	private List<ClayDataSetActionDropdownItem>
		_getClayHeadlessDataSetActionTemplates(
			String portletURL, boolean sidePanel) {

		List<ClayDataSetActionDropdownItem> clayDataSetActionDropdownItems =
			new ArrayList<>();

		ClayDataSetActionDropdownItem clayDataSetActionDropdownItem =
			new ClayDataSetActionDropdownItem(
				portletURL, "pencil", "edit",
				LanguageUtil.get(httpServletRequest, "edit"), "get", null,
				null);

		if (sidePanel) {
			clayDataSetActionDropdownItem.setTarget("sidePanel");
		}

		clayDataSetActionDropdownItems.add(clayDataSetActionDropdownItem);

		clayDataSetActionDropdownItems.add(
			new ClayDataSetActionDropdownItem(
				null, "trash", "remove",
				LanguageUtil.get(httpServletRequest, "remove"), "delete",
				"delete", "headless"));

		return clayDataSetActionDropdownItems;
	}

	private final CommerceOrderRuleEntryRelService
		_commerceOrderRuleEntryRelService;

}