/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public void run(HttpSession httpSession) throws ActionException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(_classLoader);

		try {
			_sessionAction.run(httpSession);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	private final ClassLoader _classLoader;
	private final SessionAction _sessionAction;

}