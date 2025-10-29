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

	public static final String VERSION = "8.18.8";

	@Override
	public Distributable getElasticsearchDistributable() {
		return new DistributableImpl(
			StringBundler.concat(
				"https://artifacts.elastic.co/downloads/elasticsearch",
				"/elasticsearch-", VERSION, "-linux-x86_64.tar.gz"),
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
		"6878adbf03ab0cc390758c6633eaba19562d500d173b536d9dd249397c0df716bd65" +
			"fa658ee69f3479f374138e02c60c60c2c3a5e5ba740661f598b6edf0cf3a";

	private static final String _ICU_CHECKSUM =
		"10a5b1ae104ba5275e96df5e0632f0ce1474a29819320d39cd678c52dbe8fa48ad93" +
			"f9b7e34e3fc213ed4e4ebad0282912c2592d20b807ca4e7118dea4ac3569";

	private static final String _KUROMOJI_CHECKSUM =
		"d827010e01230ac52e8340af4092e3754f6d8d017afe0e1a037f11ea68e255ff9b25" +
			"150378a3f4398036ce6a199459447ceabab185ca99a560f86b833ffa8799";

	private static final String _SMARTCN_CHECKSUM =
		"073231e4317906b4b299f8758a5f10db3958b2721e8d31853a158778d9632480cba6" +
			"7f4c73adfc6cfb624cb89dfc53ae6297a6255d56cf9d983e1f86aa5482f2";

	private static final String _STEMPEL_CHECKSUM =
		"bef8f63bdb5d693d3067c952ddfe200173b233292649ed282ab9c152ff9f1ac26c27" +
			"eb020544c124d19a11b08ef775a27a5388c75a8f7dbcc10192cf2ca57231";

}