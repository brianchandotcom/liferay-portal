/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

/**
 * @author Lourdes Fernández Besada
 */
public class AssetVocabularyModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		int[] visibilityTypes = GetterUtil.getIntegerValues(
			searchContext.getAttribute(Field.VISIBILITY_TYPE));

		if (ArrayUtil.isEmpty(visibilityTypes)) {
			return;
		}

		TermsFilter assetEntryIdsTermsFilter = new TermsFilter(
			Field.VISIBILITY_TYPE);

		assetEntryIdsTermsFilter.addValues(
			ArrayUtil.toStringArray(visibilityTypes));

		booleanFilter.add(assetEntryIdsTermsFilter, BooleanClauseOccur.MUST);
	}

}