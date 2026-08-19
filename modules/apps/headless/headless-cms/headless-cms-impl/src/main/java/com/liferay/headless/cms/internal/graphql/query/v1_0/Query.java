/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.internal.graphql.query.v1_0;

import com.liferay.headless.cms.dto.v1_0.AssetStatistics;
import com.liferay.headless.cms.dto.v1_0.AssetUsage;
import com.liferay.headless.cms.dto.v1_0.SimilarityCluster;
import com.liferay.headless.cms.resource.v1_0.AssetStatisticsResource;
import com.liferay.headless.cms.resource.v1_0.AssetUsageResource;
import com.liferay.headless.cms.resource.v1_0.SimilarityClusterResource;
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

import jakarta.validation.constraints.NotEmpty;

import jakarta.ws.rs.core.UriInfo;

import java.util.Map;
import java.util.function.BiFunction;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Crescenzo Rega
 * @generated
 */
@Generated("")
public class Query {

	public static void setAssetStatisticsResourceComponentServiceObjects(
		ComponentServiceObjects<AssetStatisticsResource>
			assetStatisticsResourceComponentServiceObjects) {

		_assetStatisticsResourceComponentServiceObjects =
			assetStatisticsResourceComponentServiceObjects;
	}

	public static void setAssetUsageResourceComponentServiceObjects(
		ComponentServiceObjects<AssetUsageResource>
			assetUsageResourceComponentServiceObjects) {

		_assetUsageResourceComponentServiceObjects =
			assetUsageResourceComponentServiceObjects;
	}

	public static void setSimilarityClusterResourceComponentServiceObjects(
		ComponentServiceObjects<SimilarityClusterResource>
			similarityClusterResourceComponentServiceObjects) {

		_similarityClusterResourceComponentServiceObjects =
			similarityClusterResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {assetStatistics(assetLibraryId: ___){approvedCount, brokenLinksCount, expiredCount, expiringSoonCount, inDraftCount, pendingCount, reviewDateOverdueCount, scheduledCount, totalCount, upcomingReviewCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public AssetStatistics assetStatistics(
			@GraphQLName("assetLibraryId") @NotEmpty String assetLibraryId)
		throws Exception {

		return _applyComponentServiceObjects(
			_assetStatisticsResourceComponentServiceObjects,
			this::_populateResourceContext,
			assetStatisticsResource ->
				assetStatisticsResource.getAssetStatistics(
					Long.valueOf(assetLibraryId)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {assetUsagesAsset(assetId: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public AssetUsagePage assetUsagesAsset(
			@GraphQLName("assetId") Long assetId,
			@GraphQLName("search") String search,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_assetUsageResourceComponentServiceObjects,
			this::_populateResourceContext,
			assetUsageResource -> new AssetUsagePage(
				assetUsageResource.getAssetUsagesAssetPage(
					assetId, search, Pagination.of(page, pageSize),
					_sortsBiFunction.apply(assetUsageResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {similarityClusters(assetLibraryId: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "List the clusters of CMS content whose main text overlaps significantly, paginated by asset. Content is compared within one language, so a translation is only ever compared against the same translation of other content, and clustering always spans the whole space, so a cluster's size never depends on the requested page. Omit assetLibraryId to span all accessible spaces. Note that totalCount counts the clustered assets while the items are the clusters that hold them, so it is the number of assets that have a near duplicate rather than a page count, and a cluster that straddles a page boundary is returned on both pages with its full size."
	)
	public SimilarityClusterPage similarityClusters(
			@GraphQLName("assetLibraryId") @NotEmpty String assetLibraryId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_similarityClusterResourceComponentServiceObjects,
			this::_populateResourceContext,
			similarityClusterResource -> new SimilarityClusterPage(
				similarityClusterResource.getSimilarityClustersPage(
					Long.valueOf(assetLibraryId),
					Pagination.of(page, pageSize))));
	}

	@GraphQLName("AssetStatisticsPage")
	public class AssetStatisticsPage {

		public AssetStatisticsPage(Page assetStatisticsPage) {
			actions = assetStatisticsPage.getActions();

			items = assetStatisticsPage.getItems();
			lastPage = assetStatisticsPage.getLastPage();
			page = assetStatisticsPage.getPage();
			pageSize = assetStatisticsPage.getPageSize();
			totalCount = assetStatisticsPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<AssetStatistics> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("AssetUsagePage")
	public class AssetUsagePage {

		public AssetUsagePage(Page assetUsagePage) {
			actions = assetUsagePage.getActions();

			items = assetUsagePage.getItems();
			lastPage = assetUsagePage.getLastPage();
			page = assetUsagePage.getPage();
			pageSize = assetUsagePage.getPageSize();
			totalCount = assetUsagePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<AssetUsage> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("SimilarityClusterPage")
	public class SimilarityClusterPage {

		public SimilarityClusterPage(Page similarityClusterPage) {
			actions = similarityClusterPage.getActions();

			items = similarityClusterPage.getItems();
			lastPage = similarityClusterPage.getLastPage();
			page = similarityClusterPage.getPage();
			pageSize = similarityClusterPage.getPageSize();
			totalCount = similarityClusterPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<SimilarityCluster> items;

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
			AssetStatisticsResource assetStatisticsResource)
		throws Exception {

		assetStatisticsResource.setContextAcceptLanguage(_acceptLanguage);
		assetStatisticsResource.setContextCompany(_company);
		assetStatisticsResource.setContextHttpServletRequest(
			_httpServletRequest);
		assetStatisticsResource.setContextHttpServletResponse(
			_httpServletResponse);
		assetStatisticsResource.setContextUriInfo(_uriInfo);
		assetStatisticsResource.setContextUser(_user);
		assetStatisticsResource.setGroupLocalService(_groupLocalService);
		assetStatisticsResource.setResourceActionLocalService(
			_resourceActionLocalService);
		assetStatisticsResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		assetStatisticsResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(AssetUsageResource assetUsageResource)
		throws Exception {

		assetUsageResource.setContextAcceptLanguage(_acceptLanguage);
		assetUsageResource.setContextCompany(_company);
		assetUsageResource.setContextHttpServletRequest(_httpServletRequest);
		assetUsageResource.setContextHttpServletResponse(_httpServletResponse);
		assetUsageResource.setContextUriInfo(_uriInfo);
		assetUsageResource.setContextUser(_user);
		assetUsageResource.setGroupLocalService(_groupLocalService);
		assetUsageResource.setResourceActionLocalService(
			_resourceActionLocalService);
		assetUsageResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		assetUsageResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			SimilarityClusterResource similarityClusterResource)
		throws Exception {

		similarityClusterResource.setContextAcceptLanguage(_acceptLanguage);
		similarityClusterResource.setContextCompany(_company);
		similarityClusterResource.setContextHttpServletRequest(
			_httpServletRequest);
		similarityClusterResource.setContextHttpServletResponse(
			_httpServletResponse);
		similarityClusterResource.setContextUriInfo(_uriInfo);
		similarityClusterResource.setContextUser(_user);
		similarityClusterResource.setGroupLocalService(_groupLocalService);
		similarityClusterResource.setResourceActionLocalService(
			_resourceActionLocalService);
		similarityClusterResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		similarityClusterResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<AssetStatisticsResource>
		_assetStatisticsResourceComponentServiceObjects;
	private static ComponentServiceObjects<AssetUsageResource>
		_assetUsageResourceComponentServiceObjects;
	private static ComponentServiceObjects<SimilarityClusterResource>
		_similarityClusterResourceComponentServiceObjects;

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
// LIFERAY-REST-BUILDER-HASH:32974760