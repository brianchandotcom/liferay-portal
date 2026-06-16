/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import org.osgi.service.component.annotations.Component;

/**
 * @author Javier Moral
 */
@Component(
	property = "indexer.class.name=com.liferay.fragment.model.FragmentEntry",
	service = ModelPreFilterContributor.class
)
public class FragmentEntryModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		long fragmentCollectionId = GetterUtil.getLong(
			searchContext.getAttribute("fragmentCollectionId"));

		if (fragmentCollectionId > 0) {
			booleanFilter.addRequiredTerm(
				"fragmentCollectionId", fragmentCollectionId);
		}

		boolean head = GetterUtil.getBoolean(
			searchContext.getAttribute("head"), true);
		boolean headListable = GetterUtil.getBoolean(
			searchContext.getAttribute("headListable"));

		if (headListable) {
			booleanFilter.addRequiredTerm("headListable", true);
		}
		else if (head) {
			booleanFilter.addRequiredTerm("head", true);
		}
	}

}