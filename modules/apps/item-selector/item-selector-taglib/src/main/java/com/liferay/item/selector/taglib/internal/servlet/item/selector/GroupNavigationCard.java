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

package com.liferay.item.selector.taglib.internal.servlet.item.selector;

import com.liferay.frontend.taglib.clay.servlet.taglib.NavigationCard;
import com.liferay.item.selector.taglib.internal.display.context.GroupSelectorDisplayContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class GroupNavigationCard implements NavigationCard {

	public GroupNavigationCard(
		Group group, GroupSelectorDisplayContext groupSelectorDisplayContext,
		HttpServletRequest httpServletRequest) {

		_group = group;
		_groupSelectorDisplayContext = groupSelectorDisplayContext;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public String getHref() {
		return _groupSelectorDisplayContext.getViewGroupURL(_group);
	}

	@Override
	public String getIcon() {
		return _groupSelectorDisplayContext.getGroupItemSelectorIcon();
	}

	@Override
	public String getImageSrc() {
		return _group.getLogoURL(_themeDisplay, false);
	}

	@Override
	public String getTitle() {
		try {
			return _group.getDescriptiveName(_themeDisplay.getLocale());
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return _group.getName(_themeDisplay.getLocale());
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GroupNavigationCard.class);

	private final Group _group;
	private final GroupSelectorDisplayContext _groupSelectorDisplayContext;
	private final ThemeDisplay _themeDisplay;

}