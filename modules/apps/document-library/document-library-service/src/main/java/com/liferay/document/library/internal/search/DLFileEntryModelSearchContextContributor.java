/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.search;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.spi.model.query.contributor.SearchContextContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.SearchContextContributorHelper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(
	property = "indexer.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = SearchContextContributor.class
)
public class DLFileEntryModelSearchContextContributor
	implements SearchContextContributor {

	@Override
	public void contribute(
		SearchContext searchContext,
		SearchContextContributorHelper searchContextContributorHelper) {

		if (!searchContext.isIncludeAttachments()) {
			return;
		}

		searchContext.addFullQueryEntryClassName(DLFileEntry.class.getName());
	}

}