/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mail.template.internal;

import com.liferay.mail.kernel.template.MailTemplate;
import com.liferay.mail.kernel.template.MailTemplateContextBuilder;
import com.liferay.mail.kernel.template.MailTemplateFactory;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = Constants.SERVICE_RANKING + ":Integer=" + Integer.MIN_VALUE,
	service = MailTemplateFactory.class
)
public class DefaultMailTemplateFactory implements MailTemplateFactory {

	@Override
	public MailTemplate createMailTemplate(
		String template, boolean escapeHTML) {

		return new DefaultMailTemplate(template, escapeHTML);
	}

	@Override
	public MailTemplateContextBuilder createMailTemplateContextBuilder() {
		return new DefaultMailTemplateContextBuilder();
	}

}