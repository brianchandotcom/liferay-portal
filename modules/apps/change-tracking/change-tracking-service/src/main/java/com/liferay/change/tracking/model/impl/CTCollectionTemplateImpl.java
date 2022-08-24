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

package com.liferay.change.tracking.model.impl;

import com.liferay.json.storage.service.JSONStorageEntryLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.time.Instant;
import java.time.LocalDate;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CTCollectionTemplateImpl extends CTCollectionTemplateBaseImpl {

	public JSONObject getJSONObject() {
		return JSONStorageEntryLocalServiceUtil.getJSONObject(
			ClassNameLocalServiceUtil.getClassNameId(getModelClassName()),
			getCtCollectionTemplateId());
	}

	public String getParsedPublicationName() {
		JSONObject jsonObject = getJSONObject();

		String name = String.valueOf(jsonObject.get("name"));

		return _parseTemplateString(name);
	}

	public String getUserName() {
		User user = UserLocalServiceUtil.fetchUser(getUserId());

		if (user == null) {
			return StringPool.BLANK;
		}

		return user.getFullName();
	}

	private String _parseTemplateString(String s) {
		if (s.contains(StringPool.DOLLAR_AND_OPEN_CURLY_BRACE)) {
			StringBundler sb = new StringBundler();

			int current = 0;

			while (current < s.length()) {
				int openBrace = s.indexOf(
					StringPool.DOLLAR_AND_OPEN_CURLY_BRACE, current);

				int closeBrace = s.indexOf(
					StringPool.CLOSE_CURLY_BRACE, openBrace);

				sb.append(s.substring(current, openBrace));

				String template = s.substring(openBrace, closeBrace + 1);

				sb.append(_templateMap.get(template));

				current = closeBrace + 1;
			}

			return sb.toString();
		}

		return s;
	}

	private final Map<String, String> _templateMap = HashMapBuilder.put(
		"${CURRENT_USERNAME}",
		() -> {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

			User user = themeDisplay.getUser();

			return user.getScreenName();
		}
	).put(
		"${RANDOM_NUMBER}",
		() -> {
			Instant now = Instant.now();

			return String.valueOf(now.getEpochSecond());
		}
	).put(
		"${TEMPLATE_CREATOR}", getUserName()
	).put(
		"${TODAY_DATE}", String.valueOf(LocalDate.now())
	).build();

}