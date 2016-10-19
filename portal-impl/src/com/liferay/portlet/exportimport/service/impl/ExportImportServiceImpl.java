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

package com.liferay.portlet.exportimport.service.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.MissingReferences;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portlet.exportimport.service.base.ExportImportServiceBaseImpl;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

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
			ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		long sourcePlid = MapUtil.getLong(settingsMap, "sourcePlid");

		Layout layout = layoutLocalService.getLayout(sourcePlid);

		GroupPermissionUtil.check(
			getPermissionChecker(), layout.getGroupId(),
			ActionKeys.EXPORT_IMPORT_PORTLET_INFO);

		return exportImportLocalService.exportPortletInfoAsFileInBackground(
			getUserId(), exportImportConfiguration);
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
			ExportImportConfiguration exportImportConfiguration,
			InputStream inputStream)
		throws PortalException {

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		long targetGroupId = MapUtil.getLong(settingsMap, "targetGroupId");

		GroupPermissionUtil.check(
			getPermissionChecker(), targetGroupId,
			ActionKeys.EXPORT_IMPORT_LAYOUTS);

		exportImportLocalService.importLayouts(
			exportImportConfiguration, inputStream);
	}

	@Override
	public long importLayoutsInBackground(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException {

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		long targetGroupId = MapUtil.getLong(settingsMap, "targetGroupId");

		GroupPermissionUtil.check(
			getPermissionChecker(), targetGroupId,
			ActionKeys.EXPORT_IMPORT_LAYOUTS);

		return exportImportLocalService.importLayoutsInBackground(
			getUserId(), exportImportConfiguration, file);
	}

	@Override
	public long importLayoutsInBackground(
			ExportImportConfiguration exportImportConfiguration,
			InputStream inputStream)
		throws PortalException {

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		long targetGroupId = MapUtil.getLong(settingsMap, "targetGroupId");

		GroupPermissionUtil.check(
			getPermissionChecker(), targetGroupId,
			ActionKeys.EXPORT_IMPORT_LAYOUTS);

		return exportImportLocalService.importLayoutsInBackground(
			getUserId(), exportImportConfiguration, inputStream);
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
			ExportImportConfiguration exportImportConfiguration,
			InputStream inputStream)
		throws PortalException {

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		long targetGroupId = MapUtil.getLong(settingsMap, "targetGroupId");

		GroupPermissionUtil.check(
			getPermissionChecker(), targetGroupId,
			ActionKeys.EXPORT_IMPORT_PORTLET_INFO);

		exportImportLocalService.importPortletInfo(
			exportImportConfiguration, inputStream);
	}

	@Override
	public long importPortletInfoInBackground(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException {

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		long targetGroupId = MapUtil.getLong(settingsMap, "targetGroupId");

		GroupPermissionUtil.check(
			getPermissionChecker(), targetGroupId,
			ActionKeys.EXPORT_IMPORT_PORTLET_INFO);

		return exportImportLocalService.importPortletInfoInBackground(
			getUserId(), exportImportConfiguration, file);
	}

	@Override
	public long importPortletInfoInBackground(
			ExportImportConfiguration exportImportConfiguration,
			InputStream inputStream)
		throws PortalException {

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		long targetGroupId = MapUtil.getLong(settingsMap, "targetGroupId");

		GroupPermissionUtil.check(
			getPermissionChecker(), targetGroupId,
			ActionKeys.EXPORT_IMPORT_PORTLET_INFO);

		return exportImportLocalService.importPortletInfoInBackground(
			getUserId(), exportImportConfiguration, inputStream);
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