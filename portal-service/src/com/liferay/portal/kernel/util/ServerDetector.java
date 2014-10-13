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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

/**
 * @author Brian Wing Shun Chan
 */
public class ServerDetector {

	public static final String GERONIMO_ID = "geronimo";

	public static final String GLASSFISH_ID = "glassfish";

	public static final String JBOSS_ID = "jboss";

	public static final String JETTY_ID = "jetty";

	public static final String JONAS_ID = "jonas";

	public static final String OC4J_ID = "oc4j";

	public static final String RESIN_ID = "resin";

	public static final String TOMCAT_ID = "tomcat";

	public static final String WEBLOGIC_ID = "weblogic";

	public static final String WEBSPHERE_ID = "websphere";

	public static ServerDetector getInstance() {
		if (_instance == null) {
			_instance = new ServerDetector();

			_instance._init();
		}

		return _instance;
	}

	public static String getServerId() {
		return getInstance()._serverId;
	}

	public static void init(String serverId) {
		ServerDetector serverDetector = new ServerDetector();

		serverDetector._serverId = serverId;

		if (serverId.equals(GERONIMO_ID)) {
			serverDetector._geronimo = true;
		}
		else if (serverId.equals(GLASSFISH_ID)) {
			serverDetector._glassfish = true;
		}
		else if (serverId.equals(JBOSS_ID)) {
			serverDetector._jBoss = true;
		}
		else if (serverId.equals(JETTY_ID)) {
			serverDetector._jetty = true;
		}
		else if (serverId.equals(JONAS_ID)) {
			serverDetector._jonas = true;
		}
		else if (serverId.equals(OC4J_ID)) {
			serverDetector._oc4j = true;
		}
		else if (serverId.equals(RESIN_ID)) {
			serverDetector._resin = true;
		}
		else if (serverId.equals(TOMCAT_ID)) {
			serverDetector._tomcat = true;
		}
		else if (serverId.equals(WEBLOGIC_ID)) {
			serverDetector._webLogic = true;
		}
		else if (serverId.equals(WEBSPHERE_ID)) {
			serverDetector._webSphere = true;
		}
		else {
			serverDetector._init();
		}

		_instance = serverDetector;
	}

	public static boolean isGeronimo() {
		return getInstance()._geronimo;
	}

	public static boolean isGlassfish() {
		return getInstance()._glassfish;
	}

	public static boolean isJBoss() {
		return getInstance()._jBoss;
	}

	public static boolean isJBoss5() {
		return getInstance()._jBoss5;
	}

	public static boolean isJBoss7() {
		return getInstance()._jBoss7;
	}

	public static boolean isJetty() {
		return getInstance()._jetty;
	}

	public static boolean isJOnAS() {
		return getInstance()._jonas;
	}

	public static boolean isOC4J() {
		return getInstance()._oc4j;
	}

	public static boolean isResin() {
		return getInstance()._resin;
	}

	public static boolean isSupportsComet() {
		return _SUPPORTS_COMET;
	}

	public static boolean isSupportsHotDeploy() {
		return getInstance()._supportsHotDeploy;
	}

	public static boolean isTomcat() {
		return getInstance()._tomcat;
	}

	public static boolean isWebLogic() {
		return getInstance()._webLogic;
	}

	public static boolean isWebSphere() {
		return getInstance()._webSphere;
	}

	public static void setSupportsHotDeploy(boolean supportsHotDeploy) {
		getInstance()._supportsHotDeploy = supportsHotDeploy;

		if (_log.isInfoEnabled()) {
			if (supportsHotDeploy) {
				_log.info("Server supports hot deploy");
			}
			else {
				_log.info("Server does not support hot deploy");
			}
		}
	}

