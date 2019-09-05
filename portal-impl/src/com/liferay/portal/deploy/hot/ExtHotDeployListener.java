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

package com.liferay.portal.deploy.hot;

import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.deploy.hot.BaseHotDeployListener;
import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.WebDirDetector;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.tools.WebXMLBuilder;
import com.liferay.portal.util.ExtRegistry;
import com.liferay.taglib.FileAvailabilityUtil;
import com.liferay.util.ant.CopyTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 */
public class ExtHotDeployListener extends BaseHotDeployListener {

	@Override
	public void invokeDeploy(HotDeployEvent hotDeployEvent)
		throws HotDeployException {

		try {
			doInvokeDeploy(hotDeployEvent);
		}
		catch (Throwable t) {
			throwHotDeployException(
				hotDeployEvent, "Error registering extension environment for ",
				t);
		}
	}

	@Override
	public void invokeUndeploy(HotDeployEvent hotDeployEvent)
		throws HotDeployException {

		try {
			doInvokeUndeploy(hotDeployEvent);
		}
		catch (Throwable t) {
			throwHotDeployException(
				hotDeployEvent,
				"Error unregistering extension environment for ", t);
		}
	}

	protected void copyJar(
			ServletContext servletContext, String dir, String jarName)
		throws Exception {

		String servletContextName = servletContext.getServletContextName();

		String jarFullName = StringBundler.concat(
			"/WEB-INF/", jarName, "/", jarName, ".jar");

		InputStream is = servletContext.getResourceAsStream(jarFullName);

		if (is == null) {
			throw new HotDeployException(jarFullName + " does not exist");
		}

		String newJarFullName = StringBundler.concat(
			dir, "ext-", servletContextName, jarName.substring(3), ".jar");

		StreamUtil.transfer(is, new FileOutputStream(new File(newJarFullName)));
	}

	protected void doInvokeDeploy(HotDeployEvent hotDeployEvent)
		throws Exception {

		ServletContext servletContext = hotDeployEvent.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking deploy for " + servletContextName);
		}

		String xml = StreamUtil.toString(
			servletContext.getResourceAsStream(
				"/WEB-INF/ext-" + servletContextName + ".xml"));

		if (xml == null) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Registering extension environment for " + servletContextName);
		}

		if (ExtRegistry.isRegistered(servletContextName)) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Extension environment for " + servletContextName +
						" has been applied.");
			}

			return;
		}

		Map<String, Set<String>> conflicts = ExtRegistry.getConflicts(
			servletContext);

		if (!conflicts.isEmpty()) {
			StringBundler sb = new StringBundler();

			sb.append("Extension environment for ");
			sb.append(servletContextName);
			sb.append(" cannot be applied because of detected conflicts:");

			for (Map.Entry<String, Set<String>> entry : conflicts.entrySet()) {
				String conflictServletContextName = entry.getKey();
				Set<String> conflictFiles = entry.getValue();

				sb.append("\n\t");
				sb.append(conflictServletContextName);
				sb.append(":");

				for (String conflictFile : conflictFiles) {
					sb.append("\n\t\t");
					sb.append(conflictFile);
				}
			}

			_log.error(sb.toString());

			return;
		}

		installExt(servletContext, hotDeployEvent.getContextClassLoader());

		FileAvailabilityUtil.clearAvailabilities();

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Extension environment for ", servletContextName,
					" has been applied. You must reboot the server and ",
					"redeploy all other plugins."));
		}
	}

	protected void doInvokeUndeploy(HotDeployEvent hotDeployEvent)
		throws Exception {

		ServletContext servletContext = hotDeployEvent.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking undeploy for " + servletContextName);
		}

		String xml = StreamUtil.toString(
			servletContext.getResourceAsStream(
				"/WEB-INF/ext-" + servletContextName + ".xml"));

		if (xml == null) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Extension environment for " + servletContextName +
					" will not be undeployed");
		}
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x)
	 */
	@Deprecated
	protected void installExt(ServletContext servletContext) throws Exception {
		installExt(servletContext, servletContext.getClassLoader());
	}

	protected void installExt(
			ServletContext servletContext, ClassLoader portletClassLoader)
		throws Exception {

		String servletContextName = servletContext.getServletContextName();

		String globalLibDir = PortalUtil.getGlobalLibDir();
		String portalWebDir = PortalUtil.getPortalWebDir();
		String portalLibDir = PortalUtil.getPortalLibDir();
		String pluginWebDir = WebDirDetector.getRootDir(portletClassLoader);

		if (ServerDetector.isTomcat()) {
			portalLibDir = globalLibDir.concat("portal/");

			FileUtil.mkdirs(portalLibDir);

			globalLibDir = globalLibDir.concat("global/");

			FileUtil.mkdirs(globalLibDir);
		}

		copyJar(servletContext, globalLibDir, "ext-kernel");

		copyJar(servletContext, portalLibDir, "ext-impl");
		copyJar(servletContext, portalLibDir, "ext-util-bridges");
		copyJar(servletContext, portalLibDir, "ext-util-java");
		copyJar(servletContext, portalLibDir, "ext-util-taglib");

		mergeWebXml(portalWebDir, pluginWebDir);

		CopyTask.copyDirectory(
			pluginWebDir + "WEB-INF/ext-web/docroot", portalWebDir,
			StringPool.BLANK, "**/WEB-INF/web.xml", true, false);

		FileUtil.copyFile(
			StringBundler.concat(
				pluginWebDir, "WEB-INF/ext-", servletContextName, ".xml"),
			StringBundler.concat(
				portalWebDir, "WEB-INF/ext-", servletContextName, ".xml"));

		ExtRegistry.registerExt(servletContext);
	}

	protected void mergeWebXml(String portalWebDir, String pluginWebDir)
		throws IOException {

		if (!FileUtil.exists(
				pluginWebDir + "WEB-INF/ext-web/docroot/WEB-INF/web.xml")) {

			return;
		}

		Path tempDirPath = Files.createTempDirectory(
			Paths.get(SystemProperties.get(SystemProperties.TMP_DIR)), null);

		File tempDir = tempDirPath.toFile();

		WebXMLBuilder.mergeWebXML(
			portalWebDir + "WEB-INF/web.xml",
			pluginWebDir + "WEB-INF/ext-web/docroot/WEB-INF/web.xml",
			tempDir.getAbsolutePath() + "/web.xml");

		File portalWebXml = new File(portalWebDir + "WEB-INF/web.xml");
		File tmpWebXml = new File(tempDir + "/web.xml");

		tmpWebXml.setLastModified(portalWebXml.lastModified());

		CopyTask.copyFile(
			tmpWebXml, new File(portalWebDir + "WEB-INF"), true, true);

		FileUtil.deltree(tempDir);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExtHotDeployListener.class);

}