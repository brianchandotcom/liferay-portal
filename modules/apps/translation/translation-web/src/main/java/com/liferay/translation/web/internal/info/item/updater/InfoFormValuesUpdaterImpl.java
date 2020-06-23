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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.translation.info.item.updater.InfoFormValuesUpdater;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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

		try {
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
							importedLocaleDescriptionMap.put(
								locale, valueString);
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
					article.getDescription(targetLocale),
					article.getDescription(),
					importedLocaleDescriptionMap.get(targetLocale));

				Map<String, String> contentFieldMap =
					importedLocaleContentMap.get(targetLocale);
				String translatedContent = article.getContent();

				if ((contentFieldMap != null) && !contentFieldMap.isEmpty()) {
					Document document = SAXReaderUtil.read(translatedContent);

					for (Map.Entry<String, String> entry :
							contentFieldMap.entrySet()) {

						translatedContent = _getTranslatedContent(
							entry.getValue(), document, entry.getKey(),
							targetLocale);
					}
				}

				article = _journalArticleService.updateArticleTranslation(
					article.getGroupId(), article.getArticleId(),
					article.getVersion(), targetLocale, translatedTitle,
					translatedDescription, translatedContent, null,
					ServiceContextThreadLocal.getServiceContext());
			}
		}
		catch (DocumentException documentException) {
			throw new PortalException(documentException);
		}

		return article;
	}

	private String _getTranslatedContent(
		String content, Document document, String fieldName,
		Locale targetLocale) {

		Element rootElement = document.getRootElement();

		String availableLanguageIds = rootElement.attributeValue(
			"available-locales");

		if (!availableLanguageIds.contains(targetLocale.toString())) {
			availableLanguageIds += StringPool.COMMA + targetLocale.toString();
			rootElement.addAttribute("available-locales", availableLanguageIds);
		}

		List<Element> dynamicElementElements = rootElement.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			if (dynamicElementElement != null) {
				String attribute = dynamicElementElement.attributeValue(
					"name", StringPool.BLANK);

				if (Objects.equals(attribute, fieldName)) {
					Element replace = null;

					for (Element element :
							dynamicElementElement.elements("dynamic-content")) {

						String languageId = element.attributeValue(
							"language-id", StringPool.BLANK);

						if (Objects.equals(
								languageId,
								LocaleUtil.toLanguageId(targetLocale))) {

							replace = element;
						}
					}

					if (replace != null) {
						dynamicElementElement.remove(replace);
					}

					Element element = dynamicElementElement.addElement(
						"dynamic-content");

					element.addAttribute(
						"language-id", LocaleUtil.toLanguageId(targetLocale));
					element.addCDATA(content);
				}
			}
		}

		return document.asXML();
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