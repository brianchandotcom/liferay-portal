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

package com.liferay.headless.commerce.delivery.cart.internal.resource.v1_0;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.commerce.constants.CommerceAddressConstants;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.context.CommerceContextFactory;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceCountryService;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.service.CommerceRegionLocalService;
import com.liferay.commerce.service.CommerceShippingMethodService;
import com.liferay.commerce.util.CommerceShippingHelper;
import com.liferay.headless.commerce.core.util.ExpandoUtil;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.Address;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.Cart;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.CartItem;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.CouponCode;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.Error;
import com.liferay.headless.commerce.delivery.cart.internal.dto.v1_0.CartDTOConverter;
import com.liferay.headless.commerce.delivery.cart.resource.v1_0.CartResource;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Andrea Sbarra
 */
@Component(
	enabled = false, properties = "OSGI-INF/liferay/rest/v1_0/cart.properties",
	scope = ServiceScope.PROTOTYPE, service = CartResource.class
)
public class CartResourceImpl extends BaseCartResourceImpl {

	@Override
	public Response deleteCart(@NotNull Long cartId) throws Exception {
		_commerceOrderService.deleteCommerceOrder(cartId);

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	public Cart getCart(@NotNull Long cartId) throws Exception {
		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			cartId);

		return _toCart(commerceOrder);
	}

	@Override
	public Page<Cart> getChannelCartsPage(
			@NotNull Long channelId, Pagination pagination)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(channelId);

		List<CommerceOrder> commerceOrders =
			_commerceOrderService.getUserPendingCommerceOrders(
				contextCompany.getCompanyId(), commerceChannel.getGroupId(),
				null, pagination.getStartPosition(),
				pagination.getEndPosition());

		long pendingCommerceOrdersCount =
			_commerceOrderService.getPendingCommerceOrdersCount(
				contextCompany.getCompanyId(), commerceChannel.getGroupId());

