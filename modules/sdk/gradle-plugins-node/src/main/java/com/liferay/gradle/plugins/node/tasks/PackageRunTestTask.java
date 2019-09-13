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

package com.liferay.gradle.plugins.node.tasks;

import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.VerificationTask;

/**
 * @author Peter Shin
 */
public class PackageRunTestTask
	extends PackageRunTask implements VerificationTask {

	public PackageRunTestTask() {
		setScriptName("test");
	}

	@Override
	public void executeNode() throws Exception {
		try {
			super.executeNode();
		}
		catch (Exception e) {
			if (isIgnoreFailures()) {
				Logger logger = getLogger();

				if (logger.isWarnEnabled()) {
					logger.warn(e.getMessage());
				}
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public boolean getIgnoreFailures() {
		return _ignoreFailures;
	}

	public boolean isIgnoreFailures() {
		return _ignoreFailures;
	}

	@Override
	public void setIgnoreFailures(boolean ignoreFailures) {
		_ignoreFailures = ignoreFailures;
	}

	private boolean _ignoreFailures;

}