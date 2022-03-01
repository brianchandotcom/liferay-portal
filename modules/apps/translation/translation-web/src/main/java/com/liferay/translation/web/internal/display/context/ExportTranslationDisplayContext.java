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
import com.liferay.petra.apache.http.components.URIBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsEntryLocalServiceUtil;
import com.liferay.segments.service.SegmentsExperienceServiceUtil;
import com.liferay.translation.exporter.TranslationInfoItemFieldValuesExporter;
import com.liferay.translation.exporter.TranslationInfoItemFieldValuesExporterTracker;
import com.liferay.translation.info.item.provider.InfoItemLanguagesProvider;

import java.net.URI;

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

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jorge González
 */
public class ExportTranslationDisplayContext {

	public ExportTranslationDisplayContext(
		long classNameId, long[] classPKs, long groupId,
		HttpServletRequest httpServletRequest,
		InfoItemServiceTracker infoItemServiceTracker,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, List<Object> models,
		String title,
		TranslationInfoItemFieldValuesExporterTracker
			translationInfoItemFieldValuesExporterTracker) {

		_classNameId = classNameId;
		_classPKs = classPKs;
		_groupId = groupId;
		_httpServletRequest = httpServletRequest;
		_infoItemServiceTracker = infoItemServiceTracker;
		_liferayPortletResponse = liferayPortletResponse;
		_models = models;
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

		List<SegmentsExperience> segmentsExperiences =
			_getSegmentsExperiences();

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

	public Map<String, Object> getExportTranslationData() throws Exception {
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
			"defaultSourceLanguageId", _getDefaultSourceLanguageId()
		).put(
			"experiences", getExperiences()
		).put(
			"exportTranslationURL", _getExportTranslationURLString()
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

	private Set<Locale> _getAvailableSourceLocales() throws Exception {
		InfoItemLanguagesProvider<Object> infoItemLanguagesProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemLanguagesProvider.class, _className);

		List<String> languageIds = new ArrayList<>();

		for (Object model : _models) {
			_intersect(
				languageIds,
				Arrays.asList(
					infoItemLanguagesProvider.getAvailableLanguageIds(model)));
		}

		Stream<String> stream = languageIds.stream();

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

		return infoItemLanguagesProvider.getDefaultLanguageId(_models.get(0));
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

	private String _getExportTranslationURLString() throws Exception {
		URIBuilder.URIBuilderWrapper uriBuilderWrapper = URIBuilder.create(
			PortalUtil.getPortalURL(_httpServletRequest) + Portal.PATH_MODULE +
				"/translation/export_translation"
		).addParameter(
			"classNameId", String.valueOf(_classNameId)
		);

		for (long classPK : _classPKs) {
			uriBuilderWrapper.addParameter("classPK", String.valueOf(classPK));
		}

		uriBuilderWrapper.addParameter("groupId", String.valueOf(_groupId));

		URI uri = uriBuilderWrapper.build();

		return uri.toString();
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

	private List<SegmentsExperience> _getSegmentsExperiences()
		throws PortalException {

		List<SegmentsExperience> segmentsExperiences = new ArrayList<>();

		for (long classPK : _classPKs) {
			_intersect(
				segmentsExperiences,
				SegmentsExperienceServiceUtil.getSegmentsExperiences(
					_groupId, PortalUtil.getClassNameId(Layout.class.getName()),
					classPK, true));
		}

		return segmentsExperiences;
	}

	private <T> void _intersect(List<T> list1, List<T> list2) {
		if (list1.isEmpty()) {
			list1.addAll(list2);
		}
		else {
			list1.retainAll(list2);
		}
	}

	private final String _className;
	private final long _classNameId;
	private final long[] _classPKs;
	private final long _groupId;
	private final HttpServletRequest _httpServletRequest;
	private final InfoItemServiceTracker _infoItemServiceTracker;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final List<Object> _models;
	private String _redirect;
	private final ThemeDisplay _themeDisplay;
	private final String _title;
	private final TranslationInfoItemFieldValuesExporterTracker
		_translationInfoItemFieldValuesExporterTracker;

}