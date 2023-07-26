/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.storage.salesforce.internal.filter.factory;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.filter.factory.BaseFilterFactory;
import com.liferay.object.rest.filter.factory.FilterFactory;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.storage.salesforce.internal.odata.filter.expression.SOSQLExpressionVisitorImpl;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.InvalidFilterException;
import com.liferay.portal.odata.filter.expression.Expression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;

import javax.ws.rs.ServerErrorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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
		EntityModel entityModel, String filterString,
		ObjectDefinition objectDefinition) {

		if (Validator.isNull(filterString)) {
			return null;
		}

		try {
			Expression expression = getExpression(entityModel, filterString);

			return (String)expression.accept(
				new SOSQLExpressionVisitorImpl(
					objectDefinition.getObjectDefinitionId(),
					_objectFieldLocalService));
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
	public String create(
		String filterString, ObjectDefinition objectDefinition) {

		try {
			return create(
				entityModelProvider.getEntityModel(objectDefinition), filterString,
				objectDefinition);
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