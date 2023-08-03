/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.filter.parser;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.filter.parser.ObjectDefinitionFilterParser;
import com.liferay.object.rest.odata.entity.v1_0.provider.EntityModelProvider;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.FilterParser;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.odata.filter.InvalidFilterException;
import com.liferay.portal.odata.filter.expression.Expression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Correa
 */
@Component(service = ObjectDefinitionFilterParser.class)
public class ObjectDefinitionFilterParserImpl
	implements ObjectDefinitionFilterParser {

	@Override
	public Expression parse(
			EntityModel entityModel, String filterString,
			ObjectDefinition objectDefinition)
		throws InvalidFilterException {

		if (Validator.isNull(filterString)) {
			return null;
		}

		FilterParser filterParser = _filterParserProvider.provide(entityModel);

		try {
			return filterParser.parse(filterString);
		}
		catch (ExpressionVisitException expressionVisitException) {
			throw new InvalidFilterException(
				expressionVisitException.getMessage(),
				expressionVisitException);
		}
	}

	@Override
	public Expression parse(
			String filterString, ObjectDefinition objectDefinition)
		throws InvalidFilterException {

		if (Validator.isNull(filterString)) {
			return null;
		}

		return parse(
			_entityModelProvider.getEntityModel(objectDefinition), filterString,
			objectDefinition);
	}

	@Reference
	private EntityModelProvider _entityModelProvider;

	@Reference
	private FilterParserProvider _filterParserProvider;

}