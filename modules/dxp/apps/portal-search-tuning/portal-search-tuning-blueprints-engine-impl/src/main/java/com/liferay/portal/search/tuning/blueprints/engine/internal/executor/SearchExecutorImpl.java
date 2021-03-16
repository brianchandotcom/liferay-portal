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

package com.liferay.portal.search.tuning.blueprints.engine.internal.executor;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.filter.ComplexQueryBuilderFactory;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.rescore.Rescore;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.sort.Sort;
import com.liferay.portal.search.tuning.blueprints.engine.component.ServiceComponentReference;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterData;
import com.liferay.portal.search.tuning.blueprints.engine.spi.query.QueryPostProcessor;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.search.tuning.blueprints.util.BlueprintHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = SearchExecutor.class)
public class SearchExecutorImpl implements SearchExecutor {

	@Override
	public SearchResponse execute(
		SearchRequestBuilder searchRequestBuilder, ParameterData parameterData,
		Blueprint blueprint, Messages messages) {

		SearchRequestBuilder searchRequestBuilder2 = _rescoreOrSort(
			searchRequestBuilder);

		return _execute(
			searchRequestBuilder2, parameterData, blueprint, messages);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void registerQueryPostProcessor(
		QueryPostProcessor queryPostProcessor, Map<String, Object> properties) {

		String name = (String)properties.get("name");

		if (Validator.isBlank(name)) {
			if (_log.isWarnEnabled()) {
				Class<?> clazz = queryPostProcessor.getClass();

				_log.warn(
					"Unable to register query post processor " +
						clazz.getName() + ". Name property empty.");
			}

			return;
		}

		int serviceRanking = GetterUtil.get(
			properties.get("service.ranking"), 0);

		ServiceComponentReference<QueryPostProcessor>
			serviceComponentReference = new ServiceComponentReference<>(
				queryPostProcessor, serviceRanking);

		if (_queryPostProcessors.containsKey(name)) {
			ServiceComponentReference<QueryPostProcessor> previousReference =
				_queryPostProcessors.get(name);

			if (previousReference.compareTo(serviceComponentReference) < 0) {
				_queryPostProcessors.put(name, serviceComponentReference);
			}
		}
		else {
			_queryPostProcessors.put(name, serviceComponentReference);
		}
	}

	protected void unregisterQueryPostProcessor(
		QueryPostProcessor queryPostProcessor, Map<String, Object> properties) {

		String name = (String)properties.get("name");

		if (Validator.isBlank(name)) {
			return;
		}

		_queryPostProcessors.remove(name);
	}

	private SearchResponse _execute(
		SearchRequestBuilder searchRequestBuilder, ParameterData parameterData,
		Blueprint blueprint, Messages messages) {

		SearchResponse searchResponse = _searcher.search(
			searchRequestBuilder.build());

		if (_log.isDebugEnabled() && (searchResponse != null)) {
			_log.debug("Request string: " + searchResponse.getRequestString());
			_log.debug("Hits: " + searchResponse.getCount());
		}

		_executeQueryPostProcessors(
			searchResponse, parameterData, blueprint, messages);

		return searchResponse;
	}

	private void _executeQueryPostProcessors(
		SearchResponse searchResponse, ParameterData parameterData,
		Blueprint blueprint, Messages messages) {

		if (_log.isDebugEnabled()) {
			_log.debug("Executing query post processors");
		}

		if (_queryPostProcessors == null) {
			return;
		}

		List<String> excludedQueryPostProcessors =
			_getExcludedQueryPostProcessors(blueprint);

		Stream<String> stream1 = excludedQueryPostProcessors.stream();

		if (stream1.anyMatch(s -> s.equals("*"))) {
			return;
		}

		for (Map.Entry<String, ServiceComponentReference<QueryPostProcessor>>
				entry : _queryPostProcessors.entrySet()) {

			ServiceComponentReference<QueryPostProcessor> value =
				entry.getValue();

			QueryPostProcessor queryPostProcessor = value.getServiceComponent();

			Stream<String> stream2 = excludedQueryPostProcessors.stream();

			if (stream2.anyMatch(s -> s.equals(entry.getKey()))) {
				if (_log.isDebugEnabled()) {
					Class<?> clazz = queryPostProcessor.getClass();

					_log.debug(clazz.getName() + " is excluded.");
				}

				continue;
			}

			queryPostProcessor.process(
				searchResponse, blueprint, parameterData, messages);
		}
	}

	private List<String> _getExcludedQueryPostProcessors(Blueprint blueprint) {
		Optional<List<String>> excludedQueryPostProcessorsOptional =
			_blueprintHelper.getExcludedQueryPostProcessorsOptional(blueprint);

		if (excludedQueryPostProcessorsOptional.isPresent()) {
			return excludedQueryPostProcessorsOptional.get();
		}

		return new ArrayList<>();
	}

	private SearchRequestBuilder _rescoreOrSort(
		SearchRequestBuilder searchRequestBuilder) {

		// Sorts cannot be used with rescorer (See Elasticsearch documentation)

		SearchRequest searchRequest = searchRequestBuilder.build();

		SearchRequestBuilder searchRequestBuilder2 =
			_searchRequestBuilderFactory.builder(searchRequest);

		List<Rescore> rescores = searchRequest.getRescores();

		if ((rescores != null) && !rescores.isEmpty()) {
			searchRequestBuilder2.sorts(new Sort[0]);
		}

		return searchRequestBuilder;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SearchExecutorImpl.class);

	@Reference
	private BlueprintHelper _blueprintHelper;

	@Reference
	private ComplexQueryBuilderFactory _complexQueryBuilderFactory;

	@Reference
	private Queries _queries;

	private volatile Map<String, ServiceComponentReference<QueryPostProcessor>>
		_queryPostProcessors = new ConcurrentHashMap<>();

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

}