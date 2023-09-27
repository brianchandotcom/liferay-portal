/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mail.kernel.template;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Adolfo Pérez
 */
public class MailTemplateFactoryUtil {

	public static MailTemplate createMailTemplate(
		String template, boolean escapeHTML) {

		return _mailTemplateFactory.createMailTemplate(template, escapeHTML);
	}

	public static MailTemplateContextBuilder
		createMailTemplateContextBuilder() {

		return _mailTemplateFactory.createMailTemplateContextBuilder();
	}

	private static volatile MailTemplateFactory _mailTemplateFactory =
		ServiceProxyFactory.newServiceTrackedInstance(
			MailTemplateFactory.class, MailTemplateFactoryUtil.class,
			"_mailTemplateFactory", false);

}