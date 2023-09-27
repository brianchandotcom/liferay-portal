/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.spi.model.query.contributor;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(
	property = "indexer.class.name=ALL",
	service = ModelPreFilterContributor.class
)
public class StagingModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		if (!modelSearchSettings.isStagingAware()) {
			return;
		}

		if (!searchContext.isIncludeLiveGroups() &&
			searchContext.isIncludeStagingGroups()) {

			booleanFilter.addTerm(
				Field.STAGING_GROUP, "true", BooleanClauseOccur.MUST);
		}
		else if (searchContext.isIncludeLiveGroups() &&
				 !searchContext.isIncludeStagingGroups()) {

			booleanFilter.addTerm(
				Field.STAGING_GROUP, "true", BooleanClauseOccur.MUST_NOT);
		}
	}

}