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

package com.liferay.commerce.subscription.web.internal.display.context;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.constants.CommerceActionKeys;
import com.liferay.commerce.frontend.model.HeaderActionModel;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelLocalService;
import com.liferay.commerce.product.display.context.util.CPRequestHelper;
import com.liferay.commerce.product.util.CPSubscriptionType;
import com.liferay.commerce.product.util.CPSubscriptionTypeJSPContributor;
import com.liferay.commerce.product.util.CPSubscriptionTypeJSPContributorRegistry;
import com.liferay.commerce.product.util.CPSubscriptionTypeRegistry;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.service.CommerceSubscriptionEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;

import java.text.DateFormat;
import java.text.Format;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class CommerceSubscriptionEntryDisplayContext {

	public CommerceSubscriptionEntryDisplayContext(
		CommercePaymentMethodGroupRelLocalService
			commercePaymentMethodGroupRelLocalService,
		CommerceSubscriptionEntryLocalService
			commerceSubscriptionEntryLocalService,
		CommerceOrderItemLocalService commerceOrderItemLocalService,
		CPSubscriptionTypeJSPContributorRegistry
			cpSubscriptionTypeJSPContributorRegistry,
		CPSubscriptionTypeRegistry cpSubscriptionTypeRegistry,
		HttpServletRequest httpServletRequest,
		PortletResourcePermission portletResourcePermission) {

		_commercePaymentMethodGroupRelLocalService =
			commercePaymentMethodGroupRelLocalService;
		_commerceSubscriptionEntryLocalService =
			commerceSubscriptionEntryLocalService;
		_commerceOrderItemLocalService = commerceOrderItemLocalService;
		_cpSubscriptionTypeJSPContributorRegistry =
			cpSubscriptionTypeJSPContributorRegistry;
		_cpSubscriptionTypeRegistry = cpSubscriptionTypeRegistry;
		_httpServletRequest = httpServletRequest;
		_portletResourcePermission = portletResourcePermission;

		_cpRequestHelper = new CPRequestHelper(httpServletRequest);
	}

	public String getCommerceAccountThumbnailURL() throws PortalException {
		if (_commerceSubscriptionEntry == null) {
			return StringPool.BLANK;
		}

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemLocalService.getCommerceOrderItem(
				_commerceSubscriptionEntry.getCommerceOrderItemId());

		CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

		CommerceAccount commerceAccount = commerceOrder.getCommerceAccount();

		ThemeDisplay themeDisplay = _cpRequestHelper.getThemeDisplay();

		StringBundler sb = new StringBundler(5);

		sb.append(themeDisplay.getPathImage());
		sb.append("/organization_logo?img_id=");
		sb.append(commerceAccount.getLogoId());

		if (commerceAccount.getLogoId() > 0) {
			sb.append("&t=");
			sb.append(
				WebServerServletTokenUtil.getToken(
					commerceAccount.getLogoId()));
		}

		return sb.toString();
	}

	public long getCommerceOrderId() throws PortalException {
		CommerceSubscriptionEntry commerceSubscriptionEntry =
			getCommerceSubscriptionEntry();

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemLocalService.getCommerceOrderItem(
				commerceSubscriptionEntry.getCommerceOrderItemId());

		return commerceOrderItem.getCommerceOrderId();
	}

	public CommerceSubscriptionEntry getCommerceSubscriptionEntry() {
		if (_commerceSubscriptionEntry != null) {
			return _commerceSubscriptionEntry;
		}

		long commerceSubscriptionEntryId = ParamUtil.getLong(
			_httpServletRequest, "commerceSubscriptionEntryId");

		if (commerceSubscriptionEntryId > 0) {
			_commerceSubscriptionEntry =
				_commerceSubscriptionEntryLocalService.
					fetchCommerceSubscriptionEntry(commerceSubscriptionEntryId);
		}

		return _commerceSubscriptionEntry;
	}

	public long getCommerceSubscriptionEntryId() {
		CommerceSubscriptionEntry commerceSubscriptionEntry =
			getCommerceSubscriptionEntry();

		if (commerceSubscriptionEntry != null) {
			return commerceSubscriptionEntry.getCommerceSubscriptionEntryId();
		}

		return 0;
	}

	public String getCommerceSubscriptionEntryStartDate() {
		CommerceSubscriptionEntry commerceSubscriptionEntry =
			getCommerceSubscriptionEntry();

		ThemeDisplay themeDisplay = _cpRequestHelper.getThemeDisplay();

		Format dateTimeFormat = FastDateFormatFactoryUtil.getDateTime(
			DateFormat.MEDIUM, DateFormat.MEDIUM, themeDisplay.getLocale(),
			themeDisplay.getTimeZone());

		return dateTimeFormat.format(commerceSubscriptionEntry.getStartDate());
	}

	public CPSubscriptionType getCPSubscriptionType(String subscriptionType) {
		return _cpSubscriptionTypeRegistry.getCPSubscriptionType(
			subscriptionType);
	}

	public CPSubscriptionTypeJSPContributor getCPSubscriptionTypeJSPContributor(
		String subscriptionType) {

		return _cpSubscriptionTypeJSPContributorRegistry.
			getCPSubscriptionTypeJSPContributor(subscriptionType);
	}

	public List<CPSubscriptionType> getCPSubscriptionTypes() {
		return _cpSubscriptionTypeRegistry.getCPSubscriptionTypes();
	}

	public String getEditCommerceOrderURL(long commerceOrderId)
		throws PortalException {

		String orderId;

		if (commerceOrderId > 0) {
			orderId = String.valueOf(commerceOrderId);
		}
		else {
			orderId = String.valueOf(getCommerceOrderId());
		}

		ThemeDisplay themeDisplay = _cpRequestHelper.getThemeDisplay();

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			_httpServletRequest, themeDisplay.getScopeGroup(),
			CommerceOrder.class.getName(), PortletProvider.Action.MANAGE);

		portletURL.setParameter(
			"mvcRenderCommandName",
			"/commerce_open_order_content/edit_commerce_order");
		portletURL.setParameter("redirect", themeDisplay.getURLCurrent());
		portletURL.setParameter("commerceOrderId", orderId);

		return portletURL.toString();
	}

	public List<HeaderActionModel> getHeaderActionModels() {
		List<HeaderActionModel> headerActionModels = new ArrayList<>();

		if (_commerceSubscriptionEntry == null) {
			return headerActionModels;
		}

		RenderResponse renderResponse = _cpRequestHelper.getRenderResponse();

		PortletURL cancelURL = renderResponse.createRenderURL();

		headerActionModels.add(
			new HeaderActionModel(
				null, null, cancelURL.toString(), null, "cancel"));

		PortletURL portletURL = getTransitionOrderPortletURL();

		portletURL.setParameter("transitionName", "save");

		headerActionModels.add(
			new HeaderActionModel(
				"btn-primary", renderResponse.getNamespace() + "fm",
				portletURL.toString(), null, "save"));

		return headerActionModels;
	}

	public String getOrderPaymentMethodImage() throws PortalException {
		CommerceSubscriptionEntry commerceSubscriptionEntry =
			getCommerceSubscriptionEntry();

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemLocalService.getCommerceOrderItem(
				commerceSubscriptionEntry.getCommerceOrderItemId());

		CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

		String paymentMethodKey = commerceOrder.getCommercePaymentMethodKey();

		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel =
			_commercePaymentMethodGroupRelLocalService.
				fetchCommercePaymentMethodGroupRel(
					commerceOrder.getGroupId(), paymentMethodKey);

		return commercePaymentMethodGroupRel.getImageURL(
			_cpRequestHelper.getThemeDisplay());
	}

	public String getOrderPaymentMethodName() throws PortalException {
		CommerceSubscriptionEntry commerceSubscriptionEntry =
			getCommerceSubscriptionEntry();

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemLocalService.getCommerceOrderItem(
				commerceSubscriptionEntry.getCommerceOrderItemId());

		CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

		String paymentMethodKey = commerceOrder.getCommercePaymentMethodKey();

		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel =
			_commercePaymentMethodGroupRelLocalService.
				fetchCommercePaymentMethodGroupRel(
					commerceOrder.getGroupId(), paymentMethodKey);

		return commercePaymentMethodGroupRel.getName(
			_cpRequestHelper.getLocale());
	}

	public int getOrderPaymentStatus() throws PortalException {
		CommerceSubscriptionEntry commerceSubscriptionEntry =
			getCommerceSubscriptionEntry();

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemLocalService.getCommerceOrderItem(
				commerceSubscriptionEntry.getCommerceOrderItemId());

		CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

		return commerceOrder.getPaymentStatus();
	}

	public PortletURL getPortletURL() {
		LiferayPortletResponse liferayPortletResponse =
			_cpRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		String redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		long commerceSubscriptionEntryId = ParamUtil.getLong(
			_httpServletRequest, "commerceSubscriptionEntryId");

		if (commerceSubscriptionEntryId > 0) {
			portletURL.setParameter(
				"commerceSubscriptionEntryId",
				String.valueOf(commerceSubscriptionEntryId));
		}

		String delta = ParamUtil.getString(_httpServletRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String deltaEntry = ParamUtil.getString(
			_httpServletRequest, "deltaEntry");

		if (Validator.isNotNull(deltaEntry)) {
			portletURL.setParameter("deltaEntry", deltaEntry);
		}

		String keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		portletURL.setParameter("navigation", getNavigation());

		return portletURL;
	}

	public PortletURL getTransitionOrderPortletURL() {
		LiferayPortletResponse liferayPortletResponse =
			_cpRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createActionURL();

		portletURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/commerce_open_order_content/edit_commerce_order");
		portletURL.setParameter(Constants.CMD, ActionKeys.UPDATE);
		portletURL.setParameter(
			"commerceSubscriptionEntryId",
			String.valueOf(
				_commerceSubscriptionEntry.getCommerceSubscriptionEntryId()));
		portletURL.setParameter("redirect", _cpRequestHelper.getCurrentURL());

		return portletURL;
	}

	public boolean hasManageCommerceSubscriptionEntryPermission() {
		return _portletResourcePermission.contains(
			_cpRequestHelper.getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SUBSCRIPTIONS);
	}

	public boolean isPaymentMethodActive(String engineKey) {
		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel =
			_commercePaymentMethodGroupRelLocalService.
				fetchCommercePaymentMethodGroupRel(
					_cpRequestHelper.getScopeGroupId(), engineKey);

		if (commercePaymentMethodGroupRel == null) {
			return false;
		}

		return commercePaymentMethodGroupRel.isActive();
	}

	protected String getNavigation() {
		return ParamUtil.getString(_httpServletRequest, "navigation", "all");
	}

	private final CommerceOrderItemLocalService _commerceOrderItemLocalService;
	private final CommercePaymentMethodGroupRelLocalService
		_commercePaymentMethodGroupRelLocalService;
	private CommerceSubscriptionEntry _commerceSubscriptionEntry;
	private final CommerceSubscriptionEntryLocalService
		_commerceSubscriptionEntryLocalService;
	private final CPRequestHelper _cpRequestHelper;
	private final CPSubscriptionTypeJSPContributorRegistry
		_cpSubscriptionTypeJSPContributorRegistry;
	private final CPSubscriptionTypeRegistry _cpSubscriptionTypeRegistry;
	private final HttpServletRequest _httpServletRequest;
	private final PortletResourcePermission _portletResourcePermission;

}