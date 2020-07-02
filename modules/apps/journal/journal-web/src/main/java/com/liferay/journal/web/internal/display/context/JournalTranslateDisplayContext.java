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

package com.liferay.journal.web.internal.display.context;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldSetEntry;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.journal.constants.JournalWebKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.web.internal.constants.JournalWebConstants;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.translation.info.field.TranslationInfoFieldChecker;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class JournalTranslateDisplayContext {

	public JournalTranslateDisplayContext(
		LiferayPortletRequest liferayPortletRequest) {

		_httpServletRequest = PortalUtil.getHttpServletRequest(
			liferayPortletRequest);

		_article = (JournalArticle)_httpServletRequest.getAttribute(
			JournalWebKeys.JOURNAL_ARTICLES);
		_infoForm = (InfoForm)_httpServletRequest.getAttribute(
			InfoForm.class.getName());
		_infoItemFieldValues =
			(InfoItemFieldValues)_httpServletRequest.getAttribute(
				InfoItemFieldValues.class.getName());

		_sourceLanguageId = (String)_httpServletRequest.getAttribute(
			JournalWebConstants.SOURCE_LANGUAGE_ID);

		_sourceLocale = LocaleUtil.fromLanguageId(_sourceLanguageId);

		_targetLanguageId = (String)_httpServletRequest.getAttribute(
			JournalWebConstants.TARGET_LANGUAGE_ID);

		_targetLocale = LocaleUtil.fromLanguageId(_targetLanguageId);

		_translationInfoFieldChecker =
			(TranslationInfoFieldChecker)_httpServletRequest.getAttribute(
				TranslationInfoFieldChecker.class.getName());
	}

	public String getInfoFieldLabel(InfoField infoField) {
		InfoLocalizedValue<String> labelInfoLocalizedValue =
			infoField.getLabelInfoLocalizedValue();

		return labelInfoLocalizedValue.getValue(getSourceLocale());
	}

	public List<InfoFieldSetEntry> getInfoFieldSetEntries() {
		return _infoForm.getInfoFieldSetEntries();
	}

	public String getInfoFieldSetLabel(
		InfoFieldSetEntry infoFieldSetEntry, Locale locale) {

		if (infoFieldSetEntry instanceof InfoFieldSet) {
			InfoFieldSet infoFieldSet = (InfoFieldSet)infoFieldSetEntry;

			return infoFieldSet.getLabel(locale);
		}

		return null;
	}

	public List<InfoFieldValue<Object>> getInfoFieldValues(
		InfoFieldSetEntry infoFieldSetEntry) {

		if (infoFieldSetEntry instanceof InfoField) {
			InfoField infoField = (InfoField)infoFieldSetEntry;

			if (_translationInfoFieldChecker.isTranslatable(infoField)) {
				return Arrays.asList(
					_infoItemFieldValues.getInfoFieldValue(
						infoField.getName()));
			}
		}
		else if (infoFieldSetEntry instanceof InfoFieldSet) {
			InfoFieldSet infoFieldSet = (InfoFieldSet)infoFieldSetEntry;

			List<InfoField> infoFields = infoFieldSet.getAllInfoFields();

			Stream<InfoField> stream = infoFields.stream();

			return stream.filter(
				_translationInfoFieldChecker::isTranslatable
			).map(
				InfoField::getName
			).map(
				_infoItemFieldValues::getInfoFieldValue
			).collect(
				Collectors.toList()
			);
		}

		return Collections.emptyList();
	}

	public String getLanguageIdTitle(String languageId) {
		return StringUtil.replace(
			languageId, CharPool.UNDERLINE, CharPool.DASH);
	}

	public String getSourceLanguageId() {
		return _sourceLanguageId;
	}

	public Locale getSourceLocale() {
		return _sourceLocale;
	}

	public String getTargetLanguageId() {
		return _targetLanguageId;
	}

	public Locale getTargetLocale() {
		return _targetLocale;
	}

	public String getTitle() {
		return _article.getTitle(PortalUtil.getLocale(_httpServletRequest));
	}

	public Map<String, Object> getTranslateLanguagesSelectorData() {
		return HashMapBuilder.<String, Object>put(
			"currentUrl", PortalUtil.getCurrentCompleteURL(_httpServletRequest)
		).put(
			"sourceAvailableLanguages",
			_httpServletRequest.getAttribute(
				JournalWebConstants.AVAILABLE_SOURCE_LANGUAGE_IDS)
		).put(
			"sourceLanguageId", _sourceLanguageId
		).put(
			"targetAvailableLanguages",
			_httpServletRequest.getAttribute(
				JournalWebConstants.AVAILABLE_TARGET_LANGUAGE_IDS)
		).put(
			"targetLanguageId", _targetLanguageId
		).build();
	}

	private final JournalArticle _article;
	private final HttpServletRequest _httpServletRequest;
	private final InfoForm _infoForm;
	private final InfoItemFieldValues _infoItemFieldValues;
	private final String _sourceLanguageId;
	private final Locale _sourceLocale;
	private final String _targetLanguageId;
	private final Locale _targetLocale;
	private final TranslationInfoFieldChecker _translationInfoFieldChecker;

}