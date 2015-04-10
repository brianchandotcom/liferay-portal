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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.templateparser.BaseTransformerListener;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;

import java.util.List;
import java.util.Map;

/**
 * @author Raymond Augé
 */
public class LocaleTransformerListener extends BaseTransformerListener {

	@Override
	public String onScript(
		String script, Document document, String languageId,
		Map<String, String> tokens) {

		if (_log.isDebugEnabled()) {
			_log.debug("onScript");
		}

		return StringUtil.replace(script, "@language_id@", languageId);
	}

	@Override
	public Document onXml(
		Document document, String languageId, Map<String, String> tokens) {

		if (_log.isDebugEnabled()) {
			_log.debug("onXml");
		}

		filterByLocalizability(document, tokens);

		filterByLanguage(document, languageId);

		return document;
	}

	protected void filterByLanguage(Document document, String languageId) {
		Element rootElement = document.getRootElement();

		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());

		String[] availableLocales = StringUtil.split(
			rootElement.attributeValue("available-locales", defaultLanguageId));

		String defaultLocale = rootElement.attributeValue(
			"default-locale", defaultLanguageId);

		boolean supportedLocale = false;

		for (String availableLocale : availableLocales) {
			if (StringUtil.equalsIgnoreCase(availableLocale, languageId)) {
				supportedLocale = true;

				break;
			}
		}

		if (!supportedLocale) {
			filterByLanguage(rootElement, defaultLocale, defaultLanguageId);
		}
		else {
			filterByLanguage(rootElement, languageId, defaultLanguageId);
		}
	}

	protected void filterByLanguage(
		Element root, String languageId, String defaultLanguageId) {

		Element defaultLanguageElement = null;

		boolean hasLanguageIdElement = false;

		List<Element> elements = root.elements();

		int listIndex = elements.size() - 1;

		while (listIndex >= 0) {
			Element element = elements.get(listIndex);

			String tempLanguageId = element.attributeValue(
				"language-id", languageId);

			if (StringUtil.equalsIgnoreCase(tempLanguageId, languageId)) {
				hasLanguageIdElement = true;

				filterByLanguage(element, languageId, defaultLanguageId);
			}
			else {
				if (StringUtil.equalsIgnoreCase(
						tempLanguageId, defaultLanguageId)) {

					defaultLanguageElement = element;
				}

				root.remove(element);
			}

			listIndex--;
		}

		if (!hasLanguageIdElement && (defaultLanguageElement != null)) {
			root.add(defaultLanguageElement);

			filterByLanguage(
				defaultLanguageElement, languageId, defaultLanguageId);
		}
	}

	protected void filterByLocalizability(
		Document document, Map<String, String> tokens) {

		long structureId = Long.parseLong(tokens.get("structure_id"));

		DDMStructure structure = null;

		try {
			structure = DDMStructureLocalServiceUtil.getDDMStructure(
				structureId);

			if (Validator.isNull(structure)) {
				return;
			}

			Element rootElement = document.getRootElement();

			String defaultLanguageId = LocaleUtil.toLanguageId(
				LocaleUtil.getSiteDefault());

			String defaultLocale = rootElement.attributeValue(
				"default-locale", defaultLanguageId);

			filterByLocalizability(rootElement, defaultLocale, structure);
		} catch (PortalException e) {
			e.printStackTrace();
		}
	}

	protected void filterByLocalizability(
			Element root, String defaultLanguageId, DDMStructure structure)
		throws PortalException {

		List<Element> elements = root.elements("dynamic-element");

		int listIndex = elements.size() - 1;

		while (listIndex >= 0) {
			Element element = elements.get(listIndex);

			String name = element.attributeValue("name");

			if (!structure.hasField(name)) {
				listIndex--;
				continue;
			}

			if (!structure.isFieldTransient(name)) {
				filterFields(element, structure, name, defaultLanguageId);
			}

			filterByLocalizability(element, defaultLanguageId, structure);

			listIndex--;
		}
	}

	protected void filterFields(
		Element dynamicElementElement, DDMStructure ddmStructure,
		String name, String defaultLanguageId) throws PortalException {

		boolean localizable = GetterUtil.getBoolean(
			ddmStructure.getFieldProperty(name, "localizable"));

		List<Element> dynamicContentElements = dynamicElementElement.elements(
			"dynamic-content");

		for (Element dynamicContentElement : dynamicContentElements) {
			String languageId = dynamicContentElement.attributeValue(
				"language-id");

			if (!localizable && !languageId.equals(defaultLanguageId)) {
				dynamicElementElement.remove(dynamicContentElement);

				continue;
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LocaleTransformerListener.class);

}