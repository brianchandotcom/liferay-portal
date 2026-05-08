/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.graphql.query.v1_0;

import com.liferay.osb.faro.rest.dto.v1_0.AssetSummaryMetric;
import com.liferay.osb.faro.rest.dto.v1_0.Event;
import com.liferay.osb.faro.rest.dto.v1_0.PageMetric;
import com.liferay.osb.faro.rest.resource.v1_0.AssetSummaryMetricResource;
import com.liferay.osb.faro.rest.resource.v1_0.EventResource;
import com.liferay.osb.faro.rest.resource.v1_0.PageMetricResource;
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
 * @author Leslie Wong
 * @generated
 */
@Generated("")
public class Query {

	public static void setAssetSummaryMetricResourceComponentServiceObjects(
		ComponentServiceObjects<AssetSummaryMetricResource>
			assetSummaryMetricResourceComponentServiceObjects) {

		_assetSummaryMetricResourceComponentServiceObjects =
			assetSummaryMetricResourceComponentServiceObjects;
	}

	public static void setEventResourceComponentServiceObjects(
		ComponentServiceObjects<EventResource>
			eventResourceComponentServiceObjects) {

		_eventResourceComponentServiceObjects =
			eventResourceComponentServiceObjects;
	}

	public static void setPageMetricResourceComponentServiceObjects(
		ComponentServiceObjects<PageMetricResource>
			pageMetricResourceComponentServiceObjects) {

		_pageMetricResourceComponentServiceObjects =
			pageMetricResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workspaceGroupAssetSummaries(channelId: ___, groupId: ___, page: ___, pageSize: ___, rangeEnd: ___, rangeKey: ___, rangeStart: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "List analytics asset summaries for pages, blogs, documents, forms, journal articles, and object entries. Rank summaries by the requested sort metric. Each summary includes download, impression, read, and view counts along with their period-over-period trend percentages. Optionally narrow results to a single channel (also known as property) or to a date range. Use this to answer 'what content is performing best' and to pick assets for deeper drill-down via `getSitePagesPage`."
	)
	public AssetSummaryMetricPage workspaceGroupAssetSummaries(
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("channelId") String channelId,
			@GraphQLName("rangeEnd") String rangeEnd,
			@GraphQLName("rangeKey") Integer rangeKey,
			@GraphQLName("rangeStart") String rangeStart,
			@GraphQLName("search") String search,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_assetSummaryMetricResourceComponentServiceObjects,
			this::_populateResourceContext,
			assetSummaryMetricResource -> new AssetSummaryMetricPage(
				assetSummaryMetricResource.getWorkspaceGroupAssetSummariesPage(
					groupId, channelId, rangeEnd, rangeKey, rangeStart, search,
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						assetSummaryMetricResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workspaceGroupChannelEvents(channelId: ___, groupId: ___, includeAnonymousUsers: ___, page: ___, pageSize: ___, rangeEnd: ___, rangeKey: ___, rangeStart: ___, search: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "List tracked analytics events for a specific channel (also known as property), optionally narrowed to a date range. For aggregated metrics across events, prefer `getSiteAssetSummariesPage` or `getSitePagesPage`."
	)
	public EventPage workspaceGroupChannelEvents(
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("channelId") String channelId,
			@GraphQLName("includeAnonymousUsers") Boolean includeAnonymousUsers,
			@GraphQLName("rangeEnd") String rangeEnd,
			@GraphQLName("rangeKey") Integer rangeKey,
			@GraphQLName("rangeStart") String rangeStart,
			@GraphQLName("search") String search,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_eventResourceComponentServiceObjects,
			this::_populateResourceContext,
			eventResource -> new EventPage(
				eventResource.getWorkspaceGroupChannelEventsPage(
					groupId, channelId, includeAnonymousUsers, rangeEnd,
					rangeKey, rangeStart, search,
					Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workspaceGroupPages(channelId: ___, dataSourceId: ___, groupId: ___, page: ___, pageSize: ___, rangeEnd: ___, rangeKey: ___, rangeStart: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "List analytics metrics for tracked pages on the workspace, ranked by views or another metric, optionally narrowed to a single channel (also known as property) or data source. Returns flattened view, visitor, bounce, exit, and access-path metrics for each page. Use this for 'top pages' style queries."
	)
	public PageMetricPage workspaceGroupPages(
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("channelId") String channelId,
			@GraphQLName("dataSourceId") String dataSourceId,
			@GraphQLName("rangeEnd") String rangeEnd,
			@GraphQLName("rangeKey") Integer rangeKey,
			@GraphQLName("rangeStart") String rangeStart,
			@GraphQLName("search") String search,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageMetricResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageMetricResource -> new PageMetricPage(
				pageMetricResource.getWorkspaceGroupPagesPage(
					groupId, channelId, dataSourceId, rangeEnd, rangeKey,
					rangeStart, search, Pagination.of(page, pageSize),
					_sortsBiFunction.apply(pageMetricResource, sortsString))));
	}

	@GraphQLName("AssetSummaryMetricPage")
	public class AssetSummaryMetricPage {

		public AssetSummaryMetricPage(Page assetSummaryMetricPage) {
			actions = assetSummaryMetricPage.getActions();

			items = assetSummaryMetricPage.getItems();
			lastPage = assetSummaryMetricPage.getLastPage();
			page = assetSummaryMetricPage.getPage();
			pageSize = assetSummaryMetricPage.getPageSize();
			totalCount = assetSummaryMetricPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<AssetSummaryMetric> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("EventPage")
	public class EventPage {

		public EventPage(Page eventPage) {
			actions = eventPage.getActions();

			items = eventPage.getItems();
			lastPage = eventPage.getLastPage();
			page = eventPage.getPage();
			pageSize = eventPage.getPageSize();
			totalCount = eventPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<Event> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("PageMetricPage")
	public class PageMetricPage {

		public PageMetricPage(Page pageMetricPage) {
			actions = pageMetricPage.getActions();

			items = pageMetricPage.getItems();
			lastPage = pageMetricPage.getLastPage();
			page = pageMetricPage.getPage();
			pageSize = pageMetricPage.getPageSize();
			totalCount = pageMetricPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<PageMetric> items;

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
			AssetSummaryMetricResource assetSummaryMetricResource)
		throws Exception {

		assetSummaryMetricResource.setContextAcceptLanguage(_acceptLanguage);
		assetSummaryMetricResource.setContextCompany(_company);
		assetSummaryMetricResource.setContextHttpServletRequest(
			_httpServletRequest);
		assetSummaryMetricResource.setContextHttpServletResponse(
			_httpServletResponse);
		assetSummaryMetricResource.setContextUriInfo(_uriInfo);
		assetSummaryMetricResource.setContextUser(_user);
		assetSummaryMetricResource.setGroupLocalService(_groupLocalService);
		assetSummaryMetricResource.setResourceActionLocalService(
			_resourceActionLocalService);
		assetSummaryMetricResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		assetSummaryMetricResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(EventResource eventResource)
		throws Exception {

		eventResource.setContextAcceptLanguage(_acceptLanguage);
		eventResource.setContextCompany(_company);
		eventResource.setContextHttpServletRequest(_httpServletRequest);
		eventResource.setContextHttpServletResponse(_httpServletResponse);
		eventResource.setContextUriInfo(_uriInfo);
		eventResource.setContextUser(_user);
		eventResource.setGroupLocalService(_groupLocalService);
		eventResource.setResourceActionLocalService(
			_resourceActionLocalService);
		eventResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		eventResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(PageMetricResource pageMetricResource)
		throws Exception {

		pageMetricResource.setContextAcceptLanguage(_acceptLanguage);
		pageMetricResource.setContextCompany(_company);
		pageMetricResource.setContextHttpServletRequest(_httpServletRequest);
		pageMetricResource.setContextHttpServletResponse(_httpServletResponse);
		pageMetricResource.setContextUriInfo(_uriInfo);
		pageMetricResource.setContextUser(_user);
		pageMetricResource.setGroupLocalService(_groupLocalService);
		pageMetricResource.setResourceActionLocalService(
			_resourceActionLocalService);
		pageMetricResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		pageMetricResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<AssetSummaryMetricResource>
		_assetSummaryMetricResourceComponentServiceObjects;
	private static ComponentServiceObjects<EventResource>
		_eventResourceComponentServiceObjects;
	private static ComponentServiceObjects<PageMetricResource>
		_pageMetricResourceComponentServiceObjects;

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
// LIFERAY-REST-BUILDER-HASH:-848832030