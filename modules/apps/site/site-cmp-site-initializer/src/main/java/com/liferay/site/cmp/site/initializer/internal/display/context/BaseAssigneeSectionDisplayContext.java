/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.display.context;

import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Map;

/**
 * @author Pedro Leite
 */
public abstract class BaseAssigneeSectionDisplayContext {

	public BaseAssigneeSectionDisplayContext(
		Language language, ObjectEntry objectEntry, ThemeDisplay themeDisplay,
		UserLocalService userLocalService) {

		_language = language;

		this.objectEntry = objectEntry;

		_themeDisplay = themeDisplay;
		_userLocalService = userLocalService;
	}

	public abstract String getAdditionalAPIURLParameters();

	public abstract String getLabelKey();

	public abstract String getName();

	public Map<String, Object> getProperties() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"label", _language.get(_themeDisplay.getLocale(), getLabelKey())
		).put(
			"name", getName()
		).put(
			"searchURL",
			_themeDisplay.getPortalURL() +
				"/o/headless-cmp/v1.0/task-assignees/?" +
					getAdditionalAPIURLParameters()
		).put(
			"triggerClassName", "form-control"
		).put(
			"usersOnly", getUsersOnly()
		).put(
			"value", getValueJSONObject()
		).put(
			"visible", true
		).build();
	}

	public abstract boolean getUsersOnly();

	public abstract JSONObject getValueJSONObject() throws Exception;

	protected JSONObject getValueJSONObject(String objectFieldName)
		throws Exception {

		User user = _userLocalService.fetchUser(
			MapUtil.getLong(objectEntry.getValues(), objectFieldName));

		if (user == null) {
			return null;
		}

		return JSONUtil.put(
			"externalReferenceCode", user.getExternalReferenceCode()
		).put(
			"name", user.getFullName()
		).put(
			"portrait", user.getPortraitURL(_themeDisplay)
		).put(
			"type", "User"
		);
	}

	protected final ObjectEntry objectEntry;

	private final Language _language;
	private final ThemeDisplay _themeDisplay;
	private final UserLocalService _userLocalService;

}