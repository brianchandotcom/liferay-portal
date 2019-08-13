/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.rankings.web.internal.searcher;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.filter.ComplexQueryPart;
import com.liferay.portal.search.filter.ComplexQueryPartBuilderFactory;
import com.liferay.portal.search.query.IdsQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.tuning.rankings.web.internal.index.Ranking;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(service = RankingSearchRequestHelper.class)
public class RankingSearchRequestHelper {

	public void contribute(
		SearchRequestBuilder searchRequestBuilder, Ranking ranking) {

		searchRequestBuilder.addComplexQueryPart(
			getBlockIdsQueryPart(ranking)
		).addComplexQueryPart(
			getPinIdsQueryPart(ranking)
		);
	}

	protected ComplexQueryPart getBlockIdsQueryPart(Ranking ranking) {
		List<String> ids = ranking.getBlockIds();

		if (ids.isEmpty()) {
			return null;
		}

		return complexQueryPartBuilderFactory.builder(
		).query(
			_getIdsQuery(ids)
		).occur(
			"must_not"
		).build();
	}

	protected ComplexQueryPart getPinIdsQueryPart(Ranking ranking) {
		Set<String> ids = _getPinIds(ranking);

		if (ids.isEmpty()) {
			return null;
		}

		return complexQueryPartBuilderFactory.builder(
		).boost(
			10000F
		).query(
			_getIdsQuery(ids)
		).occur(
			"should"
		).build();
	}

	@Reference
	protected ComplexQueryPartBuilderFactory complexQueryPartBuilderFactory;

	@Reference
	protected Queries queries;

	private IdsQuery _getIdsQuery(Collection<String> ids) {
		if (ids.isEmpty()) {
			return null;
		}

		IdsQuery idsQuery = queries.ids();

		idsQuery.addIds(ArrayUtil.toStringArray(ids));

		return idsQuery;
	}

	private Set<String> _getPinIds(Ranking ranking) {
		List<Ranking.Pin> pins = ranking.getPins();

		Stream<Ranking.Pin> stream = pins.stream();

		return stream.map(
			Ranking.Pin::getId
		).collect(
			Collectors.toSet()
		);
	}

}