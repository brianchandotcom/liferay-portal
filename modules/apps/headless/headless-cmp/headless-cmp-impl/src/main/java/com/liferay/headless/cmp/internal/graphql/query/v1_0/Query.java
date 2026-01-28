/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cmp.internal.graphql.query.v1_0;

import com.liferay.headless.cmp.dto.v1_0.TaskStatistics;
import com.liferay.headless.cmp.resource.v1_0.TaskStatisticsResource;
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

import jakarta.annotation.Generated;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.ws.rs.core.UriInfo;

import java.util.Map;
import java.util.function.BiFunction;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Carolina Barbosa
 * @generated
 */
@Generated("")
public class Query {

	public static void setTaskStatisticsResourceComponentServiceObjects(
		ComponentServiceObjects<TaskStatisticsResource>
			taskStatisticsResourceComponentServiceObjects) {

		_taskStatisticsResourceComponentServiceObjects =
			taskStatisticsResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {projectTaskStatistics(filter: ___, projectId: ___){blockedCount, inProgressCount, overdueCount, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public TaskStatistics projectTaskStatistics(
			@GraphQLName("projectId") Long projectId,
			@GraphQLName("filter") String filterString)
		throws Exception {

		return _applyComponentServiceObjects(
			_taskStatisticsResourceComponentServiceObjects,
			this::_populateResourceContext,
			taskStatisticsResource ->
				taskStatisticsResource.getProjectTaskStatistics(
					projectId,
					_filterBiFunction.apply(
						taskStatisticsResource, filterString)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {taskStatistics(filter: ___){blockedCount, inProgressCount, overdueCount, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public TaskStatistics taskStatistics(
			@GraphQLName("filter") String filterString)
		throws Exception {

		return _applyComponentServiceObjects(
			_taskStatisticsResourceComponentServiceObjects,
			this::_populateResourceContext,
			taskStatisticsResource -> taskStatisticsResource.getTaskStatistics(
				_filterBiFunction.apply(taskStatisticsResource, filterString)));
	}

	@GraphQLName("TaskStatisticsPage")
	public class TaskStatisticsPage {

		public TaskStatisticsPage(Page taskStatisticsPage) {
			actions = taskStatisticsPage.getActions();

			items = taskStatisticsPage.getItems();
			lastPage = taskStatisticsPage.getLastPage();
			page = taskStatisticsPage.getPage();
			pageSize = taskStatisticsPage.getPageSize();
			totalCount = taskStatisticsPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<TaskStatistics> items;

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
			TaskStatisticsResource taskStatisticsResource)
		throws Exception {

		taskStatisticsResource.setContextAcceptLanguage(_acceptLanguage);
		taskStatisticsResource.setContextCompany(_company);
		taskStatisticsResource.setContextHttpServletRequest(
			_httpServletRequest);
		taskStatisticsResource.setContextHttpServletResponse(
			_httpServletResponse);
		taskStatisticsResource.setContextUriInfo(_uriInfo);
		taskStatisticsResource.setContextUser(_user);
		taskStatisticsResource.setGroupLocalService(_groupLocalService);
		taskStatisticsResource.setResourceActionLocalService(
			_resourceActionLocalService);
		taskStatisticsResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		taskStatisticsResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<TaskStatisticsResource>
		_taskStatisticsResourceComponentServiceObjects;

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