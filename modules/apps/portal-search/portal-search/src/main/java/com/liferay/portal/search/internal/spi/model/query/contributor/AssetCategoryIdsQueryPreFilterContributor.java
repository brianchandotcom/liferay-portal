/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.spi.model.query.contributor;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.spi.model.query.contributor.QueryPreFilterContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = QueryPreFilterContributor.class)
public class AssetCategoryIdsQueryPreFilterContributor
	implements QueryPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter fullQueryBooleanFilter, SearchContext searchContext) {

		long[] assetCategoryIds = searchContext.getAssetCategoryIds();

		if (ArrayUtil.isEmpty(assetCategoryIds)) {
			return;
		}

		TermsFilter categoryIdsTermsFilter = new TermsFilter(
			Field.ASSET_CATEGORY_IDS);

		categoryIdsTermsFilter.addValues(
			ArrayUtil.toStringArray(assetCategoryIds));

		if (!searchContext.isIncludeInternalAssetCategories()) {
			fullQueryBooleanFilter.add(
				categoryIdsTermsFilter, BooleanClauseOccur.MUST);

			return;
		}

		BooleanFilter booleanFilter = new BooleanFilter();

		TermsFilter internalCategoryIdsTermsFilter = new TermsFilter(
			Field.ASSET_INTERNAL_CATEGORY_IDS);

		internalCategoryIdsTermsFilter.addValues(
			ArrayUtil.toStringArray(assetCategoryIds));

		booleanFilter.add(categoryIdsTermsFilter);
		booleanFilter.add(internalCategoryIdsTermsFilter);

		fullQueryBooleanFilter.add(booleanFilter, BooleanClauseOccur.MUST);
	}

}