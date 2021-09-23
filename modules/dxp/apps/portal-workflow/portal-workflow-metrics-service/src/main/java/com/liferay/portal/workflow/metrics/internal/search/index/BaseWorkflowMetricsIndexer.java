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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.script.Scripts;
import com.liferay.portal.workflow.metrics.internal.petra.executor.WorkflowMetricsPortalExecutor;
import com.liferay.portal.workflow.metrics.internal.search.index.util.WorkflowMetricsIndexerUtil;

import java.io.Serializable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Inácio Nery
 */
public abstract class BaseWorkflowMetricsIndexer {

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

	public void deleteDocument(DocumentBuilder documentBuilder) {
		documentBuilder.setValue("deleted", true);

		_updateDocument(documentBuilder.build());
	}

	public abstract String getIndexName(long companyId);

	public abstract String getIndexType();

	public void updateDocument(Document document) {
		_updateDocument(document);
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
		return WorkflowMetricsIndexerUtil.digest(getIndexType(), parts);
	}

	protected String formatLocalDateTime(LocalDateTime localDateTime) {
		return _dateTimeFormatter.format(localDateTime);
	}

	protected String getDate(Date date) {
		try {
			return DateUtil.getDate(
				date, "yyyyMMddHHmmss", LocaleUtil.getDefault());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}

			return null;
		}
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

	@Reference(
		target = ModuleServiceLifecycle.PORTLETS_INITIALIZED, unbind = "-"
	)
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	protected void updateDocuments(
		long companyId, Map<String, Object> fieldsMap, Query filterQuery) {

		if (searchEngineAdapter == null) {
			return;
		}

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(getIndexName(companyId));

		BooleanQuery booleanQuery = queries.booleanQuery();

		searchSearchRequest.setQuery(
			booleanQuery.addFilterQueryClauses(filterQuery));

		searchSearchRequest.setSelectedFieldNames("uid");
		searchSearchRequest.setSize(10000);
		searchSearchRequest.setTypes(getIndexType());

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
		).forEach(
			document -> {
				DocumentBuilder documentBuilder =
					documentBuilderFactory.builder();

				documentBuilder.setString("uid", document.getString("uid"));

				fieldsMap.forEach(documentBuilder::setValue);

				UpdateDocumentRequest updateDocumentRequest =
					new UpdateDocumentRequest(
						getIndexName(companyId), document.getString("uid"),
						documentBuilder.build());

				updateDocumentRequest.setType(getIndexType());
				updateDocumentRequest.setUpsert(true);

				bulkDocumentRequest.addBulkableDocumentRequest(
					updateDocumentRequest);
			}
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
	protected DocumentBuilderFactory documentBuilderFactory;

	@Reference
	protected Queries queries;

	@Reference
	protected Scripts scripts;

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
		DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

}