/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.stats;

import com.liferay.portal.search.stats.StatsResponseBuilder;
import com.liferay.portal.search.stats.StatsResponseBuilderFactory;

import org.osgi.service.component.annotations.Component;

/**
 * @author André de Oliveira
 */
@Component(service = StatsResponseBuilderFactory.class)
public class StatsResponseBuilderFactoryImpl
	implements StatsResponseBuilderFactory {

	@Override
	public StatsResponseBuilder getStatsResponseBuilder() {
		return new StatsResponseBuilderImpl();
	}

}