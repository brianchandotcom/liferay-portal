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

package com.liferay.calendar.internal.upgrade;

import com.liferay.calendar.internal.upgrade.v1_0_4.UpgradeClassNames;
import com.liferay.calendar.internal.upgrade.v1_0_5.UpgradeCalendarResource;
import com.liferay.calendar.internal.upgrade.v1_0_5.UpgradeCompanyId;
import com.liferay.calendar.internal.upgrade.v1_0_5.UpgradeLastPublishDate;
import com.liferay.calendar.internal.upgrade.v1_0_6.UpgradeResourcePermission;
import com.liferay.calendar.upgrade.v1_0_2.UpgradeCalendar;
import com.liferay.calendar.upgrade.v2_0_0.UpgradeSchema;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourceBlockLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 * @author Manuel de la Peña
 */
@Component(
	immediate = true,
	service = {CalendarServiceUpgrade.class, UpgradeStepRegistrator.class}
)
public class CalendarServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"com.liferay.calendar.service", "0.0.1", "1.0.0",
			new com.liferay.calendar.internal.upgrade.v1_0_0.
				UpgradeCalendarBooking());

		registry.register(
			"com.liferay.calendar.service", "1.0.0", "1.0.1",
			new com.liferay.calendar.internal.upgrade.v1_0_1.
				UpgradeCalendarBooking());

		registry.register(
			"com.liferay.calendar.service", "1.0.1", "1.0.2",
			new UpgradeCalendar());

		registry.register(
			"com.liferay.calendar.service", "1.0.2", "1.0.3",
			new DummyUpgradeStep());

		registry.register(
			"com.liferay.calendar.service", "1.0.3", "1.0.4",
			new UpgradeClassNames());

		registry.register(
			"com.liferay.calendar.service", "1.0.4", "1.0.5",
			new UpgradeCalendarResource(
				_classNameLocalService, _companyLocalService,
				_userLocalService),
			new UpgradeCompanyId(), new UpgradeLastPublishDate());

		registry.register(
			"com.liferay.calendar.service", "1.0.5", "1.0.6",
			new UpgradeResourcePermission(
				_resourceActionLocalService, _resourceBlockLocalService,
				_roleLocalService));

		registry.register(
			"com.liferay.calendar.service", "1.0.6", "2.0.0",
			new UpgradeSchema());
	}

	@Reference(unbind = "-")
	protected void setClassNameLocalService(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	@Reference(unbind = "-")
	protected void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Reference(unbind = "-")
	protected void setResourceActionLocalService(
		ResourceActionLocalService resourceActionLocalService) {

		_resourceActionLocalService = resourceActionLocalService;
	}

	@Reference(unbind = "-")
	protected void setResourceBlockLocalService(
		ResourceBlockLocalService resourceBlockLocalService) {

		_resourceBlockLocalService = resourceBlockLocalService;
	}

	@Reference(unbind = "-")
	protected void setRoleLocalService(RoleLocalService roleLocalService) {
		_roleLocalService = roleLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private ClassNameLocalService _classNameLocalService;
	private CompanyLocalService _companyLocalService;
	private ResourceActionLocalService _resourceActionLocalService;
	private ResourceBlockLocalService _resourceBlockLocalService;
	private RoleLocalService _roleLocalService;
	private UserLocalService _userLocalService;

}