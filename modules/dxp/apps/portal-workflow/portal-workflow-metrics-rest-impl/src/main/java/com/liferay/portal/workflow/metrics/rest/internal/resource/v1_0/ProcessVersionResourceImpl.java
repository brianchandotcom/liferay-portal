/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0;

import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.QueriesUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.ProcessVersion;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.ProcessVersionResource;
import com.liferay.portal.workflow.metrics.search.index.constants.WorkflowMetricsIndexNameConstants;

import java.util.ArrayList;
import java.util.List;

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
			_indexNameBuilder.getIndexName(contextCompany.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_PROCESS);

		BooleanQuery booleanQuery = QueriesUtil.booleanQuery();

		BooleanQuery filterBooleanQuery = QueriesUtil.booleanQuery();

		searchSearchRequest.setQuery(
			booleanQuery.addFilterQueryClauses(
				filterBooleanQuery.addMustQueryClauses(
					QueriesUtil.term(
						"companyId", contextCompany.getCompanyId()),
					QueriesUtil.term("deleted", false),
					QueriesUtil.term("processId", processId))));

		searchSearchRequest.setSelectedFieldNames("versions");
		searchSearchRequest.setSize(1);

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(searchSearchRequest);

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		List<ProcessVersion> processVersions = new ArrayList<>();

		for (SearchHit searchHit : searchHits.getSearchHits()) {
			Document document = searchHit.getDocument();

			processVersions.addAll(
				transform(
					document.getStrings("versions"),
					version -> new ProcessVersion() {
						{
							setName(() -> version);
						}
					}));
		}

		return Page.of(processVersions);
	}

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

}