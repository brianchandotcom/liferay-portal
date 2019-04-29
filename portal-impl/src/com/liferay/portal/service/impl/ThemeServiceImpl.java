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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.service.base.ThemeServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ThemeServiceImpl extends ThemeServiceBaseImpl {

	@Override
	public List<Theme> getThemes(long companyId) {
		return themeLocalService.getThemes(companyId);
	}

	@Override
	public JSONArray getWARThemes() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<Theme> themes = themeLocalService.getWARThemes();

		for (Theme theme : themes) {
			JSONObject jsonObject = JSONUtil.put(
				"servlet_context_name", theme.getServletContextName()
			).put(
				"theme_id", theme.getThemeId()
			).put(
				"theme_name", theme.getName()
			);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

}