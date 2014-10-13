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

package com.liferay.portal.util;

import com.liferay.portal.configuration.ConfigurationImpl;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.WebDirDetector;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.security.auth.CompanyThreadLocal;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.Servlet;

/**
 * @author Brian Wing Shun Chan
 */
public class PropsUtil {

	public static void addProperties(Properties properties) {
		_instance._addProperties(properties);
	}

	public static void addProperties(UnicodeProperties unicodeProperties) {
		_instance._addProperties(unicodeProperties);
	}

	public static boolean contains(String key) {
		return _instance._contains(key);
	}

	public static String get(String key) {
		return _instance._get(key);
	}

	public static String get(String key, Filter filter) {
		return _instance._get(key, filter);
	}

	public static String[] getArray(String key) {
		return _instance._getArray(key);
	}

	public static String[] getArray(String key, Filter filter) {
		return _instance._getArray(key, filter);
	}

	public static Properties getProperties() {
		return _instance._getProperties();
	}

	public static Properties getProperties(
		String prefix, boolean removePrefix) {

		return _instance._getProperties(prefix, removePrefix);
	}

	public static void reload() {
		_instance = new PropsUtil();
	}

	public static void removeProperties(Properties properties) {
		_instance._removeProperties(properties);
	}

	public static void set(String key, String value) {
		_instance._set(key, value);
	}

	private PropsUtil() {
		Configuration configuration = null;

		try {

			// Default liferay home directory

			SystemProperties.set(
				PropsKeys.DEFAULT_LIFERAY_HOME, _getDefaultLiferayHome());

			// Global shared lib directory

			String globalSharedLibDir = _getLibDir(Servlet.class);

			if (_log.isInfoEnabled()) {
				_log.info("Global shared lib directory " + globalSharedLibDir);
			}

			SystemProperties.set(
				PropsKeys.LIFERAY_LIB_GLOBAL_SHARED_DIR, globalSharedLibDir);

			// Global lib directory

			String globalLibDir = _getLibDir(ReleaseInfo.class);

			if (_log.isInfoEnabled()) {
				_log.info("Global lib directory " + globalLibDir);
			}

			SystemProperties.set(
				PropsKeys.LIFERAY_LIB_GLOBAL_DIR, globalLibDir);

			// Portal lib directory

			Class<?> clazz = getClass();

			ClassLoader classLoader = clazz.getClassLoader();

			String portalLibDir = WebDirDetector.getLibDir(classLoader);

			String portalLibDirProperty = System.getProperty(
				PropsKeys.LIFERAY_LIB_PORTAL_DIR);

			if (portalLibDirProperty != null) {
				if (!portalLibDirProperty.endsWith(StringPool.SLASH)) {
					portalLibDirProperty += StringPool.SLASH;
				}

				portalLibDir = portalLibDirProperty;
			}

			if (_log.isInfoEnabled()) {
				_log.info("Portal lib directory " + portalLibDir);
			}

			SystemProperties.set(
				PropsKeys.LIFERAY_LIB_PORTAL_DIR, portalLibDir);

			// Portal web directory

			String portalWebDir = WebDirDetector.getRootDir(portalLibDir);

			if (_log.isDebugEnabled()) {
				_log.debug("Portal web directory " + portalWebDir);
			}

			SystemProperties.set(
				PropsKeys.LIFERAY_WEB_PORTAL_DIR, portalWebDir);

			// Liferay home directory

			configuration = new ConfigurationImpl(
				PropsUtil.class.getClassLoader(), PropsFiles.PORTAL);
		}
		catch (Exception e) {
			_log.error("Unable to initialize PropsUtil", e);
		}

		_configuration = configuration;

		String liferayHome = _get(PropsKeys.LIFERAY_HOME);

		if (_log.isDebugEnabled()) {
			_log.debug("Configured Liferay home " + liferayHome);
		}

		SystemProperties.set(PropsKeys.LIFERAY_HOME, liferayHome);

		// Ehcache disk directory

		SystemProperties.set(
			"ehcache.disk.store.dir", liferayHome + "/data/ehcache");

		if (GetterUtil.getBoolean(
				SystemProperties.get("company-id-properties"))) {

			_configurations = new HashMap<Long, Configuration>();
		}
	}

	private void _addProperties(Properties properties) {
		_getConfiguration().addProperties(properties);
	}

