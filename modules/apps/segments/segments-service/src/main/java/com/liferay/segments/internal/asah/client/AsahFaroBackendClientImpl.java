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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NestableRuntimeException;
import com.liferay.portal.kernel.util.Validator;
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
	public Results<IndividualSegment> getIndividualSegments(
		int cur, int delta, List<OrderByField> orderByFields) {

		try {
			String response = _jsonWebServiceClient.doGet(
				Rels.INDIVIDUAL_SEGMENTS,
				_getParameters(
					IndividualSegment.Status.ACTIVE.name(), 1L, cur, delta,
					orderByFields),
				_getHeaders());

			return _asahJSONMapper.mapToIndividualSegmentResults(response);
		}
		catch (IOException ioe) {
			throw new NestableRuntimeException(
				"Error handling JSON response: " + ioe.getMessage(), ioe);
		}
	}

	@Activate
	protected void activate() {
		_jsonWebServiceClient.setBaseURI(_ASAH_FARO_BACKEND_URL);
	}

	private static String _getSystemEnv(String name, String fallbackValue) {
		String value = System.getenv(name);

		if (Validator.isNull(value)) {
			return fallbackValue;
		}

		return value;
	}

	private Map<String, String> _getHeaders() {
		Map<String, String> headers = new HashMap<>();

		headers.put(
			_ASAH_SECURITY_SIGNATURE_HEADER, _ASAH_SECURITY_SIGNATURE_VALUE);

		return headers;
	}

	private MultivaluedMap<String, Object> _getParameters(
		String individualSegmentStatus,
		Long individualSegmentMinIndividualCount, int cur, int delta,
		List<OrderByField> orderByFields) {

		MultivaluedMap<String, Object> uriVariables = _getUriVariables(
			cur, delta, orderByFields);

		FilterBuilder filterBuilder = new FilterBuilder();

		if (Validator.isNotNull(individualSegmentStatus)) {
			filterBuilder.addFilter(
				"status", FilterConstants.COMPARISON_OPERATOR_EQUALS,
				individualSegmentStatus);
		}

		if (Validator.isNotNull(individualSegmentMinIndividualCount)) {
			filterBuilder.addFilter(
				"individualCount",
				FilterConstants.COMPARISON_OPERATOR_GREATER_THAN_OR_EQUAL,
				individualSegmentMinIndividualCount);
		}

		uriVariables.putSingle("filter", filterBuilder.build());

		return uriVariables;
	}

	private MultivaluedMap<String, Object> _getUriVariables(
		int cur, int delta, List<OrderByField> orderByFields) {

		MultivaluedMap<String, Object> uriVariables =
			new MultivaluedHashMap<>();

		uriVariables.putSingle("page", cur - 1);
		uriVariables.putSingle("size", delta);

		if (orderByFields == null || orderByFields.isEmpty()) {
			return uriVariables;
		}

		List<Object> sort = new ArrayList<>();

		for (OrderByField orderByField : orderByFields) {
			String fieldName = orderByField.getFieldName();

			sort.add(fieldName + StringPool.COMMA + orderByField.getOrderBy());
		}

		uriVariables.put("sort", sort);

		return uriVariables;
	}

	private static final String _ASAH_FARO_BACKEND_URL = _getSystemEnv(
		"ASAH_FARO_BACKEND_URL",
		AsahFaroBackendClientImpl._ASAH_FARO_BACKEND_URL_DEV);

	private static final String _ASAH_FARO_BACKEND_URL_DEV =
		"https://osbasahfarobackend-asah93fdaf9914e34506bf664b9ab652fc01." +
			"eu-west-1.lfr.cloud";

	private static final String _ASAH_SECURITY_SIGNATURE_HEADER = _getSystemEnv(
		"ASAH_SECURITY_SIGNATURE_HEADER",
		"OSB-Asah-Faro-Backend-Security-Signature");

	private static final String _ASAH_SECURITY_SIGNATURE_VALUE = System.getenv(
		"ASAH_SECURITY_SIGNATURE_VALUE");

	private static final AsahJSONMapper _asahJSONMapper = new AsahJSONMapper();

	@Reference
	private JSONWebServiceClient _jsonWebServiceClient;

}