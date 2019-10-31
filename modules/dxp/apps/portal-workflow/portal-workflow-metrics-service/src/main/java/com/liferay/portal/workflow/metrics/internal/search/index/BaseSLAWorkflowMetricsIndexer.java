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

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.workflow.metrics.sla.processor.WorkflowMetricsSLAStatus;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
public abstract class BaseSLAWorkflowMetricsIndexer
	extends BaseWorkflowMetricsIndexer {

	public void deleteDocuments(long companyId, long instanceId) {
		BooleanQuery booleanQuery = queries.booleanQuery();

		_deleteDocuments(
			booleanQuery.addMustQueryClauses(
				queries.term("companyId", companyId),
				queries.term("instanceId", instanceId)));
	}

	public void deleteDocuments(
		long companyId, long processId, long slaDefinitionId) {

		BooleanQuery booleanQuery = queries.booleanQuery();

		booleanQuery.addMustNotQueryClauses(
			queries.term("status", WorkflowMetricsSLAStatus.COMPLETED.name()),
			queries.term("status", WorkflowMetricsSLAStatus.STOPPED.name()));

		_deleteDocuments(
			booleanQuery.addMustQueryClauses(
				queries.term("companyId", companyId),
				queries.term("processId", processId),
				queries.term("slaDefinitionId", slaDefinitionId)));
	}

	public void expireDocuments(long companyId, long instanceId) {
		BooleanQuery booleanQuery = queries.booleanQuery();

		_updateDocuments(
			document -> new DocumentImpl() {
				{
					addKeyword(
						"status", WorkflowMetricsSLAStatus.EXPIRED.name());
					addKeyword(Field.UID, document.getString(Field.UID));
				}
			},
			booleanQuery.addMustQueryClauses(
				queries.term("companyId", companyId),
				queries.term("instanceId", instanceId)));
	}

	@Reference
	protected Queries queries;

	private void _deleteDocuments(BooleanQuery booleanQuery) {
		_updateDocuments(
			document -> new DocumentImpl() {
				{
					addKeyword("deleted", true);
					addKeyword(Field.UID, document.getString(Field.UID));
				}
			},
			booleanQuery);
	}

	private void _updateDocuments(
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

			searchEngineAdapter.execute(bulkDocumentRequest);
		}
	}

}