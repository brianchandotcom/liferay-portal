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

package com.liferay.calendar.web.internal.upgrade;

import com.liferay.calendar.constants.CalendarPortletKeys;
import com.liferay.calendar.web.internal.upgrade.v1_0_0.UpgradePortletId;
import com.liferay.calendar.web.internal.upgrade.v1_1_0.UpgradePortalPreferences;
import com.liferay.calendar.web.internal.upgrade.v1_1_1.UpgradeEventsDisplayPortletId;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.upgrade.BaseStagingGroupTypeSettingsUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 * @author Manuel de la Peña
 */
@Component(
	immediate = true,
	service = {CalendarWebUpgrade.class, UpgradeStepRegistrator.class}
)
public class CalendarWebUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register("0.0.0", "1.1.2", new DummyUpgradeStep());

		registry.register("0.0.1", "1.0.0", new UpgradePortletId());

		registry.register("1.0.0", "1.0.1", new DummyUpgradeStep());

		registry.register(
			"1.0.1", "1.1.0", new UpgradePortalPreferences(),
			new com.liferay.calendar.web.internal.upgrade.v1_1_0.
				UpgradePortletId());

		registry.register(
			"1.1.0", "1.1.1",
			new UpgradeEventsDisplayPortletId(
				_portletPreferencesLocalService,
				_resourcePermissionLocalService));

		registry.register(
			"1.1.1", "1.1.2",
			new BaseStagingGroupTypeSettingsUpgradeProcess(
				_companyLocalService, _groupLocalService,
				CalendarPortletKeys.CALENDAR,
				CalendarPortletKeys.CALENDAR_ADMIN));
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

}