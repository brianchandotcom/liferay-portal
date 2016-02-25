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

package com.liferay.portal.kernel.events;

import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 */
public class InvokerSessionAction extends SessionAction {

	public InvokerSessionAction(SessionAction sessionAction) {
		this(sessionAction, Thread.currentThread().getContextClassLoader());
	}

	public InvokerSessionAction(
		SessionAction sessionAction, ClassLoader classLoader) {

		_sessionAction = sessionAction;
		_classLoader = classLoader;
	}

	@Override
	public void run(HttpSession session) throws ActionException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(_classLoader);

		try {
			_sessionAction.run(session);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	private final ClassLoader _classLoader;
	private final SessionAction _sessionAction;

}