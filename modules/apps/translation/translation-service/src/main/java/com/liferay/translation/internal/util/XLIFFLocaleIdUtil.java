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

package com.liferay.translation.internal.util;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;

import java.util.Optional;
import java.util.function.Function;

import net.sf.okapi.common.LocaleId;

/**
 * @author Adolfo Pérez
 */
public class XLIFFLocaleIdUtil {

	public static LocaleId getSourceLocaleId(Document document) {
		return _getLocaleId(document, "srcLang", "source-language");
	}

	public static LocaleId getTargetLocaleId(Document document) {
		return _getLocaleId(document, "trgLang", "target-language");
	}

	private static <T> Optional<T> _getAttributeValueOptional(
		Element element, String attributeName, Function<String, T> function) {

		if (element == null) {
			return Optional.empty();
		}

		Attribute attribute = element.attribute(attributeName);

		if (attribute == null) {
			return Optional.empty();
		}

		return Optional.of(function.apply(attribute.getValue()));
	}

	private static LocaleId _getLocaleId(
		Document document, String attributeName,
		String alternateAttributeName) {

		Element rootElement = document.getRootElement();

		return _getAttributeValueOptional(
			rootElement, attributeName, LocaleId::fromString
		).orElseGet(
			() -> _getAttributeValueOptional(
				rootElement.element("file"), alternateAttributeName,
				LocaleId::fromString
			).orElse(
				_defaultLocaleId
			)
		);
	}

	private static final LocaleId _defaultLocaleId = LocaleId.fromString(
		LocaleUtil.toLanguageId(LocaleUtil.getDefault()));

}