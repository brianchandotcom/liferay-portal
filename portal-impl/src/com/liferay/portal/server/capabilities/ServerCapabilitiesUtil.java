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

package com.liferay.portal.server.capabilities;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ServerDetector;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 * @author Igor Spasic
 */
public class ServerCapabilitiesUtil {

	public static void determineServerCapabilities(
		ServletContext servletContext) {

		ServerCapabilities serverCapabilities = null;

		if (ServerDetector.isGlassfish()) {
			serverCapabilities = new GlassfishServerCapabilities();
		}
		else if (ServerDetector.isJBoss()) {
			serverCapabilities = new JBossServerCapabilities();
		}
		else if (ServerDetector.isJetty()) {
			serverCapabilities = new JettyServerCapabilities();
		}
		else if (ServerDetector.isTomcat()) {
			serverCapabilities = new TomcatServerCapabilities();
		}

		if (serverCapabilities == null) {
			serverCapabilities = new DefaultServerCapabilities();
		}

		if (_log.isInfoEnabled()) {
			Class<?> clazz = serverCapabilities.getClass();

			_log.info("Using " + clazz.getName());
		}

		try {
			serverCapabilities.determine(servletContext);
		}
		catch (Exception e) {
			_log.error("Unable to determine server capabilities", e);
		}

		ServerDetector.setSupportsHotDeploy(
			serverCapabilities.isSupportsHotDeploy());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServerCapabilitiesUtil.class);

}