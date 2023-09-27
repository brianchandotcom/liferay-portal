/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.service.test;

import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.Dictionary;

/**
 * @author Alicia García
 */
public class DLFileEntryServiceTestUtil {

	protected static ConfigurationTemporarySwapper
			getConfigurationTemporarySwapper(String key, Object value)
		throws Exception {

		Dictionary<String, Object> dictionary =
			HashMapDictionaryBuilder.<String, Object>put(
				key, value
			).build();

		return new ConfigurationTemporarySwapper(
			"com.liferay.document.library.configuration.DLConfiguration",
			dictionary);
	}

}