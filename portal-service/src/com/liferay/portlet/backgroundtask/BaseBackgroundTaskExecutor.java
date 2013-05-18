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

package com.liferay.portlet.backgroundtask;

import com.liferay.portlet.backgroundtask.model.BTEntry;

/**
 * @author Michael C. Han
 */
public abstract class BaseBackgroundTaskExecutor
	implements BackgroundTaskExecutor {

	public void execute(BTEntry btEntry, ClassLoader classLoader)
		throws Exception {

		ClassLoader contextClassLoader =
			Thread.currentThread().getContextClassLoader();

		boolean overridenClassLoader = false;

		if (!contextClassLoader.equals(classLoader)) {
			Thread.currentThread().setContextClassLoader(classLoader);

			overridenClassLoader = true;
		}

		try {
			doExecute(btEntry);
		}
		finally {
			if (overridenClassLoader) {
				Thread.currentThread().setContextClassLoader(
					contextClassLoader);
			}
		}
	}

	protected abstract void doExecute(BTEntry btEntry) throws Exception;

}