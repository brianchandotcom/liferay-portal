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

package com.liferay.segments.asah.connector.internal.client;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NestableRuntimeException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.asah.connector.internal.client.data.binding.IndividualJSONObjectMapper;
import com.liferay.segments.asah.connector.internal.client.data.binding.IndividualSegmentJSONObjectMapper;
import com.liferay.segments.asah.connector.internal.client.data.binding.InterestTermsJSONObjectMapper;
import com.liferay.segments.asah.connector.internal.client.model.DXPVariants;
import com.liferay.segments.asah.connector.internal.client.model.Experiment;
import com.liferay.segments.asah.connector.internal.client.model.ExperimentSettings;
import com.liferay.segments.asah.connector.internal.client.model.Individual;
import com.liferay.segments.asah.connector.internal.client.model.IndividualSegment;
import com.liferay.segments.asah.connector.internal.client.model.Results;
import com.liferay.segments.asah.connector.internal.client.model.Topic;
import com.liferay.segments.asah.connector.internal.client.util.FilterBuilder;
import com.liferay.segments.asah.connector.internal.client.util.FilterConstants;
import com.liferay.segments.asah.connector.internal.client.util.OrderByField;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

/**
 * @author David Arques
 */
public class AsahFaroBackendClientImpl implements AsahFaroBackendClient {

	public AsahFaroBackendClientImpl(
		JSONWebServiceClient jsonWebServiceClient, String dataSourceId,
		String asahFaroBackendSecuritySignature, String asahFaroBackendURL) {

		_jsonWebServiceClient = jsonWebServiceClient;

		_dataSourceId = dataSourceId;

		_headers.put(
			"OSB-Asah-Faro-Backend-Security-Signature",
			asahFaroBackendSecuritySignature);

		_jsonWebServiceClient.setBaseURI(asahFaroBackendURL);
	}

	@Override
	public Experiment addExperiment(Experiment experiment) {
		if (experiment == null) {
			return null;
		}

		return _jsonWebServiceClient.doPost(
			Experiment.class, _PATH_EXPERIMENTS, experiment, _headers);
	}

