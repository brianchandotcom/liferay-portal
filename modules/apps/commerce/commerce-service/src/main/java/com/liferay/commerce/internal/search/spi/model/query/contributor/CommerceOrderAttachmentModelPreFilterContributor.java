/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import org.osgi.service.component.annotations.Component;

/**
 * @author Stefano Motta
 */
@Component(
	property = "indexer.class.name=com.liferay.commerce.model.CommerceOrderAttachment",
	service = ModelPreFilterContributor.class
)
public class CommerceOrderAttachmentModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		long commerceOrderId = GetterUtil.getLong(
			searchContext.getAttribute("commerceOrderId"));

		if (commerceOrderId <= 0) {
			booleanFilter.add(
				new TermFilter("commerceOrderId", "-1"),
				BooleanClauseOccur.MUST);

			return;
		}

		booleanFilter.addRequiredTerm("commerceOrderId", commerceOrderId);
	}

}