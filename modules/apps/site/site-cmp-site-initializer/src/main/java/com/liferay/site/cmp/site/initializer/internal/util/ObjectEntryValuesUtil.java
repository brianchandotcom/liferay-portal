/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.util;

import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Carolina Barbosa
 */
public class ObjectEntryValuesUtil {

	public static Map<String, String> getAssigneeFieldValue(
			ObjectEntry objectEntry, ThemeDisplay themeDisplay)
		throws Exception {

		Map<String, Serializable> values = objectEntry.getValues();

		Map<String, Long> assigneeMap = (Map<String, Long>)values.get(
			"assignTo");

		if (assigneeMap == null) {
			return null;
		}

		ClassName className = ClassNameLocalServiceUtil.fetchClassName(
			GetterUtil.getLong(assigneeMap.get("classNameId")));

		if (className == null) {
			return null;
		}

		if (StringUtil.equals(className.getClassName(), Role.class.getName())) {
			Role role = RoleLocalServiceUtil.fetchRole(
				assigneeMap.get("classPK"));

			return HashMapBuilder.put(
				"externalReferenceCode", role.getExternalReferenceCode()
			).put(
				"name", role.getName()
			).put(
				"type", "role"
			).build();
		}

		User user = UserLocalServiceUtil.fetchUser(assigneeMap.get("classPK"));

		return HashMapBuilder.put(
			"externalReferenceCode", user.getExternalReferenceCode()
		).put(
			"image", user.getPortraitURL(themeDisplay)
		).put(
			"name", user.getFullName()
		).put(
			"type", "user"
		).build();
	}

}