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

package com.liferay.exportimport.web.messaging;

import com.liferay.exportimport.web.configuration.ExportImportWebConfigurationValues;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Order;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.messaging.BaseSchedulerEntryMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.scheduler.TriggerFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portlet.exportimport.configuration.ExportImportConfigurationConstants;
import com.liferay.portlet.exportimport.model.ExportImportConfiguration;
import com.liferay.portlet.exportimport.service.ExportImportConfigurationLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Levente Hudák
 * @author Daniel Kocsis
 */
@Component(
	immediate = true,
	service = DraftExportImportConfigurationMessageListener.class
)
public class DraftExportImportConfigurationMessageListener
	extends BaseSchedulerEntryMessageListener {

	@Activate
	protected void activate() {
		schedulerEntryImpl.setTrigger(
			TriggerFactoryUtil.createTrigger(
				getEventListenerClass(), getEventListenerClass(),
				ExportImportWebConfigurationValues.
					DRAFT_EXPORT_IMPORT_CONFIGURATION_CHECK_INTERVAL,
				TimeUnit.HOUR));

		_schedulerEngineHelper.register(
			this, schedulerEntryImpl, DestinationNames.SCHEDULER_DISPATCH);
	}

	@Deactivate
	protected void deactivate() {
		_schedulerEngineHelper.unregister(this);
	}

	@Override
	protected void doReceive(Message message) throws PortalException {
		if (ExportImportWebConfigurationValues.
				DRAFT_EXPORT_IMPORT_CONFIGURATION_CLEAN_UP_COUNT == -1) {

			return;
		}

		DynamicQuery dynamicQuery =
			_exportImportConfigurationLocalService.dynamicQuery();

		Property typeProperty = PropertyFactoryUtil.forName("type");

		dynamicQuery.add(
			typeProperty.ne(
				ExportImportConfigurationConstants.
					TYPE_SCHEDULED_PUBLISH_LAYOUT_LOCAL));
		dynamicQuery.add(
			typeProperty.ne(
				ExportImportConfigurationConstants.
					TYPE_SCHEDULED_PUBLISH_LAYOUT_REMOTE));

		Property statusProperty = PropertyFactoryUtil.forName("status");

		dynamicQuery.add(statusProperty.eq(WorkflowConstants.STATUS_DRAFT));

		Order order = OrderFactoryUtil.asc("createDate");

		dynamicQuery.addOrder(order);

		dynamicQuery.setLimit(
			QueryUtil.ALL_POS,
			ExportImportWebConfigurationValues.
				DRAFT_EXPORT_IMPORT_CONFIGURATION_CLEAN_UP_COUNT);

		List<ExportImportConfiguration> exportImportConfigurations =
			_exportImportConfigurationLocalService.dynamicQuery(dynamicQuery);

		for (ExportImportConfiguration exportImportConfiguration :
				exportImportConfigurations) {

			List<BackgroundTask> backgroundTasks = getParentBackgroundTasks(
				exportImportConfiguration);

			if (ListUtil.isEmpty(backgroundTasks)) {
				_exportImportConfigurationLocalService.
					deleteExportImportConfiguration(exportImportConfiguration);

				continue;
			}

			// BackgroundTaskModelListener deletes the linked configuration
			// automatically

			for (BackgroundTask backgroundTask : backgroundTasks) {
				if (isLiveGroup(backgroundTask.getGroupId())) {
					continue;
				}

				_backgroundTaskLocalService.deleteBackgroundTask(
					backgroundTask.getBackgroundTaskId());
			}
		}
	}

	protected List<BackgroundTask> getParentBackgroundTasks(
			ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		DynamicQuery dynamicQuery = _backgroundTaskLocalService.dynamicQuery();

		Property completedProperty = PropertyFactoryUtil.forName("completed");

		dynamicQuery.add(completedProperty.eq(Boolean.TRUE));

		Property taskContextMapProperty = PropertyFactoryUtil.forName(
			"taskContextMap");

		StringBundler sb = new StringBundler(7);

		sb.append(StringPool.PERCENT);
		sb.append(StringPool.QUOTE);
		sb.append("exportImportConfigurationId");
		sb.append(StringPool.QUOTE);
		sb.append(StringPool.COLON);
		sb.append(exportImportConfiguration.getExportImportConfigurationId());
		sb.append(StringPool.PERCENT);

		dynamicQuery.add(taskContextMapProperty.like(sb.toString()));

		return _backgroundTaskLocalService.dynamicQuery(dynamicQuery);
	}

	protected boolean isLiveGroup(long groupId) {
		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return false;
		}

		if (group.hasStagingGroup()) {
			return true;
		}

		return false;
	}

	@Reference(unbind = "-")
	protected void setBackgroundTaskLocalService(
		BackgroundTaskLocalService backgroundTaskLocalService) {

		_backgroundTaskLocalService = backgroundTaskLocalService;
	}

	@Reference(unbind = "-")
	protected void setExportImportConfigurationLocalService(
		ExportImportConfigurationLocalService
			exportImportConfigurationLocalService) {

		_exportImportConfigurationLocalService =
			exportImportConfigurationLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(unbind = "-")
	protected void setSchedulerEngineHelper(
		SchedulerEngineHelper schedulerEngineHelper) {

		_schedulerEngineHelper = schedulerEngineHelper;
	}

	@Reference(unbind = "-")
	protected void setTriggerFactory(TriggerFactory triggerFactory) {
	}

	private BackgroundTaskLocalService _backgroundTaskLocalService;
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;
	private GroupLocalService _groupLocalService;
	private SchedulerEngineHelper _schedulerEngineHelper;

}