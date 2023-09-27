/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.cart.internal.graphql.mutation.v1_0;

import com.liferay.headless.commerce.delivery.cart.dto.v1_0.Cart;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.CartComment;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.CartItem;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.CouponCode;
import com.liferay.headless.commerce.delivery.cart.resource.v1_0.CartCommentResource;
import com.liferay.headless.commerce.delivery.cart.resource.v1_0.CartItemResource;
import com.liferay.headless.commerce.delivery.cart.resource.v1_0.CartResource;
import com.liferay.headless.commerce.delivery.cart.resource.v1_0.PaymentMethodResource;
import com.liferay.headless.commerce.delivery.cart.resource.v1_0.ShippingMethodResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineExportTaskResource;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineImportTaskResource;
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

	@GraphQLField
	public Response deleteCart(@GraphQLName("cartId") Long cartId)
		throws Exception {

		return _applyComponentServiceObjects(
			_cartResourceComponentServiceObjects,
			this::_populateResourceContext,
			cartResource -> cartResource.deleteCart(cartId));
	}

	@GraphQLField
	public Response deleteCartBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_cartResourceComponentServiceObjects,
			this::_populateResourceContext,
			cartResource -> cartResource.deleteCartBatch(callbackURL, object));
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
	public Response updateCartBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_cartResourceComponentServiceObjects,
			this::_populateResourceContext,
			cartResource -> cartResource.putCartBatch(callbackURL, object));
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
	public boolean deleteCartComment(
			@GraphQLName("cartCommentId") Long cartCommentId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_cartCommentResourceComponentServiceObjects,
			this::_populateResourceContext,
			cartCommentResource -> cartCommentResource.deleteCartComment(
				cartCommentId));

		return true;
	}

	@GraphQLField
	public Response deleteCartCommentBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_cartCommentResourceComponentServiceObjects,
			this::_populateResourceContext,
			cartCommentResource -> cartCommentResource.deleteCartCommentBatch(
				callbackURL, object));
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
	public Response updateCartCommentBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_cartCommentResourceComponentServiceObjects,
			this::_populateResourceContext,
			cartCommentResource -> cartCommentResource.putCartCommentBatch(
				callbackURL, object));
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

	@GraphQLField
	public Response deleteCartItemBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_cartItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			cartItemResource -> cartItemResource.deleteCartItemBatch(
				callbackURL, object));
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

	@GraphQLField
	public Response updateCartItemBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_cartItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			cartItemResource -> cartItemResource.putCartItemBatch(
				callbackURL, object));
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

	@GraphQLField
	public Response createCartPaymentMethodsPageExportBatch(
			@GraphQLName("cartId") Long cartId,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("contentType") String contentType,
			@GraphQLName("fieldNames") String fieldNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_paymentMethodResourceComponentServiceObjects,
			this::_populateResourceContext,
			paymentMethodResource ->
				paymentMethodResource.postCartPaymentMethodsPageExportBatch(
					cartId, callbackURL, contentType, fieldNames));
	}

	@GraphQLField
	public Response createCartShippingMethodsPageExportBatch(
			@GraphQLName("cartId") Long cartId,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("contentType") String contentType,
			@GraphQLName("fieldNames") String fieldNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_shippingMethodResourceComponentServiceObjects,
			this::_populateResourceContext,
			shippingMethodResource ->
				shippingMethodResource.postCartShippingMethodsPageExportBatch(
					cartId, callbackURL, contentType, fieldNames));
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

		cartResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		cartResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
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

		cartCommentResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		cartCommentResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
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

		cartItemResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		cartItemResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
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

		paymentMethodResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		paymentMethodResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
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

		shippingMethodResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		shippingMethodResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

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
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;
	private VulcanBatchEngineExportTaskResource
		_vulcanBatchEngineExportTaskResource;
	private VulcanBatchEngineImportTaskResource
		_vulcanBatchEngineImportTaskResource;

}