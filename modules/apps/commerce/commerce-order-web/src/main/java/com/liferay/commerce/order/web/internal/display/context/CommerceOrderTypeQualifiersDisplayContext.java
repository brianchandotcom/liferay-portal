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

package com.liferay.commerce.order.web.internal.display.context;

import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.service.CommerceOrderTypeRelService;
import com.liferay.commerce.service.CommerceOrderTypeService;
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
public class CommerceOrderTypeQualifiersDisplayContext
	extends CommerceOrderTypeDisplayContext {

	public CommerceOrderTypeQualifiersDisplayContext(
		HttpServletRequest httpServletRequest,
		ModelResourcePermission<CommerceOrderType>
			commerceOrderTypeModelResourcePermission,
		CommerceOrderTypeService commerceOrderTypeService,
		CommerceOrderTypeRelService commerceOrderTypeRelService,
		Portal portal) {

		super(
			httpServletRequest, commerceOrderTypeModelResourcePermission,
			commerceOrderTypeService, portal);

		_commerceOrderTypeRelService = commerceOrderTypeRelService;
	}

	public String getActiveChannelEligibility() throws PortalException {
		long commerceChannelRelsCount =
			_commerceOrderTypeRelService.
				getCommerceOrderTypeCommerceChannelRelsCount(
					getCommerceOrderTypeId(), null);

		if (commerceChannelRelsCount > 0) {
			return "channels";
		}

		return "all";
	}

	public List<ClayDataSetActionDropdownItem>
			getCommerceOrderTypeChannelClayDataSetActionDropdownItems()
		throws PortalException {

		return _getClayHeadlessDataSetActionTemplates(
			PortletURLBuilder.create(
				PortletProviderUtil.getPortletURL(
					httpServletRequest, CommerceChannel.class.getName(),
					PortletProvider.Action.MANAGE)
			).setMVCRenderCommandName(
				"/commerce_channels/edit_commerce_channel"
			).setRedirect(
				commerceOrderRequestHelper.getCurrentURL()
			).setParameter(
				"commerceChannelId", "{channel.id}"
			).buildString(),
			false);
	}

	public String getCommerceOrderTypeChannelsApiURL() throws PortalException {
		return "/o/headless-commerce-admin-order/v1.0/order-types/" +
			getCommerceOrderTypeId() +
				"/order-type-channels?nestedFields=channel";
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

	private final CommerceOrderTypeRelService _commerceOrderTypeRelService;

}