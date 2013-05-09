/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.Locale;

/**
 * @author Roberto Díaz
 */
public class ScopeUtil {

	public static String getName(long groupId, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		String name = null;

		Locale locale = themeDisplay.getLocale();

		if (groupId == themeDisplay.getScopeGroupId()) {
			StringBundler sb = new StringBundler(5);

			sb.append(LanguageUtil.get(locale, "current-site"));
			sb.append(StringPool.SPACE);
			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(HtmlUtil.escape(group.getDescriptiveName(locale)));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			name = sb.toString();
		}
		else if (group.isLayout() &&
				 (group.getClassPK() == themeDisplay.getPlid())) {

			StringBundler sb = new StringBundler(5);

			sb.append(LanguageUtil.get(locale, "current-page"));
			sb.append(StringPool.SPACE);
			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(HtmlUtil.escape(group.getDescriptiveName(locale)));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			name = sb.toString();
		}
		else if (group.isLayoutPrototype()) {
			name = LanguageUtil.get(locale, "default");
		}
		else {
			name = HtmlUtil.escape(group.getDescriptiveName(locale));
		}

		return name;
	}

	public static String getType(long groupId, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		String type = "site";

		if (groupId == themeDisplay.getScopeGroupId()) {
			type = "current-site";
		}
		else if (groupId == themeDisplay.getCompanyGroupId()) {
			type = "global";
		}
		else if (group.isLayout()) {
			type = "page";
		}
		else {
			Group scopeGroup = themeDisplay.getScopeGroup();

			if (scopeGroup.hasAncestor(groupId)) {
				type = "parent-site";
			}
			else if (group.hasAncestor(scopeGroup.getGroupId())) {
				type = "child-site";
			}
		}

		return type;
	}

}