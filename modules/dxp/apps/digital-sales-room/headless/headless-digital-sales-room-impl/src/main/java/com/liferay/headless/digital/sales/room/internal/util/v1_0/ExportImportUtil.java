/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.digital.sales.room.internal.util.v1_0;

import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactory;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.exportimport.kernel.service.ExportImportLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;

import java.io.File;
import java.io.Serializable;

import java.util.Map;

/**
 * @author Stefano Motta
 */
public class ExportImportUtil {

	public static void importLayouts(
			ExportImportConfigurationLocalService
				exportImportConfigurationLocalService,
			ExportImportConfigurationSettingsMapFactory
				exportImportConfigurationSettingsMapFactory,
			ExportImportLocalService exportImportLocalService, long[] layoutIds,
			long sourceGroupId, long targetGroupId, User user)
		throws Exception {

		Map<String, Serializable> importLayoutSettingsMap =
			exportImportConfigurationSettingsMapFactory.
				buildImportLayoutSettingsMap(
					user, targetGroupId, false, layoutIds,
					_getImportParameterMap());

		ExportImportConfiguration exportImportConfiguration =
			exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					user.getUserId(), StringPool.BLANK,
					ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
					importLayoutSettingsMap);

		exportImportLocalService.importLayouts(
			exportImportConfiguration,
			_generateLarFile(
				exportImportConfigurationLocalService,
				exportImportConfigurationSettingsMapFactory,
				exportImportLocalService, sourceGroupId, layoutIds, user));
	}

	private static File _generateLarFile(
			ExportImportConfigurationLocalService
				exportImportConfigurationLocalService,
			ExportImportConfigurationSettingsMapFactory
				exportImportConfigurationSettingsMapFactory,
			ExportImportLocalService exportImportLocalService, long groupId,
			long[] layoutIds, User user)
		throws Exception {

		Map<String, Serializable> exportLayoutSettingsMap =
			exportImportConfigurationSettingsMapFactory.
				buildExportLayoutSettingsMap(
					user, groupId, false, layoutIds, _getExportParameterMap());

		ExportImportConfiguration exportImportConfiguration =
			exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					user.getUserId(),
					ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
					exportLayoutSettingsMap);

		return exportImportLocalService.exportLayoutsAsFile(
			exportImportConfiguration);
	}

	private static Map<String, String[]> _getExportParameterMap() {
		return LinkedHashMapBuilder.put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {Boolean.TRUE.toString()}
		).build();
	}

	private static Map<String, String[]> _getImportParameterMap() {
		return LinkedHashMapBuilder.putAll(
			_getExportParameterMap()
		).put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			new String[] {PortletDataHandlerKeys.DATA_STRATEGY_MIRROR_OVERWRITE}
		).put(
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_SETTINGS,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.LAYOUT_SET_SETTINGS,
			new String[] {Boolean.TRUE.toString()}
		).build();
	}

}