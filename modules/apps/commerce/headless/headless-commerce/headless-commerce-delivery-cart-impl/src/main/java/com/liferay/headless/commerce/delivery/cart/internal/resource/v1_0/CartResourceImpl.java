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
import com.liferay.commerce.constants.CommercePaymentConstants;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.context.CommerceContextFactory;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.exception.CommerceOrderBillingAddressException;
import com.liferay.commerce.exception.CommerceOrderGuestCheckoutException;
import com.liferay.commerce.exception.CommerceOrderShippingAddressException;
import com.liferay.commerce.exception.CommerceOrderShippingMethodException;
import com.liferay.commerce.exception.CommerceOrderStatusException;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.order.CommerceOrderValidatorRegistry;
import com.liferay.commerce.order.CommerceOrderValidatorResult;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.service.CommerceOrderTypeService;
import com.liferay.commerce.service.CommerceShippingMethodLocalService;
import com.liferay.commerce.util.CommerceCheckoutStep;
import com.liferay.commerce.util.CommerceCheckoutStepServicesTracker;
import com.liferay.headless.commerce.core.util.ExpandoUtil;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.Address;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.Cart;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.CartItem;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.CouponCode;
import com.liferay.headless.commerce.delivery.cart.internal.dto.v1_0.CartDTOConverter;
import com.liferay.headless.commerce.delivery.cart.internal.dto.v1_0.CartItemDTOConverter;
import com.liferay.headless.commerce.delivery.cart.internal.dto.v1_0.CartItemDTOConverterContext;
import com.liferay.headless.commerce.delivery.cart.resource.v1_0.CartResource;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.events.ServicePreAction;
import com.liferay.portal.events.ThemeServicePreAction;
import com.liferay.portal.kernel.encryptor.Encryptor;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.DummyHttpServletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.security.Key;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Andrea Sbarra
 * @author Alessio Antonio Rendina
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/cart.properties",
	scope = ServiceScope.PROTOTYPE, service = CartResource.class
)
public class CartResourceImpl extends BaseCartResourceImpl {

	@Override
	public Response deleteCart(Long cartId) throws Exception {
		_commerceOrderService.deleteCommerceOrder(cartId);

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	public Cart getCart(Long cartId) throws Exception {
		return _toCart(_commerceOrderService.getCommerceOrder(cartId));
	}

	@Override
	public String getCartPaymentURL(Long cartId, String callbackURL)
		throws Exception {

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			cartId);

		_initThemeDisplay(commerceOrder);

		StringBundler sb = new StringBundler(14);

		sb.append(_portal.getPortalURL(contextHttpServletRequest));
		sb.append(_portal.getPathModule());
		sb.append(CharPool.SLASH);
		sb.append(CommercePaymentConstants.SERVLET_PATH);
		sb.append("?groupId=");
		sb.append(commerceOrder.getGroupId());
		sb.append(StringPool.AMPERSAND);

		if (commerceOrder.isGuestOrder()) {
			sb.append("guestToken=");

			Key key = contextCompany.getKeyObj();

			sb.append(
				_encryptor.encrypt(
					key, String.valueOf(commerceOrder.getCommerceOrderId())));

			sb.append(StringPool.AMPERSAND);
		}

		sb.append("nextStep=");

		if (Validator.isNotNull(callbackURL)) {
			sb.append(callbackURL);
		}
		else {
			sb.append(
				URLCodec.encodeURL(
					_getOrderConfirmationCheckoutStepURL(commerceOrder)));
		}

		sb.append("&uuid=");
		sb.append(commerceOrder.getUuid());

		return sb.toString();
	}

	@Override
	public Page<Cart> getChannelCartsPage(
			Long accountId, Long channelId, Pagination pagination)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(channelId);

		List<CommerceOrder> commerceOrders =
			_commerceOrderService.getPendingCommerceOrders(
				commerceChannel.getGroupId(), accountId, null,
				pagination.getStartPosition(), pagination.getEndPosition());

