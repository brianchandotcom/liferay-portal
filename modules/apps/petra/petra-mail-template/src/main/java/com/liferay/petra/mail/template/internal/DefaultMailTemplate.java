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

package com.liferay.petra.mail.template.internal;

import com.liferay.mail.kernel.template.MailTemplate;
import com.liferay.mail.kernel.template.MailTemplateContext;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.util.EscapableLocalizableFunction;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.Writer;

import java.util.Locale;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class DefaultMailTemplate implements MailTemplate {

	public DefaultMailTemplate(String template, boolean escapeHtml) {
		_template = template;
		_escapeHtml = escapeHtml;
	}

	@Override
	public void render(
			Writer writer, Locale locale,
			MailTemplateContext mailTemplateContext)
		throws IOException {

		Map<String, EscapableLocalizableFunction> replacements =
			mailTemplateContext.getReplacements();

		String content = _template;

		for (Map.Entry<String, EscapableLocalizableFunction> replacement :
				replacements.entrySet()) {

			EscapableLocalizableFunction value = replacement.getValue();

			final String valueString;

			if (_escapeHtml) {
				valueString = value.getEscapedValue(locale);
			}
			else {
				valueString = value.getOriginalValue(locale);
			}

			content = StringUtil.replace(
				content, replacement.getKey(), valueString);
		}

		EscapableLocalizableFunction escapableLocalizableFunction =
			replacements.get("[$PORTAL_URL$]");

		if (escapableLocalizableFunction != null) {
			String portalURL = escapableLocalizableFunction.getOriginalValue(
				locale);

			if (Validator.isNotNull(portalURL)) {
				content = StringUtil.replace(
					content, new String[] {"href=\"/", "src=\"/"},
					new String[] {
						"href=\"" + portalURL + "/", "src=\"" + portalURL + "/"
					});
			}
		}

		writer.append(content);
	}

	@Override
	public String renderAsString(
			Locale locale, MailTemplateContext mailTemplateContext)
		throws IOException {

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		render(unsyncStringWriter, locale, mailTemplateContext);

		return unsyncStringWriter.toString();
	}

	private final boolean _escapeHtml;
	private final String _template;

}