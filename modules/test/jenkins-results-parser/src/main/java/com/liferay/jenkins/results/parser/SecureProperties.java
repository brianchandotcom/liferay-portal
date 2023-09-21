/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.util.Properties;

/**
 * @author Peter Yoo
 */
public class SecureProperties extends Properties {

	public SecureProperties() {
	}

	public SecureProperties(Properties properties) {
		if (properties instanceof SecureProperties) {
			SecureProperties secureProperties = (SecureProperties)properties;

			for (Object key : properties.keySet()) {
				put(key, secureProperties.get(key, false));
			}

			return;
		}

		for (Object key : properties.keySet()) {
			put(key, properties.get(key));
		}
	}

	@Override
	public synchronized Object get(Object key) {
		return get(key, true);
	}

	public synchronized Object get(Object key, boolean getSecret) {
		String value = (String)super.get(key);

		if (!getSecret) {
			return value;
		}

		if (!JenkinsResultsParserUtil.isNullOrEmpty(value) &&
			SecretsUtil.isSecretProperty(value)) {

			value = SecretsUtil.getSecret(value);

			put(key, value);
		}

		return value;
	}

	@Override
	public String getProperty(String key) {
		return (String)get(key);
	}

}