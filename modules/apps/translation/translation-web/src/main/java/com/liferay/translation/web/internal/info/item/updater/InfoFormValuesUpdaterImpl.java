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

import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.translation.info.item.updater.InfoFormValuesUpdater;

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
@Component(service = InfoFormValuesUpdater.class)
public class InfoFormValuesUpdaterImpl implements InfoFormValuesUpdater {

	@Override
	public JournalArticle updateFromInfoFormValues(
			JournalArticle article, InfoItemFieldValues infoItemFieldValues)
		throws PortalException {

		Map<Locale, String> importedLocaleTitleMap = new HashMap<>();
		Map<Locale, String> importedLocaleDescriptionMap = new HashMap<>();
		Set<Locale> translatedLocales = new HashSet<>();

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

			article = _journalArticleService.updateArticleTranslation(
				article.getGroupId(), article.getArticleId(),
				article.getVersion(), targetLocale, translatedTitle,
				translatedDescription, article.getContent(), null,
				ServiceContextThreadLocal.getServiceContext());
		}

		return article;
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

}