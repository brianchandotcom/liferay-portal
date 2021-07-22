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

package com.liferay.info.collection.provider;

import com.liferay.info.pagination.InfoPage;
import com.liferay.info.type.Keyed;
import com.liferay.info.type.Labeled;
import com.liferay.petra.reflect.GenericUtil;

/**
 * @author Jorge Ferrer
 */
public interface InfoCollectionProvider<T> extends Keyed, Labeled {

	public InfoPage<T> getCollectionInfoPage(CollectionQuery collectionQuery);

	public default Class<?> getCollectionItemClass() {
		return GenericUtil.getGenericClass(this);
	}

	public default String getCollectionItemClassName() {
		Class<?> clazz = getCollectionItemClass();

		return clazz.getName();
	}

	public default boolean isAvailable() {
		return true;
	}

}