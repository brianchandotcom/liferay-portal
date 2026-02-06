/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.display.context;

import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Collections;
import java.util.Map;

/**
 * @author Igor Franca
 */
public class ViewProjectInfoSummarySectionDisplayContext
	extends BaseInfoSummarySectionDisplayContext {

	public ViewProjectInfoSummarySectionDisplayContext(
		ObjectEntry objectEntry, ThemeDisplay themeDisplay) {

		super(objectEntry, themeDisplay);
	}

	public Map<String, Object> getProperties() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"manager",
			_getUserInfoMap(
				GetterUtil.getLong(
					getFieldValue("r_userToCMPProjectManager_userId")))
		).put(
			"projectId", objectEntry.getObjectEntryId()
		).put(
			"sponsor",
			_getUserInfoMap(
				GetterUtil.getLong(
					getFieldValue("r_userToCMPProjectSponsor_userId")))
		).putAll(
			super.getProperties()
		).build();
	}

	private Map<String, String> _getUserInfoMap(long userId) throws Exception {
		User user = UserLocalServiceUtil.fetchUser(userId);

		if (user == null) {
			return Collections.emptyMap();
		}

		return HashMapBuilder.put(
			"image", user.getPortraitURL(themeDisplay)
		).put(
			"name", user.getFullName()
		).build();
	}

}