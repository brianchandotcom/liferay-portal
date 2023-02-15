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

package com.liferay.headless.builder.internal.resource;

import com.liferay.headless.builder.internal.dto.converter.HeadlessBuilderElementDTOConverter;
import com.liferay.headless.builder.internal.util.URLUtil;
import com.liferay.headless.builder.operation.MediaType;
import com.liferay.headless.builder.operation.Method;
import com.liferay.headless.builder.operation.Operation;
import com.liferay.headless.builder.operation.OperationContext;
import com.liferay.headless.builder.operation.OperationRegistry;
import com.liferay.headless.builder.operation.PathConfiguration;
import com.liferay.headless.builder.operation.handler.OperationHandler;
import com.liferay.headless.builder.operation.handler.OperationHandlerRegistry;
import com.liferay.headless.builder.operation.response.NotFoundOperationResponse;
import com.liferay.headless.builder.operation.response.OperationResponse;
import com.liferay.headless.builder.operation.response.ResponseCode;
import com.liferay.headless.builder.operation.response.SuccessfulOperationResponse;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.PrimaryKeyInfoFieldType;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Carlos Correa
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Headless.Builder.Application)",
		"osgi.jaxrs.resource=true"
	},
	scope = ServiceScope.PROTOTYPE, service = HeadlessBuilderResource.class
)
public class HeadlessBuilderResource extends BaseHeadlessBuilderResource {

	@GET
	@Path("{any: .*}")
	@Produces({"application/json", "application/xml"})
	public Response get() throws Exception {
		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-171047"))) {
			throw new NotFoundException();
		}

		Operation operation = _getOperation(
			_portal.getCompanyId(contextHttpServletRequest),
			Method.valueOf(contextHttpServletRequest.getMethod()),
			contextHttpServletRequest.getRequestURI());

		if (operation == null) {
			return Response.status(
				Response.Status.NOT_FOUND
			).entity(
				new Problem(Response.Status.NOT_FOUND, "Operation not found")
			).build();
		}

		OperationHandler operationHandler =
			_operationHandlerRegistry.getOperationHandler(operation);

		MediaType mediaType = MediaType.parse(
			contextHttpServletRequest.getHeader(HttpHeaders.ACCEPT));

		OperationResponse operationResponse = operationHandler.handle(
			operation, _getOperationContext(mediaType, operation));

		return _toResponse(operationResponse);
	}

	private DTOConverterContext _getDTOConverterContext(
		com.liferay.headless.builder.operation.Response response) {

		DTOConverterContext dtoConverterContext =
			new DefaultDTOConverterContext(null, null, null, null, null);

		dtoConverterContext.setAttribute("response", response);

		return dtoConverterContext;
	}

	private Operation _getOperation(
		long companyId, Method method, String path) {

		for (Operation operation : _operationRegistry.getOperations()) {
			if (companyId != operation.getCompanyId()) {
				continue;
			}

			Method operationMethod = operation.getMethod();

			if (!Objects.equals(operationMethod, method)) {
				continue;
			}

			PathConfiguration pathConfiguration =
				operation.getPathConfiguration();

			Pattern pattern = pathConfiguration.getPattern();

			Matcher matcher = pattern.matcher(path);

			if (matcher.matches()) {
				return operation;
			}
		}

		return null;
	}

	private OperationContext _getOperationContext(
		MediaType mediaType, Operation operation) {

		OperationContext.Builder builder = new OperationContext.Builder();

		for (InfoFieldValue<?> infoFieldValue :
				_getParameterInfoFieldValues(
					operation, contextHttpServletRequest)) {

			InfoField infoField = infoFieldValue.getInfoField();

			if (infoField.getInfoFieldType() instanceof
					PrimaryKeyInfoFieldType) {

				builder.withPrimaryKeyInfoFieldValue(infoFieldValue);
			}
			else {
				builder.withInfoFieldValue(infoFieldValue);
			}
		}

		return builder.withMediaType(
			mediaType
		).build();
	}

	private List<InfoFieldValue<?>> _getParameterInfoFieldValues(
		Operation operation, HttpServletRequest httpServletRequest) {

		List<InfoFieldValue<?>> infoFieldValues = new ArrayList<>();

		Map<String, String> pathParams = URLUtil.getPathParams(
			httpServletRequest.getRequestURI(),
			operation.getPathConfiguration());

		Map<String, InfoField> parameterInfoFields =
			operation.getParameterInfoFields();

		for (Map.Entry<String, InfoField> entry :
				parameterInfoFields.entrySet()) {

			InfoField infoField = entry.getValue();

			String externalFieldName = entry.getKey();

			if (!pathParams.containsKey(externalFieldName)) {
				if (infoField.isRequired()) {
					throw new IllegalArgumentException();
				}

				continue;
			}

			infoFieldValues.add(
				new InfoFieldValue(
					infoField, pathParams.get(externalFieldName)));
		}

		return infoFieldValues;
	}

	private Response _toResponse(OperationResponse operationResponse)
		throws Exception {

		if (Objects.equals(
				operationResponse.getResponseCode(), ResponseCode.SUCCESSFUL)) {

			SuccessfulOperationResponse successfulOperationResponse =
				(SuccessfulOperationResponse)operationResponse;

			return Response.status(
				Response.Status.OK
			).entity(
				_dtoConverter.toDTO(
					_getDTOConverterContext(
						successfulOperationResponse.getResponse()),
					successfulOperationResponse.getHeadlessBuilderEntry())
			).build();
		}
		else if (Objects.equals(
					operationResponse.getResponseCode(),
					ResponseCode.NOT_FOUND)) {

			NotFoundOperationResponse notFoundOperationResponse =
				(NotFoundOperationResponse)operationResponse;

			return Response.status(
				Response.Status.NOT_FOUND
			).entity(
				new Problem(
					Response.Status.NOT_FOUND,
					notFoundOperationResponse.getMessage())
			).build();
		}

		throw new UnsupportedOperationException();
	}

	@Reference
	private HeadlessBuilderElementDTOConverter _dtoConverter;

	@Reference
	private OperationHandlerRegistry _operationHandlerRegistry;

	@Reference
	private OperationRegistry _operationRegistry;

	@Reference
	private Portal _portal;

}