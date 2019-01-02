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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.workflow.reports.internal.constants.WorkflowIndexConstants;
import com.liferay.portal.workflow.reports.internal.constants.WorkflowIndexerFieldNames;
import com.liferay.portal.workflow.reports.internal.search.index.helper.WorkflowReportsMessageIndexerHelper;
import com.liferay.portal.workflow.reports.messaging.WorkflowReportsEvent;
import com.liferay.portal.workflow.reports.messaging.WorkflowReportsMessage;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true, service = WorkflowReportsMessageIndexer.class)
public class InstanceWorkflowReportsMessageIndexer
	extends BaseWorkflowReportsMessageIndexer {

	@Override
	public void index(WorkflowReportsMessage workflowReportsMessage) {
		if (!matchAnyEvent(
				workflowReportsMessage.getEventId(),
				WorkflowReportsEvent.INSTANCE_COMPLETE.name(),
				WorkflowReportsEvent.INSTANCE_CREATE.name(),
				WorkflowReportsEvent.INSTANCE_REMOVE.name(),
				WorkflowReportsEvent.INSTANCE_UPDATE.name())) {

			return;
		}

		Document document = new DocumentImpl();

		addField(
			document, WorkflowIndexerFieldNames.CLASS_NAME,
			workflowReportsMessage.getProperty(
				WorkflowIndexerFieldNames.CLASS_NAME));
		addField(
			document, WorkflowIndexerFieldNames.COMPANY_ID,
			workflowReportsMessage.getCompanyId());
		addField(
			document, WorkflowIndexerFieldNames.CLASS_PK,
			workflowReportsMessage.getProperty(
				WorkflowIndexerFieldNames.CLASS_PK));

		Boolean completed = workflowReportsMessage.getProperty(
			WorkflowIndexerFieldNames.COMPLETED);

		addField(
			document, WorkflowIndexerFieldNames.COMPLETED,
			GetterUtil.getBoolean(completed));

		addField(
			document, WorkflowIndexerFieldNames.DATE,
			workflowReportsMessage.getProperty(WorkflowIndexerFieldNames.DATE));

		Boolean deleted = workflowReportsMessage.getProperty(
			WorkflowIndexerFieldNames.DELETED);

		addField(
			document, WorkflowIndexerFieldNames.DELETED,
			GetterUtil.getBoolean(deleted));

		addField(
			document, WorkflowIndexerFieldNames.DURATION,
			workflowReportsMessage.getProperty(
				WorkflowIndexerFieldNames.DURATION));
		addField(
			document, WorkflowIndexerFieldNames.INSTANCE_ID,
			workflowReportsMessage.getProperty(
				WorkflowIndexerFieldNames.INSTANCE_ID));
		addField(
			document, WorkflowIndexerFieldNames.PROCESS_ID,
			workflowReportsMessage.getProperty(
				WorkflowIndexerFieldNames.PROCESS_ID));
		addField(
			document, WorkflowIndexerFieldNames.USER_ID,
			workflowReportsMessage.getUserId());

		document.addUID(
			"Instance",
			digest(
				workflowReportsMessage.getCompanyId(),
				workflowReportsMessage.getProperty(
					WorkflowIndexerFieldNames.INSTANCE_ID),
				workflowReportsMessage.getProperty(
					WorkflowIndexerFieldNames.PROCESS_ID)));

		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			WorkflowIndexConstants.INDEX_NAME_WORKFLOW_INSTANCES, document);

		indexDocumentRequest.setType(
			WorkflowIndexConstants.TYPE_MAPPING_NAME_WORKFLOW_INSTANCES);

		_searchEngineAdapter.execute(indexDocumentRequest);
	}

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

}