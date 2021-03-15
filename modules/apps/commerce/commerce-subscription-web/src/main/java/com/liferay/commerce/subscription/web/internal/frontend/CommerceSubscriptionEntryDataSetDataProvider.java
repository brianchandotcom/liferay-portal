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

package com.liferay.commerce.subscription.web.internal.frontend;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.constants.CommerceSubscriptionEntryConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.product.display.context.util.CPRequestHelper;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceSubscriptionEntryService;
import com.liferay.commerce.subscription.web.internal.frontend.constants.CommerceSubscriptionDataSetConstants;
import com.liferay.commerce.subscription.web.internal.model.Label;
import com.liferay.commerce.subscription.web.internal.model.Link;
import com.liferay.commerce.subscription.web.internal.model.SubscriptionEntry;
import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.provider.key=" + CommerceSubscriptionDataSetConstants.COMMERCE_DATA_SET_KEY_SUBSCRIPTION_ENTRIES,
	service = ClayDataSetDataProvider.class
)
public class CommerceSubscriptionEntryDataSetDataProvider
	implements ClayDataSetDataProvider<SubscriptionEntry> {

	@Override
	public List<SubscriptionEntry> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		List<SubscriptionEntry> subscriptionEntries = new ArrayList<>();

		BaseModelSearchResult<CommerceSubscriptionEntry> baseModelSearchResult =
			_getBaseModelSearchResult(
				httpServletRequest, filter, pagination, sort);

		for (CommerceSubscriptionEntry commerceSubscriptionEntry :
				baseModelSearchResult.getBaseModels()) {

			CommerceOrderItem commerceOrderItem =
				_commerceOrderItemService.getCommerceOrderItem(
					commerceSubscriptionEntry.getCommerceOrderItemId());

			String commerceOrderIdString = String.valueOf(
				commerceOrderItem.getCommerceOrderId());

			CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

			CommerceAccount commerceAccount =
				commerceOrder.getCommerceAccount();

			String commerceAccountIdString = String.valueOf(
				commerceAccount.getCommerceAccountId());

			SubscriptionEntry subscriptionEntry = new SubscriptionEntry(
				commerceSubscriptionEntry.getCommerceSubscriptionEntryId(),
				new Link(
					commerceOrderIdString,
					_getEditCommerceOrderURL(
						commerceOrder.getCommerceOrderId(),
						httpServletRequest)),
				new Link(
					commerceAccountIdString,
					_getEditAccountURL(
						commerceAccount.getCommerceAccountId(),
						httpServletRequest)),
				_getSubscriptionStatus(commerceSubscriptionEntry),
				commerceAccount.getName());

			subscriptionEntries.add(subscriptionEntry);
		}

		return subscriptionEntries;
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		BaseModelSearchResult<CommerceSubscriptionEntry> baseModelSearchResult =
			_getBaseModelSearchResult(httpServletRequest, filter, null, null);

		return baseModelSearchResult.getLength();
	}

	private BaseModelSearchResult<CommerceSubscriptionEntry>
			_getBaseModelSearchResult(
				HttpServletRequest httpServletRequest, Filter filter,
				Pagination pagination, Sort sort)
		throws PortalException {

		int start = QueryUtil.ALL_POS;
		int end = QueryUtil.ALL_POS;

		if (pagination != null) {
			start = pagination.getStartPosition();
			end = pagination.getEndPosition();
		}

		return _commerceSubscriptionEntryService.
			searchCommerceSubscriptionEntries(
				_portal.getCompanyId(httpServletRequest), null, null,
				filter.getKeywords(), start, end, sort);
	}

	private String _getEditAccountURL(
			long commerceAccountId, HttpServletRequest httpServletRequest)
		throws PortalException {

		CPRequestHelper cpRequestHelper = new CPRequestHelper(
			httpServletRequest);

		ThemeDisplay themeDisplay = cpRequestHelper.getThemeDisplay();

		String redirect = ParamUtil.getString(
			httpServletRequest, "currentUrl",
			_portal.getCurrentURL(httpServletRequest));

		PortletURL portletURL = PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				httpServletRequest, themeDisplay.getScopeGroup(),
				CommerceAccount.class.getName(), PortletProvider.Action.EDIT)
		).setMVCRenderCommandName(
			"/commerce_account_admin/edit_commerce_account"
		).setRedirect(
			redirect
		).setParameter(
			"commerceAccountId", String.valueOf(commerceAccountId)
		).build();

		return portletURL.toString();
	}

	private String _getEditCommerceOrderURL(
			long commerceOrderId, HttpServletRequest httpServletRequest)
		throws PortalException {

		CPRequestHelper cpRequestHelper = new CPRequestHelper(
			httpServletRequest);

		ThemeDisplay themeDisplay = cpRequestHelper.getThemeDisplay();

		String redirect = ParamUtil.getString(
			httpServletRequest, "currentUrl",
			_portal.getCurrentURL(httpServletRequest));

		PortletURL portletURL = PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				httpServletRequest, themeDisplay.getScopeGroup(),
				CommerceOrder.class.getName(), PortletProvider.Action.MANAGE)
		).setMVCRenderCommandName(
			"/commerce_open_order_content/edit_commerce_order"
		).setRedirect(
			redirect
		).setParameter(
			"commerceOrderId", String.valueOf(commerceOrderId)
		).build();

		return portletURL.toString();
	}

	private Label _getSubscriptionStatus(
		CommerceSubscriptionEntry commerceSubscriptionEntry) {

		int subscriptionStatus =
			commerceSubscriptionEntry.getSubscriptionStatus();

		String subscriptionStatusLabel =
			CommerceSubscriptionEntryConstants.getSubscriptionStatusLabel(
				subscriptionStatus);

		if (Validator.isNull(subscriptionStatusLabel)) {
			return null;
		}

		String label = Label.INFO;

		if (Objects.equals(
				subscriptionStatus,
				CommerceSubscriptionEntryConstants.
					SUBSCRIPTION_STATUS_ACTIVE)) {

			label = Label.SUCCESS;
		}
		else if (Objects.equals(
					subscriptionStatus,
					CommerceSubscriptionEntryConstants.
						SUBSCRIPTION_STATUS_SUSPENDED) ||
				 Objects.equals(
					 subscriptionStatus,
					 CommerceSubscriptionEntryConstants.
						 SUBSCRIPTION_STATUS_INACTIVE)) {

			label = Label.WARNING;
		}
		else if (Objects.equals(
					subscriptionStatus,
					CommerceSubscriptionEntryConstants.
						SUBSCRIPTION_STATUS_CANCELLED)) {

			label = Label.DANGER;
		}

		return new Label(subscriptionStatusLabel, label);
	}

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceSubscriptionEntryService _commerceSubscriptionEntryService;

	@Reference
	private Portal _portal;

}