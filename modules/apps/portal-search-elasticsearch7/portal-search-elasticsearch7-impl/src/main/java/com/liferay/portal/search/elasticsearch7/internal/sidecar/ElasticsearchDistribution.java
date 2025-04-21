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

	public static final String VERSION = "7.17.28";

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
		"228241d7b4b02d6f97da51b3069767d65e4d94116aba31d215229942350ae6b8cfab" +
			"88d662ff344e7bc3ed7f351fd4ea5e5e1e1068e052a44d4e166e211ffe8a";

	private static final String _ICU_CHECKSUM =
		"33c0899e6552ee3bdc9e0c7dbff8b111a33f82f6a802c8506260ba2367fdb75dec72" +
			"e2378ef99a49a61a99b557b14b9999b5b64a56bf67c08bfb4af6cf19d0ce";

	private static final String _KUROMOJI_CHECKSUM =
		"4c0311ae8a7f31caef64d1184899473b8439cee10b0b3c86204f6d86c5beec0f5278" +
			"528e63cbf06e9280d8cf301219f32ebd3a2810d204369be1fa45d847e9cb";

	private static final String _SMARTCN_CHECKSUM =
		"f3afa5961e2a9673b11991eba0fffc01e442d9a23ad85e27b454d95a19146149badd" +
			"81c2d0038e62bb7886db99ab4933d9948dc0d3f270049bb4e075008955e2";

	private static final String _STEMPEL_CHECKSUM =
		"e9576d5c3b4c643cf1dc60f28baa8b1bec6daba514c90141dc5525b9d01521d543c2" +
			"84750a00fa856d5f95c8cfadd5d44be2f3d89d1218f9e3951a3e69fdeb35";

}