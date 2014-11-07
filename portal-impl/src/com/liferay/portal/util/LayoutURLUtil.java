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

package com.liferay.portal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.persistence.LayoutUtil;
import com.liferay.portal.theme.ThemeDisplay;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Shuyang Zhou
 */
public class LayoutURLUtil {

	public static String getLayoutURL(
		Layout layout, ServiceContext serviceContext) {

		HttpServletRequest request = serviceContext.getRequest();

		if (request == null) {
			return StringPool.BLANK;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			com.liferay.portal.kernel.util.WebKeys.THEME_DISPLAY);

		try {
			return PortalUtil.getLayoutURL(layout, themeDisplay);
		}
		catch (Exception e) {
			return StringPool.BLANK;
		}
	}

	public static String getLayoutURL(
			long groupId, String portletId, ServiceContext serviceContext)
		throws PortalException {

		long plid = serviceContext.getPlid();

		long controlPanelPlid = PortalUtil.getControlPanelPlid(
			serviceContext.getCompanyId());

		if (plid == controlPanelPlid) {
			plid = PortalUtil.getPlidFromPortletId(groupId, portletId);
		}

		String layoutURL = StringPool.BLANK;

		if (plid != LayoutConstants.DEFAULT_PLID) {
			Layout layout = LayoutUtil.findByPrimaryKey(plid);

			layoutURL = getLayoutURL(layout, serviceContext);
		}

		return layoutURL;
	}

}