	@Override
	public Long calculateExperimentEstimatedDaysDuration(
		String experimentId, ExperimentSettings experimentSettings) {

		String days = _jsonWebServiceClient.doPost(
			String.class,
			StringUtil.replace(
				_PATH_EXPERIMENTS_ESTIMATED_DAYS_DURATION, "{experimentId}",
				experimentId),
			experimentSettings, _headers);

		if (Validator.isNull(days)) {
			return null;
		}

		try {
			return Long.valueOf(days);
		}
		catch (NumberFormatException numberFormatException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse " + days, numberFormatException);
			}

			return null;
		}
	}

	@Override
	public void deleteExperiment(String experimentId) {
		if (experimentId == null) {
			return;
		}

		_jsonWebServiceClient.doDelete(
			StringUtil.replace(
				_PATH_EXPERIMENTS_EXPERIMENT, "{experimentId}", experimentId),
			new HashMap<>(), _headers);
	}

	@Override
	public String getDataSourceId() {
		return _dataSourceId;
	}

	@Override
	public Individual getIndividual(String individualPK) {
		FilterBuilder filterBuilder = new FilterBuilder();

		filterBuilder.addFilter(
			"dataSourceId", FilterConstants.COMPARISON_OPERATOR_EQUALS,
			getDataSourceId());
		filterBuilder.addFilter(
			"dataSourceIndividualPKs/individualPKs",
			FilterConstants.COMPARISON_OPERATOR_EQUALS, individualPK);

		MultivaluedHashMap<String, Object> uriVariables =
			new MultivaluedHashMap<>();

		uriVariables.putSingle("includeAnonymousUsers", true);

		try {
			String response = _jsonWebServiceClient.doGet(
				_PATH_INDIVIDUALS,
				_getParameters(
					filterBuilder,
					FilterConstants.FIELD_NAME_CONTEXT_INDIVIDUAL, 1, 1, null,
					uriVariables),
				_headers);

			Results<Individual> individualResults =
				_individualJSONObjectMapper.mapToResults(response);

			List<Individual> items = individualResults.getItems();

			if (!ListUtil.isEmpty(items)) {
				return items.get(0);
			}

			return null;
		}
		catch (IOException ioException) {
			throw new NestableRuntimeException(
				_ERROR_MSG + ioException.getMessage(), ioException);
		}
	}

	@Override
	public Results<Individual> getIndividualResults(
		String individualSegmentId, int cur, int delta,
		List<OrderByField> orderByFields) {

		try {
			String response = _jsonWebServiceClient.doGet(
				StringUtil.replace(
					_PATH_INDIVIDUAL_SEGMENTS_INDIVIDUALS, "{id}",
					individualSegmentId),
				_getParameters(
					new FilterBuilder(),
					FilterConstants.FIELD_NAME_CONTEXT_INDIVIDUAL, cur, delta,
					orderByFields),
				_headers);

			return _individualJSONObjectMapper.mapToResults(response);
		}
		catch (IOException ioException) {
			throw new NestableRuntimeException(
				_ERROR_MSG + ioException.getMessage(), ioException);
		}
	}

	@Override
	public Results<IndividualSegment> getIndividualSegmentResults(
		int cur, int delta, List<OrderByField> orderByFields) {

		FilterBuilder filterBuilder = new FilterBuilder();

		filterBuilder.addFilter(
			"individualCount",
			FilterConstants.COMPARISON_OPERATOR_GREATER_THAN_OR_EQUAL, 1);
		filterBuilder.addFilter(
			"status", FilterConstants.COMPARISON_OPERATOR_EQUALS,
			IndividualSegment.Status.ACTIVE.name());

		try {
			String response = _jsonWebServiceClient.doGet(
				_PATH_INDIVIDUAL_SEGMENTS,
				_getParameters(
					filterBuilder,
					FilterConstants.FIELD_NAME_CONTEXT_INDIVIDUAL_SEGMENT, cur,
					delta, orderByFields),
				_headers);

			return _individualSegmentJSONObjectMapper.mapToResults(response);
		}
		catch (IOException ioException) {
			throw new NestableRuntimeException(
				_ERROR_MSG + ioException.getMessage(), ioException);
		}
	}

	@Override
	public Results<Topic> getInterestTermsResults(String userId) {
		try {
			String response = _jsonWebServiceClient.doGet(
				StringUtil.replace(_PATH_INTERESTS_TERMS, "{userId}", userId),
				new MultivaluedHashMap<>(), _headers);

			return _interestTermsJSONObjectMapper.mapToResults(response);
		}
		catch (IOException ioException) {
			throw new NestableRuntimeException(
				"Unable to handle JSON response: " + ioException.getMessage(),
				ioException);
		}
	}

	@Override
	public void updateExperiment(Experiment experiment) {
		if (Validator.isNull(experiment.getId())) {
			throw new IllegalArgumentException("Experiment ID is null");
		}

		_jsonWebServiceClient.doPatch(
			StringUtil.replace(
				_PATH_EXPERIMENTS_EXPERIMENT, "{experimentId}",
				experiment.getId()),
			experiment, _headers);
	}

	@Override
	public void updateExperimentDXPVariants(
		String experimentId, DXPVariants dxpVariants) {

		if (Validator.isNull(experimentId)) {
			throw new IllegalArgumentException("Experiment ID is null");
		}

		if (dxpVariants == null) {
			throw new IllegalArgumentException("DXPVariants is null");
		}

		_jsonWebServiceClient.doPut(
			StringUtil.replace(
				_PATH_EXPERIMENTS_DXP_VARIANTS, "{experimentId}", experimentId),
			dxpVariants, _headers);
	}

	private MultivaluedMap<String, Object> _getParameters(
		FilterBuilder filterBuilder, String fieldNameContext, int cur,
		int delta, List<OrderByField> orderByFields) {

		MultivaluedMap<String, Object> uriVariables = _getUriVariables(
			cur, delta, orderByFields, fieldNameContext);

		uriVariables.putSingle("filter", filterBuilder.build());

		return uriVariables;
	}

	private MultivaluedMap<String, Object> _getParameters(
		FilterBuilder filterBuilder, String fieldNameContext, int cur,
		int delta, List<OrderByField> orderByFields,
		MultivaluedMap<String, Object> initialUriVariables) {

		MultivaluedMap<String, Object> uriVariables = _getUriVariables(
			cur, delta, orderByFields, fieldNameContext, initialUriVariables);

		uriVariables.putSingle("filter", filterBuilder.build());

		return uriVariables;
	}

	private MultivaluedMap<String, Object> _getUriVariables(
		int cur, int delta, List<OrderByField> orderByFields,
		String fieldNameContext) {

		return _getUriVariables(
			cur, delta, orderByFields, fieldNameContext,
			new MultivaluedHashMap<>());
	}

	private MultivaluedMap<String, Object> _getUriVariables(
		int cur, int delta, List<OrderByField> orderByFields,
		String fieldNameContext, MultivaluedMap<String, Object> uriVariables) {

		uriVariables.putSingle("page", cur - 1);
		uriVariables.putSingle("size", delta);

		if ((orderByFields == null) || orderByFields.isEmpty()) {
			return uriVariables;
		}

		List<Object> sort = new ArrayList<>();

		for (OrderByField orderByField : orderByFields) {
			String fieldName = orderByField.getFieldName();

			if (!orderByField.isSystem() && (fieldNameContext != null)) {
				fieldName = StringUtil.replace(
					fieldNameContext, CharPool.QUESTION, fieldName);
			}

			sort.add(fieldName + StringPool.COMMA + orderByField.getOrderBy());
		}

		uriVariables.put("sort", sort);

		return uriVariables;
	}

	private static final String _ERROR_MSG = "Unable to handle JSON response: ";

	private static final String _PATH_EXPERIMENTS = "api/1.0/experiments";

	private static final String _PATH_EXPERIMENTS_DXP_VARIANTS =
		_PATH_EXPERIMENTS + "/{experimentId}/dxp-variants";

	private static final String _PATH_EXPERIMENTS_ESTIMATED_DAYS_DURATION =
		_PATH_EXPERIMENTS + "/{experimentId}/estimated-days-duration";

	private static final String _PATH_EXPERIMENTS_EXPERIMENT =
		_PATH_EXPERIMENTS + "/{experimentId}";

	private static final String _PATH_INDIVIDUAL_SEGMENTS =
		"api/1.0/individual-segments";

	private static final String _PATH_INDIVIDUAL_SEGMENTS_INDIVIDUALS =
		_PATH_INDIVIDUAL_SEGMENTS + "/{id}/individuals";

	private static final String _PATH_INDIVIDUALS = "api/1.0/individuals";

	private static final String _PATH_INTERESTS_TERMS =
		"api/1.0/interests/terms/{userId}";

	private static final Log _log = LogFactoryUtil.getLog(
		AsahFaroBackendClientImpl.class);

	private static final IndividualJSONObjectMapper
		_individualJSONObjectMapper = new IndividualJSONObjectMapper();
	private static final IndividualSegmentJSONObjectMapper
		_individualSegmentJSONObjectMapper =
			new IndividualSegmentJSONObjectMapper();
	private static final InterestTermsJSONObjectMapper
		_interestTermsJSONObjectMapper = new InterestTermsJSONObjectMapper();

	private final String _dataSourceId;
	private final Map<String, String> _headers = new HashMap<>();
	private final JSONWebServiceClient _jsonWebServiceClient;

}