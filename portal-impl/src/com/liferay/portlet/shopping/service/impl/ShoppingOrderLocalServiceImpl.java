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

package com.liferay.portlet.shopping.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PwdGenerator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.SubscriptionSender;
import com.liferay.portlet.shopping.BillingCityException;
import com.liferay.portlet.shopping.BillingCountryException;
import com.liferay.portlet.shopping.BillingEmailAddressException;
import com.liferay.portlet.shopping.BillingFirstNameException;
import com.liferay.portlet.shopping.BillingLastNameException;
import com.liferay.portlet.shopping.BillingPhoneException;
import com.liferay.portlet.shopping.BillingStateException;
import com.liferay.portlet.shopping.BillingStreetException;
import com.liferay.portlet.shopping.BillingZipException;
import com.liferay.portlet.shopping.CCExpirationException;
import com.liferay.portlet.shopping.CCNameException;
import com.liferay.portlet.shopping.CCNumberException;
import com.liferay.portlet.shopping.CCTypeException;
import com.liferay.portlet.shopping.CartMinOrderException;
import com.liferay.portlet.shopping.ShippingCityException;
import com.liferay.portlet.shopping.ShippingCountryException;
import com.liferay.portlet.shopping.ShippingEmailAddressException;
import com.liferay.portlet.shopping.ShippingFirstNameException;
import com.liferay.portlet.shopping.ShippingLastNameException;
import com.liferay.portlet.shopping.ShippingPhoneException;
import com.liferay.portlet.shopping.ShippingStateException;
import com.liferay.portlet.shopping.ShippingStreetException;
import com.liferay.portlet.shopping.ShippingZipException;
import com.liferay.portlet.shopping.ShoppingGroupServiceSettings;
import com.liferay.portlet.shopping.model.ShoppingCart;
import com.liferay.portlet.shopping.model.ShoppingCartItem;
import com.liferay.portlet.shopping.model.ShoppingItem;
import com.liferay.portlet.shopping.model.ShoppingItemField;
import com.liferay.portlet.shopping.model.ShoppingOrder;
import com.liferay.portlet.shopping.model.ShoppingOrderConstants;
import com.liferay.portlet.shopping.model.ShoppingOrderItem;
import com.liferay.portlet.shopping.model.impl.ShoppingCartItemImpl;
import com.liferay.portlet.shopping.service.base.ShoppingOrderLocalServiceBaseImpl;
import com.liferay.portlet.shopping.util.ShoppingUtil;
import com.liferay.portlet.shopping.util.comparator.OrderDateComparator;
import com.liferay.util.CreditCard;

