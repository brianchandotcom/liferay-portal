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

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.spi.model.query.contributor.SearchContextContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.SearchContextContributorHelper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = SearchContextContributor.class)
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

		String[] entryClassNames = ArrayUtil.append(
			searchContextContributorHelper.getSearchClassNames(),
			fullQueryEntryClassNames);

		searchContext.setEntryClassNames(entryClassNames);
	}

}