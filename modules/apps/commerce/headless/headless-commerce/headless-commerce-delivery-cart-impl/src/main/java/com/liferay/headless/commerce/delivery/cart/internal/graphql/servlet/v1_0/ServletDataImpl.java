/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		Mutation.setPaymentMethodResourceComponentServiceObjects(
			_paymentMethodResourceComponentServiceObjects);
		Mutation.setShippingMethodResourceComponentServiceObjects(
			_shippingMethodResourceComponentServiceObjects);

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

	public ObjectValuePair<Class<?>, String> getResourceMethodObjectValuePair(
		String methodName, boolean mutation) {

		if (mutation) {
			return _resourceMethodObjectValuePairs.get(
				"mutation#" + methodName);
		}

		return _resourceMethodObjectValuePairs.get("query#" + methodName);
	}

	private static final Map<String, ObjectValuePair<Class<?>, String>>
		_resourceMethodObjectValuePairs =
			new HashMap<String, ObjectValuePair<Class<?>, String>>() {
				{
					put(
						"mutation#deleteCart",
						new ObjectValuePair<>(
							CartResourceImpl.class, "deleteCart"));
					put(
						"mutation#deleteCartBatch",
						new ObjectValuePair<>(
							CartResourceImpl.class, "deleteCartBatch"));
					put(
						"mutation#patchCart",
						new ObjectValuePair<>(
							CartResourceImpl.class, "patchCart"));
					put(
						"mutation#updateCart",
						new ObjectValuePair<>(
							CartResourceImpl.class, "putCart"));
					put(
						"mutation#updateCartBatch",
						new ObjectValuePair<>(
							CartResourceImpl.class, "putCartBatch"));
					put(
						"mutation#createCartCheckout",
						new ObjectValuePair<>(
							CartResourceImpl.class, "postCartCheckout"));
					put(
						"mutation#createCartCouponCode",
						new ObjectValuePair<>(
							CartResourceImpl.class, "postCartCouponCode"));
					put(
						"mutation#createChannelCart",
						new ObjectValuePair<>(
							CartResourceImpl.class, "postChannelCart"));
					put(
						"mutation#deleteCartComment",
						new ObjectValuePair<>(
							CartCommentResourceImpl.class,
							"deleteCartComment"));
					put(
						"mutation#deleteCartCommentBatch",
						new ObjectValuePair<>(
							CartCommentResourceImpl.class,
							"deleteCartCommentBatch"));
					put(
						"mutation#patchCartComment",
						new ObjectValuePair<>(
							CartCommentResourceImpl.class, "patchCartComment"));
					put(
						"mutation#updateCartComment",
						new ObjectValuePair<>(
							CartCommentResourceImpl.class, "putCartComment"));
					put(
						"mutation#updateCartCommentBatch",
						new ObjectValuePair<>(
							CartCommentResourceImpl.class,
							"putCartCommentBatch"));
					put(
						"mutation#createCartComment",
						new ObjectValuePair<>(
							CartCommentResourceImpl.class, "postCartComment"));
					put(
						"mutation#deleteCartItem",
						new ObjectValuePair<>(
							CartItemResourceImpl.class, "deleteCartItem"));
					put(
						"mutation#deleteCartItemBatch",
						new ObjectValuePair<>(
							CartItemResourceImpl.class, "deleteCartItemBatch"));
					put(
						"mutation#patchCartItem",
						new ObjectValuePair<>(
							CartItemResourceImpl.class, "patchCartItem"));
					put(
						"mutation#updateCartItem",
						new ObjectValuePair<>(
							CartItemResourceImpl.class, "putCartItem"));
					put(
						"mutation#updateCartItemBatch",
						new ObjectValuePair<>(
							CartItemResourceImpl.class, "putCartItemBatch"));
					put(
						"mutation#createCartItem",
						new ObjectValuePair<>(
							CartItemResourceImpl.class, "postCartItem"));
					put(
						"mutation#createCartPaymentMethodsPageExportBatch",
						new ObjectValuePair<>(
							PaymentMethodResourceImpl.class,
							"postCartPaymentMethodsPageExportBatch"));
					put(
						"mutation#createCartShippingMethodsPageExportBatch",
						new ObjectValuePair<>(
							ShippingMethodResourceImpl.class,
							"postCartShippingMethodsPageExportBatch"));

					put(
						"query#cartBillingAddres",
						new ObjectValuePair<>(
							AddressResourceImpl.class, "getCartBillingAddres"));
					put(
						"query#cartShippingAddres",
						new ObjectValuePair<>(
							AddressResourceImpl.class,
							"getCartShippingAddres"));
					put(
						"query#cart",
						new ObjectValuePair<>(
							CartResourceImpl.class, "getCart"));
					put(
						"query#cartPaymentURL",
						new ObjectValuePair<>(
							CartResourceImpl.class, "getCartPaymentURL"));
					put(
						"query#channelCarts",
						new ObjectValuePair<>(
							CartResourceImpl.class, "getChannelCartsPage"));
					put(
						"query#cartComment",
						new ObjectValuePair<>(
							CartCommentResourceImpl.class, "getCartComment"));
					put(
						"query#cartComments",
						new ObjectValuePair<>(
							CartCommentResourceImpl.class,
							"getCartCommentsPage"));
					put(
						"query#cartItem",
						new ObjectValuePair<>(
							CartItemResourceImpl.class, "getCartItem"));
					put(
						"query#cartItems",
						new ObjectValuePair<>(
							CartItemResourceImpl.class, "getCartItemsPage"));
					put(
						"query#cartPaymentMethods",
						new ObjectValuePair<>(
							PaymentMethodResourceImpl.class,
							"getCartPaymentMethodsPage"));
					put(
						"query#cartShippingMethods",
						new ObjectValuePair<>(
							ShippingMethodResourceImpl.class,
							"getCartShippingMethodsPage"));

					put(
						"query#Cart.paymentMethods",
						new ObjectValuePair<>(
							PaymentMethodResourceImpl.class,
							"getCartPaymentMethodsPage"));
					put(
						"query#Cart.comments",
						new ObjectValuePair<>(
							CartCommentResourceImpl.class,
							"getCartCommentsPage"));
					put(
						"query#Cart.shippingAddres",
						new ObjectValuePair<>(
							AddressResourceImpl.class,
							"getCartShippingAddres"));
					put(
						"query#Cart.shippingMethods",
						new ObjectValuePair<>(
							ShippingMethodResourceImpl.class,
							"getCartShippingMethodsPage"));
					put(
						"query#Cart.paymentURL",
						new ObjectValuePair<>(
							CartResourceImpl.class, "getCartPaymentURL"));
					put(
						"query#Cart.items",
						new ObjectValuePair<>(
							CartItemResourceImpl.class, "getCartItemsPage"));
					put(
						"query#Cart.billingAddres",
						new ObjectValuePair<>(
							AddressResourceImpl.class, "getCartBillingAddres"));

					put(
						"query#CartItem.parentCartItem",
						new ObjectValuePair<>(
							CartItemResourceImpl.class, "getCartItem"));
				}
			};

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
	private ComponentServiceObjects<PaymentMethodResource>
		_paymentMethodResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ShippingMethodResource>
		_shippingMethodResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<AddressResource>
		_addressResourceComponentServiceObjects;

}