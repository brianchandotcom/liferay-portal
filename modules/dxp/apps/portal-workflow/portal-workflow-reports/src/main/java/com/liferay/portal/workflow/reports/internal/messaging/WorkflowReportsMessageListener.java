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
import com.liferay.portal.workflow.reports.internal.search.index.WorkflowIndicesCreator;
import com.liferay.portal.workflow.reports.internal.search.index.WorkflowReportsMessageIndexer;
import com.liferay.portal.workflow.reports.messaging.WorkflowReportsMessage;
import com.liferay.portal.workflow.reports.messaging.constants.WorkflowReportsDestinationNames;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true,
	property = "destination.name=" + WorkflowReportsDestinationNames.WORKFLOW_REPORTS,
	service = MessageListener.class
)
public class WorkflowReportsMessageListener extends BaseMessageListener {

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addWorkflowIndexerWriter(
		WorkflowReportsMessageIndexer workflowReportsMessageIndexer) {

		_workflowReportsMessageIndexers.add(workflowReportsMessageIndexer);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		WorkflowReportsMessage workflowReportsMessage =
			(WorkflowReportsMessage)message.getPayload();

		_workflowReportsMessageIndexers.forEach(
			indexer -> indexer.index(workflowReportsMessage));
	}

	protected void removeWorkflowIndexerWriter(
		WorkflowReportsMessageIndexer workflowReportsMessageIndexer) {

		_workflowReportsMessageIndexers.remove(workflowReportsMessageIndexer);
	}

	@Reference(unbind = "-")
	protected void setWorkflowIndicesCreator(
		WorkflowIndicesCreator workflowIndicesCreator) {
	}

	private static final List<WorkflowReportsMessageIndexer>
		_workflowReportsMessageIndexers = new ArrayList<>();

}