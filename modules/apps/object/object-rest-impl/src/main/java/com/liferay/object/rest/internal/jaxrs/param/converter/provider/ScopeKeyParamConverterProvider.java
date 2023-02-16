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

package com.liferay.object.rest.internal.jaxrs.param.converter.provider;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.util.GroupUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.utils.AnnotationUtils;

/**
 * @author Jorge García Jiménez
 */
@Provider
public class ScopeKeyParamConverterProvider
	implements ParamConverter<String>, ParamConverterProvider {

	public ScopeKeyParamConverterProvider(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Override
	public String fromString(String parameter) {
		MultivaluedMap<String, String> multivaluedMap =
			_uriInfo.getPathParameters();

		String scopeKey = getGroupId(
			_company.getCompanyId(), multivaluedMap.getFirst("scopeKey"));

		if (scopeKey != null) {
			return scopeKey;
		}

		StringBundler sb = new StringBundler(2);

		sb.append("Unable to get a valid scopeKey with name ");
		sb.append(parameter);

		throw new NotFoundException(sb.toString());
	}

	@Override
	public <T> ParamConverter<T> getConverter(
		Class<T> clazz, Type type, Annotation[] annotations) {

		if (String.class.equals(clazz) && _hasScopeKeyAnnotation(annotations)) {
			return (ParamConverter<T>)this;
		}

		return null;
	}

	public String getGroupId(long companyId, String scopeKey) {
		if (scopeKey == null) {
			return null;
		}

		Long groupId = GroupUtil.getGroupId(
			companyId, scopeKey, _groupLocalService);

		if (groupId == null) {
			return null;
		}

		return String.valueOf(groupId);
	}

	@Override
	public String toString(String parameter) {
		return String.valueOf(parameter);
	}

	private boolean _hasScopeKeyAnnotation(Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			if ((annotation.annotationType() == PathParam.class) &&
				StringUtil.equals(
					AnnotationUtils.getAnnotationValue(annotation),
					"scopeKey")) {

				return true;
			}
		}

		return false;
	}

	@Context
	private Company _company;

	private final GroupLocalService _groupLocalService;

	@Context
	private UriInfo _uriInfo;

}