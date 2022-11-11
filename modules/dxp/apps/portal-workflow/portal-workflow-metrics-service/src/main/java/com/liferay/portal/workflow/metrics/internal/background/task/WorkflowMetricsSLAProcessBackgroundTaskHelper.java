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

package com.liferay.portal.workflow.metrics.internal.background.task;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.background.task.constants.BackgroundTaskContextMapConstants;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;
import com.liferay.portal.workflow.metrics.search.background.task.WorkflowMetricsReindexStatusMessageSender;
import com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLADefinitionLocalService;

import java.io.Serializable;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(service = WorkflowMetricsSLAProcessBackgroundTaskHelper.class)
public class WorkflowMetricsSLAProcessBackgroundTaskHelper {

	public void addBackgroundTasks(boolean reindex) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_workflowMetricsSLADefinitionLocalService.
				getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property statusProperty = PropertyFactoryUtil.forName("status");

				dynamicQuery.add(
					statusProperty.eq(WorkflowConstants.STATUS_APPROVED));
			});

		long total = actionableDynamicQuery.performCount();

		AtomicInteger atomicCounter = new AtomicInteger(0);

		actionableDynamicQuery.setPerformActionMethod(
			(WorkflowMetricsSLADefinition workflowMetricsSLADefinition) -> {
				int count = _backgroundTaskLocalService.getBackgroundTasksCount(
					workflowMetricsSLADefinition.getGroupId(),
					_getBackgroundTaskName(workflowMetricsSLADefinition),
					WorkflowMetricsSLAProcessBackgroundTaskExecutor.class.
						getName(),
					false);

				if (count > 0) {
					return;
				}

				Map<String, Serializable> taskContextMap =
					HashMapBuilder.<String, Serializable>put(
						BackgroundTaskContextMapConstants.DELETE_ON_SUCCESS,
						true
					).put(
						"reindex", reindex
					).put(
						"workflowMetricsSLADefinitionId",
						workflowMetricsSLADefinition.getPrimaryKey()
					).build();

				_backgroundTaskLocalService.addBackgroundTask(
					workflowMetricsSLADefinition.getUserId(),
					workflowMetricsSLADefinition.getGroupId(),
					_getBackgroundTaskName(workflowMetricsSLADefinition),
					WorkflowMetricsSLAProcessBackgroundTaskExecutor.class.
						getName(),
					taskContextMap, new ServiceContext());

				if (reindex) {
					_workflowMetricsReindexStatusMessageSender.
						sendStatusMessage(
							atomicCounter.incrementAndGet(), total,
							"sla-instance-result");
				}
			});

		actionableDynamicQuery.performActions();
	}

	private String _getBackgroundTaskName(
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition) {

		return StringBundler.concat(
			WorkflowMetricsSLAProcessBackgroundTaskHelper.class.getSimpleName(),
			"-", workflowMetricsSLADefinition.getProcessId(),
			workflowMetricsSLADefinition.getPrimaryKey());
	}

	@Reference
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@Reference(target = ModuleServiceLifecycle.PORTLETS_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private WorkflowMetricsReindexStatusMessageSender
		_workflowMetricsReindexStatusMessageSender;

	@Reference
	private WorkflowMetricsSLADefinitionLocalService
		_workflowMetricsSLADefinitionLocalService;

	@Reference(
		target = "(background.task.executor.class.name=com.liferay.portal.workflow.metrics.internal.background.task.WorkflowMetricsSLAProcessBackgroundTaskExecutor)"
	)
	private BackgroundTaskExecutor
		_workflowMetricsSLAProcessBackgroundTaskExecutor;

}