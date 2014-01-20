/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.ExportImportConfigurationLocalServiceBaseImpl;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Daniel Kocsis
 */
public class ExportImportConfigurationLocalServiceImpl
	extends ExportImportConfigurationLocalServiceBaseImpl {

	@Override
	public ExportImportConfiguration addConfiguration(
			long userId, long groupId, String name, String description,
			int type, Map<String, Serializable> settingsMap,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		final long configurationId = counterLocalService.increment();

		ExportImportConfiguration configuration =
			exportImportConfigurationPersistence.create(configurationId);

		configuration.setGroupId(groupId);
		configuration.setCompanyId(user.getCompanyId());
		configuration.setUserId(userId);
		configuration.setUserName(user.getFullName());
		configuration.setCreateDate(serviceContext.getCreateDate(now));
		configuration.setModifiedDate(serviceContext.getModifiedDate(now));
		configuration.setName(name);
		configuration.setDescription(description);
		configuration.setType(type);

		if (settingsMap != null) {
			String settingsString = JSONFactoryUtil.serialize(settingsMap);

			configuration.setSettings(settingsString);
		}

		return exportImportConfigurationPersistence.update(configuration);
	}

	@Override
	public void deleteGroupConfigurations(long groupId)
		throws PortalException, SystemException {

		List<ExportImportConfiguration> configurations =
			exportImportConfigurationPersistence.findByGroupId(groupId);

		for (ExportImportConfiguration configuration : configurations) {
			exportImportConfigurationPersistence.remove(configuration);
		}
	}

	@Override
	public ExportImportConfiguration getConfiguration(long configurationId)
		throws PortalException, SystemException {

		return exportImportConfigurationPersistence.findByPrimaryKey(
			configurationId);
	}

	@Override
	public List<ExportImportConfiguration> getConfigurations(
			long groupId, int type)
		throws PortalException, SystemException {

		return exportImportConfigurationPersistence.findByG_T(groupId, type);
	}

	@Override
	public List<ExportImportConfiguration> getConfigurations(
			long groupId, int type, int start, int end,
			OrderByComparator orderByComparator)
		throws PortalException, SystemException {

		return exportImportConfigurationPersistence.findByG_T(
			groupId, type, start, end, orderByComparator);
	}

	@Override
	public int getConfigurationsCount(long groupId, int type)
		throws PortalException, SystemException {

		return exportImportConfigurationPersistence.countByG_T(groupId, type);
	}

	@Override
	public ExportImportConfiguration updateConfiguration(
			long configurationId, String name, String description,
			Map<String, Serializable> settingsMap,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		ExportImportConfiguration configuration =
			exportImportConfigurationPersistence.findByPrimaryKey(
				configurationId);

		configuration.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		configuration.setName(name);
		configuration.setDescription(description);

		if (settingsMap != null) {
			String settingsString = JSONFactoryUtil.serialize(settingsMap);

			configuration.setSettings(settingsString);
		}

		return exportImportConfigurationPersistence.update(configuration);
	}

}