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

package com.liferay.portal.odata.internal.filter;

import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.expression.Expression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;
import com.liferay.portal.search.query.NestedFieldQueryHelper;

import java.text.Format;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	property = "result.class.name=com.liferay.portal.kernel.search.filter.Filter",
	service = ExpressionConvert.class
)
public class ExpressionConvertImpl implements ExpressionConvert<Filter> {

	@Override
	public Filter convert(
			Expression expression, Locale locale, EntityModel entityModel)
		throws ExpressionVisitException {

		Format format = FastDateFormatFactoryUtil.getSimpleDateFormat(
			PropsUtil.get(PropsKeys.INDEX_DATE_FORMAT_PATTERN));

		return (Filter)expression.accept(
			new ExpressionVisitorImpl(
				format, locale, entityModel, nestedFieldQueryHelper));
	}

	@Reference
	protected NestedFieldQueryHelper nestedFieldQueryHelper;

}