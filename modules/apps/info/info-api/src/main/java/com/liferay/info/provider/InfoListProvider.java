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

package com.liferay.info.provider;

import aQute.bnd.annotation.ConsumerType;

import com.liferay.info.pagination.Pagination;
import com.liferay.portal.kernel.search.Sort;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.List;
import java.util.Locale;

/**
 * @author Jorge Ferrer
 */
@ConsumerType
public interface InfoListProvider<T> {

	public List<T> getInfoList(InfoListProviderContext context);

	public List<T> getInfoList(
		InfoListProviderContext context, Pagination pagination, Sort sort);

	public int getInfoListCount(InfoListProviderContext context);

	public default Class getItemClass() {
		Class<?> infoListProviderClass = getClass();

		Type[] genericInterfaceTypess =
			infoListProviderClass.getGenericInterfaces();

		for (Type genericInterfaceType : genericInterfaceTypess) {
			ParameterizedType parameterizedType =
				(ParameterizedType)genericInterfaceType;

			Class<?> clazz = (Class)parameterizedType.getRawType();

			if (!clazz.equals(InfoListProvider.class)) {
				continue;
			}

			return (Class<?>)parameterizedType.getActualTypeArguments()[0];
		}

		return Object.class;
	}

	public String getLabel(Locale locale);

	public default boolean isAvailable(InfoListProviderContext context) {
		return true;
	}

}