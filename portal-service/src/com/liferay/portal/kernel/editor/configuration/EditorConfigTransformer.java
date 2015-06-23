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
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.Map;

/**
 * Provides an interface that is responsible for adapting the high level options
 * provided by {@link EditorOptionsContributor} to specific configuration JSON
 * object (as used by {@link EditorConfigContributor}).
 *
 * <p>
 * In general, each editor should create its own Editor Config Transformer
 * because the configuration JSON object that will need to be created is usually
 * different based on the editor.
 * </p>
 *
 * <p>
 * Implementations of this class must be OSGi components that are registered in
 * the OSGi Registry.
 * </p>
 *
 * <p>
 * The Editor Config Transformers will be targeted to specific editors based on
 * the <code>editor.name</code> OSGi property.
 * </p>
 *
 * <p>
 * In case there's more than one editor config transformer for a particular
 * editor, the one with the higher service rank will be used.
 * </p>
 *
 * @author Sergio González
 */
public interface EditorConfigTransformer {

	/**
	 * Transforms the editor options in configuration that the editor can handle
	 * by populating the configuration JSON object.
	 *
	 * @param editorOptions the EditorOptions object containing the
	 *        options set by previous {@link EditorOptionsContributor}
	 *        modules
	 * @param inputEditorTaglibAttributes the attributes specified to the input
	 *        taglib tag that renders the editor
	 * @param themeDisplay the theme display
	 * @param liferayPortletResponse the Liferay portlet response (optionally
	 *        <code>null</code>). Only use the response to generate portlet
	 *        URLs.
	 * @param configJSONObject the JSON object containing the entire
	 *        configuration set by previous {@link EditorConfigContributor}
	 *        modules
	 */
	public void transform(
		EditorOptions editorOptions,
		Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse,
		JSONObject configJSONObject);

}