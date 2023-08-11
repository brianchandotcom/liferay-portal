/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.list.retriever;

import com.liferay.info.filter.InfoFilter;
import com.liferay.info.pagination.InfoPage;
import com.liferay.item.selector.ItemSelectorReturnType;

import java.util.Collections;
import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Eudaldo Alonso
 */
@ProviderType
public interface LayoutListRetriever
	<T extends ItemSelectorReturnType, S extends ListObjectReference> {

	public default InfoPage<?> getInfoPage(
		S s, LayoutListRetrieverContext layoutListRetrieverContext) {

		return InfoPage.of(
			getList(s, layoutListRetrieverContext),
			layoutListRetrieverContext.getPagination(),
			() -> getListCount(s, layoutListRetrieverContext));
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #getInfoPage(S, LayoutListRetrieverContext)}
	 */
	@Deprecated
	public List<Object> getList(
		S s, LayoutListRetrieverContext layoutListRetrieverContext);

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #getInfoPage(S, LayoutListRetrieverContext)}
	 */
	@Deprecated
	public int getListCount(
		S s, LayoutListRetrieverContext layoutListRetrieverContext);

	public default List<InfoFilter> getSupportedInfoFilters(S s) {
		return Collections.emptyList();
	}

}