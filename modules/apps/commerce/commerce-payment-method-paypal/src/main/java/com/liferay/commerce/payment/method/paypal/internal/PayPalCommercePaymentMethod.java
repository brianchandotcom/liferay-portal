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

package com.liferay.commerce.payment.method.paypal.internal;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommerceOrderPaymentConstants;
import com.liferay.commerce.constants.CommercePaymentConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.payment.method.CommercePaymentMethod;
import com.liferay.commerce.payment.method.paypal.internal.configuration.PayPalGroupServiceConfiguration;
import com.liferay.commerce.payment.method.paypal.internal.constants.PayPalCommercePaymentMethodConstants;
import com.liferay.commerce.payment.request.CommercePaymentRequest;
import com.liferay.commerce.payment.result.CommercePaymentResult;
import com.liferay.commerce.payment.result.CommerceSubscriptionStatusResult;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPSubscriptionInfo;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceAddressLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import com.paypal.api.payments.Agreement;
import com.paypal.api.payments.AgreementDetails;
import com.paypal.api.payments.AgreementStateDescriptor;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Authorization;
import com.paypal.api.payments.Capture;
import com.paypal.api.payments.Currency;
import com.paypal.api.payments.DetailedRefund;
import com.paypal.api.payments.Error;
import com.paypal.api.payments.ErrorDetails;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.MerchantPreferences;
import com.paypal.api.payments.Patch;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentDefinition;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.Plan;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.RefundRequest;
import com.paypal.api.payments.Sale;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import java.math.BigDecimal;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TimeZone;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	enabled = false, immediate = true,
	property = "commerce.payment.engine.method.key=" + PayPalCommercePaymentMethod.KEY,
	service = CommercePaymentMethod.class
)
public class PayPalCommercePaymentMethod implements CommercePaymentMethod {

	public static final String KEY = "paypal";

	public PayPalCommercePaymentMethod() {
		DecimalFormatSymbols decimalFormatSymbols =
			_payPalDecimalFormat.getDecimalFormatSymbols();

		decimalFormatSymbols.setDecimalSeparator(CharPool.PERIOD);
		decimalFormatSymbols.setGroupingSeparator(CharPool.COMMA);

		_payPalDecimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
	}

	@Override
	public boolean activateRecurringPayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		Agreement agreement = new Agreement();

		agreement.setId(commercePaymentRequest.getTransactionId());

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.getCommerceOrder(
				commercePaymentRequest.getCommerceOrderId());

		APIContext apiContext = _getAPIContext(commerceOrder.getGroupId());

		AgreementStateDescriptor agreementStateDescriptor =
			new AgreementStateDescriptor();

		agreementStateDescriptor.setNote(
			_getResource(
				commercePaymentRequest.getLocale(), "reactivate-agreement"));

		agreement.reActivate(apiContext, agreementStateDescriptor);

		Agreement updatedAgreement = Agreement.get(
			apiContext, agreement.getId());

		if (Objects.equals(
				PayPalCommercePaymentMethodConstants.ACTIVE,
				updatedAgreement.getState())) {

			return true;
		}

