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

package com.liferay.portal.log;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogWrapper;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author Brian Wing Shun Chan
 */
public class Log4jLogImpl implements Log {

	public Log4jLogImpl(Logger logger) {
		_logger = logger;
	}

	@Override
	public void debug(Object msg) {
		_logger.log(_FQCN, Level.DEBUG, msg, null);
	}

	@Override
	public void debug(Object msg, Throwable t) {
		_logger.log(_FQCN, Level.DEBUG, msg, t);
	}

	@Override
	public void debug(Throwable t) {
		_logger.log(_FQCN, Level.DEBUG, null, t);
	}

	@Override
	public void error(Object msg) {
		_logger.log(_FQCN, Level.ERROR, msg, null);
	}

	@Override
	public void error(Object msg, Throwable t) {
		_logger.log(_FQCN, Level.ERROR, msg, t);
	}

	@Override
	public void error(Throwable t) {
		_logger.log(_FQCN, Level.ERROR, null, t);
	}

	@Override
	public void fatal(Object msg) {
		_logger.log(_FQCN, Level.FATAL, msg, null);
	}

	@Override
	public void fatal(Object msg, Throwable t) {
		_logger.log(_FQCN, Level.FATAL, msg, t);
	}

	@Override
	public void fatal(Throwable t) {
		_logger.log(_FQCN, Level.FATAL, null, t);
	}

	public Logger getWrappedLogger() {
		return _logger;
	}

	@Override
	public void info(Object msg) {
		_logger.log(_FQCN, Level.INFO, msg, null);
	}

	@Override
	public void info(Object msg, Throwable t) {
		_logger.log(_FQCN, Level.INFO, msg, t);
	}

	@Override
	public void info(Throwable t) {
		_logger.log(_FQCN, Level.INFO, null, t);
	}

	@Override
	public boolean isDebugEnabled() {
		return _logger.isDebugEnabled();
	}

	@Override
	public boolean isErrorEnabled() {
		return _logger.isEnabledFor(Level.ERROR);
	}

	@Override
	public boolean isFatalEnabled() {
		return _logger.isEnabledFor(Level.FATAL);
	}

	@Override
	public boolean isInfoEnabled() {
		return _logger.isInfoEnabled();
	}

	@Override
	public boolean isTraceEnabled() {
		return _logger.isTraceEnabled();
	}

	@Override
	public boolean isWarnEnabled() {
		return _logger.isEnabledFor(Level.WARN);
	}

	@Override
	public void trace(Object msg) {
		_logger.log(_FQCN, Level.TRACE, msg, null);
	}

	@Override
	public void trace(Object msg, Throwable t) {
		_logger.log(_FQCN, Level.TRACE, msg, t);
	}

	@Override
	public void trace(Throwable t) {
		_logger.log(_FQCN, Level.TRACE, null, t);
	}

	@Override
	public void warn(Object msg) {
		_logger.log(_FQCN, Level.WARN, msg, null);
	}

	@Override
	public void warn(Object msg, Throwable t) {
		_logger.log(_FQCN, Level.WARN, msg, t);
	}

	@Override
	public void warn(Throwable t) {
		_logger.log(_FQCN, Level.WARN, null, t);
	}

	private static final String _FQCN = LogWrapper.class.getName();

	private Logger _logger;

}