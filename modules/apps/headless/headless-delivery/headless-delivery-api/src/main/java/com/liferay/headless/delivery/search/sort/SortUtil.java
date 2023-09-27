/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.search.sort;

import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.headless.delivery.dynamic.data.mapping.DDMStructureField;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Javier de Arcos
 */
public class SortUtil {

	public static void processSorts(
			DDMIndexer ddmIndexer, SearchRequestBuilder searchRequestBuilder,
			Sort[] oldSorts, Queries queries, Sorts sorts)
		throws PortalException {

		List<com.liferay.portal.search.sort.Sort> searchSorts =
			new ArrayList<>();

		for (Sort oldSort : oldSorts) {
			String sortFieldName = oldSort.getFieldName();
			SortOrder sortOrder =
				oldSort.isReverse() ? SortOrder.DESC : SortOrder.ASC;

			if (!sortFieldName.startsWith(DDMIndexer.DDM_FIELD_PREFIX) ||
				ddmIndexer.isLegacyDDMIndexFieldsEnabled()) {

				searchSorts.add(sorts.field(sortFieldName, sortOrder));

				continue;
			}

			DDMStructureField ddmStructureField = DDMStructureField.from(
				sortFieldName);

			searchSorts.add(
				ddmIndexer.createDDMStructureFieldSort(
					ddmStructureField.getDDMStructureFieldName(),
					LocaleUtil.fromLanguageId(ddmStructureField.getLocale()),
					sortOrder));
		}

		searchRequestBuilder.sorts(
			searchSorts.toArray(new com.liferay.portal.search.sort.Sort[0]));
	}

}