		return false;
	}

	@Override
	public CommercePaymentResult authorizePayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		return null;
	}

	@Override
	public CommercePaymentResult cancelPayment(
		CommercePaymentRequest commercePaymentRequest) {

		return new CommercePaymentResult(
			null, commercePaymentRequest.getCommerceOrderId(),
			CommerceOrderPaymentConstants.STATUS_CANCELLED, false, null, null,
			Collections.emptyList(), true);
	}

	@Override
	public boolean cancelRecurringPayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		Agreement agreement = new Agreement();

		agreement.setId(commercePaymentRequest.getTransactionId());

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.getCommerceOrder(
				commercePaymentRequest.getCommerceOrderId());

		APIContext apiContext = _getAPIContext(commerceOrder.getGroupId());

		AgreementStateDescriptor agreementStateDescriptor =
			new AgreementStateDescriptor();

		agreementStateDescriptor.setNote(
			_getResource(
				commercePaymentRequest.getLocale(), "cancel-agreement"));

		agreement.cancel(apiContext, agreementStateDescriptor);

		Agreement updatedAgreement = Agreement.get(
			apiContext, agreement.getId());

		if (Objects.equals(
				PayPalCommercePaymentMethodConstants.CANCELLED,
				updatedAgreement.getState())) {

			return true;
		}

		return false;
	}

	@Override
	public CommercePaymentResult capturePayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		boolean success = false;

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.getCommerceOrder(
				commercePaymentRequest.getCommerceOrderId());

		APIContext apiContext = _getAPIContext(commerceOrder.getGroupId());

		Authorization authorization = Authorization.get(
			apiContext, commercePaymentRequest.getTransactionId());

		Capture capture = new Capture();

		Amount amount = _getAmount(commerceOrder);

		capture.setAmount(amount);

		capture.setIsFinalCapture(true);

		Capture responseCapture = authorization.capture(apiContext, capture);

		if (PayPalCommercePaymentMethodConstants.AUTHORIZATION_STATE_COMPLETED.
				equals(responseCapture.getState())) {

			success = true;
		}

		List<String> messages = Arrays.asList(responseCapture.getReasonCode());

		return new CommercePaymentResult(
			null, commercePaymentRequest.getCommerceOrderId(),
			CommerceOrderConstants.PAYMENT_STATUS_PAID, false, null,
			responseCapture.getId(), messages, success);
	}

	@Override
	public CommercePaymentResult completePayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		try {
			boolean success = true;

			Payment payment = new Payment();

			PayPalCommercePaymentRequest payPalCommercePaymentRequest =
				(PayPalCommercePaymentRequest)commercePaymentRequest;

			payment.setId(payPalCommercePaymentRequest.getTransactionId());

			CommerceOrder commerceOrder =
				_commerceOrderLocalService.getCommerceOrder(
					payPalCommercePaymentRequest.getCommerceOrderId());

			APIContext apiContext = _getAPIContext(commerceOrder.getGroupId());

			PaymentExecution paymentExecution = new PaymentExecution();

			paymentExecution.setPayerId(
				payPalCommercePaymentRequest.getPayerId());

			payment.execute(apiContext, paymentExecution);

			if (PayPalCommercePaymentMethodConstants.PAYMENT_STATE_FAILED.
					equals(payment.getState())) {

				success = false;
			}

			List<String> messages = Arrays.asList(payment.getFailureReason());

			return new CommercePaymentResult(
				null, payPalCommercePaymentRequest.getCommerceOrderId(),
				CommerceOrderConstants.PAYMENT_STATUS_PAID, false, null, null,
				messages, success);
		}
		catch (PayPalRESTException payPalRESTException) {
			_log.error(payPalRESTException.getMessage(), payPalRESTException);

			List<String> resultMessages = _getErrorMessages(
				payPalRESTException);

			return new CommercePaymentResult(
				null, commercePaymentRequest.getCommerceOrderId(),
				CommerceOrderConstants.PAYMENT_STATUS_AUTHORIZED, true, null,
				null, resultMessages, false);
		}
	}

	@Override
	public CommercePaymentResult completeRecurringPayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		try {
			boolean success = true;

			Agreement agreement = new Agreement();

			PayPalCommercePaymentRequest payPalCommercePaymentRequest =
				(PayPalCommercePaymentRequest)commercePaymentRequest;

			agreement.setToken(payPalCommercePaymentRequest.getTransactionId());

			CommerceOrder commerceOrder =
				_commerceOrderLocalService.getCommerceOrder(
					commercePaymentRequest.getCommerceOrderId());

			APIContext apiContext = _getAPIContext(commerceOrder.getGroupId());

			Agreement activeAgreement = agreement.execute(
				apiContext, agreement.getToken());

			if (PayPalCommercePaymentMethodConstants.PAYMENT_STATE_FAILED.
					equals(activeAgreement.getState())) {

				success = false;
			}

			List<String> messages = Arrays.asList(
				activeAgreement.getDescription());

			return new CommercePaymentResult(
				activeAgreement.getId(),
				commercePaymentRequest.getCommerceOrderId(),
				CommerceOrderConstants.PAYMENT_STATUS_PAID, false, null, null,
				messages, success);
		}
		catch (PayPalRESTException payPalRESTException) {
			_log.error(payPalRESTException.getMessage(), payPalRESTException);

			List<String> resultMessages = _getErrorMessages(
				payPalRESTException);

			return new CommercePaymentResult(
				null, commercePaymentRequest.getCommerceOrderId(),
				CommerceOrderConstants.PAYMENT_STATUS_AUTHORIZED, true, null,
				null, resultMessages, false);
		}
	}

	@Override
	public String getDescription(Locale locale) {
		return _getResource(locale, "paypal-description");
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getName(Locale locale) {
		return LanguageUtil.get(locale, KEY);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), this method will be removed
	 */
	@Deprecated
	@Override
	public int getOrderStatusUpdateMaxIntervalMinutes() {
		return 2880;
	}

	@Override
	public int getPaymentType() {
		return CommercePaymentConstants.
			COMMERCE_PAYMENT_METHOD_TYPE_ONLINE_REDIRECT;
	}

	@Override
	public String getServletPath() {
		return PayPalCommercePaymentMethodConstants.SERVLET_PATH;
	}

	/**
	 * @param commercePaymentRequest
	 * @throws Exception
	 * @deprecated As of Mueller (7.2.x), this method will be removed
	 */
	@Deprecated
	@Override
	public CommerceSubscriptionStatusResult getSubscriptionPaymentDetails(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.getCommerceOrder(
				commercePaymentRequest.getCommerceOrderId());

		APIContext apiContext = _getAPIContext(commerceOrder.getGroupId());

		Agreement agreement = Agreement.get(
			apiContext, commercePaymentRequest.getTransactionId());

		AgreementDetails agreementDetails = agreement.getAgreementDetails();

		long failedPaymentCount = GetterUtil.getLong(
			agreementDetails.getFailedPaymentCount());
		long cyclesRemaining = GetterUtil.getLong(
			agreementDetails.getCyclesRemaining());
		long cyclesCompleted = GetterUtil.getLong(
			agreementDetails.getCyclesCompleted());

		return new CommerceSubscriptionStatusResult(
			failedPaymentCount, cyclesRemaining, cyclesCompleted);
	}

	@Override
	public boolean getSubscriptionValidity(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.getCommerceOrder(
				commercePaymentRequest.getCommerceOrderId());

		APIContext apiContext = _getAPIContext(commerceOrder.getGroupId());

		Agreement agreement = Agreement.get(
			apiContext, commercePaymentRequest.getTransactionId());

		String agreementState = agreement.getState();

		if (Objects.equals(
				PayPalCommercePaymentMethodConstants.ACTIVE, agreementState)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isAuthorizeEnabled() {
		return true;
	}

	@Override
	public boolean isCancelEnabled() {
		return true;
	}

	@Override
	public boolean isCaptureEnabled() {
		return true;
	}

	@Override
	public boolean isCompleteEnabled() {
		return true;
	}

	@Override
	public boolean isCompleteRecurringEnabled() {
		return true;
	}

	@Override
	public boolean isPartialRefundEnabled() {
		return true;
	}

	@Override
	public boolean isProcessPaymentEnabled() {
		return true;
	}

	@Override
	public boolean isProcessRecurringEnabled() {
		return true;
	}

	@Override
	public boolean isRefundEnabled() {
		return true;
	}

	@Override
	public boolean isVoidEnabled() {
		return true;
	}

	@Override
	public CommercePaymentResult partiallyRefundPayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		boolean success = false;

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.getCommerceOrder(
				commercePaymentRequest.getCommerceOrderId());

		APIContext apiContext = _getAPIContext(commerceOrder.getGroupId());

		Sale sale = Sale.get(
			apiContext, commercePaymentRequest.getTransactionId());

		RefundRequest refundRequest = new RefundRequest();

		Amount amount = new Amount();

		CommerceCurrency commerceCurrency = commerceOrder.getCommerceCurrency();

		amount.setCurrency(StringUtil.toUpperCase(commerceCurrency.getCode()));

		amount.setTotal(
			_payPalDecimalFormat.format(commercePaymentRequest.getAmount()));

		refundRequest.setAmount(amount);

		DetailedRefund detailedRefund = sale.refund(apiContext, refundRequest);

		if (PayPalCommercePaymentMethodConstants.AUTHORIZATION_STATE_COMPLETED.
				equals(detailedRefund.getState())) {

			success = true;
		}

		List<String> messages = Arrays.asList(detailedRefund.getDescription());

		return new CommercePaymentResult(
			null, commercePaymentRequest.getCommerceOrderId(),
			CommerceOrderConstants.ORDER_STATUS_PARTIALLY_REFUNDED, false, null,
			null, messages, success);
	}

	@Override
	public CommercePaymentResult processPayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		boolean success = false;
		int status = CommerceOrderPaymentConstants.STATUS_FAILED;

		try {
			String url = null;

			CommerceOrder commerceOrder =
				_commerceOrderLocalService.getCommerceOrder(
					commercePaymentRequest.getCommerceOrderId());

			Payment payment = _getPayment(
				commercePaymentRequest, commerceOrder,
				PayPalCommercePaymentMethodConstants.INTENT_SALE);

			for (Links links : payment.getLinks()) {
				if (Objects.equals(
						PayPalCommercePaymentMethodConstants.APPROVAL_URL,
						links.getRel())) {

					url = links.getHref();

					break;
				}
			}

			if (Validator.isNull(url)) {
				throw new PortalException("Unable to get PayPal payment URL");
			}

			url = _http.addParameter(
				url, PayPalCommercePaymentMethodConstants.USER_ACTION,
				PayPalCommercePaymentMethodConstants.USER_ACTION_COMMIT);

			if (PayPalCommercePaymentMethodConstants.
					AUTHORIZATION_STATE_CREATED.equals(payment.getState())) {

				success = true;
				status = CommerceOrderConstants.PAYMENT_STATUS_AUTHORIZED;
			}

			List<String> messages = Arrays.asList(payment.getFailureReason());

			return new CommercePaymentResult(
				payment.getId(), commercePaymentRequest.getCommerceOrderId(),
				status, true, url, null, messages, success);
		}
		catch (PayPalRESTException payPalRESTException) {
			_log.error(payPalRESTException.getMessage(), payPalRESTException);

			List<String> resultMessages = _getErrorMessages(
				payPalRESTException);

			return new CommercePaymentResult(
				null, commercePaymentRequest.getCommerceOrderId(), status, true,
				null, null, resultMessages, success);
		}
	}

	@Override
	public CommercePaymentResult processRecurringPayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.getCommerceOrder(
				commercePaymentRequest.getCommerceOrderId());

		boolean success = false;
		int status = CommerceOrderPaymentConstants.STATUS_FAILED;

		try {
			String url = null;

			APIContext apiContext = _getAPIContext(commerceOrder.getGroupId());

			Plan plan = _getPlan(
				commercePaymentRequest, commerceOrder, apiContext,
				commercePaymentRequest.getLocale());

			if (plan == null) {
				return null;
			}

			Agreement agreement = _getAgreement(
				commerceOrder, apiContext, plan,
				commercePaymentRequest.getLocale());

			for (Links links : agreement.getLinks()) {
				if (Objects.equals(
						PayPalCommercePaymentMethodConstants.APPROVAL_URL,
						links.getRel())) {

					url = links.getHref();

					break;
				}
			}

			String token = agreement.getToken();

			if (PayPalCommercePaymentMethodConstants.
					AUTHORIZATION_STATE_CREATED.equalsIgnoreCase(
						plan.getState()) &&
				Validator.isNotNull(token)) {

				success = true;
				status = CommerceOrderConstants.PAYMENT_STATUS_AUTHORIZED;
			}

			List<String> messages = Arrays.asList(plan.getState());

			return new CommercePaymentResult(
				token, commercePaymentRequest.getCommerceOrderId(), status,
				true, url, null, messages, success);
		}
		catch (PayPalRESTException payPalRESTException) {
			_log.error(payPalRESTException.getMessage(), payPalRESTException);

			List<String> resultMessages = _getErrorMessages(
				payPalRESTException);

			return new CommercePaymentResult(
				null, commercePaymentRequest.getCommerceOrderId(), status, true,
				null, null, resultMessages, success);
		}
	}

	@Override
	public CommercePaymentResult refundPayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		boolean success = false;

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.getCommerceOrder(
				commercePaymentRequest.getCommerceOrderId());

		APIContext apiContext = _getAPIContext(commerceOrder.getGroupId());

		Sale sale = Sale.get(
			apiContext, commercePaymentRequest.getTransactionId());

		RefundRequest refundRequest = new RefundRequest();

		Amount amount = _getAmount(commerceOrder);

		refundRequest.setAmount(amount);

		DetailedRefund detailedRefund = sale.refund(apiContext, refundRequest);

		if (PayPalCommercePaymentMethodConstants.AUTHORIZATION_STATE_COMPLETED.
				equals(detailedRefund.getState())) {

			success = true;
		}

		List<String> messages = Arrays.asList(detailedRefund.getDescription());

		return new CommercePaymentResult(
			null, commercePaymentRequest.getCommerceOrderId(),
			CommerceOrderConstants.ORDER_STATUS_REFUNDED, false, null, null,
			messages, success);
	}

	@Override
	public boolean suspendRecurringPayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		Agreement agreement = new Agreement();

		agreement.setId(commercePaymentRequest.getTransactionId());

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.getCommerceOrder(
				commercePaymentRequest.getCommerceOrderId());

		APIContext apiContext = _getAPIContext(commerceOrder.getGroupId());

		AgreementStateDescriptor agreementStateDescriptor =
			new AgreementStateDescriptor();

		agreementStateDescriptor.setNote(
			_getResource(
				commercePaymentRequest.getLocale(), "suspend-agreement"));

		agreement.suspend(apiContext, agreementStateDescriptor);

		Agreement updatedAgreement = Agreement.get(
			apiContext, agreement.getId());

		if (Objects.equals(
				PayPalCommercePaymentMethodConstants.SUSPENDED,
				updatedAgreement.getState())) {

			return true;
		}

		return false;
	}

	@Override
	public CommercePaymentResult voidTransaction(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		boolean success = false;

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.getCommerceOrder(
				commercePaymentRequest.getCommerceOrderId());

		APIContext apiContext = _getAPIContext(commerceOrder.getGroupId());

		Authorization authorization = Authorization.get(
			apiContext, commercePaymentRequest.getTransactionId());

		authorization.doVoid(apiContext);

		if (PayPalCommercePaymentMethodConstants.AUTHORIZATION_STATE_VOIDED.
				equals(authorization.getState())) {

			success = true;
		}

		List<String> messages = Arrays.asList(authorization.getPendingReason());

		return new CommercePaymentResult(
			null, commercePaymentRequest.getCommerceOrderId(),
			CommerceOrderConstants.PAYMENT_STATUS_PENDING, false, null, null,
			messages, success);
	}

	private void _addItem(
		CommerceCurrency commerceCurrency, String description, boolean discount,
		List<Item> items, String name, BigDecimal amount) {

		Item item = new Item();

		item.setCurrency(StringUtil.toUpperCase(commerceCurrency.getCode()));
		item.setDescription(description);
		item.setName(name);

		BigDecimal price = amount;

		if (discount) {
			price = amount.multiply(new BigDecimal(-1));
		}

		item.setPrice(_payPalDecimalFormat.format(price));
		item.setQuantity(String.valueOf(1));

		items.add(item);
	}

	private Agreement _getAgreement(
			CommerceOrder commerceOrder, APIContext apiContext, Plan plan,
			Locale locale)
		throws Exception {

		// Create new agreement

		Agreement agreement = new Agreement();

		agreement.setName(_getResource(locale, "base-agreement"));
		agreement.setDescription(
			_getResource(locale, "base-agreement-description"));

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(_DATE_FORMAT);

		Calendar calendar = Calendar.getInstance(_utc);

		calendar.add(Calendar.DAY_OF_MONTH, 1);

		String date = simpleDateFormat.format(calendar.getTime());

		agreement.setStartDate(date);

		// Set plan ID

		Plan agreementPlan = new Plan();

		agreementPlan.setId(plan.getId());

		agreement.setPlan(agreementPlan);

		// Add payer details

		Payer payer = new Payer();

		payer.setPaymentMethod(KEY);

		agreement.setPayer(payer);

		CommerceAddress commerceAddress = commerceOrder.getShippingAddress();

		if (commerceAddress == null) {
			CommerceAccount commerceAccount =
				commerceOrder.getCommerceAccount();

			commerceAddress = _commerceAddressLocalService.fetchCommerceAddress(
				commerceAccount.getDefaultShippingAddressId());
		}

		if (commerceAddress == null) {
			commerceAddress = commerceOrder.getBillingAddress();
		}

		ShippingAddress shippingAddress = _getShippingAddress(commerceAddress);

		shippingAddress.setRecipientName(null);

		agreement.setShippingAddress(shippingAddress);

		return agreement.create(apiContext);
	}

	private Amount _getAmount(CommerceOrder commerceOrder)
		throws PortalException {

		Amount amount = new Amount();

		CommerceCurrency commerceCurrency = commerceOrder.getCommerceCurrency();

		amount.setCurrency(StringUtil.toUpperCase(commerceCurrency.getCode()));

		amount.setTotal(_payPalDecimalFormat.format(commerceOrder.getTotal()));

		return amount;
	}

	private APIContext _getAPIContext(long groupId) throws PortalException {
		PayPalGroupServiceConfiguration payPalGroupServiceConfiguration =
			_getPayPalGroupServiceConfiguration(groupId);

		return new APIContext(
			payPalGroupServiceConfiguration.clientId(),
			payPalGroupServiceConfiguration.clientSecret(),
			payPalGroupServiceConfiguration.mode());
	}

	private List<String> _getErrorMessages(
		PayPalRESTException payPalRESTException) {

		List<String> resultMessages = new ArrayList<>();

		Error details = payPalRESTException.getDetails();

		resultMessages.add(details.getName());
		resultMessages.add(details.getMessage());

		List<ErrorDetails> detailsList = details.getDetails();

		for (ErrorDetails errorDetails : detailsList) {
			StringBundler detailsSB = new StringBundler(3);

			detailsSB.append(errorDetails.getField());
			detailsSB.append(CharPool.SPACE);
			detailsSB.append(errorDetails.getIssue());

			resultMessages.add(detailsSB.toString());
		}

		return resultMessages;
	}

	private ItemList _getItemList(CommerceOrder commerceOrder, Locale locale)
		throws PortalException {

		ItemList itemList = new ItemList();

		itemList.setItems(_getItems(commerceOrder, locale));

		CommerceAddress commerceAddress = commerceOrder.getShippingAddress();

		if (commerceAddress != null) {
			itemList.setShippingAddress(_getShippingAddress(commerceAddress));
		}

		CommerceShippingMethod commerceShippingMethod =
			commerceOrder.getCommerceShippingMethod();

		if (commerceShippingMethod != null) {
			itemList.setShippingMethod(commerceShippingMethod.getName(locale));
		}

		return itemList;
	}

	private List<Item> _getItems(CommerceOrder commerceOrder, Locale locale)
		throws PortalException {

		String languageId = LanguageUtil.getLanguageId(locale);

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		List<Item> items = new ArrayList<>(commerceOrderItems.size());

		CommerceCurrency commerceCurrency = commerceOrder.getCommerceCurrency();

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			Item item = new Item();

			item.setCurrency(
				StringUtil.toUpperCase(commerceCurrency.getCode()));

			CPDefinition cpDefinition = commerceOrderItem.getCPDefinition();

			item.setDescription(cpDefinition.getShortDescription(languageId));

			item.setName(commerceOrderItem.getName(languageId));

			/*
			PayPal is checking if the item price is coherent with total.
			We have to send the 'single item' price calculating also discounts
			 */
			BigDecimal finalPrice = commerceOrderItem.getFinalPrice();

			item.setPrice(
				_payPalDecimalFormat.format(
					finalPrice.divide(
						new BigDecimal(commerceOrderItem.getQuantity()))));

			item.setQuantity(String.valueOf(commerceOrderItem.getQuantity()));
			item.setSku(commerceOrderItem.getSku());

			items.add(item);
		}

		items = _getOrderDiscounts(commerceOrder, locale, items);

		items = _getShipping(commerceOrder, locale, items);

		items = _getTaxes(commerceOrder, locale, items);

		return items;
	}

	private List<Item> _getOrderDiscounts(
			CommerceOrder commerceOrder, Locale locale, List<Item> items)
		throws PortalException {

		BigDecimal subtotalDiscountAmount =
			commerceOrder.getSubtotalDiscountAmount();

		CommerceCurrency commerceCurrency = commerceOrder.getCommerceCurrency();

		if ((subtotalDiscountAmount != null) &&
			(subtotalDiscountAmount.compareTo(BigDecimal.ZERO) > 0)) {

			_addItem(
				commerceCurrency,
				_getResource(locale, "paypal-subtotal-discount-description"),
				true, items, _getResource(locale, "paypal-subtotal-discount"),
				subtotalDiscountAmount);
		}

		BigDecimal totalDiscountAmount = commerceOrder.getTotalDiscountAmount();

		if ((totalDiscountAmount != null) &&
			(totalDiscountAmount.compareTo(BigDecimal.ZERO) > 0)) {

			_addItem(
				commerceCurrency,
				_getResource(locale, "paypal-total-discount-description"), true,
				items, _getResource(locale, "paypal-total-discount"),
				totalDiscountAmount);
		}

		return items;
	}

	private Payment _getPayment(
			CommercePaymentRequest commercePaymentRequest,
			CommerceOrder commerceOrder, String intent)
		throws PayPalRESTException, PortalException {

		Payment payment = new Payment();

		payment.setIntent(intent);

		Payer payer = new Payer();

		payer.setPaymentMethod(KEY);

		payment.setPayer(payer);

		RedirectUrls redirectUrls = new RedirectUrls();

		redirectUrls.setCancelUrl(commercePaymentRequest.getCancelUrl());
		redirectUrls.setReturnUrl(commercePaymentRequest.getReturnUrl());

		payment.setRedirectUrls(redirectUrls);

		payment.setTransactions(
			_getTransactions(
				commerceOrder, commercePaymentRequest.getLocale()));

		APIContext apiContext = _getAPIContext(commerceOrder.getGroupId());

		return payment.create(apiContext);
	}

	private PayPalGroupServiceConfiguration _getPayPalGroupServiceConfiguration(
			long groupId)
		throws PortalException {

		return _configurationProvider.getConfiguration(
			PayPalGroupServiceConfiguration.class,
			new GroupServiceSettingsLocator(
				groupId, PayPalCommercePaymentMethodConstants.SERVICE_NAME));
	}

	private Plan _getPlan(
			CommercePaymentRequest commercePaymentRequest,
			CommerceOrder commerceOrder, APIContext apiContext, Locale locale)
		throws PayPalRESTException, PortalException {

		CommerceCurrency commerceCurrency = commerceOrder.getCommerceCurrency();

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		List<PaymentDefinition> paymentDefinitions = new ArrayList<>(
			commerceOrderItems.size());

		CommerceOrderItem commerceOrderItem = commerceOrderItems.get(0);

		CPInstance cpInstance = commerceOrderItem.fetchCPInstance();

		if (cpInstance == null) {
			return null;
		}

		CPSubscriptionInfo cpSubscriptionInfo =
			cpInstance.getCPSubscriptionInfo();

		String subscriptionType = cpSubscriptionInfo.getSubscriptionType();

		if (subscriptionType.equals(CPConstants.MONTHLY_SUBSCRIPTION_TYPE)) {
			subscriptionType = PayPalCommercePaymentMethodConstants.MONTH;
		}
		else if (subscriptionType.equals(CPConstants.DAILY_SUBSCRIPTION_TYPE)) {
			subscriptionType = PayPalCommercePaymentMethodConstants.DAY;
		}
		else if (subscriptionType.equals(
					CPConstants.WEEKLY_SUBSCRIPTION_TYPE)) {

			subscriptionType = PayPalCommercePaymentMethodConstants.WEEK;
		}
		else if (subscriptionType.equals(
					CPConstants.YEARLY_SUBSCRIPTION_TYPE)) {

			subscriptionType = PayPalCommercePaymentMethodConstants.YEAR;
		}

		Currency amount = new Currency(
			commerceCurrency.getCode(),
			_payPalDecimalFormat.format(commerceOrderItem.getFinalPrice()));

		PaymentDefinition paymentDefinition = new PaymentDefinition(
			_getResource(locale, "payment-definition"),
			PayPalCommercePaymentMethodConstants.PAYMENT_DEFINITION_REGULAR,
			String.valueOf(cpSubscriptionInfo.getSubscriptionLength()),
			subscriptionType,
			String.valueOf(cpSubscriptionInfo.getMaxSubscriptionCycles()),
			amount);

		paymentDefinitions.add(paymentDefinition);

		String name = _getResource(locale, "payment-plan");
		String description = _getResource(locale, "payment-plan-description");

		String type = PayPalCommercePaymentMethodConstants.PLAN_FIXED;

		if (cpSubscriptionInfo.getMaxSubscriptionCycles() == 0) {
			type = PayPalCommercePaymentMethodConstants.PLAN_INFINITE;
		}

		Plan plan = new Plan(name, description, type);

		plan.setPaymentDefinitions(paymentDefinitions);

		MerchantPreferences merchantPreferences = new MerchantPreferences();

		merchantPreferences.setAutoBillAmount(
			PayPalCommercePaymentMethodConstants.AUTO_BILLING_AMOUNT_ENABLED);
		merchantPreferences.setCancelUrl(commercePaymentRequest.getCancelUrl());
		merchantPreferences.setInitialFailAmountAction(
			PayPalCommercePaymentMethodConstants.INITIAL_FAIL_AMOUNT_ACTION);
		merchantPreferences.setReturnUrl(commercePaymentRequest.getReturnUrl());

		PayPalGroupServiceConfiguration payPalGroupServiceConfiguration =
			_getPayPalGroupServiceConfiguration(commerceOrder.getGroupId());

		String attemptsMaxCount =
			payPalGroupServiceConfiguration.paymentAttemptsMaxCount();

		try {
			Integer.parseInt(attemptsMaxCount);
		}
		catch (NumberFormatException numberFormatException) {
			attemptsMaxCount = "0";
		}

		merchantPreferences.setMaxFailAttempts(attemptsMaxCount);

		plan.setMerchantPreferences(merchantPreferences);

		plan = plan.create(apiContext);

		return _updatePlan(apiContext, plan);
	}

	private String _getResource(Locale locale, String key) {
		if (locale == null) {
			locale = LocaleUtil.getSiteDefault();
		}

		ResourceBundle resourceBundle = _getResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, key);
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	private List<Item> _getShipping(
			CommerceOrder commerceOrder, Locale locale, List<Item> items)
		throws PortalException {

		BigDecimal shippingAmount = commerceOrder.getShippingAmount();

		CommerceCurrency commerceCurrency = commerceOrder.getCommerceCurrency();

		if ((shippingAmount != null) &&
			(shippingAmount.compareTo(BigDecimal.ZERO) > 0)) {

			_addItem(
				commerceCurrency,
				_getResource(locale, "paypal-shipping-description"), false,
				items, _getResource(locale, "paypal-shipping"), shippingAmount);

			BigDecimal shippingDiscountAmount =
				commerceOrder.getShippingDiscountAmount();

			if ((shippingDiscountAmount != null) &&
				(shippingDiscountAmount.compareTo(BigDecimal.ZERO) > 0)) {

				_addItem(
					commerceCurrency,
					_getResource(
						locale, "paypal-shipping-discount-description"),
					true, items,
					_getResource(locale, "paypal-shipping-discount"),
					shippingDiscountAmount);
			}
		}

		return items;
	}

	private ShippingAddress _getShippingAddress(CommerceAddress commerceAddress)
		throws PortalException {

		ShippingAddress shippingAddress = new ShippingAddress();

		if (commerceAddress != null) {
			shippingAddress.setCity(commerceAddress.getCity());

			CommerceCountry commerceCountry =
				commerceAddress.getCommerceCountry();

			shippingAddress.setCountryCode(
				commerceCountry.getTwoLettersISOCode());

			shippingAddress.setLine1(commerceAddress.getStreet1());
			shippingAddress.setLine2(commerceAddress.getStreet2());
			shippingAddress.setPostalCode(commerceAddress.getZip());
			shippingAddress.setRecipientName(commerceAddress.getName());

			CommerceRegion commerceRegion = commerceAddress.getCommerceRegion();

			if (commerceRegion != null) {
				shippingAddress.setState(commerceRegion.getCode());
			}
		}

		return shippingAddress;
	}

	private List<Item> _getTaxes(
			CommerceOrder commerceOrder, Locale locale, List<Item> items)
		throws PortalException {

		BigDecimal taxAmount = commerceOrder.getTaxAmount();

		if ((taxAmount != null) && (taxAmount.compareTo(BigDecimal.ZERO) > 0)) {
			_addItem(
				commerceOrder.getCommerceCurrency(),
				_getResource(locale, "paypal-taxes-description"), false, items,
				_getResource(locale, "paypal-taxes"), taxAmount);
		}

		return items;
	}

	private List<Transaction> _getTransactions(
			CommerceOrder commerceOrder, Locale locale)
		throws PortalException {

		Transaction transaction = new Transaction();

		transaction.setAmount(_getAmount(commerceOrder));
		transaction.setItemList(_getItemList(commerceOrder, locale));

		return Collections.singletonList(transaction);
	}

	private Plan _updatePlan(APIContext apiContext, Plan plan)
		throws PayPalRESTException {

		Patch patch = new Patch();

		patch.setOp(PayPalCommercePaymentMethodConstants.OPERATION_REPLACE);
		patch.setPath(StringPool.FORWARD_SLASH);

		patch.setValue(
			Collections.singletonMap(
				PayPalCommercePaymentMethodConstants.STATE,
				PayPalCommercePaymentMethodConstants.ACTIVE));

		plan.update(apiContext, Collections.singletonList(patch));

		return plan;
	}

	private static final String _DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss'Z'";

	private static final Log _log = LogFactoryUtil.getLog(
		PayPalCommercePaymentMethod.class);

	private static final DecimalFormat _payPalDecimalFormat = new DecimalFormat(
		"#,###.##");
	private static final TimeZone _utc = TimeZone.getTimeZone("UTC");

	@Reference
	private CommerceAddressLocalService _commerceAddressLocalService;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Http _http;

}