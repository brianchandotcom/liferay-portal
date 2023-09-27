/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.google.docs.internal.upgrade.registry;

import com.liferay.document.library.google.docs.internal.upgrade.v1_0_0.DLFileEntryTypeNameUpgradeProcess;
import com.liferay.document.library.google.docs.internal.upgrade.v1_0_0.PortletPreferencesUpgradeProcess;
import com.liferay.document.library.google.docs.internal.upgrade.v2_0_0.DLFileEntryTypeUpgradeProcess;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = UpgradeStepRegistrator.class)
public class GoogleDocsUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register(
			"0.0.1", "1.0.0",
			new DLFileEntryTypeNameUpgradeProcess(
				_dlFileEntryTypeLocalService));

		registry.register(
			"1.0.0", "1.0.1",
			new PortletPreferencesUpgradeProcess(
				_configurationProvider, _prefsProps));

		registry.register(
			"1.0.1", "2.0.0",
			new DLFileEntryTypeUpgradeProcess(
				_classNameLocalService, _ddmStructureLocalService,
				_dlFileEntryTypeLocalService));
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference
	private PrefsProps _prefsProps;

}