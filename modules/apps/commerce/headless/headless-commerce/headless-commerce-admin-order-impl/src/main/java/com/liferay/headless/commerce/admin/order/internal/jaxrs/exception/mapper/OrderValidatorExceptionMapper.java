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

package com.liferay.headless.commerce.admin.order.internal.jaxrs.exception.mapper;

import com.liferay.commerce.exception.CommerceOrderValidatorException;
import com.liferay.commerce.order.CommerceOrderValidatorResult;
import com.liferay.headless.commerce.core.exception.mapper.BaseExceptionMapper;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Headless.Commerce.Admin.Order)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Headless.Commerce.Admin.Order.OrderValidatorException"
	},
	service = ExceptionMapper.class
)
@Provider
public class OrderValidatorExceptionMapper
	extends BaseExceptionMapper<CommerceOrderValidatorException> {

	@Override
	public String getErrorDescription() {
		return "Invalid order";
	}

	@Override
	public Response.Status getStatus() {
		return Response.Status.BAD_REQUEST;
	}

	@Override
	public Response toResponse(
		CommerceOrderValidatorException commerceOrderValidatorException) {

		_log.error("General REST exception", commerceOrderValidatorException);

		Response.ResponseBuilder responseBuilder = Response.status(getStatus());

		Response.Status status = getStatus();

		responseBuilder.entity(
			toJSON(
				_getErrorMessage(commerceOrderValidatorException),
				status.getStatusCode()));

		responseBuilder.type(MediaType.APPLICATION_JSON_TYPE);

		return responseBuilder.build();
	}

	private String _getErrorMessage(
		CommerceOrderValidatorException commerceOrderValidatorException) {

		List<CommerceOrderValidatorResult> commerceOrderValidatorResults =
			commerceOrderValidatorException.getCommerceOrderValidatorResults();

		if (commerceOrderValidatorResults.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler();

		sb.append(StringPool.OPEN_CURLY_BRACE);

		for (CommerceOrderValidatorResult commerceOrderValidatorResult :
				commerceOrderValidatorResults) {

			int index = commerceOrderValidatorResults.indexOf(
				commerceOrderValidatorResult);

			if (index > 0) {
				sb.append(StringPool.COMMA);
			}

			sb.append(StringPool.QUOTE);
			sb.append(commerceOrderValidatorResult.getLocalizedMessage());
			sb.append(StringPool.QUOTE);
		}

		sb.append(StringPool.CLOSE_CURLY_BRACE);

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OrderValidatorExceptionMapper.class);

}