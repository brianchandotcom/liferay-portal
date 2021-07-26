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
			DL_CONFIGURATION_PID, dictionary);
	}

	protected static final String DL_CONFIGURATION_PID =
		"com.liferay.document.library.configuration.DLConfiguration";

}