/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.odata.filter.expression.field.predicate.provider;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.odata.filter.expression.field.predicate.provider.FieldPredicateProvider;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.portal.odata.filter.expression.BinaryExpression;

import java.util.List;
import java.util.function.Function;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio Jiménez del Coso
 */
@Component(
	property = "field.predicate.provider.key=" + ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST,
	service = FieldPredicateProvider.class
)
public class MultiselectPicklistFieldPredicateProvider
	implements FieldPredicateProvider {

	@Override
	public Predicate getBinaryExpressionPredicate(
		Function<String, Column<?, ?>> objectDefinitionColumnSupplier,
		Object left, long objectDefinitionId,
		BinaryExpression.Operation operation, Object right) {

		return null;
	}

	@Override
	public Predicate getContainsPredicate(
		Function<String, Column<?, ?>> objectDefinitionColumnSupplier,
		Object fieldValue) {

		return null;
	}

	@Override
	public Predicate getInPredicate(
		Function<String, Column<?, ?>> objectDefinitionColumnSupplier,
		List<Object> rights) {

		return null;
	}

	@Override
	public Predicate getStartsWithPredicate(
		Function<String, Column<?, ?>> objectDefinitionColumnSupplier,
		Object fieldValue) {

		return null;
	}

}