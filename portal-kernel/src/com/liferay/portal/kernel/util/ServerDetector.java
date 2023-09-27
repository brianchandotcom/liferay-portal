/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class ServerDetector {

	public static final String JBOSS_ID = "jboss";

	public static final String SYSTEM_PROPERTY_KEY_SERVER_DETECTOR_SERVER_ID =
		"server.detector.server.id";

	public static final String TOMCAT_ID = "tomcat";

	public static final String WEBLOGIC_ID = "weblogic";

	public static final String WEBSPHERE_ID = "websphere";

	public static final String WILDFLY_ID = "wildfly";

	public static String getServerId() {
		return _serverType.getLowerCaseName();
	}

	public static boolean isJBoss() {
		if (_serverType == ServerType.JBOSS) {
			return true;
		}

		return false;
	}

	public static boolean isSupported(String serverType) {
		if (serverType.equals(ServerDetector.JBOSS_ID) ||
			serverType.equals(ServerDetector.TOMCAT_ID) ||
			serverType.equals(ServerDetector.WEBLOGIC_ID) ||
			serverType.equals(ServerDetector.WEBSPHERE_ID) ||
			serverType.equals(ServerDetector.WILDFLY_ID)) {

			return true;
		}

		return false;
	}

	public static boolean isSupportsComet() {
		return _SUPPORTS_COMET;
	}

	public static boolean isTomcat() {
		if (_serverType == ServerType.TOMCAT) {
			return true;
		}

		return false;
	}

	public static boolean isWebLogic() {
		if (_serverType == ServerType.WEBLOGIC) {
			return true;
		}

		return false;
	}

	public static boolean isWebSphere() {
		if (_serverType == ServerType.WEBSPHERE) {
			return true;
		}

		return false;
	}

	public static boolean isWildfly() {
		if (_serverType == ServerType.WILDFLY) {
			return true;
		}

		return false;
	}

	private static boolean _detect(String className) {
		try {
			ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

			systemClassLoader.loadClass(className);

			return true;
		}
		catch (ClassNotFoundException classNotFoundException) {
			if (_log.isDebugEnabled()) {
				_log.debug(classNotFoundException);
			}

			if (ServerDetector.class.getResource(className) != null) {
				return true;
			}

			return false;
		}
	}

	private static ServerType _detectServerType() {
		String serverId = System.getProperty(
			SYSTEM_PROPERTY_KEY_SERVER_DETECTOR_SERVER_ID);

		if (serverId != null) {
			return ServerType.valueOf(StringUtil.toUpperCase(serverId));
		}

		if (_hasSystemProperty("com.sun.aas.instanceRoot")) {
			return ServerType.GLASSFISH;
		}

		if (_hasSystemProperty("jboss.home.dir")) {
			return ServerType.JBOSS;
		}

		if (_hasSystemProperty("jonas.base")) {
			return ServerType.JONAS;
		}

		if (_detect("oracle.oc4j.util.ClassUtils")) {
			return ServerType.OC4J;
		}

		if (_hasSystemProperty("resin.home")) {
			return ServerType.RESIN;
		}

		if (_detect("/weblogic/Server.class")) {
			return ServerType.WEBLOGIC;
		}

		if (_detect("/com/ibm/websphere/product/VersionInfo.class")) {
			return ServerType.WEBSPHERE;
		}

		if (_hasSystemProperty("jboss.home.dir")) {
			return ServerType.WILDFLY;
		}

		if (_hasSystemProperty("jetty.home")) {
			return ServerType.JETTY;
		}

		if (_hasSystemProperty("catalina.base")) {
			return ServerType.TOMCAT;
		}

		return ServerType.UNKNOWN;
	}

	private static boolean _hasSystemProperty(String key) {
		String value = System.getProperty(key);

		if (value != null) {
			return true;
		}

		return false;
	}

	private static final boolean _SUPPORTS_COMET = false;

	private static final Log _log = LogFactoryUtil.getLog(ServerDetector.class);

	private static final ServerType _serverType;

	static {
		_serverType = _detectServerType();

		if (!GetterUtil.getBoolean(
				System.getProperty("server.detector.quiet")) &&
			(System.getProperty("external-properties") == null)) {

			if (_log.isInfoEnabled()) {
				_log.info("Detected server " + _serverType.getLowerCaseName());
			}
		}
	}

	private enum ServerType {

		GLASSFISH("glassfish"), JBOSS("jboss"), JETTY("jetty"), JONAS("jonas"),
		OC4J("oc4j"), RESIN("resin"), TOMCAT("tomcat"), UNKNOWN("unknown"),
		WEBLOGIC("weblogic"), WEBSPHERE("websphere"), WILDFLY("wildfly");

		public String getLowerCaseName() {
			return _lowerCaseName;
		}

		private ServerType(String lowerCaseName) {
			_lowerCaseName = lowerCaseName;
		}

		private final String _lowerCaseName;

	}

}