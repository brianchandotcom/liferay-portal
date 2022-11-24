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

package com.liferay.asset.categories.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import org.osgi.service.component.annotations.Component;

/**
 * @author Luan Maoski
 * @author Lucas Marques
 */
@Component(
	property = "indexer.class.name=com.liferay.asset.kernel.model.AssetCategory",
	service = ModelPreFilterContributor.class
)
public class AssetCategoryModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		long[] parentCategoryIds = (long[])searchContext.getAttribute(
			Field.ASSET_PARENT_CATEGORY_IDS);

		if (!ArrayUtil.isEmpty(parentCategoryIds)) {
			TermsFilter parentCategoryTermsFilter = new TermsFilter(
				Field.ASSET_PARENT_CATEGORY_ID);

			parentCategoryTermsFilter.addValues(
				ArrayUtil.toStringArray(parentCategoryIds));

			booleanFilter.add(
				parentCategoryTermsFilter, BooleanClauseOccur.MUST);
		}

		long[] vocabularyIds = (long[])searchContext.getAttribute(
			Field.ASSET_VOCABULARY_IDS);

		if (!ArrayUtil.isEmpty(vocabularyIds)) {
			TermsFilter vocabularyTermsFilter = new TermsFilter(
				Field.ASSET_VOCABULARY_ID);

			vocabularyTermsFilter.addValues(
				ArrayUtil.toStringArray(vocabularyIds));

			booleanFilter.add(vocabularyTermsFilter, BooleanClauseOccur.MUST);
		}
	}

}