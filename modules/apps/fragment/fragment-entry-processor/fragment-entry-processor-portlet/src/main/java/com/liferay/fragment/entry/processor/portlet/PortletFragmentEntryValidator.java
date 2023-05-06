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

package com.liferay.fragment.entry.processor.portlet;

import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.processor.FragmentEntryValidator;
import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.ModelHintsConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "fragment.entry.processor.priority:Integer=3",
	service = FragmentEntryValidator.class
)
public class PortletFragmentEntryValidator implements FragmentEntryValidator {

	@Override
	public void validateFragmentEntryHTML(
			String html, String configuration, Locale locale)
		throws PortalException {

		Document document = _getDocument(html);

		for (Element element : document.select("*")) {
			String htmlTagName = element.tagName();

			if (!StringUtil.startsWith(htmlTagName, "lfr-widget-")) {
				continue;
			}

			String alias = StringUtil.removeSubstring(
				htmlTagName, "lfr-widget-");

			if (Validator.isNull(_portletRegistry.getPortletName(alias))) {
				throw new FragmentEntryContentException(
					_language.format(
						locale, "there-is-no-widget-available-for-alias-x",
						alias));
			}

			String id = element.id();

			if (Validator.isNotNull(id) && !Validator.isAlphanumericName(id)) {
				throw new FragmentEntryContentException(
					_language.format(
						locale,
						"widget-id-must-contain-only-alphanumeric-characters",
						alias));
			}

			if (Validator.isNotNull(id)) {
				Elements elements = document.select("#" + id);

				if (elements.size() > 1) {
					throw new FragmentEntryContentException(
						_language.get(locale, "widget-id-must-be-unique"));
				}

				if (id.length() > GetterUtil.getInteger(
						ModelHintsConstants.TEXT_MAX_LENGTH)) {

					throw new FragmentEntryContentException(
						_language.format(
							locale, "widget-id-cannot-exceed-x-characters",
							ModelHintsConstants.TEXT_MAX_LENGTH));
				}
			}

			Elements elements = document.select(htmlTagName);

			if ((elements.size() > 1) && Validator.isNull(id)) {
				throw new FragmentEntryContentException(
					_language.get(
						locale,
						"duplicate-widgets-within-the-same-fragment-must-" +
							"have-an-id"));
			}

			if (elements.size() > 1) {
				Portlet portlet = _portletLocalService.getPortletById(
					_portletRegistry.getPortletName(alias));

				if (!portlet.isInstanceable()) {
					throw new FragmentEntryContentException(
						_language.format(
							locale,
							"you-cannot-add-the-widget-x-more-than-once",
							alias));
				}
			}
		}
	}

	private Document _getDocument(String html) {
		Document document = Jsoup.parseBodyFragment(html);

		Document.OutputSettings outputSettings = new Document.OutputSettings();

		outputSettings.prettyPrint(false);

		document.outputSettings(outputSettings);

		return document;
	}

	@Reference
	private Language _language;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private PortletRegistry _portletRegistry;

}