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