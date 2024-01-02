/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.search.spi.model.query.contributor;

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
 * @author Danny Situ
 */
@Component(
	property = "indexer.class.name=com.liferay.account.model.AccountEntry",
	service = ModelPreFilterContributor.class
)
public class AccountEntryModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		long[] commerceChannelIds = GetterUtil.getLongValues(
			searchContext.getAttribute("commerceChannelIds"), null);

		if (commerceChannelIds != null) {
			TermsFilter termsFilter = new TermsFilter("commerceChannelIds");

			termsFilter.addValues(ArrayUtil.toStringArray(commerceChannelIds));

			booleanFilter.add(termsFilter, BooleanClauseOccur.MUST);
		}
	}

}