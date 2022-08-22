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

package com.liferay.portal.vulcan.dto.converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.ws.rs.core.UriInfo;

/**
 * @author Rubén Pulido
 * @author Víctor Galán
 */
public interface DTOConverter<E, D> {

	public String getContentType();

	public default String getDTOClassName() {
		Class<?> clazz = getClass();

		Type[] types = clazz.getGenericInterfaces();

		for (Type type : types) {
			String typeName = type.getTypeName();

			if (!typeName.contains(DTOConverter.class.getSimpleName())) {
				continue;
			}

			ParameterizedType parameterizedType = (ParameterizedType)type;

			Type[] argumentTypes = parameterizedType.getActualTypeArguments();

			return argumentTypes[0].getTypeName();
		}

		return null;
	}

	public default String getJaxRsLink(long classPK, UriInfo uriInfo) {
		return null;
	}

	public default E getObject(String externalReferenceCode) throws Exception {
		return null;
	}

	public default D toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		return toDTO(
			dtoConverterContext,
			getObject(String.valueOf(dtoConverterContext.getId())));
	}

	public default D toDTO(DTOConverterContext dtoConverterContext, E object)
		throws Exception {

		return null;
	}

	public default D toDTO(E object) throws Exception {
		return toDTO(null, object);
	}

}