/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.exportimport.service.impl;

import com.liferay.exportimport.kernel.lar.MissingReferences;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portlet.exportimport.service.base.ExportImportServiceBaseImpl;

import java.io.File;
import java.io.InputStream;

/**
 * @author Daniel Kocsis
 */
public class ExportImportServiceImpl extends ExportImportServiceBaseImpl {

	@Override
	public File exportLayoutsAsFile(
			ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		long sourceGroupId = MapUtil.getLong(
			exportImportConfiguration.getSettingsMap(), "sourceGroupId");

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

		long sourceGroupId = MapUtil.getLong(
			exportImportConfiguration.getSettingsMap(), "sourceGroupId");

		GroupPermissionUtil.check(
			getPermissionChecker(), sourceGroupId,
			ActionKeys.EXPORT_IMPORT_LAYOUTS);

		return exportImportLocalService.exportLayoutsAsFileInBackground(
			getUserId(), exportImportConfiguration);
	}

	@Override
	public long exportLayoutsAsFileInBackground(
			long exportImportConfigurationId)
		throws PortalException {

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.getExportImportConfiguration(
				exportImportConfigurationId);

		long sourceGroupId = MapUtil.getLong(
			exportImportConfiguration.getSettingsMap(), "sourceGroupId");

		GroupPermissionUtil.check(
			getPermissionChecker(), sourceGroupId,
			ActionKeys.EXPORT_IMPORT_LAYOUTS);

		return exportImportLocalService.exportLayoutsAsFileInBackground(
			getUserId(), exportImportConfigurationId);
	}

	@Override
	public File exportPortletInfoAsFile(
			ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		long sourceGroupId = MapUtil.getLong(
			exportImportConfiguration.getSettingsMap(), "sourceGroupId");

		GroupPermissionUtil.check(
			getPermissionChecker(), sourceGroupId,
			ActionKeys.EXPORT_IMPORT_PORTLET_INFO);

		return exportImportLocalService.exportPortletInfoAsFile(
			exportImportConfiguration);
	}

	@Override
	public long exportPortletInfoAsFileInBackground(
			ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		long sourceGroupId = MapUtil.getLong(
			exportImportConfiguration.getSettingsMap(), "sourceGroupId");

		GroupPermissionUtil.check(
			getPermissionChecker(), sourceGroupId,
			ActionKeys.EXPORT_IMPORT_PORTLET_INFO);

		return exportImportLocalService.exportPortletInfoAsFileInBackground(
			getUserId(), exportImportConfiguration);
	}

	@Override
	public void importLayouts(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException {

		long targetGroupId = MapUtil.getLong(
			exportImportConfiguration.getSettingsMap(), "targetGroupId");

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

		long targetGroupId = MapUtil.getLong(
			exportImportConfiguration.getSettingsMap(), "targetGroupId");

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

		long targetGroupId = MapUtil.getLong(
			exportImportConfiguration.getSettingsMap(), "targetGroupId");

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

		long targetGroupId = MapUtil.getLong(
			exportImportConfiguration.getSettingsMap(), "targetGroupId");

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

		long targetGroupId = MapUtil.getLong(
			exportImportConfiguration.getSettingsMap(), "targetGroupId");

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

		long targetGroupId = MapUtil.getLong(
			exportImportConfiguration.getSettingsMap(), "targetGroupId");

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

		long targetGroupId = MapUtil.getLong(
			exportImportConfiguration.getSettingsMap(), "targetGroupId");

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

		long targetGroupId = MapUtil.getLong(
			exportImportConfiguration.getSettingsMap(), "targetGroupId");

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

		long targetGroupId = MapUtil.getLong(
			exportImportConfiguration.getSettingsMap(), "targetGroupId");

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

		long targetGroupId = MapUtil.getLong(
			exportImportConfiguration.getSettingsMap(), "targetGroupId");

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

		long targetGroupId = MapUtil.getLong(
			exportImportConfiguration.getSettingsMap(), "targetGroupId");

		GroupPermissionUtil.check(
			getPermissionChecker(), targetGroupId,
			ActionKeys.EXPORT_IMPORT_PORTLET_INFO);

		return exportImportLocalService.validateImportPortletInfo(
			exportImportConfiguration, file);
	}

	@Override
	public MissingReferences validateImportPortletInfo(
			ExportImportConfiguration exportImportConfiguration,
			InputStream inputStream)
		throws PortalException {

		long targetGroupId = MapUtil.getLong(
			exportImportConfiguration.getSettingsMap(), "targetGroupId");

		GroupPermissionUtil.check(
			getPermissionChecker(), targetGroupId,
			ActionKeys.EXPORT_IMPORT_PORTLET_INFO);

		return exportImportLocalService.validateImportPortletInfo(
			exportImportConfiguration, inputStream);
	}

	@BeanReference(type = ExportImportConfigurationLocalService.class)
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

}