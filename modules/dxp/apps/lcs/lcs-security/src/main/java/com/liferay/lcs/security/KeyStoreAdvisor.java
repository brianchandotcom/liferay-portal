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

package com.liferay.lcs.security;

import com.liferay.portal.kernel.util.GetterUtil;

import java.security.KeyStore;

import java.util.Enumeration;

/**
 * @author Igor Beslic
 */
public class KeyStoreAdvisor {

	public String getKeyAlias(
			int buildNumber, String keyAlias, KeyStore keyStore)
		throws Exception {

		if (buildNumber == 0) {
			return keyAlias;
		}

		Enumeration<String> aliases = keyStore.aliases();

		int compatibleNumberSuffix = 0;

		while (aliases.hasMoreElements()) {
			String alias = aliases.nextElement();

			if (alias.equals(keyAlias)) {
				continue;
			}

			if (!alias.startsWith(keyAlias)) {
				continue;
			}

			int aliasNumberSuffix = GetterUtil.getInteger(
				alias.substring(keyAlias.length()));

			if (buildNumber == aliasNumberSuffix) {
				return alias;
			}

			if (buildNumber < aliasNumberSuffix) {
				continue;
			}

			if ((buildNumber > aliasNumberSuffix) &&
				(compatibleNumberSuffix < aliasNumberSuffix)) {

				compatibleNumberSuffix = aliasNumberSuffix;

				continue;
			}
		}

		if (compatibleNumberSuffix == 0) {
			return keyAlias;
		}

		return keyAlias + compatibleNumberSuffix;
	}

	public String getLatestKeyAlias(String keyAlias, KeyStore keyStore)
		throws Exception {

		Enumeration<String> aliases = keyStore.aliases();

		int latestNumberSuffix = 0;

		while (aliases.hasMoreElements()) {
			String alias = aliases.nextElement();

			if (alias.equals(keyAlias)) {
				continue;
			}

			if (!alias.startsWith(keyAlias)) {
				continue;
			}

			int aliasNumberSuffix = GetterUtil.getInteger(
				alias.substring(keyAlias.length()));

			if (latestNumberSuffix < aliasNumberSuffix) {
				latestNumberSuffix = aliasNumberSuffix;
			}
		}

		if (latestNumberSuffix == 0) {
			return keyAlias;
		}

		return keyAlias + latestNumberSuffix;
	}

}