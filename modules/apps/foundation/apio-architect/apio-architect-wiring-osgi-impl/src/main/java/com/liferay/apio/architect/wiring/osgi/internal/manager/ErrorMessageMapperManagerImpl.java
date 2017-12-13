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

package com.liferay.apio.architect.wiring.osgi.internal.manager;

import static org.osgi.service.component.annotations.ReferenceCardinality.MULTIPLE;
import static org.osgi.service.component.annotations.ReferencePolicyOption.GREEDY;

import com.liferay.apio.architect.error.APIError;
import com.liferay.apio.architect.error.ApioDeveloperError;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.message.json.ErrorMessageMapper;
import com.liferay.apio.architect.wiring.osgi.manager.ErrorMessageMapperManager;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class ErrorMessageMapperManagerImpl
	implements ErrorMessageMapperManager {

	@Override
	public ErrorMessageMapper getErrorMessageMapper(
		APIError apiError, HttpHeaders httpHeaders) {

		String mediaType = _getMediaType(httpHeaders);

		Optional<ErrorMessageMapper> optional = _getErrorMessageMapper(
			mediaType, apiError, httpHeaders);

		return optional.orElseGet(
			() -> _getProblemJSONMessageMapper(apiError, httpHeaders));
	}

	private Optional<ErrorMessageMapper> _getErrorMessageMapper(
		String mediaTypeString, APIError apiError, HttpHeaders httpHeaders) {

		Stream<ErrorMessageMapper> stream = _errorMessageMappers.stream();

		return stream.filter(
			messageMapper ->
				mediaTypeString.equals(messageMapper.getMediaType()) &&
				messageMapper.supports(apiError, httpHeaders)
		).findFirst();
	}

	private String _getMediaType(HttpHeaders httpHeaders) {
		List<MediaType> acceptableMediaTypes =
			httpHeaders.getAcceptableMediaTypes();

		Try<MediaType> mediaTypeTry = Try.fromFallible(
			() -> acceptableMediaTypes.get(0));

		return mediaTypeTry.filter(
			mediaType -> mediaType != MediaType.WILDCARD_TYPE
		).map(
			MediaType::toString
		).orElse(
			"application/problem+json"
		);
	}

	private ErrorMessageMapper _getProblemJSONMessageMapper(
		APIError apiError, HttpHeaders httpHeaders) {

		Optional<ErrorMessageMapper> optional = _getErrorMessageMapper(
			"application/problem+json", apiError, httpHeaders);

		return optional.orElseThrow(
			ApioDeveloperError.MustHaveProblemJSONErrorMessageMapper::new);
	}

	@Reference(cardinality = MULTIPLE, policyOption = GREEDY)
	private List<ErrorMessageMapper> _errorMessageMappers;

}