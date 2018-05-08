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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
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

	public Optional<List<String>> getFormNavigatorEntryKeys(
		String categoryKey, String context) {

		List<String> formNavigatorEntryKeys = null;

		if (Validator.isNotNull(categoryKey) && Validator.isNotNull(context)) {
			formNavigatorEntryKeys = _formNavigatorEntryKeysMap.get(
				context + StringPool.PERIOD + categoryKey);
		}
		else if (Validator.isNotNull(context)) {
			formNavigatorEntryKeys = _formNavigatorEntryKeysMap.get(context);
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
			Matcher matcher = _pattern.matcher(line);

			while (matcher.find()) {
				String key = matcher.group("key");
				String value = matcher.group("value");

				if (key == null) {
					key = StringPool.BLANK;
				}

				_formNavigatorEntryKeysMap.put(key.trim(), _splitKeys(value));
			}
		}
	}

	private List<String> _splitKeys(String formNavigatorEntryKeys) {
		Set<String> keys = new LinkedHashSet<>();

		for (String key : StringUtil.split(formNavigatorEntryKeys)) {
			keys.add(key.trim());
		}

		return new ArrayList<>(keys);
	}

	private static final Pattern _pattern = Pattern.compile(
		"^(?:(?<key>.*)=)?(?<value>.*)$", Pattern.MULTILINE);

	private FormNavigatorConfiguration _formNavigatorConfiguration;
	private Map<String, List<String>> _formNavigatorEntryKeysMap;

}