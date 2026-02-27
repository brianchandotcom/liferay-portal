/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
import com.liferay.portal.search.aggregation.bucket.TermsAggregationResult;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.QueriesUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Role;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.RoleResource;
import com.liferay.portal.workflow.metrics.search.index.constants.WorkflowMetricsIndexNameConstants;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rafael Praxedes
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/role.properties",
	scope = ServiceScope.PROTOTYPE, service = RoleResource.class
)
public class RoleResourceImpl extends BaseRoleResourceImpl {

	@Override
	public Page<Role> getProcessRolesPage(Long processId, Boolean completed)
		throws Exception {

		return Page.of(_getRoles(GetterUtil.getBoolean(completed), processId));
	}

	private BooleanQuery _createTasksBooleanQuery(
		boolean completed, long processId) {

		BooleanQuery booleanQuery = QueriesUtil.booleanQuery();

		booleanQuery.addMustNotQueryClauses(QueriesUtil.term("taskId", "0"));

		return booleanQuery.addMustQueryClauses(
			QueriesUtil.term("assigneeType", User.class.getName()),
			QueriesUtil.term("companyId", contextCompany.getCompanyId()),
			QueriesUtil.term("completed", completed),
			QueriesUtil.term("deleted", Boolean.FALSE),
			QueriesUtil.term("instanceCompleted", completed),
			QueriesUtil.term("processId", processId));
	}

	private Set<Long> _getAssigneeIds(boolean completed, long processId) {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms(
			"assigneeId", "assigneeIds");

		termsAggregation.setSize(10000);

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames(
			_indexNameBuilder.getIndexName(contextCompany.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_TASK);
		searchSearchRequest.setQuery(
			_createTasksBooleanQuery(completed, processId));

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(searchSearchRequest);

		Map<String, AggregationResult> aggregationResultsMap =
			searchSearchResponse.getAggregationResultsMap();

		TermsAggregationResult termsAggregationResult =
			(TermsAggregationResult)aggregationResultsMap.get("assigneeId");

		return new HashSet<>(
			transform(
				termsAggregationResult.getBuckets(),
				bucket -> GetterUtil.getLong(bucket.getKey())));
	}

	private Set<Role> _getRoles(boolean completed, Long processId)
		throws Exception {

		Set<Long> assigneeIds = _getAssigneeIds(completed, processId);

		if (assigneeIds.isEmpty()) {
			return Collections.emptySet();
		}

		Set<Role> roles = new TreeSet<>(
			Comparator.comparing(Role::getName, String::compareToIgnoreCase));

		ActionableDynamicQuery actionableDynamicQuery =
			_userLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property userIdProperty = PropertyFactoryUtil.forName("userId");

				dynamicQuery.add(userIdProperty.in(assigneeIds));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(User user) -> roles.addAll(
				transform(user.getRoles(), this::_toRole)));

		actionableDynamicQuery.performActions();

		return roles;
	}

	private Role _toRole(com.liferay.portal.kernel.model.Role role) {
		return new Role() {
			{
				setId(role::getRoleId);
				setName(
					() -> role.getTitle(
						contextAcceptLanguage.getPreferredLocale()));
			}
		};
	}

	@Reference
	private Aggregations _aggregations;

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

	@Reference
	private UserLocalService _userLocalService;

}