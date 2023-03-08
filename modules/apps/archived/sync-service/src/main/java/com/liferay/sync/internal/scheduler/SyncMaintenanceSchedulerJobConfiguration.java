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

package com.liferay.sync.internal.scheduler;

import com.liferay.document.library.sync.model.DLSyncEvent;
import com.liferay.document.library.sync.service.DLSyncEventLocalService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.sync.internal.configuration.SyncServiceConfigurationValues;
import com.liferay.sync.model.SyncDLObject;
import com.liferay.sync.service.SyncDLFileVersionDiffLocalService;
import com.liferay.sync.service.SyncDLObjectLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dennis Ju
 */
@Component(service = SchedulerJobConfiguration.class)
public class SyncMaintenanceSchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	public static final String DESTINATION_NAME =
		"liferay/sync_maintenance_processor";

	@Override
	public String getDestinationName() {
		return DESTINATION_NAME;
	}

	@Override
	public UnsafeRunnable<Exception> getJobExecutor() {
		return () -> {
			_syncDLFileVersionDiffLocalService.
				deleteExpiredSyncDLFileVersionDiffs();

			if (SyncServiceConfigurationValues.SYNC_FILE_DIFF_CACHE_ENABLED) {
				try {
					_syncDLFileVersionDiffLocalService.
						deleteExpiredSyncDLFileVersionDiffs();
				}
				catch (Exception exception) {
					_log.error(exception);
				}
			}

			try {
				ActionableDynamicQuery actionableDynamicQuery =
					_dlSyncEventLocalService.getActionableDynamicQuery();

				actionableDynamicQuery.setAddCriteriaMethod(
					dynamicQuery -> {
						Property modifiedTimeProperty =
							PropertyFactoryUtil.forName("modifiedTime");

						dynamicQuery.add(
							modifiedTimeProperty.le(
								_syncDLObjectLocalService.
									getLatestModifiedTime()));
					});
				actionableDynamicQuery.setPerformActionMethod(
					(DLSyncEvent dlSyncEvent) -> {
						SyncDLObject syncDLObject =
							_syncDLObjectLocalService.fetchSyncDLObject(
								dlSyncEvent.getType(), dlSyncEvent.getTypePK());

						if ((syncDLObject == null) ||
							(dlSyncEvent.getModifiedTime() >
								syncDLObject.getModifiedTime())) {

							TransactionCommitCallbackUtil.registerCallback(
								() -> {
									Message dlSyncEventMessage = new Message();

									long lastModifiedTime =
										_syncDLObjectLocalService.
											getLatestModifiedTime();

									dlSyncEventMessage.setValues(
										HashMapBuilder.<String, Object>put(
											"event", dlSyncEvent.getEvent()
										).put(
											"modifiedTime", ++lastModifiedTime
										).put(
											"type", dlSyncEvent.getType()
										).put(
											"typePK", dlSyncEvent.getTypePK()
										).build());

									MessageBusUtil.sendMessage(
										DestinationNames.
											DOCUMENT_LIBRARY_SYNC_EVENT_PROCESSOR,
										dlSyncEventMessage);

									return null;
								});
						}
						else {
							_dlSyncEventLocalService.deleteDLSyncEvent(
								dlSyncEvent);
						}
					});

				actionableDynamicQuery.performActions();
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		};
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(
			1, TimeUnit.HOUR);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SyncMaintenanceSchedulerJobConfiguration.class);

	@Reference
	private DLSyncEventLocalService _dlSyncEventLocalService;

	@Reference
	private SyncDLFileVersionDiffLocalService
		_syncDLFileVersionDiffLocalService;

	@Reference
	private SyncDLObjectLocalService _syncDLObjectLocalService;

}