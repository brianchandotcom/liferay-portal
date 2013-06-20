/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.plugininstaller.action;

import com.liferay.portal.deploy.DeployUtil;
import com.liferay.portal.events.GlobalStartupAction;
import com.liferay.portal.kernel.deploy.auto.AutoDeployDir;
import com.liferay.portal.kernel.deploy.auto.AutoDeployListener;
import com.liferay.portal.kernel.deploy.auto.AutoDeployUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.plugin.PluginPackageUtil;
import com.liferay.portal.plugin.RepositoryReport;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.lang.DoPrivilegedBean;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.tools.deploy.BaseDeployer;
import com.liferay.portal.upload.ProgressInputStream;
import com.liferay.portal.util.HttpImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 * @author Minhchau Dang
 */
public class InstallPluginAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (!permissionChecker.isOmniadmin()) {
			SessionErrors.add(
				actionRequest, PrincipalException.class.getName());

			setForward(actionRequest, "portlet.plugin_installer.error");

			return;
		}

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals("deployConfiguration")) {
			deployConfiguration(actionRequest);
		}
		else if (cmd.equals("ignorePackages")) {
			ignorePackages(actionRequest);
		}
		else if (cmd.equals("localDeploy")) {
			localDeploy(actionRequest);
		}
		else if (cmd.equals("reloadRepositories")) {
			reloadRepositories(actionRequest);
		}
		else if (cmd.equals("remoteDeploy")) {
			remoteDeploy(actionRequest);
		}
		else if (cmd.equals("unignorePackages")) {
			unignorePackages(actionRequest);
		}
		else if (cmd.equals("uninstall")) {
			uninstall(actionRequest);
		}

		sendRedirect(actionRequest, actionResponse);
	}

	protected void deployConfiguration(ActionRequest actionRequest)
		throws Exception {

		boolean enabled = ParamUtil.getBoolean(actionRequest, "enabled");
		String deployDir = ParamUtil.getString(actionRequest, "deployDir");
		String destDir = ParamUtil.getString(actionRequest, "destDir");
		long interval = ParamUtil.getLong(actionRequest, "interval");
		boolean unpackWar = ParamUtil.getBoolean(actionRequest, "unpackWar");
		boolean customPortletXml = ParamUtil.getBoolean(
			actionRequest, "customPortletXml");
		String jbossPrefix = ParamUtil.getString(actionRequest, "jbossPrefix");
		String tomcatConfDir = ParamUtil.getString(
			actionRequest, "tomcatConfDir");
		String tomcatLibDir = ParamUtil.getString(
			actionRequest, "tomcatLibDir");
		String pluginRepositoriesTrusted = ParamUtil.getString(
			actionRequest, "pluginRepositoriesTrusted");
		String pluginRepositoriesUntrusted = ParamUtil.getString(
			actionRequest, "pluginRepositoriesUntrusted");
		boolean pluginNotificationsEnabled = ParamUtil.getBoolean(
			actionRequest, "pluginNotificationsEnabled");
		String pluginPackagesIgnored = ParamUtil.getString(
			actionRequest, "pluginPackagesIgnored");

		PortletPreferences portletPreferences = PrefsPropsUtil.getPreferences();

		portletPreferences.setValue(
			PropsKeys.AUTO_DEPLOY_ENABLED, String.valueOf(enabled));
		portletPreferences.setValue(
			PropsKeys.AUTO_DEPLOY_DEPLOY_DIR, deployDir);
		portletPreferences.setValue(PropsKeys.AUTO_DEPLOY_DEST_DIR, destDir);
		portletPreferences.setValue(
			PropsKeys.AUTO_DEPLOY_INTERVAL, String.valueOf(interval));
		portletPreferences.setValue(
			PropsKeys.AUTO_DEPLOY_UNPACK_WAR, String.valueOf(unpackWar));
		portletPreferences.setValue(
			PropsKeys.AUTO_DEPLOY_CUSTOM_PORTLET_XML,
			String.valueOf(customPortletXml));
		portletPreferences.setValue(
			PropsKeys.AUTO_DEPLOY_JBOSS_PREFIX, jbossPrefix);
		portletPreferences.setValue(
			PropsKeys.AUTO_DEPLOY_TOMCAT_CONF_DIR, tomcatConfDir);
		portletPreferences.setValue(
			PropsKeys.AUTO_DEPLOY_TOMCAT_LIB_DIR, tomcatLibDir);
		portletPreferences.setValue(
			PropsKeys.PLUGIN_REPOSITORIES_TRUSTED, pluginRepositoriesTrusted);
		portletPreferences.setValue(
			PropsKeys.PLUGIN_REPOSITORIES_UNTRUSTED,
			pluginRepositoriesUntrusted);
		portletPreferences.setValue(
			PropsKeys.PLUGIN_NOTIFICATIONS_ENABLED,
			String.valueOf(pluginNotificationsEnabled));
		portletPreferences.setValue(
			PropsKeys.PLUGIN_NOTIFICATIONS_PACKAGES_IGNORED,
			pluginPackagesIgnored);

		portletPreferences.store();

		reloadRepositories(actionRequest);

		if (_log.isInfoEnabled()) {
			_log.info("Unregistering auto deploy directories");
		}

		AutoDeployUtil.unregisterDir("defaultAutoDeployDir");

		if (enabled) {
			if (_log.isInfoEnabled()) {
				_log.info("Registering auto deploy directories");
			}

			List<AutoDeployListener> autoDeployListeners =
				GlobalStartupAction.getAutoDeployListeners(true);

			AutoDeployDir autoDeployDir = new AutoDeployDir(
				"defaultAutoDeployDir", new File(deployDir), new File(destDir),
				interval, autoDeployListeners);

			AutoDeployUtil.registerDir(autoDeployDir);
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info("Not registering auto deploy directories");
			}
		}
	}

	protected String[] getSourceForgeMirrors() {
		return PropsUtil.getArray(PropsKeys.SOURCE_FORGE_MIRRORS);
	}

	protected void ignorePackages(ActionRequest actionRequest)
		throws Exception {

		String pluginPackagesIgnored = ParamUtil.getString(
			actionRequest, "pluginPackagesIgnored");

		String oldPluginPackagesIgnored = PrefsPropsUtil.getString(
			PropsKeys.PLUGIN_NOTIFICATIONS_PACKAGES_IGNORED);

		PortletPreferences portletPreferences = PrefsPropsUtil.getPreferences();

		if (Validator.isNotNull(oldPluginPackagesIgnored)) {
			portletPreferences.setValue(
				PropsKeys.PLUGIN_NOTIFICATIONS_PACKAGES_IGNORED,
				oldPluginPackagesIgnored.concat(StringPool.NEW_LINE).concat(
					pluginPackagesIgnored));
		}
		else {
			portletPreferences.setValue(
				PropsKeys.PLUGIN_NOTIFICATIONS_PACKAGES_IGNORED,
				pluginPackagesIgnored);
		}

		portletPreferences.store();

		PluginPackageUtil.refreshUpdatesAvailableCache();
	}

	protected void localDeploy(ActionRequest actionRequest) throws Exception {
		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(actionRequest);

		String fileName = null;

		String deploymentContext = ParamUtil.getString(
			actionRequest, "deploymentContext");

		if (Validator.isNotNull(deploymentContext)) {
			fileName =
				BaseDeployer.DEPLOY_TO_PREFIX + deploymentContext + ".war";
		}
		else {
			fileName = GetterUtil.getString(
				uploadPortletRequest.getFileName("file"));

			int pos = fileName.lastIndexOf(CharPool.PERIOD);

			if (pos != -1) {
				deploymentContext = fileName.substring(0, pos);
			}
		}

		File file = uploadPortletRequest.getFile("file");

		byte[] bytes = FileUtil.getBytes(file);

		if ((bytes == null) || (bytes.length == 0)) {
			SessionErrors.add(actionRequest, UploadException.class.getName());

			return;
		}

		try {
			PluginPackageUtil.registerPluginPackageInstallation(
				deploymentContext);

			String source = file.toString();

			String deployDir = PrefsPropsUtil.getString(
				PropsKeys.AUTO_DEPLOY_DEPLOY_DIR,
				PropsValues.AUTO_DEPLOY_DEPLOY_DIR);

			String destination = deployDir + StringPool.SLASH + fileName;

			FileUtil.copyFile(source, destination);

			SessionMessages.add(actionRequest, "pluginUploaded");
		}
		finally {
			PluginPackageUtil.endPluginPackageInstallation(deploymentContext);
		}
	}

	protected void reloadRepositories(ActionRequest actionRequest)
		throws Exception {

		RepositoryReport repositoryReport =
			PluginPackageUtil.reloadRepositories();

		SessionMessages.add(
			actionRequest, WebKeys.PLUGIN_REPOSITORY_REPORT, repositoryReport);
	}

	protected void remoteDeploy(ActionRequest actionRequest) throws Exception {
		try {
			String url = ParamUtil.getString(actionRequest, "url");

			URL urlObj = new URL(url);

			String host = urlObj.getHost();

			if (host.endsWith(".sf.net") || host.endsWith(".sourceforge.net")) {
				remoteDeploySourceForge(urlObj.getPath(), actionRequest);
			}
			else {
				remoteDeploy(url, urlObj, actionRequest, true);
			}
		}
		catch (MalformedURLException murle) {
			SessionErrors.add(actionRequest, "invalidUrl", murle);
		}
	}

	protected int remoteDeploy(
			String url, URL urlObj, ActionRequest actionRequest,
			boolean failOnError)
		throws Exception {

		int responseCode = HttpServletResponse.SC_OK;

		GetMethod getMethod = null;

		String deploymentContext = ParamUtil.getString(
			actionRequest, "deploymentContext");

		try {
			HttpImpl httpImpl = null;

			Object httpObject = HttpUtil.getHttp();

			if (httpObject instanceof DoPrivilegedBean) {
				DoPrivilegedBean doPrivilegedBean =
					(DoPrivilegedBean)httpObject;

				httpImpl = (HttpImpl)doPrivilegedBean.getActualBean();
			}
			else {
				httpImpl = (HttpImpl)httpObject;
			}

			HostConfiguration hostConfiguration = httpImpl.getHostConfiguration(
				url);

			HttpClient httpClient = httpImpl.getClient(hostConfiguration);

			getMethod = new GetMethod(url);

			String fileName = null;

			if (Validator.isNotNull(deploymentContext)) {
				fileName =
					BaseDeployer.DEPLOY_TO_PREFIX + deploymentContext + ".war";
			}
			else {
				fileName = url.substring(url.lastIndexOf(CharPool.SLASH) + 1);

				int pos = fileName.lastIndexOf(CharPool.PERIOD);

				if (pos != -1) {
					deploymentContext = fileName.substring(0, pos);
				}
			}

			PluginPackageUtil.registerPluginPackageInstallation(
				deploymentContext);

			responseCode = httpClient.executeMethod(
				hostConfiguration, getMethod);

			if (responseCode != HttpServletResponse.SC_OK) {
				if (failOnError) {
					SessionErrors.add(
						actionRequest, "errorConnectingToUrl",
						new Object[] {String.valueOf(responseCode)});
				}

				return responseCode;
			}

			long contentLength = getMethod.getResponseContentLength();

			String progressId = ParamUtil.getString(
				actionRequest, Constants.PROGRESS_ID);

			ProgressInputStream pis = new ProgressInputStream(
				actionRequest, getMethod.getResponseBodyAsStream(),
				contentLength, progressId);

			String deployDir = PrefsPropsUtil.getString(
				PropsKeys.AUTO_DEPLOY_DEPLOY_DIR,
				PropsValues.AUTO_DEPLOY_DEPLOY_DIR);

			String tmpFilePath =
				deployDir + StringPool.SLASH + _DOWNLOAD_DIR +
					StringPool.SLASH + fileName;

			File tmpFile = new File(tmpFilePath);

			if (!tmpFile.getParentFile().exists()) {
				tmpFile.getParentFile().mkdirs();
			}

			FileOutputStream fos = new FileOutputStream(tmpFile);

			try {
				pis.readAll(fos);

				if (_log.isInfoEnabled()) {
					_log.info(
						"Downloaded plugin from " + urlObj + " has " +
							pis.getTotalRead() + " bytes");
				}
			}
			finally {
				pis.clearProgress();
			}

			getMethod.releaseConnection();

			if (pis.getTotalRead() > 0) {
				String destination = deployDir + StringPool.SLASH + fileName;

				File destinationFile = new File(destination);

				boolean moved = FileUtil.move(tmpFile, destinationFile);

				if (!moved) {
					FileUtil.copyFile(tmpFile, destinationFile);
					FileUtil.delete(tmpFile);
				}

				SessionMessages.add(actionRequest, "pluginDownloaded");
			}
			else {
				if (failOnError) {
					SessionErrors.add(
						actionRequest, UploadException.class.getName());
				}

				responseCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
			}
		}
		catch (MalformedURLException murle) {
			SessionErrors.add(actionRequest, "invalidUrl", murle);
		}
		catch (IOException ioe) {
			SessionErrors.add(actionRequest, "errorConnectingToUrl", ioe);
		}
		finally {
			if (getMethod != null) {
				getMethod.releaseConnection();
			}

			PluginPackageUtil.endPluginPackageInstallation(deploymentContext);
		}

		return responseCode;
	}

	protected void remoteDeploySourceForge(
			String path, ActionRequest actionRequest)
		throws Exception {

		String[] sourceForgeMirrors = getSourceForgeMirrors();

		for (int i = 0; i < sourceForgeMirrors.length; i++) {
			try {
				String url = sourceForgeMirrors[i] + path;

				if (_log.isDebugEnabled()) {
					_log.debug("Downloading from SourceForge mirror " + url);
				}

				URL urlObj = new URL(url);

				boolean failOnError = false;

				if ((i + 1) == sourceForgeMirrors.length) {
					failOnError = true;
				}

				int responseCode = remoteDeploy(
					url, urlObj, actionRequest, failOnError);

				if (responseCode == HttpServletResponse.SC_OK) {
					return;
				}
			}
			catch (MalformedURLException murle) {
				SessionErrors.add(actionRequest, "invalidUrl", murle);
			}
		}
	}

	protected void unignorePackages(ActionRequest actionRequest)
		throws Exception {

		String[] pluginPackagesUnignored = StringUtil.splitLines(
			ParamUtil.getString(actionRequest, "pluginPackagesUnignored"));

		String[] pluginPackagesIgnored = PrefsPropsUtil.getStringArray(
			PropsKeys.PLUGIN_NOTIFICATIONS_PACKAGES_IGNORED,
			StringPool.NEW_LINE,
			PropsValues.PLUGIN_NOTIFICATIONS_PACKAGES_IGNORED);

		StringBundler sb = new StringBundler();

		for (int i = 0; i < pluginPackagesIgnored.length; i++) {
			String packageId = pluginPackagesIgnored[i];

			if (!ArrayUtil.contains(pluginPackagesUnignored, packageId)) {
				sb.append(packageId);
				sb.append(StringPool.NEW_LINE);
			}
		}

		PortletPreferences portletPreferences = PrefsPropsUtil.getPreferences();

		portletPreferences.setValue(
			PropsKeys.PLUGIN_NOTIFICATIONS_PACKAGES_IGNORED, sb.toString());

		portletPreferences.store();

		PluginPackageUtil.refreshUpdatesAvailableCache();
	}

	protected void uninstall(ActionRequest actionRequest) throws Exception {
		String appServerType = ServerDetector.getServerId();

		String deploymentContext = ParamUtil.getString(
			actionRequest, "deploymentContext");

		File deployDir = new File(
			DeployUtil.getAutoDeployDestDir(), deploymentContext);

		DeployUtil.undeploy(appServerType, deployDir);

		SessionMessages.add(actionRequest, "triggeredPortletUndeploy");
	}

	private static final String _DOWNLOAD_DIR = "download";

	private static Log _log = LogFactoryUtil.getLog(InstallPluginAction.class);

}