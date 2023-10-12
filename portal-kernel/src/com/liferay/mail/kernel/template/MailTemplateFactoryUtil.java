/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mail.kernel.template;

import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * @author Adolfo Pérez
 */
public class MailTemplateFactoryUtil {

	public static MailTemplate createMailTemplate(
		String template, boolean escapeHTML) {

		MailTemplateFactory mailTemplateFactory =
			_mailTemplateFactorySnapshot.get();

		return mailTemplateFactory.createMailTemplate(template, escapeHTML);
	}

	public static MailTemplateContextBuilder
		createMailTemplateContextBuilder() {

		MailTemplateFactory mailTemplateFactory =
			_mailTemplateFactorySnapshot.get();

		return mailTemplateFactory.createMailTemplateContextBuilder();
	}

	private static final Snapshot<MailTemplateFactory>
		_mailTemplateFactorySnapshot = new Snapshot<>(
			MailTemplateFactoryUtil.class, MailTemplateFactory.class);

}