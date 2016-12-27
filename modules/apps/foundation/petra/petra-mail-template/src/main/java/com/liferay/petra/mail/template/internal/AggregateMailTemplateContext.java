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

import com.liferay.mail.kernel.template.MailTemplateContext;
import com.liferay.portal.kernel.util.EscapableLocalizableFunction;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class AggregateMailTemplateContext implements MailTemplateContext {

	public AggregateMailTemplateContext(
		MailTemplateContext... mailTemplateContexts) {

		Map<String, EscapableLocalizableFunction> replacements =
			new HashMap<>();

		for (MailTemplateContext mailTemplateContext : mailTemplateContexts) {
			replacements.putAll(mailTemplateContext.getReplacements());
		}

		_replacements = Collections.unmodifiableMap(replacements);
	}

	@Override
	public MailTemplateContext aggregateWith(
		MailTemplateContext mailTemplateContext) {

		return new AggregateMailTemplateContext(this, mailTemplateContext);
	}

	@Override
	public Map<String, EscapableLocalizableFunction> getReplacements() {
		return _replacements;
	}

	private final Map<String, EscapableLocalizableFunction> _replacements;

}