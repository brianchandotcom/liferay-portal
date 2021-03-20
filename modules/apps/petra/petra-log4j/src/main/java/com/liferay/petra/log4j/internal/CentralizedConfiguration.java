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

package com.liferay.petra.log4j.internal;

import com.liferay.petra.reflect.ReflectionUtil;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.AbstractConfiguration;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.filter.AbstractFilterable;

/**
 * @author Dante Wang
 * @see org.apache.logging.log4j.core.config.composite.DefaultMergeStrategy
 */
public class CentralizedConfiguration extends AbstractConfiguration {

	public CentralizedConfiguration(LoggerContext loggerContext) {
		super(loggerContext, ConfigurationSource.COMPOSITE_SOURCE);
	}

	public void addConfiguration(AbstractConfiguration abstractConfiguration) {
		if (abstractConfiguration.getState() != State.INITIALIZING) {
			return;
		}

		abstractConfiguration.initialize();

		Map<String, String> properties = getProperties();

		properties.putAll(abstractConfiguration.getProperties());

		_aggregateAppenders(abstractConfiguration);

		_aggregateFilter(this, abstractConfiguration);

		_aggregateLoggerConfigs(abstractConfiguration);

		LoggerContext loggerContext = getLoggerContext();

		loggerContext.updateLoggers();
	}

	@Override
	public void start() {
		LoggerConfig rootLoggerConfig = getRootLogger();

		rootLoggerConfig.start();

		addLogger(LogManager.ROOT_LOGGER_NAME, rootLoggerConfig);

		setStarted();
	}

	private void _aggregateAppenders(
		AbstractConfiguration abstractConfiguration) {

		Map<String, Appender> currentAppenders = getAppenders();

		Map<String, Appender> newAppenders =
			abstractConfiguration.getAppenders();

		for (Appender newAppender : newAppenders.values()) {
			newAppender.start();

			String appenderName = newAppender.getName();

			Appender currentAppender = currentAppenders.put(
				appenderName, newAppender);

			if (currentAppender == null) {
				continue;
			}

			Map<String, LoggerConfig> loggerConfigs = getLoggers();

			for (LoggerConfig loggerConfig : loggerConfigs.values()) {
				Map<String, Appender> appenders = loggerConfig.getAppenders();

				if (!appenders.containsKey(appenderName)) {
					continue;
				}

				AppenderRef appenderRef = _getAppenderRef(
					appenderName, loggerConfig);

				loggerConfig.removeAppender(appenderName);

				if (appenderRef == null) {
					loggerConfig.addAppender(newAppender, null, null);
				}
				else {
					loggerConfig.addAppender(
						newAppender, appenderRef.getLevel(),
						appenderRef.getFilter());
				}
			}

			currentAppender.stop();
		}
	}

	private void _aggregateFilter(
		AbstractFilterable currentAbstractFilterable,
		AbstractFilterable newAbstractFilterable) {

		Filter filter = newAbstractFilterable.getFilter();

		if (filter != null) {
			filter.start();

			currentAbstractFilterable.addFilter(filter);
		}
	}

	private void _aggregateLoggerConfigs(
		AbstractConfiguration abstractConfiguration) {

		LoggerConfig newRootLoggerConfig = abstractConfiguration.getLogger(
			LogManager.ROOT_LOGGER_NAME);

		if (newRootLoggerConfig != null) {
			_mergeLoggerConfig(getRootLogger(), newRootLoggerConfig);
		}

		Map<String, LoggerConfig> newLoggerConfigs =
			abstractConfiguration.getLoggers();

		for (LoggerConfig newLoggerConfig : newLoggerConfigs.values()) {
			String name = newLoggerConfig.getName();

			if (Objects.equals(name, LogManager.ROOT_LOGGER_NAME)) {
				continue;
			}

			LoggerConfig currentLoggerConfig = getLogger(name);

			if (currentLoggerConfig != null) {
				_mergeLoggerConfig(currentLoggerConfig, newLoggerConfig);

				continue;
			}

			addLogger(name, newLoggerConfig);

			newLoggerConfig.start();
		}
	}

	private AppenderRef _getAppenderRef(
		String name, LoggerConfig loggerConfig) {

		for (AppenderRef appenderRef : loggerConfig.getAppenderRefs()) {
			if (Objects.equals(appenderRef.getRef(), name)) {
				return appenderRef;
			}
		}

		return null;
	}

	private void _mergeLoggerConfig(
		LoggerConfig currentLoggerConfig, LoggerConfig newLoggerConfig) {

		currentLoggerConfig.setLevel(newLoggerConfig.getLevel());
		currentLoggerConfig.setAdditive(newLoggerConfig.isAdditive());

		_aggregateFilter(currentLoggerConfig, newLoggerConfig);

		Map<String, Appender> currentAppenders =
			currentLoggerConfig.getAppenders();

		Map<String, Appender> newAppenders = newLoggerConfig.getAppenders();

		List<AppenderRef> currentAppenderRefs = new ArrayList<>(
			currentLoggerConfig.getAppenderRefs());

		try {
			_appenderRefsField.set(currentLoggerConfig, currentAppenderRefs);
		}
		catch (IllegalAccessException illegalAccessException) {
			ReflectionUtil.throwException(illegalAccessException);
		}

		for (Appender newAppender : newAppenders.values()) {
			String name = newAppender.getName();

			AppenderRef newAppenderRef = _getAppenderRef(name, newLoggerConfig);

			if (currentAppenders.containsKey(name)) {
				currentLoggerConfig.removeAppender(name);

				Iterator<AppenderRef> currentAppenderRefIterator =
					currentAppenderRefs.iterator();

				while (currentAppenderRefIterator.hasNext()) {
					AppenderRef currentAppenderRef =
						currentAppenderRefIterator.next();

					if (Objects.equals(currentAppenderRef.getRef(), name)) {
						currentAppenderRefIterator.remove();

						break;
					}
				}
			}

			if (newAppenderRef == null) {
				currentLoggerConfig.addAppender(newAppender, null, null);
			}
			else {
				currentLoggerConfig.addAppender(
					newAppender, newAppenderRef.getLevel(),
					newAppenderRef.getFilter());

				currentAppenderRefs.add(newAppenderRef);
			}
		}
	}

	private static final Field _appenderRefsField;

	static {
		try {
			_appenderRefsField = ReflectionUtil.getDeclaredField(
				LoggerConfig.class, "appenderRefs");
		}
		catch (Exception exception) {
			throw new ExceptionInInitializerError(exception);
		}
	}

}