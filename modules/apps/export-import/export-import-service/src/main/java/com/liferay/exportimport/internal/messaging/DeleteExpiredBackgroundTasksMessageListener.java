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
import com.liferay.exportimport.configuration.ExportImportSystemConfiguration;
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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
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
 * @author Ha Tang
 */
@Component(
	configurationPid = {
		"com.liferay.exportimport.configuration.ExportImportServiceConfiguration",
		"com.liferay.exportimport.configuration.ExportImportSystemConfiguration"
	},
	service = {}
)
public class DeleteExpiredBackgroundTasksMessageListener
	extends BaseMessageListener {

	@Activate
	protected void activate(Map<String, Object> properties) {
		_exportImportServiceConfiguration = ConfigurableUtil.createConfigurable(
			ExportImportServiceConfiguration.class, properties);
		_exportImportSystemConfiguration = ConfigurableUtil.createConfigurable(
			ExportImportSystemConfiguration.class, properties);

		Class<?> clazz = getClass();

		String className = clazz.getName();

		Trigger trigger = _triggerFactory.createTrigger(
			className, className, null, null,
			_exportImportSystemConfiguration.cleanupJobInterval(),
			TimeUnit.MINUTE);

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

		ExportImportServiceConfiguration
			companyExportImportServiceConfiguration =
				_getExportImportServiceConfiguration(companyId);

		int exportImportEntryExpiryDays =
			companyExportImportServiceConfiguration.
				exportImportEntryExpiryDays();

		if (exportImportEntryExpiryDays <= 0) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"exportImportEntryExpiryDays=" +
						exportImportEntryExpiryDays +
							": no cleanup on this instance.");
			}

			return;
		}

		ActionableDynamicQuery actionableDynamicQuery =
			_backgroundTaskLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				dynamicQuery.add(
					RestrictionsFactoryUtil.eq("companyId", companyId));

				Property taskExecutorClassName = PropertyFactoryUtil.forName(
					"taskExecutorClassName");

				dynamicQuery.add(
					taskExecutorClassName.in(_BACKGROUND_TASK_EXECUTOR_NAMES));

				Property status = PropertyFactoryUtil.forName("status");

				dynamicQuery.add(status.in(_STATUSES));

				long exportImportExpiryTime =
					exportImportEntryExpiryDays * Time.DAY;

				Date expirationDate = new Date(
					System.currentTimeMillis() - exportImportExpiryTime);

				Property modifiedDate = PropertyFactoryUtil.forName(
					"modifiedDate");

				dynamicQuery.add(modifiedDate.lt(expirationDate));
			});

		actionableDynamicQuery.setPerformActionMethod(
			(BackgroundTask backgroundTask) -> {
				try {
					_backgroundTaskLocalService.deleteBackgroundTask(
						backgroundTask);
				}
				catch (PortalException portalException) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable delete backgroundTask " +
								backgroundTask.getBackgroundTaskId(),
							portalException);
					}
				}
			});

		try {
			actionableDynamicQuery.performActions();
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}
	}

	private ExportImportServiceConfiguration
		_getExportImportServiceConfiguration(long companyId) {

		try {
			return _configurationProvider.getCompanyConfiguration(
				ExportImportServiceConfiguration.class, companyId);
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException);

			return _exportImportServiceConfiguration;
		}
	}

	private static final String[] _BACKGROUND_TASK_EXECUTOR_NAMES = {
		BackgroundTaskExecutorNames.LAYOUT_EXPORT_BACKGROUND_TASK_EXECUTOR,
		BackgroundTaskExecutorNames.PORTLET_EXPORT_BACKGROUND_TASK_EXECUTOR
	};

	private static final int[] _STATUSES = {
		BackgroundTaskConstants.STATUS_SUCCESSFUL
	};

	private static final Log _log = LogFactoryUtil.getLog(
		DeleteExpiredBackgroundTasksMessageListener.class);

	@Reference
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	private volatile ExportImportServiceConfiguration
		_exportImportServiceConfiguration;
	private volatile ExportImportSystemConfiguration
		_exportImportSystemConfiguration;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private TriggerFactory _triggerFactory;

}