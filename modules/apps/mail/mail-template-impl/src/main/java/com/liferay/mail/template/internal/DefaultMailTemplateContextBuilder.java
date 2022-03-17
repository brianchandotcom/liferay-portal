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

import com.liferay.mail.kernel.template.MailTemplateContext;
import com.liferay.mail.kernel.template.MailTemplateContextBuilder;
import com.liferay.portal.kernel.util.EscapableLocalizableFunction;
import com.liferay.portal.kernel.util.EscapableObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class DefaultMailTemplateContextBuilder
	implements MailTemplateContextBuilder {

	@Override
	public MailTemplateContext build() {
		return new MailTemplateContextImpl(_replacements);
	}

	@Override
	public MailTemplateContextBuilder put(
		String placeholder,
		EscapableLocalizableFunction escapableLocalizableFunction) {

		_replacements.put(placeholder, escapableLocalizableFunction);

		return this;
	}

	@Override
	public MailTemplateContextBuilder put(
		String placeholder, EscapableObject<String> escapableObject) {

		put(
			placeholder,
			new EscapableLocalizableFunction(
				locale -> escapableObject.getOriginalValue(),
				escapableObject.isEscape()));

		return this;
	}

	@Override
	public MailTemplateContextBuilder put(String placeholder, String value) {
		put(placeholder, new EscapableObject<>(value, false));

		return this;
	}

	private final Map<String, EscapableLocalizableFunction> _replacements =
		new HashMap<>();

	private static class MailTemplateContextImpl
		implements MailTemplateContext {

		public MailTemplateContextImpl(
			Map<String, EscapableLocalizableFunction> placeholders) {

			_placeholders = new HashMap<>(placeholders);
		}

		@Override
		public MailTemplateContext aggregateWith(
			MailTemplateContext mailTemplateContext) {

			return new AggregateMailTemplateContext(this, mailTemplateContext);
		}

		@Override
		public Map<String, EscapableLocalizableFunction> getReplacements() {
			return Collections.unmodifiableMap(_placeholders);
		}

		private final Map<String, EscapableLocalizableFunction> _placeholders;

	}

}