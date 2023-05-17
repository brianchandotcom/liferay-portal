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

	public static final String VERSION = "7.17.10";

	@Override
	public Distributable getElasticsearchDistributable() {
		return new DistributableImpl(
			StringBundler.concat(
				"https://artifacts.elastic.co/downloads/elasticsearch",
				"/elasticsearch-", VERSION, "-no-jdk-linux-x86_64.tar.gz"),
			_ELASTICSEARCH_CHECKSUM);
	}

	@Override
	public List<Distributable> getPluginDistributables() {
		return Arrays.asList(
			new DistributableImpl(
				_getDownloadURLString("analysis-icu"), _ICU_CHECKSUM),
			new DistributableImpl(
				_getDownloadURLString("analysis-kuromoji"), _KUROMOJI_CHECKSUM),
			new DistributableImpl(
				_getDownloadURLString("analysis-smartcn"), _SMARTCN_CHECKSUM),
			new DistributableImpl(
				_getDownloadURLString("analysis-stempel"), _STEMPEL_CHECKSUM));
	}

	private String _getDownloadURLString(String plugin) {
		return StringBundler.concat(
			"https://artifacts.elastic.co/downloads/elasticsearch-plugins/",
			plugin, "/", plugin, "-", VERSION, ".zip");
	}

	private static final String _ELASTICSEARCH_CHECKSUM =
		"9997a8ee7394db302ae4bc4b3cf644a29fb6a082c5a4e7700dce5f3783818363baf9" +
			"29a667a17e52a27c732d352c339bfeb4e8e7240385911ad096146a75f559";

	private static final String _ICU_CHECKSUM =
		"e967a9b8fc62d01f3bf5da635d238acde10a6093ffc4d47d554ba61c8941cc7a791c" +
			"5c0c4a2d0317653eae4a56b2c3e4a8fe7dea29bf94627748aa004005508a";

	private static final String _KUROMOJI_CHECKSUM =
		"afb8c59b2607eb0112cb23ba67252127f73d1dbf0a0bcf6af845688a04acc86d7b7f" +
			"5907aaf86e97984657b2afc372c6f1203bfef50f41e1e234dfe2c274284b";

	private static final String _SMARTCN_CHECKSUM =
		"e789fca5c0e1a382d702a2337dfd56d627284c6e89bed5dbaffe4af671ce39d5117a" +
			"abe25e35a43eb2fc8f40164bb2ee8dd39a2d8da0689e937595a0dc9c68e7";

	private static final String _STEMPEL_CHECKSUM =
		"dba321de18efb123f9d1219b029daa21b2d417f0477880320a338f298f1430e3b589" +
			"b0029aef5fe4dc2a8c8eb3eecac5f7badb41d027476dc82f2f8424cba3e8";

}