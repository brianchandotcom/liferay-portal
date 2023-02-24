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
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.site.initializer.SiteInitializerFactory;

import java.io.File;

import java.util.Enumeration;
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

		try (ZipFile zipFile = new ZipFile(autoDeploymentContext.getFile())) {
			_deploy(zipFile);
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

				if ((_companyId > 0) && (_userId > 0)) {
					return true;
				}
			}
		}
		catch (Exception exception) {
			throw new AutoDeployException(exception);
		}

		return false;
	}

	private void _deploy(ZipFile zipFile) throws Exception {
		if (_log.isInfoEnabled()) {
			_log.info(
				"Deploying site initializer bundle from file " +
					zipFile.getName());
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

		if (zipEntry != null) {
			JSONObject jsonObject = _jsonFactory.createJSONObject(
				StringUtil.read(zipFile.getInputStream(zipEntry)));

			_userId = jsonObject.getLong("userId");
			_companyId = jsonObject.getLong("companyId");
		}
		else {
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
	private com.liferay.portal.kernel.util.File _file;

	@Reference
	private JSONFactory _jsonFactory;

	private long _userId;

	@Reference
	private UserLocalService _userLocalService;

}