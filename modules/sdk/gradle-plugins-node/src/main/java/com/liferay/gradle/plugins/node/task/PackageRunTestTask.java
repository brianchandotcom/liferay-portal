/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.node.task;

import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.VerificationTask;

/**
 * @author Peter Shin
 */
@CacheableTask
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
		catch (Exception exception) {
			if (isIgnoreFailures()) {
				Logger logger = getLogger();

				if (logger.isWarnEnabled()) {
					logger.warn(exception.getMessage());
				}
			}
			else {
				throw exception;
			}
		}
	}

	@Override
	public boolean getIgnoreFailures() {
		return _ignoreFailures;
	}

	@Internal
	public boolean isIgnoreFailures() {
		return _ignoreFailures;
	}

	@Override
	public void setIgnoreFailures(boolean ignoreFailures) {
		_ignoreFailures = ignoreFailures;
	}

	private boolean _ignoreFailures;

}