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

package com.liferay.portal.editor.configuration;

import com.liferay.portal.kernel.editor.configuration.EditorConfigTransformer;
import com.liferay.portal.kernel.editor.configuration.EditorConfiguration;
import com.liferay.portal.kernel.editor.configuration.EditorConfigurationFactory;
import com.liferay.portal.kernel.editor.configuration.EditorOptions;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.RequestBackedPortletURLFactory;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.Map;

/**
 * @author Sergio González
 */
public class EditorConfigurationFactoryImpl
	implements EditorConfigurationFactory {

	@Override
	public EditorConfiguration getEditorConfiguration(
		String portletName, String editorConfigKey, String editorName,
		Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		JSONObject configJSONObject = _editorConfigProvider.getConfigJSONObject(
			portletName, editorConfigKey, editorName,
			inputEditorTaglibAttributes, themeDisplay,
			requestBackedPortletURLFactory);

		EditorOptions editorOptions = _editorOptionsProvider.getEditorOptions(
			portletName, editorConfigKey, editorName,
			inputEditorTaglibAttributes, themeDisplay,
			requestBackedPortletURLFactory);

		EditorConfigTransformer editorConfigTransformer =
			_editorConfigTransformerServiceTrackerMap.getService(editorName);

		if (editorConfigTransformer != null) {
			editorConfigTransformer.transform(
				editorOptions, inputEditorTaglibAttributes, configJSONObject,
				themeDisplay, requestBackedPortletURLFactory);
		}

		return new EditorConfigurationImpl(configJSONObject, editorOptions);
	}

	public void setEditorConfigProvider(
		EditorConfigProvider editorConfigProvider) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_editorConfigProvider = editorConfigProvider;
	}

	public void setEditorOptionsProvider(
		EditorOptionsProvider editorOptionsProvider) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_editorOptionsProvider = editorOptionsProvider;
	}

	private static EditorConfigProvider _editorConfigProvider;
	private static final ServiceTrackerMap<String, EditorConfigTransformer>
		_editorConfigTransformerServiceTrackerMap =
			ServiceTrackerCollections.openSingleValueMap(
				EditorConfigTransformer.class, "editor.name");
	private static EditorOptionsProvider _editorOptionsProvider;

}