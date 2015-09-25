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

package com.liferay.gradle.plugins.tasks;

import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.StringUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class DirectDeployTask extends BasePortalImplToolsTask {

	public File getAppServerDeployDir() {
		return GradleUtil.toFile(project, _appServerDeployDir);
	}

	public File getAppServerDir() {
		return GradleUtil.toFile(project, _appServerDir);
	}

	public String getAppServerType() {
		return GradleUtil.toString(_appServerType);
	}

	public String getArgAppServerType() {
		String argAppServerType = GradleUtil.toString(_argAppServerType);

		if (Validator.isNull(argAppServerType)) {
			argAppServerType = getAppServerType();
		}

		return argAppServerType;
	}

	@Override
	public List<String> getArgs() {
		List<String> args = new ArrayList<>(3);

		File appServerLibPortalDir = getAppServerLibPortalDir();

		String path = appServerLibPortalDir.getAbsolutePath();

		args.add(path + "/util-bridges.jar");
		args.add(path + "/util-java.jar");
		args.add(path + "/util-taglib.jar");

		return args;
	}

	@Override
	public List<String> getJvmArgs() {
		List<String> jvmArgs = new ArrayList<>();

		File webAppFile = getWebAppFile();

		jvmArgs.add("-Ddeployer.app.server.type=" + getArgAppServerType());
		jvmArgs.add(
			"-Ddeployer.base.dir=" +
				FileUtil.getAbsolutePath(webAppFile.getParentFile()));
		jvmArgs.add(
			"-Ddeployer.dest.dir=" +
				FileUtil.getAbsolutePath(getAppServerDeployDir()));
		jvmArgs.add("-Ddeployer.file.pattern=" + webAppFile.getName());
		jvmArgs.add("-Ddeployer.unpack.war=" + isUnpackWar());
		jvmArgs.add(
			"-Dexternal-properties=com/liferay/portal/tools/dependencies/" +
				"portal-tools.properties");
		jvmArgs.add(
			"-Dliferay.lib.portal.dir=" +
				FileUtil.getAbsolutePath(getAppServerLibPortalDir()));

		String webAppType = getWebAppType();

		if (!webAppType.equals("layouttpl")) {
			jvmArgs.add(
				"-Ddeployer.tomcat.lib.dir=" +
					FileUtil.getAbsolutePath(getAppServerLibGlobalDir()));
		}

		String tldPath = FileUtil.getAbsolutePath(
			new File(getAppServerPortalDir(), "WEB-INF/tld"));

		if (webAppType.equals("portlet") || webAppType.equals("theme")) {
			jvmArgs.add(
				"-Ddeployer.theme.taglib.dtd=" + tldPath +
					"/liferay-theme.tld");
			jvmArgs.add(
				"-Ddeployer.ui.taglib.dtd=" + tldPath + "/liferay-ui.tld");
		}

		if (webAppType.equals("portlet")) {
			jvmArgs.add(
				"-Ddeployer.aui.taglib.dtd=" + tldPath + "/liferay-aui.tld");
			jvmArgs.add(
				"-Ddeployer.custom.portlet.xml=" + isCustomPortletXml());
			jvmArgs.add(
				"-Ddeployer.portlet-ext.taglib.dtd=" + tldPath +
					"/liferay-portlet-ext.tld");
			jvmArgs.add(
				"-Ddeployer.portlet.taglib.dtd=" + tldPath +
					"/liferay-portlet.tld");
			jvmArgs.add(
				"-Ddeployer.security.taglib.dtd=" + tldPath +
					"/liferay-security.tld");
			jvmArgs.add(
				"-Ddeployer.staging.taglib.dtd=util-taglib/classes/META-INF/" +
					"liferay-staging.tld");
			jvmArgs.add(
				"-Ddeployer.util.taglib.dtd=" + tldPath + "/liferay-util.tld");
		}

		return jvmArgs;
	}

	@Override
	public String getMain() {
		String webAppType = getWebAppType();

		if (webAppType.equals("layouttpl")) {
			webAppType = "layout";
		}

		return "com.liferay.portal.tools.deploy." +
			StringUtil.capitalize(webAppType) + "Deployer";
	}

	public File getWebAppFile() {
		return GradleUtil.toFile(project, _webAppFile);
	}

	public String getWebAppType() {
		return GradleUtil.toString(_webAppType);
	}

	public boolean isCustomPortletXml() {
		return _customPortletXml;
	}

	public boolean isUnpackWar() {
		return _unpackWar;
	}

	public void setAppServerDeployDir(Object appServerDeployDir) {
		_appServerDeployDir = appServerDeployDir;
	}

	public void setAppServerDir(Object appServerDir) {
		_appServerDir = appServerDir;
	}

	public void setAppServerType(Object appServerType) {
		_appServerType = appServerType;
	}

	public void setArgAppServerType(Object argAppServerType) {
		_argAppServerType = argAppServerType;
	}

	public void setCustomPortletXml(boolean customPortletXml) {
		_customPortletXml = customPortletXml;
	}

	public void setUnpackWar(boolean unpackWar) {
		_unpackWar = unpackWar;
	}

	public void setWebAppFile(Object webAppFile) {
		_webAppFile = webAppFile;
	}

	public void setWebAppType(Object webAppType) {
		_webAppType = webAppType;
	}

	@Override
	protected void addDependencies() {
		super.addDependencies();

		String appServerType = getAppServerType();

		if (appServerType.equals("jonas")) {
			File dir = new File(getAppServerDir(), "lib/endorsed");

			GradleUtil.addDependency(
				project, getConfigurationName(), getJarsFileTree(dir));
		}
	}

	@Override
	protected String getToolName() {
		return "Deployer";
	}

	private Object _appServerDeployDir;
	private Object _appServerDir;
	private Object _appServerType;
	private Object _argAppServerType;
	private boolean _customPortletXml;
	private boolean _unpackWar = true;
	private Object _webAppFile;
	private Object _webAppType;

}