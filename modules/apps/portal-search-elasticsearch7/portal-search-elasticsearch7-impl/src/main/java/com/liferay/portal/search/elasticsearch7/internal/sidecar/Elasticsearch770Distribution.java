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

import java.util.Arrays;
import java.util.List;

/**
 * @author André de Oliveira
 */
public class Elasticsearch770Distribution implements Distribution {

	@Override
	public Distributable getElasticsearchDistributable() {
		return new DistributableImpl(
			"https://artifacts.elastic.co/downloads/elasticsearch" +
				"/elasticsearch-oss-7.7.0-no-jdk-linux-x86_64.tar.gz",
			"f9b323cff078a0dc856f99efd164775a9cd0718bb0ddb6c4d4817fa53cae7174" +
				"8f1aec5158afa45d4b29076043cbada9743a996e685439436e93d94a9eff" +
					"2768");
	}

	@Override
	public List<Distributable> getPluginDistributables() {
		return Arrays.asList(
			new DistributableImpl(
				"https://artifacts.elastic.co/downloads/elasticsearch-plugins" +
					"/analysis-icu/analysis-icu-7.7.0.zip",
				"c00f41acb5adac23bce4e93720e9286559d05836e41c82f5f48da6ac5d3f" +
					"c7b7c876bd8881af96ee2ef34afa3f430e02d572038b46d68aa42072" +
						"f31545d6727f"),
			new DistributableImpl(
				"https://artifacts.elastic.co/downloads/elasticsearch-plugins" +
					"/analysis-kuromoji/analysis-kuromoji-7.7.0.zip",
				"7b3416a8584df3ba7218bcf448436fef231ad88c8f5a4e6ee68b29b97a6b" +
					"7973067209be6b42443e2d13b1cc8e21999aa8e43b9e5b2f31619dbf" +
						"31f03cfe7527"),
			new DistributableImpl(
				"https://artifacts.elastic.co/downloads/elasticsearch-plugins" +
					"/analysis-smartcn/analysis-smartcn-7.7.0.zip",
				"cf782aea55584673077ec012c360177949d95bab87165e0a0edb91be639c" +
					"0e49ae2fc5f48baddef1fbd4a7c84664e80ed4480b82ef8a6d4f6798" +
						"60a4cc215eff"),
			new DistributableImpl(
				"https://artifacts.elastic.co/downloads/elasticsearch-plugins" +
					"/analysis-stempel/analysis-stempel-7.7.0.zip",
				"7874416a73522a1b7f9cb4ae803b34ba9565afd17d44c8d0284e5fb625e6" +
					"fddaf51c4f19fae95bc25442284c92a10f52191d8b509e2f34f75e90" +
						"dcc2e7f701e6"));
	}

}