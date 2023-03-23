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

package com.liferay.object.rest.internal.petra.sql.dsl.expression;

import com.liferay.object.field.business.type.ObjectFieldBusinessTypeRegistry;
import com.liferay.object.related.models.ObjectRelatedModelsPredicateProviderRegistry;
import com.liferay.object.rest.internal.odata.entity.v1_0.ObjectEntryEntityModel;
import com.liferay.object.rest.internal.odata.filter.expression.PredicateExpressionVisitorImpl;
import com.liferay.object.rest.internal.odata.filter.expression.field.predicate.provider.FieldPredicateProviderTracker;
import com.liferay.object.rest.petra.sql.dsl.expression.FilterPredicateFactory;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.Filter;
import com.liferay.portal.odata.filter.FilterParser;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.odata.filter.InvalidFilterException;
import com.liferay.portal.odata.filter.expression.Expression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;

import javax.ws.rs.ServerErrorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(service = FilterPredicateFactory.class)
public class FilterPredicateFactoryImpl implements FilterPredicateFactory {

	@Override
	public Predicate create(
		EntityModel entityModel, String filterString, long objectDefinitionId) {

		if (Validator.isNull(filterString)) {
			return null;
		}

		try {
			FilterParser filterParser = _filterParserProvider.provide(
				entityModel);

			Filter oDataFilter = new Filter(filterParser.parse(filterString));

			Expression expression = oDataFilter.getExpression();

			return (Predicate)expression.accept(
				new PredicateExpressionVisitorImpl(
					entityModel, _fieldPredicateProviderTracker,
					objectDefinitionId, _objectFieldBusinessTypeRegistry,
					_objectFieldLocalService,
					_objectRelatedModelsPredicateProviderRegistry));
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
	public Predicate create(String filterString, long objectDefinitionId) {
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
	private FieldPredicateProviderTracker _fieldPredicateProviderTracker;

	@Reference
	private FilterParserProvider _filterParserProvider;

	@Reference
	private ObjectFieldBusinessTypeRegistry _objectFieldBusinessTypeRegistry;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectRelatedModelsPredicateProviderRegistry
		_objectRelatedModelsPredicateProviderRegistry;

}