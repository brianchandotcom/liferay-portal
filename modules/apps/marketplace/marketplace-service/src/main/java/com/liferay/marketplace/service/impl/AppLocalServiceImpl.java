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

package com.liferay.marketplace.service.impl;

import com.liferay.document.library.kernel.exception.NoSuchFileException;
import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.marketplace.exception.AppPropertiesException;
import com.liferay.marketplace.exception.AppTitleException;
import com.liferay.marketplace.exception.AppVersionException;
import com.liferay.marketplace.internal.bundle.BundleManagerUtil;
import com.liferay.marketplace.model.App;
import com.liferay.marketplace.model.Module;
import com.liferay.marketplace.service.ModuleLocalService;
import com.liferay.marketplace.service.base.AppLocalServiceBaseImpl;
import com.liferay.marketplace.util.comparator.AppTitleComparator;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.deploy.DeployManagerUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.osgi.framework.Bundle;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ryan Park
 * @author Joan Kim
 */
@Component(
	property = "model.class.name=com.liferay.marketplace.model.App",
	service = AopService.class
)
public class AppLocalServiceImpl extends AppLocalServiceBaseImpl {

	@Override
	public void clearInstalledAppsCache() {
		_installedApps = null;
		_prepackagedApps = null;
	}

	@Override
	public App deleteApp(App app) {

		// App

		clearInstalledAppsCache();

		appPersistence.remove(app);

		// Module

		List<Module> modules = modulePersistence.findByAppId(app.getAppId());

		for (Module module : modules) {
			_moduleLocalService.deleteModule(module);
		}

		// File

		try {
			DLStoreUtil.deleteFile(
				app.getCompanyId(), CompanyConstants.SYSTEM, app.getFilePath());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}
		}

