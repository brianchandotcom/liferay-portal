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

package com.liferay.segments.context.vocabulary.internal.configuration.admin.definition;

import com.liferay.configuration.admin.definition.ConfigurationFieldOptionsProvider;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.CamelCaseUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.segments.context.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Raymond Augé
 */
@Component(
	immediate = true,
	property = {
		"configuration.field.name=contextName",
		"configuration.pid=com.liferay.segments.context.vocabulary.internal.configuration.SegmentsContextVocabularyConfiguration"
	},
	service = ConfigurationFieldOptionsProvider.class
)
public class ContextNameConfigurationFieldOptionsProvider
	implements ConfigurationFieldOptionsProvider {

	@Override
	public List<Option> getOptions() {
		List<Option> options = new ArrayList<>();

		for (String defaultField : _defaultFields) {
			options.add(new ContextOption(defaultField));
		}

		Stream<Option> stream = options.stream();

		return stream.sorted(
			(a, b) -> {
				String valueA = a.getValue();
				String valueB = b.getValue();

				return valueA.compareTo(valueB);
			}
		).collect(
			Collectors.toList()
		);
	}

	private static final List<String> _defaultFields = ListUtil.fromArray(
		Context.BROWSER, Context.DEVICE_BRAND, Context.DEVICE_MODEL,
		Context.DEVICE_SCREEN_RESOLUTION_HEIGHT,
		Context.DEVICE_SCREEN_RESOLUTION_WIDTH, Context.HOSTNAME,
		Context.LANGUAGE_ID, Context.URL, Context.USER_AGENT);

	private class ContextOption implements Option {

		public ContextOption(String value) {
			_value = value;
		}

		@Override
		public String getLabel(Locale locale) {
			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", locale, getClass());

			return LanguageUtil.get(
				resourceBundle,
				"context." + CamelCaseUtil.fromCamelCase(_value));
		}

		@Override
		public String getValue() {
			return _value;
		}

		private final String _value;

	}

}