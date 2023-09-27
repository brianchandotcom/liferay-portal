/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.editor.configuration;

import com.liferay.portal.kernel.editor.configuration.EditorOptions;
import com.liferay.portal.kernel.editor.configuration.EditorOptionsContributor;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.Map;

/**
 * @author Sergio González
 */
public class EditorOptionsProvider
	extends BaseEditorProvider<EditorOptionsContributor> {

	public EditorOptionsProvider() {
		super(EditorOptionsContributor.class);
	}

	public EditorOptions getEditorOptions(
		String portletName, String editorConfigKey, String editorName,
		Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		EditorOptions editorOptions = new EditorOptions();

		visitEditorContributors(
			editorOptionsContributor ->
				editorOptionsContributor.populateEditorOptions(
					editorOptions, inputEditorTaglibAttributes, themeDisplay,
					requestBackedPortletURLFactory),
			portletName, editorConfigKey, editorName);

		return editorOptions;
	}

}