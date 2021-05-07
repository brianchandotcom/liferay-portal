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

package com.liferay.portal.servlet;

import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.PortalSessionContext;
import com.liferay.portal.kernel.util.BasePortalLifecycle;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsValues;

import javax.servlet.http.HttpSession;

/**
 * @author Michael Young
 */
public class PortalSessionCreator extends BasePortalLifecycle {

	public PortalSessionCreator(HttpSession httpSession) {
		_httpSession = httpSession;

		registerPortalLifecycle(METHOD_INIT);
	}

	@Override
	protected void doPortalDestroy() {
	}

	@Override
	protected void doPortalInit() {
		try {
			PortalSessionContext.put(_httpSession.getId(), _httpSession);
		}
		catch (IllegalStateException illegalStateException) {
			if (_log.isWarnEnabled()) {
				_log.warn(illegalStateException, illegalStateException);
			}
		}

		// Process session created events

		try {
			EventsProcessorUtil.process(
				PropsKeys.SERVLET_SESSION_CREATE_EVENTS,
				PropsValues.SERVLET_SESSION_CREATE_EVENTS, _httpSession);
		}
		catch (ActionException actionException) {
			_log.error(actionException, actionException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalSessionCreator.class);

	private final HttpSession _httpSession;

}