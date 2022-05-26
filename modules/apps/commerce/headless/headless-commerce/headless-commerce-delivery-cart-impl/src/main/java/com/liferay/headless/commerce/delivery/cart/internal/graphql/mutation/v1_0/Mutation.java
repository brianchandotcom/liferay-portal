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

package com.liferay.headless.commerce.delivery.cart.internal.graphql.mutation.v1_0;

import com.liferay.headless.commerce.delivery.cart.dto.v1_0.Cart;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.CartComment;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.CartItem;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.CouponCode;
import com.liferay.headless.commerce.delivery.cart.resource.v1_0.CartCommentResource;
import com.liferay.headless.commerce.delivery.cart.resource.v1_0.CartItemResource;
import com.liferay.headless.commerce.delivery.cart.resource.v1_0.CartResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Andrea Sbarra
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setCartResourceComponentServiceObjects(
		ComponentServiceObjects<CartResource>
			cartResourceComponentServiceObjects) {

		_cartResourceComponentServiceObjects =
			cartResourceComponentServiceObjects;
	}

	public static void setCartCommentResourceComponentServiceObjects(
		ComponentServiceObjects<CartCommentResource>
			cartCommentResourceComponentServiceObjects) {

		_cartCommentResourceComponentServiceObjects =
			cartCommentResourceComponentServiceObjects;
	}

	public static void setCartItemResourceComponentServiceObjects(
		ComponentServiceObjects<CartItemResource>
			cartItemResourceComponentServiceObjects) {

		_cartItemResourceComponentServiceObjects =
			cartItemResourceComponentServiceObjects;
	}

	@GraphQLField
	public Response deleteCart(@GraphQLName("cartId") Long cartId)
		throws Exception {

		return _applyComponentServiceObjects(
			_cartResourceComponentServiceObjects,
			this::_populateResourceContext,
			cartResource -> cartResource.deleteCart(cartId));
	}

	@GraphQLField
	public Cart patchCart(
			@GraphQLName("cartId") Long cartId, @GraphQLName("cart") Cart cart)
		throws Exception {

		return _applyComponentServiceObjects(
			_cartResourceComponentServiceObjects,
			this::_populateResourceContext,
			cartResource -> cartResource.patchCart(cartId, cart));
	}

	@GraphQLField
	public Cart updateCart(
			@GraphQLName("cartId") Long cartId, @GraphQLName("cart") Cart cart)
		throws Exception {

		return _applyComponentServiceObjects(
			_cartResourceComponentServiceObjects,
			this::_populateResourceContext,
			cartResource -> cartResource.putCart(cartId, cart));
	}

	@GraphQLField
	public Cart createCartCheckout(@GraphQLName("cartId") Long cartId)
		throws Exception {

		return _applyComponentServiceObjects(
			_cartResourceComponentServiceObjects,
			this::_populateResourceContext,
			cartResource -> cartResource.postCartCheckout(cartId));
	}

	@GraphQLField(
		description = "Add new Items to a Cart, return the whole Cart updated."
	)
	public Cart createCartCouponCode(
			@GraphQLName("cartId") Long cartId,
			@GraphQLName("couponCode") CouponCode couponCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_cartResourceComponentServiceObjects,
			this::_populateResourceContext,
			cartResource -> cartResource.postCartCouponCode(
				cartId, couponCode));
	}

	@GraphQLField
	public Cart createChannelCart(
			@GraphQLName("channelId") Long channelId,
			@GraphQLName("cart") Cart cart)
		throws Exception {

		return _applyComponentServiceObjects(
			_cartResourceComponentServiceObjects,
			this::_populateResourceContext,
			cartResource -> cartResource.postChannelCart(channelId, cart));
	}

	@GraphQLField
	public Response deleteCartComment(
			@GraphQLName("cartCommentId") Long cartCommentId)
		throws Exception {

		return _applyComponentServiceObjects(
			_cartCommentResourceComponentServiceObjects,
			this::_populateResourceContext,
			cartCommentResource -> cartCommentResource.deleteCartComment(
				cartCommentId));
	}

	@GraphQLField
	public CartComment patchCartComment(
			@GraphQLName("cartCommentId") Long cartCommentId,
			@GraphQLName("cartComment") CartComment cartComment)
		throws Exception {

		return _applyComponentServiceObjects(
			_cartCommentResourceComponentServiceObjects,
			this::_populateResourceContext,
			cartCommentResource -> cartCommentResource.patchCartComment(
				cartCommentId, cartComment));
	}

	@GraphQLField
	public CartComment updateCartComment(
			@GraphQLName("cartCommentId") Long cartCommentId,
			@GraphQLName("cartComment") CartComment cartComment)
		throws Exception {

		return _applyComponentServiceObjects(
			_cartCommentResourceComponentServiceObjects,
			this::_populateResourceContext,
			cartCommentResource -> cartCommentResource.putCartComment(
				cartCommentId, cartComment));
	}

	@GraphQLField
	public CartComment createCartComment(
			@GraphQLName("cartId") Long cartId,
			@GraphQLName("cartComment") CartComment cartComment)
		throws Exception {

		return _applyComponentServiceObjects(
			_cartCommentResourceComponentServiceObjects,
			this::_populateResourceContext,
			cartCommentResource -> cartCommentResource.postCartComment(
				cartId, cartComment));
	}

	@GraphQLField(description = "Deletes an Cart Item by ID.")
	public Response deleteCartItem(@GraphQLName("cartItemId") Long cartItemId)
		throws Exception {

		return _applyComponentServiceObjects(
			_cartItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			cartItemResource -> cartItemResource.deleteCartItem(cartItemId));
	}

	@GraphQLField(description = "Retrive information of the given Cart.")
	public CartItem patchCartItem(
			@GraphQLName("cartItemId") Long cartItemId,
			@GraphQLName("cartItem") CartItem cartItem)
		throws Exception {

		return _applyComponentServiceObjects(
			_cartItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			cartItemResource -> cartItemResource.patchCartItem(
				cartItemId, cartItem));
	}

	@GraphQLField(description = "update the given Cart.")
	public CartItem updateCartItem(
			@GraphQLName("cartItemId") Long cartItemId,
			@GraphQLName("cartItem") CartItem cartItem)
		throws Exception {

		return _applyComponentServiceObjects(
			_cartItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			cartItemResource -> cartItemResource.putCartItem(
				cartItemId, cartItem));
	}

	@GraphQLField(
		description = "Add new Items to a Cart, return the whole Cart updated."
	)
	public CartItem createCartItem(
			@GraphQLName("cartId") Long cartId,
			@GraphQLName("cartItem") CartItem cartItem)
		throws Exception {

		return _applyComponentServiceObjects(
			_cartItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			cartItemResource -> cartItemResource.postCartItem(
				cartId, cartItem));
	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private <T, E1 extends Throwable, E2 extends Throwable> void
			_applyVoidComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeConsumer<T, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			unsafeFunction.accept(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(CartResource cartResource)
		throws Exception {

		cartResource.setContextAcceptLanguage(_acceptLanguage);
		cartResource.setContextCompany(_company);
		cartResource.setContextHttpServletRequest(_httpServletRequest);
		cartResource.setContextHttpServletResponse(_httpServletResponse);
		cartResource.setContextUriInfo(_uriInfo);
		cartResource.setContextUser(_user);
		cartResource.setGroupLocalService(_groupLocalService);
		cartResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			CartCommentResource cartCommentResource)
		throws Exception {

		cartCommentResource.setContextAcceptLanguage(_acceptLanguage);
		cartCommentResource.setContextCompany(_company);
		cartCommentResource.setContextHttpServletRequest(_httpServletRequest);
		cartCommentResource.setContextHttpServletResponse(_httpServletResponse);
		cartCommentResource.setContextUriInfo(_uriInfo);
		cartCommentResource.setContextUser(_user);
		cartCommentResource.setGroupLocalService(_groupLocalService);
		cartCommentResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(CartItemResource cartItemResource)
		throws Exception {

		cartItemResource.setContextAcceptLanguage(_acceptLanguage);
		cartItemResource.setContextCompany(_company);
		cartItemResource.setContextHttpServletRequest(_httpServletRequest);
		cartItemResource.setContextHttpServletResponse(_httpServletResponse);
		cartItemResource.setContextUriInfo(_uriInfo);
		cartItemResource.setContextUser(_user);
		cartItemResource.setGroupLocalService(_groupLocalService);
		cartItemResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<CartResource>
		_cartResourceComponentServiceObjects;
	private static ComponentServiceObjects<CartCommentResource>
		_cartCommentResourceComponentServiceObjects;
	private static ComponentServiceObjects<CartItemResource>
		_cartItemResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}