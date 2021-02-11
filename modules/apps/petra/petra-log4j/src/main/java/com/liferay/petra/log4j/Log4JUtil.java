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

package com.liferay.petra.log4j;

import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactory;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.xml.DOMConfigurator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author Brian Wing Shun Chan
 * @author Tomas Polesovsky
 */
public class Log4JUtil {

	public static void configureLog4J(ClassLoader classLoader) {
		configureLog4J(classLoader.getResource("META-INF/portal-log4j.xml"));

		try {
			Enumeration<URL> enumeration = classLoader.getResources(
				"META-INF/portal-log4j-ext.xml");

			while (enumeration.hasMoreElements()) {
				configureLog4J(enumeration.nextElement());
			}
		}
		catch (IOException ioException) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to load portal-log4j-ext.xml", ioException);
			}
		}
	}

	public static void configureLog4J(URL url) {
		if (url == null) {
			return;
		}

		Document document = null;

		try (InputStream inputStream = url.openStream()) {
			SAXReader saxReader = new SAXReader();

			document = saxReader.read(
				new UnsyncStringReader(
					StreamUtil.toString(inputStream, StringPool.UTF8)),
				url.toExternalForm());
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return;
		}

		Map<String, String> priorities = new HashMap<>();

		Element rootElement = document.getRootElement();

		for (Element element : rootElement.elements()) {
			if (ServerDetector.getServerId() == null) {
				_removeAppender(rootElement, element, "TEXT_FILE");
				_removeAppender(rootElement, element, "XML_FILE");
			}

			if (Objects.equals("category", element.getName())) {
				Element priorityElement = element.element("priority");

				priorities.put(
					element.attributeValue("name"),
					priorityElement.attributeValue("value"));
			}
		}

		// See LPS-6029, LPS-8865, and LPS-24280

		DOMConfigurator domConfigurator = new DOMConfigurator();

		domConfigurator.doConfigure(
			new UnsyncStringReader(
				StringUtil.replace(
					document.asXML(), "@liferay.home@", _getLiferayHome())),
			LogManager.getLoggerRepository());

		for (Map.Entry<String, String> entry : priorities.entrySet()) {
			java.util.logging.Logger jdkLogger =
				java.util.logging.Logger.getLogger(entry.getKey());

			jdkLogger.setLevel(_getJdkLevel(entry.getValue()));
		}
	}

	public static Map<String, String> getCustomLogSettings() {
		return new HashMap<>(_customLogSettings);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public static String getOriginalLevel(String className) {
		Map<String, String> priorities = getPriorities();

		String logLevelString = priorities.get(className);

		if (Validator.isNull(logLevelString)) {
			return String.valueOf(Level.ALL);
		}

		return logLevelString;
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

	public static void initLog4J(
		String serverId, String liferayHome, ClassLoader classLoader,
		LogFactory logFactory, Map<String, String> customLogSettings) {

		System.setProperty(
			ServerDetector.SYSTEM_PROPERTY_KEY_SERVER_DETECTOR_SERVER_ID,
			serverId);

		_liferayHome = _escapeXMLAttribute(liferayHome);

		configureLog4J(classLoader);

		try {
			LogFactoryUtil.setLogFactory(logFactory);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		for (Map.Entry<String, String> entry : customLogSettings.entrySet()) {
			setLevel(entry.getKey(), entry.getValue(), false);
		}
	}

	public static void setLevel(String name, String priority, boolean custom) {
		Logger logger = Logger.getLogger(name);

		logger.setLevel(Level.toLevel(priority));

		java.util.logging.Logger jdkLogger = java.util.logging.Logger.getLogger(
			name);

		jdkLogger.setLevel(_getJdkLevel(priority));

		if (custom) {
			_customLogSettings.put(name, priority);
		}
	}

	public static void shutdownLog4J() {
		LoggerRepository loggerRepository = LogManager.getLoggerRepository();

		loggerRepository.shutdown();
	}

	private static String _escapeXMLAttribute(String s) {
		return StringUtil.replace(
			s,
			new char[] {
				CharPool.AMPERSAND, CharPool.APOSTROPHE, CharPool.LESS_THAN,
				CharPool.QUOTE
			},
			new String[] {"&amp;", "&apos;", "&lt;", "&quot;"});
	}

	private static java.util.logging.Level _getJdkLevel(String priority) {
		if (StringUtil.equalsIgnoreCase(priority, Level.DEBUG.toString())) {
			return java.util.logging.Level.FINE;
		}
		else if (StringUtil.equalsIgnoreCase(
					priority, Level.ERROR.toString())) {

			return java.util.logging.Level.SEVERE;
		}
		else if (StringUtil.equalsIgnoreCase(priority, Level.WARN.toString())) {
			return java.util.logging.Level.WARNING;
		}

		return java.util.logging.Level.INFO;
	}

	private static String _getLiferayHome() {
		if (_liferayHome == null) {
			_liferayHome = _escapeXMLAttribute(
				PropsUtil.get(PropsKeys.LIFERAY_HOME));
		}

		return _liferayHome;
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

	private static final Log _log = LogFactoryUtil.getLog(Log4JUtil.class);

	private static final Map<String, String> _customLogSettings =
		new ConcurrentHashMap<>();
	private static String _liferayHome;

}