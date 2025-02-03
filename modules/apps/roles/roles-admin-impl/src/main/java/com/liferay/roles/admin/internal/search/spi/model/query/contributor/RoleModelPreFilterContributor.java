/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.roles.admin.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import org.osgi.service.component.annotations.Component;

/**
 * @author Stefano Motta
 */
@Component(
	property = "indexer.class.name=com.liferay.portal.kernel.model.Role",
	service = ModelPreFilterContributor.class
)
public class RoleModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter contextBooleanFilter,
		ModelSearchSettings modelSearchSettings, SearchContext searchContext) {

		_filterByAccountEntryId(contextBooleanFilter, searchContext);
		_filterByType(contextBooleanFilter, searchContext);
	}

	private void _filterByAccountEntryId(
		BooleanFilter contextBooleanFilter, SearchContext searchContext) {

		contextBooleanFilter.add(
			new TermFilter(
				"accountEntryId",
				String.valueOf(
					GetterUtil.getLong(
						searchContext.getAttribute("accountEntryId")))),
			BooleanClauseOccur.MUST);
	}

	private void _filterByType(
		BooleanFilter contextBooleanFilter, SearchContext searchContext) {

		long[] types = GetterUtil.getLongValues(
			searchContext.getAttribute("types"),
			new long[] {
				RoleConstants.TYPE_ORGANIZATION, RoleConstants.TYPE_REGULAR,
				RoleConstants.TYPE_SITE
			});

		if (ArrayUtil.isNotEmpty(types)) {
			TermsFilter termsFilter = new TermsFilter(Field.TYPE);

			termsFilter.addValues(ArrayUtil.toStringArray(types));

			contextBooleanFilter.add(termsFilter, BooleanClauseOccur.MUST);
		}
	}

}