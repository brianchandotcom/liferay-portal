/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.sidecar;

import com.liferay.petra.string.StringBundler;

import java.util.Arrays;
import java.util.List;

/**
 * @author Bryan Engler
 */
public class ElasticsearchDistribution implements Distribution {

	public static final String VERSION = "8.19.11";

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
		"f645466fa4989a0e8fc5b9e3b51a6ad08e0dfbebd8f1e0627935a0339d02f8232aee" +
			"1e0e534af823c92a85a1bb7d361d2782890c16a963027a9c199bbd84d596";

	private static final String _ICU_CHECKSUM =
		"709bcdfea6589378e6c0793d00fdcb5ad7de4eb72c33d32c81881a27a7e02c011a64" +
			"ac5195abc51d7899236e3173131d2b4d862c6f6d74f1924ac8ed61d1bc38";

	private static final String _KUROMOJI_CHECKSUM =
		"12448ed5bb4b763d4804640a19701db0dbcf42d02991a71071872c805fd2faf177ca" +
			"e80d2c3bc687302be9f4b55e235c1c7a04f7361e8a84fc659bdb884e1d21";

	private static final String _SMARTCN_CHECKSUM =
		"e19e094785369d577a9a61fb1c260ce46d99203050c6734583e468a480cb4aaed1e7" +
			"6f71e33ff1f7270c9b485f0b106bb2f0ae84d53ede560b9f72787dba9bc2";

	private static final String _STEMPEL_CHECKSUM =
		"6214b0f5d8aa49a6bb4ff9656e495013d5fa88c7e4edf399937afa93fc98c06a33bd" +
			"99beee93dd707e764e39e71e6c665b132dd6726c5882ffe98d5ac1f59565";

}