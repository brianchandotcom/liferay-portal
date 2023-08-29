/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.order.internal.graphql.query.v1_0;

import com.liferay.headless.commerce.delivery.order.dto.v1_0.PlacedOrder;
import com.liferay.headless.commerce.delivery.order.dto.v1_0.PlacedOrderAddress;
import com.liferay.headless.commerce.delivery.order.dto.v1_0.PlacedOrderComment;
import com.liferay.headless.commerce.delivery.order.dto.v1_0.PlacedOrderItem;
import com.liferay.headless.commerce.delivery.order.dto.v1_0.PlacedOrderItemShipment;
import com.liferay.headless.commerce.delivery.order.resource.v1_0.PlacedOrderAddressResource;
import com.liferay.headless.commerce.delivery.order.resource.v1_0.PlacedOrderCommentResource;
import com.liferay.headless.commerce.delivery.order.resource.v1_0.PlacedOrderItemResource;
import com.liferay.headless.commerce.delivery.order.resource.v1_0.PlacedOrderItemShipmentResource;
import com.liferay.headless.commerce.delivery.order.resource.v1_0.PlacedOrderResource;
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

	public static void setPlacedOrderResourceComponentServiceObjects(
		ComponentServiceObjects<PlacedOrderResource>
			placedOrderResourceComponentServiceObjects) {

		_placedOrderResourceComponentServiceObjects =
			placedOrderResourceComponentServiceObjects;
	}

	public static void setPlacedOrderAddressResourceComponentServiceObjects(
		ComponentServiceObjects<PlacedOrderAddressResource>
			placedOrderAddressResourceComponentServiceObjects) {

		_placedOrderAddressResourceComponentServiceObjects =
			placedOrderAddressResourceComponentServiceObjects;
	}

	public static void setPlacedOrderCommentResourceComponentServiceObjects(
		ComponentServiceObjects<PlacedOrderCommentResource>
			placedOrderCommentResourceComponentServiceObjects) {

		_placedOrderCommentResourceComponentServiceObjects =
			placedOrderCommentResourceComponentServiceObjects;
	}

	public static void setPlacedOrderItemResourceComponentServiceObjects(
		ComponentServiceObjects<PlacedOrderItemResource>
			placedOrderItemResourceComponentServiceObjects) {

		_placedOrderItemResourceComponentServiceObjects =
			placedOrderItemResourceComponentServiceObjects;
	}

	public static void
		setPlacedOrderItemShipmentResourceComponentServiceObjects(
			ComponentServiceObjects<PlacedOrderItemShipmentResource>
				placedOrderItemShipmentResourceComponentServiceObjects) {

		_placedOrderItemShipmentResourceComponentServiceObjects =
			placedOrderItemShipmentResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {channelAccountPlacedOrders(accountId: ___, channelId: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves placed orders for specific account in the given channel."
	)
	public PlacedOrderPage channelAccountPlacedOrders(
			@GraphQLName("accountId") Long accountId,
			@GraphQLName("channelId") Long channelId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_placedOrderResourceComponentServiceObjects,
			this::_populateResourceContext,
			placedOrderResource -> new PlacedOrderPage(
				placedOrderResource.getChannelAccountPlacedOrdersPage(
					accountId, channelId, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {placedOrder(placedOrderId: ___){account, accountId, author, channelId, couponCode, createDate, currencyCode, customFields, errorMessages, id, lastPriceUpdateDate, modifiedDate, orderStatusInfo, orderTypeExternalReferenceCode, orderTypeId, orderUUID, paymentMethod, paymentMethodLabel, paymentStatus, paymentStatusInfo, paymentStatusLabel, placedOrderBillingAddress, placedOrderBillingAddressId, placedOrderComments, placedOrderItems, placedOrderShippingAddress, placedOrderShippingAddressId, printedNote, purchaseOrderNumber, shippingMethod, shippingOption, status, summary, useAsBilling, valid, workflowStatusInfo}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieve information of the given Placed Order."
	)
	public PlacedOrder placedOrder(
			@GraphQLName("placedOrderId") Long placedOrderId)
		throws Exception {

		return _applyComponentServiceObjects(
			_placedOrderResourceComponentServiceObjects,
			this::_populateResourceContext,
			placedOrderResource -> placedOrderResource.getPlacedOrder(
				placedOrderId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {placedOrderPaymentURL(callbackURL: ___, placedOrderId: ___){}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public String placedOrderPaymentURL(
			@GraphQLName("placedOrderId") Long placedOrderId,
			@GraphQLName("callbackURL") String callbackURL)
		throws Exception {

		return _applyComponentServiceObjects(
			_placedOrderResourceComponentServiceObjects,
			this::_populateResourceContext,
			placedOrderResource -> placedOrderResource.getPlacedOrderPaymentURL(
				placedOrderId, callbackURL));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {placedOrderPlacedOrderBillingAddres(placedOrderId: ___){city, country, countryISOCode, description, id, latitude, longitude, name, phoneNumber, region, regionISOCode, street1, street2, street3, type, typeId, vatNumber, zip}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieve placed order billing address.")
	public PlacedOrderAddress placedOrderPlacedOrderBillingAddres(
			@GraphQLName("placedOrderId") Long placedOrderId)
		throws Exception {

		return _applyComponentServiceObjects(
			_placedOrderAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			placedOrderAddressResource ->
				placedOrderAddressResource.
					getPlacedOrderPlacedOrderBillingAddres(placedOrderId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {placedOrderPlacedOrderShippingAddres(placedOrderId: ___){city, country, countryISOCode, description, id, latitude, longitude, name, phoneNumber, region, regionISOCode, street1, street2, street3, type, typeId, vatNumber, zip}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieve placed order shipping address.")
	public PlacedOrderAddress placedOrderPlacedOrderShippingAddres(
			@GraphQLName("placedOrderId") Long placedOrderId)
		throws Exception {

		return _applyComponentServiceObjects(
			_placedOrderAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			placedOrderAddressResource ->
				placedOrderAddressResource.
					getPlacedOrderPlacedOrderShippingAddres(placedOrderId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {placedOrderComment(placedOrderCommentId: ___){author, content, id, orderId, restricted}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public PlacedOrderComment placedOrderComment(
			@GraphQLName("placedOrderCommentId") Long placedOrderCommentId)
		throws Exception {

		return _applyComponentServiceObjects(
			_placedOrderCommentResourceComponentServiceObjects,
			this::_populateResourceContext,
			placedOrderCommentResource ->
				placedOrderCommentResource.getPlacedOrderComment(
					placedOrderCommentId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {placedOrderPlacedOrderComments(page: ___, pageSize: ___, placedOrderId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public PlacedOrderCommentPage placedOrderPlacedOrderComments(
			@GraphQLName("placedOrderId") Long placedOrderId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_placedOrderCommentResourceComponentServiceObjects,
			this::_populateResourceContext,
			placedOrderCommentResource -> new PlacedOrderCommentPage(
				placedOrderCommentResource.
					getPlacedOrderPlacedOrderCommentsPage(
						placedOrderId, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {placedOrderItem(placedOrderItemId: ___){adaptiveMediaImageHTMLTag, customFields, errorMessages, id, name, options, parentOrderItemId, placedOrderItemShipments, placedOrderItems, price, productId, productURLs, quantity, replacedSku, settings, sku, skuId, subscription, thumbnail, unitOfMeasureKey, valid, virtualItemURLs}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieve information of the given Placed Order."
	)
	public PlacedOrderItem placedOrderItem(
			@GraphQLName("placedOrderItemId") Long placedOrderItemId)
		throws Exception {

		return _applyComponentServiceObjects(
			_placedOrderItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			placedOrderItemResource ->
				placedOrderItemResource.getPlacedOrderItem(placedOrderItemId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {placedOrderPlacedOrderItems(page: ___, pageSize: ___, placedOrderId: ___, skuId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieve placed order items.")
	public PlacedOrderItemPage placedOrderPlacedOrderItems(
			@GraphQLName("placedOrderId") Long placedOrderId,
			@GraphQLName("skuId") Long skuId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_placedOrderItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			placedOrderItemResource -> new PlacedOrderItemPage(
				placedOrderItemResource.getPlacedOrderPlacedOrderItemsPage(
					placedOrderId, skuId, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {placedOrderItemPlacedOrderItemShipments(placedOrderItemId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieve shipments of the given Placed Order Item."
	)
	public PlacedOrderItemShipmentPage placedOrderItemPlacedOrderItemShipments(
			@GraphQLName("placedOrderItemId") Long placedOrderItemId)
		throws Exception {

		return _applyComponentServiceObjects(
			_placedOrderItemShipmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			placedOrderItemShipmentResource -> new PlacedOrderItemShipmentPage(
				placedOrderItemShipmentResource.
					getPlacedOrderItemPlacedOrderItemShipmentsPage(
						placedOrderItemId)));
	}

	@GraphQLTypeExtension(PlacedOrder.class)
	public class GetPlacedOrderPaymentURLTypeExtension {

		public GetPlacedOrderPaymentURLTypeExtension(PlacedOrder placedOrder) {
			_placedOrder = placedOrder;
		}

		@GraphQLField
		public String paymentURL(@GraphQLName("callbackURL") String callbackURL)
			throws Exception {

			return _applyComponentServiceObjects(
				_placedOrderResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				placedOrderResource ->
					placedOrderResource.getPlacedOrderPaymentURL(
						_placedOrder.getId(), callbackURL));
		}

		private PlacedOrder _placedOrder;

	}

	@GraphQLTypeExtension(PlacedOrder.class)
	public class GetPlacedOrderPlacedOrderBillingAddresTypeExtension {

		public GetPlacedOrderPlacedOrderBillingAddresTypeExtension(
			PlacedOrder placedOrder) {

			_placedOrder = placedOrder;
		}

		@GraphQLField(description = "Retrieve placed order billing address.")
		public PlacedOrderAddress placedOrderBillingAddres() throws Exception {
			return _applyComponentServiceObjects(
				_placedOrderAddressResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				placedOrderAddressResource ->
					placedOrderAddressResource.
						getPlacedOrderPlacedOrderBillingAddres(
							_placedOrder.getId()));
		}

		private PlacedOrder _placedOrder;

	}

	@GraphQLTypeExtension(PlacedOrder.class)
	public class GetPlacedOrderPlacedOrderShippingAddresTypeExtension {

		public GetPlacedOrderPlacedOrderShippingAddresTypeExtension(
			PlacedOrder placedOrder) {

			_placedOrder = placedOrder;
		}

		@GraphQLField(description = "Retrieve placed order shipping address.")
		public PlacedOrderAddress placedOrderShippingAddres() throws Exception {
			return _applyComponentServiceObjects(
				_placedOrderAddressResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				placedOrderAddressResource ->
					placedOrderAddressResource.
						getPlacedOrderPlacedOrderShippingAddres(
							_placedOrder.getId()));
		}

		private PlacedOrder _placedOrder;

	}

	@GraphQLName("PlacedOrderPage")
	public class PlacedOrderPage {

		public PlacedOrderPage(Page placedOrderPage) {
			actions = placedOrderPage.getActions();

			items = placedOrderPage.getItems();
			lastPage = placedOrderPage.getLastPage();
			page = placedOrderPage.getPage();
			pageSize = placedOrderPage.getPageSize();
			totalCount = placedOrderPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<PlacedOrder> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("PlacedOrderAddressPage")
	public class PlacedOrderAddressPage {

		public PlacedOrderAddressPage(Page placedOrderAddressPage) {
			actions = placedOrderAddressPage.getActions();

			items = placedOrderAddressPage.getItems();
			lastPage = placedOrderAddressPage.getLastPage();
			page = placedOrderAddressPage.getPage();
			pageSize = placedOrderAddressPage.getPageSize();
			totalCount = placedOrderAddressPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<PlacedOrderAddress> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("PlacedOrderCommentPage")
	public class PlacedOrderCommentPage {

		public PlacedOrderCommentPage(Page placedOrderCommentPage) {
			actions = placedOrderCommentPage.getActions();

			items = placedOrderCommentPage.getItems();
			lastPage = placedOrderCommentPage.getLastPage();
			page = placedOrderCommentPage.getPage();
			pageSize = placedOrderCommentPage.getPageSize();
			totalCount = placedOrderCommentPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<PlacedOrderComment> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("PlacedOrderItemPage")
	public class PlacedOrderItemPage {

		public PlacedOrderItemPage(Page placedOrderItemPage) {
			actions = placedOrderItemPage.getActions();

			items = placedOrderItemPage.getItems();
			lastPage = placedOrderItemPage.getLastPage();
			page = placedOrderItemPage.getPage();
			pageSize = placedOrderItemPage.getPageSize();
			totalCount = placedOrderItemPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<PlacedOrderItem> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("PlacedOrderItemShipmentPage")
	public class PlacedOrderItemShipmentPage {

		public PlacedOrderItemShipmentPage(Page placedOrderItemShipmentPage) {
			actions = placedOrderItemShipmentPage.getActions();

			items = placedOrderItemShipmentPage.getItems();
			lastPage = placedOrderItemShipmentPage.getLastPage();
			page = placedOrderItemShipmentPage.getPage();
			pageSize = placedOrderItemShipmentPage.getPageSize();
			totalCount = placedOrderItemShipmentPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<PlacedOrderItemShipment> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

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

	private void _populateResourceContext(
			PlacedOrderResource placedOrderResource)
		throws Exception {

		placedOrderResource.setContextAcceptLanguage(_acceptLanguage);
		placedOrderResource.setContextCompany(_company);
		placedOrderResource.setContextHttpServletRequest(_httpServletRequest);
		placedOrderResource.setContextHttpServletResponse(_httpServletResponse);
		placedOrderResource.setContextUriInfo(_uriInfo);
		placedOrderResource.setContextUser(_user);
		placedOrderResource.setGroupLocalService(_groupLocalService);
		placedOrderResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			PlacedOrderAddressResource placedOrderAddressResource)
		throws Exception {

		placedOrderAddressResource.setContextAcceptLanguage(_acceptLanguage);
		placedOrderAddressResource.setContextCompany(_company);
		placedOrderAddressResource.setContextHttpServletRequest(
			_httpServletRequest);
		placedOrderAddressResource.setContextHttpServletResponse(
			_httpServletResponse);
		placedOrderAddressResource.setContextUriInfo(_uriInfo);
		placedOrderAddressResource.setContextUser(_user);
		placedOrderAddressResource.setGroupLocalService(_groupLocalService);
		placedOrderAddressResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			PlacedOrderCommentResource placedOrderCommentResource)
		throws Exception {

		placedOrderCommentResource.setContextAcceptLanguage(_acceptLanguage);
		placedOrderCommentResource.setContextCompany(_company);
		placedOrderCommentResource.setContextHttpServletRequest(
			_httpServletRequest);
		placedOrderCommentResource.setContextHttpServletResponse(
			_httpServletResponse);
		placedOrderCommentResource.setContextUriInfo(_uriInfo);
		placedOrderCommentResource.setContextUser(_user);
		placedOrderCommentResource.setGroupLocalService(_groupLocalService);
		placedOrderCommentResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			PlacedOrderItemResource placedOrderItemResource)
		throws Exception {

		placedOrderItemResource.setContextAcceptLanguage(_acceptLanguage);
		placedOrderItemResource.setContextCompany(_company);
		placedOrderItemResource.setContextHttpServletRequest(
			_httpServletRequest);
		placedOrderItemResource.setContextHttpServletResponse(
			_httpServletResponse);
		placedOrderItemResource.setContextUriInfo(_uriInfo);
		placedOrderItemResource.setContextUser(_user);
		placedOrderItemResource.setGroupLocalService(_groupLocalService);
		placedOrderItemResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			PlacedOrderItemShipmentResource placedOrderItemShipmentResource)
		throws Exception {

		placedOrderItemShipmentResource.setContextAcceptLanguage(
			_acceptLanguage);
		placedOrderItemShipmentResource.setContextCompany(_company);
		placedOrderItemShipmentResource.setContextHttpServletRequest(
			_httpServletRequest);
		placedOrderItemShipmentResource.setContextHttpServletResponse(
			_httpServletResponse);
		placedOrderItemShipmentResource.setContextUriInfo(_uriInfo);
		placedOrderItemShipmentResource.setContextUser(_user);
		placedOrderItemShipmentResource.setGroupLocalService(
			_groupLocalService);
		placedOrderItemShipmentResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<PlacedOrderResource>
		_placedOrderResourceComponentServiceObjects;
	private static ComponentServiceObjects<PlacedOrderAddressResource>
		_placedOrderAddressResourceComponentServiceObjects;
	private static ComponentServiceObjects<PlacedOrderCommentResource>
		_placedOrderCommentResourceComponentServiceObjects;
	private static ComponentServiceObjects<PlacedOrderItemResource>
		_placedOrderItemResourceComponentServiceObjects;
	private static ComponentServiceObjects<PlacedOrderItemShipmentResource>
		_placedOrderItemShipmentResourceComponentServiceObjects;

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