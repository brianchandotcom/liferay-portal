/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.spi.model.query.contributor;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.spi.model.query.contributor.SearchContextContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.SearchContextContributorHelper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = SearchContextContributor.class)
public class RelatedEntrySearchContextContributor
	implements SearchContextContributor {

	@Override
	public void contribute(
		SearchContext searchContext,
		SearchContextContributorHelper searchContextContributorHelper) {

		String[] fullQueryEntryClassNames =
			searchContext.getFullQueryEntryClassNames();

		if (ArrayUtil.isNotEmpty(fullQueryEntryClassNames)) {
			searchContext.setAttribute(
				"relatedEntryClassNames",
				searchContextContributorHelper.getSearchClassNames());
		}

		searchContext.setEntryClassNames(
			ArrayUtil.append(
				searchContextContributorHelper.getSearchClassNames(),
				fullQueryEntryClassNames));
	}

}