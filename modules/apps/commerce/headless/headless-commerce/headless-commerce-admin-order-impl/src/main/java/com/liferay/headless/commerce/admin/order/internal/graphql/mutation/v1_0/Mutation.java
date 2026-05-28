/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.order.internal.graphql.mutation.v1_0;

import com.liferay.headless.commerce.admin.order.dto.v1_0.Attachment;
import com.liferay.headless.commerce.admin.order.dto.v1_0.BillingAddress;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderItem;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderNote;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRule;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRuleAccount;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRuleAccountGroup;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRuleChannel;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRuleOrderType;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderType;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderTypeChannel;
import com.liferay.headless.commerce.admin.order.dto.v1_0.ShippingAddress;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Term;
import com.liferay.headless.commerce.admin.order.dto.v1_0.TermOrderType;
import com.liferay.headless.commerce.admin.order.resource.v1_0.AttachmentResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.BillingAddressResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderItemResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderNoteResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderRuleAccountGroupResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderRuleAccountResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderRuleChannelResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderRuleOrderTypeResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderRuleResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderTypeChannelResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderTypeResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.ShippingAddressResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.TermOrderTypeResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.TermResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineExportTaskResource;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineImportTaskResource;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

import jakarta.annotation.Generated;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.util.function.BiFunction;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setAttachmentResourceComponentServiceObjects(
		ComponentServiceObjects<AttachmentResource>
			attachmentResourceComponentServiceObjects) {

		_attachmentResourceComponentServiceObjects =
			attachmentResourceComponentServiceObjects;
	}

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

	public static void setOrderRuleResourceComponentServiceObjects(
		ComponentServiceObjects<OrderRuleResource>
			orderRuleResourceComponentServiceObjects) {

		_orderRuleResourceComponentServiceObjects =
			orderRuleResourceComponentServiceObjects;
	}

	public static void setOrderRuleAccountResourceComponentServiceObjects(
		ComponentServiceObjects<OrderRuleAccountResource>
			orderRuleAccountResourceComponentServiceObjects) {

		_orderRuleAccountResourceComponentServiceObjects =
			orderRuleAccountResourceComponentServiceObjects;
	}

	public static void setOrderRuleAccountGroupResourceComponentServiceObjects(
		ComponentServiceObjects<OrderRuleAccountGroupResource>
			orderRuleAccountGroupResourceComponentServiceObjects) {

		_orderRuleAccountGroupResourceComponentServiceObjects =
			orderRuleAccountGroupResourceComponentServiceObjects;
	}

	public static void setOrderRuleChannelResourceComponentServiceObjects(
		ComponentServiceObjects<OrderRuleChannelResource>
			orderRuleChannelResourceComponentServiceObjects) {

		_orderRuleChannelResourceComponentServiceObjects =
			orderRuleChannelResourceComponentServiceObjects;
	}

	public static void setOrderRuleOrderTypeResourceComponentServiceObjects(
		ComponentServiceObjects<OrderRuleOrderTypeResource>
			orderRuleOrderTypeResourceComponentServiceObjects) {

		_orderRuleOrderTypeResourceComponentServiceObjects =
			orderRuleOrderTypeResourceComponentServiceObjects;
	}

	public static void setOrderTypeResourceComponentServiceObjects(
		ComponentServiceObjects<OrderTypeResource>
			orderTypeResourceComponentServiceObjects) {

		_orderTypeResourceComponentServiceObjects =
			orderTypeResourceComponentServiceObjects;
	}

	public static void setOrderTypeChannelResourceComponentServiceObjects(
		ComponentServiceObjects<OrderTypeChannelResource>
			orderTypeChannelResourceComponentServiceObjects) {

		_orderTypeChannelResourceComponentServiceObjects =
			orderTypeChannelResourceComponentServiceObjects;
	}

	public static void setShippingAddressResourceComponentServiceObjects(
		ComponentServiceObjects<ShippingAddressResource>
			shippingAddressResourceComponentServiceObjects) {

		_shippingAddressResourceComponentServiceObjects =
			shippingAddressResourceComponentServiceObjects;
	}

	public static void setTermResourceComponentServiceObjects(
		ComponentServiceObjects<TermResource>
			termResourceComponentServiceObjects) {

		_termResourceComponentServiceObjects =
			termResourceComponentServiceObjects;
	}

	public static void setTermOrderTypeResourceComponentServiceObjects(
		ComponentServiceObjects<TermOrderTypeResource>
			termOrderTypeResourceComponentServiceObjects) {

		_termOrderTypeResourceComponentServiceObjects =
			termOrderTypeResourceComponentServiceObjects;
	}

	@GraphQLField(
		description = "Deletes a file attachment from an order. Requires order existence verification. Returns 204 No Content."
	)
	public boolean deleteOrderAttachment(
			@GraphQLName("orderId") Long orderId,
			@GraphQLName("attachmentId") Long attachmentId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_attachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			attachmentResource -> attachmentResource.deleteOrderAttachment(
				orderId, attachmentId));

		return true;
	}

	@GraphQLField(
		description = "Deletes an attachment from an order using external reference codes for both. Validates both order and attachment existence. Returns 204 No Content."
	)
	public boolean
			deleteOrderByExternalReferenceCodeAttachmentByExternalReferenceCode(
				@GraphQLName("externalReferenceCode") String
					externalReferenceCode,
				@GraphQLName("attachmentExternalReferenceCode") String
					attachmentExternalReferenceCode)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_attachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			attachmentResource ->
				attachmentResource.
					deleteOrderByExternalReferenceCodeAttachmentByExternalReferenceCode(
						externalReferenceCode,
						attachmentExternalReferenceCode));

		return true;
	}

	@GraphQLField(
		description = "Partial update of an order attachment the service. Updates priority, restricted flag, title, and type. Returns updated Attachment DTO. Throws NoSuchOrderAttachmentException (404) if not found."
	)
	public Attachment patchOrderAttachment(
			@GraphQLName("orderId") Long orderId,
			@GraphQLName("attachmentId") Long attachmentId,
			@GraphQLName("attachment") Attachment attachment)
		throws Exception {

		return _applyComponentServiceObjects(
			_attachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			attachmentResource -> attachmentResource.patchOrderAttachment(
				orderId, attachmentId, attachment));
	}

	@GraphQLField(
		description = "Partial update of an attachment via external reference codes for both order and attachment. Throws NoSuchOrderException (404) or NoSuchOrderAttachmentException (404) if either not found."
	)
	public Attachment
			patchOrderByExternalReferenceCodeAttachmentByExternalReferenceCode(
				@GraphQLName("externalReferenceCode") String
					externalReferenceCode,
				@GraphQLName("attachmentExternalReferenceCode") String
					attachmentExternalReferenceCode,
				@GraphQLName("attachment") Attachment attachment)
		throws Exception {

		return _applyComponentServiceObjects(
			_attachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			attachmentResource ->
				attachmentResource.
					patchOrderByExternalReferenceCodeAttachmentByExternalReferenceCode(
						externalReferenceCode, attachmentExternalReferenceCode,
						attachment));
	}

	@GraphQLField(
		description = "Create a file attachment for an order. Base64-decodes the attachment field and calls the service with title, type, restricted flag, and priority. Returns created Attachment DTO."
	)
	public Attachment createOrderAttachment(
			@GraphQLName("orderId") Long orderId,
			@GraphQLName("attachment") Attachment attachment)
		throws Exception {

		return _applyComponentServiceObjects(
			_attachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			attachmentResource -> attachmentResource.postOrderAttachment(
				orderId, attachment));
	}

	@GraphQLField(
		description = "Create an attachment for an order accessed by externalReferenceCode. Resolves order first, then delegates to postOrderAttachment(). Throws NoSuchOrderException (404) if order not found."
	)
	public Attachment createOrderByExternalReferenceCodeAttachment(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("attachment") Attachment attachment)
		throws Exception {

		return _applyComponentServiceObjects(
			_attachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			attachmentResource ->
				attachmentResource.postOrderByExternalReferenceCodeAttachment(
					externalReferenceCode, attachment));
	}

	@GraphQLField(
		description = "Partial update of billing address for an order accessed by externalReferenceCode. Returns 204 No Content. Throws NoSuchOrderException (404) if order not found."
	)
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

	@GraphQLField(
		description = "Partial update of billing address via nested field endpoint. Returns 204 No Content."
	)
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

	@GraphQLField(
		description = "Deletes an order by its ID. Returns 204 No Content on success; throws NoSuchOrderException (404) if the order does not exist."
	)
	public Response deleteOrder(@GraphQLName("id") Long id) throws Exception {
		return _applyComponentServiceObjects(
			_orderResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderResource -> orderResource.deleteOrder(id));
	}

	@GraphQLField
	public Response deleteOrderBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderResource -> orderResource.deleteOrderBatch(
				callbackURL, object));
	}

	@GraphQLField(
		description = "Deletes an order identified by externalReferenceCode. Performs a lookup by externalReferenceCode within the company scope first. Returns 204 No Content; throws NoSuchOrderException (404) if not found."
	)
	public Response deleteOrderByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderResource -> orderResource.deleteOrderByExternalReferenceCode(
				externalReferenceCode));
	}

	@GraphQLField(
		description = "Partial update of an order (JSON Merge Patch semantics). Calls _updateOrder() which preserves existing fields not supplied in the patch. Updates via _commerceOrderEngine for fields, the service for pricing, _commercePaymentEngine.updateOrderPaymentStatus() if payment status changes, the service if terms change, and _updateNestedResources() for items/addresses. Returns full updated Order DTO. Throws NoSuchOrderException (404) if order not found."
	)
	public Order patchOrder(
			@GraphQLName("id") Long id, @GraphQLName("order") Order order)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderResource -> orderResource.patchOrder(id, order));
	}

	@GraphQLField(
		description = "Partial update of an order by externalReferenceCode. Identical semantics to patchOrder. Throws NoSuchOrderException (404) if order not found."
	)
	public Order patchOrderByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("order") Order order)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderResource -> orderResource.patchOrderByExternalReferenceCode(
				externalReferenceCode, order));
	}

	@GraphQLField(
		description = "Create or upsert an order. If externalReferenceCode is supplied, calls _addOrUpdateOrder() which first checks if an order with that code exists and updates it, otherwise creates a new order. Uses the service with full order details: account, channel, currency, shipping/billing addresses, dates, payment/delivery terms, and nested resources (items, addresses). Returns created/updated Order DTO. Throws exception if required fields (channel, account) cannot be resolved."
	)
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
	public Response createOrdersPageExportBatch(
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("sort") String sortsString,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("contentType") String contentType,
			@GraphQLName("fieldNames") String fieldNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderResource -> orderResource.postOrdersPageExportBatch(
				search, _filterBiFunction.apply(orderResource, filterString),
				_sortsBiFunction.apply(orderResource, sortsString), callbackURL,
				contentType, fieldNames));
	}

	@GraphQLField(
		description = "Full replace of an order by externalReferenceCode (upsert semantics). Calls _addOrUpdateOrder(externalReferenceCode, order). If order exists, all fields are replaced; if not, a new order is created. Identical to postOrder in implementation but with explicit ERC provided. Returns Order DTO."
	)
	public Order updateOrderByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("order") Order order)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderResource -> orderResource.putOrderByExternalReferenceCode(
				externalReferenceCode, order));
	}

	@GraphQLField(
		description = "Deletes an order item by ID, passing the order context with account, group, and order ID. Returns 204 No Content; throws NoSuchOrderItemException (404) if not found."
	)
	public Response deleteOrderItem(@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderItemResource -> orderItemResource.deleteOrderItem(id));
	}

	@GraphQLField
	public Response deleteOrderItemBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderItemResource -> orderItemResource.deleteOrderItemBatch(
				callbackURL, object));
	}

	@GraphQLField(
		description = "Deletes an order item by externalReferenceCode. Returns 200 OK on successful deletion; throws NoSuchOrderItemException (404) if not found."
	)
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

	@GraphQLField(
		description = "Partial update of an order item via _updateOrderItem(). Updates externalReferenceCode, options, quantity, and pricing (if user has MANAGE_COMMERCE_ORDER_PRICES permission). Returns updated OrderItem DTO. Throws NoSuchOrderItemException (404) if not found."
	)
	public OrderItem patchOrderItem(
			@GraphQLName("id") Long id,
			@GraphQLName("orderItem") OrderItem orderItem)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderItemResource -> orderItemResource.patchOrderItem(
				id, orderItem));
	}

	@GraphQLField(
		description = "Partial update of an order item by externalReferenceCode. Identical semantics to patchOrderItem. Throws NoSuchOrderItemException (404) if not found."
	)
	public OrderItem patchOrderItemByExternalReferenceCode(
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

	@GraphQLField(
		description = "Create an order item in an order accessed by externalReferenceCode. Delegates to _addOrderItem() which calls OrderItemUtil. Throws NoSuchOrderException (404) if order not found."
	)
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

	@GraphQLField(
		description = "Create an order item in an order accessed by order ID. Delegates to _addOrderItem()."
	)
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
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderItemResource -> orderItemResource.postOrderIdOrderItemBatch(
				callbackURL, object));
	}

	@GraphQLField
	public Response createOrderItemsPageExportBatch(
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("sort") String sortsString,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("contentType") String contentType,
			@GraphQLName("fieldNames") String fieldNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderItemResource ->
				orderItemResource.postOrderItemsPageExportBatch(
					search,
					_filterBiFunction.apply(orderItemResource, filterString),
					_sortsBiFunction.apply(orderItemResource, sortsString),
					callbackURL, contentType, fieldNames));
	}

	@GraphQLField(
		description = "Update an Order Item by ID. Backed by the matching commerce service method on the target resource."
	)
	public OrderItem updateOrderItem(
			@GraphQLName("id") Long id,
			@GraphQLName("orderItem") OrderItem orderItem)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderItemResource -> orderItemResource.putOrderItem(id, orderItem));
	}

	@GraphQLField
	public Response updateOrderItemBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderItemResource -> orderItemResource.putOrderItemBatch(
				callbackURL, object));
	}

	@GraphQLField(
		description = "Updates an Order Item by external reference code. Backed by the matching commerce service method on the target resource."
	)
	public OrderItem updateOrderItemByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("orderItem") OrderItem orderItem)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderItemResource ->
				orderItemResource.putOrderItemByExternalReferenceCode(
					externalReferenceCode, orderItem));
	}

	@GraphQLField(
		description = "Placeholder that returns 204 No Content (note: actual implementation may be in Base class). Throws NoSuchOrderNoteException (404) if not found."
	)
	public Response deleteOrderNote(@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderNoteResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderNoteResource -> orderNoteResource.deleteOrderNote(id));
	}

	@GraphQLField
	public Response deleteOrderNoteBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderNoteResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderNoteResource -> orderNoteResource.deleteOrderNoteBatch(
				callbackURL, object));
	}

	@GraphQLField(
		description = "Deletes an order note by externalReferenceCode. Returns 200 OK; throws NoSuchOrderNoteException (404) if not found."
	)
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

	@GraphQLField(
		description = "Partial update of an order note. Returns 200 OK (note: returns Response, not OrderNote). Throws NoSuchOrderNoteException (404) if not found."
	)
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

	@GraphQLField(
		description = "Partial update of an order note by externalReferenceCode. Returns 200 OK. Throws NoSuchOrderNoteException (404) if not found."
	)
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

	@GraphQLField(
		description = "Create an order note for an order accessed by externalReferenceCode. Delegates to _addOrUpdateOrderNote(). Throws NoSuchOrderException (404) if order not found."
	)
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

	@GraphQLField(
		description = "Create an order note for an order accessed by order ID. Delegates to _addOrUpdateOrderNote()."
	)
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
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderNoteResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderNoteResource -> orderNoteResource.postOrderIdOrderNoteBatch(
				callbackURL, object));
	}

	@GraphQLField(
		description = "Deletes an OrderRule by ID. Backed by the matching commerce service method on the target resource."
	)
	public boolean deleteOrderRule(@GraphQLName("id") Long id)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_orderRuleResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleResource -> orderRuleResource.deleteOrderRule(id));

		return true;
	}

	@GraphQLField
	public Response deleteOrderRuleBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleResource -> orderRuleResource.deleteOrderRuleBatch(
				callbackURL, object));
	}

	@GraphQLField(
		description = "Deletes an order rule by externalReferenceCode using _corEntryService.fetchCOREntryByExternalReferenceCode() followed by deletion. Throws NoSuchCOREntryException (404) if not found."
	)
	public boolean deleteOrderRuleByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_orderRuleResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleResource ->
				orderRuleResource.deleteOrderRuleByExternalReferenceCode(
					externalReferenceCode));

		return true;
	}

	@GraphQLField(
		description = "Updates an OrderRule by ID. Backed by the matching commerce service method on the target resource."
	)
	public OrderRule patchOrderRule(
			@GraphQLName("id") Long id,
			@GraphQLName("orderRule") OrderRule orderRule)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleResource -> orderRuleResource.patchOrderRule(
				id, orderRule));
	}

	@GraphQLField(
		description = "Partial update of an order rule by externalReferenceCode. Identical semantics to patchOrderRule. Throws NoSuchCOREntryException (404) if not found."
	)
	public OrderRule patchOrderRuleByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("orderRule") OrderRule orderRule)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleResource ->
				orderRuleResource.patchOrderRuleByExternalReferenceCode(
					externalReferenceCode, orderRule));
	}

	@GraphQLField(
		description = "Create a new order rule. Calls _addCOREntry() which uses _corEntryService.addCOREntry() with externalReferenceCode, active, description, dates, name, priority, type, typeSettings. Also calls _updateNestedResources() to add linked accounts/groups/channels/order-types if provided in request body. Returns created OrderRule DTO."
	)
	public OrderRule createOrderRule(
			@GraphQLName("orderRule") OrderRule orderRule)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleResource -> orderRuleResource.postOrderRule(orderRule));
	}

	@GraphQLField
	public Response createOrderRuleBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleResource -> orderRuleResource.postOrderRuleBatch(
				callbackURL, object));
	}

	@GraphQLField
	public Response createOrderRulesPageExportBatch(
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("sort") String sortsString,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("contentType") String contentType,
			@GraphQLName("fieldNames") String fieldNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleResource ->
				orderRuleResource.postOrderRulesPageExportBatch(
					search,
					_filterBiFunction.apply(orderRuleResource, filterString),
					_sortsBiFunction.apply(orderRuleResource, sortsString),
					callbackURL, contentType, fieldNames));
	}

	@GraphQLField(
		description = "Full replace of an order rule by externalReferenceCode (upsert semantics). If rule exists, updates all fields via _updateOrderRule() and _updateNestedResources(); if not, creates a new rule _addCOREntry(). Returns OrderRule DTO."
	)
	public OrderRule updateOrderRuleByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("orderRule") OrderRule orderRule)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleResource ->
				orderRuleResource.putOrderRuleByExternalReferenceCode(
					externalReferenceCode, orderRule));
	}

	@GraphQLField(
		description = "Deletes an order rule account relationship by ID using _corEntryRelService.deleteCOREntryRel(id). Returns 204 No Content."
	)
	public boolean deleteOrderRuleAccount(
			@GraphQLName("orderRuleAccountId") Long orderRuleAccountId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_orderRuleAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleAccountResource ->
				orderRuleAccountResource.deleteOrderRuleAccount(
					orderRuleAccountId));

		return true;
	}

	@GraphQLField
	public Response deleteOrderRuleAccountBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleAccountResource ->
				orderRuleAccountResource.deleteOrderRuleAccountBatch(
					callbackURL, object));
	}

	@GraphQLField(
		description = "Add an account to an order rule via externalReferenceCode. Resolves order rule, then calls _corEntryRelService.addCOREntryRel(). Returns created OrderRuleAccount DTO. Throws NoSuchCOREntryException (404) if order rule not found."
	)
	public OrderRuleAccount
			createOrderRuleByExternalReferenceCodeOrderRuleAccount(
				@GraphQLName("externalReferenceCode") String
					externalReferenceCode,
				@GraphQLName("orderRuleAccount") OrderRuleAccount
					orderRuleAccount)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleAccountResource ->
				orderRuleAccountResource.
					postOrderRuleByExternalReferenceCodeOrderRuleAccount(
						externalReferenceCode, orderRuleAccount));
	}

	@GraphQLField(
		description = "Add an account to an order rule via order rule ID. Delegates to _corEntryRelService.addCOREntryRel()."
	)
	public OrderRuleAccount createOrderRuleIdOrderRuleAccount(
			@GraphQLName("id") Long id,
			@GraphQLName("orderRuleAccount") OrderRuleAccount orderRuleAccount)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleAccountResource ->
				orderRuleAccountResource.postOrderRuleIdOrderRuleAccount(
					id, orderRuleAccount));
	}

	@GraphQLField
	public Response createOrderRuleIdOrderRuleAccountBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleAccountResource ->
				orderRuleAccountResource.postOrderRuleIdOrderRuleAccountBatch(
					callbackURL, object));
	}

	@GraphQLField(
		description = "Deletes an Order Rule Account Group by ID. Backed by the matching commerce service method on the target resource."
	)
	public boolean deleteOrderRuleAccountGroup(
			@GraphQLName("orderRuleAccountGroupId") Long
				orderRuleAccountGroupId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_orderRuleAccountGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleAccountGroupResource ->
				orderRuleAccountGroupResource.deleteOrderRuleAccountGroup(
					orderRuleAccountGroupId));

		return true;
	}

	@GraphQLField
	public Response deleteOrderRuleAccountGroupBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleAccountGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleAccountGroupResource ->
				orderRuleAccountGroupResource.deleteOrderRuleAccountGroupBatch(
					callbackURL, object));
	}

	@GraphQLField(
		description = "Add an account group to an order rule via externalReferenceCode. Throws NoSuchCOREntryException (404) if order rule not found."
	)
	public OrderRuleAccountGroup
			createOrderRuleByExternalReferenceCodeOrderRuleAccountGroup(
				@GraphQLName("externalReferenceCode") String
					externalReferenceCode,
				@GraphQLName("orderRuleAccountGroup") OrderRuleAccountGroup
					orderRuleAccountGroup)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleAccountGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleAccountGroupResource ->
				orderRuleAccountGroupResource.
					postOrderRuleByExternalReferenceCodeOrderRuleAccountGroup(
						externalReferenceCode, orderRuleAccountGroup));
	}

	@GraphQLField(
		description = "Add an account group to an order rule via order rule ID."
	)
	public OrderRuleAccountGroup createOrderRuleIdOrderRuleAccountGroup(
			@GraphQLName("id") Long id,
			@GraphQLName("orderRuleAccountGroup") OrderRuleAccountGroup
				orderRuleAccountGroup)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleAccountGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleAccountGroupResource ->
				orderRuleAccountGroupResource.
					postOrderRuleIdOrderRuleAccountGroup(
						id, orderRuleAccountGroup));
	}

	@GraphQLField
	public Response createOrderRuleIdOrderRuleAccountGroupBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleAccountGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleAccountGroupResource ->
				orderRuleAccountGroupResource.
					postOrderRuleIdOrderRuleAccountGroupBatch(
						callbackURL, object));
	}

	@GraphQLField(
		description = "Deletes an order rule channel relationship by ID using _corEntryRelService.deleteCOREntryRel(id). Returns 204 No Content."
	)
	public boolean deleteOrderRuleChannel(
			@GraphQLName("orderRuleChannelId") Long orderRuleChannelId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_orderRuleChannelResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleChannelResource ->
				orderRuleChannelResource.deleteOrderRuleChannel(
					orderRuleChannelId));

		return true;
	}

	@GraphQLField
	public Response deleteOrderRuleChannelBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleChannelResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleChannelResource ->
				orderRuleChannelResource.deleteOrderRuleChannelBatch(
					callbackURL, object));
	}

	@GraphQLField(
		description = "Add a channel to an order rule via externalReferenceCode. Throws NoSuchCOREntryException (404) if order rule not found."
	)
	public OrderRuleChannel
			createOrderRuleByExternalReferenceCodeOrderRuleChannel(
				@GraphQLName("externalReferenceCode") String
					externalReferenceCode,
				@GraphQLName("orderRuleChannel") OrderRuleChannel
					orderRuleChannel)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleChannelResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleChannelResource ->
				orderRuleChannelResource.
					postOrderRuleByExternalReferenceCodeOrderRuleChannel(
						externalReferenceCode, orderRuleChannel));
	}

	@GraphQLField(
		description = "Add a channel to an order rule via order rule ID."
	)
	public OrderRuleChannel createOrderRuleIdOrderRuleChannel(
			@GraphQLName("id") Long id,
			@GraphQLName("orderRuleChannel") OrderRuleChannel orderRuleChannel)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleChannelResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleChannelResource ->
				orderRuleChannelResource.postOrderRuleIdOrderRuleChannel(
					id, orderRuleChannel));
	}

	@GraphQLField
	public Response createOrderRuleIdOrderRuleChannelBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleChannelResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleChannelResource ->
				orderRuleChannelResource.postOrderRuleIdOrderRuleChannelBatch(
					callbackURL, object));
	}

	@GraphQLField(
		description = "Deletes an order rule order-type relationship by ID using _corEntryRelService.deleteCOREntryRel(id). Returns 204 No Content."
	)
	public boolean deleteOrderRuleOrderType(
			@GraphQLName("orderRuleOrderTypeId") Long orderRuleOrderTypeId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_orderRuleOrderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleOrderTypeResource ->
				orderRuleOrderTypeResource.deleteOrderRuleOrderType(
					orderRuleOrderTypeId));

		return true;
	}

	@GraphQLField
	public Response deleteOrderRuleOrderTypeBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleOrderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleOrderTypeResource ->
				orderRuleOrderTypeResource.deleteOrderRuleOrderTypeBatch(
					callbackURL, object));
	}

	@GraphQLField(
		description = "Add an order type to an order rule via externalReferenceCode. Throws NoSuchCOREntryException (404) if order rule not found."
	)
	public OrderRuleOrderType
			createOrderRuleByExternalReferenceCodeOrderRuleOrderType(
				@GraphQLName("externalReferenceCode") String
					externalReferenceCode,
				@GraphQLName("orderRuleOrderType") OrderRuleOrderType
					orderRuleOrderType)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleOrderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleOrderTypeResource ->
				orderRuleOrderTypeResource.
					postOrderRuleByExternalReferenceCodeOrderRuleOrderType(
						externalReferenceCode, orderRuleOrderType));
	}

	@GraphQLField(
		description = "Add an order type to an order rule via order rule ID."
	)
	public OrderRuleOrderType createOrderRuleIdOrderRuleOrderType(
			@GraphQLName("id") Long id,
			@GraphQLName("orderRuleOrderType") OrderRuleOrderType
				orderRuleOrderType)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleOrderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleOrderTypeResource ->
				orderRuleOrderTypeResource.postOrderRuleIdOrderRuleOrderType(
					id, orderRuleOrderType));
	}

	@GraphQLField
	public Response createOrderRuleIdOrderRuleOrderTypeBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderRuleOrderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderRuleOrderTypeResource ->
				orderRuleOrderTypeResource.
					postOrderRuleIdOrderRuleOrderTypeBatch(
						callbackURL, object));
	}

	@GraphQLField(
		description = "Deletes an order type by ID. Returns 204 No Content."
	)
	public boolean deleteOrderType(@GraphQLName("id") Long id)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_orderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeResource -> orderTypeResource.deleteOrderType(id));

		return true;
	}

	@GraphQLField
	public Response deleteOrderTypeBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeResource -> orderTypeResource.deleteOrderTypeBatch(
				callbackURL, object));
	}

	@GraphQLField(
		description = "Deletes an order type by externalReferenceCode. Returns 204 No Content; throws NoSuchOrderTypeException (404) if not found."
	)
	public boolean deleteOrderTypeByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_orderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeResource ->
				orderTypeResource.deleteOrderTypeByExternalReferenceCode(
					externalReferenceCode));

		return true;
	}

	@GraphQLField(
		description = "Partial update of an order type via _updateOrderType(). Updates externalReferenceCode, name, description, active, displayDate, expirationDate, displayOrder, neverExpire, and custom fields. Returns updated OrderType DTO. Throws NoSuchOrderTypeException (404) if not found."
	)
	public OrderType patchOrderType(
			@GraphQLName("id") Long id,
			@GraphQLName("orderType") OrderType orderType)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeResource -> orderTypeResource.patchOrderType(
				id, orderType));
	}

	@GraphQLField(
		description = "Partial update of an order type by externalReferenceCode. Identical semantics to patchOrderType. Throws NoSuchOrderTypeException (404) if not found."
	)
	public OrderType patchOrderTypeByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("orderType") OrderType orderType)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeResource ->
				orderTypeResource.patchOrderTypeByExternalReferenceCode(
					externalReferenceCode, orderType));
	}

	@GraphQLField(
		description = "Create a new order type. Calls _addCommerceOrderType() which uses the service with externalReferenceCode, localized name/description, active, dates, displayOrder, neverExpire. Returns created OrderType DTO."
	)
	public OrderType createOrderType(
			@GraphQLName("orderType") OrderType orderType)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeResource -> orderTypeResource.postOrderType(orderType));
	}

	@GraphQLField
	public Response createOrderTypeBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeResource -> orderTypeResource.postOrderTypeBatch(
				callbackURL, object));
	}

	@GraphQLField
	public Response createOrderTypesPageExportBatch(
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("sort") String sortsString,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("contentType") String contentType,
			@GraphQLName("fieldNames") String fieldNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeResource ->
				orderTypeResource.postOrderTypesPageExportBatch(
					search,
					_filterBiFunction.apply(orderTypeResource, filterString),
					_sortsBiFunction.apply(orderTypeResource, sortsString),
					callbackURL, contentType, fieldNames));
	}

	@GraphQLField(
		description = "Full replace of an order type by externalReferenceCode (upsert semantics). If order type exists, updates all fields() and custom fields; if not, creates a new order type. Returns OrderType DTO."
	)
	public OrderType updateOrderTypeByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("orderType") OrderType orderType)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeResource ->
				orderTypeResource.putOrderTypeByExternalReferenceCode(
					externalReferenceCode, orderType));
	}

	@GraphQLField(
		description = "Deletes an order-type-channel relationship by ID. Returns 204 No Content."
	)
	public boolean deleteOrderTypeChannel(
			@GraphQLName("orderTypeChannelId") Long orderTypeChannelId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_orderTypeChannelResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeChannelResource ->
				orderTypeChannelResource.deleteOrderTypeChannel(
					orderTypeChannelId));

		return true;
	}

	@GraphQLField
	public Response deleteOrderTypeChannelBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderTypeChannelResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeChannelResource ->
				orderTypeChannelResource.deleteOrderTypeChannelBatch(
					callbackURL, object));
	}

	@GraphQLField(
		description = "Create an order-type-channel link for an order type accessed by externalReferenceCode. Resolves channel by either ID or externalReferenceCode. Returns created OrderTypeChannel DTO. Throws NoSuchOrderTypeException (404) or NoSuchChannelException (404) if either not found."
	)
	public OrderTypeChannel
			createOrderTypeByExternalReferenceCodeOrderTypeChannel(
				@GraphQLName("externalReferenceCode") String
					externalReferenceCode,
				@GraphQLName("orderTypeChannel") OrderTypeChannel
					orderTypeChannel)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderTypeChannelResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeChannelResource ->
				orderTypeChannelResource.
					postOrderTypeByExternalReferenceCodeOrderTypeChannel(
						externalReferenceCode, orderTypeChannel));
	}

	@GraphQLField(
		description = "Create an order-type-channel link for an order type accessed by order type ID."
	)
	public OrderTypeChannel createOrderTypeIdOrderTypeChannel(
			@GraphQLName("id") Long id,
			@GraphQLName("orderTypeChannel") OrderTypeChannel orderTypeChannel)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderTypeChannelResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeChannelResource ->
				orderTypeChannelResource.postOrderTypeIdOrderTypeChannel(
					id, orderTypeChannel));
	}

	@GraphQLField
	public Response createOrderTypeIdOrderTypeChannelBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderTypeChannelResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeChannelResource ->
				orderTypeChannelResource.postOrderTypeIdOrderTypeChannelBatch(
					callbackURL, object));
	}

	@GraphQLField(
		description = "Partial update of shipping address for an order accessed by externalReferenceCode. Returns 204 No Content. Throws NoSuchOrderException (404) if order not found."
	)
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

	@GraphQLField(
		description = "Partial update of shipping address via nested field endpoint. Returns 204 No Content."
	)
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

	@GraphQLField(
		description = "Deletes a Term by ID. Backed by the matching commerce service method on the target resource."
	)
	public boolean deleteTerm(@GraphQLName("id") Long id) throws Exception {
		_applyVoidComponentServiceObjects(
			_termResourceComponentServiceObjects,
			this::_populateResourceContext,
			termResource -> termResource.deleteTerm(id));

		return true;
	}

	@GraphQLField
	public Response deleteTermBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_termResourceComponentServiceObjects,
			this::_populateResourceContext,
			termResource -> termResource.deleteTermBatch(callbackURL, object));
	}

	@GraphQLField(
		description = "Deletes a commerce term by externalReferenceCode followed by deletion. Throws NoSuchTermEntryException (404) if not found."
	)
	public boolean deleteTermByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_termResourceComponentServiceObjects,
			this::_populateResourceContext,
			termResource -> termResource.deleteTermByExternalReferenceCode(
				externalReferenceCode));

		return true;
	}

	@GraphQLField(
		description = "Updates a Term by ID. Backed by the matching commerce service method on the target resource."
	)
	public Term patchTerm(
			@GraphQLName("id") Long id, @GraphQLName("term") Term term)
		throws Exception {

		return _applyComponentServiceObjects(
			_termResourceComponentServiceObjects,
			this::_populateResourceContext,
			termResource -> termResource.patchTerm(id, term));
	}

	@GraphQLField(
		description = "Partial update of a term by externalReferenceCode via _updateTerm(). Updates active, description, displayDate, expirationDate, label, name, neverExpire, priority, typeSettings, and calls _updateNestedResources() to sync linked order types. Returns updated Term DTO. Throws NoSuchTermEntryException (404) if not found."
	)
	public Term patchTermByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("term") Term term)
		throws Exception {

		return _applyComponentServiceObjects(
			_termResourceComponentServiceObjects,
			this::_populateResourceContext,
			termResource -> termResource.patchTermByExternalReferenceCode(
				externalReferenceCode, term));
	}

	@GraphQLField(
		description = "Create a new commerce term. Calls _addCommerceTermEntry() which uses the service with externalReferenceCode, localized description/label, active, dates, name, priority, type, typeSettings. Also calls _updateNestedResources() to add linked order types if provided. Returns created Term DTO."
	)
	public Term createTerm(@GraphQLName("term") Term term) throws Exception {
		return _applyComponentServiceObjects(
			_termResourceComponentServiceObjects,
			this::_populateResourceContext,
			termResource -> termResource.postTerm(term));
	}

	@GraphQLField
	public Response createTermBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_termResourceComponentServiceObjects,
			this::_populateResourceContext,
			termResource -> termResource.postTermBatch(callbackURL, object));
	}

	@GraphQLField
	public Response createTermsPageExportBatch(
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("sort") String sortsString,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("contentType") String contentType,
			@GraphQLName("fieldNames") String fieldNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_termResourceComponentServiceObjects,
			this::_populateResourceContext,
			termResource -> termResource.postTermsPageExportBatch(
				search, _filterBiFunction.apply(termResource, filterString),
				_sortsBiFunction.apply(termResource, sortsString), callbackURL,
				contentType, fieldNames));
	}

	@GraphQLField(
		description = "Full replace of a term by externalReferenceCode (upsert semantics). If term exists, updates all fields and calls _updateNestedResources(); if not, calls postTerm(). Returns Term DTO."
	)
	public Term updateTermByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("term") Term term)
		throws Exception {

		return _applyComponentServiceObjects(
			_termResourceComponentServiceObjects,
			this::_populateResourceContext,
			termResource -> termResource.putTermByExternalReferenceCode(
				externalReferenceCode, term));
	}

	@GraphQLField(
		description = "Deletes a term-order-type relationship by ID. Returns 204 No Content."
	)
	public boolean deleteTermOrderType(
			@GraphQLName("termOrderTypeId") Long termOrderTypeId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_termOrderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			termOrderTypeResource -> termOrderTypeResource.deleteTermOrderType(
				termOrderTypeId));

		return true;
	}

	@GraphQLField
	public Response deleteTermOrderTypeBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_termOrderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			termOrderTypeResource ->
				termOrderTypeResource.deleteTermOrderTypeBatch(
					callbackURL, object));
	}

	@GraphQLField(
		description = "Add an order type to a term via externalReferenceCode. Resolves term, then calls the service. Returns created TermOrderType DTO. Throws NoSuchTermEntryException (404) if term not found."
	)
	public TermOrderType createTermByExternalReferenceCodeTermOrderType(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("termOrderType") TermOrderType termOrderType)
		throws Exception {

		return _applyComponentServiceObjects(
			_termOrderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			termOrderTypeResource ->
				termOrderTypeResource.
					postTermByExternalReferenceCodeTermOrderType(
						externalReferenceCode, termOrderType));
	}

	@GraphQLField(description = "Add an order type to a term via term ID.")
	public TermOrderType createTermIdTermOrderType(
			@GraphQLName("id") Long id,
			@GraphQLName("termOrderType") TermOrderType termOrderType)
		throws Exception {

		return _applyComponentServiceObjects(
			_termOrderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			termOrderTypeResource ->
				termOrderTypeResource.postTermIdTermOrderType(
					id, termOrderType));
	}

	@GraphQLField
	public Response createTermIdTermOrderTypeBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_termOrderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			termOrderTypeResource ->
				termOrderTypeResource.postTermIdTermOrderTypeBatch(
					callbackURL, object));
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

	private void _populateResourceContext(AttachmentResource attachmentResource)
		throws Exception {

		attachmentResource.setContextAcceptLanguage(_acceptLanguage);
		attachmentResource.setContextCompany(_company);
		attachmentResource.setContextHttpServletRequest(_httpServletRequest);
		attachmentResource.setContextHttpServletResponse(_httpServletResponse);
		attachmentResource.setContextUriInfo(_uriInfo);
		attachmentResource.setContextUser(_user);
		attachmentResource.setGroupLocalService(_groupLocalService);
		attachmentResource.setRoleLocalService(_roleLocalService);

		attachmentResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		attachmentResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
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

		orderResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		orderResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
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

		orderItemResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		orderItemResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
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

		orderNoteResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		orderNoteResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private void _populateResourceContext(OrderRuleResource orderRuleResource)
		throws Exception {

		orderRuleResource.setContextAcceptLanguage(_acceptLanguage);
		orderRuleResource.setContextCompany(_company);
		orderRuleResource.setContextHttpServletRequest(_httpServletRequest);
		orderRuleResource.setContextHttpServletResponse(_httpServletResponse);
		orderRuleResource.setContextUriInfo(_uriInfo);
		orderRuleResource.setContextUser(_user);
		orderRuleResource.setGroupLocalService(_groupLocalService);
		orderRuleResource.setRoleLocalService(_roleLocalService);

		orderRuleResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		orderRuleResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private void _populateResourceContext(
			OrderRuleAccountResource orderRuleAccountResource)
		throws Exception {

		orderRuleAccountResource.setContextAcceptLanguage(_acceptLanguage);
		orderRuleAccountResource.setContextCompany(_company);
		orderRuleAccountResource.setContextHttpServletRequest(
			_httpServletRequest);
		orderRuleAccountResource.setContextHttpServletResponse(
			_httpServletResponse);
		orderRuleAccountResource.setContextUriInfo(_uriInfo);
		orderRuleAccountResource.setContextUser(_user);
		orderRuleAccountResource.setGroupLocalService(_groupLocalService);
		orderRuleAccountResource.setRoleLocalService(_roleLocalService);

		orderRuleAccountResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		orderRuleAccountResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private void _populateResourceContext(
			OrderRuleAccountGroupResource orderRuleAccountGroupResource)
		throws Exception {

		orderRuleAccountGroupResource.setContextAcceptLanguage(_acceptLanguage);
		orderRuleAccountGroupResource.setContextCompany(_company);
		orderRuleAccountGroupResource.setContextHttpServletRequest(
			_httpServletRequest);
		orderRuleAccountGroupResource.setContextHttpServletResponse(
			_httpServletResponse);
		orderRuleAccountGroupResource.setContextUriInfo(_uriInfo);
		orderRuleAccountGroupResource.setContextUser(_user);
		orderRuleAccountGroupResource.setGroupLocalService(_groupLocalService);
		orderRuleAccountGroupResource.setRoleLocalService(_roleLocalService);

		orderRuleAccountGroupResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		orderRuleAccountGroupResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private void _populateResourceContext(
			OrderRuleChannelResource orderRuleChannelResource)
		throws Exception {

		orderRuleChannelResource.setContextAcceptLanguage(_acceptLanguage);
		orderRuleChannelResource.setContextCompany(_company);
		orderRuleChannelResource.setContextHttpServletRequest(
			_httpServletRequest);
		orderRuleChannelResource.setContextHttpServletResponse(
			_httpServletResponse);
		orderRuleChannelResource.setContextUriInfo(_uriInfo);
		orderRuleChannelResource.setContextUser(_user);
		orderRuleChannelResource.setGroupLocalService(_groupLocalService);
		orderRuleChannelResource.setRoleLocalService(_roleLocalService);

		orderRuleChannelResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		orderRuleChannelResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private void _populateResourceContext(
			OrderRuleOrderTypeResource orderRuleOrderTypeResource)
		throws Exception {

		orderRuleOrderTypeResource.setContextAcceptLanguage(_acceptLanguage);
		orderRuleOrderTypeResource.setContextCompany(_company);
		orderRuleOrderTypeResource.setContextHttpServletRequest(
			_httpServletRequest);
		orderRuleOrderTypeResource.setContextHttpServletResponse(
			_httpServletResponse);
		orderRuleOrderTypeResource.setContextUriInfo(_uriInfo);
		orderRuleOrderTypeResource.setContextUser(_user);
		orderRuleOrderTypeResource.setGroupLocalService(_groupLocalService);
		orderRuleOrderTypeResource.setRoleLocalService(_roleLocalService);

		orderRuleOrderTypeResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		orderRuleOrderTypeResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private void _populateResourceContext(OrderTypeResource orderTypeResource)
		throws Exception {

		orderTypeResource.setContextAcceptLanguage(_acceptLanguage);
		orderTypeResource.setContextCompany(_company);
		orderTypeResource.setContextHttpServletRequest(_httpServletRequest);
		orderTypeResource.setContextHttpServletResponse(_httpServletResponse);
		orderTypeResource.setContextUriInfo(_uriInfo);
		orderTypeResource.setContextUser(_user);
		orderTypeResource.setGroupLocalService(_groupLocalService);
		orderTypeResource.setRoleLocalService(_roleLocalService);

		orderTypeResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		orderTypeResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private void _populateResourceContext(
			OrderTypeChannelResource orderTypeChannelResource)
		throws Exception {

		orderTypeChannelResource.setContextAcceptLanguage(_acceptLanguage);
		orderTypeChannelResource.setContextCompany(_company);
		orderTypeChannelResource.setContextHttpServletRequest(
			_httpServletRequest);
		orderTypeChannelResource.setContextHttpServletResponse(
			_httpServletResponse);
		orderTypeChannelResource.setContextUriInfo(_uriInfo);
		orderTypeChannelResource.setContextUser(_user);
		orderTypeChannelResource.setGroupLocalService(_groupLocalService);
		orderTypeChannelResource.setRoleLocalService(_roleLocalService);

		orderTypeChannelResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		orderTypeChannelResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
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

	private void _populateResourceContext(TermResource termResource)
		throws Exception {

		termResource.setContextAcceptLanguage(_acceptLanguage);
		termResource.setContextCompany(_company);
		termResource.setContextHttpServletRequest(_httpServletRequest);
		termResource.setContextHttpServletResponse(_httpServletResponse);
		termResource.setContextUriInfo(_uriInfo);
		termResource.setContextUser(_user);
		termResource.setGroupLocalService(_groupLocalService);
		termResource.setRoleLocalService(_roleLocalService);

		termResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		termResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private void _populateResourceContext(
			TermOrderTypeResource termOrderTypeResource)
		throws Exception {

		termOrderTypeResource.setContextAcceptLanguage(_acceptLanguage);
		termOrderTypeResource.setContextCompany(_company);
		termOrderTypeResource.setContextHttpServletRequest(_httpServletRequest);
		termOrderTypeResource.setContextHttpServletResponse(
			_httpServletResponse);
		termOrderTypeResource.setContextUriInfo(_uriInfo);
		termOrderTypeResource.setContextUser(_user);
		termOrderTypeResource.setGroupLocalService(_groupLocalService);
		termOrderTypeResource.setRoleLocalService(_roleLocalService);

		termOrderTypeResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		termOrderTypeResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private static ComponentServiceObjects<AttachmentResource>
		_attachmentResourceComponentServiceObjects;
	private static ComponentServiceObjects<BillingAddressResource>
		_billingAddressResourceComponentServiceObjects;
	private static ComponentServiceObjects<OrderResource>
		_orderResourceComponentServiceObjects;
	private static ComponentServiceObjects<OrderItemResource>
		_orderItemResourceComponentServiceObjects;
	private static ComponentServiceObjects<OrderNoteResource>
		_orderNoteResourceComponentServiceObjects;
	private static ComponentServiceObjects<OrderRuleResource>
		_orderRuleResourceComponentServiceObjects;
	private static ComponentServiceObjects<OrderRuleAccountResource>
		_orderRuleAccountResourceComponentServiceObjects;
	private static ComponentServiceObjects<OrderRuleAccountGroupResource>
		_orderRuleAccountGroupResourceComponentServiceObjects;
	private static ComponentServiceObjects<OrderRuleChannelResource>
		_orderRuleChannelResourceComponentServiceObjects;
	private static ComponentServiceObjects<OrderRuleOrderTypeResource>
		_orderRuleOrderTypeResourceComponentServiceObjects;
	private static ComponentServiceObjects<OrderTypeResource>
		_orderTypeResourceComponentServiceObjects;
	private static ComponentServiceObjects<OrderTypeChannelResource>
		_orderTypeChannelResourceComponentServiceObjects;
	private static ComponentServiceObjects<ShippingAddressResource>
		_shippingAddressResourceComponentServiceObjects;
	private static ComponentServiceObjects<TermResource>
		_termResourceComponentServiceObjects;
	private static ComponentServiceObjects<TermOrderTypeResource>
		_termOrderTypeResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private BiFunction
		<Object, String, com.liferay.portal.kernel.search.filter.Filter>
			_filterBiFunction;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, com.liferay.portal.kernel.search.Sort[]>
		_sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;
	private VulcanBatchEngineExportTaskResource
		_vulcanBatchEngineExportTaskResource;
	private VulcanBatchEngineImportTaskResource
		_vulcanBatchEngineImportTaskResource;

}
// LIFERAY-REST-BUILDER-HASH:701065497