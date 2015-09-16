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
import com.liferay.exportimport.web.constants.ExportImportPortletKeys;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Order;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.messaging.BaseSchedulerEntryMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerType;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portlet.exportimport.model.ExportImportConfiguration;
import com.liferay.portlet.exportimport.service.ExportImportConfigurationLocalServiceUtil;

import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Levente Hudák
 * @author Daniel Kocsis
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + ExportImportPortletKeys.EXPORT_IMPORT},
	service = SchedulerEntry.class
)
public class DraftExportImportConfigurationMessageListener
	extends BaseSchedulerEntryMessageListener {

	@Activate
	protected void activate() {
		schedulerEntry.setTimeUnit(TimeUnit.HOUR);
		schedulerEntry.setTriggerType(TriggerType.SIMPLE);
		schedulerEntry.setTriggerValue(
			ExportImportWebConfigurationValues.
				DRAFT_EXPORT_IMPORT_CONFIGURATION_CHECK_INTERVAL);
	}

	@Override
	protected void doReceive(Message message) throws PortalException {
		if (ExportImportWebConfigurationValues.
				DRAFT_EXPORT_IMPORT_CONFIGURATION_CLEAN_UP_COUNT == -1) {

			return;
		}

		DynamicQuery dynamicQuery =
			ExportImportConfigurationLocalServiceUtil.dynamicQuery();

		Property property = PropertyFactoryUtil.forName("status");

		dynamicQuery.add(property.eq(WorkflowConstants.STATUS_DRAFT));

		Order order = OrderFactoryUtil.asc("createDate");

		dynamicQuery.addOrder(order);

		dynamicQuery.setLimit(
			QueryUtil.ALL_POS,
			ExportImportWebConfigurationValues.
				DRAFT_EXPORT_IMPORT_CONFIGURATION_CLEAN_UP_COUNT);

		List<ExportImportConfiguration> exportImportConfigurations =
			ExportImportConfigurationLocalServiceUtil.dynamicQuery(
				dynamicQuery);

		for (ExportImportConfiguration exportImportConfiguration :
				exportImportConfigurations) {

			List<BackgroundTask> backgroundTasks = getParentBackgroundTasks(
				exportImportConfiguration);

			if (ListUtil.isEmpty(backgroundTasks)) {
				ExportImportConfigurationLocalServiceUtil.
					deleteExportImportConfiguration(exportImportConfiguration);

				continue;
			}

			// BackgroundTaskModelListener deletes the linked configuration
			// automatically

			for (BackgroundTask backgroundTask : backgroundTasks) {
				BackgroundTaskLocalServiceUtil.deleteBackgroundTask(
					backgroundTask.getBackgroundTaskId());
			}
		}
	}

	protected List<BackgroundTask> getParentBackgroundTasks(
			ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		DynamicQuery dynamicQuery =
			BackgroundTaskLocalServiceUtil.dynamicQuery();

		Property property = PropertyFactoryUtil.forName("taskContextMap");

		StringBundler sb = new StringBundler(7);

		sb.append(StringPool.PERCENT);
		sb.append(StringPool.QUOTE);
		sb.append("exportImportConfigurationId");
		sb.append(StringPool.QUOTE);
		sb.append(StringPool.COLON);
		sb.append(exportImportConfiguration.getExportImportConfigurationId());
		sb.append(StringPool.PERCENT);

		dynamicQuery.add(property.like(sb.toString()));

		property = PropertyFactoryUtil.forName("completed");

		dynamicQuery.add(property.eq(Boolean.TRUE));

		return BackgroundTaskLocalServiceUtil.dynamicQuery(dynamicQuery);
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(
		target = "(javax.portlet.name=" + ExportImportPortletKeys.EXPORT_IMPORT + ")"
	)
	protected void setPortlet(Portlet portlet) {
	}

}