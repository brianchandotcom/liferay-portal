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

package com.liferay.analytics.settings.web.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rachael Koestartyo
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.portal.kernel.model.User",
	service = IndexerPostProcessor.class
)
public class UserModelIndexerPostProcessor implements IndexerPostProcessor {

	@Override
	public void postProcessContextBooleanFilter(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		long[] excludedRoleIds = GetterUtil.getLongValues(
			searchContext.getAttribute("excludedRoleIds"));

		if (!ArrayUtil.isEmpty(excludedRoleIds)) {
			booleanFilter.add(
				_createTermsFilter(
					"roleIds", ArrayUtil.toStringArray(excludedRoleIds)),
				BooleanClauseOccur.MUST_NOT);
		}

		BooleanFilter innerBooleanFilter = new BooleanFilter();

		long[] selectedOrganizationIds = GetterUtil.getLongValues(
			searchContext.getAttribute("selectedOrganizationIds"));

		if (!ArrayUtil.isEmpty(selectedOrganizationIds)) {
			innerBooleanFilter.add(
				_createTermsFilter(
					"organizationIds",
					ArrayUtil.toStringArray(selectedOrganizationIds)),
				BooleanClauseOccur.SHOULD);
		}

		long[] selectedUserGroupIds = GetterUtil.getLongValues(
			searchContext.getAttribute("selectedUserGroupIds"));

		if (!ArrayUtil.isEmpty(selectedUserGroupIds)) {
			innerBooleanFilter.add(
				_createTermsFilter(
					"userGroupIds",
					ArrayUtil.toStringArray(selectedUserGroupIds)),
				BooleanClauseOccur.SHOULD);
		}

		if (innerBooleanFilter.hasClauses()) {
			booleanFilter.add(innerBooleanFilter, BooleanClauseOccur.MUST);
		}
	}

	@Override
	public void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {
	}

	@Override
	public void postProcessDocument(Document document, Object obj)
		throws Exception {
	}

	@Override
	public void postProcessFullQuery(
			BooleanQuery fullQuery, SearchContext searchContext)
		throws Exception {
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter booleanFilter,
			SearchContext searchContext)
		throws Exception {
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {
	}

	@Override
	public void postProcessSummary(
		Summary summary, Document document, Locale locale, String snippet) {
	}

	private TermsFilter _createTermsFilter(String filterName, String[] values) {
		TermsFilter termsFilter = new TermsFilter(filterName);

		termsFilter.addValues(values);

		return termsFilter;
	}

}