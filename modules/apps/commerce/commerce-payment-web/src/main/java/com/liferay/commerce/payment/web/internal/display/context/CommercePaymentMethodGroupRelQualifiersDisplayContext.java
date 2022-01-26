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

package com.liferay.commerce.payment.web.internal.display.context;

import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.payment.method.CommercePaymentMethodRegistry;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelQualifierService;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelService;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.frontend.taglib.clay.data.set.servlet.taglib.util.ClayDataSetActionDropdownItem;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.CountryService;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Riccardo Alberti
 */
public class CommercePaymentMethodGroupRelQualifiersDisplayContext
	extends CommercePaymentMethodGroupRelsDisplayContext {

	public CommercePaymentMethodGroupRelQualifiersDisplayContext(
		ModelResourcePermission<CommerceChannel>
			commerceChannelModelResourcePermission,
		CommerceChannelLocalService commerceChannelLocalService,
		CommercePaymentMethodGroupRelService
			commercePaymentMethodGroupRelService,
		CommercePaymentMethodGroupRelQualifierService
			commercePaymentMethodGroupRelQualifierService,
		CommercePaymentMethodRegistry commercePaymentMethodRegistry,
		CountryService countryService, HttpServletRequest httpServletRequest) {

		super(
			commerceChannelLocalService, commercePaymentMethodGroupRelService,
			commercePaymentMethodRegistry, countryService, httpServletRequest);

		_commerceChannelModelResourcePermission =
			commerceChannelModelResourcePermission;
		_commercePaymentMethodGroupRelQualifierService =
			commercePaymentMethodGroupRelQualifierService;
	}

	public String getActiveOrderTypeEligibility() throws PortalException {
		long commerceOrderTypeCommercePaymentMethodGroupRelsCount =
			_commercePaymentMethodGroupRelQualifierService.
				getCommerceOrderTypeCommercePaymentMethodGroupRelQualifiersCount(
					getCommercePaymentMethodGroupRelId(), null);

		if (commerceOrderTypeCommercePaymentMethodGroupRelsCount > 0) {
			return "orderTypes";
		}

		return "all";
	}

	public String getActiveTermEntryEligibility() throws PortalException {
		long commerceTermEntryCommercePaymentMethodGroupRelsCount =
			_commercePaymentMethodGroupRelQualifierService.
				getCommerceTermEntryCommercePaymentMethodGroupRelQualifiersCount(
					getCommercePaymentMethodGroupRelId(), null);

		if (commerceTermEntryCommercePaymentMethodGroupRelsCount > 0) {
			return "termEntries";
		}

		return "none";
	}

	public List<ClayDataSetActionDropdownItem>
			getCommerceOrderTypeClayDataSetActionDropdownItems()
		throws PortalException {

		return _getClayDataSetActionTemplates(
			PortletURLBuilder.create(
				PortletProviderUtil.getPortletURL(
					commercePaymentMethodRequestHelper.getRequest(),
					CommerceOrderType.class.getName(),
					PortletProvider.Action.MANAGE)
			).setMVCRenderCommandName(
				"/commerce_order_type/edit_commerce_order_type"
			).setRedirect(
				commercePaymentMethodRequestHelper.getCurrentURL()
			).setParameter(
				"commerceOrderTypeId", "{orderType.id}"
			).buildString(),
			false);
	}

	public String getCommerceOrderTypeCommercePaymentMethodGroupRelsAPIURL()
		throws PortalException {

		return StringBundler.concat(
			"/o/headless-commerce-admin-channel/v1.0/payment-method-group-rels",
			"/", getCommercePaymentMethodGroupRelId(),
			"/payment-method-group-rel-order-types?nestedFields=orderType");
	}

	public String getCommerceTermEntriesCommercePaymentMethodGroupRelsAPIURL()
		throws PortalException {

		return StringBundler.concat(
			"/o/headless-commerce-admin-channel/v1.0/payment-method-group-rels",
			"/", getCommercePaymentMethodGroupRelId(),
			"/payment-method-group-rel-terms?nestedFields=term");
	}

	public List<ClayDataSetActionDropdownItem>
			getCommerceTermEntryClayDataSetActionDropdownItems()
		throws PortalException {

		return _getClayDataSetActionTemplates(
			PortletURLBuilder.create(
				PortletProviderUtil.getPortletURL(
					commercePaymentMethodRequestHelper.getRequest(),
					CommerceTermEntry.class.getName(),
					PortletProvider.Action.MANAGE)
			).setMVCRenderCommandName(
				"/commerce_term_entry/edit_commerce_term_entry"
			).setRedirect(
				commercePaymentMethodRequestHelper.getCurrentURL()
			).setParameter(
				"commerceTermEntryId", "{term.id}"
			).buildString(),
			false);
	}

	public boolean hasPermission(String actionId) throws PortalException {
		return _commerceChannelModelResourcePermission.contains(
			commercePaymentMethodRequestHelper.getPermissionChecker(),
			getCommerceChannelId(), actionId);
	}

	private List<ClayDataSetActionDropdownItem> _getClayDataSetActionTemplates(
		String portletURL, boolean sidePanel) {

		List<ClayDataSetActionDropdownItem> clayDataSetActionDropdownItems =
			new ArrayList<>();

		ClayDataSetActionDropdownItem clayDataSetActionDropdownItem =
			new ClayDataSetActionDropdownItem(
				portletURL, "pencil", "edit",
				LanguageUtil.get(
					commercePaymentMethodRequestHelper.getRequest(), "edit"),
				"get", null, null);

		if (sidePanel) {
			clayDataSetActionDropdownItem.setTarget("sidePanel");
		}

		clayDataSetActionDropdownItems.add(clayDataSetActionDropdownItem);

		clayDataSetActionDropdownItems.add(
			new ClayDataSetActionDropdownItem(
				null, "trash", "remove",
				LanguageUtil.get(
					commercePaymentMethodRequestHelper.getRequest(), "remove"),
				"delete", "delete", "headless"));

		return clayDataSetActionDropdownItems;
	}

	private final ModelResourcePermission<CommerceChannel>
		_commerceChannelModelResourcePermission;
	private final CommercePaymentMethodGroupRelQualifierService
		_commercePaymentMethodGroupRelQualifierService;

}