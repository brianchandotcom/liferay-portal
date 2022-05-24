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

package com.liferay.object.rest.internal.odata.filter.expression;

import com.liferay.object.petra.sql.dsl.ObjectTableProvider;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.expression.Expression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;

import java.text.Format;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = PredicateExpressionConvert.class)
public class PredicateExpressionConvert {

	public Predicate convert(
			long objectDefinitionId, EntityModel entityModel,
			Expression expression, Locale locale)
		throws ExpressionVisitException {

		Format format = FastDateFormatFactoryUtil.getSimpleDateFormat(
			PropsUtil.get(PropsKeys.INDEX_DATE_FORMAT_PATTERN));

		return (Predicate)expression.accept(
			new PredicateExpressionVisitorImpl(
				entityModel, format, locale, objectDefinitionId,
				_objectTableProvider));
	}

	@Reference
	private ObjectTableProvider _objectTableProvider;

}