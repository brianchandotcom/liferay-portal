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

package com.liferay.headless.commerce.delivery.cart.internal.graphql.servlet.v1_0;

import com.liferay.headless.commerce.delivery.cart.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.headless.commerce.delivery.cart.internal.graphql.query.v1_0.Query;
import com.liferay.headless.commerce.delivery.cart.internal.resource.v1_0.AddressResourceImpl;
import com.liferay.headless.commerce.delivery.cart.internal.resource.v1_0.CartCommentResourceImpl;
import com.liferay.headless.commerce.delivery.cart.internal.resource.v1_0.CartItemResourceImpl;
import com.liferay.headless.commerce.delivery.cart.internal.resource.v1_0.CartResourceImpl;
import com.liferay.headless.commerce.delivery.cart.internal.resource.v1_0.PaymentMethodResourceImpl;
import com.liferay.headless.commerce.delivery.cart.internal.resource.v1_0.ShippingMethodResourceImpl;
import com.liferay.headless.commerce.delivery.cart.resource.v1_0.AddressResource;
import com.liferay.headless.commerce.delivery.cart.resource.v1_0.CartCommentResource;
import com.liferay.headless.commerce.delivery.cart.resource.v1_0.CartItemResource;
import com.liferay.headless.commerce.delivery.cart.resource.v1_0.CartResource;
import com.liferay.headless.commerce.delivery.cart.resource.v1_0.PaymentMethodResource;
import com.liferay.headless.commerce.delivery.cart.resource.v1_0.ShippingMethodResource;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Andrea Sbarra
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setCartResourceComponentServiceObjects(
			_cartResourceComponentServiceObjects);
		Mutation.setCartCommentResourceComponentServiceObjects(
			_cartCommentResourceComponentServiceObjects);
		Mutation.setCartItemResourceComponentServiceObjects(
			_cartItemResourceComponentServiceObjects);

		Query.setAddressResourceComponentServiceObjects(
			_addressResourceComponentServiceObjects);
		Query.setCartResourceComponentServiceObjects(
			_cartResourceComponentServiceObjects);
		Query.setCartCommentResourceComponentServiceObjects(
			_cartCommentResourceComponentServiceObjects);
		Query.setCartItemResourceComponentServiceObjects(
			_cartItemResourceComponentServiceObjects);
		Query.setPaymentMethodResourceComponentServiceObjects(
			_paymentMethodResourceComponentServiceObjects);
		Query.setShippingMethodResourceComponentServiceObjects(
			_shippingMethodResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Headless.Commerce.Delivery.Cart";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/headless-commerce-delivery-cart-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	public ObjectValuePair<Class<?>, String> getResourceMethodPair(
		String methodName, boolean mutation) {

		if (mutation) {
			return _resourceMethodPairs.get("mutation#" + methodName);
		}

		return _resourceMethodPairs.get("query#" + methodName);
	}

	private static final Map<String, ObjectValuePair<Class<?>, String>>
		_resourceMethodPairs = new HashMap<>();

	static {
		_resourceMethodPairs.put(
			"mutation#deleteCart",
			new ObjectValuePair<>(CartResourceImpl.class, "deleteCart"));
		_resourceMethodPairs.put(
			"mutation#deleteCartBatch",
			new ObjectValuePair<>(CartResourceImpl.class, "deleteCartBatch"));
		_resourceMethodPairs.put(
			"mutation#patchCart",
			new ObjectValuePair<>(CartResourceImpl.class, "patchCart"));
		_resourceMethodPairs.put(
			"mutation#updateCart",
			new ObjectValuePair<>(CartResourceImpl.class, "putCart"));
		_resourceMethodPairs.put(
			"mutation#updateCartBatch",
			new ObjectValuePair<>(CartResourceImpl.class, "putCartBatch"));
		_resourceMethodPairs.put(
			"mutation#createCartCheckout",
			new ObjectValuePair<>(CartResourceImpl.class, "postCartCheckout"));
		_resourceMethodPairs.put(
			"mutation#createCartCouponCode",
			new ObjectValuePair<>(
				CartResourceImpl.class, "postCartCouponCode"));
		_resourceMethodPairs.put(
			"mutation#createChannelCart",
			new ObjectValuePair<>(CartResourceImpl.class, "postChannelCart"));
		_resourceMethodPairs.put(
			"mutation#deleteCartComment",
			new ObjectValuePair<>(
				CartCommentResourceImpl.class, "deleteCartComment"));
		_resourceMethodPairs.put(
			"mutation#deleteCartCommentBatch",
			new ObjectValuePair<>(
				CartCommentResourceImpl.class, "deleteCartCommentBatch"));
		_resourceMethodPairs.put(
			"mutation#patchCartComment",
			new ObjectValuePair<>(
				CartCommentResourceImpl.class, "patchCartComment"));
		_resourceMethodPairs.put(
			"mutation#updateCartComment",
			new ObjectValuePair<>(
				CartCommentResourceImpl.class, "putCartComment"));
		_resourceMethodPairs.put(
			"mutation#updateCartCommentBatch",
			new ObjectValuePair<>(
				CartCommentResourceImpl.class, "putCartCommentBatch"));
		_resourceMethodPairs.put(
			"mutation#createCartComment",
			new ObjectValuePair<>(
				CartCommentResourceImpl.class, "postCartComment"));
		_resourceMethodPairs.put(
			"mutation#deleteCartItem",
			new ObjectValuePair<>(
				CartItemResourceImpl.class, "deleteCartItem"));
		_resourceMethodPairs.put(
			"mutation#deleteCartItemBatch",
			new ObjectValuePair<>(
				CartItemResourceImpl.class, "deleteCartItemBatch"));
		_resourceMethodPairs.put(
			"mutation#patchCartItem",
			new ObjectValuePair<>(CartItemResourceImpl.class, "patchCartItem"));
		_resourceMethodPairs.put(
			"mutation#updateCartItem",
			new ObjectValuePair<>(CartItemResourceImpl.class, "putCartItem"));
		_resourceMethodPairs.put(
			"mutation#updateCartItemBatch",
			new ObjectValuePair<>(
				CartItemResourceImpl.class, "putCartItemBatch"));
		_resourceMethodPairs.put(
			"mutation#createCartItem",
			new ObjectValuePair<>(CartItemResourceImpl.class, "postCartItem"));
		_resourceMethodPairs.put(
			"query#cartBillingAddres",
			new ObjectValuePair<>(
				AddressResourceImpl.class, "getCartBillingAddres"));
		_resourceMethodPairs.put(
			"query#cartShippingAddres",
			new ObjectValuePair<>(
				AddressResourceImpl.class, "getCartShippingAddres"));
		_resourceMethodPairs.put(
			"query#cart",
			new ObjectValuePair<>(CartResourceImpl.class, "getCart"));
		_resourceMethodPairs.put(
			"query#cartPaymentURL",
			new ObjectValuePair<>(CartResourceImpl.class, "getCartPaymentURL"));
		_resourceMethodPairs.put(
			"query#channelCarts",
			new ObjectValuePair<>(
				CartResourceImpl.class, "getChannelCartsPage"));
		_resourceMethodPairs.put(
			"query#cartComment",
			new ObjectValuePair<>(
				CartCommentResourceImpl.class, "getCartComment"));
		_resourceMethodPairs.put(
			"query#cartComments",
			new ObjectValuePair<>(
				CartCommentResourceImpl.class, "getCartCommentsPage"));
		_resourceMethodPairs.put(
			"query#cartItem",
			new ObjectValuePair<>(CartItemResourceImpl.class, "getCartItem"));
		_resourceMethodPairs.put(
			"query#cartItems",
			new ObjectValuePair<>(
				CartItemResourceImpl.class, "getCartItemsPage"));
		_resourceMethodPairs.put(
			"query#cartPaymentMethods",
			new ObjectValuePair<>(
				PaymentMethodResourceImpl.class, "getCartPaymentMethodsPage"));
		_resourceMethodPairs.put(
			"query#cartShippingMethods",
			new ObjectValuePair<>(
				ShippingMethodResourceImpl.class,
				"getCartShippingMethodsPage"));
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<CartResource>
		_cartResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<CartCommentResource>
		_cartCommentResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<CartItemResource>
		_cartItemResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<AddressResource>
		_addressResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PaymentMethodResource>
		_paymentMethodResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ShippingMethodResource>
		_shippingMethodResourceComponentServiceObjects;

}