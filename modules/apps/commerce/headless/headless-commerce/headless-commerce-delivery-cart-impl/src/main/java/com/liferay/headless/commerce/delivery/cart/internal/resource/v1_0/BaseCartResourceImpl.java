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

import com.liferay.headless.commerce.delivery.cart.dto.v1_0.Cart;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.CouponCode;
import com.liferay.headless.commerce.delivery.cart.resource.v1_0.CartResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineImportTaskResource;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.ActionUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.validation.constraints.NotNull;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author Andrea Sbarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseCartResourceImpl
	implements CartResource, EntityModelResource,
			   VulcanBatchEngineTaskItemDelegate<Cart> {

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-commerce-delivery-cart/v1.0/carts/{cartId}'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "cartId")})
	@Path("/carts/{cartId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Cart")})
	public Response deleteCart(
			@NotNull @Parameter(hidden = true) @PathParam("cartId") Long cartId)
		throws Exception {

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-commerce-delivery-cart/v1.0/carts/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@DELETE
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.QUERY, name = "callbackURL")}
	)
	@Path("/carts/batch")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Cart")})
	public Response deleteCartBatch(
			@Parameter(hidden = true) @QueryParam("callbackURL") String
				callbackURL,
			Object object)
		throws Exception {

		vulcanBatchEngineImportTaskResource.setContextAcceptLanguage(
			contextAcceptLanguage);
		vulcanBatchEngineImportTaskResource.setContextCompany(contextCompany);
		vulcanBatchEngineImportTaskResource.setContextHttpServletRequest(
			contextHttpServletRequest);
		vulcanBatchEngineImportTaskResource.setContextUriInfo(contextUriInfo);
		vulcanBatchEngineImportTaskResource.setContextUser(contextUser);

		Response.ResponseBuilder responseBuilder = Response.accepted();

		return responseBuilder.entity(
			vulcanBatchEngineImportTaskResource.deleteImportTask(
				Cart.class.getName(), callbackURL, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-commerce-delivery-cart/v1.0/carts/{cartId}'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(description = "Retrive information of the given Cart.")
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "cartId")})
	@Path("/carts/{cartId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Cart")})
	public Cart getCart(
			@NotNull @Parameter(hidden = true) @PathParam("cartId") Long cartId)
		throws Exception {

		return new Cart();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PATCH' 'http://localhost:8080/o/headless-commerce-delivery-cart/v1.0/carts/{cartId}' -d $'{"accountId": ___, "billingAddress": ___, "billingAddressId": ___, "cartItems": ___, "couponCode": ___, "currencyCode": ___, "errorMessages": ___, "notes": ___, "paymentMethod": ___, "printedNote": ___, "shippingAddress": ___, "shippingAddressId": ___, "shippingMethod": ___, "shippingOption": ___, "summary": ___, "useAsBilling": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "cartId")})
	@PATCH
	@Path("/carts/{cartId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Cart")})
	public Cart patchCart(
			@NotNull @Parameter(hidden = true) @PathParam("cartId") Long cartId,
			Cart cart)
		throws Exception {

		Cart existingCart = getCart(cartId);

		if (cart.getAccount() != null) {
			existingCart.setAccount(cart.getAccount());
		}

		if (cart.getAccountId() != null) {
			existingCart.setAccountId(cart.getAccountId());
		}

		if (cart.getAuthor() != null) {
			existingCart.setAuthor(cart.getAuthor());
		}

		if (cart.getBillingAddressId() != null) {
			existingCart.setBillingAddressId(cart.getBillingAddressId());
		}

		if (cart.getChannelId() != null) {
			existingCart.setChannelId(cart.getChannelId());
		}

		if (cart.getCouponCode() != null) {
			existingCart.setCouponCode(cart.getCouponCode());
		}

		if (cart.getCreateDate() != null) {
			existingCart.setCreateDate(cart.getCreateDate());
		}

		if (cart.getCurrencyCode() != null) {
			existingCart.setCurrencyCode(cart.getCurrencyCode());
		}

		if (cart.getCustomFields() != null) {
			existingCart.setCustomFields(cart.getCustomFields());
		}

		if (cart.getErrorMessages() != null) {
			existingCart.setErrorMessages(cart.getErrorMessages());
		}

		if (cart.getLastPriceUpdateDate() != null) {
			existingCart.setLastPriceUpdateDate(cart.getLastPriceUpdateDate());
		}

		if (cart.getModifiedDate() != null) {
			existingCart.setModifiedDate(cart.getModifiedDate());
		}

		if (cart.getOrderUUID() != null) {
			existingCart.setOrderUUID(cart.getOrderUUID());
		}

		if (cart.getPaymentMethod() != null) {
			existingCart.setPaymentMethod(cart.getPaymentMethod());
		}

		if (cart.getPaymentMethodLabel() != null) {
			existingCart.setPaymentMethodLabel(cart.getPaymentMethodLabel());
		}

		if (cart.getPaymentStatus() != null) {
			existingCart.setPaymentStatus(cart.getPaymentStatus());
		}

		if (cart.getPaymentStatusLabel() != null) {
			existingCart.setPaymentStatusLabel(cart.getPaymentStatusLabel());
		}

		if (cart.getPrintedNote() != null) {
			existingCart.setPrintedNote(cart.getPrintedNote());
		}

		if (cart.getPurchaseOrderNumber() != null) {
			existingCart.setPurchaseOrderNumber(cart.getPurchaseOrderNumber());
		}

		if (cart.getShippingAddressId() != null) {
			existingCart.setShippingAddressId(cart.getShippingAddressId());
		}

		if (cart.getShippingMethod() != null) {
			existingCart.setShippingMethod(cart.getShippingMethod());
		}

		if (cart.getShippingOption() != null) {
			existingCart.setShippingOption(cart.getShippingOption());
		}

		if (cart.getStatus() != null) {
			existingCart.setStatus(cart.getStatus());
		}

		if (cart.getUseAsBilling() != null) {
			existingCart.setUseAsBilling(cart.getUseAsBilling());
		}

		if (cart.getValid() != null) {
			existingCart.setValid(cart.getValid());
		}

		preparePatch(cart, existingCart);

		return putCart(cartId, existingCart);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-commerce-delivery-cart/v1.0/carts/{cartId}' -d $'{"accountId": ___, "billingAddress": ___, "billingAddressId": ___, "cartItems": ___, "couponCode": ___, "currencyCode": ___, "errorMessages": ___, "notes": ___, "paymentMethod": ___, "printedNote": ___, "shippingAddress": ___, "shippingAddressId": ___, "shippingMethod": ___, "shippingOption": ___, "summary": ___, "useAsBilling": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "cartId")})
	@Path("/carts/{cartId}")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "Cart")})
	public Cart putCart(
			@NotNull @Parameter(hidden = true) @PathParam("cartId") Long cartId,
			Cart cart)
		throws Exception {

		return new Cart();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-commerce-delivery-cart/v1.0/carts/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.QUERY, name = "callbackURL")}
	)
	@Path("/carts/batch")
	@Produces("application/json")
	@PUT
	@Tags(value = {@Tag(name = "Cart")})
	public Response putCartBatch(
			@Parameter(hidden = true) @QueryParam("callbackURL") String
				callbackURL,
			Object object)
		throws Exception {

		vulcanBatchEngineImportTaskResource.setContextAcceptLanguage(
			contextAcceptLanguage);
		vulcanBatchEngineImportTaskResource.setContextCompany(contextCompany);
		vulcanBatchEngineImportTaskResource.setContextHttpServletRequest(
			contextHttpServletRequest);
		vulcanBatchEngineImportTaskResource.setContextUriInfo(contextUriInfo);
		vulcanBatchEngineImportTaskResource.setContextUser(contextUser);

		Response.ResponseBuilder responseBuilder = Response.accepted();

		return responseBuilder.entity(
			vulcanBatchEngineImportTaskResource.putImportTask(
				Cart.class.getName(), callbackURL, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-commerce-delivery-cart/v1.0/carts/{cartId}/checkout'  -u 'test@liferay.com:test'
	 */
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "cartId")})
	@Path("/carts/{cartId}/checkout")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Cart")})
	public Cart postCartCheckout(
			@NotNull @Parameter(hidden = true) @PathParam("cartId") Long cartId)
		throws Exception {

		return new Cart();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-commerce-delivery-cart/v1.0/carts/{cartId}/coupon-code' -d $'{"code": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Add new Items to a Cart, return the whole Cart updated."
	)
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "cartId")})
	@Path("/carts/{cartId}/coupon-code")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Cart")})
	public Cart postCartCouponCode(
			@NotNull @Parameter(hidden = true) @PathParam("cartId") Long cartId,
			CouponCode couponCode)
		throws Exception {

		return new Cart();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-commerce-delivery-cart/v1.0/channels/{channelId}/carts'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves carts for specific account in the given channel."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "channelId"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/channels/{channelId}/carts")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Cart")})
	public Page<Cart> getChannelCartsPage(
			@NotNull @Parameter(hidden = true) @PathParam("channelId") Long
				channelId,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-commerce-delivery-cart/v1.0/channels/{channelId}/carts' -d $'{"accountId": ___, "billingAddress": ___, "billingAddressId": ___, "cartItems": ___, "couponCode": ___, "currencyCode": ___, "errorMessages": ___, "notes": ___, "paymentMethod": ___, "printedNote": ___, "shippingAddress": ___, "shippingAddressId": ___, "shippingMethod": ___, "shippingOption": ___, "summary": ___, "useAsBilling": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "channelId")})
	@Path("/channels/{channelId}/carts")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Cart")})
	public Cart postChannelCart(
			@NotNull @Parameter(hidden = true) @PathParam("channelId") Long
				channelId,
			Cart cart)
		throws Exception {

		return new Cart();
	}

	@Override
	@SuppressWarnings("PMD.UnusedLocalVariable")
	public void create(
			java.util.Collection<Cart> carts,
			Map<String, Serializable> parameters)
		throws Exception {
	}

	@Override
	public void delete(
			java.util.Collection<Cart> carts,
			Map<String, Serializable> parameters)
		throws Exception {

		for (Cart cart : carts) {
			deleteCart(cart.getId());
		}
	}

	@Override
	public EntityModel getEntityModel(Map<String, List<String>> multivaluedMap)
		throws Exception {

		return getEntityModel(
			new MultivaluedHashMap<String, Object>(multivaluedMap));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return null;
	}

	@Override
	public Page<Cart> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		return null;
	}

	@Override
	public void setLanguageId(String languageId) {
		this.contextAcceptLanguage = new AcceptLanguage() {

			@Override
			public List<Locale> getLocales() {
				return null;
			}

			@Override
			public String getPreferredLanguageId() {
				return languageId;
			}

			@Override
			public Locale getPreferredLocale() {
				return LocaleUtil.fromLanguageId(languageId);
			}

		};
	}

	@Override
	public void update(
			java.util.Collection<Cart> carts,
			Map<String, Serializable> parameters)
		throws Exception {

		for (Cart cart : carts) {
			putCart(
				cart.getId() != null ? cart.getId() :
					Long.parseLong((String)parameters.get("cartId")),
				cart);
		}
	}

	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage) {
		this.contextAcceptLanguage = contextAcceptLanguage;
	}

	public void setContextCompany(
		com.liferay.portal.kernel.model.Company contextCompany) {

		this.contextCompany = contextCompany;
	}

	public void setContextHttpServletRequest(
		HttpServletRequest contextHttpServletRequest) {

		this.contextHttpServletRequest = contextHttpServletRequest;
	}

	public void setContextHttpServletResponse(
		HttpServletResponse contextHttpServletResponse) {

		this.contextHttpServletResponse = contextHttpServletResponse;
	}

	public void setContextUriInfo(UriInfo contextUriInfo) {
		this.contextUriInfo = contextUriInfo;
	}

	public void setContextUser(
		com.liferay.portal.kernel.model.User contextUser) {

		this.contextUser = contextUser;
	}

	public void setGroupLocalService(GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	public void setRoleLocalService(RoleLocalService roleLocalService) {
		this.roleLocalService = roleLocalService;
	}

	protected Map<String, String> addAction(
		String actionName, GroupedModel groupedModel, String methodName) {

		return ActionUtil.addAction(
			actionName, getClass(), groupedModel, methodName,
			contextScopeChecker, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, Long id, String methodName, Long ownerId,
		String permissionName, Long siteId) {

		return ActionUtil.addAction(
			actionName, getClass(), id, methodName, contextScopeChecker,
			ownerId, permissionName, siteId, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, Long id, String methodName,
		ModelResourcePermission modelResourcePermission) {

		return ActionUtil.addAction(
			actionName, getClass(), id, methodName, contextScopeChecker,
			modelResourcePermission, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, String methodName, String permissionName,
		Long siteId) {

		return addAction(
			actionName, siteId, methodName, null, permissionName, siteId);
	}

	protected void preparePatch(Cart cart, Cart existingCart) {
	}

	protected <T, R> List<R> transform(
		java.util.Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transform(collection, unsafeFunction);
	}

	protected <T, R> R[] transform(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction,
		Class<?> clazz) {

		return TransformUtil.transform(array, unsafeFunction, clazz);
	}

	protected <T, R> R[] transformToArray(
		java.util.Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction, Class<?> clazz) {

		return TransformUtil.transformToArray(
			collection, unsafeFunction, clazz);
	}

	protected <T, R> List<R> transformToList(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transformToList(array, unsafeFunction);
	}

	protected AcceptLanguage contextAcceptLanguage;
	protected com.liferay.portal.kernel.model.Company contextCompany;
	protected HttpServletRequest contextHttpServletRequest;
	protected HttpServletResponse contextHttpServletResponse;
	protected Object contextScopeChecker;
	protected UriInfo contextUriInfo;
	protected com.liferay.portal.kernel.model.User contextUser;
	protected GroupLocalService groupLocalService;
	protected ResourceActionLocalService resourceActionLocalService;
	protected ResourcePermissionLocalService resourcePermissionLocalService;
	protected RoleLocalService roleLocalService;
	protected VulcanBatchEngineImportTaskResource
		vulcanBatchEngineImportTaskResource;

}