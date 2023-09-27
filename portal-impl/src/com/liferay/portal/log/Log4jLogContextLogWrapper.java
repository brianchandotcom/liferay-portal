/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.log;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogContext;
import com.liferay.portal.kernel.log.LogContextRegistryUtil;
import com.liferay.portal.kernel.log.LogWrapper;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import org.apache.logging.log4j.ThreadContext;

/**
 * @author Tina Tian
 */
public class Log4jLogContextLogWrapper extends LogWrapper {

	public Log4jLogContextLogWrapper(Log log, String name) {
		super(log);

		_name = name;

		setLogWrapperClassName(Log4jLogContextLogWrapper.class.getName());
	}

	@Override
	public void debug(Object message) {
		_populateThreadContext();

		super.debug(message);

		_cleanThreadContext();
	}

	@Override
	public void debug(Object message, Throwable throwable) {
		_populateThreadContext();

		super.debug(message, throwable);

		_cleanThreadContext();
	}

	@Override
	public void debug(Throwable throwable) {
		_populateThreadContext();

		super.debug(null, throwable);

		_cleanThreadContext();
	}

	@Override
	public void error(Object message) {
		_populateThreadContext();

		super.error(message);

		_cleanThreadContext();
	}

	@Override
	public void error(Object message, Throwable throwable) {
		_populateThreadContext();

		super.error(message, throwable);

		_cleanThreadContext();
	}

	@Override
	public void error(Throwable throwable) {
		_populateThreadContext();

		super.error(null, throwable);

		_cleanThreadContext();
	}

	@Override
	public void fatal(Object message) {
		_populateThreadContext();

		super.fatal(message);

		_cleanThreadContext();
	}

	@Override
	public void fatal(Object message, Throwable throwable) {
		_populateThreadContext();

		super.fatal(message, throwable);

		_cleanThreadContext();
	}

	@Override
	public void fatal(Throwable throwable) {
		_populateThreadContext();

		super.fatal(null, throwable);

		_cleanThreadContext();
	}

	@Override
	public void info(Object message) {
		_populateThreadContext();

		super.info(message);

		_cleanThreadContext();
	}

	@Override
	public void info(Object message, Throwable throwable) {
		_populateThreadContext();

		super.info(message, throwable);

		_cleanThreadContext();
	}

	@Override
	public void info(Throwable throwable) {
		_populateThreadContext();

		super.info(null, throwable);

		_cleanThreadContext();
	}

	@Override
	public void trace(Object message) {
		_populateThreadContext();

		super.trace(message);

		_cleanThreadContext();
	}

	@Override
	public void trace(Object message, Throwable throwable) {
		_populateThreadContext();

		super.trace(message, throwable);

		_cleanThreadContext();
	}

	@Override
	public void trace(Throwable throwable) {
		_populateThreadContext();

		super.trace(null, throwable);

		_cleanThreadContext();
	}

	@Override
	public void warn(Object message) {
		_populateThreadContext();

		super.warn(message);

		_cleanThreadContext();
	}

	@Override
	public void warn(Object message, Throwable throwable) {
		_populateThreadContext();

		super.warn(message, throwable);

		_cleanThreadContext();
	}

	@Override
	public void warn(Throwable throwable) {
		_populateThreadContext();

		super.warn(null, throwable);

		_cleanThreadContext();
	}

	private void _cleanThreadContext() {
		ThreadContext.clearMap();
	}

	private void _populateThreadContext() {
		for (LogContext logContext : LogContextRegistryUtil.getLogContexts()) {
			Map<String, String> context = logContext.getContext(_name);

			for (Map.Entry<String, String> entry : context.entrySet()) {
				String key = entry.getKey();

				String logContextName = logContext.getName();

				if (Validator.isNotNull(logContextName)) {
					key = logContextName + "." + key;
				}

				ThreadContext.put(key, entry.getValue());
			}
		}
	}

	private final String _name;

}