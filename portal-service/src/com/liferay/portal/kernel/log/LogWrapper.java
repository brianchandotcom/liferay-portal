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
		catch (Exception e) {
			printMsg(msg);
		}
	}

	@Override
	public void debug(Object msg, Throwable t) {
		try {
			_log.debug(msg, t);
		}
		catch (Exception e) {
			printMsg(msg);
		}
	}

	@Override
	public void debug(Throwable t) {
		try {
			_log.debug(t);
		}
		catch (Exception e) {
			printMsg(t.getMessage());
		}
	}

	@Override
	public void error(Object msg) {
		try {
			_log.error(msg);
		}
		catch (Exception e) {
			printMsg(msg);
		}
	}

	@Override
	public void error(Object msg, Throwable t) {
		try {
			_log.error(msg, t);
		}
		catch (Exception e) {
			printMsg(msg);
		}
	}

	@Override
	public void error(Throwable t) {
		try {
			_log.error(t);
		}
		catch (Exception e) {
			printMsg(t.getMessage());
		}
	}

	@Override
	public void fatal(Object msg) {
		try {
			_log.fatal(msg);
		}
		catch (Exception e) {
			printMsg(msg);
		}
	}

	@Override
	public void fatal(Object msg, Throwable t) {
		try {
			_log.fatal(msg, t);
		}
		catch (Exception e) {
			printMsg(msg);
		}
	}

	@Override
	public void fatal(Throwable t) {
		try {
			_log.fatal(t);
		}
		catch (Exception e) {
			printMsg(t.getMessage());
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
		catch (Exception e) {
			printMsg(msg);
		}
	}

	@Override
	public void info(Object msg, Throwable t) {
		try {
			_log.info(msg, t);
		}
		catch (Exception e) {
			printMsg(msg);
		}
	}

	@Override
	public void info(Throwable t) {
		try {
			_log.info(t);
		}
		catch (Exception e) {
			printMsg(t.getMessage());
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
	public void trace(Object msg) {
		try {
			_log.trace(msg);
		}
		catch (Exception e) {
			printMsg(msg);
		}
	}

	@Override
	public void trace(Object msg, Throwable t) {
		try {
			_log.trace(msg, t);
		}
		catch (Exception e) {
			printMsg(msg);
		}
	}

	@Override
	public void trace(Throwable t) {
		try {
			_log.trace(t);
		}
		catch (Exception e) {
			printMsg(t.getMessage());
		}
	}

	@Override
	public void warn(Object msg) {
		try {
			_log.warn(msg);
		}
		catch (Exception e) {
			printMsg(msg);
		}
	}

	@Override
	public void warn(Object msg, Throwable t) {
		try {
			_log.warn(msg, t);
		}
		catch (Exception e) {
			printMsg(msg);
		}
	}

	@Override
	public void warn(Throwable t) {
		try {
			_log.warn(t);
		}
		catch (Exception e) {
			printMsg(t.getMessage());
		}
	}

	protected void printMsg(Object msg) {
		System.err.println(msg);
	}

	private Log _log;

}