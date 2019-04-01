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

package com.liferay.portal.vulcan.internal.jaxrs.context.provider;

import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.vulcan.content.space.ContentSpace;
import com.liferay.portal.vulcan.internal.jaxrs.param.converter.test.ContentSpaceParamConverter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

/**
 * @author Víctor Galán
 */
@Provider
public class VulcanParamConverterProvider implements ParamConverterProvider {

	public VulcanParamConverterProvider(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Override
	public <T> ParamConverter<T> getConverter(
		Class<T> clazz, Type type, Annotation[] annotations) {

		if (clazz == ContentSpace.class) {
			return (ParamConverter<T>)new ContentSpaceParamConverter(
				_groupLocalService);
		}

		return null;
	}

	private final GroupLocalService _groupLocalService;

}