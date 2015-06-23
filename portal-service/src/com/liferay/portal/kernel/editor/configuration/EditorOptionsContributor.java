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

import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.Map;

/**
 * Provides an interface responsible for setting the options of the
 * editor. Editor configuration can be set using
 * {@link EditorConfigContributor}.
 *
 * <p>
 * This interface is intended to easily configure options at a higher level than
 * using {@link EditorConfigContributor}. The former requires knowledge about
 * the editor internals, while the latter is more versatile in terms of
 * customization.
 * </p>
 *
 * <p>
 * The options specified through this interface are not tight to any particular
 * editor implementation. These high level options will be transformed to a low
 * level configuration object by {@link EditorConfigTransformer}.
 * </p>
 *
 * <p>
 * Implementations of this class must be OSGi components that are registered in
 * the OSGi Registry.
 * </p>
 *
 * <p>
 * The options can be targeted for specific editors, based on three different
 * criteria: portlet name, editor config key, and editor name.
 * These criteria can be defined as OSGi properties with the following names:
 * </p>
 *
 * <ul>
 * <li>
 * <code>javax.portlet.name</code>: The portlet name. If specified, the
 * options are applied to every editor used in that portlet.
 * </li>
 * <li>
 * <code>editor.config.key</code>: The key used to identify the editor (the
 * <code>input-editor</code> taglib tag's <code>configKey</code> attribute
 * value). If specified, the options are applied to every editor with the
 * specified <code>configKey</code>.
 * </li>
 * <li>
 * <code>editor.name</code>: The name of the editor (the
 * <code>input-editor</code> taglib tag's <code>editorName</code> attribute
 * value: <code>ckeditor</code>, <code>ckeditor_bbcode</code>,
 * <code>alloyeditor</code>, etc.). If specified, the options are applied to
 * every editor with that name.
 * </li>
 * </ul>
 *
 * <p>
 * In case there's more than one options contributor, they're prioritized by the
 * following criteria combinations (the first combination getting the highest
 * priority):
 * </p>
 *
 * <ol>
 * <li>
 * portlet name, editor config key, editor name
 * </li>
 * <li>
 * portlet name, editor config key
 * </li>
 * <li>
 * editor config key, editor name
 * </li>
 * <li>
 * portlet name, editor name
 * </li>
 * <li>
 * editor config key
 * </li>
 * <li>
 * portlet name
 * </li>
 * <li>
 * editor name
 * </li>
 * <li>
 * empty
 * </li>
 * </ol>
 *
 * <p>
 * If there are multiple contributors having the same criteria elements,
 * prioritization between them is based on service rank.
 * </p>
 *
 * @author Sergio González
 */
public interface EditorOptionsContributor {

	/**
	 * Updates the original EditorOptions object to some new options.
	 * It can even update or delete the original options, or any other
	 * options introduced by any other {@link EditorOptionsContributor}.
	 *
	 * <p>
	 * The editor options object contains the options that will be transformed
	 * by the editor config transformer to a Configuration JSON object.
	 * </p>
	 *
	 * @param editorOptions the original EditorOptions object containing the
	 *        options set by previous {@link EditorOptionsContributor}
	 *        modules
	 * @param inputEditorTaglibAttributes the attributes specified to the input
	 *        taglib tag that renders the editor
	 * @param themeDisplay the theme display
	 * @param liferayPortletResponse the Liferay portlet response (optionally
	 *        <code>null</code>). Only use the response to generate portlet
	 *        URLs.
	 */
	public void populateEditorOptions(
		EditorOptions editorOptions,
		Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse);

}