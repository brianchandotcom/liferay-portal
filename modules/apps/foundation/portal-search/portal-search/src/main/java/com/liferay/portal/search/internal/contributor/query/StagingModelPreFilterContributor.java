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
	immediate = true, property = "indexer.class.name=ALL",
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

			booleanFilter.addRequiredTerm(Field.STAGING_GROUP, true);
		}
		else if (searchContext.isIncludeLiveGroups() &&
				 !searchContext.isIncludeStagingGroups()) {

			booleanFilter.addRequiredTerm(Field.STAGING_GROUP, false);
		}
	}

}