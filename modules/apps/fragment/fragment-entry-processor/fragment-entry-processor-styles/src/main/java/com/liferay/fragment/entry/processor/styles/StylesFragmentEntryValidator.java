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

package com.liferay.fragment.entry.processor.styles;

import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.processor.FragmentEntryValidator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;

import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "fragment.entry.processor.priority:Integer=7",
	service = FragmentEntryValidator.class
)
public class StylesFragmentEntryValidator implements FragmentEntryValidator {

	@Override
	public void validateFragmentEntryHTML(
			String html, String configuration, Locale locale)
		throws PortalException {

		Document document = _getDocument(html);

		Elements elements = document.select("[data-lfr-styles]");

		if (!elements.isEmpty() && (elements.size() > 1)) {
			throw new FragmentEntryContentException(
				_language.get(
					locale,
					"the-data-lfr-styles-attribute-can-be-used-only-once-on-" +
						"the-same-fragment"));
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

}