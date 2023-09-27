/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mail.kernel.template;

import com.liferay.portal.kernel.util.EscapableLocalizableFunction;
import com.liferay.portal.kernel.util.EscapableObject;

/**
 * @author Adolfo Pérez
 */
public interface MailTemplateContextBuilder {

	public MailTemplateContext build();

	public MailTemplateContextBuilder put(
		String name, EscapableLocalizableFunction escapableLocalizableFunction);

	public MailTemplateContextBuilder put(
		String name, EscapableObject<String> escapableObject);

	public MailTemplateContextBuilder put(String name, String value);

}