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

package com.liferay.portal.workflow.kaleo.metrics.integration.internal.search.index.reindexer;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.workflow.kaleo.metrics.integration.internal.helper.IndexerHelper;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskAssignmentInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskInstanceTokenLocalService;
import com.liferay.portal.workflow.metrics.search.background.task.WorkflowMetricsReindexStatusMessageSender;
import com.liferay.portal.workflow.metrics.search.index.TaskWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.search.index.reindexer.WorkflowMetricsReindexer;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true, property = "workflow.metrics.index.entity.name=task",
	service = WorkflowMetricsReindexer.class
)
public class TaskWorkflowMetricsReindexer implements WorkflowMetricsReindexer {

	@Override
	public void reindex(long companyId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_kaleoTaskInstanceTokenLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property companyIdProperty = PropertyFactoryUtil.forName(
					"companyId");

				dynamicQuery.add(companyIdProperty.eq(companyId));
			});

		long total = actionableDynamicQuery.performCount();

		AtomicInteger atomicCounter = new AtomicInteger(0);

		actionableDynamicQuery.setPerformActionMethod(
			(KaleoTaskInstanceToken kaleoTaskInstanceToken) -> {
				KaleoDefinitionVersion kaleoDefinitionVersion =
					_kaleoDefinitionVersionLocalService.
						fetchKaleoDefinitionVersion(
							kaleoTaskInstanceToken.
								getKaleoDefinitionVersionId());

				if (Objects.isNull(kaleoDefinitionVersion)) {
					return;
				}

				KaleoInstance kaleoInstance =
					_kaleoInstanceLocalService.fetchKaleoInstance(
						kaleoTaskInstanceToken.getKaleoInstanceId());

				if (Objects.isNull(kaleoInstance)) {
					return;
				}

				List<KaleoTaskAssignmentInstance> kaleoTaskAssignmentInstances =
					_kaleoTaskAssignmentInstanceLocalService.
						getKaleoTaskAssignmentInstances(
							kaleoTaskInstanceToken.
								getKaleoTaskInstanceTokenId());

				_taskWorkflowMetricsIndexer.addTask(
					_indexerHelper.createAssetTitleLocalizationMap(
						kaleoTaskInstanceToken.getClassName(),
						kaleoTaskInstanceToken.getClassPK(),
						kaleoTaskInstanceToken.getGroupId()),
					_indexerHelper.createAssetTypeLocalizationMap(
						kaleoTaskInstanceToken.getClassName(),
						kaleoTaskInstanceToken.getGroupId()),
					_indexerHelper.toAssignments(kaleoTaskAssignmentInstances),
					kaleoTaskInstanceToken.getClassName(),
					kaleoTaskInstanceToken.getClassPK(),
					kaleoTaskInstanceToken.getCompanyId(),
					kaleoTaskInstanceToken.isCompleted(),
					kaleoTaskInstanceToken.getCompletionDate(),
					kaleoTaskInstanceToken.getCompletionUserId(),
					kaleoTaskInstanceToken.getCreateDate(),
					kaleoInstance.isCompleted(),
					kaleoInstance.getCompletionDate(),
					kaleoTaskInstanceToken.getKaleoInstanceId(),
					kaleoTaskInstanceToken.getModifiedDate(),
					kaleoTaskInstanceToken.getKaleoTaskName(),
					kaleoTaskInstanceToken.getKaleoTaskId(),
					kaleoInstance.getKaleoDefinitionId(),
					kaleoDefinitionVersion.getVersion(),
					kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(),
					kaleoTaskInstanceToken.getUserId());

				_workflowMetricsReindexStatusMessageSender.sendStatusMessage(
					atomicCounter.incrementAndGet(), total, "task");
			});

		actionableDynamicQuery.performActions();
	}

	@Reference
	private IndexerHelper _indexerHelper;

	@Reference
	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;

	@Reference
	private KaleoInstanceLocalService _kaleoInstanceLocalService;

	@Reference
	private KaleoTaskAssignmentInstanceLocalService
		_kaleoTaskAssignmentInstanceLocalService;

	@Reference
	private KaleoTaskInstanceTokenLocalService
		_kaleoTaskInstanceTokenLocalService;

	@Reference
	private TaskWorkflowMetricsIndexer _taskWorkflowMetricsIndexer;

	@Reference
	private WorkflowMetricsReindexStatusMessageSender
		_workflowMetricsReindexStatusMessageSender;

}