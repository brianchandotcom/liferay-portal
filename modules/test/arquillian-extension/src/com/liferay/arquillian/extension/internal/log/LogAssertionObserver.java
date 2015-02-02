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

package com.liferay.arquillian.extension.internal.log;

import com.liferay.portal.test.rule.executor.LogAssertionExecutor;

import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;

/**
 * @author Cristina González
 */
public class LogAssertionObserver {

	public void logMessageError(@Observes LogMessageError logMessageError)
		throws Throwable {

		LogAssertionExecutor failOnLogMessageError =
			_logAssertionExecuterInstance.get();

		failOnLogMessageError.caughtFailure(
			logMessageError.getLogThread(),
			logMessageError.getAssertionError());
	}

	@Inject
	private Instance<LogAssertionExecutor> _logAssertionExecuterInstance;

}