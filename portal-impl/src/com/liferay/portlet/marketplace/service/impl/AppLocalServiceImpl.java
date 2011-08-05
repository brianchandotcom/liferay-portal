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

package com.liferay.portlet.marketplace.service.impl;

import com.liferay.portal.deploy.DeployUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;
import com.liferay.portlet.marketplace.DuplicateAppException;
import com.liferay.portlet.marketplace.VersionException;
import com.liferay.portlet.marketplace.model.App;
import com.liferay.portlet.marketplace.model.Module;
import com.liferay.portlet.marketplace.service.base.AppLocalServiceBaseImpl;

import java.io.File;
import java.io.InputStream;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Ryan Park
 */
public class AppLocalServiceImpl extends AppLocalServiceBaseImpl {

	public App addApp(
			long userId, long marketplaceAppId, String version,
			String[] moduleContextNames, InputStream is)
		throws PortalException, SystemException {

		// App

		User user = userPersistence.findByPrimaryKey(userId);

		validate(version, marketplaceAppId);

		Date now = new Date();

		long appId = counterLocalService.increment();

		App app = appPersistence.create(appId);

		app.setAppId(appId);
		app.setCompanyId(user.getCompanyId());
		app.setUserId(user.getUserId());
		app.setUserName(user.getFullName());
		app.setCreateDate(now);
		app.setModifiedDate(now);
		app.setMarketplaceAppId(marketplaceAppId);
		app.setVersion(version);

		appPersistence.update(app, false);

		// File

		DLStoreUtil.addFile(
			app.getCompanyId(), CompanyConstants.SYSTEM, app.getFileName(),
			false, is);

		// Module

		for (String contextName : moduleContextNames) {
			moduleLocalService.addModule(userId, appId, contextName);
		}

		return app;
	}

	public void deleteApp(long appId) throws PortalException, SystemException {
		App app = appPersistence.findByPrimaryKey(appId);

		deleteApp(app);
	}

	public void deleteApp(App app) throws PortalException, SystemException {

		// App

		appPersistence.remove(app);

		// Module

		List<Module> modules = moduleLocalService.getAppModules(app.getAppId());

		for (Module module : modules) {
			moduleLocalService.deleteModule(module);
		}

		// File

		try {
			DLStoreUtil.deleteFile(
				app.getCompanyId(), CompanyConstants.SYSTEM, app.getFileName());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
	}

	public void deleteAppByMarketplaceAppId(long marketplaceAppId)
		throws PortalException, SystemException {

		App app = appPersistence.findByMarketplaceAppId(marketplaceAppId);

		deleteApp(app);
	}

	public App fetchApp(long marketplaceAppId) throws SystemException {
		return appPersistence.fetchByMarketplaceAppId(marketplaceAppId);
	}

	public App getApp(long marketplaceAppId)
		throws PortalException, SystemException {

		return appPersistence.findByMarketplaceAppId(marketplaceAppId);
	}

	public List<App> getApps(long companyId, int start, int end)
		throws SystemException {

		return appPersistence.findByCompanyId(companyId, start, end);
	}

	public int getAppsCount(long companyId) throws SystemException {
		return appPersistence.countByCompanyId(companyId);
	}

	public boolean hasApp(long marketplaceAppId) throws SystemException {
		App app = appPersistence.fetchByMarketplaceAppId(marketplaceAppId);

		if (app == null) {
			return false;
		}

		return true;
	}

	public void installApp(long marketplaceAppId)
		throws PortalException, SystemException {

		App app = appPersistence.findByMarketplaceAppId(marketplaceAppId);

		String deployDir = PrefsPropsUtil.getString(
			PropsKeys.AUTO_DEPLOY_DEPLOY_DIR,
			PropsValues.AUTO_DEPLOY_DEPLOY_DIR);

		String destination = deployDir + StringPool.SLASH + app.getFileName();

		try {
			InputStream is = DLStoreUtil.getFileAsStream(
				app.getCompanyId(), CompanyConstants.SYSTEM, app.getFileName());

			FileUtil.write(destination, is);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void uninstallApp(long marketplaceAppId)
		throws PortalException, SystemException {

		App app = appPersistence.findByMarketplaceAppId(marketplaceAppId);

		List<Module> modules = moduleLocalService.getAppModules(app.getAppId());

		String appServerType = ServerDetector.getServerId();

		boolean jbossAppServer = appServerType.startsWith(
			ServerDetector.JBOSS_ID);

		for (Module module : modules) {
			String context = module.getContextName();

			if (hasDependentApp(module)) {
				continue;
			}

			if (jbossAppServer) {
				context += ".war";
			}

			try {
				String autoDeployDestDir = DeployUtil.getAutoDeployDestDir();

				File deployDir = new File(
					autoDeployDestDir + StringPool.SLASH + context);

				DeployUtil.undeploy(appServerType, deployDir);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	public App updateApp(
			long appId, String version, String[] moduleContextNames,
			InputStream is)
		throws PortalException, SystemException {

		// App

		validate(version, 0);

		App app = appPersistence.findByPrimaryKey(appId);

		app.setModifiedDate(new Date());
		app.setVersion(version);

		// File

		if (is != null) {
			try {
				DLStoreUtil.deleteFile(
					app.getCompanyId(), CompanyConstants.SYSTEM,
					app.getFileName());
			}
			catch (Exception e) {
			}

			DLStoreUtil.addFile(
				app.getCompanyId(), CompanyConstants.SYSTEM, app.getFileName(),
				false, is);
		}

		// Module

		Set<Long> moduleIds = new HashSet<Long>();

		for (String contextName : moduleContextNames) {
			Module module = moduleLocalService.fetchModule(appId, contextName);

			if (module == null) {
				module = moduleLocalService.addModule(
					app.getUserId(), appId, contextName);
			}

			moduleIds.add(module.getModuleId());
		}

		List<Module> modules = moduleLocalService.getAppModules(appId);

		for (Module module : modules) {
			if (!moduleIds.contains(module.getModuleId())) {
				moduleLocalService.deleteModule(module.getModuleId());
			}
		}

		return app;
	}

	protected void validate(String version, long marketplaceAppId)
		throws PortalException, SystemException {

		if (Validator.isNull(version)) {
			throw new VersionException();
		}

		if (marketplaceAppId > 0) {
			App app = fetchApp(marketplaceAppId);

			if (app != null) {
				throw new DuplicateAppException();
			}
		}
	}

	private boolean hasDependentApp(Module module)
		throws PortalException, SystemException {

		List<Module> modules = moduleLocalService.getModulesByContextName(
			module.getContextName());

		for (Module curModule : modules) {
			App app = appPersistence.findByPrimaryKey(curModule.getAppId());

			if (curModule.getAppId() == module.getAppId()) {
				continue;
			}

			if (app.isInstalled()) {
				return true;
			}
		}

		return false;
	}

	private static Log _log = LogFactoryUtil.getLog(AppLocalServiceImpl.class);

}