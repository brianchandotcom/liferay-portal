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

package com.liferay.sync.web.internal.configuration;

import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.sync.util.SyncHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shinn Lok
 */
@Component(immediate = true, service = {})
public class SyncServiceConfigurationUtil {

	public static String get(String key) {
		return _configuration.get(key);
	}

	public static String[] getArray(String key) {
		return _configuration.getArray(key);
	}

	@Reference(unbind = "-")
	protected void setSyncHelper(SyncHelper syncHelper) {
		Class<?> clazz = syncHelper.getClass();

		_configuration = ConfigurationFactoryUtil.getConfiguration(
			clazz.getClassLoader(), "portlet");
	}

	private static Configuration _configuration;

}