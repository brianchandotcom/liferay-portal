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

package com.liferay.portal.upgrade.internal.apache.logging.log4j.core;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.ReleaseManager;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.internal.report.UpgradeReport;
import com.liferay.portal.upgrade.util.DBUpgradeStatus;
import com.liferay.portal.util.PropsValues;

import java.io.Serializable;

import java.util.Objects;

import org.apache.felix.cm.PersistenceManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.ErrorHandler;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.message.Message;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

/**
 * @author Sam Ziemer
 */
@Component(
	property = "appender.name=UpgradeLogAppender", service = Appender.class
)
public class UpgradeLogAppender implements Appender {

	@Override
	public void append(LogEvent logEvent) {
		Message message = logEvent.getMessage();

		String formattedMessage = message.getFormattedMessage();

		if (formattedMessage.equals(StringPool.NULL)) {
			Throwable throwable = logEvent.getThrown();

			formattedMessage = throwable.getMessage();
		}

		if (logEvent.getLevel() == Level.ERROR) {
			DBUpgradeStatus.addErrorMessage(
				logEvent.getLoggerName(), formattedMessage);
		}
		else if (logEvent.getLevel() == Level.INFO) {
			if (Objects.equals(
					logEvent.getLoggerName(), UpgradeProcess.class.getName()) &&
				formattedMessage.startsWith("Completed upgrade process ")) {

				DBUpgradeStatus.addUpgradeProcessMessage(
					logEvent.getLoggerName(), formattedMessage);
			}
		}
		else if (logEvent.getLevel() == Level.WARN) {
			DBUpgradeStatus.addWarningMessage(
				logEvent.getLoggerName(), message.getFormattedMessage());
		}
	}

	@Override
	public ErrorHandler getHandler() {
		return null;
	}

	@Override
	public Layout<? extends Serializable> getLayout() {
		return null;
	}

	@Override
	public String getName() {
		return "UpgradeLogAppender";
	}

	@Override
	public State getState() {
		return null;
	}

	@Override
	public boolean ignoreExceptions() {
		return false;
	}

	@Override
	public void initialize() {
	}

	@Override
	public boolean isStarted() {
		return _started;
	}

	@Override
	public boolean isStopped() {
		return !_started;
	}

	@Override
	public void setHandler(ErrorHandler handler) {
	}

	@Override
	public void start() {
		_started = true;

		DBUpgradeStatus.start();

		if (PropsValues.UPGRADE_REPORT_ENABLED) {
			_upgradeReport = new UpgradeReport();
		}

		_rootLogger.addAppender(this);
	}

	@Override
	public void stop() {
		if (_started) {
			DBUpgradeStatus.finish(_releaseManager);

			if (_upgradeReport != null) {
				_upgradeReport.generateReport(
					_persistenceManager, _releaseManager);

				_upgradeReport = null;
			}
		}

		_started = false;
	}

	private static final Logger _rootLogger =
		(Logger)LogManager.getRootLogger();

	@Reference
	private PersistenceManager _persistenceManager;

	@Reference(cardinality = ReferenceCardinality.OPTIONAL)
	private volatile ReleaseManager _releaseManager;

	private volatile boolean _started;
	private volatile UpgradeReport _upgradeReport;

}