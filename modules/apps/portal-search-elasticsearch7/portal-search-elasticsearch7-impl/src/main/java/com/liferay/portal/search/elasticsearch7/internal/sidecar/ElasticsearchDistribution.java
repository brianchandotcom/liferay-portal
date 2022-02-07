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
 * @author Bryan Engler
 */
public class ElasticsearchDistribution implements Distribution {

	@Override
	public Distributable getElasticsearchDistributable() {
		return new DistributableImpl(
			"https://artifacts.elastic.co/downloads/elasticsearch" +
				"/elasticsearch-oss-7.10.2-no-jdk-linux-x86_64.tar.gz",
			_ELASTICSEARCH_CHECKSUM);
	}

	@Override
	public List<Distributable> getPluginDistributables() {
		return Arrays.asList(
			new DistributableImpl(
				"https://artifacts.elastic.co/downloads/elasticsearch-plugins" +
					"/analysis-icu/analysis-icu-7.10.2.zip",
				_ICU_CHECKSUM),
			new DistributableImpl(
				"https://artifacts.elastic.co/downloads/elasticsearch-plugins" +
					"/analysis-kuromoji/analysis-kuromoji-7.10.2.zip",
				_KUROMOJI_CHECKSUM),
			new DistributableImpl(
				"https://artifacts.elastic.co/downloads/elasticsearch-plugins" +
					"/analysis-smartcn/analysis-smartcn-7.10.2.zip",
				_SMARTCN_CHECKSUM),
			new DistributableImpl(
				"https://artifacts.elastic.co/downloads/elasticsearch-plugins" +
					"/analysis-stempel/analysis-stempel-7.10.2.zip",
				_STEMPEL_CHECKSUM));
	}

	private static final String _ELASTICSEARCH_CHECKSUM =
		"7b63237996569ccdc7c9d9e7cc097fcb23865396eddac30e5f02543484220d2fc70a" +
			"7285b430877e5e76a5d8716d9682de9fc40d5e57a08f331e82011fc59756";

	private static final String _ICU_CHECKSUM =
		"ae2c92baf202d50a652b544b6281086749c871ac98fa581e4a9869063d1ff96df62d" +
			"db501838ee10025a288fb6125b070f599c0e738a8db711b8beb62a4683d9";

	private static final String _KUROMOJI_CHECKSUM =
		"3bd9296a4b00f76974e2d45ddd6de79113a6a2d42b05c0fb8df2f9ee8264a93709de" +
			"fec66a16d6dea5dd40b8ea719827aa80be0afec4eee80bfca1bfee6fb33f";

	private static final String _SMARTCN_CHECKSUM =
		"d8e62d60178606f3148d86f46e90e9375808299f6ef945eea5d3df814f13658203ec" +
			"6c20376c1583cb133840f05c9d38a5cd3af811cd64a356ce81a2c18a9c6a";

	private static final String _STEMPEL_CHECKSUM =
		"5dd8fddfe4e056966695a920b55ed4f349904195fefabd291794ca8cd351379001c3" +
			"5a05384a9e85c8c799559833885a188b21f63ab0b8da14e51df431e07fdf";

}