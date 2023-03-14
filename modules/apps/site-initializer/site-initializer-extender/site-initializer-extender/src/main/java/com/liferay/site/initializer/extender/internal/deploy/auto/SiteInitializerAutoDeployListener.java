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

package com.liferay.site.initializer.extender.internal.deploy.auto;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.deploy.auto.AutoDeployListener;
import com.liferay.portal.kernel.deploy.auto.AutoDeployer;
import com.liferay.portal.kernel.deploy.auto.context.AutoDeploymentContext;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerFactory;

import java.io.File;
import java.io.FileInputStream;

import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Nilton Vieira
 */
@Component(service = AutoDeployListener.class)
public class SiteInitializerAutoDeployListener implements AutoDeployListener {

	@Override
	public int deploy(AutoDeploymentContext autoDeploymentContext)
		throws AutoDeployException {

		long currentCompanyId = CompanyThreadLocal.getCompanyId();
		PermissionChecker currentPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
		String currentPrincipalThreadLocalName = PrincipalThreadLocal.getName();
		ServiceContext currentServiceContext =
			ServiceContextThreadLocal.getServiceContext();

		try {
			_deploy(autoDeploymentContext.getFile());
		}
		catch (Exception exception) {
			throw new AutoDeployException(exception);
		}
		finally {
			CompanyThreadLocal.setCompanyId(currentCompanyId);
			PermissionThreadLocal.setPermissionChecker(
				currentPermissionChecker);
			PrincipalThreadLocal.setName(currentPrincipalThreadLocalName);
			ServiceContextThreadLocal.pushServiceContext(currentServiceContext);
		}

		return AutoDeployer.CODE_DEFAULT;
	}

	@Override
	public boolean isDeployable(AutoDeploymentContext autoDeploymentContext)
		throws AutoDeployException {

		File file = autoDeploymentContext.getFile();

		String fileName = file.getName();

		if (!StringUtil.endsWith(fileName, ".zip")) {
			return false;
		}

		try (ZipFile zipFile = new ZipFile(file)) {

			ZipEntry zipEntry = zipFile.getEntry("site-initializer/");

			if(zipEntry == null){
				return false;
			}

			UnicodeProperties typeSettingsUnicodeProperties =
				_getTypeSettingsUnicodeProperties(zipFile);

			if ((typeSettingsUnicodeProperties != null) &&
				Validator.isNotNull(
					typeSettingsUnicodeProperties.getProperty("siteName"))) {

				return true;
			}
		}
		catch (Exception exception) {
			throw new AutoDeployException(exception);
		}

		return false;
	}

	private void _deploy(File file) throws Exception {
		if (_log.isInfoEnabled()) {
			_log.info(
				"Deploying site initializer bundle from file " +
					file.getName());
		}

		UnicodeProperties typeSettingsUnicodeProperties = null;

		try(ZipFile zipFile = new ZipFile(file)){
			typeSettingsUnicodeProperties =
				_getTypeSettingsUnicodeProperties(zipFile);
		}

		if (typeSettingsUnicodeProperties == null) {
			throw new AutoDeployException();
		}

		long companyId = GetterUtil.getLong(
			typeSettingsUnicodeProperties.getProperty("companyId"));

		if (companyId == 0) {
			if (_log.isWarnEnabled()) {
				_log.warn("Using default company ID");
			}

			Company company = _companyLocalService.getCompanyByWebId(
				PropsUtil.get(PropsKeys.COMPANY_DEFAULT_WEB_ID));

			companyId = company.getCompanyId();
		}

		long userId = GetterUtil.getLong(
			typeSettingsUnicodeProperties.getProperty("userId"));

		if (userId == 0) {
			if (_log.isWarnEnabled()) {
				_log.warn("Using default user ID");
			}

			userId = _userLocalService.getUserIdByScreenName(
				companyId, PropsUtil.get(PropsKeys.DEFAULT_ADMIN_SCREEN_NAME));
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(companyId);
		serviceContext.setUserId(userId);

		String groupName = typeSettingsUnicodeProperties.getProperty(
			"siteName");

		Group group = _groupLocalService.addGroup(
			userId, GroupConstants.DEFAULT_PARENT_GROUP_ID, null, 0, 0,
			HashMapBuilder.put(
				LocaleUtil.getDefault(), groupName
			).build(),
			null, GroupConstants.TYPE_SITE_PRIVATE, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(groupName),
			true, true, serviceContext);

		serviceContext.setScopeGroupId(group.getGroupId());

		CompanyThreadLocal.setCompanyId(companyId);
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(
				_userLocalService.getUser(userId)));
		PrincipalThreadLocal.setName(userId);
		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		File tempFile = FileUtil.createTempFile();

		FileUtil.write(tempFile, new FileInputStream(file));

		File tempDir = FileUtil.createTempFolder();

		FileUtil.unzip(tempFile, tempDir);

		SiteInitializer siteInitializer = _siteInitializerFactory.create(
			new File(tempDir, "site-initializer"), file.getName());

		try {
			siteInitializer.initialize(group.getGroupId());
		}
		finally {
			FileUtil.delete(tempFile);
			FileUtil.deltree(tempDir);
			PermissionCacheUtil.clearCache(userId);
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	private UnicodeProperties _getTypeSettingsUnicodeProperties(ZipFile zipFile)
		throws Exception {
			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

			while (enumeration.hasMoreElements()) {
				ZipEntry zipEntry = enumeration.nextElement();

				String name = zipEntry.getName();

				if (zipEntry.isDirectory() ||
					!name.endsWith("client-extension-config.json")) {

					continue;
				}

				JSONObject clientExtensionJSONObject = _jsonFactory.createJSONObject(
					StringUtil.read(zipFile.getInputStream(zipEntry)));

				if (clientExtensionJSONObject == null) {
					return null;
				}

				for (String key : clientExtensionJSONObject.keySet()) {
					JSONObject jsonObject = clientExtensionJSONObject.getJSONObject(key);

					JSONArray typeSettingsJSONArray = jsonObject.getJSONArray(
						"typeSettings");

					if (typeSettingsJSONArray == null) {
						continue;
					}

					String typeSettings = "";

					for (int i = 0; i < typeSettingsJSONArray.length(); i++) {
						typeSettings +=
							typeSettingsJSONArray.getString(i) + "\n";
					}

					return UnicodePropertiesBuilder.fastLoad(
						typeSettings
					).build();
				}
			}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SiteInitializerAutoDeployListener.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private SiteInitializerFactory _siteInitializerFactory;

	@Reference
	private UserLocalService _userLocalService;

}