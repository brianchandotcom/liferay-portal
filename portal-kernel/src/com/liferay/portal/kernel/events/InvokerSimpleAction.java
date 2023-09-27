/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.events;

/**
 * @author Brian Wing Shun Chan
 */
public class InvokerSimpleAction extends SimpleAction {

	public InvokerSimpleAction(SimpleAction simpleAction) {
		this(simpleAction, Thread.currentThread().getContextClassLoader());
	}

	public InvokerSimpleAction(
		SimpleAction simpleAction, ClassLoader classLoader) {

		_simpleAction = simpleAction;
		_classLoader = classLoader;
	}

	@Override
	public void run(String[] ids) throws ActionException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(_classLoader);

		try {
			_simpleAction.run(ids);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	private final ClassLoader _classLoader;
	private final SimpleAction _simpleAction;

}