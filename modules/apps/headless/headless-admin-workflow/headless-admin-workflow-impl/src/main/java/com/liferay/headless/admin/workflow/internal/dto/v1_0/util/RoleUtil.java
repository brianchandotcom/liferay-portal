/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.workflow.internal.dto.v1_0.util;

import com.liferay.headless.admin.workflow.dto.v1_0.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Locale;

/**
 * @author Javier de Arcos
 */
public class RoleUtil {

	public static Role toRole(
		boolean acceptAllLanguages, Locale locale, Portal portal,
		com.liferay.portal.kernel.model.Role role, User user) {

		return new Role() {
			{
				availableLanguages = LocaleUtil.toW3cLanguageIds(
					role.getAvailableLanguageIds());
				creator = CreatorUtil.toCreator(portal, user);
				dateCreated = role.getCreateDate();
				dateModified = role.getModifiedDate();
				description = role.getDescription(locale);
				description_i18n = LocalizedMapUtil.getI18nMap(
					acceptAllLanguages, role.getDescriptionMap());
				id = role.getRoleId();
				name = role.getTitle(locale);
				name_i18n = LocalizedMapUtil.getI18nMap(
					acceptAllLanguages, role.getTitleMap());
				roleType = role.getTypeLabel();
			}
		};
	}

}