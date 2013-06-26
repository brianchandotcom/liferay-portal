/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.notifications;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.notifications.UserNotificationFeedEntry;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationManager;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.service.ServiceContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jonathan Lee
 * @author Brian Wing Shun Chan
 */
public class UserNotificationManagerImpl implements UserNotificationManager {

	/**
	 * Adds the use notification handler to the list of available
	 * handlers.
	 *
	 * @param userNotificationHandler the user notification handler
	 */
	@Override
	public void addUserNotificationHandler(
		UserNotificationHandler userNotificationHandler) {

		String selector = userNotificationHandler.getSelector();

		Map<String, UserNotificationHandler> userNotificationHandlers =
			_userNotificationHandlers.get(selector);

		if (userNotificationHandlers == null) {
			userNotificationHandlers =
				new HashMap<String, UserNotificationHandler>();

			_userNotificationHandlers.put(
				selector, userNotificationHandlers);
		}

		userNotificationHandlers.put(
			userNotificationHandler.getPortletId(), userNotificationHandler);
	}

	/**
	 * Removes the user notification handler from the list of available
	 * handlers.
	 *
	 * @param userNotificationHandler the user notification handler
	 */
	@Override
	public void deleteUserNotificationHandler(
		UserNotificationHandler userNotificationHandler) {

		Map<String, UserNotificationHandler> userNotificationHandlers =
			_userNotificationHandlers.get(
				userNotificationHandler.getSelector());

		if (userNotificationHandlers == null) {
			return;
		}

		_userNotificationHandlers.remove(
			userNotificationHandler.getPortletId());
	}

	@Override
	public Map<String, Map<String, UserNotificationHandler>>
		getUserNotificationHandlers() {

		return Collections.unmodifiableMap(_userNotificationHandlers);
	}

	/**
	 * Creates a human readable user notification feed entry for the user
	 * notification using an available compatible user notification handler.
	 *
	 * <p>
	 * This method finds the appropriate handler for the user notification
	 * by going through the available handler and asking them if they can
	 * handle the user notidication based on the portlet.
	 * </p>
	 *
	 * @param  userNotificationEvent the user notification event to be
	 *         translated to human readable form
	 * @return the user notification feed that is a human readable form of the
	 *         user notification record or <code>null</code> if a compatible
	 *         handler is not found
	 */
	@Override
	public UserNotificationFeedEntry interpret(
			String selector, UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws PortalException {

		Map<String, UserNotificationHandler> userNotificationHandlers =
			_userNotificationHandlers.get(selector);

		if (userNotificationHandlers == null) {
			return null;
		}

		UserNotificationHandler userNotificationHandler =
			userNotificationHandlers.get(userNotificationEvent.getType());

		if (userNotificationHandler == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No interpreter found for " + userNotificationEvent);
			}

			return null;
		}

		return userNotificationHandler.interpret(
			userNotificationEvent, serviceContext);
	}

	private static Log _log = LogFactoryUtil.getLog(
		UserNotificationManagerImpl.class);

	private Map<String, Map<String, UserNotificationHandler>>
		_userNotificationHandlers =
			new HashMap<String, Map<String, UserNotificationHandler>>();

}