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

package com.liferay.portal.search.elasticsearch7.internal.deep.pagination.configuration;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.search.elasticsearch7.configuration.DeepPaginationConfiguration;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gustavo Lima
 */
@Component(
	configurationPid = "com.liferay.portal.search.elasticsearch7.configuration.DeepPaginationConfiguration",
	service = DeepPaginationConfigurationWrapper.class
)
public class DeepPaginationConfigurationWrapper {

	public DeepPaginationConfiguration getDeepPaginationConfiguration(
		long companyId) {

		try {
			DeepPaginationConfiguration deepPaginationConfiguration =
				_configurationProvider.getSystemConfiguration(
					DeepPaginationConfiguration.class);

			if (!deepPaginationConfiguration.enableDeepPagination()) {
				return _configurationProvider.getCompanyConfiguration(
					DeepPaginationConfiguration.class, companyId);
			}

			return deepPaginationConfiguration;
		}
		catch (ConfigurationException configurationException) {
			return ReflectionUtil.throwException(configurationException);
		}
	}

	public int getPointInTimeKeepAliveSeconds() {
		return _validatePointInTimeKeepAliveSeconds(
			_deepPaginationConfiguration.pointInTimeKeepAliveSeconds());
	}

	public boolean isEnableDeepPagination(long companyId) {
		_deepPaginationConfiguration = getDeepPaginationConfiguration(
			companyId);

		return _deepPaginationConfiguration.enableDeepPagination();
	}

	private int _validatePointInTimeKeepAliveSeconds(
		int pointInTimeKeepAliveSeconds) {

		if ((pointInTimeKeepAliveSeconds > 0) &&
			(pointInTimeKeepAliveSeconds <= 60)) {

			return pointInTimeKeepAliveSeconds;
		}

		return 60;
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	private volatile DeepPaginationConfiguration _deepPaginationConfiguration;

}