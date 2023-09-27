/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.search.spi.model.query.contributor;

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
 * @author Erick Monteiro
 */
@Component(
	property = "indexer.class.name=com.liferay.account.model.AccountGroup",
	service = ModelPreFilterContributor.class
)
public class AccountGroupModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		booleanFilter.addRequiredTerm("defaultAccountGroup", false);

		long[] accountEntryIds = GetterUtil.getLongValues(
			searchContext.getAttribute("accountEntryIds"), null);

		if (ArrayUtil.isNotEmpty(accountEntryIds)) {
			TermsFilter termsFilter = new TermsFilter("accountEntryIds");

			termsFilter.addValues(ArrayUtil.toStringArray(accountEntryIds));

			booleanFilter.add(termsFilter, BooleanClauseOccur.MUST);
		}
	}

}