		return app;
	}

	@Override
	public App deleteApp(long appId) throws PortalException {
		App app = appPersistence.findByPrimaryKey(appId);

		return deleteApp(app);
	}

	@Override
	public App fetchRemoteApp(long remoteAppId) {
		return appPersistence.fetchByRemoteAppId(remoteAppId);
	}

	@Override
	public List<App> getApps(String category) {
		return appPersistence.findByCategory(category);
	}

	@Override
	public List<App> getInstalledApps() {
		if (_installedApps != null) {
			return _installedApps;
		}

		List<App> installedApps = new ArrayList<>();

		// Core app

		App coreApp = appPersistence.create(0);

		coreApp.setTitle("Liferay Core");
		coreApp.setDescription("Plugins bundled with Liferay Portal.");
		coreApp.setVersion(ReleaseInfo.getVersion());

		coreApp.addContextName(_portal.getServletContextName());

		installedApps.add(coreApp);

		// Deployed apps

		List<PluginPackage> pluginPackages =
			DeployManagerUtil.getInstalledPluginPackages();

		for (PluginPackage pluginPackage : pluginPackages) {
			List<Module> modules = modulePersistence.findByContextName(
				pluginPackage.getContext());

			boolean installedApp = false;

			for (Module module : modules) {
				App app = appPersistence.fetchByPrimaryKey(module.getAppId());

				if ((app != null) && app.isInstalled()) {
					installedApp = true;

					break;
				}
			}

			if (installedApp) {
				continue;
			}

			App app = appPersistence.create(0);

			app.setTitle(pluginPackage.getName());
			app.setDescription(pluginPackage.getLongDescription());
			app.setVersion(pluginPackage.getVersion());
			app.setRequired(true);

			app.addContextName(pluginPackage.getContext());

			installedApps.add(app);
		}

		// Marketplace apps

		List<App> apps = appPersistence.findAll();

		for (App app : apps) {
			if (app.isInstalled()) {
				installedApps.add(app);
			}
		}

		installedApps = ListUtil.sort(installedApps, new AppTitleComparator());

		_installedApps = installedApps;

		return _installedApps;
	}

	@Override
	public List<App> getInstalledApps(String category) {
		List<App> apps = appPersistence.findByCategory(category);

		List<App> installedApps = new ArrayList<>(apps.size());

		for (App app : apps) {
			if (app.isInstalled()) {
				installedApps.add(app);
			}
		}

		return installedApps;
	}

	@Override
	public Map<String, String> getPrepackagedApps() {
		if (_prepackagedApps != null) {
			return _prepackagedApps;
		}

		Map<String, String> prepackagedApps = new HashMap<>();

		List<Bundle> bundles = BundleManagerUtil.getInstalledBundles();

		for (Bundle bundle : bundles) {
			Dictionary<String, String> headers = bundle.getHeaders(
				StringPool.BLANK);

			boolean liferayRelengBundle = GetterUtil.getBoolean(
				headers.get("Liferay-Releng-Bundle"));

			if (!liferayRelengBundle) {
				continue;
			}

			prepackagedApps.put(
				bundle.getSymbolicName(), String.valueOf(bundle.getVersion()));
		}

		_prepackagedApps = prepackagedApps;

		return _prepackagedApps;
	}

	@Override
	public void installApp(long remoteAppId) throws PortalException {
		App app = appPersistence.findByRemoteAppId(remoteAppId);

		if (!DLStoreUtil.hasFile(
				app.getCompanyId(), CompanyConstants.SYSTEM,
				app.getFilePath())) {

			throw new NoSuchFileException();
		}

		try (InputStream inputStream = DLStoreUtil.getFileAsStream(
				app.getCompanyId(), CompanyConstants.SYSTEM,
				app.getFilePath())) {

			if (inputStream == null) {
				throw new IOException(
					"Unable to open file at " + app.getFilePath());
			}

			StringBundler sb = new StringBundler(5);

			sb.append(SystemProperties.get(SystemProperties.TMP_DIR));
			sb.append(StringPool.SLASH);
			sb.append(encodeSafeFileName(app.getTitle()));
			sb.append(StringPool.PERIOD);
			sb.append(FileUtil.getExtension(app.getFileName()));

			File file = new File(sb.toString());

			FileUtil.write(file, inputStream);

			BundleManagerUtil.installLPKG(file);
		}
		catch (IOException ioException) {
			throw new PortalException(ioException);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}
		finally {
			clearInstalledAppsCache();
		}
	}

	@Override
	public void uninstallApp(long remoteAppId) throws PortalException {
		clearInstalledAppsCache();

		App app = appPersistence.findByRemoteAppId(remoteAppId);

		List<Module> modules = modulePersistence.findByAppId(app.getAppId());

		for (Module module : modules) {
			_moduleLocalService.deleteModule(module.getModuleId());

			if (module.isBundle()) {
				BundleManagerUtil.uninstallBundle(
					module.getBundleSymbolicName(), module.getBundleVersion());

				continue;
			}

			if (hasDependentApp(module)) {
				continue;
			}

			try {
				DeployManagerUtil.undeploy(module.getContextName());
			}
			catch (Exception exception) {
				_log.error(exception, exception);
			}
		}
	}

	@Override
	public App updateApp(long userId, File file) throws PortalException {
		Properties properties = getMarketplaceProperties(file);

		if (properties == null) {
			throw new AppPropertiesException(
				"Unable to read liferay-marketplace.properties");
		}

		long remoteAppId = GetterUtil.getLong(
			properties.getProperty("remote-app-id"));
		String title = properties.getProperty("title");
		String description = properties.getProperty("description");
		String category = properties.getProperty("category");
		String iconURL = properties.getProperty("icon-url");
		String version = properties.getProperty("version");
		boolean required = GetterUtil.getBoolean(
			properties.getProperty("required"));

		return updateApp(
			userId, remoteAppId, title, description, category, iconURL, version,
			required, file);
	}

	@Override
	public App updateApp(
			long userId, long remoteAppId, String title, String description,
			String category, String iconURL, String version, boolean required,
			File file)
		throws PortalException {

		// App

		User user = userLocalService.fetchUser(userId);
		Date now = new Date();

		validate(title, version);

		App app = appPersistence.fetchByRemoteAppId(remoteAppId);

		if (app == null) {
			long appId = counterLocalService.increment();

			app = appPersistence.create(appId);
		}

		if (user != null) {
			app.setCompanyId(user.getCompanyId());
			app.setUserId(user.getUserId());
			app.setUserName(user.getFullName());
		}

		app.setCreateDate(now);
		app.setModifiedDate(now);
		app.setRemoteAppId(remoteAppId);
		app.setTitle(title);
		app.setDescription(description);
		app.setCategory(category);
		app.setIconURL(iconURL);
		app.setVersion(version);
		app.setRequired(required);

		app = appPersistence.update(app);

		// File

		if (file != null) {
			try {
				DLStoreUtil.deleteFile(
					app.getCompanyId(), CompanyConstants.SYSTEM,
					app.getFilePath());
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception, exception);
				}
			}

			DLStoreUtil.addFile(
				app.getCompanyId(), CompanyConstants.SYSTEM, app.getFilePath(),
				false, file);
		}

		clearInstalledAppsCache();

		return app;
	}

	protected String encodeSafeFileName(String fileName) {
		if (fileName == null) {
			return StringPool.BLANK;
		}

		fileName = FileUtil.encodeSafeFileName(fileName);

		return StringUtil.replace(
			fileName, _SAFE_FILE_NAME_1, _SAFE_FILE_NAME_2);
	}

	protected Properties getMarketplaceProperties(File liferayPackageFile) {
		try (ZipFile zipFile = new ZipFile(liferayPackageFile)) {
			ZipEntry zipEntry = zipFile.getEntry(
				"liferay-marketplace.properties");

			if (zipEntry == null) {
				Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

				ZipEntry subsystemZipEntry = enumeration.nextElement();

				if (StringUtil.endsWith(subsystemZipEntry.getName(), ".lpkg")) {
					File file = null;

					try (InputStream subsystemInputStream =
							zipFile.getInputStream(subsystemZipEntry)) {

						file = FileUtil.createTempFile(subsystemInputStream);

						return getMarketplaceProperties(file);
					}
					finally {
						FileUtil.delete(file);
					}
				}

				return null;
			}

			try (InputStream inputStream = zipFile.getInputStream(zipEntry)) {
				String propertiesString = StringUtil.read(inputStream);

				return PropertiesUtil.load(propertiesString);
			}
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ioException, ioException);
			}

			return null;
		}
	}

	protected boolean hasDependentApp(Module module) throws PortalException {
		List<Module> modules = modulePersistence.findByContextName(
			module.getContextName());

		for (Module curModule : modules) {
			if (curModule.getAppId() == module.getAppId()) {
				continue;
			}

			App app = appPersistence.findByPrimaryKey(curModule.getAppId());

			if (app.isInstalled()) {
				return true;
			}
		}

		return false;
	}

	protected void validate(String title, String version)
		throws PortalException {

		if (Validator.isNull(title)) {
			throw new AppTitleException();
		}

		if (Validator.isNull(version)) {
			throw new AppVersionException();
		}
	}

	/**
	 * @see com.liferay.portal.util.FileImpl#_SAFE_FILE_NAME_1
	 */
	private static final String[] _SAFE_FILE_NAME_1 = {
		StringPool.BACK_SLASH, StringPool.COLON, StringPool.GREATER_THAN,
		StringPool.LESS_THAN, StringPool.PIPE, StringPool.QUESTION,
		StringPool.QUOTE, StringPool.SLASH, StringPool.STAR
	};

	/**
	 * @see com.liferay.portal.util.FileImpl#_SAFE_FILE_NAME_2
	 */
	private static final String[] _SAFE_FILE_NAME_2 = {
		"_BSL_", "_COL_", "_GT_", "_LT_", "_PIP_", "_QUE_", "_QUO_", "_SL_",
		"_ST_"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		AppLocalServiceImpl.class);

	private List<App> _installedApps;

	@Reference
	private ModuleLocalService _moduleLocalService;

	@Reference
	private Portal _portal;

	private Map<String, String> _prepackagedApps;

}