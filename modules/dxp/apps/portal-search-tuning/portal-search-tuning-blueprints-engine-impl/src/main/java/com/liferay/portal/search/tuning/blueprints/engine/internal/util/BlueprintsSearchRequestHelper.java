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

package com.liferay.portal.search.tuning.blueprints.engine.internal.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.tuning.blueprints.engine.component.ServiceComponentReference;
import com.liferay.portal.search.tuning.blueprints.engine.constants.ReservedParameterNames;
import com.liferay.portal.search.tuning.blueprints.engine.exception.BlueprintsEngineException;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.Parameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterData;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterDataCreator;
import com.liferay.portal.search.tuning.blueprints.engine.spi.searchrequest.SearchRequestBodyContributor;
import com.liferay.portal.search.tuning.blueprints.message.Message;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.message.Severity;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.search.tuning.blueprints.service.BlueprintService;
import com.liferay.portal.search.tuning.blueprints.util.BlueprintHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = BlueprintsSearchRequestHelper.class)
public class BlueprintsSearchRequestHelper {

	public void checkEngineErrors(long blueprintId, Messages messages)
		throws BlueprintsEngineException {

		if (messages.hasErrors()) {
			List<Message> errors = messages.getMessagesBySeverity(
				Severity.ERROR);

			StringBundler sb = new StringBundler(5);

			sb.append("There were ");
			sb.append(errors.size());
			sb.append(" error(s) in processing Blueprint ");
			sb.append(blueprintId);
			sb.append(". See messages for details.");

			throw new BlueprintsEngineException(sb.toString(), errors);
		}
	}

	public void executeSearchRequestBodyContributors(
		SearchRequestBuilder searchRequestBuilder, ParameterData parameterData,
		Blueprint blueprint, Messages messages) {

		if (_log.isDebugEnabled()) {
			_log.debug("Executing search request body contributors");
		}

		List<String> excludedSearchRequestBodyContributors =
			_getExcludedSearchRequestBodyContributors(parameterData);

		for (Map.Entry
				<String,
				 ServiceComponentReference<SearchRequestBodyContributor>>
					entry : _searchRequestBodyContributors.entrySet()) {

			try {
				if (excludedSearchRequestBodyContributors.contains(
						entry.getKey())) {

					continue;
				}

				ServiceComponentReference<SearchRequestBodyContributor> value =
					entry.getValue();

				SearchRequestBodyContributor searchRequestBodyContributor =
					value.getServiceComponent();

				searchRequestBodyContributor.contribute(
					searchRequestBuilder, blueprint, parameterData, messages);
			}
			catch (IllegalStateException illegalStateException) {
				messages.addMessage(
					new Message.Builder().className(
						getClass().getName()
					).localizationKey(
						"core.error.error-in-executing-search-request-body-" +
							"contributors"
					).msg(
						illegalStateException.getMessage()
					).severity(
						Severity.ERROR
					).throwable(
						illegalStateException
					).build());

				_log.error(
					illegalStateException.getMessage(), illegalStateException);
			}
		}
	}

	public String[] getModelIndexerClassNames(
		Blueprint blueprint, long companyId) {

		Optional<JSONArray> optional =
			_blueprintHelper.getSearchableAssetTypesOptional(blueprint);

		if (optional.isPresent()) {
			JSONArray jsonArray = optional.get();

			if (jsonArray.length() > 0) {
				return JSONUtil.toStringArray(optional.get());
			}
		}

		return new String[0];
	}

	public boolean shouldApplyIndexerClauses(Blueprint blueprint) {
		return _blueprintHelper.applyIndexerClauses(blueprint);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void registerSearchRequestBodyContributor(
		SearchRequestBodyContributor searchRequestBodyContributor,
		Map<String, Object> properties) {

		String name = (String)properties.get("name");

		if (Validator.isBlank(name)) {
			if (_log.isWarnEnabled()) {
				Class<?> clazz = searchRequestBodyContributor.getClass();

				_log.warn(
					"Unable to register search request contributor " +
						clazz.getName() + ". Name property empty.");
			}

			return;
		}

		int serviceRanking = GetterUtil.get(
			properties.get("service.ranking"), 0);

		ServiceComponentReference<SearchRequestBodyContributor>
			serviceComponentReference = new ServiceComponentReference<>(
				searchRequestBodyContributor, serviceRanking);

		if (_searchRequestBodyContributors.containsKey(name)) {
			ServiceComponentReference<SearchRequestBodyContributor>
				previousReference = _searchRequestBodyContributors.get(name);

			if (previousReference.compareTo(serviceComponentReference) < 0) {
				_searchRequestBodyContributors.put(
					name, serviceComponentReference);
			}
		}
		else {
			_searchRequestBodyContributors.put(name, serviceComponentReference);
		}
	}

	protected void unregisterSearchRequestBodyContributor(
		SearchRequestBodyContributor searchRequestBodyContributor,
		Map<String, Object> properties) {

		String name = (String)properties.get("name");

		if (Validator.isBlank(name)) {
			return;
		}

		_searchRequestBodyContributors.remove(name);
	}

	private List<String> _getExcludedSearchRequestBodyContributors(
		ParameterData parameterData) {

		Optional<Parameter> excludedSearchRequestBodyContributorsOptional =
			parameterData.getByNameOptional(
				ReservedParameterNames.
					EXCLUDED_SEARCH_REQUEST_BODY_CONTRIBUTORS.getKey());

		if (!excludedSearchRequestBodyContributorsOptional.isPresent()) {
			return new ArrayList<>();
		}

		Object object = excludedSearchRequestBodyContributorsOptional.get();

		if (object instanceof String[]) {
			return Arrays.asList((String[])object);
		}

		return new ArrayList<>();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BlueprintsSearchRequestHelper.class);

	@Reference
	private BlueprintHelper _blueprintHelper;

	@Reference
	private BlueprintService _blueprintService;

	@Reference
	private ParameterDataCreator _parameterDataCreator;

	private volatile Map
		<String, ServiceComponentReference<SearchRequestBodyContributor>>
			_searchRequestBodyContributors = new ConcurrentHashMap<>();

}