/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.display.context;

import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Pedro Leite
 */
public class ViewTaskInfoSummarySectionDisplayContext
	extends BaseInfoSummarySectionDisplayContext {

	public ViewTaskInfoSummarySectionDisplayContext(
		ClassNameLocalService classNameLocalService, Language language,
		ListTypeEntryLocalService listTypeEntryLocalService,
		ObjectEntry objectEntry,
		ObjectFieldLocalService objectFieldLocalService,
		RoleLocalService roleLocalService, UserLocalService userLocalService,
		ThemeDisplay themeDisplay) {

		super(
			listTypeEntryLocalService, objectEntry, objectFieldLocalService,
			themeDisplay);

		this.classNameLocalService = classNameLocalService;
		this.language = language;
		this.roleLocalService = roleLocalService;
		this.userLocalService = userLocalService;
	}

	@Override
	public Map<String, Object> getProperties() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"assignTo",
			() -> {
				Map<String, Serializable> values = objectEntry.getValues();

				Map<String, Long> assignTo = (Map<String, Long>)values.get(
					"assignTo");

				ClassName className = classNameLocalService.fetchClassName(
					GetterUtil.getLong(assignTo.get("classNameId")));

				if (className == null) {
					return null;
				}

				if (StringUtil.equals(
						className.getClassName(), Role.class.getName())) {

					Role role = roleLocalService.fetchRole(
						assignTo.get("classPK"));

					return HashMapBuilder.<String, Object>put(
						"externalReferenceCode", role.getExternalReferenceCode()
					).put(
						"name", role.getName()
					).put(
						"type", "role"
					).build();
				}

				User user = userLocalService.fetchUser(assignTo.get("classPK"));

				return HashMapBuilder.<String, Object>put(
					"externalReferenceCode", user.getExternalReferenceCode()
				).put(
					"image", user.getPortraitURL(themeDisplay)
				).put(
					"name", user.getFullName()
				).put(
					"type", "user"
				).build();
			}
		).put(
			"taskId", objectEntry.getObjectEntryId()
		).putAll(
			super.getProperties()
		).build();
	}

	protected final ClassNameLocalService classNameLocalService;
	protected final Language language;
	protected final RoleLocalService roleLocalService;
	protected final UserLocalService userLocalService;

}