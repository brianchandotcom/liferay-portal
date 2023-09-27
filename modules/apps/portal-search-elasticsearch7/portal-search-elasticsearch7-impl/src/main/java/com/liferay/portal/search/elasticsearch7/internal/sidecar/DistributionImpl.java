/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.sidecar;

import java.util.List;

/**
 * @author André de Oliveira
 */
public class DistributionImpl implements Distribution {

	public DistributionImpl(
		Distributable elasticsearchDistributable,
		List<Distributable> pluginDistributables) {

		_elasticsearchDistributable = elasticsearchDistributable;
		_pluginDistributables = pluginDistributables;
	}

	@Override
	public Distributable getElasticsearchDistributable() {
		return _elasticsearchDistributable;
	}

	@Override
	public List<Distributable> getPluginDistributables() {
		return _pluginDistributables;
	}

	private final Distributable _elasticsearchDistributable;
	private final List<Distributable> _pluginDistributables;

}