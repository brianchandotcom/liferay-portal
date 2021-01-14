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

package com.liferay.commerce.order.content.web.internal.display.context;

import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.configuration.CommerceOrderFieldsConfiguration;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.constants.CommerceOrderActionKeys;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.constants.CommerceShipmentConstants;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.frontend.model.HeaderActionModel;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderNote;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.order.content.web.internal.portlet.configuration.CommerceOpenOrderContentPortletInstanceConfiguration;
import com.liferay.commerce.order.content.web.internal.portlet.configuration.CommerceOrderContentPortletInstanceConfiguration;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelService;
import com.liferay.commerce.percentage.PercentageFormatter;
import com.liferay.commerce.price.CommerceOrderPrice;
import com.liferay.commerce.price.CommerceOrderPriceCalculation;
import com.liferay.commerce.product.display.context.util.CPRequestHelper;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceOrderNoteService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.service.CommerceShipmentItemService;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.Format;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderContentDisplayContext {

	public CommerceOrderContentDisplayContext(
			CommerceAddressService commerceAddressService,
			CommerceChannelLocalService commerceChannelLocalService,
			CommerceOrderNoteService commerceOrderNoteService,
			CommerceOrderPriceCalculation commerceOrderPriceCalculation,
			CommerceOrderService commerceOrderService,
			CommercePaymentMethodGroupRelService
				commercePaymentMethodGroupRelService,
			CommerceShipmentItemService commerceShipmentItemService,
			HttpServletRequest httpServletRequest,
			ModelResourcePermission<CommerceOrder> modelResourcePermission,
			PercentageFormatter percentageFormatter,
			PortletResourcePermission portletResourcePermission)
		throws PortalException {

		_commerceAddressService = commerceAddressService;
		_commerceChannelLocalService = commerceChannelLocalService;
		_commerceOrderNoteService = commerceOrderNoteService;
		_commerceOrderPriceCalculation = commerceOrderPriceCalculation;
		_commerceOrderService = commerceOrderService;
		_commercePaymentMethodGroupRelService =
			commercePaymentMethodGroupRelService;
		_commerceShipmentItemService = commerceShipmentItemService;
		_httpServletRequest = httpServletRequest;
		_modelResourcePermission = modelResourcePermission;
		_percentageFormatter = percentageFormatter;
		_portletResourcePermission = portletResourcePermission;

		_cpRequestHelper = new CPRequestHelper(httpServletRequest);

		_portletDisplay = _cpRequestHelper.getPortletDisplay();

		ThemeDisplay themeDisplay = _cpRequestHelper.getThemeDisplay();

		_commerceOrderDateFormatDate = FastDateFormatFactoryUtil.getDate(
			DateFormat.MEDIUM, themeDisplay.getLocale(),
			themeDisplay.getTimeZone());
		_commerceOrderDateFormatTime = FastDateFormatFactoryUtil.getTime(
			DateFormat.MEDIUM, themeDisplay.getLocale(),
			themeDisplay.getTimeZone());

		_commerceContext = (CommerceContext)_httpServletRequest.getAttribute(
			CommerceWebKeys.COMMERCE_CONTEXT);

		_commerceAccount = _commerceContext.getCommerceAccount();

		_commerceOrderNoteId = ParamUtil.getLong(
			_httpServletRequest, "commerceOrderNoteId");
	}

	public CommerceChannel fetchCommerceChannel() {
		return _commerceChannelLocalService.fetchCommerceChannelBySiteGroupId(
			_cpRequestHelper.getScopeGroupId());
	}

	public List<CommerceAddress> getBillingCommerceAddresses(
			long commerceAccountId, long companyId)
		throws PortalException {

		return _commerceAddressService.getBillingCommerceAddresses(
			companyId, CommerceAccount.class.getName(), commerceAccountId);
	}

	public CommerceAccount getCommerceAccount() {
		return _commerceAccount;
	}

	public long getCommerceAccountId() {
		long commerceAccountId = 0;

		if (_commerceAccount != null) {
			commerceAccountId = _commerceAccount.getCommerceAccountId();
		}

		return commerceAccountId;
	}

	public CommerceOrder getCommerceOrder() throws PortalException {
		long commerceOrderId = getCommerceOrderId();

		if (commerceOrderId > 0) {
			return _commerceOrderService.fetchCommerceOrder(
				getCommerceOrderId());
		}

		String commerceOrderUuid = ParamUtil.getString(
			_httpServletRequest, "commerceOrderUuid");

		return _commerceOrderService.fetchCommerceOrder(
			commerceOrderUuid, _cpRequestHelper.getChannelGroupId());
	}

	public String getCommerceOrderDate(CommerceOrder commerceOrder) {
		Date orderDate = commerceOrder.getCreateDate();

		if (commerceOrder.getOrderDate() != null) {
			orderDate = commerceOrder.getOrderDate();
		}

		return _commerceOrderDateFormatDate.format(orderDate);
	}

	public long getCommerceOrderId() {
		return ParamUtil.getLong(_httpServletRequest, "commerceOrderId");
	}

	public String getCommerceOrderItemsDetailURL(long commerceOrderId) {
		LiferayPortletResponse liferayPortletResponse =
			_cpRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "viewCommerceOrderItems");
		portletURL.setParameter("redirect", _cpRequestHelper.getCurrentURL());
		portletURL.setParameter(
			"commerceOrderId", String.valueOf(commerceOrderId));

		return portletURL.toString();
	}

	public CommerceOrderNote getCommerceOrderNote() throws PortalException {
		if ((_commerceOrderNote == null) && (_commerceOrderNoteId > 0)) {
			_commerceOrderNote = _commerceOrderNoteService.getCommerceOrderNote(
				_commerceOrderNoteId);
		}

		return _commerceOrderNote;
	}

	public List<CommerceOrderNote> getCommerceOrderNotes(
			CommerceOrder commerceOrder)
		throws PortalException {

		if (hasModelPermission(
				commerceOrder,
				CommerceOrderActionKeys.
					MANAGE_COMMERCE_ORDER_RESTRICTED_NOTES)) {

			return _commerceOrderNoteService.getCommerceOrderNotes(
				commerceOrder.getCommerceOrderId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);
		}

		return _commerceOrderNoteService.getCommerceOrderNotes(
			commerceOrder.getCommerceOrderId(), false);
	}

	public int getCommerceOrderNotesCount(CommerceOrder commerceOrder)
		throws PortalException {

		if (hasModelPermission(commerceOrder, ActionKeys.UPDATE_DISCUSSION)) {
			return _commerceOrderNoteService.getCommerceOrderNotesCount(
				commerceOrder.getCommerceOrderId());
		}

		return _commerceOrderNoteService.getCommerceOrderNotesCount(
			commerceOrder.getCommerceOrderId(), false);
	}

	public String getCommerceOrderPaymentMethodName(CommerceOrder commerceOrder)
		throws PortalException {

		String commercePaymentMethodKey =
			commerceOrder.getCommercePaymentMethodKey();

		if (Validator.isNull(commercePaymentMethodKey)) {
			return StringPool.BLANK;
		}

		CommercePaymentMethodGroupRel commercePaymentMethod =
			_commercePaymentMethodGroupRelService.
				getCommercePaymentMethodGroupRel(
					commerceOrder.getGroupId(), commercePaymentMethodKey);

		if (commercePaymentMethod == null) {
			return StringPool.BLANK;
		}

		String name = commercePaymentMethod.getName(
			_cpRequestHelper.getLocale());

		if (!commercePaymentMethod.isActive()) {
			StringBundler sb = new StringBundler(4);

			sb.append(name);
			sb.append(" (");
			sb.append(
				LanguageUtil.get(_cpRequestHelper.getRequest(), "inactive"));
			sb.append(CharPool.CLOSE_PARENTHESIS);

			name = sb.toString();
		}

		return name;
	}

	public CommerceOrderPrice getCommerceOrderPrice() throws PortalException {
		return _commerceOrderPriceCalculation.getCommerceOrderPrice(
			getCommerceOrder(), _commerceContext);
	}

	public List<CommerceOrder> getCommerceOrders() throws PortalException {
		if (_commerceOrders != null) {
			return _commerceOrders;
		}

		String keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		if (isOpenOrderContentPortlet()) {
			_commerceOrders =
				_commerceOrderService.getUserPendingCommerceOrders(
					_cpRequestHelper.getCompanyId(),
					_cpRequestHelper.getChannelGroupId(), keywords,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}
		else {
			_commerceOrders = _commerceOrderService.getUserPlacedCommerceOrders(
				_cpRequestHelper.getCompanyId(),
				_cpRequestHelper.getChannelGroupId(), keywords,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}

		return _commerceOrders;
	}

	public String getCommerceOrderStatus(CommerceOrder commerceOrder) {
		return LanguageUtil.get(
			_httpServletRequest,
			CommerceOrderConstants.getOrderStatusLabel(
				commerceOrder.getOrderStatus()));
	}

	public String getCommerceOrderTime(CommerceOrder commerceOrder) {
		Date orderDate = commerceOrder.getCreateDate();

		if (commerceOrder.getOrderDate() != null) {
			orderDate = commerceOrder.getOrderDate();
		}

		return _commerceOrderDateFormatTime.format(orderDate);
	}

	public String getCommerceOrderTotal(CommerceOrder commerceOrder)
		throws PortalException {

		CommerceMoney totalCommerceMoney =
			_commerceOrderPriceCalculation.getTotal(
				commerceOrder, _commerceContext);

		if (totalCommerceMoney == null) {
			return StringPool.BLANK;
		}

		return totalCommerceMoney.format(_cpRequestHelper.getLocale());
	}

	public String getCommercePriceDisplayType() {
		CommerceChannel commerceChannel = fetchCommerceChannel();

		return commerceChannel.getPriceDisplayType();
	}

	public List<CommerceShipmentItem> getCommerceShipmentItems(
			long commerceOrderItemId)
		throws PortalException {

		return _commerceShipmentItemService.
			getCommerceShipmentItemsByCommerceOrderItemId(commerceOrderItemId);
	}

	public String getCommerceShipmentStatusLabel(int status) {
		return LanguageUtil.get(
			_httpServletRequest,
			CommerceShipmentConstants.getShipmentStatusLabel(status));
	}

	public String getDisplayStyle(String portletId)
		throws ConfigurationException {

		if (Validator.isNull(portletId)) {
			return StringPool.BLANK;
		}
		else if (portletId.equals(
					CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT)) {

			CommerceOpenOrderContentPortletInstanceConfiguration
				commerceOpenOrderContentPortletInstanceConfiguration =
					_portletDisplay.getPortletInstanceConfiguration(
						CommerceOpenOrderContentPortletInstanceConfiguration.
							class);

			return commerceOpenOrderContentPortletInstanceConfiguration.
				displayStyle();
		}
		else if (portletId.equals(CommercePortletKeys.COMMERCE_ORDER_CONTENT)) {
			CommerceOrderContentPortletInstanceConfiguration
				commerceOrderContentPortletInstanceConfiguration =
					_portletDisplay.getPortletInstanceConfiguration(
						CommerceOrderContentPortletInstanceConfiguration.class);

			return commerceOrderContentPortletInstanceConfiguration.
				displayStyle();
		}

		return StringPool.BLANK;
	}

	public long getDisplayStyleGroupId(String portletId)
		throws ConfigurationException {

		if (Validator.isNull(portletId)) {
			return _cpRequestHelper.getScopeGroupId();
		}
		else if (portletId.equals(
					CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT)) {

			CommerceOpenOrderContentPortletInstanceConfiguration
				commerceOpenOrderContentPortletInstanceConfiguration =
					_portletDisplay.getPortletInstanceConfiguration(
						CommerceOpenOrderContentPortletInstanceConfiguration.
							class);

			return commerceOpenOrderContentPortletInstanceConfiguration.
				displayStyleGroupId();
		}
		else if (portletId.equals(CommercePortletKeys.COMMERCE_ORDER_CONTENT)) {
			CommerceOrderContentPortletInstanceConfiguration
				commerceOrderContentPortletInstanceConfiguration =
					_portletDisplay.getPortletInstanceConfiguration(
						CommerceOrderContentPortletInstanceConfiguration.class);

			return commerceOrderContentPortletInstanceConfiguration.
				displayStyleGroupId();
		}

		return _cpRequestHelper.getScopeGroupId();
	}

	public List<DropdownItem> getDropdownItems() {
		List<DropdownItem> headerDropdownItems = new ArrayList<>();

		DropdownItem headerDropdownItem1 = new DropdownItem();

		headerDropdownItem1.setHref("/first-link");
		headerDropdownItem1.setIcon("home");
		headerDropdownItem1.setLabel("First link");

		headerDropdownItems.add(headerDropdownItem1);

		DropdownItem headerDropdownItem2 = new DropdownItem();

		headerDropdownItem2.setActive(true);
		headerDropdownItem2.setIcon("blogs");
		headerDropdownItem2.setHref("/second-link");
		headerDropdownItem2.setLabel("Second link");

		headerDropdownItems.add(headerDropdownItem2);

		return headerDropdownItems;
	}

	public List<HeaderActionModel> getHeaderActionModels()
		throws PortalException {

		return Collections.emptyList();
	}

	public String getLocalizedPercentage(BigDecimal percentage, Locale locale)
		throws PortalException {

		CommerceOrder commerceOrder = getCommerceOrder();

		CommerceCurrency commerceCurrency = commerceOrder.getCommerceCurrency();

		return _percentageFormatter.getLocalizedPercentage(
			locale, commerceCurrency.getMaxFractionDigits(),
			commerceCurrency.getMinFractionDigits(), percentage);
	}

	public PortletURL getPortletURL() throws PortalException {
		LiferayPortletResponse liferayPortletResponse =
			_cpRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		String delta = ParamUtil.getString(_httpServletRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String deltaEntry = ParamUtil.getString(
			_httpServletRequest, "deltaEntry");

		if (Validator.isNotNull(deltaEntry)) {
			portletURL.setParameter("deltaEntry", deltaEntry);
		}

		return portletURL;
	}

	public List<CommerceAddress> getShippingCommerceAddresses(
			long commerceAccountId, long companyId)
		throws PortalException {

		return _commerceAddressService.getShippingCommerceAddresses(
			companyId, CommerceAccount.class.getName(), commerceAccountId);
	}

	public boolean hasModelPermission(
			CommerceOrder commerceOrder, String actionId)
		throws PortalException {

		return _modelResourcePermission.contains(
			_cpRequestHelper.getPermissionChecker(), commerceOrder, actionId);
	}

	public boolean hasModelPermission(long commerceOrderId, String actionId)
		throws PortalException {

		return _modelResourcePermission.contains(
			_cpRequestHelper.getPermissionChecker(), commerceOrderId, actionId);
	}

	public boolean hasPermission(String actionId) {
		return _portletResourcePermission.contains(
			_cpRequestHelper.getPermissionChecker(),
			_cpRequestHelper.getScopeGroupId(), actionId);
	}

	public boolean isCommerceSiteTypeB2C() {
		if (_commerceContext.getCommerceSiteType() ==
				CommerceAccountConstants.SITE_TYPE_B2C) {

			return true;
		}

		return false;
	}

	public boolean isOpenOrderContentPortlet() {
		String portletName = _cpRequestHelper.getPortletName();

		return portletName.equals(
			CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT);
	}

	public boolean isShowPurchaseOrderNumber() throws PortalException {
		try {
			CommerceOrderFieldsConfiguration commerceOrderFieldsConfiguration =
				ConfigurationProviderUtil.getConfiguration(
					CommerceOrderFieldsConfiguration.class,
					new GroupServiceSettingsLocator(
						_cpRequestHelper.getChannelGroupId(),
						CommerceConstants.SERVICE_NAME_ORDER));

			return commerceOrderFieldsConfiguration.showPurchaseOrderNumber();
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderContentDisplayContext.class);

	private final CommerceAccount _commerceAccount;
	private final CommerceAddressService _commerceAddressService;
	private final CommerceChannelLocalService _commerceChannelLocalService;
	private final CommerceContext _commerceContext;
	private final Format _commerceOrderDateFormatDate;
	private final Format _commerceOrderDateFormatTime;
	private CommerceOrderNote _commerceOrderNote;
	private final long _commerceOrderNoteId;
	private final CommerceOrderNoteService _commerceOrderNoteService;
	private final CommerceOrderPriceCalculation _commerceOrderPriceCalculation;
	private List<CommerceOrder> _commerceOrders;
	private final CommerceOrderService _commerceOrderService;
	private final CommercePaymentMethodGroupRelService
		_commercePaymentMethodGroupRelService;
	private final CommerceShipmentItemService _commerceShipmentItemService;
	private final CPRequestHelper _cpRequestHelper;
	private final HttpServletRequest _httpServletRequest;
	private final ModelResourcePermission<CommerceOrder>
		_modelResourcePermission;
	private final PercentageFormatter _percentageFormatter;
	private final PortletDisplay _portletDisplay;
	private final PortletResourcePermission _portletResourcePermission;

}