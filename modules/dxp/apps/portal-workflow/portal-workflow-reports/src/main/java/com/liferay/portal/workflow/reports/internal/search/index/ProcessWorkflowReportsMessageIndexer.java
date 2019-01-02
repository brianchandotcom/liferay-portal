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
import com.liferay.portal.kernel.search.Field;
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
public class ProcessWorkflowReportsMessageIndexer
	implements WorkflowReportsMessageIndexer {

	@Override
	public void index(WorkflowReportsMessage workflowReportsMessage) {
		if (!_workflowReportsMessageIndexerHelper.matchAnyEvent(
				workflowReportsMessage.getEventId(),
				WorkflowReportsEvent.PROCESS_CREATE.name(),
				WorkflowReportsEvent.PROCESS_REMOVE.name(),
				WorkflowReportsEvent.PROCESS_UPDATE.name())) {

			return;
		}

		Document document = new DocumentImpl();

		_workflowReportsMessageIndexerHelper.addField(
			document, WorkflowIndexerFieldNames.ACTIVE,
			workflowReportsMessage.getProperty(
				WorkflowIndexerFieldNames.ACTIVE));
		_workflowReportsMessageIndexerHelper.addField(
			document, WorkflowIndexerFieldNames.COMPANY_ID,
			workflowReportsMessage.getCompanyId());
		_workflowReportsMessageIndexerHelper.addField(
			document,
			Field.getSortableFieldName(WorkflowIndexerFieldNames.DATE),
			workflowReportsMessage.getProperty(WorkflowIndexerFieldNames.DATE));

		Boolean deleted = workflowReportsMessage.getProperty(
			WorkflowIndexerFieldNames.DELETED);

		_workflowReportsMessageIndexerHelper.addField(
			document, WorkflowIndexerFieldNames.DELETED,
			GetterUtil.getBoolean(deleted));

		_workflowReportsMessageIndexerHelper.addField(
			document, WorkflowIndexerFieldNames.DESCRIPTION,
			workflowReportsMessage.getProperty(
				WorkflowIndexerFieldNames.DESCRIPTION));
		_workflowReportsMessageIndexerHelper.addField(
			document, WorkflowIndexerFieldNames.NAME,
			workflowReportsMessage.getProperty(WorkflowIndexerFieldNames.NAME));
		_workflowReportsMessageIndexerHelper.addField(
			document, WorkflowIndexerFieldNames.PROCESS_ID,
			workflowReportsMessage.getProperty(
				WorkflowIndexerFieldNames.PROCESS_ID));
		_workflowReportsMessageIndexerHelper.addLocalizedField(
			document, WorkflowIndexerFieldNames.TITLE,
			workflowReportsMessage.getProperty(
				WorkflowIndexerFieldNames.TITLE));
		_workflowReportsMessageIndexerHelper.addField(
			document, WorkflowIndexerFieldNames.USER_ID,
			workflowReportsMessage.getUserId());

		document.addUID(
			"Process",
			_workflowReportsMessageIndexerHelper.digest(
				workflowReportsMessage.getCompanyId(),
				workflowReportsMessage.getProperty(
					WorkflowIndexerFieldNames.PROCESS_ID)));

		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			WorkflowIndexConstants.INDEX_NAME_WORKFLOW_PROCESSES, document);

		indexDocumentRequest.setType(
			WorkflowIndexConstants.TYPE_MAPPING_NAME_WORKFLOW_PROCESSES);

		_searchEngineAdapter.execute(indexDocumentRequest);
	}

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

	private final WorkflowReportsMessageIndexerHelper
		_workflowReportsMessageIndexerHelper =
			new WorkflowReportsMessageIndexerHelper();

}