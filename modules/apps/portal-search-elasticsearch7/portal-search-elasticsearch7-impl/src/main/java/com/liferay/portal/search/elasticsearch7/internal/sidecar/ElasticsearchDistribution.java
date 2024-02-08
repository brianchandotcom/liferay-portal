/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.sidecar;

import com.liferay.petra.string.StringBundler;

import java.util.Arrays;
import java.util.List;

/**
 * @author Bryan Engler
 */
public class ElasticsearchDistribution implements Distribution {

	public static final String VERSION = "7.17.18";

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
		"3df93ce9eb35bafd4ceb11078fdcbb5e0722636b0f36eec0a55e460f05c4b34cca28" +
			"82f9cd68570e7715ec4a5287ef97ae4fef67566828728044275feca26274";

	private static final String _ICU_CHECKSUM =
		"b26d19ed528fc0658e3ac90c272e84b18893f6088e3fbb8cbe43bf0f475db37d9d6c" +
			"c2c389ccd7693c696e9637312e6ff497f5510358f53586669fcf5ad9dc93";

	private static final String _KUROMOJI_CHECKSUM =
		"5432c3f2e90f60d8076e6621c2b7141b66ce7e4f9b779e332cd4b6505b264a80d933" +
			"10409d78cf26a4df4e1fde2991059fea82a8dfd3c4b31ce5fed5d2558bf1";

	private static final String _SMARTCN_CHECKSUM =
		"20cd06c6463afeb2f9685ab69d6a8a0801d421a21bd2ad0ad17e71ed167db9c35d07" +
			"c83efca7dc90a3283f7ae94dd4e2c89db3170a9ba3de4f30913db77c3483";

	private static final String _STEMPEL_CHECKSUM =
		"ee679f316c9bbd7412c46e0942edc818b68c60414e0ce4f29d5aa4398aed7568d0e2" +
			"b092d0d80098fa868948ea860171aa3976e66e579eea7171897f1ccc09e7";

}