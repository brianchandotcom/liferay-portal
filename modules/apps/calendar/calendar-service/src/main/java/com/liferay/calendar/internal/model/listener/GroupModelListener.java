/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(service = ModelListener.class)
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
				_localization.populateLocalizationMap(
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
	private Localization _localization;

	@Reference
	private Portal _portal;

}