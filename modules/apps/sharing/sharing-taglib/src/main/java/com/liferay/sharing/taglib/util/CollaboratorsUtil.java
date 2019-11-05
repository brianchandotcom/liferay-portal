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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.model.SharingEntryModel;
import com.liferay.sharing.service.SharingEntryLocalServiceUtil;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Alejandro Tardín
 */
public class CollaboratorsUtil {

	public static JSONObject getCollaboratorsJSONObject(
		long classNameId, long classPK, ThemeDisplay themeDisplay) {

		return JSONUtil.put(
			"collaborators",
			_getSharingEntryToUsersJSONArray(classPK, classNameId, themeDisplay)
		).put(
			"owner",
			_getUserJSONObject(_getOwner(classNameId, classPK), themeDisplay)
		).put(
			"total",
			SharingEntryLocalServiceUtil.getSharingEntriesCount(
				classNameId, classPK)
		);
	}

	private static String _getDisplayURL(ThemeDisplay themeDisplay, User user) {
		try {
			if (user.isDefaultUser()) {
				return StringPool.BLANK;
			}

			return user.getDisplayURL(themeDisplay);
		}
		catch (PortalException pe) {
			_log.error(pe, pe);

			return null;
		}
	}

	private static User _getOwner(long classNameId, long classPK) {
		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(classNameId);

		try {
			AssetRenderer<?> assetRenderer =
				assetRendererFactory.getAssetRenderer(classPK);

			return UserLocalServiceUtil.fetchUser(assetRenderer.getUserId());
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		return null;
	}

	private static String _getPortraitURL(
		ThemeDisplay themeDisplay, User user) {

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

	private static JSONArray _getSharingEntryToUsersJSONArray(
		long classPK, long classNameId, ThemeDisplay themeDisplay) {

		List<SharingEntry> sharingEntries =
			SharingEntryLocalServiceUtil.getSharingEntries(
				classNameId, classPK, 0, 4);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		Stream<SharingEntry> stream = sharingEntries.stream();

		stream.map(
			SharingEntryModel::getToUserId
		).map(
			UserLocalServiceUtil::fetchUserById
		).filter(
			Objects::nonNull
		).map(
			user -> _getUserJSONObject(user, themeDisplay)
		).forEach(
			jsonArray::put
		);

		return jsonArray;
	}

	private static JSONObject _getUserJSONObject(
		User user, ThemeDisplay themeDisplay) {

		return JSONUtil.put(
			"displayURL", _getDisplayURL(themeDisplay, user)
		).put(
			"fullName", user.getFullName()
		).put(
			"portraitURL", _getPortraitURL(themeDisplay, user)
		).put(
			"userId", user.getUserId()
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CollaboratorsUtil.class);

}