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

import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.workflow.metrics.model.AddTransitionRequest;
import com.liferay.portal.workflow.metrics.model.DeleteTransitionRequest;
import com.liferay.portal.workflow.metrics.search.index.TransitionWorkflowMetricsIndexer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = TransitionWorkflowMetricsIndexer.class)
public class TransitionWorkflowMetricsIndexerImpl
	extends BaseWorkflowMetricsIndexer
	implements TransitionWorkflowMetricsIndexer {

	@Override
	public Document addTransition(AddTransitionRequest addTransitionRequest) {
		if (searchEngineAdapter == null) {
			return null;
		}

		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		Document document = documentBuilder.setLong(
			"companyId", addTransitionRequest.getCompanyId()
		).setDate(
			"createDate", getDate(addTransitionRequest.getCreateDate())
		).setValue(
			"deleted", false
		).setDate(
			"modifiedDate", getDate(addTransitionRequest.getModifiedDate())
		).setString(
			"name", addTransitionRequest.getName()
		).setLong(
			"nodeId", addTransitionRequest.getNodeId()
		).setLong(
			"processId", addTransitionRequest.getProcessId()
		).setLong(
			"sourceNodeId", addTransitionRequest.getSourceNodeId()
		).setString(
			"sourceNodeName", addTransitionRequest.getSourceNodeName()
		).setLong(
			"targetNodeId", addTransitionRequest.getTargetNodeId()
		).setString(
			"targetNodeName", addTransitionRequest.getTargetNodeName()
		).setString(
			"uid",
			digest(
				addTransitionRequest.getCompanyId(),
				addTransitionRequest.getTransitionId())
		).setLong(
			"userId", addTransitionRequest.getUserId()
		).setString(
			"version", addTransitionRequest.getProcessVersion()
		).build();

		workflowMetricsPortalExecutor.execute(() -> addDocument(document));

		return document;
	}

	@Override
	public void deleteTransition(
		DeleteTransitionRequest deleteTransitionRequest) {

		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		documentBuilder.setLong(
			"companyId", deleteTransitionRequest.getCompanyId()
		).setLong(
			"transitionId", deleteTransitionRequest.getTransitionId()
		).setString(
			"uid",
			digest(
				deleteTransitionRequest.getCompanyId(),
				deleteTransitionRequest.getTransitionId())
		);

		workflowMetricsPortalExecutor.execute(
			() -> deleteDocument(documentBuilder));
	}

	@Override
	public String getIndexName(long companyId) {
		return _transitionWorkflowMetricsIndex.getIndexName(companyId);
	}

	@Override
	public String getIndexType() {
		return _transitionWorkflowMetricsIndex.getIndexType();
	}

	@Reference(target = "(workflow.metrics.index.entity.name=transition)")
	private WorkflowMetricsIndex _transitionWorkflowMetricsIndex;

}