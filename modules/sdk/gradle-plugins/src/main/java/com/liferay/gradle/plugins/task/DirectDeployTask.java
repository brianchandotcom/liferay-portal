/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.task;

import com.liferay.gradle.plugins.LiferayBasePlugin;
import com.liferay.gradle.plugins.internal.util.FileUtil;
import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.util.StringUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.artifacts.Configuration;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;

/**
 * @author Andrea Di Giorgi
 */
public class DirectDeployTask extends BasePortalToolsTask {

	@Internal
	public File getAppServerDeployDir() {
		return GradleUtil.toFile(project, _appServerDeployDir);
	}

	@Internal
	public File getAppServerDir() {
		return GradleUtil.toFile(project, _appServerDir);
	}

	@Internal
	public File getAppServerLibGlobalDir() {
		return GradleUtil.toFile(project, _appServerLibGlobalDir);
	}

	@Internal
	public File getAppServerPortalDir() {
		return GradleUtil.toFile(project, _appServerPortalDir);
	}

	@Input
	public String getAppServerType() {
		return GradleUtil.toString(_appServerType);
	}

	@Override
	public List<String> getArgs() {
		List<String> args = new ArrayList<>(3);

		File appServerLibPortalDir = _getAppServerLibPortalDir();

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

		jvmArgs.add("-Ddeployer.app.server.type=" + getAppServerType());
		jvmArgs.add(
			"-Ddeployer.base.dir=" +
				FileUtil.getAbsolutePath(webAppFile.getParentFile()));
		jvmArgs.add(
			"-Ddeployer.dest.dir=" +
				FileUtil.getAbsolutePath(getAppServerDeployDir()));
		jvmArgs.add("-Ddeployer.file.pattern=" + webAppFile.getName());
		jvmArgs.add("-Ddeployer.unpack.war=" + isUnpackWar());
		jvmArgs.add(
			"-Dexternal-properties=com/liferay/portal/tools/dependencies" +
				"/portal-tools.properties");
		jvmArgs.add(
			"-Dliferay.lib.portal.dir=" +
				FileUtil.getAbsolutePath(_getAppServerLibPortalDir()));

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
				"-Ddeployer.staging.taglib.dtd=util-taglib/classes/META-INF" +
					"/liferay-staging.tld");
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

	@Internal
	public File getWebAppFile() {
		return GradleUtil.toFile(project, _webAppFile);
	}

	@Input
	public String getWebAppType() {
		return GradleUtil.toString(_webAppType);
	}

	@Input
	public boolean isCustomPortletXml() {
		return _customPortletXml;
	}

	@Input
	public boolean isUnpackWar() {
		return _unpackWar;
	}

	public void setAppServerDeployDir(Object appServerDeployDir) {
		_appServerDeployDir = appServerDeployDir;
	}

	public void setAppServerDir(Object appServerDir) {
		_appServerDir = appServerDir;
	}

	public void setAppServerLibGlobalDir(Object appServerLibGlobalDir) {
		_appServerLibGlobalDir = appServerLibGlobalDir;
	}

	public void setAppServerPortalDir(Object appServerPortalDir) {
		_appServerPortalDir = appServerPortalDir;
	}

	public void setAppServerType(Object appServerType) {
		_appServerType = appServerType;
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
		Configuration configuration = GradleUtil.getConfiguration(
			project, getConfigurationName());

		Configuration portalConfiguration = GradleUtil.getConfiguration(
			project, LiferayBasePlugin.PORTAL_CONFIGURATION_NAME);

		configuration.extendsFrom(portalConfiguration);
	}

	@Override
	protected String getToolName() {
		return "Deployer";
	}

	private File _getAppServerLibPortalDir() {
		return new File(getAppServerPortalDir(), "WEB-INF/lib");
	}

	private Object _appServerDeployDir;
	private Object _appServerDir;
	private Object _appServerLibGlobalDir;
	private Object _appServerPortalDir;
	private Object _appServerType;
	private boolean _customPortletXml;
	private boolean _unpackWar = true;
	private Object _webAppFile;
	private Object _webAppType;

}