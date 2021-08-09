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

import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.ProcessVersion;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.ProcessVersionResource;
import com.liferay.portal.workflow.metrics.search.index.name.WorkflowMetricsIndexNameBuilder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Feliphe Marinho
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/process-version.properties",
	scope = ServiceScope.PROTOTYPE, service = ProcessVersionResource.class
)
public class ProcessVersionResourceImpl extends BaseProcessVersionResourceImpl {

	@Override
	public Page<ProcessVersion> getProcessProcessVersionsPage(Long processId)
		throws Exception {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(
			_processWorkflowMetricsIndexNameBuilder.getIndexName(
				contextCompany.getCompanyId()));

		BooleanQuery booleanQuery = _queries.booleanQuery();

		BooleanQuery filterBooleanQuery = _queries.booleanQuery();

		searchSearchRequest.setQuery(
			booleanQuery.addFilterQueryClauses(
				filterBooleanQuery.addMustQueryClauses(
					_queries.term("companyId", contextCompany.getCompanyId()),
					_queries.term("deleted", false),
					_queries.term("processId", processId))));

		searchSearchRequest.setSelectedFieldNames("versions");
		searchSearchRequest.setSize(1);

		return Page.of(
			Stream.of(
				_searchRequestExecutor.executeSearchRequest(searchSearchRequest)
			).map(
				SearchSearchResponse::getSearchHits
			).map(
				SearchHits::getSearchHits
			).flatMap(
				List::stream
			).map(
				SearchHit::getDocument
			).map(
				document -> document.getStrings("versions")
			).flatMap(
				List::stream
			).map(
				version -> new ProcessVersion() {
					{
						name = version;
					}
				}
			).collect(
				Collectors.toList()
			));
	}

	@Reference(target = "(workflow.metrics.index.entity.name=process)")
	private WorkflowMetricsIndexNameBuilder
		_processWorkflowMetricsIndexNameBuilder;

	@Reference
	private Queries _queries;

	@Reference
	private SearchRequestExecutor _searchRequestExecutor;

}