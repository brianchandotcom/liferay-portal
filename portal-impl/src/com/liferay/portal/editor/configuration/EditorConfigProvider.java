/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.editor.configuration;

import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.Map;

/**
 * @author Sergio González
 */
public class EditorConfigProvider
	extends BaseEditorProvider<EditorConfigContributor> {

	public EditorConfigProvider() {
		super(EditorConfigContributor.class);
	}

	public JSONObject getConfigJSONObject(
		String portletName, String editorConfigKey, String editorName,
		Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		JSONObject configJSONObject = JSONFactoryUtil.createJSONObject();

		visitEditorContributors(
			editorConfigContributor ->
				editorConfigContributor.populateConfigJSONObject(
					configJSONObject, inputEditorTaglibAttributes, themeDisplay,
					requestBackedPortletURLFactory),
			portletName, editorConfigKey, editorName);

		return configJSONObject;
	}

}