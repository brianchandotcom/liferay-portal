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

package com.liferay.portal.workflow.reports.internal.search.index;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.workflow.reports.internal.constants.WorkflowIndexConstants;
import com.liferay.portal.workflow.reports.internal.constants.WorkflowIndexerFieldNames;
import com.liferay.portal.workflow.reports.internal.search.index.helper.WorkflowReportsMessageIndexerHelper;
import com.liferay.portal.workflow.reports.messaging.WorkflowReportsMessage;

import java.util.Map;
import java.util.UUID;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true, service = WorkflowReportsMessageIndexer.class)
public class EventsWorkflowReportsMessageIndexer
	extends BaseWorkflowReportsMessageIndexer {

	@Override
	public void index(WorkflowReportsMessage workflowReportsMessage) {
		Document document = new DocumentImpl();

		Map<String, ?> properties = workflowReportsMessage.getProperties();

		properties.forEach((key, value) -> addField(document, key, value));

		addField(
			document, WorkflowIndexerFieldNames.COMPANY_ID,
			workflowReportsMessage.getCompanyId());
		addField(
			document, WorkflowIndexerFieldNames.EVENT_ID,
			workflowReportsMessage.getEventId());
		addField(
			document, WorkflowIndexerFieldNames.USER_ID,
			workflowReportsMessage.getUserId());

		UUID uuid = UUID.randomUUID();

		document.addUID("Workflow", uuid.toString());

		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			WorkflowIndexConstants.INDEX_NAME_WORKFLOW_EVENTS, document);

		indexDocumentRequest.setType(
			WorkflowIndexConstants.TYPE_MAPPING_NAME_WORKFLOW_EVENTS);

		_searchEngineAdapter.execute(indexDocumentRequest);
	}

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

}