/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.item.provider;

import com.liferay.info.type.Keyed;
import com.liferay.petra.reflect.GenericUtil;

import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public interface RelatedInfoItemProvider<T> extends Keyed {

	public List<String> getRelatedItemClassNames();

	public default Class<?> getSourceItemClass() {
		return GenericUtil.getGenericClass(this);
	}

	public default String getSourceItemClassName() {
		Class<?> clazz = getSourceItemClass();

		return clazz.getName();
	}

}