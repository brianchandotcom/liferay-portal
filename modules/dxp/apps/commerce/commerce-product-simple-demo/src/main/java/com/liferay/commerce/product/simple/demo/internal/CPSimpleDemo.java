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

package com.liferay.commerce.product.simple.demo.internal;

import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.users.admin.demo.data.creator.OmniAdminUserDemoDataCreator;

import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {"osgi.command.function=demo", "osgi.command.scope=commerce"},
	service = {CPSimpleDemo.class}
)
public class CPSimpleDemo {

	public void demo(String prefix, int quantity) throws Exception {
		long companyId = _portal.getDefaultCompanyId();

		Group group = _groupLocalService.getGroup(companyId, "Guest");

		User user = _omniAdminUserDemoDataCreator.create(
			companyId, "alessio.rendina@liferay.com");

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setScopeGroupId(group.getGroupId());
		serviceContext.setTimeZone(user.getTimeZone());
		serviceContext.setUserId(user.getUserId());

		for (int i = 0; i < quantity; i++) {
			String title = prefix + i;

			Map<Locale, String> titleMap = Collections.singletonMap(
				Locale.US, title);

			Calendar displayCalendar = CalendarFactoryUtil.getCalendar(
				serviceContext.getTimeZone());

			displayCalendar.add(Calendar.YEAR, -1);

			int displayDateMonth = displayCalendar.get(Calendar.MONTH);
			int displayDateDay = displayCalendar.get(Calendar.DAY_OF_MONTH);
			int displayDateYear = displayCalendar.get(Calendar.YEAR);
			int displayDateHour = displayCalendar.get(Calendar.HOUR);
			int displayDateMinute = displayCalendar.get(Calendar.MINUTE);
			int displayDateAmPm = displayCalendar.get(Calendar.AM_PM);

			if (displayDateAmPm == Calendar.PM) {
				displayDateHour += 12;
			}

			_cpDefinitionLocalService.addCPDefinition(
				titleMap, null, null, null, null, null, null, "simple", true,
				false, false, false, 0, 0, 0, 0, 0, null, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, 0, 0, 0, 0, 0, true, true, serviceContext);
		}
	}

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private OmniAdminUserDemoDataCreator _omniAdminUserDemoDataCreator;

	@Reference
	private Portal _portal;

}