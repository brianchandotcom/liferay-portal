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

import com.liferay.portal.test.log.LogAssertionExecutorImpl;

import java.util.logging.Handler;

import org.apache.log4j.AppenderSkeleton;

import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;

/**
 * @author Cristina González
 */
public class LogAssertionExecutorInArquillian extends LogAssertionExecutorImpl {

	@Override
	protected AppenderSkeleton getLog4jAppender() {
		return _logAssertionAppenderArquillianInstance.get();
	}

	@Override
	protected Handler getLogAssertionHandler() {
		return _logAssertionHandlerArquillianInstance.get();
	}

	@Inject
	private Instance<LogAssertionAppenderArquillian>
		_logAssertionAppenderArquillianInstance;

	@Inject
	private Instance<LogAssertionHandlerArquillian>
		_logAssertionHandlerArquillianInstance;

}