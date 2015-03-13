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

package com.liferay.portlet.editor.config;

import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.theme.ThemeDisplay;

/**
 * @author Sergio González
 */
public class PortletEditorConfigFactoryUtil {

	public static PortletEditorConfig getPortletEditorConfig(
		String portletName, String editorConfigKey, ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {

		return getPortletEditorConfigFactory().getPortletEditorConfig(
			portletName, editorConfigKey, themeDisplay, liferayPortletResponse);
	}

	public static PortletEditorConfigFactory getPortletEditorConfigFactory() {
		PortalRuntimePermission.checkGetBeanProperty(
			PortletEditorConfigFactoryUtil.class);

		return _portletEditorConfigFactory;
	}

	public void setPortletEditorConfigFactory(
		PortletEditorConfigFactory portletEditorConfigFactory) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_portletEditorConfigFactory = portletEditorConfigFactory;
	}

	private static PortletEditorConfigFactory _portletEditorConfigFactory;

}