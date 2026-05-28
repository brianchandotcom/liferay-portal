/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.shipment.internal.graphql.mutation.v1_0;

import com.liferay.headless.commerce.admin.shipment.dto.v1_0.Shipment;
import com.liferay.headless.commerce.admin.shipment.dto.v1_0.ShipmentItem;
import com.liferay.headless.commerce.admin.shipment.dto.v1_0.ShippingAddress;
import com.liferay.headless.commerce.admin.shipment.resource.v1_0.ShipmentItemResource;
import com.liferay.headless.commerce.admin.shipment.resource.v1_0.ShipmentResource;
import com.liferay.headless.commerce.admin.shipment.resource.v1_0.ShippingAddressResource;
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
 * @author Andrea Sbarra
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setShipmentResourceComponentServiceObjects(
		ComponentServiceObjects<ShipmentResource>
			shipmentResourceComponentServiceObjects) {

		_shipmentResourceComponentServiceObjects =
			shipmentResourceComponentServiceObjects;
	}

	public static void setShipmentItemResourceComponentServiceObjects(
		ComponentServiceObjects<ShipmentItemResource>
			shipmentItemResourceComponentServiceObjects) {

		_shipmentItemResourceComponentServiceObjects =
			shipmentItemResourceComponentServiceObjects;
	}

	public static void setShippingAddressResourceComponentServiceObjects(
		ComponentServiceObjects<ShippingAddressResource>
			shippingAddressResourceComponentServiceObjects) {

		_shippingAddressResourceComponentServiceObjects =
			shippingAddressResourceComponentServiceObjects;
	}

	@GraphQLField(
		description = "Deletes the shipment identified by shipmentId. Calls the service with restoreStockQuantity=false; the row is removed without re-crediting the shipped quantity back to the order items."
	)
	public boolean deleteShipment(@GraphQLName("shipmentId") Long shipmentId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_shipmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			shipmentResource -> shipmentResource.deleteShipment(shipmentId));

		return true;
	}

	@GraphQLField
	public Response deleteShipmentBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_shipmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			shipmentResource -> shipmentResource.deleteShipmentBatch(
				callbackURL, object));
	}

	@GraphQLField(
		description = "Deletes the shipment identified by external reference code. Resolves the row against the current company scope and calls deleteCommerceShipment with restoreStockQuantity=false; raises a not-found error (404) when the ERC is unknown."
	)
	public boolean deleteShipmentByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_shipmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			shipmentResource ->
				shipmentResource.deleteShipmentByExternalReferenceCode(
					externalReferenceCode));

		return true;
	}

	@GraphQLField(
		description = "Updates the shipment identified by shipmentId using JSON Merge Patch (only the supplied fields are modified). Resolves the row, applies carrier, trackingNumber, trackingURL, expectedDate, shippingDate, and shippingMethodId via updateCommerceShipment, replays the nested shippingAddress and shipmentItems collections when supplied, and optionally rewrites externalReferenceCode. Domain validation (inactive warehouse, malformed address) maps to 422."
	)
	public Shipment patchShipment(
			@GraphQLName("shipmentId") Long shipmentId,
			@GraphQLName("shipment") Shipment shipment)
		throws Exception {

		return _applyComponentServiceObjects(
			_shipmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			shipmentResource -> shipmentResource.patchShipment(
				shipmentId, shipment));
	}

	@GraphQLField(
		description = "Updates the shipment identified by external reference code using JSON Merge Patch (only the supplied fields are modified). Resolves the row, applies carrier, trackingNumber, trackingURL, expectedDate, shippingDate, and shippingMethodId, and replays the nested shippingAddress and shipmentItems collections when supplied. Raises a not-found error (404) when the ERC is unknown; domain validation (inactive warehouse, malformed address) maps to 422."
	)
	public Shipment patchShipmentByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("shipment") Shipment shipment)
		throws Exception {

		return _applyComponentServiceObjects(
			_shipmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			shipmentResource ->
				shipmentResource.patchShipmentByExternalReferenceCode(
					externalReferenceCode, shipment));
	}

	@GraphQLField(
		description = "Creates a shipment for an existing order identified by orderId (preferred) or orderExternalReferenceCode. Resolves the order, then calls the service cloning the order's groupId, accountId, shippingAddressId, shippingMethodId, and shippingOptionName. Applies the optional carrier, trackingNumber, trackingURL, expectedDate, and shippingDate via updateCommerceShipment; when shippingAddress is supplied the linked address is updated in place, and when shipmentItems is supplied every existing item is replaced. Raises a not-found error (404) when orderExternalReferenceCode is unknown; domain validation (inactive warehouse, invalid shipping address) maps to 422."
	)
	public Shipment createShipment(@GraphQLName("shipment") Shipment shipment)
		throws Exception {

		return _applyComponentServiceObjects(
			_shipmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			shipmentResource -> shipmentResource.postShipment(shipment));
	}

	@GraphQLField
	public Response createShipmentBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_shipmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			shipmentResource -> shipmentResource.postShipmentBatch(
				callbackURL, object));
	}

	@GraphQLField(
		description = "Transitions the shipment identified by external reference code to the DELIVERED workflow state (code 3). Resolves the parent shipment via fetchCommerceShipmentByExternalReferenceCode and calls the service with SHIPMENT_STATUS_DELIVERED; raises a not-found error (404) when the ERC is unknown and a status conflict (400) when the transition is not allowed from the current state."
	)
	public Shipment createShipmentByExternalReferenceCodeStatusDelivered(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_shipmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			shipmentResource ->
				shipmentResource.
					postShipmentByExternalReferenceCodeStatusDelivered(
						externalReferenceCode));
	}

	@GraphQLField(
		description = "Transitions the shipment identified by external reference code to the READY_TO_BE_SHIPPED workflow state (code 1). Resolves the parent shipment via fetchCommerceShipmentByExternalReferenceCode and calls the service with SHIPMENT_STATUS_READY_TO_BE_SHIPPED; raises a not-found error (404) when the ERC is unknown and a status conflict (400) when the transition is not allowed from the current state."
	)
	public Shipment createShipmentByExternalReferenceCodeStatusFinishProcessing(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_shipmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			shipmentResource ->
				shipmentResource.
					postShipmentByExternalReferenceCodeStatusFinishProcessing(
						externalReferenceCode));
	}

	@GraphQLField(
		description = "Transitions the shipment identified by external reference code to the SHIPPED workflow state (code 2). Resolves the parent shipment via fetchCommerceShipmentByExternalReferenceCode and calls the service with SHIPMENT_STATUS_SHIPPED; raises a not-found error (404) when the ERC is unknown and a status conflict (400) when the transition is not allowed from the current state."
	)
	public Shipment createShipmentByExternalReferenceCodeStatusShipped(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_shipmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			shipmentResource ->
				shipmentResource.
					postShipmentByExternalReferenceCodeStatusShipped(
						externalReferenceCode));
	}

	@GraphQLField(
		description = "Transitions the shipment identified by shipmentId to the DELIVERED workflow state (code 3). Calls the service with SHIPMENT_STATUS_DELIVERED; raises a status conflict (400) when the transition is not allowed from the current state."
	)
	public Shipment createShipmentStatusDelivered(
			@GraphQLName("shipmentId") Long shipmentId)
		throws Exception {

		return _applyComponentServiceObjects(
			_shipmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			shipmentResource -> shipmentResource.postShipmentStatusDelivered(
				shipmentId));
	}

	@GraphQLField(
		description = "Transitions the shipment identified by shipmentId to the READY_TO_BE_SHIPPED workflow state (code 1). Calls the service with SHIPMENT_STATUS_READY_TO_BE_SHIPPED; raises a status conflict (400) when the transition is not allowed from the current state."
	)
	public Shipment createShipmentStatusFinishProcessing(
			@GraphQLName("shipmentId") Long shipmentId)
		throws Exception {

		return _applyComponentServiceObjects(
			_shipmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			shipmentResource ->
				shipmentResource.postShipmentStatusFinishProcessing(
					shipmentId));
	}

	@GraphQLField(
		description = "Transitions the shipment identified by shipmentId to the SHIPPED workflow state (code 2). Calls the service with SHIPMENT_STATUS_SHIPPED; raises a status conflict (400) when the transition is not allowed from the current state."
	)
	public Shipment createShipmentStatusShipped(
			@GraphQLName("shipmentId") Long shipmentId)
		throws Exception {

		return _applyComponentServiceObjects(
			_shipmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			shipmentResource -> shipmentResource.postShipmentStatusShipped(
				shipmentId));
	}

	@GraphQLField
	public Response createShipmentsPageExportBatch(
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("sort") String sortsString,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("contentType") String contentType,
			@GraphQLName("fieldNames") String fieldNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_shipmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			shipmentResource -> shipmentResource.postShipmentsPageExportBatch(
				search, _filterBiFunction.apply(shipmentResource, filterString),
				_sortsBiFunction.apply(shipmentResource, sortsString),
				callbackURL, contentType, fieldNames));
	}

	@GraphQLField(
		description = "Upserts the shipment identified by external reference code. When the ERC resolves, the row is updated and the nested shippingAddress and shipmentItems collections are replayed; when the ERC is unknown, the request body's orderId is used to resolve the source order and addCommerceShipment is invoked to seed a new shipment from the order's groupId, accountId, shippingAddressId, shippingMethodId, and shippingOptionName. Domain validation maps to 422."
	)
	public Shipment updateShipmentByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("shipment") Shipment shipment)
		throws Exception {

		return _applyComponentServiceObjects(
			_shipmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			shipmentResource ->
				shipmentResource.putShipmentByExternalReferenceCode(
					externalReferenceCode, shipment));
	}

	@GraphQLField(
		description = "Deletes the shipment item identified by shipmentItemId. Calls the service with restoreStockQuantity=false; the row is removed without re-crediting the shipped quantity back to the order item."
	)
	public boolean deleteShipmentItem(
			@GraphQLName("shipmentItemId") Long shipmentItemId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_shipmentItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			shipmentItemResource -> shipmentItemResource.deleteShipmentItem(
				shipmentItemId));

		return true;
	}

	@GraphQLField
	public Response deleteShipmentItemBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_shipmentItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			shipmentItemResource ->
				shipmentItemResource.deleteShipmentItemBatch(
					callbackURL, object));
	}

	@GraphQLField(
		description = "Deletes the shipment item identified by external reference code. Resolves the row and calls deleteCommerceShipmentItem with restoreStockQuantity=false; raises a not-found error (404) when the ERC is unknown."
	)
	public boolean deleteShipmentItemByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_shipmentItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			shipmentItemResource ->
				shipmentItemResource.deleteShipmentItemByExternalReferenceCode(
					externalReferenceCode));

		return true;
	}

	@GraphQLField(
		description = "Updates the shipment item identified by shipmentItemId using JSON Merge Patch (only the supplied fields are modified). Resolves the row, then calls updateCommerceShipmentItem with the resolved warehouse, the new quantity, and the validateInventory flag (default true). Domain validation (inactive warehouse, insufficient stock) maps to 422."
	)
	public ShipmentItem patchShipmentItem(
			@GraphQLName("shipmentItemId") Long shipmentItemId,
			@GraphQLName("shipmentItem") ShipmentItem shipmentItem)
		throws Exception {

		return _applyComponentServiceObjects(
			_shipmentItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			shipmentItemResource -> shipmentItemResource.patchShipmentItem(
				shipmentItemId, shipmentItem));
	}

	@GraphQLField(
		description = "Updates the shipment item identified by external reference code using JSON Merge Patch (only the supplied fields are modified). Resolves the row, then calls the service with the resolved warehouse, the new quantity, and the validateInventory flag (default true). Raises a not-found error (404) when the ERC is unknown; domain validation (inactive warehouse, insufficient stock) maps to 422."
	)
	public ShipmentItem patchShipmentItemByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("shipmentItem") ShipmentItem shipmentItem)
		throws Exception {

		return _applyComponentServiceObjects(
			_shipmentItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			shipmentItemResource ->
				shipmentItemResource.patchShipmentItemByExternalReferenceCode(
					externalReferenceCode, shipmentItem));
	}

	@GraphQLField(
		description = "Creates a shipment item on the shipment identified by shipmentId. Resolves the order item and warehouse via the supplied IDs or external reference codes, then calls the service with the requested quantity and the validateInventory flag (default true). Domain validation (inactive warehouse, insufficient stock, missing order item) maps to 422."
	)
	public ShipmentItem createShipmentItem(
			@GraphQLName("shipmentId") Long shipmentId,
			@GraphQLName("shipmentItem") ShipmentItem shipmentItem)
		throws Exception {

		return _applyComponentServiceObjects(
			_shipmentItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			shipmentItemResource -> shipmentItemResource.postShipmentItem(
				shipmentId, shipmentItem));
	}

	@GraphQLField(
		description = "Creates a shipment item on the shipment identified by external reference code. Resolves the parent shipment via fetchCommerceShipmentByExternalReferenceCode and then delegates to the postShipmentItem flow, which resolves the warehouse and order item, then calls the service with the supplied quantity and validateInventory flag (default true). Raises a not-found error (404) when the parent ERC is unknown; domain validation (inactive warehouse, insufficient stock) maps to 422."
	)
	public ShipmentItem createShipmentItemByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("shipmentItem") ShipmentItem shipmentItem)
		throws Exception {

		return _applyComponentServiceObjects(
			_shipmentItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			shipmentItemResource ->
				shipmentItemResource.postShipmentItemByExternalReferenceCode(
					externalReferenceCode, shipmentItem));
	}

	@GraphQLField(
		description = "Upserts a shipment item on the shipment addressed by external reference code. The parent shipment is resolved by ERC and, when missing, by the body's shipmentId; the item is then created or updated through the service keyed by the body's externalReferenceCode. Domain validation (inactive warehouse, insufficient stock) maps to 422."
	)
	public ShipmentItem updateShipmentByExternalReferenceCodeItem(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("shipmentItem") ShipmentItem shipmentItem)
		throws Exception {

		return _applyComponentServiceObjects(
			_shipmentItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			shipmentItemResource ->
				shipmentItemResource.putShipmentByExternalReferenceCodeItem(
					externalReferenceCode, shipmentItem));
	}

	@GraphQLField(
		description = "Updates the ShippingAddress bound to the shipment identified by external reference code. Resolves the parent shipment via fetchCommerceShipmentByExternalReferenceCode and delegates to ShippingAddressUtil.updateShippingAddress, which creates or updates the underlying address and rebinds the shipment to it; country and region are resolved by ISO code. Raises a not-found error (404) when the ERC is unknown; domain validation (malformed city, country, name, street, or zip) maps to 422."
	)
	public ShippingAddress patchShipmentByExternalReferenceCodeShippingAddress(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("shippingAddress") ShippingAddress shippingAddress)
		throws Exception {

		return _applyComponentServiceObjects(
			_shippingAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			shippingAddressResource ->
				shippingAddressResource.
					patchShipmentByExternalReferenceCodeShippingAddress(
						externalReferenceCode, shippingAddress));
	}

	@GraphQLField(
		description = "Updates the ShippingAddress bound to the shipment identified by shipmentId. Resolves the parent shipment and delegates to ShippingAddressUtil.updateShippingAddress, which creates or updates the underlying address and rebinds the shipment to it; country and region are resolved by ISO code. Domain validation (malformed city, country, name, street, or zip) maps to 422."
	)
	public ShippingAddress patchShipmentShippingAddress(
			@GraphQLName("shipmentId") Long shipmentId,
			@GraphQLName("shippingAddress") ShippingAddress shippingAddress)
		throws Exception {

		return _applyComponentServiceObjects(
			_shippingAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			shippingAddressResource ->
				shippingAddressResource.patchShipmentShippingAddress(
					shipmentId, shippingAddress));
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

	private void _populateResourceContext(ShipmentResource shipmentResource)
		throws Exception {

		shipmentResource.setContextAcceptLanguage(_acceptLanguage);
		shipmentResource.setContextCompany(_company);
		shipmentResource.setContextHttpServletRequest(_httpServletRequest);
		shipmentResource.setContextHttpServletResponse(_httpServletResponse);
		shipmentResource.setContextUriInfo(_uriInfo);
		shipmentResource.setContextUser(_user);
		shipmentResource.setGroupLocalService(_groupLocalService);
		shipmentResource.setRoleLocalService(_roleLocalService);

		shipmentResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		shipmentResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private void _populateResourceContext(
			ShipmentItemResource shipmentItemResource)
		throws Exception {

		shipmentItemResource.setContextAcceptLanguage(_acceptLanguage);
		shipmentItemResource.setContextCompany(_company);
		shipmentItemResource.setContextHttpServletRequest(_httpServletRequest);
		shipmentItemResource.setContextHttpServletResponse(
			_httpServletResponse);
		shipmentItemResource.setContextUriInfo(_uriInfo);
		shipmentItemResource.setContextUser(_user);
		shipmentItemResource.setGroupLocalService(_groupLocalService);
		shipmentItemResource.setRoleLocalService(_roleLocalService);

		shipmentItemResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		shipmentItemResource.setVulcanBatchEngineImportTaskResource(
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

	private static ComponentServiceObjects<ShipmentResource>
		_shipmentResourceComponentServiceObjects;
	private static ComponentServiceObjects<ShipmentItemResource>
		_shipmentItemResourceComponentServiceObjects;
	private static ComponentServiceObjects<ShippingAddressResource>
		_shippingAddressResourceComponentServiceObjects;

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
// LIFERAY-REST-BUILDER-HASH:343654984