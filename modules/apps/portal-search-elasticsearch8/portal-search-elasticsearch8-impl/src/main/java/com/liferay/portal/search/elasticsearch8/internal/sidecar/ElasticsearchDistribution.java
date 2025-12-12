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

	public static final String VERSION = "8.19.8";

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
		"1e75ba6174de88754e55d64fd29dc2f05a9c5993604fc20ce068a8dea54141a29dba" +
			"a59d252c613412f7da72b7f113fc474c83f1eb2f200b3072ade0a98f54cf";

	private static final String _ICU_CHECKSUM =
		"94b48634b9504162f114c650240e4ab9fc82dc8955cf042e9e24ac94120a97ebe7d2" +
			"9924ee0a071b49afd6858c7d98f8a99311f03d498d49e59cdfe1453e62d8";

	private static final String _KUROMOJI_CHECKSUM =
		"539676ab36a7b1ab653ef8f5b00f90f342a3734d4e60cf846da17fbcc5b853e2a93a" +
			"d28c8cce690b622e19a6829a5d5b9c08846526048a576e71c9a503a01b55";

	private static final String _SMARTCN_CHECKSUM =
		"af1917517058dec86b79a15cef193354f96d2b01ca2069e9b2fda8275b6b0669e608" +
			"4b273f40d54f8aeb6cfb9a99ef679ac3dadc1d0941d4925849652d894e2a";

	private static final String _STEMPEL_CHECKSUM =
		"caba9ae24f78054deebe5740ffe9b02acf69837fefde9a31eded8df7adb0b5479a7f" +
			"466886fe5398e58374be72717a4543daa6a3e6d17d201779b60a3ded5856";

}