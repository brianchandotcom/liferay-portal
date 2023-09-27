/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.helper;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.headless.builder.internal.odata.entity.APISchemaEntityModel;
import com.liferay.headless.builder.internal.odata.filter.expression.APISchemaTranslatorExpressionVisitor;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.filter.parser.ObjectDefinitionFilterParser;
import com.liferay.object.rest.odata.entity.v1_0.provider.EntityModelProvider;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.InvalidFilterException;
import com.liferay.portal.odata.filter.expression.BinaryExpression;
import com.liferay.portal.odata.filter.expression.Expression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;
import com.liferay.portal.odata.filter.expression.factory.ExpressionFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Miguel Barcos
 */
@Component(service = FilterExpressionHelper.class)
public class FilterExpressionHelper {

	public Expression getExpression(
			long companyId, APIApplication.Endpoint endpoint,
			String filterString)
		throws PortalException {

		APIApplication.Filter filter = endpoint.getFilter();

		if ((filter == null) && Validator.isNull(filterString)) {
			return null;
		}

		Expression endpointFilterExpression = null;

		APIApplication.Schema schema = endpoint.getResponseSchema();

		String objectDefinitionExternalReferenceCode =
			schema.getMainObjectDefinitionExternalReferenceCode();

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					objectDefinitionExternalReferenceCode, companyId);

		EntityModel entityModel = _entityModelProvider.getEntityModel(
			objectDefinition);

		if (filter != null) {
			endpointFilterExpression = _objectDefinitionFilterParser.parse(
				entityModel, filter.getODataFilterString(), objectDefinition);
		}

		Expression requestFilterExpression = null;

		if (Validator.isNotNull(filterString)) {
			EntityModel apiSchemaEntityModel = new APISchemaEntityModel(
				entityModel, endpoint.getResponseSchema());

			Expression expression = _objectDefinitionFilterParser.parse(
				apiSchemaEntityModel, filterString, objectDefinition);

			try {
				requestFilterExpression = expression.accept(
					new APISchemaTranslatorExpressionVisitor(
						apiSchemaEntityModel, _expressionFactory));
			}
			catch (ExpressionVisitException expressionVisitException) {
				throw new InvalidFilterException(
					expressionVisitException.getMessage(),
					expressionVisitException);
			}
		}

		if (endpointFilterExpression == null) {
			return requestFilterExpression;
		}
		else if (requestFilterExpression == null) {
			return endpointFilterExpression;
		}

		return _expressionFactory.createBinaryExpression(
			endpointFilterExpression, BinaryExpression.Operation.AND,
			requestFilterExpression);
	}

	@Reference
	private EntityModelProvider _entityModelProvider;

	@Reference
	private ExpressionFactory _expressionFactory;

	@Reference
	private ObjectDefinitionFilterParser _objectDefinitionFilterParser;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}