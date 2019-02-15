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

package com.liferay.portal.workflow.metrics.internal.model.listener;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.workflow.metrics.internal.search.index.WorkflowMetricsIndicesCreator;

import java.io.Serializable;

import java.time.OffsetDateTime;
import java.time.ZoneId;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
public abstract class BaseKaleoModelListener<T extends BaseModel<T>>
	extends BaseModelListener<T> {

	protected void addDocument(Document document, Date date) {
		_addDate(document, date);

		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			getIndexName(), document);

		indexDocumentRequest.setType(getIndexType());

		searchEngineAdapter.execute(indexDocumentRequest);
	}

	protected void deleteDocument(Document document) {
		document.addKeyword("deleted", true);

		updateDocument(document, new Date());
	}

	protected String digest(Serializable... parts) {
		StringBuilder sb = new StringBuilder();

		for (Serializable part : parts) {
			sb.append(part);
		}

		return DigestUtils.sha256Hex(sb.toString());
	}

	protected abstract String getIndexName();

	protected abstract String getIndexType();

	@Reference(unbind = "-")
	protected void setWorkflowMetricsIndicesCreator(
		WorkflowMetricsIndicesCreator workflowMetricsIndicesCreator) {
	}

	protected void updateDocument(Document document, Date date) {
		_addDate(document, date);

		UpdateDocumentRequest updateDocumentRequest = new UpdateDocumentRequest(
			getIndexName(), document.getUID(), document);

		updateDocumentRequest.setType(getIndexType());

		searchEngineAdapter.execute(updateDocumentRequest);
	}

	@Reference
	protected SearchEngineAdapter searchEngineAdapter;

	private void _addDate(Document document, Date date) {
		OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(
			date.toInstant(), ZoneId.systemDefault());

		document.addKeyword(
			Field.getSortableFieldName("date"), offsetDateTime.toString());
	}

}