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

import com.liferay.configuration.admin.definition.ConfigurationFieldOptionsProvider;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.definitions.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.context.vocabulary.internal.configuration.SegmentsContextVocabularyConfiguration;
import com.liferay.segments.context.vocabulary.internal.configuration.persistence.listener.DuplicatedSegmentsContextVocabularyConfigurationModelListenerException;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
public class SegmentsContextVocabularyConfigurationDisplayContext {

	public SegmentsContextVocabularyConfigurationDisplayContext(
		List<ConfigurationFieldOptionsProvider.Option> assetVocabularyOptions,
		ConfigurationAdmin configurationAdmin,
		List<ConfigurationFieldOptionsProvider.Option> entityFieldOptions,
		ExtendedObjectClassDefinition extendedObjectClassDefinition,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_assetVocabularyOptions = assetVocabularyOptions;
		_configurationAdmin = configurationAdmin;
		_entityFieldOptions = entityFieldOptions;
		_extendedObjectClassDefinition = extendedObjectClassDefinition;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_duplicated = SessionErrors.contains(
			_renderRequest,
			DuplicatedSegmentsContextVocabularyConfigurationModelListenerException.class);

		_entityFields = _getEntityFields(getPid());

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_portletDisplay = themeDisplay.getPortletDisplay();

		_locale = themeDisplay.getLocale();
	}

	public void addPortletBreadcrumbEntries() {
		PortalUtil.addPortletBreadcrumbEntry(
			PortalUtil.getHttpServletRequest(_renderRequest),
			_portletDisplay.getPortletDisplayName(),
			String.valueOf(_renderResponse.createRenderURL()));

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			_locale, getClass());

		PortalUtil.addPortletBreadcrumbEntry(
			PortalUtil.getHttpServletRequest(_renderRequest),
			ResourceBundleUtil.getString(resourceBundle, "segments"),
			String.valueOf(getRedirect()));

		PortletURL portletURL = PortletURLUtil.getCurrent(
			_renderRequest, _renderResponse);

		PortalUtil.addPortletBreadcrumbEntry(
			PortalUtil.getHttpServletRequest(_renderRequest),
			ResourceBundleUtil.getString(
				resourceBundle,
				"segments-context-vocabulary-configuration-name"),
			String.valueOf(portletURL));
	}

	public ActionURL getActionURL() {
		ActionURL actionURL = _renderResponse.createActionURL();

		actionURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/update_segments_context_vocabulary_configuration");
		actionURL.setParameter("redirect", String.valueOf(getRedirect()));

		return actionURL;
	}

	public String getAssetVocabulary() throws IOException {
		return Optional.ofNullable(
			_getConfiguration()
		).map(
			Configuration::getProperties
		).map(
			properties -> String.valueOf(properties.get("assetVocabulary"))
		).orElse(
			StringPool.BLANK
		);
	}

	public List<ConfigurationFieldOptionsProvider.Option>
		getAssetVocabularyOptions() {

		return _assetVocabularyOptions;
	}

	public String getDescription() {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			_locale, getClass());

		return LanguageUtil.get(
			resourceBundle, _extendedObjectClassDefinition.getDescription());
	}

	public String getEntityField() throws IOException {
		return Optional.ofNullable(
			_getConfiguration()
		).map(
			Configuration::getProperties
		).map(
			properties -> String.valueOf(properties.get("entityField"))
		).orElse(
			StringPool.BLANK
		);
	}

	public String getEntityFieldHelpMessage() {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			_locale, getClass());

		if (_duplicated) {
			return ResourceBundleUtil.getString(
				resourceBundle,
				"this-field-is-already-linked-to-one-vocabulary");
		}

		return ResourceBundleUtil.getString(
			resourceBundle,
			"segments-context-vocabulary-configuration-entity-field-" +
				"description");
	}

	public List<ConfigurationFieldOptionsProvider.Option>
		getEntityFieldOptions() {

		return _entityFieldOptions;
	}

	public String getFactoryPid() {
		return ParamUtil.getString(_renderRequest, "factoryPid");
	}

	public String getPid() {
		return ParamUtil.getString(_renderRequest, "pid");
	}

	public PortletURL getRedirect() {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCRenderCommandName(
			"/view_configuration_screen"
		).setParameter(
			"configurationScreenKey",
			"segments-context-vocabulary-configuration-name"
		).build();
	}

	public String getTitle() throws IOException {
		return Optional.ofNullable(
			_getConfiguration()
		).map(
			Configuration::getProperties
		).map(
			properties -> String.valueOf(properties.get("entityField"))
		).orElseGet(
			() -> LanguageUtil.get(_locale, "add")
		);
	}

	public boolean isDisabled(String entityField) {
		return _entityFields.contains(entityField);
	}

	public boolean isDuplicated() {
		return _duplicated;
	}

	private Configuration _getConfiguration() throws IOException {
		String pid = getPid();

		if (Validator.isNull(pid)) {
			return null;
		}

		return _configurationAdmin.getConfiguration(pid, StringPool.QUESTION);
	}

	private List<String> _getEntityFields(String pid) {
		try {
			return Stream.of(
				Optional.ofNullable(
					_configurationAdmin.listConfigurations(
						StringBundler.concat(
							"(", ConfigurationAdmin.SERVICE_FACTORYPID, "=",
							SegmentsContextVocabularyConfiguration.class.
								getCanonicalName(),
							")"))
				).orElse(
					new Configuration[0]
				)
			).filter(
				configuration -> !Objects.equals(pid, configuration.getPid())
			).map(
				Configuration::getProperties
			).map(
				properties -> String.valueOf(properties.get("entityField"))
			).collect(
				Collectors.toList()
			);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return Collections.emptyList();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsContextVocabularyConfigurationDisplayContext.class);

	private final List<ConfigurationFieldOptionsProvider.Option>
		_assetVocabularyOptions;
	private final ConfigurationAdmin _configurationAdmin;
	private final boolean _duplicated;
	private final List<ConfigurationFieldOptionsProvider.Option>
		_entityFieldOptions;
	private final List<String> _entityFields;
	private final ExtendedObjectClassDefinition _extendedObjectClassDefinition;
	private final Locale _locale;
	private final PortletDisplay _portletDisplay;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}