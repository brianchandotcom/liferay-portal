/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.site.setting.internal.graphql.query.v1_0;

import com.liferay.headless.commerce.admin.site.setting.dto.v1_0.AvailabilityEstimate;
import com.liferay.headless.commerce.admin.site.setting.dto.v1_0.MeasurementUnit;
import com.liferay.headless.commerce.admin.site.setting.dto.v1_0.TaxCategory;
import com.liferay.headless.commerce.admin.site.setting.dto.v1_0.Warehouse;
import com.liferay.headless.commerce.admin.site.setting.resource.v1_0.AvailabilityEstimateResource;
import com.liferay.headless.commerce.admin.site.setting.resource.v1_0.MeasurementUnitResource;
import com.liferay.headless.commerce.admin.site.setting.resource.v1_0.TaxCategoryResource;
import com.liferay.headless.commerce.admin.site.setting.resource.v1_0.WarehouseResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import jakarta.annotation.Generated;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.ws.rs.core.UriInfo;

import java.util.Map;
import java.util.function.BiFunction;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public class Query {

	public static void setAvailabilityEstimateResourceComponentServiceObjects(
		ComponentServiceObjects<AvailabilityEstimateResource>
			availabilityEstimateResourceComponentServiceObjects) {

		_availabilityEstimateResourceComponentServiceObjects =
			availabilityEstimateResourceComponentServiceObjects;
	}

	public static void setMeasurementUnitResourceComponentServiceObjects(
		ComponentServiceObjects<MeasurementUnitResource>
			measurementUnitResourceComponentServiceObjects) {

		_measurementUnitResourceComponentServiceObjects =
			measurementUnitResourceComponentServiceObjects;
	}

	public static void setTaxCategoryResourceComponentServiceObjects(
		ComponentServiceObjects<TaxCategoryResource>
			taxCategoryResourceComponentServiceObjects) {

		_taxCategoryResourceComponentServiceObjects =
			taxCategoryResourceComponentServiceObjects;
	}

	public static void setWarehouseResourceComponentServiceObjects(
		ComponentServiceObjects<WarehouseResource>
			warehouseResourceComponentServiceObjects) {

		_warehouseResourceComponentServiceObjects =
			warehouseResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {availabilityEstimate(id: ___){groupId, id, priority, title}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Fetches the AvailabilityEstimate identified by id. Calls CommerceAvailabilityEstimateService.getCommerceAvailabilityEstimate. Validation -- 404 when no estimate matches the id."
	)
	public AvailabilityEstimate availabilityEstimate(@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_availabilityEstimateResourceComponentServiceObjects,
			this::_populateResourceContext,
			availabilityEstimateResource ->
				availabilityEstimateResource.getAvailabilityEstimate(id));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {commerceAdminSettingGroupAvailabilityEstimate(groupId: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Unimplemented endpoint for listing AvailabilityEstimate records under the supplied site (groupId). Calls -- none; throws UnsupportedOperationException without invoking CommerceAvailabilityEstimateService, so every request is rejected with a 400 Bad Request and no records are returned."
	)
	public AvailabilityEstimatePage
			commerceAdminSettingGroupAvailabilityEstimate(
				@GraphQLName("groupId") Long groupId,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_availabilityEstimateResourceComponentServiceObjects,
			this::_populateResourceContext,
			availabilityEstimateResource -> new AvailabilityEstimatePage(
				availabilityEstimateResource.
					getCommerceAdminSiteSettingGroupAvailabilityEstimatePage(
						groupId, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {measurementUnit(id: ___){companyId, externalReferenceCode, id, key, name, primary, priority, rate, type}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Fetches the MeasurementUnit identified by id. Calls CPMeasurementUnitService.fetchCPMeasurementUnit. Validation -- NoSuchCPMeasurementUnitException -> 404 when id not found."
	)
	public MeasurementUnit measurementUnit(@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_measurementUnitResourceComponentServiceObjects,
			this::_populateResourceContext,
			measurementUnitResource ->
				measurementUnitResource.getMeasurementUnit(id));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {measurementUnitByExternalReferenceCode(externalReferenceCode: ___){companyId, externalReferenceCode, id, key, name, primary, priority, rate, type}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Fetches the MeasurementUnit identified by external reference code. Calls CPMeasurementUnitService.fetchCPMeasurementUnitByExternalReferenceCode. Validation -- NoSuchCPMeasurementUnitException -> 404 when ERC not found."
	)
	public MeasurementUnit measurementUnitByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_measurementUnitResourceComponentServiceObjects,
			this::_populateResourceContext,
			measurementUnitResource ->
				measurementUnitResource.
					getMeasurementUnitByExternalReferenceCode(
						externalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {measurementUnitByKey(key: ___){companyId, externalReferenceCode, id, key, name, primary, priority, rate, type}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Fetches the MeasurementUnit identified by its stable string key. Calls CPMeasurementUnitService.fetchCPMeasurementUnit (companyId, key). Validation -- NoSuchCPMeasurementUnitException -> 404 when key not found."
	)
	public MeasurementUnit measurementUnitByKey(@GraphQLName("key") String key)
		throws Exception {

		return _applyComponentServiceObjects(
			_measurementUnitResourceComponentServiceObjects,
			this::_populateResourceContext,
			measurementUnitResource ->
				measurementUnitResource.getMeasurementUnitByKey(key));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {measurementUnitsByType(measurementUnitType: ___, page: ___, pageSize: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Returns a page of MeasurementUnit records of the supplied type for the caller's company; type accepts either the integer code (0, 1, 2) or the human label (Dimensions, Weight, Unit). Calls CPMeasurementUnitService.getCPMeasurementUnits (companyId, type), applying the page and pageSize query parameters to bound the response. List query support -- the sort parameter is accepted on the wire but ignored, because the service is called with a null OrderByComparator. When the type cannot be resolved, returns an empty list (CPMeasurementUnitTypeException is swallowed at debug level)."
	)
	public MeasurementUnitPage measurementUnitsByType(
			@GraphQLName("measurementUnitType") String measurementUnitType,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_measurementUnitResourceComponentServiceObjects,
			this::_populateResourceContext,
			measurementUnitResource -> new MeasurementUnitPage(
				measurementUnitResource.getMeasurementUnitsByType(
					measurementUnitType, Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						measurementUnitResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {measurementUnits(filter: ___, page: ___, pageSize: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Returns a page of MeasurementUnit records scoped to the caller's company. Calls CPMeasurementUnitService.getCPMeasurementUnits, applying the page and pageSize query parameters to bound the response. List query support -- the filter (OData v4) and sort parameters are accepted on the wire but ignored, because the service is called with a null OrderByComparator and no entity model is registered for OData filtering."
	)
	public MeasurementUnitPage measurementUnits(
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_measurementUnitResourceComponentServiceObjects,
			this::_populateResourceContext,
			measurementUnitResource -> new MeasurementUnitPage(
				measurementUnitResource.getMeasurementUnitsPage(
					_filterBiFunction.apply(
						measurementUnitResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						measurementUnitResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {commerceAdminSettingGroupTaxCategory(groupId: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Unimplemented endpoint for listing TaxCategory records under the supplied site (groupId). Calls -- none; throws UnsupportedOperationException without invoking CPTaxCategoryService, so every request is rejected with a 400 Bad Request and no records are returned."
	)
	public TaxCategoryPage commerceAdminSettingGroupTaxCategory(
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxCategoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxCategoryResource -> new TaxCategoryPage(
				taxCategoryResource.
					getCommerceAdminSiteSettingGroupTaxCategoryPage(
						groupId, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {taxCategory(id: ___){description, groupId, id, name}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Fetches the TaxCategory identified by id. Calls CPTaxCategoryService.getCPTaxCategory. Validation -- 404 when no entity matches the id."
	)
	public TaxCategory taxCategory(@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxCategoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxCategoryResource -> taxCategoryResource.getTaxCategory(id));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {commerceAdminSettingGroupWarehouse(active: ___, groupId: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Unimplemented endpoint for listing Warehouse records under the supplied site (groupId). Calls -- none; throws UnsupportedOperationException without invoking CommerceInventoryWarehouseService, so every request is rejected with a 400 Bad Request and no records are returned. For Warehouse list semantics that persist, use the inventory administration API."
	)
	public WarehousePage commerceAdminSettingGroupWarehouse(
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("active") Boolean active,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_warehouseResourceComponentServiceObjects,
			this::_populateResourceContext,
			warehouseResource -> new WarehousePage(
				warehouseResource.getCommerceAdminSiteSettingGroupWarehousePage(
					groupId, active, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {warehouse(id: ___){active, city, commerceCountryId, commerceRegionId, description, groupId, id, latitude, longitude, mvccVersion, name, primary, street1, street2, street3, zip}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Fetches the Warehouse definition identified by id. Calls CommerceInventoryWarehouseService.getCommerceInventoryWarehouse. Validation -- 404 when no entity matches the id."
	)
	public Warehouse warehouse(@GraphQLName("id") Long id) throws Exception {
		return _applyComponentServiceObjects(
			_warehouseResourceComponentServiceObjects,
			this::_populateResourceContext,
			warehouseResource -> warehouseResource.getWarehouse(id));
	}

	@GraphQLName("AvailabilityEstimatePage")
	public class AvailabilityEstimatePage {

		public AvailabilityEstimatePage(Page availabilityEstimatePage) {
			actions = availabilityEstimatePage.getActions();

			items = availabilityEstimatePage.getItems();
			lastPage = availabilityEstimatePage.getLastPage();
			page = availabilityEstimatePage.getPage();
			pageSize = availabilityEstimatePage.getPageSize();
			totalCount = availabilityEstimatePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<AvailabilityEstimate> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("MeasurementUnitPage")
	public class MeasurementUnitPage {

		public MeasurementUnitPage(Page measurementUnitPage) {
			actions = measurementUnitPage.getActions();

			items = measurementUnitPage.getItems();
			lastPage = measurementUnitPage.getLastPage();
			page = measurementUnitPage.getPage();
			pageSize = measurementUnitPage.getPageSize();
			totalCount = measurementUnitPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<MeasurementUnit> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("TaxCategoryPage")
	public class TaxCategoryPage {

		public TaxCategoryPage(Page taxCategoryPage) {
			actions = taxCategoryPage.getActions();

			items = taxCategoryPage.getItems();
			lastPage = taxCategoryPage.getLastPage();
			page = taxCategoryPage.getPage();
			pageSize = taxCategoryPage.getPageSize();
			totalCount = taxCategoryPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<TaxCategory> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

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
		protected Map<String, Map<String, String>> actions;

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
			AvailabilityEstimateResource availabilityEstimateResource)
		throws Exception {

		availabilityEstimateResource.setContextAcceptLanguage(_acceptLanguage);
		availabilityEstimateResource.setContextCompany(_company);
		availabilityEstimateResource.setContextHttpServletRequest(
			_httpServletRequest);
		availabilityEstimateResource.setContextHttpServletResponse(
			_httpServletResponse);
		availabilityEstimateResource.setContextUriInfo(_uriInfo);
		availabilityEstimateResource.setContextUser(_user);
		availabilityEstimateResource.setGroupLocalService(_groupLocalService);
		availabilityEstimateResource.setResourceActionLocalService(
			_resourceActionLocalService);
		availabilityEstimateResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		availabilityEstimateResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			MeasurementUnitResource measurementUnitResource)
		throws Exception {

		measurementUnitResource.setContextAcceptLanguage(_acceptLanguage);
		measurementUnitResource.setContextCompany(_company);
		measurementUnitResource.setContextHttpServletRequest(
			_httpServletRequest);
		measurementUnitResource.setContextHttpServletResponse(
			_httpServletResponse);
		measurementUnitResource.setContextUriInfo(_uriInfo);
		measurementUnitResource.setContextUser(_user);
		measurementUnitResource.setGroupLocalService(_groupLocalService);
		measurementUnitResource.setResourceActionLocalService(
			_resourceActionLocalService);
		measurementUnitResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		measurementUnitResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			TaxCategoryResource taxCategoryResource)
		throws Exception {

		taxCategoryResource.setContextAcceptLanguage(_acceptLanguage);
		taxCategoryResource.setContextCompany(_company);
		taxCategoryResource.setContextHttpServletRequest(_httpServletRequest);
		taxCategoryResource.setContextHttpServletResponse(_httpServletResponse);
		taxCategoryResource.setContextUriInfo(_uriInfo);
		taxCategoryResource.setContextUser(_user);
		taxCategoryResource.setGroupLocalService(_groupLocalService);
		taxCategoryResource.setResourceActionLocalService(
			_resourceActionLocalService);
		taxCategoryResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		taxCategoryResource.setRoleLocalService(_roleLocalService);
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
		warehouseResource.setResourceActionLocalService(
			_resourceActionLocalService);
		warehouseResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		warehouseResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<AvailabilityEstimateResource>
		_availabilityEstimateResourceComponentServiceObjects;
	private static ComponentServiceObjects<MeasurementUnitResource>
		_measurementUnitResourceComponentServiceObjects;
	private static ComponentServiceObjects<TaxCategoryResource>
		_taxCategoryResourceComponentServiceObjects;
	private static ComponentServiceObjects<WarehouseResource>
		_warehouseResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private BiFunction
		<Object, String, com.liferay.portal.kernel.search.filter.Filter>
			_filterBiFunction;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private ResourceActionLocalService _resourceActionLocalService;
	private ResourcePermissionLocalService _resourcePermissionLocalService;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, com.liferay.portal.kernel.search.Sort[]>
		_sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}
// LIFERAY-REST-BUILDER-HASH:-104213659