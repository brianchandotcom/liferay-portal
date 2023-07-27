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

	public static final String VERSION = "7.17.12";

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
		"5b6c7614c2629fdfa6c9fe9866518cd38abad0244bf7c56d8fba3894bd0493403ded" +
			"0f581783deded85287cf1e97b18f45f1ffb182d3dc5ca9c96471276d64d1";

	private static final String _ICU_CHECKSUM =
		"0c6daec4adb15f9823d9602915785bf618eb350b09b1761098d7783a375834245669" +
			"1afec53080cc9ba8b75bd1ac0f2a87c9c174ad3086001bc28a2d0d8642d6";

	private static final String _KUROMOJI_CHECKSUM =
		"1fe0b0636a15fcee93708a758de5f883fb7e7078238465ed374a14322d6bdda68a2e" +
			"9ea57187e24d73e0e6730db6eaf1e52f9c9ff79448c5402c6891ffadd8a8";

	private static final String _SMARTCN_CHECKSUM =
		"ac9c33e80cc46212a2552b461014bf83d0119badf9f95e2f8facadbdc2cd869b64ca" +
			"9514183aeb66fa5f6a0c316d676b623a553f87ad712242ed9f2f0aace4d7";

	private static final String _STEMPEL_CHECKSUM =
		"c321bdc6bacd3bf54c283aa1a392182553a00317f82ccea3a4928667415c220a5621" +
			"94750c57b01563f99dc48cb3945ecd1d4f04df88f55ece318ae23088ab36";

}