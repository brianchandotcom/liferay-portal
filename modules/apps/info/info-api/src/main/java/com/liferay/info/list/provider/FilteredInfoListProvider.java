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

import com.liferay.info.filter.InfoFilter;
import com.liferay.info.pagination.Pagination;
import com.liferay.info.sort.Sort;
import com.liferay.petra.reflect.GenericUtil;

import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public interface FilteredInfoListProvider<T, F extends InfoFilter>
	extends InfoListProvider<T> {

	public default Class<F> getInfoFilterClass() {
		return (Class<F>)GenericUtil.getGenericClass(this, 1);
	}

	public List<T> getInfoList(
		InfoListProviderContext infoListProviderContext, F infoFilter,
		Pagination pagination, Sort sort);

	public int getInfoListCount(
		InfoListProviderContext infoListProviderContext, F infoFilter);

}