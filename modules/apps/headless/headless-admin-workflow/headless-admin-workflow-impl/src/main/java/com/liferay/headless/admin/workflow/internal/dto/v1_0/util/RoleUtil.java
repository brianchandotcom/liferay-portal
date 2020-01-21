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
				description_i18n = LocalizedMapUtil.getLocalizedMap(
					acceptAllLanguages, role.getDescriptionMap());
				id = role.getRoleId();
				name = role.getTitle(locale);
				name_i18n = LocalizedMapUtil.getLocalizedMap(
					acceptAllLanguages, role.getTitleMap());
				roleType = role.getTypeLabel();
			}
		};
	}

}