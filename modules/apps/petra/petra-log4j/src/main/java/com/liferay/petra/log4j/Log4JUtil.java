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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.LogFactory;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/**
 * @author Brian Wing Shun Chan
 * @author Tomas Polesovsky
 */
public class Log4JUtil {

	public static void configureLog4J(ClassLoader classLoader) {
		configureLog4J(classLoader.getResource("META-INF/portal-log4j.xml"));

		try {
			Enumeration<URL> enu = classLoader.getResources(
				"META-INF/portal-log4j-ext.xml");

			while (enu.hasMoreElements()) {
				configureLog4J(enu.nextElement());
			}
		}
		catch (IOException ioe) {
			java.util.logging.Logger logger =
				java.util.logging.Logger.getLogger(Log4JUtil.class.getName());

			logger.log(
				java.util.logging.Level.WARNING,
				"Unable to load portal-log4j-ext.xml", ioe);
		}
	}

	public static void configureLog4J(URL url) {
		if (url == null) {
			return;
		}

		String urlContent = _getURLContent(url);

		if (urlContent == null) {
			return;
		}

		// See LPS-6029, LPS-8865, and LPS-24280

		DOMConfigurator domConfigurator = new DOMConfigurator();

		domConfigurator.doConfigure(
			new UnsyncStringReader(urlContent),
			LogManager.getLoggerRepository());

		try {
			SAXReader saxReader = new SAXReader();

			saxReader.setEntityResolver(
				new EntityResolver() {

					@Override
					public InputSource resolveEntity(
						String publicId, String systemId) {

						if (systemId.endsWith("log4j.dtd")) {
							return new InputSource(
								DOMConfigurator.class.getResourceAsStream(
									"log4j.dtd"));
						}

						return null;
					}

				});

			Document document = saxReader.read(
				new UnsyncStringReader(urlContent), url.toExternalForm());

			Element rootElement = document.getRootElement();

			List<Element> categoryElements = rootElement.elements("category");

			for (Element categoryElement : categoryElements) {
				String name = categoryElement.attributeValue("name");

				Element priorityElement = categoryElement.element("priority");

				String priority = priorityElement.attributeValue("value");

				java.util.logging.Logger jdkLogger =
					java.util.logging.Logger.getLogger(name);

				jdkLogger.setLevel(_getJdkLevel(priority));
			}
		}
		catch (Exception e) {
			_logger.error(e, e);
		}
	}

	public static Map<String, String> getCustomLogSettings() {
		return new HashMap<>(_customLogSettings);
	}

	public static String getOriginalLevel(String className) {
		Level level = Level.ALL;

		Enumeration<Logger> enu = LogManager.getCurrentLoggers();

		while (enu.hasMoreElements()) {
			Logger logger = enu.nextElement();

			if (className.equals(logger.getName())) {
				level = logger.getLevel();

				break;
			}
		}

		return level.toString();
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
		catch (Exception e) {
			_logger.error(e, e);
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

	private static String _escapeXMLAttribute(String s) {
		return StringUtil.replace(
			s,
			new char[] {
				CharPool.AMPERSAND, CharPool.APOSTROPHE, CharPool.LESS_THAN,
				CharPool.QUOTE
			},
			new String[] {"&amp;", "&apos;", "&lt;", "&quot;"});
	}

	/**
	 * @see com.liferay.portal.util.FileImpl#getBytes(InputStream, int, boolean)
	 */
	private static byte[] _getBytes(InputStream inputStream)
		throws IOException {

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		StreamUtil.transfer(inputStream, unsyncByteArrayOutputStream, -1, true);

		return unsyncByteArrayOutputStream.toByteArray();
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

	private static String _getURLContent(URL url) {
		Map<String, String> variables = new HashMap<>();

		variables.put("@liferay.home@", _getLiferayHome());

		String spiId = System.getProperty("spi.id");

		if (spiId == null) {
			spiId = StringPool.BLANK;
		}

		variables.put("@spi.id@", spiId);

		String urlContent = null;

		try (InputStream inputStream = url.openStream()) {
			byte[] bytes = _getBytes(inputStream);

			urlContent = new String(bytes, StringPool.UTF8);
		}
		catch (Exception e) {
			_logger.error(e, e);

			return null;
		}

		for (Map.Entry<String, String> variable : variables.entrySet()) {
			urlContent = StringUtil.replace(
				urlContent, variable.getKey(), variable.getValue());
		}

		if (ServerDetector.getServerId() != null) {
			return urlContent;
		}

		urlContent = _removeAppender(urlContent, "TEXT_FILE");

		return _removeAppender(urlContent, "XML_FILE");
	}

	private static String _removeAppender(String content, String appenderName) {
		int x = content.indexOf("<appender name=\"" + appenderName + "\"");

		int y = content.indexOf("</appender>", x);

		if (y != -1) {
			y = content.indexOf("<", y + 1);
		}

		if ((x != -1) && (y != -1)) {
			content = content.substring(0, x) + content.substring(y);
		}

		return StringUtil.replace(
			content, "<appender-ref ref=\"" + appenderName + "\" />",
			StringPool.BLANK);
	}

	private static final Logger _logger = Logger.getRootLogger();

	private static final Map<String, String> _customLogSettings =
		new ConcurrentHashMap<>();
	private static String _liferayHome;

}