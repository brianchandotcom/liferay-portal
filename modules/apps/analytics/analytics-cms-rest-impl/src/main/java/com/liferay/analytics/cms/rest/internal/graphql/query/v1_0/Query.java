/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.cms.rest.internal.graphql.query.v1_0;

import com.liferay.analytics.cms.rest.dto.v1_0.ConnectionInfo;
import com.liferay.analytics.cms.rest.dto.v1_0.InventoryAnalysis;
import com.liferay.analytics.cms.rest.dto.v1_0.ObjectEntryMetric;
import com.liferay.analytics.cms.rest.dto.v1_0.Overview;
import com.liferay.analytics.cms.rest.resource.v1_0.ConnectionInfoResource;
import com.liferay.analytics.cms.rest.resource.v1_0.InventoryAnalysisResource;
import com.liferay.analytics.cms.rest.resource.v1_0.ObjectEntryMetricResource;
import com.liferay.analytics.cms.rest.resource.v1_0.OverviewResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.GroupLocalService;
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
 * @author Rachael Koestartyo
 * @generated
 */
@Generated("")
public class Query {

	public static void setConnectionInfoResourceComponentServiceObjects(
		ComponentServiceObjects<ConnectionInfoResource>
			connectionInfoResourceComponentServiceObjects) {

		_connectionInfoResourceComponentServiceObjects =
			connectionInfoResourceComponentServiceObjects;
	}

	public static void setInventoryAnalysisResourceComponentServiceObjects(
		ComponentServiceObjects<InventoryAnalysisResource>
			inventoryAnalysisResourceComponentServiceObjects) {

		_inventoryAnalysisResourceComponentServiceObjects =
			inventoryAnalysisResourceComponentServiceObjects;
	}

	public static void setObjectEntryMetricResourceComponentServiceObjects(
		ComponentServiceObjects<ObjectEntryMetricResource>
			objectEntryMetricResourceComponentServiceObjects) {

		_objectEntryMetricResourceComponentServiceObjects =
			objectEntryMetricResourceComponentServiceObjects;
	}