		return Page.of(
			_toCarts(commerceOrders), pagination,
			_commerceOrderService.getPendingCommerceOrdersCount(
				commerceChannel.getGroupId(), accountId, null));
	}

	@Override
	public Cart patchCart(Long cartId, Cart cart) throws Exception {
		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			cartId);

		_updateOrder(commerceOrder, cart);

		return _toCart(commerceOrder);
	}

	@Override
	public Cart postCartCheckout(Long cartId) throws Exception {
		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			cartId);

		Cart cart = _validateOrder(commerceOrder);

		if (cart.getValid()) {
			try {
				commerceOrder = _commerceOrderEngine.checkoutCommerceOrder(
					commerceOrder, contextUser.getUserId());

				cart = _toCart(commerceOrder);
			}
			catch (Exception exception) {
				if (exception.getCause() instanceof
						CommerceOrderBillingAddressException) {

					cart.setValid(false);
					cart.setErrorMessages(
						new String[] {"Invalid billing address"});
				}

				if (exception.getCause() instanceof
						CommerceOrderGuestCheckoutException) {

					cart.setValid(false);
					cart.setErrorMessages(
						new String[] {"Invalid guest checkout"});
				}

				if (exception.getCause() instanceof
						CommerceOrderShippingAddressException) {

					cart.setValid(false);
					cart.setErrorMessages(
						new String[] {"Invalid shipping address"});
				}

				if (exception.getCause() instanceof
						CommerceOrderShippingMethodException) {

					cart.setValid(false);
					cart.setErrorMessages(
						new String[] {"Invalid shipping method"});
				}

				if (exception.getCause() instanceof
						CommerceOrderStatusException) {

					cart.setValid(false);
					cart.setErrorMessages(new String[] {"Invalid cart status"});
				}
			}
		}

		return cart;
	}

	@Override
	public Cart postCartCouponCode(Long cartId, CouponCode couponCode)
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
	public Cart postChannelCart(Long channelId, Cart cart) throws Exception {
		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(channelId);

		CommerceOrder commerceOrder = _addCommerceOrder(
			cart, commerceChannel.getGroupId());

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

	private CommerceAddress _addCommerceAddress(
			CommerceOrder commerceOrder, Address address, int type,
			ServiceContext serviceContext)
		throws Exception {

		Country country = _countryService.getCountryByA2(
			commerceOrder.getCompanyId(), address.getCountryISOCode());

		return _commerceAddressService.addCommerceAddress(
			commerceOrder.getModelClassName(),
			commerceOrder.getCommerceOrderId(), address.getName(),
			address.getDescription(), address.getStreet1(),
			address.getStreet2(), address.getStreet3(), address.getCity(),
			address.getZip(), _getRegionId(null, country, address),
			country.getCountryId(), address.getPhoneNumber(), type,
			serviceContext);
	}

	private CommerceOrder _addCommerceOrder(
			Cart cart, long commerceChannelGroupId)
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
			commerceChannelGroupId, commerceAccount.getCommerceAccountId(),
			commerceCurrencyId, _getCommerceOrderTypeId(cart));
	}

	private void _addOrUpdateBillingAddress(
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

		_commerceOrderEngine.updateCommerceOrder(
			commerceOrder.getExternalReferenceCode(),
			commerceOrder.getCommerceOrderId(),
			commerceOrder.getBillingAddressId(),
			commerceOrder.getCommerceShippingMethodId(),
			commerceOrder.getShippingAddressId(),
			commerceOrder.getAdvanceStatus(),
			commerceOrder.getCommercePaymentMethodKey(),
			commerceOrder.getPurchaseOrderNumber(),
			commerceOrder.getShippingAmount(),
			commerceOrder.getShippingOptionName(),
			commerceOrder.getShippingWithTaxAmount(),
			commerceOrder.getSubtotal(),
			commerceOrder.getSubtotalWithTaxAmount(),
			commerceOrder.getTaxAmount(), commerceOrder.getTotal(),
			commerceOrder.getTotalDiscountAmount(),
			commerceOrder.getTotalWithTaxAmount(), commerceContext, true);
	}

	private void _addOrUpdateCommerceOrderItem(
			CartItem cartItem, CommerceOrder commerceOrder,
			CommerceContext commerceContext, ServiceContext serviceContext)
		throws Exception {

		CPInstance cpInstance = null;

		if (cartItem.getSkuId() != null) {
			cpInstance = _cpInstanceLocalService.getCPInstance(
				cartItem.getSkuId());
		}

		_commerceOrderItemService.addOrUpdateCommerceOrderItem(
			commerceOrder.getCommerceOrderId(), cpInstance.getCPInstanceId(),
			cartItem.getOptions(), GetterUtil.get(cartItem.getQuantity(), 1), 0,
			commerceContext, serviceContext);
	}

	private void _addOrUpdateNestedResources(
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
				_addOrUpdateCommerceOrderItem(
					cartItem, commerceOrder, commerceContext, serviceContext);
			}

			commerceOrder = _commerceOrderService.recalculatePrice(
				commerceOrder.getCommerceOrderId(), commerceContext);
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
			commerceOrder = _addOrUpdateShippingAddress(
				commerceOrder, shippingAddress, type, commerceContext,
				serviceContext);
		}

		if (useAsBilling) {
			_commerceOrderEngine.updateCommerceOrder(
				commerceOrder.getExternalReferenceCode(),
				commerceOrder.getCommerceOrderId(),
				commerceOrder.getBillingAddressId(),
				commerceOrder.getCommerceShippingMethodId(),
				commerceOrder.getShippingAddressId(),
				commerceOrder.getAdvanceStatus(),
				commerceOrder.getCommercePaymentMethodKey(),
				commerceOrder.getPurchaseOrderNumber(),
				commerceOrder.getShippingAmount(),
				commerceOrder.getShippingOptionName(),
				commerceOrder.getShippingWithTaxAmount(),
				commerceOrder.getSubtotal(),
				commerceOrder.getSubtotalWithTaxAmount(),
				commerceOrder.getTaxAmount(), commerceOrder.getTotal(),
				commerceOrder.getTotalDiscountAmount(),
				commerceOrder.getTotalWithTaxAmount(), commerceContext, true);
		}
		else {

			// Billing Address

			Address billingAddress = cart.getBillingAddress();

			if (billingAddress != null) {
				_addOrUpdateBillingAddress(
					commerceOrder, billingAddress,
					CommerceAddressConstants.ADDRESS_TYPE_BILLING,
					commerceContext, serviceContext);
			}
		}
	}

	private CommerceOrder _addOrUpdateShippingAddress(
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

		return _commerceOrderEngine.updateCommerceOrder(
			commerceOrder.getExternalReferenceCode(),
			commerceOrder.getCommerceOrderId(),
			commerceOrder.getBillingAddressId(),
			commerceOrder.getCommerceShippingMethodId(),
			commerceOrder.getShippingAddressId(),
			commerceOrder.getAdvanceStatus(),
			commerceOrder.getCommercePaymentMethodKey(),
			commerceOrder.getPurchaseOrderNumber(),
			commerceOrder.getShippingAmount(),
			commerceOrder.getShippingOptionName(),
			commerceOrder.getShippingWithTaxAmount(),
			commerceOrder.getSubtotal(),
			commerceOrder.getSubtotalWithTaxAmount(),
			commerceOrder.getTaxAmount(), commerceOrder.getTotal(),
			commerceOrder.getTotalDiscountAmount(),
			commerceOrder.getTotalWithTaxAmount(), commerceContext, true);
	}

	private long _getCommerceOrderTypeId(Cart cart) throws Exception {
		if (cart.getOrderTypeId() != null) {
			return cart.getOrderTypeId();
		}

		CommerceOrderType commerceOrderType =
			_commerceOrderTypeService.fetchByExternalReferenceCode(
				cart.getOrderTypeExternalReferenceCode(),
				contextCompany.getCompanyId());

		if (commerceOrderType != null) {
			return commerceOrderType.getCommerceOrderTypeId();
		}

		return 0;
	}

	private String _getOrderConfirmationCheckoutStepURL(
			CommerceOrder commerceOrder)
		throws Exception {

		return PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				contextHttpServletRequest,
				CommercePortletKeys.COMMERCE_CHECKOUT,
				PortletProvider.Action.VIEW)
		).setParameter(
			"checkoutStepName",
			() -> {
				CommerceCheckoutStep commerceCheckoutStep =
					_commerceCheckoutStepServicesTracker.
						getCommerceCheckoutStep("order-confirmation");

				return commerceCheckoutStep.getName();
			}
		).setParameter(
			"commerceOrderUuid", commerceOrder.getUuid()
		).buildString();
	}

	private long _getRegionId(
			CommerceAddress commerceAddress, Country country, Address address)
		throws Exception {

		if (Validator.isNull(address.getRegionISOCode()) &&
			(commerceAddress != null)) {

			return commerceAddress.getRegionId();
		}

		if (Validator.isNull(address.getRegionISOCode()) || (country == null)) {
			return 0;
		}

		Region region = _regionLocalService.getRegion(
			country.getCountryId(), address.getRegionISOCode());

		return region.getRegionId();
	}

	private CartItem[] _getValidatedCommerceOrderItems(
			CommerceOrder commerceOrder, Cart cart)
		throws Exception {

		List<CartItem> cartItems = new ArrayList<>();

		Map<Long, List<CommerceOrderValidatorResult>>
			commerceOrderValidatorResults =
				_commerceOrderValidatorRegistry.
					getCommerceOrderValidatorResults(null, commerceOrder);

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			CartItem cartItem = _cartItemDTOConverter.toDTO(
				new CartItemDTOConverterContext(
					commerceOrder.getCommerceAccountId(),
					commerceOrderItem.getCommerceOrderItemId(),
					contextAcceptLanguage.getPreferredLocale()));

			if (commerceOrderValidatorResults.containsKey(
					commerceOrderItem.getCommerceOrderItemId())) {

				List<CommerceOrderValidatorResult>
					commerceOrderItemValidatorResults =
						commerceOrderValidatorResults.get(
							commerceOrderItem.getCommerceOrderItemId());

				Stream<CommerceOrderValidatorResult>
					commerceOrderValidatorResultStream =
						commerceOrderItemValidatorResults.stream();

				boolean cartItemValid = commerceOrderValidatorResultStream.map(
					commerceOrderItemValidatorResult ->
						commerceOrderItemValidatorResult.isValid()
				).reduce(
					true, Boolean::logicalAnd
				);

				cartItem.setValid(cartItemValid);

				cart.setValid(cartItemValid);

				commerceOrderValidatorResultStream =
					commerceOrderItemValidatorResults.stream();

				cartItem.setErrorMessages(
					commerceOrderValidatorResultStream.map(
						commerceOrderItemValidatorResult ->
							commerceOrderItemValidatorResult.
								getLocalizedMessage()
					).toArray(
						String[]::new
					));
			}

			cartItems.add(cartItem);
		}

		return cartItems.toArray(new CartItem[0]);
	}

	private void _initThemeDisplay(CommerceOrder commerceOrder)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)contextHttpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay != null) {
			return;
		}

		ServicePreAction servicePreAction = new ServicePreAction();

		HttpServletResponse httpServletResponse =
			new DummyHttpServletResponse();

		servicePreAction.servicePre(
			contextHttpServletRequest, httpServletResponse, false);

		ThemeServicePreAction themeServicePreAction =
			new ThemeServicePreAction();

		themeServicePreAction.run(
			contextHttpServletRequest, httpServletResponse);

		themeDisplay = (ThemeDisplay)contextHttpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannelByOrderGroupId(
				commerceOrder.getGroupId());

		themeDisplay.setScopeGroupId(commerceChannel.getSiteGroupId());
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

		Country country = commerceAddress.getCountry();

		_commerceAddressService.updateCommerceAddress(
			commerceAddress.getCommerceAddressId(), address.getName(),
			GetterUtil.get(
				address.getDescription(), commerceAddress.getDescription()),
			address.getStreet1(),
			GetterUtil.get(address.getStreet2(), commerceAddress.getStreet2()),
			GetterUtil.get(address.getStreet3(), commerceAddress.getStreet3()),
			address.getCity(),
			GetterUtil.get(address.getZip(), commerceAddress.getZip()),
			_getRegionId(commerceAddress, country, address),
			country.getCountryId(),
			GetterUtil.get(
				address.getPhoneNumber(), commerceAddress.getPhoneNumber()),
			type, serviceContext);
	}

	private void _updateOrder(CommerceOrder commerceOrder, Cart cart)
		throws Exception {

		long commerceShippingMethodId =
			commerceOrder.getCommerceShippingMethodId();

		CommerceShippingMethod commerceShippingMethod =
			_commerceShippingMethodLocalService.fetchCommerceShippingMethod(
				commerceOrder.getGroupId(), cart.getShippingMethod());

		if (commerceShippingMethod != null) {
			commerceShippingMethodId =
				commerceShippingMethod.getCommerceShippingMethodId();
		}

		CommerceContext commerceContext = _commerceContextFactory.create(
			contextCompany.getCompanyId(), commerceOrder.getGroupId(),
			contextUser.getUserId(), commerceOrder.getCommerceOrderId(),
			commerceOrder.getCommerceAccountId());

		_commerceOrderEngine.updateCommerceOrder(
			commerceOrder.getExternalReferenceCode(),
			commerceOrder.getCommerceOrderId(),
			GetterUtil.get(
				cart.getBillingAddressId(),
				commerceOrder.getBillingAddressId()),
			commerceShippingMethodId,
			GetterUtil.get(
				cart.getShippingAddressId(),
				commerceOrder.getShippingAddressId()),
			commerceOrder.getAdvanceStatus(),
			GetterUtil.get(
				cart.getPaymentMethod(),
				commerceOrder.getCommercePaymentMethodKey()),
			commerceOrder.getPurchaseOrderNumber(),
			commerceOrder.getShippingAmount(),
			GetterUtil.get(
				cart.getShippingOption(),
				commerceOrder.getShippingOptionName()),
			commerceOrder.getShippingWithTaxAmount(),
			commerceOrder.getSubtotal(),
			commerceOrder.getSubtotalWithTaxAmount(),
			commerceOrder.getTaxAmount(), commerceOrder.getTotal(),
			commerceOrder.getTotalDiscountAmount(),
			commerceOrder.getTotalWithTaxAmount(), commerceContext, true);

		// Expando

		Map<String, ?> customFields = cart.getCustomFields();

		if ((customFields != null) && !customFields.isEmpty()) {
			ExpandoUtil.updateExpando(
				contextCompany.getCompanyId(), CommerceOrder.class,
				commerceOrder.getPrimaryKey(), customFields);
		}

		// Update nested resources

		_addOrUpdateNestedResources(cart, commerceOrder, commerceContext);
	}

	private Cart _validateOrder(CommerceOrder commerceOrder) throws Exception {
		List<String> errorMessages = new ArrayList<>();

		Cart cart = _toCart(commerceOrder);

		cart.setValid(true);

		if (!errorMessages.isEmpty()) {
			cart.setValid(false);
			cart.setErrorMessages(errorMessages.toArray(new String[0]));
		}

		CartItem[] validatedCartItems = _getValidatedCommerceOrderItems(
			commerceOrder, cart);

		cart.setCartItems(validatedCartItems);

		return cart;
	}

	@Reference
	private CartDTOConverter _cartDTOConverter;

	@Reference
	private CartItemDTOConverter _cartItemDTOConverter;

	@Reference
	private CommerceAccountService _commerceAccountService;

	@Reference
	private CommerceAddressService _commerceAddressService;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommerceCheckoutStepServicesTracker
		_commerceCheckoutStepServicesTracker;

	@Reference
	private CommerceContextFactory _commerceContextFactory;

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Reference
	private CommerceOrderEngine _commerceOrderEngine;

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceOrderTypeService _commerceOrderTypeService;

	@Reference
	private CommerceOrderValidatorRegistry _commerceOrderValidatorRegistry;

	@Reference
	private CommerceShippingMethodLocalService
		_commerceShippingMethodLocalService;

	@Reference
	private CountryService _countryService;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private Encryptor _encryptor;

	@Reference
	private Portal _portal;

	@Reference
	private RegionLocalService _regionLocalService;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}