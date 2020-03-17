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

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.vocabulary.field.customizer.internal.configuration.SegmentsVocabularyFieldCustomizerConfiguration;
import com.liferay.segments.vocabulary.field.customizer.internal.constants.SegmentsVocabularyFieldCustomizerWebKeys;

import java.util.List;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.cm.Configuration;

/**
 * @author Cristina González
 */
public class SegmentsVocabularyFieldCustomizerFactoryDisplayContext {

	public SegmentsVocabularyFieldCustomizerFactoryDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_configurations = (List<Configuration>)renderRequest.getAttribute(
			SegmentsVocabularyFieldCustomizerWebKeys.
				SEGMENTS_VOCABULARY_FIELD_CUSTOMIZER_CONFIGURATIONS);

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public PortletURL getAddConfigurationURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName",
			"/edit_segments_vocabulary_field_customizer");
		portletURL.setParameter(
			"factoryPid",
			SegmentsVocabularyFieldCustomizerConfiguration.class.
				getCanonicalName());

		return portletURL;
	}

	public PortletURL getEditConfigurationURL(Configuration configuration) {
		return _renderResponse.createRenderURL();
	}

	public String getEmptyResultMessage() {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			_themeDisplay.getLocale(),
			SegmentsVocabularyFieldCustomizerFactoryDisplayContext.class);

		return LanguageUtil.format(
			resourceBundle, "no-entries-for-x-have-been-added-yet",
			"segments-vocabulary-field-customizer-configuration-name");
	}

	public PortletURL getIteratorURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/view_configuration_screen");
		portletURL.setParameter(
			"configurationScreenKey",
			"segments-vocabulary-field-customizer-configuration-name");

		return portletURL;
	}

	public List<Configuration> getResults(int start, int end) {
		return ListUtil.subList(_configurations, start, end);
	}

	public String getTitle() {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			_themeDisplay.getLocale(), getClass());

		return ResourceBundleUtil.getString(resourceBundle, "field-name");
	}

	public int getTotal() {
		return _configurations.size();
	}

	private final List<Configuration> _configurations;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}