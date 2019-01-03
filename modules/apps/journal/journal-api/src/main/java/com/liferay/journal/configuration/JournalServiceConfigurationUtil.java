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

package com.liferay.journal.configuration;

import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

/**
 * @author     Eduardo García
 * @deprecated As of Judson (7.1.x), see {@link JournalServiceConfiguration}
 */
@Deprecated
public class JournalServiceConfigurationUtil {

	public static String get(String key) {
		return _configuration.get(key);
	}

	public static String get(String key, Filter filter) {
		return _configuration.get(key, filter);
	}

	public static String[] getArray(String key) {
		return _configuration.getArray(key);
	}

	public static String getContent(String location) {
		try {
			return StringUtil.read(
				JournalServiceConfigurationUtil.class.getClassLoader(),
				location);
		}
		catch (IOException ioe) {
			return null;
		}
	}

	private static final Configuration _configuration =
		ConfigurationFactoryUtil.getConfiguration(
			JournalServiceConfigurationUtil.class.getClassLoader(), "portlet");

}