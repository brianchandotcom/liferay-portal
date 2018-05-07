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

package com.liferay.journal.internal.transformer;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.templateparser.BaseTransformerListener;
import com.liferay.portal.kernel.templateparser.TransformerListener;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + JournalPortletKeys.JOURNAL,
	service = TransformerListener.class
)
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

	protected void filter(
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

	protected void filterByLanguage(Document document, String languageId) {
		Element rootElement = document.getRootElement();

		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());

		String articleDefaultLanguageId = rootElement.attributeValue(
			"default-locale", defaultLanguageId);

		String[] availableLanguageIds = StringUtil.split(
			rootElement.attributeValue(
				"available-locales", articleDefaultLanguageId));

		if (!ArrayUtil.contains(availableLanguageIds, languageId, true)) {
			filterByLanguage(
				rootElement, articleDefaultLanguageId,
				articleDefaultLanguageId);
		}
		else {
			filterByLanguage(rootElement, languageId, articleDefaultLanguageId);
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
				if (Validator.isNotNull(
						element.attributeValue(
							"language-id", StringPool.BLANK))) {

					hasLanguageIdElement = true;
				}

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
				_ddmStructureLocalService.fetchDDMStructure(ddmStructureId);

			if (ddmStructure == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Not checking localization because dynamic data " +
							"mapping structure is not available");
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
		catch (NullPointerException npe) {
			_log.error("Unable to filter by localizability", npe);
		}
		catch (PortalException pe) {
			_log.error("Unable to filter by localizability", pe);
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
				filter(element, ddmStructure, name, defaultLanguageId);
			}

			filterByLocalizability(element, defaultLanguageId, ddmStructure);
		}
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LocaleTransformerListener.class);

	private DDMStructureLocalService _ddmStructureLocalService;

}