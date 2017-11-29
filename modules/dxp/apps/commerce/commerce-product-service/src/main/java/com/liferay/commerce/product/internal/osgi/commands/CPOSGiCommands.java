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

package com.liferay.commerce.product.internal.osgi.commands;

import com.liferay.commerce.product.model.CPGroup;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPGroupLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"osgi.command.function=commerceSites",
		"osgi.command.function=createProducts", "osgi.command.scope=commerce"
	},
	service = {CPOSGiCommands.class}
)
public class CPOSGiCommands {

	public List<Group> commerceSites() throws PortalException {
		List<CPGroup> cpGroups = _cpGroupLocalService.getCPGroups(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		List<Group> groups = new ArrayList<>();

		for (CPGroup cpGroup : cpGroups) {
			Group group = _groupLocalService.getGroup(cpGroup.getGroupId());

			groups.add(group);
		}

		return groups;
	}

	public void createProducts(long groupId, String prefix, int quantity)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		User user = _userLocalService.getDefaultUser(group.getCompanyId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setTimeZone(user.getTimeZone());
		serviceContext.setUserId(user.getUserId());

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

		Map<Locale, String> titleMap = new HashMap<>();

		for (int i = 0; i < quantity; i++) {
			String title = prefix + i;

			titleMap.put(Locale.US, title);

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
	private CPGroupLocalService _cpGroupLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private UserLocalService _userLocalService;

}