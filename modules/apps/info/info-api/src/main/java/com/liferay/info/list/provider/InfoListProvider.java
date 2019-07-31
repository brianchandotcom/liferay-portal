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

package com.liferay.info.list.provider;

import com.liferay.info.pagination.Pagination;
import com.liferay.info.sort.Sort;

import java.util.List;
import java.util.Locale;

/**
 * @author Jorge Ferrer
 */
public interface InfoListProvider<T> {

	public List<T> getInfoList(InfoListProviderContext infoListProviderContext);

	public List<T> getInfoList(
		InfoListProviderContext infoListProviderContext, Pagination pagination,
		Sort sort);

	public int getInfoListCount(
		InfoListProviderContext infoListProviderContext);

	public String getLabel(Locale locale);

	public default boolean isAvailable(
		InfoListProviderContext infoListProviderContext) {

		return true;
	}

}