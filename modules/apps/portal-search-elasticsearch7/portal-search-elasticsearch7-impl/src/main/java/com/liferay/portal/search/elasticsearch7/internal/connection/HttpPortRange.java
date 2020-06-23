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

package com.liferay.portal.search.elasticsearch7.internal.connection;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationWrapper;

import java.util.Objects;

/**
 * @author André de Oliveira
 */
public class HttpPortRange {

	public static final String AUTO = "AUTO";

	public HttpPortRange(
		ElasticsearchConfigurationWrapper elasticsearchConfigurationWrapper) {

		String httpPort = getHttpPort(elasticsearchConfigurationWrapper);

		_httpPort = httpPort;
	}

	public HttpPortRange(String httpPort) {
		_httpPort = httpPort;
	}

	public String toSettingsString() {
		return _httpPort;
	}

	protected static String getHttpPort(
		ElasticsearchConfigurationWrapper elasticsearchConfigurationWrapper) {

		String sidecarHttpPort =
			elasticsearchConfigurationWrapper.sidecarHttpPort();

		if (Objects.equals(sidecarHttpPort, HttpPortRange.AUTO)) {
			return "9201-9300";
		}

		if (!Validator.isBlank(sidecarHttpPort)) {
			return sidecarHttpPort;
		}

		return String.valueOf(
			elasticsearchConfigurationWrapper.embeddedHttpPort());
	}

	private final String _httpPort;

}