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

package com.liferay.announcements.web.internal.display.context.helper;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;

import java.util.Locale;

/**
 * @author Hong Vo
 */
public class GroupNameUtil {

	public static String getGroupNameWithType(Group group, Locale locale)
		throws PortalException {

		if (group.isOrganization()) {
			return String.format(
				"%s(%s)", group.getDescriptiveName(locale),
				LanguageUtil.get(locale, "organization"));
		}

		return group.getDescriptiveName(locale);
	}

}