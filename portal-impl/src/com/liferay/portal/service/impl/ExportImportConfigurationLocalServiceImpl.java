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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.ExportImportConfigurationLocalServiceBaseImpl;
import com.liferay.portlet.trash.model.TrashEntry;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Daniel Kocsis
 */
public class ExportImportConfigurationLocalServiceImpl
	extends ExportImportConfigurationLocalServiceBaseImpl {

	@Override
	public ExportImportConfiguration addExportImportConfiguration(
			long userId, long groupId, String name, String description,
			int type, Map<String, Serializable> settingsMap, int status,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		long exportImportConfigurationId = counterLocalService.increment();

		ExportImportConfiguration exportImportConfiguration =
			exportImportConfigurationPersistence.create(
				exportImportConfigurationId);

		exportImportConfiguration.setGroupId(groupId);
		exportImportConfiguration.setCompanyId(user.getCompanyId());
		exportImportConfiguration.setUserId(userId);
		exportImportConfiguration.setUserName(user.getFullName());
		exportImportConfiguration.setCreateDate(
			serviceContext.getCreateDate(now));
		exportImportConfiguration.setModifiedDate(
			serviceContext.getModifiedDate(now));
		exportImportConfiguration.setName(name);
		exportImportConfiguration.setDescription(description);
		exportImportConfiguration.setType(type);

		if (settingsMap != null) {
			String settings = JSONFactoryUtil.serialize(settingsMap);

			exportImportConfiguration.setSettings(settings);
		}

		exportImportConfiguration.setStatus(status);
		exportImportConfiguration.setStatusByUserId(userId);
		exportImportConfiguration.setStatusByUserName(user.getScreenName());
		exportImportConfiguration.setStatusDate(now);

		return exportImportConfigurationPersistence.update(
			exportImportConfiguration);
	}

	@Override
	public ExportImportConfiguration addExportImportConfiguration(
			long userId, long groupId, String name, String description,
			int type, Map<String, Serializable> settingsMap,
			ServiceContext serviceContext)
		throws PortalException {

		return addExportImportConfiguration(
			userId, groupId, name, description, type, settingsMap,
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public ExportImportConfiguration deleteExportImportConfiguration(
			ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		exportImportConfigurationPersistence.remove(exportImportConfiguration);

		trashEntryLocalService.deleteEntry(
			ExportImportConfiguration.class.getName(),
			exportImportConfiguration.getExportImportConfigurationId());

		return exportImportConfiguration;
	}

	@Override
	public ExportImportConfiguration deleteExportImportConfiguration(
			long exportImportConfigurationId)
		throws PortalException {

		ExportImportConfiguration exportImportConfiguration =
			exportImportConfigurationPersistence.findByPrimaryKey(
				exportImportConfigurationId);

		return exportImportConfigurationLocalService.
			deleteExportImportConfiguration(exportImportConfiguration);
	}

	@Override
	public void deleteExportImportConfigurations(long groupId)
		throws PortalException {

		List<ExportImportConfiguration> exportImportConfigurations =
			exportImportConfigurationPersistence.findByGroupId(groupId);

		for (ExportImportConfiguration exportImportConfiguration :
				exportImportConfigurations) {

			exportImportConfigurationLocalService.
				deleteExportImportConfiguration(exportImportConfiguration);
		}
	}

	@Override
	public List<ExportImportConfiguration> getExportImportConfigurations(
		long groupId, int type) {

		return exportImportConfigurationPersistence.findByG_T_S(
			groupId, type, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public List<ExportImportConfiguration> getExportImportConfigurations(
		long groupId, int type, int start, int end,
		OrderByComparator orderByComparator) {

		return exportImportConfigurationPersistence.findByG_T_S(
			groupId, type, WorkflowConstants.STATUS_APPROVED, start, end,
			orderByComparator);
	}

	@Override
	public int getExportImportConfigurationsCount(long groupId) {
		return exportImportConfigurationPersistence.countByG_S(
			groupId, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public int getExportImportConfigurationsCount(long groupId, int type) {
		return exportImportConfigurationPersistence.countByG_T_S(
			groupId, type, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public ExportImportConfiguration moveExportImportConfigurationToTrash(
			long userId, long exportImportConfigurationId)
		throws PortalException {

		ExportImportConfiguration exportImportConfiguration =
			exportImportConfigurationPersistence.findByPrimaryKey(
				exportImportConfigurationId);

		int oldStatus = exportImportConfiguration.getStatus();

		exportImportConfiguration = updateStatus(
			userId, exportImportConfiguration.getExportImportConfigurationId(),
			WorkflowConstants.STATUS_IN_TRASH);

		trashEntryLocalService.addTrashEntry(
			userId, exportImportConfiguration.getGroupId(),
			ExportImportConfiguration.class.getName(),
			exportImportConfiguration.getExportImportConfigurationId(), null,
			null, oldStatus, null, null);

		return exportImportConfiguration;
	}

	@Override
	public ExportImportConfiguration restoreExportImportConfigurationFromTrash(
			long userId, long exportImportConfigurationId)
		throws PortalException {

		ExportImportConfiguration exportImportConfiguration =
			exportImportConfigurationPersistence.findByPrimaryKey(
				exportImportConfigurationId);

		TrashEntry trashEntry = trashEntryLocalService.getEntry(
			ExportImportConfiguration.class.getName(),
			exportImportConfigurationId);

		exportImportConfiguration = updateStatus(
			userId, exportImportConfiguration.getExportImportConfigurationId(),
			trashEntry.getStatus());

		trashEntryLocalService.deleteEntry(
			ExportImportConfiguration.class.getName(),
			exportImportConfiguration.getExportImportConfigurationId());

		return exportImportConfiguration;
	}

	@Override
	public ExportImportConfiguration updateExportImportConfiguration(
			long userId, long exportImportConfigurationId, String name,
			String description, Map<String, Serializable> settingsMap,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		ExportImportConfiguration exportImportConfiguration =
			exportImportConfigurationPersistence.findByPrimaryKey(
				exportImportConfigurationId);

		exportImportConfiguration.setUserId(userId);
		exportImportConfiguration.setUserName(user.getFullName());
		exportImportConfiguration.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		exportImportConfiguration.setName(name);
		exportImportConfiguration.setDescription(description);

		if (settingsMap != null) {
			String settings = JSONFactoryUtil.serialize(settingsMap);

			exportImportConfiguration.setSettings(settings);
		}

		return exportImportConfigurationPersistence.update(
			exportImportConfiguration);
	}

	@Override
	public ExportImportConfiguration updateStatus(
			long userId, long exportImportConfigurationId, int status)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		ExportImportConfiguration exportImportConfiguration =
			exportImportConfigurationPersistence.findByPrimaryKey(
				exportImportConfigurationId);

		exportImportConfiguration.setStatus(status);
		exportImportConfiguration.setStatusByUserId(userId);
		exportImportConfiguration.setStatusByUserName(user.getScreenName());
		exportImportConfiguration.setStatusDate(new Date());

		exportImportConfigurationPersistence.update(exportImportConfiguration);

		return exportImportConfiguration;
	}

}