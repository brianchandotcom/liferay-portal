/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.web.internal.scheduler;

import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.exportimport.web.internal.configuration.ExportImportWebConfigurationValues;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Order;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Levente Hudák
 * @author Daniel Kocsis
 */
@Component(service = SchedulerJobConfiguration.class)
public class DraftExportImportConfigurationSchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
		return () -> {
			if (ExportImportWebConfigurationValues.
					DRAFT_EXPORT_IMPORT_CONFIGURATION_CLEAN_UP_COUNT == -1) {

				return;
			}

			Date lastCreateDate;

			if (ExportImportWebConfigurationValues.
					DRAFT_EXPORT_IMPORT_CONFIGURATION_CLEAN_UP_COUNT == 0) {

				lastCreateDate = new Date();
			}
			else {
				DynamicQuery dynamicQuery =
					_exportImportConfigurationLocalService.dynamicQuery();

				_addCommonCriterions(dynamicQuery);

				Order order = OrderFactoryUtil.desc("createDate");

				dynamicQuery.addOrder(order);

				dynamicQuery.setLimit(
					QueryUtil.ALL_POS,
					ExportImportWebConfigurationValues.
						DRAFT_EXPORT_IMPORT_CONFIGURATION_CLEAN_UP_COUNT);

				dynamicQuery.setProjection(
					ProjectionFactoryUtil.property("createDate"));

				List<Date> createDates =
					_exportImportConfigurationLocalService.dynamicQuery(
						dynamicQuery);

				if (ListUtil.isEmpty(createDates)) {
					return;
				}

				lastCreateDate = createDates.get(createDates.size() - 1);
			}

			ActionableDynamicQuery actionableDynamicQuery =
				_exportImportConfigurationLocalService.
					getActionableDynamicQuery();

			Property createDate = PropertyFactoryUtil.forName("createDate");

			actionableDynamicQuery.setAddCriteriaMethod(
				dynamicQuery -> {
					_addCommonCriterions(dynamicQuery);

					dynamicQuery.add(createDate.lt(lastCreateDate));
				});

			actionableDynamicQuery.setPerformActionMethod(
				(ExportImportConfiguration exportImportConfiguration) -> {
					List<BackgroundTask> backgroundTasks =
						_getParentBackgroundTasks(exportImportConfiguration);

					if (ListUtil.isEmpty(backgroundTasks)) {
						_exportImportConfigurationLocalService.
							deleteExportImportConfiguration(
								exportImportConfiguration);

						return;
					}

					// BackgroundTaskModelListener deletes the linked
					// configuration automatically

					for (BackgroundTask backgroundTask : backgroundTasks) {
						if (_isLiveGroup(backgroundTask.getGroupId())) {
							continue;
						}

						_backgroundTaskLocalService.deleteBackgroundTask(
							backgroundTask.getBackgroundTaskId());
					}
				});

			actionableDynamicQuery.performActions();
		};
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(
			ExportImportWebConfigurationValues.
				DRAFT_EXPORT_IMPORT_CONFIGURATION_CHECK_INTERVAL,
			TimeUnit.HOUR);
	}

	private void _addCommonCriterions(DynamicQuery dynamicQuery) {
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
	}

	private List<BackgroundTask> _getParentBackgroundTasks(
			ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		DynamicQuery dynamicQuery = _backgroundTaskLocalService.dynamicQuery();

		Property completedProperty = PropertyFactoryUtil.forName("completed");

		dynamicQuery.add(completedProperty.eq(Boolean.TRUE));

		Property taskContextMapProperty = PropertyFactoryUtil.forName(
			"taskContextMap");

		dynamicQuery.add(
			taskContextMapProperty.like(
				StringBundler.concat(
					"%\"exportImportConfigurationId\":",
					exportImportConfiguration.getExportImportConfigurationId(),
					StringPool.PERCENT)));

		return _backgroundTaskLocalService.dynamicQuery(dynamicQuery);
	}

	private boolean _isLiveGroup(long groupId) {
		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return false;
		}

		if (group.hasStagingGroup()) {
			return true;
		}

		return false;
	}

	@Reference
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@Reference
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}