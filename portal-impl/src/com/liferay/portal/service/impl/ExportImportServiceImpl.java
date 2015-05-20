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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.MissingReferences;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.base.ExportImportServiceBaseImpl;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import java.util.Date;
import java.util.Map;

/**
 * @author Daniel Kocsis
 */
@ProviderType
public class ExportImportServiceImpl extends ExportImportServiceBaseImpl {

	@Override
	public File exportLayoutsAsFile(
			ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		long sourceGroupId = MapUtil.getLong(settingsMap, "sourceGroupId");

		GroupPermissionUtil.check(
			getPermissionChecker(), sourceGroupId,
			ActionKeys.EXPORT_IMPORT_LAYOUTS);

		return exportImportLocalService.exportLayoutsAsFile(
			exportImportConfiguration);
	}

	@Override
	public long exportLayoutsAsFileInBackground(
			ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		GroupPermissionUtil.check(
			getPermissionChecker(), exportImportConfiguration.getGroupId(),
			ActionKeys.EXPORT_IMPORT_LAYOUTS);

		return exportImportLocalService.exportLayoutsAsFileInBackground(
			getUserId(), exportImportConfiguration);
	}

	@Override
	public long exportLayoutsAsFileInBackground(
			long exportImportConfigurationId)
		throws PortalException {

		ExportImportConfiguration exportImportConfiguration =
			exportImportConfigurationLocalService.getExportImportConfiguration(
				exportImportConfigurationId);

		GroupPermissionUtil.check(
			getPermissionChecker(), exportImportConfiguration.getGroupId(),
			ActionKeys.EXPORT_IMPORT_LAYOUTS);

		return exportImportLocalService.exportLayoutsAsFileInBackground(
			getUserId(), exportImportConfigurationId);
	}

