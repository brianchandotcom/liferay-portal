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

		serviceContext.setScopeGroupId(scopeGroupId);
		serviceContext.setUserId(userId);
		serviceContext.setCompanyId(user.getCompanyId());

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