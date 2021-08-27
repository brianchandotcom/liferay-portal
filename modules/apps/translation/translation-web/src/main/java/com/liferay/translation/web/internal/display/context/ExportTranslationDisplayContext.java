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

package com.liferay.translation.web.internal.display.context;

import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsEntryLocalServiceUtil;
import com.liferay.segments.service.SegmentsExperienceServiceUtil;
import com.liferay.translation.constants.TranslationPortletKeys;
import com.liferay.translation.exporter.TranslationInfoItemFieldValuesExporter;
import com.liferay.translation.exporter.TranslationInfoItemFieldValuesExporterTracker;
import com.liferay.translation.info.item.provider.InfoItemLanguagesProvider;
import com.liferay.translation.web.internal.configuration.FFLayoutExperienceSelectorConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jorge González
 */
public class ExportTranslationDisplayContext {

	public ExportTranslationDisplayContext(
		long classNameId, long classPK,
		FFLayoutExperienceSelectorConfiguration
			ffLayoutExperienceSelectorConfiguration,
		long groupId, HttpServletRequest httpServletRequest,
		InfoItemServiceTracker infoItemServiceTracker,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, Object model,
		String title,
		TranslationInfoItemFieldValuesExporterTracker
			translationInfoItemFieldValuesExporterTracker) {

		_classNameId = classNameId;
		_classPK = classPK;
		_ffLayoutExperienceSelectorConfiguration =
			ffLayoutExperienceSelectorConfiguration;
		_groupId = groupId;
		_httpServletRequest = httpServletRequest;
		_infoItemServiceTracker = infoItemServiceTracker;
		_liferayPortletResponse = liferayPortletResponse;
		_model = model;
		_title = title;
		_translationInfoItemFieldValuesExporterTracker =
			translationInfoItemFieldValuesExporterTracker;

		_className = PortalUtil.getClassName(_classNameId);
		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getDisplayName(Locale currentLocale, Locale locale) {

		// LPS-138104

		String key = "language." + locale.getLanguage();

		String displayName = LanguageUtil.get(currentLocale, key);

		if (displayName.equals(key)) {
			return locale.getDisplayName(currentLocale);
		}

		return StringBundler.concat(
			displayName, " (", locale.getDisplayCountry(currentLocale), ")");
	}

	public List<Map<String, String>> getExperiences() throws PortalException {
		if (!Objects.equals(_className, Layout.class.getName())) {
			return null;
		}

		Map<String, String> defaultExperience = HashMapBuilder.put(
			"label",
			SegmentsExperienceConstants.getDefaultSegmentsExperienceName(
				_themeDisplay.getLocale())
		).put(
			"segment",
			_getSegmentsEntryName(
				SegmentsEntryConstants.ID_DEFAULT, _themeDisplay.getLocale())
		).put(
			"value",
			String.valueOf((Object)SegmentsExperienceConstants.ID_DEFAULT)
		).build();

		List<Map<String, String>> experiences = new ArrayList<>();

		if (!_ffLayoutExperienceSelectorConfiguration.enabled()) {
			experiences.add(defaultExperience);

			return experiences;
		}

		List<SegmentsExperience> segmentsExperiences =
			SegmentsExperienceServiceUtil.getSegmentsExperiences(
				_groupId, PortalUtil.getClassNameId(Layout.class.getName()),
				_classPK, true);

		boolean addedDefault = false;

		for (SegmentsExperience segmentsExperience : segmentsExperiences) {
			if ((segmentsExperience.getPriority() <
					SegmentsExperienceConstants.PRIORITY_DEFAULT) &&
				!addedDefault) {

				experiences.add(defaultExperience);

				addedDefault = true;
			}

			experiences.add(
				HashMapBuilder.put(
					"label",
					segmentsExperience.getName(_themeDisplay.getLocale())
				).put(
					"segment",
					_getSegmentsEntryName(
						segmentsExperience.getSegmentsEntryId(),
						_themeDisplay.getLocale())
				).put(
					"value",
					String.valueOf(segmentsExperience.getSegmentsExperienceId())
				).build());
		}

		if (!addedDefault) {
			experiences.add(defaultExperience);
		}

		return experiences;
	}

	public Map<String, Object> getExportTranslationData()
		throws PortalException {

		ResourceURL getExportTranslationAvailableLocalesURL =
			_liferayPortletResponse.createResourceURL(
				TranslationPortletKeys.TRANSLATION);

		getExportTranslationAvailableLocalesURL.setParameter(
			"groupId", String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID));
		getExportTranslationAvailableLocalesURL.setParameter(
			"classNameId", String.valueOf(_classNameId));
		getExportTranslationAvailableLocalesURL.setResourceID(
			"/translation/get_export_translation_available_locales");

		return HashMapBuilder.<String, Object>put(
			"availableExportFileFormats",
			() -> {
				Collection<TranslationInfoItemFieldValuesExporter>
					translationInfoItemFieldValuesExporters =
						_translationInfoItemFieldValuesExporterTracker.
							getTranslationInfoItemFieldValuesExporters();

				Stream<TranslationInfoItemFieldValuesExporter>
					translationInfoItemFieldValuesExportersStream =
						translationInfoItemFieldValuesExporters.stream();

				return translationInfoItemFieldValuesExportersStream.map(
					this::_getExportFileFormatJSONObject
				).collect(
					Collectors.toList()
				);
			}
		).put(
			"availableSourceLocales",
			_getLocalesJSONArray(
				_themeDisplay.getLocale(), _getAvailableSourceLocales())
		).put(
			"availableTargetLocales",
			_getLocalesJSONArray(
				_themeDisplay.getLocale(),
				LanguageUtil.getAvailableLocales(
					_themeDisplay.getSiteGroupId()))
		).put(
			"classPK", _classPK
		).put(
			"defaultSourceLanguageId", _getDefaultSourceLanguageId()
		).put(
			"experiences", getExperiences()
		).put(
			"exportTranslationURL", _getExportTranslationURLString()
		).put(
			"getExportTranslationAvailableLocalesURL",
			getExportTranslationAvailableLocalesURL.toString()
		).put(
			"pathModule", PortalUtil.getPathModule()
		).put(
			"redirectURL", getRedirect()
		).build();
	}

