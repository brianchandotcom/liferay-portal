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

	public static final String VERSION = "7.17.26";

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
		"5b32714d5683059c213fbe610de21d1016048e97ee4215ae4104aa6065d409c33d7c" +
			"e13b5e1443b559ea2167f7ebe8a5b26609ff1e085e4f4995bf46083afa35";

	private static final String _ICU_CHECKSUM =
		"68f1024daea400b100db8a4a6ec81ad5b9df76f1ef60f98cad06389a9aea58ee21b2" +
			"939ba770f7d3c8e1901eab347cfd4d68590f85d730217271f17cd392bfc6";

	private static final String _KUROMOJI_CHECKSUM =
		"16226951b084dd4f33d640da87a69a132336e53e5537d441711080a594dd616b5c3f" +
			"5c5b2e4b678afc06c8d3472d642018c258e4e07b105ad69ffb6b02f7892f";

	private static final String _SMARTCN_CHECKSUM =
		"df4babcd8a937fda74fb7c31f1a4d138cfb0c2e6a87f31e092a3193df5e138c9182e" +
			"af521370178cb9b5b0a4dabacf56a5236c8d6927460d072c49cd46da1148";

	private static final String _STEMPEL_CHECKSUM =
		"524df432270e94ff2b21d3efc0f2a82a2bb2c0fd36c3dbe33333237dfff0c67eff4d" +
			"de289bfc6f274959b7b544535f5ded49cd58ee9a2202c4bcd228756aef69";

}