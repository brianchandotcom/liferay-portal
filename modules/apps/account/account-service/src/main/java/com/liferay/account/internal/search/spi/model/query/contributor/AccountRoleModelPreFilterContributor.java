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

package com.liferay.account.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import org.osgi.service.component.annotations.Component;

/**
 * @author Drew Brokke
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.account.model.AccountRole",
	service = ModelPreFilterContributor.class
)
public class AccountRoleModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		_filterByAccountEntryIds(booleanFilter, searchContext);
		_filterByExcludedRoleNames(booleanFilter, searchContext);
	}

	private void _filterByAccountEntryIds(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		long[] accountEntryIds = (long[])searchContext.getAttribute(
			"accountEntryIds");

		if (ArrayUtil.isNotEmpty(accountEntryIds)) {
			TermsFilter termsFilter = new TermsFilter("accountEntryId");

			termsFilter.addValues(ArrayUtil.toStringArray(accountEntryIds));

			booleanFilter.add(termsFilter, BooleanClauseOccur.MUST);
		}
	}

	private void _filterByExcludedRoleNames(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		String[] excludedRoleNames = (String[])searchContext.getAttribute(
			"excludedRoleNames");

		if (ArrayUtil.isNotEmpty(excludedRoleNames)) {
			TermsFilter termsFilter = new TermsFilter(
				Field.getSortableFieldName(Field.NAME));

			for (String excludedRoleName : excludedRoleNames) {
				termsFilter.addValue(StringUtil.lowerCase(excludedRoleName));
			}

			booleanFilter.add(termsFilter, BooleanClauseOccur.MUST_NOT);
		}
	}

}