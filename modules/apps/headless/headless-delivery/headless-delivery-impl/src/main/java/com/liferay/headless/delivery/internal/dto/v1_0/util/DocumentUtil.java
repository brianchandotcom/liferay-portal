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

package com.liferay.headless.delivery.internal.dto.v1_0.util;

import com.liferay.portal.events.ServicePreAction;
import com.liferay.portal.events.ThemeServicePreAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jorge García Jiménez
 */
public class DocumentUtil {

	public static void addThemeDisplay(
			long groupId, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		ServicePreAction servicePreAction = new ServicePreAction();

		servicePreAction.servicePre(
			httpServletRequest, httpServletResponse, false);

		ThemeServicePreAction themeServicePreAction =
			new ThemeServicePreAction();

		themeServicePreAction.run(httpServletRequest, httpServletResponse);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		themeDisplay.setScopeGroupId(groupId);
		themeDisplay.setSiteGroupId(groupId);
	}

}