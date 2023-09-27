/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.spi.model.query.contributor.QueryPreFilterContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(service = QueryPreFilterContributor.class)
public class AssetEntryModelPreFilterContributor
	implements QueryPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter fullQueryBooleanFilter, SearchContext searchContext) {

		long[] assetEntryIds = GetterUtil.getLongValues(
			searchContext.getAttribute(Field.ASSET_ENTRY_IDS));

		if (ArrayUtil.isEmpty(assetEntryIds)) {
			return;
		}

		TermsFilter assetEntryIdsTermsFilter = new TermsFilter(
			Field.ASSET_ENTRY_ID);

		assetEntryIdsTermsFilter.addValues(
			ArrayUtil.toStringArray(assetEntryIds));

		fullQueryBooleanFilter.add(
			assetEntryIdsTermsFilter, BooleanClauseOccur.MUST);
	}

}