	@Override
	public long exportLayoutsAsFileInBackground(
			String taskName, long groupId, boolean privateLayout,
			long[] layoutIds, Map<String, String[]> parameterMap,
			Date startDate, Date endDate)
		throws PortalException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.EXPORT_IMPORT_LAYOUTS);

		return exportImportLocalService.exportLayoutsAsFileInBackground(
			getUserId(), taskName, groupId, privateLayout, layoutIds,
			parameterMap, startDate, endDate);
	}

	@Override
	public File exportPortletInfoAsFile(
			ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		long sourcePlid = MapUtil.getLong(settingsMap, "sourcePlid");

		Layout layout = layoutLocalService.getLayout(sourcePlid);

		GroupPermissionUtil.check(
			getPermissionChecker(), layout.getGroupId(),
			ActionKeys.EXPORT_IMPORT_PORTLET_INFO);

		return exportImportLocalService.exportPortletInfoAsFile(
			exportImportConfiguration);
	}

	@Override
	public long exportPortletInfoAsFileInBackground(
			String taskName, long plid, long groupId, String portletId,
			Map<String, String[]> parameterMap, Date startDate, Date endDate,
			String fileName)
		throws PortalException {

		Layout layout = layoutLocalService.getLayout(plid);

		GroupPermissionUtil.check(
			getPermissionChecker(), layout.getGroupId(),
			ActionKeys.EXPORT_IMPORT_PORTLET_INFO);

		return exportImportLocalService.exportPortletInfoAsFileInBackground(
			getUserId(), taskName, plid, groupId, portletId, parameterMap,
			startDate, endDate, fileName);
	}

	@Override
	public long exportPortletInfoAsFileInBackground(
			String taskName, String portletId,
			Map<String, String[]> parameterMap, Date startDate, Date endDate,
			String fileName)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(getUserId());

		Group companyGroup = groupLocalService.getCompanyGroup(
			user.getCompanyId());

		GroupPermissionUtil.check(
			getPermissionChecker(), companyGroup,
			ActionKeys.EXPORT_IMPORT_PORTLET_INFO);

		return exportImportLocalService.exportPortletInfoAsFileInBackground(
			getUserId(), taskName, portletId, parameterMap, startDate, endDate,
			fileName);
	}

	@Override
	public void importLayouts(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException {

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		long targetGroupId = MapUtil.getLong(settingsMap, "targetGroupId");

		GroupPermissionUtil.check(
			getPermissionChecker(), targetGroupId,
			ActionKeys.EXPORT_IMPORT_LAYOUTS);

		exportImportLocalService.importLayouts(exportImportConfiguration, file);
	}

	@Override
	public void importLayouts(
			ExportImportConfiguration exportImportConfiguration, InputStream is)
		throws PortalException {

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		long targetGroupId = MapUtil.getLong(settingsMap, "targetGroupId");

		GroupPermissionUtil.check(
			getPermissionChecker(), targetGroupId,
			ActionKeys.EXPORT_IMPORT_LAYOUTS);

		exportImportLocalService.importLayouts(exportImportConfiguration, is);
	}

	@Override
	public long importLayoutsInBackground(
			String taskName, long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, File file)
		throws PortalException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.EXPORT_IMPORT_LAYOUTS);

		return exportImportLocalService.importLayoutsInBackground(
			getUserId(), taskName, groupId, privateLayout, parameterMap, file);
	}

	@Override
	public long importLayoutsInBackground(
			String taskName, long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, InputStream inputStream)
		throws PortalException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.EXPORT_IMPORT_LAYOUTS);

		return exportImportLocalService.importLayoutsInBackground(
			getUserId(), taskName, groupId, privateLayout, parameterMap,
			inputStream);
	}

	@Override
	public void importPortletInfo(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException {

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		long targetGroupId = MapUtil.getLong(settingsMap, "targetGroupId");

		GroupPermissionUtil.check(
			getPermissionChecker(), targetGroupId,
			ActionKeys.EXPORT_IMPORT_PORTLET_INFO);

		exportImportLocalService.importPortletInfo(
			exportImportConfiguration, file);
	}

	@Override
	public void importPortletInfo(
			ExportImportConfiguration exportImportConfiguration, InputStream is)
		throws PortalException {

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		long targetGroupId = MapUtil.getLong(settingsMap, "targetGroupId");

		GroupPermissionUtil.check(
			getPermissionChecker(), targetGroupId,
			ActionKeys.EXPORT_IMPORT_PORTLET_INFO);

		exportImportLocalService.importPortletInfo(
			exportImportConfiguration, is);
	}

	@Override
	public long importPortletInfoInBackground(
			String taskName, long plid, long groupId, String portletId,
			Map<String, String[]> parameterMap, File file)
		throws PortalException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId,
			ActionKeys.EXPORT_IMPORT_PORTLET_INFO);

		return exportImportLocalService.importPortletInfoInBackground(
			getUserId(), taskName, plid, groupId, portletId, parameterMap,
			file);
	}

	@Override
	public long importPortletInfoInBackground(
			String taskName, long plid, long groupId, String portletId,
			Map<String, String[]> parameterMap, InputStream is)
		throws PortalException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId,
			ActionKeys.EXPORT_IMPORT_PORTLET_INFO);

		return exportImportLocalService.importPortletInfoInBackground(
			getUserId(), taskName, plid, groupId, portletId, parameterMap, is);
	}

	@Override
	public void importPortletInfoInBackground(
			String taskName, String portletId,
			Map<String, String[]> parameterMap, File file)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(getUserId());

		Group companyGroup = groupLocalService.getCompanyGroup(
			user.getCompanyId());

		GroupPermissionUtil.check(
			getPermissionChecker(), companyGroup,
			ActionKeys.EXPORT_IMPORT_PORTLET_INFO);

		exportImportLocalService.importPortletInfoInBackground(
			getUserId(), taskName, portletId, parameterMap, file);
	}

	@Override
	public void importPortletInfoInBackground(
			String taskName, String portletId,
			Map<String, String[]> parameterMap, InputStream is)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(getUserId());

		Group companyGroup = groupLocalService.getCompanyGroup(
			user.getCompanyId());

		GroupPermissionUtil.check(
			getPermissionChecker(), companyGroup,
			ActionKeys.EXPORT_IMPORT_PORTLET_INFO);

		exportImportLocalService.importPortletInfoInBackground(
			getUserId(), taskName, portletId, parameterMap, is);
	}

	@Override
	public MissingReferences validateImportLayoutsFile(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException {

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		long targetGroupId = MapUtil.getLong(settingsMap, "targetGroupId");

		GroupPermissionUtil.check(
			getPermissionChecker(), targetGroupId,
			ActionKeys.EXPORT_IMPORT_LAYOUTS);

		return exportImportLocalService.validateImportLayoutsFile(
			exportImportConfiguration, file);
	}

	@Override
	public MissingReferences validateImportLayoutsFile(
			ExportImportConfiguration exportImportConfiguration,
			InputStream inputStream)
		throws PortalException {

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		long targetGroupId = MapUtil.getLong(settingsMap, "targetGroupId");

		GroupPermissionUtil.check(
			getPermissionChecker(), targetGroupId,
			ActionKeys.EXPORT_IMPORT_LAYOUTS);

		return exportImportLocalService.validateImportLayoutsFile(
			exportImportConfiguration, inputStream);
	}

	@Override
	public MissingReferences validateImportPortletInfo(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException {

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		long targetPlid = MapUtil.getLong(settingsMap, "targetPlid");
		String portletId = MapUtil.getString(settingsMap, "portletId");

		PortletPermissionUtil.check(
			getPermissionChecker(), targetPlid, portletId,
			ActionKeys.CONFIGURATION);

		return exportImportLocalService.validateImportPortletInfo(
			exportImportConfiguration, file);
	}

	@Override
	public MissingReferences validateImportPortletInfo(
			ExportImportConfiguration exportImportConfiguration,
			InputStream inputStream)
		throws PortalException {

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		long targetPlid = MapUtil.getLong(settingsMap, "targetPlid");
		String portletId = MapUtil.getString(settingsMap, "portletId");

		PortletPermissionUtil.check(
			getPermissionChecker(), targetPlid, portletId,
			ActionKeys.CONFIGURATION);

		return exportImportLocalService.validateImportPortletInfo(
			exportImportConfiguration, inputStream);
	}

}