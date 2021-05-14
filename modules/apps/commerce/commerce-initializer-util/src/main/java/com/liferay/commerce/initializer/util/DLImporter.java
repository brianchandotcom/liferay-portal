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

package com.liferay.commerce.initializer.util;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.InputStream;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Steven Smith
 */
@Component(enabled = false, service = DLImporter.class)
public class DLImporter {

	public void importDocuments(
			JSONArray jsonArray, ClassLoader classLoader,
			String documentsDependenciesPath, long scopeGroupId, long userId)
		throws Exception {

		User user = _userLocalService.fetchUser(userId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(scopeGroupId);
		serviceContext.setUserId(userId);
		serviceContext.setCompanyId(user.getCompanyId());

		for (int i = 0; i < jsonArray.length(); i++) {
			_addDLFileEntries(
				jsonArray.getJSONObject(i), classLoader,
				documentsDependenciesPath, userId, scopeGroupId,
				serviceContext);
		}
	}

	protected void updatePermissions(
			long companyId, String name, String primKey, JSONArray jsonArray)
		throws PortalException {

		if (jsonArray == null) {
			jsonArray = JSONFactoryUtil.createJSONArray(
				"[{\"actionIds\": [\"VIEW\"], \"roleName\": \"Site Member\"," +
					"\"scope\": 4}]");
		}

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			int scope = jsonObject.getInt("scope");

			String roleName = jsonObject.getString("roleName");

			Role role = _roleLocalService.getRole(companyId, roleName);

			String[] actionIds = new String[0];

			JSONArray actionIdsJSONArray = jsonObject.getJSONArray("actionIds");

			if (actionIdsJSONArray != null) {
				for (int j = 0; j < actionIdsJSONArray.length(); j++) {
					actionIds = ArrayUtil.append(
						actionIds, actionIdsJSONArray.getString(j));
				}
			}

			_resourcePermissionLocalService.setResourcePermissions(
				companyId, name, scope, primKey, role.getRoleId(), actionIds);
		}
	}

	private void _addDLFileEntries(
			JSONObject jsonObject, ClassLoader classLoader,
			String documentsDependencyPath, long userId, long scopeGroupId,
			ServiceContext serviceContext)
		throws Exception {

		DLFolder dlFolder = _addDLFolder(
			jsonObject, userId, scopeGroupId, serviceContext);

		JSONArray dlFileEntriesJSONArray = jsonObject.getJSONArray("files");

		for (int i = 0; i < dlFileEntriesJSONArray.length(); i++) {
			JSONObject fileJSONObject = dlFileEntriesJSONArray.getJSONObject(i);

			_addDLFileEntry(
				fileJSONObject, classLoader, documentsDependencyPath, userId,
				dlFolder, serviceContext);
		}
	}

	private DLFileEntry _addDLFileEntry(
			JSONObject jsonObject, ClassLoader classLoader,
			String documentsDependencyPath, long userId, DLFolder dlFolder,
			ServiceContext serviceContext)
		throws Exception {

		// DL File Entry

		DLFileEntry dlFileEntry = null;

		String fileName = jsonObject.getString("fileName");

		if (Validator.isNotNull(fileName)) {
			String description = jsonObject.getString("description");
			String title = jsonObject.getString("title");

			Repository repository = _repositoryLocalService.fetchRepository(
				dlFolder.getRepositoryId());

			InputStream inputStream = classLoader.getResourceAsStream(
				documentsDependencyPath + fileName);

			File file = FileUtil.createTempFile(inputStream);

			FileEntry fileEntry = _dlAppLocalService.addFileEntry(
				null, userId, repository.getRepositoryId(),
				dlFolder.getFolderId(), fileName,
				MimeTypesUtil.getContentType(file), title, description,
				StringPool.BLANK, file, null, null, serviceContext);

			dlFileEntry = _dlFileEntryLocalService.getDLFileEntry(
				fileEntry.getFileEntryId());

			JSONArray permissionsJSONArray = jsonObject.getJSONArray(
				"permissions");

			if ((permissionsJSONArray != null) &&
				(permissionsJSONArray.length() > 0)) {

				updatePermissions(
					dlFileEntry.getCompanyId(), dlFileEntry.getModelClassName(),
					String.valueOf(dlFileEntry.getFileEntryId()),
					permissionsJSONArray);
			}
			else {

				// Give site members view permissions

				updatePermissions(
					dlFileEntry.getCompanyId(), dlFileEntry.getModelClassName(),
					String.valueOf(dlFileEntry.getPrimaryKey()), null);
			}
		}

		return dlFileEntry;
	}

	private DLFolder _addDLFolder(
			JSONObject jsonObject, long userId, long scopeGroupId,
			ServiceContext serviceContext)
		throws Exception {

		// DL Folder

		String description = jsonObject.getString("description");
		String name = jsonObject.getString("name");

		List<Repository> repositories =
			_repositoryLocalService.getGroupRepositories(scopeGroupId);

		Repository repository = repositories.get(0);

		DLFolder dlFolder = _dlFolderLocalService.addFolder(
			userId, scopeGroupId, repository.getRepositoryId(), false,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, name, description,
			false, serviceContext);

		JSONArray permissionsJSONArray = jsonObject.getJSONArray("permissions");

		if ((permissionsJSONArray != null) &&
			(permissionsJSONArray.length() > 0)) {

			updatePermissions(
				dlFolder.getCompanyId(), dlFolder.getModelClassName(),
				String.valueOf(dlFolder.getPrimaryKey()), permissionsJSONArray);
		}
		else {

			// Give site members view permissions

			updatePermissions(
				dlFolder.getCompanyId(), dlFolder.getModelClassName(),
				String.valueOf(dlFolder.getPrimaryKey()), null);
		}

		return dlFolder;
	}

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DLFolderLocalService _dlFolderLocalService;

	@Reference
	private RepositoryLocalService _repositoryLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}