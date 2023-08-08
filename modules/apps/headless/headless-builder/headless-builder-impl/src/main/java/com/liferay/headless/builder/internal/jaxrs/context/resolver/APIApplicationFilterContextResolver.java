/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.jaxrs.context.resolver;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.headless.builder.internal.odata.entity.APISchemaEntityModel;
import com.liferay.headless.builder.internal.odata.filter.expression.APISchemaTranslatorExpressionVisitor;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.filter.parser.ObjectDefinitionFilterParser;
import com.liferay.object.rest.odata.entity.v1_0.provider.EntityModelProvider;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.InvalidFilterException;
import com.liferay.portal.odata.filter.expression.BinaryExpression;
import com.liferay.portal.odata.filter.expression.Expression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;
import com.liferay.portal.odata.filter.expression.factory.ExpressionFactory;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * @author Luis Miguel Barcos
 */
@Provider
public class APIApplicationFilterContextResolver
	implements ContextResolver<Expression> {

	public APIApplicationFilterContextResolver(
		EntityModelProvider entityModelProvider,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectDefinitionFilterParser objectDefinitionFilterParser,
		ExpressionFactory expressionFactory) {

		_entityModelProvider = entityModelProvider;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectDefinitionFilterParser = objectDefinitionFilterParser;
		_expressionFactory = expressionFactory;
	}

	@Override
	public Expression getContext(Class<?> aClass) {
		APIApplication.Filter filter = _endpoint.getFilter();
		String filterString = ParamUtil.getString(
			_httpServletRequest, "filter");

		if ((filter == null) && Validator.isNull(filterString)) {
			return null;
		}

		APIApplication.Schema schema = _endpoint.getResponseSchema();

		ObjectDefinition objectDefinition = _getObjectDefinition(
			_company.getCompanyId(),
			schema.getMainObjectDefinitionExternalReferenceCode());

		EntityModel entityModel = _entityModelProvider.getEntityModel(
			objectDefinition);

		Expression endpointFilterExpression = null;

		if (filter != null) {
			endpointFilterExpression = _objectDefinitionFilterParser.parse(
				entityModel, filter.getODataFilterString(), objectDefinition);
		}

		Expression requestFilterExpression = null;

		if (Validator.isNotNull(filterString)) {
			EntityModel apiSchemaEntityModel = new APISchemaEntityModel(
				entityModel, _endpoint.getResponseSchema());

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

	private ObjectDefinition _getObjectDefinition(
		long companyId, String objectDefinitionExternalReferenceCode) {

		try {
			return _objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					objectDefinitionExternalReferenceCode, companyId);
		}
		catch (PortalException portalException) {
			throw new NotFoundException(portalException);
		}
	}

	@Context
	private Company _company;

	@Context
	private AcceptLanguage _contextAcceptLanguage;

	@Context
	private APIApplication.Endpoint _endpoint;

	private final EntityModelProvider _entityModelProvider;
	private final ExpressionFactory _expressionFactory;

	@Context
	private HttpServletRequest _httpServletRequest;

	private final ObjectDefinitionFilterParser _objectDefinitionFilterParser;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;

}