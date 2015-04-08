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

package com.liferay.portal.kernel.editor.config;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.Map;

/**
 * Provides an interface responsible for setting the configuration and options
 * of the editor.
 *
 * <p>
 * Implementations of this class must be OSGI components that are registered in
 * the OSGI Registry.
 * </p>
 *
 * <p>
 * The configuration and options can be targeted on specific editors, based on
 * three different criteria: the portlet name, the editor config key and the
 * editor name. These criteria will be defined as OSGI properties with the
 * following names:
 *
 * <ul>
 * <li>
 * javax.portlet.name: The portlet name. If specified, the configuration and
 * options populated in the JSON object will apply to every editor used in that
 * portlet.
 * </li>
 * <li>
 * editor.config.key: The key used to identify the editor (the configKey
 * attribute used by the input-editor taglib) If specified, the configuration
 * and options populated in the JSON object will apply to every editor with the
 * specified configKey.
 * </li>
 * <li>
 * editor.name: The name of the editor (the eidtorName attributed used by the
 * input-editor taglib: ckeditor, ckeditor_bbcode, alloyeditor...) If specified,
 * the configuration and options populated in the JSON object will apply to
 * every editor with that name.
 * </li>
 * </ul>
 *
 * <p>
 * In case that there's more than one config,
 * the priority is defined as follows (the first have highest priority):
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
 * </ol>
 *
 * <p>
 * If there are more than one config in the same of any of the previous levels,
 * the priority will be defined based on the service rank.
 * </p>
 *
 * @author Sergio González
 */
public interface EditorConfigContributor {

	/**
	 * Updates the original configuration JSON object to add some new
	 * configuration. It can even update or delete the original configuration,
	 * or any other configuration introduced by any other
	 * EditorConfigContributor.
	 *
	 * <p>
	 * The configuration object contains the configuration that will be directly
	 * used by the editor. Therefore, the config JSON object might be different
	 * for different editors.
	 * </p>
	 *
	 * @param  jsonObject the original json object containing all the
	 *         configuration set by previous EditorConfigContributor modules.
	 * @param  inputEditorTaglibAttributes the attributes specified to the input
	 *         taglib that is rendering the editor.
	 * @param  themeDisplay the theme display
	 * @param  liferayPortletResponse the liferay portlet response. Optionally
	 *         <code>null</code>. This should only be used to generate portlet
	 *         URLs.
	 */
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse);

	/**
	 * Updates the original options JSON object to add some new configuration.
	 * It can even update or delete the original options, or any other option
	 * introduced by any other EditorConfigContributor.
	 *
	 * <p>
	 * The options object is used by the input-editor taglib and it contains
	 * options to decide the behavior of the editor, but it's not directly used
	 * by the editor, so typically will be the same for all the editors.
	 * </p>
	 *
	 * @param  jsonObject the original json object containing all the
	 *         options set by previous EditorConfigContributor modules.
	 * @param  inputEditorTaglibAttributes the attributes specified to the input
	 *         taglib that is rendering the editor.
	 * @param  themeDisplay the theme display
	 * @param  liferayPortletResponse the liferay portlet response. Optionally
	 *         <code>null</code>. This should only be used to generate portlet
	 *         URLs.
	 */
	public void populateOptionsJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse);

}