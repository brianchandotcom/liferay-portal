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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.notifications.UserNotificationFeedEntry;
import com.liferay.portal.kernel.notifications.UserNotificationInterpreter;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.UserNotificationInterpreterLocalServiceBaseImpl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jonathan Lee
 * @author Brian Wing Shun Chan
 */
public class UserNotificationInterpreterLocalServiceImpl
	extends UserNotificationInterpreterLocalServiceBaseImpl {

	/**
	 * Adds the use notification interpreter to the list of available
	 * interpreters.
	 *
	 * @param userNotificationInterpreter the user notification interpreter
	 */
	@Override
	public void addUserNotificationInterpreter(
		UserNotificationInterpreter userNotificationInterpreter) {

		String selector = userNotificationInterpreter.getSelector();

		Map<String, UserNotificationInterpreter> userNotificationInterpreters =
			_userNotificationInterpreters.get(selector);

		if (userNotificationInterpreters == null) {
			userNotificationInterpreters =
				new HashMap<String, UserNotificationInterpreter>();

			_userNotificationInterpreters.put(
				selector, userNotificationInterpreters);
		}

		userNotificationInterpreters.put(
			userNotificationInterpreter.getPortletId(),
			userNotificationInterpreter);
	}

	/**
	 * Removes the user notification interpreter from the list of available
	 * interpreters.
	 *
	 * @param userNotificationInterpreter the user notification interpreter
	 */
	@Override
	public void deleteUserNotificationInterpreter(
		UserNotificationInterpreter userNotificationInterpreter) {

		Map<String, UserNotificationInterpreter> userNotificationInterpreters =
			_userNotificationInterpreters.get(
				userNotificationInterpreter.getSelector());

		if (userNotificationInterpreters == null) {
			return;
		}

		_userNotificationInterpreters.remove(
			userNotificationInterpreter.getPortletId());
	}

	@Override
	public Map<String, Map<String, UserNotificationInterpreter>>
		getUserNotificationInterpreters() {

		return Collections.unmodifiableMap(_userNotificationInterpreters);
	}

	/**
	 * Creates a human readable user notification feed entry for the user
	 * notification using an available compatible user notification interpreter.
	 *
	 * <p>
	 * This method finds the appropriate interpreter for the user notification
	 * by going through the available interpreters and asking them if they can
	 * handle the user notidication based on the portlet.
	 * </p>
	 *
	 * @param  userNotificationEvent the user notification event to be
	 *         translated to human readable form
	 * @return the user notification feed that is a human readable form of the
	 *         user notification record or <code>null</code> if a compatible
	 *         interpreter is not found
	 */
	@Override
	public UserNotificationFeedEntry interpret(
			String selector, UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws PortalException {

		Map<String, UserNotificationInterpreter> userNotificationInterpreters =
			_userNotificationInterpreters.get(selector);

		if (userNotificationInterpreters == null) {
			return null;
		}

		UserNotificationInterpreter userNotificationInterpreter =
			userNotificationInterpreters.get(userNotificationEvent.getType());

		if (userNotificationInterpreter == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No interpreter found for: " + userNotificationEvent);
			}

			return null;
		}

		return userNotificationInterpreter.interpret(
			userNotificationEvent, serviceContext);
	}

	private static Log _log = LogFactoryUtil.getLog(
		UserNotificationInterpreterLocalServiceImpl.class);

	private Map<String, Map<String, UserNotificationInterpreter>>
		_userNotificationInterpreters =
			new HashMap<String, Map<String, UserNotificationInterpreter>>();

}