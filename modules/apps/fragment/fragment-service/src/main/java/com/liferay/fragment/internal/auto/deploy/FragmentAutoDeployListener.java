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

package com.liferay.fragment.internal.auto.deploy;

import com.liferay.fragment.importer.FragmentsImporter;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.deploy.auto.AutoDeployListener;
import com.liferay.portal.kernel.deploy.auto.AutoDeployer;
import com.liferay.portal.kernel.deploy.auto.context.AutoDeploymentContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.IOException;

import java.util.Enumeration;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = AutoDeployListener.class)
public class FragmentAutoDeployListener implements AutoDeployListener {

	@Override
	public int deploy(AutoDeploymentContext autoDeploymentContext)
		throws AutoDeployException {

		File file = autoDeploymentContext.getFile();

		try {
			JSONObject deployJSONObject = _getDeployJSONObject(file);

			if ((deployJSONObject == null) ||
				!deployJSONObject.has("companyWebId")) {

				throw new AutoDeployException();
			}

			String webId = deployJSONObject.getString("companyWebId");

			Company company = _companyLocalService.getCompanyByWebId(webId);

			Group group = null;

			if (deployJSONObject.has("groupKey")) {
				group = _groupLocalService.getGroup(
					company.getCompanyId(),
					deployJSONObject.getString("groupKey"));
			}
			else {
				group = _groupLocalService.getCompanyGroup(
					company.getCompanyId());
			}

			User user = _getUser(group);

			if (user == null) {
				throw new AutoDeployException();
			}

			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			PrincipalThreadLocal.setName(user.getUserId());

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setUserId(user.getUserId());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			_fragmentsImporter.importFile(
				user.getUserId(), group.getGroupId(), 0, file, true);
		}
		catch (Exception e) {
			_log.error(e, e);
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

		try {
			JSONObject deployJSONObject = _getDeployJSONObject(file);

			if ((deployJSONObject != null) &&
				deployJSONObject.has("companyWebId")) {

				return true;
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return false;
	}

	private JSONObject _getDeployJSONObject(File file)
		throws IOException, JSONException {

		ZipFile zipFile = new ZipFile(file);

		ZipEntry zipEntry = _getDeployZipEntry(zipFile);

		if (zipEntry == null) {
			return null;
		}

		return JSONFactoryUtil.createJSONObject(
			StringUtil.read(zipFile.getInputStream(zipEntry)));
	}

	private ZipEntry _getDeployZipEntry(ZipFile zipFile) {
		Enumeration<? extends ZipEntry> iterator = zipFile.entries();

		while (iterator.hasMoreElements()) {
			ZipEntry zipEntry = iterator.nextElement();

			if (Objects.equals(
					_getFileName(zipEntry.getName()), "deploy.json")) {

				return zipEntry;
			}
		}

		return null;
	}

	private String _getFileName(String path) {
		int pos = path.lastIndexOf(CharPool.SLASH);

		if (pos > 0) {
			return path.substring(pos + 1);
		}

		return path;
	}

	private User _getUser(Group group) throws PortalException {
		long userId = group.getCreatorUserId();

		User user = _userLocalService.fetchUserById(userId);

		if ((user == null) || user.isDefaultUser()) {
			Role role = _roleLocalService.getRole(
				group.getCompanyId(), RoleConstants.ADMINISTRATOR);

			long[] userIds = _userLocalService.getRoleUserIds(role.getRoleId());

			return _userLocalService.getUser(userIds[0]);
		}

		return user;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentAutoDeployListener.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private FragmentsImporter _fragmentsImporter;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}