		return Page.of(
			_toCarts(commerceOrders), pagination, pendingCommerceOrdersCount);
	}

	@Override
	public Cart patchCart(@NotNull Long cartId, Cart cart) throws Exception {
		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			cartId);

		_updateOrder(commerceOrder, cart);

		return _toCart(commerceOrder);
	}

	@Override
	public Cart postCartCouponCode(@NotNull Long cartId, CouponCode couponCode)
		throws Exception {

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			cartId);

		CommerceContext commerceContext = _commerceContextFactory.create(
			contextCompany.getCompanyId(), commerceOrder.getGroupId(),
			contextUser.getUserId(), commerceOrder.getCommerceOrderId(),
			commerceOrder.getCommerceAccountId());

		return _toCart(
			_commerceOrderService.applyCouponCode(
				cartId, couponCode.getCode(), commerceContext));
	}

	@Override
	public Cart postChannelCart(@NotNull Long channelId, Cart cart)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(channelId);

		CommerceOrder commerceOrder = _addCommerceOrder(
			cart, commerceChannel.getGroupId(), contextUser.getUserId());

		_updateOrder(commerceOrder, cart);

		return _toCart(commerceOrder);
	}

	@Override
	public Cart putCart(Long cartId, Cart cart) throws Exception {
		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			cartId);

		_updateOrder(commerceOrder, cart);

		return _toCart(commerceOrder);
	}

	@Override
	public Cart putCartCheckout(Long cartId) throws Exception {
		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			cartId);

		List<Error> errors = _validateOrder(commerceOrder);

		if (ListUtil.isNotEmpty(errors)) {
			Cart cart = _toCart(commerceOrder);

			cart.setErrors(errors.toArray(new Error[0]));

			return cart;
		}

		commerceOrder = _commerceOrderEngine.checkoutCommerceOrder(
			commerceOrder, contextUser.getUserId());

		return _toCart(commerceOrder);
	}

	private CommerceAddress _addCommerceAddress(
			CommerceOrder commerceOrder, Address address, int type,
			ServiceContext serviceContext)
		throws Exception {

		CommerceCountry commerceCountry =
			_commerceCountryService.getCommerceCountry(
				commerceOrder.getCompanyId(), address.getCountryISOCode());

		return _commerceAddressService.addCommerceAddress(
			commerceOrder.getModelClassName(),
			commerceOrder.getCommerceOrderId(), address.getName(),
			address.getDescription(), address.getStreet1(),
			address.getStreet2(), address.getStreet3(), address.getCity(),
			address.getZip(),
			_getCommerceRegionId(null, commerceCountry, address),
			commerceCountry.getCommerceCountryId(), address.getPhoneNumber(),
			type, serviceContext);
	}

	private CommerceOrder _addCommerceOrder(
			Cart cart, long commerceChannelGroupId, long userId)
		throws Exception {

		long commerceCurrencyId = 0;

		CommerceCurrency commerceCurrency =
			_commerceCurrencyLocalService.getCommerceCurrency(
				contextCompany.getCompanyId(), cart.getCurrencyCode());

		if (commerceCurrency != null) {
			commerceCurrencyId = commerceCurrency.getCommerceCurrencyId();
		}

		CommerceAccount commerceAccount =
			_commerceAccountService.getCommerceAccount(cart.getAccountId());

		return _commerceOrderService.addCommerceOrder(
			userId, commerceChannelGroupId,
			commerceAccount.getCommerceAccountId(), commerceCurrencyId);
	}

	private long _getCommerceRegionId(
			CommerceAddress commerceAddress, CommerceCountry commerceCountry,
			Address address)
		throws Exception {

		if (Validator.isNull(address.getRegionISOCode()) &&
			(commerceAddress != null)) {

			return commerceAddress.getCommerceRegionId();
		}

		if (Validator.isNull(address.getRegionISOCode()) ||
			(commerceCountry == null)) {

			return 0;
		}

		CommerceRegion commerceRegion =
			_commerceRegionLocalService.getCommerceRegion(
				commerceCountry.getCommerceCountryId(),
				address.getRegionISOCode());

		return commerceRegion.getCommerceRegionId();
	}

	private Cart _toCart(CommerceOrder commerceOrder) throws Exception {
		return _cartDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceOrder.getCommerceOrderId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	private List<Cart> _toCarts(List<CommerceOrder> commerceOrders)
		throws Exception {

		List<Cart> carts = new ArrayList<>();

		for (CommerceOrder commerceOrder : commerceOrders) {
			carts.add(_toCart(commerceOrder));
		}

		return carts;
	}

	private void _updateCommerceOrderAddress(
			CommerceOrder commerceOrder, Address address, int type,
			ServiceContext serviceContext)
		throws Exception {

		CommerceAddress commerceAddress =
			_commerceAddressService.getCommerceAddress(
				commerceOrder.getShippingAddressId());

		CommerceCountry commerceCountry = commerceAddress.getCommerceCountry();

		_commerceAddressService.updateCommerceAddress(
			commerceAddress.getCommerceAddressId(), address.getName(),
			GetterUtil.get(
				address.getDescription(), commerceAddress.getDescription()),
			address.getStreet1(),
			GetterUtil.get(address.getStreet2(), commerceAddress.getStreet2()),
			GetterUtil.get(address.getStreet3(), commerceAddress.getStreet3()),
			address.getCity(),
			GetterUtil.get(address.getZip(), commerceAddress.getZip()),
			_getCommerceRegionId(commerceAddress, commerceCountry, address),
			commerceCountry.getCommerceCountryId(),
			GetterUtil.get(
				address.getPhoneNumber(), commerceAddress.getPhoneNumber()),
			type, serviceContext);
	}

	private void _updateOrder(CommerceOrder commerceOrder, Cart cart)
		throws Exception {

		long commerceShippingMethodId =
			commerceOrder.getCommerceShippingMethodId();

		CommerceShippingMethod commerceShippingMethod =
			_commerceShippingMethodService.fetchCommerceShippingMethod(
				commerceOrder.getGroupId(), cart.getShippingMethod());

		if (commerceShippingMethod != null) {
			commerceShippingMethodId =
				commerceShippingMethod.getCommerceShippingMethodId();
		}

		CommerceContext commerceContext = _commerceContextFactory.create(
			contextCompany.getCompanyId(), commerceOrder.getGroupId(),
			contextUser.getUserId(), commerceOrder.getCommerceOrderId(),
			commerceOrder.getCommerceAccountId());

		commerceOrder = _commerceOrderService.updateCommerceOrder(
			commerceOrder.getCommerceOrderId(),
			GetterUtil.get(
				cart.getBillingAddressId(),
				commerceOrder.getBillingAddressId()),
			GetterUtil.get(
				cart.getShippingAddressId(),
				commerceOrder.getShippingAddressId()),
			GetterUtil.get(
				cart.getPaymentMethod(),
				commerceOrder.getCommercePaymentMethodKey()),
			commerceShippingMethodId,
			GetterUtil.get(
				cart.getShippingOption(),
				commerceOrder.getShippingOptionName()),
			commerceOrder.getPurchaseOrderNumber(), commerceOrder.getSubtotal(),
			commerceOrder.getShippingAmount(), commerceOrder.getTotal(),
			commerceOrder.getAdvanceStatus(), commerceContext);

		// Expando

		Map<String, ?> customFields = cart.getCustomFields();

		if ((customFields != null) && !customFields.isEmpty()) {
			ExpandoUtil.updateExpando(
				contextCompany.getCompanyId(), CommerceOrder.class,
				commerceOrder.getPrimaryKey(), customFields);
		}

		// Update nested resources

		_upsertNestedResources(cart, commerceOrder, commerceContext);
	}

	private void _upsertBillingAddress(
			CommerceOrder commerceOrder, Address address, int type,
			CommerceContext commerceContext, ServiceContext serviceContext)
		throws Exception {

		if (commerceOrder.getBillingAddressId() > 0) {
			_updateCommerceOrderAddress(
				commerceOrder, address, type, serviceContext);
		}
		else {
			CommerceAddress commerceAddress = _addCommerceAddress(
				commerceOrder, address, type, serviceContext);

			commerceOrder.setBillingAddressId(
				commerceAddress.getCommerceAddressId());
		}

		_commerceOrderService.updateCommerceOrder(
			commerceOrder.getCommerceOrderId(),
			commerceOrder.getBillingAddressId(),
			commerceOrder.getShippingAddressId(),
			commerceOrder.getCommercePaymentMethodKey(),
			commerceOrder.getCommerceShippingMethodId(),
			commerceOrder.getShippingOptionName(),
			commerceOrder.getPurchaseOrderNumber(), commerceOrder.getSubtotal(),
			commerceOrder.getShippingAmount(), commerceOrder.getTotal(),
			commerceOrder.getAdvanceStatus(), commerceContext);
	}

	private void _upsertCommerceOrderItem(
			CartItem cartItem, CommerceOrder commerceOrder,
			CommerceContext commerceContext, ServiceContext serviceContext)
		throws Exception {

		CPInstance cpInstance = null;

		if (cartItem.getSkuId() != null) {
			cpInstance = _cpInstanceLocalService.getCPInstance(
				cartItem.getSkuId());
		}

		_commerceOrderItemService.upsertCommerceOrderItem(
			commerceOrder.getCommerceOrderId(), cpInstance.getCPInstanceId(),
			GetterUtil.get(cartItem.getQuantity(), 1), 0, cartItem.getOptions(),
			commerceContext, serviceContext);
	}

	private void _upsertNestedResources(
			Cart cart, CommerceOrder commerceOrder,
			CommerceContext commerceContext)
		throws Exception {

		ServiceContext serviceContext = _serviceContextHelper.getServiceContext(
			commerceOrder.getGroupId());

		// Order items

		CartItem[] orderItems = cart.getCartItems();

		if (orderItems != null) {
			_commerceOrderItemService.deleteCommerceOrderItems(
				commerceOrder.getCommerceOrderId());

			for (CartItem cartItem : orderItems) {
				_upsertCommerceOrderItem(
					cartItem, commerceOrder, commerceContext, serviceContext);
			}
		}

		commerceOrder.setBillingAddressId(
			GetterUtil.get(cart.getBillingAddressId(), 0));
		commerceOrder.setShippingAddressId(
			GetterUtil.get(cart.getShippingAddressId(), 0));

		boolean useAsBilling = GetterUtil.get(cart.getUseAsBilling(), false);
		int type = CommerceAddressConstants.ADDRESS_TYPE_SHIPPING;

		if (useAsBilling) {
			type = CommerceAddressConstants.ADDRESS_TYPE_BILLING_AND_SHIPPING;
		}

		// Shipping Address

		Address shippingAddress = cart.getShippingAddress();

		if (shippingAddress != null) {
			commerceOrder = _upsertShippingAddress(
				commerceOrder, shippingAddress, type, commerceContext,
				serviceContext);
		}

		if (useAsBilling) {
			_commerceOrderService.updateCommerceOrder(
				commerceOrder.getCommerceOrderId(),
				commerceOrder.getShippingAddressId(),
				commerceOrder.getShippingAddressId(),
				commerceOrder.getCommercePaymentMethodKey(),
				commerceOrder.getCommerceShippingMethodId(),
				commerceOrder.getShippingOptionName(),
				commerceOrder.getPurchaseOrderNumber(),
				commerceOrder.getSubtotal(), commerceOrder.getShippingAmount(),
				commerceOrder.getTotal(), commerceOrder.getAdvanceStatus(),
				commerceContext);
		}
		else {

			// Billing Address

			type = CommerceAddressConstants.ADDRESS_TYPE_BILLING;
			Address billingAddress = cart.getBillingAddress();

			if (billingAddress != null) {
				_upsertBillingAddress(
					commerceOrder, billingAddress, type, commerceContext,
					serviceContext);
			}
		}
	}

	private CommerceOrder _upsertShippingAddress(
			CommerceOrder commerceOrder, Address address, int type,
			CommerceContext commerceContext, ServiceContext serviceContext)
		throws Exception {

		if (commerceOrder.getShippingAddressId() > 0) {
			_updateCommerceOrderAddress(
				commerceOrder, address, type, serviceContext);
		}
		else {
			CommerceAddress commerceAddress = _addCommerceAddress(
				commerceOrder, address, type, serviceContext);

			commerceOrder.setShippingAddressId(
				commerceAddress.getCommerceAddressId());
		}

		return _commerceOrderService.updateCommerceOrder(
			commerceOrder.getCommerceOrderId(),
			commerceOrder.getBillingAddressId(),
			commerceOrder.getShippingAddressId(),
			commerceOrder.getCommercePaymentMethodKey(),
			commerceOrder.getCommerceShippingMethodId(),
			commerceOrder.getShippingOptionName(),
			commerceOrder.getPurchaseOrderNumber(), commerceOrder.getSubtotal(),
			commerceOrder.getShippingAmount(), commerceOrder.getTotal(),
			commerceOrder.getAdvanceStatus(), commerceContext);
	}

	private void _validateBillingAddressSelected(
			CommerceOrder commerceOrder, List<Error> errors)
		throws Exception {

		CommerceAddress billingAddress = commerceOrder.getBillingAddress();
		CommerceAddress shippingAddress = commerceOrder.getShippingAddress();

		if (billingAddress == null) {
			Error error = new Error();

			error.setErrorDescription("No billing address selected");

			errors.add(error);
		}

		if ((shippingAddress != null) &&
			(shippingAddress.getType() ==
				CommerceAddressConstants.ADDRESS_TYPE_BILLING_AND_SHIPPING)) {

			if (billingAddress.getCommerceAddressId() !=
					shippingAddress.getCommerceAddressId()) {

				Error error = new Error();

				error.setErrorDescription(
					"Shipping and billing address are not the same");

				errors.add(error);
			}
		}
		else if (billingAddress.getType() !=
					CommerceAddressConstants.ADDRESS_TYPE_BILLING) {

			Error error = new Error();

			error.setErrorDescription(
				"Billing address is not a billing address");

			errors.add(error);
		}
	}

	private void _validateCommerceOrderItems(
		CommerceOrder commerceOrder, List<Error> errors) {

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		if (commerceOrderItems.isEmpty()) {
			Error error = new Error();

			error.setErrorDescription("The cart is empty");

			errors.add(error);
		}
	}

	private List<Error> _validateOrder(CommerceOrder commerceOrder)
		throws Exception {

		List<Error> errors = new ArrayList<>();

		_validateCommerceOrderItems(commerceOrder, errors);
		_validateBillingAddressSelected(commerceOrder, errors);
		_validateShippingAddressSelected(commerceOrder, errors);
		_validateShippingMethodSelected(commerceOrder, errors);
		_validatePaymentMethodSelected(commerceOrder, errors);

		return errors;
	}

	private void _validatePaymentMethodSelected(
		CommerceOrder commerceOrder, List<Error> errors) {

		String commercePaymentMethodKey =
			commerceOrder.getCommercePaymentMethodKey();

		if ((commercePaymentMethodKey == null) ||
			commercePaymentMethodKey.isEmpty()) {

			Error error = new Error();

			error.setErrorDescription("No payment method selected");

			errors.add(error);
		}
	}

	private void _validateShippingAddressSelected(
			CommerceOrder commerceOrder, List<Error> errors)
		throws Exception {

		CommerceAddress shippingAddress = commerceOrder.getShippingAddress();

		if (shippingAddress == null) {
			Error error = new Error();

			error.setErrorDescription("No shipping address selected");

			errors.add(error);
		}

		if (shippingAddress.getType() ==
				CommerceAddressConstants.ADDRESS_TYPE_BILLING) {

			Error error = new Error();

			error.setErrorDescription("Shipping address of billing type");

			errors.add(error);
		}
	}

	private void _validateShippingMethodSelected(
			CommerceOrder commerceOrder, List<Error> errors)
		throws Exception {

		if (!_commerceShippingHelper.isShippable(commerceOrder)) {
			Error error = new Error();

			error.setErrorDescription("Not shippable item");

			errors.add(error);

			return;
		}

		CommerceShippingMethod commerceShippingMethod =
			commerceOrder.getCommerceShippingMethod();

		if (commerceShippingMethod == null) {
			Error error = new Error();

			error.setErrorDescription("No shipping method selected");

			errors.add(error);
		}
	}

	@Reference
	private CartDTOConverter _cartDTOConverter;

	@Reference
	private CommerceAccountService _commerceAccountService;

	@Reference
	private CommerceAddressService _commerceAddressService;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommerceContextFactory _commerceContextFactory;

	@Reference
	private CommerceCountryService _commerceCountryService;

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Reference
	private CommerceOrderEngine _commerceOrderEngine;

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceRegionLocalService _commerceRegionLocalService;

	@Reference
	private CommerceShippingHelper _commerceShippingHelper;

	@Reference
	private CommerceShippingMethodService _commerceShippingMethodService;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}