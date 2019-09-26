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

package com.liferay.sharing.taglib.util;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.model.SharingEntryModel;
import com.liferay.sharing.service.SharingEntryLocalService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = CollaboratorsHelper.class)
public class CollaboratorsHelper {

	public JSONObject getCollaboratorsJSONObject(
			long classNameId, long classPK, ThemeDisplay themeDisplay)
		throws Exception {

		return JSONUtil.put(
			"collaborators",
			_getSharingEntryToUsersJSONArray(classPK, classNameId, themeDisplay)
		).put(
			"owner",
			_getUserJSONObject(_getOwner(classNameId, classPK), themeDisplay)
		).put(
			"total",
			_sharingEntryLocalService.getSharingEntriesCount(
				classNameId, classPK)
		);
	}

	private User _getOwner(long classNameId, long classPK)
		throws PortalException {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(classNameId);

		AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(
			classPK);

		return _userLocalService.fetchUser(assetRenderer.getUserId());
	}

	private String _getPortraitURL(ThemeDisplay themeDisplay, User user) {
		try {
			if (user.getPortraitId() == 0) {
				return null;
			}

			return user.getPortraitURL(themeDisplay);
		}
		catch (PortalException pe) {
			_log.error(pe, pe);

			return null;
		}
	}

	private JSONArray _getSharingEntryToUsersJSONArray(
		long classPK, long classNameId, ThemeDisplay themeDisplay) {

		List<SharingEntry> sharingEntries =
			_sharingEntryLocalService.getSharingEntries(
				classNameId, classPK, 0, 4);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		Stream<SharingEntry> stream = sharingEntries.stream();

		stream.map(
			SharingEntryModel::getToUserId
		).map(
			_userLocalService::fetchUserById
		).filter(
			Objects::nonNull
		).map(
			user -> _getUserJSONObject(user, themeDisplay)
		).forEach(
			jsonArray::put
		);

		return jsonArray;
	}

	private JSONObject _getUserJSONObject(
		User user, ThemeDisplay themeDisplay) {

		return JSONUtil.put(
			"fullName", user.getFullName()
		).put(
			"userId", user.getUserId()
		).put(
			"portraitURL", _getPortraitURL(themeDisplay, user)
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CollaboratorsHelper.class);

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}