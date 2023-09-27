/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.search.spi.model.query.contributor;

import com.liferay.account.constants.AccountConstants;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.ExistsFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import org.osgi.service.component.annotations.Component;

/**
 * @author Drew Brokke
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

		long[] accountEntryIds = GetterUtil.getLongValues(
			searchContext.getAttribute("accountEntryIds"), null);

		if (accountEntryIds != null) {
			if ((accountEntryIds.length == 1) &&
				(accountEntryIds[0] == AccountConstants.ACCOUNT_ENTRY_ID_ANY)) {

				ExistsFilter existsFilter = new ExistsFilter("accountEntryIds");

				booleanFilter.add(existsFilter, BooleanClauseOccur.MUST);
			}
			else if (accountEntryIds.length == 0) {
				ExistsFilter existsFilter = new ExistsFilter("accountEntryIds");

				booleanFilter.add(existsFilter, BooleanClauseOccur.MUST_NOT);
			}
			else {
				TermsFilter termsFilter = new TermsFilter("accountEntryIds");

				termsFilter.addValues(ArrayUtil.toStringArray(accountEntryIds));

				booleanFilter.add(termsFilter, BooleanClauseOccur.MUST);
			}
		}

		String[] emailAddressDomains = (String[])searchContext.getAttribute(
			"emailAddressDomains");

		if (emailAddressDomains != null) {
			TermsFilter emailAddressDomainTermsFilter = new TermsFilter(
				"emailAddressDomain");

			emailAddressDomainTermsFilter.addValues(emailAddressDomains);

			booleanFilter.add(
				emailAddressDomainTermsFilter, BooleanClauseOccur.MUST);
		}
	}

}