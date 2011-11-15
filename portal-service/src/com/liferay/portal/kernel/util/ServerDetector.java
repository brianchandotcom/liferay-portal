/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

	public static String getServerId() {
		return _instance._serverId;
	}

	public static boolean isGeronimo() {
		return _instance._geronimo;
	}

	public static boolean isGlassfish() {
		return _instance._glassfish;
	}

	public static boolean isJBoss() {
		return _instance._jBoss;
	}

	public static boolean isJetty() {
		return _instance._jetty;
	}

	public static boolean isJOnAS() {
		return _instance._jonas;
	}

	public static boolean isOC4J() {
		return _instance._oc4j;
	}

	public static boolean isResin() {
		return _instance._resin;
	}

	public static boolean isSupportsComet() {
		return _instance._supportsComet;
	}

	public static boolean isTomcat() {
		return _instance._tomcat;
	}

	public static boolean isWebLogic() {
		return _instance._webLogic;
	}

	public static boolean isWebSphere() {
		return _instance._webSphere;
	}

	private ServerDetector() {
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

	private boolean _detect(String className) {
		try {
			ClassLoader systemClassLoader =
				ClassLoader.getSystemClassLoader();

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

	private boolean _isGeronimo() {
		return _hasSystemProperty("org.apache.geronimo.home.dir");
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

	private static Log _log = LogFactoryUtil.getLog(ServerDetector.class);

	private static ServerDetector _instance = new ServerDetector();

	private String _serverId;
	private boolean _geronimo;
	private boolean _glassfish;
	private boolean _jBoss;
	private boolean _jetty;
	private boolean _jonas;
	private boolean _oc4j;
	private boolean _resin;
	private boolean _supportsComet;
	private boolean _tomcat;
	private boolean _webLogic;
	private boolean _webSphere;

}