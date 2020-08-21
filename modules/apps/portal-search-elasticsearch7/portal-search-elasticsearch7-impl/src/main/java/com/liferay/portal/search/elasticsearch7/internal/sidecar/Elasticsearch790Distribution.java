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
public class Elasticsearch790Distribution implements Distribution {

	@Override
	public Distributable getElasticsearchDistributable() {
		return new DistributableImpl(
			"https://artifacts.elastic.co/downloads/elasticsearch" +
				"/elasticsearch-oss-7.9.0-no-jdk-linux-x86_64.tar.gz",
			"6ec0bbb5a9f2bbf7bf78aea2999be2216f47b9f96caba27f99d40bdcdbd9f78f" +
				"55e804643240fa97438cef375f4f7237860998e1d63fc401b8930d64edde" +
					"a800");
	}

	@Override
	public List<Distributable> getPluginDistributables() {
		return Arrays.asList(
			new DistributableImpl(
				"https://artifacts.elastic.co/downloads/elasticsearch-plugins" +
					"/analysis-icu/analysis-icu-7.9.0.zip",
				"c096feab6658c8340c16720a5527de8100061e5844af1e86bfc2d6d1954d" +
					"afe9836fef1e562831ceb3711f1d4856de916087fb417f087857062b" +
						"b7265a58210e"),
			new DistributableImpl(
				"https://artifacts.elastic.co/downloads/elasticsearch-plugins" +
					"/analysis-kuromoji/analysis-kuromoji-7.9.0.zip",
				"ab11c3dfbf45a84ec3ef7c5112d331ffe416a0c65a9286f1b1069616f293" +
					"e687ada461b17792bd72a81c503660ea92afa7d5accf2db5c5a2ece1" +
						"59062e484099"),
			new DistributableImpl(
				"https://artifacts.elastic.co/downloads/elasticsearch-plugins" +
					"/analysis-smartcn/analysis-smartcn-7.9.0.zip",
				"ce26697f6cbb2634aac44d5bcc93637b3a988d8f5af7960518b1cb88b222" +
					"d12ca60727f2090cf19408fc9d876629db2a0adb038e0654ffd32a8c" +
						"5296448a254f"),
			new DistributableImpl(
				"https://artifacts.elastic.co/downloads/elasticsearch-plugins" +
					"/analysis-stempel/analysis-stempel-7.9.0.zip",
				"96b66961307bf7c07cfd08162019f649673abf3fce8e33a8f56259784794" +
					"27b5066bb6d0c728c4d290ebe04f246d2ae1e432ad26dac83a0bfe7f" +
						"cba21691b67c"));
	}

}