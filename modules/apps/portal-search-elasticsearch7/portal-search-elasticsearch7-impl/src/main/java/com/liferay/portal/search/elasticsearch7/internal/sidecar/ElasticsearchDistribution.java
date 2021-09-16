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

package com.liferay.portal.search.elasticsearch7.internal.sidecar;

import com.liferay.petra.string.StringBundler;

import java.util.Arrays;
import java.util.List;

/**
 * @author Bryan Engler
 */
public class ElasticsearchDistribution implements Distribution {

	public ElasticsearchDistribution(String version) {
		_version = version;
	}

	@Override
	public Distributable getElasticsearchDistributable() {
		if (_version.startsWith("7.10")) {
			return new DistributableImpl(
				StringBundler.concat(
					"https://artifacts.elastic.co/downloads/elasticsearch",
					"/elasticsearch-oss-", _version,
					"-no-jdk-linux-x86_64.tar.gz"));
		}

		return new DistributableImpl(
			StringBundler.concat(
				"https://artifacts.elastic.co/downloads/elasticsearch",
				"/elasticsearch-", _version, "-no-jdk-linux-x86_64.tar.gz"));
	}

	@Override
	public List<Distributable> getPluginDistributables() {
		return Arrays.asList(
			new DistributableImpl(_getURL("analysis-icu")),
			new DistributableImpl(_getURL("analysis-kuromoji")),
			new DistributableImpl(_getURL("analysis-smartcn")),
			new DistributableImpl(_getURL("analysis-stempel")));
	}

	private String _getURL(String plugin) {
		return StringBundler.concat(
			"https://artifacts.elastic.co/downloads/elasticsearch-plugins/",
			plugin, "/", plugin, "-", _version, ".zip");
	}

	private final String _version;

}