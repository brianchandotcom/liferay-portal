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

package com.liferay.portal.vulcan.internal.param.converter.provider;

import com.liferay.portal.vulcan.internal.param.converter.DateParamConverter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import java.util.Date;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

/**
 * @author Ivica Cardic
 */
@Provider
public class DateParamConverterProvider implements ParamConverterProvider {

	@Override
	@SuppressWarnings("unchecked")
	public <T> ParamConverter<T> getConverter(
		Class<T> rawType, Type genericType, Annotation[] annotations) {

		if (Date.class.equals(rawType)) {
			return (ParamConverter<T>)new DateParamConverter();
		}

		return null;
	}

}