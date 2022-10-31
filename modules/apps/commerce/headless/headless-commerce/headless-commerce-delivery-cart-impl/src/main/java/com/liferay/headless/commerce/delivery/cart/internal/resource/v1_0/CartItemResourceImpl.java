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

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.context.CommerceContextFactory;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.Cart;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.CartItem;
import com.liferay.headless.commerce.delivery.cart.internal.dto.v1_0.CartItemDTOConverter;
import com.liferay.headless.commerce.delivery.cart.internal.dto.v1_0.CartItemDTOConverterContext;
import com.liferay.headless.commerce.delivery.cart.resource.v1_0.CartItemResource;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Andrea Sbarra
 * @author Alessio Antonio Rendina
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/cart-item.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {CartItemResource.class, NestedFieldSupport.class}
)
public class CartItemResourceImpl
	extends BaseCartItemResourceImpl implements NestedFieldSupport {

	@Override
	public Response deleteCartItem(Long cartItemId) throws Exception {
		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemService.getCommerceOrderItem(cartItemId);

		CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

		CommerceContext commerceContext = _commerceContextFactory.create(
			contextCompany.getCompanyId(), commerceOrder.getGroupId(),
			contextUser.getUserId(), commerceOrder.getCommerceOrderId(),
			commerceOrder.getCommerceAccountId());

		_commerceOrderItemService.deleteCommerceOrderItem(
			cartItemId, commerceContext);

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	public CartItem getCartItem(Long cartItemId) throws Exception {
		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemService.getCommerceOrderItem(cartItemId);

		CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

		return _toCartItem(
			commerceOrder.getCommerceAccountId(), commerceOrderItem);
	}

	@NestedField(parentClass = Cart.class, value = "cartItems")
	@Override
	public Page<CartItem> getCartItemsPage(
			@NestedFieldId("id") Long cartId, Long skuId, Pagination pagination)
		throws Exception {

		if (cartId == 0) {
			return Page.of(Collections.emptyList());
		}

		return Page.of(
			_filterCartItems(
				transform(
					_commerceOrderItemService.getCommerceOrderItems(
						cartId, QueryUtil.ALL_POS, QueryUtil.ALL_POS),
					commerceOrderItem -> {
						if ((skuId != null) &&
							!Objects.equals(
								commerceOrderItem.getCPInstanceId(), skuId)) {

							return null;
						}

						CommerceOrder commerceOrder =
							commerceOrderItem.getCommerceOrder();

						return _toCartItem(
							commerceOrder.getCommerceAccountId(),
							commerceOrderItem);
					})));
	}

	@Override
	public CartItem patchCartItem(Long cartItemId, CartItem cartItem)
		throws Exception {

		return super.patchCartItem(cartItemId, cartItem);
	}

	@Override
	public CartItem postCartItem(Long cartId, CartItem cartItem)
		throws Exception {

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			cartId);

		return _toCartItem(
			commerceOrder.getCommerceAccountId(),
			_commerceOrderItemService.addOrUpdateCommerceOrderItem(
				commerceOrder.getCommerceOrderId(), cartItem.getSkuId(),
				cartItem.getOptions(), cartItem.getQuantity(), 0,
				_commerceContextFactory.create(
					contextCompany.getCompanyId(), commerceOrder.getGroupId(),
					contextUser.getUserId(), cartId,
					commerceOrder.getCommerceAccountId()),
				_serviceContextHelper.getServiceContext(
					commerceOrder.getGroupId())));
	}

	@Override
	public CartItem putCartItem(Long cartItemId, CartItem cartItem)
		throws Exception {

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemService.getCommerceOrderItem(cartItemId);

		CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

		return _toCartItem(
			commerceOrder.getCommerceAccountId(),
			_commerceOrderItemService.updateCommerceOrderItem(
				commerceOrderItem.getCommerceOrderItemId(),
				cartItem.getQuantity(),
				_commerceContextFactory.create(
					contextCompany.getCompanyId(), commerceOrder.getGroupId(),
					contextUser.getUserId(), commerceOrder.getCommerceOrderId(),
					commerceOrder.getCommerceAccountId()),
				_serviceContextHelper.getServiceContext(
					commerceOrder.getGroupId())));
	}

	private List<CartItem> _filterCartItems(List<CartItem> cartItems) {
		Map<Long, CartItem> cartItemsMap = new HashMap<>();

		for (CartItem cartItem : cartItems) {
			cartItemsMap.put(cartItem.getId(), cartItem);
		}

		for (CartItem cartItem : cartItems) {
			Long parentCartItemId = cartItem.getParentCartItemId();

			if (parentCartItemId == null) {
				continue;
			}

			CartItem parentCartItem = cartItemsMap.get(parentCartItemId);

			if (parentCartItem == null) {
				continue;
			}

			if (parentCartItem.getCartItems() == null) {
				parentCartItem.setCartItems(new CartItem[0]);
			}

			parentCartItem.setCartItems(
				ArrayUtil.append(parentCartItem.getCartItems(), cartItem));

			cartItemsMap.remove(cartItem.getId());
		}

		return new ArrayList(cartItemsMap.values());
	}

	private CartItem _toCartItem(
			long commerceAccountId, CommerceOrderItem commerceOrderItem)
		throws Exception {

		return _orderItemDTOConverter.toDTO(
			new CartItemDTOConverterContext(
				commerceAccountId, commerceOrderItem.getCommerceOrderItemId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference
	private CommerceContextFactory _commerceContextFactory;

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CartItemDTOConverter _orderItemDTOConverter;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}