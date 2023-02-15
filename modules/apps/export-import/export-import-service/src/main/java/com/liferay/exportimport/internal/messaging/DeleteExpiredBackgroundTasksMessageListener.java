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

package com.liferay.exportimport.internal.messaging;

import com.liferay.exportimport.configuration.ExportImportServiceConfiguration;
import com.liferay.exportimport.kernel.background.task.BackgroundTaskExecutorNames;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.Time;

import java.util.Date;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author To Trinh
 */
@Component(
	configurationPid = "com.liferay.exportimport.configuration.ExportImportServiceConfiguration",
	service = {}
)
public class DeleteExpiredBackgroundTasksMessageListener
	extends BaseMessageListener {

	@Activate
	protected void activate(Map<String, Object> properties) {
		_exportImportServiceConfiguration = ConfigurableUtil.createConfigurable(
			ExportImportServiceConfiguration.class, properties);

		Class<?> clazz = getClass();

		String className = clazz.getName();

		//TODO: replace 2 with _exportImportServiceConfiguration.cleanupJobInterval()
		Trigger trigger = _triggerFactory.createTrigger(
			className, className, null, null, 2, TimeUnit.DAY);

		SchedulerEntry schedulerEntry = new SchedulerEntryImpl(
			className, trigger);

		_schedulerEngineHelper.register(
			this, schedulerEntry, DestinationNames.SCHEDULER_DISPATCH);
	}

	@Deactivate
	protected void deactivate() {
		_schedulerEngineHelper.unregister(this);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		_companyLocalService.forEachCompanyId(
			companyId -> _deleteExpiredBackGroundTasks(companyId));
	}

	private void _deleteExpiredBackGroundTasks(long companyId)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			_backgroundTaskLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				dynamicQuery.add(
					RestrictionsFactoryUtil.eq("companyId", companyId));

				Property taskExecutorClassName = PropertyFactoryUtil.forName(
					"taskExecutorClassName");

				dynamicQuery.add(
					taskExecutorClassName.in(_BACKGROUND_TASK_CLASS_NAMES));

				Property status = PropertyFactoryUtil.forName("status");

				dynamicQuery.add(status.in(_STATUSES));

				//TODO: replace 2 with _exportImportServiceConfiguration.exportImportCheckInterval()
				long exportImportCheckInterval = 2 * Time.DAY;

				Date expirationDate = new Date(
					System.currentTimeMillis() - exportImportCheckInterval);

				Property modifiedDate = PropertyFactoryUtil.forName(
					"modifiedDate");

				dynamicQuery.add(modifiedDate.le(expirationDate));
			});

		actionableDynamicQuery.setPerformActionMethod(
			backgroundTask -> _backgroundTaskLocalService.deleteBackgroundTask(
				(BackgroundTask)backgroundTask));

		actionableDynamicQuery.performActions();
	}

	private static final String[] _BACKGROUND_TASK_CLASS_NAMES = {
		BackgroundTaskExecutorNames.LAYOUT_EXPORT_BACKGROUND_TASK_EXECUTOR,
		BackgroundTaskExecutorNames.LAYOUT_IMPORT_BACKGROUND_TASK_EXECUTOR,
		BackgroundTaskExecutorNames.PORTLET_EXPORT_BACKGROUND_TASK_EXECUTOR,
		BackgroundTaskExecutorNames.PORTLET_IMPORT_BACKGROUND_TASK_EXECUTOR
	};

	private static final int[] _STATUSES = {
		BackgroundTaskConstants.STATUS_CANCELLED,
		BackgroundTaskConstants.STATUS_FAILED,
		BackgroundTaskConstants.STATUS_SUCCESSFUL
	};

	@Reference
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	private volatile ExportImportServiceConfiguration
		_exportImportServiceConfiguration;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private TriggerFactory _triggerFactory;

}