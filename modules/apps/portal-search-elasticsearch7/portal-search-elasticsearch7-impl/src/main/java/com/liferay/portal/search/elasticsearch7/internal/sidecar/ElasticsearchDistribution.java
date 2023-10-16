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

	public static final String VERSION = "7.17.14";

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
		"4103fc9b931e2acfc52c3583cec820ff62e59957e147215e16a5c7f5987a9d6fc858" +
			"b5b239333c852bd18b528d8fb38ac9bec6d8229c3a331f12e4f8ff0d26f2";

	private static final String _ICU_CHECKSUM =
		"c1c9f92e121882016fc2cd01f32fe3a5fbeb06976b3758e3c943c1cd64ed0e95a004" +
			"a2126028cbfd8404aba243ef15c0a8b50f31cbaf557c93c612eb55fc091f";

	private static final String _KUROMOJI_CHECKSUM =
		"77b524e4a4b7d60332242c610d50216056e530b51b4dca1f99c90c3f0d1f28052831" +
			"2ba0a38403b330fc101b1ee2b69eb4e6d016c4eabc6b563f493fe196e2b3";

	private static final String _SMARTCN_CHECKSUM =
		"7733cd7053496088644f07ed774ab74d98c5bbcd42828f59e216cb05fe8fc1d42dce" +
			"1e71d74d1a43180edc1c50fd800005954d567040cef7ed3ab982f1ac373f";

	private static final String _STEMPEL_CHECKSUM =
		"ef30afe037fc12363a764fe9065b6f67e1491d2d2d294d4bec16b59d03263da08e40" +
			"9847fbd6838656d7a93bc0a28af19fe8573f30da360df59cc7010ed2dedc";

}