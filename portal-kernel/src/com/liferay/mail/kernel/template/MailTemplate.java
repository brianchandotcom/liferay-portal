/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mail.kernel.template;

import java.io.IOException;
import java.io.Writer;

import java.util.Locale;

/**
 * @author Adolfo Pérez
 */
public interface MailTemplate {

	public void render(
			Writer writer, Locale locale,
			MailTemplateContext mailTemplateContext)
		throws IOException;

	public String renderAsString(
			Locale locale, MailTemplateContext mailTemplateContext)
		throws IOException;

}