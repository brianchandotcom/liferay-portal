/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.editor.configuration;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.Map;

/**
 * Provides an interface for adapting an {@link EditorOptionsContributor}'s high
 * level options to a specific configuration JSON object (as used by the {@link
 * EditorConfigContributor}).
 *
 * <p>
 * Each editor should typically create its own Editor Config Transformer for the
 * editor's configuration JSON objects, which can differ from those of other
 * editors.
 * </p>
 *
 * <p>
 * Implementations must be OSGi components that are registered in the OSGi
 * Registry.
 * </p>
 *
 * <p>
 * Editor Config Transformers are targeted to specific editors based on the
 * <code>editor.name</code> OSGi property.
 * </p>
 *
 * <p>
 * In case there's more than one Editor Config Transformer for an editor, the
 * one with the highest service rank is used.
 * </p>
 *
 * @author Sergio González
 */
public interface EditorConfigTransformer {

	/**
	 * Transforms the editor options in configuration that the editor can
	 * handle, by populating the configuration JSON object.
	 *
	 * @param editorOptions the {@link EditorOptions} object composed of the
	 *        options set by {@link EditorOptionsContributor} modules
	 * @param inputEditorTaglibAttributes the attributes specified to the input
	 *        taglib tag that renders the editor
	 * @param configJSONObject the JSON object composed of the entire
	 *        configuration set by {@link EditorConfigContributor} modules
	 * @param themeDisplay the theme display
	 */
	public void transform(
		EditorOptions editorOptions,
		Map<String, Object> inputEditorTaglibAttributes,
		JSONObject configJSONObject, ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory);

}