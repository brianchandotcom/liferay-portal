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

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.xml.DOMConfigurator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author Tina Tian
 */
public class Log4jConfigUtil {

	public static Map<String, String> configureLog4J(
		String xmlContent, String... removedAppenderNames) {

		Document document = null;

		try {
			SAXReader saxReader = new SAXReader();

			document = saxReader.read(new UnsyncStringReader(xmlContent));
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return Collections.emptyMap();
		}

		Map<String, String> priorities = new HashMap<>();

		Element rootElement = document.getRootElement();

		for (Element element : rootElement.elements()) {
			for (String appenderName : removedAppenderNames) {
				_removeAppender(rootElement, element, appenderName);
			}

			if (Objects.equals("category", element.getName())) {
				Element priorityElement = element.element("priority");

				priorities.put(
					element.attributeValue("name"),
					priorityElement.attributeValue("value"));
			}
		}

		DOMConfigurator domConfigurator = new DOMConfigurator();

		domConfigurator.doConfigure(
			new UnsyncStringReader(document.asXML()),
			LogManager.getLoggerRepository());

		return priorities;
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

		Enumeration<Logger> enumeration = LogManager.getCurrentLoggers();

		while (enumeration.hasMoreElements()) {
			Logger logger = enumeration.nextElement();

			Level level = logger.getLevel();

			if (level != null) {
				priorities.put(logger.getName(), level.toString());
			}
		}

		return priorities;
	}

	public static void setLevel(String name, String priority) {
		Logger logger = Logger.getLogger(name);

		logger.setLevel(Level.toLevel(priority));
	}

	public static void shutdownLog4J() {
		LoggerRepository loggerRepository = LogManager.getLoggerRepository();

		loggerRepository.shutdown();
	}

	private static void _removeAppender(
		Element rootElement, Element element, String appenderName) {

		if (Objects.equals("appender", element.getName()) &&
			Objects.equals(appenderName, element.attributeValue("name"))) {

			rootElement.remove(element);
		}

		for (Element childElement : element.elements()) {
			if (Objects.equals("appender-ref", childElement.getName()) &&
				Objects.equals(
					appenderName, childElement.attributeValue("ref"))) {

				element.remove(childElement);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		Log4jConfigUtil.class);

}