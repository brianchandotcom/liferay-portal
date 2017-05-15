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

import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portlet.exportimport.service.base.ExportImportConfigurationServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 * @author Levente Hudák
 */
public class ExportImportConfigurationServiceImpl
	extends ExportImportConfigurationServiceBaseImpl {

	@Override
	public void deleteExportImportConfiguration(
			long exportImportConfigurationId)
		throws PortalException {

		ExportImportConfiguration exportImportConfiguration =
			exportImportConfigurationLocalService.getExportImportConfiguration(
				exportImportConfigurationId);

		GroupPermissionUtil.check(
			getPermissionChecker(), exportImportConfiguration.getGroupId(),
			ActionKeys.DELETE);

		exportImportConfigurationLocalService.deleteExportImportConfiguration(
			exportImportConfiguration);
	}

	@Override
	public ExportImportConfiguration moveExportImportConfigurationToTrash(
			long exportImportConfigurationId)
		throws PortalException {

		ExportImportConfiguration exportImportConfiguration =
			exportImportConfigurationLocalService.getExportImportConfiguration(
				exportImportConfigurationId);

		GroupPermissionUtil.check(
			getPermissionChecker(), exportImportConfiguration.getGroupId(),
			ActionKeys.DELETE);

		return exportImportConfigurationLocalService.
			moveExportImportConfigurationToTrash(
				getUserId(), exportImportConfigurationId);
	}

	@Override
	public ExportImportConfiguration restoreExportImportConfigurationFromTrash(
			long exportImportConfigurationId)
		throws PortalException {

		ExportImportConfiguration exportImportConfiguration =
			exportImportConfigurationLocalService.getExportImportConfiguration(
				exportImportConfigurationId);

		GroupPermissionUtil.check(
			getPermissionChecker(), exportImportConfiguration.getGroupId(),
			ActionKeys.DELETE);

		return exportImportConfigurationLocalService.
			restoreExportImportConfigurationFromTrash(
				getUserId(), exportImportConfigurationId);
	}

}