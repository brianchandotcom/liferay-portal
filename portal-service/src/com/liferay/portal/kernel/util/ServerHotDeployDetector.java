/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.PortalUtil;

import java.io.File;
import java.io.IOException;

import java.util.List;

/**
 * @author Igor Spasic
 */
public class ServerHotDeployDetector {

	public static ServerHotDeployDetector getInstance() {
		if (_instance == null) {
			init();
		}

		return _instance;
	}

	public static void init() {
		_instance = new ServerHotDeployDetector();

		_instance._init();
	}

	public static boolean isHotDeployEnabled() {
		return getInstance()._hotDeployEnabled;
	}

	private static boolean _isHotDeployEnabledOnTomcat() {

		String catalinaBaseDir = System.getenv("CATALINA_BASE");

		File serverXmlFile = new File(catalinaBaseDir, "conf/server.xml");

		Document document = null;

		try {
			document = SAXReaderUtil.read(serverXmlFile);
		}
		catch (DocumentException e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Can't parse Tomcat server.xml", e);
			}

			return false;
		}

		List<Node> hostNodes = document.selectNodes(
			"/Server/Service/Engine/Host");

		if (hostNodes.isEmpty()) {
			return false;
		}

		String portalWebDir = PortalUtil.getPortalWebDir();

		File portalWebDirFile = null;

		try {
			portalWebDirFile = new File(portalWebDir).getCanonicalFile();
		}
		catch (IOException e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to resolve portal web dir", e);
			}
			return false;
		}

		for (Node node : hostNodes) {
			Element element = (Element) node;

			Attribute appBaseAttribute = element.attribute("appBase");

			if (appBaseAttribute == null) {
				continue;
			}

			String appBase = appBaseAttribute.getValue();

			File appBaseFile = new File(catalinaBaseDir, appBase);

			if (!appBaseFile.exists()) {
				appBaseFile = new File(appBase);
			}

			if (!appBaseFile.exists()) {
				continue;
			}

			try {
				appBaseFile = appBaseFile.getCanonicalFile();
			}
			catch (IOException e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to resolve appBase dir", e);
				}
				continue;
			}

			portalWebDir = portalWebDirFile.getPath();
			appBase = appBaseFile.getPath();

			if (!portalWebDir.startsWith(appBase)) {
				continue;
			}

			Attribute autoDeployAttribute = element.attribute("autoDeploy");

			if (autoDeployAttribute == null) {
				return true;
			}

			String autoDeploy = autoDeployAttribute.getValue();

			return Boolean.parseBoolean(autoDeploy);
		}

		return false;
	}

	private void _init() {
		if (ServerDetector.isTomcat()) {
			_hotDeployEnabled = _isHotDeployEnabledOnTomcat();
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to detect if hot deploy is enabled");
			}
		}

		if (_log.isInfoEnabled()) {
			if (_hotDeployEnabled) {
				_log.info("Hot Deploy enabled");
			}
			else {
				_log.info("Hot Deploy NOT enabled");
			}
		}
	}

	private static ServerHotDeployDetector _instance;

	private static Log _log = LogFactoryUtil.getLog(
		ServerHotDeployDetector.class);

	private boolean _hotDeployEnabled;

}