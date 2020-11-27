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

package com.liferay.commerce.shipping.engine.fedex.internal.util;

import com.fedex.ws.rate.v22.Address;
import com.fedex.ws.rate.v22.ClientDetail;
import com.fedex.ws.rate.v22.Contact;
import com.fedex.ws.rate.v22.DropoffType;
import com.fedex.ws.rate.v22.LinearUnits;
import com.fedex.ws.rate.v22.Money;
import com.fedex.ws.rate.v22.Notification;
import com.fedex.ws.rate.v22.NotificationSeverityType;
import com.fedex.ws.rate.v22.Party;
import com.fedex.ws.rate.v22.Payment;
import com.fedex.ws.rate.v22.PaymentType;
import com.fedex.ws.rate.v22.Payor;
import com.fedex.ws.rate.v22.RatePortType;
import com.fedex.ws.rate.v22.RateReply;
import com.fedex.ws.rate.v22.RateReplyDetail;
import com.fedex.ws.rate.v22.RateRequest;
import com.fedex.ws.rate.v22.RateRequestType;
import com.fedex.ws.rate.v22.RateServiceLocator;
import com.fedex.ws.rate.v22.RatedShipmentDetail;
import com.fedex.ws.rate.v22.RequestedPackageLineItem;
import com.fedex.ws.rate.v22.RequestedShipment;
import com.fedex.ws.rate.v22.ReturnedRateType;
import com.fedex.ws.rate.v22.ServiceType;
import com.fedex.ws.rate.v22.ShipmentRateDetail;
import com.fedex.ws.rate.v22.TransactionDetail;
import com.fedex.ws.rate.v22.VersionId;
import com.fedex.ws.rate.v22.WebAuthenticationCredential;
import com.fedex.ws.rate.v22.WebAuthenticationDetail;
import com.fedex.ws.rate.v22.Weight;
import com.fedex.ws.rate.v22.WeightUnits;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.exception.CommerceShippingEngineException;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.model.CommerceShippingOption;
import com.liferay.commerce.model.Dimensions;
import com.liferay.commerce.product.constants.CPMeasurementUnitConstants;
import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.commerce.product.service.CPMeasurementUnitLocalService;
import com.liferay.commerce.shipping.engine.fedex.internal.configuration.FedExCommerceShippingEngineGroupServiceConfiguration;
import com.liferay.commerce.shipping.engine.fedex.internal.constants.FedExCommerceShippingEngineConstants;
import com.liferay.commerce.shipping.origin.locator.CommerceShippingOriginLocator;
import com.liferay.commerce.util.CommerceShippingHelper;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.axis.types.NonNegativeInteger;
import org.apache.axis.types.PositiveInteger;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class FedExCommerceShippingOptionHelper {

	public static String getCommerceShippingOptionLabel(
		String name, ResourceBundle resourceBundle) {

		return ResourceBundleUtil.getString(
			resourceBundle,
			FedExCommerceShippingEngineConstants.getServiceTypeLabel(name));
	}

	public FedExCommerceShippingOptionHelper(
			CommerceContext commerceContext, CommerceOrder commerceOrder,
			CommerceCurrencyLocalService commerceCurrencyLocalService,
			CommerceShippingHelper commerceShippingHelper,
			CommerceShippingOriginLocator commerceShippingOriginLocator,
			CPMeasurementUnitLocalService cpMeasurementUnitLocalService,
			ConfigurationProvider configurationProvider,
			ResourceBundle resourceBundle)
		throws Exception {

		_commerceContext = commerceContext;
		_commerceOrder = commerceOrder;
		_commerceCurrencyLocalService = commerceCurrencyLocalService;
		_commerceShippingHelper = commerceShippingHelper;
		_commerceShippingOriginLocator = commerceShippingOriginLocator;
		_cpMeasurementUnitLocalService = cpMeasurementUnitLocalService;
		_resourceBundle = resourceBundle;

		long groupId = _commerceOrder.getGroupId();

		_commerceCurrency =
			_commerceCurrencyLocalService.fetchPrimaryCommerceCurrency(
				_commerceOrder.getCompanyId());

		if (_commerceCurrency == null) {
			throw new CommerceShippingEngineException.MustSetPrimaryCurrency();
		}

		_dimensionCPMeasurementUnit = _getCPMeasurementUnit(
			CPMeasurementUnitConstants.TYPE_DIMENSION, LinearUnits._CM,
			LinearUnits._IN);
		_weightCPMeasurementUnit = _getCPMeasurementUnit(
			CPMeasurementUnitConstants.TYPE_WEIGHT, WeightUnits._KG,
			WeightUnits._LB);

		_linearUnits = LinearUnits.fromValue(
			StringUtil.toUpperCase(_dimensionCPMeasurementUnit.getKey()));
		_weightUnits = WeightUnits.fromValue(
			StringUtil.toUpperCase(_weightCPMeasurementUnit.getKey()));

		_shippingAddress = _commerceOrder.getShippingAddress();

		if (_shippingAddress == null) {
			throw new CommerceShippingEngineException.MustSetShippingAddress();
		}

		_fedExCommerceShippingEngineGroupServiceConfiguration =
			configurationProvider.getConfiguration(
				FedExCommerceShippingEngineGroupServiceConfiguration.class,
				new GroupServiceSettingsLocator(
					groupId,
					FedExCommerceShippingEngineConstants.SERVICE_NAME));

		_serviceTypes = SetUtil.fromArray(
			StringUtil.split(
				_fedExCommerceShippingEngineGroupServiceConfiguration.
					serviceTypes()));
	}

	public List<CommerceShippingOption> getCommerceShippingOptions()
		throws Exception {

		Map<String, List<BigDecimal>> rates = new HashMap<>();

		Map<CommerceAddress, List<CommerceOrderItem>> originAddresses =
			_commerceShippingOriginLocator.getOriginAddresses(_commerceOrder);

		for (Map.Entry<CommerceAddress, List<CommerceOrderItem>> entry :
				originAddresses.entrySet()) {

			try {
				_executeRateRequest(rates, entry.getValue(), entry.getKey());
			}
			catch (CommerceShippingEngineException
						commerceShippingEngineException) {

				_log.error(
					commerceShippingEngineException,
					commerceShippingEngineException);
			}
		}

		List<CommerceShippingOption> commerceShippingOptions = new ArrayList<>(
			_serviceTypes.size());

		for (Map.Entry<String, List<BigDecimal>> entry : rates.entrySet()) {
			List<BigDecimal> amounts = entry.getValue();

			if (amounts.size() < originAddresses.size()) {
				continue;
			}

			String name = entry.getKey();

			String label = getCommerceShippingOptionLabel(
				name, _resourceBundle);

			BigDecimal amount = BigDecimal.ZERO;

			for (BigDecimal amountCur : amounts) {
				amount = amount.add(amountCur);
			}

			commerceShippingOptions.add(
				new CommerceShippingOption(name, label, amount));
		}

		return commerceShippingOptions;
	}

	private void _executeRateRequest(
			Map<String, List<BigDecimal>> rates,
			List<CommerceOrderItem> commerceOrderItems,
			CommerceAddress originAddress)
		throws Exception {

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			if (!commerceOrderItem.isShippable() ||
				commerceOrderItem.isFreeShipping()) {

				commerceOrderItems.remove(commerceOrderItem);
			}
		}

		RateRequest rateRequest = _getRateRequest(
			commerceOrderItems, originAddress);

		RateServiceLocator rateServiceLocator = new RateServiceLocator();

		String url =
			_fedExCommerceShippingEngineGroupServiceConfiguration.url();

		if (Validator.isNotNull(url)) {
			rateServiceLocator.setRateServicePortEndpointAddress(url);
		}

		RatePortType ratePortType = rateServiceLocator.getRateServicePort();

		RateReply rateReply = ratePortType.getRates(rateRequest);

		NotificationSeverityType notificationSeverityType =
			rateReply.getHighestSeverity();
		RateReplyDetail[] rateReplyDetails = rateReply.getRateReplyDetails();

		if (ArrayUtil.isEmpty(rateReplyDetails) ||
			(!notificationSeverityType.equals(NotificationSeverityType.NOTE) &&
			 !notificationSeverityType.equals(
				 NotificationSeverityType.SUCCESS) &&
			 !notificationSeverityType.equals(
				 NotificationSeverityType.WARNING))) {

			throw new CommerceShippingEngineException.ServerError(
				_getErrorKVPs(rateReply.getNotifications()));
		}

		boolean useDiscountedRates =
			_fedExCommerceShippingEngineGroupServiceConfiguration.
				useDiscountedRates();

		for (RateReplyDetail rateReplyDetail : rateReplyDetails) {
			ServiceType serviceType = rateReplyDetail.getServiceType();

			String name = serviceType.getValue();

			if (!_serviceTypes.contains(name)) {
				continue;
			}

			BigDecimal amount = BigDecimal.ZERO;

			for (RatedShipmentDetail ratedShipmentDetail :
					rateReplyDetail.getRatedShipmentDetails()) {

				ShipmentRateDetail shipmentRateDetail =
					ratedShipmentDetail.getShipmentRateDetail();

				ReturnedRateType returnedRateType =
					shipmentRateDetail.getRateType();

				if ((useDiscountedRates &&
					 (returnedRateType.equals(
						 ReturnedRateType.PAYOR_ACCOUNT_PACKAGE) ||
					  returnedRateType.equals(
						  ReturnedRateType.PAYOR_ACCOUNT_SHIPMENT))) ||
					returnedRateType.equals(
						ReturnedRateType.PAYOR_LIST_PACKAGE) ||
					returnedRateType.equals(
						ReturnedRateType.PAYOR_LIST_SHIPMENT)) {

					amount = _getAmount(shipmentRateDetail.getTotalNetCharge());
				}
			}

			List<BigDecimal> amounts = rates.get(name);

			if (amounts == null) {
				amounts = new ArrayList<>();

				rates.put(name, amounts);
			}

			amounts.add(amount);
		}
	}

	private Address _getAddress(CommerceAddress commerceAddress)
		throws Exception {

		CommerceCountry commerceCountry =
			commerceAddress.fetchCommerceCountry();

		if (commerceCountry == null) {
			return null;
		}

		Address address = new Address();

		address.setCity(commerceAddress.getCity());
		address.setCountryCode(commerceCountry.getTwoLettersISOCode());
		address.setCountryName(commerceCountry.getName(LocaleUtil.US));

		address.setPostalCode(commerceAddress.getZip());

		if (_fedExCommerceShippingEngineGroupServiceConfiguration.
				useResidentialRates()) {

			address.setResidential(Boolean.TRUE);
		}

		CommerceRegion commerceRegion = commerceAddress.getCommerceRegion();

		if (commerceRegion != null) {
			address.setStateOrProvinceCode(commerceRegion.getCode());
		}

		List<String> streetLines = new ArrayList<>(3);

		streetLines.add(commerceAddress.getStreet1());

		String street2 = commerceAddress.getStreet2();

		if (Validator.isNotNull(street2)) {
			streetLines.add(street2);
		}

		String street3 = commerceAddress.getStreet3();

		if (Validator.isNotNull(street3)) {
			streetLines.add(street3);
		}

		address.setStreetLines(streetLines.toArray(new String[0]));

		return address;
	}

	private BigDecimal _getAmount(Money money) throws Exception {
		CommerceCurrency moneyCommerceCurrency = null;

		String code = money.getCurrency();

		List<CommerceCurrency> commerceCurrencies =
			_commerceCurrencyLocalService.getCommerceCurrencies(
				_commerceOrder.getCompanyId(), true);

		for (CommerceCurrency commerceCurrency : commerceCurrencies) {
			if (StringUtil.equalsIgnoreCase(code, commerceCurrency.getCode())) {
				moneyCommerceCurrency = commerceCurrency;

				break;
			}
		}

		if (moneyCommerceCurrency == null) {
			throw new CommerceShippingEngineException.MustSetCurrency(code);
		}

		BigDecimal amount = money.getAmount();

		return amount.multiply(moneyCommerceCurrency.getRate());
	}

	private ClientDetail _getClientDetail() {
		ClientDetail clientDetail = new ClientDetail();

		clientDetail.setAccountNumber(
			_fedExCommerceShippingEngineGroupServiceConfiguration.
				accountNumber());
		clientDetail.setMeterNumber(
			_fedExCommerceShippingEngineGroupServiceConfiguration.
				meterNumber());

		return clientDetail;
	}

	private Contact _getContact(CommerceAddress commerceAddress) {
		Contact contact = new Contact();

		contact.setPersonName(commerceAddress.getName());
		contact.setPhoneNumber(commerceAddress.getPhoneNumber());

		return contact;
	}

	private CPMeasurementUnit _getCPMeasurementUnit(int type, String... keys)
		throws Exception {

		List<CPMeasurementUnit> cpMeasurementUnits =
			_cpMeasurementUnitLocalService.getCPMeasurementUnits(
				_commerceOrder.getCompanyId(), keys, type);

		if (cpMeasurementUnits.isEmpty()) {
			throw new CommerceShippingEngineException.MustSetMeasurementUnit(
				keys);
		}

		for (CPMeasurementUnit cpMeasurementUnit : cpMeasurementUnits) {
			if (cpMeasurementUnit.isPrimary()) {
				return cpMeasurementUnit;
			}
		}

		return cpMeasurementUnits.get(0);
	}

	private List<KeyValuePair> _getErrorKVPs(Notification[] notifications) {
		List<KeyValuePair> errorKVPs = new ArrayList<>(notifications.length);

		for (Notification notification : notifications) {
			errorKVPs.add(
				new KeyValuePair(
					notification.getCode(), notification.getMessage()));
		}

		return errorKVPs;
	}

	private int _getFedExDimension(double dimension) {
		int fedExDimension = (int)Math.ceil(
			dimension * _dimensionCPMeasurementUnit.getRate());

		if (fedExDimension < 1) {
			fedExDimension = 1;
		}

		return fedExDimension;
	}

	private double _getFedExWeight(double weight) {
		double fedExWeight = weight * _weightCPMeasurementUnit.getRate();

		if (fedExWeight < 1) {
			fedExWeight = 1;
		}

		return fedExWeight;
	}

	private Money _getMoney(BigDecimal amount) {
		return new Money(_commerceCurrency.getCode(), amount);
	}

	private NonNegativeInteger _getNonnegativeInteger(int d) {
		return new NonNegativeInteger(String.valueOf(d));
	}

	private int _getPackageSize(
		int fedExWidth, int fedExHeight, int fedExDepth) {

		int girth = (fedExHeight * 2) + (fedExDepth * 2);

		return girth + fedExWidth;
	}

	private Party _getParty(CommerceAddress commerceAddress) throws Exception {
		Party party = new Party();

		party.setAddress(_getAddress(commerceAddress));
		party.setContact(_getContact(commerceAddress));

		return party;
	}

	private Payment _getPayment(String accountNumber) {
		Party party = new Party();

		party.setAccountNumber(accountNumber);

		Payor payor = new Payor(party);

		return new Payment(PaymentType.SENDER, payor);
	}

	private PositiveInteger _getPositiveInteger(int i) {
		return new PositiveInteger(String.valueOf(i));
	}

	private BigDecimal _getPrice(List<CommerceOrderItem> commerceOrderItems)
		throws Exception {

		BigDecimal price = BigDecimal.ZERO;

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			price = price.add(commerceOrderItem.getFinalPrice());
		}

		return price;
	}

	private RateRequest _getRateRequest(
			List<CommerceOrderItem> commerceOrderItems,
			CommerceAddress originAddress)
		throws Exception {

		RateRequest rateRequest = new RateRequest();

		rateRequest.setClientDetail(_getClientDetail());
		rateRequest.setRequestedShipment(
			_getRequestedShipment(commerceOrderItems, originAddress));
		rateRequest.setReturnTransitAndCommit(Boolean.TRUE);
		rateRequest.setTransactionDetail(
			_getTransactionDetail(commerceOrderItems));
		rateRequest.setVersion(new VersionId("crs", 22, 0, 0));
		rateRequest.setWebAuthenticationDetail(_getWebAuthenticationDetail());

		return rateRequest;
	}

	private RequestedPackageLineItem _getRequestedPackageLineItem(
		int fedExWidth, int fedExHeight, int fedExDepth, double fedExWeight,
		BigDecimal price, int groupPackageCount, int sequenceNumber) {

		RequestedPackageLineItem requestedPackageLineItem =
			new RequestedPackageLineItem();

		requestedPackageLineItem.setDimensions(
			new com.fedex.ws.rate.v22.Dimensions(
				_getNonnegativeInteger(fedExDepth),
				_getNonnegativeInteger(fedExWidth),
				_getNonnegativeInteger(fedExHeight), _linearUnits));
		requestedPackageLineItem.setGroupPackageCount(
			_getNonnegativeInteger(groupPackageCount));
		requestedPackageLineItem.setInsuredValue(_getMoney(price));
		requestedPackageLineItem.setSequenceNumber(
			_getPositiveInteger(sequenceNumber));
		requestedPackageLineItem.setWeight(
			new Weight(_weightUnits, BigDecimal.valueOf(fedExWeight)));

		return requestedPackageLineItem;
	}

	private RequestedPackageLineItem[]
			_getRequestedPackageLineItemsByDimensions(
				List<CommerceOrderItem> commerceOrderItems)
		throws Exception {

		int maxSize =
			_fedExCommerceShippingEngineGroupServiceConfiguration.
				maxSizeCentimeters();

		if (_linearUnits.equals(LinearUnits.IN)) {
			maxSize =
				_fedExCommerceShippingEngineGroupServiceConfiguration.
					maxSizeInches();
		}

		int maxWeight =
			_fedExCommerceShippingEngineGroupServiceConfiguration.
				maxWeightKilograms();

		if (_weightUnits.equals(WeightUnits.LB)) {
			maxWeight =
				_fedExCommerceShippingEngineGroupServiceConfiguration.
					maxWeightPounds();
		}

		Dimensions dimensions = _commerceShippingHelper.getDimensions(
			commerceOrderItems);

		int fedExWidth = _getFedExDimension(dimensions.getWidth());
		int fedExHeight = _getFedExDimension(dimensions.getHeight());
		int fedExDepth = _getFedExDimension(dimensions.getDepth());

		double size = _getPackageSize(fedExWidth, fedExHeight, fedExDepth);

		boolean tooLarge = false;

		if (size > maxSize) {
			tooLarge = true;
		}

		double fedExWeight = _getFedExWeight(
			_commerceShippingHelper.getWeight(commerceOrderItems));

		boolean tooHeavy = false;

		if (fedExWeight > maxWeight) {
			tooHeavy = true;
		}

		BigDecimal price = _getPrice(commerceOrderItems);

		if (!tooHeavy && !tooLarge) {
			RequestedPackageLineItem requestedPackageLineItem =
				_getRequestedPackageLineItem(
					fedExWidth, fedExHeight, fedExDepth, fedExWeight, price, 1,
					1);

			return new RequestedPackageLineItem[] {requestedPackageLineItem};
		}

		int packagesCount = Math.max(
			(int)Math.ceil((double)fedExWeight / (double)maxWeight),
			(int)Math.ceil(size / (double)maxSize));

		if (packagesCount == 0) {
			packagesCount = 1;
		}

		int packageFedExWidth = Math.max(fedExWidth / packagesCount, 1);
		int packageFedExHeight = Math.max(fedExHeight / packagesCount, 1);
		int packageFedExDepth = Math.max(fedExDepth / packagesCount, 1);
		double packageFedExWeight = Math.max(fedExWeight / packagesCount, 1);
		BigDecimal packagePrice = price.divide(new BigDecimal(packagesCount));

		RequestedPackageLineItem[] requestedPackageLineItems =
			new RequestedPackageLineItem[packagesCount];

		for (int i = 0; i < packagesCount; i++) {
			requestedPackageLineItems[i] = _getRequestedPackageLineItem(
				packageFedExWidth, packageFedExHeight, packageFedExDepth,
				packageFedExWeight, packagePrice, 1, i + 1);
		}

		return requestedPackageLineItems;
	}

	private RequestedPackageLineItem[]
			_getRequestedPackageLineItemsOneItemPerPackage(
				List<CommerceOrderItem> commerceOrderItems)
		throws Exception {

		List<RequestedPackageLineItem> requestedPackageLineItems =
			new ArrayList<>(commerceOrderItems.size());

		for (int i = 0; i < commerceOrderItems.size(); i++) {
			CommerceOrderItem commerceOrderItem = commerceOrderItems.get(i);

			Dimensions dimensions = _commerceShippingHelper.getDimensions(
				commerceOrderItem);

			int fedExWidth = _getFedExDimension(dimensions.getWidth());
			int fedExHeight = _getFedExDimension(dimensions.getHeight());
			int fedExDepth = _getFedExDimension(dimensions.getDepth());

			double fedExWeight = _getFedExWeight(
				_commerceShippingHelper.getWeight(commerceOrderItem));

			BigDecimal finalPrice = commerceOrderItem.getFinalPrice();

			BigDecimal price = finalPrice.divide(
				BigDecimal.valueOf(commerceOrderItem.getQuantity()));

			for (int j = 0; j < commerceOrderItem.getQuantity(); j++) {
				RequestedPackageLineItem requestedPackageLineItem =
					_getRequestedPackageLineItem(
						fedExWidth, fedExHeight, fedExDepth, fedExWeight, price,
						1, i + 1);

				requestedPackageLineItems.add(requestedPackageLineItem);
			}
		}

		return requestedPackageLineItems.toArray(
			new RequestedPackageLineItem[0]);
	}

	private RequestedShipment _getRequestedShipment(
			List<CommerceOrderItem> commerceOrderItems,
			CommerceAddress originAddress)
		throws Exception {

		RequestedShipment requestedShipment = new RequestedShipment();

		RequestedPackageLineItem[] requestedPackageLineItems = null;

		String packingType =
			_fedExCommerceShippingEngineGroupServiceConfiguration.packingType();

		if (packingType.equals(
				FedExCommerceShippingEngineConstants.
					PACKING_TYPE_BY_DIMENSIONS)) {

			requestedPackageLineItems =
				_getRequestedPackageLineItemsByDimensions(commerceOrderItems);
		}
		else if (packingType.equals(
					FedExCommerceShippingEngineConstants.
						PACKING_TYPE_ONE_ITEM_PER_PACKAGE)) {

			requestedPackageLineItems =
				_getRequestedPackageLineItemsOneItemPerPackage(
					commerceOrderItems);
		}
		else {
			throw new IllegalArgumentException(
				"Invalid packing type " + packingType);
		}

		requestedShipment.setDropoffType(
			DropoffType.fromValue(
				_fedExCommerceShippingEngineGroupServiceConfiguration.
					dropoffType()));
		requestedShipment.setPackageCount(
			_getNonnegativeInteger(requestedPackageLineItems.length));
		requestedShipment.setRateRequestTypes(
			new RateRequestType[] {
				RateRequestType.LIST, RateRequestType.PREFERRED
			});
		requestedShipment.setRecipient(_getParty(_shippingAddress));
		requestedShipment.setRecipientLocationNumber(
			String.valueOf(_shippingAddress.getCommerceAddressId()));
		requestedShipment.setRequestedPackageLineItems(
			requestedPackageLineItems);
		requestedShipment.setShipper(_getParty(originAddress));
		requestedShipment.setShippingChargesPayment(
			_getPayment(
				_fedExCommerceShippingEngineGroupServiceConfiguration.
					accountNumber()));
		requestedShipment.setShipTimestamp(_getShipTimestamp());
		requestedShipment.setTotalInsuredValue(
			_getMoney(_getPrice(commerceOrderItems)));

		return requestedShipment;
	}

	private Calendar _getShipTimestamp() {
		Calendar calendar = CalendarFactoryUtil.getCalendar();

		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			calendar.add(Calendar.DATE, 2);
		}

		return calendar;
	}

	private TransactionDetail _getTransactionDetail(
		List<CommerceOrderItem> commerceOrderItems) {

		TransactionDetail transactionDetail = new TransactionDetail();

		StringBundler sb = new StringBundler(commerceOrderItems.size() * 2);

		sb.append("Liferay Commerce rate request for order items ");

		boolean first = true;

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			if (!first) {
				sb.append(StringPool.COMMA_AND_SPACE);
			}

			first = false;

			sb.append(commerceOrderItem.getCommerceOrderItemId());
		}

		transactionDetail.setCustomerTransactionId(sb.toString());

		return transactionDetail;
	}

	private WebAuthenticationDetail _getWebAuthenticationDetail() {
		WebAuthenticationCredential webAuthenticationCredential =
			new WebAuthenticationCredential(
				_fedExCommerceShippingEngineGroupServiceConfiguration.key(),
				_fedExCommerceShippingEngineGroupServiceConfiguration.
					password());

		return new WebAuthenticationDetail(null, webAuthenticationCredential);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FedExCommerceShippingOptionHelper.class);

	private final CommerceContext _commerceContext;
	private final CommerceCurrency _commerceCurrency;
	private final CommerceCurrencyLocalService _commerceCurrencyLocalService;
	private final CommerceOrder _commerceOrder;
	private final CommerceShippingHelper _commerceShippingHelper;
	private final CommerceShippingOriginLocator _commerceShippingOriginLocator;
	private final CPMeasurementUnitLocalService _cpMeasurementUnitLocalService;
	private final CPMeasurementUnit _dimensionCPMeasurementUnit;
	private final FedExCommerceShippingEngineGroupServiceConfiguration
		_fedExCommerceShippingEngineGroupServiceConfiguration;
	private final LinearUnits _linearUnits;
	private final ResourceBundle _resourceBundle;
	private final Set<String> _serviceTypes;
	private final CommerceAddress _shippingAddress;
	private final CPMeasurementUnit _weightCPMeasurementUnit;
	private final WeightUnits _weightUnits;

}