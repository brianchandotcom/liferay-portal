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

package com.liferay.portal.search.internal.contributor.query;

import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.util.ExpandoBridgeFactory;
import com.liferay.expando.kernel.util.ExpandoBridgeIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.search.internal.expando.ExpandoQueryContributorHelper;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = KeywordQueryContributor.class)
public class ExpandoKeywordQueryContributor implements KeywordQueryContributor {

	@Override
	public void contribute(
		String keywords, BooleanQuery booleanQuery,
		KeywordQueryContributorHelper keywordQueryContributorHelper) {

		SearchContext searchContext =
			keywordQueryContributorHelper.getSearchContext();

		ExpandoQueryContributorHelper expandoQueryContributorHelper =
			new ExpandoQueryContributorHelper(
				expandoBridgeFactory, expandoBridgeIndexer,
				expandoColumnLocalService, getLocalization());

		expandoQueryContributorHelper.setAndSearch(searchContext.isAndSearch());
		expandoQueryContributorHelper.setBooleanQuery(booleanQuery);
		expandoQueryContributorHelper.setClassNamesStream(
			keywordQueryContributorHelper.getSearchClassNamesStream());
		expandoQueryContributorHelper.setCompanyId(
			searchContext.getCompanyId());
		expandoQueryContributorHelper.setKeywords(keywords);
		expandoQueryContributorHelper.setLocale(searchContext.getLocale());

		expandoQueryContributorHelper.contribute();
	}

	protected Localization getLocalization() {

		// See LPS-72507

		if (localization != null) {
			return localization;
		}

		return LocalizationUtil.getLocalization();
	}

	@Reference
	protected ExpandoBridgeFactory expandoBridgeFactory;

	@Reference
	protected ExpandoBridgeIndexer expandoBridgeIndexer;

	@Reference
	protected ExpandoColumnLocalService expandoColumnLocalService;

	protected Localization localization;

}