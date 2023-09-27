/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.filter.factory;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.field.business.type.ObjectFieldBusinessTypeRegistry;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.related.models.ObjectRelatedModelsPredicateProviderRegistry;
import com.liferay.object.rest.filter.factory.BaseFilterFactory;
import com.liferay.object.rest.filter.factory.FilterFactory;
import com.liferay.object.rest.internal.odata.filter.expression.PredicateExpressionVisitorImpl;
import com.liferay.object.rest.internal.odata.filter.expression.field.predicate.provider.FieldPredicateProviderTracker;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.expression.ExpressionVisitor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "filter.factory.key=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
	service = FilterFactory.class
)
public class DefaultFilterFactoryImpl
	extends BaseFilterFactory<Predicate> implements FilterFactory<Predicate> {

	@Override
	public ExpressionVisitor<Object> getExpressionVisitor(
		EntityModel entityModel, ObjectDefinition objectDefinition) {

		return new PredicateExpressionVisitorImpl(
			entityModel, entityModelProvider, _fieldPredicateProviderTracker,
			objectDefinition, _objectFieldBusinessTypeRegistry,
			_objectFieldLocalService,
			_objectRelatedModelsPredicateProviderRegistry);
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