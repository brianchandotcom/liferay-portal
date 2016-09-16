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

/**
 * @author Brian Wing Shun Chan
 */
public class ServerDetector {

	public static final String GLASSFISH_ID = "glassfish";

	public static final String JBOSS_ID = "jboss";

	public static final String JETTY_ID = "jetty";

	public static final String JONAS_ID = "jonas";

	public static final String OC4J_ID = "oc4j";

	public static final String RESIN_ID = "resin";

	public static final String TOMCAT_ID = "tomcat";

	public static final String WEBLOGIC_ID = "weblogic";

	public static final String WEBSPHERE_ID = "websphere";

	public static final String WILDFLY_ID = "wildfly";

	public static ServerDetector getInstance() {
		if (_instance == null) {
			_instance = new ServerDetector();

			_instance._init();
		}

		return _instance;
	}

	public static String getServerId() {
		return StringUtil.toLowerCase(getInstance()._serverType.toString());
	}

	public static void init(String serverId) {
		ServerDetector serverDetector = new ServerDetector();

		try {
			serverDetector._serverType = ServerType.valueOf(
				StringUtil.toUpperCase(serverId));
		}
		catch (IllegalArgumentException iae) {
			serverDetector._init();
		}

		_instance = serverDetector;
	}

	public static boolean isGlassfish() {
		if (getInstance()._serverType == ServerType.GLASSFISH) {
			return true;
		}

		return false;
	}

	public static boolean isJBoss() {
		if (getInstance()._serverType == ServerType.JBOSS) {
			return true;
		}

		return false;
	}

	public static boolean isJetty() {
		if (getInstance()._serverType == ServerType.JETTY) {
			return true;
		}

		return false;
	}

	public static boolean isJOnAS() {
		if (getInstance()._serverType == ServerType.JONAS) {
			return true;
		}

		return false;
	}

	public static boolean isOC4J() {
		if (getInstance()._serverType == ServerType.OC4J) {
			return true;
		}

		return false;
	}

	public static boolean isResin() {
		if (getInstance()._serverType == ServerType.RESIN) {
			return true;
		}

		return false;
	}

	public static boolean isSupportsComet() {
		return _SUPPORTS_COMET;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static boolean isSupportsHotDeploy() {
		return true;
	}

	public static boolean isTomcat() {
		if (getInstance()._serverType == ServerType.TOMCAT) {
			return true;
		}

		return false;
	}

	public static boolean isWebLogic() {
		if (getInstance()._serverType == ServerType.WEBLOGIC) {
			return true;
		}

		return false;
	}

	public static boolean isWebSphere() {
		if (getInstance()._serverType == ServerType.WEBSPHERE) {
			return true;
		}

		return false;
	}

	public static boolean isWildfly() {
		if (getInstance()._serverType == ServerType.WILDFLY) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static void setSupportsHotDeploy(boolean supportsHotDeploy) {
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
		if (_isGlassfish()) {
			_serverType = ServerType.GLASSFISH;
		}
		else if (_isJBoss()) {
			_serverType = ServerType.JBOSS;
		}
		else if (_isJOnAS()) {
			_serverType = ServerType.JONAS;
		}
		else if (_isOC4J()) {
			_serverType = ServerType.OC4J;
		}
		else if (_isResin()) {
			_serverType = ServerType.RESIN;
		}
		else if (_isWebLogic()) {
			_serverType = ServerType.WEBLOGIC;
		}
		else if (_isWebSphere()) {
			_serverType = ServerType.WEBSPHERE;
		}
		else if (_isWildfly()) {
			_serverType = ServerType.WILDFLY;
		}

		if (_serverType == null) {
			if (_isJetty()) {
				_serverType = ServerType.JETTY;
			}
			else if (_isTomcat()) {
				_serverType = ServerType.TOMCAT;
			}
		}

		if (System.getProperty("external-properties") == null) {
			if (_log.isInfoEnabled()) {
				if (_serverType != null) {
					_log.info(
						"Detected server " +
							StringUtil.toLowerCase(_serverType.toString()));
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

	private boolean _isGlassfish() {
		return _hasSystemProperty("com.sun.aas.instanceRoot");
	}

	private boolean _isJBoss() {
		return _hasSystemProperty("jboss.home.dir");
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

	private boolean _isWildfly() {
		return _hasSystemProperty("jboss.home.dir");
	}

	private static final boolean _SUPPORTS_COMET = false;

	private static final Log _log = LogFactoryUtil.getLog(ServerDetector.class);

	private static ServerDetector _instance;

	private ServerType _serverType;

	private enum ServerType {

		GLASSFISH, JBOSS, JETTY, JONAS, OC4J, RESIN, TOMCAT, WEBLOGIC,
		WEBSPHERE, WILDFLY;
	}

}