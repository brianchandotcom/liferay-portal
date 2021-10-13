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

package com.liferay.headless.commerce.delivery.cart.internal.graphql.query.v1_0;

import com.liferay.headless.commerce.delivery.cart.dto.v1_0.Address;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.Cart;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.CartComment;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.CartItem;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.PaymentMethod;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.ShippingMethod;
import com.liferay.headless.commerce.delivery.cart.resource.v1_0.AddressResource;
import com.liferay.headless.commerce.delivery.cart.resource.v1_0.CartCommentResource;
import com.liferay.headless.commerce.delivery.cart.resource.v1_0.CartItemResource;
import com.liferay.headless.commerce.delivery.cart.resource.v1_0.CartResource;
import com.liferay.headless.commerce.delivery.cart.resource.v1_0.PaymentMethodResource;
import com.liferay.headless.commerce.delivery.cart.resource.v1_0.ShippingMethodResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLTypeExtension;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Map;
import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Andrea Sbarra
 * @generated
 */
@Generated("")
public class Query {

	public static void setAddressResourceComponentServiceObjects(
		ComponentServiceObjects<AddressResource>
			addressResourceComponentServiceObjects) {

		_addressResourceComponentServiceObjects =
			addressResourceComponentServiceObjects;
	}

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

	public static void setPaymentMethodResourceComponentServiceObjects(
		ComponentServiceObjects<PaymentMethodResource>
			paymentMethodResourceComponentServiceObjects) {

		_paymentMethodResourceComponentServiceObjects =
			paymentMethodResourceComponentServiceObjects;
	}

