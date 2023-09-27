/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.initializer.util;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.File;
import com.liferay.portal.kernel.util.MimeTypesUtil;

import java.io.InputStream;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Steven Smith
 */
@Component(service = BlogsImporter.class)
public class BlogsImporter {

	public void importBlogsEntries(
			JSONArray jsonArray, ClassLoader classLoader,
			String imageDependenciesPath, long scopeGroupId, long userId)
		throws Exception {

		User user = _userLocalService.getUser(userId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setScopeGroupId(scopeGroupId);
		serviceContext.setUserId(userId);

		Date date = new Date();

		for (int i = 0; i < jsonArray.length(); i++) {
			_addBlogsEntry(
				jsonArray.getJSONObject(i), classLoader, imageDependenciesPath,
				userId, date, serviceContext);
		}
	}

	protected void updatePermissions(
			long companyId, String name, String primKey, JSONArray jsonArray)
		throws PortalException {

		if (jsonArray == null) {
			jsonArray = _jsonFactory.createJSONArray(
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

	private void _addBlogsEntry(
			JSONObject jsonObject, ClassLoader classLoader,
			String imageDependenciesPath, long userId, Date displayDate,
			ServiceContext serviceContext)
		throws Exception {

		// Blogs Entry

		String content = jsonObject.getString("content");
		String title = jsonObject.getString("title");

		BlogsEntry blogsEntry = _blogsEntryLocalService.addEntry(
			userId, title, content, displayDate, serviceContext);

		JSONArray permissionsJSONArray = jsonObject.getJSONArray("permissions");

		if ((permissionsJSONArray != null) &&
			(permissionsJSONArray.length() > 0)) {

			updatePermissions(
				blogsEntry.getCompanyId(), blogsEntry.getModelClassName(),
				String.valueOf(blogsEntry.getPrimaryKey()),
				permissionsJSONArray);
		}
		else {

			// Give site members view permissions

			updatePermissions(
				blogsEntry.getCompanyId(), blogsEntry.getModelClassName(),
				String.valueOf(blogsEntry.getPrimaryKey()), null);
		}

		// Add Cover Image

		String fileName = jsonObject.getString("coverImageFileName");

		InputStream inputStream = classLoader.getResourceAsStream(
			imageDependenciesPath + fileName);

		ImageSelector imageSelector = new ImageSelector(
			_file.getBytes(inputStream), fileName,
			MimeTypesUtil.getContentType(fileName), StringPool.BLANK);

		_blogsEntryLocalService.addCoverImage(
			blogsEntry.getEntryId(), imageSelector);

		// Add Tags

		JSONArray tagsJSONArray = jsonObject.getJSONArray("tags");

		String[] assetTagNames = ArrayUtil.toStringArray(tagsJSONArray);

		if (assetTagNames.length > 0) {
			_blogsEntryLocalService.updateAsset(
				userId, blogsEntry, new long[0], assetTagNames, new long[0],
				null);
		}
	}

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Reference
	private File _file;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}