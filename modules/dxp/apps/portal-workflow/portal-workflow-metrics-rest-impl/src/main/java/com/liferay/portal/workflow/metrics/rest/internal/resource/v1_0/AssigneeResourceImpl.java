/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
import com.liferay.portal.search.aggregation.bucket.TermsAggregationResult;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Assignee;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.AssigneeBulkSelection;
import com.liferay.portal.workflow.metrics.rest.internal.dto.v1_0.util.AssigneeUtil;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.AssigneeResource;
import com.liferay.portal.workflow.metrics.search.index.name.WorkflowMetricsIndexNameBuilder;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rafael Praxedes
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/assignee.properties",
	scope = ServiceScope.PROTOTYPE, service = AssigneeResource.class
)
public class AssigneeResourceImpl extends BaseAssigneeResourceImpl {

	@Override
	public Page<Assignee> postProcessAssigneesPage(
			Long processId, AssigneeBulkSelection assigneeBulkSelection)
		throws Exception {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms(
			"assigneeId", "assigneeIds");

		termsAggregation.setSize(10000);

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames(
			_taskWorkflowMetricsIndexNameBuilder.getIndexName(
				contextCompany.getCompanyId()));
		searchSearchRequest.setQuery(
			_createTasksBooleanQuery(
				assigneeBulkSelection.getInstanceIds(), processId));

		return Page.of(
			Stream.of(
				_searchRequestExecutor.executeSearchRequest(searchSearchRequest)
			).map(
				SearchSearchResponse::getAggregationResultsMap
			).map(
				aggregationResultsMap ->
					(TermsAggregationResult)aggregationResultsMap.get(
						"assigneeId")
			).map(
				TermsAggregationResult::getBuckets
			).flatMap(
				Collection::stream
			).map(
				bucket -> AssigneeUtil.toAssignee(
					_language, _portal,
					ResourceBundleUtil.getModuleAndPortalResourceBundle(
						contextAcceptLanguage.getPreferredLocale(),
						AssigneeResourceImpl.class),
					GetterUtil.getLong(bucket.getKey()),
					_userLocalService::fetchUser)
			).filter(
				Objects::nonNull
			).sorted(
				Comparator.comparing(
					Assignee::getName, String::compareToIgnoreCase)
			).collect(
				Collectors.toList()
			));
	}

	private BooleanQuery _createTasksBooleanQuery(
		Long[] instanceIds, long processId) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		if (ArrayUtil.isNotEmpty(instanceIds)) {
			TermsQuery termsQuery = _queries.terms("instanceId");

			termsQuery.addValues(
				Stream.of(
					instanceIds
				).filter(
					instanceId -> instanceId > 0
				).map(
					String::valueOf
				).toArray(
					Object[]::new
				));

			booleanQuery.addMustQueryClauses(termsQuery);
		}

		booleanQuery.addMustNotQueryClauses(_queries.term("taskId", "0"));

		return booleanQuery.addMustQueryClauses(
			_queries.term("assigneeType", User.class.getName()),
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("deleted", Boolean.FALSE),
			_queries.term("processId", processId));
	}

	@Reference
	private Aggregations _aggregations;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private Queries _queries;

	@Reference
	private SearchRequestExecutor _searchRequestExecutor;

	@Reference(target = "(workflow.metrics.index.entity.name=task)")
	private WorkflowMetricsIndexNameBuilder
		_taskWorkflowMetricsIndexNameBuilder;

	@Reference
	private UserLocalService _userLocalService;

}