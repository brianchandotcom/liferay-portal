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

import com.liferay.headless.builder.constants.HeadlessBuilderConstants;
import com.liferay.headless.builder.internal.util.HeadlessBuilderUtil;
import com.liferay.headless.builder.operation.MediaType;
import com.liferay.headless.builder.operation.Operation;
import com.liferay.headless.builder.operation.OperationContext;
import com.liferay.headless.builder.operation.Response;
import com.liferay.headless.builder.operation.handler.OperationHandler;
import com.liferay.headless.builder.operation.response.NotFoundOperationResponse;
import com.liferay.headless.builder.operation.response.OperationResponse;
import com.liferay.headless.builder.operation.response.ResponseCode;
import com.liferay.headless.builder.operation.response.SuccessfulOperationResponse;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.portal.kernel.util.GetterUtil;

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
	public OperationResponse handle(
			Operation operation, OperationContext operationContext)
		throws Exception {

		InfoFieldValue<?> pkInfoFieldValue =
			operationContext.getPrimaryKeyInfoFieldValue();

		if (pkInfoFieldValue == null) {
			throw new UnsupportedOperationException(
				"The operation context does not contain the primary key");
		}

		MediaType mediaType = operationContext.getMediaType();

		Response response = operation.getResponse(
			mediaType, ResponseCode.SUCCESSFUL);

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

			return new SuccessfulOperationResponse(
				HeadlessBuilderUtil.toHeadlessBuilderEntry(
					infoItemFieldValues.getInfoFieldValues(), primaryKey,
					response.getSchemaName()),
				response);
		}
		catch (NoSuchInfoItemException noSuchInfoItemException) {
			String message = noSuchInfoItemException.getMessage();

			Throwable throwable = noSuchInfoItemException.getCause();

			if (throwable != null) {
				message = throwable.getMessage();
			}

			return new NotFoundOperationResponse(
				message,
				operation.getResponse(mediaType, ResponseCode.NOT_FOUND));
		}
	}

}