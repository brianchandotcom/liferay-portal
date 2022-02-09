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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.AbstractConfiguration;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.config.plugins.util.PluginManager;
import org.apache.logging.log4j.core.config.xml.XmlConfiguration;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author Tina Tian
 */
public class Log4jConfigUtil {

	public static Map<String, String> configureLog4J(
		String xmlContent, String... removedAppenderNames) {

		try {
			SAXReader saxReader = new SAXReader();

			Document document = saxReader.read(
				new UnsyncStringReader(xmlContent));

			Element rootElement = document.getRootElement();

			Map<String, String> priorities = new HashMap<>();

			AbstractConfiguration abstractConfiguration;

			if (Objects.equals("Configuration", rootElement.getName())) {
				if (!GetterUtil.getBoolean(
						rootElement.attributeValue("strict"))) {

					_log.error(
						"<Configuration> strict attribute requires true");

					return Collections.emptyMap();
				}

				for (Element element : rootElement.elements()) {
					_removeAppender(
						element, "AppenderRef", "Appender",
						removedAppenderNames);

					for (Element childElement : element.elements("Logger")) {
						priorities.put(
							childElement.attributeValue("name"),
							childElement.attributeValue("level"));
					}
				}

				if (removedAppenderNames.length > 0) {
					xmlContent = document.asXML();
				}

				abstractConfiguration = new XmlConfiguration(
					_loggerContext,
					new ConfigurationSource(
						new UnsyncByteArrayInputStream(
							xmlContent.getBytes(StringPool.UTF8)))) {

					@Override
					protected void setToDefault() {
					}

				};
			}
			else {
				_removeAppender(
					rootElement, "appender-ref", "appender",
					removedAppenderNames);

				for (Element childElement : rootElement.elements("category")) {
					Element priorityElement = childElement.element("priority");

					priorities.put(
						childElement.attributeValue("name"),
						priorityElement.attributeValue("value"));
				}

				if ((removedAppenderNames.length > 0) ||
					_renameLog4j1Appenders(rootElement)) {

					xmlContent = document.asXML();
				}

				abstractConfiguration =
					new org.apache.log4j.xml.XmlConfiguration(
						_loggerContext,
						new ConfigurationSource(
							new UnsyncByteArrayInputStream(
								xmlContent.getBytes(StringPool.UTF8))),
						0) {

						@Override
						protected void setToDefault() {
						}

					};
			}

			_centralizedConfiguration.addConfiguration(abstractConfiguration);

			return priorities;
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return Collections.emptyMap();
	}

	public static java.util.logging.Level getJDKLevel(String levelString) {
		if (StringUtil.equalsIgnoreCase(levelString, Level.DEBUG.toString())) {
			return java.util.logging.Level.FINE;
		}
		else if (StringUtil.equalsIgnoreCase(
					levelString, Level.ERROR.toString())) {

			return java.util.logging.Level.SEVERE;
		}
		else if (StringUtil.equalsIgnoreCase(
					levelString, Level.WARN.toString())) {

			return java.util.logging.Level.WARNING;
		}

		return java.util.logging.Level.INFO;
	}

	public static Map<String, String> getPriorities() {
		Map<String, String> priorities = new HashMap<>();

		Map<String, LoggerConfig> loggerConfigs =
			_centralizedConfiguration.getLoggers();

		for (LoggerConfig loggerConfig : loggerConfigs.values()) {
			String loggerConfigName = loggerConfig.getName();

			if (!Objects.equals(
					loggerConfigName, LogManager.ROOT_LOGGER_NAME)) {

				priorities.put(
					loggerConfigName, String.valueOf(loggerConfig.getLevel()));
			}
		}

		return priorities;
	}

	public static void setLevel(String name, String priority) {
		Level level = Level.toLevel(priority);

		LoggerConfig loggerConfig = _centralizedConfiguration.getLogger(name);

		if (loggerConfig == null) {
			loggerConfig = new LoggerConfig(name, level, true);

			_centralizedConfiguration.addLogger(name, loggerConfig);
		}
		else {
			loggerConfig.setLevel(level);
		}

		_loggerContext.updateLoggers();
	}

	public static void shutdownLog4J() {
		LogManager.shutdown();
	}

	private static String _getLog4j1AppenderSuffix(Element element) {
		String suffix = StringUtil.randomString();

		for (Element childElement : element.elements("rollingPolicy")) {
			for (Element paramElement : childElement.elements("param")) {
				if (Objects.equals(
						paramElement.attributeValue("name"),
						"FileNamePattern")) {

					String value = paramElement.attributeValue("value");

					value = value.substring(
						value.lastIndexOf(StringPool.SLASH) + 1);

					suffix = value.substring(
						0, value.indexOf(StringPool.PERIOD));
				}
			}
		}

		return StringPool.UNDERLINE.concat(suffix);
	}

	private static void _removeAppender(
		Element parentElement, String appenderRefTagName,
		String appenderTagName, String... removedAppenderNames) {

		if (removedAppenderNames.length == 0) {
			return;
		}

		for (Element element : parentElement.elements()) {
			for (String appenderName : removedAppenderNames) {
				if (Objects.equals(appenderTagName, element.getName()) &&
					Objects.equals(
						appenderName, element.attributeValue("name"))) {

					parentElement.remove(element);
				}

				for (Element childElement : element.elements()) {
					if (Objects.equals(
							appenderRefTagName, childElement.getName()) &&
						Objects.equals(
							appenderName, childElement.attributeValue("ref"))) {

						element.remove(childElement);
					}
				}
			}
		}
	}

	private static boolean _renameLog4j1Appenders(Element parentElement) {
		Map<String, String> newAppenderNames = new HashMap<>();

		for (Element element : parentElement.elements("appender")) {
			String appenderName = element.attributeValue("name");

			if (_reservedAppenderNames.contains(appenderName)) {
				String newAppenderName = appenderName.concat(
					_getLog4j1AppenderSuffix(element));

				newAppenderNames.put(appenderName, newAppenderName);

				element.addAttribute("name", newAppenderName);
			}
		}

		if (newAppenderNames.isEmpty()) {
			return false;
		}

		for (Element element : parentElement.elements()) {
			for (Element childElement : element.elements("appender-ref")) {
				String newAppenderName = newAppenderNames.get(
					childElement.attributeValue("ref"));

				if (newAppenderName != null) {
					childElement.addAttribute("ref", newAppenderName);
				}
			}
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		Log4jConfigUtil.class);

	private static final CentralizedConfiguration _centralizedConfiguration;
	private static final LoggerContext _loggerContext =
		LoggerContext.getContext();
	private static final List<String> _reservedAppenderNames = Arrays.asList(
		"TEXT_FILE", "XML_FILE");

	static {
		PluginManager.addPackage("com.liferay.petra.log4j.internal");

		_centralizedConfiguration = new CentralizedConfiguration(
			_loggerContext);

		_loggerContext.setConfiguration(_centralizedConfiguration);
	}

}