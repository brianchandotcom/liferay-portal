/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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