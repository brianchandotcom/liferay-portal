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

package com.liferay.portal.vulcan.internal.configuration.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.vulcan.internal.configuration.VulcanConfiguration;

import java.util.Dictionary;
import java.util.HashSet;
import java.util.Set;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Javier Gamarra
 */
public class ConfigurationUtil {

	public static Set<String> getExcludedOperationIds(
		ConfigurationAdmin configurationAdmin, String path) {

		try {
			String filterString = String.format(
				"(&(path=%s)(service.factoryPid=%s))", path,
				VulcanConfiguration.class.getName());

			Configuration[] configurations =
				configurationAdmin.listConfigurations(filterString);

			if (configurations != null) {
				for (Configuration configuration : configurations) {
					return _getExcludedOperationIds(configuration);
				}
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return new HashSet<>();
	}

	private static Set<String> _getExcludedOperationIds(
		Configuration configuration) {

		Dictionary<String, Object> properties = configuration.getProperties();

		String excludedOperationIds = GetterUtil.getString(
			properties.get("excludedOperationIds"));

		return SetUtil.fromArray(excludedOperationIds.split(","));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ConfigurationUtil.class);

}