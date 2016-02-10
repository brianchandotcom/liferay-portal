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

package com.liferay.portal.tools.shard.builder.test.util;

import com.liferay.portal.tools.shard.builder.internal.util.PropsReader;

import java.io.IOException;

import java.net.URL;

import java.util.Properties;

import org.junit.Assume;

/**
 * @author Manuel de la Peña
 */
public class DBProviderTestUtil {

	public static Properties readProperties(String propertiesFileName)
		throws IOException {

		URL url = DBProviderTestUtil.class.getResource(
			"/" + propertiesFileName + ".properties");

		Assume.assumeNotNull(url);

		return PropsReader.read(url.getFile());
	}

}