/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.internal.search.spi.model.index.contributor;

import com.liferay.commerce.inventory.model.CommerceInventoryBookedQuantity;
import com.liferay.commerce.inventory.service.CommerceInventoryBookedQuantityLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;

/**
 * @author Brian I. Kim
 */
public class CommerceInventoryBookedQuantityModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<CommerceInventoryBookedQuantity> {

	public CommerceInventoryBookedQuantityModelIndexerWriterContributor(
		CommerceInventoryBookedQuantityLocalService
			commerceInventoryBookedQuantityLocalService) {

		_commerceInventoryBookedQuantityLocalService =
			commerceInventoryBookedQuantityLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _commerceInventoryBookedQuantityLocalService.
			getIndexableActionableDynamicQuery();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(
		CommerceInventoryBookedQuantity commerceInventoryBookedQuantity) {

		return IndexerWriterMode.UPDATE;
	}

	private final CommerceInventoryBookedQuantityLocalService
		_commerceInventoryBookedQuantityLocalService;

}