	public static void setOverviewResourceComponentServiceObjects(
		ComponentServiceObjects<OverviewResource>
			overviewResourceComponentServiceObjects) {

		_overviewResourceComponentServiceObjects =
			overviewResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {connectionInfo(depotEntryId: ___){admin, connectedToAnalyticsCloud, connectedToSpace, siteSyncedToAnalyticsCloud}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ConnectionInfo connectionInfo(
			@GraphQLName("depotEntryId") Long depotEntryId)
		throws Exception {

		return _applyComponentServiceObjects(
			_connectionInfoResourceComponentServiceObjects,
			this::_populateResourceContext,
			connectionInfoResource -> connectionInfoResource.getConnectionInfo(
				depotEntryId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {inventoryAnalysis(categoryId: ___, depotEntryId: ___, groupBy: ___, languageId: ___, page: ___, pageSize: ___, rangeEnd: ___, rangeKey: ___, rangeStart: ___, structureId: ___, tagId: ___, vocabularyId: ___){inventoryAnalysisItems, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public InventoryAnalysis inventoryAnalysis(
			@GraphQLName("categoryId") Long categoryId,
			@GraphQLName("depotEntryId") Long depotEntryId,
			@GraphQLName("groupBy") String groupBy,
			@GraphQLName("languageId") String languageId,
			@GraphQLName("rangeEnd") String rangeEnd,
			@GraphQLName("rangeKey") Integer rangeKey,
			@GraphQLName("rangeStart") String rangeStart,
			@GraphQLName("structureId") Long structureId,
			@GraphQLName("tagId") Long tagId,
			@GraphQLName("vocabularyId") Long vocabularyId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_inventoryAnalysisResourceComponentServiceObjects,
			this::_populateResourceContext,
			inventoryAnalysisResource ->
				inventoryAnalysisResource.getInventoryAnalysis(
					categoryId, depotEntryId, groupBy, languageId, rangeEnd,
					rangeKey, rangeStart, structureId, tagId, vocabularyId,
					Pagination.of(page, pageSize)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {objectEntryMetric(externalReferenceCode: ___, groupId: ___, rangeKey: ___, selectedMetrics: ___){dataSourceId, defaultMetric, externalReferenceCode, selectedMetrics}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ObjectEntryMetric objectEntryMetric(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("rangeKey") Integer rangeKey,
			@GraphQLName("selectedMetrics") String[] selectedMetrics)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectEntryMetricResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectEntryMetricResource ->
				objectEntryMetricResource.getObjectEntryMetric(
					externalReferenceCode, groupId, rangeKey, selectedMetrics));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {contentOverview(depotEntryId: ___, languageId: ___, rangeEnd: ___, rangeKey: ___, rangeStart: ___){categoriesCount, tagsCount, totalCount, trend, vocabulariesCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Overview contentOverview(
			@GraphQLName("depotEntryId") Long depotEntryId,
			@GraphQLName("languageId") String languageId,
			@GraphQLName("rangeEnd") String rangeEnd,
			@GraphQLName("rangeKey") Integer rangeKey,
			@GraphQLName("rangeStart") String rangeStart)
		throws Exception {

		return _applyComponentServiceObjects(
			_overviewResourceComponentServiceObjects,
			this::_populateResourceContext,
			overviewResource -> overviewResource.getContentOverview(
				depotEntryId, languageId, rangeEnd, rangeKey, rangeStart));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {fileOverview(depotEntryId: ___, languageId: ___, rangeEnd: ___, rangeKey: ___, rangeStart: ___){categoriesCount, tagsCount, totalCount, trend, vocabulariesCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Overview fileOverview(
			@GraphQLName("depotEntryId") Long depotEntryId,
			@GraphQLName("languageId") String languageId,
			@GraphQLName("rangeEnd") String rangeEnd,
			@GraphQLName("rangeKey") Integer rangeKey,
			@GraphQLName("rangeStart") String rangeStart)
		throws Exception {

		return _applyComponentServiceObjects(
			_overviewResourceComponentServiceObjects,
			this::_populateResourceContext,
			overviewResource -> overviewResource.getFileOverview(
				depotEntryId, languageId, rangeEnd, rangeKey, rangeStart));
	}

	@GraphQLName("ConnectionInfoPage")
	public class ConnectionInfoPage {

		public ConnectionInfoPage(Page connectionInfoPage) {
			actions = connectionInfoPage.getActions();

			items = connectionInfoPage.getItems();
			lastPage = connectionInfoPage.getLastPage();
			page = connectionInfoPage.getPage();
			pageSize = connectionInfoPage.getPageSize();
			totalCount = connectionInfoPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<ConnectionInfo> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("InventoryAnalysisPage")
	public class InventoryAnalysisPage {

		public InventoryAnalysisPage(Page inventoryAnalysisPage) {
			actions = inventoryAnalysisPage.getActions();

			items = inventoryAnalysisPage.getItems();
			lastPage = inventoryAnalysisPage.getLastPage();
			page = inventoryAnalysisPage.getPage();
			pageSize = inventoryAnalysisPage.getPageSize();
			totalCount = inventoryAnalysisPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<InventoryAnalysis> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ObjectEntryMetricPage")
	public class ObjectEntryMetricPage {

		public ObjectEntryMetricPage(Page objectEntryMetricPage) {
			actions = objectEntryMetricPage.getActions();

			items = objectEntryMetricPage.getItems();
			lastPage = objectEntryMetricPage.getLastPage();
			page = objectEntryMetricPage.getPage();
			pageSize = objectEntryMetricPage.getPageSize();
			totalCount = objectEntryMetricPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<ObjectEntryMetric> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("OverviewPage")
	public class OverviewPage {

		public OverviewPage(Page overviewPage) {
			actions = overviewPage.getActions();

			items = overviewPage.getItems();
			lastPage = overviewPage.getLastPage();
			page = overviewPage.getPage();
			pageSize = overviewPage.getPageSize();
			totalCount = overviewPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<Overview> items;

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
			ConnectionInfoResource connectionInfoResource)
		throws Exception {

		connectionInfoResource.setContextAcceptLanguage(_acceptLanguage);
		connectionInfoResource.setContextCompany(_company);
		connectionInfoResource.setContextHttpServletRequest(
			_httpServletRequest);
		connectionInfoResource.setContextHttpServletResponse(
			_httpServletResponse);
		connectionInfoResource.setContextUriInfo(_uriInfo);
		connectionInfoResource.setContextUser(_user);
		connectionInfoResource.setGroupLocalService(_groupLocalService);
		connectionInfoResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			InventoryAnalysisResource inventoryAnalysisResource)
		throws Exception {

		inventoryAnalysisResource.setContextAcceptLanguage(_acceptLanguage);
		inventoryAnalysisResource.setContextCompany(_company);
		inventoryAnalysisResource.setContextHttpServletRequest(
			_httpServletRequest);
		inventoryAnalysisResource.setContextHttpServletResponse(
			_httpServletResponse);
		inventoryAnalysisResource.setContextUriInfo(_uriInfo);
		inventoryAnalysisResource.setContextUser(_user);
		inventoryAnalysisResource.setGroupLocalService(_groupLocalService);
		inventoryAnalysisResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			ObjectEntryMetricResource objectEntryMetricResource)
		throws Exception {

		objectEntryMetricResource.setContextAcceptLanguage(_acceptLanguage);
		objectEntryMetricResource.setContextCompany(_company);
		objectEntryMetricResource.setContextHttpServletRequest(
			_httpServletRequest);
		objectEntryMetricResource.setContextHttpServletResponse(
			_httpServletResponse);
		objectEntryMetricResource.setContextUriInfo(_uriInfo);
		objectEntryMetricResource.setContextUser(_user);
		objectEntryMetricResource.setGroupLocalService(_groupLocalService);
		objectEntryMetricResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(OverviewResource overviewResource)
		throws Exception {

		overviewResource.setContextAcceptLanguage(_acceptLanguage);
		overviewResource.setContextCompany(_company);
		overviewResource.setContextHttpServletRequest(_httpServletRequest);
		overviewResource.setContextHttpServletResponse(_httpServletResponse);
		overviewResource.setContextUriInfo(_uriInfo);
		overviewResource.setContextUser(_user);
		overviewResource.setGroupLocalService(_groupLocalService);
		overviewResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<ConnectionInfoResource>
		_connectionInfoResourceComponentServiceObjects;
	private static ComponentServiceObjects<InventoryAnalysisResource>
		_inventoryAnalysisResourceComponentServiceObjects;
	private static ComponentServiceObjects<ObjectEntryMetricResource>
		_objectEntryMetricResourceComponentServiceObjects;
	private static ComponentServiceObjects<OverviewResource>
		_overviewResourceComponentServiceObjects;

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

}