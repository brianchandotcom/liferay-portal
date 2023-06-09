/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.exportimport.internal.scheduler;

import com.liferay.exportimport.kernel.background.task.BackgroundTaskExecutorNames;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.Time;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author To Trinh
 * @author Ha Tang
 */
@Component(service = SchedulerJobConfiguration.class)
public class DeleteObsoleteBackgroundTasksSchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
		return () -> _companyLocalService.forEachCompanyId(
			companyId -> _deleteObsoleteBackGroundTasks(companyId));
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(1, TimeUnit.DAY);
	}

	private void _deleteObsoleteBackGroundTasks(long companyId)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			_backgroundTaskLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				dynamicQuery.add(
					RestrictionsFactoryUtil.eq("companyId", companyId));

				Property modifiedDateProperty = PropertyFactoryUtil.forName(
					"modifiedDate");

				dynamicQuery.add(
					modifiedDateProperty.lt(
						new Date(
							System.currentTimeMillis() - (30 * Time.DAY))));

				Property taskExecutorClassNameProperty =
					PropertyFactoryUtil.forName("taskExecutorClassName");

				dynamicQuery.add(
					taskExecutorClassNameProperty.in(
						_TASK_EXECUTOR_CLASS_NAMES));

				Property statusProperty = PropertyFactoryUtil.forName("status");

				dynamicQuery.add(statusProperty.in(_STATUSES));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(BackgroundTask backgroundTask) ->
				_backgroundTaskLocalService.deleteBackgroundTask(
					backgroundTask));

		actionableDynamicQuery.performActions();
	}

	private static final int[] _STATUSES = {
		BackgroundTaskConstants.STATUS_CANCELLED,
		BackgroundTaskConstants.STATUS_FAILED,
		BackgroundTaskConstants.STATUS_SUCCESSFUL
	};

	private static final String[] _TASK_EXECUTOR_CLASS_NAMES = {
		BackgroundTaskExecutorNames.LAYOUT_EXPORT_BACKGROUND_TASK_EXECUTOR,
		BackgroundTaskExecutorNames.LAYOUT_IMPORT_BACKGROUND_TASK_EXECUTOR,
		BackgroundTaskExecutorNames.PORTLET_EXPORT_BACKGROUND_TASK_EXECUTOR,
		BackgroundTaskExecutorNames.PORTLET_IMPORT_BACKGROUND_TASK_EXECUTOR
	};

	@Reference
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

}