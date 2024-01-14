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
				setAvailableLanguages(
					() -> LocaleUtil.toW3cLanguageIds(
						role.getAvailableLanguageIds()));
				setCreator(() -> CreatorUtil.toCreator(portal, user));
				setDateCreated(role::getCreateDate);
				setDateModified(role::getModifiedDate);
				setDescription(() -> role.getDescription(locale));
				setDescription_i18n(
					() -> LocalizedMapUtil.getI18nMap(
						acceptAllLanguages, role.getDescriptionMap()));
				setId(role::getRoleId);
				setName(() -> role.getTitle(locale));
				setName_i18n(
					() -> LocalizedMapUtil.getI18nMap(
						acceptAllLanguages, role.getTitleMap()));
				setRoleType(role::getTypeLabel);
			}
		};
	}

}