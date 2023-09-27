/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.collection.provider;

import com.liferay.info.filter.InfoFilter;

import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public interface FilteredInfoCollectionProvider<T>
	extends InfoCollectionProvider<T> {

	public List<InfoFilter> getSupportedInfoFilters();

}