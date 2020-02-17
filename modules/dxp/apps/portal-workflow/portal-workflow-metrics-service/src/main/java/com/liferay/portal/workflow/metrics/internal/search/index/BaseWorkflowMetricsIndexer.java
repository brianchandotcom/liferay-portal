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

package com.liferay.portal.workflow.metrics.internal.search.index;

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MatchAllQuery;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.engine.adapter.index.CreateIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexResponse;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskAssignmentInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskInstanceTokenLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskLocalService;
import com.liferay.portal.workflow.metrics.internal.petra.executor.WorkflowMetricsPortalExecutor;

import java.io.Serializable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import org.apache.commons.codec.digest.DigestUtils;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Inácio Nery
 */
public abstract class BaseWorkflowMetricsIndexer {

	public void addDocument(Document document) {
		if (searchEngineAdapter == null) {
			return;
		}

		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			getIndexName(), document);

		if (PortalRunMode.isTestMode()) {
			indexDocumentRequest.setRefresh(true);
		}

		indexDocumentRequest.setType(getIndexType());

		searchEngineAdapter.execute(indexDocumentRequest);
	}

	public void addDocuments(List<Document> documents) {
		if (searchEngineAdapter == null) {
			return;
		}

		BulkDocumentRequest bulkDocumentRequest = new BulkDocumentRequest();

		documents.forEach(
			document -> bulkDocumentRequest.addBulkableDocumentRequest(
				new IndexDocumentRequest(
					getIndexName(), document.getUID(), document) {

					{
						setType(getIndexType());
					}
				}));

		if (ListUtil.isNotEmpty(
				bulkDocumentRequest.getBulkableDocumentRequests())) {

			if (PortalRunMode.isTestMode()) {
				bulkDocumentRequest.setRefresh(true);
			}

			searchEngineAdapter.execute(bulkDocumentRequest);
		}
	}

	public void createIndex() throws PortalException {
		if (searchEngineAdapter == null) {
			return;
		}

		if (hasIndex(getIndexName())) {
			return;
		}

		CreateIndexRequest createIndexRequest = new CreateIndexRequest(
			getIndexName());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			StringUtil.read(getClass(), "/META-INF/search/mappings.json"));

		createIndexRequest.setSource(
			JSONUtil.put(
				"mappings",
				JSONUtil.put(getIndexType(), jsonObject.get(getIndexType()))
			).put(
				"settings",
				JSONFactoryUtil.createJSONObject(
					StringUtil.read(
						getClass(), "/META-INF/search/settings.json"))
			).toString());

		searchEngineAdapter.execute(createIndexRequest);
	}

	public void deleteDocument(Document document) {
		document.addKeyword("deleted", true);

		_updateDocument(document);
	}

	public void deleteIndex(long companyId) throws PortalException {
		if (searchEngineAdapter == null) {
			return;
		}

		if (!hasIndex(getIndexName())) {
			return;
		}

		BooleanQuery booleanQuery = new BooleanQueryImpl();

		booleanQuery.add(new MatchAllQuery(), BooleanClauseOccur.MUST);

		BooleanFilter booleanFilter = new BooleanFilter();

		booleanFilter.add(
			new TermFilter("companyId", String.valueOf(companyId)),
			BooleanClauseOccur.MUST);

		booleanQuery.setPreBooleanFilter(booleanFilter);

		DeleteByQueryDocumentRequest deleteByQueryDocumentRequest =
			new DeleteByQueryDocumentRequest(booleanQuery, getIndexName());

		if (PortalRunMode.isTestMode()) {
			deleteByQueryDocumentRequest.setRefresh(true);
		}

		searchEngineAdapter.execute(deleteByQueryDocumentRequest);
	}

	public abstract String getIndexName();

	public abstract String getIndexType();

	public abstract void reindex(long companyId) throws PortalException;

	public void updateDocument(Document document) {
		_updateDocument(document);
	}

	@Activate
	protected void activate() throws Exception {
		createIndex();

		if (!_INDEX_ON_STARTUP) {
			for (Company company : companyLocalService.getCompanies()) {
				reindex(company.getCompanyId());
			}
		}
	}

	protected String digest(Serializable... parts) {
		StringBuilder sb = new StringBuilder();

		for (Serializable part : parts) {
			sb.append(part);
		}

		return DigestUtils.sha256Hex(sb.toString());
	}

	protected KaleoDefinition getKaleoDefinition(
		long kaleoDefinitionVersionId) {

		KaleoDefinitionVersion kaleoDefinitionVersion =
			getKaleoDefinitionVersion(kaleoDefinitionVersionId);

		if (kaleoDefinitionVersion != null) {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(kaleoDefinitionVersion.getCompanyId());

			return kaleoDefinitionLocalService.fetchKaleoDefinition(
				kaleoDefinitionVersion.getName(), serviceContext);
		}

		return null;
	}

	protected KaleoDefinitionVersion getKaleoDefinitionVersion(
		long kaleoDefinitionVersionId) {

		return kaleoDefinitionVersionLocalService.fetchKaleoDefinitionVersion(
			kaleoDefinitionVersionId);
	}

	protected boolean hasIndex(String indexName) {
		if (searchEngineAdapter == null) {
			return false;
		}

		IndicesExistsIndexRequest indicesExistsIndexRequest =
			new IndicesExistsIndexRequest(indexName);

		IndicesExistsIndexResponse indicesExistsIndexResponse =
			searchEngineAdapter.execute(indicesExistsIndexRequest);

		return indicesExistsIndexResponse.isExists();
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	protected void updateDocuments(
		Function<com.liferay.portal.search.document.Document, Document>
			transformDocumentFunction,
		Query query) {

		if (searchEngineAdapter == null) {
			return;
		}

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(getIndexName());
		searchSearchRequest.setQuery(query);
		searchSearchRequest.setSelectedFieldNames(Field.UID);
		searchSearchRequest.setSize(10000);

		SearchSearchResponse searchSearchResponse = searchEngineAdapter.execute(
			searchSearchRequest);

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		if (searchHits.getTotalHits() == 0) {
			return;
		}

		BulkDocumentRequest bulkDocumentRequest = new BulkDocumentRequest();

		Stream.of(
			searchHits.getSearchHits()
		).flatMap(
			List::stream
		).map(
			SearchHit::getDocument
		).map(
			document -> new UpdateDocumentRequest(
				getIndexName(), document.getString(Field.UID),
				transformDocumentFunction.apply(document)) {

				{
					setType(getIndexType());
				}
			}
		).forEach(
			bulkDocumentRequest::addBulkableDocumentRequest
		);

		if (ListUtil.isNotEmpty(
				bulkDocumentRequest.getBulkableDocumentRequests())) {

			if (PortalRunMode.isTestMode()) {
				bulkDocumentRequest.setRefresh(true);
			}

			searchEngineAdapter.execute(bulkDocumentRequest);
		}
	}

	@Reference
	protected AssetEntryLocalService assetEntryLocalService;

	@Reference
	protected CompanyLocalService companyLocalService;

	@Reference
	protected KaleoDefinitionLocalService kaleoDefinitionLocalService;

	@Reference
	protected KaleoDefinitionVersionLocalService
		kaleoDefinitionVersionLocalService;

	@Reference
	protected KaleoInstanceLocalService kaleoInstanceLocalService;

	@Reference
	protected KaleoNodeLocalService kaleoNodeLocalService;

	@Reference
	protected KaleoTaskAssignmentInstanceLocalService
		kaleoTaskAssignmentInstanceLocalService;

	@Reference
	protected KaleoTaskInstanceTokenLocalService
		kaleoTaskInstanceTokenLocalService;

	@Reference
	protected KaleoTaskLocalService kaleoTaskLocalService;

	@Reference
	protected Queries queries;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(search.engine.impl=Elasticsearch)"
	)
	protected volatile SearchEngineAdapter searchEngineAdapter;

	@Reference
	protected WorkflowMetricsPortalExecutor workflowMetricsPortalExecutor;

	private void _updateDocument(Document document) {
		if (searchEngineAdapter == null) {
			return;
		}

		UpdateDocumentRequest updateDocumentRequest = new UpdateDocumentRequest(
			getIndexName(), document.getUID(), document);

		if (PortalRunMode.isTestMode()) {
			updateDocumentRequest.setRefresh(true);
		}

		updateDocumentRequest.setType(getIndexType());

		searchEngineAdapter.execute(updateDocumentRequest);
	}

	private static final boolean _INDEX_ON_STARTUP = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.INDEX_ON_STARTUP));

}