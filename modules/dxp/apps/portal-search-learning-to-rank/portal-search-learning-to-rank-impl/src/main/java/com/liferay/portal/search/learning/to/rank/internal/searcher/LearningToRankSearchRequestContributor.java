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

package com.liferay.portal.search.learning.to.rank.internal.searcher;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.search.learning.to.rank.configuration.LearningToRankConfiguration;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.rescore.Rescore;
import com.liferay.portal.search.rescore.RescoreBuilder;
import com.liferay.portal.search.rescore.RescoreBuilderFactory;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.spi.searcher.SearchRequestContributor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(
	configurationPid = "com.liferay.portal.search.learning.to.rank.configuration.LearningToRankConfiguration",
	immediate = true,
	property = "search.request.contributor.id=com.liferay.portal.search.learning.to.rank",
	service = SearchRequestContributor.class
)
public class LearningToRankSearchRequestContributor
	implements SearchRequestContributor {

	@Override
	public SearchRequest contribute(SearchRequest searchRequest) {
		if (!_enabled) {
			return searchRequest;
		}

		SearchRequestBuilder searchRequestBuilder =
			searchRequestBuilderFactory.builder(searchRequest);

		List<Rescore> rescores = getRescores(
			searchRequest, rescoreBuilderFactory.getRescoreBuilder());

		searchRequestBuilder.rescores(rescores);

		return searchRequestBuilder.build();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		LearningToRankConfiguration learningToRankConfiguration =
			ConfigurableUtil.createConfigurable(
				LearningToRankConfiguration.class, properties);

		_enabled = learningToRankConfiguration.enabled();
		_model = learningToRankConfiguration.model();
	}

	protected Query getRescoreQuery(String model, String keywords) {
		return queries.wrapper(
			JSONUtil.put(
				"sltr",
				JSONUtil.put(
					"model", model
				).put(
					"params", JSONUtil.put("keywords", keywords)
				)
			).toString());
	}

	protected List<Rescore> getRescores(
		SearchRequest searchRequest, RescoreBuilder rescoreBuilder) {

		return Arrays.asList(
			rescoreBuilder.query(
				getRescoreQuery(_model, searchRequest.getQueryString())
			).windowSize(
				1000
			).build());
	}

	@Reference
	protected Queries queries;

	@Reference
	protected RescoreBuilderFactory rescoreBuilderFactory;

	@Reference
	protected SearchRequestBuilderFactory searchRequestBuilderFactory;

	private boolean _enabled;
	private String _model;

}