	private boolean _detect(String className) {
		try {
			ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

			systemClassLoader.loadClass(className);

			return true;
		}
		catch (ClassNotFoundException cnfe) {
			Class<?> clazz = getClass();

			if (clazz.getResource(className) != null) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	private boolean _hasSystemProperty(String key) {
		String value = System.getProperty(key);

		if (value != null) {
			return true;
		}
		else {
			return false;
		}
	}

	private void _init() {
		if (_isGeronimo()) {
			_serverId = GERONIMO_ID;
			_geronimo = true;
		}
		else if (_isGlassfish()) {
			_serverId = GLASSFISH_ID;
			_glassfish = true;
		}
		else if (_isJBoss()) {
			_serverId = JBOSS_ID;
			_jBoss = true;

			if (_isJBoss5()) {
				_jBoss5 = true;
			}
			else {
				_jBoss7 = true;
			}
		}
		else if (_isJOnAS()) {
			_serverId = JONAS_ID;
			_jonas = true;
		}
		else if (_isOC4J()) {
			_serverId = OC4J_ID;
			_oc4j = true;
		}
		else if (_isResin()) {
			_serverId = RESIN_ID;
			_resin = true;
		}
		else if (_isWebLogic()) {
			_serverId = WEBLOGIC_ID;
			_webLogic = true;
		}
		else if (_isWebSphere()) {
			_serverId = WEBSPHERE_ID;
			_webSphere = true;
		}

		if (_serverId == null) {
			if (_isJetty()) {
				_serverId = JETTY_ID;
				_jetty = true;
			}
			else if (_isTomcat()) {
				_serverId = TOMCAT_ID;
				_tomcat = true;
			}
		}

		if (System.getProperty("external-properties") == null) {
			if (_log.isInfoEnabled()) {
				if (_serverId != null) {
					_log.info("Detected server " + _serverId);
				}
				else {
					_log.info("No server detected");
				}
			}
		}

		/*if (_serverId == null) {
			throw new RuntimeException("Server is not supported");
		}*/
	}

	private boolean _isGeronimo() {
		return _hasSystemProperty("org.apache.geronimo.home.dir");
	}

	private boolean _isGlassfish() {
		return _hasSystemProperty("com.sun.aas.instanceRoot");
	}

	private boolean _isJBoss() {
		return _hasSystemProperty("jboss.home.dir");
	}

	private boolean _isJBoss5() {
		try {
			for (MBeanServer mBeanServer :
					MBeanServerFactory.findMBeanServer(null)) {

				String defaultDomain = mBeanServer.getDefaultDomain();

				if (defaultDomain.equals("jboss")) {
					ObjectName objectName = new ObjectName(
						"jboss.system:type=Server");

					String version = (String)mBeanServer.getAttribute(
						objectName, "VersionNumber");

					if (version.startsWith("5")) {
						return true;
					}

					return false;
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return false;
	}

	private boolean _isJetty() {
		return _hasSystemProperty("jetty.home");
	}

	private boolean _isJOnAS() {
		return _hasSystemProperty("jonas.base");
	}

	private boolean _isOC4J() {
		return _detect("oracle.oc4j.util.ClassUtils");
	}

	private boolean _isResin() {
		return _hasSystemProperty("resin.home");
	}

	private boolean _isTomcat() {
		return _hasSystemProperty("catalina.base");
	}

	private boolean _isWebLogic() {
		return _detect("/weblogic/Server.class");
	}

	private boolean _isWebSphere() {
		return _detect("/com/ibm/websphere/product/VersionInfo.class");
	}

	private static final boolean _SUPPORTS_COMET = false;

	private static final Log _log = LogFactoryUtil.getLog(ServerDetector.class);

	private static ServerDetector _instance;

	private boolean _geronimo;
	private boolean _glassfish;
	private boolean _jBoss;
	private boolean _jBoss5;
	private boolean _jBoss7;
	private boolean _jetty;
	private boolean _jonas;
	private boolean _oc4j;
	private boolean _resin;
	private String _serverId;
	private boolean _supportsHotDeploy;
	private boolean _tomcat;
	private boolean _webLogic;
	private boolean _webSphere;

}