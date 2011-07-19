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

package com.liferay.portal.deploy;

import com.liferay.portal.deploy.auto.ExtAutoDeployer;
import com.liferay.portal.deploy.auto.HookAutoDeployer;
import com.liferay.portal.deploy.auto.LayoutTemplateAutoDeployer;
import com.liferay.portal.deploy.auto.MVCPortletAutoDeployer;
import com.liferay.portal.deploy.auto.PHPPortletAutoDeployer;
import com.liferay.portal.deploy.auto.PortletAutoDeployer;
import com.liferay.portal.deploy.auto.ThemeAutoDeployer;
import com.liferay.portal.deploy.auto.WAIAutoDeployer;
import com.liferay.portal.deploy.auto.WebAutoDeployer;
import com.liferay.portal.kernel.deploy.DeployManager;
import com.liferay.portal.kernel.deploy.Deployer;
import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.plugin.PluginPackageUtil;
import com.liferay.portal.tools.deploy.ExtDeployer;
import com.liferay.portal.tools.deploy.HookDeployer;
import com.liferay.portal.tools.deploy.LayoutTemplateDeployer;
import com.liferay.portal.tools.deploy.PortletDeployer;
import com.liferay.portal.tools.deploy.ThemeDeployer;
import com.liferay.portal.tools.deploy.WebDeployer;
import com.liferay.portal.util.Portal;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipFile;

import org.apache.commons.io.FileUtils;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 * @author Ryan Park
 */
public class DeployManagerImpl implements DeployManager {

	public void deploy(File source) throws Exception {
		deploy(source, null);
	}

	public void deploy(File source, String context) throws Exception {
		Deployer deployer = null;

		if (isExtPlugin(source)) {
			deployer = new ExtAutoDeployer();
		}
		else if (isHookPlugin(source)) {
			deployer = new HookAutoDeployer();
		}
		else if (isLayoutTemplatePlugin(source)) {
			deployer = new LayoutTemplateAutoDeployer();
		}
		else if (isPortletPlugin(source)) {
			deployer = new PortletAutoDeployer();
		}
		else if (isMvcPortletPlugin(source)) {
			deployer = new MVCPortletAutoDeployer();
		}
		else if (isPhpPortletPlugin(source)) {
			deployer = new PHPPortletAutoDeployer();
		}
		else if (isWaiPortletPlugin(source)) {
			deployer = new WAIAutoDeployer();
		}
		else if (isThemePlugin(source)) {
			deployer = new ThemeAutoDeployer();
		}
		else if (isWebPlugin(source)) {
			deployer = new WebAutoDeployer();
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn("Invalid plugin type " + source.getName());
			}

			return;
		}

