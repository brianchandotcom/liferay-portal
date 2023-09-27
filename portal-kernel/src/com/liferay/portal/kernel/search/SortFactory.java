/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public interface SortFactory {

	public Sort create(String fieldName, boolean reverse);

	public Sort create(String fieldName, int type, boolean reverse);

	public Sort[] getDefaultSorts();

	public Sort getSort(
		Class<?> clazz, int type, String orderByCol, boolean inferSortField,
		String orderByType);

	public Sort getSort(
		Class<?> clazz, int type, String orderByCol, String orderByType);

	public Sort getSort(Class<?> clazz, String orderByCol, String orderByType);

	public Sort[] toArray(List<Sort> sorts);

}