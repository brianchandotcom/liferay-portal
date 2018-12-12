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

package com.liferay.portal.workflow.reports.internal.messaging;

import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.workflow.reports.internal.constants.WorkflowIndexConstants;
import com.liferay.portal.workflow.reports.internal.index.WorkflowIndicesCreator;
import com.liferay.portal.workflow.reports.messaging.constants.WorkflowReportsDestinationNames;

import java.util.Map;
import java.util.UUID;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true,
	property = "destination.name=" + WorkflowReportsDestinationNames.WORKFLOW_REPORTS,
	service = MessageListener.class
)
public class WorkflowReportsMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		Document document = new DocumentImpl();

		Map<String, String> values = (Map<String, String>)message.getPayload();

		values.forEach((key, value) -> document.add(new Field(key, value)));

		UUID uuid = UUID.randomUUID();

		document.addUID("Workflow", uuid.toString());

		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			WorkflowIndexConstants.INDEX_NAME_WORKFLOW_EVENTS, document);

		indexDocumentRequest.setType(
			WorkflowIndexConstants.TYPE_MAPPING_NAME_WORKFLOW_EVENTS);

		_searchEngineAdapter.execute(indexDocumentRequest);
	}

	@Reference(unbind = "-")
	protected void setWorkflowIndicesCreator(
		WorkflowIndicesCreator workflowIndicesCreator) {
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)")
	private SearchEngineAdapter _searchEngineAdapter;

}