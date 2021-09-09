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

package com.liferay.calendar.internal.model.listener;

import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarResourceLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = ModelListener.class)
public class GroupModelListener extends BaseModelListener<Group> {

	@Override
	public void onAfterUpdate(Group originalGroup, Group group)
		throws ModelListenerException {

		try {
			CalendarResource calendarResource =
				_calendarResourceLocalService.fetchCalendarResource(
					_portal.getClassNameId(Group.class), group.getGroupId());

			if (calendarResource == null) {
				return;
			}

			calendarResource.setNameMap(
				LocalizationUtil.populateLocalizationMap(
					HashMapBuilder.put(
						LocaleUtil.getSiteDefault(), group.getDescriptiveName()
					).build(),
					LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()),
					group.getGroupId()));

			_calendarResourceLocalService.updateCalendarResource(
				calendarResource);
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	@Override
	public void onBeforeRemove(Group group) throws ModelListenerException {
		try {

			// Global calendar resource

			CalendarResource calendarResource =
				_calendarResourceLocalService.fetchCalendarResource(
					_portal.getClassNameId(Group.class), group.getGroupId());

			if (calendarResource != null) {
				_calendarResourceLocalService.deleteCalendarResource(
					calendarResource);
			}

			// Local calendar resources

			_calendarResourceLocalService.deleteCalendarResources(
				group.getGroupId());
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	@Reference
	private CalendarResourceLocalService _calendarResourceLocalService;

	@Reference
	private Portal _portal;

}