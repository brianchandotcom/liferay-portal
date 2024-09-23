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

	public static final String VERSION = "7.17.24";

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
		"18a4d726b33f301f0a69b2ec04a94b3411cbcf70b471ad0febed527bf51d0546b7ab" +
			"1293bb4c121a4a10d8849773c0b6b88aa0863cac49183b08eafa70179266";

	private static final String _ICU_CHECKSUM =
		"9f21050c2459ad3f4fa866bee25592fda6b6e13bc706d6aed8b168e46bf20549ebb2" +
			"13b65e3ba9e0bb8f93ee800a50a189c40bd96ada1ca7805d9151d6986d64";

	private static final String _KUROMOJI_CHECKSUM =
		"3273e10fef3281062b821e6adbbbbc2a8b2a3865dc78586c95f59a57b584b3d41ddc" +
			"3a0694958c8fec1ec8af178eb6d0fde347eff1a261721ff259915a591c01";

	private static final String _SMARTCN_CHECKSUM =
		"6dd341cdce1b4fcc02e1654c93bf1c91476d9bceec6d59923c106fba20ffd2845630" +
			"df82e214856fe08260f1a3d4ad36b3439ddfcd2e224f8c634cbbe06859c9";

	private static final String _STEMPEL_CHECKSUM =
		"643b88e0aa857a4a9a3ba31acfb57dd70daa6954a4f1a8b7d154023051eff38e2c58" +
			"693234ed2e13ee37a4450175e128759ebb15a1d743fccf45cc7dae4de9a3";

}