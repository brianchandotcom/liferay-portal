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

package com.liferay.layout.seo.internal.template;

import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.layout.seo.template.LayoutSEOTemplateProcessor;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(service = LayoutSEOTemplateProcessor.class)
public class LayoutSEOTemplateProcessorImpl
	implements LayoutSEOTemplateProcessor {

	@Override
	public String processTemplate(
		String template, InfoItemFieldValues infoItemFieldValues,
		Locale locale) {

		if ((infoItemFieldValues == null) || Validator.isNull(template)) {
			return null;
		}

		StringBuffer sb = new StringBuffer();

		Matcher matcher = _pattern.matcher(template);

		while (matcher.find()) {
			String variableName = matcher.group(1);

			InfoFieldValue<Object> infoFieldValue =
				infoItemFieldValues.getInfoFieldValue(variableName);

			if (infoFieldValue != null) {
				matcher.appendReplacement(
					sb,
					Matcher.quoteReplacement(
						String.valueOf(infoFieldValue.getValue(locale))));
			}
			else {
				matcher.appendReplacement(
					sb, Matcher.quoteReplacement(variableName));
			}
		}

		matcher.appendTail(sb);

		return sb.toString();
	}

	private static final Pattern _pattern = Pattern.compile("\\$\\{([^}]+)\\}");

}