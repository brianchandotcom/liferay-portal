/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.rest.internal.graphql.query.v1_0;

import com.liferay.analytics.reports.rest.dto.v1_0.AssetMetric;
import com.liferay.analytics.reports.rest.resource.v1_0.AssetMetricResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.Map;
import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Marcos Martins
 * @generated
 */
@Generated("")
public class Query {

	public static void setAssetMetricResourceComponentServiceObjects(
		ComponentServiceObjects<AssetMetricResource>
			assetMetricResourceComponentServiceObjects) {

		_assetMetricResourceComponentServiceObjects =
			assetMetricResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {groupAssetMetric(assetId: ___, assetType: ___, groupId: ___, identityType: ___, rangeKey: ___, selectedMetrics: ___){assetId, assetTitle, assetType, dataSourceId, defaultMetric, selectedMetrics}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public AssetMetric groupAssetMetric(
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("assetType") String assetType,
			@GraphQLName("assetId") String assetId,
			@GraphQLName("identityType") String identityType,
			@GraphQLName("rangeKey") Integer rangeKey,
			@GraphQLName("selectedMetrics") String[] selectedMetrics)
		throws Exception {

		return _applyComponentServiceObjects(
			_assetMetricResourceComponentServiceObjects,
			this::_populateResourceContext,
			assetMetricResource -> assetMetricResource.getGroupAssetMetric(
				groupId, assetType, assetId, identityType, rangeKey,
				selectedMetrics));
	}

	@GraphQLName("AssetMetricPage")
	public class AssetMetricPage {

		public AssetMetricPage(Page assetMetricPage) {
			actions = assetMetricPage.getActions();

			items = assetMetricPage.getItems();
			lastPage = assetMetricPage.getLastPage();
			page = assetMetricPage.getPage();
			pageSize = assetMetricPage.getPageSize();
			totalCount = assetMetricPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<AssetMetric> items;

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
			AssetMetricResource assetMetricResource)
		throws Exception {

		assetMetricResource.setContextAcceptLanguage(_acceptLanguage);
		assetMetricResource.setContextCompany(_company);
		assetMetricResource.setContextHttpServletRequest(_httpServletRequest);
		assetMetricResource.setContextHttpServletResponse(_httpServletResponse);
		assetMetricResource.setContextUriInfo(_uriInfo);
		assetMetricResource.setContextUser(_user);
		assetMetricResource.setGroupLocalService(_groupLocalService);
		assetMetricResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<AssetMetricResource>
		_assetMetricResourceComponentServiceObjects;

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