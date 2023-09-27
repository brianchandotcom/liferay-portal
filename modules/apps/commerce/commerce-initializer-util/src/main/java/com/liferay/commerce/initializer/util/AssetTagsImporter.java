/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.initializer.util;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(service = AssetTagsImporter.class)
public class AssetTagsImporter {

	public List<AssetTag> importAssetTags(
			JSONArray jsonArray, long scopeGroupId, long userId)
		throws Exception {

		User user = _userLocalService.getUser(userId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setScopeGroupId(scopeGroupId);
		serviceContext.setUserId(userId);

		List<AssetTag> assetTags = new ArrayList<>(jsonArray.length());

		for (int i = 0; i < jsonArray.length(); i++) {
			String tagName = jsonArray.getString(i);

			AssetTag assetTag = _assetTagLocalService.fetchTag(
				scopeGroupId, tagName);

			if (assetTag == null) {
				assetTag = _assetTagLocalService.addTag(
					userId, scopeGroupId, tagName, serviceContext);
			}

			assetTags.add(assetTag);
		}

		return assetTags;
	}

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private UserLocalService _userLocalService;

}