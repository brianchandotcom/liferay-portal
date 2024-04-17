/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.info.collection.provider.util;

import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.filter.KeywordsInfoFilter;
import com.liferay.portal.vulcan.pagination.Pagination;

/**
 * @author Carolina Barbosa
 */
public class ObjectEntryInfoCollectionProviderUtil {

	public static Pagination getPagination(
		com.liferay.info.pagination.Pagination pagination) {

		int page = 1;

		int pageSize = pagination.getEnd() - pagination.getStart();

		if (pageSize > 0) {
			page = pagination.getEnd() / pageSize;
		}

		return Pagination.of(page, pageSize);
	}

	public static String getSearch(CollectionQuery collectionQuery) {
		KeywordsInfoFilter keywordsInfoFilter = collectionQuery.getInfoFilter(
			KeywordsInfoFilter.class);

		if (keywordsInfoFilter == null) {
			return null;
		}

		return keywordsInfoFilter.getKeywords();
	}

}