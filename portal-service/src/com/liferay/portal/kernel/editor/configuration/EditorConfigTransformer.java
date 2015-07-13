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

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.PortletURLBuilder;

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
	 * @param themeDisplay the theme display
	 * @param portletURLBuilder a portlet URL builder.
	 * @param configJSONObject the JSON object composed of the entire
	 *        configuration set by {@link EditorConfigContributor} modules
	 */
	public void transform(
		EditorOptions editorOptions,
		Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay, PortletURLBuilder portletURLBuilder,
		JSONObject configJSONObject);

}