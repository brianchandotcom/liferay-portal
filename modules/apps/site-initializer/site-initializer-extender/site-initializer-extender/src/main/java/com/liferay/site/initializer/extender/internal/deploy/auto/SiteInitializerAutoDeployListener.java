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

import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.deploy.auto.AutoDeployListener;
import com.liferay.portal.kernel.deploy.auto.AutoDeployer;
import com.liferay.portal.kernel.deploy.auto.context.AutoDeploymentContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerFactory;

import java.io.File;

import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
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

		try {
			_deploy(autoDeploymentContext.getFile());
		}
		catch (Exception exception) {
			throw new AutoDeployException(exception);
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
			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

			while (enumeration.hasMoreElements()) {
				ZipEntry zipEntry = enumeration.nextElement();

				if (!zipEntry.isDirectory() || !_isValid(zipEntry.getName())) {
					continue;
				}

				_setDeployConfiguration(zipFile, zipEntry.getName());

				if ((_companyId > 0) && (_groupName != null) && (_userId > 0)) {
					return true;
				}
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

		Map<Locale, String> nameMap = HashMapBuilder.put(
			LocaleUtil.getDefault(), _groupName
		).build();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(_companyId);
		serviceContext.setUserId(_userId);

		PrincipalThreadLocal.setName(_userId);

		Group group = _groupLocalService.addGroup(
			_userId, GroupConstants.DEFAULT_PARENT_GROUP_ID, null, 0, 0,
			nameMap, null, GroupConstants.TYPE_SITE_OPEN, true, 0,
			"/" + FriendlyURLNormalizerUtil.normalize(_groupName), true, true,
			serviceContext);

		serviceContext.setScopeGroupId(group.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(
				_userLocalService.getUser(_userId)));

		File tempFile = FileUtil.createTempFile();

		FileUtil.write(
			tempFile,
			new FileInputStream(file));

		File tempDir1 = FileUtil.createTempFolder();

		FileUtil.unzip(tempFile, tempDir1);

		tempFile.delete();

		SiteInitializer siteInitializer = _siteInitializerFactory.create(
			new File(tempDir1, "site-initializer"), null);

		tempFile.delete();

		try {
			siteInitializer.initialize(group.getGroupId());
		}
		catch (Exception e) {
			ServiceContextThreadLocal.popServiceContext();
			_groupLocalService.deleteGroup(group);
		}
	}

	private boolean _isValid(String fileName) {
		if (Objects.equals(fileName, "site-initializer/")) {
			return true;
		}

		return false;
	}

	private void _setDeployConfiguration(ZipFile zipFile, String zipEntryName)
		throws Exception {

		ZipEntry zipEntry = zipFile.getEntry(zipEntryName + "config.json");

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			StringUtil.read(zipFile.getInputStream(zipEntry)));

		_groupName = jsonObject.getString("groupName");

		if (_groupName == null) {
			return;
		}

		_companyId = jsonObject.getLong("companyId");

		if (_companyId == 0) {
			if (_log.isWarnEnabled()) {
				_log.warn("Using default company ID for this site process");
			}

			try {
				Company company = _companyLocalService.getCompanyByWebId(
					PropsUtil.get(PropsKeys.COMPANY_DEFAULT_WEB_ID));

				_companyId = company.getCompanyId();
			}
			catch (PortalException portalException) {
				_log.error("Unable to get default company ID", portalException);
			}
		}

		_userId = jsonObject.getLong("userId");

		if (_userId == 0) {
			if (_log.isWarnEnabled()) {
				_log.warn("Using default user ID for this site process");
			}

			try {
				_userId = _userLocalService.getUserIdByScreenName(
					_companyId,
					PropsUtil.get(PropsKeys.DEFAULT_ADMIN_SCREEN_NAME));
			}
			catch (PortalException portalException) {
				_log.error("Unable to get default user ID", portalException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SiteInitializerAutoDeployListener.class);

	private long _companyId;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private SiteInitializerFactory _siteInitializerFactory;

	private String _groupName;

	private long _userId;

	@Reference
	private UserLocalService _userLocalService;

}