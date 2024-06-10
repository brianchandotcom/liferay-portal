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

	public static final String VERSION = "7.17.21";

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
		"8caac909b44e87be674a2543ef6e6c1a7e1c2acae7a197540106ec28851b285ad89c" +
			"7c1f0059f68a8515c87f98297336f8b3b42cc0f37284ad02992b2fa0b3b0";

	private static final String _ICU_CHECKSUM =
		"f41b1dd6196f16d0beae8556fc19fb00acbe0e2c085f81da6f23b41696c0d194c92f" +
			"3842edaa5cbbb9e40f650faaf34284abbb1a7ef995e83209fd1ff03f18aa";

	private static final String _KUROMOJI_CHECKSUM =
		"8095c616e3363b6926719379398366cf5056d80e6d0e5040ef13ed208c9f63a45c37" +
			"fd6da14fd586ef4d10e150ca01933ec07f97aa76bcf2d3597ff5e933e838";

	private static final String _SMARTCN_CHECKSUM =
		"4a17410911521815308a2a0e5558db8b957dae5de651ff38bed4f608d483973d2312" +
			"00778863efd9842cc18e68ead0d69a82279c86a123da8d58ec29a1f8108c";

	private static final String _STEMPEL_CHECKSUM =
		"58ae32edf111cdda55266ab74b230534e4c82f427aae6f39d388fe46a4666827cfd7" +
			"05c990b195d35303fd8b9afb7d1dd69c97e3da8ef501f134e99c107e2cb9";

}