/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.workflow.metrics.model.AddProcessRequest;
import com.liferay.portal.workflow.metrics.model.DeleteProcessRequest;
import com.liferay.portal.workflow.metrics.model.UpdateProcessRequest;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.internal.dto.v1_0.util.ProcessUtil;
import com.liferay.portal.workflow.metrics.rest.internal.resource.exception.NoSuchProcessException;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.ProcessResource;
import com.liferay.portal.workflow.metrics.search.index.ProcessWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.search.index.constants.WorkflowMetricsIndexNameConstants;

import java.util.List;
import java.util.Locale;
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
	public void deleteProcess(Long processId) throws Exception {
		Process process = getProcess(processId);

		DeleteProcessRequest.Builder builder =
			new DeleteProcessRequest.Builder();

		_processWorkflowMetricsIndexer.deleteProcess(
			builder.companyId(
				contextCompany.getCompanyId()
			).processId(
				process.getId()
			).build());
	}

	@Override
	public Process getProcess(Long processId) throws Exception {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(
			_indexNameBuilder.getIndexName(contextCompany.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_PROCESS);
		searchSearchRequest.setQuery(_createBooleanQuery(processId));

		SearchSearchResponse searchSearchResponse =
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		List<SearchHit> searchHitsList = searchHits.getSearchHits();

		if (searchHitsList.isEmpty()) {
			throw new NoSuchProcessException(
				"No process exists with the process ID " + processId);
		}

		SearchHit searchHit = searchHitsList.get(0);

		return ProcessUtil.toProcess(
			searchHit.getDocument(),
			contextAcceptLanguage.getPreferredLocale());
	}

	@Override
	public String getProcessTitle(Long processId) throws Exception {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(
			_indexNameBuilder.getIndexName(contextCompany.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_PROCESS);
		searchSearchRequest.setQuery(_createBooleanQuery(processId));
		searchSearchRequest.setSelectedFieldNames(
			"processId",
			_getTitleFieldName(contextAcceptLanguage.getPreferredLocale()),
			_getTitleFieldName(LocaleThreadLocal.getDefaultLocale()));

		SearchSearchResponse searchSearchResponse =
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		List<SearchHit> searchHitsList = searchHits.getSearchHits();

		if (searchHitsList.isEmpty()) {
			return StringPool.BLANK;
		}

		SearchHit searchHit = searchHitsList.get(0);

		Document document = searchHit.getDocument();

		String title = document.getString(
			_getTitleFieldName(contextAcceptLanguage.getPreferredLocale()));

		if (Validator.isNull(title)) {
			title = document.getString(
				_getTitleFieldName(LocaleThreadLocal.getDefaultLocale()));
		}

		return title;
	}

	@Override
	public Process postProcess(Process process) throws Exception {
		AddProcessRequest.Builder builder = new AddProcessRequest.Builder();

		return ProcessUtil.toProcess(
			_processWorkflowMetricsIndexer.addProcess(
				builder.active(
					process.getActive()
				).companyId(
					contextCompany.getCompanyId()
				).createDate(
					process.getDateCreated()
				).description(
					process.getDescription()
				).modifiedDate(
					process.getDateModified()
				).name(
					process.getName()
				).processId(
					process.getId()
				).title(
					process.getTitle()
				).titleMap(
					LocalizedMapUtil.getLocalizedMap(process.getTitle_i18n())
				).version(
					process.getVersion()
				).versions(
					new String[] {process.getVersion()}
				).build()),
			contextAcceptLanguage.getPreferredLocale());
	}

	@Override
	public void putProcess(Long processId, Process process) throws Exception {
		Process getProcess = getProcess(processId);

		Map<Locale, String> titleMap = LocalizedMapUtil.getLocalizedMap(
			process.getTitle_i18n());

		UpdateProcessRequest.Builder builder =
			new UpdateProcessRequest.Builder();

		_processWorkflowMetricsIndexer.updateProcess(
			builder.active(
				process.getActive()
			).companyId(
				contextCompany.getCompanyId()
			).description(
				process.getDescription()
			).modifiedDate(
				process.getDateModified()
			).processId(
				getProcess.getId()
			).title(
				titleMap.get(contextAcceptLanguage.getPreferredLocale())
			).titleMap(
				titleMap
			).version(
				process.getVersion()
			).build());
	}

	private BooleanQuery _createBooleanQuery(Long processId) {
		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addMustQueryClauses(_queries.term("processId", processId));

		return booleanQuery.addMustQueryClauses(
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("deleted", Boolean.FALSE));
	}

	private String _getTitleFieldName(Locale locale) {
		return Field.getLocalizedName(locale, "title");
	}

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference
	private ProcessWorkflowMetricsIndexer _processWorkflowMetricsIndexer;

	@Reference
	private Queries _queries;

	@Reference
	private SearchRequestExecutor _searchRequestExecutor;

}