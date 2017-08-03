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

package com.liferay.saml.runtime.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.runtime.internal.constants.LegacySamlPropsKeys;

import java.util.Dictionary;
import java.util.Objects;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Stian Sigvartsen
 * @author Tomas Polesovsky
 */
public class UpgradeSamlConfigurationPreferences extends BaseUpgradeSaml {

	public UpgradeSamlConfigurationPreferences(
		ConfigurationAdmin configurationAdmin, Props props) {

		_configurationAdmin = configurationAdmin;
		_props = props;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			Configuration configuration = _configurationAdmin.getConfiguration(
				"com.liferay.saml.runtime.configuration.SamlConfiguration",
				StringPool.QUESTION);

			Dictionary<String, Object> dictionary =
				configuration.getProperties();

			if (dictionary == null) {
				dictionary = new HashMapDictionary<>();
			}

			Filter filter = null;

			String entityId = _props.get(LegacySamlPropsKeys.SAML_ENTITY_ID);

			if (Validator.isNotNull(entityId)) {
				filter = new Filter(entityId);
			}

			for (String key : LegacySamlPropsKeys.SAML_KEYS_PROPS) {
				if (ArrayUtil.contains(
						LegacySamlPropsKeys.SAML_KEYS_DEPRECATED, key)) {

					continue;
				}

				String value = getPropsValue(_props, key, filter);

				if (value == null) {
					continue;
				}

				String defaultValue = getDefaultValue(key);

				if (!Objects.equals(value, defaultValue)) {
					dictionary.put(key, value);
				}
			}

			if (!dictionary.isEmpty()) {
				configuration.update(dictionary);
			}
		}
	}

	private final ConfigurationAdmin _configurationAdmin;
	private final Props _props;

}