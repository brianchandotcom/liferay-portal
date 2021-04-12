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

import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.info.sort.Sort;
import com.liferay.info.type.Keyed;
import com.liferay.info.type.Labeled;
import com.liferay.petra.reflect.GenericUtil;

/**
 * @author Jorge Ferrer
 */
public interface InfoItemRelatedListProvider<S, R> extends Keyed, Labeled {

	public default Class<? extends R> getRelatedItemClass() {
		return (Class<? extends R>)GenericUtil.getGenericClass(this, 1);
	}

	public InfoPage<? extends R> getRelatedItemsInfoPage(
		S sourceItem, InfoListProviderContext infoListProviderContext,
		Pagination pagination, Sort sort);

	public default Class<? extends S> getSourceItemClass() {
		return (Class<? extends S>)GenericUtil.getGenericClass(this, 0);
	}

}