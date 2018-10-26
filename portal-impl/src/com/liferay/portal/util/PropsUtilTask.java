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

package com.liferay.portal.util;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/**
 * @author Brian Wing Shun Chan
 */
public class PropsUtilTask extends Task {

	@Override
	public void execute() throws BuildException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			Class<?> clazz = getClass();

			currentThread.setContextClassLoader(clazz.getClassLoader());

			Project project = getProject();

			project.setUserProperty(_result, PropsUtil.get(_key));
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public void setKey(String key) {
		_key = key;
	}

	public void setResult(String result) {
		_result = result;
	}

	private String _key;
	private String _result;

}