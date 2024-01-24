/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index;

import com.liferay.portal.search.tuning.rankings.helper.RankingHelper;
import com.liferay.portal.search.tuning.rankings.index.Ranking;
import com.liferay.portal.search.tuning.rankings.index.RankingBuilderFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = RankingBuilderFactory.class)
public class RankingBuilderFactoryImpl implements RankingBuilderFactory {

	@Override
	public Ranking.Builder builder() {
		return new RankingImpl.BuilderImpl(_rankingHelper);
	}

	@Override
	public Ranking.Builder builder(Ranking ranking) {
		return new RankingImpl.BuilderImpl(ranking, _rankingHelper);
	}

	@Reference
	private RankingHelper _rankingHelper;

}