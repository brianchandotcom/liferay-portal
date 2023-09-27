/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.collection.provider;

import com.liferay.info.type.Keyed;
import com.liferay.info.type.Labeled;
import com.liferay.petra.reflect.GenericUtil;

/**
 * @author Eudaldo Alonso
 */
public interface RelatedInfoItemCollectionProvider<S, C>
	extends InfoCollectionProvider<C>, Keyed, Labeled {

	@Override
	public default Class<?> getCollectionItemClass() {
		return GenericUtil.getGenericClass(this, 1);
	}

	public default Class<?> getSourceItemClass() {
		return GenericUtil.getGenericClass(this);
	}

	public default String getSourceItemClassName() {
		Class<?> clazz = getSourceItemClass();

		return clazz.getName();
	}

}