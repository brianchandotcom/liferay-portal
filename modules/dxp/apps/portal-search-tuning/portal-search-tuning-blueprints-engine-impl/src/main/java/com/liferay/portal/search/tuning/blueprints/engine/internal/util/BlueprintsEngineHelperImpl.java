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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributes;
import com.liferay.portal.search.tuning.blueprints.engine.constants.ReservedParameterNames;
import com.liferay.portal.search.tuning.blueprints.engine.exception.BlueprintsEngineException;
import com.liferay.portal.search.tuning.blueprints.engine.internal.executor.SearchExecutor;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.Parameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterData;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterDataCreator;
import com.liferay.portal.search.tuning.blueprints.engine.util.BlueprintsEngineHelper;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.search.tuning.blueprints.service.BlueprintService;
import com.liferay.portal.search.tuning.blueprints.util.BlueprintHelper;

import java.util.Locale;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = BlueprintsEngineHelper.class)
public class BlueprintsEngineHelperImpl implements BlueprintsEngineHelper {

	@Override
	public SearchRequestBuilder getSearchRequestBuilder(
			long blueprintId, BlueprintsAttributes blueprintsAttributes,
			Messages messages)
		throws BlueprintsEngineException, PortalException {

		Blueprint blueprint = _blueprintService.getBlueprint(blueprintId);

		ParameterData parameterData = _parameterDataCreator.create(
			blueprint, blueprintsAttributes, messages);

		SearchRequestBuilder searchRequestBuilder = _getSearchRequestBuilder(
			parameterData, blueprint, messages,
			blueprintsAttributes.getCompanyId(),
			blueprintsAttributes.getLocale());

		_blueprintsSearchRequestHelper.checkEngineErrors(
			blueprint.getBlueprintId(), messages);

		return searchRequestBuilder;
	}

	@Override
	public SearchResponse search(
			Blueprint blueprint, BlueprintsAttributes blueprintsAttributes,
			Messages messages)
		throws BlueprintsEngineException, PortalException {

		ParameterData parameterData = _parameterDataCreator.create(
			blueprint, blueprintsAttributes, messages);

		SearchRequestBuilder searchRequestBuilder = _getSearchRequestBuilder(
			parameterData, blueprint, messages,
			blueprintsAttributes.getCompanyId(),
			blueprintsAttributes.getLocale());

		_blueprintsSearchRequestHelper.checkEngineErrors(
			blueprint.getBlueprintId(), messages);

		return _searchExecutor.execute(
			searchRequestBuilder, parameterData, blueprint, messages);
	}

	@Override
	public SearchResponse search(
			long blueprintId, BlueprintsAttributes blueprintsAttributes,
			Messages messages)
		throws BlueprintsEngineException, PortalException {

		Blueprint blueprint = _blueprintService.getBlueprint(blueprintId);

		ParameterData parameterData = _parameterDataCreator.create(
			blueprint, blueprintsAttributes, messages);

		SearchRequestBuilder searchRequestBuilder = _getSearchRequestBuilder(
			parameterData, blueprint, messages,
			blueprintsAttributes.getCompanyId(),
			blueprintsAttributes.getLocale());

		_blueprintsSearchRequestHelper.checkEngineErrors(
			blueprint.getBlueprintId(), messages);

		return _searchExecutor.execute(
			searchRequestBuilder, parameterData, blueprint, messages);
	}

	private int _getFrom(
		ParameterData parameterData, Blueprint blueprint, int size) {

		Optional<Parameter> optional = parameterData.getByNameOptional(
			_blueprintHelper.getPageParameterName(blueprint));

		if (!optional.isPresent()) {
			return 1;
		}

		Parameter parameter = optional.get();

		int page = GetterUtil.getInteger(parameter.getValue(), 1);

		return _getFromValue(size, page);
	}

	private int _getFromValue(int size, int page) {
		if (page <= 1) {
			return 0;
		}

		return (page - 1) * size;
	}

	private SearchRequestBuilder _getSearchRequestBuilder(
		ParameterData parameterData, Blueprint blueprint, Messages messages,
		long companyId, Locale locale) {

		int size = _getSize(parameterData, blueprint);

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder(
			).companyId(
				companyId
			).emptySearchEnabled(
				true
			).excludeContributors(
				"com.liferay.portal.search.tuning.blueprints"
			).explain(
				_isExplain(parameterData)
			).includeResponseString(
				_isIncludeResponseString(parameterData)
			).locale(
				locale
			).modelIndexerClassNames(
				_blueprintsSearchRequestHelper.getModelIndexerClassNames(
					blueprint, companyId)
			).size(
				size
			).from(
				_getFrom(parameterData, blueprint, size)
			);

		if (!_blueprintsSearchRequestHelper.shouldApplyIndexerClauses(
				blueprint)) {

			searchRequestBuilder.withSearchContext(
				searchContext -> searchContext.setAttribute(
					"search.full.query.suppress.indexer.provided.clauses",
					Boolean.TRUE));
		}
		else {
			searchRequestBuilder.queryString(parameterData.getKeywords());
		}

		_blueprintsSearchRequestHelper.executeSearchRequestBodyContributors(
			searchRequestBuilder, parameterData, blueprint, messages);

		return searchRequestBuilder;
	}

	private int _getSize(ParameterData parameterData, Blueprint blueprint) {
		String parameterName = _blueprintHelper.getSizeParameterName(blueprint);

		Optional<Parameter> optional = parameterData.getByNameOptional(
			parameterName);

		if (!optional.isPresent()) {
			_blueprintHelper.getDefaultSize(blueprint);
		}

		Parameter parameter = optional.get();

		return GetterUtil.getInteger(parameter.getValue());
	}

	private boolean _isExplain(ParameterData parameterData) {
		Optional<Parameter> parameterOptional = parameterData.getByNameOptional(
			ReservedParameterNames.EXPLAIN.getKey());

		if (!parameterOptional.isPresent()) {
			return false;
		}

		Parameter parameter = parameterOptional.get();

		return GetterUtil.getBoolean(parameter.getValue());
	}

	private boolean _isIncludeResponseString(ParameterData parameterData) {
		Optional<Parameter> parameterOptional = parameterData.getByNameOptional(
			ReservedParameterNames.INCLUDE_RESPONSE_STRING.getKey());

		if (!parameterOptional.isPresent()) {
			return false;
		}

		Parameter parameter = parameterOptional.get();

		return GetterUtil.getBoolean(parameter.getValue());
	}

	@Reference
	private BlueprintHelper _blueprintHelper;

	@Reference
	private BlueprintService _blueprintService;

	@Reference
	private BlueprintsSearchRequestHelper _blueprintsSearchRequestHelper;

	@Reference
	private ParameterDataCreator _parameterDataCreator;

	@Reference
	private SearchExecutor _searchExecutor;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

}