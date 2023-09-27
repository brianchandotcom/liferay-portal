/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mail.template.internal;

import com.liferay.mail.kernel.template.MailTemplate;
import com.liferay.mail.kernel.template.MailTemplateContext;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
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

			String valueString;

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