import java.util.Currency;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class ShoppingOrderLocalServiceImpl
	extends ShoppingOrderLocalServiceBaseImpl {

	@Override
	public ShoppingOrder addLatestOrder(long userId, long groupId)
		throws PortalException {

		// Order

		User user = userPersistence.findByPrimaryKey(userId);

		String number = getNumber();

		ShoppingOrder order = null;

		long orderId = counterLocalService.increment();

		List<ShoppingOrder> pastOrders =
			shoppingOrderPersistence.findByG_U_PPPS(
				groupId, userId, ShoppingOrderConstants.STATUS_CHECKOUT, 0, 1);

		if (!pastOrders.isEmpty()) {
			ShoppingOrder pastOrder = pastOrders.get(0);

			order = shoppingOrderPersistence.create(orderId);

			order.setBillingCompany(pastOrder.getBillingCompany());
			order.setBillingStreet(pastOrder.getBillingStreet());
			order.setBillingCity(pastOrder.getBillingCity());
			order.setBillingState(pastOrder.getBillingState());
			order.setBillingZip(pastOrder.getBillingZip());
			order.setBillingCountry(pastOrder.getBillingCountry());
			order.setBillingPhone(pastOrder.getBillingPhone());
			order.setShipToBilling(pastOrder.isShipToBilling());
			order.setShippingCompany(pastOrder.getShippingCompany());
			order.setShippingStreet(pastOrder.getShippingStreet());
			order.setShippingCity(pastOrder.getShippingCity());
			order.setShippingState(pastOrder.getShippingState());
			order.setShippingZip(pastOrder.getShippingZip());
			order.setShippingCountry(pastOrder.getShippingCountry());
			order.setShippingPhone(pastOrder.getShippingPhone());
		}
		else {
			order = shoppingOrderPersistence.create(orderId);
		}

		order.setGroupId(groupId);
		order.setCompanyId(user.getCompanyId());
		order.setUserId(user.getUserId());
		order.setUserName(user.getFullName());
		order.setNumber(number);
		order.setBillingFirstName(user.getFirstName());
		order.setBillingLastName(user.getLastName());
		order.setBillingEmailAddress(user.getEmailAddress());
		order.setShippingFirstName(user.getFirstName());
		order.setShippingLastName(user.getLastName());
		order.setShippingEmailAddress(user.getEmailAddress());
		order.setCcName(user.getFullName());
		order.setPpPaymentStatus(ShoppingOrderConstants.STATUS_LATEST);
		order.setSendOrderEmail(true);
		order.setSendShippingEmail(true);

		shoppingOrderPersistence.update(order);

		// Message boards

		if (PropsValues.SHOPPING_ORDER_COMMENTS_ENABLED) {
			mbMessageLocalService.addDiscussionMessage(
				userId, order.getUserName(), groupId,
				ShoppingOrder.class.getName(), orderId,
				WorkflowConstants.ACTION_PUBLISH);
		}

		return order;
	}

	@Override
	public void completeOrder(
			String number, String ppTxnId, String ppPaymentStatus,
			double ppPaymentGross, String ppReceiverEmail, String ppPayerEmail,
			boolean updateInventory, ServiceContext serviceContext)
		throws PortalException {

		// Order

		ShoppingOrder order = shoppingOrderPersistence.findByNumber(number);

		order.setPpTxnId(ppTxnId);
		order.setPpPaymentStatus(ppPaymentStatus);
		order.setPpPaymentGross(ppPaymentGross);
		order.setPpReceiverEmail(ppReceiverEmail);
		order.setPpPayerEmail(ppPayerEmail);

		shoppingOrderPersistence.update(order);

		// Inventory

		if (updateInventory &&
			ppPaymentStatus.equals(ShoppingOrderConstants.STATUS_COMPLETED)) {

			List<ShoppingOrderItem> orderItems =
				shoppingOrderItemLocalService.getOrderItems(order.getOrderId());

			for (ShoppingOrderItem orderItem : orderItems) {
				ShoppingItem item = shoppingItemLocalService.getItem(
					ShoppingUtil.getItemId(orderItem.getItemId()));

				if (item.isInfiniteStock()) {
					continue;
				}

				if (!item.isFields()) {
					int quantity =
						item.getStockQuantity() - orderItem.getQuantity();

					item.setStockQuantity(quantity);
				}
				else {
					List<ShoppingItemField> itemFields =
						shoppingItemFieldLocalService.getItemFields(
							item.getItemId());

					ShoppingItemField[] itemFieldsArray = itemFields.toArray(
						new ShoppingItemField[itemFields.size()]);

					String[] fieldsArray = ShoppingCartItemImpl.getFieldsArray(
						ShoppingUtil.getItemFields(orderItem.getItemId()));

					int rowPos = ShoppingUtil.getFieldsQuantitiesPos(
						item, itemFieldsArray, fieldsArray);

					String[] fieldsQuantities = item.getFieldsQuantitiesArray();

					try {
						int quantity =
							GetterUtil.getInteger(fieldsQuantities[rowPos]) -
							orderItem.getQuantity();

						fieldsQuantities[rowPos] = String.valueOf(quantity);

						item.setFieldsQuantitiesArray(fieldsQuantities);
					}
					catch (Exception e) {
					}
				}

				shoppingItemPersistence.update(item);
			}
		}

		// Email

		sendEmail(order, "confirmation", serviceContext);
	}

	@Override
	public void deleteOrder(long orderId) throws PortalException {
		ShoppingOrder order = shoppingOrderPersistence.findByPrimaryKey(
			orderId);

		deleteOrder(order);
	}

	@Override
	public void deleteOrder(ShoppingOrder order) throws PortalException {

		// Order

		shoppingOrderPersistence.remove(order);

		// Subscriptions

		subscriptionLocalService.deleteSubscriptions(
			order.getCompanyId(), ShoppingOrder.class.getName(),
			order.getOrderId());

		// Items

		shoppingOrderItemPersistence.removeByOrderId(order.getOrderId());

		// Message boards

		mbMessageLocalService.deleteDiscussionMessages(
			ShoppingOrder.class.getName(), order.getOrderId());
	}

	@Override
	public void deleteOrders(long groupId) throws PortalException {
		List<ShoppingOrder> orders = shoppingOrderPersistence.findByGroupId(
			groupId);

		for (ShoppingOrder order : orders) {
			deleteOrder(order);
		}
	}

	@Override
	public ShoppingOrder getLatestOrder(long userId, long groupId)
		throws PortalException {

		return shoppingOrderPersistence.findByG_U_PPPS_First(
			groupId, userId, ShoppingOrderConstants.STATUS_LATEST, null);
	}

	@Override
	public ShoppingOrder getOrder(long orderId) throws PortalException {
		return shoppingOrderPersistence.findByPrimaryKey(orderId);
	}

	@Override
	public ShoppingOrder getOrder(String number) throws PortalException {
		return shoppingOrderPersistence.findByNumber(number);
	}

	@Override
	public ShoppingOrder getPayPalTxnIdOrder(String ppTxnId)
		throws PortalException {

		return shoppingOrderPersistence.findByPPTxnId(ppTxnId);
	}

	@Override
	public ShoppingOrder saveLatestOrder(ShoppingCart cart)
		throws PortalException {

		Map<ShoppingCartItem, Integer> items = cart.getItems();

		ShoppingGroupServiceSettings shoppingGroupServiceSettings =
			ShoppingGroupServiceSettings.getInstance(cart.getGroupId());

		if (!ShoppingUtil.meetsMinOrder(shoppingGroupServiceSettings, items)) {
			throw new CartMinOrderException();
		}

		ShoppingOrder order = getLatestOrder(
			cart.getUserId(), cart.getGroupId());

		order.setPpPaymentStatus(ShoppingOrderConstants.STATUS_CHECKOUT);

		shoppingOrderPersistence.update(order);

		boolean requiresShipping = false;

		for (Map.Entry<ShoppingCartItem, Integer> entry : items.entrySet()) {
			ShoppingCartItem cartItem = entry.getKey();
			Integer count = entry.getValue();

			ShoppingItem item = cartItem.getItem();

			if (item.isRequiresShipping()) {
				requiresShipping = true;
			}

			long orderItemId = counterLocalService.increment();

			ShoppingOrderItem orderItem = shoppingOrderItemPersistence.create(
				orderItemId);

			orderItem.setOrderId(order.getOrderId());
			orderItem.setItemId(cartItem.getCartItemId());
			orderItem.setSku(item.getSku());
			orderItem.setName(item.getName());
			orderItem.setDescription(item.getDescription());
			orderItem.setProperties(item.getProperties());
			orderItem.setPrice(
				ShoppingUtil.calculateActualPrice(item, count.intValue()) /
					count.intValue());
			orderItem.setQuantity(count.intValue());

			shoppingOrderItemPersistence.update(orderItem);
		}

		order.setTax(ShoppingUtil.calculateTax(items, order.getBillingState()));
		order.setShipping(
			ShoppingUtil.calculateAlternativeShipping(
				items, cart.getAltShipping()));
		order.setAltShipping(
			shoppingGroupServiceSettings.getAlternativeShippingName(
				cart.getAltShipping()));
		order.setRequiresShipping(requiresShipping);
		order.setInsure(cart.isInsure());
		order.setInsurance(ShoppingUtil.calculateInsurance(items));
		order.setCouponCodes(cart.getCouponCodes());
		order.setCouponDiscount(
			ShoppingUtil.calculateCouponDiscount(
				items, order.getBillingState(), cart.getCoupon()));
		order.setSendOrderEmail(true);
		order.setSendShippingEmail(true);

		shoppingOrderPersistence.update(order);

		return order;
	}

	@Override
	public List<ShoppingOrder> search(
		long groupId, long companyId, long userId, String number,
		String billingFirstName, String billingLastName,
		String billingEmailAddress, String shippingFirstName,
		String shippingLastName, String shippingEmailAddress,
		String ppPaymentStatus, boolean andOperator, int start, int end) {

		OrderDateComparator obc = new OrderDateComparator(false);

		return shoppingOrderFinder.findByG_C_U_N_PPPS(
			groupId, companyId, userId, number, billingFirstName,
			billingLastName, billingEmailAddress, shippingFirstName,
			shippingLastName, shippingEmailAddress, ppPaymentStatus,
			andOperator, start, end, obc);
	}

	@Override
	public int searchCount(
		long groupId, long companyId, long userId, String number,
		String billingFirstName, String billingLastName,
		String billingEmailAddress, String shippingFirstName,
		String shippingLastName, String shippingEmailAddress,
		String ppPaymentStatus, boolean andOperator) {

		return shoppingOrderFinder.countByG_C_U_N_PPPS(
			groupId, companyId, userId, number, billingFirstName,
			billingLastName, billingEmailAddress, shippingFirstName,
			shippingLastName, shippingEmailAddress, ppPaymentStatus,
			andOperator);
	}

	@Override
	public void sendEmail(
			long orderId, String emailType, ServiceContext serviceContext)
		throws PortalException {

		ShoppingOrder order = shoppingOrderPersistence.findByPrimaryKey(
			orderId);

		sendEmail(order, emailType, serviceContext);
	}

	@Override
	public void sendEmail(
			ShoppingOrder order, String emailType,
			ServiceContext serviceContext)
		throws PortalException {

		ShoppingGroupServiceSettings shoppingGroupServiceSettings =
			ShoppingGroupServiceSettings.getInstance(order.getGroupId());

		if (emailType.equals("confirmation") &&
			shoppingGroupServiceSettings.isEmailOrderConfirmationEnabled()) {
		}
		else if (emailType.equals("shipping") &&
				 shoppingGroupServiceSettings.isEmailOrderShippingEnabled()) {
		}
		else {
			return;
		}

		notifyUser(
			order, emailType, shoppingGroupServiceSettings, serviceContext);

		if (emailType.equals("confirmation") && order.isSendOrderEmail()) {
			order.setSendOrderEmail(false);

			shoppingOrderPersistence.update(order);
		}
		else if (emailType.equals("shipping") && order.isSendShippingEmail()) {
			order.setSendShippingEmail(false);

			shoppingOrderPersistence.update(order);
		}
	}

	@Override
	public ShoppingOrder updateLatestOrder(
			long userId, long groupId, String billingFirstName,
			String billingLastName, String billingEmailAddress,
			String billingCompany, String billingStreet, String billingCity,
			String billingState, String billingZip, String billingCountry,
			String billingPhone, boolean shipToBilling,
			String shippingFirstName, String shippingLastName,
			String shippingEmailAddress, String shippingCompany,
			String shippingStreet, String shippingCity, String shippingState,
			String shippingZip, String shippingCountry, String shippingPhone,
			String ccName, String ccType, String ccNumber, int ccExpMonth,
			int ccExpYear, String ccVerNumber, String comments)
		throws PortalException {

		ShoppingOrder order = getLatestOrder(userId, groupId);

		return updateOrder(
			order.getOrderId(), billingFirstName, billingLastName,
			billingEmailAddress, billingCompany, billingStreet, billingCity,
			billingState, billingZip, billingCountry, billingPhone,
			shipToBilling, shippingFirstName, shippingLastName,
			shippingEmailAddress, shippingCompany, shippingStreet, shippingCity,
			shippingState, shippingZip, shippingCountry, shippingPhone, ccName,
			ccType, ccNumber, ccExpMonth, ccExpYear, ccVerNumber, comments);
	}

	@Override
	public ShoppingOrder updateOrder(
			long orderId, String ppTxnId, String ppPaymentStatus,
			double ppPaymentGross, String ppReceiverEmail, String ppPayerEmail)
		throws PortalException {

		ShoppingOrder order = shoppingOrderPersistence.findByPrimaryKey(
			orderId);

		order.setPpTxnId(ppTxnId);
		order.setPpPaymentStatus(ppPaymentStatus);
		order.setPpPaymentGross(ppPaymentGross);
		order.setPpReceiverEmail(ppReceiverEmail);
		order.setPpPayerEmail(ppPayerEmail);

		shoppingOrderPersistence.update(order);

		return order;
	}

	@Override
	public ShoppingOrder updateOrder(
			long orderId, String billingFirstName, String billingLastName,
			String billingEmailAddress, String billingCompany,
			String billingStreet, String billingCity, String billingState,
			String billingZip, String billingCountry, String billingPhone,
			boolean shipToBilling, String shippingFirstName,
			String shippingLastName, String shippingEmailAddress,
			String shippingCompany, String shippingStreet, String shippingCity,
			String shippingState, String shippingZip, String shippingCountry,
			String shippingPhone, String ccName, String ccType, String ccNumber,
			int ccExpMonth, int ccExpYear, String ccVerNumber, String comments)
		throws PortalException {

		ShoppingOrder order = shoppingOrderPersistence.findByPrimaryKey(
			orderId);

		ShoppingGroupServiceSettings shoppingGroupServiceSettings =
			ShoppingGroupServiceSettings.getInstance(order.getGroupId());

		validate(
			shoppingGroupServiceSettings, billingFirstName, billingLastName,
			billingEmailAddress, billingStreet, billingCity, billingState,
			billingZip, billingCountry, billingPhone, shipToBilling,
			shippingFirstName, shippingLastName, shippingEmailAddress,
			shippingStreet, shippingCity, shippingState, shippingZip,
			shippingCountry, shippingPhone, ccName, ccType, ccNumber,
			ccExpMonth, ccExpYear, ccVerNumber);

		order.setBillingFirstName(billingFirstName);
		order.setBillingLastName(billingLastName);
		order.setBillingEmailAddress(billingEmailAddress);
		order.setBillingCompany(billingCompany);
		order.setBillingStreet(billingStreet);
		order.setBillingCity(billingCity);
		order.setBillingState(billingState);
		order.setBillingZip(billingZip);
		order.setBillingCountry(billingCountry);
		order.setBillingPhone(billingPhone);
		order.setShipToBilling(shipToBilling);

		if (shipToBilling) {
			order.setShippingFirstName(billingFirstName);
			order.setShippingLastName(billingLastName);
			order.setShippingEmailAddress(billingEmailAddress);
			order.setShippingCompany(billingCompany);
			order.setShippingStreet(billingStreet);
			order.setShippingCity(billingCity);
			order.setShippingState(billingState);
			order.setShippingZip(billingZip);
			order.setShippingCountry(billingCountry);
			order.setShippingPhone(billingPhone);
		}
		else {
			order.setShippingFirstName(shippingFirstName);
			order.setShippingLastName(shippingLastName);
			order.setShippingEmailAddress(shippingEmailAddress);
			order.setShippingCompany(shippingCompany);
			order.setShippingStreet(shippingStreet);
			order.setShippingCity(shippingCity);
			order.setShippingState(shippingState);
			order.setShippingZip(shippingZip);
			order.setShippingCountry(shippingCountry);
			order.setShippingPhone(shippingPhone);
		}

		order.setCcName(ccName);
		order.setCcType(ccType);
		order.setCcNumber(ccNumber);
		order.setCcExpMonth(ccExpMonth);
		order.setCcExpYear(ccExpYear);
		order.setCcVerNumber(ccVerNumber);
		order.setComments(comments);

		shoppingOrderPersistence.update(order);

		return order;
	}

	protected String getNumber() {
		String number = PwdGenerator.getPassword(
			12, PwdGenerator.KEY1, PwdGenerator.KEY2);

		ShoppingOrder order = shoppingOrderPersistence.fetchByNumber(number);

		if (order != null) {
			return order.getNumber();
		}

		return number;
	}

	protected void notifyUser(
			ShoppingOrder order, String emailType,
			ShoppingGroupServiceSettings shoppingGroupServiceSettings,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(order.getUserId());

		Currency currency = Currency.getInstance(
			shoppingGroupServiceSettings.getCurrencyId());

		String billingAddress =
			order.getBillingFirstName() + " " + order.getBillingLastName() +
				"<br>" +
			order.getBillingEmailAddress() + "<br>" +
			order.getBillingStreet() + "<br>" +
			order.getBillingCity() + "<br>" +
			order.getBillingState() + "<br>" +
			order.getBillingZip() + "<br>" +
			order.getBillingCountry() + "<br>" +
			order.getBillingPhone() + "<br>";

		String shippingAddress =
			order.getShippingFirstName() + " " + order.getShippingLastName() +
				"<br>" +
			order.getShippingEmailAddress() + "<br>" +
			order.getShippingStreet() + "<br>" +
			order.getShippingCity() + "<br>" +
			order.getShippingState() + "<br>" +
			order.getShippingZip() + "<br>" +
			order.getShippingCountry() + "<br>" +
			order.getShippingPhone() + "<br>";

		double total = ShoppingUtil.calculateTotal(order);

		String fromName = shoppingGroupServiceSettings.getEmailFromName();
		String fromAddress = shoppingGroupServiceSettings.getEmailFromAddress();

		String toName = user.getFullName();
		String toAddress = user.getEmailAddress();

		LocalizedValuesMap subjectLocalizedValuesMap = null;
		LocalizedValuesMap bodyLocalizedValuesMap = null;

		if (emailType.equals("confirmation")) {
			subjectLocalizedValuesMap =
				shoppingGroupServiceSettings.getEmailOrderConfirmationSubject();
			bodyLocalizedValuesMap =
				shoppingGroupServiceSettings.getEmailOrderConfirmationBody();
		}
		else if (emailType.equals("shipping")) {
			subjectLocalizedValuesMap =
				shoppingGroupServiceSettings.getEmailOrderShippingSubject();
			bodyLocalizedValuesMap =
				shoppingGroupServiceSettings.getEmailOrderShippingBody();
		}

		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setCompanyId(order.getCompanyId());
		subscriptionSender.setContextAttributes(
			"[$ORDER_BILLING_ADDRESS$]", billingAddress, "[$ORDER_CURRENCY$]",
			currency.getSymbol(), "[$ORDER_NUMBER$]", order.getNumber(),
			"[$ORDER_SHIPPING_ADDRESS$]", shippingAddress, "[$ORDER_TOTAL$]",
			total);
		subscriptionSender.setFrom(fromAddress, fromName);
		subscriptionSender.setHtmlFormat(true);

		if (bodyLocalizedValuesMap != null) {
			subscriptionSender.setLocalizedBodyMap(
				LocalizationUtil.getMap(bodyLocalizedValuesMap));
		}

		if (subjectLocalizedValuesMap != null) {
			subscriptionSender.setLocalizedSubjectMap(
				LocalizationUtil.getMap(subjectLocalizedValuesMap));
		}

		subscriptionSender.setMailId("shopping_order", order.getOrderId());
		subscriptionSender.setPortletId(PortletKeys.SHOPPING);
		subscriptionSender.setScopeGroupId(order.getGroupId());
		subscriptionSender.setServiceContext(serviceContext);

		subscriptionSender.addRuntimeSubscribers(toAddress, toName);

		subscriptionSender.flushNotificationsAsync();
	}

	protected void validate(
			ShoppingGroupServiceSettings shoppingGroupServiceSettings,
			String billingFirstName, String billingLastName,
			String billingEmailAddress, String billingStreet,
			String billingCity, String billingState, String billingZip,
			String billingCountry, String billingPhone, boolean shipToBilling,
			String shippingFirstName, String shippingLastName,
			String shippingEmailAddress, String shippingStreet,
			String shippingCity, String shippingState, String shippingZip,
			String shippingCountry, String shippingPhone, String ccName,
			String ccType, String ccNumber, int ccExpMonth, int ccExpYear,
			String ccVerNumber)
		throws PortalException {

		if (Validator.isNull(billingFirstName)) {
			throw new BillingFirstNameException();
		}
		else if (Validator.isNull(billingLastName)) {
			throw new BillingLastNameException();
		}
		else if (!Validator.isEmailAddress(billingEmailAddress)) {
			throw new BillingEmailAddressException();
		}
		else if (Validator.isNull(billingStreet)) {
			throw new BillingStreetException();
		}
		else if (Validator.isNull(billingCity)) {
			throw new BillingCityException();
		}
		else if (Validator.isNull(billingState)) {
			throw new BillingStateException();
		}
		else if (Validator.isNull(billingZip)) {
			throw new BillingZipException();
		}
		else if (Validator.isNull(billingCountry)) {
			throw new BillingCountryException();
		}
		else if (Validator.isNull(billingPhone)) {
			throw new BillingPhoneException();
		}

		if (!shipToBilling) {
			if (Validator.isNull(shippingFirstName)) {
				throw new ShippingFirstNameException();
			}
			else if (Validator.isNull(shippingLastName)) {
				throw new ShippingLastNameException();
			}
			else if (!Validator.isEmailAddress(shippingEmailAddress)) {
				throw new ShippingEmailAddressException();
			}
			else if (Validator.isNull(shippingStreet)) {
				throw new ShippingStreetException();
			}
			else if (Validator.isNull(shippingCity)) {
				throw new ShippingCityException();
			}
			else if (Validator.isNull(shippingState)) {
				throw new ShippingStateException();
			}
			else if (Validator.isNull(shippingZip)) {
				throw new ShippingZipException();
			}
			else if (Validator.isNull(shippingCountry)) {
				throw new ShippingCountryException();
			}
			else if (Validator.isNull(shippingPhone)) {
				throw new ShippingPhoneException();
			}
		}

		if (!shoppingGroupServiceSettings.usePayPal() &&
			(shoppingGroupServiceSettings.getCcTypes().length > 0)) {

			if (Validator.isNull(ccName)) {
				throw new CCNameException();
			}
			else if (Validator.isNull(ccType)) {
				throw new CCTypeException();
			}
			else if (!CreditCard.isValidNumber(ccNumber, ccType)) {
				throw new CCNumberException();
			}
			else if (!CreditCard.isValidExpirationDate(ccExpMonth, ccExpYear)) {
				throw new CCExpirationException();
			}
		}
	}

}