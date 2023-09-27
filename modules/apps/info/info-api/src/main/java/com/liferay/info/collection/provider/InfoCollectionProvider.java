/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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