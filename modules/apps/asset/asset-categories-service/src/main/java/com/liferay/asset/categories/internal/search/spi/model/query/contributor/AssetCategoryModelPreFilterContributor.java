/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.MissingFilter;
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

		long[] classNameIds = (long[])searchContext.getAttribute(
			"classNameIds");

		if (ArrayUtil.isNotEmpty(classNameIds)) {
			BooleanFilter classNameIdsBooleanFilter = new BooleanFilter();

			for (long classNameId : classNameIds) {
				TermsFilter classNameIdsTermsFilter = new TermsFilter(
					"classNameIds");

				classNameIdsTermsFilter.addValues(String.valueOf(classNameId));

				classNameIdsBooleanFilter.add(
					classNameIdsTermsFilter, BooleanClauseOccur.SHOULD);
			}

			classNameIdsBooleanFilter.add(
				new MissingFilter("classNameIds"), BooleanClauseOccur.SHOULD);

			booleanFilter.add(
				classNameIdsBooleanFilter, BooleanClauseOccur.MUST);
		}

		long[] groupIds = (long[])searchContext.getAttribute("groupIds");

		if (ArrayUtil.isNotEmpty(groupIds)) {
			BooleanFilter groupIdsBooleanFilter = new BooleanFilter();

			for (long groupId : groupIds) {
				TermsFilter termsFilter = new TermsFilter("groupIds");

				termsFilter.addValues(String.valueOf(groupId));

				groupIdsBooleanFilter.add(
					termsFilter, BooleanClauseOccur.SHOULD);
			}

			if (groupIdsBooleanFilter.hasClauses()) {
				booleanFilter.add(
					groupIdsBooleanFilter, BooleanClauseOccur.MUST);
			}
		}

		long[] parentCategoryIds = (long[])searchContext.getAttribute(
			Field.ASSET_PARENT_CATEGORY_IDS);

		if (ArrayUtil.isNotEmpty(parentCategoryIds)) {
			TermsFilter parentCategoryTermsFilter = new TermsFilter(
				Field.ASSET_PARENT_CATEGORY_ID);

			parentCategoryTermsFilter.addValues(
				ArrayUtil.toStringArray(parentCategoryIds));

			booleanFilter.add(
				parentCategoryTermsFilter, BooleanClauseOccur.MUST);
		}

		long[] vocabularyIds = (long[])searchContext.getAttribute(
			Field.ASSET_VOCABULARY_IDS);

		if (ArrayUtil.isNotEmpty(vocabularyIds)) {
			TermsFilter vocabularyTermsFilter = new TermsFilter(
				Field.ASSET_VOCABULARY_ID);

			vocabularyTermsFilter.addValues(
				ArrayUtil.toStringArray(vocabularyIds));

			booleanFilter.add(vocabularyTermsFilter, BooleanClauseOccur.MUST);
		}
	}

}