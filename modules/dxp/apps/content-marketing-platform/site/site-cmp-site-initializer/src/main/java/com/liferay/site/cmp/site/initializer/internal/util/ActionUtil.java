/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.util;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author Carolina Barbosa
 */
public class ActionUtil {

	public static String getAddProjectURL(
		ObjectDefinition objectDefinition, ThemeDisplay themeDisplay) {

		return StringBundler.concat(
			themeDisplay.getPortalURL(), themeDisplay.getPathMain(),
			GroupConstants.CMS_FRIENDLY_URL, "/add_project?objectDefinitionId=",
			objectDefinition.getObjectDefinitionId(), "&plid=",
			themeDisplay.getPlid(), "&redirect=", themeDisplay.getURLCurrent());
	}

	public static String getAddTaskURL(
		long groupId, ObjectDefinition objectDefinition, long objectEntryId,
		String redirect, ThemeDisplay themeDisplay) {

		return StringBundler.concat(
			themeDisplay.getPortalURL(), themeDisplay.getPathMain(),
			GroupConstants.CMS_FRIENDLY_URL, "/add_task?objectDefinitionId=",
			objectDefinition.getObjectDefinitionId(), "&plid=",
			themeDisplay.getPlid(), "&projectGroupId=", groupId, "&projectId=",
			objectEntryId, "&redirect=", redirect);
	}

	public static String getAddTaskURL(
		long groupId, ObjectDefinition objectDefinition, long objectEntryId,
		ThemeDisplay themeDisplay) {

		return getAddTaskURL(
			groupId, objectDefinition, objectEntryId,
			themeDisplay.getURLCurrent(), themeDisplay);
	}

	public static String getBaseEditProjectURL(
		ObjectDefinition objectDefinition, ThemeDisplay themeDisplay) {

		return StringBundler.concat(
			themeDisplay.getPathFriendlyURLPublic(),
			GroupConstants.CMS_FRIENDLY_URL, "/e/edit-project/",
			PortalUtil.getClassNameId(objectDefinition.getClassName()),
			StringPool.SLASH);
	}

	public static String getBaseEditTaskURL(
		ObjectDefinition objectDefinition, ThemeDisplay themeDisplay) {

		return StringBundler.concat(
			themeDisplay.getPathFriendlyURLPublic(),
			GroupConstants.CMS_FRIENDLY_URL, "/e/edit-task/",
			PortalUtil.getClassNameId(objectDefinition.getClassName()),
			StringPool.SLASH);
	}

	public static String getBaseViewProjectURL(
		ObjectDefinition objectDefinition, ThemeDisplay themeDisplay) {

		return StringBundler.concat(
			themeDisplay.getPathFriendlyURLPublic(),
			GroupConstants.CMS_FRIENDLY_URL, "/e/project/",
			PortalUtil.getClassNameId(objectDefinition.getClassName()),
			StringPool.SLASH);
	}

	public static String getBaseViewTaskURL(
		ObjectDefinition objectDefinition, ThemeDisplay themeDisplay) {

		return StringBundler.concat(
			themeDisplay.getPathFriendlyURLPublic(),
			GroupConstants.CMS_FRIENDLY_URL, "/e/task/",
			PortalUtil.getClassNameId(objectDefinition.getClassName()),
			StringPool.SLASH);
	}

	public static String getProjectsURL(ThemeDisplay themeDisplay) {
		return StringBundler.concat(
			themeDisplay.getPathFriendlyURLPublic(),
			GroupConstants.CMS_FRIENDLY_URL, "/projects");
	}

}