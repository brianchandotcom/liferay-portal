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
import com.liferay.portal.workflow.metrics.search.index.TransitionWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.search.index.name.WorkflowMetricsIndexNameBuilder;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true,
	property = "workflow.metrics.index.entity.name=transition",
	service = {
		TransitionWorkflowMetricsIndexer.class, WorkflowMetricsIndex.class
	}
)
public class TransitionWorkflowMetricsIndexerImpl
	extends BaseWorkflowMetricsIndexer
	implements TransitionWorkflowMetricsIndexer {

	@Override
	public Document addTransition(
		long companyId, Date createDate, Date modifiedDate, String name,
		long nodeId, long processId, String processVersion, long sourceNodeId,
		String sourceNodeName, long targetNodeId, String targetNodeName,
		long transitionId, long userId) {

		if (searchEngineAdapter == null) {
			return null;
		}

		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		documentBuilder.setLong(
			"companyId", companyId
		).setDate(
			"createDate", formatDate(createDate)
		).setValue(
			"deleted", false
		).setDate(
			"modifiedDate", formatDate(modifiedDate)
		).setString(
			"name", name
		).setLong(
			"nodeId", nodeId
		).setLong(
			"processId", processId
		).setLong(
			"sourceNodeId", sourceNodeId
		).setString(
			"sourceNodeName", sourceNodeName
		).setLong(
			"targetNodeId", targetNodeId
		).setString(
			"targetNodeName", targetNodeName
		).setString(
			"uid", digest(companyId, transitionId)
		).setLong(
			"userId", userId
		).setString(
			"version", processVersion
		);

		Document document = documentBuilder.build();

		workflowMetricsPortalExecutor.execute(() -> addDocument(document));

		return document;
	}

	@Override
	public void deleteTransition(long companyId, long transitionId) {
		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		documentBuilder.setLong(
			"companyId", companyId
		).setLong(
			"transitionId", transitionId
		).setString(
			"uid", digest(companyId, transitionId)
		);

		workflowMetricsPortalExecutor.execute(
			() -> deleteDocument(documentBuilder));
	}

	@Override
	public String getIndexName(long companyId) {
		return _transitionWorkflowMetricsIndexNameBuilder.getIndexName(
			companyId);
	}

	@Override
	public String getIndexType() {
		return "WorkflowMetricsTransitionType";
	}

	@Reference(target = "(workflow.metrics.index.entity.name=transition)")
	private WorkflowMetricsIndexNameBuilder
		_transitionWorkflowMetricsIndexNameBuilder;

}