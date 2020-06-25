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

package com.liferay.translation.web.internal.info.item.updater;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.util.JournalConverter;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.translation.info.item.updater.InfoItemFieldValuesUpdater;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(
	property = "model.class.name=com.liferay.journal.model.JournalArticle",
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
		Map<Locale, Map<String, String>> importedLocaleContentMap =
			new HashMap<>();
		Set<Locale> translatedLocales = new HashSet<>();
		Map<String, String> fieldNameContentMap = new HashMap<>();

		for (InfoFieldValue<Object> infoFieldValue :
				infoItemFieldValues.getInfoFieldValues()) {

			InfoField infoField = infoFieldValue.getInfoField();

			InfoLocalizedValue<String> labelInfoLocalizedValue =
				infoField.getLabelInfoLocalizedValue();

			for (Locale locale :
					labelInfoLocalizedValue.getAvailableLocales()) {

				translatedLocales.add(locale);

				if (infoFieldValue.getValue(locale) instanceof String) {
					String fieldName = infoField.getName();

					String valueString = String.valueOf(
						infoFieldValue.getValue(locale));

					if (Objects.equals("description", fieldName)) {
						importedLocaleDescriptionMap.put(locale, valueString);
					}
					else if (Objects.equals("title", fieldName)) {
						importedLocaleTitleMap.put(locale, valueString);
					}
					else {
						fieldNameContentMap.put(fieldName, valueString);

						importedLocaleContentMap.put(
							locale, fieldNameContentMap);
					}
				}
			}
		}

		for (Locale targetLocale : translatedLocales) {
			String translatedTitle = _getTranslatedString(
				article.getTitle(targetLocale), article.getTitle(),
				importedLocaleTitleMap.get(targetLocale));
			String translatedDescription = _getTranslatedString(
				article.getDescription(targetLocale), article.getDescription(),
				importedLocaleDescriptionMap.get(targetLocale));
			String translatedContent = _getTranslatedContent(
				article.getContent(), article.getDDMStructure(),
				importedLocaleContentMap, targetLocale);

			article = _journalArticleService.updateArticleTranslation(
				article.getGroupId(), article.getArticleId(),
				article.getVersion(), targetLocale, translatedTitle,
				translatedDescription, translatedContent, null,
				ServiceContextThreadLocal.getServiceContext());
		}

		return article;
	}

	private String _getTranslatedContent(
			String content, DDMStructure ddmStructure,
			Map<Locale, Map<String, String>> importedLocaleContentMap,
			Locale targetLocale)
		throws Exception {

		Map<String, String> contentFieldMap = importedLocaleContentMap.get(
			targetLocale);

		if ((contentFieldMap == null) || contentFieldMap.isEmpty()) {
			return content;
		}

		Fields ddmFields = _journalConverter.getDDMFields(
			ddmStructure, content);

		for (Map.Entry<String, String> entry : contentFieldMap.entrySet()) {
			Field field = ddmFields.get(entry.getKey());

			field.setValue(targetLocale, entry.getValue());
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

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private JournalConverter _journalConverter;

}