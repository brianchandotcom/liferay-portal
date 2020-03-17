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

package com.liferay.segments.vocabulary.field.customizer.internal.display.context;

import com.liferay.configuration.admin.definition.ConfigurationFieldOptionsProvider;
import com.liferay.portal.configuration.metatype.definitions.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.ActionURL;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Cristina González
 */
public class SegmentsVocabularyFieldCustomizerDisplayContext {

	public SegmentsVocabularyFieldCustomizerDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		ExtendedObjectClassDefinition extendedObjectClassDefinition,
		List<ConfigurationFieldOptionsProvider.Option> fieldNameOptions,
		List<ConfigurationFieldOptionsProvider.Option> vocabularyNameOptions) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_extendedObjectClassDefinition = extendedObjectClassDefinition;
		_fieldNameOptions = fieldNameOptions;
		_vocabularyNameOptions = vocabularyNameOptions;

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_locale = themeDisplay.getLocale();
	}

	public ActionURL getActionURL() {
		return _renderResponse.createActionURL();
	}

	public String getDescription() {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			_locale, getClass());

		return LanguageUtil.get(
			resourceBundle, _extendedObjectClassDefinition.getDescription());
	}

	public String getFactoryPid() {
		return ParamUtil.getString(_renderRequest, "factoryPid");
	}

	public List<ConfigurationFieldOptionsProvider.Option>
		getFieldNameOptions() {

		return _fieldNameOptions;
	}

	public String getPid() {
		return ParamUtil.getString(_renderRequest, "pid");
	}

	public PortletURL getRedirect() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/view_configuration_screen");
		portletURL.setParameter(
			"configurationScreenKey",
			"segments-vocabulary-field-customizer-configuration-name");

		return portletURL;
	}

	public List<ConfigurationFieldOptionsProvider.Option>
		getVocabularyNameOptions() {

		return _vocabularyNameOptions;
	}

	private final ExtendedObjectClassDefinition _extendedObjectClassDefinition;
	private final List<ConfigurationFieldOptionsProvider.Option>
		_fieldNameOptions;
	private final Locale _locale;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final List<ConfigurationFieldOptionsProvider.Option>
		_vocabularyNameOptions;

}