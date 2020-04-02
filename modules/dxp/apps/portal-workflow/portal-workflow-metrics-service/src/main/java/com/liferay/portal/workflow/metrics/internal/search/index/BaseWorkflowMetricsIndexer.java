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

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MatchAllQuery;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.engine.adapter.index.CreateIndexRequest;
import com.liferay.portal.search.engine.adapter.index.DeleteIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexResponse;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.workflow.metrics.internal.petra.executor.WorkflowMetricsPortalExecutor;

import java.io.Serializable;

import java.text.DateFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
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
public abstract class BaseWorkflowMetricsIndexer
	implements WorkflowMetricsIndex {

	public void addDocuments(List<Document> documents) {
		if (searchEngineAdapter == null) {
			return;
		}

		BulkDocumentRequest bulkDocumentRequest = new BulkDocumentRequest();

		documents.forEach(
			document -> bulkDocumentRequest.addBulkableDocumentRequest(
				new IndexDocumentRequest(
					getIndexName(document.getLong("companyId")),
					document.getString("uid"), document) {

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

	@Override
	public void clearIndex(long companyId) throws PortalException {
		if (searchEngineAdapter == null) {
			return;
		}

		if (!hasIndex(getIndexName(companyId))) {
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
			new DeleteByQueryDocumentRequest(
				booleanQuery, getIndexName(companyId));

		if (PortalRunMode.isTestMode()) {
			deleteByQueryDocumentRequest.setRefresh(true);
		}

		searchEngineAdapter.execute(deleteByQueryDocumentRequest);
	}

	@Override
	public void createIndex(long companyId) throws PortalException {
		if (searchEngineAdapter == null) {
			return;
		}

		if (hasIndex(getIndexName(companyId))) {
			return;
		}

		CreateIndexRequest createIndexRequest = new CreateIndexRequest(
			getIndexName(companyId));

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
		_updateDocument(document);
	}

	public void deleteDocument(DocumentBuilder documentBuilder) {
		documentBuilder.setValue("deleted", true);

		_updateDocument(documentBuilder.build());
	}

	@Override
	public void removeIndex(long companyId) throws PortalException {
		if (searchEngineAdapter == null) {
			return;
		}

		if (!hasIndex(getIndexName(companyId))) {
			return;
		}

		searchEngineAdapter.execute(
			new DeleteIndexRequest(getIndexName(companyId)));
	}

	public void updateDocument(Document document) {
		_updateDocument(document);
	}

	@Activate
	protected void activate() throws Exception {
		ActionableDynamicQuery actionableDynamicQuery =
			companyLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			(Company company) -> createIndex(company.getCompanyId()));

		actionableDynamicQuery.performActions();
	}

	protected void addDocument(Document document) {
		if (searchEngineAdapter == null) {
			return;
		}

		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			getIndexName(document.getLong("companyId")), document);

		if (PortalRunMode.isTestMode()) {
			indexDocumentRequest.setRefresh(true);
		}

		indexDocumentRequest.setType(getIndexType());

		searchEngineAdapter.execute(indexDocumentRequest);
	}

	protected String digest(Serializable... parts) {
		StringBuilder sb = new StringBuilder();

		for (Serializable part : parts) {
			sb.append(part);
		}

		return StringUtil.removeSubstring(getIndexType(), "Type") +
			DigestUtils.sha256Hex(sb.toString());
	}

	protected String formatDate(Date date) {
		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyyMMddHHmmss");

		try {
			return dateFormat.format(date);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}

			return null;
		}
	}

	protected String formatLocalDateTime(LocalDateTime localDateTime) {
		return _dateTimeFormatter.format(localDateTime);
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

	protected void setLocalizedField(
		DocumentBuilder documentBuilder, String fieldName,
		Map<Locale, String> localizedMap) {

		Stream.of(
			localizedMap.entrySet()
		).flatMap(
			Set::stream
		).forEach(
			entry -> {
				String localizedName = Field.getLocalizedName(
					entry.getKey(), fieldName);

				documentBuilder.setValue(
					localizedName, entry.getValue()
				).setValue(
					Field.getSortableFieldName(localizedName), entry.getValue()
				);
			}
		);
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	protected void updateDocuments(
		long companyId, Map<String, Object> fieldsMap, Query query) {

		if (searchEngineAdapter == null) {
			return;
		}

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(getIndexName(companyId));
		searchSearchRequest.setQuery(query);
		searchSearchRequest.setTypes(getIndexType());
		searchSearchRequest.setSelectedFieldNames("uid");
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
			document -> {
				DocumentBuilder documentBuilder =
					documentBuilderFactory.builder();

				documentBuilder.setString("uid", document.getString("uid"));

				fieldsMap.forEach(
					(name, value) -> documentBuilder.setValue(name, value));

				return new UpdateDocumentRequest(
					getIndexName(companyId), document.getString("uid"),
					documentBuilder.build()) {

					{
						setType(getIndexType());
						setUpsert(true);
					}
				};
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
	protected CompanyLocalService companyLocalService;

	@Reference
	protected DocumentBuilderFactory documentBuilderFactory;

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
			getIndexName(document.getLong("companyId")),
			document.getString("uid"), document);

		if (PortalRunMode.isTestMode()) {
			updateDocumentRequest.setRefresh(true);
		}

		updateDocumentRequest.setType(getIndexType());
		updateDocumentRequest.setUpsert(true);

		searchEngineAdapter.execute(updateDocumentRequest);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseWorkflowMetricsIndexer.class);

	private final DateTimeFormatter _dateTimeFormatter =
		DateTimeFormatter.ofPattern(
			PropsUtil.get(PropsKeys.INDEX_DATE_FORMAT_PATTERN));

}