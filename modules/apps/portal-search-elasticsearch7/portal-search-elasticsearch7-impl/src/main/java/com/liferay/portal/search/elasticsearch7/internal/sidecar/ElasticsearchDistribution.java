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

	public static final String VERSION = "7.17.0";

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
			new DistributableImpl(_getDownloadURLString("analysis-icu"), _ICU_CHECKSUM),
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
		"b23f5345631ab7366488fa621d0cb8050ed5a29ac9bf71d9f0689baaa2440e614ea3" +
			"b34a0f65f8a84593f356da053e52e2ed2624abe76b067fe7f7adeb108565";

	private static final String _ICU_CHECKSUM =
		"95e7016f35e64234ee504d6476a0e4a969a20c733866352847a01fe5746f9b6122e8" +
			"925ae57753d8a53c719b6047018b49ddc206a1313082c505b678003fbc50";

	private static final String _KUROMOJI_CHECKSUM =
		"c775248a40df8b05a4eefe08c0f7a071aa9da3d211e00936cc6c29e99c1221777788" +
			"e647dde7960255a3332f7ea09d1ec2a5f4a1255f86b926de8b3577889e22";

	private static final String _SMARTCN_CHECKSUM =
		"0642c0ac8e333468f5ca71de3e756dff7c79fa0687bf900043d77dfce2b1d7de9f25" +
			"cb756dd8548521deb3ed7c95db98624cd8fd4d3cc01d2a6e25b3d31dc6d9";

	private static final String _STEMPEL_CHECKSUM =
		"719dad74f53b3f9c554a6fddfd11e7fa68a2eb690e679f91e151112d243288bf2798" +
			"11f64714ca6a605fc988adb900da1b0ca477447c35f210c229c60a6361af";

}