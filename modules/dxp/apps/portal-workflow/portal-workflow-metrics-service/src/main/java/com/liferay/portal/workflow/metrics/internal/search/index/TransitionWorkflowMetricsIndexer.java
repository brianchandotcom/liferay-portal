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

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.kaleo.model.KaleoTransition;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = TransitionWorkflowMetricsIndexer.class)
public class TransitionWorkflowMetricsIndexer
	extends BaseWorkflowMetricsIndexer {

	public Document createDocument(KaleoTransition kaleoTransition)
		throws PortalException {

		Document document = new DocumentImpl();

		document.addUID(
			"WorkflowMetricsTransition",
			digest(
				kaleoTransition.getCompanyId(),
				kaleoTransition.getKaleoDefinitionVersionId(),
				kaleoTransition.getKaleoTransitionId()));
		document.addKeyword("companyId", kaleoTransition.getCompanyId());
		document.addDateSortable("createDate", kaleoTransition.getCreateDate());
		document.addKeyword("deleted", false);
		document.addDateSortable(
			"modifiedDate", kaleoTransition.getModifiedDate());
		document.addKeyword("name", kaleoTransition.getName());
		document.addKeyword(
			"nodeId", _getNodeId(kaleoTransition.getKaleoNodeId()));
		document.addKeyword(
			"processId", kaleoTransition.getKaleoDefinitionId());
		document.addKeyword(
			"sourceNodeId", _getNodeId(kaleoTransition.getSourceKaleoNodeId()));
		document.addKeyword(
			"sourceNodeName", kaleoTransition.getSourceKaleoNodeName());
		document.addKeyword(
			"targetNodeId", _getNodeId(kaleoTransition.getSourceKaleoNodeId()));
		document.addKeyword(
			"targetNodeName", kaleoTransition.getTargetKaleoNodeName());
		document.addKeyword(
			"transitionId", kaleoTransition.getKaleoTransitionId());
		document.addKeyword("userId", kaleoTransition.getUserId());

		return document;
	}

	@Override
	public String getIndexName() {
		return "workflow-metrics-transitions";
	}

	@Override
	public String getIndexType() {
		return "WorkflowMetricsTransitionType";
	}

	@Override
	public void reindex(long companyId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			kaleoTransitionLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property companyIdProperty = PropertyFactoryUtil.forName(
					"companyId");

				dynamicQuery.add(companyIdProperty.eq(companyId));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(KaleoTransition kaleoTransition) ->
				workflowMetricsPortalExecutor.execute(
					() -> addDocument(createDocument(kaleoTransition))));

		actionableDynamicQuery.performActions();
	}

	private long _getNodeId(long kaleoNodeId) throws PortalException {
		KaleoNode kaleoNode = kaleoNodeLocalService.fetchKaleoNode(kaleoNodeId);

		if ((kaleoNode == null) ||
			!Objects.equals(kaleoNode.getType(), NodeType.TASK.name())) {

			return kaleoNodeId;
		}

		KaleoTask kaleoTask = kaleoTaskLocalService.getKaleoNodeKaleoTask(
			kaleoNode.getKaleoNodeId());

		return kaleoTask.getKaleoTaskId();
	}

}