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

package com.liferay.document.library.web.internal.upgrade;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.web.internal.upgrade.v1_0_0.UpgradeAdminPortlets;
import com.liferay.document.library.web.internal.upgrade.v1_0_0.UpgradePortletPreferences;
import com.liferay.document.library.web.internal.upgrade.v1_0_0.UpgradePortletSettings;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.upgrade.BaseUpgradeStagingGroupTypeSettings;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class DLWebUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register("0.0.0", "1.0.2", new DummyUpgradeStep());

		registry.register(
			"0.0.1", "1.0.0", new UpgradeAdminPortlets(),
			new UpgradePortletSettings(_settingsFactory));

		registry.register(
			"1.0.0", "1.0.1",
			new BaseUpgradeStagingGroupTypeSettings(
				_companyLocalService, _groupLocalService,
				DLPortletKeys.DOCUMENT_LIBRARY,
				DLPortletKeys.DOCUMENT_LIBRARY_ADMIN));

		registry.register("1.0.1", "1.0.2", new UpgradePortletPreferences());
	}

	@Reference(unbind = "-")
	public void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Reference(unbind = "-")
	public void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setSettingsFactory(SettingsFactory settingsFactory) {
		_settingsFactory = settingsFactory;
	}

	private CompanyLocalService _companyLocalService;
	private GroupLocalService _groupLocalService;
	private SettingsFactory _settingsFactory;

}