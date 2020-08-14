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

package com.liferay.translation.internal.info.item.updater;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.NumberInfoFieldType;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.updater.InfoItemFieldValuesUpdater;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.util.JournalConverter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.text.NumberFormat;
import java.text.ParseException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(
	property = "item.class.name=com.liferay.journal.model.JournalArticle",
	service = InfoItemFieldValuesUpdater.class
)
public class JournalArticleInfoItemFieldValuesUpdaterImpl
	implements InfoItemFieldValuesUpdater<JournalArticle> {

	@Override
	public JournalArticle updateFromInfoItemFieldValues(
			JournalArticle article, InfoItemFieldValues infoItemFieldValues)
		throws Exception {

		Map<Locale, String> importedLocaleTitleMap = new HashMap<>();
		Map<Locale, String> importedLocaleDescriptionMap = new HashMap<>();
		Map<Locale, Map<String, Serializable>> importedLocaleContentMap =
			new HashMap<>();
		Set<Locale> translatedLocales = new HashSet<>();
		Map<String, Serializable> fieldNameContentMap = new HashMap<>();

		for (InfoFieldValue<Object> infoFieldValue :
				infoItemFieldValues.getInfoFieldValues()) {

			_getInfoLocalizedValueOptional(
				infoFieldValue
			).ifPresent(
				infoLocalizedValue -> {
					InfoField infoField = infoFieldValue.getInfoField();

					for (Locale locale :
							infoLocalizedValue.getAvailableLocales()) {

						if ((infoFieldValue.getValue(locale) != null) &&
							(infoFieldValue.getValue(locale) instanceof
								String)) {

							translatedLocales.add(locale);

							String fieldName = infoField.getName();

							String valueString = String.valueOf(
								infoFieldValue.getValue(locale));

							if (Objects.equals("description", fieldName)) {
								importedLocaleDescriptionMap.put(
									locale, valueString);
							}
							else if (Objects.equals("title", fieldName)) {
								importedLocaleTitleMap.put(locale, valueString);
							}
							else {
								fieldNameContentMap.put(
									fieldName,
									_getSerializable(
										infoField, valueString, locale));

								importedLocaleContentMap.put(
									locale, fieldNameContentMap);
							}
						}
					}
				}
			);
		}

		Map<Locale, String> titleMap = article.getTitleMap();
		Map<Locale, String> descriptionMap = article.getDescriptionMap();
		String translatedContent = article.getContent();

		for (Locale targetLocale : translatedLocales) {
			titleMap.put(
				targetLocale,
				_getTranslatedString(
					article.getTitle(targetLocale), article.getTitle(),
					importedLocaleTitleMap.get(targetLocale)));
			descriptionMap.put(
				targetLocale,
				_getTranslatedString(
					article.getDescription(targetLocale),
					article.getDescription(),
					importedLocaleDescriptionMap.get(targetLocale)));
			translatedContent = _getTranslatedContent(
				translatedContent, article.getDDMStructure(),
				importedLocaleContentMap, targetLocale);
		}

		return _journalArticleService.updateArticle(
			article.getUserId(), article.getGroupId(), article.getFolderId(),
			article.getArticleId(), article.getVersion(), titleMap,
			descriptionMap, translatedContent, article.getLayoutUuid(),
			ServiceContextThreadLocal.getServiceContext());
	}

	private Optional<InfoLocalizedValue<Object>> _getInfoLocalizedValueOptional(
		InfoFieldValue<Object> infoFieldValue) {

		Object value = infoFieldValue.getValue();

		if (value instanceof InfoLocalizedValue) {
			return Optional.of((InfoLocalizedValue)value);
		}

		return Optional.empty();
	}

	private Serializable _getSerializable(
		InfoField infoField, String value, Locale locale) {

		if (Objects.equals(
				NumberInfoFieldType.INSTANCE, infoField.getInfoFieldType())) {

			NumberFormat numberFormat = NumberFormat.getInstance(locale);

			try {
				return numberFormat.parse(GetterUtil.getString(value));
			}
			catch (ParseException parseException) {
				_log.error(parseException, parseException);
			}
		}

		return value;
	}

	private String _getTranslatedContent(
			String content, DDMStructure ddmStructure,
			Map<Locale, Map<String, Serializable>> importedLocaleContentMap,
			Locale targetLocale)
		throws Exception {

		Map<String, Serializable> contentFieldMap =
			importedLocaleContentMap.get(targetLocale);

		if ((contentFieldMap == null) || contentFieldMap.isEmpty()) {
			return content;
		}

		Fields ddmFields = _journalConverter.getDDMFields(
			ddmStructure, content);

		for (Map.Entry<String, Serializable> entry :
				contentFieldMap.entrySet()) {

			Field field = ddmFields.get(entry.getKey());

			if (field != null) {
				field.setValue(targetLocale, entry.getValue());
			}
		}

		return _journalConverter.getContent(ddmStructure, ddmFields);
	}

	private String _getTranslatedString(
		String currentString, String defaultString, String importedString) {

		if (importedString != null) {
			return importedString;
		}

		if (Validator.isNotNull(currentString)) {
			return currentString;
		}

		return defaultString;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleInfoItemFieldValuesUpdaterImpl.class);

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private JournalConverter _journalConverter;

}