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
import javax.servlet.http.HttpSessionEvent;

/**
 * @author Michael Young
 */
public class PortalSessionCreator extends BasePortalLifecycle {

	public PortalSessionCreator(HttpSession httpSession) {
		_httpSession = httpSession;

		registerPortalLifecycle(METHOD_INIT);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #PortalSessionCreator(HttpSession)}
	 */
	@Deprecated
	public PortalSessionCreator(HttpSessionEvent httpSessionEvent) {
		this(httpSessionEvent.getSession());
	}

	@Override
	protected void doPortalDestroy() {
	}

	@Override
	protected void doPortalInit() {
		if (PropsValues.SESSION_DISABLED) {
			return;
		}

		try {
			PortalSessionContext.put(_httpSession.getId(), _httpSession);
		}
		catch (IllegalStateException ise) {
			if (_log.isWarnEnabled()) {
				_log.warn(ise, ise);
			}
		}

		// Process session created events

		try {
			EventsProcessorUtil.process(
				PropsKeys.SERVLET_SESSION_CREATE_EVENTS,
				PropsValues.SERVLET_SESSION_CREATE_EVENTS, _httpSession);
		}
		catch (ActionException ae) {
			_log.error(ae, ae);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalSessionCreator.class);

	private final HttpSession _httpSession;

}