/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.mobile.device.rulegroup.action.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.mobiledevicerules.model.MDRAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Edward Han
 */
public class SiteRedirectActionHandler extends BaseRedirectActionHandler {

	public static String getHandlerType() {
		return SiteRedirectActionHandler.class.getName();
	}

	public Collection<String> getPropertyNames() {
		return _propertyNames;
	}

	public String getType() {
		return getHandlerType();
	}

	public void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	public void setLayoutLocalService(LayoutLocalService layoutLocalService) {
		_layoutLocalService = layoutLocalService;
	}

	protected String getURL(
			MDRAction action, HttpServletRequest request,
			HttpServletResponse response)
		throws PortalException, SystemException {

		UnicodeProperties typeSettingsProperties =
			action.getTypeSettingsProperties();

		long layoutId = GetterUtil.get(
			typeSettingsProperties.getProperty(LAYOUT_ID), 0L);

		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(
				WebKeys.THEME_DISPLAY);

		long currentLayoutId = themeDisplay.getLayout().getLayoutId();

		if (currentLayoutId == layoutId) {
			return null;
		}

		Layout layout = _layoutLocalService.fetchLayout(layoutId);

		long groupId = GetterUtil.get(
			typeSettingsProperties.getProperty(GROUP_ID), 0L);

		if ((layout != null) && (layout.getGroupId() != groupId)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"layoutId " + layoutId +
						" does not belong to group with ID: " + groupId +
						". Using default public page");
			}

			layout = null;
		}

		if (layout == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Using default public group layout, invalid plid: " +
						layoutId);
			}

			long currentGroupId = themeDisplay.getLayout().getGroupId();

			Group group = null;

			if (currentGroupId != groupId) {
				group = _groupLocalService.fetchGroup(groupId);
			}

			if (group == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("Group does not exist: " + groupId);
				}

				return null;
			}

			layout = LayoutLocalServiceUtil.fetchLayout(
				group.getDefaultPublicPlid());
		}

		if (layout != null) {
			return PortalUtil.getLayoutURL(layout, themeDisplay);
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to resolve default layout");
			}

			return null;
		}
	}

	private static final String GROUP_ID = "groupId";
	private static final String LAYOUT_ID = "layoutId";

	private static Log _log = LogFactoryUtil.getLog(
		SiteRedirectActionHandler.class);

	private static Collection<String> _propertyNames;

	private GroupLocalService _groupLocalService;
	private LayoutLocalService _layoutLocalService;

	static {
		_propertyNames = new ArrayList<String>(2);

		_propertyNames.add(GROUP_ID);
		_propertyNames.add(LAYOUT_ID);

		_propertyNames = Collections.unmodifiableCollection(_propertyNames);
	}
}