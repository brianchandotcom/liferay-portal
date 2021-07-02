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

package com.liferay.change.tracking.web.internal.display.context;

import com.liferay.change.tracking.web.internal.display.CTDisplayRendererRegistry;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.List;
import java.util.Set;

/**
 * @author Samuel Trong Tran
 */
public class DisplayContextUtil {

	public static JSONObject getTypeNamesJSONObject(
		Set<Long> classNameIds,
		CTDisplayRendererRegistry ctDisplayRendererRegistry,
		ThemeDisplay themeDisplay) {

		JSONObject typeNamesJSONObject = JSONFactoryUtil.createJSONObject();

		for (long classNameId : classNameIds) {
			String typeName = ctDisplayRendererRegistry.getTypeName(
				themeDisplay.getLocale(), classNameId);

			typeNamesJSONObject.put(String.valueOf(classNameId), typeName);
		}

		return typeNamesJSONObject;
	}

	public static JSONObject getUserInfoJSONObject(
		Predicate innerJoinPredicate, Table<?> innerJoinTable,
		ThemeDisplay themeDisplay, UserLocalService userLocalService,
		Predicate wherePredicate) {

		JSONObject userInfoJSONObject = JSONFactoryUtil.createJSONObject();

		List<User> users = userLocalService.dslQuery(
			DSLQueryFactoryUtil.selectDistinct(
				UserTable.INSTANCE
			).from(
				UserTable.INSTANCE
			).innerJoinON(
				innerJoinTable, innerJoinPredicate
			).where(
				wherePredicate
			));

		for (User user : users) {
			String portraitURL = StringPool.BLANK;

			if (user.getPortraitId() > 0) {
				try {
					portraitURL = user.getPortraitURL(themeDisplay);
				}
				catch (PortalException portalException) {
					_log.error(portalException, portalException);
				}
			}

			userInfoJSONObject.put(
				String.valueOf(user.getUserId()),
				JSONUtil.put(
					"portraitURL", portraitURL
				).put(
					"userName", user.getFullName()
				));
		}

		return userInfoJSONObject;
	}

	private DisplayContextUtil() {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DisplayContextUtil.class);

}