	public String getRedirect() {
		if (Validator.isNotNull(_redirect)) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		return _redirect;
	}

	public String getTitle() throws PortalException {
		return _title;
	}

	private Set<Locale> _getAvailableSourceLocales() throws PortalException {
		InfoItemLanguagesProvider<Object> infoItemLanguagesProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemLanguagesProvider.class, _className);

		Stream<String> stream = Arrays.stream(
			infoItemLanguagesProvider.getAvailableLanguageIds(_model));

		Stream<Locale> localesStream = stream.map(LocaleUtil::fromLanguageId);

		Set<Locale> availableSourceLocales = localesStream.collect(
			Collectors.toSet());

		if (!availableSourceLocales.contains(
				PortalUtil.getSiteDefaultLocale(_groupId))) {

			availableSourceLocales.add(
				PortalUtil.getSiteDefaultLocale(_groupId));
		}

		return availableSourceLocales;
	}

	private String _getDefaultSourceLanguageId() {
		InfoItemLanguagesProvider<Object> infoItemLanguagesProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemLanguagesProvider.class, _className);

		if (infoItemLanguagesProvider == null) {
			return LanguageUtil.getLanguageId(
				_themeDisplay.getSiteDefaultLocale());
		}

		return infoItemLanguagesProvider.getDefaultLanguageId(_model);
	}

	private JSONObject _getExportFileFormatJSONObject(
		TranslationInfoItemFieldValuesExporter
			translationInfoItemFieldValuesExporter) {

		return JSONUtil.put(
			"displayName",
			() -> {
				InfoLocalizedValue<String> labelInfoLocalizedValue =
					translationInfoItemFieldValuesExporter.
						getLabelInfoLocalizedValue();

				return labelInfoLocalizedValue.getValue(
					_themeDisplay.getLocale());
			}
		).put(
			"mimeType", translationInfoItemFieldValuesExporter.getMimeType()
		);
	}

	private String _getExportTranslationURLString() {
		LiferayPortletURL liferayPortletURL =
			_liferayPortletResponse.createResourceURL(
				TranslationPortletKeys.TRANSLATION);

		liferayPortletURL.setResourceID("/translation/export_translation");

		return PortletURLBuilder.create(
			liferayPortletURL
		).setParameter(
			"classNameId", _classNameId
		).setParameter(
			"classPK", _classPK
		).setParameter(
			"groupId", _groupId
		).buildString();
	}

	private JSONArray _getLocalesJSONArray(
		Locale locale, Collection<Locale> locales) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		locales.forEach(
			currentLocale -> jsonArray.put(
				JSONUtil.put(
					"displayName",
					LocaleUtil.getLocaleDisplayName(currentLocale, locale)
				).put(
					"languageId", LocaleUtil.toLanguageId(currentLocale)
				)));

		return jsonArray;
	}

	private String _getSegmentsEntryName(long segmentsEntryId, Locale locale) {
		if (segmentsEntryId == SegmentsEntryConstants.ID_DEFAULT) {
			return SegmentsEntryConstants.getDefaultSegmentsEntryName(locale);
		}

		SegmentsEntry segmentsEntry =
			SegmentsEntryLocalServiceUtil.fetchSegmentsEntry(segmentsEntryId);

		return segmentsEntry.getName(locale);
	}

	private final String _className;
	private final long _classNameId;
	private final long _classPK;
	private final FFLayoutExperienceSelectorConfiguration
		_ffLayoutExperienceSelectorConfiguration;
	private final long _groupId;
	private final HttpServletRequest _httpServletRequest;
	private final InfoItemServiceTracker _infoItemServiceTracker;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final Object _model;
	private String _redirect;
	private final ThemeDisplay _themeDisplay;
	private final String _title;
	private final TranslationInfoItemFieldValuesExporterTracker
		_translationInfoItemFieldValuesExporterTracker;

}