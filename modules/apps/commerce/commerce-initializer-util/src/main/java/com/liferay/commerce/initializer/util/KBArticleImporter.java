/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.initializer.util;

import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Steven Smith
 */
@Component(service = KBArticleImporter.class)
public class KBArticleImporter {

	public void importKBArticles(
			JSONArray jsonArray, long scopeGroupId, long userId)
		throws Exception {

		User user = _userLocalService.getUser(userId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setScopeGroupId(scopeGroupId);
		serviceContext.setUserId(userId);

		for (int i = 0; i < jsonArray.length(); i++) {
			_addKBArticle(jsonArray.getJSONObject(i), userId, serviceContext);
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

	private void _addKBArticle(
			JSONObject jsonObject, long userId, ServiceContext serviceContext)
		throws Exception {

		// KB Article

		String content = jsonObject.getString("content");
		String title = jsonObject.getString("title");

		String[] sections = {};
		String[] selectedFileNames = {};

		long folderClassNameId = _classNameLocalService.getClassNameId(
			KBFolderConstants.getClassName());

		KBArticle kbArticle = _kbArticleLocalService.addKBArticle(
			null, userId, folderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID, title, null, content,
			StringPool.BLANK, sections, null, new Date(), null, null,
			selectedFileNames, serviceContext);

		JSONArray tagsJSONArray = jsonObject.getJSONArray("tags");

		if (tagsJSONArray.length() > 0) {
			String[] assetTagNames = ArrayUtil.toStringArray(tagsJSONArray);

			_kbArticleLocalService.updateKBArticleAsset(
				userId, kbArticle, new long[0], assetTagNames, new long[0]);
		}

		JSONArray permissionsJSONArray = jsonObject.getJSONArray("permissions");

		if ((permissionsJSONArray != null) &&
			(permissionsJSONArray.length() > 0)) {

			updatePermissions(
				kbArticle.getCompanyId(), kbArticle.getModelClassName(),
				String.valueOf(kbArticle.getResourcePrimKey()),
				permissionsJSONArray);
		}
		else {

			// Give site members view permissions

			updatePermissions(
				kbArticle.getCompanyId(), kbArticle.getModelClassName(),
				String.valueOf(kbArticle.getResourcePrimKey()), null);
		}
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private KBArticleLocalService _kbArticleLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}