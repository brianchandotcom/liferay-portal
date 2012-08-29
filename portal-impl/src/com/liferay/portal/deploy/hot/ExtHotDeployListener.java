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

package com.liferay.portal.deploy.hot;

import com.liferay.portal.kernel.deploy.hot.BaseHotDeployListener;
import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.WebDirDetector;
import com.liferay.portal.kernel.servlet.taglib.FileAvailabilityUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.tools.WebXMLBuilder;
import com.liferay.portal.util.ExtRegistry;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.ant.CopyTask;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;

import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * @author Brian Wing Shun Chan
 * @author Tomas Polesovsky
 */
public class ExtHotDeployListener extends BaseHotDeployListener {

	public static void uninstallManually(String servletContextName)
		throws Exception {

		if (!ExtRegistry.isRegistered(servletContextName)) {
			return;
		}

		if (ExtRegistry.isStarted(servletContextName)) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Uninstalling Ext Plugin for " + servletContextName);
		}

		ExtHotDeployListener extHotDeployListener = new ExtHotDeployListener();
		extHotDeployListener.uninstallExt(servletContextName);

		if (_log.isInfoEnabled()) {
			_log.info("Ext Plugin " + servletContextName +
				" has been uninstalled. Please restart server" +
				" to apply changes!");
		}
	}

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

		String jarFullName = "/WEB-INF/" + jarName + "/" + jarName + ".jar";

		InputStream is = servletContext.getResourceAsStream(jarFullName);

		if (is == null) {
			throw new HotDeployException(jarFullName + " does not exist");
		}

		String newJarFullName =
			dir + "ext-" + servletContextName + jarName.substring(3) + ".jar";

		StreamUtil.transfer(is, new FileOutputStream(new File(newJarFullName)));
	}

	protected void doInvokeDeploy(HotDeployEvent hotDeployEvent)
		throws Exception {

		ServletContext servletContext = hotDeployEvent.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking deploy for " + servletContextName);
		}

		String xml = HttpUtil.URLtoString(
			servletContext.getResource(
				"/WEB-INF/ext-" + servletContextName + ".xml"));

		if (xml == null) {
			return;
		}

		if (ExtRegistry.isRegistered(servletContextName) &&
			ExtRegistry.isUndeployed(servletContextName)) {

			if (_log.isInfoEnabled()) {
				_log.info("Redeploying ext plugin for " + servletContextName);
			}

			uninstallExt(servletContextName);

			if (_log.isInfoEnabled()) {
				_log.info(
					"Ext plugin " + servletContextName +
						" has been undeployed.");
			}

		}

		if (ExtRegistry.isRegistered(servletContextName)) {
			ExtRegistry.setStarted(servletContext);

			if (_log.isInfoEnabled()) {
				_log.info(
					"Ext plugin for " + servletContextName +
						" is correctly installed.");
			}

			return;
		}

		Map<String, Set<String>> conflicts = ExtRegistry.getConflicts(
			servletContext);

		if (!conflicts.isEmpty()) {
			StringBundler sb = new StringBundler();

			sb.append(
				"Ext plugin for " + servletContextName +
					" cannot be applied because of detected conflicts:");

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

		if (_log.isInfoEnabled()) {
			_log.info("Installing ext plugin for " + servletContextName + ".");
		}

		try {
			installExt(servletContext, hotDeployEvent.getContextClassLoader());
		} catch (Exception e) {
			try {
				// rollback
				uninstallExt(servletContextName);
			} catch (Exception ex) {
				_log.warn("Rollback of ext plugin installation for " +
					servletContext + " wasn't successful", ex);
			}

			throw e;
		}

		FileAvailabilityUtil.reset();

		ExtRegistry.setRestartPending(servletContextName);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Ext plugin for " + servletContextName +
					" has been installed. You must restart the server.");
		}
	}

	protected void doInvokeUndeploy(HotDeployEvent hotDeployEvent)
		throws Exception {

		ServletContext servletContext = hotDeployEvent.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking undeploy for " + servletContextName);
		}

		if (ExtRegistry.isRegistered(servletContextName)) {
			ExtRegistry.setUndeployed(servletContextName);

			if (_log.isInfoEnabled()) {
				_log.info(
					"Ext Plugin for " + servletContextName +
						" was undeployed, but is still registered. " +
						"To uninstall it completely please click " +
						"Uninstall button in Control Panel -> " +
						"Plugins Installation -> Ext Plugins");
			}
		} else {
			_log.error(
				"Ext Plugin for " + servletContextName +
					" is not registered!");
		}
	}

	protected void installExt(
			ServletContext servletContext, ClassLoader portletClassLoader)
		throws Exception {

		String servletContextName = servletContext.getServletContextName();

		String portalWebDir = PortalUtil.getPortalWebDir();
		String pluginWebDir = WebDirDetector.getRootDir(portletClassLoader);

		ExtRegistry.registerExt(servletContext);

		installJars(servletContext);
		installWebInfJar(portalWebDir, pluginWebDir, servletContextName);
		installWebFiles(portalWebDir, pluginWebDir, servletContextName);

		boolean portalExtPropertiesInstalled = installPortalExtProperties(
			portalWebDir, pluginWebDir, servletContextName);

		if (portalExtPropertiesInstalled) {
			rebuildPortalExtPluginProperties();
		}

		boolean webXmlInstalled = installWebXml(
			portalWebDir, pluginWebDir, servletContextName);

		if (webXmlInstalled) {
			rebuildWebXml();
		}

		FileUtil.copyFile(
			pluginWebDir + "WEB-INF/ext-" + servletContextName + ".xml",
			portalWebDir + "WEB-INF/ext-" + servletContextName + ".xml");

	}

	protected void installJars(ServletContext servletContext)
			throws Exception {
		String globalLibDir = PortalUtil.getGlobalLibDir();
		String portalLibDir = PortalUtil.getPortalLibDir();

		for (String jarName : ExtRegistry.EXT_PLUGIN_JARS_GLOBAL_CL) {
			copyJar(servletContext, globalLibDir, jarName);
		}

		for (String jarName : ExtRegistry.EXT_PLUGIN_JARS_PORTAL_CL) {
			copyJar(servletContext, portalLibDir, jarName);
		}
	}

	protected boolean installPortalExtProperties(
		String portalWebDir, String pluginWebDir, String servletContextName)
		throws Exception {

		File pluginPortalExtPropertiesFile = new File(
			pluginWebDir + "WEB-INF/ext-web/docroot/WEB-INF/classes/" +
			"portal-ext.properties");

		if (!pluginPortalExtPropertiesFile.exists()) {
			if (_log.isDebugEnabled()) {
				_log.debug("Ext plugin " + servletContextName + " doesn't " +
					"contain portal-ext.properties.");
			}

			return false;
		}

		File portalPluginPortalExtPropertiesFile =
			getPluginPortalExtPropertiesFile(portalWebDir, servletContextName);

		FileUtil.copyFile(
			pluginPortalExtPropertiesFile, portalPluginPortalExtPropertiesFile);

		return true;
	}

	protected void installWebFiles(
		String portalWebDir, String pluginWebDir, String servletContextName)
		throws Exception {

		HookHotDeployListener hookListener = new HookHotDeployListener();

		Set<String> files = ExtRegistry.getFiles(servletContextName);
		for (String file : files) {
			if (file.startsWith("ext-web/docroot/") &&
				!ExtRegistry.isMergedFile(file)) {

				String relativeFile = file.substring(
					file.indexOf("docroot/") + "docroot/".length());

				File pluginFile = new File(pluginWebDir + "WEB-INF/", file);
				File portalFile = new File(portalWebDir, relativeFile);
				File originalFile = portalFile;

				if (portalFile.exists()) {
					File backupFile = getBackupFile(
						portalFile.getAbsolutePath());

					File portalJspBackupFile =
						hookListener.getPortalJspBackupFile(portalFile);

					if (portalJspBackupFile.exists()) {

						// hook created a backup of JSP, original file is now
						// in the hook's backup file

						originalFile = portalJspBackupFile;
					}

					if (_log.isDebugEnabled()) {
						_log.debug("Creating backup [of, to]: [" +
							originalFile + ", " + backupFile + "]");
					}

					createBackup(originalFile, backupFile);
				}

				if (_log.isDebugEnabled()) {
					_log.debug("Copying [from, to]: [" + pluginFile + ", " +
						originalFile + "]");
				}

				FileUtil.copyFile(pluginFile, originalFile);
			}
		}
	}

	protected void installWebInfJar(
		String portalWebDir, String pluginWebDir, String servletContextName)
		throws Exception {

		String zipName =
			portalWebDir + "WEB-INF/lib/ext-" + servletContextName +
				"-webinf.jar";

		File dir = new File(pluginWebDir + "WEB-INF/ext-web/docroot/WEB-INF");
		if (!dir.isDirectory()) {
			throw new IllegalArgumentException("Not a directory: " + dir);
		}

		File[] filesToZip = dir.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return ExtRegistry.isMergedFile(pathname.getPath());
			}
		});

		zipWebInfJar(zipName, filesToZip);
	}

	protected boolean installWebXml(
		String portalWebDir, String pluginWebDir, String servletContextName)
		throws Exception {

		File pluginWebXmlFile = new File(
			pluginWebDir + "WEB-INF/ext-web/docroot/WEB-INF/web.xml");

		if (!pluginWebXmlFile.exists()) {
			if (_log.isDebugEnabled()) {
				_log.debug("Ext plugin " + servletContextName + " doesn't " +
					"contain portal web.xml.");
			}

			return false;
		}

		File portalPluginWebXmlFile = getPluginWebXMLFile(
			portalWebDir, servletContextName);

		FileUtil.copyFile(pluginWebXmlFile, portalPluginWebXmlFile);

		return true;
	}

	protected void rebuildPortalExtPluginProperties() throws Exception {
		String portalWebDir = PortalUtil.getPortalWebDir();

		File extPluginPropsFile = new File(
			PortalUtil.getPortalWebDir() + "WEB-INF/classes/" +
				"portal-ext-plugin.properties");

		extPluginPropsFile.delete();
		extPluginPropsFile.createNewFile();

		Set<String> contextNames = ExtRegistry.getServletContextNames();

		// sort by name so changes are applied in the right order
		List<String> contextNamesList = ListUtil.fromCollection(contextNames);
		ListUtil.sort(contextNamesList);

		for (String servletContextName : contextNamesList) {
			File pluginPropsFile = getPluginPortalExtPropertiesFile(
				portalWebDir, servletContextName);

			if (!pluginPropsFile.exists()) {
				if (_log.isDebugEnabled()) {
					_log.debug("Ext Plugin's portal-ext.properties not found " +
						"for " + servletContextName);
				}

				return;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Loading portal-ext.properties from " +
					pluginPropsFile);
			}

			rebuildPortalExtPluginProperties(pluginPropsFile);
		}
	}

	protected void rebuildWebXml() throws IOException {
		String portalWebDir = PortalUtil.getPortalWebDir();

		File webXmlFile = new File(portalWebDir + "WEB-INF/web.xml");
		File backupFile = getBackupFile(webXmlFile.getAbsolutePath());

		if (!backupFile.exists()) {
			if (_log.isDebugEnabled()) {
				_log.debug("Creating backup of web.xml");
			}

			createBackup(webXmlFile, backupFile);
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("Restoring backup of web.xml");
			}

			restoreBackup(webXmlFile, backupFile, false);
		}

		Set<String> contextNames = ExtRegistry.getServletContextNames();

		// sort by name so changes are applied in the right order
		List<String> contextNamesList = ListUtil.fromCollection(contextNames);
		ListUtil.sort(contextNamesList);

		for (String servletContextName : contextNamesList) {
			File pluginWebXMLFile = getPluginWebXMLFile(
				portalWebDir, servletContextName);

			if (!pluginWebXMLFile.exists()) {
				if (_log.isDebugEnabled()) {
					_log.debug("Ext Plugin's web.xml not found for " +
						servletContextName);
				}

				return;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Rebuilding portal's web.xml using " +
					pluginWebXMLFile);
			}

			rebuildWebXml(pluginWebXMLFile);
		}
	}

	protected void rebuildWebXml(File pluginWebXMLFile) throws IOException {
		String portalWebDir = PortalUtil.getPortalWebDir();
		String tmpDir =
			SystemProperties.get(SystemProperties.TMP_DIR) + StringPool.SLASH +
				Time.getTimestamp();

		WebXMLBuilder.main(
			new String[] {
				portalWebDir + "WEB-INF/web.xml",
				pluginWebXMLFile.getAbsolutePath(), tmpDir + "/web.xml"
			});

		File portalWebXml = new File(portalWebDir + "WEB-INF/web.xml");
		File tmpWebXml = new File(tmpDir + "/web.xml");

		tmpWebXml.setLastModified(portalWebXml.lastModified());

		CopyTask.copyFile(
			tmpWebXml, new File(portalWebDir + "WEB-INF"), true, true);

		FileUtil.deltree(tmpDir);
	}

	protected void removeJar(
			String servletContextName, String dir, String jarName)
			throws Exception {

		String newJarFullName =
				dir + "ext-" + servletContextName + jarName.substring(3) +
					".jar";

		FileUtil.delete(newJarFullName);
	}

	protected void uninstallExt(String servletContextName) throws Exception {
		uninstallJars(servletContextName);
		uninstallWebInfJar(servletContextName);
		uninstallWebFiles(servletContextName);

		ExtRegistry.unregisterExt(servletContextName);

		boolean portalExtPropertiesUninstalled = uninstallPortalExtProperties(
			servletContextName);

		if (portalExtPropertiesUninstalled) {
			rebuildPortalExtPluginProperties();
		}

		boolean webXmlUninstalled = uninstallWebXml(servletContextName);

		if (webXmlUninstalled) {
			rebuildWebXml();
		}

		FileUtil.delete(PortalUtil.getPortalWebDir() +
			"WEB-INF/ext-" + servletContextName + ".xml");
	}

	protected void uninstallJars(String servletContextName)
			throws Exception {
		String globalLibDir = PortalUtil.getGlobalLibDir();
		String portalLibDir = PortalUtil.getPortalLibDir();

		for (String jarName : ExtRegistry.EXT_PLUGIN_JARS_GLOBAL_CL) {
			removeJar(servletContextName, globalLibDir, jarName);
		}

		for (String jarName : ExtRegistry.EXT_PLUGIN_JARS_PORTAL_CL) {
			removeJar(servletContextName, portalLibDir, jarName);
		}
	}

	protected boolean uninstallPortalExtProperties(String servletContextName) {
		String portalWebDir = PortalUtil.getPortalWebDir();

		File portalExtPropertiesFile = getPluginPortalExtPropertiesFile(
			portalWebDir, servletContextName);

		return FileUtil.delete(portalExtPropertiesFile);
	}

	protected void uninstallWebFiles(String servletContextName)
		throws IOException {

		String portalWebDir = PortalUtil.getPortalWebDir();
		HookHotDeployListener hookListener = new HookHotDeployListener();

		Set<String> files = ExtRegistry.getFiles(servletContextName);
		for (String file : files) {
			if (file.startsWith("ext-web/docroot/") &&
				!ExtRegistry.isMergedFile(file)) {

				String relativeFile = file.substring(
					file.indexOf("docroot/") + "docroot/".length());

				File portalFile = new File(portalWebDir, relativeFile);

				File backupFile = getBackupFile(portalFile.getAbsolutePath());

				if (!backupFile.exists()) {

					// file didn't exist in portal, we can delete
					// the imported file

					portalFile.delete();

					continue;
				}

				File originalFile = portalFile;

				File portalJspBackupFile = hookListener.getPortalJspBackupFile(
					portalFile);

				if (portalJspBackupFile.exists()) {
					// hook created a backup of JSP, original file is the hook's
					// backup file
					originalFile = portalJspBackupFile;
				}

				if (_log.isDebugEnabled()) {
					_log.debug("Restoring backup [of, from]: [" +
						originalFile + ", " + backupFile + "]");
				}

				restoreBackup(originalFile, backupFile, true);
			}
		}
	}

	protected void uninstallWebInfJar(String servletContextName)
		throws Exception {

		String portalLibDir = PortalUtil.getPortalLibDir();
		removeJar(servletContextName, portalLibDir, "ext-webinf");
	}

	protected boolean uninstallWebXml(String servletContextName) {
		String portalWebDir = PortalUtil.getPortalWebDir();

		File webXmlFile = getPluginWebXMLFile(portalWebDir, servletContextName);

		return FileUtil.delete(webXmlFile);
	}

	private void createBackup(File fileToBackup, File backupFile)
		throws IOException {

		if (backupFile.exists()) {
			_log.warn("Backup file " + backupFile + " already exists!");
			return;
		}

		FileUtil.copyFile(fileToBackup, backupFile);
		backupFile.setLastModified(fileToBackup.lastModified());
	}

	private File getBackupFile(String originalFileName) {
		return new File(originalFileName + BACKUP_EXT);
	}

	private File getPluginPortalExtPropertiesFile(
		String portalWebDir, String servletContextName) {

		return new File(portalWebDir + "WEB-INF", "ext-" + servletContextName +
			"-portal-ext.properties");
	}

	private File getPluginWebXMLFile(
		String portalWebDir, String servletContextName) {

		return new File(portalWebDir + "WEB-INF", "ext-" + servletContextName +
			"-web.xml");
	}

	private void rebuildPortalExtPluginProperties(File pluginPropsFile)
		throws Exception {

		PropertiesConfiguration pluginProps =
			new PropertiesConfiguration(pluginPropsFile);

		PropertiesConfiguration portalProps = new PropertiesConfiguration(
			this.getClass().getClassLoader().getResource("portal.properties"));

		File extPluginPropsFile = new File(
			PortalUtil.getPortalWebDir() + "WEB-INF/classes/" +
				"portal-ext-plugin.properties");

		PropertiesConfiguration extPluginPortalProps =
			new PropertiesConfiguration();

		if (extPluginPropsFile.exists()) {
			extPluginPortalProps.load(extPluginPropsFile);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Merging portal-ext-plugin.properties with " +
				extPluginPropsFile);
		}

		for (Iterator it = pluginProps.getKeys(); it.hasNext();) {
			String key = (String) it.next();
			List value = pluginProps.getList(key);

			if (key.endsWith(StringPool.PLUS)) {
				// merging values with existing ones, appending to end
				key = key.substring(0, key.length() - 1);
				List existingValues = null;

				if (extPluginPortalProps.containsKey(key)) {
					// already changed by another ext plugin
					existingValues = extPluginPortalProps.getList(key);
				} else {
					existingValues = portalProps.getList(key);
				}

				if (existingValues != null) {
					List newValue =
						new ArrayList(existingValues.size() + value.size());

					newValue.addAll(existingValues);
					newValue.addAll(value);

					value = newValue;
				}
			}

			extPluginPortalProps.setProperty(key, value);
		}

		extPluginPortalProps.save(extPluginPropsFile);
	}

	private void restoreBackup(
		File restoredFile, File backupFile, boolean deleteBackup)
		throws IOException {

		if (!backupFile.exists()) {
			_log.warn("Backup file " + backupFile + " doesn't exist!");
			return;
		}

		FileUtil.copyFile(backupFile, restoredFile);
		restoredFile.setLastModified(backupFile.lastModified());

		if (deleteBackup) {
			backupFile.delete();
		}
	}

	private void zipWebInfJar(String zipName, File[] files) throws Exception {
		byte[] buffer = new byte[4096];
		int bytesRead;

		if (files.length == 0) {
			return;
		}

		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
			zipName));

		try {
			for (int i = 0; i < files.length; i++) {
				File f = files[i];
				if (f.isDirectory()) {
					continue;
				}

				String fileName = "WEB-INF/" + f.getName();
				FileInputStream in = new FileInputStream(f);
				try {
					ZipEntry entry = new ZipEntry(fileName);
					out.putNextEntry(entry);
					while ((bytesRead = in.read(buffer)) != -1) {
						out.write(buffer, 0, bytesRead);
					}
				} finally {
					in.close();
				}
			}
		} finally {
			try {
				out.close();
			} catch (Exception ex) {
				_log.warn("Cannot close zip stream: " + ex.getMessage());
			}
		}
	}

	private static final String BACKUP_EXT = ".beforeExt";

	private static Log _log = LogFactoryUtil.getLog(ExtHotDeployListener.class);

}