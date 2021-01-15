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
import com.liferay.headless.commerce.delivery.cart.resource.v1_0.CartItemResource;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.HashMap;
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
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/cart-item.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {CartItemResource.class, NestedFieldSupport.class}
)
public class CartItemResourceImpl
	extends BaseCartItemResourceImpl implements NestedFieldSupport {

	@Override
	public Response deleteCartItem(@NotNull Long cartItemId) throws Exception {
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
	public CartItem getCartItem(@NotNull Long cartItemId) throws Exception {
		return _toCartItem(
			_commerceOrderItemService.getCommerceOrderItem(cartItemId));
	}

	@NestedField(parentClass = Cart.class, value = "cartItems")
	@Override
	public Page<CartItem> getCartItemsPage(
			@NestedFieldId("id") @NotNull Long cartId, Pagination pagination)
		throws Exception {

		return Page.of(
			_toCartItems(
				_commerceOrderItemService.getCommerceOrderItems(
					cartId, QueryUtil.ALL_POS, QueryUtil.ALL_POS)));
	}

	@Override
	public CartItem postCartItem(@NotNull Long cartId, CartItem cartItem)
		throws Exception {

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			cartId);

		ServiceContext serviceContext = _serviceContextHelper.getServiceContext(
			commerceOrder.getGroupId());

		CommerceContext commerceContext = _commerceContextFactory.create(
			contextCompany.getCompanyId(), commerceOrder.getGroupId(),
			contextUser.getUserId(), cartId,
			commerceOrder.getCommerceAccountId());

		return _toCartItem(
			_commerceOrderItemService.upsertCommerceOrderItem(
				commerceOrder.getCommerceOrderId(), cartItem.getSkuId(),
				cartItem.getOptions(), cartItem.getQuantity(), 0,
				commerceContext, serviceContext));
	}

	@Override
	public CartItem putCartItem(@NotNull Long cartItemId, CartItem cartItem)
		throws Exception {

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemService.getCommerceOrderItem(cartItemId);

		ServiceContext serviceContext = _serviceContextHelper.getServiceContext(
			commerceOrderItem.getGroupId());

		CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

		CommerceContext commerceContext = _commerceContextFactory.create(
			contextCompany.getCompanyId(), commerceOrder.getGroupId(),
			contextUser.getUserId(), commerceOrder.getCommerceOrderId(),
			commerceOrder.getCommerceAccountId());

		return _toCartItem(
			_commerceOrderItemService.updateCommerceOrderItem(
				commerceOrderItem.getCommerceOrderItemId(),
				cartItem.getQuantity(), commerceContext, serviceContext));
	}

	private List<CartItem> _handleProductBundle(List<CartItem> cartItems) {
		Map<Long, CartItem> cartItemMap = new HashMap<>();

		for (CartItem cartItem : cartItems) {
			cartItemMap.put(cartItem.getId(), cartItem);
		}

		for (CartItem cartItem : cartItems) {
			Long parentId = cartItem.getParentCartItemId();

			if (parentId != null) {
				CartItem parent = cartItemMap.get(parentId);

				if (parent != null) {
					if (parent.getCartItems() == null) {
						parent.setCartItems(new CartItem[0]);
					}

					parent.setCartItems(
						ArrayUtil.append(parent.getCartItems(), cartItem));
					cartItemMap.remove(cartItem.getId());
				}
			}
		}

		return new ArrayList(cartItemMap.values());
	}

	private CartItem _toCartItem(CommerceOrderItem commerceOrderItem)
		throws Exception {

		return _orderItemDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceOrderItem.getCommerceOrderItemId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	private List<CartItem> _toCartItems(
			List<CommerceOrderItem> commerceOrderItems)
		throws Exception {

		List<CartItem> cartItems = new ArrayList<>();

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			cartItems.add(_toCartItem(commerceOrderItem));
		}

		return _handleProductBundle(cartItems);
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