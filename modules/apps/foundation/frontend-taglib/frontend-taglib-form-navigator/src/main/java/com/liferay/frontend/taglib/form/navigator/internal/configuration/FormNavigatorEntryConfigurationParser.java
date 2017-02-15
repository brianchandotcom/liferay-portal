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

package com.liferay.frontend.taglib.form.navigator.internal.configuration;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Alejandro Tardín
 */
@Component(
	configurationPid = "com.liferay.frontend.taglib.form.navigator.internal.configuration.FormNavigatorConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	service = FormNavigatorEntryConfigurationParser.class
)
public class FormNavigatorEntryConfigurationParser {

	public Optional<Set<String>> getFormNavigatorEntryKeys(
		String categoryKey, String context) {

		Set<String> formNavigatorEntryKeys = null;

		if (Validator.isNotNull(context)) {
			formNavigatorEntryKeys = _formNavigatorEntryKeysMap.get(
				context + StringPool.PERIOD + categoryKey);
		}

		if (formNavigatorEntryKeys == null) {
			formNavigatorEntryKeys = _formNavigatorEntryKeysMap.get(
				categoryKey);
		}

		return Optional.ofNullable(formNavigatorEntryKeys);
	}

	public String getFormNavigatorId() {
		return _formNavigatorConfiguration.formNavigatorId();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_formNavigatorConfiguration = ConfigurableUtil.createConfigurable(
			FormNavigatorConfiguration.class, properties);

		String[] formNavigatorEntryKeys =
			_formNavigatorConfiguration.formNavigatorEntryKeys();

		_formNavigatorEntryKeysMap = new HashMap<>();

		for (String line : formNavigatorEntryKeys) {
			Matcher matcher = _LINE_PATTERN.matcher(line);

			while (matcher.find()) {
				_formNavigatorEntryKeysMap.put(
					matcher.group("key").trim(),
					_splitKeys(matcher.group("value")));
			}
		}
	}

	private Set<String> _splitKeys(String formNavigatorEntryKeys) {
		Set<String> keys = new LinkedHashSet<>();

		Arrays.stream(StringUtil.split(formNavigatorEntryKeys)).map(
			String::trim).forEach(keys::add);

		return keys;
	}

	private static final Pattern _LINE_PATTERN = Pattern.compile(
		"^(?<key>.*)=(?<value>.*)$", Pattern.MULTILINE);

	private FormNavigatorConfiguration _formNavigatorConfiguration;
	private Map<String, Set<String>> _formNavigatorEntryKeysMap;

}