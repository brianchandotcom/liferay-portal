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

package com.liferay.portal.search.elasticsearch.internal.connection.embedded;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch.settings.BaseSettingsContributor;
import com.liferay.portal.search.elasticsearch.settings.SettingsContributor;

import java.util.Map;

import org.elasticsearch.common.settings.ImmutableSettings;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration",
	immediate = true, property = {"operation.mode=EMBEDDED"},
	service = SettingsContributor.class
)
public class HttpSettingsContributor extends BaseSettingsContributor {

	public HttpSettingsContributor() {
		super(1);
	}

	@Override
	public int getPriority() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void populate(ImmutableSettings.Builder builder) {
		builder.put("http.enabled", _elasticsearchConfiguration.httpEnabled());

		if (!_elasticsearchConfiguration.httpEnabled()) {
			return;
		}

		builder.put(
			"http.cors.enabled", _elasticsearchConfiguration.httpCORSEnabled());

		if (!_elasticsearchConfiguration.httpCORSEnabled()) {
			return;
		}

		String[] httpCORSConfigurations =
			_elasticsearchConfiguration.httpCORSConfigurations();

		if (ArrayUtil.isEmpty(httpCORSConfigurations)) {
			return;
		}

		for (String httpCORSConfiguration : httpCORSConfigurations) {
			String[] httpCORSConfigurationPair = StringUtil.split(
				httpCORSConfiguration, StringPool.EQUAL);

			if (httpCORSConfigurationPair.length < 2) {
				continue;
			}

			builder.put(
				httpCORSConfigurationPair[0], httpCORSConfigurationPair[1]);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_elasticsearchConfiguration = Configurable.createConfigurable(
			ElasticsearchConfiguration.class, properties);
	}

	private volatile ElasticsearchConfiguration _elasticsearchConfiguration;

}