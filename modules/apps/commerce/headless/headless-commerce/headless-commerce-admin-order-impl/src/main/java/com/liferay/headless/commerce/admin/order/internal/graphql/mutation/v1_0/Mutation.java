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

package com.liferay.headless.commerce.admin.order.internal.graphql.mutation.v1_0;

import com.liferay.headless.commerce.admin.order.dto.v1_0.BillingAddress;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderItem;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderNote;
import com.liferay.headless.commerce.admin.order.dto.v1_0.ShippingAddress;
import com.liferay.headless.commerce.admin.order.resource.v1_0.BillingAddressResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderItemResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderNoteResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.ShippingAddressResource;
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
 * @author Alessio Antonio Rendina
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setBillingAddressResourceComponentServiceObjects(
		ComponentServiceObjects<BillingAddressResource>
			billingAddressResourceComponentServiceObjects) {

		_billingAddressResourceComponentServiceObjects =
			billingAddressResourceComponentServiceObjects;
	}

	public static void setOrderResourceComponentServiceObjects(
		ComponentServiceObjects<OrderResource>
			orderResourceComponentServiceObjects) {

		_orderResourceComponentServiceObjects =
			orderResourceComponentServiceObjects;
	}

	public static void setOrderItemResourceComponentServiceObjects(
		ComponentServiceObjects<OrderItemResource>
			orderItemResourceComponentServiceObjects) {

		_orderItemResourceComponentServiceObjects =
			orderItemResourceComponentServiceObjects;
	}

	public static void setOrderNoteResourceComponentServiceObjects(
		ComponentServiceObjects<OrderNoteResource>
			orderNoteResourceComponentServiceObjects) {

		_orderNoteResourceComponentServiceObjects =
			orderNoteResourceComponentServiceObjects;
	}

	public static void setShippingAddressResourceComponentServiceObjects(
		ComponentServiceObjects<ShippingAddressResource>
			shippingAddressResourceComponentServiceObjects) {

		_shippingAddressResourceComponentServiceObjects =
			shippingAddressResourceComponentServiceObjects;
	}

	@GraphQLField
	public Response patchOrderByExternalReferenceCodeBillingAddress(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("billingAddress") BillingAddress billingAddress)
		throws Exception {

		return _applyComponentServiceObjects(
			_billingAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			billingAddressResource ->
				billingAddressResource.
					patchOrderByExternalReferenceCodeBillingAddress(
						externalReferenceCode, billingAddress));
	}

	@GraphQLField
	public Response patchOrderIdBillingAddress(
			@GraphQLName("id") Long id,
			@GraphQLName("billingAddress") BillingAddress billingAddress)
		throws Exception {

		return _applyComponentServiceObjects(
			_billingAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			billingAddressResource ->
				billingAddressResource.patchOrderIdBillingAddress(
					id, billingAddress));
	}

	@GraphQLField
	public Order createOrder(@GraphQLName("order") Order order)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderResource -> orderResource.postOrder(order));
	}

	@GraphQLField
	public Response createOrderBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderResource -> orderResource.postOrderBatch(callbackURL, object));
	}

	@GraphQLField
	public Response deleteOrderByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderResource -> orderResource.deleteOrderByExternalReferenceCode(
				externalReferenceCode));
	}

	@GraphQLField
	public Response patchOrderByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("order") Order order)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderResource -> orderResource.patchOrderByExternalReferenceCode(
				externalReferenceCode, order));
	}

	@GraphQLField
	public Response deleteOrder(@GraphQLName("id") Long id) throws Exception {
		return _applyComponentServiceObjects(
			_orderResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderResource -> orderResource.deleteOrder(id));
	}

	@GraphQLField
	public Response deleteOrderBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderResource -> orderResource.deleteOrderBatch(
				id, callbackURL, object));
	}

	@GraphQLField
	public Response patchOrder(
			@GraphQLName("id") Long id, @GraphQLName("order") Order order)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderResource -> orderResource.patchOrder(id, order));
	}

	@GraphQLField
	public Response deleteOrderItemByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderItemResource ->
				orderItemResource.deleteOrderItemByExternalReferenceCode(
					externalReferenceCode));
	}

	@GraphQLField
	public Response patchOrderItemByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("orderItem") OrderItem orderItem)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderItemResource ->
				orderItemResource.patchOrderItemByExternalReferenceCode(
					externalReferenceCode, orderItem));
	}

	@GraphQLField
	public Response deleteOrderItem(@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderItemResource -> orderItemResource.deleteOrderItem(id));
	}

	@GraphQLField
	public Response deleteOrderItemBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderItemResource -> orderItemResource.deleteOrderItemBatch(
				id, callbackURL, object));
	}

	@GraphQLField
	public Response patchOrderItem(
			@GraphQLName("id") Long id,
			@GraphQLName("orderItem") OrderItem orderItem)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderItemResource -> orderItemResource.patchOrderItem(
				id, orderItem));
	}

	@GraphQLField
	public OrderItem createOrderByExternalReferenceCodeOrderItem(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("orderItem") OrderItem orderItem)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderItemResource ->
				orderItemResource.postOrderByExternalReferenceCodeOrderItem(
					externalReferenceCode, orderItem));
	}

	@GraphQLField
	public OrderItem createOrderIdOrderItem(
			@GraphQLName("id") Long id,
			@GraphQLName("orderItem") OrderItem orderItem)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderItemResource -> orderItemResource.postOrderIdOrderItem(
				id, orderItem));
	}

	@GraphQLField
	public Response createOrderIdOrderItemBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderItemResource -> orderItemResource.postOrderIdOrderItemBatch(
				id, callbackURL, object));
	}

	@GraphQLField
	public Response deleteOrderNoteByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderNoteResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderNoteResource ->
				orderNoteResource.deleteOrderNoteByExternalReferenceCode(
					externalReferenceCode));
	}

	@GraphQLField
	public Response patchOrderNoteByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("orderNote") OrderNote orderNote)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderNoteResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderNoteResource ->
				orderNoteResource.patchOrderNoteByExternalReferenceCode(
					externalReferenceCode, orderNote));
	}

	@GraphQLField
	public Response deleteOrderNote(@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderNoteResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderNoteResource -> orderNoteResource.deleteOrderNote(id));
	}

	@GraphQLField
	public Response deleteOrderNoteBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderNoteResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderNoteResource -> orderNoteResource.deleteOrderNoteBatch(
				id, callbackURL, object));
	}

	@GraphQLField
	public Response patchOrderNote(
			@GraphQLName("id") Long id,
			@GraphQLName("orderNote") OrderNote orderNote)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderNoteResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderNoteResource -> orderNoteResource.patchOrderNote(
				id, orderNote));
	}

	@GraphQLField
	public OrderNote createOrderByExternalReferenceCodeOrderNote(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("orderNote") OrderNote orderNote)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderNoteResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderNoteResource ->
				orderNoteResource.postOrderByExternalReferenceCodeOrderNote(
					externalReferenceCode, orderNote));
	}

	@GraphQLField
	public OrderNote createOrderIdOrderNote(
			@GraphQLName("id") Long id,
			@GraphQLName("orderNote") OrderNote orderNote)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderNoteResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderNoteResource -> orderNoteResource.postOrderIdOrderNote(
				id, orderNote));
	}

	@GraphQLField
	public Response createOrderIdOrderNoteBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderNoteResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderNoteResource -> orderNoteResource.postOrderIdOrderNoteBatch(
				id, callbackURL, object));
	}

	@GraphQLField
	public Response patchOrderByExternalReferenceCodeShippingAddress(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("shippingAddress") ShippingAddress shippingAddress)
		throws Exception {

		return _applyComponentServiceObjects(
			_shippingAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			shippingAddressResource ->
				shippingAddressResource.
					patchOrderByExternalReferenceCodeShippingAddress(
						externalReferenceCode, shippingAddress));
	}

	@GraphQLField
	public Response patchOrderIdShippingAddress(
			@GraphQLName("id") Long id,
			@GraphQLName("shippingAddress") ShippingAddress shippingAddress)
		throws Exception {

		return _applyComponentServiceObjects(
			_shippingAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			shippingAddressResource ->
				shippingAddressResource.patchOrderIdShippingAddress(
					id, shippingAddress));
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

	private void _populateResourceContext(
			BillingAddressResource billingAddressResource)
		throws Exception {

		billingAddressResource.setContextAcceptLanguage(_acceptLanguage);
		billingAddressResource.setContextCompany(_company);
		billingAddressResource.setContextHttpServletRequest(
			_httpServletRequest);
		billingAddressResource.setContextHttpServletResponse(
			_httpServletResponse);
		billingAddressResource.setContextUriInfo(_uriInfo);
		billingAddressResource.setContextUser(_user);
		billingAddressResource.setGroupLocalService(_groupLocalService);
		billingAddressResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(OrderResource orderResource)
		throws Exception {

		orderResource.setContextAcceptLanguage(_acceptLanguage);
		orderResource.setContextCompany(_company);
		orderResource.setContextHttpServletRequest(_httpServletRequest);
		orderResource.setContextHttpServletResponse(_httpServletResponse);
		orderResource.setContextUriInfo(_uriInfo);
		orderResource.setContextUser(_user);
		orderResource.setGroupLocalService(_groupLocalService);
		orderResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(OrderItemResource orderItemResource)
		throws Exception {

		orderItemResource.setContextAcceptLanguage(_acceptLanguage);
		orderItemResource.setContextCompany(_company);
		orderItemResource.setContextHttpServletRequest(_httpServletRequest);
		orderItemResource.setContextHttpServletResponse(_httpServletResponse);
		orderItemResource.setContextUriInfo(_uriInfo);
		orderItemResource.setContextUser(_user);
		orderItemResource.setGroupLocalService(_groupLocalService);
		orderItemResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(OrderNoteResource orderNoteResource)
		throws Exception {

		orderNoteResource.setContextAcceptLanguage(_acceptLanguage);
		orderNoteResource.setContextCompany(_company);
		orderNoteResource.setContextHttpServletRequest(_httpServletRequest);
		orderNoteResource.setContextHttpServletResponse(_httpServletResponse);
		orderNoteResource.setContextUriInfo(_uriInfo);
		orderNoteResource.setContextUser(_user);
		orderNoteResource.setGroupLocalService(_groupLocalService);
		orderNoteResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			ShippingAddressResource shippingAddressResource)
		throws Exception {

		shippingAddressResource.setContextAcceptLanguage(_acceptLanguage);
		shippingAddressResource.setContextCompany(_company);
		shippingAddressResource.setContextHttpServletRequest(
			_httpServletRequest);
		shippingAddressResource.setContextHttpServletResponse(
			_httpServletResponse);
		shippingAddressResource.setContextUriInfo(_uriInfo);
		shippingAddressResource.setContextUser(_user);
		shippingAddressResource.setGroupLocalService(_groupLocalService);
		shippingAddressResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<BillingAddressResource>
		_billingAddressResourceComponentServiceObjects;
	private static ComponentServiceObjects<OrderResource>
		_orderResourceComponentServiceObjects;
	private static ComponentServiceObjects<OrderItemResource>
		_orderItemResourceComponentServiceObjects;
	private static ComponentServiceObjects<OrderNoteResource>
		_orderNoteResourceComponentServiceObjects;
	private static ComponentServiceObjects<ShippingAddressResource>
		_shippingAddressResourceComponentServiceObjects;

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