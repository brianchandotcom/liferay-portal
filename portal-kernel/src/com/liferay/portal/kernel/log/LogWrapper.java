/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.log;

/**
 * @author Brian Wing Shun Chan
 */
public class LogWrapper implements Log {

	public LogWrapper(Log log) {
		_log = log;
	}

	@Override
	public void debug(Object msg) {
		try {
			_log.debug(msg);
		}
		catch (Exception exception) {
			printMsg(msg);
		}
	}

	@Override
	public void debug(Object msg, Throwable throwable) {
		try {
			_log.debug(msg, throwable);
		}
		catch (Exception exception) {
			printMsg(msg);
		}
	}

	@Override
	public void debug(Throwable throwable) {
		try {
			_log.debug(throwable);
		}
		catch (Exception exception) {
			printMsg(throwable.getMessage());
		}
	}

	@Override
	public void error(Object msg) {
		try {
			_log.error(msg);
		}
		catch (Exception exception) {
			printMsg(msg);
		}
	}

	@Override
	public void error(Object msg, Throwable throwable) {
		try {
			_log.error(msg, throwable);
		}
		catch (Exception exception) {
			printMsg(msg);
		}
	}

	@Override
	public void error(Throwable throwable) {
		try {
			_log.error(throwable);
		}
		catch (Exception exception) {
			printMsg(throwable.getMessage());
		}
	}

	@Override
	public void fatal(Object msg) {
		try {
			_log.fatal(msg);
		}
		catch (Exception exception) {
			printMsg(msg);
		}
	}

	@Override
	public void fatal(Object msg, Throwable throwable) {
		try {
			_log.fatal(msg, throwable);
		}
		catch (Exception exception) {
			printMsg(msg);
		}
	}

	@Override
	public void fatal(Throwable throwable) {
		try {
			_log.fatal(throwable);
		}
		catch (Exception exception) {
			printMsg(throwable.getMessage());
		}
	}

	public Log getWrappedLog() {
		return _log;
	}

	@Override
	public void info(Object msg) {
		try {
			_log.info(msg);
		}
		catch (Exception exception) {
			printMsg(msg);
		}
	}

	@Override
	public void info(Object msg, Throwable throwable) {
		try {
			_log.info(msg, throwable);
		}
		catch (Exception exception) {
			printMsg(msg);
		}
	}

	@Override
	public void info(Throwable throwable) {
		try {
			_log.info(throwable);
		}
		catch (Exception exception) {
			printMsg(throwable.getMessage());
		}
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

	public void setLog(Log log) {
		_log = log;
	}

	@Override
	public void setLogWrapperClassName(String className) {
		_log.setLogWrapperClassName(className);
	}

	@Override
	public void trace(Object msg) {
		try {
			_log.trace(msg);
		}
		catch (Exception exception) {
			printMsg(msg);
		}
	}

	@Override
	public void trace(Object msg, Throwable throwable) {
		try {
			_log.trace(msg, throwable);
		}
		catch (Exception exception) {
			printMsg(msg);
		}
	}

	@Override
	public void trace(Throwable throwable) {
		try {
			_log.trace(throwable);
		}
		catch (Exception exception) {
			printMsg(throwable.getMessage());
		}
	}

	@Override
	public void warn(Object msg) {
		try {
			_log.warn(msg);
		}
		catch (Exception exception) {
			printMsg(msg);
		}
	}

	@Override
	public void warn(Object msg, Throwable throwable) {
		try {
			_log.warn(msg, throwable);
		}
		catch (Exception exception) {
			printMsg(msg);
		}
	}

	@Override
	public void warn(Throwable throwable) {
		try {
			_log.warn(throwable);
		}
		catch (Exception exception) {
			printMsg(throwable.getMessage());
		}
	}

	protected void printMsg(Object msg) {
		System.err.println(msg);
	}

	private Log _log;

}