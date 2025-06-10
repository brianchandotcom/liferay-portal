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

	public static final String VERSION = "8.17.4";

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
		"02e44da0cca804ba5593d63d90c20b0a8affedcd2d69a32303c6863f391bbcb31cc6" +
			"b5dba6dfc53c7682b5c36825089f399a5d6cb8649fc829cfc6f1a7de02f1";

	private static final String _ICU_CHECKSUM =
		"43fa54d27530a0fe414c83b9befa08c60b111b67b36d5092d9987facb77274c8adb9" +
			"c5a8b98e56e0c9b33b5b9516349961e2f35eee1a4c97029b05e4e16f5dcd";

	private static final String _KUROMOJI_CHECKSUM =
		"7bf5ff9324b6ecf77170ec2419c7d981831b14a450b1a25231f01bfb10b4d44baeb3" +
			"c1adb0473b7fb314c0ffb56b9986588f357d1e4a4db11a5f193c6eb7da3c";

	private static final String _SMARTCN_CHECKSUM =
		"25b8584f6511ebe5d2a930ee4989f6ab8f2e2c93ed8af6a5f9151288d0b20625dc15" +
			"2a29668fdb5376a1b3df55f7d661fe209329e507ac479a03bce3ea0ab0ab";

	private static final String _STEMPEL_CHECKSUM =
		"29c862b4f03474b9bffdd88eae161a73320042f50e3c1edef0b3a7278ab2162fdd48" +
			"36435efe0ea292ecd1049ad9c6c1d01f2c2a7da08aeaf063eaf1ff12e58d";

}