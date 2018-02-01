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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.exception.CommerceCartBillingAddressException;
import com.liferay.commerce.exception.CommerceCartPaymentMethodException;
import com.liferay.commerce.exception.CommerceCartShippingAddressException;
import com.liferay.commerce.exception.CommerceCartShippingMethodException;
import com.liferay.commerce.exception.CommerceOrderPurchaseOrderNumberException;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.model.CommerceCartItem;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderConstants;
import com.liferay.commerce.model.CommercePaymentMethod;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.organization.service.CommerceOrganizationLocalService;
import com.liferay.commerce.service.base.CommerceOrderLocalServiceBaseImpl;
import com.liferay.commerce.util.CommercePriceCalculator;
import com.liferay.commerce.util.CommerceShippingHelper;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderLocalServiceImpl
	extends CommerceOrderLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder addCommerceOrder(
			long siteGroupId, long orderOrganizationId, long orderUserId,
			long commercePaymentMethodId, long commerceShippingMethodId,
			String shippingOptionName, double subtotal, double shippingPrice,
			double total, int paymentStatus, int shippingStatus,
			int orderStatus, ServiceContext serviceContext)
		throws PortalException {

		// Commerce order

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long commerceOrderId = counterLocalService.increment();

		CommerceOrder commerceOrder = commerceOrderPersistence.create(
			commerceOrderId);

		commerceOrder.setUuid(serviceContext.getUuid());
		commerceOrder.setGroupId(groupId);
		commerceOrder.setCompanyId(user.getCompanyId());
		commerceOrder.setUserId(user.getUserId());
		commerceOrder.setUserName(user.getFullName());
		commerceOrder.setSiteGroupId(siteGroupId);
		commerceOrder.setOrderOrganizationId(orderOrganizationId);
		commerceOrder.setOrderUserId(orderUserId);
		commerceOrder.setCommercePaymentMethodId(commercePaymentMethodId);
		commerceOrder.setCommerceShippingMethodId(commerceShippingMethodId);
		commerceOrder.setShippingOptionName(shippingOptionName);
		commerceOrder.setSubtotal(subtotal);
		commerceOrder.setShippingPrice(shippingPrice);
		commerceOrder.setTotal(total);
		commerceOrder.setPaymentStatus(paymentStatus);
		commerceOrder.setShippingStatus(shippingStatus);
		commerceOrder.setOrderStatus(orderStatus);
		commerceOrder.setExpandoBridgeAttributes(serviceContext);

		commerceOrderPersistence.update(commerceOrder);

		// Workflow

		startWorkflowInstance(user.getUserId(), commerceOrder, serviceContext);

		return commerceOrder;
	}

	@Override
	public CommerceOrder addCommerceOrderFromCart(
			long commerceCartId, ServiceContext serviceContext)
		throws PortalException {

		// Commerce order

		CommerceCart commerceCart = commerceCartLocalService.getCommerceCart(
			commerceCartId);

		validate(commerceCart);

		long siteGroupId = serviceContext.getScopeGroupId();

		long orderOrganizationId = 0;

		if (commerceCart.isB2B()) {
			Organization organization =
				_commerceOrganizationLocalService.getAccountOrganization(
					commerceCart.getClassPK());

			if (organization != null) {
				orderOrganizationId = organization.getOrganizationId();
			}
		}

		serviceContext.setScopeGroupId(commerceCart.getGroupId());

		double subtotal = _commercePriceCalculator.getSubtotal(commerceCart);
		double shippingPrice = commerceCart.getShippingPrice();

		double total = subtotal + shippingPrice;

		CommerceOrder commerceOrder =
			commerceOrderLocalService.addCommerceOrder(
				siteGroupId, orderOrganizationId, commerceCart.getUserId(),
				commerceCart.getCommercePaymentMethodId(),
				commerceCart.getCommerceShippingMethodId(),
				commerceCart.getShippingOptionName(), subtotal, shippingPrice,
				total, CommerceOrderConstants.PAYMENT_STATUS_PENDING,
				CommerceOrderConstants.SHIPPING_STATUS_NOT_SHIPPED,
				CommerceOrderConstants.ORDER_STATUS_PENDING, serviceContext);

		// Commerce addresses

		long billingAddressId = commerceCart.getBillingAddressId();

		if (billingAddressId > 0) {
			CommerceAddress commerceAddress =
				commerceAddressLocalService.copyCommerceAddress(
					billingAddressId, commerceOrder.getModelClassName(),
					commerceOrder.getCommerceOrderId(), serviceContext);

			billingAddressId = commerceAddress.getCommerceAddressId();
		}

		long shippingAddressId = commerceCart.getShippingAddressId();

		if (shippingAddressId > 0) {
			CommerceAddress commerceAddress =
				commerceAddressLocalService.copyCommerceAddress(
					shippingAddressId, commerceOrder.getModelClassName(),
					commerceOrder.getCommerceOrderId(), serviceContext);

			shippingAddressId = commerceAddress.getCommerceAddressId();
		}

		if ((billingAddressId > 0) || (shippingAddressId > 0)) {
			commerceOrder.setBillingAddressId(billingAddressId);
			commerceOrder.setShippingAddressId(shippingAddressId);

			commerceOrder = commerceOrderPersistence.update(commerceOrder);
		}

		// Commerce order items

		List<CommerceCartItem> commerceCartItems =
			commerceCartItemLocalService.getCommerceCartItems(
				commerceCart.getCommerceCartId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		for (CommerceCartItem commerceCartItem : commerceCartItems) {
			double price = _commercePriceCalculator.getPrice(
				commerceCartItem.fetchCPInstance(),
				commerceCartItem.getQuantity());

			commerceOrderItemLocalService.addCommerceOrderItem(
				commerceOrder.getCommerceOrderId(),
				commerceCartItem.getCPInstanceId(),
				commerceCartItem.getQuantity(), 0, commerceCartItem.getJson(),
				price, serviceContext);
		}

		// Commerce cart

		commerceCartLocalService.deleteCommerceCart(commerceCart);

		return commerceOrder;
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public CommerceOrder deleteCommerceOrder(CommerceOrder commerceOrder)
		throws PortalException {

		// Commerce order

		commerceOrderPersistence.remove(commerceOrder);

		// Commerce order items

		commerceOrderItemLocalService.deleteCommerceOrderItems(
			commerceOrder.getCommerceOrderId());

		// Commerce order notes

		commerceOrderNoteLocalService.deleteCommerceOrderNotes(
			commerceOrder.getCommerceOrderId());

		// Commerce order payments

		commerceOrderPaymentLocalService.deleteCommerceOrderPayments(
			commerceOrder.getCommerceOrderId());

		// Commerce addresses

		commerceAddressLocalService.deleteCommerceAddresses(
			commerceOrder.getModelClassName(),
			commerceOrder.getCommerceOrderId());

		// Expando

		expandoRowLocalService.deleteRows(commerceOrder.getCommerceOrderId());

		// Workflow

		workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
			commerceOrder.getCompanyId(), commerceOrder.getGroupId(),
			CommerceOrder.class.getName(), commerceOrder.getCommerceOrderId());

		return commerceOrder;
	}

	@Override
	public CommerceOrder deleteCommerceOrder(long commerceOrderId)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		return commerceOrderLocalService.deleteCommerceOrder(commerceOrder);
	}

	@Override
	public List<CommerceOrder> getCommerceOrders(
		long groupId, int orderStatus, int start, int end,
		OrderByComparator<CommerceOrder> orderByComparator) {

		if (orderStatus == CommerceOrderConstants.ORDER_STATUS_ANY) {
			return commerceOrderPersistence.findByGroupId(
				groupId, start, end, orderByComparator);
		}
		else {
			return commerceOrderPersistence.findByG_O(
				groupId, orderStatus, start, end, orderByComparator);
		}
	}

	@Override
	public Map<Integer, Long> getCommerceOrdersCount(long groupId) {
		return commerceOrderFinder.countByG_S(groupId);
	}

	@Override
	public int getCommerceOrdersCount(long groupId, int orderStatus) {
		if (orderStatus == CommerceOrderConstants.ORDER_STATUS_ANY) {
			return commerceOrderPersistence.countByGroupId(groupId);
		}
		else {
			return commerceOrderPersistence.countByG_O(groupId, orderStatus);
		}
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder updateBillingAddress(
			long commerceOrderId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long commerceRegionId, long commerceCountryId,
			String phoneNumber, ServiceContext serviceContext)
		throws PortalException {

		return updateAddress(
			commerceOrderId, name, description, street1, street2, street3, city,
			zip, commerceRegionId, commerceCountryId, phoneNumber,
			CommerceOrder::getBillingAddressId,
			CommerceOrder::setBillingAddressId, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder updateCommerceOrder(
			long commerceOrderId, long commercePaymentMethodId,
			String purchaseOrderNumber, double subtotal, double shippingPrice,
			double total, int paymentStatus, int orderStatus)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		commerceOrder.setCommercePaymentMethodId(commercePaymentMethodId);
		commerceOrder.setPurchaseOrderNumber(purchaseOrderNumber);
		commerceOrder.setSubtotal(subtotal);
		commerceOrder.setShippingPrice(shippingPrice);
		commerceOrder.setTotal(total);
		commerceOrder.setPaymentStatus(paymentStatus);
		commerceOrder.setOrderStatus(orderStatus);

		commerceOrderPersistence.update(commerceOrder);

		return commerceOrder;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder updatePaymentStatus(
			long commerceOrderId, int paymentStatus, int orderStatus)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		commerceOrder.setPaymentStatus(paymentStatus);
		commerceOrder.setOrderStatus(orderStatus);

		commerceOrderPersistence.update(commerceOrder);

		return commerceOrder;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder updatePurchaseOrderNumber(
			long commerceOrderId, String purchaseOrderNumber)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		validatePurchaseOrderNumber(purchaseOrderNumber);

		commerceOrder.setPurchaseOrderNumber(purchaseOrderNumber);

		commerceOrderPersistence.update(commerceOrder);

		return commerceOrder;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder updateShippingAddress(
			long commerceOrderId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long commerceRegionId, long commerceCountryId,
			String phoneNumber, ServiceContext serviceContext)
		throws PortalException {

		return updateAddress(
			commerceOrderId, name, description, street1, street2, street3, city,
			zip, commerceRegionId, commerceCountryId, phoneNumber,
			CommerceOrder::getShippingAddressId,
			CommerceOrder::setShippingAddressId, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder updateStatus(
			long userId, long commerceOrderId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);
		Date now = new Date();

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		commerceOrder.setStatus(status);
		commerceOrder.setStatusByUserId(user.getUserId());
		commerceOrder.setStatusByUserName(user.getFullName());
		commerceOrder.setStatusDate(serviceContext.getModifiedDate(now));

		commerceOrderPersistence.update(commerceOrder);

		return commerceOrder;
	}

	protected CommerceOrder startWorkflowInstance(
			long userId, CommerceOrder commerceOrder,
			ServiceContext serviceContext)
		throws PortalException {

		Map<String, Serializable> workflowContext = new HashMap<>();

		return WorkflowHandlerRegistryUtil.startWorkflowInstance(
			commerceOrder.getCompanyId(), commerceOrder.getGroupId(), userId,
			CommerceOrder.class.getName(), commerceOrder.getCommerceOrderId(),
			commerceOrder, serviceContext, workflowContext);
	}

	protected CommerceOrder updateAddress(
			long commerceOrderId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long commerceRegionId, long commerceCountryId,
			String phoneNumber,
			Function<CommerceOrder, Long> commerceAddressIdGetter,
			BiConsumer<CommerceOrder, Long> commerceAddressIdSetter,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		CommerceAddress commerceAddress = null;

		long commerceAddressId = commerceAddressIdGetter.apply(commerceOrder);

		if (commerceAddressId > 0) {
			commerceAddress = commerceAddressLocalService.updateCommerceAddress(
				commerceAddressId, name, description, street1, street2, street3,
				city, zip, commerceRegionId, commerceCountryId, phoneNumber,
				false, false, serviceContext);
		}
		else {
			commerceAddress = commerceAddressLocalService.addCommerceAddress(
				commerceOrder.getModelClassName(),
				commerceOrder.getCommerceOrderId(), name, description, street1,
				street2, street3, city, zip, commerceRegionId,
				commerceCountryId, phoneNumber, false, false, serviceContext);
		}

		commerceAddressIdSetter.accept(
			commerceOrder, commerceAddress.getCommerceAddressId());

		return commerceOrderPersistence.update(commerceOrder);
	}

	protected void validate(CommerceCart commerceCart) throws PortalException {
		CommercePaymentMethod commercePaymentMethod = null;

		long commercePaymentMethodId =
			commerceCart.getCommercePaymentMethodId();

		if (commercePaymentMethodId > 0) {
			commercePaymentMethod =
				commercePaymentMethodLocalService.getCommercePaymentMethod(
					commercePaymentMethodId);

			if (!commercePaymentMethod.isActive()) {
				commercePaymentMethod = null;
			}
			else if (!commerceCart.isB2B() &&
					 (commerceCart.getBillingAddressId() <= 0)) {

				throw new CommerceCartBillingAddressException();
			}
		}

		if ((commercePaymentMethod == null) &&
			(commercePaymentMethodLocalService.getCommercePaymentMethodsCount(
				commerceCart.getGroupId(), true) > 0)) {

			throw new CommerceCartPaymentMethodException();
		}

		CommerceShippingMethod commerceShippingMethod = null;

		long commerceShippingMethodId =
			commerceCart.getCommerceShippingMethodId();

		if (commerceShippingMethodId > 0) {
			commerceShippingMethod =
				commerceShippingMethodLocalService.getCommerceShippingMethod(
					commerceShippingMethodId);

			if (!commerceShippingMethod.isActive()) {
				commerceShippingMethod = null;
			}
			else if (commerceCart.getShippingAddressId() <= 0) {
				throw new CommerceCartShippingAddressException();
			}
		}

		if ((commerceShippingMethod == null) &&
			(commerceShippingMethodLocalService.getCommerceShippingMethodsCount(
				commerceCart.getGroupId(), true) > 0) &&
			_commerceShippingHelper.isShippable(commerceCart)) {

			throw new CommerceCartShippingMethodException();
		}
	}

	protected void validatePurchaseOrderNumber(String purchaseOrderNumber)
		throws PortalException {

		if (Validator.isNull(purchaseOrderNumber)) {
			throw new CommerceOrderPurchaseOrderNumberException();
		}
	}

	@ServiceReference(type = CommerceOrganizationLocalService.class)
	private CommerceOrganizationLocalService _commerceOrganizationLocalService;

	@ServiceReference(type = CommercePriceCalculator.class)
	private CommercePriceCalculator _commercePriceCalculator;

	@ServiceReference(type = CommerceShippingHelper.class)
	private CommerceShippingHelper _commerceShippingHelper;

}