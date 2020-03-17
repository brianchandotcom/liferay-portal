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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.definitions.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionURL;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Cristina González
 */
public class SegmentsVocabularyFieldCustomizerDisplayContext {

	public SegmentsVocabularyFieldCustomizerDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		ConfigurationAdmin configurationAdmin,
		ExtendedObjectClassDefinition extendedObjectClassDefinition,
		List<ConfigurationFieldOptionsProvider.Option> fieldNameOptions,
		List<ConfigurationFieldOptionsProvider.Option> vocabularyNameOptions) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_configurationAdmin = configurationAdmin;
		_extendedObjectClassDefinition = extendedObjectClassDefinition;
		_fieldNameOptions = fieldNameOptions;
		_vocabularyNameOptions = vocabularyNameOptions;

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_locale = themeDisplay.getLocale();
	}

	public ActionURL getActionURL() {
		ActionURL actionURL = _renderResponse.createActionURL();

		actionURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/update_segments_vocabulary_field_customizer");
		actionURL.setParameter("redirect", String.valueOf(getRedirect()));

		return actionURL;
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

	public String getFieldName() throws IOException {
		return Optional.ofNullable(
			_getConfiguration()
		).map(
			Configuration::getProperties
		).map(
			properties -> String.valueOf(properties.get("fieldName"))
		).orElse(
			StringPool.BLANK
		);
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

	public String getTitle() throws IOException {
		return Optional.ofNullable(
			_getConfiguration()
		).map(
			Configuration::getProperties
		).map(
			properties -> String.valueOf(properties.get("fieldName"))
		).orElseGet(
			() -> LanguageUtil.get(_locale, "add")
		);
	}

	public String getVocabularyName() throws IOException {
		return Optional.ofNullable(
			_getConfiguration()
		).map(
			Configuration::getProperties
		).map(
			properties -> String.valueOf(properties.get("vocabularyName"))
		).orElse(
			StringPool.BLANK
		);
	}

	public List<ConfigurationFieldOptionsProvider.Option>
		getVocabularyNameOptions() {

		return _vocabularyNameOptions;
	}

	private Configuration _getConfiguration() throws IOException {
		String pid = getPid();

		if (Validator.isNull(pid)) {
			return null;
		}

		return _configurationAdmin.getConfiguration(pid, StringPool.QUESTION);
	}

	private final ConfigurationAdmin _configurationAdmin;
	private final ExtendedObjectClassDefinition _extendedObjectClassDefinition;
	private final List<ConfigurationFieldOptionsProvider.Option>
		_fieldNameOptions;
	private final Locale _locale;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final List<ConfigurationFieldOptionsProvider.Option>
		_vocabularyNameOptions;

}