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
import com.liferay.portal.kernel.notifications.UserNotificationFeedEntry;
import com.liferay.portal.kernel.notifications.UserNotificationInterpreter;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.service.ServiceContext;

/**
 * @author Jonathan Lee
 */
public class UserNotificationInterpreterImpl
	implements UserNotificationInterpreter {

	public UserNotificationInterpreterImpl(
		String portletId,
		UserNotificationInterpreter userNotificationInterpreter) {

		_portletId = portletId;
		_userNotificationInterpreter = userNotificationInterpreter;
	}

	@Override
	public String getPortletId() {
		return _userNotificationInterpreter.getPortletId();
	}

	@Override
	public String getSelector() {
		return _userNotificationInterpreter.getSelector();
	}

	@Override
	public UserNotificationFeedEntry interpret(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws PortalException {

		return _userNotificationInterpreter.interpret(
			userNotificationEvent, serviceContext);
	}

	private String _portletId;
	private String _selector;
	private UserNotificationInterpreter _userNotificationInterpreter;

}