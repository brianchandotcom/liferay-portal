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

package com.liferay.notifications.web.internal.search;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.UserNotificationFeedEntry;
import com.liferay.portal.kernel.notifications.UserNotificationManagerUtil;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserNotificationEventLocalServiceUtil;

import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author István András Dézsi
 */
public class UserNotificationEventRowChecker extends EmptyOnClickRowChecker {

	public UserNotificationEventRowChecker(
		HttpServletRequest httpServletRequest,
		PortletResponse portletResponse) {

		super(portletResponse);

		_httpServletRequest = httpServletRequest;
	}

	@Override
	public boolean isDisabled(Object object) {
		UserNotificationEvent userNotificationEvent =
			(UserNotificationEvent)object;

		if (userNotificationEvent.isActionRequired()) {
			return true;
		}

		UserNotificationFeedEntry userNotificationFeedEntry = null;

		try {
			UserNotificationEvent storedUserNotificationEvent =
				UserNotificationEventLocalServiceUtil.
					fetchUserNotificationEvent(
						userNotificationEvent.getUserNotificationEventId());

			if (storedUserNotificationEvent != null) {
				userNotificationFeedEntry =
					UserNotificationManagerUtil.interpret(
						StringPool.BLANK, userNotificationEvent,
						ServiceContextFactory.getInstance(_httpServletRequest));
			}
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}
		}

		if ((userNotificationFeedEntry == null) || super.isDisabled(object)) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserNotificationEventRowChecker.class);

	private final HttpServletRequest _httpServletRequest;

}