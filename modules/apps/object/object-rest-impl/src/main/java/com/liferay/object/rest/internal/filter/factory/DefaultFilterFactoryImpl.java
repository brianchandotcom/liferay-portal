/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.filter.factory;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.field.business.type.ObjectFieldBusinessTypeRegistry;
import com.liferay.object.related.models.ObjectRelatedModelsPredicateProviderRegistry;
import com.liferay.object.rest.filter.factory.BaseFilterFactory;
import com.liferay.object.rest.filter.factory.FilterFactory;
import com.liferay.object.rest.internal.odata.filter.expression.PredicateExpressionVisitorImpl;
import com.liferay.object.rest.internal.odata.filter.expression.field.predicate.provider.FieldPredicateProviderTracker;
import com.liferay.object.rest.odata.entity.v1_0.ObjectEntryEntityModel;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.InvalidFilterException;
import com.liferay.portal.odata.filter.expression.Expression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.ws.rs.ServerErrorException;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "filter.factory.key=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
	service = FilterFactory.class
)
public class DefaultFilterFactoryImpl
	extends BaseFilterFactory implements FilterFactory<Predicate> {

	@Override
	public Predicate create(
		EntityModel entityModel, String filterString, long objectDefinitionId) {

		if (Validator.isNull(filterString)) {
			return null;
		}

		try {
			Expression expression = getExpression(entityModel, filterString);

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
	private ObjectFieldBusinessTypeRegistry _objectFieldBusinessTypeRegistry;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectRelatedModelsPredicateProviderRegistry
		_objectRelatedModelsPredicateProviderRegistry;

}