	public static void setShippingMethodResourceComponentServiceObjects(
		ComponentServiceObjects<ShippingMethodResource>
			shippingMethodResourceComponentServiceObjects) {

		_shippingMethodResourceComponentServiceObjects =
			shippingMethodResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {cartBillingAddres(cartId: ___){city, country, countryISOCode, description, id, latitude, longitude, name, phoneNumber, region, regionISOCode, street1, street2, street3, type, typeId, vatNumber, zip}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrive cart billing address.")
	public Address cartBillingAddres(@GraphQLName("cartId") Long cartId)
		throws Exception {

		return _applyComponentServiceObjects(
			_addressResourceComponentServiceObjects,
			this::_populateResourceContext,
			addressResource -> addressResource.getCartBillingAddres(cartId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {cartShippingAddres(cartId: ___){city, country, countryISOCode, description, id, latitude, longitude, name, phoneNumber, region, regionISOCode, street1, street2, street3, type, typeId, vatNumber, zip}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrive cart billing address.")
	public Address cartShippingAddres(@GraphQLName("cartId") Long cartId)
		throws Exception {

		return _applyComponentServiceObjects(
			_addressResourceComponentServiceObjects,
			this::_populateResourceContext,
			addressResource -> addressResource.getCartShippingAddres(cartId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {cart(cartId: ___){account, accountId, author, billingAddress, billingAddressId, cartItems, channelId, couponCode, createDate, currencyCode, customFields, errorMessages, id, lastPriceUpdateDate, modifiedDate, notes, orderStatusInfo, orderTypeExternalReferenceCode, orderTypeId, orderUUID, paymentMethod, paymentMethodLabel, paymentStatus, paymentStatusInfo, paymentStatusLabel, printedNote, purchaseOrderNumber, shippingAddress, shippingAddressId, shippingMethod, shippingOption, status, summary, useAsBilling, valid, workflowStatusInfo}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrive information of the given Cart.")
	public Cart cart(@GraphQLName("cartId") Long cartId) throws Exception {
		return _applyComponentServiceObjects(
			_cartResourceComponentServiceObjects,
			this::_populateResourceContext,
			cartResource -> cartResource.getCart(cartId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {channelCarts(accountId: ___, channelId: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves carts for specific account in the given channel."
	)
	public CartPage channelCarts(
			@GraphQLName("accountId") Long accountId,
			@GraphQLName("channelId") Long channelId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_cartResourceComponentServiceObjects,
			this::_populateResourceContext,
			cartResource -> new CartPage(
				cartResource.getChannelCartsPage(
					accountId, channelId, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {cartComment(cartCommentId: ___){author, content, id, orderId, restricted}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public CartComment cartComment(
			@GraphQLName("cartCommentId") Long cartCommentId)
		throws Exception {

		return _applyComponentServiceObjects(
			_cartCommentResourceComponentServiceObjects,
			this::_populateResourceContext,
			cartCommentResource -> cartCommentResource.getCartComment(
				cartCommentId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {cartComments(cartId: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public CartCommentPage cartComments(
			@GraphQLName("cartId") Long cartId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_cartCommentResourceComponentServiceObjects,
			this::_populateResourceContext,
			cartCommentResource -> new CartCommentPage(
				cartCommentResource.getCartCommentsPage(
					cartId, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {cartItem(cartItemId: ___){adaptiveMediaImageHTMLTag, cartItems, customFields, errorMessages, id, name, options, parentCartItemId, price, productId, productURLs, quantity, settings, sku, skuId, subscription, thumbnail, valid}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrive information of the given Cart")
	public CartItem cartItem(@GraphQLName("cartItemId") Long cartItemId)
		throws Exception {

		return _applyComponentServiceObjects(
			_cartItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			cartItemResource -> cartItemResource.getCartItem(cartItemId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {cartItems(cartId: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrive cart items of a Cart.")
	public CartItemPage cartItems(
			@GraphQLName("cartId") Long cartId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_cartItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			cartItemResource -> new CartItemPage(
				cartItemResource.getCartItemsPage(
					cartId, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {cartPaymentMethods(cartId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrive payment methods available for the Cart."
	)
	public PaymentMethodPage cartPaymentMethods(
			@GraphQLName("cartId") Long cartId)
		throws Exception {

		return _applyComponentServiceObjects(
			_paymentMethodResourceComponentServiceObjects,
			this::_populateResourceContext,
			paymentMethodResource -> new PaymentMethodPage(
				paymentMethodResource.getCartPaymentMethodsPage(cartId)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {cartShippingMethods(cartId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrive payment methods available for the Cart."
	)
	public ShippingMethodPage cartShippingMethods(
			@GraphQLName("cartId") Long cartId)
		throws Exception {

		return _applyComponentServiceObjects(
			_shippingMethodResourceComponentServiceObjects,
			this::_populateResourceContext,
			shippingMethodResource -> new ShippingMethodPage(
				shippingMethodResource.getCartShippingMethodsPage(cartId)));
	}

	@GraphQLTypeExtension(Cart.class)
	public class GetCartPaymentMethodsPageTypeExtension {

		public GetCartPaymentMethodsPageTypeExtension(Cart cart) {
			_cart = cart;
		}

		@GraphQLField(
			description = "Retrive payment methods available for the Cart."
		)
		public PaymentMethodPage paymentMethods() throws Exception {
			return _applyComponentServiceObjects(
				_paymentMethodResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				paymentMethodResource -> new PaymentMethodPage(
					paymentMethodResource.getCartPaymentMethodsPage(
						_cart.getId())));
		}

		private Cart _cart;

	}

	@GraphQLTypeExtension(Cart.class)
	public class GetCartCommentsPageTypeExtension {

		public GetCartCommentsPageTypeExtension(Cart cart) {
			_cart = cart;
		}

		@GraphQLField
		public CartCommentPage comments(
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_cartCommentResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				cartCommentResource -> new CartCommentPage(
					cartCommentResource.getCartCommentsPage(
						_cart.getId(), Pagination.of(page, pageSize))));
		}

		private Cart _cart;

	}

	@GraphQLTypeExtension(Cart.class)
	public class GetCartShippingAddresTypeExtension {

		public GetCartShippingAddresTypeExtension(Cart cart) {
			_cart = cart;
		}

		@GraphQLField(description = "Retrive cart billing address.")
		public Address shippingAddres() throws Exception {
			return _applyComponentServiceObjects(
				_addressResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				addressResource -> addressResource.getCartShippingAddres(
					_cart.getId()));
		}

		private Cart _cart;

	}

	@GraphQLTypeExtension(Cart.class)
	public class GetCartShippingMethodsPageTypeExtension {

		public GetCartShippingMethodsPageTypeExtension(Cart cart) {
			_cart = cart;
		}

		@GraphQLField(
			description = "Retrive payment methods available for the Cart."
		)
		public ShippingMethodPage shippingMethods() throws Exception {
			return _applyComponentServiceObjects(
				_shippingMethodResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				shippingMethodResource -> new ShippingMethodPage(
					shippingMethodResource.getCartShippingMethodsPage(
						_cart.getId())));
		}

		private Cart _cart;

	}

	@GraphQLTypeExtension(Cart.class)
	public class GetCartItemsPageTypeExtension {

		public GetCartItemsPageTypeExtension(Cart cart) {
			_cart = cart;
		}

		@GraphQLField(description = "Retrive cart items of a Cart.")
		public CartItemPage items(
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_cartItemResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				cartItemResource -> new CartItemPage(
					cartItemResource.getCartItemsPage(
						_cart.getId(), Pagination.of(page, pageSize))));
		}

		private Cart _cart;

	}

	@GraphQLTypeExtension(Cart.class)
	public class GetCartBillingAddresTypeExtension {

		public GetCartBillingAddresTypeExtension(Cart cart) {
			_cart = cart;
		}

		@GraphQLField(description = "Retrive cart billing address.")
		public Address billingAddres() throws Exception {
			return _applyComponentServiceObjects(
				_addressResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				addressResource -> addressResource.getCartBillingAddres(
					_cart.getId()));
		}

		private Cart _cart;

	}

	@GraphQLName("AddressPage")
	public class AddressPage {

		public AddressPage(Page addressPage) {
			actions = addressPage.getActions();

			items = addressPage.getItems();
			lastPage = addressPage.getLastPage();
			page = addressPage.getPage();
			pageSize = addressPage.getPageSize();
			totalCount = addressPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Address> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("CartPage")
	public class CartPage {

		public CartPage(Page cartPage) {
			actions = cartPage.getActions();

			items = cartPage.getItems();
			lastPage = cartPage.getLastPage();
			page = cartPage.getPage();
			pageSize = cartPage.getPageSize();
			totalCount = cartPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Cart> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("CartCommentPage")
	public class CartCommentPage {

		public CartCommentPage(Page cartCommentPage) {
			actions = cartCommentPage.getActions();

			items = cartCommentPage.getItems();
			lastPage = cartCommentPage.getLastPage();
			page = cartCommentPage.getPage();
			pageSize = cartCommentPage.getPageSize();
			totalCount = cartCommentPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<CartComment> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("CartItemPage")
	public class CartItemPage {

		public CartItemPage(Page cartItemPage) {
			actions = cartItemPage.getActions();

			items = cartItemPage.getItems();
			lastPage = cartItemPage.getLastPage();
			page = cartItemPage.getPage();
			pageSize = cartItemPage.getPageSize();
			totalCount = cartItemPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<CartItem> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("PaymentMethodPage")
	public class PaymentMethodPage {

		public PaymentMethodPage(Page paymentMethodPage) {
			actions = paymentMethodPage.getActions();

			items = paymentMethodPage.getItems();
			lastPage = paymentMethodPage.getLastPage();
			page = paymentMethodPage.getPage();
			pageSize = paymentMethodPage.getPageSize();
			totalCount = paymentMethodPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<PaymentMethod> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ShippingMethodPage")
	public class ShippingMethodPage {

		public ShippingMethodPage(Page shippingMethodPage) {
			actions = shippingMethodPage.getActions();

			items = shippingMethodPage.getItems();
			lastPage = shippingMethodPage.getLastPage();
			page = shippingMethodPage.getPage();
			pageSize = shippingMethodPage.getPageSize();
			totalCount = shippingMethodPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<ShippingMethod> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLTypeExtension(CartItem.class)
	public class ParentCartItemCartItemIdTypeExtension {

		public ParentCartItemCartItemIdTypeExtension(CartItem cartItem) {
			_cartItem = cartItem;
		}

		@GraphQLField(description = "Retrive information of the given Cart")
		public CartItem parentCartItem() throws Exception {
			if (_cartItem.getParentCartItemId() == null) {
				return null;
			}

			return _applyComponentServiceObjects(
				_cartItemResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				cartItemResource -> cartItemResource.getCartItem(
					_cartItem.getParentCartItemId()));
		}

		private CartItem _cartItem;

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

	private void _populateResourceContext(AddressResource addressResource)
		throws Exception {

		addressResource.setContextAcceptLanguage(_acceptLanguage);
		addressResource.setContextCompany(_company);
		addressResource.setContextHttpServletRequest(_httpServletRequest);
		addressResource.setContextHttpServletResponse(_httpServletResponse);
		addressResource.setContextUriInfo(_uriInfo);
		addressResource.setContextUser(_user);
		addressResource.setGroupLocalService(_groupLocalService);
		addressResource.setRoleLocalService(_roleLocalService);
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

	private void _populateResourceContext(
			PaymentMethodResource paymentMethodResource)
		throws Exception {

		paymentMethodResource.setContextAcceptLanguage(_acceptLanguage);
		paymentMethodResource.setContextCompany(_company);
		paymentMethodResource.setContextHttpServletRequest(_httpServletRequest);
		paymentMethodResource.setContextHttpServletResponse(
			_httpServletResponse);
		paymentMethodResource.setContextUriInfo(_uriInfo);
		paymentMethodResource.setContextUser(_user);
		paymentMethodResource.setGroupLocalService(_groupLocalService);
		paymentMethodResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			ShippingMethodResource shippingMethodResource)
		throws Exception {

		shippingMethodResource.setContextAcceptLanguage(_acceptLanguage);
		shippingMethodResource.setContextCompany(_company);
		shippingMethodResource.setContextHttpServletRequest(
			_httpServletRequest);
		shippingMethodResource.setContextHttpServletResponse(
			_httpServletResponse);
		shippingMethodResource.setContextUriInfo(_uriInfo);
		shippingMethodResource.setContextUser(_user);
		shippingMethodResource.setGroupLocalService(_groupLocalService);
		shippingMethodResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<AddressResource>
		_addressResourceComponentServiceObjects;
	private static ComponentServiceObjects<CartResource>
		_cartResourceComponentServiceObjects;
	private static ComponentServiceObjects<CartCommentResource>
		_cartCommentResourceComponentServiceObjects;
	private static ComponentServiceObjects<CartItemResource>
		_cartItemResourceComponentServiceObjects;
	private static ComponentServiceObjects<PaymentMethodResource>
		_paymentMethodResourceComponentServiceObjects;
	private static ComponentServiceObjects<ShippingMethodResource>
		_shippingMethodResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private BiFunction<Object, String, Filter> _filterBiFunction;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}