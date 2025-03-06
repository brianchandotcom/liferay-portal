/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.MissingFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "indexer.class.name=com.liferay.commerce.model.CommerceShipment",
	service = ModelPreFilterContributor.class
)
public class CommerceShipmentModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		_filterByCommerceAccountIds(booleanFilter, searchContext);
		_filterByCommerceOrderIds(booleanFilter, searchContext);
		_filterByShipmentStatuses(booleanFilter, searchContext);
	}

	private void _filterByCommerceAccountIds(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		long[] commerceAccountIds = GetterUtil.getLongValues(
			searchContext.getAttribute("commerceAccountIds"), null);

		if (commerceAccountIds != null) {
			BooleanFilter commerceAccountIdBooleanFilter = new BooleanFilter();

			for (long commerceAccountId : commerceAccountIds) {
				commerceAccountIdBooleanFilter.add(
					new TermFilter(
						"commerceAccountId", String.valueOf(commerceAccountId)),
					BooleanClauseOccur.SHOULD);
			}

			commerceAccountIdBooleanFilter.add(
				new MissingFilter("commerceAccountId"),
				BooleanClauseOccur.SHOULD);

			booleanFilter.add(
				commerceAccountIdBooleanFilter, BooleanClauseOccur.MUST);
		}
	}

	private void _filterByCommerceOrderIds(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		long[] commerceOrderIds = GetterUtil.getLongValues(
			searchContext.getAttribute("commerceOrderIds"), null);

		if ((commerceOrderIds != null) && (commerceOrderIds.length > 0)) {
			BooleanFilter commerceOrderItemIdsBooleanFilter =
				new BooleanFilter();

			for (long commerceOrderId : commerceOrderIds) {
				commerceOrderItemIdsBooleanFilter.add(
					new TermFilter(
						"commerceOrderIds", String.valueOf(commerceOrderId)),
					BooleanClauseOccur.SHOULD);
			}

			booleanFilter.add(
				commerceOrderItemIdsBooleanFilter, BooleanClauseOccur.MUST);
		}
	}

	private void _filterByShipmentStatuses(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		int[] shipmentStatuses = GetterUtil.getIntegerValues(
			searchContext.getAttribute("shipmentStatuses"), null);

		if (shipmentStatuses != null) {
			BooleanFilter shipmentStatusesBooleanFilter = new BooleanFilter();

			for (long shipmentStatus : shipmentStatuses) {
				shipmentStatusesBooleanFilter.add(
					new TermFilter(
						Field.STATUS, String.valueOf(shipmentStatus)),
					BooleanClauseOccur.SHOULD);
			}

			shipmentStatusesBooleanFilter.add(
				new MissingFilter(Field.STATUS), BooleanClauseOccur.SHOULD);

			if (GetterUtil.getBoolean(
					searchContext.getAttribute("negateShipmentStatuses"))) {

				booleanFilter.add(
					shipmentStatusesBooleanFilter, BooleanClauseOccur.MUST_NOT);
			}
			else {
				booleanFilter.add(
					shipmentStatusesBooleanFilter, BooleanClauseOccur.MUST);
			}
		}
	}

}