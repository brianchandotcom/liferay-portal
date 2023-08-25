/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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