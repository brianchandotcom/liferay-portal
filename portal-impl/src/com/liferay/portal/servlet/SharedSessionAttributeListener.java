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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.concurrent.ConcurrentHashSet;
import com.liferay.portal.kernel.servlet.ServletVersionDetector;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * <p>
 * Listener used to help manage shared session attributes into a cache. This
 * cache is more thread safe than the HttpSession and leads to fewer problems
 * with shared session attributes being modified out of sequence.
 * </p>
 *
 * @author Michael C. Han
 */
public class SharedSessionAttributeListener
	implements HttpSessionAttributeListener, HttpSessionListener {

	public SharedSessionAttributeListener() {
		if (ServletVersionDetector.is2_5()) {
			return;
		}

		_sessionIds = new ConcurrentHashSet<String>();
	}

	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		if (PropsValues.SESSION_DISABLED || ServletVersionDetector.is2_5()) {
			return;
		}

		HttpSession session = event.getSession();

		if (!_sessionIds.contains(session.getId())) {
			return;
		}

		SharedSessionAttributeCache sharedSessionAttributeCache =
			SharedSessionAttributeCache.getInstance(session);

		String name = event.getName();

		if (ArrayUtil.contains(
				PropsValues.SESSION_SHARED_ATTRIBUTES_EXCLUDES, name)) {

			return;
		}

		for (String sharedName : PropsValues.SESSION_SHARED_ATTRIBUTES) {
			if (!name.startsWith(sharedName)) {
				continue;
			}

			sharedSessionAttributeCache.setAttribute(name, event.getValue());

			return;
		}
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		if (PropsValues.SESSION_DISABLED || ServletVersionDetector.is2_5()) {
			return;
		}

		HttpSession session = event.getSession();

		if (!_sessionIds.contains(session.getId())) {
			return;
		}

		SharedSessionAttributeCache sharedSessionAttributeCache =
			SharedSessionAttributeCache.getInstance(session);

		sharedSessionAttributeCache.removeAttribute(event.getName());
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		if (PropsValues.SESSION_DISABLED || ServletVersionDetector.is2_5()) {
			return;
		}

		HttpSession session = event.getSession();

		if (!_sessionIds.contains(session.getId())) {
			return;
		}

		SharedSessionAttributeCache sharedSessionAttributeCache =
			SharedSessionAttributeCache.getInstance(session);

		if (sharedSessionAttributeCache.contains(event.getName())) {
			Object value = session.getAttribute(event.getName());

			sharedSessionAttributeCache.setAttribute(event.getName(), value);
		}
	}

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		if (PropsValues.SESSION_DISABLED || ServletVersionDetector.is2_5()) {
			return;
		}

		HttpSession session = event.getSession();

		SharedSessionAttributeCache.getInstance(session);

		_sessionIds.add(session.getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		if (PropsValues.SESSION_DISABLED || ServletVersionDetector.is2_5()) {
			return;
		}

		HttpSession session = event.getSession();

		_sessionIds.remove(session.getId());
	}

	private Set<String> _sessionIds;

}