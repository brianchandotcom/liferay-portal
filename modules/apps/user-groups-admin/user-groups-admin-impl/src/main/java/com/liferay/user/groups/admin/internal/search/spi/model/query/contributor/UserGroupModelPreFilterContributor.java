/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.groups.admin.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rafael Cardoso
 */
@Component(
	property = "indexer.class.name=com.liferay.portal.kernel.model.UserGroup",
	service = ModelPreFilterContributor.class
)
public class UserGroupModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		long[] userIds = (long[])searchContext.getAttribute("userIds");

		if (ArrayUtil.isNotEmpty(userIds)) {
			TermsFilter termsFilter = new TermsFilter("userIds");

			termsFilter.addValues(ArrayUtil.toStringArray(userIds));

			booleanFilter.add(termsFilter, BooleanClauseOccur.MUST);
		}
	}

}