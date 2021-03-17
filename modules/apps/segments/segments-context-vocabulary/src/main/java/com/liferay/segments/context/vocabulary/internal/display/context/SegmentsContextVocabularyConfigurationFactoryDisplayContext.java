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

package com.liferay.segments.context.vocabulary.internal.display.context;

import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.context.vocabulary.internal.configuration.SegmentsContextVocabularyConfiguration;
import com.liferay.segments.context.vocabulary.internal.constants.SegmentsContextVocabularyWebKeys;

import java.util.List;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.cm.Configuration;

/**
 * @author Cristina González
 */
public class SegmentsContextVocabularyConfigurationFactoryDisplayContext {

	public SegmentsContextVocabularyConfigurationFactoryDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_configurations = (List<Configuration>)renderRequest.getAttribute(
			SegmentsContextVocabularyWebKeys.
				SEGMENTS_CONTEXT_VOCABULARY_CONFIGURATIONS);

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public PortletURL getAddConfigurationURL() {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCRenderCommandName(
			"/edit_segments_context_vocabulary_configuration"
		).setParameter(
			"factoryPid",
			SegmentsContextVocabularyConfiguration.class.getCanonicalName()
		).build();
	}

	public PortletURL getDeleteConfigurationURL(Configuration configuration) {
		return PortletURLBuilder.createActionURL(
			_renderResponse
		).setActionName(
			"/delete_segments_context_vocabulary_configuration"
		).setRedirect(
			String.valueOf(_getRedirect())
		).setParameter(
			"factoryPid", configuration.getFactoryPid()
		).setParameter(
			"pid", configuration.getPid()
		).build();
	}

	public PortletURL getEditConfigurationURL(Configuration configuration) {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCRenderCommandName(
			"/edit_segments_context_vocabulary_configuration"
		).setParameter(
			"factoryPid", configuration.getFactoryPid()
		).setParameter(
			"pid", configuration.getPid()
		).build();
	}

	public String getEmptyResultMessage() {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			_themeDisplay.getLocale(),
			SegmentsContextVocabularyConfigurationFactoryDisplayContext.class);

		return LanguageUtil.format(
			resourceBundle, "no-entries-for-x-have-been-added-yet",
			"segments-context-vocabulary-configuration-name");
	}

	public PortletURL getIteratorURL() {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCRenderCommandName(
			"/view_configuration_screen"
		).setParameter(
			"configurationScreenKey",
			"segments-context-vocabulary-configuration-name"
		).build();
	}

	public List<Configuration> getResults(int start, int end) {
		return ListUtil.subList(_configurations, start, end);
	}

	public String getTitle() {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			_themeDisplay.getLocale(), getClass());

		return ResourceBundleUtil.getString(
			resourceBundle,
			"segments-context-vocabulary-configuration-entity-field-name");
	}

	public int getTotal() {
		return _configurations.size();
	}

	private PortletURL _getRedirect() {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCRenderCommandName(
			"/view_configuration_screen"
		).setParameter(
			"configurationScreenKey",
			"segments-context-vocabulary-configuration-name"
		).build();
	}

	private final List<Configuration> _configurations;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}