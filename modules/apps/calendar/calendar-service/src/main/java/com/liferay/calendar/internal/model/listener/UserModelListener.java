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
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.Portal;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Antonio Junior
 */
@Component(service = ModelListener.class)
public class UserModelListener extends BaseModelListener<User> {

	@Override
	public void onAfterUpdate(User originalUser, User user)
		throws ModelListenerException {

		try {
			CalendarResource calendarResource =
				_calendarResourceLocalService.fetchCalendarResource(
					_portal.getClassNameId(User.class), user.getUserId());

			if (calendarResource == null) {
				return;
			}

			String name = calendarResource.getName(LocaleUtil.getSiteDefault());

			if (Objects.equals(name, user.getFullName()) ||
				(user.isGuestUser() && name.equals(GroupConstants.GUEST))) {

				return;
			}

			Group group = user.getGroup();

			calendarResource.setNameMap(
				_localization.populateLocalizationMap(
					HashMapBuilder.put(
						LocaleUtil.getSiteDefault(), user.getFullName()
					).build(),
					LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()),
					(group == null) ? 0 : group.getGroupId()));

			_calendarResourceLocalService.updateCalendarResource(
				calendarResource);
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