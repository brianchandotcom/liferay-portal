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
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.Objects;
import java.util.ResourceBundle;

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
			if (Objects.equals(HtmlUtil.stripHtml(html), html)) {
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

}