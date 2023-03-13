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

package com.liferay.headless.builder.internal.operation.handler;

import com.liferay.headless.builder.internal.constants.HeadlessBuilderConstants;
import com.liferay.headless.builder.internal.operation.Operation;
import com.liferay.headless.builder.internal.util.HeadlessBuilderUtil;
import com.liferay.headless.builder.internal.util.URLUtil;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Correa
 */
@Component(
	property = HeadlessBuilderConstants.OPERATION_NAME + "=getByPrimaryKey",
	service = OperationHandler.class
)
public class GetByPrimaryKeyOperationHandler implements OperationHandler {

	@Override
	public Response handle(
			HttpServletRequest httpServletRequest, Operation operation)
		throws Exception {

		Operation.Response response = operation.getResponse(
			httpServletRequest.getHeader(HttpHeaders.ACCEPT),
			Response.Status.OK.getStatusCode());

		InfoItemObjectProvider<?> infoItemObjectProvider =
			HeadlessBuilderUtil.getInfoItemService(
				response.getEntityName(), InfoItemObjectProvider.class);

		try {
			Map<String, String> pathParameters = URLUtil.getPathParameters(
				httpServletRequest.getRequestURI(),
				operation.getPathConfiguration());

			Object object = infoItemObjectProvider.getInfoItem(
				GetterUtil.getLong(pathParameters.get("id")));

			InfoItemFieldValuesProvider infoItemFieldValuesProvider =
				HeadlessBuilderUtil.getInfoItemService(
					response.getEntityName(),
					InfoItemFieldValuesProvider.class);

			return Response.status(
				Response.Status.OK
			).entity(
				HeadlessBuilderUtil.getEntity(
					infoItemFieldValuesProvider.getInfoItemFieldValues(object),
					response)
			).build();
		}
		catch (NoSuchInfoItemException noSuchInfoItemException) {
			String message = noSuchInfoItemException.getMessage();

			Throwable throwable = noSuchInfoItemException.getCause();

			if (throwable != null) {
				message = throwable.getMessage();
			}

			return Response.status(
				Response.Status.NOT_FOUND
			).entity(
				new Problem(Response.Status.NOT_FOUND, message)
			).build();
		}
	}

}