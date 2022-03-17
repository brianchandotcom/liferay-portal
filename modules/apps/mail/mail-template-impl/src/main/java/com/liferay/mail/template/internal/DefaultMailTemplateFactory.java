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