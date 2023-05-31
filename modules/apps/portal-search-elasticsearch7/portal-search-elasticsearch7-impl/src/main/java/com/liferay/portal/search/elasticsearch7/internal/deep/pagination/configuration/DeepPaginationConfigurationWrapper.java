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

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.search.elasticsearch7.configuration.DeepPaginationConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Gustavo Lima
 */
@Component(
	configurationPid = "com.liferay.portal.search.elasticsearch7.configuration.DeepPaginationConfiguration",
	service = DeepPaginationConfigurationWrapper.class
)
public class DeepPaginationConfigurationWrapper {

	public boolean getEnableDeepPagination() {
		return _deepPaginationConfiguration.enableDeepPagination();
	}

	public int getPointInTimeKeepAliveSeconds() {
		return _validatePointInTimeKeepAliveSeconds(
			_deepPaginationConfiguration.pointInTimeKeepAliveSeconds());
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> map) {
		_deepPaginationConfiguration = ConfigurableUtil.createConfigurable(
			DeepPaginationConfiguration.class, map);
	}

	private int _validatePointInTimeKeepAliveSeconds(
		int pointInTimeKeepAliveSeconds) {

		if ((pointInTimeKeepAliveSeconds > 0) &&
			(pointInTimeKeepAliveSeconds <= 60)) {

			return pointInTimeKeepAliveSeconds;
		}

		return _DEFAULT_POINT_IN_TIME_KEEP_ALIVE_SECONDS;
	}

	private static final int _DEFAULT_POINT_IN_TIME_KEEP_ALIVE_SECONDS = 60;

	private volatile DeepPaginationConfiguration _deepPaginationConfiguration;

}