	private void _addProperties(UnicodeProperties unicodeProperties) {
		Properties properties = new Properties();

		properties.putAll(unicodeProperties);

		_addProperties(properties);
	}

	private boolean _contains(String key) {
		return _getConfiguration().contains(key);
	}

	private String _get(String key) {
		return _getConfiguration().get(key);
	}

	private String _get(String key, Filter filter) {
		return _getConfiguration().get(key, filter);
	}

	private String[] _getArray(String key) {
		return _getConfiguration().getArray(key);
	}

	private String[] _getArray(String key, Filter filter) {
		return _getConfiguration().getArray(key, filter);
	}

	private Configuration _getConfiguration() {
		if (_configurations == null) {
			return _configuration;
		}

		Long companyId = CompanyThreadLocal.getCompanyId();

		if (companyId > CompanyConstants.SYSTEM) {
			Configuration configuration = _configurations.get(companyId);

			if (configuration == null) {
				configuration = new ConfigurationImpl(
					PropsUtil.class.getClassLoader(), PropsFiles.PORTAL,
					companyId);

				_configurations.put(companyId, configuration);
			}

			return configuration;
		}
		else {
			return _configuration;
		}
	}

	private String _getDefaultLiferayHome() {
		String defaultLiferayHome = null;

		if (ServerDetector.isGeronimo()) {
			defaultLiferayHome =
				SystemProperties.get("org.apache.geronimo.home.dir") + "/..";
		}
		else if (ServerDetector.isGlassfish()) {
			defaultLiferayHome =
				SystemProperties.get("com.sun.aas.installRoot") + "/..";
		}
		else if (ServerDetector.isJBoss()) {
			defaultLiferayHome = SystemProperties.get("jboss.home.dir") + "/..";
		}
		else if (ServerDetector.isJOnAS()) {
			defaultLiferayHome = SystemProperties.get("jonas.base") + "/..";
		}
		else if (ServerDetector.isWebLogic()) {
			defaultLiferayHome =
				SystemProperties.get("env.DOMAIN_HOME") + "/..";
		}
		else if (ServerDetector.isJetty()) {
			defaultLiferayHome = SystemProperties.get("jetty.home") + "/..";
		}
		else if (ServerDetector.isResin()) {
			defaultLiferayHome = SystemProperties.get("resin.home") + "/..";
		}
		else if (ServerDetector.isTomcat()) {
			defaultLiferayHome = SystemProperties.get("catalina.base") + "/..";
		}
		else {
			defaultLiferayHome = SystemProperties.get("user.dir") + "/liferay";
		}

		defaultLiferayHome = StringUtil.replace(
			defaultLiferayHome, CharPool.BACK_SLASH, CharPool.SLASH);

		defaultLiferayHome = StringUtil.replace(
			defaultLiferayHome, StringPool.DOUBLE_SLASH, StringPool.SLASH);

		if (defaultLiferayHome.endsWith("/..")) {
			int pos = defaultLiferayHome.lastIndexOf(
				CharPool.SLASH, defaultLiferayHome.length() - 4);

			if (pos != -1) {
				defaultLiferayHome = defaultLiferayHome.substring(0, pos);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Default Liferay home " + defaultLiferayHome);
		}

		return defaultLiferayHome;
	}

	private String _getLibDir(Class<?> clazz) {
		String path = ClassUtil.getParentPath(
			clazz.getClassLoader(), clazz.getName());

		int pos = path.lastIndexOf(".jar!");

		if (pos == -1) {
			pos = path.lastIndexOf(".jar/");
		}

		pos = path.lastIndexOf(CharPool.SLASH, pos);

		path = path.substring(0, pos + 1);

		return path;
	}

	private Properties _getProperties() {
		return _getConfiguration().getProperties();
	}

	private Properties _getProperties(String prefix, boolean removePrefix) {
		return _getConfiguration().getProperties(prefix, removePrefix);
	}

	private void _removeProperties(Properties properties) {
		_getConfiguration().removeProperties(properties);
	}

	private void _set(String key, String value) {
		_getConfiguration().set(key, value);
	}

	private static final Log _log = LogFactoryUtil.getLog(PropsUtil.class);

	private static PropsUtil _instance = new PropsUtil();

	private final Configuration _configuration;
	private Map<Long, Configuration> _configurations;

}