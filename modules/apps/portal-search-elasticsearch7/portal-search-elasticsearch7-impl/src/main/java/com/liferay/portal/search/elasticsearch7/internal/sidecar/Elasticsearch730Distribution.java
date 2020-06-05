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
public class Elasticsearch730Distribution implements Distribution {

	@Override
	public Distributable getElasticsearchDistributable() {
		return new DistributableImpl(
			"https://artifacts.elastic.co/downloads/elasticsearch" +
				"/elasticsearch-oss-7.3.0-no-jdk-linux-x86_64.tar.gz",
			"97d279f03a398bf522f02e407e048b8ed408902f8d6bc43ff68069989ccd85fb" +
				"c4e9bab4fa0adebb479a940c5a531fc8a768b07fffbd172d9b51381a14d1" +
					"e479");
	}

	@Override
	public List<Distributable> getPluginDistributables() {
		return Arrays.asList(
			new DistributableImpl(
				"https://artifacts.elastic.co/downloads/elasticsearch-plugins" +
					"/analysis-icu/analysis-icu-7.3.0.zip",
				"474f6f4431ef3eab8e1d300d0b38f996cb3ef1bf51a1e4a23dd9bc4efadc" +
					"37a8b78675bd35bf2692b00697a9213ee64a1e5d93af3614ba113a7a" +
						"7c48227b6af7"),
			new DistributableImpl(
				"https://artifacts.elastic.co/downloads/elasticsearch-plugins" +
					"/analysis-kuromoji/analysis-kuromoji-7.3.0.zip",
				"748def5b3b9ff85fd2e205a5cb0fac723ae39956974a8e56009869b5c5fb" +
					"76b26e036c267b69bcf1704d41785b1d5b550e5dde5d887489335918" +
						"d4452b40d474"),
			new DistributableImpl(
				"https://artifacts.elastic.co/downloads/elasticsearch-plugins" +
					"/analysis-smartcn/analysis-smartcn-7.3.0.zip",
				"95af3804f22b88780e3d25da8d25e4358feba0b146c7555d7fced7108a22" +
					"2679cc6d60a515f6ed8f526b6512e023d2f5fe11c4a511281139fbf2" +
						"705ca8f3f978"),
			new DistributableImpl(
				"https://artifacts.elastic.co/downloads/elasticsearch-plugins" +
					"/analysis-stempel/analysis-stempel-7.3.0.zip",
				"5f88b009642b5ccaf5e342ca6de8029cadc72538e0654534fcb89bdaea30" +
					"3019906f46345df0f890d9680c8e222ad789134fbea3f6e31f82a8e3" +
						"0d25fb43989d"));
	}

}