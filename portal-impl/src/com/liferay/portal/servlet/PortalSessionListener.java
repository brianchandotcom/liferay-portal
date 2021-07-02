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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.filters.compoundsessionid.CompoundSessionIdHttpSession;
import com.liferay.portal.kernel.servlet.filters.compoundsessionid.CompoundSessionIdSplitterUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		HttpSession httpSession = httpSessionEvent.getSession();

		if (CompoundSessionIdSplitterUtil.hasSessionDelimiter()) {
			httpSession = new CompoundSessionIdHttpSession(
				httpSessionEvent.getSession());
		}

		new PortalSessionCreator(httpSession);

		if ((PropsValues.SESSION_MAX_ALLOWED > 0) &&
			(_counter.incrementAndGet() > PropsValues.SESSION_MAX_ALLOWED)) {

			httpSession.setAttribute(WebKeys.SESSION_MAX_ALLOWED, Boolean.TRUE);

			_log.error(
				StringBundler.concat(
					"Exceeded maximum number of ",
					PropsValues.SESSION_MAX_ALLOWED,
					" sessions allowed. You may be experiencing a DoS ",
					"attack."));
		}
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		HttpSession httpSession = httpSessionEvent.getSession();

		if (CompoundSessionIdSplitterUtil.hasSessionDelimiter()) {
			httpSession = new CompoundSessionIdHttpSession(
				httpSessionEvent.getSession());
		}

		new PortalSessionDestroyer(httpSession);

		if (PropsValues.SESSION_MAX_ALLOWED > 0) {
			_counter.decrementAndGet();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalSessionListener.class);

	private final AtomicInteger _counter = new AtomicInteger();

}