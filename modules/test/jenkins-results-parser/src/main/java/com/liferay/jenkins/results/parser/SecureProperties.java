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
				put(key, secureProperties._get(key, false));
			}

			return;
		}

		for (Object key : properties.keySet()) {
			put(key, properties.get(key));
		}
	}

	@Override
	public synchronized Object get(Object key) {
		return _get(key, true);
	}

	@Override
	public String getProperty(String key) {
		return (String)get(key);
	}

	private synchronized Object _get(Object key, boolean getSecrets) {
		String value = (String)super.get(key);

		if (getSecrets && SecretsUtil.isSecretProperty(value)) {
			value = SecretsUtil.getSecret(value);
			//System.out.println(key + "=" + value);

			put(key, value);
		}

		return value;
	}

}