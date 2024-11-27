/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.search.spi.model.query.contributor;

import com.liferay.commerce.product.constants.CPField;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import org.osgi.service.component.annotations.Component;

/**
 * @author Andrea Sbarra
 */
@Component(
	property = {
		"indexer.class.name=com.liferay.commerce.product.model.CPConfigurationEntry",
		"indexer.clauses.mandatory=true"
	},
	service = ModelPreFilterContributor.class
)
public class CPConfigurationEntryModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		long cpConfigurationListId = GetterUtil.getLong(
			searchContext.getAttribute(CPField.CP_CONFIGURATION_LIST_ID));

		if (cpConfigurationListId > 0) {
			booleanFilter.add(
				new TermFilter(
					CPField.CP_CONFIGURATION_LIST_ID,
					String.valueOf(cpConfigurationListId)),
				BooleanClauseOccur.MUST);
		}

		booleanFilter.add(
			new TermFilter(Field.ENTRY_CLASS_PK, StringPool.BLANK),
			BooleanClauseOccur.MUST_NOT);
	}

}