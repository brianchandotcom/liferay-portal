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

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Brian Wing Shun Chan
 */
public class Jdk14LogImpl implements Log {

	public Jdk14LogImpl(Logger log) {
		_log = log;
	}

	@Override
	public void debug(Object msg) {
		_log.log(Level.FINE, msg.toString());
	}

	@Override
	public void debug(Object msg, Throwable t) {
		_log.log(Level.FINE, msg.toString(), t);
	}

	@Override
	public void debug(Throwable t) {
		_log.log(Level.FINE, t.getMessage(), t);
	}

	@Override
	public void error(Object msg) {
		_log.log(Level.SEVERE, msg.toString());
	}

	@Override
	public void error(Object msg, Throwable t) {
		_log.log(Level.SEVERE, msg.toString(), t);
	}

	@Override
	public void error(Throwable t) {
		_log.log(Level.SEVERE, t.getMessage(), t);
	}

	@Override
	public void fatal(Object msg) {
		_log.log(Level.SEVERE, msg.toString());
	}

	@Override
	public void fatal(Object msg, Throwable t) {
		_log.log(Level.SEVERE, msg.toString(), t);
	}

	@Override
	public void fatal(Throwable t) {
		_log.log(Level.SEVERE, t.getMessage(), t);
	}

	public Logger getWrappedLogger() {
		return _log;
	}

	@Override
	public void info(Object msg) {
		_log.log(Level.INFO, msg.toString());
	}

	@Override
	public void info(Object msg, Throwable t) {
		_log.log(Level.INFO, msg.toString(), t);
	}

	@Override
	public void info(Throwable t) {
		_log.log(Level.INFO, t.getMessage(), t);
	}

	@Override
	public boolean isDebugEnabled() {
		return _log.isLoggable(Level.FINE);
	}

	@Override
	public boolean isErrorEnabled() {
		return _log.isLoggable(Level.SEVERE);
	}

	@Override
	public boolean isFatalEnabled() {
		return _log.isLoggable(Level.SEVERE);
	}

	@Override
	public boolean isInfoEnabled() {
		return _log.isLoggable(Level.INFO);
	}

	@Override
	public boolean isTraceEnabled() {
		return _log.isLoggable(Level.FINEST);
	}

	@Override
	public boolean isWarnEnabled() {
		return _log.isLoggable(Level.WARNING);
	}

	@Override
	public void trace(Object msg) {
		_log.log(Level.FINEST, msg.toString());
	}

	@Override
	public void trace(Object msg, Throwable t) {
		_log.log(Level.FINEST, msg.toString(), t);
	}

	@Override
	public void trace(Throwable t) {
		_log.log(Level.FINEST, t.getMessage(), t);
	}

	@Override
	public void warn(Object msg) {
		_log.log(Level.WARNING, msg.toString());
	}

	@Override
	public void warn(Object msg, Throwable t) {
		_log.log(Level.WARNING, msg.toString(), t);
	}

	@Override
	public void warn(Throwable t) {
		_log.log(Level.WARNING, t.getMessage(), t);
	}

	private Logger _log;

}