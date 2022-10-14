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
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rachael Koestartyo
 */
@Component(
	property = "indexer.class.name=com.liferay.portal.kernel.model.User",
	service = ModelPreFilterContributor.class
)
public class UserModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

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

	private TermsFilter _createTermsFilter(String filterName, String[] values) {
		TermsFilter termsFilter = new TermsFilter(filterName);

		termsFilter.addValues(values);

		return termsFilter;
	}

}