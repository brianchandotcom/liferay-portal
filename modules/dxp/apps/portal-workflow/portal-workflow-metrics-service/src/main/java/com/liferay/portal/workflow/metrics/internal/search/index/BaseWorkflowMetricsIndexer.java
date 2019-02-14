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
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
public abstract class BaseWorkflowMetricsIndexer
	implements WorkflowMetricsIndexer {

	@Override
	public void index(Document document) {
		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			getIndexName(), document);

		indexDocumentRequest.setType(getIndexType());

		searchEngineAdapter.execute(indexDocumentRequest);
	}

	@Override
	public void update(Document document) {
		UpdateDocumentRequest updateDocumentRequest = new UpdateDocumentRequest(
			getIndexName(), document.getUID(), document);

		updateDocumentRequest.setType(getIndexType());

		searchEngineAdapter.execute(updateDocumentRequest);
	}

	@Reference(unbind = "-")
	protected void setWorkflowMetricsIndicesCreator(
		WorkflowMetricsIndicesCreator workflowMetricsIndicesCreator) {
	}

	@Reference
	protected SearchEngineAdapter searchEngineAdapter;

}