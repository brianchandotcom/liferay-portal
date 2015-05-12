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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
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

		String[] availableLanguageIds = StringUtil.split(
			rootElement.attributeValue("available-locales", defaultLanguageId));

		String articleDefaultLanguageId = rootElement.attributeValue(
			"default-locale", defaultLanguageId);

		if (!ArrayUtil.contains(availableLanguageIds, languageId, true)) {
			filterByLanguage(
				rootElement, articleDefaultLanguageId, defaultLanguageId);
		}
		else {
			filterByLanguage(rootElement, languageId, defaultLanguageId);
		}
	}

	protected void filterByLanguage(
		Element root, String languageId, String defaultLanguageId) {

		Element defaultLanguageElement = null;

		boolean hasLanguageIdElement = false;

		for (Element element : root.elements()) {
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
		}

		if (!hasLanguageIdElement && (defaultLanguageElement != null)) {
			root.add(defaultLanguageElement);

			filterByLanguage(
				defaultLanguageElement, languageId, defaultLanguageId);
		}
	}

	protected void filterByLocalizability(
		Document document, Map<String, String> tokens) {

		try {
			long ddmStructureId = GetterUtil.getLong(
				tokens.get("ddm_structure_id"));

			DDMStructure ddmStructure =
				DDMStructureLocalServiceUtil.fetchDDMStructure(ddmStructureId);

			if (ddmStructure == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"DDM structure is not available. Not checking " +
							"localizibilty.");
				}

				return;
			}

			Element rootElement = document.getRootElement();

			String defaultLanguageId = LocaleUtil.toLanguageId(
				LocaleUtil.getSiteDefault());

			String articleDefaultLanguageId = rootElement.attributeValue(
				"default-locale", defaultLanguageId);

			filterByLocalizability(
				rootElement, articleDefaultLanguageId, ddmStructure);
		}
		catch (PortalException pe) {
			_log.error(pe);
		}
		catch (NullPointerException npe) {
			_log.error(npe);
		}
	}

	protected void filterByLocalizability(
			Element root, String defaultLanguageId, DDMStructure ddmStructure)
		throws PortalException {

		for (Element element : root.elements("dynamic-element")) {
			String name = element.attributeValue("name");

			if (!ddmStructure.hasField(name)) {
				continue;
			}

			if (!ddmStructure.isFieldTransient(name)) {
				filterFields(element, ddmStructure, name, defaultLanguageId);
			}

			filterByLocalizability(element, defaultLanguageId, ddmStructure);
		}
	}

	protected void filterFields(
			Element dynamicElementElement, DDMStructure ddmStructure,
			String name, String defaultLanguageId)
		throws PortalException {

		boolean localizable = GetterUtil.getBoolean(
			ddmStructure.getFieldProperty(name, "localizable"));

		List<Element> dynamicContentElements = dynamicElementElement.elements(
			"dynamic-content");

		for (Element dynamicContentElement : dynamicContentElements) {
			String languageId = dynamicContentElement.attributeValue(
				"language-id");

			if (!localizable && !languageId.equals(defaultLanguageId)) {
				dynamicElementElement.remove(dynamicContentElement);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LocaleTransformerListener.class);

}