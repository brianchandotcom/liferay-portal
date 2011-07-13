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

import com.liferay.portal.kernel.deploy.DeployManager;
import com.liferay.portal.kernel.deploy.Deployer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.tools.deploy.ExtDeployer;
import com.liferay.portal.tools.deploy.HookDeployer;
import com.liferay.portal.tools.deploy.LayoutTemplateDeployer;
import com.liferay.portal.tools.deploy.PortletDeployer;
import com.liferay.portal.tools.deploy.ThemeDeployer;
import com.liferay.portal.tools.deploy.WebDeployer;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public class DeployManagerImpl implements DeployManager {

	public void deploy(File source) throws Exception {
		deploy(source, null);
	}

	public void deploy(File source, String context) throws Exception {
		String fileName = source.getName();

		int pos = fileName.lastIndexOf(StringPool.PERIOD);

		if (pos > 0) {
			context = fileName.substring(0, pos);
		}
		else {
			context = fileName;
		}

		Deployer deployer = null;

		List<String> jars = new ArrayList<String>();

		if (context.endsWith("-ext")) {
			deployer = getExtDeployer(jars);
		}
		else if (context.endsWith("-hook")) {
			deployer = getHookDeployer(jars);
		}
		else if (context.endsWith("-layouttpl")) {
			deployer = getLayoutTemplateDeployer();
		}
		else if (context.endsWith("-portlet")) {
			deployer = getPortletDeployer(jars);
		}
		else if (context.endsWith("-theme")) {
			deployer = getThemeDeployer(jars);
		}
		else if (context.endsWith("-web")) {
			deployer = getWebDeployer(jars);
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn("Invalid context " + context);
			}

			return;
		}

		deployer.setAppServerType(ServerDetector.getServerId());
		deployer.setBaseDir(
			PrefsPropsUtil.getString(
				PropsKeys.AUTO_DEPLOY_DEPLOY_DIR,
				PropsValues.AUTO_DEPLOY_DEPLOY_DIR));
		deployer.setDestDir(getDeployDir());
		deployer.setFilePattern(StringPool.BLANK);
		deployer.setJbossPrefix(
			PrefsPropsUtil.getString(
				PropsKeys.AUTO_DEPLOY_JBOSS_PREFIX,
				PropsValues.AUTO_DEPLOY_JBOSS_PREFIX));
		deployer.setTomcatLibDir(
			PrefsPropsUtil.getString(
				PropsKeys.AUTO_DEPLOY_TOMCAT_LIB_DIR,
				PropsValues.AUTO_DEPLOY_TOMCAT_LIB_DIR));
		deployer.setUnpackWar(
			PrefsPropsUtil.getBoolean(
				PropsKeys.AUTO_DEPLOY_UNPACK_WAR,
				PropsValues.AUTO_DEPLOY_UNPACK_WAR));

		deployer.setJars(jars);

		List<String> wars = new ArrayList<String>();

		wars.add(source.getAbsolutePath());

		deployer.setWars(wars);

		deployer.checkArguments();

		deployer.deployFile(source, context);
	}

	public String getDeployDir() throws Exception {
		return DeployUtil.getAutoDeployDestDir();
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
		File webXml = new File(getDeployDir(), "/WEB-INF/web.xml");

		FileUtils.touch(webXml);
	}

	private static Log _log = LogFactoryUtil.getLog(DeployManagerImpl.class);

}