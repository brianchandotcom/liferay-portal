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

package com.liferay.vulcan.wiring.osgi.manager;

import static org.osgi.service.component.annotations.ReferenceCardinality.MULTIPLE;
import static org.osgi.service.component.annotations.ReferencePolicyOption.GREEDY;

import com.liferay.vulcan.error.VulcanDeveloperError.MustHaveProblemJSONErrorMessageMapper;
import com.liferay.vulcan.message.json.ErrorMessageMapper;
import com.liferay.vulcan.result.APIError;
import com.liferay.vulcan.result.Try;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides methods to get the {@link
 * ErrorMessageMapper} that corresponds to the
 * {@link APIError} and {@code
 * HttpHeaders}.
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true, service = ErrorMessageMapperManager.class)
public class ErrorMessageMapperManager {

	/**
	 * Returns the {@code ErrorMessageMapper} that corresponds to the {@code
	 * APIError} and {@code HttpHeaders}. If no acceptable media type is found
	 * in the current request, or no mapper is found for that request's {@code
	 * Accept} type, this method returns a mapper for the {@code
	 * application/problem+json} media type.
	 *
	 * @param  apiError the {@code APIError}
	 * @param  httpHeaders the current request's HTTP headers
	 * @return the {@code ErrorMessageMapper} that corresponds to the {@code
	 *         APIError} and {@code HttpHeaders}
	 */
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

		return optional.orElseThrow(MustHaveProblemJSONErrorMessageMapper::new);
	}

	@Reference(cardinality = MULTIPLE, policyOption = GREEDY)
	private List<ErrorMessageMapper> _errorMessageMappers;

}