		deployer.deployFile(source, context);
	}

	public String getDeployDir() throws Exception {
		return DeployUtil.getAutoDeployDestDir();
	}

	public boolean isDeployed(String context) {
		return PluginPackageUtil.isInstalled(context);
	}

	public boolean isExtPlugin(File file) {
		return file.getName().contains("-ext");
	}

	public  boolean isHookPlugin(File file) throws AutoDeployException {
		return isMatchingFile(
				file, "WEB-INF/liferay-plugin-package.properties") &&
			file.getName().contains("-hook") &&
			!file.getName().contains("-portlet");
	}

	public boolean isLayoutTemplatePlugin(File file)
		throws AutoDeployException {

		return isMatchingFile(file, "WEB-INF/liferay-layout-templates.xml");
	}

	public boolean isMatchingFile(File file, String checkXmlFile)
		throws AutoDeployException {

		if (!isMatchingFileExtension(file)) {
			return false;
		}

		ZipFile zipFile = null;

		try {
			zipFile = new ZipFile(file);

			if (zipFile.getEntry(checkXmlFile) == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						file.getPath() + " does not have " + checkXmlFile);
				}

				return false;
			}
			else {
				return true;
			}
		}
		catch (IOException ioe) {
			throw new AutoDeployException(ioe);
		}
		finally {
			if (zipFile != null) {
				try {
					zipFile.close();
				}
				catch (IOException ioe) {
				}
			}
		}
	}

	public boolean isMatchingFileExtension(File file) {
		String fileName = file.getName().toLowerCase();

		if (fileName.endsWith(".war") || fileName.endsWith(".zip")) {
			if (_log.isDebugEnabled()) {
				_log.debug(file.getPath() + " has a matching extension");
			}

			return true;
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug(
					file.getPath() + " does not have a matching extension");
			}

			return false;
		}
	}

	public boolean isMvcPortletPlugin(File file)
		throws AutoDeployException {

		return isMatchingFile(file, "index_mvc.jsp");
	}

	public boolean isPhpPortletPlugin(File file)
		throws AutoDeployException {

		return isMatchingFile(file, "index.php");
	}

	public boolean isPortletPlugin(File file) throws AutoDeployException {
		return isMatchingFile(
			file, "WEB-INF/" + Portal.PORTLET_XML_FILE_NAME_STANDARD);
	}

	public boolean isWaiPortletPlugin(File file)
		throws AutoDeployException {

		return !isExtPlugin(file) &&
			   !isHookPlugin(file) &&
			   !isMatchingFile(
				   file, "WEB-INF/liferay-layout-templates.xml") &&
			   !isThemePlugin(file) &&
			   !isWebPlugin(file) &&
			   file.getName().endsWith(".war");
	}

	public boolean isThemePlugin(File file) throws AutoDeployException {
		if (isMatchingFile(file, "WEB-INF/liferay-look-and-feel.xml")) {
			return true;
		}

		if ((isMatchingFile(
				file, "WEB-INF/liferay-plugin-package.properties")) &&
			(file.getName().contains("-theme"))) {

			return true;
		}

		return false;
	}

	public boolean isWebPlugin(File file) throws AutoDeployException {
		return isMatchingFile(
				file, "WEB-INF/liferay-plugin-package.properties") &&
			file.getName().contains("-web");
	}

	public void redeploy(String context) throws Exception {
		if (ServerDetector.isJetty()) {
			redeployJetty(context);
		}
		else if (ServerDetector.isTomcat()) {
			redeployTomcat(context);
		}
	}

	public void undeploy(String context) throws Exception {
		File deployDir = new File(getDeployDir(), context);

		DeployUtil.undeploy(ServerDetector.getServerId(), deployDir);
	}

	protected Deployer getExtDeployer(List<String> jars)
		throws Exception {

		Deployer deployer = new ExtDeployer();

		deployer.addRequiredJar(jars, "util-java.jar");

		return deployer;
	}

	protected Deployer getHookDeployer(List<String> jars)
		throws Exception {

		Deployer deployer = new HookDeployer();

		deployer.addExtJar(jars, "ext-util-java.jar");
		deployer.addRequiredJar(jars, "util-java.jar");

		return deployer;
	}

	protected Deployer getLayoutTemplateDeployer() throws Exception {
		return new LayoutTemplateDeployer();
	}

	protected Deployer getPortletDeployer(List<String> jars)
		throws Exception {

		Deployer deployer = new PortletDeployer();

		deployer.setAuiTaglibDTD(getResourcePath("liferay-aui.tld"));
		deployer.setPortletTaglibDTD(getResourcePath("liferay-portlet.tld"));
		deployer.setPortletExtTaglibDTD(
			getResourcePath("liferay-portlet-ext.tld"));
		deployer.setSecurityTaglibDTD(getResourcePath("liferay-security.tld"));
		deployer.setThemeTaglibDTD(getResourcePath("liferay-theme.tld"));
		deployer.setUiTaglibDTD(getResourcePath("liferay-ui.tld"));
		deployer.setUtilTaglibDTD(getResourcePath("liferay-util.tld"));

		deployer.addExtJar(jars, "ext-util-bridges.jar");
		deployer.addExtJar(jars, "ext-util-java.jar");
		deployer.addExtJar(jars, "ext-util-taglib.jar");
		deployer.addRequiredJar(jars, "util-bridges.jar");
		deployer.addRequiredJar(jars, "util-java.jar");
		deployer.addRequiredJar(jars, "util-taglib.jar");

		return deployer;
	}

	protected String getResourcePath(String resource) throws Exception {
		return DeployUtil.getResourcePath(resource);
	}

	protected Deployer getThemeDeployer(List<String> jars) throws Exception {
		Deployer deployer = new ThemeDeployer();

		deployer.setThemeTaglibDTD(getResourcePath("liferay-theme.tld"));
		deployer.setUiTaglibDTD(getResourcePath("liferay-ui.tld"));

		deployer.addExtJar(jars, "ext-util-java.jar");
		deployer.addExtJar(jars, "ext-util-taglib.jar");
		deployer.addRequiredJar(jars, "util-java.jar");
		deployer.addRequiredJar(jars, "util-taglib.jar");

		return deployer;
	}

	protected Deployer getWebDeployer(List<String> jars) throws Exception {
		Deployer deployer = new WebDeployer();

		deployer.addExtJar(jars, "ext-util-java.jar");
		deployer.addRequiredJar(jars, "util-java.jar");

		return deployer;
	}

	protected void redeployJetty(String context) throws Exception {
		String contextsDirName = System.getProperty("jetty.home") + "/contexts";

		File contextXml = new File(contextsDirName + "/" + context + ".xml");

		if (contextXml.exists()) {
			FileUtils.touch(contextXml);
		}
		else {
			Map<String, String> filterMap = new HashMap<String, String>();

			filterMap.put("context", context);

			DeployUtil.copyDependencyXml(
				"jetty-context-configure.xml", contextsDirName, filterMap,
				true);
		}
	}

	protected void redeployTomcat(String context) throws Exception {
		File webXml = new File(getDeployDir(), context + "/WEB-INF/web.xml");

		FileUtils.touch(webXml);
	}

	private static Log _log = LogFactoryUtil.getLog(DeployManagerImpl.class);

}