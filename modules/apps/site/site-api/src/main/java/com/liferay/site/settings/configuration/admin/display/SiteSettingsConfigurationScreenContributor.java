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

package com.liferay.site.settings.configuration.admin.display;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eudaldo Alonso
 */
public interface SiteSettingsConfigurationScreenContributor {

	public String getCategoryKey();

	public String getJspPath();

	public String getKey();

	public default String getName(Locale locale) {
		return getKey();
	}

	public default String getSaveMVCActionCommandName() {
		return null;
	}

	public ServletContext getServletContext();

	public default boolean isVisible(Group group) {
		return true;
	}

	public default void setAttributes(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(
			httpServletRequest, "groupId", themeDisplay.getSiteGroupId());

		Group group = null;

		if (groupId > 0) {
			group = GroupLocalServiceUtil.fetchGroup(groupId);
		}

		if (group == null) {
			httpServletRequest.setAttribute("site.group", null);
			httpServletRequest.setAttribute("site.liveGroup", null);
			httpServletRequest.setAttribute("site.liveGroupId", 0);
			httpServletRequest.setAttribute("site.stagingGroup", null);
			httpServletRequest.setAttribute("site.stagingGroupId", 0);

			return;
		}

		httpServletRequest.setAttribute("site.group", group);

		Group liveGroup = null;

		Group stagingGroup = null;

		if (group.isStagingGroup()) {
			liveGroup = group.getLiveGroup();

			stagingGroup = group;
		}
		else {
			liveGroup = group;

			if (group.hasStagingGroup()) {
				stagingGroup = group.getStagingGroup();
			}
		}

		httpServletRequest.setAttribute(
			"site.groupTypeSettings", liveGroup.getTypeSettingsProperties());
		httpServletRequest.setAttribute("site.liveGroup", liveGroup);
		httpServletRequest.setAttribute(
			"site.liveGroupId", liveGroup.getGroupId());
		httpServletRequest.setAttribute("site.stagingGroup", stagingGroup);

		long stagingGroupId = 0;

		if (stagingGroup != null) {
			stagingGroupId = stagingGroup.getGroupId();
		}

		httpServletRequest.setAttribute("site.stagingGroupId", stagingGroupId);
	}

}