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

package com.liferay.fragment.util;

import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = HtmlParserUtil.class)
public class HtmlParserUtil {

	public Document parse(String html) throws FragmentEntryContentException {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getClass());

		Document document = null;

		try {
			Matcher matcher = _pattern.matcher(html.trim());

			if (!matcher.matches()) {
				html = "<span>" + html + "</span>";
			}

			document = SAXReaderUtil.read(html);
		}
		catch (DocumentException de) {
			throw new FragmentEntryContentException(
				LanguageUtil.get(
					resourceBundle, "fragment-entry-html-is-invalid"),
				de);
		}

		return document;
	}

	private static final Pattern _pattern = Pattern.compile(
		"^<(\\S+?)(.*?)>(.*?)</\\1>",
		Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);

}