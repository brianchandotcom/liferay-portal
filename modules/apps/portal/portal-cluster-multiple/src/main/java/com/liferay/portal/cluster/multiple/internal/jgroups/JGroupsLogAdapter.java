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

package com.liferay.portal.cluster.multiple.internal.jgroups;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

/**
 * @author Tina Tian
 */
public class JGroupsLogAdapter implements org.jgroups.logging.Log {

	public JGroupsLogAdapter(Class<?> clazz) {
		_log = LogFactoryUtil.getLog(clazz);
	}

	public JGroupsLogAdapter(String category) {
		_log = LogFactoryUtil.getLog(category);
	}

	@Override
	public void debug(String message) {
		_log.debug(message);
	}

	@Override
	public void debug(String message, Object... args) {
		_log.debug(String.format(LocaleUtil.getDefault(), message, args));
	}

	@Override
	public void debug(String message, Throwable throwable) {
		_log.debug(message, throwable);
	}

	@Override
	public void error(String message) {
		_log.error(message);
	}

	@Override
	public void error(String message, Object... args) {
		_log.error(String.format(LocaleUtil.getDefault(), message, args));
	}

	@Override
	public void error(String message, Throwable throwable) {
		_log.error(message, throwable);
	}

	@Override
	public void fatal(String message) {
		_log.fatal(message);
	}

	@Override
	public void fatal(String message, Object... args) {
		_log.fatal(String.format(LocaleUtil.getDefault(), message, args));
	}

	@Override
	public void fatal(String message, Throwable throwable) {
		_log.fatal(message, throwable);
	}

	@Override
	public String getLevel() {
		if (_log.isTraceEnabled()) {
			return "TRACE";
		}
		else if (_log.isDebugEnabled()) {
			return "DEBUG";
		}
		else if (_log.isInfoEnabled()) {
			return "INFO";
		}
		else if (_log.isWarnEnabled()) {
			return "WARN";
		}
		else if (_log.isErrorEnabled()) {
			return "ERROR";
		}
		else if (_log.isFatalEnabled()) {
			return "FATAL";
		}
		else {
			return "NONE";
		}
	}

	@Override
	public void info(String message) {
		_log.info(message);
	}

	@Override
	public void info(String message, Object... args) {
		_log.info(String.format(LocaleUtil.getDefault(), message, args));
	}

	@Override
	public boolean isDebugEnabled() {
		return _log.isDebugEnabled();
	}

	@Override
	public boolean isErrorEnabled() {
		return _log.isErrorEnabled();
	}

	@Override
	public boolean isFatalEnabled() {
		return _log.isFatalEnabled();
	}

	@Override
	public boolean isInfoEnabled() {
		return _log.isInfoEnabled();
	}

	@Override
	public boolean isTraceEnabled() {
		return _log.isTraceEnabled();
	}

	@Override
	public boolean isWarnEnabled() {
		return _log.isWarnEnabled();
	}

	@Override
	public void setLevel(String level) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void trace(Object message) {
		_log.trace(message);
	}

	@Override
	public void trace(String message) {
		_log.trace(message);
	}

	@Override
	public void trace(String message, Object... args) {
		_log.trace(String.format(LocaleUtil.getDefault(), message, args));
	}

	@Override
	public void trace(String message, Throwable throwable) {
		_log.trace(message, throwable);
	}

	@Override
	public void warn(String message) {
		_log.warn(message);
	}

	@Override
	public void warn(String message, Object... args) {
		_log.warn(String.format(LocaleUtil.getDefault(), message, args));
	}

	@Override
	public void warn(String message, Throwable throwable) {
		_log.warn(message, throwable);
	}

	private final Log _log;

}