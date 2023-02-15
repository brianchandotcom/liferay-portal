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
import com.liferay.headless.builder.internal.dto.converter.HeadlessBuilderElementDTOConverter;
import com.liferay.headless.builder.internal.operation.Operation;
import com.liferay.headless.builder.internal.operation.OperationContext;
import com.liferay.headless.builder.internal.operation.Response;
import com.liferay.headless.builder.internal.util.HeadlessBuilderUtil;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Correa
 */
@Component(
	property = HeadlessBuilderConstants.OPERATION_NAME + "=getByPrimaryKey",
	service = OperationHandler.class
)
public class GetByPrimaryKeyOperationHandler implements OperationHandler {

	@Override
	public javax.ws.rs.core.Response handle(
			Operation operation, OperationContext operationContext)
		throws Exception {

		InfoFieldValue<?> pkInfoFieldValue =
			operationContext.getPrimaryKeyInfoFieldValue();

		if (pkInfoFieldValue == null) {
			throw new UnsupportedOperationException(
				"The operation context does not contain the primary key");
		}

		Response response = operation.getResponse(
			operationContext.getMediaType(),
			javax.ws.rs.core.Response.Status.OK.getStatusCode());

		InfoItemObjectProvider<?> infoItemObjectProvider =
			HeadlessBuilderUtil.getInfoItemService(
				response.getEntityName(), InfoItemObjectProvider.class);

		long primaryKey = GetterUtil.getLong(pkInfoFieldValue.getValue());

		try {
			Object object = infoItemObjectProvider.getInfoItem(primaryKey);

			InfoItemFieldValuesProvider infoItemFieldValuesProvider =
				HeadlessBuilderUtil.getInfoItemService(
					response.getEntityName(),
					InfoItemFieldValuesProvider.class);

			InfoItemFieldValues infoItemFieldValues =
				infoItemFieldValuesProvider.getInfoItemFieldValues(object);

			return javax.ws.rs.core.Response.status(
				javax.ws.rs.core.Response.Status.OK
			).entity(
				_dtoConverter.toDTO(
					_getDTOConverterContext(response),
					HeadlessBuilderUtil.toHeadlessBuilderEntry(
						infoItemFieldValues.getInfoFieldValues(), primaryKey,
						response.getSchemaName()))
			).build();
		}
		catch (NoSuchInfoItemException noSuchInfoItemException) {
			String message = noSuchInfoItemException.getMessage();

			Throwable throwable = noSuchInfoItemException.getCause();

			if (throwable != null) {
				message = throwable.getMessage();
			}

			return javax.ws.rs.core.Response.status(
				javax.ws.rs.core.Response.Status.NOT_FOUND
			).entity(
				new Problem(javax.ws.rs.core.Response.Status.NOT_FOUND, message)
			).build();
		}
	}

	private DTOConverterContext _getDTOConverterContext(Response response) {
		DTOConverterContext dtoConverterContext =
			new DefaultDTOConverterContext(null, null, null, null, null);

		dtoConverterContext.setAttribute("response", response);

		return dtoConverterContext;
	}

	@Reference
	private HeadlessBuilderElementDTOConverter _dtoConverter;

}