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

package com.liferay.commerce.internal.order.term.contributor;

import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.constants.CommerceDefinitionTermConstants;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.model.CommerceShippingOption;
import com.liferay.commerce.order.CommerceDefinitionTermContributor;
import com.liferay.commerce.payment.exception.NoSuchPaymentMethodGroupRelException;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelLocalService;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionLocalService;
import com.liferay.commerce.util.CommerceShippingEngineRegistry;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ListTypeService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.Format;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	immediate = true,
	property = {
		"commerce.definition.term.contributor.key=" + CommerceOrderCommerceDefinitionTermContributor.KEY,
		"commerce.notification.type.key=" + CommerceOrderConstants.ORDER_NOTIFICATION_AWAITING_SHIPMENT,
		"commerce.notification.type.key=" + CommerceOrderConstants.ORDER_NOTIFICATION_COMPLETED,
		"commerce.notification.type.key=" + CommerceOrderConstants.ORDER_NOTIFICATION_PARTIALLY_SHIPPED,
		"commerce.notification.type.key=" + CommerceOrderConstants.ORDER_NOTIFICATION_PLACED,
		"commerce.notification.type.key=" + CommerceOrderConstants.ORDER_NOTIFICATION_PROCESSING,
		"commerce.notification.type.key=" + CommerceOrderConstants.ORDER_NOTIFICATION_SHIPPED
	},
	service = CommerceDefinitionTermContributor.class
)
public class CommerceOrderCommerceDefinitionTermContributor
	implements CommerceDefinitionTermContributor {

	public static final String KEY =
		CommerceDefinitionTermConstants.
			BODY_AND_SUBJECT_DEFINITION_TERMS_CONTRIBUTOR;

	@Override
	public String getFilledTerm(String term, Object object, Locale locale)
		throws PortalException {

		if (!(object instanceof CommerceOrder)) {
			return term;
		}

		CommerceOrder commerceOrder = (CommerceOrder)object;

		if (term.equals(_DELIVERY_TERMS_DESCRIPTION)) {
			return commerceOrder.getDeliveryCommerceTermEntryDescription();
		}

		if (term.equals(_ORDER_BILLING_ADDRESS)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Processing billing address term");
			}

			return _formatAddressTerm(
				commerceOrder.getBillingAddress(), locale);
		}

		if (term.equals(_ORDER_CREATOR)) {
			return _getOrderCreatorTerm(commerceOrder);
		}

		if (term.equals(_ORDER_CREATOR_USER_FIRST_NAME)) {
			User user = _userLocalService.getUser(commerceOrder.getUserId());

			return user.getFirstName();
		}

		if (term.equals(_ORDER_CREATOR_USER_LAST_NAME)) {
			User user = _userLocalService.getUser(commerceOrder.getUserId());

			return user.getLastName();
		}

		if (term.equals(_ORDER_CREATOR_USER_TITLE)) {
			return _getOrderCreatorUserTitleTerm(commerceOrder, locale);
		}

		if (term.equals(_ORDER_CURRENCY_CODE)) {
			CommerceCurrency commerceCurrency =
				commerceOrder.getCommerceCurrency();

			return commerceCurrency.getCode();
		}

		if (term.equals(_ORDER_CURRENCY_SYMBOL)) {
			CommerceCurrency commerceCurrency =
				commerceOrder.getCommerceCurrency();

			return commerceCurrency.getSymbol();
		}

		if (term.equals(_ORDER_DATE)) {
			return _getOrderDateTerm(commerceOrder, locale);
		}

		if (term.equals(_ORDER_EXTERNAL_REFERENCE_CODE)) {
			return commerceOrder.getExternalReferenceCode();
		}

		if (term.equals(_ORDER_ID)) {
			return String.valueOf(commerceOrder.getCommerceOrderId());
		}

		if (term.equals(_ORDER_ITEMS)) {
			return _getOrderItemsTerm(commerceOrder, locale);
		}

		if (term.equals(_ORDER_PAYMENT_METHOD)) {
			return _getOrderPaymentMethodTerm(commerceOrder, locale);
		}

		if (term.equals(_ORDER_SHIPPING_ADDRESS)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Processing shipping address term");
			}

			return _formatAddressTerm(
				commerceOrder.getShippingAddress(), locale);
		}

		if (term.equals(_ORDER_SHIPPING_OPTION)) {
			return _getOrderShippinhOptionTerm(commerceOrder, locale);
		}

		if (term.equals(_ORDER_SHIPPING_TOTAL)) {
			return _formatAmount(
				commerceOrder, commerceOrder.getShippingAmount());
		}

		if (term.equals(_ORDER_SHIPPING_WITH_TAX_TOTAL)) {
			return _formatAmount(
				commerceOrder, commerceOrder.getShippingWithTaxAmount());
		}

		if (term.equals(_ORDER_TAX_TOTAL)) {
			return _formatAmount(commerceOrder, commerceOrder.getTaxAmount());
		}

		if (term.equals(_ORDER_TOTAL)) {
			return _formatAmount(commerceOrder, commerceOrder.getTotal());
		}

		if (term.equals(_ORDER_WITH_TAX_TOTAL)) {
			return _formatAmount(
				commerceOrder, commerceOrder.getTotalWithTaxAmount());
		}

		if (term.equals(_ORDER_URL)) {
			return _getOrderUrlTerm(commerceOrder);
		}

		if (term.equals(_PAYMENT_TERMS_DESCRIPTION)) {
			return commerceOrder.getPaymentCommerceTermEntryDescription();
		}

		return term;
	}

	@Override
	public String getLabel(String term, Locale locale) {
		return _language.get(locale, _languageKeys.get(term));
	}

	@Override
	public List<String> getTerms() {
		return new ArrayList<>(_languageKeys.keySet());
	}

	private String _formatAddressTerm(
		CommerceAddress commerceAddress, Locale locale) {

		if (commerceAddress == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Commerce address is null");
			}

			return StringPool.BLANK;
		}

		StringBundler addressSB = new StringBundler(commerceAddress.getName());

		addressSB.append("<br/>");

		addressSB.append(commerceAddress.getStreet1());
		addressSB.append("<br/>");

		if (!Validator.isBlank(commerceAddress.getStreet2())) {
			addressSB.append(commerceAddress.getStreet2());
			addressSB.append("<br/>");
		}

		if (!Validator.isBlank(commerceAddress.getStreet3())) {
			addressSB.append(commerceAddress.getStreet3());
			addressSB.append("<br/>");
		}

		addressSB.append(commerceAddress.getCity());
		addressSB.append(StringPool.COMMA_AND_SPACE);
		addressSB.append(commerceAddress.getZip());
		addressSB.append("<br/>");

		try {
			Region region = commerceAddress.getRegion();

			if (region != null) {
				addressSB.append(region.getName());
				addressSB.append(StringPool.COMMA_AND_SPACE);
			}

			Country country = commerceAddress.getCountry();

			if (country != null) {
				addressSB.append(country.getTitle(locale));
			}
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"It was not possible to get either the country or region " +
						"for this commerce address",
					portalException);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Adding address term to the notification: " +
					addressSB.toString());
		}

		return addressSB.toString();
	}

	private String _formatAmount(CommerceOrder commerceOrder, BigDecimal amount)
		throws PortalException {

		if (amount != null) {
			CommerceCurrency commerceCurrency =
				commerceOrder.getCommerceCurrency();

			return String.valueOf(commerceCurrency.round(amount));
		}

		return "";
	}

	private String _getOrderCreatorTerm(CommerceOrder commerceOrder)
		throws PortalException {

		CommerceAccount commerceAccount = commerceOrder.getCommerceAccount();

		if (commerceAccount.getType() ==
				CommerceAccountConstants.ACCOUNT_TYPE_PERSONAL) {

			User user = _userLocalService.getUser(commerceAccount.getUserId());

			return user.getFullName(true, true);
		}

		return commerceAccount.getName();
	}

	private String _getOrderCreatorUserTitleTerm(
			CommerceOrder commerceOrder, Locale locale)
		throws PortalException {

		User user = _userLocalService.getUser(commerceOrder.getUserId());

		Contact contact = user.getContact();

		long prefixListTypeId = contact.getPrefixListTypeId();

		if (prefixListTypeId > 0) {
			ListType listType = _listTypeService.getListType(prefixListTypeId);

			return _language.get(locale, listType.getName());
		}

		return "";
	}

	private String _getOrderDateTerm(CommerceOrder commerceOrder, Locale locale)
		throws PortalException {

		User user = _userLocalService.getUser(commerceOrder.getUserId());

		Format commerceOrderDateFormatDate = FastDateFormatFactoryUtil.getDate(
			DateFormat.MEDIUM, locale, user.getTimeZone());

		Format commerceOrderDateFormatTime = FastDateFormatFactoryUtil.getTime(
			DateFormat.MEDIUM, locale, user.getTimeZone());

		Date orderDate = commerceOrder.getOrderDate();

		return commerceOrderDateFormatDate.format(orderDate) + " " +
			commerceOrderDateFormatTime.format(orderDate);
	}

	private String _getOrderItemsTerm(
		CommerceOrder commerceOrder, Locale locale) {

		if (_log.isDebugEnabled()) {
			_log.debug("Processing order items term");
		}

		if (commerceOrder == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Trying to get the item list for an order without an " +
						"order object");
			}

			return StringPool.BLANK;
		}

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		if (ListUtil.isEmpty(commerceOrderItems)) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"This order has no linked order items to be included in " +
						"the mail notification");
			}

			return StringPool.BLANK;
		}

		StringBundler orderItemsTableSB = new StringBundler(
			"<table style=\"border: 1px solid black;\">");

		orderItemsTableSB.append("<tr><th style=\"border: 1px solid black;\">");
		orderItemsTableSB.append(_language.get(locale, "product-name"));
		orderItemsTableSB.append("</th>");
		orderItemsTableSB.append("<th style=\"border: 1px solid black;\">");
		orderItemsTableSB.append(_language.get(locale, "sku"));
		orderItemsTableSB.append("</th>");
		orderItemsTableSB.append("<th style=\"border: 1px solid black;\">");
		orderItemsTableSB.append(_language.get(locale, "quantity"));
		orderItemsTableSB.append("</th></tr>");

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			orderItemsTableSB.append(
				"<tr><td style=\"border: 1px solid black;\">");
			orderItemsTableSB.append(commerceOrderItem.getName(locale));
			orderItemsTableSB.append(
				"</td><td style=\"border: 1px solid black;\">");
			orderItemsTableSB.append(commerceOrderItem.getSku());
			orderItemsTableSB.append(
				"</td><td style=\"border: 1px solid black;\">");
			orderItemsTableSB.append(commerceOrderItem.getQuantity());
			orderItemsTableSB.append("</td></tr>");
		}

		orderItemsTableSB.append("</table>");

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Order items table built successfully: " +
					orderItemsTableSB.toString());
		}

		return orderItemsTableSB.toString();
	}

	private String _getOrderPaymentMethodTerm(
			CommerceOrder commerceOrder, Locale locale)
		throws NoSuchPaymentMethodGroupRelException {

		if (!Validator.isBlank(commerceOrder.getCommercePaymentMethodKey())) {
			CommercePaymentMethodGroupRel commercePaymentMethodGroupRel =
				_commercePaymentMethodGroupRelLocalService.
					getCommercePaymentMethodGroupRel(
						commerceOrder.getGroupId(),
						commerceOrder.getCommercePaymentMethodKey());

			return commercePaymentMethodGroupRel.getName(locale);
		}

		return "";
	}

	private String _getOrderShippinhOptionTerm(
			CommerceOrder commerceOrder, Locale locale)
		throws PortalException {

		CommerceShippingFixedOption commerceShippingFixedOption =
			_commerceShippingFixedOptionLocalService.
				fetchCommerceShippingFixedOption(
					commerceOrder.getCompanyId(),
					commerceOrder.getShippingOptionName());

		if (commerceShippingFixedOption != null) {
			return commerceShippingFixedOption.getName(locale);
		}

		CommerceShippingMethod commerceShippingMethod =
			commerceOrder.getCommerceShippingMethod();

		if (commerceShippingMethod == null) {
			CommerceShippingEngine commerceShippingEngine =
				_commerceShippingEngineRegistry.getCommerceShippingEngine(
					commerceShippingMethod.getEngineKey());

			if (commerceShippingEngine != null) {
				List<CommerceShippingOption> commerceShippingOptions =
					commerceShippingEngine.getCommerceShippingOptions(
						null, commerceOrder, locale);

				Stream<CommerceShippingOption> commerceShippingOptionsStream =
					commerceShippingOptions.stream();

				return commerceShippingOptionsStream.filter(
					commerceShippingOption -> commerceShippingOption.getKey(
					).equals(
						commerceOrder.getShippingOptionName()
					)
				).findFirst(
				).map(
					CommerceShippingOption::getName
				).orElse(
					""
				);
			}
		}

		return "";
	}

	private String _getOrderUrlTerm(CommerceOrder commerceOrder)
		throws PortalException {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannelByOrderGroupId(
				commerceOrder.getGroupId());

		String portalURL = StringPool.BLANK;

		if (StringUtil.equals(
				commerceChannel.getType(),
				CommerceChannelConstants.CHANNEL_TYPE_SITE)) {

			Company company = _companyLocalService.getCompany(
				commerceOrder.getCompanyId());

			StringBuffer sb = new StringBuffer();

			sb.append(company.getPortalURL(commerceOrder.getGroupId()));

			Layout layout = null;

			if (commerceOrder.isOpen()) {
				layout = _layoutLocalService.getLayout(
					_portal.getPlidFromPortletId(
						commerceChannel.getSiteGroupId(),
						CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT));
			}
			else {
				layout = _layoutLocalService.getLayout(
					_portal.getPlidFromPortletId(
						commerceChannel.getSiteGroupId(),
						CommercePortletKeys.COMMERCE_ORDER_CONTENT));
			}

			if (layout.isPublicLayout()) {
				sb.append(_portal.getPathFriendlyURLPublic());
			}
			else {
				sb.append(_portal.getPathFriendlyURLPrivateGroup());
			}

			Group group = layout.getGroup();

			sb.append(group.getFriendlyURL());

			sb.append(layout.getFriendlyURL());

			sb.append(Portal.FRIENDLY_URL_SEPARATOR);

			sb.append("placed-order");

			sb.append(StringPool.FORWARD_SLASH);

			sb.append(commerceOrder.getCommerceOrderId());

			portalURL = sb.toString();
		}

		return portalURL;
	}

	private static final String _DELIVERY_TERMS_DESCRIPTION =
		"[%DELIVERY_TERMS_DESCRIPTION%]";

	private static final String _ORDER_BILLING_ADDRESS =
		"[%ORDER_BILLING_ADDRESS%]";

	private static final String _ORDER_CREATOR = "[%ORDER_CREATOR%]";

	private static final String _ORDER_CREATOR_USER_FIRST_NAME =
		"[%ORDER_CREATOR_USER_FIRST_NAME%]";

	private static final String _ORDER_CREATOR_USER_LAST_NAME =
		"[%ORDER_CREATOR_USER_LAST_NAME%]";

	private static final String _ORDER_CREATOR_USER_TITLE =
		"[%ORDER_CREATOR_USER_TITLE%]";

	private static final String _ORDER_CURRENCY_CODE =
		"[%ORDER_CURRENCY_CODE%]";

	private static final String _ORDER_CURRENCY_SYMBOL =
		"[%ORDER_CURRENCY_SYMBOL%]";

	private static final String _ORDER_DATE = "[%ORDER_DATE%]";

	private static final String _ORDER_EXTERNAL_REFERENCE_CODE =
		"[%ORDER_EXTERNAL_REFERENCE_CODE%]";

	private static final String _ORDER_ID = "[%ORDER_ID%]";

	private static final String _ORDER_ITEMS = "[%ORDER_ITEMS%]";

	private static final String _ORDER_PAYMENT_METHOD =
		"[%ORDER_PAYMENT_METHOD%]";

	private static final String _ORDER_SHIPPING_ADDRESS =
		"[%ORDER_SHIPPING_ADDRESS%]";

	private static final String _ORDER_SHIPPING_OPTION =
		"[%ORDER_SHIPPING_OPTION%]";

	private static final String _ORDER_SHIPPING_TOTAL =
		"[%ORDER_SHIPPING_TOTAL%]";

	private static final String _ORDER_SHIPPING_WITH_TAX_TOTAL =
		"[%ORDER_SHIPPING_WITH_TAX_TOTAL%]";

	private static final String _ORDER_TAX_TOTAL = "[%ORDER_TAX_TOTAL%]";

	private static final String _ORDER_TOTAL = "[%ORDER_TOTAL%]";

	private static final String _ORDER_URL = "[%ORDER_URL%]";

	private static final String _ORDER_WITH_TAX_TOTAL =
		"[%ORDER_WITH_TAX_TOTAL%]";

	private static final String _PAYMENT_TERMS_DESCRIPTION =
		"[%PAYMENT_TERMS_DESCRIPTION%]";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderCommerceDefinitionTermContributor.class);

	private static final Map<String, String> _languageKeys = HashMapBuilder.put(
		_DELIVERY_TERMS_DESCRIPTION, "delivery_terms_description"
	).put(
		_ORDER_BILLING_ADDRESS, "order_billing_address"
	).put(
		_ORDER_CREATOR, "order_creator"
	).put(
		_ORDER_CREATOR_USER_FIRST_NAME, "order_creator_user_first_name"
	).put(
		_ORDER_CREATOR_USER_LAST_NAME, "order_creator_user_last_name"
	).put(
		_ORDER_CREATOR_USER_TITLE, "order_creator_user_title"
	).put(
		_ORDER_CURRENCY_CODE, "order_currency_code"
	).put(
		_ORDER_CURRENCY_SYMBOL, "order_currency_symbol"
	).put(
		_ORDER_DATE, "order_date"
	).put(
		_ORDER_EXTERNAL_REFERENCE_CODE, "order_external_reference_code"
	).put(
		_ORDER_ID, "order_id"
	).put(
		_ORDER_ITEMS, "order_items"
	).put(
		_ORDER_PAYMENT_METHOD, "order_payment_method"
	).put(
		_ORDER_SHIPPING_ADDRESS, "order_shipping_address"
	).put(
		_ORDER_SHIPPING_OPTION, "order_shipping_option"
	).put(
		_ORDER_SHIPPING_TOTAL, "order_shipping_total"
	).put(
		_ORDER_SHIPPING_WITH_TAX_TOTAL, "order_shipping_with_tax_total"
	).put(
		_ORDER_TAX_TOTAL, "order_tax_total"
	).put(
		_ORDER_TOTAL, "order_total"
	).put(
		_ORDER_URL, "order_url"
	).put(
		_ORDER_WITH_TAX_TOTAL, "order_with_tax_total"
	).put(
		_PAYMENT_TERMS_DESCRIPTION, "payment_terms_description"
	).build();

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommercePaymentMethodGroupRelLocalService
		_commercePaymentMethodGroupRelLocalService;

	@Reference
	private CommerceShippingEngineRegistry _commerceShippingEngineRegistry;

	@Reference
	private CommerceShippingFixedOptionLocalService
		_commerceShippingFixedOptionLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private ListTypeService _listTypeService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}