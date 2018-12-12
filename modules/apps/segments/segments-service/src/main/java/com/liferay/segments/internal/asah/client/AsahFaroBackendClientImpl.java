/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.segments.internal.asah.client;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NestableRuntimeException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.segments.internal.asah.client.data.binding.IndividualJSONObjectMapper;
import com.liferay.segments.internal.asah.client.data.binding.IndividualSegmentJSONObjectMapper;
import com.liferay.segments.internal.asah.client.model.Individual;
import com.liferay.segments.internal.asah.client.model.IndividualSegment;
import com.liferay.segments.internal.asah.client.model.Rels;
import com.liferay.segments.internal.asah.client.model.Results;
import com.liferay.segments.internal.asah.client.util.FilterBuilder;
import com.liferay.segments.internal.asah.client.util.FilterConstants;
import com.liferay.segments.internal.asah.client.util.OrderByField;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(immediate = true, service = AsahFaroBackendClient.class)
public class AsahFaroBackendClientImpl implements AsahFaroBackendClient {

	@Override
	public Results<Individual> getIndividuals(
		String individualSegmentId, int cur, int delta,
		List<OrderByField> orderByFields) {

		try {
			String response = _jsonWebServiceClient.doGet(
				Rels.INDIVIDUALS,
				_getParameters(
					_getIndividualsFilterBuilder(
						_getDataSourceId(), individualSegmentId),
					FilterConstants.FIELD_NAME_CONTEXT_INDIVIDUAL, cur, delta,
					orderByFields),
				_getHeaders());

			return _individualJSONObjectMapper.mapToResults(response);
		}
		catch (IOException ioe) {
			throw new NestableRuntimeException(
				"Unable to handle JSON response: " + ioe.getMessage(), ioe);
		}
	}

	@Override
	public Results<IndividualSegment> getIndividualSegments(
		int cur, int delta, List<OrderByField> orderByFields) {

		try {
			String response = _jsonWebServiceClient.doGet(
				Rels.INDIVIDUAL_SEGMENTS,
				_getParameters(
					_getIndividualSegmentFilterBuilder(
						IndividualSegment.Status.ACTIVE.name(), 1),
					FilterConstants.FIELD_NAME_CONTEXT_INDIVIDUAL_SEGMENT, cur,
					delta, orderByFields),
				_getHeaders());

			return _individualSegmentJSONObjectMapper.mapToResults(response);
		}
		catch (IOException ioe) {
			throw new NestableRuntimeException(
				"Unable to handle JSON response: " + ioe.getMessage(), ioe);
		}
	}

	@Activate
	protected void activate() {
		_jsonWebServiceClient.setBaseURI(
			System.getProperty("asah.faro.backend.url"));
	}

	private String _getDataSourceId() {
		return System.getProperty("asah.faro.backend.dxp.dataSourceId");
	}

	private Map<String, String> _getHeaders() {
		Map<String, String> headers = new HashMap<>();

		headers.put(
			"OSB-Asah-Faro-Backend-Security-Signature",
			System.getProperty("asah.faro.backend.security.signature"));

		return headers;
	}

	private FilterBuilder _getIndividualSegmentFilterBuilder(
		String individualSegmentStatus,
		long individualSegmentMinIndividualCount) {

		FilterBuilder filterBuilder = new FilterBuilder();

		filterBuilder.addFilter(
			"individualCount",
			FilterConstants.COMPARISON_OPERATOR_GREATER_THAN_OR_EQUAL,
			individualSegmentMinIndividualCount);
		filterBuilder.addFilter(
			"status", FilterConstants.COMPARISON_OPERATOR_EQUALS,
			individualSegmentStatus);

		return filterBuilder;
	}

	private FilterBuilder _getIndividualsFilterBuilder(
		String dataSourceId, String individualSegmentId) {

		FilterBuilder filterBuilder = new FilterBuilder();

		filterBuilder.addFilter(
			"individualSegmentIds", FilterConstants.COMPARISON_OPERATOR_EQUALS,
			individualSegmentId);

		filterBuilder.addFilter(
			"dataSourceIndividualPKs/" + dataSourceId,
			FilterConstants.COMPARISON_OPERATOR_NOT_EQUALS, StringPool.NULL);

		return filterBuilder;
	}

	private MultivaluedMap<String, Object> _getParameters(
		FilterBuilder filterBuilder, String fieldNameContext, int cur,
		int delta, List<OrderByField> orderByFields) {

		MultivaluedMap<String, Object> uriVariables = _getUriVariables(
			cur, delta, orderByFields, fieldNameContext);

		uriVariables.putSingle("filter", filterBuilder.build());

		return uriVariables;
	}

	private MultivaluedMap<String, Object> _getUriVariables(
		int cur, int delta, List<OrderByField> orderByFields,
		String fieldNameContext) {

		MultivaluedMap<String, Object> uriVariables =
			new MultivaluedHashMap<>();

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

	private static final IndividualJSONObjectMapper
		_individualJSONObjectMapper = new IndividualJSONObjectMapper();
	private static final IndividualSegmentJSONObjectMapper
		_individualSegmentJSONObjectMapper =
			new IndividualSegmentJSONObjectMapper();

	@Reference
	private JSONWebServiceClient _jsonWebServiceClient;

}