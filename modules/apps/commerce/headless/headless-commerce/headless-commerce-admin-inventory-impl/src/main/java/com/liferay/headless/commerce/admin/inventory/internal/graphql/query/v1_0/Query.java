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

package com.liferay.headless.commerce.admin.inventory.internal.graphql.query.v1_0;

import com.liferay.headless.commerce.admin.inventory.dto.v1_0.Warehouse;
import com.liferay.headless.commerce.admin.inventory.dto.v1_0.WarehouseItem;
import com.liferay.headless.commerce.admin.inventory.resource.v1_0.WarehouseItemResource;
import com.liferay.headless.commerce.admin.inventory.resource.v1_0.WarehouseResource;
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

import java.util.Date;
import java.util.Map;
import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
@Generated("")
public class Query {

	public static void setWarehouseResourceComponentServiceObjects(
		ComponentServiceObjects<WarehouseResource>
			warehouseResourceComponentServiceObjects) {

		_warehouseResourceComponentServiceObjects =
			warehouseResourceComponentServiceObjects;
	}

	public static void setWarehouseItemResourceComponentServiceObjects(
		ComponentServiceObjects<WarehouseItemResource>
			warehouseItemResourceComponentServiceObjects) {

		_warehouseItemResourceComponentServiceObjects =
			warehouseItemResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {warehousByExternalReferenceCode(externalReferenceCode: ___){active, city, countryISOCode, description, externalReferenceCode, id, latitude, longitude, mvccVersion, name, regionISOCode, street1, street2, street3, type, warehouseItems, zip}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Warehouse warehousByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_warehouseResourceComponentServiceObjects,
			this::_populateResourceContext,
			warehouseResource ->
				warehouseResource.getWarehousByExternalReferenceCode(
					externalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {warehousId(id: ___){active, city, countryISOCode, description, externalReferenceCode, id, latitude, longitude, mvccVersion, name, regionISOCode, street1, street2, street3, type, warehouseItems, zip}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Warehouse warehousId(@GraphQLName("id") Long id) throws Exception {
		return _applyComponentServiceObjects(
			_warehouseResourceComponentServiceObjects,
			this::_populateResourceContext,
			warehouseResource -> warehouseResource.getWarehousId(id));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {warehouses(filter: ___, page: ___, pageSize: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public WarehousePage warehouses(
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_warehouseResourceComponentServiceObjects,
			this::_populateResourceContext,
			warehouseResource -> new WarehousePage(
				warehouseResource.getWarehousesPage(
					_filterBiFunction.apply(warehouseResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(warehouseResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {warehouseItemByExternalReferenceCode(externalReferenceCode: ___){externalReferenceCode, id, modifiedDate, quantity, reservedQuantity, sku, warehouseExternalReferenceCode, warehouseId}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public WarehouseItem warehouseItemByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_warehouseItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			warehouseItemResource ->
				warehouseItemResource.getWarehouseItemByExternalReferenceCode(
					externalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {warehouseItem(id: ___){externalReferenceCode, id, modifiedDate, quantity, reservedQuantity, sku, warehouseExternalReferenceCode, warehouseId}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public WarehouseItem warehouseItem(@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_warehouseItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			warehouseItemResource -> warehouseItemResource.getWarehouseItem(
				id));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {warehousByExternalReferenceCodeWarehouseItems(externalReferenceCode: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public WarehouseItemPage warehousByExternalReferenceCodeWarehouseItems(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_warehouseItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			warehouseItemResource -> new WarehouseItemPage(
				warehouseItemResource.
					getWarehousByExternalReferenceCodeWarehouseItemsPage(
						externalReferenceCode, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {warehousIdWarehouseItems(id: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public WarehouseItemPage warehousIdWarehouseItems(
			@GraphQLName("id") Long id, @GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_warehouseItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			warehouseItemResource -> new WarehouseItemPage(
				warehouseItemResource.getWarehousIdWarehouseItemsPage(
					id, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {warehouseItemsUpdated(end: ___, page: ___, pageSize: ___, start: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public WarehouseItemPage warehouseItemsUpdated(
			@GraphQLName("end") Date end, @GraphQLName("start") Date start,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_warehouseItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			warehouseItemResource -> new WarehouseItemPage(
				warehouseItemResource.getWarehouseItemsUpdatedPage(
					end, start, Pagination.of(page, pageSize))));
	}

	@GraphQLTypeExtension(Warehouse.class)
	public class GetWarehouseItemByExternalReferenceCodeTypeExtension {

		public GetWarehouseItemByExternalReferenceCodeTypeExtension(
			Warehouse warehouse) {

			_warehouse = warehouse;
		}

		@GraphQLField
		public WarehouseItem itemByExternalReferenceCode() throws Exception {
			return _applyComponentServiceObjects(
				_warehouseItemResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				warehouseItemResource ->
					warehouseItemResource.
						getWarehouseItemByExternalReferenceCode(
							_warehouse.getExternalReferenceCode()));
		}

		private Warehouse _warehouse;

	}

	@GraphQLTypeExtension(Warehouse.class)
	public class
		GetWarehousByExternalReferenceCodeWarehouseItemsPageTypeExtension {

		public GetWarehousByExternalReferenceCodeWarehouseItemsPageTypeExtension(
			Warehouse warehouse) {

			_warehouse = warehouse;
		}

		@GraphQLField
		public WarehouseItemPage warehousByExternalReferenceCodeWarehouseItems(
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_warehouseItemResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				warehouseItemResource -> new WarehouseItemPage(
					warehouseItemResource.
						getWarehousByExternalReferenceCodeWarehouseItemsPage(
							_warehouse.getExternalReferenceCode(),
							Pagination.of(page, pageSize))));
		}

		private Warehouse _warehouse;

	}

	@GraphQLTypeExtension(WarehouseItem.class)
	public class GetWarehousByExternalReferenceCodeTypeExtension {

		public GetWarehousByExternalReferenceCodeTypeExtension(
			WarehouseItem warehouseItem) {

			_warehouseItem = warehouseItem;
		}

		@GraphQLField
		public Warehouse warehousByExternalReferenceCode() throws Exception {
			return _applyComponentServiceObjects(
				_warehouseResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				warehouseResource ->
					warehouseResource.getWarehousByExternalReferenceCode(
						_warehouseItem.getExternalReferenceCode()));
		}

		private WarehouseItem _warehouseItem;

	}

	@GraphQLName("WarehousePage")
	public class WarehousePage {

		public WarehousePage(Page warehousePage) {
			actions = warehousePage.getActions();

			items = warehousePage.getItems();
			lastPage = warehousePage.getLastPage();
			page = warehousePage.getPage();
			pageSize = warehousePage.getPageSize();
			totalCount = warehousePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Warehouse> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("WarehouseItemPage")
	public class WarehouseItemPage {

		public WarehouseItemPage(Page warehouseItemPage) {
			actions = warehouseItemPage.getActions();

			items = warehouseItemPage.getItems();
			lastPage = warehouseItemPage.getLastPage();
			page = warehouseItemPage.getPage();
			pageSize = warehouseItemPage.getPageSize();
			totalCount = warehouseItemPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<WarehouseItem> items;

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

	private void _populateResourceContext(WarehouseResource warehouseResource)
		throws Exception {

		warehouseResource.setContextAcceptLanguage(_acceptLanguage);
		warehouseResource.setContextCompany(_company);
		warehouseResource.setContextHttpServletRequest(_httpServletRequest);
		warehouseResource.setContextHttpServletResponse(_httpServletResponse);
		warehouseResource.setContextUriInfo(_uriInfo);
		warehouseResource.setContextUser(_user);
		warehouseResource.setGroupLocalService(_groupLocalService);
		warehouseResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			WarehouseItemResource warehouseItemResource)
		throws Exception {

		warehouseItemResource.setContextAcceptLanguage(_acceptLanguage);
		warehouseItemResource.setContextCompany(_company);
		warehouseItemResource.setContextHttpServletRequest(_httpServletRequest);
		warehouseItemResource.setContextHttpServletResponse(
			_httpServletResponse);
		warehouseItemResource.setContextUriInfo(_uriInfo);
		warehouseItemResource.setContextUser(_user);
		warehouseItemResource.setGroupLocalService(_groupLocalService);
		warehouseItemResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<WarehouseResource>
		_warehouseResourceComponentServiceObjects;
	private static ComponentServiceObjects<WarehouseItemResource>
		_warehouseItemResourceComponentServiceObjects;

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