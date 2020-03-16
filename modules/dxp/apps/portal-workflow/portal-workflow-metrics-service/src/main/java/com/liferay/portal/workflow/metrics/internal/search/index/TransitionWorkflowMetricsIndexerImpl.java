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
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.kaleo.model.KaleoTransition;
import com.liferay.portal.workflow.metrics.search.index.TransitionWorkflowMetricsIndexer;

import java.util.Date;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true, service = TransitionWorkflowMetricsIndexerImpl.class
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

		documentBuilder.setString(
			Field.UID, digest(companyId, transitionId)
		).setLong(
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

		documentBuilder.setString(Field.UID, digest(companyId, transitionId));

		workflowMetricsPortalExecutor.execute(
			() -> deleteDocument(documentBuilder));
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
			(KaleoTransition kaleoTransition) -> {
				KaleoDefinitionVersion kaleoDefinitionVersion =
					getKaleoDefinitionVersion(
						kaleoTransition.getKaleoDefinitionVersionId());

				if (Objects.isNull(kaleoTransition)) {
					return;
				}

				addTransition(
					kaleoTransition.getCompanyId(),
					kaleoTransition.getCreateDate(),
					kaleoTransition.getModifiedDate(),
					kaleoTransition.getName(),
					_getNodeId(kaleoTransition.getKaleoNodeId()),
					kaleoTransition.getKaleoDefinitionId(),
					kaleoDefinitionVersion.getVersion(),
					_getNodeId(kaleoTransition.getSourceKaleoNodeId()),
					kaleoTransition.getSourceKaleoNodeName(),
					_getNodeId(kaleoTransition.getTargetKaleoNodeId()),
					kaleoTransition.getTargetKaleoNodeName(),
					kaleoTransition.getKaleoTransitionId(),
					kaleoTransition.getUserId());
			});

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