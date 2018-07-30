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

package com.liferay.portal.kernel.editor.configuration;

import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.Map;

/**
 * @author Sergio González
 */
public class EditorConfigurationFactoryUtil {

	public static EditorConfiguration getEditorConfiguration(
		String portletName, String editorConfigKey, String editorName,
		Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		return getEditorConfigurationFactory().getEditorConfiguration(
			portletName, editorConfigKey, editorName,
			inputEditorTaglibAttributes, themeDisplay,
			requestBackedPortletURLFactory);
	}

	public static EditorConfigurationFactory getEditorConfigurationFactory() {
		return _editorConfigurationFactory;
	}

	public void setEditorConfigurationFactory(
		EditorConfigurationFactory editorConfigurationFactory) {

		_editorConfigurationFactory = editorConfigurationFactory;
	}

	private static EditorConfigurationFactory _editorConfigurationFactory;

}