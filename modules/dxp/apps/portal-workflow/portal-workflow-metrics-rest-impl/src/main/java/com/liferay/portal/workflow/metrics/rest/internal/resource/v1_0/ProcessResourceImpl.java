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

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.GroupBy;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.engine.adapter.search.CountSearchRequest;
import com.liferay.portal.search.engine.adapter.search.CountSearchResponse;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.facet.custom.CustomFacetFactory;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.ProcessResource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rafael Praxedes
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/process.properties",
	scope = ServiceScope.PROTOTYPE, service = ProcessResource.class
)
public class ProcessResourceImpl extends BaseProcessResourceImpl {

	@Override
	public Page<Process> getProcessesPage(String title, Pagination pagination)
		throws Exception {

		return Page.of(
			_getProcesses(title, pagination), pagination,
			_getProcessesCount(title));
	}

	private Facet _createFacet(String fieldName) {
		Facet facet = _customFacetFactory.newInstance(null);

		facet.setFieldName(fieldName);

		return facet;
	}

	private BooleanFilter _createProcessBooleanFilter() {
		return new BooleanFilter() {
			{
				addRequiredTerm("active", true);
				addRequiredTerm("companyId", contextCompany.getCompanyId());
				addRequiredTerm("deleted", false);
			}
		};
	}

	private int _getInstanceCount(String name) {
		BooleanFilter booleanFilter = new BooleanFilter() {
			{
				addRequiredTerm("companyId", contextCompany.getCompanyId());
				addRequiredTerm("completed", false);
				addRequiredTerm("deleted", false);

				TermsFilter termsFilter = new TermsFilter("processId");

				for (long processId : _getProcessIds(name)) {
					termsFilter.addValue(String.valueOf(processId));
				}

				add(termsFilter, BooleanClauseOccur.MUST);
			}
		};

		CountSearchResponse countSearchResponse =
			_searchRequestExecutor.executeSearchRequest(
				new CountSearchRequest() {
					{
						setIndexNames("workflow-metrics-instances");
						setQuery(
							new BooleanQueryImpl() {
								{
									setPreBooleanFilter(booleanFilter);
								}
							});
					}
				});

		return (int)countSearchResponse.getCount();
	}

	private Collection<Process> _getProcesses(
			String title, Pagination pagination)
		throws Exception {

		List<Process> processes = new ArrayList<>();

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		Sort[] sorts = {new Sort(Field.getSortableFieldName("date"), true)};

		searchSearchRequest.setGroupBy(
			new GroupBy("name") {
				{
					setSize(1);
					setSorts(sorts);
				}
			});

		searchSearchRequest.setIndexNames("workflow-metrics-processes");
		searchSearchRequest.setQuery(
			new BooleanQueryImpl() {
				{
					if (Validator.isNotNull(title)) {
						addTerm("title", title);
					}

					setPreBooleanFilter(_createProcessBooleanFilter());
				}
			});
		searchSearchRequest.setSize(pagination.getItemsPerPage());
		searchSearchRequest.setSorts(sorts);
		searchSearchRequest.setStart(pagination.getStartPosition());
		searchSearchRequest.setStats(Collections.emptyMap());

		SearchSearchResponse searchSearchResponse =
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		Hits searchSearchResponseHits = searchSearchResponse.getHits();

		Map<String, Hits> hitsMap = searchSearchResponseHits.getGroupedHits();

		List<Hits> hitsList = ListUtil.subList(
			new ArrayList<>(hitsMap.values()), pagination.getStartPosition(),
			pagination.getStartPosition() + pagination.getItemsPerPage());

		for (Hits hits : hitsList) {
			Document[] documents = hits.getDocs();

			Document document = documents[0];

			Process process = _toProcess(document);

			processes.add(process);
		}

		return processes;
	}

	private int _getProcessesCount(String title) throws Exception {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		Facet facet = _createFacet("name");

		searchSearchRequest.putFacet(facet.getFieldName(), facet);

		searchSearchRequest.setIndexNames("workflow-metrics-processes");
		searchSearchRequest.setQuery(
			new BooleanQueryImpl() {
				{
					if (Validator.isNotNull(title)) {
						addTerm("title", title);
					}

					setPreBooleanFilter(_createProcessBooleanFilter());
				}
			});
		searchSearchRequest.setStats(Collections.emptyMap());

		_searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		FacetCollector facetCollector = facet.getFacetCollector();

		List<TermCollector> termCollectors = facetCollector.getTermCollectors();

		return termCollectors.size();
	}

	private long[] _getProcessIds(String name) {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		Facet facet = _createFacet("processId");

		searchSearchRequest.putFacet(facet.getFieldName(), facet);

		searchSearchRequest.setIndexNames("workflow-metrics-processes");

		BooleanFilter booleanFilter = _createProcessBooleanFilter();

		booleanFilter.addRequiredTerm("name", name);

		searchSearchRequest.setQuery(
			new BooleanQueryImpl() {
				{
					setPreBooleanFilter(booleanFilter);
				}
			});

		searchSearchRequest.setStats(Collections.emptyMap());

		_searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		FacetCollector facetCollector = facet.getFacetCollector();

		List<TermCollector> termCollectors = facetCollector.getTermCollectors();

		long[] processIds = new long[termCollectors.size()];

		for (int i = 0; i < termCollectors.size(); i++) {
			TermCollector termCollector = termCollectors.get(i);

			long processId = GetterUtil.getLong(termCollector.getTerm());

			processIds[i] = processId;
		}

		return processIds;
	}

	private Process _toProcess(Document document) {
		return new Process() {
			{
				instanceCount = _getInstanceCount(document.get("name"));
				title = document.get("title");
			}
		};
	}

	@Reference
	private CustomFacetFactory _customFacetFactory;

	@Reference
	private SearchRequestExecutor _searchRequestExecutor;

}