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

package com.liferay.object.storage.salesforce.internal.filter.factory;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.rest.filter.factory.BaseFilterFactory;
import com.liferay.object.rest.filter.factory.FilterFactory;
import com.liferay.object.rest.odata.entity.v1_0.ObjectEntryEntityModel;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.storage.salesforce.internal.odata.filter.expression.SOSQLExpressionVisitorImpl;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.InvalidFilterException;
import com.liferay.portal.odata.filter.expression.Expression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.ws.rs.ServerErrorException;

/**
 * @author Paulo Albuquerque
 */
@Component(
	property = "filter.factory.key=" + ObjectDefinitionConstants.STORAGE_TYPE_SALESFORCE,
	service = FilterFactory.class
)
public class SalesforceFilterFactoryImpl
	extends BaseFilterFactory implements FilterFactory<String> {

	@Override
	public String create(
		EntityModel entityModel, String filterString, long objectDefinitionId) {

		if (Validator.isNull(filterString)) {
			return null;
		}

		try {
			Expression expression = getExpression(entityModel, filterString);

			return (String)expression.accept(
				new SOSQLExpressionVisitorImpl(
					objectDefinitionId, _objectFieldLocalService));
		}
		catch (ExpressionVisitException expressionVisitException) {
			throw new InvalidFilterException(
				expressionVisitException.getMessage(),
				expressionVisitException);
		}
		catch (InvalidFilterException invalidFilterException) {
			throw invalidFilterException;
		}
		catch (Exception exception) {
			throw new ServerErrorException(500, exception);
		}
	}

	@Override
	public String create(String filterString, long objectDefinitionId) {
		try {
			EntityModel entityModel = new ObjectEntryEntityModel(
				objectDefinitionId,
				_objectFieldLocalService.getObjectFields(objectDefinitionId));

			return create(entityModel, filterString, objectDefinitionId);
		}
		catch (ExpressionVisitException expressionVisitException) {
			throw new InvalidFilterException(
				expressionVisitException.getMessage(),
				expressionVisitException);
		}
		catch (InvalidFilterException invalidFilterException) {
			throw invalidFilterException;
		}
		catch (Exception exception) {
			throw new ServerErrorException(500, exception);
		}
	}

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}