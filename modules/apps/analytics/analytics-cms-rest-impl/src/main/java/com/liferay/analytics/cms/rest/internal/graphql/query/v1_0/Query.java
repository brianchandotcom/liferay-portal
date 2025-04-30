/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.cms.rest.internal.graphql.query.v1_0;

import com.liferay.analytics.cms.rest.dto.v1_0.OverviewContent;
import com.liferay.analytics.cms.rest.resource.v1_0.OverviewContentResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
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
 * @author Rachael Koestartyo
 * @generated
 */
@Generated("")
public class Query {

	public static void setOverviewContentResourceComponentServiceObjects(
		ComponentServiceObjects<OverviewContentResource>
			overviewContentResourceComponentServiceObjects) {

		_overviewContentResourceComponentServiceObjects =
			overviewContentResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {overviewContent(languageId: ___, rangeKey: ___, spaceId: ___){categoriesCount, tagsCount, totalCount, trend, vocabulariesCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OverviewContent overviewContent(
			@GraphQLName("languageId") String languageId,
			@GraphQLName("rangeKey") Integer rangeKey,
			@GraphQLName("spaceId") Integer spaceId)
		throws Exception {

		return _applyComponentServiceObjects(
			_overviewContentResourceComponentServiceObjects,
			this::_populateResourceContext,
			overviewContentResource ->
				overviewContentResource.getOverviewContent(
					languageId, rangeKey, spaceId));
	}

	@GraphQLName("OverviewContentPage")
	public class OverviewContentPage {

		public OverviewContentPage(Page overviewContentPage) {
			actions = overviewContentPage.getActions();

			items = overviewContentPage.getItems();
			lastPage = overviewContentPage.getLastPage();
			page = overviewContentPage.getPage();
			pageSize = overviewContentPage.getPageSize();
			totalCount = overviewContentPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<OverviewContent> items;

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
			OverviewContentResource overviewContentResource)
		throws Exception {

		overviewContentResource.setContextAcceptLanguage(_acceptLanguage);
		overviewContentResource.setContextCompany(_company);
		overviewContentResource.setContextHttpServletRequest(
			_httpServletRequest);
		overviewContentResource.setContextHttpServletResponse(
			_httpServletResponse);
		overviewContentResource.setContextUriInfo(_uriInfo);
		overviewContentResource.setContextUser(_user);
		overviewContentResource.setGroupLocalService(_groupLocalService);
		overviewContentResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<OverviewContentResource>
		_overviewContentResourceComponentServiceObjects;

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