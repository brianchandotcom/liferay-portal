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

package com.liferay.application.list.taglib.internal.util;

import com.liferay.application.list.GroupProvider;
import com.liferay.application.list.PanelApp;
import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.SessionClicks;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class PanelCategoryUtil {

	public static Group getGroup(HttpServletRequest httpServletRequest) {
		GroupProvider groupProvider =
			(GroupProvider)httpServletRequest.getAttribute(
				ApplicationListWebKeys.GROUP_PROVIDER);

		if (groupProvider != null) {
			Group group = groupProvider.getGroup(httpServletRequest);

			if (group != null) {
				return group;
			}
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return themeDisplay.getScopeGroup();
	}

	public static String getId(PanelCategory panelCategory) {
		String id = StringUtil.replace(
			panelCategory.getKey(), CharPool.PERIOD, CharPool.UNDERLINE);

		return "panel-manage-" + id;
	}

	public static int getNotificationsCount(
		HttpServletRequest httpServletRequest, PanelCategory panelCategory,
		PanelCategoryHelper panelCategoryHelper) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return panelCategoryHelper.getNotificationsCount(
			panelCategory.getKey(), themeDisplay.getPermissionChecker(),
			getGroup(httpServletRequest), themeDisplay.getUser());
	}

	public static List<PanelApp> getPanelApps(
		HttpServletRequest httpServletRequest,
		PanelAppRegistry panelAppRegistry, PanelCategory panelCategory) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return panelAppRegistry.getPanelApps(
			panelCategory, themeDisplay.getPermissionChecker(),
			getGroup(httpServletRequest));
	}

	public static boolean isActive(
		HttpServletRequest httpServletRequest, List<PanelApp> panelApps,
		PanelCategory panelCategory, List<PanelCategory> panelCategories,
		PanelCategoryHelper panelCategoryHelper) {

		if (panelCategories.size() == 1) {
			return true;
		}

		if (panelCategory.isPersistState()) {
			String state = SessionClicks.get(
				httpServletRequest,
				PanelCategory.class.getName() + getId(panelCategory), "closed");

			if (Objects.equals(state, "open")) {
				return true;
			}
		}

		if (panelApps.isEmpty()) {
			return false;
		}

		return panelCategory.isActive(
			httpServletRequest, panelCategoryHelper,
			getGroup(httpServletRequest));
	}

	public static boolean isHeaderActive(
		HttpServletRequest httpServletRequest, PanelCategory panelCategory,
		PanelCategoryHelper panelCategoryHelper) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String portletId = themeDisplay.getPpid();

		if (Validator.isNotNull(portletId) &&
			panelCategoryHelper.containsPortlet(
				portletId, panelCategory.getKey())) {

			return true;
		}

		return false;
	}

}